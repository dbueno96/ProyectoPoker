
public class ControlPoker {
	
	private Carta[] mazo1;
	private Carta[] mazo2;
	private int bote;
	
	public  ControlPoker()
	{
		mazo1 = new Carta[52];
		
		for(int i =0 ; i<52 ; i++)
		{
			mazo1[i] = new Carta();
		}
		
		mazo2 = new Carta[52];
		
		for(int i =0 ; i<52 ; i++)
		{
			mazo2[i] = new Carta();
		}
		
	}
	
	public void llenarMazo()
	{
		int posicion = 0;
		for(int i= 1; i<=13 ; i++)
		{ 	
						
			for(int j=0; j<=3; j++)
			{
				mazo1[posicion].setNumero(i);				
				mazo1[posicion].setPalo(1+j);
				mazo2[posicion].setNumero(i);				
				mazo2[posicion].setPalo(1+j);
				posicion++;
			}
		}
	}
	
	
	
	public void repartir(int num,Carta[] comunes,Carta[]mazo)
	{
		int aleatorio1= 0;
		int contador = 0;
		
		while(contador != num)
		{
			aleatorio1 = (int)(Math.random()*52);
			
			while(mazo[aleatorio1].getNumero() != 0)
			{
				comunes[contador].setNumero(mazo[aleatorio1].getNumero());
				comunes[contador].setPalo(mazo[aleatorio1].getPalo());
				mazo[aleatorio1].setNumero(0);
				contador++;
				
			}
		}
	}
	
	public void ordenar(Carta[] mano){
        //Ordenar de las cartas en orden creciente
        Carta c; 
        for (int i=0; i< mano.length -1; i++) 
        {
            for (int j=i+1; j<mano.length; j++) 
            {
                if ( mano[i].getNumero() >mano[j].getNumero() )
                {
                    c= new Carta();
                    c.setPalo(mano[j].getPalo());
                    c.setNumero(mano[j].getNumero());
                    
                    mano[j].setPalo(mano[i].getPalo());
                    mano[j].setNumero(mano[i].getNumero()); 
                    
                    mano[i].setNumero(c.getNumero());
                    mano[i].setPalo(c.getPalo() );
                }
            }
        }
	}
	
	public Carta[] getMazo1() {
		return mazo1;
	}

	public Carta[] getMazo2() {
		return mazo2;
	}


	public int getBote() {
		return bote;
	}

	public void setBote(int bote) {
		this.bote = bote;
	}

	
	
	
	
	
}
