// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.Persona;
import es.deusto.prog.examen.Sesion;
import es.deusto.prog.examen.Sesion.DiaSemana;

public class DataInitializer {

    private GestorBDInitializer gestorBD = new GestorBDInitializer();

    public void inicializarBBDD() {
        // Crear las tablas en la base de datos
        gestorBD.crearTablas();

        // Crear actividades y sesiones
        List<Actividad> actividades = new ArrayList<>();

        // Yoga - Lunes y Miércoles a las 9:00
        Actividad yoga = new Actividad(Actividad.TipoActividad.YOGA, 15);
        yoga.addSesion(new Sesion(yoga, DiaSemana.LUNES, 9));
        yoga.addSesion(new Sesion(yoga, DiaSemana.MIERCOLES, 9));
        actividades.add(yoga);

        // Pilates - Martes y Jueves a las 10:00
        Actividad pilates = new Actividad(Actividad.TipoActividad.PILATES, 15);
        pilates.addSesion(new Sesion(pilates, DiaSemana.MARTES, 10));
        pilates.addSesion(new Sesion(pilates, DiaSemana.JUEVES, 10));
        actividades.add(pilates);

        // Zumba - Martes y Jueves a las 11:00
        Actividad zumba = new Actividad(Actividad.TipoActividad.ZUMBA, 15);
        zumba.addSesion(new Sesion(zumba, DiaSemana.MARTES, 11));
        zumba.addSesion(new Sesion(zumba, DiaSemana.JUEVES, 11));
        actividades.add(zumba);

        // Boxeo - Lunes y Viernes a las 12:00
        Actividad boxeo = new Actividad(Actividad.TipoActividad.BOXEO, 8);
        boxeo.addSesion(new Sesion(boxeo, DiaSemana.LUNES, 12));
        boxeo.addSesion(new Sesion(boxeo, DiaSemana.VIERNES, 12));
        actividades.add(boxeo);

        // Spinning - Lunes, Miércoles y Viernes a las 13:00
        Actividad spinning = new Actividad(Actividad.TipoActividad.SPINNING, 12);
        spinning.addSesion(new Sesion(spinning, DiaSemana.LUNES, 13));
        spinning.addSesion(new Sesion(spinning, DiaSemana.MIERCOLES, 13));
        spinning.addSesion(new Sesion(spinning, DiaSemana.VIERNES, 13));
        actividades.add(spinning);

        // Insertar actividades y sesiones en la base de datos
        for (Actividad actividad : actividades) {
            gestorBD.insertActividad(actividad);
            
            for (Sesion sesion : actividad.getSesiones()) {
            	gestorBD.insertSesion(sesion);
            }
        }

        // Crear personas directamente en una lista
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Tony Stark", "iron.man@gmail.com", Persona.TipoAbono.SIMPLE));
        personas.add(new Persona("Barbara Gordon", "batgirl@gmail.com", Persona.TipoAbono.SIMPLE));
        personas.add(new Persona("Storm", "storm@gmail.com", Persona.TipoAbono.SIMPLE));        
        personas.add(new Persona("Natasha Romanoff", "black.widow@gmail.com", Persona.TipoAbono.SIMPLE));
        personas.add(new Persona("Steve Rogers", "captain.america@gmail.com", Persona.TipoAbono.DUO));        
        personas.add(new Persona("Peter Parker", "spiderman@gmail.com", Persona.TipoAbono.DUO));        
        personas.add(new Persona("Hulk", "hulk@gmail.com", Persona.TipoAbono.DUO));        
        personas.add(new Persona("James Howlett", "wolverine@gmail.com", Persona.TipoAbono.DUO));                 
        personas.add(new Persona("Natasha Romanoff", "black.widow@gmail.com", Persona.TipoAbono.DUO));
        personas.add(new Persona("Jean Grey", "phoenix@gmail.com", Persona.TipoAbono.DUO));        
        personas.add(new Persona("Black Panther", "black.panther@gmail.com", Persona.TipoAbono.COMPLETO));        
        personas.add(new Persona("Mystique", "mystique@gmail.com", Persona.TipoAbono.COMPLETO));
        personas.add(new Persona("Bruce Wayne", "batman@gmail.com", Persona.TipoAbono.COMPLETO));
        personas.add(new Persona("Clark Kent", "superman@gmail.com", Persona.TipoAbono.COMPLETO));
        personas.add(new Persona("Diana of Themiscyra", "wonder.woman@gmail.com", Persona.TipoAbono.COMPLETO)); 
        personas.add(new Persona("Thor Odinson", "thor@gmail.com", Persona.TipoAbono.COMPLETO));

        // Insertar personas en la base de datos
        for (Persona persona : personas) {
            gestorBD.insertPersona(persona);
        }

        // Agrupar personas por tipo de abono
        asignarPersonasAActividades(personas, actividades);        
    }

    private void asignarPersonasAActividades(List<Persona> personasAux, List<Actividad> actividades) {
        Random random = new Random();
    	
    	for (Persona persona : personasAux) {
    		int numActividadesAsignadas = persona.getTipoAbono().getNumActividades();
    		
    		while (numActividadesAsignadas > 0) {
    			Actividad actividad = actividades.get(random.nextInt(actividades.size()));
    		
    			if (actividad.getPlazasDisponibles() > 0 && !actividad.getPersonas().contains(persona)) {    				
    	            actividad.addPersona(persona);
    	            persona.getActividades().add(actividad);
    	            gestorBD.insertPersonaActividad(persona, actividad);
    				
    				numActividadesAsignadas--;
    			}    		
    		}
        }
    }
       
    public static void main(String[] args) {
    	DataInitializer inicializador = new DataInitializer();
        inicializador.inicializarBBDD();
    }
}