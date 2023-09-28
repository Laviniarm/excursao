import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

public class APPExcursao {

	private JFrame frame;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JLabel label;
	private JLabel label_1;
	private JTextArea textArea;
	private JPopupMenu popupMenu;
	private Excursao excursao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					APPExcursao window = new APPExcursao();
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
	public APPExcursao() {
		initialize();
	}
	
	public void carregarItensMenu() {
		try {
			ArrayList<String> excursoes = new ArrayList<>();
			FileReader arq = new FileReader(".\\excursoes.txt");
			Scanner arquivo = new Scanner(arq);
			String linha;
			while (arquivo.hasNextLine()) {
				linha = arquivo.nextLine();
				excursoes.add(linha);
			}
			for (String item : excursoes) {
				JMenuItem menuItem = new JMenuItem(item);
				menuItem.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                	try {
	                		int codigoExcursao = Integer.parseInt(item);
		                    excursao = new Excursao(codigoExcursao);
		                    excursao.carregar();
		                    label.setText("Valor total: " + Double.toString(excursao.calcularValorTotal()));
	                	}
	                    catch (Exception ex) {
	                    	label.setText(ex.getMessage());
	                    }
	                }
	            });
				popupMenu.add(menuItem);
			}
		} catch (Exception ex) {
			label.setText(ex.getMessage());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setTitle("Plataforma de Excursões");
		frame.setResizable(false);
		frame.setBounds(100, 100, 425, 221);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		popupMenu = new JPopupMenu();
		
		carregarItensMenu();
		
		button = new JButton("Criar Excursão");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(JOptionPane.showInputDialog("Código da Excursão:"));
					String valor = JOptionPane.showInputDialog("Valor por pessoa:");
					valor.replaceAll( "," , "." );
					double preco = Double.parseDouble(valor);
					int vagas = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de vagas:"));
					
					Excursao excursao = new Excursao(id, preco, vagas);
					popupMenu.removeAll();
					carregarItensMenu();
				}
				catch (Exception ex) {
						label.setText(ex.getMessage());
				}
			}
		});
		button.setBounds(10, 11, 190, 23);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("Reservar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_1.setBounds(10, 76, 139, 23);
		frame.getContentPane().add(button_1);
		
		button_2 = new JButton("Cancelar Reserva");
		button_2.setBounds(10, 110, 139, 23);
		frame.getContentPane().add(button_2);
		
		button_3 = new JButton("Abrir Excursão");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        popupMenu.show(button_3, 0, button_3.getHeight());
			}
		});
		button_3.setBounds(209, 11, 190, 23);
		frame.getContentPane().add(button_3);
		
		button_4 = new JButton("Listar Reservas");
		button_4.setBounds(10, 144, 139, 23);
		frame.getContentPane().add(button_4);
		
		label = new JLabel("valor total aqui");
		label.setBounds(272, 45, 127, 20);
		frame.getContentPane().add(label);
		
		label_1 = new JLabel("nome da excursao aqui");
		label_1.setBounds(10, 45, 250, 20);
		frame.getContentPane().add(label_1);
		
		textArea = new JTextArea();
		textArea.setBounds(159, 75, 240, 92);
		frame.getContentPane().add(textArea);
	}
}
