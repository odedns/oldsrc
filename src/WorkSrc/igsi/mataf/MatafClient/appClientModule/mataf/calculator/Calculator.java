package mataf.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;

/**
 * This class represents an arithmethical calculator.<p>
 * The calculator supports the following :<p>
 * - 4 basic operations (+,-,*,/) with precedence.<p>
 * - Storing and recalling.<p>
 * - History log of commited operations.<p>
 * - Grouping representation. (i.e. 1,234,567).<p>
 * - Customizable number of digits before and after the decimal point (Future).<p>
 * 
 * 
 * PENDING :
 * 1.Handle overflow in result.
 * 2.Make calculations rely on BigDecimal class (Solves 1?).
 * 3.Check what should trigger history clear.
 * 4.Make the ability to control properties :
 * 	 - Number of digits after the decimal point.
 *   - Number of digits before the decimal point.
 *   - Use / Do not use operators precedence.
 *   - Use / Do not use Grouping.
 *   - Control of rounding methods.
 *   - Use Integers only.
 * 
 * @author Nati Dykstein. Creation Date : (01/03/2004 12:21:15).  
 */
public class Calculator extends JPanel
							implements DigitsIndices, ActionListener
{
	/** The buttons labels matrix. */
	private static final String[] mainPanelLabels =
	{ 
	  "7" , "8"   , "9" , "/" , "C"  ,
	  "4" , "5"   , "6" , "*" , "MS" ,
	  "1" , "2"   , "3" , "-" , "MR" ,
	  "0" , "+/-" , "." , "+" , "="
	};
	
	/** The buttons colors matrix. */
	private static final Color[] buttonsColors = new Color[]
	{
		Color.black , Color.black, Color.black, Color.blue, Color.red,
		Color.black , Color.black, Color.black, Color.blue, Color.red,
		Color.black , Color.black, Color.black, Color.blue, Color.red,
		Color.black , Color.blue,  Color.black, Color.blue, Color.black
	};
	
	private static final Font DISPLAY_FONT = new Font("Tahoma", Font.PLAIN, 14);
	
	private static final Dimension CALCULATOR_WINDOW_SIZE = new Dimension(265,335);
	
	private static Calculator	calculator;
	private static JDialog 	ownerDialog;
	
	/** Contains the elements of the mathemthical expression. */
	private Vector expression = new Vector();
	
	/** The result of the last commited operation. */
	private String 				result;
	
	/** Used by MS/MR buttons. */
	private String 				memoryCell;
	
	/** The textfield to which we return the answer. */
	private JTextField 			returnTextField;
	
	/** true if last action was an operator. */
	private boolean 				lastWasOp;
	
	// Information panel vars.
	private JPanel					infoPanel;
	private JLabel					expressionLabel;	
	private JTextArea				historyTextArea;
	private JLabel 				statusLine;

	// Display panel vars.
	private JPanel 				displayPanel;
	private JFormattedTextField 	displayField;
	
	// Memory panel vars.
	private JPanel 				memoryPanel;
	
	// Main panel vars.
	private JPanel 				mainPanel;
	private CalculatorButton[]		mainPanelButtons;


	/**
	 * Constructor builds the calculator panel.
	 */
	public Calculator()
	{
		super(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
		
		// Display Panel
		initDisplayPanel();
		
		// Main Panel
		initMainPanel();

		// Information Panel
		initInfoPanel();
		
		// Bind actions.
		bindActions();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////// G U I    B U I L D///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Binds keys for additional functionality :
	 * Alt+2 - Use 2 digits precision.
	 * Alt+4 - Use 4 digits precision.
	 * Alt+6 - Use 6 digits precision.
	 * 
	 */
	private void bindActions()
	{
		InputMap im = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
		im.put(KeyStroke.getKeyStroke("F1"), "Help");
				
		im.put(KeyStroke.getKeyStroke("alt 2"), "SetDigitsPrecision");
		im.put(KeyStroke.getKeyStroke("alt 4"), "SetDigitsPrecision");
		im.put(KeyStroke.getKeyStroke("alt 6"), "SetDigitsPrecision");
		
		ActionMap am = getActionMap();
		
		/**
		 * Changes the number of precision digits used in calculations.
		 */
		am.put("SetDigitsPrecision", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				CalculatorNumberFormatter formatter = 
							(CalculatorNumberFormatter)displayField.getFormatter();
				
				DecimalFormat df = (DecimalFormat)formatter.getFormat();
				int digits = Integer.parseInt(e.getActionCommand());
				df.setMaximumFractionDigits(digits);
				statusLine.setText("Using "+digits+" digits precision.");
			}
		});
		
		am.put("Help", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				String message = 
					"Mathematical Calculator Version 1.00" + "\n" +
					"Keys Functionality : " + "\n" +
					"Alt+2 - Use 2 digits precision." + "\n" +
					"Alt+4 - Use 4 digits precision." + "\n" +
					"Alt+6 - Use 6 digits precision." + "\n";
				
				JOptionPane.showMessageDialog(ownerDialog,message,"Help",JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	/**
	 * Creates the panel that contains the textfield in which the
	 * numbers are displayed.
	 */
	private void initDisplayPanel()
	{		
		displayPanel = new JPanel();
		displayPanel.setBorder(BorderFactory.createEmptyBorder());
		
		initDisplayField();
		displayPanel.add(displayField);
			
		add(displayPanel, BorderLayout.NORTH);
	}

	/**
	 * Creates the main panel the contains the buttons representing
	 * the digits, operators and functions of the calculator.
	 */
	private void initMainPanel()
	{		
		mainPanel = new JPanel(new GridLayout(4,5,10,10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		// Create and init the calculator buttons.
		mainPanelButtons = new CalculatorButton[mainPanelLabels.length];
		for(int i=0;i<mainPanelLabels.length;i++)
		{
			mainPanelButtons[i] = new CalculatorButton(mainPanelLabels[i]);
			mainPanelButtons[i].setForeground(buttonsColors[i]);			
			mainPanelButtons[i].addActionListener(this);
			mainPanel.add(mainPanelButtons[i]);
		}
		
		// Add the main panel the our calculator panel.
		add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Create the information panel that consists of a label the displays
	 * the current expression, and text area that displays the history of
	 * operations.
	 */
	private void initInfoPanel()
	{	
		infoPanel = new JPanel(new BorderLayout());
		infoPanel.setPreferredSize(new Dimension(100,130));
		
		infoPanel.setBorder(BorderFactory.createTitledBorder("History"));
		
		// Create and init the expression label.
		expressionLabel = new JLabel();
		expressionLabel.setBorder(BorderFactory.createEmptyBorder(0,0,6,0));
		expressionLabel.setFont(DISPLAY_FONT);
		expressionLabel.setPreferredSize(new Dimension(150,15));
		
		infoPanel.add(expressionLabel, BorderLayout.NORTH);
		
		// Create and init the history text area.
		historyTextArea = new JTextArea(4,15);
		historyTextArea.setEditable(false);
		historyTextArea.setFont(DISPLAY_FONT);
		historyTextArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		JScrollPane historyScroller = new JScrollPane(historyTextArea);
		historyScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		infoPanel.add(historyScroller, BorderLayout.CENTER);
		
		statusLine = new JLabel("Use F1 for Help.");
		statusLine.setFont(new Font("Monospaced", Font.PLAIN,12));
		statusLine.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		statusLine.setForeground(Color.blue);
		infoPanel.add(statusLine, BorderLayout.SOUTH);
		
		add(infoPanel, BorderLayout.SOUTH);
	}
	
	
	private void initDisplayField()
	{
		// Create the decimal format.
		DecimalFormat ourFormat = new DecimalFormat();
		ourFormat.setMaximumFractionDigits(2);
	
		// Create the formatter with our format.
		CalculatorNumberFormatter calculatorFormatter = 
									new CalculatorNumberFormatter(ourFormat, this);
		calculatorFormatter.setAllowsInvalid(false);
		calculatorFormatter.setValueClass(BigDecimal.class);
				
		// Install the formatted onto the textfield.
		displayField = new JFormattedTextField(calculatorFormatter);
		
		// Init the display field.
		displayField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		displayField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		displayField.setPreferredSize(new Dimension(200,20));
		displayField.setFont(DISPLAY_FONT);
		displayField.addActionListener(this);
		
		// Let the display field get the focus.
		displayField.requestFocus();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////// M O D E L    B U I L D///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Handle the events generated from the calculator buttons.
	 */
	public void actionPerformed(ActionEvent e)
	{
		// Enter was pressed in the textfield.
		if(e.getSource() instanceof JFormattedTextField)
		{
			displayResult();
			return;
		}
		
		JButton button = (JButton)e.getSource();
		String label = button.getText();
		char labelChar = label.charAt(0);
		
		if(isDigitOrDecimalPoint(label))
			processDigits(label);
		
		// Handle operators selection. 
		if(isAnOperator(label))
			processOperators(label);			
		
		// Compute and display result
		if(labelChar=='=')
			displayResult();			
		
		// Clear all data.
		if(labelChar=='C')
			clearData();
		
		// Memory Store.
		if(label.equals("MS"))
			memoryCell = displayField.getText();
		
		// Memort Recall.
		if(label.equals("MR"))
			displayText(memoryCell);
		
		// Negate value.
		if(label.equals("+/-"))
			negateValue();
		
	}
	
	/**
	 * Negate the value currently in the display field.
	 */
	private void negateValue()
	{
		try
		{
			BigDecimal bd = (BigDecimal)displayField.getFormatter().stringToValue(displayField.getText());			
			bd = bd.negate();
			displayText(displayField.getFormatter().valueToString(bd));
		}
		catch (ParseException e)
		{
			//e.printStackTrace();
		}
	}
	
	/**
	 * Handle the 4 basic mathematical operations.
	 * 
	 * @param operator - The operator pressed on the calculator.
	 */
	void processOperators(String operator)
	{
		//System.out.println("Performing "+operator);
		try
		{
			// Two consecutive operators were selected.
			// Replace previous operator with new one.
			if(lastWasOp)
				expression.setElementAt(operator,expression.size()-1);
			else
			{
				// Allow to prefix the number with a minus sign.
				if(displayField.getText().equals(""))
				{ 
					if(operator.charAt(0)=='-')
						displayText("-");
				    // Ignore choosing an operator prior to an operand.
					return;
				}
				
				// Get the displayed number.
				String num = displayField.getText();
		
				// Add the number to the expression.					
				expression.add(displayField.getFormatter().stringToValue(num));
		
				// Add the operator.
				expression.add(operator);
		
				// Indicate that an operator was selected.
				lastWasOp = true;
				
				// Update expression Label.
				updateExpressionLabel();
			}
		}
		catch(ParseException e)
		{
			//e.printStackTrace();
		}
	}
	
	/**
	 * Handle all digits and decimal point processing.
	 * 
	 * @param label
	 * @throws BadLocationException
	 */
	void processDigits(String label) 
	{
		try
		{
			// The length of the current number.
			int len = displayField.getText().length();
				
			// Remove last number before starting next one.
			 if(lastWasOp)
			 {
				 //System.out.println("Removing previous number");
				 displayText(label);
				 lastWasOp = false;
			 }
			 else
			 {
				boolean containsDecimal = displayField.getText().indexOf('.')!=-1;
				// Number already contains a decimal point.
			 	if(label.charAt(0)=='.' && containsDecimal)
			 		return;
			 	// Insert the new digit or decimal point.
				displayField.getDocument().insertString(len, label, null);
			 }
				 
			// Update expression Label.
			updateExpressionLabel();
		}
		catch(BadLocationException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Computes and displays the result of the expression.
	 * 
	 * @throws ParseException
	 */
	private void displayResult() 
	{
		try
		{
			String num = displayField.getText();
			
			// Check if display field is empty.
			if(num.equals(""))
				return;
			
			// Check if expression vector is empty.
			if(expression.size()==0)
				return;
				
			expression.add(displayField.getFormatter().stringToValue(num));
			
			// Add the last operand to the label.
			updateExpressionLabel();
			
			// Main computing method.
			evaluateAndCompute();
			
			// Clear the expression vector.
			expression.clear();
			
			// Update the result field.
			setResult(displayField.getText());
			
			// Update the expressionLabel and history with the result.
			String finalResult = expressionLabel.getText()+" = "+displayField.getText();
			expressionLabel.setText(finalResult);
			historyTextArea.append(finalResult+"\n");
			
			// Clear flag.
			lastWasOp = false;
		}
		catch(ParseException e)
		{
			// Should never happen.
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears all data from the calculator.
	 */
	private void clearData()
	{
		// Clear the display field.
		displayField.setText("");
		
		// Clear expression vector.
		expression.clear();
		
		// Clear the expression label.
		updateExpressionLabel();
		
		// Clear the history.
		historyTextArea.setText("");
		
		// Reset the flag.
		lastWasOp = false;
	}
	
	/**
	 * Clears the history text area.
	 */
	private void clearHistory()
	{
		historyTextArea.setText("");
	}
	
	/**
	 * Update the text of the expression label.
	 */
	private void updateExpressionLabel()
	{
		String stringExp = "";
		for(int i=0;i<expression.size();i++)
			stringExp+=expression.elementAt(i).toString();
		
		expressionLabel.setText(stringExp);
	}
	
	/**
	 * Returns true if the string parameter is a digit or a decimal point.
	 * 
	 * @param s - The string to check.
	 * @return true if the string parameter is a digit or a decimal point.
	 */
	static boolean isDigitOrDecimalPoint(String s)
	{
		return s.length()==1 && Character.isDigit(s.charAt(0)) || s.charAt(0)=='.';
	}
	
	/**
	 * Returns true if s is one of the basic arithmetic operators.
	 * 
	 * @param s - The string to check.
	 * @return true if s is one of the basic arithmetic operators.
	 */
	static boolean isAnOperator(String s)
	{
		return s.equals("/") || s.equals("*") || s.equals("-") || s.equals("+");
	}
	
	/**
	 * This method evalutes the expression in two passes.
	 * First pass computes all the '*' and '/' operators and the second pass
	 * computes all the '+' and '-' operators.<p>
	 * Efficiency : O(2n).
	 */
	private void evaluateAndCompute()
	{
//		System.out.println("\nExpression : ");
//		for(int i=0;i<expression.size();i++)
//			System.out.print(""+expression.elementAt(i));
		
		double answer = 0;
		double operand = 0;
		String operator = "+";
		
		// First pass -> compute * and /.
		int i=1;
		while(i<expression.size()-1)
		{
			operator = (String)expression.elementAt(i);
			if(operator.equals("*") || operator.equals("/"))
			{
				// NOTE : Invokation of computeInsideExpression() replaces the expression with 
				// its result therefore the expression vector size is reduces by two, so we're 
				// NOT incrementing the index here !.
				computeInsideExpression(i);
			}
			else 
				i+=2;
		}
		
		// Second pass -> compute + and -.
		for(i=0, operator="+";i<expression.size();i++)
		{
			// On even we expect an operand.
			if(i%2==0)
			{
				operand = getExpressionValueAt(i);
				answer = compute(answer,operator,operand);
			}
			else // On odd we expect an operator.
				operator = (String)expression.elementAt(i);
		}
		
		// Get the result as String.
		String stringAnswer = String.valueOf(answer);
		
		// Get rid of the decimal point for integers.
		if(stringAnswer.endsWith(".0"))
			stringAnswer = stringAnswer.substring(0,stringAnswer.length()-2);
		
		// Display the result.
		displayText(stringAnswer);
		
		// Update the object variable.
		result = stringAnswer;		
	}
	
	/**
	 * Returns the result of operand1 'operator' operand2.
	 * Operator can be one of the 4 basic arithmethic operations. 
	 * 
	 * @param operand1 The first operand.
	 * @param operator The operator.
	 * @param operand2 The second operand.
	 * @return operand1 'operator' operand2.
	 */
	public static double compute(double operand1, String operator, double operand2)
	{
		if(operator.equals("/"))
		 return operand1 / operand2;
		if(operator.equals("*"))
		 return operand1 * operand2;
		if(operator.equals("-"))
		 return operand1 - operand2;
		if(operator.equals("+"))
		 return operand1 + operand2;
		
		// Should never occur !
		return Double.NaN;
	}
	
	/**
	 * Compute a basic binary expression inside the expression vector, 
	 * and replace the expression with the result.
	 * 
	 * After the operation the expression vector size will reduce by 2.
	 * 	 
	 * @param operatorIndex - The index of the operator of the expression to compute.
	 */
	private void computeInsideExpression(int operatorIndex)
	{
		// Get operands
		double operand1 = getExpressionValueAt(operatorIndex-1);		
		double operand2 = getExpressionValueAt(operatorIndex+1);
		
		// Get operator.
		String operator = (String)expression.elementAt(operatorIndex);
		
		// Compute binary expression.
		double result = compute(operand1, operator, operand2);
		
		// Insert result.
		expression.setElementAt(String.valueOf(result),operatorIndex-1);
		
		// Remove already computed elements.
		expression.remove(operatorIndex+1);
		expression.remove(operatorIndex);
	}
	
	/**
	 * Convenient method for retrieving the value inside the expression vector.
	 * 
	 * @param numberIndex - the index from which to retrieve the data.
	 * @return The data at index 'numberIndex'.
	 */
	private double getExpressionValueAt(int numberIndex)
	{
		return Double.parseDouble(expression.elementAt(numberIndex).toString());
	}
	
	/**
	 * Use this method instead of setText() due to the complexity of the formatter.
	 */
	private void displayText(String newText)
	{
		displayField.setText("");
		try
		{
			displayField.getDocument().insertString(0, newText, null);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setReturnTextField(JTextField returnTextField)
	{
		this.returnTextField = returnTextField;
	}
	
	public JTextField getReturnTextField()
	{
		return returnTextField;
	}
	
	/**
	 * Returns the result as a string inside the textfield assigned for
	 * the calculator.
	 *
	 */
	public void returnResult()
	{
		if(returnTextField!=null)
		{
			// Get the value without the grouping signs.
			String value;
			try
			{
				value =	displayField.getFormatter().stringToValue(displayField.getText()).toString();
				returnTextField.setText(value);
			}
			catch (ParseException e)
			{
				//e.printStackTrace();
			}
		}
	}
	
	public static void openCalculator()
	{
		openCalculator(new JFrame(), null);
	}
	
	/**
	 * @return
	 */
	public String getResult()
	{
		return result;
	}

	/**
	 * @param string
	 */
	public void setResult(String string)
	{
		result = string;
	}
	
	/**
	 * Use this method to open the calculator window.
	 * @param owner - The frame that the calculator should open in.
	 * @param returnTextField - The field to update with the result when closing
	 * 							 the calculator window.
	 */
	public static void openCalculator(JFrame owner, JTextField returnTextField)
	{
		if(ownerDialog==null)
		{
			calculator = new Calculator();
			
			ownerDialog = new JDialog(owner,"Calculator - Version 1.00",false);
			ownerDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			ownerDialog.setContentPane(calculator);
			ownerDialog.setSize(CALCULATOR_WINDOW_SIZE);
			ownerDialog.setResizable(false);
			ownerDialog.setLocationRelativeTo(null);
			
			ownerDialog.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					//System.out.println("Returning result : "+calculator.getResult());
					calculator.returnResult();
				}
			});
		}
				
		calculator.setReturnTextField(returnTextField);
		calculator.clearHistory();
		calculator.clearData();
		ownerDialog.setVisible(true);
	}

		
	public static void main(String[] args)
	{
		Calculator.openCalculator();
		ownerDialog.setResizable(true);
		ownerDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		((JFrame)ownerDialog.getOwner()).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ownerDialog.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				System.out.println("Calculator size = "+ownerDialog.getSize());
			}
		});
	}
}
