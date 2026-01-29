# Examen Extraordinario Programación III - Febrero 2026

## Sistema de Gestión de Biblioteca

Este es un examen de práctica diseñado siguiendo los patrones de los exámenes anteriores de Programación III en la Universidad de Deusto.

### 📁 Estructura del Proyecto

- **Examen2026Extraordinaria-fuentes/**: Proyecto base con las tareas a completar (para practicar)
- **Examen2026Extraordinaria-solucion/**: Proyecto con las soluciones completas
- **ENUNCIADO_EXAMEN.txt**: Enunciado completo del examen con todas las tareas

### 📚 Contenido del Examen

El examen consta de 3 tareas principales:

1. **RECURSIVIDAD (30 puntos)**: Implementar métodos recursivos para buscar y contar libros
2. **SWING - JTable (35 puntos)**: Crear una interfaz gráfica con tabla de libros y eventos
3. **HILOS (35 puntos)**: Simular un sistema de iluminación automática con múltiples hilos

### 🎯 Temática

Sistema de gestión para una biblioteca universitaria que incluye:
- Catálogo de libros con autores de diferentes nacionalidades
- Gestión de usuarios y préstamos
- Múltiples géneros literarios
- Simulación de sistema de iluminación automática

### ⚙️ Cómo usar este examen de práctica

1. **Importa el proyecto fuentes en Eclipse**:
   - File → Import → Existing Projects into Workspace
   - Selecciona la carpeta `Examen2026Extraordinaria-fuentes`

2. **Lee el enunciado completo** en `ENUNCIADO_EXAMEN.txt`

3. **Completa las tareas** siguiendo los comentarios TODO en el código

4. **Comprueba tus soluciones** comparando con el proyecto solucion

### 📖 Análisis del Patrón del Profesor

Basado en los exámenes anteriores (2024-2026), este examen sigue los patrones típicos:

#### Temas Recurrentes:
- **Recursividad**: Siempre presente, búsqueda/conteo en listas
- **Swing/JTable**: Visualización de datos con eventos (doble clic, teclas)
- **Hilos**: Simulaciones visuales con pausar/reanudar/detener
- **Dominio**: Clases bien estructuradas con enums y relaciones

#### Patrones Identificados:

1. **Estructura del examen**:
   - 3 tareas principales (Recursividad, Swing, Hilos)
   - ~30-35 puntos por tarea
   - 3 horas de duración
   - Base de datos opcional (no necesaria si ya aprobada)

2. **Recursividad**:
   - Siempre sobre listas de objetos
   - Métodos auxiliares con índice adicional
   - Caso base: índice >= tamaño lista
   - NO se permiten bucles

3. **Swing/JTable**:
   - TableModel ya proporcionado
   - Ajustar dimensiones (alto filas, ancho columnas)
   - Eventos: doble clic o teclas específicas
   - Mostrar información con JOptionPane

4. **Hilos**:
   - Simulaciones visuales con paneles que cambian de color
   - Grid de paneles (típicamente 5x8 o 8x8)
   - Tres botones: Iniciar, Pausar/Reanudar, Detener
   - wait/notify para pausar
   - volatile para compartir estado

5. **Temáticas**:
   - Alternancia entre deportes/fitness, educación, ocio
   - 2026 Ordinaria: Estaciones de esquí
   - 2025 Extraordinaria: Gimnasio
   - 2024 Extraordinaria: Exámenes universitarios
   - **2026 Extraordinaria (este)**: Biblioteca (educación)

### 💡 Recomendaciones Clave

#### Para Recursividad:
- ✅ Siempre crear método auxiliar con índice
- ✅ Caso base claro: índice >= tamaño
- ✅ NO usar bucles (suspenso automático)
- ✅ Probar con listas pequeñas primero

#### Para Swing:
- ✅ Revisar ejemplos de clase antes del examen
- ✅ Recordar índices de columnas empiezan en 0
- ✅ MouseAdapter para no implementar todos los métodos
- ✅ e.getClickCount() == 2 para doble clic

#### Para Hilos:
- ✅ Empezar simple: un hilo que funcione
- ✅ SwingUtilities.invokeLater() para actualizar UI
- ✅ synchronized + wait/notifyAll para pausar
- ✅ volatile para variables compartidas
- ✅ join() al detener para esperar hilos

#### Gestión del Tiempo:
- 50 min → Recursividad
- 60 min → Swing
- 70 min → Hilos
- 10 min → Revisar y probar

#### Errores Comunes a Evitar:
- ❌ Usar bucles en recursividad
- ❌ No usar SwingUtilities.invokeLater en hilos
- ❌ Olvidar hacer join() al detener hilos
- ❌ No sincronizar acceso a variables compartidas
- ❌ Modificar clases del dominio

### 🔍 Similitudes con Examen 2026 Ordinario

El examen ordinario de 2026 fue sobre **estaciones de esquí**. Este extraordinario mantiene:
- Misma estructura de paquetes
- Misma distribución de puntos
- Complejidad similar en cada tarea
- Temática diferente pero patrones idénticos

### 📝 Notas Importantes

- **NO necesitas implementar JDBC** (base de datos) si ya la tienes aprobada
- Las clases del dominio **NO se deben modificar**
- Compila frecuentemente para detectar errores pronto
- Si te atascas, pasa a la siguiente tarea y vuelve después

### 🎓 Sobre la Creación de este Examen

Este examen ha sido creado mediante:
1. Análisis exhaustivo de exámenes anteriores (2024-2026)
2. Identificación de patrones del profesor
3. Creación de un tema nuevo (biblioteca) que encaja en el patrón educativo
4. Mantenimiento de la estructura y dificultad estándar

**Es altamente probable que el examen real siga este mismo patrón**, posiblemente con una temática diferente pero estructura idéntica.

---

**¡Buena suerte en tu examen!** 🍀

Si completas este examen de práctica correctamente, estarás bien preparado para el examen real.
