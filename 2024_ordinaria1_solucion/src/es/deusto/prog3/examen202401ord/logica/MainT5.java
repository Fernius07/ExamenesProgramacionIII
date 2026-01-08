package es.deusto.prog3.examen202401ord.logica;

import java.util.ArrayList;
import java.util.Comparator;
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
    	lF.sort( new Comparator<Frase>() {
    		public int compare(Frase o1, Frase o2) {
    			if (o1.getIdConv()!=o2.getIdConv()) {
    				return o1.getIdConv()-o2.getIdConv();
    			}
    			return (int) (o1.getFecha() - o2.getFecha());
    		}
		});
    	TreeMap<String,TreeSet<String>> mapaFrases = new TreeMap<>();
    	for (int i=0; i<lF.size(); i++) {
    		Frase f = lF.get(i);
    		if (f.getEmisor().equals( Main.NOMBRE_CHAT)) {
    			// La frase es del chat
    			if (i<lF.size()-1) {
    				Frase fSiguiente = lF.get(i+1);
    				if (f.getIdConv()==fSiguiente.getIdConv()) {
    					// Hay frase siguiente de usuario en la misma conversación: a trabajar
    					i++; // Salta esta frase en la siguiente iteración
    					if (!mapaFrases.containsKey( f.getTexto() )) {
    						mapaFrases.put( f.getTexto(), new TreeSet<>() );
    					}
    					mapaFrases.get( f.getTexto() ).add( fSiguiente.getTexto() );
    				}
    			}
    		}
    	}
    	for (String frase : mapaFrases.keySet()) {
    		System.out.println( frase );
    		for (String respuesta : mapaFrases.get( frase )) {
    			System.out.println( "\t" + respuesta );
    		}
    	}
    }

}
