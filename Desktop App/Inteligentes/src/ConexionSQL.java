/*
 * Autor: David Ochoa Gutierrez
 * Project name: Ghost Freak
 * */

import java.sql.*;

import javax.swing.JOptionPane;

public class ConexionSQL {

	public static String user = "root";	
	public static String password = "";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://127.0.0.1:3306/inteligentes";
	static int attempt = 0;

	public static Connection conn = null;
	public static PreparedStatement psql = null;
	public static ResultSet rs = null;

	public void conectar(){
		try{
			System.out.println("Intentando conectarse a la base de datos");
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Conectado exitosamente");
		}catch(SQLException e){
			Sounds sou = new Sounds();
			sou.PlaySounds("Error");
			System.out.println("Error de MySQL" /*+e.getMessage()*/);
			//JOptionPane.showMessageDialog(null, "Posiblemente la base de datos esta fuera de linea");
			int input = JOptionPane.showOptionDialog(null, "Se saldrá del programa", "Error de conexion a Base de Datos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Aceptar", "Salir"}, null);
			if(input == JOptionPane.OK_OPTION){
				System.exit(0);
			}else{
			//if(input == JOptionPane.CANCEL_OPTION){
				//attempt  = attempt + 1;
				//conectar();
				System.exit(0);
			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			Sounds sou = new Sounds();
			sou.PlaySounds("Error");
		}catch(Exception e){
			System.out.println("Error general "+ e.getMessage());
			Sounds sou = new Sounds();
			sou.PlaySounds("Error");
		}
//		if(attempt == 3){
//			JOptionPane.showMessageDialog(null, "Numero de intentos de reconexion alcanzado, se saldrá del programa");
//			System.exit(0);
//		}
	}

	public void desconectar(){
		try{
			if(rs!=null) rs.close();
			if(psql!=null) psql.close();
			if(conn!=null) conn.close();
			System.out.println("Desconexion exitosa");
		}catch(SQLException e){
			e.printStackTrace();
			Sounds sou = new Sounds();
			sou.PlaySounds("Error");
		}

	}

}
