package mataf.halbanathon.operationsteps;

import java.util.Enumeration;
import java.util.Vector;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.DataCollection;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.common.testcases.ServerOperationTestCase;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.extensions.AssertBTTFramework;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PreparePirteyLakoachMizdamenTest extends ServerOperationTestCase {
	private PreparePirteyLakoachMizdamen step;

	/**
	 * Constructor for HalbanaHotTransmitSugLakoachOpStepTest.
	 * @param arg0
	 */
	public PreparePirteyLakoachMizdamenTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		step = new PreparePirteyLakoachMizdamen();

		DSEServerOperation serverop = (DSEServerOperation) DSEServerOperation.readObject("halbanatHonTransmitDetailsServerOp");
		step.setOperation(serverop);

		initSourceCollections();
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void initSourceCollections() throws Exception {
		KeyedCollection sourceKcoll = (KeyedCollection) step.getElementAt("GLSG_GLBL");
		initCollections(sourceKcoll);

		sourceKcoll = (KeyedCollection) step.getElementAt("GLSF_MAZAV");
		initCollections(sourceKcoll);

		sourceKcoll = (KeyedCollection) step.getElementAt("GLSF_GLBL");
		initCollections(sourceKcoll);

		sourceKcoll = (KeyedCollection) step.getElementAt("GLSE_GLBL");
		initCollections(sourceKcoll);

		sourceKcoll = (KeyedCollection) step.getElementAt("HASS_LAK_PRATIM");
		initCollections(sourceKcoll);

		sourceKcoll = (KeyedCollection) step.getElementAt("HASX_PIRTEY_CHESHBON");
		initCollections(sourceKcoll);

		sourceKcoll = (KeyedCollection) step.getElementAt("GLSX_K86P_PARAMS");
		initCollections(sourceKcoll);

		sourceKcoll = (KeyedCollection) step.getElementAt("HASS_LAKOACH_SUG");
		initCollections(sourceKcoll);
	}

	//		(3)	Check the specific assignment of GKAI_HDR
	public void testAssign_GKAI_HDR() throws Exception {
		step.assign_GKAI_HDR();

		AssertBTTFramework.assertEqualsConstantValue("The value in GKSI_HDR.GL_PEULA should be equal to Z410", step, "Z410", "GKSI_HDR.GL_PEULA");
	}

	//		(5)	Check GL_TRANS_ID
	public void testAssign_GL_TRANS_ID() throws Exception {
		step.assign_GL_TRANS_ID();

		AssertBTTFramework.assertEqualsConstantValue("The value in \"GKSI_HDR.GL_TRANS_ID\" should be O361", step, "O361", "GKSI_HDR.GL_TRANS_ID");

		AssertBTTFramework.assertEqualsConstantValue(
			"The value in \"GKSR_HDR.GL_TRANS_ID\" should be HM",
			step,
			"HM",
			"HASR_MIZDAMEN_PRATIM.HostHeaderReplyData.GKSR_HDR.GL_TRANS_ID");
	}
	//		(7)	Check HAAI_MIZD_PRATIM assignment
	public void testAssign_HAAI_MIZD_PRATIM() throws Exception {
		step.assign_HAAI_MIZD_PRATIM();

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_MIZDAMEN_PRATIM.HA_COUNTRY_CODE should be equal to the value in HASS_LAK_PRATIM.HA_COUNTRY_CODE",
			step,
			"HASS_LAK_PRATIM.HA_COUNTRY_CODE",
			"HASI_MIZDAMEN_PRATIM.HA_COUNTRY_CODE");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_MIZDAMEN_PRATIM.OP_TZ should be equal to the value in HASS_LAK_PRATIM.HA_TZ",
			step,
			"HASS_LAK_PRATIM.HA_TZ",
			"HASI_MIZDAMEN_PRATIM.OP_TZ");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_MIZDAMEN_PRATIM.HASG_TR_LEDA should be equal to the value in HASS_LAK_PRATIM.HASG_TR_LEDA",
			step,
			"HASS_LAK_PRATIM.HASG_TR_LEDA",
			"HASI_MIZDAMEN_PRATIM.HASG_TR_LEDA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_MIZDAMEN_PRATIM.HASG_TR_HANPAKA should be equal to the value in HASS_LAK_PRATIM.HASG_TR_HANPAKA",
			step,
			"HASS_LAK_PRATIM.HASG_TR_HANPAKA",
			"HASI_MIZDAMEN_PRATIM.HASG_TR_HANPAKA");
	}

	//		(8)	Check HA_SW_IMUT_MALAM
	public void testAssign_HA_SW_IMUT_MALAM() throws Exception {
		step.assign_HA_SW_IMUT_MALAM();
		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_MIZDAMEN_PRATIM.HA_SW_IMUT_MALAM should be equal to the value in HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM",
			step,
			"HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM",
			"HASI_MIZDAMEN_PRATIM.HA_SW_IMUT_MALAM");
	}

	public void testAssign_HAAI_INQ_SHIDUR() throws Exception {
		step.assign_HAAI_INQ_SHIDUR();

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GLSG_GLBL.GL_BANK should be equal to the value in GLSX_K86P_PARAMS.HA_BANK",
			step,
			"GLSX_K86P_PARAMS.HA_BANK",
			"GLSG_GLBL.GL_BANK");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR.GL_SNIF should be equal to the value in GLSX_K86P_PARAMS.HA_SNIF",
			step,
			"GLSX_K86P_PARAMS.HA_SNIF",
			"GKSI_HDR.GL_SNIF");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR.GL_SCH should be equal to the value in GLSX_K86P_PARAMS.HA_SCH",
			step,
			"GLSX_K86P_PARAMS.HA_SCH",
			"GKSI_HDR.GL_SCH");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR.GL_CH should be equal to the value in GLSX_K86P_PARAMS.HA_CH",
			step,
			"GLSX_K86P_PARAMS.HA_CH",
			"GKSI_HDR.GL_CH");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR.GL_MATBEA should be equal to the value in GLSX_K86P_PARAMS.HA_MATBEA",
			step,
			"GLSX_K86P_PARAMS.HA_MATBEA",
			"GKSI_HDR.GL_MATBEA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR.GL_PEULA should be equal to the value in GLSX_K86P_PARAMS.HA_SEMEL_PEULA",
			step,
			"GLSX_K86P_PARAMS.HA_SEMEL_PEULA",
			"GKSI_HDR.GL_PEULA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR_CONT.GL_SIDURI_TAHANA should be equal to the value in GLSX_K86P_PARAMS.HA_SIDURI_TAHANA",
			step,
			"GLSX_K86P_PARAMS.HA_SIDURI_TAHANA",
			"GKSI_HDR_CONT.GL_SIDURI_TAHANA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_MISPAR_TAHANA should be equal to the value in GLSX_K86P_PARAMS.HA_MISPAR_TAHANA",
			step,
			"GLSX_K86P_PARAMS.HA_MISPAR_TAHANA",
			"HASI_PRATIM_SHIDUR.HA_MISPAR_TAHANA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SIDURI_TAHANA should be equal to the value in GLSX_K86P_PARAMS.HA_SIDURI_TAHANA",
			step,
			"GLSX_K86P_PARAMS.HA_SIDURI_TAHANA",
			"HASI_PRATIM_SHIDUR.HA_SIDURI_TAHANA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SUG_PEULA should be equal to the value in GLSX_K86P_PARAMS.HA_SUG_PEULA",
			step,
			"GLSX_K86P_PARAMS.HA_SUG_PEULA",
			"HASI_PRATIM_SHIDUR.HA_SUG_PEULA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_PEULA_KOD_NOSE should be equal to the value in GLSX_K86P_PARAMS.HA_KOD_NOSE",
			step,
			"GLSX_K86P_PARAMS.HA_KOD_NOSE",
			"HASI_PRATIM_SHIDUR.HA_PEULA_KOD_NOSE");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_BANK should be equal to the value in GLSX_K86P_PARAMS.HA_BANK",
			step,
			"GLSX_K86P_PARAMS.HA_BANK",
			"HASI_PRATIM_SHIDUR.HA_BANK");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SNIF should be equal to the value in GLSX_K86P_PARAMS.HA_SNIF",
			step,
			"GLSX_K86P_PARAMS.HA_SNIF",
			"HASI_PRATIM_SHIDUR.HA_SNIF");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SCH should be equal to the value in GLSX_K86P_PARAMS.HA_SCH",
			step,
			"GLSX_K86P_PARAMS.HA_SCH",
			"HASI_PRATIM_SHIDUR.HA_SCH");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_CH should be equal to the value in GLSX_K86P_PARAMS.HA_CH",
			step,
			"GLSX_K86P_PARAMS.HA_CH",
			"HASI_PRATIM_SHIDUR.HA_CH");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_MATBEA should be equal to the value in GLSX_K86P_PARAMS.HA_MATBEA",
			step,
			"GLSX_K86P_PARAMS.HA_MATBEA",
			"HASI_PRATIM_SHIDUR.HA_MATBEA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SCHUM should be equal to the value in GLSX_K86P_PARAMS.HA_SCHUM",
			step,
			"GLSX_K86P_PARAMS.HA_SCHUM",
			"HASI_PRATIM_SHIDUR.HA_SCHUM");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SCHUM_NIS should be equal to the value in GLSX_K86P_PARAMS.HA_SCHUM_NIS",
			step,
			"GLSX_K86P_PARAMS.HA_SCHUM_NIS",
			"HASI_PRATIM_SHIDUR.HA_SCHUM_NIS");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_MISPAR_KG should be equal to the value in GLSX_K86P_PARAMS.HA_MISPAR_KG",
			step,
			"GLSX_K86P_PARAMS.HA_MISPAR_KG",
			"HASI_PRATIM_SHIDUR.HA_MISPAR_KG");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_AMIT_KIDOMET should be equal to the value in GLSX_K86P_PARAMS.HA_AMIT_KIDOMET",
			step,
			"GLSX_K86P_PARAMS.HA_AMIT_KIDOMET",
			"HASI_PRATIM_SHIDUR.HA_AMIT_KIDOMET");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_AMIT_SIYOMET should be equal to the value in GLSX_K86P_PARAMS.HA_AMIT_SIYOMET",
			step,
			"GLSX_K86P_PARAMS.HA_AMIT_SIYOMET",
			"HASI_PRATIM_SHIDUR.HA_AMIT_SIYOMET");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_TEUR_PEULA should be equal to the value in GLSX_K86P_PARAMS.HA_TEUR_PEULA",
			step,
			"GLSX_K86P_PARAMS.HA_TEUR_PEULA",
			"HASI_PRATIM_SHIDUR.HA_TEUR_PEULA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SUG_LAKOACH should be equal to the value in HASS_LAKOACH_SUG.HA_SUG_LAKOACH",
			step,
			"HASS_LAKOACH_SUG.HA_SUG_LAKOACH",
			"HASI_PRATIM_SHIDUR.HA_SUG_LAKOACH");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SW_IMUT_MALAM should be equal to the value in HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM",
			step,
			"HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM",
			"HASI_PRATIM_SHIDUR.HA_SW_IMUT_MALAM");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_PEULA_CASPIT should be equal to the value in HASX_PIRTEY_CHESHBON.HA_PEULA_CASPIT",
			step,
			"HASX_PIRTEY_CHESHBON.HA_PEULA_CASPIT",
			"HASI_PRATIM_SHIDUR.HA_PEULA_CASPIT");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_PEULA_MEMUKENET should be equal to the value in HASX_PIRTEY_CHESHBON.HA_PEULA_MEMUKENET",
			step,
			"HASX_PIRTEY_CHESHBON.HA_PEULA_MEMUKENET",
			"HASI_PRATIM_SHIDUR.HA_PEULA_MEMUKENET");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_PRATIM_MALE_CHELK should be equal to the value in HASX_PIRTEY_CHESHBON.HA_PRATIM_MALE_CHELK",
			step,
			"HASX_PIRTEY_CHESHBON.HA_PRATIM_MALE_CHELK",
			"HASI_PRATIM_SHIDUR.HA_PRATIM_MALE_CHELK");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SUBYECTIVY_OBYECT should be equal to the value in HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH",
			step,
			"HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH",
			"HASI_PRATIM_SHIDUR.HA_SUBYECTIVY_OBYECT");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_DIVUACH_CHOVA should be equal to the value in HASX_PIRTEY_CHESHBON.HA_DIVUACH_CHOVA",
			step,
			"HASX_PIRTEY_CHESHBON.HA_DIVUACH_CHOVA",
			"HASI_PRATIM_SHIDUR.HA_DIVUACH_CHOVA");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_KOD_MAKOR should be equal to the value in HASX_PIRTEY_CHESHBON.HA_KOD_MAKOR",
			step,
			"HASX_PIRTEY_CHESHBON.HA_KOD_MAKOR",
			"HASI_PRATIM_SHIDUR.HA_KOD_MAKOR");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_KOD_KENEGED should be equal to the value in GLSX_K86P_PARAMS.HA_KOD_KENEGED",
			step,
			"GLSX_K86P_PARAMS.HA_KOD_KENEGED",
			"HASI_PRATIM_SHIDUR.HA_KOD_KENEGED");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR_CONT.GL_SAMCHUT_MEASHERET should be equal to the value in trxORData.samchutMeasheret",
			step,
			"trxORData.samchutMeasheret",
			"GKSI_HDR_CONT.GL_SAMCHUT_MEASHERET");

		AssertBTTFramework.assertEqualsDataValues(
			"The value in GKSI_HDR_CONT.GL_ZIHUI_MEASHER should be equal to the value in trxORData.mgrUserId",
			step,
			"trxORData.mgrUserId",
			"GKSI_HDR_CONT.GL_ZIHUI_MEASHER");
	}

}
