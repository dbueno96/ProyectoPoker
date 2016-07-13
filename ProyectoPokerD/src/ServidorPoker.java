 //@nombre: Jhon Alejandro Orobio 
//@código: 1533627
//@fecha: 17/junio/2016


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;



public class ServidorPoker {
	
	private Jugador[] jugadores;
	
	private Jugador[] jugadoresTexas;
	private Jugador[] jugadoresCubierto;
	private ControlJuego[] controles;
	private ServerSocket servidor; // socket servidor
	private int contador1;
	private int contador2;// contador del nï¿½mero de conexiones
	private int contador;
	private ExecutorService ejecutarJuego;

	private ControlPoker controlJuego;
	private Carta[] mazo1;
	private Carta[] mazo2;
	private Carta[] comunes;
	private Lock bloqueoJuego; // para bloquear el juego y estar sincronizado
	private Condition otroJugadorConectadoTexas;
	private Condition otroJugadorConectadoCubierto;// para esperar al otro jugador
	private Condition turnoOtroJugador; // para esperar el turno del otro jugador
	private int jugadorActualTexas;
	private int jugadorActualCubierto;
	
	private String auxTexas;
	private String auxCubierto;
	
	private int cuentaTurno;
	public ServidorPoker() 
	{
		auxTexas = "";
		auxCubierto = "";
		contador = 0;
		contador1 = 0;
		contador2 = 0;
		cuentaTurno = 0;
		mazo1 = new Carta[52];
		mazo2 = new Carta[52];
		comunes = new Carta[5];
		
		for(int i  = 0; i<52;i++)
		{
			mazo1[i] = new Carta();
			mazo2[i] = new Carta();
		}
		
		for(int i  = 0; i<5;i++)
		{
			comunes[i] = new Carta();
			
		}
		
		controlJuego = new ControlPoker();
		jugadorActualTexas = 0;
		jugadorActualCubierto = 0;
		bloqueoJuego = new ReentrantLock(); 
		otroJugadorConectadoTexas = bloqueoJuego.newCondition(); 
		otroJugadorConectadoCubierto = bloqueoJuego.newCondition(); 
		turnoOtroJugador = bloqueoJuego.newCondition();
		jugadores = new Jugador[8];
		controles = new ControlJuego[2];
		jugadoresTexas = new Jugador[4];
		jugadoresCubierto = new Jugador[4];
		controlJuego.llenarMazo();
		mazo1 = controlJuego.getMazo1();
		mazo2 = controlJuego.getMazo2();
		controlJuego.repartir(5,comunes,mazo1);
		
		
	}
		
	
	
	public void execute()
	{
		
		
		
		ejecutarJuego = Executors.newFixedThreadPool( 8 );
		
		try {
			servidor = new ServerSocket(12345,8);
			System.out.println("conexion");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//pintar();
		
		for ( int i = 0; i < jugadores.length; i++ ) {
			try {
				
				if(contador ==4)
				{
					contador = 0;
				}
				jugadores[i] = new Jugador( servidor.accept() );
				jugadores[i].setNumeroJugador(contador++);
				ejecutarJuego.execute(jugadores[i]);//establece la conexiï¿½n via Socket
//				System.out.println(jugadores[i].getNumeroJugador());
			
		} // fin de try
			catch ( IOException excepcionES ) 
			{
				excepcionES.printStackTrace();
				System.exit( 1 );
				
			} // fin de catch
		} // fin de for
//		 // fin de try
		
		
	}
	
	
	
	public boolean turnos(int jugador, Jugador jugadores[], int jugadorActual,String aux) 
	{
		boolean resultado = false;
		// mientras no sea el jugador actual, debe esperar su turno
		while ( jugador != jugadorActual )
		{
			
			
			bloqueoJuego.lock(); // bloquea el juego para esperar a que el otro jugador	haga su movmiento
			try{
				turnoOtroJugador.await(); // espera el turno de jugador
			} 
			catch ( InterruptedException excepcion ){
				excepcion.printStackTrace();
			} 
			finally
			{
				bloqueoJuego.unlock(); // desbloquea el juego despuï¿½s de esperar
			} 
		
		}
		
		
		
		
			if( (jugadores[jugadorActual].getJugada() !=  0))
			{
				jugadores[jugadorActual].setJugada(0);
				
				for (int i = 0;i < 3;i++)
	        	{
			 		cuentaTurno = ( cuentaTurno + 1 ) % 4;
	        		jugadores[cuentaTurno].otroJugadorMovio(jugadores,jugadorActual,aux);
	        		
	        		System.out.println("cambio"+" "+cuentaTurno);
	        	}
			 	
			 	 jugadorActual = ( jugadorActual + 1 ) % 4;
			 	   
			 	System.out.println("jugador actual23: "+jugadorActual); 
//	           
	            bloqueoJuego.lock(); // bloquea el juego para indicar al otro jugador que realice su movimiento
	            
	            try {
	                turnoOtroJugador.signal(); // indica al otro jugador que debe continuar
	            } 
	            finally {
	                bloqueoJuego.unlock(); // desbloquea el juego despues de avisar
	            } 
	        
				resultado = true;
			}
			 else 
			    {
			        resultado = false; // notifica al jugador que el movimiento fue inv�lido
			  // notifica al jugador que el movimiento fue invï¿½lido
			    }
			
			return resultado;
		
		
//		if (  (tablero[x][y].getTipo() == 7))
//        {
//			
//		
//			 	tablero[x][y].setEstado(1);
//	        	
//	//           
//				int contador = jugadores[jugadorActual].getMarcador();
//			 	aux += Integer.toString(x)+Integer.toString(y);
//			 	 
//			 	cuentaTurno = jugadorActual;
//		            
//				 	for (int i = 0;i < 3;i++)
//		        	{
//				 		cuentaTurno = ( cuentaTurno + 1 ) % 4;
//		        		jugadores[cuentaTurno].otroJugadorMovio(x,y,jugadores,jugadorActual,aux);
//		        		
//		        		System.out.println("cambio"+" "+cuentaTurno);
//		        	}
//				 	
//	            jugadores[jugadorActual].setMarcador(contador += 1);
//	     
//	            bloqueoJuego.lock(); // bloquea el juego para indicar al otro jugador que realice su movimiento
//	            
//	            try {
//	                turnoOtroJugador.signal(); // indica al otro jugador que debe con7tinuar
//	            } 
//	            finally {
//	                bloqueoJuego.unlock(); // desbloquea el juego despues de avisar
//	            } 
//	        
//	            resultado = true; // notifica al jugador que el movimiento fue vlido
//			
//            
//        }
//	 
//		if ((tablero[x][y].getTipo() != 7))
//        {
//        	tablero[x][y].setEstado(1);
//        	
//        	
//        	 cuentaTurno = jugadorActual;
//            
//		 	for (int i = 0;i < 3;i++)
//        	{
//		 		cuentaTurno = ( cuentaTurno + 1 ) % 4;
//        		jugadores[cuentaTurno].otroJugadorMovio(x,y,jugadores,jugadorActual,aux);
//        		
//        		System.out.println("cambio"+" "+cuentaTurno);
//        	}
//		 	
//		 	 jugadorActual = ( jugadorActual + 1 ) % 4;
//        	
//            // camb		aux="";ia el jugador
//            System.out.println(jugadorActual);
//            // permite al jugador saber que se realiz� un movimiento
//            
//            	    
//            bloqueoJuego.lock(); // bloquea el juego para indicar al otro jugador que realice su movimiento
//            
//            try {
//                turnoOtroJugador.signal(); // indica al otro jugador que debe continuar
//            } 
//            finally {
//                bloqueoJuego.unlock(); // desbloquea el juego despues de avisar
//            } 
//        
//            resultado = true; // notifica al jugador que el movimiento fue v�lido
//        } // fin de if
	        
	
		}
   
		

	
	
	public boolean terminarJuego()
	{
//		if (jugadores[0].getMarcador() == 5 || jugadores[1].getMarcador() == 5)
//		{
//			return true;
//		}
//		else 
//			return false;
		return false;
	}
	

	
	
	public class Jugador implements Runnable{

		private Socket conexion; // conexiï¿½n con el cliente
		private Scanner entrada; // entrada del cliente
		private Formatter salida; // salida al cliente
		private int numeroJugador; // identifica al Jugador
		private boolean suspendido = true; // indica si el subproceso estï¿½ suspendido
		private int tipoJuego;
		private Carta[] cartas;
		private int marcador;
		private int jugada;
		private int contador;

		

		private int auxInt;
		private int posY;
			
		
		
		public Jugador (Socket socket)
		{
			contador = 0;
			tipoJuego = 0;
			jugada = 0;
			conexion = socket;
			auxInt = 0;
			posY = 0;
			marcador =0;
			numeroJugador = 0;
			cartas = new Carta[7];
			for(int i = 0; i< 7; i++)
			{
				cartas[i] = new Carta();
			}
			
			try {
				entrada = new Scanner(conexion.getInputStream());
				salida = new Formatter(conexion.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void acomodarJugador()
		{
			if(tipoJuego == 1)
			{
				jugadoresTexas[contador1] = this;
				contador1++;
				
			}
			
			else if(tipoJuego == 2)
			{
				jugadoresCubierto[contador2] = this;
				contador2++;
			}
		}
		
		public void otroJugadorMovio(Jugador jugadores[],int jugadorActual,String aux)
		{
			if(terminarJuego() == true 
					&& jugadores[jugadorActual].getMarcador()!=13)
			{
				salida.format( "El otro jugador ha ganado\n" );
				salida.flush();
				salida.format("%s\n",aux);
				salida.flush();
			}
			else
			{
//				System.out.println(this.getJugada());
				System.out.println("jugador actual: "+jugadorActual); 
				salida.format("El otro jugador movio, es tu turno\n");
				salida.flush();
				
			}
	
		}
		
		public void establecerSuspendido(boolean b) {
			// TODO Auto-generated method stub
			suspendido = b;
		}

		@Override
		public void run() 
		{
		

			try{
			
				
				if(contador1 >= 3)
				{
					controles [0]=new ControlJuego(); 
					controles[0].setJugadorLocal(jugadoresTexas );
					controles[0].setOtroJugadorConectado(otroJugadorConectadoTexas);
					
					ejecutarJuego.execute( controles[0]);
					
					 // continï¿½a el jugador X
					// despierta el subproceso del jugador X// ejecuta el objeto Runnable jugador
				}
			
				if(contador2 >= 3)
				{
					controles [1]=new ControlJuego(); 
					controles[1].setJugadorLocal(jugadoresCubierto );
					controles[0].setOtroJugadorConectado(otroJugadorConectadoCubierto);
					
					ejecutarJuego.execute( controles[1]);
					
					// despierta el subproceso del jugador X
				}
				
				tipoJuego = entrada.nextInt();
				
				acomodarJugador();
				salida.format("%s\n",Integer.toString(numeroJugador));
				System.out.println("numero jugador: "+numeroJugador);
				salida.flush();
				
				if(tipoJuego ==1)
				{
					
					controlJuego.repartir(2,cartas,mazo1);
					for(int i = 2; i<7;i++)
					{
						cartas[i] = comunes[i-2];
						//System.out.print( cartas[i].getNumero()+ "  "+cartas[i].getPalo());
					}
					
				}
				
				if(tipoJuego ==2)
				{
				
					controlJuego.repartir(5,cartas,mazo2);
					
				}
				
				
				
				if(tipoJuego ==1)
				{
					
					for(int i = 0; i<7;i++)
					{
						if((Integer.toString(cartas[i].getNumero())).length()==2)
						{
							auxTexas+= Integer.toString(cartas[i].getNumero())+Integer.toString(cartas[i].getPalo());
						}
						else
						{
							auxTexas+= Integer.toString(0)+Integer.toString(cartas[i].getNumero())+Integer.toString(cartas[i].getPalo());
						}
						
//						System.out.println(auxTexas);
					}
//					System.out.println(auxTexas);
					salida.format("%s\n",auxTexas);
					auxTexas= "";
					salida.flush();
				}
				
				if(tipoJuego ==2)
				{
				
					for(int i = 0; i<7;i++)
					{
						if((Integer.toString(cartas[i].getNumero())).length()==2)
						{
							auxCubierto+= Integer.toString(cartas[i].getNumero())+Integer.toString(cartas[i].getPalo());
						}
						else
							auxCubierto+= Integer.toString(0)+Integer.toString(cartas[i].getPalo());
							
					}
					
					
					salida.format("%s\n",auxCubierto);
					auxCubierto= "";
					salida.flush();
				}
				
			
					
					if(numeroJugador == 0)
					{
					if(tipoJuego==1 )
					{
						try{
							salida.format("9\n");//eres el jugador 1
							salida.flush();
							bloqueoJuego.lock(); // bloquea el juego para esperar al segundo jugador
							
							while( suspendido ){
								
								otroJugadorConectadoTexas.await(); // espera al jugador O
							} // fin de while
						} // fin de try
						catch ( InterruptedException excepcion ){
							excepcion.printStackTrace();
						} // fin de catch
						finally {
							bloqueoJuego.unlock(); // desbloquea el juego 
						} // fin de finally
					}
					
					else if(tipoJuego==2 )
					{
						try{
							salida.format("9\n");
							salida.flush();
							bloqueoJuego.lock(); // bloquea el juego para esperar al segundo jugador
							
							while( suspendido ){
								
								otroJugadorConectadoCubierto.await(); // espera al jugador O
							} // fin de while
						} // fin de try
						catch ( InterruptedException excepcion ){
							excepcion.printStackTrace();
						} // fin de catch
						finally {
							bloqueoJuego.unlock(); // desbloquea el juego 
						} // fin de finally
					}
				
					
					
					salida.format( "El otro jugador se conecto. Ahora es su turno.\n" );
					salida.flush(); // vacï¿½a la salida
					}// fin de if
					else
					{
						salida.format("eres el jugador:\n" );
						//envía el numero de jugador para identificarlo 
						salida.flush(); // vacï¿½a la salida
					} 
				
			// fin de else
			while(!terminarJuego())
			{
			try
			{
						if(entrada.hasNext())
							{
							
							auxInt = entrada.nextInt();
						
							if(tipoJuego == 1)
								{
									
			
									jugadores[jugadorActualTexas].setJugada(auxInt);
								System.out.println(	jugadores[jugadorActualTexas].getNumeroJugador());
									
								}
								
								if(tipoJuego == 2)
								{
									jugadores[jugadorActualCubierto].setJugada(auxInt);
								
								}
							}
					}catch(IllegalStateException e){
						
						
					}
			
				
					
					if(tipoJuego == 1)
					{
						if(turnos(numeroJugador,jugadoresTexas,jugadorActualTexas,auxTexas))
						{
							contador++;
							System.out.println("juga: "+jugadorActualTexas);
							
							salida.format("Jugador ha movido\n");
							salida.flush();
							
							
						}
					}
					else if(tipoJuego == 2)
					{
						if(turnos(numeroJugador,jugadoresCubierto,jugadorActualCubierto,auxCubierto))
						{
							salida.format("Jugador ha movido\n");
							salida.flush();
							
						}
					} 
					else
					{
						salida.format("movimiento invalido\n");
						salida.flush();
					}
					
				}
			}
			finally {
				try {
					
					if (terminarJuego() == true 
							&& jugadores[jugadorActualTexas].getMarcador() ==5)
					{
						
						salida.format( "Has ganado!!" );
				 	 	jugadorActualTexas = ( jugadorActualTexas + 1 ) % 4;
				 	 	System.out.println(auxTexas);
			            jugadores[ jugadorActualTexas ].otroJugadorMovio(jugadoresTexas,jugadorActualTexas,auxTexas);
						salida.flush();
					}
					conexion.close(); // cierra la conexiï¿½n con el cliente
				} // fin de try
				catch ( IOException excepcionES ){
					excepcionES.printStackTrace();
					System.exit( 1 );
				} // fin de catch
				} 
			// TODO Auto-generated method stub
			//System.out.println("hablalo");
			
		}	
	
		

		public int getNumeroJugador() {
			return numeroJugador;
		}

		public void setNumeroJugador(int numeroJugador) {
			this.numeroJugador = numeroJugador;
		}

		public boolean isSuspendido() {
			return suspendido;
		}

		public void setSuspendido(boolean suspendido) {
			this.suspendido = suspendido;
		}
		
		public int getMarcador() {
			return marcador;
		}
	
		public void setMarcador(int marcador) {
			this.marcador = marcador;
		}
		
		public int getTipoJuego() {
			return tipoJuego;
		}

		public void setTipoJuego(int tipoJuego) {
			this.tipoJuego = tipoJuego;
		}
		
		public int getJugada() {
			return jugada;
		}

		public void setJugada(int jugada) {
			this.jugada = jugada;
		}


	}
		

	
	public class ControlJuego implements Runnable
	{
		
		private Jugador[] jugadorLocal;
		private Condition otroJugadorConectado;
	
		public Condition getOtroJugadorConectado() {
			return otroJugadorConectado;
		}

		public void setOtroJugadorConectado(Condition otroJugadorConectado) {
			this.otroJugadorConectado = otroJugadorConectado;
		}

		public ControlJuego()
		{
			jugadorLocal = new Jugador[4];
			otroJugadorConectado = bloqueoJuego.newCondition();
		}

		@Override
		public void run() 
		{
	
			
			bloqueoJuego.lock(); // bloquea el juego para avisar al subproceso del jugador X que inicie
			
			try {
				
					jugadorLocal[ 0 ].establecerSuspendido( false ); // continï¿½a el jugador X
					otroJugadorConectado.signal(); // despierta el subproceso del jugador X
				
				
			} // fin de try
			finally{
				bloqueoJuego.unlock(); // desbloquea el juego despuï¿½s de avisar al jugador X
			}
			
		
			
		
		}
		
		
		
	
		public Condition getOtroJugadorConectado1() {
			return otroJugadorConectado;
		}

		public void setOtroJugadorConectado1(Condition otroJugadorConectado) {
			this.otroJugadorConectado = otroJugadorConectado;
		}
		
		public Jugador[] getJugadorLocal() {
			return jugadorLocal;
		}
		
		public void setJugadorLocal(Jugador[] jugadorLocal) {
			this.jugadorLocal = jugadorLocal;
		}

	}
	

}
