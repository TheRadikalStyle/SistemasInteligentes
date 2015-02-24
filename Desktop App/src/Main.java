import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;


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
		lblComboboxlabel.setBounds(272, 0, 280, 24);
		frame.getContentPane().add(lblComboboxlabel);
		
		table = new JTable();
		table.setBounds(12, 42, 624, 81);
		frame.getContentPane().add(table);
	}
}
