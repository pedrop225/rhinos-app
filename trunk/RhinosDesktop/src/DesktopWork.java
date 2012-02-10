import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
					fillRhPanelClients();
				}
			}
		});
		
		rh.setRhPanel(rhPanel);
		
		rh.setVisible(true);
		log.setVisible(true);
	}
	
	private void fillRhPanelClients() {
		
		for (Client c : mySql.getClients(App.user)) {
			Object [] d = new Object[6];
			d[0] = new String(c.getId().toString());
			d[1] = new String(c.getName());
			d[2] = new String(c.getTlf_1());
			d[3] = new String(c.getTlf_2());
			d[4] = new String(c.getMail());
			d[5] = new Integer(0);
			
			rhPanel.addTableData(d);
		}
	}
}