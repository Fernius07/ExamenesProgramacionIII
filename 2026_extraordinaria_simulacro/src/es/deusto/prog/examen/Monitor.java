package es.deusto.prog.examen;

public class Monitor {
    private int id;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private int clasesHoy;
    
    public Monitor(int id, String nombre, String apellidos, String especialidad, int clasesHoy) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.clasesHoy = clasesHoy;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getNombreCompleto() { return nombre + " " + apellidos; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public int getClasesHoy() { return clasesHoy; }
    public void setClasesHoy(int clasesHoy) { this.clasesHoy = clasesHoy; }
    
    public boolean isDisponible() { return clasesHoy < 4; }
    
    @Override
    public String toString() {
        return String.format("Monitor[%d, %s, %s, %d clases]", 
            id, getNombreCompleto(), especialidad, clasesHoy);
    }
}
