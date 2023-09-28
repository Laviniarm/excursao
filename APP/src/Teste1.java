public class Teste1 {
	public static void main(String[] args) {
		try {
			Excursao excursao = new Excursao(1234, 100.0, 20);
			System.out.println("excursao:" + excursao);
			
			Excursao e2 = new Excursao(5, 100.0, 20);
			Excursao e3 = new Excursao(10, 100.0, 20);

			excursao.criarReserva("111", "joao");
			excursao.criarReserva("222", "maria");
			excursao.criarReserva("333", "jose");
			excursao.criarReserva("333", "paulo");
			excursao.criarReserva("333", "ana");
			excursao.criarReserva("555", "antonio");
			excursao.criarReserva("555", "joana");

			System.out.println("\nlistar todas as reservas");
			
//			for(String s : excursao.listarReservasPorCpf("333")) {
//				System.out.println(s);
//			}
			
			System.out.println(excursao.listarReservasPorCpf(""));
			System.out.println("\nlistar as reservas por cpf");
			System.out.println(excursao.listarReservasPorCpf("3"));
			System.out.println("\nlistar as reservas por nome");
			System.out.println(excursao.listarReservasPorNome("jo"));

			excursao.cancelarReserva("111", "joao");
			System.out.println("aqui");
			excursao.cancelarReserva("333");

			System.out.println("\nlistar todas as reservas");
			System.out.println(excursao.listarReservasPorCpf(""));
			System.out.println("\nexcursao:" + excursao);
			System.out.println("\ntotal=" + excursao.calcularValorTotal());
		} 
		catch (Exception erro) {
			System.out.println("-->" + erro.getMessage());
		}

	}
}