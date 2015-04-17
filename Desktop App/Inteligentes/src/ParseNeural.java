import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.googlecode.fannj.Fann;

public class ParseNeural {
	static ParseDictionary PD = new ParseDictionary();
	static int x, y;
	public static void main(){
		Main.labelImagen.setIcon(new ImageIcon("img\\Cortana_Think.gif"));
		JOptionPane.showMessageDialog(null, "Analisis por red neuronal del usuario "+Main.comboBox.getSelectedItem());
		
		System.setProperty("jna.library.path", "C:\\Users\\David\\Downloads\\FANN\\bin\\"); //Right click for unlock the file on the system
		System.out.println( System.getProperty("jna.library.path") ); //maybe the path is malformed
		File file = new File(System.getProperty("jna.library.path") + "fannfloat.dll");
		System.out.println("Is the dll file there:" + file.exists());
		System.load(file.getAbsolutePath());
		
		dataObtaining();
		neural();
		resetValues();
	    
	   // Main.labelImagen.setIcon(new ImageIcon("img\\Cortana.gif"));
	}
	
	public static void dataObtaining(){ //Saving code lines calling functions from ParseDictionary
		PD.dataObtain();
		PD.dataRecolection();
		PD.analisis_lvl1();
		PD.analisis_lvl2();
		PD.analisis_lvl3();
		PD.resultados();
	}
	
	public static void neural(){

		try{
			Fann fann = new Fann( "C:\\Users\\David\\Desktop\\ms.net" );
		    float[] inputs = new float[]{ 1, -1 };
		    float[] outputs = fann.run( inputs );
		    fann.close();
		    
		    for (float f : outputs) {
		        System.out.print(f + ",");
		    }
		}catch(Exception e){
			System.out.println("Error " +e.getMessage());
		}
	}
	
	public static void resetValues(){
		PD.resetingValues();
	}
	
}
