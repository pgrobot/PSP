package ejemplo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Actividad3 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Actividad3 window = new Actividad3();
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
	public Actividad3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCalculadora = new JButton("Calculadora");
		btnCalculadora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCalculadora.setIcon(new ImageIcon("C:\\Users\\pgonzalez8205\\Downloads\\calcu.PNG"));
		btnCalculadora.setSelectedIcon(null);
		btnCalculadora.setBounds(55, 241, 158, 67);
		frame.getContentPane().add(btnCalculadora);
		
		JButton btnBlocNotas = new JButton("Bloc de Notas");
		btnBlocNotas.setIcon(new ImageIcon("C:\\Users\\pgonzalez8205\\Downloads\\bloc.PNG"));
		btnBlocNotas.setSelectedIcon(new ImageIcon("C:\\Users\\pgonzalez8205\\Downloads\\5102524.png"));
		btnBlocNotas.setBounds(315, 241, 164, 67);
		frame.getContentPane().add(btnBlocNotas);
		
		JLabel lblNewLabel = new JLabel("PRACTICA 1 PSP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(184, 39, 143, 93);
		frame.getContentPane().add(lblNewLabel);
		
		ActionListener acN = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Clase ac = new Clase();
				String ruta = "C:\\WINDOWS\\system32\\notepad.exe";
				
				ac.ejecutarProceso(ruta);
				
				
				
			}
		};
		ActionListener acC = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Process p= new ProcessBuilder("CMD", "/C", "CALC").start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		
		btnBlocNotas.addActionListener(acN);
		btnCalculadora.addActionListener(acC);
	}
}
