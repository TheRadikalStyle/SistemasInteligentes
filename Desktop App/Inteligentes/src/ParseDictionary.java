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
import java.util.StringTokenizer;

import javax.swing.JOptionPane;


public class ParseDictionary {

	static ArrayList<String> datosBD = new ArrayList<String>();
	/************************START DICTIONARY DEFINITION**************************************************************/
	private static String Dic_Lvl1 = "tonto,tonta,sonso,sonsa,zonzo,zonza,tontita,tontito,wey,guey,invecil,imbecil,inbecil,imbecil,morro";
	private static String[] level1 = Dic_Lvl1.split(",");
	private static String Dic_Lvl2 = "idiota,pendejo,estupido,estupida,pendeja,madre,culero,culera,cabron,mierda,ojete,hdp,hijaputa,hijoputa,pinche,inche,inchi,inshi,pinki,maldita,maldito,caca,caka,kk,mojon,joder,mta,pu�etas,pu�os";
	private static String[] level2 = Dic_Lvl2.split(",");
	private static String Dic_Lvl3 = "perra,golfa,puto,puta,marica,verga,vergon,vergota,vergotas,vergas,chingada,cojer,coger,chupa,chupame,huevos,huebos,uevos,uebos,guebos,guevos,culo,kulo,prosti,prostituta,bulto,concha,cuca,webos,weboz,pelame,pelotas,cojones,negro,indigena,indio,india,nigga,pene,viola,violar,violare,sexo,felacion,tetas,tetona,tetotas,pito";
	private static String[] level3 = Dic_Lvl3.split(",");
	/************************FINISH DICTIONARY DEFINITION**************************************************************/	
	//private static String[] dataSeparated;

	public static int noGoodWords1 = 0; //Almacen numerico de palabras ofensivas por nivel
	public static int noGoodWords2 = 0;
	public static int noGoodWords3 = 0;

	static ArrayList<String> noGoodWordsArray1 = new ArrayList<String>(); //Almacen de palabras ofensivas por nivel
	static ArrayList<String> noGoodWordsArray2 = new ArrayList<String>();
	static ArrayList<String> noGoodWordsArray3 = new ArrayList<String>();

	public static int TotalWords = 0;

	static ArrayList<String> BDData = new ArrayList<String>();
	static ArrayList<String> dataSeparated = new ArrayList<String>();

	public static void main(String args[]){
		JOptionPane.showMessageDialog(null, "Analisis por diccionario del usuario "+Main.comboBox.getSelectedItem());
		dataObtain();
		dataRecolection();
		analisis_lvl1();
		analisis_lvl2();
		analisis_lvl3();
		resultados();
		BDData.clear(); //Delete data for prevent bad results
		datosBD.clear();
		dataSeparated.clear();
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
		/*for (int i = 0; i < level1.length; i++) { //Recorre los datos del Diccionario1 [TESTING]       
		System.out.println(level1[i]);
	}*/

		for(int i = 0;i<datosBD.size();i++){ //Recorre ArrayList de los datos de la BD
			//System.out.println(datosBD.get(i)); [TESTING]
			BDData.add(datosBD.get(i));
		}

		for(int u = 0; u < BDData.size(); u++){
			//System.out.println(u);
			String dat = BDData.get(u);                                            

			StringTokenizer st = new StringTokenizer(dat); //Separa por tokens (palabras)
			TotalWords = TotalWords + st.countTokens(); //Conteo de palabras
			//String[] dato =  dat.split(" ");
			while(st.hasMoreTokens()){
				dataSeparated.add(st.nextToken());
			}
		}

		//System.out.println("DataSeparated contiene "+dataSeparated.size());
		/*for (int i = 0; i < dataSeparated.size(); i++) { //Recorre datos de la BD separados [TESTING]
		 System.out.println(dataSeparated.get(i));
	}*/

	}

	public static void analisis_lvl1(){ //Funcion de analisis del diccionario 1
		for(String datos: dataSeparated){ // (For each), por cada string obtenido desde la base de datos va a hacer
			for( int p = 0; p < level1.length; p++){ //Por cada palabra del diccionario vas a analizar
				if(datos.equals(level1[p])){ //Si corresponden (son iguales) ejecuta
					noGoodWords1 = noGoodWords1 + 1; //Sumar el numero de palabras encontradas
					noGoodWordsArray1.add(level1[p]); //Agregar a un arrayList las palabras encontradas
				}
			}
		}
	}

	public static void analisis_lvl2(){ //Funcion de analisis del diccionario 2
		for(String datos: dataSeparated){ // (For each), por cada string obtenido desde la base de datos va a hacer
			for( int p1 = 0; p1 < level2.length; p1++){ //Por cada palabra del diccionario vas a analizar
				if(datos.equals(level2[p1])){ //Si corresponden (son iguales) ejecuta
					noGoodWords2 = noGoodWords2 + 1; //Sumar el numero de palabras encontradas
					noGoodWordsArray2.add(level2[p1]); //Agregar a un arrayList las palabras encontradas
				}
			}
		}
	}

	public static void analisis_lvl3(){ //Funcion de analisis del diccionario 3
		for(String datos: dataSeparated){ // (For each), por cada string obtenido desde la base de datos va a hacer
			for( int p2 = 0; p2 < level3.length; p2++){ //Por cada palabra del diccionario vas a analizar
				if(datos.equals(level3[p2])){ //Si corresponden (son iguales) ejecuta
					noGoodWords3 = noGoodWords3 + 1; //Sumar el numero de palabras encontradas
					noGoodWordsArray3.add(level3[p2]); //Agregar a un arrayList las palabras encontradas
				}
			}
		}
	}

	public static void resultados(){
		System.out.println("Palabras Totales: "+TotalWords);
		System.out.println("Palabras ofensivas de nivel 1: " +noGoodWords1);
		System.out.println("Palabras ofensivas de nivel 2: " +noGoodWords2);
		System.out.println("Palabras ofensivas de nivel 3: " +noGoodWords3);
		int sum  = noGoodWords1 + noGoodWords2 + noGoodWords3;
		System.out.println("El usuario dijo un total de "+sum +" palabras altisonantes de un total de " +TotalWords+ " palabras");
		System.out.println("**************************************************************************************************\n");
		String userBad = "";

		if(noGoodWords1 > noGoodWords2){
			userBad = "El usuario "+Main.comboBox.getSelectedItem()+" se ha clasificado en el NIVEL 1 de agresividad";
		}else if(noGoodWords2 >noGoodWords3){
			userBad = "El usuario "+Main.comboBox.getSelectedItem()+" se ha clasificado en el NIVEL 2 de agresividad";   //  <---- Simple, pero funcional a mis propositos
		}else if(noGoodWords3 > noGoodWords1){
			userBad = "El usuario "+Main.comboBox.getSelectedItem()+" se ha clasificado en el NIVEL 3 de agresividad";
		}else{
			userBad = "El usuario "+Main.comboBox.getSelectedItem()+" NO cuenta con registros de agresividad";
		}

		System.out.println(userBad);
		System.out.println("**************************************************************************************************\n");
		/*for(int y = 0; y < noGoodWordsArray1; y++){
	System.out.println(noGoodWordsArray1[y]);
	}*/
		TotalWords = 0;
		noGoodWords1 = 0;
		noGoodWords2 = 0;
		noGoodWords3 = 0;
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