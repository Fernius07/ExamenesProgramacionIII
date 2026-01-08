package es.deusto.prog.examen.threads.solucion;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.SwingUtilities;


public class MainThreadsSolucion extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int COLUMNAS = 8;
    private static final int FILAS = 8;

    private JPanel[][] paneles;

    private Thread[][] threads;

    public MainThreadsSolucion() {
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(FILAS, COLUMNAS));

        paneles = new JPanel[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.LIGHT_GRAY);
                panel.setOpaque(true);
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                panel.setBorder(border);
                
                panelCentral.add(panel);
                paneles[i][j] = panel;                
            }
        }

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        add(panelBotones, BorderLayout.SOUTH);

        JButton botonIniciar = new JButton("Iniciar");
        panelBotones.add(botonIniciar);

        JButton botonParar = new JButton("Parar");
        botonParar.setEnabled(false);
        panelBotones.add(botonParar);

        botonIniciar.addActionListener(e -> {
            botonIniciar.setEnabled(false);
            botonParar.setEnabled(true);
            // TODO Tarea 2 - Hilos: Añade aquí el código para iniciar la visualización de los colores
            threads = new Thread[FILAS][COLUMNAS];
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < COLUMNAS; j++) {
                    
                    final int fila = i;
                    final int columna = j;
                    threads[i][j] = new Thread(() -> {
                        try {
                            int periodo = periodoAleatorio();

                            while (!Thread.currentThread().isInterrupted()) {
                                Thread.sleep(periodo);
                                cambiarEstado(fila, columna);
                            }
                        } catch (InterruptedException e1) {
                            Thread.currentThread().interrupt();
                        }
                    });
                    threads[i][j].start();
                }
            }
        });

        botonParar.addActionListener(e -> {
            botonIniciar.setEnabled(true);
            botonParar.setEnabled(false);
            // TODO Tarea 2 - Hilos: Añade aquí el código para detener la visualización de los colores
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < COLUMNAS; j++) {
                    threads[i][j].interrupt();
                }
            }
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private int periodoAleatorio() {
        return (int) (Math.random() * 1500) + 500;
    }

    private void cambiarEstado(int fila, int columna) {
        if (paneles[fila][columna].getBackground().equals(Color.LIGHT_GRAY)) {
            paneles[fila][columna].setBackground(Color.RED);
        } else {
            paneles[fila][columna].setBackground(Color.LIGHT_GRAY);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainThreadsSolucion::new);
    }
}
