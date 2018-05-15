package at.tuwien.solranalysis;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import at.tuwien.solranalysis.SolranalysisApplication.CORETYPE;

@Service
public class RelevanceCheck {
	private Map<String, String[]> questions = new HashMap<>();
	private String[] q1 = {"CLEF2013wn-ChiC-Akaserehet2013".toLowerCase()}; 
	private String q1Name = "MAP UNINE's runs CHIC 2013 Polish task".toLowerCase();
	
	private String[] q2 = {"CLEF2013wn-ChiC-Akaserehet2013".toLowerCase()}; 
	private String q2Name = "parameter settings UNINE submissions CHIC AND 2013 Polish task".toLowerCase();
	
	private String[] q3 = {"CLEF2000wn-adhoc-GeyEt2000".toLowerCase(), "CLEF2000wn-adhoc-NieEt2000".toLowerCase(), "CLEF2000wn-adhoc-OardEt2000".toLowerCase(), "CLEF2000wn-adhoc-HiemstraEt2000".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000".toLowerCase(),
			"CLEF2000wn-adhoc-GeyEt2000".toLowerCase(), "CLEF2000wn-adhoc-OardEt2000".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000_2".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000_5".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000_6".toLowerCase(),
			"CLEF2000wn-adhoc-deVries2000_8".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000_4".toLowerCase(), "CLEF2000wn-adhoc-NieEt2000_5".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000_2".toLowerCase(),
			"CLEF2000wn-adhoc-BraschlerEt2000_4".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000_5".toLowerCase(), "CLEF2000wn-adhoc-BraschlerEt2000_6".toLowerCase()}; 
	private String q3Name = "precision Berkley team  2000 multilingual runs".toLowerCase();
	
	private String[] q4 = {"CLEF2001wn-adhoc-Kraaij2001".toLowerCase(), "CLEF2001wn-adhoc-Chen2001".toLowerCase(), "CLEF2001wn-adhoc-Savoy2001".toLowerCase(),
			"CLEF2001wn-adhoc-BraschlerEt2001".toLowerCase(), "CLEF2001wn-adhoc-Kraaij2001_8".toLowerCase(), "CLEF2001wn-adhoc-Savoy2001_2".toLowerCase()
			}; 
	private String q4Name = "language pairs multilingual experiments in CLEF AND *2001*".toLowerCase();
	
	private String[] q5 = {"CLEF2013wn-ImageCLEF-Zellhofer2013".toLowerCase(), "CLEF2013wn-ImageCLEF-StathopoulosEt2013".toLowerCase(), "CLEF2013wn-CLEFeHealth-BedrickEt2013".toLowerCase(), "CLEF2013wn-CHiC-PetrasEt2013".toLowerCase(),
			"CLEF2013wn-ImageCLEF-SanchezOroEt2013".toLowerCase(), "CLEF2013wn-ImageCLEF-GranaEt2013".toLowerCase(), "CLEF2013wn-CLEFeHealth-ZucconEt2013_2".toLowerCase(),
			"CLEF2013wn-CLEFeHealth-ZucconEt2013".toLowerCase(), "CLEF2013wn-CLEFeHealth-ChoiEt2013".toLowerCase(), "CLEF2013wn-ImageCLEF-VillegasEt2013_10".toLowerCase(),
			"CLEF2013wn-ImageCLEF-StathopoulosEt2013_1".toLowerCase(), "CLEF2013wn-ImageCLEF-StathopoulosEt2013_4".toLowerCase(), "CLEF2013wn-CLEFeHealth-ZucconEt2013".toLowerCase(),
			"CLEF2013wn-CLEFeHealth-CaballeroEt2013_1".toLowerCase(), "CLEF2013wn-ImageCLEF-VillegasEt2013_11".toLowerCase(), "CLEF2013wn-ImageCLEF-ChoiEt2013".toLowerCase()}; 
	private String q5Name = "size CLEF 2013 MAP".toLowerCase();
	
	private String[] q6 = {"CLEF2011wn-CLEF-IP-VermaEt2011".toLowerCase(), "CLEF2011wn-ImageCLEF-Luoet2011".toLowerCase(), "CLEF2011wn-CLEF-IP-VermaEt2011_4".toLowerCase(),
			"CLEF2011wn-CLEF-IP-MahdabiEt2011".toLowerCase()}; 
	private String q6Name = "What methods were used in retrieval tasks of clef AND *2011*".toLowerCase();
	
	private String[] q7 = {"CLEF2011wn-QA4MRE-BabychEt2011".toLowerCase(), "CLEF2011wn-ImageCLEF-CastellanosEt2011".toLowerCase(), "CLEF2011wn-ImageCLEF-MataEt2011".toLowerCase(), "CLEF2011wn-ImageCLEF-NowakEt2011".toLowerCase(),
			"CLEF2011wn-ImageCLEF-Luoet2011".toLowerCase(), "CLEF2011wn-ImageCLEF-Kapalthy-Crameret2011".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011".toLowerCase(), "CLEF2011wn-ImageCLEF-ZnaidiaEt2011".toLowerCase(),
			"CLEF2008wn-QACLEF-MartinezGonzalezEt2008".toLowerCase(), "CLEF2011wn-CLEF-IP-PiroiEt2011".toLowerCase(), "CLEF2011wn-CLEF-IP-VerberneEt2011_6".toLowerCase(), "CLEF2011wn-CLEF-IP-PiroiEt2011_2".toLowerCase(),
			"CLEF2011wn-CLEF-IP-SeoEt2011_7".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011_8".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011_9".toLowerCase(), "CLEF2011wn-CLEF-IP-VerberneEt2011".toLowerCase(), "CLEF2011wn-CLEF-IP-VerberneEt2011_2".toLowerCase(),
			"CLEF2011wn-CLEF-IP-CsurkaEt2011_6".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011_4".toLowerCase(), "CLEF2011wn-CLEF-IP-CsurkaEt2011_1".toLowerCase(),
			"CLEF2011wn-CLEF-IP-CsurkaEt2011_4".toLowerCase(), "CLEF2011wn-CLEF-IP-CsurkaEt2011_5".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011_1".toLowerCase()}; 
	private String q7Name = "What different runs exists in clef AND *2011* evaluations".toLowerCase();
	
	private String[] q8 = {"CLEF2009wn-ImageCLEF-RadhouaniEt2009".toLowerCase(), "CLEF2009wn-CLEFIP-RodaEt2009".toLowerCase(), "CLEF2009wn-adhoc-BuscaldiEt2009".toLowerCase(), "CLEF2009wn-MorphoChallenge-KurimoEt2009".toLowerCase(),
			"CLEF2009wn-VideoCLEF-PereaOrtegaEt2009".toLowerCase(), "CLEF2009wn-adhoc-AgirreEt2009b".toLowerCase(), "CLEF2009wn-CLEFIP-VerberneEt2009".toLowerCase(), "CLEF2009wn-adhoc-McNamee2009".toLowerCase(),
			"CLEF2009wn-ImageCLEF-BerberEt2009".toLowerCase(), "CLEF2009wn-ImageCLEF-ArafaEt2009_1".toLowerCase(), "CLEF2009wn-CLEFIP-VerberneEt2009_1".toLowerCase(), "CLEF2009wn-ImageCLEF-Ruiz2009_2".toLowerCase(), 
			"CLEF2009wn-ImageCLEF-ArafaEt2009_2".toLowerCase(), "CLEF2009wn-ImageCLEF-ZhuEt2009_1".toLowerCase(), "CLEF2009wn-CLEFIP-RodaEt2009_7".toLowerCase(), "CLEF2009wn-GridCLEF-EiblEt2009".toLowerCase(), 
			"CLEF2009wn-adhoc-HosseinEt2009_6".toLowerCase(), "CLEF2009wn-adhoc-HosseinEt2009_7".toLowerCase(), "CLEF2009wn-adhoc-FernandezEt2009_1".toLowerCase(), "CLEF2009wn-GridCLEF-EiblEt2009_9".toLowerCase()}; 
	private String q8Name = "What are the MAP values for different tasks and experminets in *CLEF* AND *2009*".toLowerCase();
	
	private String[] q9 = {"CLEF2009wn-CLEFIP-RodaEt2009".toLowerCase(), "CLEF2009wn-QACLEF-TurmoEt2009".toLowerCase()}; 
	private String q9Name = "Which institutions or groups participated to the *CLEF* AND *2009*".toLowerCase();
	
	private String[] q10 = {"CLEF2009wn-QACLEF-PerezEt2009".toLowerCase(), "CLEF2008wn-VideoCLEF-VillenaRomanEt2008".toLowerCase(), "CLEF2008wn-adhoc-DolamicEt2008".toLowerCase(), "CLEF2008wn-ImageCLEF-TorjmenEt2008a".toLowerCase(),
			"CLEF2008wn-adhoc-DolamicEt2008_13".toLowerCase(), "CLEF2008wn-adhoc-DolamicEt2008_19".toLowerCase(), "CLEF2008wn-adhoc-SorgEt2008_2".toLowerCase(), "CLEF2008wn-QACLEF-RodrigoEt2008_2".toLowerCase(), "CLEF2008wn-ImageCLEF-LanaSerranoEt2008a_6".toLowerCase(),
			"CLEF2008wn-QACLEF-FornerEt2008_16".toLowerCase(), "CLEF2008wn-QACLEF-FornerEt2008_17".toLowerCase(), "CLEF2008wn-QACLEF-GlocknerEt2008_4".toLowerCase()}; 
	private String q10Name = "what precision clef AND *2008*?".toLowerCase();
	
	private String[] q11 = {"15-NTCIR10-RITE2-LiuM.html".toLowerCase(), "07-NTCIR10-1CLICK-TomohiroM.html".toLowerCase(), "06-NTCIR10-RITE2-ShihC.html".toLowerCase(),
			"11-NTCIR10-MEDNLP-NomuraY.html".toLowerCase(), "12-NTCIR10-MEDNLP-TakeuchiK.html".toLowerCase(), "02-NTCIR10-RITE2-MoritaH.html".toLowerCase(), 
			"07-NTCIR10-MATH-HaginoH_7", "12-NTCIR10-SPOKENDOC-SakamotoN_14".toLowerCase(), "10-NTCIR10-MEDNLP-MiuraY_5".toLowerCase(), "02-NTCIR10-MEDNLP-FujiiR_4".toLowerCase(),
			"01-NTCIR10-OV-CROSSLINK2-TangL".toLowerCase(), "09-NTCIR10-SPOKENDOC-FuruyaY_6".toLowerCase(), "11-NTCIR10-CROSSLINK2-TangL_8".toLowerCase(), "01-NTCIR10-OV-CROSSLINK2-TangL".toLowerCase(),
			"04-NTCIR10-MEDNLP-ImachiH_4".toLowerCase()}; 
	private String q11Name = "precision *NTCIR10* experiments?".toLowerCase();
	
	private String[] q12 = {"01-NTCIR9-OV-RITE-ShimaH-revised-20120424".toLowerCase(), "01-NTCIR9-OV-CROSSLINK-TangL.html".toLowerCase(), "01-NTCIR9-OV-VisEx-KatoT.html".toLowerCase(), 
			"01-NTCIR9-PATENTMT-GotoI_42".toLowerCase(), "01-NTCIR9-OV-RITE-ShimaH-revised-20120424_2".toLowerCase()}; 
	private String q12Name = "*institution*  *groups* + *particip* *Ntcir9*".toLowerCase();
	
	private String[] q13 = {"CLEF2009wn-ImageCLEF-RadhouaniEt2009.html".toLowerCase(), "CLEF2009wn-CLEFIP-RodaEt2009.html".toLowerCase(), "CLEF2009wn-adhoc-BuscaldiEt2009.html".toLowerCase(),
			"CLEF2009wn-QACLEF-HartrumpfEt2009.html".toLowerCase(), "CLEF2009wn-VideoCLEF-PereaOrtegaEt2009.html".toLowerCase(), "CLEF2009wn-adhoc-AgirreEt2009b.html".toLowerCase(), "CLEF2009wn-adhoc-McNamee2009.html".toLowerCase(), 
			"CLEF2009wn-ImageCLEF-BerberEt2009.html".toLowerCase(), "CLEF2009wn-CLEFIP-RodaEt2009", "CLEF2009wn-ImageCLEF-ArafaEt2009".toLowerCase(), "CLEF2009wn-adhoc-ZazoEt2009".toLowerCase(),
			"CLEF2009wn-CLEFIP-RodaEt2009_9".toLowerCase(), "CLEF2009wn-INFILE-BesanconEt2009_7".toLowerCase(), "CLEF2009wn-INFILE-BesanconEt2009_11".toLowerCase(),
			"CLEF2009wn-INFILE-BesanconEt2009_12".toLowerCase(), "CLEF2009wn-INFILE-BesanconEt2009_13CLEF2009wn-INFILE-BesanconEt2009_13".toLowerCase(),
			"CLEF2009wn-ImageCLEF-Ruiz2009_2".toLowerCase(), "CLEF2009wn-QACLEF-IfteneEt2009b_4".toLowerCase(), "CLEF2009wn-VideoCLEF-DobrilaEt2009_2".toLowerCase(), "CLEF2009wn-QACLEF-BarraganEt2009_4".toLowerCase(),
			"CLEF2009wn-INFILE-BesanconEt2009_4".toLowerCase(), "CLEF2009wn-VideoCLEF-DobrilaEt2009_1".toLowerCase(), "CLEF2009wn-ImageCLEF-NavarroEt2009".toLowerCase(), "CLEF2009wn-ImageCLEF-MoulinEt2009_5".toLowerCase()}; 
	private String q13Name = "What different runs or experminents are represented in CLEF 2009?".toLowerCase();
	
	private String[] q14 = {"CLEF2011wn-CLEF-IP-VermaEt2011.html".toLowerCase(), "CLEF2011wn-ImageCLEF-CastellanosEt2011.html".toLowerCase(), "CLEF2011wn-ImageCLEF-MataEt2011.html".toLowerCase(), "CLEF2011wn-PAN-Escalante2011.html".toLowerCase(),
			"CLEF2011wn-ImageCLEF-NowakEt2011.html".toLowerCase(), "CLEF2011wn-ImageCLEF-Luoet2011.html".toLowerCase(), "CLEF2011wn-ImageCLEF-Kapalthy-Crameret2011.html".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011.html".toLowerCase(), "CLEF2011wn-PAN-GrozeaEt2011.html".toLowerCase(),
			"CLEF2011wn-CLEF-IP-VerberneEt2011_6".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011_8".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011_9".toLowerCase(), "CLEF2011wn-CLEF-IP-CsurkaEt2011_5".toLowerCase(), 
			"CLEF2011wn-ImageCLEF-ZellhoeferEt2011_7".toLowerCase(), "CLEF2011wn-CLEF-IP-CsurkaEt2011".toLowerCase(), "CLEF2011wn-CLEF-IP-SeoEt2011".toLowerCase()}; 
	private String q14Name = "recall precision clef AND *2011*?".toLowerCase();
	
	private String[] q15 = {"LEF2006wn-adhoc-LamAdesinaEt2006".toLowerCase()}; 
	private String q15Name = "Which retrieval systems or methods were used in the Spanish monolingual tasks?".toLowerCase();

	private String[] q16 = {"CLEF2012wn-ImageCLEF-HuangEt2012".toLowerCase(), "CLEF2012wn-CLEFIP-SalampasisEt2012".toLowerCase(), "CLEF2012wn-CLEFIP-AnderssonEt2012".toLowerCase(),
			"CLEF2012wn-ImageCLEF-CrespoEt2012".toLowerCase(), "CLEF2012wn-CLEFIP-SalampasisEt2012".toLowerCase(), "CLEF2012wn-ImageCLEF-ThomeeEt2012".toLowerCase(), "CLEF2012wn-CLEFIP-JurgensEt2012".toLowerCase()}; 
	private String q16Name = "MAP task retrieval *CLEF* AND *2012*".toLowerCase();
	
	private String[] q17 = {"03-NTCIR8-GeoTime-MachadoJ.html".toLowerCase(), "04-NTCIR8-IR4QA-CaiD.html".toLowerCase(), "08-NTCIR8-PATMN-GuiJ.html".toLowerCase(), 
			"06-NTCIR8-IR4QA-TangL.html".toLowerCase(), "05-NTCIR8-IR4QA-ZhouD.html".toLowerCase(), "03-NTCIR8-CQA-SongY.html".toLowerCase(), "06-NTCIR8-CCLQA-LinC_20".toLowerCase(),
			"06-NTCIR8-CCLQA-LinC_21".toLowerCase(), "06-NTCIR8-CCLQA-LinC_22".toLowerCase(), "06-NTCIR8-CCLQA-LinC_23".toLowerCase(), "01-NTCIR8-OV-GeoTime-GeyF_10".toLowerCase(), "01-NTCIR8-OV-GeoTime-GeyF_12".toLowerCase(),
			"01-NTCIR8-OV-CCLQA-MitamuraT_3".toLowerCase(), "01-NTCIR8-OV-CCLQA-MitamuraT_4".toLowerCase(), "04-NTCIR8-IR4QA-CaiD_11".toLowerCase(), "05-NTCIR8-IR4QA-ZhouD_6".toLowerCase(), 
			"03-NTCIR8-IR4QA-LinM_6".toLowerCase(), "03-NTCIR8-IR4QA-LinM_8".toLowerCase(), "08-NTCIR8-MOAT-HuangW_2".toLowerCase(), "01-NTCIR8-OV-GeoTime-GeyF_4".toLowerCase(),
			"01-NTCIR8-OV-GeoTime-GeyF_8".toLowerCase(), "05-NTCIR8-MOAT-BalahurA_6".toLowerCase(), "05-NTCIR8-MOAT-BalahurA_7".toLowerCase(), "01-NTCIR8-OV-GeoTime-GeyF_5".toLowerCase(), "01-NTCIR8-OV-GeoTime-GeyF_3".toLowerCase()}; 
	private String q17Name = "*runs* AND *NTCIR8* evaluation".toLowerCase();
	
	private String[] q18 = {"03-NTCIR7-MOAT-QuL.html".toLowerCase(), "01-NTCIR7-OV-CCLQA-MitamuraT-revised-20090113.html".toLowerCase(), "04-NTCIR7-MuST-T-UenishiY.html".toLowerCase(), 
			"02-NTCIR7-MuST-F-YoshidaM.html".toLowerCase(), "06-NTCIR7-MOAT-HuangY.html".toLowerCase(), "02-NTCIR7-MuST-T-InoueT.html".toLowerCase(),
			"02-NTCIR7-CCLQA-WuY_11".toLowerCase(), "10-NTCIR7-MOAT-KobayashiD_6".toLowerCase(), "01-NTCIR7-OV-CCLQA-MitamuraT_10".toLowerCase(),
			"11-NTCIR7-MOAT-KuL".toLowerCase(), "08-NTCIR7-MOAT-KimJ_6".toLowerCase(), "02-NTCIR7-MuST-T-InoueT_10".toLowerCase(), "08-NTCIR7-MOAT-KimJ_8".toLowerCase(),
			"01-NTCIR7-OV-MuST-KatoT_12".toLowerCase(), "01-NTCIR7-OV-MuST-KatoT_13".toLowerCase(), "11-NTCIR7-MOAT-KuL_6".toLowerCase(), "17-NTCIR7-MOAT-Villena-RomanJ_3".toLowerCase()}; 
	private String q18Name = "precision recall *NTCIR*7*".toLowerCase();
	
	private String[] q19 = {"03-NTCIR7-MOAT-QuL.html".toLowerCase(), "04-NTCIR7-MuST-T-UenishiY.html".toLowerCase(), "18-NTCIR7-MOAT-WuY.html".toLowerCase(), 
			"02-NTCIR7-MuST-F-YoshidaM.html".toLowerCase(), "08-NTCIR7-MOAT-KimJ_4".toLowerCase(), "03-NTCIR7-MuST-F-TakamaY_3".toLowerCase(),
			"08-NTCIR7-MOAT-KimJ_6".toLowerCase(), "03-NTCIR7-MOAT-QuL_7".toLowerCase(), "02-NTCIR7-MuST-T-InoueT_10".toLowerCase()}; 
	private String q19Name = "f-measure AND *NTCIR*7*".toLowerCase();
	
	private String[] q20 = {"CLEF2013wn-ImageCLEF-Zellhofer2013.html".toLowerCase(), "CLEF2013wn-PAN-MechtiEt2013.html".toLowerCase(), "CLEF2013wn-PAN-HalvaniEt2013.html".toLowerCase(), 
			"CLEF2013wn-ImageCLEF-GranaEt2013.html".toLowerCase(), "CLEF2013wn-CLEFeHealth-ZucconEt2013_2".toLowerCase(), "CLEF2013wn-CLEFeHealth-ZucconEt2013_3".toLowerCase(),
			"CLEF2013wn-CLEFeHealth-TangEt2013_2".toLowerCase(), "CLEF2013wn-CLEFeHealth-PatrickEt2013_3".toLowerCase(), "CLEF2013wn-CLEFeHealth-ZucconEt2013_3".toLowerCase(), "CLEF2013wn-RepLab-MosqueraEt2013_5".toLowerCase()}; 
	private String q20Name = "*f-measure*  CLEF AND *2013*".toLowerCase();
	
	private String[] q21 = {"CLEF2012wn-PAN-GrozeaEt2012.html".toLowerCase(), "CLEF2012wn-PAN-KontostathisEt2012.html".toLowerCase(), "CLEF2012wn-CLEFIP-SalampasisEt2012.html".toLowerCase(),
			"CLEF2012wn-CLEFIP-AnderssonEt2012.html".toLowerCase(), "CLEF2012wn-PAN-deGraaffEt2012.html".toLowerCase(), "CLEF2012wn-PAN-VillatoroTelloEt2012b.html".toLowerCase(),
			"CLEF2012wn-CLEFIP-SalampasisEt2012_3".toLowerCase(), "CLEF2012wn-RepLab-AmigoEt2012_4".toLowerCase(), "CLEF2012wn-RepLab-AmigoEt2012_5".toLowerCase(), "CLEF2012wn-PAN-deGraaffEt2012_9".toLowerCase(),
			"CLEF2012wn-PAN-MorrisEt2012_3".toLowerCase(), "CLEF2012wn-PAN-SuchomelEt2012_3".toLowerCase(), "CLEF2012wn-PAN-SuchomelEt2012_3".toLowerCase(), "CLEF2012wn-CLEFIP-JurgensEt2012_2".toLowerCase()}; 
	private String q21Name = "precision recall task retrieval *CLEF* AND *2012*".toLowerCase();
	
	private String[] q22 = {"CLEF2007wn-GeoCLEF-FerresEt2007a.html".toLowerCase(), "CLEF2007wn-adhoc-BandyopadhyayEt2007.html".toLowerCase(), "CLEF2007wn-GeoCLEF-LevelingEt2007.html".toLowerCase(), 
			"CLEF2007wn-QACLEF-MollaEt2007.html".toLowerCase(), "CLEF2007wn-ImageCLEF-EscalanteEt2007.html".toLowerCase(), "CLEF2007wn-ImageCLEF-ChangEt2007.html".toLowerCase(), "CLEF2007wn-adhoc-MajumderEt2007.html".toLowerCase(),
			"CLEF2007wn-ImageCLEF-LanaSerranoEt2007.html".toLowerCase(), "CLEF2007wn-ImageCLEF-TushabeEt2007.html".toLowerCase(), "CLEF2007wn-ImageCLEF-Hoi2007.html".toLowerCase(), "CLEF2007wn-CLSR-Huurnink2007.html".toLowerCase(),
			"CLEF2007wn-adhoc-NogueraEt2007.html".toLowerCase(), "CLEF2007wn-DomainSpecific-FautschEt2007_9".toLowerCase(), "CLEF2007wn-ImageCLEF-DeselaersEt2007a_7".toLowerCase(),
			"CLEF2007wn-ImageCLEF-DeselaersEt2007a_8".toLowerCase(), "CLEF2007wn-ImageCLEF-DeselaersEt2007a_9".toLowerCase(), "CLEF2007wn-ImageCLEF-GrubingerEt2007_5".toLowerCase(),
			"CLEF2007wn-ImageCLEF-VillenaRomanEt2007_2".toLowerCase(), "CLEF2007wn-ImageCLEF-ZhouEt2007_2".toLowerCase(), "CLEF2007wn-ImageCLEF-ZhouEt2007_4".toLowerCase(), "CLEF2007wn-ImageCLEF-ZhouEt2007_5".toLowerCase(),
			"CLEF2007wn-ImageCLEF-ZhouEt2007_6".toLowerCase(), "CLEF2007wn-adhoc-KumarChinnakotlaEt2007_8".toLowerCase(), "CLEF2007wn-WebCLEF-JijkounEt2007a_4".toLowerCase(), "CLEF2007wn-GeoCLEF-KolleEt2007_2".toLowerCase(),
			"CLEF2007wn-adhoc-KekebaTuneEt2007_1".toLowerCase(), "CLEF2007wn-adhoc-KekebaTuneEt2007_2".toLowerCase(), "CLEF2007wn-QACLEF-WangEt2007_2".toLowerCase(), "CLEF2007wn-adhoc-HeuwingEt2007_1".toLowerCase(), 
			"CLEF2007wn-ImageCLEF-ZhouEt2007_6".toLowerCase(), "CLEF2007wn-CLSR-PecinaEt2007_4".toLowerCase(), "CLEF2007wn-CLSR-PecinaEt2007_6".toLowerCase(), "CLEF2007wn-CLSR-PecinaEt2007_10".toLowerCase()}; 
	private String q22Name = "run CLEF AND 2007".toLowerCase();
	
	
	private String[] q23 = {"pro-ICTNET_session_3".toLowerCase()}; 
	private String q23Name = "systems vertical selection TREC AND 2014 FedWeb track".toLowerCase();
	
	private String[] q24 = {"CLEF2005wn-ImageCLEF-Appendix_C.html".toLowerCase(), "CLEF2005wn-adhoc-SavoyEt2005.html".toLowerCase(), "CLEF2005wn-CLSR-Appendix_C.html".toLowerCase(), "CLEF2005wn-GeoCLEF-Leidner2005.html".toLowerCase(),
			"CLEF2005wn-adhoc-GoniMenoyoEt2005.html".toLowerCase(), "CLEF2005wn-GeoCLEF-Leidner2005_3".toLowerCase(), "CLEF2005wn-DomainSpecific-Appendix_A_840".toLowerCase(), "CLEF2005wn-DomainSpecific-Appendix_A_459".toLowerCase(), "CLEF2005wn-DomainSpecific-Appendix_A_462".toLowerCase(),
			"CLEF2005wn-DomainSpecific-Appendix_A_465".toLowerCase(), "CLEF2005wn-DomainSpecific-Appendix_A_468".toLowerCase(), "CLEF2005wn-DomainSpecific-Appendix_A_588".toLowerCase(),
			"CLEF2005wn-GeoCLEF-Appendix_B".toLowerCase()}; 
	private String q24Name = "recall precision clef AND *2005*?".toLowerCase();
	
	private String[] q25 = {"CLEF2012wn-ImageCLEF-HuangEt2012".toLowerCase(), "CLEF2012wn-ImageCLEF-HuangEt2012_3".toLowerCase()}; 
	private String q25Name = "BUAA AUDR ImageCLEF 2012 photo annotation retrieval tasks".toLowerCase();
	
	@PostConstruct
	public void RelevanceCheckInit() {
		questions.put(q1Name, q1);
		questions.put(q2Name, q2);
		questions.put(q3Name, q3);
		questions.put(q4Name, q4);
		questions.put(q5Name, q5);
		questions.put(q6Name, q6);
		questions.put(q7Name, q7);
		questions.put(q8Name, q8);
		questions.put(q9Name, q9);
		questions.put(q10Name, q10);
		questions.put(q11Name, q11);
		questions.put(q12Name, q12);
		questions.put(q13Name, q13);
		questions.put(q14Name, q14);
		questions.put(q15Name, q15);
		questions.put(q16Name, q16);
		questions.put(q17Name, q17);
		questions.put(q18Name, q18);
		questions.put(q19Name, q19);
		questions.put(q20Name, q20);
		questions.put(q21Name, q21);
		questions.put(q22Name, q22);
		questions.put(q23Name, q23);
		questions.put(q24Name, q24);
		questions.put(q25Name, q25);
	}
	
	public Integer chechRelevance(String questionid, String documentid,CORETYPE coreType) {
		if(questions.get(questionid.toLowerCase()) != null) {
			;
			Set<String> lst = new HashSet<>(Arrays.asList(questions.get(questionid.toLowerCase())));
			if(lst.stream().anyMatch(action -> action.matches(documentid.toLowerCase().replace("[", "").replace("]", "") + "*"))) {
				return 1;
			}
		}
		return 0;
	}
}
