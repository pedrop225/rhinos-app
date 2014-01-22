import javax.swing.UIManager;

import com.desktop.rhinos.gui.RhFrame;
import com.desktop.rhinos.gui.RhLoader;

public class RhinosDesktop {

	public static void main(String[] args) {

		RhLoader load = new RhLoader();
		load.setVisible(true);
		
		try {
		    com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "");
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} 
		catch (Exception e) {}
			    
		RhFrame f = new RhFrame();
		load.setVisible(false);
		f.setVisible(true);
		f.getLogger().setVisible(true);
	}
}
