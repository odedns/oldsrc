package mataf.slika.panels;

import mataf.types.MatafButton;

import com.ibm.dse.gui.SpButton;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SummaryPanel extends ConclusionBasePanel {
	private MatafButton matafButton3;
	public SummaryPanel() {
		super();
		initialize();
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setBounds(0, 0, 780, 450);
        this.add(getMatafButton3());
			
	}
	
	public MatafButton getMatafButton3() {
		if(matafButton3 == null) {
			matafButton3 = new MatafButton();
			matafButton3.setBounds(30, 425, 50, 20);
			matafButton3.setText("ца");
			matafButton3.setType(MatafButton.CLOSE_VIEW);
			//matafButton3.setDataName("CloseButton1");
			matafButton3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"",0,"slikaView","mataf.slika.views.SlikaView","","","","",0,0,0,0,false,false));
		}
		return matafButton3;
	}
}
