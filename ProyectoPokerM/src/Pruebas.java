
public class Pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControlPoker p = new ControlPoker();
		
		Carta[] mazo1;
		
		mazo1 = new Carta[5];
		for(int i =0 ; i<5 ; i++)
		{
			mazo1[i] = new Carta();
		}
		
		p.llenarMazo();
		
		//p.repartir();
		p.imprimirMazo();
		
			
		
	}

}
