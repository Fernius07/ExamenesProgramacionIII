package es.deusto.hospital.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PlanoHospitalSolucion extends JFrame {

    private static final long serialVersionUID = 1L;
    
    // Panel para la leyenda de colores de las zonas con FlowLayout alineado a la izquierda
    // y 10px de separación horizontal entre componentes
    private JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    public PlanoHospitalSolucion(Map<Integer, List<Zona>> zonas) {
    	// TAREA 1.1 - Layout: Diseña el layout del plano del hospital.
    	    	
    	// Panel central con BorderLayout con 10px de separación horizontal y 
    	// vertical entre componentes
        JPanel pPrincipal = new JPanel(new BorderLayout(10, 10));
        
        // SOUTH: leyenda
        pPrincipal.add(leyenda, BorderLayout.SOUTH);
        
        // CENTER: zonas
        JPanel plantas = new JPanel(new GridLayout(zonas.size(), 1, 0, 8));
        plantas.setBorder(new EmptyBorder(0, 10, 0, 10));
        
        // Añadir un panel por cada planta
        for (List<Zona> plantaZonas : zonas.values()) {
        	plantas.add(panelPlanta(plantaZonas));
        }
                        
        pPrincipal.add(plantas, BorderLayout.CENTER);
        setContentPane(pPrincipal);
        
        // Configuración final del JFrame        
        setTitle("Plano del Hospital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Crea un panel con las zonas de una planta.
    // El panel tiene un título en la parte superior y 
    // debajo las zonas en un grid con las zonas.
    private JPanel panelPlanta(List<Zona> zonas) {
    	JPanel pPlanta = new JPanel(new BorderLayout());
    	
    	// Cabezera con el número de planta
    	JLabel titulo = new JLabel("Planta " + zonas.get(0).getPlanta(), SwingConstants.LEFT);
    	titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));    	
    	pPlanta.add(titulo, BorderLayout.NORTH);
    	// Panel con las zonas en un grid
    	JPanel pZonas = new JPanel(new GridLayout(1, zonas.size(), 4, 4));
    	zonas.forEach(z -> pZonas.add(panelZona(z)));
    	// Añadir el panel de zonas al panel de planta
    	pPlanta.add(pZonas, BorderLayout.CENTER);
    	
    	return pPlanta;
    }

    // Crea un panel para una zona con el color de fondo de la zona
    // También añade a la leyenda de colores el color y nombre de la zona
    private JPanel panelZona(Zona zona) {
        JPanel panelZona = new JPanel(new BorderLayout());
        panelZona.setBorder(new LineBorder(Color.BLACK));
        panelZona.setBackground(zona.getColor());
        
        JLabel nombreZona = new JLabel(zona.getNombre(), SwingConstants.CENTER);
        nombreZona.setFont(nombreZona.getFont().deriveFont(Font.BOLD, 14f));
        nombreZona.setForeground(zona.getColor());
        nombreZona.setVisible(false);
        panelZona.add(nombreZona, BorderLayout.CENTER);
        
        // Etiqueta con el color de la zona
		JLabel lblColor = new JLabel("      ");
		lblColor.setOpaque(true);
		lblColor.setBackground(zona.getColor());
		lblColor.setBorder(new LineBorder(Color.BLACK));		
		// Añadir a la leyenda de colores el color y nombre de la zona
		leyenda.add(lblColor);		
		leyenda.add(new JLabel(zona.getNombre()));
		
    	// TAREA 1.2: - Evento de ratón: Añade la gestión de eventos de ratón
    	// para lograr el efecto que se muestra en el vídeo Evento.mp4.
		
		// Añadir listerner de ratón para cambiar el color y mostrar el nombre al pasar el ratón
		panelZona.addMouseListener(new java.awt.event.MouseAdapter() {
			// Al entrar el ratón se poner el color más oscuro y se muestra el nombre de la zona
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				panelZona.setBackground(zona.getColor().darker());
				nombreZona.setVisible(true);
			}
			
			// Al salir el ratón se vuelve al color original y se oculta el nombre de la zona
			public void mouseExited(java.awt.event.MouseEvent evt) {
				panelZona.setBackground(zona.getColor());
				nombreZona.setVisible(false);
			}
		});
		        
        return panelZona;
    }

    public static void main(String[] args) {
    	// Definición del plano del hospital con las zonas correspondientes
    	// Cada planta del hospital tiene una lista de zonas
    	Map<Integer, List<Zona>> plano = Map.of(
		    -1, Arrays.asList(
			        new Zona("Quirófanos", -1, new Color(220, 235, 255))
		    ),
    		0, Arrays.asList(
		        new Zona("Urgencias", 0, new Color(245, 243, 187)),
		        new Zona("Recepción", 0, new Color(223, 160, 110))
		    ),
		    1, Arrays.asList(
		        new Zona("Cafetería", 1, new Color(230, 245, 230))
		    ),
		    2, Arrays.asList(
		        new Zona("Maternidad", 2, new Color(255, 205, 230)),
		        new Zona("Pediatría", 2, new Color(220, 205, 255))
		    )
        );
        // Creación de un mapa ordenado para que al invocar el método "values()"
    	// sobre el mapa devuelva las List<Zona> en orden de planta
    	SortedMap<Integer, List<Zona>> planoOrdenado = new TreeMap<>(plano);
		
    	// Creación y visualización del layout del plano del hospital
        SwingUtilities.invokeLater(() -> new PlanoHospitalSolucion(planoOrdenado));
    }
}