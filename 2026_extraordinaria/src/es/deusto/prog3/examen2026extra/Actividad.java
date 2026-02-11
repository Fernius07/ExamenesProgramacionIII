package es.deusto.prog3.examen2026extra;

public class Actividad {
    public enum Tipo { CARDIO, FUERZA, MOVILIDAD }
    public enum Intensidad { BAJA, MEDIA, ALTA }

    private String nombre;
    private int minutos;
    private Tipo tipo;
    private Intensidad intensidad;

    public Actividad(String nombre, int minutos, Tipo tipo, Intensidad intensidad) {
        this.nombre = nombre;
        this.minutos = minutos;
        this.tipo = tipo;
        this.intensidad = intensidad;
    }

    public String getNombre() { return nombre; }
    public int getMinutos() { return minutos; }
    public Tipo getTipo() { return tipo; }
    public Intensidad getIntensidad() { return intensidad; }

    public void setMinutos(int minutos) { this.minutos = minutos; }

    @Override
    public String toString() {
        return "%s (%s, %s) - %d min".formatted(nombre, tipo, intensidad, minutos);
    }
}
