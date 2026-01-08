package es.deusto.prog3.examen202401ord.datos;

/** Clase para gestión de objetos de frase de ChatNoGPT
 * @author prog3 @ ingenieria.deusto.es
 */
public class Frase {
	private int id;
	private String emisor;
	private String receptor;
	private long fecha;
	private String texto;
	private int idConv;

	/** Construye un objeto frase sin datos
	 */
	public Frase() {
		this.idConv = -1;
	}
	
	/** Construye un objeto frase
	 * @param emisor	Usuario que emite la frase
	 * @param receptor	Usuario que la recibe
	 * @param fecha	Fecha en la que se realiza
	 * @param texto	Texto de la frase
	 * @param idConv	Identificador de la conversación
	 */
	public Frase(String emisor, String receptor, long fecha, String texto, int idConv) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.fecha = fecha;
		this.texto = texto;
		this.idConv = idConv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getIdConv() {
		return idConv;
	}
	
	public void setIdConv(int idConv) {
		this.idConv = idConv;
	}

	@Override
	public String toString() {
		return "Frase [id=" + id + ", emisor=" + emisor + ", receptor=" + receptor + ", fecha=" + fecha + ", texto="
				+ texto + ", idConv=" + idConv + "]";
	}
	
}
