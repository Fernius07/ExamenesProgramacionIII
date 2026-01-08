package es.deusto.prog3.examen202401ord.gui;

import javax.swing.*;

import es.deusto.prog3.examen202401ord.logica.GestorUsuarios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** Ventana de login
 * @author prog3 @ ingenieria.deusto.es
 */
@SuppressWarnings("serial")
public class VentanaLogin extends JFrame {
	
	private GestorUsuarios gestor;
	
	private JTextField tfUsuario;
	private JPasswordField tfContrasenya;
	private JButton botonRegistrarse;
	private JButton botonIniciarSesion;
	
	/** Crea una ventana de login asociada a un gestor de usuarios
	 * @param gestorUsuarios	Gestor de usuarios con el que trabajará la ventana
	 */
	public VentanaLogin( GestorUsuarios gestorUsuarios ) {
		this.gestor = gestorUsuarios;
		gestor.setVentanaLogin( this );
		
		setTitle( "Chat no GPT - Login" );
		setSize( 400, 150 );
		setLocationRelativeTo( null );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setLayout( new BorderLayout(10, 10) );
		
		JPanel panelCampos = new JPanel();
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel( new FlowLayout( FlowLayout.LEFT ));
		JPanel panel2 = new JPanel( new FlowLayout( FlowLayout.LEFT ));
		tfUsuario = new JTextField( 20 );
		tfContrasenya = new JPasswordField( 15 );
		
		panel1.add(new JLabel("Usuario: "));
		panel1.add(tfUsuario);
		panelCampos.add( panel1 );
		panel2.add(new JLabel("Contraseña: "));
		panel2.add(tfContrasenya);
		panelCampos.add( panel2 );
		
		JPanel panelBotones = new JPanel();
		botonRegistrarse = new JButton("Registrarse");
		botonIniciarSesion = new JButton("Iniciar Sesión");
		panelBotones.add(botonRegistrarse);
		panelBotones.add(botonIniciarSesion);
		
		add(panelCampos, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
		
		getRootPane().setDefaultButton( botonIniciarSesion );
		
		// Listeners
		
		botonRegistrarse.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	gestor.logout();
		        gestor.registrarUsuario( tfUsuario.getText(), new String(tfContrasenya.getPassword()) );
		        gestor.procesoPostLogin();
		    }
		});
		
		botonIniciarSesion.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	gestor.logout();
		        gestor.loginUsuario( tfUsuario.getText(), new String(tfContrasenya.getPassword()) );
		        gestor.procesoPostLogin();
		    }
		});
		
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				gestor.cierraVentanas();
			}
		});
	}    
	
}
