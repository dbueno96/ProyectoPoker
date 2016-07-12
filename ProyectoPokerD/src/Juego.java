//@nombre: Jhon Alejandro Orobio 
//@código: 1533627
//@fecha: 17/junio/2016

public class Juego {
	private Casilla[][] tablero;
	private String aux;
	
	
	public Juego()
	{
		aux="";
		tablero = new Casilla[10][10];
		for (int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				tablero[i][j] = new Casilla();
				tablero[i][j].setTipo(0);
			}
	
		}
		
		
	}
	
	public String getAux() {
		return aux;
	}

	public void setAux(String aux) {
		this.aux = aux;
	}

	public void llenaOro()
	{
		int aleatorio1=0;
		int aleatorio2=0;
		int oro = 0;
	
		for(int i=0; oro <25; i++)
		{
			aleatorio1 =(int) (Math.random()*10 + 0);
			aleatorio2 =(int) (Math.random()*10 + 0);
			if(	tablero[aleatorio1][aleatorio2].getTipo() == 0)
			{
				tablero[aleatorio1][aleatorio2].setTipo(7);
				oro++;

			}
		
		}
	}
	
	public void llenaNumeros()
	{
	   	//Columna cero
	    for(int i=1; i<=8; i++)
	    {	  
	    	if(tablero[i][0].getTipo() == 7)
	    	{
	    		if ( tablero[i-1][0].getTipo() != 7)
	    		tablero[i-1][0].setTipo( tablero[i-1][0].getTipo() + 1);
	    		if ( tablero[i-1][1].getTipo() != 7)
				tablero[i-1][1].setTipo( tablero[i-1][1].getTipo() + 1);	
	    		if ( tablero[i]  [1].getTipo() != 7)
				tablero[i]  [1].setTipo( tablero[i]  [1].getTipo() + 1);
	    		if ( tablero[i+1][0].getTipo() != 7)
				tablero[i+1][0].setTipo( tablero[i+1][0].getTipo() + 1);
	    		if ( tablero[i+1][1].getTipo() != 7)
				tablero[i+1][1].setTipo( tablero[i+1][1].getTipo() + 1);
	    	}
	    }
		
	    //Columna nueve
	    for(int i=1; i<=8; i++)
	    {
	    	if(tablero[i][9].getTipo() == 7)
	    	{
	    		if( tablero[i-1][9].getTipo() != 7)
	    		tablero[i-1][9].setTipo( tablero[i-1][9].getTipo() + 1);	
	    		if( tablero[i-1][8].getTipo() != 7)
				tablero[i-1][8].setTipo( tablero[i-1][8].getTipo() + 1);
	    		if( tablero[i]  [8].getTipo() != 7)
				tablero[i]  [8].setTipo( tablero[i]  [8].getTipo() + 1);
	    		if( tablero[i+1][8].getTipo() != 7)
				tablero[i+1][8].setTipo( tablero[i+1][8].getTipo() + 1);
	    		if( tablero[i+1][9].getTipo() != 7)
				tablero[i+1][9].setTipo( tablero[i+1][9].getTipo() + 1);
	    	}
	    }
		 
	    //Fila cero
	    for(int i=1; i<=8; i++)
	    {
	    	if(tablero[0][i].getTipo() == 7)
	    	{
	    		if( tablero[0][i-1].getTipo() != 7)
	    		tablero[0][i-1].setTipo( tablero[0][i-1].getTipo() + 1);
	    		if( tablero[1][i-1].getTipo() != 7)
				tablero[1][i-1].setTipo( tablero[1][i-1].getTipo() + 1);	
	    		if( tablero[1]  [i].getTipo() != 7)
				tablero[1]  [i].setTipo( tablero[1]  [i].getTipo() + 1);
	    		if( tablero[0][i+1].getTipo() != 7)
				tablero[0][i+1].setTipo( tablero[0][i+1].getTipo() + 1);
	    		if( tablero[1][i+1].getTipo() != 7)
				tablero[1][i+1].setTipo( tablero[1][i+1].getTipo() + 1);
	    	}
	    }
	 
	    //Fila nueve
	    for(int i=1; i<=8; i++)
	    {
	    	if(tablero[9][i].getTipo() == 7)
	    	{
	    		if( tablero[9][i-1].getTipo() != 7)
	    		tablero[9][i-1].setTipo( tablero[9][i-1].getTipo() + 1);	
	    		if( tablero[8][i-1].getTipo() != 7)
				tablero[8][i-1].setTipo( tablero[8][i-1].getTipo() + 1);
	    		if( tablero[8]  [i].getTipo() != 7)
				tablero[8]  [i].setTipo( tablero[8]  [i].getTipo() + 1);	
	    		if( tablero[8][i+1].getTipo() != 7)
				tablero[8][i+1].setTipo( tablero[8][i+1].getTipo() + 1);	
	    		if( tablero[9][i+1].getTipo() != 7)
				tablero[9][i+1].setTipo( tablero[9][i+1].getTipo() + 1);
	    	}
	    }
	    
	    //Esquina Superior Izquierda
	    if(tablero[0][0].getTipo() == 7)
	    {
	    	if( tablero[0][1].getTipo() != 7)
			tablero[0][1].setTipo(   tablero[0][1].getTipo() + 1);
	    	if( tablero[1][0].getTipo() != 7)
			tablero[1][0].setTipo(   tablero[1][0].getTipo() + 1);
	    	if( tablero[1][1].getTipo() != 7)
			tablero[1][1].setTipo(   tablero[1][1].getTipo() + 1);
	    }
	    //Esquina superior derecha
	    if(tablero[0][9].getTipo() == 7)
	    {
	    	if( tablero[0][8].getTipo() != 7)
			tablero[0][8].setTipo(   tablero[0][8].getTipo() + 1);
	    	if( tablero[1][8].getTipo() != 7)
			tablero[1][8].setTipo(   tablero[1][8].getTipo() + 1);
	    	if( tablero[1][9].getTipo() != 7)
			tablero[1][9].setTipo(   tablero[1][9].getTipo() + 1);
	    }
	    //Esquina inferior izquierda
	    if(tablero[9][0].getTipo() == 7)
	    {
	    	if( tablero[8][0].getTipo() != 7)
			tablero[8][0].setTipo(   tablero[8][0].getTipo() + 1);
	    	if( tablero[8][1].getTipo() != 7)
			tablero[8][1].setTipo(   tablero[8][1].getTipo() + 1);
	    	if( tablero[9][1].getTipo() != 7)
			tablero[9][1].setTipo(   tablero[9][1].getTipo() + 1);
	    	
	    }
	    //Esquina inferior derecha
	    if(tablero[9][9].getTipo() == 7)
	    {
	    	if( tablero[9][8].getTipo() != 7)
			tablero[9][8].setTipo(   tablero[9][8].getTipo() + 1);
	    	if( tablero[8][8].getTipo() != 7)
			tablero[8][8].setTipo(   tablero[8][8].getTipo() + 1);
	    	if( tablero[8][9].getTipo() != 7)
			tablero[8][9].setTipo(   tablero[8][9].getTipo() + 1);
	    }
	    
	    //Cuadritos del centro
		for(int i=1; i<9; i++)
		{
			for(int j=1; j<9; j++)
			{
				if(tablero[i][j].getTipo() == 7)
				{
					if( tablero[i-1][j-1].getTipo() != 7)
					tablero[i-1][j-1].setTipo( tablero[i-1][j-1].getTipo() + 1);
					if( tablero[i-1]  [j].getTipo() != 7)
					tablero[i-1]  [j].setTipo( tablero[i-1]  [j].getTipo() + 1);	
					if( tablero[i-1][j+1].getTipo() != 7)
					tablero[i-1][j+1].setTipo( tablero[i-1][j+1].getTipo() + 1);
					if( tablero[i]  [j-1].getTipo() != 7)
					tablero[i]  [j-1].setTipo( tablero[i]  [j-1].getTipo() + 1);
					if( tablero[i]  [j+1].getTipo() != 7)
					tablero[i]  [j+1].setTipo( tablero[i]  [j+1].getTipo() + 1);
					if( tablero[i+1][j-1].getTipo() != 7)
					tablero[i+1][j-1].setTipo( tablero[i+1][j-1].getTipo() + 1);
					if( tablero[i+1]  [j].getTipo() != 7)
					tablero[i+1]  [j].setTipo( tablero[i+1]  [j].getTipo() + 1);
					if( tablero[i+1][j+1].getTipo() != 7)
					tablero[i+1][j+1].setTipo( tablero[i+1][j+1].getTipo() + 1);
				}
			}
		}
				
		//llena vacios
		
		
	}
	
	

	public void destapaVacios(int x, int y)
	{

		//Esquina superior izquierda
			if((x==0) && (y == 0))
			{
		    	if((tablero[0][1].getTipo() == 0) && (tablero[0][1].getEstado() == 0))
		    	{
		    		aux += Integer.toString(0) + Integer.toString(1);
		    		tablero[0][1].setEstado(1);
					destapaVacios(0,1);	
		    	}					
			    if((tablero[1][0].getTipo() == 0) && (tablero[1][0].getEstado() == 0))
			    {
					aux += Integer.toString(1) + Integer.toString(0);
					tablero[1][0].setEstado(1);
					destapaVacios(1,0);
			    }					
			    if(( tablero[1][1].getTipo() ==0) && ( tablero[1][1].getEstado() ==0))
				{
					aux += Integer.toString(1) + Integer.toString(1);
					tablero[1][1].setEstado(1);
					destapaVacios(1,1);
				}
			}
			//Esquina superior derecha
			if((x==0) && (y == 9))
			{
		    	if(( tablero[0][8].getTipo() == 0) && ( tablero[0][8].getEstado() == 0))
		    	{
		    		aux += Integer.toString(0) + Integer.toString(8);
		    		tablero[0][8].setEstado(1);
					destapaVacios(0,8);	
		    	}					
			    if(( tablero[1][8].getTipo() == 0) && ( tablero[1][8].getEstado() == 0))
			    {
					aux += Integer.toString(1) + Integer.toString(8);
					tablero[1][8].setEstado(1);
					destapaVacios(1,8);
			    }					
			    if(( tablero[1][9].getTipo() ==0)  && ( tablero[1][9].getEstado() ==0))
				{
					aux += Integer.toString(1) + Integer.toString(9);
					tablero[1][9].setEstado(1) ;
					destapaVacios(1,9);
				}
			}
			
			//Esquina inferior izquierda
			if((x==9) && (y == 0))
			{
		    	if(( tablero[8][0].getTipo() == 0) && ( tablero[8][0].getEstado() == 0))
		    	{
		    		aux += Integer.toString(8) + Integer.toString(0);
		    		tablero[8][0].setEstado(1) ;
					destapaVacios(8,0);	
		    	}					
			    if(( tablero[8][1].getTipo() == 0) && ( tablero[8][1].getEstado() == 0))
			    {
					aux += Integer.toString(8) + Integer.toString(1);
					tablero[8][1].setEstado(1);
					destapaVacios(8,1);
			    }					
			    if( (tablero[9][1].getTipo() == 0) && (tablero[9][1].getEstado() == 0))
				{
					aux += Integer.toString(9) + Integer.toString(1);
					tablero[9][1].setEstado(1);
					destapaVacios(9,1);
				}
			}
			    
				//Esquina inferior derecha
				if((x==9) && (y == 9))
				{
			    	if(( tablero[9][8].getTipo() == 0) && ( tablero[9][8].getEstado() == 0))
			    	{
			    		aux += Integer.toString(9) + Integer.toString(8);
			    		tablero[9][8].setEstado(1);
						destapaVacios(9,8);	
			    	}					
				    if(( tablero[8][8].getTipo() == 0) && ( tablero[8][8].getEstado() == 0))
				    {
						aux += Integer.toString(8) + Integer.toString(8);
						tablero[8][8].setEstado(1) ;
						destapaVacios(8,8);
				    }					
				    if(( tablero[8][9].getTipo() == 0) && ( tablero[8][9].getEstado() == 0))
					{
						aux += Integer.toString(8) + Integer.toString(9);
						tablero[8][9].setEstado(1);
						destapaVacios(8,9);
					}
				}
				
					
			   	//y cero
			    
			    	if((x!= 0) && (x!= 9) && (y == 0))
			    	{
			    		if (( tablero[x-1][0].getTipo() == 0) && ( tablero[x-1][0].getEstado() == 0))
			    		{
							aux += Integer.toString(x-1) + Integer.toString(0);
							tablero[x-1][0].setEstado(1);
							destapaVacios(x-1,0);
			    		}
			    		if (( tablero[x-1][1].getTipo() == 0) && ( tablero[x-1][1].getEstado() == 0))
			    		{
							aux += Integer.toString(x-1) + Integer.toString(1);
							tablero[x-1][1].setEstado(1);
							destapaVacios(x-1,1);
			    		}					
			    		if (( tablero[x]  [1].getTipo() == 0) && ( tablero[x]  [1].getEstado() == 0))
			    		{
							aux += Integer.toString(x) + Integer.toString(1);
							tablero[x]  [1].setEstado(1) ;
							destapaVacios(x,1);
			    		}
			    		if (( tablero[x+1][0].getTipo() == 0) && ( tablero[x+1][0].getEstado() == 0))
			    		{
							aux += Integer.toString(x+1) + Integer.toString(0);
							tablero[x+1][0].setEstado(1);
							destapaVacios(x+1,0);
			    		}
			    		if (( tablero[x+1][1].getTipo() == 0) && (tablero[x+1][1].getEstado() == 0))
			    		{
							aux += Integer.toString(x+1) + Integer.toString(1);
							tablero[x+1][1].setEstado(1);
							destapaVacios(x+1,1);
			    		}
	
			    	}
			    
				
			    //y nueve
			  
			    	if((x != 0) && (x != 9) &&(y == 9))
			    	{
			    		if(( tablero[x-1][9].getTipo() == 0) && ( tablero[x-1][9].getEstado() == 0))
			    		{
							aux += Integer.toString(x-1) + Integer.toString(9);
							tablero[x-1][9].setEstado(1);
							destapaVacios(x-1,9);
			    		}			
			    		if(( tablero[x-1][8].getTipo() == 0) && (tablero[x-1][8].getEstado() == 0))
			    		{
							aux += Integer.toString(x-1) + Integer.toString(8);
							tablero[x-1][8].setEstado(1);
							destapaVacios(x-1,8);
			    		}		
			    		if(( tablero[x]  [8].getTipo() == 0) && (tablero[x]  [8].getEstado() == 0))
			    		{
							aux += Integer.toString(x) + Integer.toString(8);
							tablero[x]  [8].setEstado(1);
							destapaVacios(x,8);
			    		}			
			    		if(( tablero[x+1][8].getTipo() == 0) && (tablero[x+1][8].getEstado()  == 0))
			    		{
							aux += Integer.toString(x+1) + Integer.toString(8);
							tablero[x+1][8].setEstado(1) ;
							destapaVacios(x+1,8);
			    		}				
			    		if(( tablero[x+1][9].getTipo() == 0) && (tablero[x+1][9].getEstado() == 0))
			    		{
							aux += Integer.toString(x+1) + Integer.toString(9);
							tablero[x+1][9].setEstado(1) ;
							destapaVacios(x+1,9);
			    		}

			    	}
			    
				 
			    //x cero

			    	if((x == 0) && (y != 0) && (y != 9))
			    	{
			    		if(( tablero[0][y-1].getTipo() == 0) && (tablero[0][y-1].getEstado()== 0))
			    		{
							aux += Integer.toString(0) + Integer.toString(y-1);
							tablero[0][y-1].setEstado(1);
							destapaVacios(0,y-1);
			    		}
			    		if(( tablero[1][y-1].getTipo() == 0) && (tablero[1][y-1].getEstado()  == 0))
			    		{
							aux += Integer.toString(1) + Integer.toString(y-1);
							tablero[1][y-1].setEstado(1) ;
							destapaVacios(1,y-1);
			    		}	
			    		if(( tablero[1]  [y].getTipo() == 0) && ( tablero[1]  [y].getEstado() == 0))
			    		{
							aux += Integer.toString(1) + Integer.toString(y);
							tablero[1]  [y].setEstado(1);
							destapaVacios(1,y);
			    		}
			    		if( (tablero[0][y+1].getTipo() == 0) && (tablero[0][y+1].getEstado()== 0))
			    		{
							aux += Integer.toString(0) + Integer.toString(y+1);
							tablero[0][y+1].setEstado(1);
							destapaVacios(0,y+1);
			    		}
			    		if(( tablero[1][y+1].getTipo() == 0) && (tablero[1][y+1].getEstado()== 0))
			    		{
							aux += Integer.toString(1) + Integer.toString(y+1);
							tablero[1][y+1].setEstado(1);
							destapaVacios(1,y+1);
			    		}
			    	}
			    
			 
			    //x nueve

			    	if((x== 9) && (y != 9) && (y != 0))
			    	{
			    		if(( tablero[9][y-1].getTipo() == 0) && (tablero[9][y-1].getEstado()  == 0))
			    		{
							aux += Integer.toString(9) + Integer.toString(y-1);
							tablero[9][y-1].setEstado(1);
							destapaVacios(9,y-1);
			    		}			    
			    		if(( tablero[8][y-1].getTipo() == 0) && (tablero[8][y-1].getEstado()  == 0))
			    		{
							aux += Integer.toString(8) + Integer.toString(y-1);
							tablero[8][y-1].setEstado(1) ;
							destapaVacios(8,y-1);
			    		}
			    		if((tablero[8]  [y].getTipo() == 0) && (tablero[8]  [y].getEstado() == 0))
			    		{
							aux += Integer.toString(8) + Integer.toString(y);
							tablero[8]  [y].setEstado(1) ;
							destapaVacios(8,y);
			    		}
						if(( tablero[8][y+1].getTipo() == 0) && (tablero[8][y+1].getEstado() == 0))
						{
							aux += Integer.toString(8) + Integer.toString(y+1);
							tablero[8][y+1].setEstado(1) ;
							destapaVacios(8,y+1);
						}
			    		if(( tablero[9][y+1].getTipo() == 0) && (tablero[9][y+1].getEstado() == 0))
			    		{
							aux += Integer.toString(9) + Integer.toString(y+1);
							tablero[9][y+1].setEstado(1);
							destapaVacios(9,y+1);
			    		}
					}
			    
					
			    	//Cuadros centro
				if((x != 0) && (x != 9) && (y != 0) && (y != 9))
				{
					if(( tablero[x-1][y-1].getTipo() == 0) && (tablero[x-1][y-1].getEstado() == 0))
					{
						aux += Integer.toString(x-1) + Integer.toString(y-1);
						tablero[x-1][y-1].setEstado(1) ;
						destapaVacios(x-1, y-1);
					}						
					if(( tablero[x-1]  [y].getTipo() == 0)  && ( tablero[x-1]  [y].getEstado() == 0))
					{
						aux += Integer.toString(x-1) + Integer.toString(y);
						tablero[x-1]  [y].setEstado(1);
						destapaVacios(x-1, y);
					}						
					if(( tablero[x-1][y+1].getTipo() == 0) && (tablero[x-1][y+1].getEstado()== 0))
					{
						aux += Integer.toString(x-1) + Integer.toString(y+1);
						tablero[x-1][y+1].setEstado(1);
						destapaVacios(x-1, y+1);
					}						
					if(( tablero[x]  [y-1].getTipo() == 0) && (tablero[x]  [y-1].getEstado()  == 0))
					{
						aux += Integer.toString(x) + Integer.toString(y-1);
						tablero[x]  [y-1].setEstado(1) ;
						destapaVacios(x, y-1);
					}						
					if(( tablero[x]  [y+1].getTipo() == 0) && (tablero[x]  [y+1].getEstado()== 0))
					{
						aux += Integer.toString(x) + Integer.toString(y+1);
						tablero[x]  [y+1].setEstado(1);
						destapaVacios(x, y+1);
					}				
					if(( tablero[x+1][y-1].getTipo() == 0) && (tablero[x+1][y-1].getEstado()== 0))
					{
						aux += Integer.toString(x+1) + Integer.toString(y-1);
						tablero[x+1][y-1].setEstado(1);
						destapaVacios(x+1, y-1);
					}						
					if(( tablero[x+1]  [y].getTipo() == 0) && (tablero[x+1]  [y].getEstado() == 0))
					{
						aux += Integer.toString(x+1) + Integer.toString(y);
						tablero[x+1]  [y].setEstado(1) ;
						destapaVacios(x+1, y);
					}						
					if(( tablero[x+1][y+1].getTipo() == 0) && (tablero[x+1][y+1].getEstado()== 0))
					{
						aux += Integer.toString(x+1) + Integer.toString(y+1);
						tablero[x+1][y+1].setEstado(1);
						destapaVacios(x+1, y+1);
					}
						
				}
				
		
		
	}
	
	public Casilla[][] getTablero() {
		return tablero;
	}

	

	public void pintar()
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				System.out.print(tablero[i][j].getTipo() + "  ");
			}
			System.out.println();
		}
	}
	
}
