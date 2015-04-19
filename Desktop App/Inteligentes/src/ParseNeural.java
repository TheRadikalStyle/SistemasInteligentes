import java.awt.Color;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.googlecode.fannj.Fann;

public class ParseNeural {
	static ArrayList<String> datosBD = new ArrayList<String>();
	/************************START DICTIONARY DEFINITION**************************************************************/
	private static String Dic_Lvl1 = "tonto,tonta,sonso,sonsa,zonzo,zonza,tontita,tontito,wey,guey,invecil,imbecil,inbecil,imbecil,morro";
	private static String[] level1 = Dic_Lvl1.split(",");
	private static String Dic_Lvl2 = "idiota,pendejo,estupido,estupida,pendeja,madre,culero,culera,cabron,mierda,ojete,hdp,hijaputa,hijoputa,pinche,pinches,inche,inchi,inshi,pinki,maldita,maldito,caca,caka,kk,mojon,joder,mta,pu�etas,pu�os";
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
	
	static String sql2 = "";
	
	
	public static void main(){
		Main.labelImagen.setIcon(new ImageIcon("img\\Cortana_Think.gif"));
		JOptionPane.showMessageDialog(null, "Analisis por red neuronal del usuario "+Main.comboBox.getSelectedItem());
		Main.textAreaBadWords.setText("");
		
		//TODO Aqui deberia haber un TRY/CATCH
		System.setProperty("jna.library.path", "C:\\Users\\David\\Downloads\\FANN\\FANN\\bin\\"); //Right click for unlock the file on the system
		System.out.println( System.getProperty("jna.library.path") ); //maybe the path is malformed
		File file = new File(System.getProperty("jna.library.path") + "fannfloat.dll");
		System.out.println("Is the dll file there:" + file.exists());
		System.load(file.getAbsolutePath());
		
		dataObtaining();
		dataRecolection();
		analisis_lvl1();
		analisis_lvl2();
		analisis_lvl3();
		resultados();
		//neural();
		resetingValues();
	}
	
	public static void dataObtaining(){ //Saving code lines calling functions from ParseDictionary
			if(Main.comboBox.getSelectedIndex() == 0){
				sql2 = "SELECT Usu_Comentario FROM usuario";
				System.out.println(sql2);
				Main.lblElUsuarioSe.setText("Analisis de datos completos");
			}else{
			sql2 = "SELECT Usu_Comentario FROM usuario WHERE Usu_Nombre="+"'"+Main.comboBox.getSelectedItem()+"'";
			System.out.println(sql2);
			}
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
					/*Conversion a minusculas*/
					String aMin = st.nextToken();
					aMin = aMin.toLowerCase(); 
					//System.out.println(aMin);
					
					/*Eliminaci�n de comas*/
					String delComma = aMin;
					delComma = delComma.replace(",", " ");
					
					/*Reemplazo de acentos... Puntos y parentesis*/
					if(delComma.contains("�")){ 
						delComma = delComma.replace("�", "a");
						System.out.println("Se cambio de la � a la a");
					}else if(delComma.contains("�")){
							delComma = delComma.replace("�", "e");
							System.out.println("Se cambio de la � a la e");
							}else if(delComma.contains("�")){
								delComma = delComma.replace("�", "i");
								System.out.println("Se cambio de la � a la i");
								}else if(delComma.contains("�")){
									delComma = delComma.replace("�", "o");
									System.out.println("Se cambio de la � a la o");
									}else if(delComma.contains("�")){
										delComma = delComma.replace("�", "u");
										System.out.println("Se cambio de la � a la u");
										}else if(delComma.contains(".")){
											delComma = delComma.replace(".", " ");
											System.out.println("Se cambio un . por un espacio");
											}else if(delComma.contains("(")){
												delComma = delComma.replace("(", " ");
												System.out.println("Se cambio de ( a un espacio");
												}else if(delComma.contains(")")){
													delComma = delComma.replace(")", " ");
													System.out.println("Se cambio de ) a un espacio");
													}else if(delComma.contains("!")){
														delComma = delComma.replace("!", " ");
														System.out.println("Se cambio de ! a un espacio");
														}else if(delComma.contains("�")){
															delComma = delComma.replace("�", " ");
															System.out.println("Se cambio de � a un espacio");
															}else if(delComma.contains("�")){
																delComma = delComma.replace("�", "u");
																System.out.println("Se cambio de � a una u");
																}
					
					/*Detector de caracteres especiales*/
//						Pattern p = Pattern.compile("[^a-z0-9 ]"); //TODO Esto falla, debe detectar toda el string no el inicial
//						Matcher m = p.matcher(delComma);
//						boolean b = m.find();
//
//						if (b){
//							String nueva = m.group().toString();
//						   System.out.println("There is a special character in my string "+nueva);
//						}/*else{
//							//String nueva = m.group();
//							System.out.println("No tiene caracter especial ");
//						}*/
//						
						
					/*Agregar a arraylist de "palabras separadas" y listas para analisis*/
					dataSeparated.add(delComma);
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
					if(datos.contains(level1[p])){ //Si corresponden (son iguales) ejecuta
						noGoodWords1 = noGoodWords1 + 1; //Sumar el numero de palabras encontradas
						noGoodWordsArray1.add(level1[p]); //Agregar a un arrayList las palabras encontradas
					}
				}
			}
		}

		public static void analisis_lvl2(){ //Funcion de analisis del diccionario 2
			for(String datos: dataSeparated){ // (For each), por cada string obtenido desde la base de datos va a hacer
				for( int p1 = 0; p1 < level2.length; p1++){ //Por cada palabra del diccionario vas a analizar
					if(datos.contains(level2[p1])){ //Si corresponden (son iguales) ejecuta //BUGGGGG
						noGoodWords2 = noGoodWords2 + 1; //Sumar el numero de palabras encontradas
						noGoodWordsArray2.add(level2[p1]); //Agregar a un arrayList las palabras encontradas
					}
				}
			}
		}

		public static void analisis_lvl3(){ //Funcion de analisis del diccionario 3
			for(String datos: dataSeparated){ // (For each), por cada string obtenido desde la base de datos va a hacer
				for( int p2 = 0; p2 < level3.length; p2++){ //Por cada palabra del diccionario vas a analizar
					if(datos.contains(level3[p2])){ //Si corresponden (son iguales) ejecuta
						noGoodWords3 = noGoodWords3 + 1; //Sumar el numero de palabras encontradas
						noGoodWordsArray3.add(level3[p2]); //Agregar a un arrayList las palabras encontradas
					}
				}
			}
		}

		public static void resultados(){
			System.out.println("Palabras Totales: "+TotalWords);
			System.out.println("Palabras ofensivas de nivel 1: " +noGoodWords1);
			Main.textN1.setText(""+noGoodWords1);
			System.out.println("Palabras ofensivas de nivel 2: " +noGoodWords2);
			Main.textN2.setText(""+noGoodWords2);
			System.out.println("Palabras ofensivas de nivel 3: " +noGoodWords3);
			Main.textN3.setText(""+noGoodWords3);
			int sum  = noGoodWords1 + noGoodWords2 + noGoodWords3;
			System.out.println("El usuario dijo un total de "+sum +" palabras altisonantes de un total de " +TotalWords+ " palabras");
			Main.textTotalPalabras.setText(""+TotalWords);
			Main.textTotalMalas.setText(""+sum);
			System.out.println("**************************************************************************************************\n");
			String userBad = "";

			if(noGoodWords1 > noGoodWords2){
				//userBad = "El usuario "+Main.comboBox.getSelectedItem()+" se ha clasificado en el NIVEL 1 de agresividad";
				userBad = "Agresividad Nivel 1";
				Main.lblNivel.setForeground(Color.green);
				Main.lblNivel.setText(userBad);
			}else if(noGoodWords2 >noGoodWords3){
				//userBad = "El usuario "+Main.comboBox.getSelectedItem()+" se ha clasificado en el NIVEL 2 de agresividad";   //  <---- Simple, pero funcional a mis propositos
				userBad = "Agresividad Nivel 2";
				Main.lblNivel.setForeground(Color.blue);
				Main.lblNivel.setText(userBad);
			}else if(noGoodWords3 > noGoodWords1){
				//userBad = "El usuario "+Main.comboBox.getSelectedItem()+" se ha clasificado en el NIVEL 3 de agresividad";
				userBad = "Agresividad Nivel 3";
				Main.lblNivel.setForeground(Color.red);
				Main.lblNivel.setText(userBad);
			}else{
				//userBad = "El usuario "+Main.comboBox.getSelectedItem()+" NO cuenta con registros de agresividad";
				userBad = "NO cuenta con registros de agresividad";
				Main.lblNivel.setForeground(Color.black);
				Main.lblNivel.setText(userBad);
			}

			System.out.println(userBad);
			System.out.println("**************************************************************************************************\n");
			
			Main.lblNivel.setText(userBad);
			Main.textAreaBadWords.setText("Palabras Nivel 1\n");
			for(int bg = 0; bg < noGoodWordsArray1.size(); bg++){
				//System.out.println(noGoodWordsArray1.get(bg)); [TESTING]
				Main.textAreaBadWords.append("\n"+noGoodWordsArray1.get(bg));
			}
			Main.textAreaBadWords.append("\nPalabras Nivel 2\n");
			for(int bg2 = 0; bg2 < noGoodWordsArray2.size(); bg2++){
				//System.out.println(noGoodWordsArray2.get(bg2));  [TESTING]
				Main.textAreaBadWords.append("\n"+noGoodWordsArray2.get(bg2));
			}
			Main.textAreaBadWords.append("\nPalabras Nivel 3\n");
			for(int bg3 = 0; bg3 < noGoodWordsArray3.size(); bg3++){
				//System.out.println(noGoodWordsArray3.get(bg3));  [TESTING]
				Main.textAreaBadWords.append("\n"+noGoodWordsArray3.get(bg3));
			}
			
			/*float percentage = (sum/TotalWords)*100;
			System.out.println(percentage);
			Main.labelPercentage.setText(""+percentage+"%");*/
		}
		
		public static void resetingValues(){
			TotalWords = 0;  //Reseteo de variables para evitar errores
			noGoodWords1 = 0;
			noGoodWords2 = 0;
			noGoodWords3 = 0;
			noGoodWordsArray1.clear();
			noGoodWordsArray2.clear();
			noGoodWordsArray3.clear();
			
			BDData.clear(); //Delete data for prevent bad results
			datosBD.clear();
			dataSeparated.clear();
			Main.labelImagen.setIcon(new ImageIcon("img\\Cortana.gif"));
			System.out.println("Valores reseteados");
		}
	
	public static void neural(){

		try{
			Fann fann = new Fann( "C:\\Users\\David\\Downloads\\FANN\\FILES\\ms4.net" );
		    float[] inputs = new float[]{ 1, -1 };
		    float[] outputs = fann.run( inputs );
		    fann.close();
		    
		    for (float f : outputs) {
		        System.out.print(f + ",");
		    }
		}catch(Exception e){
			System.out.println("Error " +e.getMessage());
		}
		finally{ //Aunque haya un error, se ejecuta
			System.out.println("Se ejecut� la red neuronal");
		}
	}
		
}
