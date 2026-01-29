package es.deusto.prog.biblioteca.dominio;

import java.util.Objects;

public class Autor implements Comparable<Autor> {
	
	public enum Nacionalidad {
		ES("🇪🇸", "España"),
		UK("🇬🇧", "Reino Unido"),
		US("🇺🇸", "Estados Unidos"),
		FR("🇫🇷", "Francia"),
		DE("🇩🇪", "Alemania"),
		IT("🇮🇹", "Italia"),
		JP("🇯🇵", "Japón");
		
		private final String icon;
		private final String nombre;
		
		Nacionalidad(String icon, String nombre) {
			this.icon = icon;
			this.nombre = nombre;
		}
		
		public String getIcon() {
			return icon;
		}
		
		public String getNombre() {
			return nombre;
		}
	}
	
	private long id;
	private final String nombre;
	private final Nacionalidad nacionalidad;
	
	public Autor(String nombre, Nacionalidad nacionalidad) {
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.id = -1;
	}
	
	public Autor(long id, String nombre, Nacionalidad nacionalidad) {
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
	}
	
	public long getId() { return id; }
	public String getNombre() { return nombre; }
	public Nacionalidad getNacionalidad() { return nacionalidad; }
	public void setId(long id) { this.id = id; }
	
	@Override
	public String toString() {
		return String.format("%s %s", nacionalidad.getIcon(), nombre);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nombre, nacionalidad);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		return Objects.equals(nombre, other.nombre) && nacionalidad == other.nacionalidad;
	}
	
	@Override
	public int compareTo(Autor o) {
		int res = this.nacionalidad.compareTo(o.nacionalidad);
		
		if (res == 0) {
			res = this.nombre.compareTo(o.nombre);
		}
		return res;
	}
}
