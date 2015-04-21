import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Font;


public class About {
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	public JFrame frmAcercaDe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About window = new About();
					window.frmAcercaDe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public About() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAcercaDe = new JFrame();
		frmAcercaDe.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 11));
		frmAcercaDe.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Presionado en el frame - Cerrando");
				frmAcercaDe.setVisible(false);
			}
		});
		frmAcercaDe.getContentPane().setBackground(new Color(204, 204, 204));
		frmAcercaDe.getContentPane().setLayout(null);
		
		JLabel lblIntegrantes = new JLabel("Integrantes");
		lblIntegrantes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIntegrantes.setBounds(10, 11, 77, 14);
		frmAcercaDe.getContentPane().add(lblIntegrantes);
		
		JLabel lblDavid = new JLabel("David Ochoa Gutierrez");
		lblDavid.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblDavid.setBounds(20, 30, 183, 14);
		frmAcercaDe.getContentPane().add(lblDavid);
		
		JLabel lblNewLabel = new JLabel("Gustavo Davila Treviño");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel.setBounds(20, 45, 183, 14);
		frmAcercaDe.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Victor Manuel Martinez Moreno");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(20, 60, 183, 14);
		frmAcercaDe.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Francisco Albear Cardenas");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(20, 75, 183, 14);
		frmAcercaDe.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Diana Anaid Loza Cerda");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(20, 90, 183, 14);
		frmAcercaDe.getContentPane().add(lblNewLabel_3);
		
		JLabel label = new JLabel("Sistemas Inteligentes - FIME - UANL - Ene-Jun 2015");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		label.setBounds(10, 275, 280, 14);
		frmAcercaDe.getContentPane().add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 270, 300, 2);
		frmAcercaDe.getContentPane().add(separator);
		frmAcercaDe.setResizable(false);
		frmAcercaDe.setTitle("Acerca de");
		frmAcercaDe.setUndecorated(true);
		frmAcercaDe.setOpacity(0.95f);
		frmAcercaDe.setAlwaysOnTop(true);
		frmAcercaDe.setBounds(100, 100, 450, 300);
		frmAcercaDe.setSize(300, 300);
		frmAcercaDe.setLocation(dim.width/2-frmAcercaDe.getSize().width/2, dim.height/2-frmAcercaDe.getSize().height/2);
		//frmAcercaDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
