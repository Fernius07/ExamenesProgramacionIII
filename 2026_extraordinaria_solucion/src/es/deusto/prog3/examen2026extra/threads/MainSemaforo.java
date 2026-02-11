package es.deusto.prog3.examen2026extra.threads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainSemaforo extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int FILAS = 4;
    private static final int COLUMNAS = 4;

    private final JPanel[][] celdas = new JPanel[FILAS][COLUMNAS];
    private volatile boolean enEjecucion;

    public MainSemaforo() {
        setTitle("Examen 2026 - Threads");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel centro = new JPanel(new GridLayout(FILAS, COLUMNAS));
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                JPanel p = new JPanel();
                p.setBackground(Color.DARK_GRAY);
                celdas[f][c] = p;
                centro.add(p);
            }
        }
        add(centro, BorderLayout.CENTER);

        JPanel sur = new JPanel();
        JButton bIniciar = new JButton("Iniciar");
        JButton bParar = new JButton("Parar");
        bParar.setEnabled(false);

        bIniciar.addActionListener(e -> {
            bIniciar.setEnabled(false);
            bParar.setEnabled(true);
            enEjecucion = true;

            for (int f = 0; f < FILAS; f++) {
                for (int c = 0; c < COLUMNAS; c++) {
                    final int fila = f;
                    final int columna = c;
                    Thread t = new Thread(() -> {
                        while (enEjecucion) {
                            SwingUtilities.invokeLater(() -> alternarColor(fila, columna));
                            try {
                                Thread.sleep(periodoAleatorio());
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    });
                    t.setDaemon(true);
                    t.start();
                }
            }
        });

        bParar.addActionListener(e -> {
            bIniciar.setEnabled(true);
            bParar.setEnabled(false);
            enEjecucion = false;
        });

        sur.add(bIniciar);
        sur.add(bParar);
        add(sur, BorderLayout.SOUTH);
    }

    private void alternarColor(int fila, int columna) {
        JPanel celda = celdas[fila][columna];
        celda.setBackground(celda.getBackground().equals(Color.DARK_GRAY) ? Color.GREEN : Color.DARK_GRAY);
    }

    private int periodoAleatorio() {
        return 400 + (int) (Math.random() * 1200);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainSemaforo().setVisible(true));
    }
}
