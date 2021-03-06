/*
 * Autor: David Ochoa Gutierrez
 * Project name: Ghost Freak
 * */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
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
import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.ImageIcon;

import java.awt.Toolkit;


public class Main {

	private JFrame frame;
	public static JTable table;
	public static ConexionSQL conex = new ConexionSQL();
	
	Color grayMaterial1 = Color.decode("#616161");
	//public static boolean comprobador = true;

	public static JComboBox comboBox = new JComboBox();
	static DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel(); //modelo de combobox
	static DefaultTableModel modeloTabla = new DefaultTableModel();
	public static String sql1 = "";
	public static JTextField textN1;
	public static JTextField textN2;
	public static JTextField textN3;
	public static JTextField textTotalPalabras;
	public static JTextField textTotalMalas;
	public static JLabel lblNivel = new JLabel("");
	public static JTextArea textAreaBadWords = new JTextArea();
	public static JLabel labelPercentage = new JLabel("");
	public static JLabel labelImagen = new JLabel("");
	public static JLabel lblElUsuarioSe = new JLabel("");

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
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		//frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\logo48.png"));
		URL url = getClass().getResource("/img/logo48.png");
		frame.setIconImage(new ImageIcon(url).getImage());
		frame.setBounds(100, 100, 750, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setName("Interface");
		frame.setTitle("Sistemas Inteligentes");
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		//frame.getContentPane().setBackground(grayMaterial1);
		frame.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 11));
		comboBox.setBackground(Color.LIGHT_GRAY);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		table.setBackground(Color.LIGHT_GRAY);
		table.setRowSelectionAllowed(false);
		table.setBounds(12, 62, 624, 166);
		frame.getContentPane().add(table);


		JButton btnDiccionarios = new JButton("Diccionarios");
		btnDiccionarios.setBackground(Color.LIGHT_GRAY);
		btnDiccionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ParseDictionary pd = new ParseDictionary();
				pd.main(null);
			}
		});
		btnDiccionarios.setBounds(260, 239, 157, 25);
		frame.getContentPane().add(btnDiccionarios);

		JButton btnRedNeuronal = new JButton("Red Neuronal");
		btnRedNeuronal.setBackground(Color.LIGHT_GRAY);
		btnRedNeuronal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ParseNeural Pfann = new ParseNeural();
				Pfann.main();
			}
		});
		btnRedNeuronal.setBounds(464, 239, 171, 25);
		frame.getContentPane().add(btnRedNeuronal);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 62, 722, 166);
		frame.getContentPane().add(scrollPane);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(29, 270, 687, 7);
		frame.getContentPane().add(separator);
		
		JLabel lblSeleccionaMedioDe = new JLabel("Selecciona medio de analisis");
		lblSeleccionaMedioDe.setBounds(22, 244, 191, 14);
		frame.getContentPane().add(lblSeleccionaMedioDe);
		
		textN1 = new JTextField();
		textN1.setBackground(Color.LIGHT_GRAY);
		textN1.setBounds(444, 312, 86, 20);
		frame.getContentPane().add(textN1);
		textN1.setColumns(10);
		
		textN2 = new JTextField();
		textN2.setBackground(Color.LIGHT_GRAY);
		textN2.setBounds(444, 340, 86, 20);
		frame.getContentPane().add(textN2);
		textN2.setColumns(10);
		
		textN3 = new JTextField();
		textN3.setBackground(Color.LIGHT_GRAY);
		textN3.setBounds(444, 371, 86, 20);
		frame.getContentPane().add(textN3);
		textN3.setColumns(10);
		
		JLabel lblN = new JLabel("N1");
		lblN.setBounds(388, 312, 37, 14);
		frame.getContentPane().add(lblN);
		
		JLabel lblN_1 = new JLabel("N2");
		lblN_1.setBounds(388, 340, 46, 14);
		frame.getContentPane().add(lblN_1);
		
		JLabel lblN_2 = new JLabel("N3");
		lblN_2.setBounds(388, 371, 46, 14);
		frame.getContentPane().add(lblN_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(383, 399, 147, 7);
		frame.getContentPane().add(separator_1);
		
		JLabel lblPalabrasAnalizadas = new JLabel("Palabras analizadas");
		lblPalabrasAnalizadas.setBounds(413, 290, 117, 14);
		frame.getContentPane().add(lblPalabrasAnalizadas);
		
		JLabel lblTotalp = new JLabel("Total_P");
		lblTotalp.setBounds(383, 417, 59, 14);
		frame.getContentPane().add(lblTotalp);
		
		textTotalPalabras = new JTextField();
		textTotalPalabras.setBackground(Color.LIGHT_GRAY);
		textTotalPalabras.setBounds(444, 414, 86, 20);
		frame.getContentPane().add(textTotalPalabras);
		textTotalPalabras.setColumns(10);
		
		textTotalMalas = new JTextField();
		textTotalMalas.setBackground(Color.LIGHT_GRAY);
		textTotalMalas.setBounds(444, 442, 86, 20);
		frame.getContentPane().add(textTotalMalas);
		textTotalMalas.setColumns(10);
		
		JLabel lblTotalmal = new JLabel("Total_Mal");
		lblTotalmal.setBounds(383, 445, 66, 14);
		frame.getContentPane().add(lblTotalmal);
		
		
		lblElUsuarioSe.setBounds(29, 472, 213, 14);
		frame.getContentPane().add(lblElUsuarioSe);
		lblNivel.setForeground(Color.BLACK);
		
		
		lblNivel.setBounds(29, 486, 315, 24);
		frame.getContentPane().add(lblNivel);
		textAreaBadWords.setBackground(Color.LIGHT_GRAY);
		textAreaBadWords.setEditable(false);
		
		
		textAreaBadWords.setBounds(540, 285, 194, 225);
		frame.getContentPane().add(textAreaBadWords);
		
		
		//labelImagen.setIcon(new ImageIcon("img\\normal.gif"));
		labelImagen.setBounds(29, 277, 230, 185);
		frame.getContentPane().add(labelImagen);
		
		
		labelPercentage.setBounds(333, 486, 46, 14);
		frame.getContentPane().add(labelPercentage);
		
		JScrollPane scrollPane_1 = new JScrollPane(textAreaBadWords);
		scrollPane_1.setBounds(536, 282, 200, 228);
		frame.getContentPane().add(scrollPane_1);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About ab = new About();
				ab.main(null);
			}
		});
		//btnNewButton.setIcon(new ImageIcon("resources\\img\\about2.png"));
		try {
		    Image img = ImageIO.read(getClass().getResource("/img/about2.png"));
		   btnNewButton.setIcon(new ImageIcon(img));
		  	}catch (IOException ex) {
		  		JOptionPane.showMessageDialog(null, "No se pueden cargar las imagenes");
		  }
		//URL url1 = getClass().getResource("/img/about2.png");
		//btnNewButton.setIconImage(new ImageIcon(url1).getImage());
		btnNewButton.setBounds(666, 11, 66, 39);
		frame.getContentPane().add(btnNewButton);
	}

	public static void app(){
		String osType = new String(System.getProperty("os.arch"));//Salir porque FANN no es compatible con sistemas x64
		if(osType.equals("amd64")){
			JOptionPane.showMessageDialog(null, "Tu sistema operativo es "+osType+" (64bits) se necesita una arquitectura x86 (32bits) para funcionar correctamente");
			System.exit(0);
		}
		
		ImageIcon imageIcon = new ImageIcon(Main.class.getResource("img/normal.gif"));
		labelImagen.setIcon(imageIcon);
		String sql = "SELECT DISTINCT Usu_Nombre FROM usuario ORDER BY Usu_Nombre ASC"; /*Lllenado de ComboBox*/
		try{
			ConexionSQL conne = new ConexionSQL();
			conne.conectar();
			Statement a =ConexionSQL.psql=ConexionSQL.conn.prepareStatement(sql); //Cambio de variables debido a problemas de conexion y traslape de variables
			ResultSet b = ConexionSQL.rs = ConexionSQL.psql.executeQuery();

			comboBox.addItem("Analizar todo");
			System.out.println("Conexion para combobox");
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
		modeloTabla.setRowCount(0); //Agregado para borrar las tablas por defecto que carga el codigo
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
			lblElUsuarioSe.setText("El usuario se ha catalogado como:");
		}

		try{
			System.out.println(sql1);
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
