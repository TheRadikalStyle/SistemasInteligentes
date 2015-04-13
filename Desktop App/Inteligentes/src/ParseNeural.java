import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.googlecode.fannj.Fann;

public class ParseNeural {
	public static void main(){
	//System.setProperty("jna.library.path", ["C:\\Users\\David\\Downloads\\FAN\\bin\\fannfloat.dll"]);
		Main.labelImagen.setIcon(new ImageIcon("img\\Cortana_Think.gif"));
		JOptionPane.showMessageDialog(null, "Analisis por red neuronal del usuario "+Main.comboBox.getSelectedItem());
		
		System.setProperty("jna.library.path", "C:\\Users\\David\\Downloads\\FANN\\bin\\");
		System.out.println( System.getProperty("jna.library.path") ); //maybe the path is malformed
		File file = new File(System.getProperty("jna.library.path") + "fannfloat.dll");
		System.out.println("Is the dll file there:" + file.exists());
		System.load(file.getAbsolutePath());
		
		Fann fann = new Fann( "C:\\Users\\David\\Desktop\\sunspots.net" );
	    float[] inputs = new float[]{ 0, 1 };
	    float[] outputs = fann.run( inputs );
	    fann.close();
	    
	    for (float f : outputs) {
	        System.out.print(f + ",");
	    }
	    
	    Main.labelImagen.setIcon(new ImageIcon("img\\Cortana.gif"));
	}	
}
