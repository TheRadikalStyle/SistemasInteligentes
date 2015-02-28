import java.sql.*;

public class ConexionSQL {

public static String user = "USER";	
public static String password = "PASSWORD";
public static String driver = "com.mysql.jdbc.Driver";
public static String url = "jdbc:mysql://IP_ADD/DB_NAME";

public static Connection conn = null;
public static PreparedStatement psql = null;
public static ResultSet rs = null;
	
public void conectar(){
	try{
		System.out.println("Intentando conectase a la base de datos");
		Class.forName(driver);
		
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println("Conectado exitosamente");
		
		//Jalando datos
//		Statement estado = conn.createStatement();
//		ResultSet resultado = estado.executeQuery("NUESTRO QUERY");
//		
//		System.out.println("Datos \t Mas Datos");
//		while(resultado.next()){
//			System.out.println(resultado.getString("CAMPO DESEADO"));
//		}
		
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
	}catch(SQLException e){
		e.printStackTrace();
	}
	
}
}
