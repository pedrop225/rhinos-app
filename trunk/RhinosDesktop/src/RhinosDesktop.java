import com.desktop.rhinos.connector.MySqlConnector;
import com.desktop.rhinos.gui.AddContract;
import com.desktop.rhinos.gui.Logger;
import com.desktop.rhinos.gui.RhFrame;

public class RhinosDesktop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RhFrame rh = new RhFrame();
		
		Logger log = new Logger(rh);
		log.setVisible(true);
		
		MySqlConnector conn = new MySqlConnector();
//		System.out.println(conn.login("pedroap", "register"));
		
		AddContract ac = new AddContract(rh);
		ac.setVisible(true);
	}
}
