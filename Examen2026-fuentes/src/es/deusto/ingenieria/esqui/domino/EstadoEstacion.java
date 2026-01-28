package es.deusto.ingenieria.esqui.domino;

import java.util.Date;

public class EstadoEstacion {

	public enum Clima {
	    SOLEADO("☀️"),
	    NUBLADO("☁️"),
	    NEVANDO("❄️"),
	    LLUVIOSO("🌧️"),
	    VENTOSO("💨"),
	    TORMENTOSO("⛈️");

	    private final String icon;

	    Clima(String icon) {
	        this.icon = icon;
	    }

	    public String getIcon() {
	        return icon;
	    }
	}
	
	public enum Apertura {
	    ABIERTA("🟢"),
	    CERRADA("🔴"),
	    DESCONOCIDA("⚪");

	    private final String icon;

	    Apertura(String icon) {
	        this.icon = icon;
	    }

	    public String getIcon() {
	        return icon;
	    }
	}
		
	private int id;
	private final Clima clima;
	private final Apertura apertura;
	private final int temperatura;
	private final float kmEsquiables;
	private final Date actualizacion;
	
	public EstadoEstacion(Clima clima, 
						  Apertura apertura,
						  int temperatura,
						  float kmEsquiables,
						  Date actualizacion) {
		this.clima = clima;
		this.apertura = apertura;
		this.temperatura = temperatura;
		this.kmEsquiables = kmEsquiables;
		this.actualizacion = actualizacion;
		this.id = -1;
	}
	
	public EstadoEstacion(int id,
						  Clima clima, 
						  Apertura apertura,
						  int temperatura,
						  float kmEsquiables,
						  Date actualizacion) {
		this.clima = clima;
		this.apertura = apertura;
		this.temperatura = temperatura;
		this.kmEsquiables = kmEsquiables;
		this.actualizacion = actualizacion;
		this.id = id;
	}

	public int getId() { return id; }
	public Clima getClima() { return clima; }
	public Apertura getApertura() { return apertura; }
	public int getTemperatura() { return temperatura; }
	public float getKmEsquiables() { return kmEsquiables; }
	public Date getActualizacion() { return actualizacion; }
	public void setId(int id) { this.id = id; }
	
	@Override
	public String toString() {
		return String.format("%s %s, %d°C, %.2f km (Actualización: %tF %tT)", 
				apertura.getIcon(),
				clima.getIcon(),
				temperatura,
				kmEsquiables,
				actualizacion,
				actualizacion);
	}
}