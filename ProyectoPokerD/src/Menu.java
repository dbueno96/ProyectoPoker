
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JFrame implements MouseListener{

	private Container contenedor;
    private JLabel titulo;
    private JLabel imagenTexas;
    private JLabel imagenPoker; 
	private JButton texas;
	private JButton poker;
	private BorderLayout organizadorBorde;
	private FlowLayout organizadorBotones;
	
	private ImageIcon imagen1 = new ImageIcon("Imagenes/texas.png");		
	private ImageIcon imagenEscala1 = new ImageIcon(imagen1.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_DEFAULT));
	
	private ImageIcon imagen2 = new ImageIcon("Imagenes/poker.png");		
	private ImageIcon imagenEscala2 = new ImageIcon(imagen2.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_DEFAULT));
	
	private String nombreHost;
	
	public Menu(String host)
	{
		super("Poker");
		contenedor = getContentPane();
		nombreHost = host; 
		
		organizadorBorde = new BorderLayout();
		organizadorBotones = new FlowLayout();
        titulo = new JLabel();
        imagenTexas = new JLabel();
        imagenPoker = new JLabel();

		contenedor.setLayout(null);
		
        texas = new JButton("TEXAS HOLD'EM");
	    poker = new JButton("POKER CUBIERTO");
	   
	    
	    texas.addMouseListener(this);
	    texas.setBackground(Color.RED);
	    poker.addMouseListener(this);
	    poker.setBackground(Color.RED);

	    ImageIcon imagen;			
		imagen = new ImageIcon("Imagenes/titulo.png");		
		ImageIcon imagenEscala = new ImageIcon(imagen.getImage().getScaledInstance(380, 180, java.awt.Image.SCALE_DEFAULT));
		
	       
        titulo.setIcon(imagenEscala);
        titulo.setBounds(220, 20, 400, 200);
        
        
        imagenTexas.setBounds(50, 180, 450, 250);
        imagenPoker.setBounds(600, 140, 350, 350);
        
      
	    texas.setBounds(350, 250, 150, 50);
	    poker.setBounds(350, 350, 150, 50);
	    
	    
	    contenedor.setBackground(Color.DARK_GRAY);	
	    contenedor.add(titulo);
	    contenedor.add(texas);
	    contenedor.add(poker);
	    contenedor.add(imagenTexas);
	    contenedor.add(imagenPoker);
	    

	    setVisible(true);
	
		setSize(950,500);
}

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		
		if(arg0.getSource() == texas )
		{
			for (int i = 0; i < 4; i++) {
				
				ClientePoker interfaz = new ClientePoker(nombreHost, 1);
				interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
			}
			
		}
		if(arg0.getSource() == poker )
		{
			ClientePoker interfaz = new ClientePoker(nombreHost,2 );
			interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			if(ACEPTADO EN MESA)
//			{
				this.dispose();
//			}
//			else 
//			{
//				//boton de alerta
//			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == texas)
		{
			imagenTexas.setIcon(imagenEscala1);
			
		}
		if(arg0.getSource() == poker)
		{
			imagenPoker.setIcon(imagenEscala2);
			
		}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == texas)
		{
			imagenTexas.setIcon(null);
			
		}
		if(arg0.getSource() == poker)
		{
			imagenPoker.setIcon(null);
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
//	
//    public static void main(String args[]){
//        Menu a = new Menu();
//    }
	

}