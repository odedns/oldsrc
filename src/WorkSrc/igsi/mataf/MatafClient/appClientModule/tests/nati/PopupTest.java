package tests.nati;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mataf.desktop.beans.MatafMenu;
import mataf.desktop.beans.MatafMenuItem;
import mataf.desktop.beans.MenuItemMouseListener;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (17/05/2004 19:52:31).  
 */
public class PopupTest extends JFrame
{

	/**
	 * @throws java.awt.HeadlessException
	 */
	public PopupTest() throws HeadlessException
	{
		super("Testing Popup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		
		MatafMenu menu1 = new MatafMenu("File");
		MatafMenuItem item1 = new MatafMenuItem("Load");
		MatafMenuItem item2 = new MatafMenuItem("Save");
		MatafMenuItem item3 = new MatafMenuItem("Exit");
		menu1.add((Object)item1);
		menu1.add((Object)item2);
		menu1.add((Object)item3);
		
		MatafMenu menu2 = new MatafMenu("Edit");
		MatafMenuItem item4 = new MatafMenuItem("Copy");
		MatafMenuItem item5 = new MatafMenuItem("Cut");
		MatafMenuItem item6 = new MatafMenuItem("Paste");
		
		MatafMenu menu3 = new MatafMenu("View");
		MatafMenuItem item7 = new MatafMenuItem("Add View");
		MatafMenuItem item8 = new MatafMenuItem("Remove View");
		MatafMenuItem item9 = new MatafMenuItem("Properties");
		menu3.add((Object)item7);
		menu3.add((Object)item8);
		menu3.add((Object)item9);
		
		menu2.add((Object)item4);		
		menu2.add((Object)item5);
		menu2.add((Object)item6);
		menu2.add((Object)menu3);
		
		MatafMenu menu4 = new MatafMenu("Personal Menu");
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu4);
		
		setJMenuBar(menuBar);
	}

	
	public static void main(String[] args)
	{
		PopupTest pt = new PopupTest();
		pt.setSize(800,600);
		pt.setVisible(true);
	}
}