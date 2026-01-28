package es.deusto.ingenieria.esqui.domino;

import java.util.Date;
import java.util.Objects;

public class Estacion implements Comparable<Estacion> {	
	private long id;
    private final String nombre;
    private final Ciudad ciudad;
    private final float kmEsquiablesTotal;    
    private EstadoEstacion estado;

    public Estacion(String nombre, Ciudad ciudad, float kmEsquiablesTotal) {
        this.nombre = nombre;
        this.ciudad = ciudad;
		this.kmEsquiablesTotal = kmEsquiablesTotal;
		this.id = -1;
    }
    
    public Estacion(long id, String nombre, Ciudad ciudad, float kmEsquiablesTotal, EstadoEstacion estado) {
    	this.id = id;
    	this.nombre = nombre;
    	this.ciudad = ciudad;
    	this.kmEsquiablesTotal = kmEsquiablesTotal;
    	this.estado = estado;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public Ciudad getCiudad() { return ciudad; }
    public float getKmEsquiablesTotal() { return kmEsquiablesTotal; }
    public EstadoEstacion getEstado() { return estado; }
    public void setId(long id) { this.id = id; }
    
    public void setEstado(EstadoEstacion estado) {
    	this.estado = estado;
    }
    
    @Override
    public String toString() {
        float kmActuales = (estado != null) ? estado.getKmEsquiables() : 0.00f;
        String aperturaStatus = (estado != null) ? estado.getApertura().getIcon() : "¿?";
        String climaStatus = (estado != null) ? estado.getClima().getIcon() : "¿?";
        Date fecha = (estado != null) ? estado.getActualizacion() : null;
        
        return String.format("⛷️ %s [%s], (%s) %s, km: %.2f/%.2f, (Actualización: %tF %tT)", 
						     nombre, 
						     ciudad.toString(),
						     aperturaStatus,
						     climaStatus,
						     kmActuales,
						     kmEsquiablesTotal,
						     fecha,
						     fecha);
    }

	@Override
	public int hashCode() {
		return Objects.hash(ciudad, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estacion other = (Estacion) obj;
		return Objects.equals(ciudad, other.ciudad) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public int compareTo(Estacion o) {
		int resultado = this.ciudad.getPais().getNombre().compareTo(o.ciudad.getPais().getNombre());
		
		if (resultado == 0) {
			resultado = this.nombre.compareTo(o.nombre);
		}
		
		return resultado;
	}
}