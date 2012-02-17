import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.android.rhinos.gest.Client;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Logger;
import com.desktop.rhinos.gui.RhFrame;
import com.desktop.rhinos.gui.RhPanel;


public class DesktopWork {
	
	private RhFrame rh;
	private RhPanel rhPanel;
	private Logger log;
	private MySqlConnector mySql;
	
	public DesktopWork() {
		rh = new RhFrame();
		rhPanel = new RhPanel();
		log = new Logger(rh);
		mySql = new MySqlConnector();
		
		log.getAcceptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	

				if (mySql.login(log.getUserString(), log.getPasswordString())) {
					log.setVisible(false);
					rhPanel.updateClientsData(importMySqlClients());
				}
			}
		});
		
		rh.setRhPanel(rhPanel);
		
		rh.setVisible(true);
		log.setVisible(true);
	}
	
	private Object [][] importMySqlClients() {
		ArrayList<Client> c = mySql.getClients(App.user);
		Object [][] d = new Object[c.size()][6];
		
		for (int i = 0; i < c.size(); i++) {
			d[i][0] = new String(c.get(i).getId().toString());
			d[i][1] = new String(c.get(i).getName());
			d[i][2] = new String(c.get(i).getTlf_1());
			d[i][3] = new String(c.get(i).getTlf_2());
			d[i][4] = new String(c.get(i).getMail());
			d[i][5] = new Integer(0);
		}
		return d;
	}
}