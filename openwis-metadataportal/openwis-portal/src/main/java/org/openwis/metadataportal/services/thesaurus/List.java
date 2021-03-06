//=============================================================================
//===	Copyright (C) 2001-2005 Food and Agriculture Organization of the
//===	United Nations (FAO-UN), United Nations World Food Programme (WFP)
//===	and United Nations Environment Programme (UNEP)
//===
//===	This program is free software; you can redistribute it and/or modify
//===	it under the terms of the GNU General Public License as published by
//===	the Free Software Foundation; either version 2 of the License, or (at
//===	your option) any later version.
//===
//===	This program is distributed in the hope that it will be useful, but
//===	WITHOUT ANY WARRANTY; without even the implied warranty of
//===	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//===	General Public License for more details.
//===
//===	You should have received a copy of the GNU General Public License
//===	along with this program; if not, write to the Free Software
//===	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//===
//===	Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
//===	Rome - Italy. email: GeoNetwork@fao.org
//==============================================================================

package org.openwis.metadataportal.services.thesaurus;

import jeeves.interfaces.Service;
import jeeves.server.ServiceConfig;
import jeeves.server.context.ServiceContext;
import org.fao.geonet.GeonetContext;
import org.fao.geonet.constants.Geonet;
import org.fao.geonet.constants.Params;
import org.fao.geonet.kernel.ThesaurusManager;
import org.jdom.Element;
import org.openwis.metadataportal.services.common.json.JeevesJsonWrapper;
import org.openwis.metadataportal.services.thesaurus.dto.DirectoryThesaurusDTO;
import org.openwis.metadataportal.services.thesaurus.dto.ListThesaurusDTO;
import org.openwis.metadataportal.services.thesaurus.dto.ThesaurusDTO;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;

//=============================================================================

/** Given a metadata id returns all associated categories. Called by the
  * metadata.category service
  */

public class List implements Service {
   //--------------------------------------------------------------------------
   //---
   //--- Init
   //---
   //--------------------------------------------------------------------------

   private static String LOCAL_DIR;

   private static String EXTERNAL_DIR;

   private String init_type;

   public void init(String appPath, ServiceConfig params) throws Exception {

      LOCAL_DIR = File.separator + Geonet.CodeList.LOCAL + File.separator
            + Geonet.CodeList.THESAURUS + File.separator;
      EXTERNAL_DIR = File.separator + Geonet.CodeList.EXTERNAL + File.separator
            + Geonet.CodeList.THESAURUS + File.separator;

      init_type = params.getValue(Params.TYPE, "_none_");
   }

   /** Filter on directory */
   private FilenameFilter directoryFilter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
         if (dir.isDirectory() && !name.startsWith("."))
            return true;
         else
            return false;
      }
   };

   /** Filter on directory */
   private FilenameFilter thesauriFilter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
         if (dir.isDirectory() && !name.startsWith("."))
            return true;
         else if (!dir.isDirectory() && name.endsWith(".xml") || name.endsWith(".rdf"))
            return true;
         else
            return false;
      }
   };

   //--------------------------------------------------------------------------
   //---
   //--- Service
   //---
   //--------------------------------------------------------------------------

   public Element exec(Element params, ServiceContext context) throws Exception {
      ListThesaurusDTO listThesaurusDTO = JeevesJsonWrapper.read(params, ListThesaurusDTO.class);
      GeonetContext gc = (GeonetContext) context.getHandlerContext(Geonet.CONTEXT_NAME);
      ThesaurusManager thesaurusMan = gc.getThesaurusManager();
      String THESAURUS_DIR = thesaurusMan.getThesauriDirectory();

      Element thesauriList = new Element("thesaurusList");

      String type = listThesaurusDTO.getType();

      if (type.equals("all-directories")) {
         listThesauri(thesauriList, THESAURUS_DIR + EXTERNAL_DIR, 1, directoryFilter,
               Geonet.CodeList.EXTERNAL);
         listThesauri(thesauriList, THESAURUS_DIR + LOCAL_DIR, 1, directoryFilter,
               Geonet.CodeList.LOCAL);
      } else if (type.equals("upload-directories")) {
         listThesauri(thesauriList, THESAURUS_DIR + EXTERNAL_DIR, 1, directoryFilter,
               Geonet.CodeList.EXTERNAL);
      } else if (type.equals("all-thesauri")) {
         listThesauri(thesauriList, THESAURUS_DIR + EXTERNAL_DIR, 2, thesauriFilter,
               Geonet.CodeList.EXTERNAL);
         listThesauri(thesauriList, THESAURUS_DIR + LOCAL_DIR, 2, thesauriFilter,
               Geonet.CodeList.LOCAL);
      } else if (type.equals("update-thesauri")) {
         listThesauri(thesauriList, THESAURUS_DIR + LOCAL_DIR, 3, thesauriFilter,
               Geonet.CodeList.LOCAL);
      } else {
         listThesauri(thesauriList, THESAURUS_DIR + EXTERNAL_DIR, 3, thesauriFilter,
               Geonet.CodeList.EXTERNAL);
         listThesauri(thesauriList, THESAURUS_DIR + LOCAL_DIR, 3, thesauriFilter,
               Geonet.CodeList.LOCAL);
      }

      
      java.util.List<DirectoryThesaurusDTO> dtoList = new ArrayList<DirectoryThesaurusDTO>();
      java.util.List a = thesauriList.getContent();
      for (Iterator iterator = a.iterator(); iterator.hasNext();) {
         DirectoryThesaurusDTO dto = new DirectoryThesaurusDTO();
         Element e = (Element) iterator.next();
         dto.setLabel(e.getAttributeValue("label"));
         java.util.List b = e.getContent();
         for (Iterator iterator2 = b.iterator(); iterator2.hasNext();) {
            Element t = (Element) iterator2.next();
            ThesaurusDTO dto2 = new ThesaurusDTO();
            dto2.setValue(t.getAttributeValue("value"));
            dto2.setDname(t.getAttributeValue("dname"));
            dto2.setFname(t.getAttributeValue("fname"));
            dto2.setType(t.getAttributeValue("type"));
            dto.getThesaurusListDTO().add(dto2);
         }
         dtoList.add(dto);
      }
      return JeevesJsonWrapper.send(dtoList);
   }

   /**
    * Browse directory tree and return thesaurus in xml and rdf format
    * 
    * @param params
    * @param context
    * @return
    * @throws Exception
    */
   private void listThesauri(Element list, String dir, int mode, FilenameFilter filter,
         String rootName) throws Exception {
      File thesauriDirectory = new File(dir);
      if (thesauriDirectory.isDirectory()) {

         File[] rdfDataDirectory = thesauriDirectory.listFiles(filter);
         for (int i = 0; i < rdfDataDirectory.length; i++) {

            if (rdfDataDirectory[i].isDirectory()) {

               if (mode == 1) { // Simple tree 
                  Element dirE = new Element("directory").setAttribute("label",
                        rdfDataDirectory[i].getName());
                  dirE.setAttribute("type", rootName);
                  list.addContent(dirE);
                  listThesauri(dirE, rdfDataDirectory[i].getAbsolutePath(), mode, filter, rootName);
               }

               else if (mode == 2) { // Complex tree local + external 
                  Element dirE = new Element("directory").setAttribute("label",
                        rdfDataDirectory[i].getName());
                  dirE.setAttribute("type", rootName);

                  // Search directory with same name
                  java.util.List children = list.getChildren("directory");
                  Element element = null;
                  for (Iterator iter = children.iterator(); iter.hasNext();) {
                     element = (Element) iter.next();
                     if (element.getAttribute("label").getValue()
                           .equals(rdfDataDirectory[i].getName())
                           )
                        break;
                     element = null;
                  }

                  if (element != null) {
                     listThesauri(element, rdfDataDirectory[i].getAbsolutePath(), mode, filter,
                           rootName);
                  } else {
                     list.addContent(dirE);
                     listThesauri(dirE, rdfDataDirectory[i].getAbsolutePath(), mode, filter,
                           rootName);
                  }
               }

               else
                  // Flat tree
                  listThesauri(list, rdfDataDirectory[i].getAbsolutePath(), mode, filter, rootName);

            } else if (mode != 1) { // File
               String thesaurusName = rootName
                     + '.'
                     + thesauriDirectory.getName()
                     + '.'
                     + rdfDataDirectory[i].getName().substring(0,
                           rdfDataDirectory[i].getName().indexOf(".rdf"));

               Element thesaurusE = new Element("thesaurus").setAttribute("value", thesaurusName);
               thesaurusE.setAttribute("type", rootName);
               thesaurusE.setAttribute("dname", thesauriDirectory.getName());
               thesaurusE.setAttribute("fname", rdfDataDirectory[i].getName());
               list.addContent(thesaurusE);
            }
         }
      }
   }

   // =====================================================================================

}
