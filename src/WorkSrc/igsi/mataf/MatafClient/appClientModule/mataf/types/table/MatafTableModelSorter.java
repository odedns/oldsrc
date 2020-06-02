package mataf.types.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumn;

/**
 * This class listens for mouse clicks on the table's header and sorts
 * the table's model.
 * 
 * @author Nati Dikshtein.
 */
 
public class MatafTableModelSorter extends MouseAdapter
{	
	private static final ImageIcon	ascendingIcon;
	private static final ImageIcon  	decendingIcon;
	
	private boolean 	ascending;
	private MatafTable table;

	static
	{
		ascendingIcon = new ImageIcon(ClassLoader.getSystemResource("images/guibeans/SortAscending.gif"));
		decendingIcon = new ImageIcon(ClassLoader.getSystemResource("images/guibeans/SortDecending.gif"));
	}
	
	/**
	 * Creates a sorter with a reference to the table who's
	 * model is to be sorted.
	 * 
	 * @param table
	 */
	public MatafTableModelSorter(MatafTable table) 
	{
		this.table = table;
	}
	
	/**
	 * React to mouse clicks on the table header by sorting its column.
	 */	
	public void mouseClicked(MouseEvent e) 
	{
		if(table.getOurModel().isSortable())
		{
			int column = table.columnAtPoint(e.getPoint());
			if (column!=-1)
				sort(column);
		}
	}
	
	/**
	 * Method updates the sorting icon state and sorts the model.
	 * 
	 * @param table
	 * @param sortingColumn
	 */
	public void sort(int sortingColumn)
	{
		// Each click reverses the sorting order.
		ascending = !ascending;
				
		// Update the table about which column is used for sorting it's model.
		table.setSortingColumn(sortingColumn);
		
		int columnCount = table.getColumnCount();

		// Update column headers icon.
		for (int i=0; i<columnCount; i++) 
		{
			TableColumn tableColumn = table.getColumnModel().getColumn(i);
			MatafTableHeaderRenderer renderer = 
					(MatafTableHeaderRenderer) tableColumn.getHeaderRenderer();
			renderer.setIcon( (i==sortingColumn) ? getSortIcon() : null);
		}

		// Repaint headers.
		table.getTableHeader().repaint();

		// Sort!.
		sortByColumn(sortingColumn, ascending);
	}
	
	/**
	 * Returns the icon reprsenting the current sorting direction.
	 * 
	 * @return Icon the icon reprsenting the current sorting direction.
	 */
	private Icon getSortIcon()
	{
		return ascending ? ascendingIcon : decendingIcon;
	}				
	
	/** 
	 * Method used for table dynamic sorting.
	 */
	private void sortByColumn(int column, boolean ascending) 
	{
		MatafTableModel model = table.getOurModel();
		
		// Sort !.		
		Collections.sort(model.getRawTableData(), getRowsComparator(column, ascending));
	
		// Notify table about the change.
		model.fireTableDataChanged();
	}
	
	/**
	 * Generates the Comparator object for sorting the table's model.<p>
	 *	----------|------------------|-------------------|<p>
	 *	   		  | ascending = true | ascending = false |<p>
	 *	----------|------------------|-------------------|<p>
	 *	   x<y    |       -1         |         1         |<p>
	 *	----------|------------------|-------------------|<p>
	 *	   x=y    |        0         |         0         |<p>
	 *	----------|------------------|-------------------|<p>
	 *	   x>y    |        1         |        -1         |<p>
	 *	----------|------------------|-------------------|<p>
	 */
	private Comparator getRowsComparator(final int sortingColumn, final boolean ascending)
	{
		return new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				// Both elements are vectors representing a table row.
				Vector row1 = (Vector)o1;
				Vector row2 = (Vector)o2;
			
				// Use first element as the comparator.					
				Comparable e1 = (Comparable) row1.elementAt(sortingColumn);
				Object e2 = row2.elementAt(sortingColumn);

				// Check if the class type of the sorting column is a number.
				if(Number.class.isAssignableFrom(table.getColumnClass(sortingColumn)))
				{
					DecimalFormat decFormat = (DecimalFormat)DecimalFormat.getInstance();
					try
					{
						double x = Double.parseDouble(decFormat.parse(e1.toString()).toString());
						double y = Double.parseDouble(decFormat.parse(e2.toString()).toString());
				
						return x<y ? (ascending ? -1 : 1) : (x==y ? 0 : (ascending ? 1 : -1) ) ;
					}
					catch(ParseException e)
					{
						// Parsed an empty number.
						return -1;
					}
				}
				else
				{
					// Reverse the result according to sort order.
					int coef = ascending ? 1 : -1;
						
					// Handle the 'null cases' :
					
					// Both nulls - they are equal.
					if((e1==null) && (e2==null))
						return 0;
					
					// e1 is null, then e2 is greater.
					if(e1==null)
						return coef*-1;
					
					// e2 is null, then e1 is greater.
					if(e2==null)
						return coef*1;
						
					// Return the comparable result as our result.
					return coef*e1.compareTo(e2);
				}
			}
		};
	}	
}
