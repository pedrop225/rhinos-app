package com.desktop.rhinos.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Util {
	
	public static JPanel packInJP(Component...o) {
		JPanel r = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (Component c : o)
			r.add(c);
				
		return r;
	}
	
	public static JPanel packInJP( LayoutManager lm, Component...o) {
		JPanel r = new JPanel(lm);
		for (Component c : o)
			r.add(c);
				
		return r;
	}
}
