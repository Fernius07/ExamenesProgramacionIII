package dominio;

/**
 * Clase que representa una competencia de una asignatura
 */
public class Competencia {
	
	public String codigo; // código de la competencia en la asignatura (p. ej, CE-01)
	public String desc; // descripción de la competencia  
	
	public Competencia(String codigo, String desc) {
		this.codigo = codigo;
		this.desc = desc;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDesc() {
		return desc;
	}

	public void setNombre(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", codigo, desc);
	}
}
