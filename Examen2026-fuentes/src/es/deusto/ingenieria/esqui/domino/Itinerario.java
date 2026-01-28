package es.deusto.ingenieria.esqui.domino;

import java.util.Objects;

public class Itinerario implements Comparable<Itinerario> {
	private long id;
    private final Ciudad origen;
    private final Ciudad destino;
    private final int duracion; // Duración en minutos
	    
    public Itinerario(Ciudad origen, Ciudad destino, int duracion) {
		this.origen = origen;
		this.destino = destino;
		this.duracion = duracion;
		this.id = -1;
	}
    
    public Itinerario(long id, Ciudad origen, Ciudad destino, int duracion) {
		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.duracion = duracion;
	}
    
    public long getId() { return id; }
    public Ciudad getOrigen() { return origen; }	
	public Ciudad getDestino() { return destino; }	
	public int getDuracion() { return duracion; }
	public void setId(long id) { this.id = id; }
	
	@Override
	public String toString() {
		return String.format("🚌: %s %s -> %s %s (%d min.)",
				origen.getPais().getIcon(), origen.getNombre(),
				destino.getPais().getIcon(), destino.getNombre(), duracion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(destino, duracion, origen);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Itinerario other = (Itinerario) obj;
		return Objects.equals(destino, other.destino) && duracion == other.duracion
				&& Objects.equals(origen, other.origen);
	}

	@Override
	//Criterios de ordenación: ciudad origen, ciudad destino, duración
	public int compareTo(Itinerario o) {		
		int resultado = this.origen.getNombre().compareTo(o.origen.getNombre());
		
		if (resultado == 0) {
			resultado = this.destino.getNombre().compareTo(o.destino.getNombre());
		
			if (resultado == 0) {
				resultado = Integer.compare(this.duracion, o.duracion);
			}
		}
		
		return resultado;
	}
}