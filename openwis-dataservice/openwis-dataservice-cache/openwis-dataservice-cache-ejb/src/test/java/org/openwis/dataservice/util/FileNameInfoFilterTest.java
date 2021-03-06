/**
 *
 */
package org.openwis.dataservice.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Short Description goes here. <p>
 * Explanation goes here. <p>
 *
 * @author <a href="mailto:franck.foutou@vcs.de">Franck Foutou</a>
 */
public class FileNameInfoFilterTest {
   
   public final String[] filenames = {
			// GTS
			"T_PGBE07_C_KWBC_20020610180000_D241_SIG_WEATHER_250-600_VT_06Z.tif",
			"A_HPWZ89LFPW131200RRA_C_LFPW_20020913160300.bin",
			"Z_IDN60000_C_AMMC_20020617000000.gif",
			"Z_LWDA_C_EGRR_20020617000000_LWDA16_0000.bin.Z",
			"T_SDCN50_C_CWAO_200204201530--_WKR_ECHOTOP,2-0,100M,AGL,78,N.gif",
			"Z__C_CWAO_2002032812----_CMC_reg_TMP_ISBL_500_ps60km_2002032812_P036.bin",
			"Z_SM_C_BABJ_20020520101502.txt",
			
			// MTP
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+CSR_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+CSR_C_EUMS_20110701090045.bin",
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+CSR_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+CSR_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+ELW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+ELW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+ELW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+ELW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HRV_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HRV_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HRV_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HRV_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HWW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HWW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HWW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+HWW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+RDC_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+RDC_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+RDC_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+RDC_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+WVW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+WVW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+WVW_C_EUMS_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET07+WVW_C_EUMS_20110701090045.bin", 

			// MSG
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+AMV_C_EUMG_20110701090045.bin",
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+AMV_C_EUMG_20110701090045.bin",
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+AMV_C_EUMG_20110701090045.bin",
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+AMV_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+AMV_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+AMV_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+ASR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+ASR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+ASR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+ASR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+ASR_C_EUMG_20110701090045.bin",
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+ASR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+CLA_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+CLA_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+CLA_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+CLA_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+CLA_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+CLA_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+CSR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+CSR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+CSR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+CSR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+CSR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+CSR_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+TH_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+TH_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+TH_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+TH_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+TH_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+TH_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+TOZ_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+TOZ_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+TOZ_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+TOZ_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET08+TOZ_C_EUMG_20110701090045.bin", 
			"W_XX-EUMETSAT-Darmstadt,SING+LEV+SAT,MET09+TOZ_C_EUMG_20110701090045.bin",
			
			// EPS
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+AMSUA_C_EUMP_20110701090858_00101_eps_o_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,NOAA18+AMSUA_C_EUMP_20090701102307_12085_eps_o_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SURFACE+SATELLITE,METOPA+ASCAT_C_EUMP_20110701090858_00101_eps_o_250_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SURFACE+SATELLITE,METOPA+ASCAT_C_EUMP_20110701090858_00101_eps_o_125_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SURFACE+SATELLITE,METOPA+ASCAT_C_EUMP_20110701090858_00101_eps_o_250_ssm_l2.bin",
			"W_XX-EUMETSAT-Darmstadt,SURFACE+SATELLITE,METOPA+ASCAT_C_EUMP_20110701090858_00101_eps_o_125_ssm_l2.bin",
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+ATOVS_C_EUMP_20110701090858_00101_eps_o_l2.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,NOAA18+ATOVS_C_EUMP_20090701102307_12085_eps_o_l2.bin",
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+AVHRR_C_EUMP_20110701090858_00101_eps_o_amv_l2.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,NOAA18+AVHRR_C_EUMP_20110701090858_00101_eps_o_amv_l2.bin", 
			"W_XX-EUMETSAT-Darmstadt,SURFACE+SATELLITE,METOPA+AVHRR_C_EUMP_20110701090858_00101_eps_o_ndvi_l2.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+GRAS_C_EUMP_20110701090858_00101_eps_o_thn_l1.bin",
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+HIRS_C_EUMP_20110701090858_00101_eps_o_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,NOAA18+HIRS_C_EUMP_20090701102307_12085_eps_o_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+IASI_C_EUMP_20110701090858_00101_eps_o_300_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+IASI_C_EUMP_20110701090858_00101_eps_o_pcs_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+IASI_C_EUMP_20110701090858_00101_eps_o_twt_l2.bin",
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+IASI_C_EUMP_20110701090858_00101_eps_o_clp_l2.bin",
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+IASI_C_EUMP_20110701090858_00101_eps_o_ozo_l2.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+IASI_C_EUMP_20110701090858_00101_eps_o_trg_l2.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,METOPA+MHS_C_EUMP_20110701090858_00101_eps_o_l1.bin", 
			"W_XX-EUMETSAT-Darmstadt,SOUNDING+SATELLITE,NOAA18+MHS_C_EUMP_20090701102307_12085_eps_o_l1.bin",
			
			// JASON2
			"W_XX-EUMETSAT-Darmstadt,SURFACE+SATELLITE,JASON2+OGDR_C_EUMS_20080722093000_A_111_222_20080722111500.bin",
			"W_US-NOAA-Washington,SURFACE+SATELLITE,JASON2+OGDR_C_KNES_20080722093000_A_111_222_20080722111500.bin",
			"W_FR-CNES-Toulouse,SURFACE+SATELLITE,JASON2+OGDR_C_LFPW_20080722093000_A_111_222_20080722111500.bin",

			// EARS
			"W_XX-EUMETSAT-edm,AMSUA,rars+noaa17+edm_C_EUMS_20090210181200_amsua_20090210_1812_noaa17_34476_edm_l1c_bufr.bin",
			"W_XX-EUMETSAT-ewa,AMSUA,rars+noaa18+ewa_C_EUMS_20090210122800_amsua_20090210_1228_noaa18_19208_ewa_l1c_bufr.bin",
			"W_XX-EUMETSAT-gan,AMSUA,rars+noaa17+gan_C_EUMS_20090210131300_amsua_20090210_1313_noaa17_34473_gan_l1c_bufr.bin",
			"W_XX-EUMETSAT-gil,AMSUA,rars+noaa15+gil_C_EUMS_20090210191100_amsua_20090210_1911_noaa15_55878_gil_l1c_bufr.bin",
			"W_XX-EUMETSAT-gil,AMSUA,rars+noaa17+gil_C_EUMS_20090210181200_amsua_20090210_1812_noaa17_34476_gil_l1c_bufr.bin",
			"W_XX-EUMETSAT-lan,AMSUA,rars+noaa15+lan_C_EUMS_20090211070700_amsua_20090211_0707_noaa15_55885_lan_l1c_bufr.bin",
			"W_XX-EUMETSAT-lan,AMSUA,rars+noaa18+lan_C_EUMS_20090210152400_amsua_20090210_1524_noaa18_19211_lan_l1c_bufr.bin",
			"W_XX-EUMETSAT-mas,AMSUA,rars+metopa+mas_C_EUMS_20090210121000_amsua_20090210_1210_metopa_12002_mas_l1c_bufr.bin",
			"W_XX-EUMETSAT-wal,AMSUA,rars+noaa17+wal_C_EUMS_20090210163600_amsua_20090210_1636_noaa17_34475_wal_l1c_bufr.bin",
			"W_XX-EUMETSAT-edm,AMSUB,rars+noaa17+edm_C_EUMS_20090210181200_amsub_20090210_1812_noaa17_34476_edm_l1c_bufr.bin",
			"W_XX-EUMETSAT-gan,AMSUB,rars+noaa17+gan_C_EUMS_20090210131300_amsub_20090210_1313_noaa17_34473_gan_l1c_bufr.bin",
			"W_XX-EUMETSAT-gil,AMSUB,rars+noaa15+gil_C_EUMS_20090210191100_amsub_20090210_1911_noaa15_55878_gil_l1c_bufr.bin",
			"W_XX-EUMETSAT-gil,AMSUB,rars+noaa17+gil_C_EUMS_20090210181200_amsub_20090210_1812_noaa17_34476_gil_l1c_bufr.bin",
			"W_XX-EUMETSAT-lan,AMSUB,rars+noaa15+lan_C_EUMS_20090211070700_amsub_20090211_0707_noaa15_55885_lan_l1c_bufr.bin",
			"W_XX-EUMETSAT-mia,AMSUB,rars+noaa16+mia_C_EUMS_20090210123400_amsub_20090210_1234_noaa16_43242_mia_l1c_bufr.bin",
			"W_XX-EUMETSAT-wal,AMSUB,rars+noaa17+wal_C_EUMS_20090210163600_amsub_20090210_1636_noaa17_34475_wal_l1c_bufr.bin",
			"W_XX-EUMETSAT-edm,HIRS,rars+noaa17+edm_C_EUMS_20090210181200_hirs_20090210_1812_noaa17_34476_edm_l1c_bufr.bin",
			"W_XX-EUMETSAT-ewa,HIRS,rars+noaa18+ewa_C_EUMS_20090210122800_hirs_20090210_1228_noaa18_19208_ewa_l1c_bufr.bin",
			"W_XX-EUMETSAT-gan,HIRS,rars+noaa17+gan_C_EUMS_20090210131300_hirs_20090210_1313_noaa17_34473_gan_l1c_bufr.bin",
			"W_XX-EUMETSAT-gil,HIRS,rars+noaa15+gil_C_EUMS_20090210191100_hirs_20090210_1911_noaa15_55878_gil_l1c_bufr.bin",
			"W_XX-EUMETSAT-gil,HIRS,rars+noaa17+gil_C_EUMS_20090210181200_hirs_20090210_1812_noaa17_34476_gil_l1c_bufr.bin",
			"W_XX-EUMETSAT-lan,HIRS,rars+noaa15+lan_C_EUMS_20090211070700_hirs_20090211_0707_noaa15_55885_lan_l1c_bufr.bin",
			"W_XX-EUMETSAT-lan,HIRS,rars+noaa18+lan_C_EUMS_20090210152400_hirs_20090210_1524_noaa18_19211_lan_l1c_bufr.bin",
			"W_XX-EUMETSAT-mas,HIRS,rars+metopa+mas_C_EUMS_20090210121000_hirs_20090210_1210_metopa_12002_mas_l1c_bufr.bin",
			"W_XX-EUMETSAT-mia,HIRS,rars+noaa16+mia_C_EUMS_20090210123400_hirs_20090210_1234_noaa16_43242_mia_l1c_bufr.bin",
			"W_XX-EUMETSAT-wal,HIRS,rars+noaa17+wal_C_EUMS_20090210163600_hirs_20090210_1636_noaa17_34475_wal_l1c_bufr.bin",
			"W_XX-EUMETSAT-ewa,MHS,rars+noaa18+ewa_C_EUMS_20090210122800_mhs_20090210_1228_noaa18_19208_ewa_l1c_bufr.bin",
			"W_XX-EUMETSAT-lan,MHS,rars+noaa18+lan_C_EUMS_20090210152400_mhs_20090210_1524_noaa18_19211_lan_l1c_bufr.bin",
			"W_XX-EUMETSAT-mas,MHS,rars+metopa+mas_C_EUMS_20090210121000_mhs_20090210_1210_metopa_12002_mas_l1c_bufr.bin",
			"W_NL-KNMI-DeBilt,ASCAT,ears+metopa+sva_C_EHDB_20090819074022_ascat_20090819_074022_metopa_14699_ear_o_250_ovw_l2_bufr.bin",
			"W_NL-KNMI-DeBilt,ASCAT,ears+metopa+sva_C_EHDB_20090819074022_ascat_20090819_074022_metopa_14699_ear_o_125_ovw_l2_bufr.bin"
	};
   
   @Test
   public void metadataFilterTest(){
	   WMOFNC wmofnc = null;
	   for (String filename : filenames){
		   
		   try {
			   wmofnc = FileNameParser.parseFileName(filename);
		   }
		   catch (ParseException e) {
			   e.printStackTrace();
		   }
		   assertNotNull(wmofnc);
		   String metadata = wmofnc.getMetadataURN();
           assertNotNull(metadata);

           FileNameInfoFilter filter = FileNameInfoFilter.createMetadataFilter(metadata);
           assertNotNull(filter);

           assertTrue(filter.accept(filename));
	   }
   }
   
   @Test
   public void productDateFilterTest(){
	   SimpleDateFormat formatter = new SimpleDateFormat("HH:mm'Z'");
	   formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	   for (String filename : filenames) {
		   WMOFNC info = null;
		   try {
			   info = FileNameParser.parseFileName(filename);
		   }
		   catch(ParseException e){
			   e.printStackTrace();
		   }
		   assertNotNull(info);

		   Date productDate = info.getProductDate();
		   assertNotNull(productDate);

		   String date = formatter.format(productDate);
		   String period = String.format("%1$s/%1$s", date);

		   FileNameInfoFilter filter = FileNameInfoFilter.createProductDateFilter(period);
		   assertNotNull(filter);

		   assertTrue(filter.accept(filename));
	   }
   }
}
