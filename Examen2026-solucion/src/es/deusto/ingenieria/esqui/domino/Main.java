/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.domino;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import es.deusto.ingenieria.esqui.domino.Ciudad.Pais;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion.Apertura;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion.Clima;
import es.deusto.ingenieria.esqui.jdbc.GestorBD;

public class Main {

	// Generador de números aleatorios para simular duraciones de viaje
	private static final Random RANDOM = new Random();

	// Lista de estaciones
	private static List<Ciudad> ciudades = new ArrayList<>();
	private static List<Itinerario> itinerarios = new ArrayList<>();
	private static List<Estacion> estaciones = new ArrayList<>();

	/**
	 * Genera un tiempo de viaje aleatorio dentro de un rango dado.
	 * 
	 * @param min Duración mínima en minutos.
	 * @param max Duración máxima en minutos.
	 * @return Duración aleatoria.
	 */
	private static int generateTravelTime(int min, int max) {
		// nextInt(max - min + 1) genera un número entre 0 y (max - min) (inclusive)
		return RANDOM.nextInt(max - min + 1) + min;
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(2026, Calendar.JANUARY, 9, 10, 0, 0);
		
		Date ahora = cal.getTime();
		
		System.out.println("\n--- 1. CIUDADES ---");

		// Bases España
		Ciudad sallent = new Ciudad("Sallent de Gállego", Pais.ES); // Hub ES-1 (Formigal)
		Ciudad vielha = new Ciudad("Vielha", Pais.ES); // Hub ES-2 (Baqueira)
		Ciudad benasque = new Ciudad("Benasque", Pais.ES); // Secundario ES
		Ciudad jaca = new Ciudad("Jaca", Pais.ES); // Hub ES-3 (Astún/Candanchú)
		Ciudad alp = new Ciudad("Alp", Pais.ES); // Hub ES-4 (La Molina/Masella)
		Ciudad boiTaullBase = new Ciudad("La Vall de Boí", Pais.ES); // Secundario ES
		Ciudad espotBase = new Ciudad("Espot", Pais.ES); // Secundario ES
		Ciudad rialp = new Ciudad("Rialp", Pais.ES); // Secundario ES
		Ciudad laComa = new Ciudad("La Coma i la Pedra", Pais.ES); // Secundario ES
		Ciudad tavascanBase = new Ciudad("Tavascan", Pais.ES); // Secundario ES
		Ciudad queralbs = new Ciudad("Queralbs", Pais.ES); // Secundario ES
		Ciudad setcases = new Ciudad("Setcases", Pais.ES); // Secundario ES

		// Bases Andorra
		Ciudad encamp = new Ciudad("Encamp", Pais.AD); // Hub AD-1 (Grandvalira)
		Ciudad laMassana = new Ciudad("La Massana", Pais.AD); // Hub AD-2 (Pal Arinsal)
		Ciudad ordino = new Ciudad("Ordino", Pais.AD); // Secundario AD
		Ciudad canaro = new Ciudad("Canaro", Pais.AD); // Secundario AD

		// Bases Francia
		Ciudad laruns = new Ciudad("Laruns", Pais.FR); // Secundario FR
		Ciudad eauxBonnes = new Ciudad("Eaux-Bonnes", Pais.FR); // Secundario FR
		Ciudad arette = new Ciudad("Arette", Pais.FR); // Secundario FR
		Ciudad cauterets = new Ciudad("Cauterets", Pais.FR); // Secundario FR
		Ciudad gedre = new Ciudad("Gèdre", Pais.FR); // Secundario FR
		Ciudad bagneresBigorre = new Ciudad("Bagnères-de-Bigorre", Pais.FR); // Hub FR-1 (Grand Tourmalet)
		Ciudad beaucens = new Ciudad("Beaucens", Pais.FR); // Secundario FR
		Ciudad luzStSauveur = new Ciudad("Luz-Saint-Sauveur", Pais.FR); // Secundario FR
		Ciudad peyresourde = new Ciudad("Peyresourde", Pais.FR); // Secundario FR
		Ciudad aragnouet = new Ciudad("Aragnouet", Pais.FR); // Hub FR-2 (Piau-Engaly)
		Ciudad stLary = new Ciudad("Saint-Lary", Pais.FR); // Hub FR-3 (Saint-Lary)
		Ciudad valLouron = new Ciudad("Val Louron", Pais.FR); // Secundario FR
		Ciudad bourgOueil = new Ciudad("Bourg-d'Oueil", Pais.FR); // Secundario FR
		Ciudad boutx = new Ciudad("Boutx", Pais.FR); // Secundario FR
		Ciudad luchon = new Ciudad("Bagnères-de-Luchon", Pais.FR); // Secundario FR
		Ciudad ascou = new Ciudad("Ascou", Pais.FR); // Secundario FR
		Ciudad axLesThermes = new Ciudad("Ax-les-Thermes", Pais.FR); // Hub FR-4 (Ax 3 Domaines)
		Ciudad goulier = new Ciudad("Goulier", Pais.FR); // Secundario FR
		Ciudad sentenacOust = new Ciudad("Sentenac d'Oust", Pais.FR); // Secundario FR
		Ciudad montferrier = new Ciudad("Montferrier", Pais.FR); // Secundario FR
		Ciudad mijanes = new Ciudad("Mijanès", Pais.FR); // Secundario FR
		Ciudad camurac = new Ciudad("Camurac", Pais.FR); // Secundario FR
		Ciudad stPierreForcats = new Ciudad("Saint-Pierre-dels-Forcats", Pais.FR); // Secundario FR
		Ciudad fontRomeu = new Ciudad("Font-Romeu", Pais.FR); // Hub FR-5 (Font-Romeu)
		Ciudad formigueres = new Ciudad("Formiguères", Pais.FR); // Secundario FR
		Ciudad laQuillane = new Ciudad("La Quillane", Pais.FR); // Secundario FR
		Ciudad lesAngles = new Ciudad("Les Angles", Pais.FR); // Hub FR-6 (Les Angles)
		Ciudad portePuymorens = new Ciudad("Porté-Puymorens", Pais.FR); // Secundario FR

		// Se añaden las ciudades a la lista general
		ciudades.addAll(List.of(sallent, vielha, benasque, jaca, alp, boiTaullBase, espotBase, rialp, laComa,
				tavascanBase, queralbs, setcases, encamp, laMassana, ordino, canaro, laruns, eauxBonnes, arette,
				cauterets, gedre, bagneresBigorre, beaucens, luzStSauveur, peyresourde, aragnouet, stLary, valLouron,
				bourgOueil, boutx, luchon, ascou, axLesThermes, goulier, sentenacOust, montferrier, mijanes, camurac,
				stPierreForcats, fontRomeu, formigueres, laQuillane, lesAngles, portePuymorens));

		// Definición de Hubs (para la generación de itinerarios)
		List<Ciudad> esMains = List.of(sallent, vielha, jaca, alp);
		List<Ciudad> adMains = List.of(encamp, laMassana);
		List<Ciudad> frMains = List.of(bagneresBigorre, aragnouet, stLary, axLesThermes, fontRomeu, lesAngles);

		// Definición de Secundarios (para la generación de itinerarios)
		List<Ciudad> esSecondaries = List.of(benasque, boiTaullBase, espotBase, rialp, laComa, tavascanBase, queralbs,
				setcases);
		List<Ciudad> adSecondaries = List.of(ordino, canaro);
		List<Ciudad> frSecondaries = List.of(laruns, eauxBonnes, arette, cauterets, gedre, beaucens, luzStSauveur,
				peyresourde, valLouron, bourgOueil, boutx, luchon, ascou, goulier, sentenacOust, montferrier, mijanes,
				camurac, stPierreForcats, formigueres, laQuillane, portePuymorens);

		 //--------------------------------------------------------
		// 2. ESTADOS Y ESTACIONES (Actualizado: 09/01/2026 10:00)
		// --------------------------------------------------------

		// --- ESPAÑA (12 Estaciones) ---
		Estacion est1 = new Estacion("Formigal - Panticosa", sallent, 182.0f);
		est1.setEstado(new EstadoEstacion(Clima.NEVANDO, Apertura.ABIERTA, -4, 120.0f, ahora));
		estaciones.add(est1);

		Estacion est2 = new Estacion("Baqueira Beret", vielha, 173.0f);
		est2.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -8, 150.0f, ahora));
		estaciones.add(est2);

		Estacion est3 = new Estacion("Cerler", benasque, 81.0f);
		est3.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -5, 95.0f, ahora));
		estaciones.add(est3);

		Estacion est4 = new Estacion("Astún - Candanchú", jaca, 101.0f);
		est4.setEstado(new EstadoEstacion(Clima.NUBLADO, Apertura.ABIERTA, -6, 110.5f, ahora));
		estaciones.add(est4);

		Estacion est5 = new Estacion("La Molina/Masella", alp, 145.0f);
		est5.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -4, 85.0f, ahora));
		estaciones.add(est5);

		Estacion est6 = new Estacion("Boí Taüll", boiTaullBase, 45.0f);
		est6.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -10, 75.0f, ahora));
		estaciones.add(est6);

		Estacion est7 = new Estacion("Espot", espotBase, 25.0f);
		est7.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.CERRADA, -7, 60.0f, ahora));
		estaciones.add(est7);

		Estacion est8 = new Estacion("Port Ainé", rialp, 27.0f);
		est8.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -6, 55.0f, ahora));
		estaciones.add(est8);

		Estacion est9 = new Estacion("Port del Comte", laComa, 40.6f);
		est9.setEstado(new EstadoEstacion(Clima.NUBLADO, Apertura.ABIERTA, -3, 30.8f, ahora));
		estaciones.add(est9);

		Estacion est10 = new Estacion("Tavascán", tavascanBase, 7.0f);
		estaciones.add(est10);

		Estacion est12 = new Estacion("Vallter", setcases, 11.0f);
		est12.setEstado(new EstadoEstacion(Clima.NUBLADO, Apertura.ABIERTA, -5, 35.0f, ahora));
		estaciones.add(est12);

		// --- ANDORRA (4 Estaciones) ---
		Estacion est13 = new Estacion("Grandvalira", encamp, 215.0f);
		est13.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -7, 130.0f, ahora));
		estaciones.add(est13);

		Estacion est14 = new Estacion("Ordino Arcalís", ordino, 30.5f);
		est14.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -9, 160.0f, ahora));
		estaciones.add(est14);

		Estacion est15 = new Estacion("Vallnord Pal Arinsal", laMassana, 63.0f);
		est15.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.CERRADA, -6, 90.0f, ahora));
		estaciones.add(est15);

		Estacion est16 = new Estacion("Ski Canaro", canaro, 0.5f);
		estaciones.add(est16);

		// --- FRANCIA (25 Estaciones) ---
		Estacion est17 = new Estacion("Artouste", laruns, 27.0f);
		est17.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -5, 45.0f, ahora));
		estaciones.add(est17);

		Estacion est18 = new Estacion("Gourette", eauxBonnes, 42.0f);
		est18.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -6, 70.0f, ahora));
		estaciones.add(est18);

		Estacion est19 = new Estacion("La Pierre Saint-Martin", arette, 26.0f);
		est19.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -4, 80.0f, ahora));
		estaciones.add(est19);

		Estacion est20 = new Estacion("Cauterets", cauterets, 40.0f);
		est20.setEstado(new EstadoEstacion(Clima.NEVANDO, Apertura.ABIERTA, -8, 110.0f, ahora));
		estaciones.add(est20);

		Estacion est21 = new Estacion("Cauterets Cirque du Lys", cauterets, 36.0f);
		est21.setEstado(new EstadoEstacion(Clima.NEVANDO, Apertura.CERRADA, -8, 105.0f, ahora));
		estaciones.add(est21);

		Estacion est22 = new Estacion("Cauterets Pont d´Espagne", cauterets, 4.0f);
		est22.setEstado(new EstadoEstacion(Clima.NUBLADO, Apertura.ABIERTA, -4, 25.0f, ahora));
		estaciones.add(est22);

		Estacion est23 = new Estacion("Gavarnie-Gèdre", gedre, 32.0f);
		est23.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.CERRADA, -5, 60.0f, ahora));
		estaciones.add(est23);

		Estacion est24 = new Estacion("Grand Tourmalet", bagneresBigorre, 100.0f);
		est24.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -7, 125.0f, ahora));
		estaciones.add(est24);

		Estacion est25 = new Estacion("Hautacam", beaucens, 4.0f);
		est25.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -2, 15.0f, ahora));
		estaciones.add(est25);

		Estacion est26 = new Estacion("Luz Ardiden", luzStSauveur, 60.0f);
		est26.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -6, 75.0f, ahora));
		estaciones.add(est26);

		Estacion est27 = new Estacion("Peyragudes", peyresourde, 60.0f);
		est27.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.CERRADA, -5, 80.0f, ahora));
		estaciones.add(est27);

		Estacion est28 = new Estacion("Piau-Engaly", aragnouet, 65.0f);
		est28.setEstado(new EstadoEstacion(Clima.NEVANDO, Apertura.ABIERTA, -10, 145.0f, ahora));
		estaciones.add(est28);

		Estacion est29 = new Estacion("Saint-Lary", stLary, 100.0f);
		est29.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -6, 115.0f, ahora));
		estaciones.add(est29);

		Estacion est30 = new Estacion("Val Louron", valLouron, 22.0f);
		est30.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -4, 45.0f, ahora));
		estaciones.add(est30);

		Estacion est31 = new Estacion("Bourg d´Oueil", bourgOueil, 5.0f);
		est31.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -2, 20.0f, ahora));
		estaciones.add(est31);

		Estacion est32 = new Estacion("Le Mourtis", boutx, 23.0f);
		est32.setEstado(new EstadoEstacion(Clima.NUBLADO, Apertura.ABIERTA, -3, 40.0f, ahora));
		estaciones.add(est32);

		Estacion est33 = new Estacion("Luchon - Superbagnères", luchon, 33.0f);
		est33.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -4, 55.0f, ahora));
		estaciones.add(est33);

		Estacion est34 = new Estacion("Ascou-Pailhères", ascou, 17.0f);
		est34.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -5, 40.0f, ahora));
		estaciones.add(est34);

		Estacion est35 = new Estacion("Ax 3 Domaines", axLesThermes, 80.0f);
		estaciones.add(est35);

		Estacion est36 = new Estacion("Goulier Neige", goulier, 7.0f);
		est36.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -3, 25.0f, ahora));
		estaciones.add(est36);

		Estacion est37 = new Estacion("Guzet", sentenacOust, 40.0f);
		estaciones.add(est37);

		Estacion est38 = new Estacion("Les Monts d´Olmes", montferrier, 15.0f);
		est38.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -4, 35.0f, ahora));
		estaciones.add(est38);

		Estacion est39 = new Estacion("Mijanès-Donezan", mijanes, 12.0f);
		est39.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -3, 30.0f, ahora));
		estaciones.add(est39);

		Estacion est40 = new Estacion("Camurac", camurac, 15.0f);
		est40.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -2, 25.0f, ahora));
		estaciones.add(est40);

		Estacion est41 = new Estacion("Cambre d´Aze", stPierreForcats, 24.0f);
		est41.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -5, 45.0f, ahora));
		estaciones.add(est41);

		Estacion est42 = new Estacion("Font-Romeu Pyrénées 2000", fontRomeu, 43.0f);
		estaciones.add(est42);

		Estacion est43 = new Estacion("Formiguères", formigueres, 25.0f);
		estaciones.add(est43);

		Estacion est44 = new Estacion("La Quillane", laQuillane, 2.5f);
		est44.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -2, 10.0f, ahora));
		estaciones.add(est44);

		Estacion est45 = new Estacion("Les Angles", lesAngles, 55.0f);
		est45.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -6, 85.0f, ahora));
		estaciones.add(est45);

		Estacion est46 = new Estacion("Porté-Puymorens", portePuymorens, 50.0f);
		est46.setEstado(new EstadoEstacion(Clima.SOLEADO, Apertura.ABIERTA, -8, 95.0f, ahora));
		estaciones.add(est46);		
		

		System.out.println(String.format("\n--- 2. ESTACIONES (%d) ---", estaciones.size()));

		for (Estacion e : estaciones) {
			System.out.println(e);
		}

		// -----------------------------
		// 3. GENERACIÓN DE ITINERARIOS
		// -----------------------------
		System.out.println("\n--- 3. GENERACIÓN DE ITINERARIOS ---");

		// Rango de Duraciones
		final int INTER_MIN = 240; // 4 horas
		final int INTER_MAX = 480; // 8 horas
		final int INTRA_MIN = 30; // 30 minutos
		final int INTRA_MAX = 150; // 2.5 horas

		// --- 3.1. Itinerarios Inter-Países (Hub a Hub) ---
		System.out.println("\n-- 3.1. Rutas Internacionales (Entre estaciones principales) --");
		List<List<Ciudad>> hubGroups = List.of(esMains, adMains, frMains);

		for (int i = 0; i < hubGroups.size(); i++) {
			for (int j = i + 1; j < hubGroups.size(); j++) {
				List<Ciudad> groupA = hubGroups.get(i);
				List<Ciudad> groupB = hubGroups.get(j);

				for (Ciudad hubA : groupA) {
					for (Ciudad hubB : groupB) {
						int duracion = generateTravelTime(INTER_MIN, INTER_MAX);

						// Ruta de Ida (A -> B)
						Itinerario ida = new Itinerario(hubA, hubB, duracion);
						itinerarios.add(ida);
						System.out.println(ida);

						// Ruta de Vuelta (B -> A)
						Itinerario vuelta = new Itinerario(hubB, hubA, duracion);
						itinerarios.add(vuelta);
						System.out.println(vuelta);
					}
				}
			}
		}

		// --- 3.2. Itinerarios Intra-Regionales ---
		System.out.println("\n-- 3.2. Rutas Intra-Regionales (Hub <-> Secundarias) --");

		// España
		System.out.println("\n> Rutas España (ES)");
		for (Ciudad hub : esMains) {
			for (Ciudad secondary : esSecondaries) {
				int duracion = generateTravelTime(INTRA_MIN, INTRA_MAX);

				// Ruta de Ida (Hub -> Secundaria)
				Itinerario ida = new Itinerario(hub, secondary, duracion);
				itinerarios.add(ida);
				System.out.println(ida);

				// Ruta de Vuelta (Secundaria -> Hub)
				Itinerario vuelta = new Itinerario(secondary, hub, duracion);
				itinerarios.add(vuelta);
				System.out.println(vuelta);
			}
		}

		// Andorra
		System.out.println("\n> Rutas Andorra (AD)");
		for (Ciudad hub : adMains) {
			for (Ciudad secondary : adSecondaries) {
				int duracion = generateTravelTime(INTRA_MIN, INTRA_MAX);

				// Ruta de Ida (Hub -> Secundaria)
				Itinerario ida = new Itinerario(hub, secondary, duracion);
				itinerarios.add(ida);
				System.out.println(ida);

				// Ruta de Vuelta (Secundaria -> Hub)
				Itinerario vuelta = new Itinerario(secondary, hub, duracion);
				itinerarios.add(vuelta);
				System.out.println(vuelta);
			}
		}

		// Francia
		System.out.println("\n> Rutas Francia (FR)");
		for (Ciudad hub : frMains) {
			for (Ciudad secondary : frSecondaries) {
				int duracion = generateTravelTime(INTRA_MIN, INTRA_MAX);

				// Ruta de Ida (Hub -> Secundaria)
				Itinerario ida = new Itinerario(hub, secondary, duracion);
				itinerarios.add(ida);
				System.out.println(ida);

				// Ruta de Vuelta (Secundaria -> Hub)
				Itinerario vuelta = new Itinerario(secondary, hub, duracion);
				itinerarios.add(vuelta);
				System.out.println(vuelta);
			}
		}

		GestorBD gestorBD = new GestorBD();
		gestorBD.crearTablas();
		gestorBD.inicializarBBDD(ciudades, itinerarios, estaciones);
	}
}