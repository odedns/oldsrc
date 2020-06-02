package mataf.srika.panels;

import java.awt.LayoutManager;

import mataf.types.MatafEmbeddedPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (15/02/2004 14:52:38).  
 */
public class SrikaNavigationPanel extends MatafEmbeddedPanel
{

	private mataf.srika.panels.SrikaPirteyIska srikaPirteyIska = null;
	private mataf.types.MatafTitle matafTitle = null;
	private mataf.srika.panels.SrikaNavigationInnerPanel srikaNavigationInnerPanel = null;
	/**
	 * @param layout
	 */
	public SrikaNavigationPanel(LayoutManager layout)
	{
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SrikaNavigationPanel(LayoutManager arg0, boolean arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SrikaNavigationPanel(boolean arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public SrikaNavigationPanel()
	{
		super();
			initialize();
	// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setLayout(new java.awt.BorderLayout());
        this.add(getSrikaPirteyIska(), java.awt.BorderLayout.SOUTH);
        this.add(getMatafTitle(), java.awt.BorderLayout.NORTH);
        this.add(getSrikaNavigationInnerPanel(), java.awt.BorderLayout.CENTER);
        this.setBounds(0, 0, 780, 450);
			
	}
	/**
	 * This method initializes srikaPirteyIska
	 * 
	 * @return mataf.srika.panels.SrikaPirteyIska
	 */
	private mataf.srika.panels.SrikaPirteyIska getSrikaPirteyIska() {
		if(srikaPirteyIska == null) {
			srikaPirteyIska = new mataf.srika.panels.SrikaPirteyIska();
		}
		return srikaPirteyIska;
	}
	/**
	 * This method initializes matafTitle
	 * 
	 * @return mataf.types.MatafTitle
	 */
	private mataf.types.MatafTitle getMatafTitle() {
		if(matafTitle == null) {
			matafTitle = new mataf.types.MatafTitle();
			matafTitle.setText("491 - סריקת פעולות הפקדה ופידיון");
		}
		return matafTitle;
	}
	/**
	 * This method initializes srikaNavigationInnerPanel
	 * 
	 * @return mataf.srika.panels.SrikaNavigationInnerPanel
	 */
	private mataf.srika.panels.SrikaNavigationInnerPanel getSrikaNavigationInnerPanel() {
		if(srikaNavigationInnerPanel == null) {
			srikaNavigationInnerPanel = new mataf.srika.panels.SrikaNavigationInnerPanel();
		}
		return srikaNavigationInnerPanel;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="9,-29"
