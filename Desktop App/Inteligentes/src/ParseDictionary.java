import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class ParseDictionary {
	
public static void main(String args[]){
	JOptionPane.showMessageDialog(null, "Analisis por diccionario del usuario "+Main.comboBox.getSelectedItem());
	analisis();
}

public static void analisis(){
	String sql2 = "SELECT Usu_Comentario FROM usuario WHERE Usu_Nombre="+"'"+Main.comboBox.getSelectedItem()+"'";
	System.out.println(sql2);
	try{
		ConexionSQL conne = new ConexionSQL();
		conne.conectar();
		Statement c =ConexionSQL.psql=ConexionSQL.conn.prepareStatement(sql2); //Cambio de variables debido a problemas de conexion y traslape de variables
		ResultSet d = ConexionSQL.rs = ConexionSQL.psql.executeQuery();

		while(d.next()){
			System.out.println(d.getString("Usu_Comentario"));
		}
		c.close();

	}catch(SQLException e){
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error de conexion a la Base de Datos, intenta nuevamente");
	}
}
	

/*public void Lee(){
	File archivo = null;
	try {
	archivo = new File("archivo.txt");//"archivo.txt" es el archivo que va a leer
	String linea;
	FileReader fr = new FileReader (archivo);
	BufferedReader br = new BufferedReader(fr);
	int i,j,a=0;
	while((linea=br.readLine())!=null) {
	for(i=0;i<linea.length();i++)
	{if(i==0)
	   {if(linea.charAt(i)!=' ')
	    a++;
	   }
	   else
	   {if(linea.charAt(i-1)==' ')
	     if(linea.charAt(i)!=' ')	
	       a++;
	    
	   }	
	}
	}

	System.out.println("son "+a+" palabras");

	fr.close();
	}
	catch(IOException a){
	System.out.println(a);
	}
}*/	
	
	
}
