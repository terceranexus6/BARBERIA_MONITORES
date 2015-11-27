public class Mundo {
	private static final int NUM_DE_CLIENTES = 2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Barberia barberia = new Barberia();
		Thread [] cliente;
		Thread barbero;
		cliente = new Thread[NUM_DE_CLIENTES];
		
		for(int i = 0; i<NUM_DE_CLIENTES; i++){
			cliente[i] = new Cliente ("c"+i, barberia);
			cliente[i].start();
		}
		barbero = new Barbero(barberia);
		barbero.start();

	}
}
