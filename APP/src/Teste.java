public class Teste {
	public static void main(String[] args) {
		try {
			
			Excursao e1 = new Excursao(1, 15, 5);
			e1.criarReserva("123", "raiza");
			e1.criarReserva("245", "lucas");
			e1.criarReserva("245", "maria");
			e1.criarReserva("123", "godofredo");
			//e1.reservar("123", "godofredo");
			
			Excursao e2 = new Excursao(2, 25, 3);
			e2.criarReserva("456", "raizinha");
			
			e1.cancelarIndividualmente("123", "raiza");
			e1.cancelarCPF("245");
			
			System.out.println(e1.listarReservasPorCpf("123"));
			System.out.println(e2.listarReservasPorNome("raizinha"));


			e1.criarReserva("111", "joao");
			excursao.criarReserva("222", "maria");
			excursao.criarReserva("333", "jose");
			excursao.criarReserva("333", "paulo");
			excursao.criarReserva("333", "ana");
			excursao.criarReserva("555", "antonio");
			excursao.criarReserva("555", "joana");

			System.out.println("\nlistar todas as reservas");
			System.out.println(excursao.listarReservasPorCpf(""));
			System.out.println("\ntotal=" + excursao.calcularValorTotal());
		}
		catch (Exception erro) {
			System.out.println("-->" + erro.getMessage());
		}
	}
}