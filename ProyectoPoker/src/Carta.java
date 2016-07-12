import javax.swing.JButton;


public class Carta extends JButton {

	
	private int numero;
	private int palo;
	private boolean visible;
	
	
	
	
	public Carta()
	{
		numero = 0;
		palo = 0;
		visible = false;
	}
	
	
	public void setNumero(int n)
	{
		numero = n;
	
	}
	
	public void setPalo(int n)
	{
		palo = n;
	
	}
	
	public int getNumero()
	{
		return numero;
	
	}
	
	public int getPalo()
	{
		return palo;
	}
	
	
	public void setVisible(boolean v)
	{
		visible = v;
	}
	
	
	public boolean getVisible()
	{
		return visible;
	}
}
