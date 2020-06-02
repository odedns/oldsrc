package tests.nati;

import javax.swing.JMenuItem;

import mataf.desktop.beans.MenuItemMouseListener;

public class TestMenuItem extends JMenuItem
{
	public TestMenuItem(String text)
	{
		super(text);
		addMouseListener(new MenuItemMouseListener());
	}
	
	public void addToPersonalMenu()
	{
		System.out.println("Adding "+this);
	}
}