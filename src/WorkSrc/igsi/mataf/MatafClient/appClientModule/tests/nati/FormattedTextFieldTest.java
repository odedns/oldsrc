package tests.nati;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.types.MatafComboTextField;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafScrollPane;
import mataf.types.MatafTextField;
import mataf.types.specific.BanksTableComboBoxButton;
import mataf.types.specific.BranchTableComboBoxButton;
import mataf.types.table.MatafTable;
import mataf.types.textfields.MatafDateField;
import mataf.types.textfields.MatafDecimalField;
import mataf.types.textfields.MatafNumericField;
import mataf.types.textfields.MatafStringField;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.Settings;
import com.ibm.dse.clientserver.CSClientService;

/**
 * FormattedTextFieldDemo.java is a 1.4 example that implements a panel
 * containing mortgage-calculating fields.  For standalone use, the main
 * method creates a JFrame and puts the panel inside it.
 */
public class FormattedTextFieldTest extends MatafTransactionView
{
	//Labels to identify the text fields
	private JLabel hourLabel;
	private JLabel amountLabel;
	private JLabel numPeriodsLabel;
	private JLabel paymentLabel;

	//Strings for the labels
	private static String hourString = "Date Field: ";
	private static String amountString = "Decimal Field : ";
	private static String numPeriodsString = "Numeric Field : ";
	private static String paymentString = "String Field : ";

	//Text fields for data entry
	private MatafDateField hourField;
	private MatafDecimalField 	amountField;
	private MatafNumericField 	branchField;
	private MatafStringField 	paymentField;

	//Formats to format and parse numbers
	private NumberFormat moneyFormat;
	private NumberFormat percentFormat;
	private DecimalFormat paymentFormat;
	
	private MatafTable matafTable;

	public FormattedTextFieldTest()
	{
		setLayout(new BorderLayout());

		setUpFormats();

		//Create the labels.
		hourLabel = new JLabel(hourString);
		amountLabel = new JLabel(amountString);
		numPeriodsLabel = new JLabel(numPeriodsString);
		paymentLabel = new JLabel(paymentString);

		hourField = new MatafDateField();
		
		hourField.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				System.out.println(
					"Hour is = " + ((MatafTextField)e.getSource()).getText());
			}
		});
		
		hourField.setDateFormatString("dd/MM/yyyy");

		amountField = new MatafDecimalField();
		amountField.setMaxChars(6);
		amountField.setRequired(true);

		branchField = new MatafNumericField();
		branchField.setMaxChars(10);
		branchField.setRequired(true);	
		
		paymentField = new MatafStringField();
		paymentField.setColumns(10);
		paymentField.setMaxChars(10);

		//Tell accessibility tools about label/textfield pairs.
		hourLabel.setLabelFor(hourField);
		amountLabel.setLabelFor(amountField);
		numPeriodsLabel.setLabelFor(branchField);
		paymentLabel.setLabelFor(paymentField);

		//Lay out the labels in a panel.
		JPanel labelPane = new JPanel(new GridLayout(0, 1));
		labelPane.add(hourLabel);
		labelPane.add(amountLabel);
		labelPane.add(numPeriodsLabel);
		labelPane.add(paymentLabel);

		//Layout the text fields in a panel.
		JPanel fieldPane = new JPanel(new GridLayout(0, 1));
		fieldPane.add(hourField);
		fieldPane.add(amountField);
		fieldPane.add(branchField);
		fieldPane.add(paymentField);

		//Put the panels in this panel, labels on left,
		//text fields on right.
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(labelPane, BorderLayout.CENTER);
		add(fieldPane, BorderLayout.LINE_END);
		add(new MatafScrollPane(getMatafTable()),BorderLayout.PAGE_END);
	}

	
	public class FormattedTextFieldVerifier extends InputVerifier
	{
		public boolean verify(JComponent input)
		{
			if (input instanceof JFormattedTextField)
			{
				JFormattedTextField ftf = (JFormattedTextField)input;
				JFormattedTextField.AbstractFormatter formatter =
					ftf.getFormatter();
				if (formatter != null)
				{
					String text = ftf.getText();
					try
					{
						formatter.stringToValue(text);
						return true;
					}
					catch (ParseException pe)
					{
						return false;
					}
				}
			}
			return true;
		}
		public boolean shouldYieldFocus(JComponent input)
		{
			return verify(input);
		}
	}

	//Create and set up number formats. These objects also
	//parse numbers input by user.
	private void setUpFormats()
	{
		moneyFormat = NumberFormat.getNumberInstance();
		// moneyFormat.setMaximumIntegerDigits(4);

		percentFormat = NumberFormat.getNumberInstance();

		percentFormat.setMinimumFractionDigits(3);

		paymentFormat = (DecimalFormat)NumberFormat.getNumberInstance();
		paymentFormat.setMaximumFractionDigits(2);
		paymentFormat.setNegativePrefix("(");
		paymentFormat.setNegativeSuffix(")");
	}

	/**
	 * This method initializes matafTable
	 * 
	 * @return mataf.types.table.MatafTable
	 */
	public MatafTable getMatafTable()
	{
		if (matafTable == null)
		{
			matafTable = new MatafTable();
			matafTable.setDataNameForList("CZSS_T110_LIST");
			
			matafTable.setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);
			matafTable.setEditable(true);

			
			matafTable.getOurModel().addColumn(
				Long.class,
				"מספר המחאה",
				"CZSS_T110_LINE.CH_MISPAR_CHEQ",
				true,
				10);
			matafTable.getOurModel().addColumn(
				Integer.class,
				"בנק",
				"CZSS_T110_LINE.CH_BANK_CHOTEM",
				true,
				2,
				true);
			matafTable.getOurModel().addColumn(
				Integer.class,
				"סניף",
				"CZSS_T110_LINE.CH_SNIF_CHOTEM",
				true,
				3,
				true);
			matafTable.getOurModel().addColumn(
				Long.class,
				"ספרת ביקורת",
				"CZSS_T110_LINE.CH_SNIF_S_B",
				true,
				2);
			matafTable.getOurModel().addColumn(
				Long.class,
				"מספר חשבון",
				"CZSS_T110_LINE.CH_CH_CHOTEM",
				true,
				10);
			matafTable.getOurModel().addColumn(
				Double.class,
				"סכום המחאה",
				"CZSS_T110_LINE.CH_SCHUM_CHEQ",
				true);
			
			matafTable.getOurModel().addColumn(
				String.class,
				"תקינות 1",
				"CZSS_T110_LINE.CH_MSG1");
			matafTable.getOurModel().addColumn(
				String.class,
				"תקינות 2",
				"CZSS_T110_LINE.CH_MSG2");

			matafTable.setNumberOfHiddenColumns(2);

			// Attach operations to the columns :
	/*		matafTable.setNavigationParametersForColumn(
				1,
				new com.ibm.dse.gui.NavigationParameters(
					"processor",
					0,
					"checkBankNumberClientOp",
					0,
					"",
					"",
					"",
					"",
					"",
					"",
					0,
					0,
					0,
					0,
					false,
					false));
			matafTable.setNavigationParametersForColumn(
				2,
				new com.ibm.dse.gui.NavigationParameters(
					"processor",
					0,
					"checkBranchIdAccording2bankClientOp",
					0,
					"",
					"",
					"",
					"",
					"",
					"",
					0,
					0,
					0,
					0,
					false,
					false));
			matafTable.setNavigationParametersForColumn(
				4,
				new com.ibm.dse.gui.NavigationParameters(
					"processor",
					0,
					"checkAccountNumber4ChequeClientOp",
					0,
					"",
					"",
					"",
					"",
					"",
					"",
					0,
					0,
					0,
					0,
					false,
					false));
			matafTable.setNavigationParametersForColumn(
				5,
				new com.ibm.dse.gui.NavigationParameters(
					"processor",
					0,
					"checkAmmountOfCheckClientOp",
					0,
					"",
					"",
					"",
					"",
					"",
					"",
					0,
					0,
					0,
					0,
					false,
					false));*/

			// Special configuration for the bank column.			
			MatafComboTextField mctf1 =
				(MatafComboTextField)matafTable.getTextEditorAtColumn(1);

			//	Create the banks list table.
			BanksTableComboBoxButton bcbb = new BanksTableComboBoxButton();
			mctf1.setTable(bcbb.getTable());
			mctf1.setFillInChar((char)Character.UNASSIGNED);

			// Special configuration for the snif column.			
			MatafComboTextField mctf2 =
				(MatafComboTextField)matafTable.getTextEditorAtColumn(2);

			// Create the branches list table.
			BranchTableComboBoxButton bcbb2 = new BranchTableComboBoxButton();
			mctf2.setTable(bcbb2.getTable());

			matafTable
				.getSelectionModel()
				.addListSelectionListener(new ListSelectionListener()
			{
				/**
				 * Method synchronizes the CH_MSG1 and CH_MSG2 when changing row selection.
				 */
				public void valueChanged(ListSelectionEvent e)
				{
					if (matafTable.getOurModel().getRowCount() == 0)
						return;

					DefaultListSelectionModel dlsm =
						(DefaultListSelectionModel)e.getSource();
					int selectedRow = dlsm.getAnchorSelectionIndex();
					System.out.println("Selected Row=" + selectedRow);
				}
			});

			/**
			 * Allow the inserting of new data to the table through an automatic
			 * cheque reader.If the cheque-reader toggle button is selected
			 * the new line will be automatically fed from the reader, 
			 * and putting the focus on the amount column.
			 * 
			 * PENDING : Maybe join this code with the table's infra-structure.
			 */
			matafTable
				.getModel()
				.addTableModelListener(new TableModelListener()
			{
				public void tableChanged(TableModelEvent e)
				{
					if (e.getType() == TableModelEvent.INSERT)
					{
						System.out.println("Insert");
					}
				}
			});
		}
		
		matafTable.getOurModel().appendNewRow();
		return matafTable;
	}
	
	
	private static void initEnvironment() throws Exception
	{
		Context.reset();
		Settings.reset("http://127.0.0.1:9080/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY);
		
		Context ctx = (Context) Context.readObject("workstationCtx");			
		CSClientService csrv = (CSClientService) ctx.getService("CSClient");
		csrv.establishSession();	
		DSEClientOperation startOp =  (DSEClientOperation) DSEOperation.readObject("startupClientOp");
		startOp.execute();
	}
	
	public static void main(String[] args)
	{
		try
		{
			initEnvironment();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		//Create and set up the window.
		JFrame frame = new JFrame("FormattedTextFieldDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		
		MatafTransactionView transactionView = new FormattedTextFieldTest();
		transactionView.setOpaque(true);
		transactionView.setContextName("slikaCtx");
		transactionView.setPreferredSize(new Dimension(1015,635));
		
		
		MatafClientView clientView = new MatafClientView();
		clientView.addTransactionView(transactionView);
		
		frame.setContentPane(clientView);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
