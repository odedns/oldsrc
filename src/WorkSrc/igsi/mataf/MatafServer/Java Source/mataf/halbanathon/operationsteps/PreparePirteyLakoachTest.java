package mataf.halbanathon.operationsteps;

import java.util.Enumeration;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.DataElement;
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
public class PreparePirteyLakoachTest extends ServerOperationTestCase {
	private PreparePirteyLakoach step;
	private String MIVNE_EZER = "HASX_PIRTEY_CHESHBON";
	private String MIVNE_SHIDUR = "HASI_PRATIM_SHIDUR";
	private String actualValue;
	private String originValue;

	/**
	 * Constructor for HalbanaHotTransmitSugLakoachOpStepTest.
	 * @param arg0
	 */
	public PreparePirteyLakoachTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		step = new PreparePirteyLakoach();

		DSEServerOperation serverop = (DSEServerOperation) DSEServerOperation.readObject("halbanatHonTransmitSugLakoachServerOp");
		step.setOperation(serverop);

//		initSourceCollections();
//		step.setValueAt(MIVNE_EZER + ".HA_MIZDAMEN_CHADASH", "1");
//		step.setValueAt("HelpData.SW_SCREEN", "2");
//		step.setValueAt("HASS_LAK_PRATIM.HA_PHONE_AREA1", "09");
//		step.setValueAt("HASS_LAKOACH_SUG.HA_SCH_ACHER", "");
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void initSourceCollections() throws Exception {
		KeyedCollection sourceKcoll = (KeyedCollection) step.getElementAt("GLSX_K86P_PARAMS");
		Enumeration elements = sourceKcoll.getEnumeration();
		int i = 0;
		while (elements.hasMoreElements()) {
			((DataElement) elements.nextElement()).setValue(String.valueOf(i++));

		}

		sourceKcoll = (KeyedCollection) step.getElementAt("HASS_LAK_PRATIM");
		elements = sourceKcoll.getEnumeration();
		i = 100;
		while (elements.hasMoreElements()) {
			((DataElement) elements.nextElement()).setValue(String.valueOf(i++));
		}

		sourceKcoll = (KeyedCollection) step.getElementAt("HASS_LAKOACH_SUG");
		elements = sourceKcoll.getEnumeration();
		i = 200;
		while (elements.hasMoreElements()) {
			((DataElement) elements.nextElement()).setValue(String.valueOf(i++));
		}

		sourceKcoll = (KeyedCollection) step.getElementAt("HASX_PIRTEY_CHESHBON");
		elements = sourceKcoll.getEnumeration();
		i = 300;
		while (elements.hasMoreElements()) {
			((DataElement) elements.nextElement()).setValue(String.valueOf(i++));
		}
	}

	//		(3)	Check HA_MIZDAMEN_CHADASH
	public void testAssign_HA_MIZDAMEN_CHADASH() throws Exception {
		step.assign_HA_MIZDAMEN_CHADASH();

		String HA_MIZDAMEN_CHADASH = (String) step.getValueAt(MIVNE_EZER + ".HA_MIZDAMEN_CHADASH");
		if (HA_MIZDAMEN_CHADASH.equals("1")) {
			AssertBTTFramework.assertEqualsConstantValue(
				"The value in HASI_PRATIM_SHIDUR.HA_MIZDAMEN_CHADASH should be 1",
				step,
				"1",
				"HASI_PRATIM_SHIDUR.HA_MIZDAMEN_CHADASH");
		}
	}

	//		(4)	Check HA_SW_IMUT_MALAM
	public void testAssign_HA_SW_IMUT_MALAM() throws Exception {
		step.assign_HA_SW_IMUT_MALAM();
		AssertBTTFramework.assertEqualsDataValues(
			"The value in HASI_PRATIM_SHIDUR.HA_SW_IMUT_MALAM should be equal to the value in HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM",
			step,
			MIVNE_EZER + ".HA_SW_IMUT_MALAM",
			"HASI_PRATIM_SHIDUR.HA_SW_IMUT_MALAM");
	}

	//		(5)	Check SHEM_SHEDER (GL_TRANS_ID) & SHEM_TSHUVA
	public void testAssign_GL_TRANS_ID() throws Exception {
		step.assign_GL_TRANS_ID();

		AssertBTTFramework.assertEqualsConstantValue(
			"The value in \"GKSI_HDR.GL_TRANS_ID\" should be O365",
			step,
			"O365",
			"GKSI_HDR.GL_TRANS_ID");

		AssertBTTFramework.assertEqualsConstantValue(
			"The value in \"GKSR_HDR.GL_TRANS_ID\" should be HS",
			step,
			"HS",
			"HASR_PRATIM_SHIDUR.HostHeaderReplyData.GKSR_HDR.GL_TRANS_ID");
	}

	//		(6)	Checking SW_SCREEN (SW_DO in Avi's doc) field and its following assignment
	public void testAssignAccordingTo_SW_SCREEN() throws Exception {
		step.assignAccordingTo_SW_SCREEN();

		String SW_SCREEN = (String) step.getValueAt("HelpData.SW_SCREEN");
		if (SW_SCREEN.equals("2")) {
			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_HOME_STREET should be equal to the value in HASS_LAK_PRATIM.HA_HOME_STREET",
				step,
				"HASS_LAK_PRATIM.HA_HOME_STREET",
				"HASI_PRATIM_SHIDUR.HA_HOME_STREET");

			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_HOME_ST_NUM should be equal to the value in HASS_LAK_PRATIM.HA_HOME_ST_NUM",
				step,
				"HASS_LAK_PRATIM.HA_HOME_ST_NUM",
				"HASI_PRATIM_SHIDUR.HA_HOME_ST_NUM");

			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_HOME_ST_SUB_NUM should be equal to the value in HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM",
				step,
				"HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM",
				"HASI_PRATIM_SHIDUR.HA_HOME_ST_SUB_NUM");
			
			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_HOME_ST_SUB_NUM should be equal to the value in HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM",
				step,
				"HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM",
				"HASI_PRATIM_SHIDUR.HA_HOME_ST_SUB_NUM");
			
			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_HOME_APT should be equal to the value in HASS_LAK_PRATIM.HA_HOME_APT",
				step,
				"HASS_LAK_PRATIM.HA_HOME_APT",
				"HASI_PRATIM_SHIDUR.HA_HOME_APT");
			
			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_HOME_CITY should be equal to the value in HASS_LAK_PRATIM.HA_HOME_CITY",
				step,
				"HASS_LAK_PRATIM.HA_HOME_CITY",
				"HASI_PRATIM_SHIDUR.HA_HOME_CITY");

			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_HOME_ZIP should be equal to the value in HASS_LAK_PRATIM.HA_HOME_ZIP",
				step,
				"HASS_LAK_PRATIM.HA_HOME_ZIP",
				"HASI_PRATIM_SHIDUR.HA_HOME_ZIP");

			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_PHONE_AREA2 should be equal to the value in HASS_LAK_PRATIM.HA_PHONE_AREA2",
				step,
				"HASS_LAK_PRATIM.HA_PHONE_AREA2",
				"HASI_PRATIM_SHIDUR.HA_PHONE_AREA2");
			
			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_PHONE_NUM2 should be equal to the value in HASS_LAK_PRATIM.HA_PHONE_NUM2",
				step,
				"HASS_LAK_PRATIM.HA_PHONE_NUM2",
				"HASI_PRATIM_SHIDUR.HA_PHONE_NUM2");

			AssertBTTFramework.assertEqualsDataValues(
				"The value in HASI_PRATIM_SHIDUR.HA_SEX should be equal to the value in HASS_LAK_PRATIM.HA_SEX",
				step,
				"HASS_LAK_PRATIM.HA_SEX",
				"HASI_PRATIM_SHIDUR.HA_SEX");

			
			
			originValue = (String) step.getValueAt("HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD");
			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_KOD_MAAMAD");
			Assert.assertEquals(
				"The value in HASI_PRATIM_SHIDUR.HA_KOD_MAAMAD should be equal to the value in HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD",
				originValue,
				actualValue);

			originValue = (String) step.getValueAt("HASS_LAK_PRATIM.HASG_TR_LEDA_MLM");
			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HASG_TR_LEDA");
			Assert.assertEquals(
				"The value in HASI_PRATIM_SHIDUR.HASG_TR_LEDA should be equal to the value in HASS_LAK_PRATIM.HASG_TR_LEDA_MLM",
				originValue,
				actualValue);

			originValue = (String) step.getValueAt("HASS_LAK_PRATIM.HASG_TR_HANPAKA");
			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HASG_TR_HANPAKA");
			Assert.assertEquals(
				"The value in HASI_PRATIM_SHIDUR.HASG_TR_HANPAKA should be equal to the value in HASS_LAK_PRATIM.HASG_TR_HANPAKA",
				originValue,
				actualValue);

		}

		originValue = (String) step.getValueAt("HASS_LAK_PRATIM.HA_COUNTRY_CODE");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_COUNTRY_CODE");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_COUNTRY_CODE should be equal to the value in HASS_LAK_PRATIM.HA_COUNTRY_CODE",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASS_LAK_PRATIM.HA_TZ");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_TZ");
		Assert.assertEquals("The value in HASI_PRATIM_SHIDUR.HA_TZ should be equal to the value in HASS_LAK_PRATIM.HA_TZ", originValue, actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_RESULT.HA_NAME_LAST");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_NAME_LAST");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_NAME_LAST should be equal to the value in HASS_LAK_PRATIM.HA_NAME_LAST",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_RESULT.HA_NAME_FIRST");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_NAME_FIRST");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_NAME_FIRST should be equal to the value in HASS_LAK_PRATIM.HA_NAME_FIRST",
			originValue,
			actualValue);
		originValue = (String) step.getValueAt("GLSX_K86P_RESULT.HA_PHONE_AREA1");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_PHONE_AREA1");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_PHONE_AREA1 should be equal to the value in HASS_LAK_PRATIM.HA_PHONE_AREA1",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_RESULT.HA_PHONE_NUM1");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_PHONE_NUM1");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_PHONE_NUM1 should be equal to the value in HASS_LAK_PRATIM.HA_PHONE_NUM1",
			originValue,
			actualValue);

	}

	public void testHandleDataElements() throws Exception {
		step.handleDataElements();

		//		(7)	Check HAAI_INQ_SHIDUR assignment
		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_BANK");
		actualValue = (String) step.getValueAt("GLSG_GLBL.GL_BANK");
		Assert.assertEquals(
			"The value in GKSI_HDR.GL_BANK should be equal to the value in GLSX_K86P_PARAMS.HA_BANK",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SNIF");
		actualValue = (String) step.getValueAt("GKSI_HDR.GL_SNIF");
		Assert.assertEquals(
			"The value in GKSI_HDR.GL_SNIF should be equal to the value in GLSX_K86P_PARAMS.HA_SNIF",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		actualValue = (String) step.getValueAt("GKSI_HDR.GL_SCH");
		Assert.assertEquals(
			"The value in GKSI_HDR.GL_SCH should be equal to the value in GLSX_K86P_PARAMS.HA_SCH",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_CH");
		actualValue = (String) step.getValueAt("GKSI_HDR.GL_CH");
		Assert.assertEquals(
			"The value in GKSI_HDR.GL_CH should be equal to the value in GLSX_K86P_PARAMS.HA_CH",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_MATBEA");
		actualValue = (String) step.getValueAt("GKSI_HDR.GL_MATBEA");
		Assert.assertEquals(
			"The value in GKSI_HDR.GL_MATBEA should be equal to the value in GLSX_K86P_PARAMS.HA_MATBEA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SEMEL_PEULA");
		actualValue = (String) step.getValueAt("GKSI_HDR.GL_PEULA");
		Assert.assertEquals(
			"The value in GKSI_HDR.GL_PEULA should be equal to the value in GLSX_K86P_PARAMS.HA_SEMEL_PEULA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SIDURI_TAHANA");
		actualValue = (String) step.getValueAt("GKSI_HDR_CONT.GL_SIDURI_TAHANA");
		Assert.assertEquals(
			"The value in GKSI_HDR_CONT.GL_SIDURI_TAHANA should be equal to the value in GLSX_K86P_PARAMS.HA_SIDURI_TAHANA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_MISPAR_TAHANA");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_MISPAR_TAHANA");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_MISPAR_TAHANA should be equal to the value in GLSX_K86P_PARAMS.HA_MISPAR_TAHANA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SIDURI_TAHANA");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SIDURI_TAHANA");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SIDURI_TAHANA should be equal to the value in GLSX_K86P_PARAMS.HA_SIDURI_TAHANA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SIDURI_TAHANA");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SIDURI_TAHANA");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SIDURI_TAHANA should be equal to the value in GLSX_K86P_PARAMS.HA_SIDURI_TAHANA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SUG_PEULA");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SUG_PEULA");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SUG_PEULA should be equal to the value in GLSX_K86P_PARAMS.HA_SUG_PEULA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_KOD_NOSE");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_PEULA_KOD_NOSE");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_PEULA_KOD_NOSE should be equal to the value in GLSX_K86P_PARAMS.HA_KOD_NOSE",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_BANK");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_BANK");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_BANK should be equal to the value in GLSX_K86P_PARAMS.HA_BANK",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SNIF");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SNIF");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SNIF should be equal to the value in GLSX_K86P_PARAMS.HA_SNIF",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SCH");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SCH should be equal to the value in GLSX_K86P_PARAMS.HA_SCH",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_CH");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_CH");
		Assert.assertEquals("The value in HASI_PRATIM_SHIDUR.HA_CH should be equal to the value in GLSX_K86P_PARAMS.HA_CH", originValue, actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_MATBEA");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_MATBEA");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_MATBEA should be equal to the value in GLSX_K86P_PARAMS.HA_MATBEA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SCHUM");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SCHUM");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SCHUM should be equal to the value in GLSX_K86P_PARAMS.HA_SCHUM",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_SCHUM_NIS");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SCHUM_NIS");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SCHUM_NIS should be equal to the value in GLSX_K86P_PARAMS.HA_SCHUM_NIS",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_MISPAR_KG");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_MISPAR_KG");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_MISPAR_KG should be equal to the value in GLSX_K86P_PARAMS.HA_MISPAR_KG",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_AMIT_KIDOMET");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_AMIT_KIDOMET");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_AMIT_KIDOMET should be equal to the value in GLSX_K86P_PARAMS.HA_AMIT_KIDOMET",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_AMIT_SIYOMET");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_AMIT_SIYOMET");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_AMIT_SIYOMET should be equal to the value in GLSX_K86P_PARAMS.HA_AMIT_SIYOMET",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_TEUR_PEULA");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_TEUR_PEULA");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_AMIT_SIYOMET should be equal to the value in GLSX_K86P_PARAMS.HA_TEUR_PEULA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SUG_LAKOACH");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_AMIT_SIYOMET should be equal to the value in HASS_LAKOACH_SUG.HA_SUG_LAKOACH",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SW_IMUT_MALAM");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SW_IMUT_MALAM should be equal to the value in HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_CASPIT");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_PEULA_CASPIT");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_PEULA_CASPIT should be equal to the value in HASX_PIRTEY_CHESHBON.HA_PEULA_CASPIT",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_MEMUKENET");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_PEULA_MEMUKENET");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_PEULA_MEMUKENET should be equal to the value in HASX_PIRTEY_CHESHBON.HA_PEULA_CASPIT",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASX_PIRTEY_CHESHBON.HA_PRATIM_MALE_CHELK");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_PRATIM_MALE_CHELK");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_PRATIM_MALE_CHELK should be equal to the value in HASX_PIRTEY_CHESHBON.HA_PRATIM_MALE_CHELK",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SUBYECTIVY_OBYECT");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_SUBYECTIVY_OBYECT should be equal to the value in HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASX_PIRTEY_CHESHBON.HA_DIVUACH_CHOVA");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_DIVUACH_CHOVA");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_DIVUACH_CHOVA should be equal to the value in HASX_PIRTEY_CHESHBON.HA_DIVUACH_CHOVA",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("HASX_PIRTEY_CHESHBON.HA_KOD_MAKOR");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_KOD_MAKOR");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_KOD_MAKOR should be equal to the value in HASX_PIRTEY_CHESHBON.HA_KOD_MAKOR",
			originValue,
			actualValue);

		originValue = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_KOD_KENEGED");
		actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_KOD_KENEGED");
		Assert.assertEquals(
			"The value in HASI_PRATIM_SHIDUR.HA_KOD_KENEGED should be equal to the value in GLSX_K86P_PARAMS.HA_KOD_KENEGED",
			originValue,
			actualValue);

		//		(8)
		String sugCheshbonLakoach = (String) step.getValueAt("HASS_LAKOACH_SUG.HA_SCH_ACHER");
		String cheshbonNegdi = (String) step.getValueAt("HASS_LAKOACH_SUG.HA_CH_ACHER");
		if ((sugCheshbonLakoach != null) && (!sugCheshbonLakoach.equals(""))) {
			originValue = (String) step.getValueAt("HASS_LAKOACH_SUG.HA_SNIF_ACHER");
			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SNIF");
			Assert.assertEquals(
				"The value in HASI_PRATIM_SHIDUR.HA_SNIF should be equal to this in HASS_LAKOACH_SUG.HA_SNIF_ACHER",
				originValue,
				actualValue);

			originValue = (String) step.getValueAt("HASS_LAKOACH_SUG.HA_SCH_ACHER");
			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SCH");
			Assert.assertEquals(
				"The value in HASI_PRATIM_SHIDUR.HA_SCH should be equal to this in HASS_LAKOACH_SUG.HA_SCH_ACHER",
				originValue,
				actualValue);

			originValue = (String) step.getValueAt("HASS_LAKOACH_SUG.HA_CH_ACHER");
			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_CH");
			Assert.assertEquals(
				"The value in HASI_PRATIM_SHIDUR.HA_CH should be equal to this in HASS_LAKOACH_SUG.HA_CH_ACHER",
				originValue,
				actualValue);

			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_MATBEA");
			Assert.assertEquals("The value in HASI_PRATIM_SHIDUR.HA_MATBEA should be equal to 0", "0", actualValue);
		} else if ((cheshbonNegdi != null) && (!cheshbonNegdi.equals(""))) {
			originValue = cheshbonNegdi;
			actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_CH");
			Assert.assertEquals(
				"The value in HASI_PRATIM_SHIDUR.HA_CH should be equal to this in HASS_LAKOACH_SUG.HA_CH_ACHER",
				originValue,
				actualValue);

			String noseAnglit = (String) step.getValueAt("GLSX_K86P_PARAMS.GL_NOSE_PEULA");
			//		Additional checks should be written for "Teller" and "Machatz"
			if (noseAnglit.equalsIgnoreCase("M")) {
			} else if (noseAnglit.equalsIgnoreCase("T")) {

			} else if (noseAnglit.equalsIgnoreCase("W")) {

				//		(9)
			} else {
				String bankMaarechet = (String) step.getValueAt("GLSG_GLBL.GL_BANK");
				String bankMasach = (String) step.getValueAt("HASS_LAKOACH_SUG.HA_BANK");
				if ((bankMaarechet != null) && (!bankMaarechet.equals(bankMasach))) {
					actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".GKSI_HDR.GL_SNIF");
					Assert.assertEquals("The value in HASI_PRATIM_SHIDUR.GKSI_HDR.GL_SNIF should be equal to 0", "0", actualValue);
				}

				actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_DRISAT_DIVUACH");
				Assert.assertEquals("The value in HASI_PRATIM_SHIDUR.HA_SNIF should be equal to 0", "0", actualValue);

				String peulaMemukenet = (String) step.getValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_MEMUKENET");
				if ((peulaMemukenet.equals("2")) || (peulaMemukenet.equals("3"))) {
					originValue = (String) step.getValueAt("GLSF_GLBL.GL_MISPAR_TAHANA");
					actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_MISPAR_TAHANA");
					Assert.assertEquals(
						"The value in HASI_PRATIM_SHIDUR.HA_MISPAR_TAHANA should be equal to the value in GLSF_GLBL.GL_MISPAR_TAHANA",
						originValue,
						actualValue);

					originValue = (String) step.getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_TL");
					actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_SIDURI_TAHANA");
					Assert.assertEquals(
						"The value in HASI_PRATIM_SHIDUR.HA_SIDURI_TAHANA should be equal to the value in GLSF_GLBL.GL_SIDURI_ID",
						originValue,
						actualValue);
				}

				if (peulaMemukenet.equals("3")) {
					actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".HA_PEULA_MEMUKENET");
					Assert.assertEquals("The value in HASI_PRATIM_SHIDUR.HA_PEULA_MEMUKENET should be equal to 2", originValue, actualValue);
				}

				String kodNoseIska = (String) step.getValueAt("GLSX_K86P_PARAMS.HA_KOD_NOSE_ISKA");
				if (kodNoseIska.equals("1")) {
					originValue = (String) step.getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_TL");
					actualValue = (String) step.getValueAt(MIVNE_SHIDUR + ".GKSI_HDR_CONT.GL_SIDURI_TAHANA");
					Assert.assertEquals(
						"The value in HASI_PRATIM_SHIDUR.GKSI_HDR_CONT.GL_SIDURI_TAHANA should be equal to the value in GLSF_GLBL.GL_SIDURI_TAHANA_TL",
						originValue,
						actualValue);
				}

			}
		}
	}
}
