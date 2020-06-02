package mataf.utils;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;

import mataf.data.VisualDataField;
import mataf.data.VisualProperties;
import mataf.exchangers.VisualPropertiesExchanger;
import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.gui.Settings;

/**
 *
 * This class encapsulates general-purpose helper methods.
 * 
 * @author Nati Dykstein. Creation Date : (07/09/2003 16:26:50).
 */
public class MatafGuiUtilities
{
	/**
	 * This method sets component's properties according to the methods
	 * reflected in the interface VisualProperties.
	 * Note : METHOD RELY ON THE INTERFACE LOCATION.
	 *  	  DO NOT CHANGE IT'S LOCATION WITHOUT UPDATING THIS METHOD !.
	 */
	public static void exchangeVisualPropertiesWith(
		VisualPropertiesExchanger bean,
		VisualProperties propExchanger)
	{
		// Reflect the interface.
		try
		{
			// When moving the interface change this code ! :
			Class theInterface = Class.forName("mataf.data.VisualProperties");

			Method[] getterMethods = theInterface.getMethods();
			Method[] setterMethods = new Method[getterMethods.length];

			String getterMethodName;
			String setterMethodName;

			for (int i = 0; i < getterMethods.length; i++)
			{
				getterMethodName = getterMethods[i].getName();
				if (!isGetterMethod(getterMethodName))
					continue;
				setterMethodName = getSetterName(getterMethodName);

				// Invoke setters on the component.

				// Get the setter method :

				// Special treatment for the focus method.
				if (setterMethodName.equals("requestFocus"))
				{
					if (propExchanger.shouldRequestFocus())
						(
							bean.getClass().getMethod(
								setterMethodName,
								null)).invoke(
							bean,
							null);
				}
				else // General case
					{
					setterMethods[i] =
						bean.getClass().getMethod(
							setterMethodName,
							new Class[] { getterMethods[i].getReturnType()});

					// Invoke the setter with the return value of the getter.
					try
					{
						Object[] returnedValue =
							new Object[] {
								 getterMethods[i].invoke(propExchanger, null)};
						setterMethods[i].invoke(bean, returnedValue);
					}
					catch (Exception e)
					{
						GLogger.warn(e.getMessage());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Method removes the 'is'/'get' from the methods names and prepends
	 * 'set'.
	 */
	private static String getSetterName(String getter)
	{
		// Removes the 'is' from the method name and prepends 'set'.
		if (getter.startsWith("is"))
			return "set" + getter.substring(2);
		// Removes the 'get' from the method name and prepends 'set'.
		if (getter.startsWith("get"))
			return "set" + getter.substring(3);
		// Special treatment for the focus method.
		if (getter.equals("shouldRequestFocus"))
			return "requestFocus";

		throw new IllegalArgumentException(
			"Method "
				+ getter
				+ " in interface VisualPropertiesExchanger Do Not Match the getXXX Format!");
	}

	/**
	 * Verify that the method is a 'getter' type.
	 */
	private static boolean isGetterMethod(String getterMethodName)
	{
		return getterMethodName.startsWith("get")
			|| getterMethodName.startsWith("is")
			|| getterMethodName.startsWith("should");
	}

	//////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Closes all the views specified by thier corresponding ID's in
	 * the array.
	 */
	public static void closeViews(String[] viewsID)
	{
		//TaskButton taskButton = null;
		try
		{
			Desktop.getDesktop().getTaskArea().getCurrentTask().closeTask();
		}
		catch (NullPointerException e)
		{
			// Could happend when executing parts of the application 
			// without the desktop.(getDesktop()==null)
			e.printStackTrace();
		}

		//		if(taskButton instanceof DSETaskButton)
		//		{
		//			DSETaskButton dseTaskButton = (DSETaskButton)taskButton;			
		//			
		//			for(int i=0;i<viewsID.length;i++)
		//			{
		//				System.out.print("Closing view : "+viewsID[i]+"...");
		//				try
		//				{
		//					DSENavigationController navigator = 
		//						(DSENavigationController) dseTaskButton.getNavigationController();
		//					DSECoordinatedPanel view = (DSECoordinatedPanel) navigator.getViewInstance(viewsID[i]);
		//					view.close(true);
		//					//navigator.closeView(viewsID[i]);
		//				}
		//				catch(NullPointerException e) 
		//				{
		//					e.printStackTrace();
		//					// Stupid Exception that is genereated becuase the TaskArea
		//					// is looking for a component with the name "ExitButton"
		//					// and fails.
		//				}
		//			/*	SpInternalFrame iFrame = dseTaskButton.getInternalFrame();
		//				System.out.println("View's InternalFrame = "+iFrame);
		//				iFrame.dispose();
		//				Desktop.getDesktop().getWorkingArea().removeFrame(iFrame);*/
		//				System.out.println("Closed.");
		//			}
		//		}
	}


	/**
	 * 
	 * This method 'filters' the IC icoll2set by the 
	 * arrayOfElementsNamesInKColl and inserts the filtered IC into
	 * the filteredIC IC.
	 *
	 * @param completeIC
	 * @param filteredIC
	 * @param kcollName
	 * @param filterKeys
	 * @throws DSEObjectNotFoundException
	 * @throws DSEInvalidArgumentException
	 * @throws IOException
	 */ 
	public static void filterIC(
		IndexedCollection completeIC,
		IndexedCollection filteredIC,
		String kcollName,
		String[] filterKeys)
		throws DSEObjectNotFoundException, DSEInvalidArgumentException, IOException
	{
		filteredIC.removeAll();
		
		// Create the template empty KeyedCollection.
		KeyedCollection kCollTemplate =
						(KeyedCollection)DataElement.readObject(kcollName);
		for (int row = 0; row < completeIC.size(); row++)
		{
			KeyedCollection kCollFromTable =
				(KeyedCollection)completeIC.getElementAt(row);
			KeyedCollection kColl2add;
			try
			{
				kColl2add = (KeyedCollection)kCollTemplate.clone();
			}
			catch(CloneNotSupportedException e)
			{
				e.printStackTrace();
				return;
			}
				
			for (int i = 0; i < filterKeys.length; i++)
			{
				kColl2add.setValueAt(filterKeys[i], kCollFromTable.getValueAt(filterKeys[i]));
			}
			filteredIC.addElement(kColl2add);
		}
	}
	
	/**
	 * Debug purposes.
	 */	
	public static void showComponentInputMap(JComponent c)
	{
		showInputMap(c.getInputMap());
	}
	
	public static void showInputMap(InputMap iMap)
	{
		if(iMap==null)
		{
			System.out.println("Got a null InputMap!");
		}
		else
		{
			System.out.println("InputMap : ");
			KeyStroke[] strokes = iMap.allKeys();
			if(strokes==null)
			{
				System.out.println("EMPTY"); 
			}
			else
			{
				for(int i=0;i<strokes.length;i++)
					System.out.println(i+"."+strokes[i]+" --> "+iMap.get(strokes[i]));
			}
		}
	}
	
	/**
	 * Debug purposes.
	 */	
	public static void showComponentActionMap(JComponent c)
	{
		showActionMap(c.getActionMap());
	}
	
	public static void showActionMap(ActionMap aMap)
	{
		if(aMap==null)
		{
			System.out.println("Got a null ActionMap!");
		}
		else
		{
			System.out.println("ActionMap :");
			Object[] actions = aMap.allKeys();
			if(actions==null)
			{
				System.out.println("EMPTY");				
			}
			else
			{
				for(int i=0;i<actions.length;i++)
					System.out.println(i+"."+actions[i]+" --> "+aMap.get(actions[i]));
			}
		}
	}
	
	/**
	 * Gets the InternalFrame of a component.
	 * 
	 * @param c A component that is placed inside an InternalFrame.
	 * @return
	 */
	public static JInternalFrame getInternalFrame(Component c)
	{
		while(!(c instanceof JInternalFrame))
		{
			c = c.getParent();
			if(c==null)
			 throw new IllegalArgumentException("Component is not a child of JInternalFrame.");
		}
		return (JInternalFrame)c;
	}
}
