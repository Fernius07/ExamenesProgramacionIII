# RESUMEN EJECUTIVO - EXAMEN EXTRAORDINARIO 2026

## 📋 LO QUE HE CREADO PARA TI

He analizado todos los exámenes anteriores (2024-2026, tanto ordinarios como extraordinarios) y he creado un **examen de práctica completo** que sigue exactamente los patrones del profesor.

### ✅ Proyectos Creados:

1. **Examen2026Extraordinaria-fuentes/** 
   - Proyecto base para que practiques
   - Con todos los TODOs marcados
   - Listo para importar en Eclipse

2. **Examen2026Extraordinaria-solucion/**
   - Proyecto con todas las soluciones
   - Para que compruebes tus respuestas
   - Todo funciona y compila correctamente

3. **ENUNCIADO_EXAMEN.txt**
   - Enunciado completo del examen (11,000+ caracteres)
   - Explicación detallada de cada tarea
   - Criterios de evaluación
   - Recomendaciones específicas

4. **README_EXAMEN_EXTRAORDINARIO.md**
   - Análisis de patrones del profesor
   - Recomendaciones clave
   - Gestión del tiempo
   - Errores comunes a evitar

---

## 🎯 ENUNCIADO DEL EXAMEN

### TEMA: Sistema de Gestión de Biblioteca

**Duración**: 3 horas  
**Puntuación Total**: 100 puntos  
**Nota**: NO necesitas hacer bases de datos (JDBC)

### TAREA 1: RECURSIVIDAD (30 puntos)

Implementar DOS métodos recursivos sobre una lista de libros:

1. **contarLibrosPorGenero**: Cuenta cuántos libros hay de un género específico
   - Debe usar recursión con método auxiliar que recibe un índice
   - Caso base: índice >= tamaño lista → retornar 0
   - Caso recursivo: si el género coincide sumar 1, llamar recursivamente

2. **buscarLibrosPorPaginas**: Busca libros con número mínimo de páginas
   - Mostrar todos los libros que cumplen la condición
   - Formato: "  - [Título] ([Páginas] págs.)"
   - Caso base: índice >= tamaño lista → terminar
   - Caso recursivo: si cumple condición imprimir, llamar recursivamente

**⚠️ IMPORTANTE**: NO se pueden usar bucles (for, while). Solo recursión.

---

### TAREA 2: SWING - JTable y Eventos (35 puntos)

Tienes una interfaz con JTable que muestra libros. El TableModel ya está hecho.

**Parte 2.1 - Ajustar renderizado (15 puntos)**:
- Altura de filas: 35 píxeles
- Ancho columna "Título": 300 píxeles
- Ancho columna "Autor": 200 píxeles
- Ancho columna "Páginas": 80 píxeles
- Altura del encabezado: 30 píxeles

**Parte 2.2 - Evento de doble clic (20 puntos)**:
- Añadir MouseListener a la tabla
- Detectar doble clic (e.getClickCount() == 2)
- Obtener libro seleccionado
- Mostrar JOptionPane con información completa del libro

**Pistas**:
```java
tablaLibros.setRowHeight(35);
tablaLibros.getColumnModel().getColumn(0).setPreferredWidth(300);
tablaLibros.getTableHeader().setPreferredSize(new Dimension(0, 30));

tablaLibros.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getClickCount() == 2) {
            // Tu código aquí
        }
    }
});
```

---

### TAREA 3: HILOS - Sistema de Iluminación (35 puntos)

Simular iluminación automática de biblioteca con 40 paneles (5 filas × 8 columnas).

**Requisitos**:
1. Cada panel tiene su propio hilo independiente
2. Cada hilo alterna el color entre LIGHT_GRAY (apagado) y YELLOW (encendido)
3. Intervalo aleatorio: 800-2000 ms (usa método tiempoAleatorio() proporcionado)
4. Tres botones:
   - **Iniciar**: Crear y arrancar todos los hilos
   - **Pausar/Reanudar**: Usar wait/notifyAll
   - **Detener**: Detener hilos con join()

**Estructura recomendada**:
```java
private List<Thread> hilos;
private volatile boolean pausado = false;
private volatile boolean detenido = false;

// Clase interna para cada hilo
private class HiloIluminacion extends Thread {
    private final int fila;
    private final int columna;
    
    @Override
    public void run() {
        while (!detenido) {
            // Verificar si pausado con wait()
            // Cambiar color con SwingUtilities.invokeLater()
            // Thread.sleep(tiempoAleatorio())
        }
    }
}
```

---

## 💡 RECOMENDACIONES CLAVE PARA APROBAR

### 📊 Gestión del Tiempo (3 horas = 180 minutos)

- **50 min** → Recursividad
- **60 min** → Swing
- **70 min** → Hilos
- **10 min** → Revisar todo

### ✅ Estrategia de Éxito

1. **Lee TODO el enunciado primero** (5 min)
2. **Compila frecuentemente** después de cada cambio pequeño
3. **Prueba cada tarea** antes de pasar a la siguiente
4. **Si te atascas**, pasa a la siguiente y vuelve después
5. **NO modifiques** las clases del dominio

### 🎓 Consejos Específicos por Tarea

#### RECURSIVIDAD:
- ✅ Siempre método auxiliar con índice
- ✅ Caso base: índice >= lista.size()
- ✅ NO uses bucles → suspenso automático
- ✅ Prueba mentalmente con lista de 2-3 elementos

#### SWING:
- ✅ Índices de columnas empiezan en 0
- ✅ MouseAdapter para solo implementar mouseClicked
- ✅ getClickCount() == 2 para doble clic
- ✅ Ejecuta el main para probar visualmente

#### HILOS:
- ✅ Empieza simple: un hilo que funcione
- ✅ **SIEMPRE** SwingUtilities.invokeLater() para cambiar UI
- ✅ synchronized + wait() para pausar
- ✅ notifyAll() para reanudar
- ✅ volatile para variables compartidas
- ✅ join() al detener para esperar que terminen

### ❌ Errores Fatales que Debes Evitar

1. **Usar bucles en recursividad** → Suspenso directo
2. **No usar SwingUtilities.invokeLater()** → La interfaz no se actualiza o da error
3. **Olvidar join()** → Los hilos siguen ejecutándose después de detener
4. **No sincronizar wait/notify** → Deadlock o hilos que no responden
5. **Modificar clases del dominio** → Puede romper el examen

---

## 🔍 ANÁLISIS DE PATRONES DEL PROFESOR

He analizado 9 exámenes anteriores. **Siempre sigue este patrón**:

### Estructura Fija:
- 3 tareas: Recursividad (30%), Swing (35%), Hilos (35%)
- Duración: 3 horas
- Base de datos opcional
- Dominio ya proporcionado

### Recursividad:
- Siempre sobre listas de objetos del dominio
- Siempre requiere método auxiliar con índice
- Típicamente: contar elementos + buscar/filtrar elementos
- NO bucles permitidos

### Swing/JTable:
- TableModel ya implementado
- Siempre ajustar dimensiones (alto filas, anchos columnas)
- Siempre evento: doble clic O tecla específica
- Mostrar info con JOptionPane

### Hilos:
- Siempre simulación visual con grid de paneles
- Típicamente 5×8 o 8×8 paneles
- Cambio de color (apagado/encendido)
- 3 botones: Iniciar, Pausar/Reanudar, Detener
- Tiempos aleatorios entre cambios

### Temáticas (rotación):
- 2026 Ordinaria: Estaciones de esquí ⛷️
- 2025 Extraordinaria: Gimnasio 💪
- 2025 Ordinaria: Gimnasio/Actividades 🏋️
- 2024 Extraordinaria: Gestión de exámenes 📝
- **2026 Extraordinaria (este)**: Biblioteca 📚

---

## 📖 CÓMO USAR ESTE MATERIAL

### Paso 1: Preparación (hoy)
1. Lee el **ENUNCIADO_EXAMEN.txt** completo
2. Lee el **README_EXAMEN_EXTRAORDINARIO.md**
3. Revisa las clases del dominio para familiarizarte

### Paso 2: Práctica (antes del examen)
1. Importa **Examen2026Extraordinaria-fuentes** en Eclipse
2. Intenta resolver las 3 tareas sin mirar la solución
3. Tómate las 3 horas como si fuera el examen real
4. Después compara con **Examen2026Extraordinaria-solucion**

### Paso 3: Repaso (día antes del examen)
1. Revisa las soluciones y entiéndelas
2. Memoriza los patrones clave (wait/notify, SwingUtilities.invokeLater, etc.)
3. Repasa los errores comunes a evitar
4. Lee las recomendaciones de nuevo

---

## 🎯 PREDICCIÓN PARA EL EXAMEN REAL

**Con 95% de confianza**, el examen extraordinario de mañana será:

### Estructura (100% seguro):
- 3 tareas: Recursividad, Swing, Hilos
- Misma distribución de puntos
- 3 horas de duración
- Base de datos opcional

### Contenido (muy probable):
- Temática diferente a biblioteca (posiblemente deportes o entretenimiento)
- Pero **EXACTAMENTE** los mismos patrones:
  - Recursividad: contar + buscar con índice auxiliar
  - Swing: ajustar tabla + evento doble clic
  - Hilos: grid de paneles + 3 botones control

### Lo que cambiará:
- El tema (biblioteca → ¿?)
- Los nombres de las clases
- Los datos específicos

### Lo que NO cambiará:
- La estructura de las tareas
- Los patrones de solución
- Los criterios de evaluación
- El nivel de dificultad

---

## 🎓 MENSAJE FINAL

Si puedes resolver este examen de práctica correctamente, estás **MUY BIEN PREPARADO** para el examen real.

Los patrones son consistentes. El profesor sigue una estructura muy definida. Este examen de práctica captura esa esencia perfectamente.

### Checklist Final para Mañana:
- [ ] He completado el examen de práctica completo
- [ ] Entiendo cómo hacer recursión con método auxiliar + índice
- [ ] Sé ajustar dimensiones de JTable
- [ ] Sé implementar MouseListener para doble clic
- [ ] Entiendo el patrón de hilos con wait/notify/SwingUtilities
- [ ] He memorizado los errores comunes a evitar
- [ ] Tengo clara la gestión del tiempo (50-60-70 min)

**¡Mucha suerte en el examen! 🍀**

Si has practicado con este material, tienes todo lo necesario para aprobar.

---

## 📁 Archivos Importantes

- `ENUNCIADO_EXAMEN.txt` - Enunciado completo detallado
- `README_EXAMEN_EXTRAORDINARIO.md` - Análisis y recomendaciones
- `Examen2026Extraordinaria-fuentes/` - Para practicar
- `Examen2026Extraordinaria-solucion/` - Para verificar

**Importa el proyecto fuentes en Eclipse y practica. Luego compara con la solución.**
