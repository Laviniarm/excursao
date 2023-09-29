
public class Teste3 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Excursao e1 = new Excursao(2);
		e1.carregar();
		System.out.println(e1.listarReservasPorCpf("111"));
	}

}
