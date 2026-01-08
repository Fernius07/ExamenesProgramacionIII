// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

import es.deusto.prog.examen.parcial.Actividad;

public class HorarioGimnasioJTable extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Actividad> actividades;
    
    public HorarioGimnasioJTable(List<Actividad> actividades) {
    	this.actividades = actividades;
    	
		//TODO: TAREA 2: Modificar el modelo de datos y renderizado de la tabla de actividades.
        String[] columnNames = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        String[][] data = {
            {"09:00", "Yoga", "", "Yoga", "", ""},
            {"10:00", "", "Pilates", "", "Pilates", ""},
            {"11:00", "", "Zumba", "", "Zumba", ""},
            {"12:00", "Boxeo", "", "", "", "Boxeo"},
            {"13:00", "Spinning", "", "Spinning", "", "Spinning"}
        };

        JTable tablaActividades = new JTable(data, columnNames);                       
        
        //TODO: TAREA 3: Mostrar un mensaje de confirmación para cerrar de la aplicación al pulsar CTRL + E.
        
		this.add(tablaActividades.getTableHeader(), BorderLayout.NORTH);
		this.add(tablaActividades, BorderLayout.CENTER);

		JLabel lblFooter = new JLabel("<html><a href=\"\">Icons created by Freepik - Flaticon (https://www.flaticon.com/authors/freepik)</a><html>", JLabel.RIGHT);
		lblFooter.setForeground(Color.BLUE);
		this.add(lblFooter, BorderLayout.SOUTH);

		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
    }
}