//@nombre: Jhon Alejandro Orobio 
//@código: 1533627
//@fecha: 17/junio/2016



import java.io.Serializable;

import javax.swing.JButton;

public class Casilla extends JButton implements Serializable{
	private int estado;
	private int tipo;
	private int ubicacionX;
	private int ubicacionY;
	private String mensaje;
	
	
	public Casilla()
	{
		estado = 0;
		tipo = 0;
		ubicacionX = 0;
		ubicacionY = 0;
		mensaje = "r";
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getUbicacionX() {
		return ubicacionX;
	}
	public void setUbicacionX(int ubicacionX) {
		this.ubicacionX = ubicacionX;
	}
	public int getUbicacionY() {
		return ubicacionY;
	}
	public void setUbicacionY(int ubicacionY) {
		this.ubicacionY = ubicacionY;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
