package mataf.override;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.*;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import mataf.services.proxy.ProxyService;
import mataf.services.proxy.RequestException;
import mataf.services.reftables.RefTablesService;
import mataf.types.MatafErrorLabel;
import mataf.types.table.MatafTable;
import mataf.utils.*;
import com.ibm.dse.base.*;
import com.ibm.dse.desktop.Desktop;
import com.mataf.dse.appl.OpenDesktop;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LocalOverrideDialog extends JDialog {

     private javax.swing.JPanel jContentPane = null;
     private ProxyService m_proxy = null;
     private String m_managerId = null;
     private String m_chosenUser = null;
     private String m_samchut = null;
     private IndexedCollection m_ic = null;
     private boolean m_authResult = false;
     private mataf.types.MatafLabel matafLabel = null;
     private mataf.types.MatafLabel matafLabel2 = null;
     private mataf.types.MatafButton matafButton = null;
     private javax.swing.JComboBox jComboBox = null;
     private com.ibm.dse.gui.SpPasswordField spPasswordField = null;
     private mataf.types.MatafButton matafButton2 = null;
     private String[] saValues=null;
     
     protected JDialog jdSelf=this;
     private mataf.types.MatafLabel matafLabel3 = null;
     private javax.swing.JComboBox jComboBox1 = null;
     
     private String vCode[];
     private String vDesc[];
     
     private int iSelectedReason=-1;
     
	/**
	 * Constructor for LocalOverrideDialog.
	 * @throws HeadlessException
	 */
	public LocalOverrideDialog(ProxyService proxy, String managerId) throws HeadlessException {
		super(Desktop.getFrame());
		setModal(true);
		m_proxy = proxy;
		m_managerId =  managerId;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initialize();
	}


	public static void main(String[] args) {
		ProxyService proxy = null;
		LocalOverrideDialog idag = new LocalOverrideDialog(proxy,"111111");
		idag.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		idag.setVisible(true);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setContentPane(getJContentPane());
        this.setSize(238, 184);
        this.setTitle("אישור מנהל מקומי");
		this.setContentPane(getJContentPane());
			
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getMatafLabel(), null);
			jContentPane.add(getMatafLabel2(), null);
			jContentPane.add(getMatafButton(), null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getSpPasswordField(), null);
			jContentPane.add(getMatafButton2(), null);
			jContentPane.add(getMatafLabel3(), null);
			jContentPane.add(getJComboBox1(), null);
		}
		return jContentPane;
	}
	
	
	/**
	 * set the proxy service to use.
	 */
	void setProxy(ProxyService proxy)
	{
		m_proxy = proxy;
	}
	/**
	 * get the authentication result.
	 */
	boolean getAuthResult()
	{
		return(m_authResult);
	}
	
	/**
	 * get the managerId
	 */
	public String getManagerId()
	{
		return(m_chosenUser);
	}

	/**
	 * get the samchut
	 */
	public String getSamchut()
	{
		return(m_samchut);
	}

	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(141, 10, 80, 22);
			matafLabel.setText("מזהה מנהל");
		}
		return matafLabel;
	}
	/**
	 * This method initializes matafLabel2
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel2() {
		if(matafLabel2 == null) {
			matafLabel2 = new mataf.types.MatafLabel();
			matafLabel2.setBounds(141, 43, 80, 22);
			matafLabel2.setText("סיסמת מנהל");
		}
		return matafLabel2;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			
			matafButton.setBounds(127, 116, 79, 26);
			
			matafButton.setText("אישור");
			matafButton.addActionListener (new ActionListener() {
      			public void actionPerformed (ActionEvent e) {

					//System.out.println("-----------\n Loation "+jdSelf.getLocationOnScreen()+" \n Dimensions "+jdSelf.getSize()+"\n--------------\n");
      				//String userId = (String) getJComboBox().getSelectedItem();
      				if(getJComboBox().getSelectedIndex()==0)
      					return;
      				String userId = saValues[getJComboBox().getSelectedIndex()-1];
      				
      				
      				//String userId =(String) getMatafTableComboBox().getDataValue();
      				String password = new String(getSpPasswordField().getPassword());
			      	GLogger.debug("authenitcating userId=" + userId);
			      	m_chosenUser = userId;
			      	KeyedCollection kc = IndexedColUtils.findFirst(m_ic,"managerId",userId);
					if(null != kc) {
						try {
							m_samchut = (String) kc.getValueAt("samchutMeasheret");
						} catch(DSEObjectNotFoundException dseo) {
							m_samchut = "";
							GLogger.error(dseo);
						}
						
					}
					
					MatafErrorLabel melErrors=OpenDesktop.getActiveMatafPanel().getTheErrorLabel();
					
      				int res = m_proxy.authenticate(userId,password);
      				
      				m_authResult =false;
      				
      				switch(res)
      				{
      					case AuthorizationConstants.AUTH_OK:
      								m_authResult = true;
      								iSelectedReason=getJComboBox1().getSelectedIndex();
      								if(iSelectedReason==0)
      									iSelectedReason=-1;
      								melErrors.setText("");
			      					dispose();
      								break;
      					case AuthorizationConstants.AUTH_GERROR:
      					case AuthorizationConstants.AUTH_UNKNOWNERR:
      					case AuthorizationConstants.AUTH_OVEDAHER:
			      					melErrors.queueErrorMessage("שגיאה לא ידועה",Color.RED);
      								break;
      					default:			
      					case AuthorizationConstants.AUTH_BADPASS:
      					case AuthorizationConstants.AUTH_BADPASSATTEMPT1:
      					case AuthorizationConstants.AUTH_BADPASSATTEMPT2:
      								melErrors.queueErrorMessage("סיסמא שגויה!",Color.RED);
      								break;
      					case AuthorizationConstants.AUTH_EXPIRED:
      								melErrors.queueErrorMessage("תוקף הססמה פג!",Color.RED);
      								break;
      					case AuthorizationConstants.AUTH_LOCKED:
			      					melErrors.queueErrorMessage("המשתמש נעול!",Color.RED);
      								break;
      					case AuthorizationConstants.AUTH_TEMPEXPIRED:
      								melErrors.queueErrorMessage("משתמש זמני",Color.RED);
      								break;
      					case AuthorizationConstants.AUTH_INVALID:
      								melErrors.queueErrorMessage("משתמש לא תקף לתחנה",Color.RED);
      								break;
      					case AuthorizationConstants.AUTH_PRIMARYPASS:
      								melErrors.queueErrorMessage("סיסמא ראשונית",Color.RED);
      								break;
      					case AuthorizationConstants.AUTH_TOBEEXPIRED:
      								melErrors.queueErrorMessage("סיסמא עומדת לפוג.",Color.RED);
      								m_authResult = true;				
      								iSelectedReason=getJComboBox1().getSelectedIndex();
      								if(iSelectedReason==0)
      									iSelectedReason=-1;
      								dispose();
      								break;
      								
      				}
      				/*if(res == 0) {
					    
      				} else {
      					m_authResult = false;
      					GLogger.info("authentication for user: " + userId + " failed");
      					//matafLabel3.setText("סיסמה שגויה!");
      					OpenDesktop.getActiveMatafPanel().getTheErrorLabel().queueErrorMessage("סיסמא שגויה!",Color.RED);
      				}	*/
		      	}
    		});
	
		}
		return matafButton;
	}
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox() {
		if(jComboBox == null) {
			jComboBox = new javax.swing.JComboBox();
			jComboBox.setBounds(2, 10, 133, 22);
			jComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			try {
				m_ic = m_proxy.getManagersList();			
				if(null != m_ic) {
					/*
					 * put the user's manager first.
					 */
  				    IndexedColUtils.putFirst(m_ic,"managerId",m_managerId);
  				    GLogger.debug("ic = " + m_ic.toString());
					String vId[] = IndexedColUtils.toStringArray(m_ic,"managerId");	
					String vName[] = IndexedColUtils.toStringArray(m_ic,"managerName");
					
					jComboBox.addItem("בחר/י מנהל...");
					
					saValues=new String[vId.length];
					for(int i=0; i < vId.length; ++i) 
					{
						jComboBox.addItem(vId[i]+", " +vName[i]);
						saValues[i]=vId[i];
						
					} // for
				}  else {
					//matafLabel3.setText("אין מנהלים פעילים בסניף! נסה מאוחר יותר");
					OpenDesktop.getActiveMatafPanel().getTheErrorLabel().queueErrorMessage("אין מנהלים פעילים בסניף! נסה מאוחר יותר",Color.RED);
				}// if
			} catch(RequestException re) {
				GLogger.error("Erorr: " + re.getErrorCode() + "msg: " + re.getMessage());		
				m_ic = null;	
			}
		} // if
		return jComboBox;
	}
	/**
	 * This method initializes spPasswordField
	 * 
	 * @return com.ibm.dse.gui.SpPasswordField
	 */
	private com.ibm.dse.gui.SpPasswordField getSpPasswordField() {
		if(spPasswordField == null) {
			spPasswordField = new com.ibm.dse.gui.SpPasswordField();
			spPasswordField.setBounds(33, 43, 102, 20);
		}
		return spPasswordField;
	}
  //  @jve:visual-info  decl-index=0 visual-constraint="31,4"


	/**
	 * This method initializes matafButton2
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton2() {
		if(matafButton2 == null) {
			matafButton2 = new mataf.types.MatafButton();
			matafButton2.setBounds(13, 105, 107, 26);
			matafButton2.setSize(79, 26);
			matafButton2.setText("צא");
			matafButton2.setLocation(23, 116);
			matafButton2.addActionListener (new ActionListener() {
      			public void actionPerformed (ActionEvent e) {
      				m_authResult = false;
      				dispose();	
      			}
    		});
      		
		}
		return matafButton2;
	}
	/**
	 * This method initializes matafLabel3
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel3() {
		if(matafLabel3 == null) {
			matafLabel3 = new mataf.types.MatafLabel();
			matafLabel3.setBounds(141, 76, 80, 22);
			matafLabel3.setText("סיבה");
		}
		return matafLabel3;
	}
	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox1() {
		if(jComboBox1 == null) {
			jComboBox1 = new javax.swing.JComboBox();
			jComboBox1.setBounds(2, 76, 133, 22);
			jComboBox1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			
			try
			{
				RefTablesService refTables = (RefTablesService) OpenDesktop.getContext().getService("refTablesService");
			 	IndexedCollection ansIc = refTables.getAll("GLST_TESHUVA");
			 	
			 	vCode = IndexedColUtils.toStringArray(ansIc,"GL_KOD_TESHUVA");	
				vDesc = IndexedColUtils.toStringArray(ansIc,"GL_TEUR_TESHUVA");
				
				jComboBox1.addItem("בחר/י סיבה");
				
				for(int i=0;i<vCode.length;i++)
				{
					jComboBox1.addItem(vDesc[i]);
				}
			 	
			}
			catch(Exception e)
			{
					GLogger.error(e);
			}
		}
		return jComboBox1;
	}
	
	public String getReasonCode()
	{
		if(iSelectedReason!=-1)
			return vCode[iSelectedReason-1];
		else
			return "";
	}
	
	public String getReasonDesc()
	{
		if(iSelectedReason!=-1)	
			return vDesc[iSelectedReason-1];
		else
			return "";
	}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="31,4"


