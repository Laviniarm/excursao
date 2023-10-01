import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
	private JLabel label;
	private JLabel label_1;
	private JTextArea textArea;
	private JPopupMenu popupMenuAbrir;
	private JPopupMenu popupMenuCancelar;
	private Excursao excursao;
	private JButton button_6;
	private JLabel label_2;

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

	public boolean arquivoExiste() {
		return (new File(".\\excursoes.txt")).exists();
	}

	public void carregarItensMenu() {
		if (arquivoExiste()) {
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
								button_1.setEnabled(true);
								button_2.setEnabled(true);
								button_6.setEnabled(true);
								String codigo = excursao.toString();
								String[] partes = codigo.split("\n");
								label_1.setText(partes[0]);
								label_2.setText("Total de reservas: " + excursao.tamanho());
								label.setText("Valor total: " + excursao.calcularValorTotal());
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, ex.getMessage());
							}
						}
					});
					popupMenuAbrir.add(menuItem);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
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
		frame.setBounds(100, 100, 329, 284);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		popupMenuAbrir = new JPopupMenu();

		carregarItensMenu();

		button = new JButton("Criar Excursão");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(JOptionPane.showInputDialog("Código da Excursão:"));
					String valor = JOptionPane.showInputDialog("Valor por pessoa:");
					double preco = Double.parseDouble(valor);
					int vagas = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de vagas:"));

					Excursao excursao = new Excursao(id, preco, vagas);
					popupMenuAbrir.removeAll();
					if (arquivoExiste()) {
						FileWriter arq = new FileWriter(".\\excursoes.txt", true);
						arq.write(Integer.toString(id) + "\n");
						arq.close();
					} else {
						File arquivo = new File(new File(".\\excursoes.txt").getCanonicalPath());
						FileWriter arq = new FileWriter(".\\excursoes.txt", true);
						arq.write(Integer.toString(id) + "\n");
						arq.close();
					}
					carregarItensMenu();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		button.setBounds(10, 11, 139, 23);
		frame.getContentPane().add(button);

		button_1 = new JButton("Reservar");
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String cpf = JOptionPane.showInputDialog("Digite seu cpf:");
					String nome = JOptionPane.showInputDialog("Digite seu nome:");
					excursao.criarReserva(cpf, nome);
					label.setText("Valor total: " + excursao.calcularValorTotal());
					label_2.setText("Total de reservas: " + excursao.tamanho());
					JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		button_1.setBounds(10, 107, 139, 23);
		frame.getContentPane().add(button_1);

		button_2 = new JButton("Cancelar");
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupMenuCancelar = new JPopupMenu();
				JMenuItem menuItemCPF = new JMenuItem("Por CPF");
				popupMenuCancelar.add(menuItemCPF);
				JMenuItem menuItemNome = new JMenuItem("Por Nome");
				popupMenuCancelar.add(menuItemNome);
				menuItemCPF.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String cpf = JOptionPane.showInputDialog("Digite o cpf da reserva que deseja cancelar:");
							excursao.cancelarReserva(cpf);
							label_2.setText("Total de reservas: " + excursao.tamanho());
							label.setText("Valor total: " + excursao.calcularValorTotal());
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				});
				menuItemNome.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String cpf = JOptionPane.showInputDialog("Digite o cpf da reserva que deseja cancelar:");
							String nome = JOptionPane.showInputDialog("Digite o  nome da reserva que deseja cancelar:");
							excursao.cancelarReserva(cpf, nome);
							label_2.setText("Total de reservas: " + excursao.tamanho());
							label.setText("Valor total: " + excursao.calcularValorTotal());
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				});
				popupMenuCancelar.show(button_2, 0, button_2.getHeight());

			}
		});
		button_2.setBounds(10, 141, 139, 23);
		frame.getContentPane().add(button_2);

		button_3 = new JButton("Abrir Excursão");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupMenuAbrir.show(button_3, 0, button_3.getHeight());
			}
		});
		button_3.setBounds(159, 11, 139, 23);
		frame.getContentPane().add(button_3);

		label = new JLabel("");
		label.setBounds(10, 209, 139, 20);
		frame.getContentPane().add(label);

		label_1 = new JLabel();
		label_1.setBounds(10, 45, 288, 20);
		frame.getContentPane().add(label_1);

		textArea = new JTextArea();
		textArea.setBounds(159, 110, 139, 119);
		frame.getContentPane().add(textArea);

		button_6 = new JButton("Listar");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_6.setEnabled(false);
		button_6.setBounds(10, 175, 139, 23);
		frame.getContentPane().add(button_6);

		label_2 = new JLabel();
		label_2.setBounds(10, 76, 288, 20);
		frame.getContentPane().add(label_2);
	}
}
