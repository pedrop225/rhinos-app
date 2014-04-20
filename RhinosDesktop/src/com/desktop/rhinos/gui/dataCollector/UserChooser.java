package com.desktop.rhinos.gui.dataCollector;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.structures.tree.RhTree;

public class UserChooser extends JDialog {
	
	private static final int WIDTH = 480;
	private static final int HEIGHT = 360;
	private static final String TITLE = "Users Hierarchy";
	
	private static final long serialVersionUID = 1L;
	
	private JPanel treePanel;
	private JTree userTree;

	public UserChooser() {
		init();
	}
	
	private void init() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setIconImage(new ImageIcon(UserChooser.class.getResource("/icons/User/User_16x16.png")).getImage());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		treePanel = new JPanel(new BorderLayout());
		add(new JScrollPane(treePanel));
		
		setUser(App.user);

		setLocationRelativeTo(null);
	}
	
	private void setUser(User user) {
		ArrayList<ArrayList<User>> st = MySqlConnector.getInstance().getUserStructure(user);
		
		RhTree<User> t = new RhTree<User>(user);
		for (int i = 0; i < st.size(); i++)
			for (int j = 1; j < st.get(i).size(); j++)
				t.add(st.get(i).get(0), st.get(i).get(j));
		
		userTree = new JTree(t.getRoot());
		userTree.setFont(App.DEFAULT_FONT);
		
		treePanel.add(userTree);
	}
}
