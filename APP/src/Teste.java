
public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Excursao e1 = new Excursao(01, 15, 5);
			e1.reservar("123", "raiza");
			e1.reservar("245", "lucas");
			e1.reservar("245", "maria");
			e1.reservar("123", "godofredo");
			
			Excursao e2 = new Excursao(02, 25, 3);
			e2.reservar("456", "raizinha");
			
			e1.cancelarIndividualmente("123", "raiza");
			e1.cancelarCPF("245");
			
			System.out.println(e1.listarReservasPorCpf("123"));
			System.out.println(e2.listarReservasPorNome("raizinha"));
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}