package mataf.override;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.types.MatafEmbeddedPanel;

import com.ibm.dse.base.DSEObjectNotFoundException;
import mataf.dse.appl.OpenDesktop;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SampleSlikaOverridePanel extends MatafEmbeddedPanel
{

     private mataf.types.MatafLabel matafLabel = null;
     private mataf.types.MatafLabel matafLabel2 = null;
     private mataf.types.MatafButton matafButton = null;
	/**
	 * Constructor for SampleSlikaOverride.
	 */
	public SampleSlikaOverridePanel()
	{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getMatafLabel(), null);
        this.add(getMatafLabel2(), null);
        this.add(getMatafButton(), null);
        this.setSize(527, 171);
			
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(22, 26, 141, 55);
			matafLabel.setText("JLabel");
			matafLabel.setDataName("AccountNumber");
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
			matafLabel2.setBounds(210, 29, 154, 50);
			matafLabel2.setText("JLabel");
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
			matafButton = new mataf.types.MatafButton("Refresh");
			matafButton.setBounds(200, 120, 92, 27);
			matafButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					MatafTransactionView dseView = OpenDesktop.getActiveTransactionView();
					try
					{
						System.out.println("Our Ctx name: "+dseView.getContext().getName());
						System.out.println("Our active Context is :"+dseView.getContext().getValueAt("AccountNumber"));
					}
					catch (DSEObjectNotFoundException ex)
					{
						ex.printStackTrace();	
					}
					dseView.refreshDataExchangers();					
				}
			});
		}
		return matafButton;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,-1"
