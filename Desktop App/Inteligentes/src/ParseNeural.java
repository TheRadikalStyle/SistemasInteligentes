import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.googlecode.fannj.Fann;

public class ParseNeural {
	//static String homeDir = System.getProperty("user.home");
	static String homeDir = System.getProperty("user.dir");
	static float w;

	static float x;

	static float y;

	static float z;
	
	static float resultadoNeural;
	
	static ArrayList<String> datosBD = new ArrayList<String>();
	/************************START DICTIONARY DEFINITION**************************************************************/
	private static String Dic_Lvl1 = "tonto,tonta,sonso,sonsa,zonzo,zonza,tontita,tontito,wey,guey,invecil,imbecil,inbecil,imbecil,morro";
	private static String[] level1 = Dic_Lvl1.split(",");
	private static String Dic_Lvl2 = "idiota,pendejo,estupido,estupida,pendeja,madre,culero,culera,cabron, carajo,mierda,ojete,hdp,hijaputa,hijoputa,pinche,pinches,inche,inchi,inshi,pinki,maldita,maldito,caca,caka,kk,mojon,joder,mta,puñetas,puños";
	private static String[] level2 = Dic_Lvl2.split(",");
	private static String Dic_Lvl3 = "perra,golfa,puto,puta,marica,verga,vergon,vergota,vergotas,vergas,chingada,cojer,coger,chupa,chupame,huevos,huebos,uevos,uebos,guebos,guevos,culo,kulo,prosti,prostituta,bulto,concha,cuca,webos,weboz,pelame,pelotas,cojones,negro,indigena,indio,india,nigga,pene,viola,violar,violare,sexo,felacion,tetas,tetona,tetotas,pito,panocha,panochon";
	private static String[] level3 = Dic_Lvl3.split(",");
	/************************FINISH DICTIONARY DEFINITION**************************************************************/	
	//private static String[] dataSeparated;

	public static int noGoodWords1 = 0; //Almacen numerico de palabras ofensivas por nivel
	public static int noGoodWords2 = 0;
	public static int noGoodWords3 = 0;

	static ArrayList<String> noGoodWordsArray1 = new ArrayList<String>(); //Almacen de palabras ofensivas por nivel
	static ArrayList<String> noGoodWordsArray2 = new ArrayList<String>();
	static ArrayList<String> noGoodWordsArray3 = new ArrayList<String>();
	
	static ArrayList<String> specialCharsArray = new ArrayList<String>();
	static ArrayList<String> emoticonArray = new ArrayList<String>();

	public static int TotalWords = 0;

	static ArrayList<String> BDData = new ArrayList<String>();
	static ArrayList<String> dataSeparated = new ArrayList<String>();
	
	static String sql2 = "";
	
	
	public static void main(){
		//Main.labelImagen.setIcon(new ImageIcon("img\\loading.gif"));
		//Main.labelImagen.setIcon(new ImageIcon("resources\\img\\loading.gif"));
		ImageIcon imageIcon = new ImageIcon(Main.class.getResource("img/loading.gif"));
		Main.labelImagen.setIcon(imageIcon);
		JOptionPane.showMessageDialog(null, "Analisis por red neuronal del usuario "+Main.comboBox.getSelectedItem());
		Main.textAreaBadWords.setText("");
		
		//TODO Aqui deberia haber un TRY/CATCH [Ya está pero no creo que funcione bien]
		try{
			System.setProperty("jna.library.path", homeDir+"\\FANN\\bin\\");
			System.out.println( System.getProperty("jna.library.path") );
			File file = new File(System.getProperty("jna.library.path") + "fannfloat.dll");
			System.out.println("Is the dll file there:" + file.exists());
			if(file.exists()){
				try{
					System.load(file.getAbsolutePath());
					System.out.println("fannfloat dll cargado exitosamente");
					Sounds.PlaySounds("Success_2");
				}catch(Exception ex){
				Sounds.PlaySounds("Error");
				System.out.println("Ha ocurrido un error en la carga del dll fann " +ex);
				JOptionPane.showMessageDialog(null, "Ha ocurrido un error en la carga de fannfloat.dll [Se cerrara el programa]");
				System.exit(0);
				}finally{
					dataObtaining();
					dataRecolection();
					analisis_lvl1();
					analisis_lvl2();
					analisis_lvl3();
					especiales();
					resultados();
					//neural();
					resetingValues();	
					Sounds sn = new Sounds();
					sn.PlaySounds("Success_1");
				}
				
			}else{
				Sounds.PlaySounds("Error");
				System.out.println("Ha ocurrido un error en la carga del dll fann ");
				JOptionPane.showMessageDialog(null, "Ha ocurrido un error en la carga de fannfloat.dll [Se cerrara el programa]");
				System.exit(0);
			}

	}catch(Exception ed){
		Sounds.PlaySounds("Error");
		}
	}
	
	public static void dataObtaining(){
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
					
					/*Eliminación de comas*/
					String delComma = aMin;
					delComma = delComma.replace(",", " ");
					
					/*Detector de caracteres especiales*/
					Pattern p = Pattern.compile("[^a-z0-9 ]");
					Matcher m = p.matcher(delComma);
					boolean b = m.find();
					
					if (b){ //Si detecta caracteres especiales
						//String nueva = m.group().toString();
						delComma = delComma.toUpperCase();
					   System.out.println("Caracteres especiales detectados en "+delComma);
					   specialCharsArray.add(delComma);
					}
					
					/*Reemplazo de acentos... Puntos, parentesis y dieresis*/
					if(delComma.contains("á")){ 
						delComma = delComma.replace("á", "a");
						System.out.println("Se cambio de la á a la a");
					}else if(delComma.contains("é")){
							delComma = delComma.replace("é", "e");
							System.out.println("Se cambio de la é a la e");
							}else if(delComma.contains("í")){
								delComma = delComma.replace("í", "i");
								System.out.println("Se cambio de la í a la i");
								}else if(delComma.contains("ó")){
									delComma = delComma.replace("ó", "o");
									System.out.println("Se cambio de la ó a la o");
									}else if(delComma.contains("ú")){
										delComma = delComma.replace("ú", "u");
										System.out.println("Se cambio de la ú a la u");
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
														}else if(delComma.contains("¡")){
															delComma = delComma.replace("¡", " ");
															System.out.println("Se cambio de ¡ a un espacio");
															}else if(delComma.contains("ü")){
																delComma = delComma.replace("ü", "u");
																System.out.println("Se cambio de ü a una u");
																}
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
			
			/*[Analisis avanzado] - Corrector ortografico y morfología conjunta*/
			for(String datos: dataSeparated){ 
				String nuevoDato = datos;
				
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(nuevoDato);
				boolean b = m.find();

				if (b){ //Si detecta caracteres especiales
					System.out.println("Numeros detectados en "+nuevoDato);
					String nuevoDato3 = nuevoDato;
					/*Based from https://gustavoarielschwartz.files.wordpress.com/2013/05/letras-x-nc3bameros.jpg*/
					nuevoDato3 = nuevoDato3.replaceAll("1", "i");
					//nuevoDato2 = nuevoDato2.replaceAll("2", "s");
					nuevoDato3 = nuevoDato3.replaceAll("3", "e");
					nuevoDato3 = nuevoDato3.replaceAll("4", "a");
					nuevoDato3 = nuevoDato3.replaceAll("5", "s");
					nuevoDato3 = nuevoDato3.replaceAll("6", "g");
					nuevoDato3 = nuevoDato3.replaceAll("7", "t");
					nuevoDato3 = nuevoDato3.replaceAll("8", "b");
					nuevoDato3 = nuevoDato3.replaceAll("9", "p");
					nuevoDato3 = nuevoDato3.replaceAll("0", "o");
					
					nuevoDato3 = nuevoDato3.replaceAll("b", "v");
					nuevoDato3 = nuevoDato3.replaceAll("z", "s");
					
					nuevoDato3 = nuevoDato3.replaceAll("(.)\\1", "$1"); /*Elimina caracteres repetidos*/
					
					for( int p2 = 0; p2 < level1.length; p2++){ //Por cada palabra del diccionario vas a analizar
						if(nuevoDato3.contains(level1[p2])){ //Si corresponden (son iguales) ejecuta
							noGoodWords1 = noGoodWords1 + 1; //Sumar el numero de palabras encontradas
							noGoodWordsArray1.add(nuevoDato); //Agregar a un arrayList las palabras encontradas nueviDato = String original, sin cambio de letras
						}
					}
				}
			}
			
			
		}

		public static void analisis_lvl2(){ //Funcion de analisis del diccionario 2
			for(String datos: dataSeparated){ // (For each), por cada string obtenido desde la base de datos va a hacer
				for( int p1 = 0; p1 < level2.length; p1++){ //Por cada palabra del diccionario vas a analizar
					if(datos.contains(level2[p1])){ //Si corresponden (son iguales) ejecuta
						noGoodWords2 = noGoodWords2 + 1; //Sumar el numero de palabras encontradas
						noGoodWordsArray2.add(level2[p1]); //Agregar a un arrayList las palabras encontradas
					}
				}
			}
			/*[Analisis avanzado] - Corrector ortografico y morfología conjunta*/
			for(String datos: dataSeparated){ 
				String nuevoDato = datos;
				
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(nuevoDato);
				boolean b = m.find();

				if (b){ //Si detecta caracteres especiales
					System.out.println("Numeros detectados en "+nuevoDato);
					String nuevoDato4 = nuevoDato;
					/*Based from https://gustavoarielschwartz.files.wordpress.com/2013/05/letras-x-nc3bameros.jpg*/
					nuevoDato4 = nuevoDato4.replaceAll("1", "i");
					//nuevoDato2 = nuevoDato2.replaceAll("2", "s");
					nuevoDato4 = nuevoDato4.replaceAll("3", "e");
					nuevoDato4 = nuevoDato4.replaceAll("4", "a");
					nuevoDato4 = nuevoDato4.replaceAll("5", "s");
					nuevoDato4 = nuevoDato4.replaceAll("6", "g");
					nuevoDato4 = nuevoDato4.replaceAll("7", "t");
					nuevoDato4 = nuevoDato4.replaceAll("8", "b");
					nuevoDato4 = nuevoDato4.replaceAll("9", "p");
					nuevoDato4 = nuevoDato4.replaceAll("0", "o");
					
					nuevoDato4 = nuevoDato4.replaceAll("b", "v");
					nuevoDato4 = nuevoDato4.replaceAll("z", "s");
					
					nuevoDato4 = nuevoDato4.replaceAll("(.)\\1", "$1"); /*Elimina caracteres repetidos*/
					
					for( int p2 = 0; p2 < level2.length; p2++){ //Por cada palabra del diccionario vas a analizar
						if(nuevoDato4.contains(level2[p2])){ //Si corresponden (son iguales) ejecuta
							noGoodWords2 = noGoodWords2 + 1; //Sumar el numero de palabras encontradas
							noGoodWordsArray2.add(nuevoDato); //Agregar a un arrayList las palabras encontradas nueviDato = String original, sin cambio de letras
						}
					}
				}
			}
		}

		public static void analisis_lvl3(){ //Funcion de analisis del diccionario 3
			for(String datos: dataSeparated){ // (For each), por cada string obtenido desde la base de datos va a hacer [Analisis sencillo]
				for( int p2 = 0; p2 < level3.length; p2++){ //Por cada palabra del diccionario vas a analizar
					if(datos.contains(level3[p2])){ //Si corresponden (son iguales) ejecuta
						noGoodWords3 = noGoodWords3 + 1; //Sumar el numero de palabras encontradas
						noGoodWordsArray3.add(level3[p2]); //Agregar a un arrayList las palabras encontradas
					}
				}
			}
//			/*[Analisis avanzado] - Morfología numerica*/
//			for(String datos: dataSeparated){ 
//				String nuevoDato = datos;
//				
//				Pattern p = Pattern.compile("[0-9]");
//				Matcher m = p.matcher(nuevoDato);
//				boolean b = m.find();
//
//				if (b){ //Si detecta caracteres especiales
//					System.out.println("Numeros detectados en "+nuevoDato);
//					//String nueva = m.group().toString();
//					//delComma = delComma.toUpperCase();
//					String nuevoDato2 = nuevoDato;
//					/*Based from https://gustavoarielschwartz.files.wordpress.com/2013/05/letras-x-nc3bameros.jpg*/
//					nuevoDato2 = nuevoDato2.replaceAll("1", "i");
//					//nuevoDato2 = nuevoDato2.replaceAll("2", "s");
//					nuevoDato2 = nuevoDato2.replaceAll("3", "e");
//					nuevoDato2 = nuevoDato2.replaceAll("4", "a");
//					nuevoDato2 = nuevoDato2.replaceAll("5", "s");
//					nuevoDato2 = nuevoDato2.replaceAll("6", "g");
//					nuevoDato2 = nuevoDato2.replaceAll("7", "t");
//					nuevoDato2 = nuevoDato2.replaceAll("8", "b");
//					nuevoDato2 = nuevoDato2.replaceAll("9", "p");
//					nuevoDato2 = nuevoDato2.replaceAll("0", "o");
//					
//					nuevoDato2 = nuevoDato2.replaceAll("(.)\\1", "$1"); /*Elimina caracteres repetidos*/
//					
//					for( int p2 = 0; p2 < level3.length; p2++){ //Por cada palabra del diccionario vas a analizar
//						if(nuevoDato2.contains(level3[p2])){ //Si corresponden (son iguales) ejecuta
//							noGoodWords3 = noGoodWords3 + 1; //Sumar el numero de palabras encontradas
//							noGoodWordsArray3.add(nuevoDato); //Agregar a un arrayList las palabras encontradas nueviDato = String original, sin cambio de letras
//						}
//					}
//				}
//			}
//			/*[Analisis avanzado] - Corrector ortografico*/
//			for(String datos: dataSeparated){ 
//				String nuevoDato3 = datos;
//
//					String nuevoDato4 = nuevoDato3;
//					/*Correcor ortografico*/
//					nuevoDato4 = nuevoDato4.replaceAll("b", "v");
//					nuevoDato4 = nuevoDato4.replaceAll("z", "s");
//					
//					nuevoDato4 = nuevoDato4.replaceAll("(.)\\1", "$1"); /*Elimina caracteres repetidos*/
//					
//					for( int p2 = 0; p2 < level3.length; p2++){ //Por cada palabra del diccionario vas a analizar
//						if(nuevoDato4.contains(level3[p2])){ //Si corresponden (son iguales) ejecuta
//							noGoodWords3 = noGoodWords3 + 1; //Sumar el numero de palabras encontradas
//							noGoodWordsArray3.add(nuevoDato3); //Agregar a un arrayList las palabras encontradas nueviDato = String original, sin cambio de letras
//						}
//					}
//				}
			
			/*[Analisis avanzado] - Corrector ortografico y morfología conjunta*/
			for(String datos: dataSeparated){ 
				String nuevoDato = datos;
				
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(nuevoDato);
				boolean b = m.find();

				if (b){ //Si detecta caracteres especiales
					System.out.println("Numeros detectados en "+nuevoDato);
					String nuevoDato5 = nuevoDato;
					/*Based from https://gustavoarielschwartz.files.wordpress.com/2013/05/letras-x-nc3bameros.jpg*/
					nuevoDato5 = nuevoDato5.replaceAll("1", "i");
					//nuevoDato2 = nuevoDato2.replaceAll("2", "s");
					nuevoDato5 = nuevoDato5.replaceAll("3", "e");
					nuevoDato5 = nuevoDato5.replaceAll("4", "a");
					nuevoDato5 = nuevoDato5.replaceAll("5", "s");
					nuevoDato5 = nuevoDato5.replaceAll("6", "g");
					nuevoDato5 = nuevoDato5.replaceAll("7", "t");
					nuevoDato5 = nuevoDato5.replaceAll("8", "b");
					nuevoDato5 = nuevoDato5.replaceAll("9", "p");
					nuevoDato5 = nuevoDato5.replaceAll("0", "o");
					
					nuevoDato5 = nuevoDato5.replaceAll("b", "v");
					nuevoDato5 = nuevoDato5.replaceAll("z", "s");
					
					nuevoDato5 = nuevoDato5.replaceAll("(.)\\1", "$1"); /*Elimina caracteres repetidos*/
					
					for( int p2 = 0; p2 < level3.length; p2++){ //Por cada palabra del diccionario vas a analizar
						if(nuevoDato5.contains(level3[p2])){ //Si corresponden (son iguales) ejecuta
							noGoodWords3 = noGoodWords3 + 1; //Sumar el numero de palabras encontradas
							noGoodWordsArray3.add(nuevoDato); //Agregar a un arrayList las palabras encontradas nueviDato = String original, sin cambio de letras
						}
					}
				}
			}
			
		}
		
		public static void especiales(){
			if(!specialCharsArray.isEmpty()){ /*Si no esta vacio analizará*/
				for(String dataToAnalize : specialCharsArray){
					if(dataToAnalize.contains("Ñ")){
						dataToAnalize = dataToAnalize.toLowerCase();
						dataToAnalize.replaceAll("ñ", "n");
						//TODO Analizarlos por medio de red neuronal o algoritmo
					}
					if(dataToAnalize.contains("$") && dataToAnalize.contains("#")){
						System.out.println("Posible insulto oculto "+dataToAnalize);
						noGoodWordsArray3.add(dataToAnalize);
					}
					if(dataToAnalize.length() == 2){
						System.out.println("Posible emoticon "+dataToAnalize);
						emoticonArray.add(dataToAnalize);
					}
					if(dataToAnalize.length() == 3){
						System.out.println("Posible insulto simplificado "+dataToAnalize);
						if(dataToAnalize.contains("-") || dataToAnalize.contains(":")){ //Si detecta un guion medio lo cataloga como emoticon... El gion medio se puede considerar estandar en emoticones de longitud 3
							System.out.println("Emoticon de 3 caracteres detectado en: "+dataToAnalize);
							emoticonArray.add(dataToAnalize);
						}else{
							if(dataToAnalize.contains(".")){
								w = 0;
								x = 1;
								y = 0;
								z = 0;
								neural(w,x,y,z);
								if(resultadoNeural >= 0.5){
									noGoodWordsArray3.add(dataToAnalize);
									System.out.println(dataToAnalize+" se ha catalogado como nivel 3");
								}
							}
						}
					}
					if(dataToAnalize.length() >= 4){
						int counterAst = 0;
						int counterSharp = 0;
						System.out.println("Posible insulto o repeticion de letras"+dataToAnalize);
						if(dataToAnalize.contains("*")){ 
							char charArray[] = dataToAnalize.toCharArray();
							for(int oj = 0; oj < charArray.length; oj++){
								if(charArray[oj] == '*'){
									counterAst = counterAst + 1;
									if(counterAst >= 1){
										String asteris = new String(charArray);
										noGoodWordsArray3.add(asteris);
										System.out.println("insulto disfrazado - Nivel 3 aplicado "+asteris);
									}
								}
								
						if(charArray[oj] == '#'){
							counterSharp = counterSharp + 1;
							if(counterSharp >= 2){
								String sharps = new String(charArray);
								noGoodWordsArray3.add(sharps);
								System.out.println("insulto disfrazado - Nivel 3 aplicado "+sharps);
							}
						}
								
					}
				}
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
			}else if(noGoodWords1 == noGoodWords2){
				//TODO Toma de decisión por medio de red neuronal
				w = 1;
				x = 1;
				y = 0;
				z = 0;
				
				neural(w,x,y,z);
				if(resultadoNeural <= 0.50){
					userBad = "Agresividad Nivel 2 - (Red Neuronal)";
					Main.lblNivel.setForeground(Color.blue);
					Main.lblNivel.setText(userBad);
				}
			}else if(noGoodWords2 == noGoodWords3){
				//TODO Toma de decisión por medio de red neuronal
				w = 0;
				x = 1;
				y = 1;
				z = 0;
				
				neural(w,x,y,z);
				if(resultadoNeural <= 0.60){
					userBad = "Agresividad Nivel 3 - (Red Neuronal)";
					Main.lblNivel.setForeground(Color.red);
					Main.lblNivel.setText(userBad);
				}
			}else if(noGoodWords3 == noGoodWords1){
				//TODO Toma de decisión por medio de red neuronal
				w = 1;
				x = 0;
				y = 1;
				z = 0;
				
				neural(w,x,y,z);
				if(resultadoNeural <= 0.60){
					userBad = "Agresividad Nivel 3 - (Red Neuronal)";
					Main.lblNivel.setForeground(Color.red);
					Main.lblNivel.setText(userBad);
				}
			}else if(noGoodWords1 == noGoodWords2 && noGoodWords2 == noGoodWords3 && noGoodWords3 == noGoodWords1){
				userBad = "NO cuenta con registros de agresividad";
				Main.lblNivel.setForeground(Color.black);
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
			Main.textAreaBadWords.setText("**Palabras Nivel 1**\n");
			for(int bg = 0; bg < noGoodWordsArray1.size(); bg++){
				//System.out.println(noGoodWordsArray1.get(bg)); [TESTING]
				Main.textAreaBadWords.append("\n"+noGoodWordsArray1.get(bg));
			}
			Main.textAreaBadWords.append("\n\n**Palabras Nivel 2**\n");
			for(int bg2 = 0; bg2 < noGoodWordsArray2.size(); bg2++){
				//System.out.println(noGoodWordsArray2.get(bg2));  [TESTING]
				Main.textAreaBadWords.append("\n"+noGoodWordsArray2.get(bg2));
			}
			Main.textAreaBadWords.append("\n\n**Palabras Nivel 3**\n");
			for(int bg3 = 0; bg3 < noGoodWordsArray3.size(); bg3++){
				//System.out.println(noGoodWordsArray3.get(bg3));  [TESTING]
				Main.textAreaBadWords.append("\n"+noGoodWordsArray3.get(bg3));
			}
			Main.textAreaBadWords.append("\n\n**Emoticones**\n");
			for(int bg4 = 0; bg4 < emoticonArray.size(); bg4++){
				//System.out.println(noGoodWordsArray3.get(bg3));  [TESTING]
				Main.textAreaBadWords.append("\n"+emoticonArray.get(bg4));
			}
			
			/*float percentage = (sum/TotalWords)*100;
			System.out.println(percentage);
			Main.labelPercentage.setText(""+percentage+"%");*/
			
			/****Guardado de reporte****/
			int input = JOptionPane.showOptionDialog(null, "¿Desea guardar un reporte XML del analisis?", "Feedback", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Si", "No"}, null);
			if(input == JOptionPane.OK_OPTION){
				 /***Saving Settings - Report Generator on XML format***/
			    Properties saveProps = new Properties();
			    saveProps.setProperty("Nombre_Usuario",""+Main.comboBox.getSelectedItem());
			    saveProps.setProperty("Usuario categorizado nivel", Main.lblNivel.getText());
			    saveProps.setProperty("Palabras_totales", ""+TotalWords);
			    saveProps.setProperty("Palabras_nivel_1", Main.textN1.getText());
			    saveProps.setProperty("Palabras_nivel_2", Main.textN2.getText());
			    saveProps.setProperty("Palabras_nivel_3", Main.textN3.getText());
			    try {
			    	JFileChooser fc = new JFileChooser();
			    	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    	int respuesta = fc.showSaveDialog(fc);
			    	
			    	if(respuesta == JFileChooser.APPROVE_OPTION){
			    	File carpetaElegida = fc.getSelectedFile();
			    	saveProps.storeToXML(new FileOutputStream(carpetaElegida+"\\report_"+Main.comboBox.getSelectedItem()+".xml"), "");
					JOptionPane.showMessageDialog(null, "Se ha guardado un reporte del usuario "+Main.comboBox.getSelectedItem());
			    	}
				} catch (FileNotFoundException e) {
					Sounds.PlaySounds("Error");
					JOptionPane.showMessageDialog(null, "Error de guardado de reporte");
					e.printStackTrace();
				} catch (IOException e) {
					Sounds.PlaySounds("Error");
					JOptionPane.showMessageDialog(null, "Error de guardado de reporte");
					e.printStackTrace();
				}
			}else{
				
			}
			/****Guardado de reporte****/

		}
		
		public static void resetingValues(){
			TotalWords = 0;  //Reseteo de variables para evitar errores
			noGoodWords1 = 0;
			noGoodWords2 = 0;
			noGoodWords3 = 0;
			noGoodWordsArray1.clear();
			noGoodWordsArray2.clear();
			noGoodWordsArray3.clear();
			specialCharsArray.clear();
			emoticonArray.clear();
			
			BDData.clear();
			datosBD.clear();
			dataSeparated.clear();
			//Main.labelImagen.setIcon(new ImageIcon("img\\normal.gif"));
			ImageIcon imageIcon = new ImageIcon(Main.class.getResource("img/normal.gif"));
			Main.labelImagen.setIcon(imageIcon);
			System.out.println("Valores reseteados");
		}
	
	public static void neural(float w,float x, float y, float z){
		try{
		    Fann fann = new Fann(System.getProperty("user.dir") +"/NeuralFiles/ANN.net");
			float[] inputs = new float[]{w,x,y,z};
		    float[] outputs = fann.run( inputs );
		    fann.close();
		    
		    for (float f : outputs) {
		        System.out.print(f);
			    resultadoNeural = f;
		    }
		}catch(Exception e){
			System.out.println("Error " +e.getMessage());
		}
		finally{ //Aunque haya un error, se ejecuta
			System.out.println("Se ejecutó la red neuronal");
		}
	}
		
}
