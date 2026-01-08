package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import datos.BDException;
import datos.GestorBD;
import datos.LeerExamenes;
import dominio.Asignatura;
import dominio.Estudiante;
import dominio.Examen;

/**
 * Ventana principal de la aplicación
 */
public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GestorBD gestorBD; // referencia la gestor de base de datos de la aplicación
	
	private JList<Asignatura> jListAsignaturas; // lista de selección de asignaturas 
	private JTable jTableEstudiantes; // tabla que muestra información para cada asignatura	
	private JPanel panelLateral; // panel lateral de la ventana
	
	private JTable jTableResultados; // tabla que muestra los resultados de los exámenes de cada estudiante	
	
	private Map<Integer, List<Examen>> mapaExamenes; // resultados de exámenes realizados para cada estudiante

	public VentanaPrincipal() {		
		try {
			String[] ficheros = {"src/2020-11-10.csv", "src/2021-01-14.csv" };
			mapaExamenes = LeerExamenes.leerFicheros(ficheros, 145315);
			
			prepararBD();
			
			List<Examen> examenes = mapaExamenes.values().stream().flatMap(List::stream).collect(Collectors.toList());
			gestorBD.insertarExamenes(examenes);
		} catch (BDException | IOException e) {
			System.out.println("Error inicializando la aplicación. " + e.getCause());
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					gestorBD.desconectar();
				} catch (BDException e1) {
					System.out.println("Error desconectando la BD");
				}
				System.out.println("Programa finalizado");
			}
			
		});
		
		setSize(640, 480);
		setTitle("Gestor competencias");
		
		crearListaAsignaturas();
		
		panelLateral= new JPanel(new BorderLayout());
		add(panelLateral, BorderLayout.CENTER);
		
		crearJTableEstudiantes();
		crearTablaResultados();
		
		setVisible(true);
	}

	private void prepararBD() throws BDException {
		// crear instancia de gestor de bd
		gestorBD = new GestorBD();
		// conectar a bd
		gestorBD.conectar("competencias.bd");
		// crear tablas de base de datos
		gestorBD.crearTablas();
		gestorBD.insertarDatosPrueba();
		System.out.println("Base de datos creada correctamente");			
	} 
	
	// crea la lista de selección de asigntura
	private void crearListaAsignaturas() {
		try {
			List<Asignatura> asignaturas = gestorBD.listadoAsignaturas();		
			jListAsignaturas = new JList<Asignatura>(new Vector<Asignatura>(asignaturas));
			jListAsignaturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			jListAsignaturas.addListSelectionListener(e -> {
				if (!e.getValueIsAdjusting()) {
					actualizarTablaEstudiantes();
				}
			});
			
			add(jListAsignaturas, BorderLayout.WEST);
		} catch (BDException e) {
			System.out.println("Error obteniendo asignaturas de la BD. " + e.getCause());
		}
	}
	
	private void crearJTableEstudiantes() {
		jTableEstudiantes = new JTable();
		jTableEstudiantes.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		jTableEstudiantes.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				((AbstractTableModel) jTableResultados.getModel()).fireTableDataChanged();
			}
		});
		
		actualizarTablaEstudiantes();
		
		JScrollPane jPanelTablaAsignaturas = new JScrollPane(jTableEstudiantes);
		panelLateral.add(jPanelTablaAsignaturas);
	}
	
	private void actualizarTablaEstudiantes() {
		String[] cabeceras = { "NIA", "Apellido", "Nombre" };
		Object[][] datos = new Object[0][cabeceras.length];
		
		List<Estudiante> estudiantes = new ArrayList<>();
		
		try {
			int i = 0;
			if (!jListAsignaturas.isSelectionEmpty()) {
				estudiantes = gestorBD.obtenerEstudiantesMatriculados(jListAsignaturas.getSelectedValue());
				datos = new Object[estudiantes.size()][cabeceras.length];
				for (Estudiante e : estudiantes) {
					datos[i][0] = e.getNIA();
					datos[i][1] = e.getApellido();
					datos[i][2] = e.getNombre();
					i++;
				}
			}
		} catch (BDException e) {
			System.out.println("Error obteniendo estudiantes de la BD. " + e.getCause());
		}
		
		jTableEstudiantes.setModel(new DefaultTableModel(datos, cabeceras) {
			
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
		});
		
		jTableEstudiantes.getColumnModel().getColumn(0).setMaxWidth(40);
		jTableEstudiantes.getColumnModel().getColumn(2).setMaxWidth(100);
	}
	
	// T4. Modifica la tabla para añadir el modelo de datos y los renderers necesarios
	private void crearTablaResultados() {
		jTableResultados = new JTable(new ModeloTablaResultados());
		jTableResultados.setDefaultRenderer(LocalDate.class, new FechaRenderer());
		jTableResultados.setDefaultRenderer(Float.class, new CompentenciasRenderer());
		
	    Dimension tablePreferredSize = new Dimension(jTableResultados.getPreferredSize().width, 100);
	    jTableResultados.setPreferredScrollableViewportSize(tablePreferredSize);
		
		JScrollPane panelTabla = new JScrollPane(jTableResultados);
		panelLateral.add(panelTabla, BorderLayout.SOUTH);
	}
	
	class ModeloTablaResultados extends AbstractTableModel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
				
		String[] cabecera = { "Fecha", "CE-01", "CE-02", "CE-03", "CE-04", "CE-05" };

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			int filaSeleccionada = jTableEstudiantes.getSelectedRow();
			if (filaSeleccionada == -1) {
				return "";
			}
			
			Examen examen = mapaExamenes.get(jTableEstudiantes.getValueAt(jTableEstudiantes.getSelectedRow(), 0)).get(rowIndex);
			if (columnIndex == 0) { 
				return examen.getFecha();
			}
			
			int indiceCompetencia = columnIndex - 1;
			if (indiceCompetencia < examen.getResultados().size()) {
				return examen.getResultados().get(indiceCompetencia);
			}
			
			return Float.NaN;
		}
		
		@Override
		public int getRowCount() {			
			int filaSeleccionada = jTableEstudiantes.getSelectedRow();
			if (filaSeleccionada == -1) {
				return 0;
			} 
			
			int niaSeleccionado = (int) jTableEstudiantes.getValueAt(jTableEstudiantes.getSelectedRow(), 0);
			if (!mapaExamenes.containsKey(niaSeleccionado)) {
				return 0;
			}
			
			return mapaExamenes.get(niaSeleccionado).size();
		}
		
		@Override
		public int getColumnCount() {
			return cabecera.length;
		}

		@Override
		public String getColumnName(int column) {
			return cabecera[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) { 
				return LocalDate.class;
			} else {
				return Float.class;
			}
		}
		
	};
	
	class FechaRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, false, false, row, column);
			
			if (column == 0) {
				DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("yy/MM/dd");
				setText(formateadorFecha.format((LocalDate) value));
				setFont(getFont().deriveFont(getFont().getStyle() | java.awt.Font.BOLD));
			}
			
			return this;
		}				
	}
	
	class CompentenciasRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, false, false, row, column);
			
			setHorizontalAlignment(CENTER);
			
			float resultado = ((Float) value);
			if (!Float.isNaN(resultado)) {
				if (resultado >= 5.0f) {
					setBackground(Color.GREEN);
				} else { 
					setBackground(Color.RED);
				}
			} else {
				setText("-");
				setBackground(Color.LIGHT_GRAY);
			}
			
			return this;
		}				
	}
}
