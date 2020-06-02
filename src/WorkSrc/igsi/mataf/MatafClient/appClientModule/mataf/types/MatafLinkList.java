/*
 * Created on 17/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import mataf.desktop.beans.MatafMenuBar;
import mataf.desktop.beans.MatafWorkingArea;
import mataf.desktop.infopanels.SheiltotPanel;
import mataf.desktop.views.MatafClientView;
import mataf.dse.appl.OpenDesktop;
import mataf.services.proxy.ProxyService;
import mataf.utils.FontFactory;

import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.gui.CoordinatedEventListener;
import com.ibm.dse.gui.CoordinatedEventMulticaster;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.DataChangedListener;
import com.ibm.dse.gui.DataExchanger;
import com.ibm.dse.gui.DataExchangerWithList;
import com.ibm.dse.gui.NavigationParameters;
import com.ibm.dse.gui.Outsider;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.gui.SpPanel;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafLinkList implements Outsider, DataExchangerWithList
{
	private static final int SPACING = 3;
	private static final int LINK_HEIGHT = 12;
	
	private SpPanel			spPanel = null;
	private MatafEmbeddedPanel 	spQueryPanel= null;
	private String		 	dataNameForList;
	private List			alLinks= null;

	private transient 		CoordinatedEventListener aCoordinatedEventListener;
	/**
	 * 
	 */
	public MatafLinkList()
	{
		super();
		alLinks=new ArrayList();
	}

	
	/*public static void main(String[] args)
		{
			JFrame f = new JFrame("Testing MessagesPane");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
			MatafLinkList mmp = new MatafLinkList();
			JScrollPane scroller = mmp.getLogScrollPane();
			f.getContentPane().add(scroller);
			f.setSize(320,200);    	
			f.setVisible(true);
    	
			for(int i=0;i<30;i++)
			{
				Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
				mmp.log("בדיקה חלון הודעות",c);
			}
		}*/


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.Outsider#getSpPanel()
	 */
	/**
	 * @see com.ibm.dse.gui.PanelActions#getSpPanel()
	 */
	public SpPanel getSpPanel() 
	{
		return spPanel;		
	}
	
	/**
	 * @see com.ibm.dse.gui.Outsider#setSpPanel(SpPanel)
	 */
	public void setSpPanel(SpPanel spPanel) 
	{
		this.spPanel = spPanel;
		spPanel.registerOutsider(this);
	}

	public MatafEmbeddedPanel getQueryPanel()
	{
		return spQueryPanel;		
	}

	public void setQueryPanel(MatafEmbeddedPanel spQueryPanel)
	{
		this.spQueryPanel=spQueryPanel;		
	}

	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#addActionListener(java.awt.event.ActionListener)
	 */
	public void addActionListener(ActionListener ae)
	{
	}

	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#addCoordinatedEventListener(com.ibm.dse.gui.CoordinatedEventListener)
	 */
	public void addCoordinatedEventListener(CoordinatedEventListener newListener)
	{
		aCoordinatedEventListener = CoordinatedEventMulticaster.add(aCoordinatedEventListener, newListener);
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#addDataChangedListener(com.ibm.dse.gui.DataChangedListener)
	 */
	public void addDataChangedListener(DataChangedListener adl)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#getAlternativeDataName()
	 */
	public String getAlternativeDataName()
	{
		return "";
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#getDataDirection()
	 */
	public String getDataDirection()
	{
		return Settings.OUTPUT_DIRECTION;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#getDataName()
	 */
	public String getDataName()
	{
		return "";
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#getDataValue()
	 */
	public Object getDataValue()
	{
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#getErrorMessage()
	 */
	public String getErrorMessage()
	{
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#getNavigationParameters()
	 */
	public NavigationParameters getNavigationParameters()
	{
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#getType()
	 */
	public String getType()
	{
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#hasAlternativeDataName()
	 */
	public boolean hasAlternativeDataName()
	{
		return false;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#isInError()
	 */
	public boolean isInError()
	{
		return false;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#isKeyedValue()
	 */
	public boolean isKeyedValue()
	{
		return false;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#isRequired()
	 */
	public boolean isRequired()
	{
		return false;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#removeActionListener(java.awt.event.ActionListener)
	 */
	public void removeActionListener(ActionListener ae)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#removeCoordinatedEventListener(com.ibm.dse.gui.CoordinatedEventListener)
	 */
	public void removeCoordinatedEventListener(CoordinatedEventListener newListener)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#removeDataChangedListener(com.ibm.dse.gui.DataChangedListener)
	 */
	public void removeDataChangedListener(DataChangedListener dcl)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setAlternativeDataName(java.lang.String)
	 */
	public void setAlternativeDataName(String o)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setDataDirection(java.lang.String)
	 */
	public void setDataDirection(String o)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setDataName(java.lang.String)
	 */
	public void setDataName(String o)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setDataValue(java.lang.Object)
	 */
	public void setDataValue(Object o)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setHelpID(java.lang.String)
	 */
	public void setHelpID(String helpID)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setNavigationParameters(com.ibm.dse.gui.NavigationParameters)
	 */
	public void setNavigationParameters(NavigationParameters navigationParameters)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setRequired(boolean)
	 */
	public void setRequired(boolean req)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchanger#setType(java.lang.String)
	 */
	public void setType(String type)
	{
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.PanelActions#getDataToClear()
	 */
	public String getDataToClear()
	{
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.PanelActions#getDSECoordinatedPanel()
	 */
	public DSECoordinatedPanel getDSECoordinatedPanel()
	{
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchangerWithList#getDataNameForList()
	 */
	public String getDataNameForList()
	{
		return dataNameForList;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchangerWithList#setDataNameForList(java.lang.String)
	 */
	public void setDataNameForList(String dataNameForList)
	{
		this.dataNameForList = dataNameForList;
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.gui.DataExchangerWithList#setDataValueForList(java.lang.Object)
	 */
	public void setDataValueForList(Object dataValueForList)
	{
		Vector v = null;
		try
		{
			v = (com.ibm.dse.base.Vector)((IndexedCollection)dataValueForList).getValue();
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		// Clear previous messages.
		//alLinks.clear();
		//addMessage(CLEAR_MESSAGES);
		
		Font fFont=FontFactory.createFont("Tahoma", Font.PLAIN, 10);
		
		for(int i=0;i<v.size();i++)
		{
			KeyedCollection kcTemp= (KeyedCollection)v.get(i);
			
			DataField dfQueryName=(DataField)kcTemp.tryGetElementAt("queryName");
			DataField dfQueryId=(DataField)kcTemp.tryGetElementAt("queryId");
			
			String strQueryShortId=((String)dfQueryId.getValue());
			strQueryShortId=strQueryShortId.substring(strQueryShortId.length()-3);
			String strValue=(String)dfQueryName.getValue();
			
			final String strQueryId=(String)dfQueryId.getValue();
			final String strFinalQueryId=strQueryShortId;
			
			
			MatafLink mlTempName=new MatafLink(strValue);
			MatafLink mlTempId=new MatafLink(strQueryShortId);
			
			
			mlTempName.setSize(spQueryPanel.getWidth()-30,LINK_HEIGHT);
			mlTempName.setLocation(0,LINK_HEIGHT*i+SPACING);
			mlTempName.setFont(fFont);
			
			mlTempId.setSize(20,LINK_HEIGHT);
			mlTempId.setLocation(mlTempName.getWidth()+mlTempName.getX(),LINK_HEIGHT*i+SPACING);
			mlTempId.setFont(fFont);
			
			MouseListener mlLinkListener=null;
			
			mlTempName.addMouseListener(mlLinkListener=new MouseListener(){
				String strQueryNum=strQueryId;
				public void mouseClicked(MouseEvent e)
				{
					if(e.getButton()==MouseEvent.BUTTON3)
					{
						Font fPopupFont=FontFactory.createFont("Tahoma", Font.PLAIN, 12);
						JPopupMenu jpmMenu=new JPopupMenu("פעולות");
						JMenuItem jmiRemoveRow=new JMenuItem("הסר שאילתא "+strFinalQueryId);
						jmiRemoveRow.setFont(fPopupFont);
						//jmiRemoveRow.setHorizontalAlignment(SwingConstants.RIGHT);
						JLabel jlHeader=new JLabel("פעולות על שאילתא");
						jlHeader.setFont(fPopupFont);
						jpmMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						jpmMenu.add(jlHeader);
						jpmMenu.add(new JSeparator());
						jpmMenu.add(jmiRemoveRow);
						jpmMenu.show(e.getComponent(),e.getX(),e.getY());														
					}
					else
					{
						try
						{
							/*ProxyService proxy = 
								(ProxyService)OpenDesktop.getContext().getService("proxyService");
							proxy.activateTransaction(strQueryNum);*/
							MatafMenuBar.getMenuItemByTaskName(strQueryNum).doClick();
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					}
				}
				public void mouseEntered(MouseEvent e){}
				public void mouseExited(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
			});			
			
			mlTempId.addMouseListener(mlLinkListener);
			
			spQueryPanel.add(mlTempName);
			spQueryPanel.add(mlTempId);
		}
		
		/*while(v.size()>0)
		{
			KeyedCollection k = (KeyedCollection)v.remove(0);

			DataField df = (DataField)k.tryGetElementAt("BusinessMessage");
			if(df instanceof VisualDataField)
			{
				VisualDataField vf = (VisualDataField)df;			
				addMessage((String)vf.getValue(),vf.getForeground());
			}
			else // In case the business message is NOT a VisualDataField just show the message in red.(QuickPatch)
				addMessage((String)df.getValue(),Color.red);
				
				
		}*/
		
	}
	
	public void fireCoordinationEvent(DSECoordinationEvent event)
	{
		if (aCoordinatedEventListener == null)
			return;
	
		aCoordinatedEventListener.handleDSECoordinationEvent(event);
	}
}
