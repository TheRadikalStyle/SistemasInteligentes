/*
 * Autor: David Ochoa Gutierrez
 * Project name: Ghost Freak
 * */

import java.sql.*;

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
