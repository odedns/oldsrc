package com.ness.fw.shared.ui;

import java.util.Random;

public class MenuGeneratorRandom
{

	public static final String[] MENU_TEXTS =
		{
			"לקוחות",
			"מוצרים",
			"קטלוג מוצרים",
			"איתור לקוחות בעייתיים",
			"מבצעי ביטוח חיים",
			"בריאות",
			"פנסיה",
			"סימולציה להצעות מחיר",
			"ייעוץ פיננסי להשקעות",
			"חיתום ידני",
			"חיתום רפואי",
			"הסכמים",
			"הפקת פוליסה מרכזת",
			"תור עבודה",
			"שולחן עבודה",
			"טיפול מיוחד באחמים",
			"טבלאות אקטואריה",
			"מחירוני כיסויים",
			"תכניות חדשות",
			"נהול קשרי לקוחות",
			"מערכת עזרה מקוונת",
			"ניהול יעדי סוכנים",
			"מבנה ארגוני מחוזות",
			"אלפון סוכנים",
			"מאגר מקצועות מסוכנים",
			"בניית חוקים במערכת החוקה",
			"חוק ההסדרים הלכה למעשה",
			"איתור צווארי בקבוק",
			"ניתור תהליכים פתוחים",
			"מערכת תמיכה טכנית" };
	private Random rnd = null;
	
	public MenuGeneratorRandom(int addition){
		rnd = new Random(System.currentTimeMillis() + addition);
	}

	public String generate(String url)
	{
		StringBuffer menu = new StringBuffer(512);
		int mainItems = rnd.nextInt(15) + 1;
		for (int i = 0; i < mainItems; i++)
		{
			int r = rnd.nextInt(MENU_TEXTS.length);
			menu.append(
				"[null, \""
					+ MENU_TEXTS[r]
					+ "\", \""
					+ url
					+ "\", \"_self\", null");
			if (rnd.nextInt(3) == 0)
			{
				menu.append(",\n");
				int menuItems = rnd.nextInt(8) + 1;
				for (int j = 0; j < menuItems; j++)
				{
					int r2 = rnd.nextInt(MENU_TEXTS.length);
					menu.append(
						"\t[null, \""
							+ MENU_TEXTS[r2]
							+ "\", \""
							+ url
							+ "\", \"_self\", null]");
					menu.append((j == menuItems - 1) ? "\n" : ",\n");
				}
			}
			if (i == mainItems - 1)
			{
				menu.append("]\n");
			}
			else
			{
				menu.append("],\n");
				if (rnd.nextInt(4) == 0)
				{
					menu.append("_cmSplit,\n");
				}
			}
		}
		return menu.toString();
	}

	public static void main(String[] args)
	{
		MenuGeneratorRandom mg = new MenuGeneratorRandom(7);
		System.out.println(mg.generate("javascript:alert('test')"));
	}

}
