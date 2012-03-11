import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFrame;

import com.adobe.acrobat.Viewer;
import com.desktop.rhinos.gui.RhFrame;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class RhinosDesktop {

	public static void main(String[] args) {
		
		try {
			createPdf("peter.pdf");
		}
		catch (Exception e){};
		
		RhFrame f = new RhFrame();
		f.setVisible(true);
		f.getLogger().setVisible(true);
	}
	
    public static void createPdf(String filename)
	throws Exception {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("Hello World!"));
        document.add(new Paragraph("Hello World!"));
        // step 5
        document.close();
        
        JFrame f = new JFrame();
        Viewer v = new Viewer();
        v.setDocumentInputStream(new FileInputStream(new File("peter.pdf")));
        f.getContentPane().setLayout(new BorderLayout());
        f.add(v);
        
        f.pack();
        f.setVisible(true);
        v.activate();
    }
}
