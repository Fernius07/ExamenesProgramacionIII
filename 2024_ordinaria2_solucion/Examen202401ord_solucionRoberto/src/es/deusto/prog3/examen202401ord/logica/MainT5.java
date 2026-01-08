package es.deusto.prog3.examen202401ord.logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import es.deusto.prog3.examen202401ord.datos.Frase;
import es.deusto.prog3.examen202401ord.datos.GestorPersistencia;
import es.deusto.prog3.examen202401ord.main.Main;

public class MainT5 {

	private static GestorPersistencia bd = new GestorPersistencia();
	
    public static void main(String[] args) {
    	bd.init( Main.NOMBRE_BD );
    	if (!bd.existeBD( Main.NOMBRE_BD )) {
        	bd.crearTablasBD( null, true );
    	}
    	procesoT5();
    }
    
    private static void procesoT5() {
    	ArrayList<Frase> lF = bd.leerFrases( null, null );
    	// TODO T5 - partir de la lista lF para resolver la tarea
    	
    	// Ordena la lista de frases por identificador de conversación 
    	// en primer lugar, por fecha en segundo.    	
    	Comparator<Frase> comparadorFrases = (f1, f2) -> {
    		if (f1.getIdConv() != f2.getId()) {
    			return Integer.compare(f1.getIdConv(), f2.getIdConv());
    		} else {
    			return Long.compare(f1.getFecha(), f2.getFecha());
    		}
    	};
    	
    	Collections.sort(lF, comparadorFrases);
    	
    	// Crea un mapa de cada una de las frases lanzadas por ChatNoGPT, 
    	// asociando a cada una de ellas la serie de todas las respuestas 
    	// que ha dado cualquier usuario a esa frase.    	
    	Map<String, Set<String>> mapaFrases = new TreeMap<>();
    	
    	Frase fraseAnterior = null;
    	
    	for (Frase frase : lF) {
    		// Se crea una nueva entrada en el mapa si el emisor es Main.NOMBRE_CHAT
    		if (frase.getEmisor().equalsIgnoreCase(Main.NOMBRE_CHAT)) {
    			mapaFrases.putIfAbsent(frase.getTexto(), new TreeSet<>());
    		// Si el emisor de la frase no el Main.NOMBRE_CHAT, 
    		// la fraseAnterior no es null y pertenece a la misma conversación
    		// se añade la frase como respuesta a la frase actual
    		} else if (fraseAnterior != null && fraseAnterior.getIdConv() == frase.getIdConv()) {
    			mapaFrases.get(fraseAnterior.getTexto()).add(frase.getTexto());
    		}    		    		
    		
    		fraseAnterior = frase;
    	}
    	
    	//Se imprime el mapa
    	mapaFrases.keySet().forEach(k -> {    		
    		if (mapaFrases.get(k).size() > 0) {
    			System.out.println(k);    		
    			mapaFrases.get(k).forEach(v -> System.out.println("\t" + v));
    		}
    	});
    }
}
