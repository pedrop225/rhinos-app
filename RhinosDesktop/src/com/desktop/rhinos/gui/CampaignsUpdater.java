package com.desktop.rhinos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.structures.tree.RhTree;

@SuppressWarnings("serial")
public class CampaignsUpdater extends JDialog {

	private JPanel contentPane;
	private JTextField textField;
	private JTree tree;
	
	private RhTree<String> tModel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CampaignsUpdater frame = new CampaignsUpdater();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CampaignsUpdater() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1, BorderLayout.NORTH);
		
		textField = new JTextField("http://www.pedroapv.com/rhino/campaigns/campaigns_repository.cfg");
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setFont(App.DEFAULT_FONT);
		panel_1.add(textField);
		textField.setColumns(45);
		
		tModel = new RhTree<String>();
		updateCampaignsTree();
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MySqlConnector.getInstance().clearCampaigns();

					URL url = new URL(textField.getText().trim());
					BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
								
					String l;
					while ((l = bf.readLine()) != null) {
						Campaign camp = new Campaign();
						camp.load(new URL(l));
						
						MySqlConnector.getInstance().addCampaign(camp);
					}
					updateCampaignsTree();

					bf.close();
					textField.setForeground(Color.BLACK);
				} 
				catch (Exception e) {
					textField.setForeground(Color.RED);
				}
			}
		});
		panel_1.add(btnNewButton);
		panel.add(new JScrollPane(tree), BorderLayout.CENTER);
		setModal(true);
		setLocationRelativeTo(null);
	}
	
	private void updateCampaignsTree() {
		tModel.setRoot("Campañas");
		tree = new JTree(tModel.getRoot());
		
		ArrayList<Campaign> campaigns = MySqlConnector.getInstance().getCampaigns(App.user);
		
		for (Campaign camp : campaigns) {
			tModel.add(tModel.getRoot().getData(), camp.toString());
			for (Service s : camp.getServices().values()) {
				tModel.add(camp.getName(), s.toString());
			}
		}
		tree.updateUI();
	}
}
