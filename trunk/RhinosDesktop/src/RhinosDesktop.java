import javax.swing.UIManager;

import com.desktop.rhinos.gui.RhFrame;

public class RhinosDesktop {

	public static void main(String[] args) {

		try {
		    com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "");
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} 
		catch (Exception e) {}
		    
		RhFrame f = new RhFrame();
		f.setVisible(true);
		f.getLogger().setVisible(true);
	}
}
