package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RhLoader extends JFrame {
	
	public static final int WIDTH = 450;
	public static final int HEIGHT = 342;
	
	private static final long serialVersionUID = 1L;

	public RhLoader() {
		setSize(WIDTH, HEIGHT);
		setUndecorated(true);
		setLocationRelativeTo(null);
		
		setOpacity(.9f);
	
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		LoaderImage iPanel = new LoaderImage(new ImageIcon(RhLoader.class.getResource("/icons/rhinos.png")).getImage());
		iPanel.setBorder(BorderFactory.createLineBorder(new Color(0x008000), 2));
		
		c.add(iPanel);
	}
}

class LoaderImage extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Image img;
	
	public LoaderImage(Image img) {
		this.img = img;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(img, 0, 0, RhLoader.WIDTH, RhLoader.HEIGHT, null);
	}
}
