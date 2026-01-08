package es.deusto.prog.examen;

import java.time.LocalDate;

import es.deusto.prog.examen.Persona.TipoAbono;

public class Registro {
    
    private long idPersona;
    private LocalDate fecha;
    private TipoAbono abonoAnterior;
    private TipoAbono abonoNuevo;

    public Registro(long idPersona, LocalDate fecha, TipoAbono abonoAnterior, TipoAbono abonoNuevo) {
        this.idPersona = idPersona;
        this.fecha = fecha;
        this.abonoAnterior = abonoAnterior;
        this.abonoNuevo = abonoNuevo;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public TipoAbono getAbonoAnterior() {
        return abonoAnterior;
    }

    public TipoAbono getAbonoNuevo() {
        return abonoNuevo;
    }

    @Override
    public String toString() {
        return "Registro [idPersona=" + idPersona + ", fecha=" + fecha + ", abonoAnterior=" + abonoAnterior
                + ", abonoNuevo=" + abonoNuevo + "]";
    }
}
