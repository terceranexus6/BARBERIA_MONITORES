class Barbero extends Thread{
		private Barberia barberia;
		private Cliente cliente;
		
		public Barbero(Barberia LaBarberia){
			barberia = LaBarberia;
		}
		public void run(){
			while (true) {
				try {
					cliente = barberia.siguientecliente();				
					cortar();
					barberia.showOut(cliente);
				} catch (InterruptedException e){}
			}
		}
		private void cortar(){
			try {
				System.out.println("barbero corta " + cliente.getNombreCliente());
				Thread.sleep((int)(Math.random() * 100));
			} catch (InterruptedException e) {}			
		}
	}
