package com.ibm.dse.monitor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

class BallIcon implements Icon {
	
	protected int w,h;
	protected int ofsX,ofsY;
	protected int fontSize;
	protected Color col;
	protected String str;

	public int getIconHeight() { return h; }
	public int getIconWidth() { return w; }

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2;
		Rectangle2D rect;
		int cx,cy;
		Font f;

		g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(col);
		g2.fillOval(x,y,w-1,h-1);
		g2.setColor(Color.black);
		g2.drawOval(x,y,w-1,h-1);
		g2.setColor(Color.white);
		f=new Font("dialog",Font.BOLD,fontSize);
		g2.setFont(f);
		rect=f.getStringBounds(str,g2.getFontRenderContext());
		cx=x+w/2;
		cy=y+h/2-1;
		cx-=rect.getWidth()/2;
		cy+=rect.getHeight()/2;
		g2.drawString(str,cx+ofsX,cy-2+ofsY);
	}
	
	protected BallIcon(Color col, String str) {
		this.col=col;
		this.str=str;
	}

	public static Icon getBallIcon(Color col, String str,
		int ww, int hh, int ox, int oy, int fs) {
		BufferedImage bi;
		Graphics2D g2;
		BallIcon icn;

		bi=new BufferedImage(ww,hh,BufferedImage.TYPE_INT_ARGB);
		g2=bi.createGraphics();
		icn=new BallIcon(col,str);
		icn.w=ww; icn.h=hh;
		icn.ofsX=ox; icn.ofsY=oy;
		icn.fontSize=fs;
		icn.paintIcon(null,g2,0,0);
		return new ImageIcon(bi);
	}
	
	public static Icon getBallIcon(Color col, String str) {
		return getBallIcon(col,str,16,16,0,0,12);
	}

}
