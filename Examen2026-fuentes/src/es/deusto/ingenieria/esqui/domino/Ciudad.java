package es.deusto.ingenieria.esqui.domino;

import java.util.Objects;

public class Ciudad implements Comparable<Ciudad> {
	
	public enum Pais {
		AD("🇦🇩", "Andorra"),
		ES("🇪🇸", "España"),
		FR("🇫🇷", "Francia");

		private final String icon;
		private final String nombre;

		Pais(String icon, String nombre) {
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
	private final Pais pais;

	public Ciudad(String nombre, Pais pais) {
		this.nombre = nombre;
		this.pais = pais;
		this.id = -1; 
	}

	public Ciudad(long id, String nombre, Pais pais) {
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
	}

	public long getId() { return id; }
	public String getNombre() { return nombre; }
	public Pais getPais() { return pais; }
	public void setId(long id) { this.id = id;}

	@Override
	public String toString() {
		return String.format("%s %s", pais.getIcon(), nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, pais);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ciudad other = (Ciudad) obj;
		return Objects.equals(nombre, other.nombre) && pais == other.pais;
	}

	@Override
	public int compareTo(Ciudad o) {
		int res = this.pais.compareTo(o.pais);
		
		if (res == 0) {
			res = this.nombre.compareTo(o.nombre);
		}
		return res;
	}
}