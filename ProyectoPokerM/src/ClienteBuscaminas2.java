
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class ClienteBuscaminas2 extends JFrame implements Runnable {

	
		private JFrame contenedor;
		private JTextArea informacion;
		private JPanel panel,panel1;
		private Juego control;
		private ImageIcon imagen;
		private Casilla[][] tablero;
		private Socket conexion;
		private Formatter salida;
		private Scanner entrada;
		private String host;
		private EventosMouse eventos;
		private Casilla casillaAux;
		private int posX;
		private int posY;
		private String posicion;
		private boolean miTurno;
		private String miIdentidad;
		private String mensaje;
		private int puntaje;
		
		
		public ClienteBuscaminas2(String host)
		{
			puntaje = 0;
			contenedor  = new JFrame("Buscaminas");
			informacion = new JTextArea(50,8);
			posX = 0;
			posY = 0;
			posicion = "";
			miTurno = true;
			panel = new JPanel();
			panel1 = new JPanel();
			this.host = host;
			control = new Juego();
			casillaAux = new Casilla();
			eventos = new EventosMouse();
			panel.setLayout(new GridLayout(10,10,5,5) );
			mensaje = "";
			tablero = new Casilla[10][10];
			
			
			for (int i=0; i<10; i++)
			{
				for(int j=0; j<10; j++)
				{
					tablero[i][j] = new Casilla();
					
				}
		
			}
			
			imagen = new ImageIcon("src/images/fondo.gif");

			//System.out.print(imagen.getIconWidth());
			
			for(int i = 0; i<10;i++)
			{
				for(int j = 0 ; j< 10;j++)
				{
					panel.add(tablero[i][j]);
					tablero[i][j].setBackground(new java.awt.Color(255,0,77));
					tablero[i][j].setUbicacionX(i);
					tablero[i][j].setUbicacionY(j);
					tablero[i][j].addMouseListener(eventos);
				
				}
				System.out.println();
			}
		
			informacion.setEditable(false);
			panel1.add(informacion);
			
			panel1.setBounds(0,630,630,100);
			
		
		
		
		contenedor.setSize(500,500);
		contenedor.add(new JScrollPane(panel1), BorderLayout.WEST);
		contenedor.add(panel, BorderLayout.CENTER);
		contenedor.setVisible(true);
		contenedor.setResizable(false);
		
		iniciarCliente();
			
		}
		
		public void iniciarCliente()
		{
			// se conecta al servidor, obtiene los flujos e inicia subproceso de salida
			
			try {
				conexion = new Socket(InetAddress.getByName( host ), 12345 );
				salida = new Formatter(conexion.getOutputStream());
				entrada = new Scanner(conexion.getInputStream());
				
			}
			catch ( IOException excepcionES ){
				excepcionES.printStackTrace();
			} 

		// crea e inicia subproceso trabajador para este cliente
		ExecutorService trabajador = Executors.newFixedThreadPool( 1 );
		trabajador.execute( this ); // ejecuta el cliente
		}
		
	
		public void run() 
		{
			
			salida.format("%d\n", 2);
			salida.flush();
			miIdentidad = entrada.nextLine();
			System.out.println(miIdentidad);
			posicion = entrada.nextLine();
			llenarTablero();
			
			miTurno = (miIdentidad.equals("0"));
			
			
			
			while(true)
			{
				
				if(entrada.hasNextLine())
				{
					
					procesarCasilla( entrada.nextLine());
				}
			
			} 
		
		}
		
		public void llenarTablero()
		{
			int contador = 0;
			int x =0;
		
			for(int i= 0; i<10;i++)
			{
				for(int j= 0; j<10;j++)
				{
					x=Integer.parseInt(String.valueOf(posicion.charAt(contador)));
					tablero[i][j].setTipo(x);
					contador++;
				}
			}
			
			for(int i=0; i<10; i++)
			{
				for(int j=0; j<10; j++)
				{
					System.out.print(tablero[i][j].getTipo() + "  ");
				}
				System.out.println();
			}
		}
		
		public void procesarCasilla(String s)
		{
			
				if(s.equals("Jugador ha movido"))
				{
					posicion = entrada.nextLine();
					
					for(int i = 0;i<posicion.length();i=i+2)
					{
						posX = Integer.parseInt(String.valueOf(posicion.charAt(i)));
						posY = Integer.parseInt(String.valueOf(posicion.charAt(i+1)));
						setImagen(posX, posY);
						
						System.out.println(posicion);
					}
					if(tablero[posX][posY].getTipo() != 7)
					{
						mostrarMensaje("Espera un momento...");
					}
					 
						
						
				}
				
				else if(s.equals("El otro jugador se conecto. Ahora es su turno."))
				{
					mostrarMensaje(s);
				}
				
				else if(s.equals("Eres el jugador 2, por favor espere"))
				{
					mostrarMensaje(s);
				}
				
				else if (s.equals("movimiento invalido"))
				{
					
					mostrarMensaje(s);
					miTurno=true;
				}
					
				
				else if(s.equals("El otro jugador movio, es tu turno") )
				{
					
					
					posicion = entrada.nextLine();
					for(int i = 0;i<posicion.length();i=i+2)
					{
						posX = Integer.parseInt(String.valueOf(posicion.charAt(i)));
						posY = Integer.parseInt(String.valueOf(posicion.charAt(i+1)));
						SwingUtilities.invokeLater(new Runnable() {
							public void run(){
									// muestra la marca del jugador
								setImagen(posX, posY);
							} // fin del m�todo run
				} // fin de la clase interna an�nima
			);
						
					}
					
					if(tablero[posX][posY].getTipo() == 7)
					{
					mostrarMensaje("Bien hecho, tienes otro intento");
					
					puntaje++;
					
					mostrarMensaje("Tu puntaje es: "+puntaje);
					miTurno = true;
					
					}
					else 
						mostrarMensaje(s);
						miTurno = true;
						
						
				}
				
					
				else if(s.equals("eres el jugador 1"))
						{
							mostrarMensaje(s);
						}
				else if(s.equals("Has ganado!!")) 
				{
					mostrarMensaje(s);
				}
				
				else if(s.equals("El otro jugador se ha desconectado")) 
				{
					mostrarMensaje(s);
				}
				
				
				
				else if(s.equals("El otro jugador ha ganado"))
				{
					posicion = entrada.nextLine();
					for(int i = 0;i<posicion.length();i=i+2)
					{
						posX = Integer.parseInt(String.valueOf(posicion.charAt(i)));
						posY = Integer.parseInt(String.valueOf(posicion.charAt(i+1)));
						setImagen(posX, posY);
					}
					mostrarMensaje(s);
				}
				
	}
		
		
		public void mostrarMensaje(final String m)
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
				public void run()
				{
						// muestra la marca del jugador
					    informacion.append( m+"\n" );
				} // fin del m�todo run
			} // fin de la clase interna an�nima
					);
		}
		
		
		public void setImagen( int x, int y)
		{
			
			imagen = new ImageIcon("src/images/"+tablero[x][y].getTipo()+".png");
			ImageIcon imagenEscala = new ImageIcon(imagen.getImage().getScaledInstance(60, 70, java.awt.Image.SCALE_DEFAULT));
			tablero[x][y].setIcon(imagenEscala);
			
			
		}
		
		public void enviarInfo(String p)
		{
			
			
			salida.format("%s\n",p);
			salida.flush();
			miTurno = false;
			
		}
		
		public class EventosMouse implements MouseListener
		{

			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(miTurno)
				{
				posicion = "";
				posX = ((Casilla) e.getComponent()).getUbicacionX();
				posY = ((Casilla) e.getComponent()).getUbicacionY();
				posicion += Integer.toString(posX)+Integer.toString(posY);
				
				enviarInfo(posicion);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}

		
		// fin del m�todo iniciarCliente

		
		
		
		
		
}

