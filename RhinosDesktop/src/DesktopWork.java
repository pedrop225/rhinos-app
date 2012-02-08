import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.Logger;
import com.desktop.rhinos.gui.RhFrame;


public class DesktopWork {
	
	private RhFrame rh;
	private Logger log;
	private MySqlConnector mySql;
	
	public DesktopWork() {
		rh = new RhFrame();
		log = new Logger(rh);
		mySql = new MySqlConnector();
		
		log.getAcceptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				
				if (mySql.login(log.getUserString(), log.getPasswordString()))
					log.setVisible(false);
			}
		});
		
		rh.setVisible(true);
		log.setVisible(true);
	}
}
