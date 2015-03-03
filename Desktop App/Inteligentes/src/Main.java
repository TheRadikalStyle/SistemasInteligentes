import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Main {

	private JFrame frame;
	public static JTable table;
	public static ConexionSQL conex = new ConexionSQL();

	public static JComboBox comboBox = new JComboBox();
	static DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel(); //modelo de combobox
	static DefaultTableModel modeloTabla = new DefaultTableModel();
	public static String sql1 = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		app();
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setName("Interface");
		frame.setTitle("Sistemas Inteligentes");
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Hakuna matata");
				//modeloTabla.setRowCount(0);
				System.out.println(sql1);
				nuevaTabla();
				modeloTabla.fireTableDataChanged();
			}
		});

		comboBox.setBounds(12, 12, 230, 24);
		frame.getContentPane().add(comboBox);

		JLabel lblComboboxlabel = new JLabel("Selecciona el usuario");
		lblComboboxlabel.setBounds(260, 12, 280, 24);
		frame.getContentPane().add(lblComboboxlabel);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setBounds(12, 62, 624, 166);
		frame.getContentPane().add(table);


		JButton btnDiccionarios = new JButton("Diccionarios");
		btnDiccionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Click ParseDictionary");
				ParseDictionary pd = new ParseDictionary();
				pd.main(null);
			}
		});
		btnDiccionarios.setBounds(29, 239, 157, 25);
		frame.getContentPane().add(btnDiccionarios);

		JButton btnRedNeuronal = new JButton("Red Neuronal");
		btnRedNeuronal.setBounds(395, 239, 171, 25);
		frame.getContentPane().add(btnRedNeuronal);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 62, 622, 166);
		frame.getContentPane().add(scrollPane);
	}

	public static void app(){
		String sql = "SELECT DISTINCT Usu_Nombre FROM usuario";
		try{
			ConexionSQL conne = new ConexionSQL();
			conne.conectar();
			Statement a =ConexionSQL.psql=ConexionSQL.conn.prepareStatement(sql); //Cambio de variables debido a problemas de conexion y traslape de variables
			ResultSet b = ConexionSQL.rs = ConexionSQL.psql.executeQuery();

			comboBox.addItem("Selecciona un campo");
			//comboBox.setModel(modeloCombo); //Agregamos el objeto

			while(b.next()){
				comboBox.addItem(b.getObject("Usu_Nombre"));
			}
			a.close();

		}catch(SQLException e){
			e.printStackTrace();
		}

		/****TABLE****/
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("ID Comentario");
		modeloTabla.addColumn("Comentario");

		String nameBox = (String) comboBox.getSelectedItem();

		//System.out.println(nameBox);

		//if(comboBox.getSelectedIndex() == 0){
			sql1 = "SELECT * FROM usuario";
			//System.out.println("Seleccionado 0");
		//}else{
		//	sql1 = "SELECT * FROM usuario WHERE Usu_Nombre='"+nameBox+"'";
		//}

		//String [] datos = new String[10];
		try{
			ConexionSQL conne = new ConexionSQL();
			conne.conectar();
			ConexionSQL.psql=ConexionSQL.conn.prepareStatement(sql1);
			ConexionSQL.rs = ConexionSQL.psql.executeQuery();

			String N="", IDU="", UC="", IDC="";

			while(ConexionSQL.rs.next()){
				N = ConexionSQL.rs.getString("ID_Facebook");
				IDU = ConexionSQL.rs.getString("Usu_Nombre");
				UC = ConexionSQL.rs.getString("ID_Comentario");
				IDC = ConexionSQL.rs.getString("Usu_Comentario");
				modeloTabla.addRow(new Object[]{N,IDU,UC,IDC});
			}
			//modeloTabla.addRow(datos);
			table.setModel(modeloTabla);
			ConexionSQL.psql.close();

		}catch(SQLException e){
			e.printStackTrace();
		}
		/***Finish Table***/
	}

	public static void nuevaTabla(){
		/****TABLE****/
		modeloTabla.setRowCount(0);

		String nameBox = (String) comboBox.getSelectedItem();
		if(comboBox.getSelectedIndex() == 0){
			sql1 = "SELECT * FROM usuario";
		}else{
			sql1 = "SELECT * FROM usuario WHERE Usu_Nombre='"+nameBox+"'";
		}

		try{
			ConexionSQL conne = new ConexionSQL();
			conne.conectar();
			ConexionSQL.psql=ConexionSQL.conn.prepareStatement(sql1);
			ConexionSQL.rs = ConexionSQL.psql.executeQuery();

			String N="", IDU="", UC="", IDC="";

			while(ConexionSQL.rs.next()){
				N = ConexionSQL.rs.getString("ID_Facebook");
				IDU = ConexionSQL.rs.getString("Usu_Nombre");
				UC = ConexionSQL.rs.getString("ID_Comentario");
				IDC = ConexionSQL.rs.getString("Usu_Comentario");
				modeloTabla.addRow(new Object[]{N,IDU,UC,IDC});
			}
			table.setModel(modeloTabla);
			ConexionSQL.psql.close();

		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
