import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.googlecode.fannj.Fann;

public class ParseNeural {
	public static void main(){
	//System.setProperty("jna.library.path", ["C:\\Users\\David\\Downloads\\FAN\\bin\\fannfloat.dll"]);
		Main.labelImagen.setIcon(new ImageIcon("img\\Cortana_Think.gif"));
		JOptionPane.showMessageDialog(null, "Analisis por red neuronal del usuario "+Main.comboBox.getSelectedItem());
		
		Fann fann = new Fann( "C:\\Users\\David\\Downloads\\FAN\\bin\\" );
	    float[] inputs = new float[]{ -1, 1 };
	    float[] outputs = fann.run( inputs );
	    fann.close();
	    
	    Main.labelImagen.setIcon(new ImageIcon("img\\Cortana.gif"));
	}	
}
