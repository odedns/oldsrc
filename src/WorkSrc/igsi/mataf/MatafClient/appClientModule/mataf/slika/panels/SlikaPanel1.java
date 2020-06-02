package mataf.slika.panels;

import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;

import mataf.dse.gui.VisualFloatConverter;
import mataf.slika.panels.handlers.SlikaPanel1Handler;
import mataf.types.MatafButton;
import mataf.types.MatafButtonGroup;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;
import mataf.types.MatafRadioButton;
import mataf.types.MatafScrollPane;
import mataf.types.MatafTableComboBoxButton;
import mataf.types.MatafTextField;
import mataf.types.MatafTitle;
import mataf.types.specific.AccountTypeTableComboBoxButton;
import mataf.types.specific.BranchTableComboBoxButton;
import mataf.types.table.MatafTable;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.FloatConverter;
import com.ibm.dse.gui.SpButton;
import com.mataf.dse.appl.OpenDesktop;

/**
 *
 * 
 * 
 * @author Nati Dykstein. Creation Date : (10/08/2003 17:20:36).  
 */
public class SlikaPanel1 extends MatafEmbeddedPanel {

     private MatafTitle jLabel = null;
     private MatafLabel jLabel1 = null;
     private BranchTableComboBoxButton branchTableComboBox = null;
     private MatafLabel jLabel2 = null;
     private AccountTypeTableComboBoxButton accountTypeTableComboBox = null;
     private MatafLabel jLabel3 = null;

     private mataf.types.MatafTableComboBoxButton tableComboBox3 = null;
     private MatafTextField jTextField = null;
     private MatafLabel jLabel5 = null;
     private MatafTextField jTextField1 = null;
     private MatafLabel jLabel6 = null;
     private MatafTextField jTextField2 = null;
     private MatafLabel jLabel7 = null;
     private MatafLabel jLabel8 = null;
     private MatafLabel jLabel9 = null;
     private MatafRadioButton jRadioButton = null;
     private MatafRadioButton jRadioButton1 = null;
     private mataf.types.MatafLabel matafLabel = null;
     private mataf.types.MatafRadioButton matafRadioButton = null;
     private mataf.types.MatafRadioButton matafRadioButton2 = null;
     private mataf.types.MatafButton matafButton = null;
     private mataf.types.MatafButton matafButton2 = null;
     private mataf.types.MatafButton matafButton3 = null;
     private mataf.types.MatafButton matafButton4 = null;
     private mataf.types.MatafLabel matafLabel3 = null;
     private mataf.types.MatafLabel matafTextField = null;
     private mataf.types.MatafLabel matafLabel4 = null;
     private mataf.types.MatafTextField matafTextField2 = null;
     private mataf.types.MatafLabel matafLabel5 = null;
     private mataf.types.table.MatafTable matafTable = null;  //
     private MatafScrollPane jScrollPane = null;
     private mataf.types.MatafRadioButton matafRadioButton3 = null;
     private mataf.types.MatafRadioButton matafRadioButton4 = null;
     private MatafButtonGroup group1 = null;
     private MatafButtonGroup group2 = null;
     public  MatafButtonGroup group3 = null;
     private mataf.types.MatafTextField matafTextField3 = null;
     private SlikaPanel1Handler eventHandler;
     private mataf.types.MatafTextField matafTextField4 = null;
     private mataf.types.MatafTextField matafTextField5 = null;
     private mataf.types.MatafComboTextField matafTextField6 = null;
     private mataf.types.MatafComboTextField matafTextField7 = null;
     private mataf.types.MatafLabel matafLabel2 = null;
     private mataf.types.MatafLabel matafLabel6 = null;
     private mataf.types.MatafTextField matafTextField8 = null;
     private mataf.types.MatafTextField matafTextField9 = null;
	private mataf.types.MatafTableComboBoxButton matafTableComboBoxButton = null;
	private mataf.types.MatafComboTextField matafComboTextField = null;
	private mataf.types.MatafLabel matafLabel1 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public SlikaPanel1() {
		super();
		initSpButtonGroupObjects();
		initialize();
		setFocusTraversalPolicy(new SlikaPanel1FocusPolicy(this));
	}
	
	private void initSpButtonGroupObjects() {
		group1 = new MatafButtonGroup();
		group2 = new MatafButtonGroup();
		group3 = new MatafButtonGroup();
		
		group1.setDataName("ContinueAction");
		group1.add(getJRadioButton1());
		group1.add(getJRadioButton());
		//group1.addCoordinatedEventListener(this);

		group2.setDataName("DepositSource");
		group2.add(getMatafRadioButton());		
		group2.add(getMatafRadioButton2());
		//group2.addCoordinatedEventListener(this);
		
		group3.setDataName("MahutTashlum");
		group3.add(getMatafRadioButton3());
		group3.add(getMatafRadioButton4());
		//group3.addCoordinatedEventListener(this);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
       this.setLayout(null);
        this.add(getJLabel(), getJLabel().getName());
        this.add(getJLabel1(), getJLabel1().getName());
        this.add(getBranchTableComboBox(), null);
        this.add(getJLabel2(), null);
        this.add(getAccountTypeTableComboBox(), null);
        this.add(getJLabel3(), null);
        this.add(getTableComboBox3(), null);
        this.add(getJTextField(), null);
        this.add(getJLabel5(), null);
        this.add(getJTextField1(), null);
        this.add(getJLabel6(), null);
        this.add(getJTextField2(), null);
        this.add(getJLabel7(), null);
        this.add(getJLabel8(), null);
        this.add(getJLabel9(), null);
        this.add(getJRadioButton(), null);
        this.add(getJRadioButton1(), null);
        this.add(getMatafLabel(), null);
        this.add(getMatafRadioButton(), null);
        this.add(getMatafRadioButton2(), null);
        this.add(getMatafButton(), null);
        this.add(getMatafButton2(), null);
        this.add(getMatafButton3(), null);
        this.add(getMatafButton4(), null);
        this.add(getMatafLabel3(), null);
        this.add(getMatafTextField(), null);
        this.add(getMatafLabel4(), null);
        this.add(getMatafTextField2(), null);
        this.add(getMatafLabel5(), null);
        this.add(getJScrollPane(), null);
        this.add(getMatafRadioButton3(), null);
        this.add(getMatafRadioButton4(), null);
        this.add(getMatafTextField3(), null);
        this.add(getMatafTextField4(), null);
        this.add(getMatafTextField5(), null);
        this.add(getMatafTextField6(), null);
        this.add(getMatafTextField7(), null);
        this.add(getMatafLabel2(), null);
        this.add(getMatafLabel6(), null);
        this.add(getMatafTextField8(), null);
        this.add(getMatafTextField9(), null);
        this.add(getMatafTableComboBoxButton(), null);
        this.add(getMatafComboTextField(), null);
        this.add(getMatafLabel1(), null);
        this.setBounds(0, 0, 780, 450);        
	}
	
	
	
	private SlikaPanel1Handler getEventHandler() {
		if (eventHandler==null)
			eventHandler = new SlikaPanel1Handler(this);
		return eventHandler;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return MatafLabel
	 */
	public MatafTitle getJLabel() {
		if(jLabel == null) {
			jLabel = new MatafTitle();
			jLabel.setText("410 - הפקדת המחאות");			
		}
		return jLabel;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return MatafLabel
	 */
	public MatafLabel getJLabel1() {
		if(jLabel1 == null) 
		{
			jLabel1 = new MatafLabel();
			jLabel1.setBounds(661, 30, 112, 20);
			jLabel1.setText("סניף :");			
		}
		return jLabel1;
	}

	/**
	 * This method initializes branchTableComboBox
	 * 
	 * @return mataf.types.TableComboBox
	 */
	public BranchTableComboBoxButton getBranchTableComboBox() {
		if(branchTableComboBox == null)
		{
			branchTableComboBox = new BranchTableComboBoxButton();			
			branchTableComboBox.setBounds(390, 30, 60, 20);
		}
		return branchTableComboBox;
	}

	/**
	 * This method initializes jLabel2
	 * 
	 * @return MatafLabel
	 */
	public MatafLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new MatafLabel();
			jLabel2.setBounds(661, 55, 112, 20);
			jLabel2.setText("מס' וסוג חשבון :");
			
		}
		return jLabel2;
	}

	/**
	 * This method initializes accountTypeTableComboBox
	 * 
	 * @return mataf.types.TableComboBox
	 */
	public AccountTypeTableComboBoxButton getAccountTypeTableComboBox() {
		if(accountTypeTableComboBox == null) 
		{
			accountTypeTableComboBox = new AccountTypeTableComboBoxButton();
			accountTypeTableComboBox.setBounds(390, 55, 60, 20);
		}
		return accountTypeTableComboBox;
	}

	/**
	 * This method initializes jLabel3
	 * 
	 * @return MatafLabel
	 */
	public MatafLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new MatafLabel();
			jLabel3.setBounds(661, 105, 112, 20);
			jLabel3.setText("חשבון להחזרה :");			
		}
		return jLabel3;
	}
	/**
	 * This method initializes tableComboBox3
	 * 
	 * @return mataf.types.TableComboBox
	 */
	public mataf.types.MatafTableComboBoxButton getTableComboBox3() {
		if(tableComboBox3 == null) {
			
			MatafTable table = new MatafTable();
			table.setDataNameForList("AccountsToReturnList");
			table.getOurModel().addColumn(Integer.class, "מספר", "AccountsToReturnRecord.CH_CH_RTRN", Integer.MAX_VALUE, 100);
			table.getOurModel().addColumn(String.class, "סוג", "AccountsToReturnRecord.CH_TEUR_CH_RTRN", Integer.MAX_VALUE, 130);
			
			tableComboBox3 = new mataf.types.MatafTableComboBoxButton();
			tableComboBox3.setTable(table);
			tableComboBox3.setDataName("AccountsToReturn");
			tableComboBox3.setBounds(390, 106, 60, 20);
			tableComboBox3.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					try {
						System.out.println("ActionPerformed on : "+e.getSource());
						String accountData = tableComboBox3.getSelectedRowAsString();
						System.out.println("AD="+accountData);
						if(accountData==null || accountData.length()<15)
							return;
						else
							accountData = accountData.substring(0,15);
							
						StringTokenizer tokenizer = new StringTokenizer(accountData, "-");
						Context ctx = DSECoordinatedPanel.getDSECoordinatedPanel(tableComboBox3).getContext();
						
						String s1 = ((String)tokenizer.nextElement()).trim();
						ctx.setValueAt("BeneficiaryBranchId", s1);
						getMatafTextField5().setText(s1);
						
						String s2 = (String)tokenizer.nextElement();
						ctx.setValueAt("BeneficiaryAccountType", s2);
						getMatafTextField4().setText(s2);
						
						String s3 = (String)tokenizer.nextElement();
						ctx.setValueAt("BeneficiaryAccountNumber", s3);
						getJTextField().setText(s3);
					} catch(DSEObjectNotFoundException ex) {
						ex.printStackTrace();
					} catch (DSEInvalidArgumentException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return tableComboBox3;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return MatafTextField
	 */
	public MatafTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new MatafTextField();
			jTextField.setDataName("BeneficiaryAccountNumber");
			jTextField.setBounds(570, 105, 84, 20);
			jTextField.setType("Execute_Operation");
			jTextField.setName("BnfAccountNoTextField");
			jTextField.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkReturnAccountNumberClientOp",0,"","","","","","",0,0,0,0,false,false));
			jTextField.setMaxChars(6);
			jTextField.setFillInChar('0');
			//jTextField.setFormatter(new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"));
			jTextField.setDataDirection("Both");
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return MatafLabel
	 */
	public MatafLabel getJLabel5() {
		if(jLabel5 == null) {
			jLabel5 = new MatafLabel();
			jLabel5.setBounds(661, 130, 112, 20);
			jLabel5.setText("סה\"כ סכום מסוכם :");
		}
		return jLabel5;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return MatafTextField
	 */
	public MatafTextField getJTextField1() {
		if(jTextField1 == null) {
			jTextField1 = new MatafTextField();
			jTextField1.setDataName("TotalAmount");
			jTextField1.setBounds(570, 130, 84, 20);
			jTextField1.setType(MatafButton.EXECUTE_OPERATION);
			jTextField1.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkTotalAmountSlClientOp",0,"","","","","","",0,0,0,0,false,false));
//			jTextField1.setFormatter(new VisualFloatConverter("",",",'.',2,false));
			jTextField1.setMaxChars(15);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return MatafLabel
	 */
	public MatafLabel getJLabel6() {
		if(jLabel6 == null) {
			jLabel6 = new MatafLabel();
			jLabel6.setBounds(661, 155, 112, 20);
			jLabel6.setText("אסמכתא :");
		}
		return jLabel6;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return MatafTextField
	 */
	public MatafTextField getJTextField2() {
		if(jTextField2 == null) {
			jTextField2 = new MatafTextField();
			jTextField2.setDataName("Asmachta");
			jTextField2.setMaxChars(6);
			jTextField2.setFillInChar('0');
			jTextField2.setBounds(570, 155, 84, 20);
		}
		return jTextField2;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return MatafLabel
	 */
	public MatafLabel getJLabel7() {
		if(jLabel7 == null) {
			jLabel7 = new MatafLabel();
			jLabel7.setBounds(673, 230, 100, 20);
			jLabel7.setText("פעולת המשך :");
		}
		return jLabel7;
	}
	/**
	 * This method initializes jRadioButton
	 * 
	 * @return MatafRadioButton
	 */
	public MatafRadioButton getJRadioButton() {
		if(jRadioButton == null) {
			jRadioButton = new MatafRadioButton("לא", true);
			jRadioButton.setBounds(624, 230, 44, 20);
			jRadioButton.setValue("0");				
			jRadioButton.setOpaque(false);
		}
		return jRadioButton;
	}
	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return MatafRadioButton
	 */
	public MatafRadioButton getJRadioButton1() {
		if(jRadioButton1 == null) {
			jRadioButton1 = new MatafRadioButton();
			jRadioButton1.setBounds(558, 230, 42, 20);
			jRadioButton1.setText("כן");
			jRadioButton1.setValue("1");
			jRadioButton1.setOpaque(false);
		}
		return jRadioButton1;
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(673, 255, 100, 20);
			matafLabel.setText("מקור הפקדה :");
		}
		return matafLabel;
	}
	/**
	 * This method initializes matafRadioButton
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton() {
		if(matafRadioButton == null) {
			matafRadioButton = new mataf.types.MatafRadioButton("פקיד", true);
			matafRadioButton.setBounds(610, 255, 58, 20);
			matafRadioButton.setValue("0");
//			matafRadioButton.setSelected(true);
		}
		return matafRadioButton;
	}
	/**
	 * This method initializes matafRadioButton2
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton2() {
		if(matafRadioButton2 == null) {
			matafRadioButton2 = new mataf.types.MatafRadioButton();
			matafRadioButton2.setBounds(480, 255, 120, 20);
			matafRadioButton2.setValue("1");
			matafRadioButton2.setText("שרות עצמי");
		}
		return matafRadioButton2;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			matafButton.setBounds(260, 425, 130, 20);
			matafButton.setText("פירוט הלוואות");
			matafButton.setDataName("PerutHalvaotButton");
			matafButton.setActionCommand("loansDetailsBtn");
			matafButton.addActionListener(getEventHandler());
		}
		return matafButton;
	}
	/**
	 * This method initializes matafButton2
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton2() {
		if(matafButton2 == null) {
			matafButton2 = new mataf.types.MatafButton();
			matafButton2.setBounds(100, 425, 150, 20);
			matafButton2.setText("הזרמת המחאות >>");
			matafButton2.setDataName("HazramatHamchaotButton");
			matafButton2.setType(MatafButton.NEXT_VIEW);
			matafButton2.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",0,"slikaChequesView","mataf.slika.views.SlikaChequesView","","","","",0,0,0,0,false,false));
			matafButton2.setActionCommand("הזרמת המחאות >>");
		}
		return matafButton2;
	}
	/**
	 * This method initializes matafButton3
	 * 
	 * @return mataf.types.MatafButton
	 */
	public SpButton getMatafButton3() {
		if(matafButton3 == null) {
			matafButton3 = new MatafButton();
			matafButton3.setBounds(30, 425, 50, 20);
			matafButton3.setText("צא");
			matafButton3.setType(MatafButton.CLOSE_VIEW);
			matafButton3.setDataName("CloseButton1");
			matafButton3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"",0,"slikaView","mataf.slika.views.SlikaView","","","","",0,0,0,0,false,false));
		}
		return matafButton3;
	}
	
	public SpButton getMatafButton4() {
		if(matafButton4 == null) {
			matafButton4 = new MatafButton();
			matafButton4.setBounds(400, 425, 100, 20);
			matafButton4.setText("ודא");
			matafButton4.setDataName("VadeHalvaotButton");
			matafButton4.setType("Execute_Operation");
			matafButton4.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkLoanDetailsClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafButton4;
	}
	
	/**
	 * This method initializes matafLabel3
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel3() {
		if(matafLabel3 == null) {
			matafLabel3 = new mataf.types.MatafLabel();
			matafLabel3.setBounds(673, 280, 100, 20);
			matafLabel3.setText("מס' המחאות :");
		}
		return matafLabel3;
	}
	/**
	 * This method initializes matafTextField
	 * 
	 * @return mataf.dse.gui.MatafTextField
	 */
	public MatafLabel getMatafTextField() {
		if(matafTextField == null) {
			matafTextField = new mataf.types.MatafLabel();
			matafTextField.setBounds(637, 280, 31, 20);
			matafTextField.setDataName("NumberOfCheques");
		}
		return matafTextField;
	}
	/**
	 * This method initializes matafLabel4
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel4() {
		if(matafLabel4 == null) {
			matafLabel4 = new mataf.types.MatafLabel();
			matafLabel4.setBounds(673, 330, 100, 20);
			matafLabel4.setDataName("IdCardNumberTitle");
		}
		return matafLabel4;
	}
	/**
	 * This method initializes matafTextField2
	 * 
	 * @return mataf.dse.gui.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField2() {
		if(matafTextField2 == null) {
			matafTextField2 = new mataf.types.MatafTextField();
			matafTextField2.setDataName("IdCardNumber");
			matafTextField2.setBounds(587, 330, 81, 20);
			matafTextField2.setType("Execute_Operation");
			matafTextField2.setName("IdCardNumberField");
			matafTextField2.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkIdCardNumberClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTextField2.setMaxChars(9);
			//matafTextField2.setFormatter(new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"));
		}
		return matafTextField2;
	}
	/**
	 * This method initializes matafLabel5
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel5() {
		if(matafLabel5 == null) {
			
			matafLabel5 = new mataf.types.MatafLabel();
			matafLabel5.setBounds(673, 355, 100, 20);
			matafLabel5.setDataName("MahutTashlumTitle");
		}
		return matafLabel5;
	}
	/**
	 * This method initializes matafTable
	 * 
	 * @return mataf.types.table.MatafTable
	 */
	public mataf.types.table.MatafTable getMatafTable() {
		if(matafTable == null) {
			matafTable = new mataf.types.table.MatafTable();
			matafTable.setEditable(true);
			//matafTable.setColumnTypes(new Class[]{Long.class, String.class});
			matafTable.setDataNameForList("LoansList");
			matafTable.getOurModel().addColumn(Long.class, "מספר הלוואה", "LoanRecord.loanNumber");
			matafTable.getOurModel().addColumn(String.class, "סכום", "LoanRecord.loanAmount");
			
			/*matafTable.setDataNameAndColumns((((new com.ibm.dse.gui.VectorEditor(3)).setElemAt("LoansList",0)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("LoanRecord.loanNumber","מספר הלוואה",new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"),false,true,false,200),1)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("LoanRecord.loanAmount","סכום",new FloatConverter("הערך אינו נומרי", "", '.', 2),false,true,false,290),2));
			*/
			//matafTable.getTextEditorAtColumn(0).setDataDirection("Both");
			
			matafTable.setSortable(false);
			matafTable.setNavigationParametersForColumn(0,new com.ibm.dse.gui.NavigationParameters("processor",0,"checkLoanNumberClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public MatafScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new MatafScrollPane();
			jScrollPane.setViewportView(getMatafTable());
			jScrollPane.setBounds(85, 304, 419, 60);
			jScrollPane.setDataName("HalvaotTable");
		}
		return jScrollPane;
	}
	
	/**
	 * This method initializes matafRadioButton3
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton3() {
		if(matafRadioButton3 == null) {
			matafRadioButton3 = new mataf.types.MatafRadioButton("הוצאות ראשוניות", true);
			matafRadioButton3.setBounds(527, 380, 141, 20);
			matafRadioButton3.setType("Execute_Operation");
			matafRadioButton3.setValue("1");
			matafRadioButton3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkMahutTashlumClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafRadioButton3.setActionCommand("hotsaotRishoniotBtn");
			matafRadioButton3.addActionListener(getEventHandler());
		}
		return matafRadioButton3;
	}
	
	/**
	 * This method initializes matafRadioButton4
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton4() {
		if(matafRadioButton4 == null) {
			matafRadioButton4 = new mataf.types.MatafRadioButton("תקבולים ע\"ח הלוואה");
			matafRadioButton4.setBounds(525, 355, 143, 20);
			matafRadioButton4.setType("Execute_Operation");
			matafRadioButton4.setValue("2");
			matafRadioButton4.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkMahutTashlumClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafRadioButton4.setActionCommand("takbulimBtn");
			matafRadioButton4.addActionListener(getEventHandler());
		}
		return matafRadioButton4;
	}
	/**
	 * This method initializes matafTextField3
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField3() {
		if(matafTextField3 == null) {
			matafTextField3 = new mataf.types.MatafTextField();
			matafTextField3.setDataName("AccountNumber");
			matafTextField3.setBounds(599, 55, 55, 20);
			matafTextField3.setType(MatafButton.EXECUTE_OPERATION);
			matafTextField3.setName("matafTextField3");
			matafTextField3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkAccountSlClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTextField3.setMaxChars(6);
			matafTextField3.setFillInChar('0');
		//	matafTextField3.setFormatter(new com.ibm.dse.gui.NumericConverter("ערך אינו נומרי"));
//			matafTextField3.setMinChars(new com.ibm.dse.gui.TextFieldMinLengthProperty(6,"שדה צריך להכיל 6 ספרות"));

		}
		return matafTextField3;
	}
	/**
	 * This method initializes matafTextField4
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField4() {
		if(matafTextField4 == null) {
			matafTextField4 = new mataf.types.MatafTextField();
			matafTextField4.setDataName("BeneficiaryAccountType");
			matafTextField4.setBounds(525, 105, 45, 20);
			matafTextField4.setType("Execute_Operation");
			matafTextField4.setName("BnfAccountTypeTextField");
			matafTextField4.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkAccountTypeClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTextField4.setMaxChars(3);
			matafTextField4.setFillInChar('0');
			//matafTextField4.setMinChars(new com.ibm.dse.gui.TextFieldMinLengthProperty(3,"שדה צריך להכיל 3 ספרות"));
	//		matafTextField4.setFormatter(new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"));
			matafTextField4.setDataDirection("Both");
		}
		return matafTextField4;
	}
	/**
	 * This method initializes matafTextField5
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField5() {
		if(matafTextField5 == null) {
			matafTextField5 = new mataf.types.MatafTextField();
			matafTextField5.setDataName("BeneficiaryBranchId");
			matafTextField5.setBounds(480, 105, 45, 20);
			matafTextField5.setType("Execute_Operation");
			matafTextField5.setName("BnfBranchIdTextField");
			matafTextField5.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkBranchIdClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTextField5.setMaxChars(3);
			matafTextField5.setFillInChar('0');
			//matafTextField5.setMinChars(new com.ibm.dse.gui.TextFieldMinLengthProperty(3,"שדה צריך להכיל 3 ספרות"));
	//		matafTextField5.setFormatter(new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"));
			matafTextField5.setDataDirection("Both");
		}
		return matafTextField5;
	}	
	
	/**
	 * This method initializes matafTextField6
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafComboTextField getMatafTextField6() {
		if(matafTextField6 == null) {
			matafTextField6 = new mataf.types.MatafComboTextField();
			matafTextField6.setBounds(619, 30, 35, 21);
			matafTextField6.setDataName("BranchIdInput");
			matafTextField6.setMaxChars(3);
			matafTextField6.setFillInChar('0');
			matafTextField6.setTableComboBox(getBranchTableComboBox());
			matafTextField6.setDescriptionLabel(getMatafLabel2());
		}
		return matafTextField6;
	}
	/**
	 * This method initializes matafTextField7
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafComboTextField getMatafTextField7() {
		if(matafTextField7 == null) {
			matafTextField7 = new mataf.types.MatafComboTextField();
			matafTextField7.setBounds(564, 55, 35, 20);
			matafTextField7.setMaxChars(3);
			matafTextField7.setFillInChar('0');
			matafTextField7.setDescriptionLabel(getMatafLabel6());
			matafTextField7.setTableComboBox(getAccountTypeTableComboBox());
			matafTextField7.setDataName("AccountType");
		}
		return matafTextField7;
	}
	/**
	 * This method initializes matafLabel2
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel2() {
		if(matafLabel2 == null) {
			matafLabel2 = new mataf.types.MatafLabel();
			matafLabel2.setBounds(450, 30, 150, 21);
			matafLabel2.setText("משוב סניף");
			matafLabel2.setDataName("BranchDescIdInput");
		}
		return matafLabel2;
	}
	/**
	 * This method initializes matafLabel6
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel6() {
		if(matafLabel6 == null) {
			matafLabel6 = new mataf.types.MatafLabel();
			matafLabel6.setBounds(450, 55, 105, 20);
			matafLabel6.setText("משוב סוג חשבון");
			matafLabel6.setDataName("AccountTypeFeedback");
		}
		return matafLabel6;
	}
	/**
	 * This method initializes matafLabel8
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getJLabel8() {
		if(jLabel8 == null) {
			jLabel8 = new mataf.types.MatafLabel();
			jLabel8.setBounds(400, 380, 104, 21);
			jLabel8.setDataName("HotsaotRishoniotLoanNumberTitle");
		}
		return jLabel8;
	}
	/**
	 * This method initializes matafTextField8
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getMatafTextField8() {
		if(matafTextField8 == null) {
			matafTextField8 = new mataf.types.MatafTextField();
			matafTextField8.setBounds(285, 380, 104, 21);
			matafTextField8.setType("Execute_Operation");
			matafTextField8.setDataName("HotsaotRishoniotLoanNumber");
			matafTextField8.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkLoanNumberClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTextField8.setMaxChars(13);
	//		matafTextField8.setFormatter(new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"));
		}
		return matafTextField8;
	}
	/**
	 * This method initializes matafLabel9
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getJLabel9() {
		if(jLabel9 == null) {
			jLabel9 = new mataf.types.MatafLabel();
			jLabel9.setBounds(200, 380, 60, 21);
			jLabel9.setDataName("HotsaotRishoniotLoanAmmountTitle");
		}
		return jLabel9;
	}
	/**
	 * This method initializes matafTextField9
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getMatafTextField9() {
		if(matafTextField9 == null) {
			matafTextField9 = new mataf.types.MatafTextField();
			matafTextField9.setBounds(85, 380, 104, 21);
			matafTextField9.setType("Execute_Operation");
			matafTextField9.setDataName("HotsaotRishoniotLoanAmmount");
			matafTextField9.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"loanDataChangedClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafTextField9;
	}
	/**
	 * This method initializes matafTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getMatafTableComboBoxButton() {
		if(matafTableComboBoxButton == null) 
		{
			MatafTable table = new MatafTable();
			
			table.setDynamic(true);
			table.setDataNameForList("SemelDivuachList");
			table.getOurModel().addColumn(Integer.class,"קוד דיווח","SemelDivuachRecord.TL_ASMACHTA_001", Integer.MAX_VALUE, 80);
			table.getOurModel().addColumn(String.class,"תיאור","SemelDivuachRecord.TL_TEUR_ASM_142", Integer.MAX_VALUE, 400);
			
			
			matafTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			matafTableComboBoxButton.setBounds(390, 155, 60, 20);
			matafTableComboBoxButton.setTable(table);
			matafTableComboBoxButton.setVisible(false);
		}
		return matafTableComboBoxButton;
	}
	/**
	 * This method initializes matafComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getMatafComboTextField() {
		if(matafComboTextField == null) {
			matafComboTextField = new mataf.types.MatafComboTextField();
			matafComboTextField.setBounds(458, 155, 35, 20);
			matafComboTextField.setDataName("TL_DIVUACH_HOST");
			matafComboTextField.setMaxChars(4);
			matafComboTextField.setFillInChar('0');
			matafComboTextField.setTableComboBox(getMatafTableComboBoxButton());
			
		}
		return matafComboTextField;
	}
	/**
	 * This method initializes matafLabel1
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel1() {
		if(matafLabel1 == null) {
			matafLabel1 = new mataf.types.MatafLabel();
			matafLabel1.setBounds(495, 155, 67, 20);
			matafLabel1.setText("סמל דיווח :");
			matafLabel1.setVisible(false);
		}
		return matafLabel1;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="-2,6"
