/*
 * Autor: David Ochoa Gutierrez
 * Project name: Ghost Freak
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class ParseDictionary {
	
	static ArrayList<String> datosBD = new ArrayList<String>();

	private static String Dic_Lvl1 = "tonto,tonta,sonso,sonsa,zonzo,zonza,tontita,tontito,wey,guey,invecil,imbecil,inbecil,imbecil,morro";
	private static String[] level1 = Dic_Lvl1.split(",");
	private static String Dic_Lvl2 = "idiota,pendejo,estupido,estupida,pendeja,madre,culero,culera,cabron,mierda,ojete,hdp,hijaputa,hijoputa,pinche,inche,inchi,inshi,pinki,maldita,maldito,caca,caka,kk,mojon,joder,mta,puñetas,puños";
	private static String[] level2 = Dic_Lvl2.split(",");
	private static String Dic_Lvl3 = "perra,golfa,puto,puta,marica,verga,vergon,vergota,vergotas,vergas,chingada,cojer,coger,chupa,chupame,huevos,huebos,uevos,uebos,guebos,guevos,culo,kulo,prosti,prostituta,bulto,concha,cuca,webos,weboz,pelame,pelotas,cojones,negro,indigena,indio,india,nigga,pene,viola,violar,violare,sexo,felacion,tetas,tetona,tetotas,pito";
	private static String[] level3 = Dic_Lvl3.split(",");
	
	private static String[] dataSeparated;
	
public static void main(String args[]){
	JOptionPane.showMessageDialog(null, "Analisis por diccionario del usuario "+Main.comboBox.getSelectedItem());
	dataObtain();
	dataRecolection();
	//analisis();
}

public static void dataObtain(){
	String sql2 = "SELECT Usu_Comentario FROM usuario WHERE Usu_Nombre="+"'"+Main.comboBox.getSelectedItem()+"'";
	System.out.println(sql2);
	try{
		ConexionSQL conne = new ConexionSQL();
		conne.conectar();
		Statement c =ConexionSQL.psql=ConexionSQL.conn.prepareStatement(sql2); //Cambio de variables debido a problemas de conexion y traslape de variables
		ResultSet d = ConexionSQL.rs = ConexionSQL.psql.executeQuery();

		while(d.next()){
			datosBD.add(d.getString("Usu_Comentario"));
			//System.out.println(d.getString("Usu_Comentario"));
		}
		c.close();

	}catch(SQLException e){
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error de conexion a la Base de Datos, intenta nuevamente");
	}
}

public static void dataRecolection(){
	for (int i = 0; i < level1.length; i++) { //Recorre Diccionario
		System.out.println(level1[i]);
	}
	
	for(int i = 0;i<datosBD.size();i++){ //Recorre ArrayList
        System.out.println(datosBD.get(i));
        
        String BDData = datosBD.get(i); //Obtiene los datos de la BD y separa las oraciones en palabras separadas por espacios
    	dataSeparated = BDData.split(" ");
	}
	for (int i = 0; i < dataSeparated.length; i++) { //Recorre datos
		System.out.println(dataSeparated[i]);
	}
}

/*public static void analisis(){
	for(String datos: dataSeparated){
		if(datos.equals()){
			
		}
	}
}*/
	

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
