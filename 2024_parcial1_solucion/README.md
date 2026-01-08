# Examen parcial Prog.III & Prog.Apps (2024-11-15)

Este repositorio contiene las especificaciones de implementación para cuatro tareas de desarrollo en Java Swing, diseñadas para evaluar habilidades en layout, renderización personalizada, eventos de teclado y manejo de hilos en aplicaciones gráficas.

Enunciado: https://docs.google.com/document/d/1ef7uzV0x_lnwq5UTA2rWjJ5oV-Nxj4nf_bYcmVwsIsA

## Tarea 1: Layout de Componentes de Plan de Actividades

**Objetivo**: Diseñar una interfaz gráfica que muestre el horario semanal de actividades de un gimnasio.

![T1  Layout-solucion](https://github.com/user-attachments/assets/27c25823-00fa-4bd9-844d-86b330ab95a1)

**Instrucciones**:
- Crear una estructura que represente el calendario semanal de actividades del gimnasio
- Usar un `GridLayout` para el panel principal.
- Distribuir Labels o Paneles con los días de la semana, los bloques horarios y las diferentes sesiones de las actividades.

---

## Tarea 2: Modelo de Datos y Render de Tabla de Actividades

**Objetivo**: Implementar un modelo de datos personalizado para la tabla de actividades y aplicar un renderizado específico a sus celdas.

![T2-T3  JTable-solucion](https://github.com/user-attachments/assets/f43c0e89-88a2-4221-8b24-583f0cbe5b87)

**Instrucciones**:
- Crear una clase de modelo de datos personalizada que represente las actividades.
  - Las celdas deben ser no editables.
  - La primera columna muestra el horario y el resto muestra actividades en función del día de la semana.
- Implementar un `TableCellRenderer` que, en cada celda de actividad, muestre una imagen centrada de la actividad y un fondo de color según el tipo de actividad.
- Definir un render personalizado para la cabecera de la tabla.
- Ajsutar la altura mínima de las celdas.
- Modificar la tabla para que no se puedan reordenar las columnas.

---

## Tarea 3: Evento de Teclado

**Objetivo**: Implementar un evento de teclado que permita cerrar la aplicación mediante la combinación de teclas `CTRL + E`.

**Instrucciones**:
- Capturar la pulsación de `CTRL + E` y mostrar un cuadro de diálogo de confirmación para cerrar la aplicación.

---

## Tarea 4: Implementación de la Lógica de los Hilos del Juego de Máquina de Slots

**Objetivo**: Desarrollar la lógica de un juego de máquina de slots que simule una secuencia de imágenes de actividades cambiando de forma aleatoria.

https://github.com/user-attachments/assets/8ddf6974-3f70-4c5a-a401-f148ca0e15c5

**Instrucciones**:
- Implementar hilos independientes para cada "slot", de modo que se muestre una imagen aleatoria de actividad cada 100ms en cada uno de los 3 JLabel.
- Al pulsar el botón **Iniciar**, los tres hilos deben comenzar a cambiar las imágenes en sus respectivas celdas de forma continua.
- Al pulsar el botón **Parar**, detener completamente los hilos y verificar si las tres imágenes mostradas son iguales.
  - Si todas las imágenes son iguales, mostrar un cuadro de diálogo indicando que el usuario ha ganado un abono mensual para la actividad mostrada.

---

Todas las tareas tiene las clases base y clases con el sufijo "Solucion" para diferenciar el código que les daríamos y nuestra posibles solución.
