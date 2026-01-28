/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.hilos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelCarretera extends JPanel {
	
    private static final long serialVersionUID = 1L;
    // Valor de progreso del bus (0 a 100)
    private int progreso = 0;
    private Image imgBus;
    private Image imgCiudad;
    private Image imgFondo;

    public JPanelCarretera() {
        imgBus = new ImageIcon("resources/images/BUS.png").getImage();
        imgCiudad = new ImageIcon("resources/images/BUS_STOP.png").getImage();
        imgFondo = new ImageIcon("resources/images/FONDO.png").getImage();
        this.setToolTipText("Imagen de fondo generada con Gemini 3");
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        if (imgFondo != null) {
            g2.drawImage(imgFondo, 0, 0, w, h, this);
        }

        int roadY = h - 64;

        g2.setColor(new Color(128, 128, 128, 200));
        g2.fillRect(0, roadY - 30, w, 60);
        
        g2.setColor(Color.YELLOW.brighter());
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{20f, 20f}, 0));
        g2.drawLine(0, roadY, w, roadY);

        int startX = 44;
        int endX = w - 106;
        int busX = startX + (int) ((endX - startX) * (progreso / 100.0));
        int busY = roadY - 32;

        g2.drawImage(imgCiudad, -12, roadY - 32, this);
        g2.drawImage(imgCiudad, w - 52, roadY - 32, this);
        g2.drawImage(imgBus, busX, busY, this);
    }
}