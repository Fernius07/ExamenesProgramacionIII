package es.deusto.prog3.examen202401ord.logica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import es.deusto.prog3.examen202401ord.datos.Conversacion;
import es.deusto.prog3.examen202401ord.datos.Frase;
import es.deusto.prog3.examen202401ord.datos.GestorPersistencia;
import es.deusto.prog3.examen202401ord.datos.Usuario;
import es.deusto.prog3.examen202401ord.gui.VentanaChat;
import es.deusto.prog3.examen202401ord.gui.VentanaLogin;
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
	private void defineTablaConversaciones( JTable tabla, ArrayList<Conversacion> listaConvs ) {
		// TODO T4
		tabla.setModel(new TableModelConversacion(listaConvs));
		
		// Se define la anchura de las columnas
		tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(110);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(65);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(65);

		// Render para la columa "Id"
		tabla.getColumnModel().getColumn(0).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
			// Se usa el render por defecto
			JLabel result = (JLabel) table.getDefaultRenderer(String.class).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// Se establece el ToolTipText con el texto de la primera frase de la conversación
			result.setToolTipText(((TableModelConversacion) table.getModel()).getConversaciones().get(row).getFrases().get(0).getTexto());
			
			return result;			
		});
		
		//Render para la columna Fecha
		tabla.getColumnModel().getColumn(1).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
			//Se formatea la fecha usando el SimpleDateFormat definido como atributo de a nivel de clase
			JLabel result = new JLabel(sdf.format(new Date((long) value)));
			
			if (isSelected || hasFocus) {
				result.setForeground(table.getSelectionForeground());
				result.setBackground(table.getSelectionBackground());
			} else {
				result.setForeground(table.getForeground());
				result.setBackground(table.getBackground());
			}
			
			// Se establece el ToolTipText con el texto de la primera frase de la conversación
			result.setToolTipText(((TableModelConversacion) table.getModel()).getConversaciones().get(row).getFrases().get(0).getTexto());
			result.setOpaque(true);
			
			return result;
		});
		
		//Render para la columna Nº Frases
		tabla.getColumnModel().getColumn(2).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			// Se centra el texto
			result.setHorizontalAlignment(JLabel.CENTER);
			
			if (isSelected || hasFocus) {
				result.setForeground(table.getSelectionForeground());
				result.setBackground(table.getSelectionBackground());
			} else {
				result.setForeground(table.getForeground());
				result.setBackground(table.getBackground());
			}
			
			// Se establece el ToolTipText con el texto de la primera frase de la conversación
			result.setToolTipText(((TableModelConversacion) table.getModel()).getConversaciones().get(row).getFrases().get(0).getTexto());
			result.setOpaque(true);
			
			return result;
		});

		// Render para la columna Duración
		tabla.getColumnModel().getColumn(3).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {			
			LabelDuracion result = new LabelDuracion((int)value);
			
			// Se establece el ToolTipText con el texto de la primera frase de la conversación
			result.setToolTipText(((TableModelConversacion) table.getModel()).getConversaciones().get(row).getFrases().get(0).getTexto());
			
			return result;
		});
	}
	
	private class LabelDuracion extends JLabel {
		private static final long serialVersionUID = 1L;
		private int segundos;
		
		public LabelDuracion(int segundos) {
			super();
			setFont(new Font("Arial", Font.ITALIC, 12));
			setHorizontalAlignment(JLabel.CENTER);
			
			this.segundos = segundos;
			
			SimpleDateFormat dateFormatter = new SimpleDateFormat("mm:ss");						
			setText(dateFormatter.format(new Date(segundos * 1000)));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.GREEN );
			g.fillOval(-segundos/2, -getHeight(), segundos, getHeight()*2);
			super.paintComponent(g);
		}
	}
	
	// T4 Definición de atributos o clases necesarias para la tarea 4
	private class TableModelConversacion extends DefaultTableModel {
		
		private static final long serialVersionUID = 1L;
		private List<Conversacion> conversaciones;
		private String[] columnas = {"Id", "Fecha", "Nº Frases", "Duración"};
		private Class<?>[] claseColumna = {String.class, Long.class, Integer.class, Integer.class};
		
		public TableModelConversacion(List<Conversacion> conversaciones) {
			this.conversaciones = conversaciones;
		}
		
		public List<Conversacion> getConversaciones() {
			return conversaciones;
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
		@Override
		public int getColumnCount() {
			return 4;
		}
		
		@Override
		public String getColumnName(int column) {
			return columnas[column];
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return claseColumna[columnIndex];
		}
		
		@Override
		public int getRowCount() {
			return conversaciones != null ? conversaciones.size() : 0;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
				case 0: return String.valueOf(conversaciones.get(row).getId());
				case 1: return conversaciones.get(row).getFecha();
				case 2: return conversaciones.get(row).getFrases().size();
				case 3:	
					List<Frase> frases = conversaciones.get(row).getFrases(); 
					return (int) (frases.get(frases.size()-1).getFecha() - frases.get(0).getFecha()) / 1000;
				default:
					return "";
			}
		}
	}
}
