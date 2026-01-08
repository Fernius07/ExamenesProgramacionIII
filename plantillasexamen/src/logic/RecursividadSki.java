package logic;

import domain.Pista;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecursividadSki {

    // Método público lanzador
    public static void buscarRutas(List<Pista> pistas, double kmsObjetivo) {
        List<List<Pista>> resultado = new ArrayList<>();
        
        // Llamada al método recursivo
        generarCombinaciones(resultado, pistas, kmsObjetivo, new ArrayList<>());
        
        // Imprimir resultados (Estilo profesor)
        System.out.format("Se han encontrado (%d) rutas de %.1f km:\n", resultado.size(), kmsObjetivo);
        for (List<Pista> ruta : resultado) {
            System.out.println("- " + ruta);
        }
    }

    // Método recursivo privado (Patrón Backtracking del profesor)
    private static void generarCombinaciones(List<List<Pista>> resultado, List<Pista> pistasDisponibles, double kmsRestantes, List<Pista> actual) {
        
        // CASO BASE: Hemos llegado al objetivo (con margen de error por ser double)
        // El profesor usaba 'duracion == 0', aquí usamos un margen pequeño
        if (kmsRestantes == 0) {
            
            // Truco del profesor: Ordenar para evitar duplicados {A,B} == {B,A}
            List<Pista> combinacionOrdenada = new ArrayList<>(actual);
            Collections.sort(combinacionOrdenada);
            
            // Si esta combinación no ha salido ya, la guardamos
            if (!resultado.contains(combinacionOrdenada)) {
                resultado.add(combinacionOrdenada);
            }
            
        } else {
            // CASO RECURSIVO: Probar todas las pistas
            for (Pista p : pistasDisponibles) {
                
                // Poda: Solo entramos si la pista no está ya en la ruta actual
                // Y si la longitud no se pasa del objetivo (con margen)
                if (!actual.contains(p) && p.getLongitud() <= kmsRestantes) {
                    
                    // 1. Añadir (Forward)
                    actual.add(p);
                    
                    // 2. Recursión (restando kms)
                    generarCombinaciones(resultado, pistasDisponibles, kmsRestantes - p.getLongitud(), actual);
                    
                    // 3. Quitar (Backtracking/Backwards)
                    actual.remove(actual.size() - 1);
                }
            }
        }
    }
}