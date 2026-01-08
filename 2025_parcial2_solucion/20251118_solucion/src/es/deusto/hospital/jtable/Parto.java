package es.deusto.hospital.jtable;

import java.awt.Color;
import java.util.List;

import javax.swing.ImageIcon;

public class Parto {

	public enum Estado {
	    PREPARTO(new Color(33, 150, 243)),
	    EN_PROCESO(new Color(255, 152, 0)),
	    POSTPARTO(new Color(76, 175, 80));
		
		private Color color;
		
		private Estado(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return color;
		}
		
		public ImageIcon getIcon() {
			return switch (this) {
				case PREPARTO -> new ImageIcon("resources/images/PRE.png");
				case EN_PROCESO -> 	new ImageIcon("resources/images/IN.png");
				default -> new ImageIcon("resources/images/POST.png");					
			};				
		}
	}
	
	private String habitacion;
	private String nombreMadre;
	private List<Bebe> bebes;
	private Estado estado;
	
	public Parto(String habitacion, String nombreMadre, List<Bebe> bebes, Estado estado) {
		this.habitacion = habitacion;
		this.nombreMadre = nombreMadre;
		this.bebes = bebes;
		this.estado = estado;
	}

	public String getHabitacion() {
		return habitacion;
	}

	public String getNombreMadre() {
		return nombreMadre;
	}

	public List<Bebe> getBebes() {
		return bebes;
	}

	public Estado getEstado() {
		return estado;
	}
}