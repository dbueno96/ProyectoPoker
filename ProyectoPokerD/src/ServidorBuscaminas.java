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



public class ServidorBuscaminas {
	
	private Jugador[] jugadores;
	
	private Jugador[] jugadoresTexas;
	private Jugador[] jugadoresCubierto;
	private ControlJuego[] controles;
	private ServerSocket servidor; // socket servidor
	private int contador1;
	private int contador2;// contador del nï¿½mero de conexiones
	private int contador;
	private ExecutorService ejecutarJuego;
	private Casilla casillaAux;
	private Juego juego;
	private Casilla[][] tablero;
	private Lock bloqueoJuego; // para bloquear el juego y estar sincronizado
	private Condition otroJugadorConectado; // para esperar al otro jugador
	private Condition turnoOtroJugador; // para esperar el turno del otro jugador
	private int jugadorActualTexas;
	private int jugadorActualCubierto;
	private int tipoCasilla;
	private String aux;
	
	private int cuentaTurno;
	public ServidorBuscaminas() 
	{
		aux = "";
		contador = 0;
		contador1 = 0;
		contador2 = 0;
		cuentaTurno = 0 ;
		tipoCasilla = 0;
		jugadorActualTexas = 0;
		jugadorActualCubierto = 0;
		bloqueoJuego = new ReentrantLock(); 
		otroJugadorConectado = bloqueoJuego.newCondition(); 
		turnoOtroJugador = bloqueoJuego.newCondition();
		tablero = new Casilla[10][10];
		casillaAux = new Casilla();
		jugadores = new Jugador[8];
		controles = new ControlJuego[2];
		jugadoresTexas = new Jugador[4];
		jugadoresCubierto = new Jugador[4];
		juego =new Juego();
		tablero = juego.getTablero();
		juego.llenaOro();
		juego.llenaNumeros();
		
		for(int i = 0; i<10; i++)
		{
			for(int j = 0; j<10; j++)
			{
				System.out.print(tablero[i][j].getTipo()+"  ");
			}
			System.out.println();
		}
	
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
				//System.out.println(jugadores[i].getNumeroJugador());
				if(contador1 == 4)
					{
						controles [0]=new ControlJuego(); 
						controles[0].setJugadorLocal(jugadoresTexas );
						System.out.println("holi");
						ejecutarJuego.execute( controles[0]);
					
						 // continï¿½a el jugador X
						// despierta el subproceso del jugador X// ejecuta el objeto Runnable jugador
					}
				
				if(contador2 == 4)
				{
					controles [1]=new ControlJuego(); 
					controles[1].setJugadorLocal(jugadoresCubierto );
					System.out.println("holi");
					ejecutarJuego.execute( controles[1]);
					
					// despierta el subproceso del jugador X
				}
		} // fin de try
			catch ( IOException excepcionES ) 
			{
				excepcionES.printStackTrace();
				System.exit( 1 );
				
			} // fin de catch
		} // fin de for
//		bloqueoJuego.lock(); // bloquea el juego para avisar al subproceso del jugador X que inicie
		
//		bloqueoJuego.lock(); // bloquea el juego para avisar al subproceso del jugador X que inicie
		
		try {
//			jugadoresTexas[ 0 ].establecerSuspendido( false ); // continï¿½a el jugador X
//			jugadoresCubierto[ 0 ].establecerSuspendido( false );
			
		} // fin de try
		finally{
//		bloqueoJuego.unlock(); // desbloquea el juego despuï¿½s de avisar al jugador X
		}
		
	}
	
	
	
	public boolean turnos( int x, int y, int jugador, Jugador jugadores[], int jugadorActual) 
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
		
		
		
		if(validarYMover(x,y))
		{
			if( (tablero[x][y].getTipo() == 0))
			{
				tablero[x][y].setEstado(1);
				juego.destapaVacios(x, y);
				aux+=juego.getAux();
				
//	            jugadorActual = ( jugadorActual + 1 ) % 2; // camb		aux="";ia el jugador

	            // permite al jugador saber que se realiz� un movimiento
				
				
	            
	            	    
	            bloqueoJuego.lock(); // bloquea el juego para indicar al otro jugador que realice su movimiento
	            
	            try {
	                turnoOtroJugador.signal(); // indica al otro jugador que debe continuar
	            } 
	            finally {
	                bloqueoJuego.unlock(); // desbloquea el juego despues de avisar
	            } 
	        
				resultado = true;
			}
			
		
		
		if (  (tablero[x][y].getTipo() == 7))
        {
			
		
			 	tablero[x][y].setEstado(1);
	        	
	//           
				int contador = jugadores[jugadorActual].getMarcador();
			 	aux += Integer.toString(x)+Integer.toString(y);
			 	 
			 	cuentaTurno = jugadorActual;
		            
				 	for (int i = 0;i < 3;i++)
		        	{
				 		cuentaTurno = ( cuentaTurno + 1 ) % 4;
		        		jugadores[cuentaTurno].otroJugadorMovio(x,y,jugadores,jugadorActual);
		        		
		        		System.out.println("cambio"+" "+cuentaTurno);
		        	}
	           
	            jugadores[jugadorActual].setMarcador(contador += 1);
	     
	            bloqueoJuego.lock(); // bloquea el juego para indicar al otro jugador que realice su movimiento
	            
	            try {
	                turnoOtroJugador.signal(); // indica al otro jugador que debe con7tinuar
	            } 
	            finally {
	                bloqueoJuego.unlock(); // desbloquea el juego despues de avisar
	            } 
	        
	            resultado = true; // notifica al jugador que el movimiento fue vlido
			
            
        }
	 
		if ((tablero[x][y].getTipo() != 7))
        {
        	tablero[x][y].setEstado(1);
        	
        	
        	 cuentaTurno = jugadorActual;
            
		 	for (int i = 0;i < 3;i++)
        	{
		 		cuentaTurno = ( cuentaTurno + 1 ) % 4;
        		jugadores[cuentaTurno].otroJugadorMovio(x,y,jugadores,jugadorActual);
        		
        		System.out.println("cambio"+" "+cuentaTurno);
        	}
		 	
		 	 jugadorActual = ( jugadorActual + 1 ) % 4;
        	
            // camb		aux="";ia el jugador
            System.out.println(jugadorActual);
            // permite al jugador saber que se realiz� un movimiento
            
            	    
            bloqueoJuego.lock(); // bloquea el juego para indicar al otro jugador que realice su movimiento
            
            try {
                turnoOtroJugador.signal(); // indica al otro jugador que debe continuar
            } 
            finally {
                bloqueoJuego.unlock(); // desbloquea el juego despues de avisar
            } 
        
            resultado = true; // notifica al jugador que el movimiento fue v�lido
        } // fin de if
	        
	
		}
    else 
        resultado = false; // notifica al jugador que el movimiento fue inv�lido
  // notifica al jugador que el movimiento fue invï¿½lido
		return resultado;
		
}

	public void repartirJugadores(Jugador jugador)
	{
		if(jugador.tipoJuego == 1)
		{
			for(int i = 0; i<jugadoresTexas.length;i++)
			{
				
			}
				
		}
	}
	
	
	public boolean validarYMover(int x, int y)
	{
		
		if(tablero[x][y].getEstado() == 0)
		{
			
			return true;
		}

		
		else 
			return false;
	}
	
	
	public boolean terminarJuego()
	{
		if (jugadores[0].getMarcador() == 5 || jugadores[1].getMarcador() == 5)
		{
			return true;
		}
		else 
			return false;
		
	}
	

	
	
	public class Jugador implements Runnable{

		private Socket conexion; // conexiï¿½n con el cliente
		private Scanner entrada; // entrada del cliente
		private Formatter salida; // salida al cliente
		private int numeroJugador; // identifica al Jugador
		private boolean suspendido = true; // indica si el subproceso estï¿½ suspendido
		private int tipoJuego;
		
		private int marcador;
	

		private int posX;
		private int posY;
			
		
		
		public Jugador (Socket socket)
		{
			tipoJuego = 0;
			casillaAux = new Casilla();
			conexion = socket;
			posX = 0;
			posY = 0;
			marcador =0;
			numeroJugador = 0;
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
		
		public void otroJugadorMovio(int x, int y,Jugador jugadores[],int jugadorActual)
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
				aux+= Integer.toString(x)+Integer.toString(y);
				salida.format("El otro jugador movio, es tu turno\n");
				salida.flush();
				salida.format("%s\n",aux);
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
				
				tipoJuego = entrada.nextInt();
				System.out.println(tipoJuego);
				acomodarJugador();
				salida.format("%s\n",Integer.toString(numeroJugador));
				System.out.println(numeroJugador);
				salida.flush();
				
				for(int i = 0; i<10;i++)
				{
					for(int j = 0; j<10;j++)
					{
						aux+= Integer.toString(tablero[i][j].getTipo());
					}
				}
				
				salida.format("%s\n",aux);
				aux= "";
				salida.flush();
			
				if ( numeroJugador == 0 ||  numeroJugador == 0 )
				{
					salida.format("eres el jugador 1\n" );
					salida.flush();
					bloqueoJuego.lock(); // bloquea el juego para esperar al segundo jugador
					
					try{
						while( suspendido ){
							otroJugadorConectado.await(); // espera al jugador O
						} // fin de while
					} // fin de try
					catch ( InterruptedException excepcion ){
						excepcion.printStackTrace();
					} // fin de catch
					finally {
						bloqueoJuego.unlock(); // desbloquea el juego 
					} // fin de finally
					
					
					//envï¿½a un mensaje que ind1ica que el otro jugador se conectï¿½
					salida.format( "El otro jugador se conecto. Ahora es su turno.\n" );
					salida.flush(); // vacï¿½a la salida
				} // fin de if
				else {
					salida.format( "Eres el jugador 2, por favor espere\n" );
					salida.flush(); // vacï¿½a la salida
				} 
				
			// fin de else
			while(!terminarJuego())
			{
				
					try{
						if(tipoJuego == 1)
						{
						if(entrada.hasNext())
							{
								
								aux += entrada.nextLine();
								System.out.print(aux);
								posX = Integer.parseInt(String.valueOf(aux.charAt(aux.length()-2)));
								posY = Integer.parseInt(String.valueOf(aux.charAt(aux.length()-1)));
							}
						}
					}catch(IllegalStateException e){
						
						salida.format("El otro jugador se ha desconectado\n");
						salida.flush();
					}
					
					if(tipoJuego == 1)
					{
						if(turnos(posX,posY,numeroJugador,jugadoresTexas,jugadorActualTexas))
						{
							salida.format("Jugador ha movido\n");
							salida.flush();
							
							salida.format("%s\n",aux);
							salida.flush();
						}
					}
					else if(tipoJuego == 2)
					{
						if(turnos(posX,posY,numeroJugador,jugadoresCubierto,jugadorActualCubierto))
						{
							salida.format("Jugador ha movido\n");
							salida.flush();
							
							salida.format("%s\n",aux);
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
				 	 	jugadorActualTexas = ( jugadorActualTexas + 1 ) % 2;
				 	 	System.out.println(aux);
			            jugadores[ jugadorActualTexas ].otroJugadorMovio(posX,posY,jugadoresTexas,jugadorActualTexas);
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


	}
		

	public class ControlJuego implements Runnable
	{
		
		Jugador[] jugadorLocal;
		
	
		public ControlJuego()
		{
			jugadorLocal = new Jugador[4];
			
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
		
		
		
	
		
		public Jugador[] getJugadorLocal() {
			return jugadorLocal;
		}
		
		public void setJugadorLocal(Jugador[] jugadorLocal) {
			this.jugadorLocal = jugadorLocal;
		}

	}
	

}
