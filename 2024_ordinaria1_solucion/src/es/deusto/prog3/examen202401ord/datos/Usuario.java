package es.deusto.prog3.examen202401ord.datos;

import java.util.ArrayList;
import java.util.Arrays;

/** Clase para gestión de objetos de usuario
 * @author prog3 @ ingenieria.deusto.es
 */
public class Usuario {
	private String nick;
	private String password;
	private String nombre;
	private String apellidos;
	private long telefono;
	private long fechaUltimoLogin;
	private ArrayList<String> listaEmails;

	protected void setNick( String nick ) {
		this.nick = nick;
	}
	public String getNick() {
		return nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public long getTelefono() {
		return telefono;
	}
	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}
	public long getFechaUltimoLogin() {
		return fechaUltimoLogin;
	}
	public void setFechaUltimoLogin(long fechaUltimoLogin) {
		this.fechaUltimoLogin = fechaUltimoLogin;
	}
	public ArrayList<String> getListaEmails() {
		return listaEmails;
	}
	public void setListaEmails(ArrayList<String> listaEmails) {
		this.listaEmails = listaEmails;
	}
	/** Devuelve los emails como un string único, en una lista separada por comas
	 * @return	Lista de emails
	 */
	public String getEmails() {
		String ret = "";
		if (listaEmails.size()>0) ret = listaEmails.get(0);
		for (int i=1; i<listaEmails.size(); i++) ret += (", " + listaEmails.get(i));
		return ret;
	}
	
	/** Constructor por defecto, deja un usuario sin datos
	 */
	public Usuario() {
		this.fechaUltimoLogin = -1;
	}
	
	/** Constructor principal de usuario
	 * @param nick
	 * @param password
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param tipo
	 * @param listaEmails
	 */
	public Usuario(String nick, String password, String nombre, String apellidos, long telefono, ArrayList<String> listaEmails) {
		super();
		this.nick = nick;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.listaEmails = listaEmails;
		this.fechaUltimoLogin = -1;
	}
	
	/** Constructor de usuario recibiendo los emails como una lista de parámetros de tipo String
	 * @param nick
	 * @param password
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param tipo
	 * @param emails
	 */
	public Usuario(String nick, String password, String nombre, String apellidos, long telefono, String... emails ) {
		this( nick, password, nombre, apellidos, telefono, new ArrayList<String>( Arrays.asList(emails)) );
	}

	// Dos usuarios son iguales si todos sus atributos son iguales
	public boolean equals( Object o ) {
		Usuario u2 = null;
		if (o instanceof Usuario) u2 = (Usuario) o;
		else return false;  // Si no es de la clase, son diferentes
		return (nick.equals(u2.nick))
			&& (password.equals(u2.password))
			&& (nombre.equals(u2.nombre))
			&& (apellidos.equals(u2.apellidos))
			&& (telefono == u2.telefono)
			&& (fechaUltimoLogin == u2.fechaUltimoLogin)
			&& (listaEmails.equals( u2.listaEmails ));
	}
	
	@Override
	public String toString() {
		return "Usuario [nick=" + nick + ", password=" + password + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", telefono=" + telefono + ", fechaUltimoLogin=" + fechaUltimoLogin + ", listaEmails=" + listaEmails
				+ "]";
	}

	
}
