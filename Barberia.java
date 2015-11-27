import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//barberia
public class Barberia {
	
	Lock lock;

	private static final int NUM_DE_ASIENTOS = 3;
	private int numero_de_clientes = 0;
	
	private boolean corteacabado = false, invited=false;

	private Cliente en_silla;
	private Condition sitiolibre, invitacion, acabado, nuevo_cliente,preparado;


	public Barberia(){
		lock = new ReentrantLock();
		sitiolibre = lock.newCondition();
		invitacion = lock.newCondition();
		acabado = lock.newCondition();
		nuevo_cliente = lock.newCondition();
		preparado = lock.newCondition();
	}
	
	
	
	public void cortar_pelo() throws InterruptedException {
		lock.lock();
		
		try {
			while (nositioslibres()) 
				sitiolibre.await();
			
			System.out.println(Thread.currentThread().getName() + " tome asiento en la sala de espera");
			
			numero_de_clientes++;
			nuevo_cliente.signal();
			
			while(!invited)
				invitacion.await();
			invited = false;
			System.out.println(Thread.currentThread().getName() + " le ha invitado el barbero");
					
			numero_de_clientes--;
			sitiolibre.signal();
			System.out.println(Thread.currentThread().getName() + " abandona la sala de espera");
			
			/* go sit in Barbers chair */
			en_silla = (Cliente) Thread.currentThread();
			preparado.signal();
			
			System.out.println(Thread.currentThread().getName() + " se sienta en la silla de barbero");
			
			while (!corteacabado)
				acabado.await();
			corteacabado = false;
			
			System.out.println(Thread.currentThread().getName() + " abandona la barberia");
			
		} finally {
			lock.unlock();
		}
		
	}
	

	
	public  Cliente siguientecliente()throws InterruptedException{
		lock.lock();
		try {
			while (noclientes()){
				System.out.println("El barbero se va a dormir");
				nuevo_cliente.await();
			}

			invited = true;
			invitacion.signal();
			
			while (en_silla == null)
				preparado.await();
		
			return en_silla;
		} finally {
			lock.unlock();
		}
	
	}
	

	
	public void showOut(Cliente cliente){
		lock.lock();
		try{
			en_silla = null;
			corteacabado = true;
			acabado.signal();
		} finally{
			lock.unlock();
		}
	}
	
	private boolean noclientes(){
		return numero_de_clientes == 0;
	}
	
	private boolean nositioslibres(){
		return numero_de_clientes == NUM_DE_ASIENTOS;
	}
}
