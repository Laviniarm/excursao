import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Excursao {
	private ArrayList<String> reservas = new ArrayList<>();
	private int id;
	private double preco;
	private int vagas;

	public Excursao(int id, double preco, int vagas) throws Exception {
		this.id = id;
		this.preco = preco;
		this.vagas = vagas;

		if (vagas <= 0) {
			throw new Exception("Quantidade de vagas deve ser maior que 0!");
		}
	}

	public Excursao(int id) throws Exception {
		this.id = id;
		
		if (id <= 0) {
			throw new Exception("O id deve ser um numero maior que 0!");
		}

	}

	public void criarReserva(String cpf, String nome) throws Exception {

		if (vagas == reservas.size()) {
			throw new Exception("A reserva não pode ser feita. Vagas esgotadas!");
		}

		for (String reserva : reservas) {
			String[] partes = reserva.split("/");
			String nome1 = partes[1];
			if (nome1.equals(nome)) {
				throw new Exception("A reserva não pode ser feita, ja existe uma reserva com esse nome");
			}
		}
		
		reservas.add(cpf + "/" + nome);
		salvar();
	}
	
	public void cancelarReserva(String... arg) throws Exception {
	    if (arg.length == 1) {
	        cancelarCPF(arg[0]);
	    } else if (arg.length == 2) {
	        cancelarIndividualmente(arg[0], arg[1]);
	    }
	}

	public void cancelarIndividualmente(String cpf, String nome) throws Exception {
		String pessoa = (cpf + "/" + nome);
		if(reservas.contains(pessoa)) {
			reservas.remove(pessoa);
		} else {
			throw new Exception(" Não existe reserva com esse cpf e nome para ser cancelada");
		}
		salvar();
	}

	public void cancelarCPF(String cpf) throws Exception {

		ArrayList<String> reservasARemover = new ArrayList<>();

		for (String reserva : reservas) {
			String[] partes = reserva.split("/");
			String cpf1 = partes[0];

			if (cpf1.equals(cpf)) {
				reservasARemover.add(reserva);
			}
		}

		reservas.removeAll(reservasARemover);
		salvar();

		if (reservasARemover.size() == 0) {
			throw new Exception("Não existe reserva com esse cpf, por isso não poderá ser removido");
		}
	}

	public ArrayList<String> listarReservasPorCpf(String cpf)  {
		ArrayList <String> reservasPorCpf = new ArrayList<>();

		for (String reserva : reservas) {
			String[] partes = reserva.split("/");
			String cpf1 = partes[0];

			if (cpf1.equals(cpf)) {
				reservasPorCpf.add(reserva);
			}

		}
		return reservasPorCpf;
	}

	public ArrayList<String> listarReservasPorNome(String nome)  {
		
		ArrayList <String> reservasPorNome = new ArrayList<>();

		for (String reserva : reservas) {
			String[] partes = reserva.split("/");
			String nome1 = partes[1];

			if (nome1.equals(nome)) {
				reservasPorNome.add(reserva);
			}

		}
		return reservasPorNome;
	}

	public double calcularValorTotal() {
		return preco * reservas.size();
	}
	
	public void salvar() throws Exception {
		File f = new File(new File(".\\" + id + ".txt").getCanonicalPath());
		FileWriter arquivo = new FileWriter(f, false);
		arquivo.write("Valor por pessoa: " + preco + "\nQuantidade de vagas: " + vagas + "\nReservas:\n");
		for (String reserva : reservas) {
			arquivo.write(reserva + "\n");
		}
		arquivo.close();
	}
	
	public void carregar() throws Exception {
		File f = new File(new File(".\\" + id + ".txt").getCanonicalPath());
		Scanner arquivo = new Scanner(f);
		String linha, cpf, nome;
		String[] partes;
		String valor = arquivo.nextLine();
		String quantidade = arquivo.nextLine();
		String titulo = arquivo.nextLine();
		while (arquivo.hasNextLine()) {
			linha = arquivo.nextLine();
			partes = linha.split("/");
			cpf = partes[0];
			nome = partes[1];
			reservas.add(cpf + "/" + nome);
		}
	}

	public String toString(){
		return "\nCódigo: " + id +
				"\nPreço: " + preco +
				"\nVagas: " + vagas +
				"\nTotal de reservas: " + reservas.size();
	}

}