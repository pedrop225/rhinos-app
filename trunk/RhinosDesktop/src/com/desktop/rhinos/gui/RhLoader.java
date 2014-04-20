package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.desktop.rhinos.connector.MySqlConnector.App;

public class RhLoader extends JFrame {
	
	public static final int WIDTH = 450;
	public static final int HEIGHT = 342;
	
	private static final long serialVersionUID = 1L;

	public RhLoader() {
		setSize(WIDTH, HEIGHT);
		setUndecorated(true);
		setLocationRelativeTo(null);
			
		JPanel c = (JPanel)getContentPane();
		c.setLayout(new BorderLayout());
		
		LoaderImage iPanel = new LoaderImage(new ImageIcon(RhLoader.class.getResource("/icons/rhinos.png")).getImage());
		iPanel.setBorder(BorderFactory.createLineBorder(App.APP_GREEN, 5));
		
		JProgressBar pb = new JProgressBar(JProgressBar.HORIZONTAL);
		pb.setForeground(App.APP_GREEN);
		
		pb.setIndeterminate(true);
		
		c.add(iPanel);
		c.add(pb, BorderLayout.SOUTH);
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
		setBackground(new Color(57, 134, 90, 40));
		g2.drawImage(img, 0, 0, RhLoader.WIDTH, RhLoader.HEIGHT, null);
	}
}
