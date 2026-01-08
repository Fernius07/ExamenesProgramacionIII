package es.deusto.prog3.examen202401ord.logica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import es.deusto.prog3.examen202401ord.datos.*;
import es.deusto.prog3.examen202401ord.gui.*;
import es.deusto.prog3.examen202401ord.main.Main;

/** Gestor de usuarios
 * @author prog3 @ ingenieria.deusto.es
 */
public class GestorUsuarios {

	private Usuario usuarioLogueado = null;
	private GestorPersistencia bd = null;
	private VentanaLogin ventLogin = null;
	private VentanaChat ventChat = null;
	private ChatNoGPT chatNoGPT = new ChatNoGPT();
	private ArrayList<Conversacion> listaConvs = null;
	private Conversacion conversacionEnCurso = null;

	private static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yy HH:mm:ss" );
	
	/** Crea un gestor de usuarios
	 * @param bd	Base de datos con la que trabajar para la persistencia de usuarios
	 */
	public GestorUsuarios( GestorPersistencia bd ) {
		this.bd = bd;
	}
	
	/** Asocia el gestor de usuarios a una ventana de login
	 * @param ventLogin	Ventana de login en la que realizar trabajo de registro e identificación de usuarioss
	 */
	public void setVentanaLogin( VentanaLogin ventLogin ) {
		this.ventLogin = ventLogin;
	}
	
	/** Asocia el gestor de usuarios a una ventana de chat
	 * @param ventLogin	Ventana de chat en la que realizar el proceso de chat
	 */
	public void setVentanaChat( VentanaChat ventChat ) {
		this.ventChat = ventChat;
	}
	
	/** Registra e identifica un usuario nuevo
	 * @param nick	Nick de ese usuario
	 * @param password	Password de ese usuario
	 * @return	true si el nick no existía y la password es correcta (no null ni string vacío), false si el usuario (nick) ya existía
	 */
	public boolean registrarUsuario( String nick, String password ) {
		return registrarUsuario( new Usuario( nick, password, "", "", 0, new ArrayList<>() ) );
	}
	
	/** Registra e identifica un usuario
	 * @param usuario	Usuario a registrar
	 * @return	true si el nick no existía y la password es correcta (no null ni string vacío), false si el usuario (nick) ya existía
	 */
	public boolean registrarUsuario( Usuario usuario ) {
		if (usuario.getNick() == null || usuario.getPassword() == null || 
			usuario.getNick().isEmpty() || usuario.getPassword().isEmpty() || usuario.getNick().equals(Main.NOMBRE_CHAT)) {
			return false;
		}
		ArrayList<Usuario> lU = bd.leerUsuarios( null, "where nick='" + usuario.getNick() +"'" );
		if (lU.isEmpty()) {
			return loginUsuario( usuario.getNick(), usuario.getPassword() );
		} else {
			return false;
		}
	}

	/** Identifica y loguea un usuario
	 * @param nick	Nick de usuario a loguear
	 * @param password	Password de usuario a loguear
	 * @return	true si el nick y password son correctas, false si el usuario no existe o su password es incorrecta
	 */
	public boolean loginUsuario( String nick, String password ) {
		if (nick == null || password == null || nick.equals(Main.NOMBRE_CHAT)) {
			return false;
		}
		ArrayList<Usuario> lU = bd.leerUsuarios( null, "nick='" + nick +"'" );
		if (lU.isEmpty()) {
			return false;
		} else {
			boolean login = password.equals( lU.get(0).getPassword() );
			if (login) {
				usuarioLogueado = lU.get(0);
			}
			return login;
		}
	}
	
	/** Lanza proceso tras login
	 */
	public void procesoPostLogin() {
		if (ventLogin!=null) {
	        if (getUsuarioLogueado()==null) {
	        	JOptionPane.showMessageDialog( ventLogin, "Proceso incorrecto", "Error en login/registro", JOptionPane.ERROR_MESSAGE );
	        } else {
	        	if (ventChat!=null) {
	        		cargaTablaConversaciones(); // Carga tabla de conversaciones antes de sacar la ventana
	        		ventLogin.setVisible( false );
	        		ventChat.setVisible( true );
	        	}
	        }
		}
	}
	
	/** Hace un logout del usuario identificado (si lo hay)
	 */
	public void logout() {
		usuarioLogueado = null;
	}
	
	/** Lanza final de ventana de chat
	 */
	public void acabaChat() {
		if (ventLogin!=null && ventChat!=null) {
			ventChat.setVisible( false );
			ventLogin.setVisible( true );
			conversacionEnCurso = null;
		}
	}
	
	/** Lanza cierre de ventanas
	 */
	public void cierraVentanas() {
		if (ventChat!=null) ventChat.dispose();
	}
	

	/** Devuelve el usuario que esté logueado
	 * @return	usuario con login en curso, null si no hay ningún usuario logueado
	 */
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	/** Gestiona una nueva frase de chat que lanza el usuario en la ventana, 
	 * generando respuesta, mostrándola en la ventana, y almacenando las frases.
	 */
	public void nuevaFraseChat() {
		if (ventChat==null) return;
		String texto = ventChat.getTaChat().getText();
		if (texto.isEmpty()) return;
		
		// Comprueba si se inicia nueva conversación
		if (conversacionEnCurso==null) {
			conversacionEnCurso = new Conversacion( usuarioLogueado.getNick(), System.currentTimeMillis() );
			bd.anyadirConv( conversacionEnCurso, null );
		}
		
		// Muestra y registra la frase del usuario
		ventChat.nuevaFraseUsuario( texto );
		bd.anyadirFrase( new Frase( usuarioLogueado.getNick(), Main.NOMBRE_CHAT, System.currentTimeMillis(), texto, conversacionEnCurso.getId() ), null );

		// Calcula, muestra y registra la respuesta de ChatNoGPT
		try {
			Thread.sleep( 200 + (int)(Math.random()*400) );  // Simula una pequeña espera (décimas de segundo) aleatoria
		} catch (InterruptedException e) {} 
		String respuesta = chatNoGPT.devuelveRespuesta( texto );
		ventChat.nuevaRespuestaChat( respuesta );
		bd.anyadirFrase( new Frase( Main.NOMBRE_CHAT, usuarioLogueado.getNick(), System.currentTimeMillis(), respuesta, conversacionEnCurso.getId() ), null );

		desplazaScrollChat();
		cargaTablaConversaciones();
	}
	
	// Desplaza el scroll del panel de chat al final y vacía la caja de texto de siguiente interacción
	private void desplazaScrollChat() {
		Rectangle rect = new Rectangle(0, ventChat.getPanelChat().getHeight(), 10, 10);
		ventChat.getPanelChat().scrollRectToVisible(rect);
		// Vacía y prepara la caja de texto para siguiente interacción
		ventChat.getTaChat().setText( "" );
		ventChat.getTaChat().requestFocus();
	}
	

	public void dobleClickConversacion( int fila ) {
		if (listaConvs==null || listaConvs.size() < fila+1) return;
		Conversacion conv = listaConvs.get( fila );
		conversacionEnCurso = conv;
		ventChat.nuevoEspacioInicioChat();
		for (Frase f : conv.getFrases()) {
			if (f.getEmisor().equals(Main.NOMBRE_CHAT)) {
				ventChat.nuevaRespuestaChat( f.getTexto() );
			} else {
				ventChat.nuevaFraseUsuario( f.getTexto() );
			}
		}
		desplazaScrollChat();
	}
	
	/** Carga y visualiza la tabla de conversaciones en la ventana de chat
	 */
	public void cargaTablaConversaciones() {
		listaConvs = bd.cargaConversaciones( usuarioLogueado.getNick() );
		JTable tabla = ventChat.getTablaConversaciones();
		// TODO  T4
		// Sustituir este código...
//		Object[] columnas = new String[] { "Fecha", "Nº frases" };
//		Object[][] datos = new Object[listaConvs.size()][2];
//		for (int i=0; i<listaConvs.size(); i++) {
//			Conversacion conv = listaConvs.get(i);
//			datos[i][0] = sdf.format( new Date( conv.getFecha() ) );
//			datos[i][1] = conv.getFrases().size();
//		}
//		DefaultTableModel modelo = new DefaultTableModel( datos, columnas ) {
//			private static final long serialVersionUID = 1L;
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};
//		tabla.setModel( modelo );
		// ...por esta llamada (codificando ese método)
		defineTablaConversaciones( tabla, listaConvs );
		// ...fin T4
		tabla.repaint();
	}

	// Define y estructura tabla visual de conversaciones
	@SuppressWarnings("serial")
	private void defineTablaConversaciones( JTable tabla, ArrayList<Conversacion> listaConvs ) {
		// TODO T4
		ModeloConversacion modelo = new ModeloConversacion(listaConvs);
		tabla.setModel( modelo );
		if (yaConfiguradaTabla) return;
		tabla.getColumnModel().getColumn(0).setPreferredWidth( 20 );
		tabla.getColumnModel().getColumn(1).setPreferredWidth( 110 );
		tabla.getColumnModel().getColumn(2).setPreferredWidth( 65 );
		tabla.getColumnModel().getColumn(3).setPreferredWidth( 65 );
		tabla.setDefaultRenderer( Long.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JLabel) c).setText( sdf.format( new Date( (Long) value ) ));
				return c;
			}
		});
		tabla.setDefaultRenderer( Integer.class, new DefaultTableCellRenderer() {
			JLabelDuracion lDuracion = new JLabelDuracion();
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column==2) { // Nº Frases
					((JLabel)c).setHorizontalAlignment( JLabel.CENTER );
					return c;
				} else {
					lDuracion.setSegundos( (Integer)value );
					return lDuracion;
				}
			}
		});
		tabla.addMouseMotionListener( new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int fila = tabla.rowAtPoint( e.getPoint() );
				if (fila>=0) {
					Conversacion conv = listaConvs.get(fila);
					tabla.setToolTipText( "          " + conv.getFrases().get(0).getTexto() );
				}
			}
		});
		yaConfiguradaTabla = true;
	}
	
	// T4 Definición de atributos o clases necesarias para la tarea 4
	
	private boolean yaConfiguradaTabla = false;  // Optimización para no incluir los escuchadores y renderers más que una vez

	@SuppressWarnings("serial")
	private static class JLabelDuracion extends JLabel {
		private int segundos;
		public JLabelDuracion() {
			super( "", JLabel.CENTER );
			setFont( new Font( "Arial", Font.ITALIC, 12 ) );
		}
		public void setSegundos(int segundos) {
			this.segundos = segundos;
			setText( sdfMMSS.format( new Date( segundos*1000 ) ));
		}
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor( Color.GREEN );
			g.fillOval( -segundos/2,  -getHeight(), segundos, getHeight()*2 );
			super.paintComponent(g);
		}
	}
	
	private static SimpleDateFormat sdfMMSS = new SimpleDateFormat( "mm:ss" );
	private static final String[] CABS = { "Id", "Fecha", "Nº Frases", "Duración" };
	private static final Class<?>[] TIPOS = { String.class, Long.class, Integer.class, Integer.class };
	// Columnas: id (int) / fecha (long) / número frases (int) / segs. conv (int)
	@SuppressWarnings("serial")
	private class ModeloConversacion extends DefaultTableModel {
		private ArrayList<Conversacion> listaConvs;
		public ModeloConversacion( ArrayList<Conversacion> listaConvs ) {
			super();
			this.listaConvs = listaConvs;
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public void setDataVector(Vector dataVector, Vector columnIdentifiers) {
		}
		
		@Override
		public int getRowCount() {
			return listaConvs.size();
		}

		@Override
		public int getColumnCount() {
			return CABS.length;
		}

		@Override
		public String getColumnName(int column) {
			return CABS[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return TIPOS[columnIndex];
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public Object getValueAt(int row, int column) {
			Conversacion conv = listaConvs.get(row);
			switch (column) {
				case 0: return conv.getId() + "";
				case 1: return conv.getFecha();
				case 2: return conv.getFrases().size();
				case 3: return calculaDuracion( conv.getFrases() );
				default: return "";
			}
		}
			private int calculaDuracion( ArrayList<Frase> listaFrases ) {
				long dif = listaFrases.get(listaFrases.size()-1).getFecha() - listaFrases.get(0).getFecha();
				return (int) Math.round(dif/1000.0);
			}

	}
	
}
