package mataf.override;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import mataf.types.MatafEmbeddedPanel;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OverridePanel extends MatafEmbeddedPanel
{

	private OverrideResponcePanel orpPanel=null;

	public OverrideResponcePanel getOverrideResponcePanel()
	{
		return orpPanel;
	}

	/**
	 * Constructor for OverridePanel.
	 * @param layout
	 */
	public OverridePanel(LayoutManager layout)
	{
		super(layout);
	}	

	/**
	 * Constructor for OverridePanel.
	 */
	public OverridePanel()
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
        //this.setSize(535, 370);
        setLayout(new BorderLayout());
        this.setBounds(0, 0, 780, 450);
        setBorder(null);
		add(orpPanel=new OverrideResponcePanel(),BorderLayout.SOUTH);	
		//add(new SampleSlikaOverridePanel(),BorderLayout.CENTER);	
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
