import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controlador de la interfaz gráfica diseñada en VentanaPrincipal.form.
 * Se comunica únicamente con RepositorioAvengers y CalculadoraPagos.
 */
public class VentanaPrincipal extends JFrame {

    private RepositorioAvengers repositorio;
    private final CalculadoraPagos calculadora;

    // Campos enlazados desde VentanaPrincipal.form
    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JLabel lblTitulo;
    private JComboBox<String> cmbEstructura;
    private JPanel panelFormulario;
    private JTextField txtId;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtMision;
    private JTextField txtPeligrosidad;
    private JTextField txtPagoMensual;
    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnLimpiar;
    private JTabbedPane pestanas;
    private JTable tblListado;
    private JButton btnListar;
    private JTable tblInforme;
    private JLabel lblResumenInforme;
    private JButton btnInforme;

    private DefaultTableModel modeloListado;
    private DefaultTableModel modeloInforme;

    public VentanaPrincipal() {
        this.repositorio = new RepositorioHashMap();
        this.calculadora = new CalculadoraPagos();

        setTitle("Rol de Misiones - Avengers");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 560);
        setLocationRelativeTo(null);

        configurarModelosYEventos();
    }

    private void configurarModelosYEventos() {
        cmbEstructura.setModel(new DefaultComboBoxModel<>(new String[]{
                "HashMap (Alternativa 2 - O(1))",
                "ArrayList (Alternativa 1 - O(n))"
        }));
        cmbEstructura.addActionListener(e -> cambiarEstructuraDatos());

        modeloListado = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Misión", "Peligrosidad", "Pago Mensual ($)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblListado.setModel(modeloListado);
        tblListado.setRowHeight(24);
        tblListado.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblListado.getSelectedRow() != -1) {
                cargarFilaSeleccionada(tblListado.getSelectedRow());
            }
        });

        modeloInforme = new DefaultTableModel(
                new String[]{"Nombre", "Pago Mensual", "Fondo Héroes (8%)", "Impuesto Gobierno", "Pago Neto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblInforme.setModel(modeloInforme);
        tblInforme.setRowHeight(24);

        btnBuscar.addActionListener(e -> buscarAvengerPorId());
        btnRegistrar.addActionListener(e -> registrarAvenger());
        btnModificar.addActionListener(e -> modificarAvenger());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnListar.addActionListener(e -> listarAvengers());
        btnInforme.addActionListener(e -> generarInforme());
    }

    private Avenger leerFormulario() {
        String id = txtId.getText();
        String nombre = txtNombre.getText();
        String mision = txtMision.getText();

        int peligrosidad;
        try {
            peligrosidad = Integer.parseInt(txtPeligrosidad.getText().trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("La peligrosidad debe ser un número entero entre 1 y 5.");
        }

        double pagoMensual;
        try {
            pagoMensual = Double.parseDouble(txtPagoMensual.getText().trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El pago mensual debe ser un valor numérico válido.");
        }

        return new Avenger(id, nombre, mision, peligrosidad, pagoMensual);
    }

    public void registrarAvenger() {
        try {
            Avenger nuevo = leerFormulario();
            if (!repositorio.registrar(nuevo)) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe un Avenger registrado con el ID: " + nuevo.getId(),
                        "ID Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            listarAvengers();
            generarInforme();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Avenger registrado correctamente.",
                    "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarAvenger() {
        try {
            Avenger datosActualizados = leerFormulario();
            String id = datosActualizados.getId();
            if (!repositorio.modificar(id, datosActualizados)) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró ningún Avenger con el ID: " + id,
                        "ID No Encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            listarAvengers();
            generarInforme();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Datos del Avenger " + id + " actualizados correctamente.",
                    "Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarAvengers() {
        modeloListado.setRowCount(0);
        for (Avenger a : repositorio.listar()) {
            modeloListado.addRow(new Object[]{
                    a.getId(),
                    a.getNombre(),
                    a.getMision(),
                    a.getPeligrosidad(),
                    String.format("$%.2f", a.getPagoMensual())
            });
        }
    }

    public void generarInforme() {
        modeloInforme.setRowCount(0);
        List<Avenger> lista = repositorio.listar();
        double totalNeto = 0;

        for (Avenger a : lista) {
            double pm = a.getPagoMensual();
            double fondo = calculadora.calcularFondo(pm);
            double impuesto = calculadora.calcularImpuestoMensual(pm);
            double neto = calculadora.calcularNeto(pm);
            totalNeto += neto;

            modeloInforme.addRow(new Object[]{
                    a.getNombre(),
                    String.format("$%.2f", pm),
                    String.format("$%.2f", fondo),
                    String.format("$%.2f", impuesto),
                    String.format("$%.2f", neto)
            });
        }
        lblResumenInforme.setText(String.format(
                "Total Avengers: %d | Desembolso Neto Total: $%.2f", lista.size(), totalNeto));
    }

    private void buscarAvengerPorId() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID para buscar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Avenger a = repositorio.buscarPorId(id);
        if (a == null) {
            JOptionPane.showMessageDialog(this, "No existe un Avenger con el ID: " + id,
                    "No Encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        txtNombre.setText(a.getNombre());
        txtMision.setText(a.getMision());
        txtPeligrosidad.setText(String.valueOf(a.getPeligrosidad()));
        txtPagoMensual.setText(String.valueOf(a.getPagoMensual()));
    }

    private void cargarFilaSeleccionada(int fila) {
        String id = modeloListado.getValueAt(fila, 0).toString();
        Avenger a = repositorio.buscarPorId(id);
        if (a != null) {
            txtId.setText(a.getId());
            txtNombre.setText(a.getNombre());
            txtMision.setText(a.getMision());
            txtPeligrosidad.setText(String.valueOf(a.getPeligrosidad()));
            txtPagoMensual.setText(String.valueOf(a.getPagoMensual()));
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtMision.setText("");
        txtPeligrosidad.setText("");
        txtPagoMensual.setText("");
        tblListado.clearSelection();
        txtId.requestFocus();
    }

    private void cambiarEstructuraDatos() {
        List<Avenger> actuales = repositorio.listar();
        if (cmbEstructura.getSelectedIndex() == 0) {
            repositorio = new RepositorioHashMap();
        } else {
            repositorio = new RepositorioArrayList();
        }
        for (Avenger a : actuales) {
            repositorio.registrar(a);
        }
        listarAvengers();
        generarInforme();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}