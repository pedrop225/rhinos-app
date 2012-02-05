import com.desktop.rhinos.connector.MysqlConnector;
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
		
		MysqlConnector conn = new MysqlConnector();
	}
}
