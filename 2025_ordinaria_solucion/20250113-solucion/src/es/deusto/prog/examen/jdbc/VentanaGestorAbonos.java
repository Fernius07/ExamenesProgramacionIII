// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.jdbc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.Actividad.TipoActividad;
import es.deusto.prog.examen.Persona;
import es.deusto.prog.examen.Persona.TipoAbono;

public class VentanaGestorAbonos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JList<Persona> jListPersonas;
    private DefaultListModel<Persona> listModelPersonas;
    private JList<TipoActividad> jListActividades;
    private DefaultListModel<TipoActividad> listModelActividades;
    private JComboBox<Object> jComboTipoAbono;
    private JTextField jTextNombre;
    private JTextField jTextEmail;
    private JButton btnActualizarPersona;
    private JLabel lblInfo;
    
    private List<Actividad> actividades;
    private GestorBD gestorBD = new GestorBD();
    private boolean cambiosPendientes = false;

    public VentanaGestorAbonos() {
    	this.actividades = gestorBD.loadActividades();
    	    	    	
		List<Persona> personas = new ArrayList<>();
		
		this.actividades.forEach(actividad -> {
			actividad.getPersonas().forEach(persona -> {
				if (!personas.contains(persona)) {
					personas.add(persona);
				}
			});
		});
		
		Collections.sort(personas, (p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
    	
    	this.setLayout(new BorderLayout());
    	
    	JPanel panelGeneral = new JPanel(new GridLayout(1, 2));

        listModelPersonas = new DefaultListModel<>();
        personas.forEach(persona -> listModelPersonas.addElement(persona));
        jListPersonas = new JList<>(listModelPersonas);
        jListPersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListPersonas.addListSelectionListener(e -> {
            if (jListPersonas.getSelectedIndex() >= 0) {
                actualizarDetallesPersona(personas.get(jListPersonas.getSelectedIndex()));
            } else {
                limpiarDetallesPersona();
            }
            
            btnActualizarPersona.setEnabled(false);
            cambiosPendientes = false;
            lblInfo.setText("");
        });
        panelGeneral.add(new JScrollPane(jListPersonas));

        JPanel panelDetalles = new JPanel(new GridLayout(2, 1));
        JPanel panelInfo = new JPanel(new GridLayout(3, 1));

        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNombre.add(new JLabel("Nombre:"));
        jTextNombre = new JTextField(18);
        jTextNombre.setEditable(false);
        panelNombre.add(jTextNombre);
        panelInfo.add(panelNombre);

        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEmail.add(new JLabel("Email: "));
        jTextEmail = new JTextField(18);
        jTextEmail.setEditable(false);
        panelEmail.add(jTextEmail);
        panelInfo.add(panelEmail);

        JPanel panelAbono = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAbono.add(new JLabel("Abono:"));
        jComboTipoAbono = new JComboBox<>();
        jComboTipoAbono.addItem("Selecciona un tipo de abono");
        
        for (TipoAbono tipo : TipoAbono.values()) {
            jComboTipoAbono.addItem(tipo);
        }
        
        jComboTipoAbono.addActionListener(e -> {
            if (jListPersonas.getSelectedIndex() >= 0 && jComboTipoAbono.getSelectedIndex() > 0) {
                Persona persona = personas.get(jListPersonas.getSelectedIndex());
                TipoAbono nuevoTipo = (TipoAbono) jComboTipoAbono.getSelectedItem();

                if (cambiosPendientes) {
                    resetCambiosPendientes(persona);
                }

                int diferenciaActividades = nuevoTipo.getNumActividades() - persona.getActividades().size();

                if (diferenciaActividades > 0) {
                    gestionarAmpliacionAbono(persona, nuevoTipo, diferenciaActividades);
                } else if (diferenciaActividades < 0) {
                    gestionarReduccionAbono(persona, -diferenciaActividades);
                }
            } else {
                resetCambiosPendientes(personas.get(jListPersonas.getSelectedIndex()));
            }
        });
        
        panelAbono.add(jComboTipoAbono);
        panelInfo.add(panelAbono);

        panelDetalles.add(panelInfo);

        listModelActividades = new DefaultListModel<>();
        jListActividades = new JList<>(listModelActividades);
        jListActividades.setBorder(new TitledBorder("Actividades seleccionadas"));
        jListActividades.setEnabled(false);
        jListActividades.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.toString());                       
            ImageIcon resizedIcon = new ImageIcon(value.getIcon().getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));            

            label.setIcon(resizedIcon);            
            label.setBackground(value.getColor());
            label.setOpaque(true);
            
            return label;
        });        

        JPanel panelActividades = new JPanel(new BorderLayout());
        panelActividades.add(new JScrollPane(jListActividades), BorderLayout.CENTER);

        panelDetalles.add(panelActividades);
        panelGeneral.add(panelDetalles);

        lblInfo = new JLabel("");
        lblInfo.setFont(lblInfo.getFont().deriveFont(12.0f).deriveFont(Font.ITALIC));
        lblInfo.setHorizontalAlignment(JLabel.CENTER);
        lblInfo.setForeground(Color.RED);
        this.add(lblInfo, BorderLayout.NORTH);
        
        btnActualizarPersona = new JButton("Actualizar abono y actividades");
        btnActualizarPersona.setEnabled(false);
		btnActualizarPersona.addActionListener(e -> {
			if (cambiosPendientes) {				
				Persona persona = personas.get(jListPersonas.getSelectedIndex());
				TipoAbono nuevoTipo = (TipoAbono) jComboTipoAbono.getSelectedItem();
				
				persona.getActividades().forEach(a -> {
					a.getPersonas().remove(persona);					
				});				
								
				List<Actividad> nuevasActividades = new ArrayList<>();
				
				for (int i = 0; i < listModelActividades.size(); i++) {
					TipoActividad tipo = listModelActividades.getElementAt(i);

					actividades.forEach(a -> {
						if (a.getTipo().equals(tipo)) {
							nuevasActividades.add(a);
							a.getPersonas().add(persona);
						}						
					});
				}
				
				persona.setTipoAbono(nuevoTipo);
				persona.setActividades(nuevasActividades);				
				actualizarPersona(persona);
				
				lblInfo.setText("");
				btnActualizarPersona.setEnabled(false);
				cambiosPendientes = false;
			}
		});        
        
        JPanel panelBotonUpdate = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonUpdate.add(btnActualizarPersona);
        this.add(panelBotonUpdate, BorderLayout.SOUTH);
                
        this.add(panelGeneral, BorderLayout.CENTER);
        
        this.setTitle("Gestión de abonos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 400);
		this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void gestionarAmpliacionAbono(Persona persona, TipoAbono nuevoTipo, int numNuevas) {
        List<Actividad> actividadesDisponibles = actividadesDisponible(persona.getActividades());

        if (actividadesDisponibles.size() < numNuevas) {
            JOptionPane.showMessageDialog(this,
                    String.format("No se puede ampliar a abono %s. No hay plazas disponibles", nuevoTipo),
                    "Error al ampliar el abono", JOptionPane.ERROR_MESSAGE);
            resetCambiosPendientes(persona);
            return;
        }

        JOptionPane.showMessageDialog(this, String.format("Se añadirá(n) %d actividad(es)", numNuevas),
                "Ampliación del abono", JOptionPane.INFORMATION_MESSAGE);

        for (int i = 0; i < numNuevas; i++) {
            listModelActividades.addElement(actividadesDisponibles.remove(0).getTipo());
        }

        lblInfo.setText("Ampliación del abono - Recuerde actualizar la persona antes de salir");
        btnActualizarPersona.setEnabled(true);
        cambiosPendientes = true;
    }
    
    private void resetCambiosPendientes(Persona persona) {
        actualizarDetallesPersona(persona);
        btnActualizarPersona.setEnabled(false);
        cambiosPendientes = false;
        lblInfo.setText("");
    }

    private void gestionarReduccionAbono(Persona persona, int numEliminar) {
        JOptionPane.showMessageDialog(this, String.format("Se eliminará(n) %d actividad(es)", numEliminar),
                "Reducción del abono", JOptionPane.WARNING_MESSAGE);

        for (int i = 0; i < numEliminar; i++) {
            listModelActividades.removeElementAt(listModelActividades.size() - 1);
        }

        lblInfo.setText("Reducción del abono - Recuerde actualizar la persona antes de salir");
        btnActualizarPersona.setEnabled(true);
        cambiosPendientes = true;
    }    
    
    private void actualizarDetallesPersona(Persona persona) {
        jTextNombre.setText(persona.getNombre());
        jTextEmail.setText(persona.getEmail());
        jComboTipoAbono.setSelectedItem(persona.getTipoAbono());
        listModelActividades.clear();
        persona.getActividades().forEach(actividad -> listModelActividades.addElement(actividad.getTipo()));
    }

    private void limpiarDetallesPersona() {
        jTextNombre.setText("");
        jTextEmail.setText("");
        jComboTipoAbono.setSelectedIndex(0);
        listModelActividades.clear();
    }

    private List<Actividad> actividadesDisponible(List<Actividad> actividadesApuntadas) {
    	List<Actividad> actividadesDisponibles = new ArrayList<>(actividades);
    	
    	for (Actividad a : actividades) {
    		if (a.getPlazasDisponibles() == 0) {
    			actividadesDisponibles.remove(a);
    		}
    	}
    	
		actividadesDisponibles.removeAll(actividadesApuntadas);
		
		return actividadesDisponibles;
    }
    
    private void actualizarPersona(Persona persona) {
    	// Invocación al método de la Tarea 1
    	if (gestorBD.updatePersona(persona)) {
    		System.out.format("\n- La información de '%s' se ha actualizado", persona.getNombre());    
    	} else {
    		System.err.format("\n* La información de '%s' no se ha actualizado", persona.getNombre());
    	}
    }
}