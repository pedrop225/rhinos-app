import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.android.rhinos.gest.Client;
import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.connector.MySqlConnector.App;
import com.desktop.rhinos.gui.Logger;
import com.desktop.rhinos.gui.RhFrame;

public class DesktopWork {
	
	private RhFrame rh;
	private Logger log;
	private MySqlConnector mySql;
	
	public DesktopWork() {
		rh = new RhFrame();
		log = new Logger(rh);
		mySql = MySqlConnector.getInstance();
		
		rh.setUpdateAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rh.setClientsTableData(importMySqlClients());
				rh.validate();
			}
		});
		
		log.getAcceptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	

				if (mySql.login(log.getUserString(), log.getPasswordString())) {
					log.setVisible(false);
					rh.showUserBanner();
					rh.setClientsTableData(importMySqlClients());
					rh.validate();					
				}
			}
		});
		
		rh.setVisible(true);
		log.setVisible(true);
	}
	
	private Object [][] importMySqlClients() {
		ArrayList<Client> c = mySql.getClients(App.user);
		Object [][] d = new Object[c.size()][4];
		
		for (int i = 0; i < c.size(); i++) {
			d[i][0] = new String(c.get(i).getId().toString());
			d[i][1] = new String(c.get(i).getName());
			d[i][2] = new String(c.get(i).getTlf_1());
			d[i][3] = new String(c.get(i).getMail());
		}
		return d;
	}
}