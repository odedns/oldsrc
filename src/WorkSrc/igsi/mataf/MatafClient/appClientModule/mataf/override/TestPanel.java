package mataf.override;

import mataf.logger.GLogger;
import mataf.types.MatafEmbeddedPanel;

import com.ibm.dse.gui.DSECoordinationEvent;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestPanel extends MatafEmbeddedPanel {

     private com.ibm.dse.gui.SpLabel aSpLabel = null;
     private com.ibm.dse.gui.SpButton aSpButton = null;
     private com.ibm.dse.gui.NavigationParameters ivjNavigationParameters = null;
     private com.ibm.dse.gui.SpButton aSpButton1 = null;
     private com.ibm.dse.gui.SpButton aSpButton2 = null;
     private com.ibm.dse.gui.SpLabel spLabel = null;
     private com.ibm.dse.gui.SpTextField spTextField = null;
     private com.ibm.dse.gui.SpLabel spLabel2 = null;
     private com.ibm.dse.gui.SpTextField spTextField2 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public TestPanel() {
		super();
		System.out.println("TestPanel()");
		initialize();
	}
	
		public static void main(String[] args) {
		try {
		javax.swing.JFrame frame = new javax.swing.JFrame();		
		TestPanel testPanel = new TestPanel();
		frame.setContentPane(testPanel);
		frame.setSize(testPanel.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.show();
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		frame.setVisible(true);
	} catch (Throwable exception) {
		exception.printStackTrace(System.out);
	}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getASpLabel(), null);
        this.add(getASpButton(), null);
        this.add(getASpButton1(), null);
        this.add(getASpButton2(), null);
        this.add(getSpLabel(), null);
        this.add(getSpTextField(), null);
        this.add(getSpLabel2(), null);
        this.add(getSpTextField2(), null);
        this.setSize(570, 147);
        this.setBackground(new java.awt.Color(255,247,255));               
//        setContextName("testPanelCtx");

	}
	/**
	 * This method initializes aSpLabel
	 * 
	 * @return com.ibm.dse.gui.SpLabel
	 */
	private com.ibm.dse.gui.SpLabel getASpLabel() {
		if(aSpLabel == null) {
			aSpLabel = new com.ibm.dse.gui.SpLabel();
			aSpLabel.setBounds(35, 29, 240, 37);
			aSpLabel.setText("Test Panel");
		}
		return aSpLabel;
	}
	/**
	 * This method initializes aSpButton
	 * 
	 * @return com.ibm.dse.gui.SpButton
	 */
	private com.ibm.dse.gui.SpButton getASpButton() {
		if(aSpButton == null) {
			aSpButton = new com.ibm.dse.gui.SpButton();
			aSpButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",3,"SendOverrideView","mataf.override.SendOverrideView","","","","",0,0,0,0,false,false));
			aSpButton.setBounds(156, 99, 109, 29);
			aSpButton.setBounds(125, 100, 109, 29);
			aSpButton.setText("אישור מנהל מרוחק");
			aSpButton.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black,0));
			aSpButton.setType("Next_View");
			/*
			aSpButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
        		System.out.println("Send Override button");        				
     			OpenDesktop.getActiveMatafPanel().refreshDataExchangers();
		      }
    		});
    		*/
    		
		}
		return aSpButton;
	}


	/**
	 * This method initializes aSpButton1
	 * 
	 * @return com.ibm.dse.gui.SpButton
	 */
	private com.ibm.dse.gui.SpButton getASpButton1() {
		if(aSpButton1 == null) {
			aSpButton1 = new com.ibm.dse.gui.SpButton();
			aSpButton1.setBounds(24, 100, 80, 30);
			aSpButton1.setText("סגור");
			aSpButton1.setType("Close");
		}
		return aSpButton1;
	}
	/**
	 * This method initializes aSpButton2
	 * 
	 * @return com.ibm.dse.gui.SpButton
	 */
	private com.ibm.dse.gui.SpButton getASpButton2() {
		if(aSpButton2 == null) {
			aSpButton2 = new com.ibm.dse.gui.SpButton();
			aSpButton2.setBounds(286, 96, 116, 32);
			aSpButton2.setText("אישור מנהל מקומי");
			aSpButton2.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",3,"LocalOverrideView","mataf.override.LocalOverrideView","","","","",0,0,0,0,false,false));
			aSpButton2.setType("Next_View");
		}
		return aSpButton2;
	}
	/**
	 * This method initializes spLabel
	 * 
	 * @return com.ibm.dse.gui.SpLabel
	 */
	private com.ibm.dse.gui.SpLabel getSpLabel() {
		if(spLabel == null) {
			spLabel = new com.ibm.dse.gui.SpLabel();
			spLabel.setBounds(312, 27, 118, 25);
			spLabel.setText("samchut");
		}
		return spLabel;
	}
	/**
	 * This method initializes spTextField
	 * 
	 * @return com.ibm.dse.gui.SpTextField
	 */
	private com.ibm.dse.gui.SpTextField getSpTextField() {
		if(spTextField == null) {
			spTextField = new com.ibm.dse.gui.SpTextField();
			spTextField.setBounds(442, 25, 94, 24);
			spTextField.setDataName("trxORData.samchutMeasheret");
		}
		return spTextField;
	}
	
	/**
	 * @see com.ibm.dse.gui.SpPanel#handleDSECoordinationEvent(DSECoordinationEvent)
	 */
	public void handleDSECoordinationEvent(DSECoordinationEvent arg0) {
		super.handleDSECoordinationEvent(arg0);
		GLogger.debug("event: " + arg0.getEventSourceType());
	}

	


	/**
	 * This method initializes spLabel2
	 * 
	 * @return com.ibm.dse.gui.SpLabel
	 */
	private com.ibm.dse.gui.SpLabel getSpLabel2() {
		if(spLabel2 == null) {
			spLabel2 = new com.ibm.dse.gui.SpLabel();
			spLabel2.setBounds(317, 66, 113, 22);
			spLabel2.setText("Snif 60");
		}
		return spLabel2;
	}
	/**
	 * This method initializes spTextField2
	 * 
	 * @return com.ibm.dse.gui.SpTextField
	 */
	private com.ibm.dse.gui.SpTextField getSpTextField2() {
		if(spTextField2 == null) {
			spTextField2 = new com.ibm.dse.gui.SpTextField();
			spTextField2.setBounds(442, 67, 97, 23);
			spTextField2.setDataName("GLSE_GLBL.GL_SAMCHUT_SNIF_60");
		}
		return spTextField2;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="96,24"
