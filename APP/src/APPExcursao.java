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
import java.awt.Font;

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
	private JPopupMenu popupMenuListar;
	private JPopupMenu popupMenuCancelar;
	private Excursao excursao;
	private JButton button_6;
	private JLabel label_2;
	private JButton button_4;

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

	
	public void setarLabels() {
		label_2.setText("Total de reservas: " + excursao.tamanho());
		label.setText("Valor total: " + excursao.calcularValorTotal());
		button_4.doClick();
	}

	public void carregarItensMenu() {
		try {
			ArrayList<String> excursoes = new ArrayList<>();
			
			File pasta = new File(".\\src\\excursoes");
			
			if (pasta.isDirectory()) {
	            String[] nomesArquivos = pasta.list();
	            
	            for (String nome : nomesArquivos) {
	            	String[] partes = nome.split(".txt");
	            	String codigo = partes[0];
	            	excursoes.add(codigo);
	            }
	        } else {
	        	JOptionPane.showMessageDialog(null, "O caminho especificado não é uma pasta ou não existe.");
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
							button_4.setEnabled(true);
							String codigo = excursao.toString();
							String[] partes = codigo.split("\n");
							label_1.setText(partes[0]);
							label_2.setText("Total de reservas: " + excursao.tamanho());
							label.setText("Valor total: " + excursao.calcularValorTotal());
							textArea.setText("");
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Century", Font.PLAIN, 12));
		frame.getContentPane().setBackground(new Color(243, 245, 245));
		frame.setBackground(new Color(255, 255, 255));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setTitle("Plataforma de Excursões");
		frame.setResizable(false);
		frame.setBounds(100, 100, 332, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		popupMenuAbrir = new JPopupMenu();

		carregarItensMenu();

		button = new JButton("Criar Excursão");
		button.setFont(new Font("Century", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(JOptionPane.showInputDialog("Código da Excursão:"));
					String valor = JOptionPane.showInputDialog("Valor por pessoa:");
					double preco = Double.parseDouble(valor);
					int vagas = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de vagas:"));

					Excursao excursao = new Excursao(id, preco, vagas);
					popupMenuAbrir.removeAll();
					carregarItensMenu();
					textArea.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Insira um número válido.");
				}
			}
		});
		button.setBounds(10, 11, 139, 23);
		frame.getContentPane().add(button);

		button_1 = new JButton("Reservar");
		button_1.setFont(new Font("Century", Font.PLAIN, 12));
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String cpf, nome;
					cpf = JOptionPane.showInputDialog("Digite seu CPF:");
					nome = JOptionPane.showInputDialog("Digite seu nome:");

					if ((cpf != null && !cpf.isBlank()) && (nome != null && !nome.isBlank())) {
						excursao.criarReserva(cpf, nome);
						JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!");
						setarLabels();
					} else {
						throw new IllegalArgumentException("CPF e/ou nome não podem ser vazios.");
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		button_1.setBounds(10, 76, 139, 23);
		frame.getContentPane().add(button_1);

		button_2 = new JButton("Cancelar");
		button_2.setFont(new Font("Century", Font.PLAIN, 12));
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupMenuCancelar = new JPopupMenu();
				JMenuItem menuItemCPF = new JMenuItem("Em grupo");
				popupMenuCancelar.add(menuItemCPF);
				JMenuItem menuItemNome = new JMenuItem("Individual");
				popupMenuCancelar.add(menuItemNome);
				menuItemCPF.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String cpf = JOptionPane.showInputDialog("Digite o cpf das reservas que deseja cancelar:");
							excursao.cancelarReserva(cpf);
							setarLabels();
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
							setarLabels();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				});
				popupMenuCancelar.show(button_2, 0, button_2.getHeight());

			}
		});
		button_2.setBounds(10, 110, 139, 23);
		frame.getContentPane().add(button_2);

		button_3 = new JButton("Abrir Excursão");
		button_3.setFont(new Font("Century", Font.PLAIN, 12));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupMenuAbrir.show(button_3, 0, button_3.getHeight());
			}
		});
		button_3.setBounds(159, 11, 139, 23);
		frame.getContentPane().add(button_3);

		label = new JLabel("");
		label.setFont(new Font("Century Gothic", Font.BOLD, 12));
		label.setBounds(10, 212, 139, 20);
		frame.getContentPane().add(label);

		label_1 = new JLabel();
		label_1.setFont(new Font("Century Gothic", Font.BOLD, 12));
		label_1.setBounds(10, 45, 288, 20);
		frame.getContentPane().add(label_1);

		textArea = new JTextArea();
		textArea.setBounds(159, 75, 139, 126);
		frame.getContentPane().add(textArea);

		button_6 = new JButton("Listar");
		button_6.setFont(new Font("Century", Font.PLAIN, 12));
		button_6.setEnabled(false);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popupMenuListar = new JPopupMenu();
				JMenuItem menuItemC = new JMenuItem("Por cpf");
				popupMenuListar.add(menuItemC);
				JMenuItem menuItemN = new JMenuItem("Por Nome");
				popupMenuListar.add(menuItemN);

				menuItemC.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String cpf1 = JOptionPane.showInputDialog("Digite numeros do cpf que deseja listar:");
							ArrayList<String> lista = excursao.listarReservasPorCpf(cpf1);
							textArea.setText("");
							for (String s : lista)
								textArea.append(s + "\n");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				});

				menuItemN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String nome1 = JOptionPane.showInputDialog("Digite o nome que deseja listar:");
							ArrayList<String> listaN = excursao.listarReservasPorNome(nome1);
							textArea.setText("");
							for (String s : listaN)
								textArea.append(s + "\n");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
					}
				});

				popupMenuListar.show(button_6, 0, button_6.getHeight());

			}
		});

		button_6.setEnabled(false);
		button_6.setBounds(10, 144, 139, 23);
		frame.getContentPane().add(button_6);

		label_2 = new JLabel();
		label_2.setBounds(10, 76, 288, 20);
		frame.getContentPane().add(label_2);

		button_4 = new JButton("Listar todos");
		button_4.setFont(new Font("Century", Font.PLAIN, 12));
		button_4.setEnabled(false);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<String> listaTodos = excursao.listarReservasPorNome("");
					textArea.setText("");
					for (String s : listaTodos)
						textArea.append(s + "\n");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		button_4.setEnabled(false);
		button_4.setBounds(10, 178, 139, 23);
		frame.getContentPane().add(button_4);
	}
}
