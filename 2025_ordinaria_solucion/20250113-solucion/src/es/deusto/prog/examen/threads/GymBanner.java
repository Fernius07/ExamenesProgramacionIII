// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.threads;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

// TODO: TAREA 4 - Hilos.
// El objetivo de esta tarea es implementar el funcionamiento de un banner que
// se utilizará para mostrar publicidad de las actividades del gimnasio. El 
// banner se visualiza en una ventana y consiste en un carrusel de imágenes que 
// va cambiando cada 1,5 segundos en una secuencia continua. La ventana incluye 
// un botón con el texto “Ocultar”. Cuando se pulse el botón "Ocultar", el 
// carrusel de imágenes se reemplaza por una cuenta atrás de 5 a 0. Al finalizar
// la cuenta atrás, se vuelve a mostrar la secuencia de imágenes. El botón 
// "Ocultar" debe deshabilitarse mientras se muestra el contador y estar habilitado
// cuando se muestre la secuencia de imágenes.
public class GymBanner extends JFrame {
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
        
    public GymBanner() {
        setLayout(new BorderLayout());
        add(new JLabel("*Imágenes creadas con ChatGPT", JLabel.CENTER), BorderLayout.NORTH);
        
        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setVerticalAlignment(JLabel.CENTER);
        lblImagen.setFont(new Font("Courier", Font.BOLD, 92));
        add(lblImagen, BorderLayout.CENTER);

        btnOcultar = new JButton("Ocultar");
        add(btnOcultar, BorderLayout.SOUTH);

        btnOcultar.addActionListener(e -> {
        	btnOcultar.setEnabled(false);

        	//TODO: Incluye aquí la lógica necesaria para ocultar las imágenes
        	//y mostrar la cuenta atrás de 5 a 0 cuando se pulse el botón "Ocultar".        	
        });        
        
        setTitle("Gym Banner");
        setSize(250, 430);        
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymBanner());
    }
}