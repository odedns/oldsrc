package tests;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;
import mataf.types.MatafTextField;
import mataf.types.textfields.MatafNumericField;

import com.ibm.dse.base.Settings;
import com.ibm.dse.gui.DSEPanel;

/**
 * @author administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestView extends DSEPanel {

     private MatafButton button;
     private MatafLabel label;
     private MatafNumericField matafTextField3;
//     private SlikaPanel1 panel;
//     private SlikaPanel1 panel;
     private MatafEmbeddedPanel panel;

	/**
	 * This method initializes 
	 * 
	 */
	public TestView() {
		super();
		setContextName("slikaCtx");				
		initialize();
//		System.out.println("getContext().getKeyedCollection()=" + getContext().getKeyedCollection());
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setViewName("testView");
		panel = new MatafEmbeddedPanel();
//		panel = new SlikaPanel1();
		panel.setBounds(0,0,800,600);
//		panel.setBackground(Color.PINK);
//		panel.setLayout(new FlowLayout());
		
//		panel.add(getButton(), null);
//		panel.add(getMatafTextField3(), null);
        add(getLabel(), null);		
		JScrollPane scroll = new JScrollPane(panel);
		this.add(scroll);
        this.setSize(800, 600);
			
	}
	/**
	 * This method initializes button
	 * 
	 * @return com.mataf.dse.gui.MatafButton
	 */
	private MatafButton getButton() {
		if(button == null) {
			button = new MatafButton();
			button.setBounds(180, 100, 200, 30);			
			button.setText("עדכן Context עם מידע");
			button.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkAccountSlClientOp",0,"","","","","","",0,0,0,0,false,false));
			button.setType("Execute_Operation");
//			button.addActionListener(new java.awt.event.ActionListener() 
//			{
//				public void actionPerformed(java.awt.event.ActionEvent e) 
//				{
//					
//					try {
//						setValueAt("BranchId",""+Math.random());
//					} catch (DSEInvalidArgumentException e2) {
//					} catch (DSEObjectNotFoundException e3) {
//					}
//					refreshDataExchangers();
//				}
//			});
		}
		return button;
	}
	
	private MatafNumericField getMatafTextField3() {
		if(matafTextField3 == null) {
			matafTextField3 = new MatafNumericField();
			matafTextField3.setDataName("AccountNumber");
			matafTextField3.setBounds(180, 200, 200, 30);
			matafTextField3.setType("Execute_Operation");
			matafTextField3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"checkAccountSlClientOp",0,"","","","","","",0,0,0,0,false,false));
			
			matafTextField3.addFocusListener(new FocusAdapter() 
			{
				
				public void focusLost(java.awt.event.FocusEvent e) 
				{
					try {
						matafTextField3.fireCoordinationEvent();

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return matafTextField3;
	}
	/**
	 * This method initializes textField
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private MatafLabel getLabel() {
		if(label == null) {
			label = new MatafLabel("בדיקת רכיבים");
			label.setDataName("BranchId");
			label.setBounds(180, 134, 300, 20);
		}
		return label;
	}
	
	public static void main(String[] args)
	{
		try
		{
			// Init.
			Settings.reset("http://127.0.0.1/MatafServer/dse/dse.ini");
			Settings.initializeExternalizers(Settings.MEMORY);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JFrame f = new JFrame("Testing Table");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TestView tv = new TestView();
		f.getContentPane().add(tv, BorderLayout.CENTER);

		
		f.setSize(640,480);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		tv.refreshDataExchangers();
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
