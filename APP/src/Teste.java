import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Teste {

    public static void main(String[] args) throws Exception {
    	Excursao e1 = new Excursao(1,20,10);
    	e1.criarReserva("123", "raiza");
    	
    	ArrayList<String> excursoes = new ArrayList<>();
		
		File pasta = new File(".\\src\\excursoes");
		
		if (pasta.isDirectory()) {
			System.out.println("ok1");
            String[] nomesArquivos = pasta.list();
            System.out.println("ok2");
            
            for (String nome : nomesArquivos) {
            	String[] partes = nome.split(".txt");
            	String codigo = partes[0];
            	excursoes.add(codigo);
            	System.out.println("ok3");
            }
            System.out.println(excursoes);
        } else {
        	JOptionPane.showMessageDialog(null, "O caminho especificado não é uma pasta ou não existe.");
        }
    }
}