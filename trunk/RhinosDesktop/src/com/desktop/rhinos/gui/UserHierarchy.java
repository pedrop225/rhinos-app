package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.android.rhinos.gest.User;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.structures.tree.RhNode;
import com.desktop.rhinos.structures.tree.RhTree;

public class UserHierarchy extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTree userTree;
	
	public UserHierarchy() {
		super(new BorderLayout());
		init();
	}
	
	private void init() {
		userTree = new JTree();
		userTree.setFont(App.DEFAULT_FONT);
		userTree.setSelectionRow(0);
		
		add(new JScrollPane(userTree));
	}
	
	public void setUser(User user) {
		ArrayList<ArrayList<User>> st = MySqlConnector.getInstance().getUserStructure(user);
		
		RhTree<User> t = new RhTree<User>(user);
		for (int i = 0; i < st.size(); i++)
			for (int j = 1; j < st.get(i).size(); j++)
				t.add(st.get(i).get(0), st.get(i).get(j));
		
		userTree = new JTree(t.getRoot());
		userTree.setSelectionRow(0);
		userTree.setFont(App.DEFAULT_FONT);

		add(new JScrollPane(userTree));
	}
	
	@SuppressWarnings("unchecked")
	public User getSelectedNode() {
		TreePath path = userTree.getSelectionPath();
		return (path != null) ? ((RhNode<User>)path.getLastPathComponent()).getData() : null;
	}
}
