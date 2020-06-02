package com.ness.fw.shared.ui;

public class MenuGenerator
{

	public static final String[] MENU_TEXTS =
		{
			"בצע",
		};

	public MenuGenerator(int addition){
	}

	public String generate(String url)
	{
		StringBuffer menu = new StringBuffer(512);

		menu.append("[null, \""	+ MENU_TEXTS[0]	+ "\", \"" + url + "\", \"_self\", null]\n");
		return menu.toString();
	}

	public static void main(String[] args)
	{
		MenuGenerator mg = new MenuGenerator(7);
		System.out.println(mg.generate("javascript:alert('test')"));
	}

}
