package es.deusto.hospital.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PlanoHospital extends JFrame {

    private static final long serialVersionUID = 1L;

    public PlanoHospital(Map<Integer, List<Zona>> zonas) {    	
    	// TAREA 1.1 - Layout: Diseña el layout del plano del hospital.
    	
    	// TAREA 1.2: - Evento de ratón: Añade la gestión de eventos de ratón
    	// para lograr el efecto que se muestra en el vídeo Evento.mp4.
    	
    	// -------- INICIO DEL CÓDIGO A REEMPLAZAR -------- //
    	JPanel pPrincipal = new JPanel(new BorderLayout());
        // Color de fondo inicial para el panel principal
    	Color bgColor = new Color(167, 201, 87);
    	pPrincipal.setBackground(bgColor);
        
        JLabel titleLabel = new JLabel("Plano del Hospital", JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        // IMPORTANTE: Ajustar el color del texto del label para que sea más oscuro que el color de fondo     
        titleLabel.setForeground(bgColor.darker());        
        pPrincipal.add(titleLabel, BorderLayout.CENTER);       
                
        setContentPane(pPrincipal);
        // -------- FIN DEL CÓDIGO A REEMPLAZAR -------- //  
        
        // Configuración final del JFrame
        setTitle("Plano del Hospital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 600);
        setLocationRelativeTo(null);
        setVisible(true);
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
    	// sobre el mapa devuelva las plantas en orden de menor a mayor.
    	SortedMap<Integer, List<Zona>> planoOrdenado = new TreeMap<>(plano);
		
    	// Creación y visualización del layout del plano del hospital
        SwingUtilities.invokeLater(() -> new PlanoHospital(planoOrdenado));
    }
}