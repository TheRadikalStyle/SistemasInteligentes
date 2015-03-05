/*
 * Autor: David Ochoa Gutierrez
 * Project name: Ghost Freak
 * */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;


public class Main {

	private JFrame frame;
	public static JTable table;
	public static ConexionSQL conex = new ConexionSQL();

	public static JComboBox comboBox = new JComboBox();
	static DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel(); //modelo de combobox
	static DefaultTableModel modeloTabla = new DefaultTableModel();
	public static String sql1 = "";
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

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
		btnDiccionarios.setBounds(187, 239, 157, 25);
		frame.getContentPane().add(btnDiccionarios);

		JButton btnRedNeuronal = new JButton("Red Neuronal");
		btnRedNeuronal.setBounds(391, 239, 171, 25);
		frame.getContentPane().add(btnRedNeuronal);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 62, 622, 166);
		frame.getContentPane().add(scrollPane);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(29, 275, 591, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblSeleccionaMedioDe = new JLabel("Selecciona medio de analisis");
		lblSeleccionaMedioDe.setBounds(22, 244, 155, 14);
		frame.getContentPane().add(lblSeleccionaMedioDe);
		
		textField = new JTextField();
		textField.setBounds(534, 312, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(534, 340, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(534, 371, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblN = new JLabel("N1");
		lblN.setBounds(503, 315, 37, 14);
		frame.getContentPane().add(lblN);
		
		JLabel lblN_1 = new JLabel("N2");
		lblN_1.setBounds(503, 343, 46, 14);
		frame.getContentPane().add(lblN_1);
		
		JLabel lblN_2 = new JLabel("N3");
		lblN_2.setBounds(503, 374, 46, 14);
		frame.getContentPane().add(lblN_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(523, 399, 100, 7);
		frame.getContentPane().add(separator_1);
		
		JLabel lblPalabrasAnalizadas = new JLabel("Palabras analizadas");
		lblPalabrasAnalizadas.setBounds(517, 288, 117, 14);
		frame.getContentPane().add(lblPalabrasAnalizadas);
		
		JLabel lblTotalp = new JLabel("Total_P");
		lblTotalp.setBounds(484, 417, 59, 14);
		frame.getContentPane().add(lblTotalp);
		
		textField_3 = new JTextField();
		textField_3.setBounds(534, 414, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(534, 442, 86, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblTotalmal = new JLabel("Total_Mal");
		lblTotalmal.setBounds(484, 445, 46, 14);
		frame.getContentPane().add(lblTotalmal);
		
		JLabel lblElUsuarioSe = new JLabel("El usuario se ha catalogado como:");
		lblElUsuarioSe.setBounds(29, 417, 213, 14);
		frame.getContentPane().add(lblElUsuarioSe);
		
		JLabel lblNivel = new JLabel("Nivel");
		lblNivel.setBounds(39, 445, 138, 14);
		frame.getContentPane().add(lblNivel);
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
			JOptionPane.showMessageDialog(null, "Error de conexion a la Base de Datos, intenta nuevamente");
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
