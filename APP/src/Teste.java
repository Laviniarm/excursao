public class Teste {
	public static void main(String[] args) {
		try {
			Excursao excursao = new Excursao(1234);			
			excursao.carregar();
			System.out.println(excursao.listarReservasPorCpf(""));
			System.out.println("\ntotal=" + excursao.calcularValorTotal());
		} 
		catch (Exception erro) {
			System.out.println("-->" + erro.getMessage());
		}
	}
}