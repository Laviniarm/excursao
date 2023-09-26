import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Excursao {
	private ArrayList<String> reservas = new ArrayList<>();
	private int id;
	private double preco;
	private int vagas;
	private File f;

	public Excursao(int id, double preco, int vagas) throws Exception {
		this.id = id;
		this.preco = preco;
		this.vagas = vagas;

		if (vagas <= 0) {
			throw new Exception("Quantidade de vagas deve ser maior que 0!");
		}

		this.f = new File(new File(".\\" + id + ".csv").getCanonicalPath());

	}

	public void reservar(String cpf, String nome) throws Exception {

		FileWriter arq;
		boolean arquivoExiste = f.exists() && !f.isDirectory();

		if (arquivoExiste) {
			arq = new FileWriter(f, true);
		} else {
			arq = new FileWriter(f);
			arq.write("CPF;Nome\n");
		}

		arq.write(cpf + ";" + nome + "\n");
		arq.close();
	}

	public void cancelarIndividualmente(String cpf, String nome) throws Exception {

		File tempArq = new File("temp.csv");
		FileWriter tempWriter = new FileWriter(tempArq);

		Scanner scanner = new Scanner(f);

		boolean cabecalho = true;

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			if (cabecalho) {
				cabecalho = false;
				tempWriter.write(line + "\n");
				continue;
			}

			String[] parts = line.split(";");
			if (parts[0].equals(cpf) && parts[1].equals(nome)) {
				continue;
			}
			tempWriter.write(line + "\n");
		}

		scanner.close();

		tempWriter.close();
		f.delete();
		tempArq.renameTo(f);
	}

	public void cancelarCPF(String cpf) throws Exception {
//		String cpfdigitos = Integer.toString(cpf);
//		if (cpfdigitos.length() != 11) {
//			throw new Exception("CPF inválido!");
//		}

		File tempArq = new File("temp.csv");
		FileWriter tempWriter = new FileWriter(tempArq);

		Scanner scanner = new Scanner(f);

		boolean cabecalho = true;

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			if (cabecalho) {
				cabecalho = false;
				tempWriter.write(line + "\n");
				continue;
			}

			String[] parts = line.split(";");
			if (parts[0].equals(cpf)) {
				continue;
			}
			tempWriter.write(line + "\n");
		}

		scanner.close();

		tempWriter.close();
		f.delete();
		tempArq.renameTo(f);
	}

	public ArrayList<String> listarReservasPorCpf(String buscaCpf) throws Exception {
		Scanner arquivo = new Scanner(f);
		String linha, cpf, nome;
		ArrayList<String> reservasPorCpf = new ArrayList<>();
		String[] partes;
		arquivo.nextLine(); 
		while (arquivo.hasNextLine()) {
			linha = arquivo.nextLine(); 
			partes = linha.split(";"); 
			cpf = partes[0]; 
			nome = partes[1]; 
			if (buscaCpf.equals(" ")) {
				reservasPorCpf.add(cpf + "/" + nome);
			} else if (cpf.equals(buscaCpf)) {
				reservasPorCpf.add(cpf + "/" + nome);
			}
		}
		arquivo.close();
		return reservasPorCpf;
	}

	public ArrayList<String> listarReservasPorNome(String buscaNome) throws Exception {	
		Scanner arquivo = new Scanner(f);
		String linha, cpf, nome;
		ArrayList<String> reservasPorNome = new ArrayList<>();
		String[] partes;
		arquivo.nextLine(); 
		while (arquivo.hasNextLine()) {
			linha = arquivo.nextLine(); 
			partes = linha.split(";"); 
			cpf = partes[0]; 
			nome = partes[1]; 
			if (buscaNome.equals(" ")) {
				reservasPorNome.add(cpf + "/" + nome);
			} else if (nome.equals(buscaNome)) {
				reservasPorNome.add(cpf + "/" + nome);
			}
		}
		return reservasPorNome;
	}
}