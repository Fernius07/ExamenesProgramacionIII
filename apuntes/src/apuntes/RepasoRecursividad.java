package apuntes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// --- CLASES AUXILIARES (CONTEXTO) ---

enum Nivel {
	PRINCIPIANTE, INTERMEDIO, EXPERTO
}

enum Talla {
	S, M, L
}

class Esquiador {
	private Nivel nivel;
	private Talla talla;

	public Nivel getNivel() {
		return nivel;
	}

	public Talla getTalla() {
		return talla;
	}
}

// ==========================================================
//   CLASE PRINCIPAL: REPASO RECURSIVIDAD
// ==========================================================
public class RepasoRecursividad {

	// -------------------------------------------------------------------------
	// NIVEL 1: RECURSIVIDAD LINEAL (Calculadora)
	// Objetivo: Sumar, Contar, Buscar Máximo. Usar subList(1, size).
	// -------------------------------------------------------------------------

	// CASO A: Sumar Kilómetros (Filtrando por Color)
	public double sumarKmRec(List<Pista> pistas, Dificultad colorBuscado) {
		// 1. CASO BASE: Si la lista está vacía, la suma es 0
		if (pistas.isEmpty()) {
			return 0;
		}

		// 2. PROCESAR CABEZA
		Pista actual = pistas.get(0);

		// 3. PREPARAR LA COLA (RESTO)
		List<Pista> resto = pistas.subList(1, pistas.size());

		// 4. DECISIÓN Y LLAMADA RECURSIVA
		if (actual.getDificultad() == colorBuscado) {
			// SI COINCIDE: Sumo mis km + lo que digan los demás
			return actual.getKilometros() + sumarKmRec(resto, colorBuscado);
		} else {
			// NO COINCIDE: No sumo nada, paso el problema al siguiente
			return sumarKmRec(resto, colorBuscado);
		}
	}

	// CASO B: Buscar el Máximo (La pista más larga)
	public double buscarMaxKm(List<Pista> pistas) {
		// 1. CASO BASE (Si queda 1, ese es el máximo)
		if (pistas.size() == 1) {
			return pistas.get(0).getKilometros();
		}

		// 2. DIVIDIR
		double miLongitud = pistas.get(0).getKilometros();
		double maximoDelResto = buscarMaxKm(pistas.subList(1, pistas.size()));

		// 3. COMPARAR AL VOLVER
		return Math.max(miLongitud, maximoDelResto);
	}

	// -------------------------------------------------------------------------
	// NIVEL 2: BACKTRACKING (Combinaciones)
	// Patrón: Bucle for + add + recursión + remove.
	// -------------------------------------------------------------------------

	// CASO C: El Planificador (Generar Rutinas Exactas)
	public void buscarRutaExacta(List<Pista> todas, double kmObjetivo) {
		buscarRutaRec(todas, kmObjetivo, new ArrayList<>(), 0);
	}

	private void buscarRutaRec(List<Pista> catalogo, double kmRestantes, List<Pista> rutaActual, int indice) {
		// 1. CASO BASE ÉXITO: Hemos llegado a 0 exactos (con margen por ser double)
		if (Math.abs(kmRestantes) < 0.1) {
			System.out.println("Ruta encontrada: " + rutaActual);
			// IMPORTANTE: Si guardas en una lista de listas, usa new
			// ArrayList<>(rutaActual)
			return;
		}

		// 2. CASO BASE FRACASO: Nos hemos pasado
		if (kmRestantes < 0) {
			return;
		}

		// 3. BUCLE DE EXPLORACIÓN
		for (int i = indice; i < catalogo.size(); i++) {
			Pista p = catalogo.get(i);

			// A. HACER (Elegir pista)
			rutaActual.add(p);

			// B. RECURSIÓN (Avanzar i+1 para no repetir la misma pista)
			buscarRutaRec(catalogo, kmRestantes - p.getKilometros(), rutaActual, i + 1);

			// C. DESHACER (Backtracking - Borrar para probar otra)
			rutaActual.remove(rutaActual.size() - 1);
		}
	}

	// CASO D: El Optimizador (La Mochila / Mejor Ruta)
	public void mejorRuta(List<Pista> catalogo, double tiempoLimite, List<Pista> mejorSolucion, List<Pista> actual,
			int idx) {
		// Si la actual es mejor (más larga) que la guardada, actualizamos
		if (actual.size() > mejorSolucion.size()) {
			mejorSolucion.clear();
			mejorSolucion.addAll(actual); // Copia de seguridad
		}

		for (int i = idx; i < catalogo.size(); i++) {
			Pista p = catalogo.get(i);

			// PODA: Solo entramos si cabe en el tiempo
			if (tiempoLimite - p.getTiempo() >= 0) {
				actual.add(p);
				// Recursión
				mejorRuta(catalogo, tiempoLimite - p.getTiempo(), mejorSolucion, actual, i + 1);
				// Backtracking
				actual.remove(actual.size() - 1);
			}
		}
	}

	// -------------------------------------------------------------------------
	// NIVEL 3: AGRUPACIÓN Y MAPAS
	// -------------------------------------------------------------------------

	// Escenario: Organizar esquiadores en telesillas (Agrupación)
	public void llenarTelesillas(List<Esquiador> cola, List<List<Esquiador>> telesillas, List<Esquiador> sillaActual,
			int indice) {
		// CASO BASE: La silla está llena (4 personas)
		if (sillaActual.size() == 4) {
			telesillas.add(new ArrayList<>(sillaActual)); // Guardamos copia
			return;
		}

		for (int i = indice; i < cola.size(); i++) {
			Esquiador e = cola.get(i);

			// Condición: Solo suben Expertos a esta silla
			if (e.getNivel() == Nivel.EXPERTO) {
				sillaActual.add(e);
				llenarTelesillas(cola, telesillas, sillaActual, i + 1);
				// Backtracking
				sillaActual.remove(sillaActual.size() - 1);
			}
		}
	}

	// Recorrer un Mapa Recursivamente (Sumar Total Estación)
	public double sumarTotalEstacion(Map<Dificultad, List<Pista>> mapaPistas) {
		// Truco: Convertir values() en Lista de Listas para poder hacer recursión
		// lineal
		List<List<Pista>> listaDeListas = new ArrayList<>(mapaPistas.values());
		return sumarListasRec(listaDeListas);
	}

	private double sumarListasRec(List<List<Pista>> listas) {
		if (listas.isEmpty())
			return 0;

		List<Pista> pistasDeUnColor = listas.get(0);

		// Sumamos esa lista (auxiliar)
		double sumaParcial = 0;
		for (Pista p : pistasDeUnColor)
			sumaParcial += p.getKilometros();

		// Recursión con el resto de listas
		return sumaParcial + sumarListasRec(listas.subList(1, listas.size()));
	}

	// Crear un Mapa Recursivamente
	public Map<Nivel, List<Esquiador>> agruparEsquiadores(List<Esquiador> lista) {
		Map<Nivel, List<Esquiador>> mapa = new HashMap<>();
		agruparRec(lista, mapa);
		return mapa;
	}

	private void agruparRec(List<Esquiador> lista, Map<Nivel, List<Esquiador>> mapa) {
		if (lista.isEmpty())
			return;

		Esquiador e = lista.get(0);
		Nivel nivel = e.getNivel();

		// Lógica de Mapa
		mapa.putIfAbsent(nivel, new ArrayList<>());
		mapa.get(nivel).add(e);

		// Recursión con el resto
		agruparRec(lista.subList(1, lista.size()), mapa);
	}

	// NIVEL "JEFE FINAL": Backtracking con Inventario (Mapas)
	public boolean puedenEsquiarTodos(List<Esquiador> cola, Map<Talla, Integer> inventario) {
		// 1. CASO BASE ÉXITO
		if (cola.isEmpty())
			return true;

		// 2. COGEMOS AL PRIMERO
		Esquiador actual = cola.get(0);
		Talla tallaNecesaria = actual.getTalla();

		// 3. VERIFICAMOS STOCK
		int stockDisponible = inventario.getOrDefault(tallaNecesaria, 0);

		if (stockDisponible > 0) {
			// A. HACER (Prestar esquís) -> Restamos 1 al inventario
			inventario.put(tallaNecesaria, stockDisponible - 1);

			// B. RECURSIÓN
			boolean exitoResto = puedenEsquiarTodos(cola.subList(1, cola.size()), inventario);
			if (exitoResto)
				return true;

			// C. DESHACER (Backtracking) -> Devolvemos los esquís
			inventario.put(tallaNecesaria, stockDisponible);
		}

		return false;
	}
}