import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Excursao {
//	private ArrayList<String> reservas = new ArrayList<>();
	private File f;

	public Excursao(int id, double preco, int vagas) throws Exception {
		if (vagas <= 0)
			throw new Exception("Quantidade de vagas deve ser maior que 0!");

		this.f = new File(new File(".\\" + id + ".csv").getCanonicalPath());
	}

	public void reservar(int cpf, String nome) throws Exception {

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

	public void cancelarIndividualmente(int cpf, String nome) throws Exception {

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
	        if (Integer.parseInt(parts[0]) == cpf && parts[1].equals(nome)) {
	            continue;
	        }
	        tempWriter.write(line + "\n");
	    }

		scanner.close();

		tempWriter.close();
		f.delete();
		tempArq.renameTo(f);
	}

	public void cancelarCPF(int cpf) throws Exception {
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
	        if (Integer.parseInt(parts[0]) == cpf) {
	            continue;
	        }
	        tempWriter.write(line + "\n");
	    }

		scanner.close();

		tempWriter.close();
		f.delete();
		tempArq.renameTo(f);
	}
}