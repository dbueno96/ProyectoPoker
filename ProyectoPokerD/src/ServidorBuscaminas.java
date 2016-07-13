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
	private ServerSocket servidor; // socket servidor
	private int contador =1; // contador del nï¿½mero de conexiones
	private ExecutorService ejecutarJuego;
	private Casilla casillaAux;
	private Juego juego;
	private Casilla[][] tablero;
	private Lock bloqueoJuego; // para bloquear el juego y estar sincronizado
	private Condition otroJugadorConectado; // para esperar al otro jugador
	private Condition turnoOtroJugador; // para esperar el turno del otro jugador
	private int jugadorActual;
	private int tipoCasilla;
	private String aux;
	
	public ServidorBuscaminas()
	{
		aux = "";
		tipoCasilla = 0;
		jugadorActual = 0;
		bloqueoJuego = new ReentrantLock(); 
		otroJugadorConectado = bloqueoJuego.newCondition(); 
		turnoOtroJugador = bloqueoJuego.newCondition();
		tablero = new Casilla[10][10];
		casillaAux = new Casilla();
		jugadores = new Jugador[2];
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
		
		
		
		ejecutarJuego = Executors.newFixedThreadPool( 2 );
		try {
			servidor = new ServerSocket(12345,2);
			System.out.println("conexion");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//pintar();
		
		for ( int i = 0; i < jugadores.length; i++ ) {
			try {
				
				jugadores[i] = new Jugador( servidor.accept() );
				jugadores[i].setNumeroJugador(i);//establece la conexiï¿½n via Socket
				//System.out.println(jugadores[i].getNumeroJugador());
				ejecutarJuego.execute( jugadores[ i ] ); // ejecuta el objeto Runnable jugador
			} // fin de try
			catch ( IOException excepcionES ) {
				excepcionES.printStackTrace();
				System.exit( 1 );
				
			} // fin de catch
		} // fin de for
		bloqueoJuego.lock(); // bloquea el juego para avisar al subproceso del jugador X que inicie
		
		try {
			jugadores[ 0 ].establecerSuspendido( false ); // continï¿½a el jugador X
			otroJugadorConectado.signal(); // despierta el subproceso del jugador X
		} // fin de try
		finally{
			bloqueoJuego.unlock(); // desbloquea el juego despuï¿½s de avisar al jugador X
		}
		
	}
	
	
	
	public boolean turnos( int x, int y, int jugador) 
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
	            jugadores[ jugadorActual ].otroJugadorMovio( x,y );
	            	    
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
			 	
	            
	            jugadores[ jugadorActual ].otroJugadorMovio(x,y);
	           
	            jugadores[jugadorActual].setMarcador(contador += 1);
	            System.out.println(contador);
	            bloqueoJuego.lock(); // bloquea el juego para indicar al otro jugador que realice su movimiento
	            
	            try {
	                turnoOtroJugador.signal(); // indica al otro jugador que debe continuar
	            } 
	            finally {
	                bloqueoJuego.unlock(); // desbloquea el juego despues de avisar
	            } 
	        
	            resultado = true; // notifica al jugador que el movimiento fue vlido
			
            
        }
	 
		if ((tablero[x][y].getTipo() != 7))
        {
        	tablero[x][y].setEstado(1);
        	
//            tablero[ ubicacion ] = MARCAS[ jugadorActual ]; // establece el movimiento en el tablero
            jugadorActual = ( jugadorActual + 1 ) % 2; // camb		aux="";ia el jugador

            // permite al jugador saber que se realiz� un movimiento
            jugadores[ jugadorActual ].otroJugadorMovio( x,y );
            	    
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
		private ServidorBuscaminas servidor;
		private int marcador;
	

		private int posX;
		private int posY;
			
		
		
		public Jugador (Socket socket)
		{
			
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
		
		public void otroJugadorMovio(int x, int y)
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
				salida.format("%s\n",Integer.toString(numeroJugador));
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
			
				if ( numeroJugador == 0 )
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
						if(entrada.hasNext())
						{
							
							aux += entrada.nextLine();
							
							posX = Integer.parseInt(String.valueOf(aux.charAt(aux.length()-2)));
							posY = Integer.parseInt(String.valueOf(aux.charAt(aux.length()-1)));
						}
					}catch(IllegalStateException e){
						
						salida.format("El otro jugador se ha desconectado\n");
						salida.flush();
					}
					
					
					if(turnos(posX,posY,numeroJugador))
					{
						salida.format("Jugador ha movido\n");
						salida.flush();
						
						salida.format("%s\n",aux);
						salida.flush();
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
							&& jugadores[jugadorActual].getMarcador() ==5)
					{
						
						salida.format( "Has ganado!!" );
				 	 	jugadorActual = ( jugadorActual + 1 ) % 2;
				 	 	System.out.println(aux);
			            jugadores[ jugadorActual ].otroJugadorMovio(posX,posY);
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

	}
		

	

}
