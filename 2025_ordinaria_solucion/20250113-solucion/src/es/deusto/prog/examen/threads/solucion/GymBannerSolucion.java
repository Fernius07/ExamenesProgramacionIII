// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.threads.solucion;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

//TODO: TAREA 4 - Hilos.
//El objetivo de esta tarea es implementar el funcionamiento de un banner que
//se utilizará para mostrar publicidad de las actividades del gimnasio. El 
//banner se visualiza en una ventana y consiste en un carrusel de imágenes que 
//va cambiando cada 1,5 segundos en una secuencia continua. La ventana incluye 
//un botón con el texto “Ocultar”. Cuando se pulse el botón "Ocultar", el 
//carrusel de imágenes se reemplaza por una cuenta atrás de 5 a 0. Al finalizar
//la cuenta atrás, se vuelve a mostrar la secuencia de imágenes. El botón 
//"Ocultar" debe deshabilitarse mientras se muestra el contador y estar habilitado
//cuando se muestre la secuencia de imágenes.
public class GymBannerSolucion extends JFrame {
    private static final long serialVersionUID = 1L;
	private JLabel lblImagen;
    private JButton btnOcultar;
    
    protected static String[] imagenes = {
            "resources/images/banner1.png",
            "resources/images/banner2.png",
            "resources/images/banner3.png",
            "resources/images/banner4.png",
            "resources/images/banner5.png"
    };

    // Hilo para la animación del banner
    private Thread hiloBanner;

    public GymBannerSolucion() {
        setLayout(new BorderLayout());
        add(new JLabel("*Imágenes creadas con ChatGPT", JLabel.CENTER), BorderLayout.NORTH);
        
        // Label del banner de imágenes
        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setVerticalAlignment(JLabel.CENTER);
        lblImagen.setFont(new Font("Courier", Font.BOLD, 92));
        add(lblImagen, BorderLayout.CENTER);

        // Botón para ocultar el banner
        btnOcultar = new JButton("Ocultar");
        add(btnOcultar, BorderLayout.SOUTH);

        // Iniciar el hilo de animación del banner
        iniciarBanner();

        // Acción del botón de ocultar el banner
        btnOcultar.addActionListener(e -> {
        	btnOcultar.setEnabled(false);
        	
        	//TODO: Incluye aquí la lógica necesaria para ocultar las imágenes
        	//y mostrar la cuenta atrás de 5 a 0 cuando se pulse el botón "Ocultar".        	
        	ocultarBanner();	
        });        
        
        setTitle("Gym Banner");
        setSize(250, 450);        
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Método para resetear el banner (eliminar imagen y texto)
    protected void resetLblBanner() {
    	SwingUtilities.invokeLater(() -> {
			lblImagen.setText("");
			lblImagen.setIcon(null);
		});    	
    }
    
    // Método para actualizar la imagen del banner
    protected void actualizarLblBanner(String imagen) {
    	SwingUtilities.invokeLater(() -> lblImagen.setIcon(new ImageIcon(imagen)));
    }
    
    // Método para actualizar el texto del banner
    protected void actualizarLblBanner(int tiempo) {
    	SwingUtilities.invokeLater(() -> {
    		lblImagen.setText(String.valueOf(tiempo));
    	});
    }

    // Método para iniciar el hilo del banner
    private void iniciarBanner() {
        hiloBanner = new Thread(() -> {
        	// Se resetea el label
	        resetLblBanner();
        	
            int numImagen = 0;

            while (!Thread.currentThread().isInterrupted()) {            
                // Se actualiza la imagen del label                	
            	actualizarLblBanner(imagenes[numImagen]);
	                
                try {
                	// Esperar 1,5 segundos
	                Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Coger el índice de la siguiente imagen
                numImagen = (numImagen + 1) % imagenes.length;
            }
        });

        hiloBanner.start();
    }
    
    // Método para ocultar el banner y mostrar la cuenta atrás
    private void ocultarBanner() {
    	hiloBanner.interrupt();
                
        new Thread(() -> {
	        // Se resetea el label
	        resetLblBanner();

        	for (int i = 5; i >= 0; i--) {
				// Se actaluza el valor numérico del Label
				actualizarLblBanner(i);
			    
				try {
			    	Thread.sleep(1000);
			    } catch (InterruptedException e) {
			        Thread.currentThread().interrupt();
			    }			
			}
			
        	// Se habilita el botón de ocultar al finalizar la cuenta atrás
        	SwingUtilities.invokeLater(() -> btnOcultar.setEnabled(true));
	        // Se vuelve a iniciar el hilo del banner
	        iniciarBanner();
        }).start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymBannerSolucion());
    }
}