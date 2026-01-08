package es.deusto.hospital.jtable;

import javax.swing.ImageIcon;

public class Bebe {
	
	public enum Sexo {
	    BOY, 
	    GIRL, 
	    UNKNOWN;
		
		public ImageIcon getIcon() {
			return switch (this) {
				case BOY -> new ImageIcon("resources/images/boy.png");
				case GIRL -> new ImageIcon("resources/images/girl.png");
				default -> new ImageIcon("resources/images/unknown.png");
			};
		}					
	}
	
	private Sexo sexo;
	private String nombre;

	public Bebe(String nombre, Sexo sexo) {
		this.nombre = nombre;
		this.sexo = sexo;
	}
	
	public Sexo getSexo() {
		return sexo;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String toString() {
		return sexo + " - " + nombre;
	}
}