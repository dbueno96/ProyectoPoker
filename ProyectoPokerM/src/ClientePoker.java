import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class ClientePoker extends JFrame implements Runnable, MouseListener  {

	private JTextArea campoDeTexto; 
//	private JTextField tField;
//	private JPanel panelExterior;
//	private JPanel panelInterior; 
//	private JPanel panelCartasFlop;
//	
//	private JPanel panelJugador; 
//	
//	private JButton botonRetirar;
//	private JButton botonPasar; 
//	private JButton botonSubirApuesta;
//	private JButton botonBajarApuesta; 
//	private JButton botonApostar;
//	
//	
//	private JLabel holdem;
//	private JLabel cover; 
 	private Carta[] cartas;
    private JButton apostar;
    private JButton igualar;
    private JButton pasar;
    private JButton retirarse;
    private JLabel cartas1;
    private JLabel cartas2;
    private JLabel cartas3;
    private JLabel[] cartasCentro;

	private ImageIcon imagen1 = new ImageIcon("src/Imagenes/fondo.png");		//imagen fondo
	private ImageIcon imagenEscala1 = new ImageIcon(imagen1.getImage().getScaledInstance(1200, 800, java.awt.Image.SCALE_DEFAULT));
	private ImageIcon imagen2 = new ImageIcon("src/Imagenes/boton.png");		//imagen fondo
	private ImageIcon imagenEscala2 = new ImageIcon(imagen2.getImage().getScaledInstance(150, 90, java.awt.Image.SCALE_DEFAULT));
	private ImageIcon imagen3 = new ImageIcon("src/Imagenes/reves.jpg");		//imagen fondo
	private ImageIcon imagenEscala3 = new ImageIcon(imagen3.getImage().getScaledInstance(120, 180, java.awt.Image.SCALE_DEFAULT));
	private ImageIcon imagenEscala33 = new ImageIcon(imagen3.getImage().getScaledInstance(80, 110, java.awt.Image.SCALE_DEFAULT));
	//	private ImageIcon imagenEscalaX = new ImageIcon(imagen.getImage().getScaledInstance(120, 180, java.awt.Image.SCALE_DEFAULT));

	private Socket socket; 
	private Scanner entrada;
	private Formatter salida; 
	private String miIdentidad;
	private boolean miTurno ;
	private String cartasRecibidas;
	private int  mesaDeJuego; // lA MESA 1 ES HOLDEM --- LA MESA 2 ES POKER CUBIERTO 
	private int posicionEnMesa; 
	private int valorApuesta;
	private int dineroRestante; 
	private boolean activoEnRonda; 
	private String aux;
	private Carta[] cartasFlop;
	private Carta[] cartasMano;
	
//	private GridBagConstraints gridBag;
	private String nombreHost ; 

	
//	private final int cliente0 = 0;
//	private final int cliente1 = 1;
//	private final int cliente2 = 2;
//	private final int cliente3 = 3; 
//	private final int cliente4 = 4; 
//	private final int cliente5 = 5; 
//	private final int cliente6 = 6; 
//	private final int cliente7 = 7; 
	
	
	
		
	public ClientePoker(String host, int juego) 
	{
		super("PROYECTO PI ");
		nombreHost = host; 
		cartasRecibidas = "";
		aux= "";
		miIdentidad = "";
		cartasFlop = new Carta[5];
		cartasMano = new Carta[7];
		mesaDeJuego = juego;
		
		//Cartas de el jugador
		for(int i = 0; i<7; i++)
		{
			cartasMano[i] = new Carta();
		}
    	cartas = new Carta[5];	
        for(int i =0;i<= 4;i++)
        { 
        	cartas[i] = new Carta();
        	cartas[i].setBounds(400+(i*140), 100, 120, 180); // ajusta el tamano y la posicion respecto a la ventana.
            cartas[i].setIcon(imagenEscala3);
            cartas[i].addMouseListener(this);
            this.add(cartas[i]);
    
        }
        
    	//Cartas Centro
    	cartasCentro = new JLabel[5];	
        for(int i =0;i<= 4;i++)
        { 
        	cartasCentro[i] = new JLabel();
        	cartasCentro[i].setBounds(400+(i*90), 480, 80, 110); // ajusta el tamano y la posicion respecto a la ventana.
            cartasCentro[i].setIcon(imagenEscala33);
            this.add(cartasCentro[i]);
    
        }
        
        //BOTONES DE ACCION
        
        //Apostar
        campoDeTexto = new JTextArea(6,120); 
		campoDeTexto.setEditable(false);
		campoDeTexto.setBounds(170,90,130,40);
		this.add(campoDeTexto);
		
        apostar = new JButton(" APOSTAR ");
		apostar.setBounds(170,130,130,40);	
		apostar.setBackground(new java.awt.Color(80,99,225));;
		apostar.setForeground(Color.WHITE);
		this.add(apostar);
		apostar.addMouseListener(this);
		
		//Igualar
        igualar = new JButton(" IGUALAR ");
		igualar.setBounds(170,180,130,40);
		igualar.setBackground(new java.awt.Color(44,64,193));;
		igualar.setForeground(Color.WHITE);
		this.add(igualar);
		igualar.addMouseListener(this);
		
		//Pasar
        pasar = new JButton("  PASAR  ");
		pasar.setBounds(170,230,130,40);
		pasar.setBackground(new java.awt.Color(33,52,175));;
		pasar.setForeground(Color.WHITE);
		this.add(pasar);
		pasar.addMouseListener(this);
		
		//Retirarse
        retirarse = new JButton("RETIRARSE");
		retirarse.setBounds(170,280,130,40);
		retirarse.setBackground(new java.awt.Color(10,26,134));;
		retirarse.setForeground(Color.WHITE);
		this.add(retirarse);
		retirarse.addMouseListener(this);


    




    	//FIN

        

        //Fondo 
		((JPanel)getContentPane()).setOpaque(false);
		JLabel fondo= new JLabel(); 
		fondo.setIcon(imagenEscala1); 
		getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
		fondo.setBounds(0,0,imagenEscala1.getIconWidth(),imagenEscala1.getIconHeight());
		
        // configuramos la ventanda
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
//		tField = new JTextField(); 
//		tField.setEditable(false);
//		tField.setText("JUEGO DE POKER");
//		add(tField , BorderLayout.NORTH);
//		
        
//		add(new JScrollPane (campoDeTexto), BorderLayout.SOUTH );
//		
//		panelExterior = new JPanel () ;
//		panelExterior.setLayout(new BorderLayout () );
//		panelExterior.setBackground(Color.BLACK); 
//		
//		panelJugador = new JPanel () ; 
//		panelJugador.setSize(50,50);
//		panelJugador.setBackground(Color.WHITE);
//		
//		
//		panelCartasFlop = new JPanel (new GridBagLayout() ); 
//		panelCartasFlop.setSize(400,400); 
//		
//		GridBagConstraints gridBag = new GridBagConstraints();
//		gridBag.insets = new Insets(10, 10, 10 , 10) ;
//		
//		
//		//panelCartasFlop.setBackground(Color.WHITE);
//		
//		
//		JLabel prueba = new JLabel(); 
//		prueba.setText("Prueba");
//		prueba.setSize(50, 30);
//		
//		
//		
//		
//		botonSubirApuesta = new JButton ("++");
//		botonBajarApuesta = new JButton ("--");
//		botonRetirar = new JButton ("Retirarse");
//		botonApostar = new JButton ("Apostar: "+ valorApuesta);
//		botonPasar = new JButton ("Pasar");
//	
//		
//		panelInterior = new JPanel(); 
//		panelInterior.setLayout(new BorderLayout() );
//		panelInterior.setBackground(Color.BLUE);
//		panelExterior.add(panelInterior, BorderLayout.CENTER);
//		
//		
//		
//		
//		panelInterior.add(panelJugador, BorderLayout.LINE_START);
//		panelInterior.add(panelCartasFlop, BorderLayout.CENTER);
////		
////		holdem = new JLabel(); 
////		holdem.setText("JUGAR MODO TEXAS HOLD'EM");
////		panelInterior.add(holdem, BorderLayout.WEST);
////		poner tamañoss
////		cover = new JLabel(); 
////		cover.setText("JUGAR MODO POKER CUBIERTO");
////		panelInterior.add(cover, BorderLayout.EAST);
////		
////		
////		holdem.addMouseListener (this); 
////		cover.addMouseListener(this);
////		
//		
//		mostrarCartas();
//		
//		add(panelExterior);
//		
//		setSize(800,800);
//		setVisible(true);
		
		
		iniciarCliente();
		
		
	}

	
	
	
	public void iniciarCliente() 
	{
		try
		{
			socket = new Socket(InetAddress.getByName(nombreHost), 12345) ;
			
			entrada = new Scanner(socket.getInputStream() );
			
			salida = new Formatter (socket.getOutputStream() );
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		ExecutorService ejecutar = Executors.newFixedThreadPool(1);
		ejecutar.execute(this); 
	}
	
	

//	public void setValorApuesta(int n)
//	{
//		if (valorApuesta + n < 0)
//		{
//			botonBajarApuesta.setVisible(false);
//			botonSubirApuesta.setVisible(true);
//			valorApuesta = 0;
//		}
//		else if (valorApuesta + n > dineroRestante)
//		{
//			botonSubirApuesta.setVisible(false);
//			botonBajarApuesta.setVisible(true);
//			valorApuesta = dineroRestante;
//		}
//		else 
//		{
//			valorApuesta += n; 
//			
//		}
//	}
	
	public void setDineroRestante (int n ) 
	{
		if (dineroRestante - n < 0 )
		{
			dineroRestante = 0; 
		}
		else 
			dineroRestante = dineroRestante - n; 
	}
	public void mostrarMesa ()
	{
		
	}
	
//	public void leerFlop ()
//	{
//		int palo = 100;
//		int numero = 100;
//				
//		for (int i = 0 ; i < 2 ; i++)
//		{
//			if(entrada.hasNextInt())
//			{
//				palo = entrada.nextInt(); 
//				entrada.nextLine();
//				numero = entrada.nextInt();
//				
//				cartasFlop[i]= new Carta(palo, numero);
//			}
//		}
//	}
//	
//	public void mostrarCartas()
//	{
//		//
//		ImageIcon img = new ImageIcon ("imgPoker/Baraja.jpg");
//		
//		for (int i = 0 ; i < 4 ; i++)
//		{
//			JLabel aux = new JLabel (); 
//			aux.setSize(30,30);
//			aux.setIcon(img);
////			gridBag.gridx += 10;
//			
//		}
//		
//		
//		
//	}
	
	public void enviarInfoCartas() // ENVIA LOS DATOS DE LAS CARTAS QUE TIENE EL CLIENTE AL SERVIDOR PARA QUE DETERMINE LA MANO .
	{
		for (int i = 0; i < 6 ; i ++)
		{
			salida.format("%d\n", cartasMano[i].getPalo() );
			salida.format("%d\n", cartasMano[i].getNumero());
			salida.flush();
			
		}
	}
	
//	public void enviarCartasDescarte()
//	{
//		for (int i = 0; i < cartasMano.length ; i++ )
//			
//		{
//			if (cartasMano[i].getDescartar())
//			{
//				salida.format("%d\n", cartasMano[i].getPalo() );
//				salida.format("%d\n", cartasMano[i].getNumero());
//				salida.flush();
//			}
//			
//		}
//		
//		//bandera para informa que ya no hay mas cartas para descarte
//		//salida.format("no mas descarte \n");
//		//salida.flush();
//		
//	}
	
@Override
	public void run() 
	{
	
		salida.format("%d\n",mesaDeJuego);
		salida.flush();
		miIdentidad = entrada.nextLine();
		System.out.println(miIdentidad);
		cartasRecibidas = entrada.nextLine();
		System.out.println("cartas "+cartasRecibidas);
		if(mesaDeJuego ==1)
		{
			recibirCartas(7);
		}
		else if(mesaDeJuego ==2)
		{
			recibirCartas(5);
		} 
		miTurno = (miIdentidad.equals("0"));
		
//		int mensaje = -1; 
//		if(entrada.hasNextInt())
//			mensaje = entrada.nextInt();
//		
//		if (mensaje == 0) // el primer mensaje que recibe es el de aceptación o rechazo en una mesa 
//		{
//			mostrarMensaje("LA MESA QUE DESEAS SE ENCUENTRA LLENA! \n INTENTA DE NUEVO MS TARDE O PRUEBA OTRA OPCIÓN \n");
//			
//		}
//		else  // si es aceptado, también recibo un tipo de mesa y una posicion en la mesa para el cliente.
//		{
//			mesaDeJuego = entrada.nextInt(); 
//			posicionEnMesa = entrada.nextInt(); 
//			dineroRestante = entrada.nextInt();  
//			
//		}
	
		while (true)
		{
			if(entrada.hasNextInt() )
				procesarMensaje(entrada.nextLine() );
		}
//
	}

	public String sacarParejas(String s,int pos)
	{
		String aux = "";
		char aux1 = 0;
		char aux2 = 0;
		
		aux1 = s.charAt(pos);
		aux2 = s.charAt(pos+1);
		
		aux = Character.toString(aux1)+ Character.toString(aux2);
		System.out.println("parejas "+aux);
		return aux;
	}


	public void recibirCartas(int num)
	{
		int contador = 0;
		int n =0;
		int p =0;
		
		for(int i= 0; i<num;i++)
		{
			
				n=Integer.parseInt(String.valueOf(sacarParejas( cartasRecibidas, contador)));
				cartasMano[i].setNumero(n);
				System.out.println("numero "+n);
				contador+=2;
				p=Integer.parseInt(String.valueOf(cartasRecibidas.charAt(contador)));
				cartasMano[i].setPalo(p);
				contador++;
				
			
		}
		
		for(int i=0; i<num; i++)
		{
			
			System.out.print(cartasMano[i].getNumero() + "  " + cartasMano[i].getPalo());
			
			System.out.println();
		}
	}
	
	public void procesarMensaje (String s)
	{
	
		if(s.equals("Jugador ha movido"))
		{
			aux = entrada.nextLine();
			
			mostrarMensaje(aux);
			 
				
				
		}
		
		else if(s.equals("El otro jugador se conecto. Ahora es su turno."))
		{
			mostrarMensaje(s);
			miTurno = true;
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
			
			
			aux = entrada.nextLine();
		
				
			mostrarMensaje(s);
			miTurno = true;
			
		
//			
//			if(tablero[posX][posY].getTipo() == 7)
//			{
//			mostrarMensaje("Bien hecho, tienes otro intento");
//			
//			puntaje++;
//			
//			mostrarMensaje("Tu puntaje es: "+puntaje);
//			miTurno = true;
//			
//			}
//			else 
			
				
				
		}
//		
//			
		else if(s.equals("eres el jugador 1"))
				{
					mostrarMensaje(s);
				}
//		else if(s.equals("Has ganado!!")) 
//		{
//			mostrarMensaje(s);
//		}
//		
//		else if(s.equals("El otro jugador se ha desconectado")) 
//		{
//			mostrarMensaje(s);
//		}
//		
//		
//		
//		else if(s.equals("El otro jugador ha ganado"))
//		{
//			posicion = entrada.nextLine();
//			for(int i = 0;i<posicion.length();i=i+2)
//			{
//				posX = Integer.parseInt(String.valueOf(posicion.charAt(i)));
//				posY = Integer.parseInt(String.valueOf(posicion.charAt(i+1)));
//				setImagen(posX, posY);
//			}
//			mostrarMensaje(s);
//		}
		
//		if(codigo == 0) //codigo para recibir el valor de apuesta a igualar, o apuesta mínima.
//		{
//			int auxInt= 0; 
//			miTurno = true;
//			auxInt= entrada.nextInt(); 
////			setValorApuesta(auxInt);
//		}
//		
//		else if(codigo == 1 ) //codigo para recibir un STRING para mostrar en el area de texto 
//		{
//			String auxString = "";
//			auxString = entrada.nextLine();
//			mostrarMensaje(auxString);  
//		}
//		else if(codigo == 2) //Codigo para recibe mensajes de String del cliente con la mano ganadora 
//		{
//			String auxString = entrada.nextLine(); //string con la mano ganadora
//			entrada.nextInt () ;
//			int auxInt = entrada.nextInt(); 
//			mostrarMensaje("Gana el jugador " + auxInt + " con :" + auxString+ "\n" ) ; 
//			activoEnRonda = true; 
//		}
//		else if (codigo == 3) //codigo para recibir monto cuando se gana la partida
//		{
//			int auxInt = entrada.nextInt(); 
//			
//			
//			
//		}
		
	}
	
	private void mostrarMensaje( final String mensajeAMostrar ){
		SwingUtilities.invokeLater( new Runnable() {
										 public void run() {
											campoDeTexto.append( mensajeAMostrar ); // actualiza la salida
										 } // fin del m�todo run
									 } // fin de la clase interna
								  ); // fin de la llamada a SwingUtilities.invokeLater
	} // fin del m�todo mostrarMensaje

	
	@Override
	public void mouseClicked(MouseEvent e) {
		mostrarMensaje("holii");
		
		// TODO Auto-generated method stub
		if(miTurno)
		{
			if (  e.getSource() == apostar)
			{
				int a = 0;
				salida.format("%d\n", a);
				salida.flush();
			}
			
			miTurno = false;
		}
		
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
//		if( e.getSource() == botonSubirApuesta )
//		{
//			setValorApuesta(10);
//		}
//		else if (e.getSource() == botonBajarApuesta)
//		{
//			setValorApuesta(-10);
//		}
		
//		if(e.getSource() == apostar)
//		{
//			salida.format("%d\n", valorApuesta); //lee del socket el valor de apuesta mínima 
//			salida.flush(); 
//			mostrarMensaje("Has Apostado " + valorApuesta +"\n");
//			mostrarMensaje("Turno Finalizado\n");
//			setDineroRestante(valorApuesta);  // resta el valor apostado al dinero restante
//			miTurno = false; 
//		}
//		else if(e.getSource() == retirarse )
//		{
//			salida.format("%d\n", 3); //CODIGO PARA RETIRARSEr
//			//salida.format("retirar); 
//			salida.flush() ; 
//			
//			activoEnRonda = false;
//			mostrarMensaje("Te retiras de la mano en curso.");
//			
//		}
//		else if(e.getSource () == pasar)
//		{
//			salida.format("%d\n", 2); // CODIGO PARA INDICAR QUE EL JUGADOR PASA 
//			//salida.format("pasar") ; 
//			salida.flush();
//			mostrarMensaje ("Has pasado tu turno \n");
//			miTurno = false; 
//			
//		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
//	private class Carta extends JButton implements MouseListener
//	{
//		private int palo;
//		private int numero;
//		private boolean descartar; 
//		
//		public Carta ()
//		{
//			palo = 0;
//			numero = 0; 
//			
//			this.addMouseListener(this);
//			
//		}
//		
//		public void setDescartar(boolean b)
//		{
//			descartar = b; 
//		}
//		
//		public boolean getDescartar()
//		{
//			return descartar; 
//		}
//		
//		public void setNumero(int x)
//		{
//			numero = x; 
//		}
//		
//		public void setPalo(int x)
//		{
//			palo = x;
//		}
//		
//		public int getPalo()
//		{
//			return palo;
//		}
//		
//		public int getNumero()
//		{
//			return numero;
//		}
//		
//		
//		
//	
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			
//			
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			
//			
//			//Administra el valor apostar haciendo click en los botones de subir o bajar la apuesta
//			
//			
//			
//			
//			
//			
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		
//		
//		
//	}



}
