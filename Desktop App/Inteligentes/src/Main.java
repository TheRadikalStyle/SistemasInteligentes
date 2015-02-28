import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;


public class Main {

	private JFrame frame;
	private JTable table;

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
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setName("Interface");
		frame.setTitle("Sistemas Inteligentes");
		frame.getContentPane().setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(12, 12, 230, 24);
		frame.getContentPane().add(comboBox);
		
		JLabel lblComboboxlabel = new JLabel("Selecciona el usuario");
		lblComboboxlabel.setBounds(260, 12, 280, 24);
		frame.getContentPane().add(lblComboboxlabel);
		
		table = new JTable();
		table.setBounds(12, 62, 624, 81);
		frame.getContentPane().add(table);
		
		JButton btnDiccionarios = new JButton("Diccionarios");
		btnDiccionarios.setBounds(29, 239, 157, 25);
		frame.getContentPane().add(btnDiccionarios);
		
		JButton btnRedNeuronal = new JButton("Red Neuronal");
		btnRedNeuronal.setBounds(395, 239, 171, 25);
		frame.getContentPane().add(btnRedNeuronal);
	}
}
