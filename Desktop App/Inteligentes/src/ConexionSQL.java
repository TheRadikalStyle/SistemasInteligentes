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

public static Connection conn = null;
public static PreparedStatement psql = null;
public static ResultSet rs = null;
	
public void conectar(){
	try{
		System.out.println("Intentando conectarse a la base de datos");
		Class.forName(driver);
		
		conn = DriverManager.getConnection(url, user, password);
		System.out.println("Conectado exitosamente");
		
		//test();
		
	}catch(SQLException e){
		System.out.println("Error de MySQL" /*+e.getMessage()*/);
		//JOptionPane.showMessageDialog(null, "Posiblemente la base de datos esta fuera de linea");
		int input = JOptionPane.showOptionDialog(null, "Se saldrá del programa", "Error de conexion a Base de Datos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if(input == JOptionPane.OK_OPTION){
			System.exit(0);
		}else{
			System.exit(0);
		}
		}catch(ClassNotFoundException e){
		 e.printStackTrace();
			}catch(Exception e){
			 System.out.println("Error general "+ e.getMessage());
			}
	}

public void desconectar(){
	try{
		if(rs!=null) rs.close();
		if(psql!=null) psql.close();
		if(conn!=null) conn.close();
		System.out.println("Desconexion exitosa");
	}catch(SQLException e){
		e.printStackTrace();
	}
	
}

/*public void test(){
	//Connection Test
	Statement estado = conn.createStatement();
	ResultSet resultado = estado.executeQuery("SELECT * FROM usuario");
	
	System.out.println("Datos \t Mas Datos");
	while(resultado.next()){
		System.out.println(resultado.getString("Usu_Comentario"));
	}
	
	desconectar();
}*/
}
