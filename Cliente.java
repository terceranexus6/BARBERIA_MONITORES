class Cliente extends Thread{

	private Barberia barberia;
	public Cliente(String name, Barberia LaBarberia){
		super(name);
		barberia = LaBarberia;
	}
	public void run(){
		while (true) {	
			try{
				justLive();
				barberia.cortar_pelo();
			} catch (InterruptedException e){};
		}
	}

	private void justLive(){
		try {
			System.out.println(getName() + " living");
			Thread.sleep((int)(Math.random() * 1000));
		} catch (InterruptedException e) {}

	}

	public String getNombreCliente(){
		return getName();
	}
}
