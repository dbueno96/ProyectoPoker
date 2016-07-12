import javax.swing.JFrame;


public class PruebaClientePoker {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	Menu juego ;  //declara obj. ClienteBuscaminas
	
	if(args.length == 0 )
		juego = new Menu ("127.0.0.1");
	else 
		juego = new Menu (args [0]);
	
	juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}//fin main


	
}
