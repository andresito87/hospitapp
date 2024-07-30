package hospital.UI;

import hospital.kernel.Diagnostico;
import hospital.kernel.Cama;
import hospital.kernel.Paciente;
import hospital.kernel.VisitaMedica;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andres
 */
public class FormMantPacientes extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    public static final int AGREGAR = 0;
    public static final int MODIFICAR = 1;
    public static final int ELIMINAR = 2;

    private final Connection conexionBD;

    private Paciente pacienteActivo = null;
    private int operacionActiva = AGREGAR;

    ArrayList<Cama> listaCamas;
    private Cama camaSeleccionada = null;
    private ArrayList<VisitaMedica> visitasMedicas;
    private VisitaMedica visitaSeleccionada = null;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public FormMantPacientes(Connection conexionBD,
            javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Información de Paciente");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;

        this.desabilitarPestanhasEnModoAgregar();
        this.prepararFormulario();

    }

    public FormMantPacientes(long id, int operacion, Connection conexionBD,
            javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Información de Paciente");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;

        this.pacienteActivo = Paciente.getPaciente(id, conexionBD);
        this.operacionActiva = operacion;

        this.desabilitarPestanhasEnModoAgregar();
        this.prepararFormulario();

    }

    public FormMantPacientes(Paciente paciente, int operacion, Connection conexionBD,
            javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Información de Paciente");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;

        this.pacienteActivo = paciente;
        this.operacionActiva = operacion;

        this.prepararFormulario();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestión del Formulario">
    private void desabilitarPestanhasEnModoAgregar() {

        if (operacionActiva == FormMantCamas.AGREGAR) {

            panelFichas.setEnabledAt(1, false);
            panelFichas.setEnabledAt(2, false);
        }
    }

    private boolean prepararFormulario() {
        boolean devolucion = true;

        try {

            this.botonAgregar.setVisible(this.operacionActiva == AGREGAR);
            this.botonModificar.setVisible(this.operacionActiva == MODIFICAR);
            this.botonEliminar.setVisible(this.operacionActiva == ELIMINAR);

            if (this.cargarDatosComboCamaInterfaz() == true) {
                if (this.operacionActiva == MODIFICAR || this.operacionActiva == ELIMINAR) {
                    devolucion = this.cargarDatosPacienteInterfaz();
                }
            } else {
                devolucion = false;
            }

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean cargarDatosComboCamaInterfaz() {
        boolean devolucion;
        Cama camaAux;
        int indice;
        String cadenaComboAux;
        long camaSeleccionada = 0;
        int indiceSeleccionado = 0;

        try {
            if (this.pacienteActivo != null) {
                camaSeleccionada = this.pacienteActivo.getIdCama();
            }
            listaCamas = Cama.getTodasCamas(this.conexionBD);

            if (listaCamas != null) {
                for (indice = 0; indice < listaCamas.size(); indice++) {
                    camaAux = listaCamas.get(indice);

                    cadenaComboAux = camaAux.getId() + " - " + camaAux.getMarca() + " " + camaAux.getModelo();
                    this.comboCama.addItem(cadenaComboAux);

                    if (camaAux.getId() == camaSeleccionada) {
                        indiceSeleccionado = indice;
                    }
                }
                this.comboCama.setSelectedIndex(indiceSeleccionado);
            }

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean cargarDatosPacienteInterfaz() {
        boolean devolucion;

        try {
            if (this.pacienteActivo != null) {
                devolucion = this.cargarDatosGenerales()
                        && this.cargarListaDiagnosticos()
                        && this.cargarListaVisitasMedicas();
            } else {
                devolucion = false;
            }

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean cargarDatosGenerales() {
        boolean devolucion;
        String FechaAux;

        try {
            this.textDni.setText(this.pacienteActivo.getDni());

            FechaAux = this.pacienteActivo.getFechaNacimiento(null);
            if (FechaAux != null) {
                this.textFechaNacimiento.setText(FechaAux);
            }

            this.textCi.setText(this.pacienteActivo.getCi());
            this.textNumSeguridadSocial.setText(this.pacienteActivo.getNumSeguridadSocial());
            this.textNombre.setText(this.pacienteActivo.getNombre());
            this.textApellido1.setText(this.pacienteActivo.getApellido1());
            this.textApellido2.setText(this.pacienteActivo.getApellido2());

            FechaAux = this.pacienteActivo.getFechaIngreso(null);
            if (FechaAux != null) {
                this.textFechaIngreso.setValue(FechaAux);
            }

            FechaAux = this.pacienteActivo.getFechaAlta(null);
            if (FechaAux != null) {
                this.textFechaAlta.setValue(FechaAux);
            }

            this.textObservaciones.setText(this.pacienteActivo.getObservaciones());

            devolucion = true;

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean recogerDatosInterfaz() {
        boolean devolucion = false;

        try {
            if (this.pacienteActivo == null) {
                this.pacienteActivo = new Paciente(this.conexionBD);
            }

            String fechaNacimiento = null;
            String fechaIngreso = null;
            String fechaAlta = null;
            try {
                fechaNacimiento = LocalDate.parse(this.textFechaNacimiento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
                fechaIngreso = LocalDate.parse(this.textFechaIngreso.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
                fechaAlta = LocalDate.parse(this.textFechaAlta.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
            } catch (Exception ex) {

            }

            if (fechaNacimiento != null && fechaIngreso != null) {
                String ci = this.textCi.getText().equals("") ? null : this.textCi.getText();
                String nombre = this.textNombre.getText().equals("") ? null : this.textNombre.getText();
                String apellido1 = this.textApellido1.getText().equals("") ? null : this.textApellido1.getText();
                String apellido2 = this.textApellido2.getText().equals("") ? null : this.textApellido2.getText();
                String numSegSocial = this.textNumSeguridadSocial.getText().equals("") ? null : this.textNumSeguridadSocial.getText();

                if (ci != null && nombre != null && apellido1 != null
                        && apellido2 != null && numSegSocial != null
                        && this.camaSeleccionada != null) {

                    this.pacienteActivo.setData(this.textDni.getText(),
                            ci,
                            nombre,
                            apellido1,
                            apellido2,
                            fechaNacimiento,
                            numSegSocial,
                            fechaIngreso,
                            fechaAlta,
                            this.camaSeleccionada,
                            this.textObservaciones.getText());
                    devolucion = true;
                }
            } else {
                devolucion = false;
            }
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestión de la Lista de Diagnosticos">
    private boolean cargarListaDiagnosticos() {
        boolean devolucion;
        int VIndice;
        Diagnostico diagnosticoAux;
        String[] Linea = new String[3];

        DefaultTableModel modeloTabla;
        ArrayList<Diagnostico> listaDiagnosticos;

        try {

            listaDiagnosticos = this.pacienteActivo.getDiagnosticos();

            if (listaDiagnosticos != null) {

                modeloTabla = this.configurarListaDiagnosticos();

                for (VIndice = 0; VIndice < listaDiagnosticos.size(); VIndice++) {
                    diagnosticoAux = listaDiagnosticos.get(VIndice);

                    Linea[0] = diagnosticoAux.getCodigo();
                    Linea[1] = diagnosticoAux.getFecha().toString();
                    Linea[2] = diagnosticoAux.getMedico().getNombreFormalCompleto();

                    modeloTabla.addRow(Linea);
                }

                this.tablaDiagnosticos.setModel(modeloTabla);

                devolucion = true;
            } else {
                devolucion = false;
            }
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private DefaultTableModel configurarListaDiagnosticos() {

        DefaultTableModel devolucion;

        try {
            devolucion = new DefaultTableModel();

            devolucion.addColumn("Codigo");
            devolucion.addColumn("Fecha");
            devolucion.addColumn("Médico");

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;

    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestión de la Lista de Visitas Médicas">
    private boolean cargarListaVisitasMedicas() {
        boolean devolucion;
        int VIndice;
        VisitaMedica visitaAux;
        String[] Linea = new String[2];

        DefaultTableModel modeloTabla;

        try {

            this.visitasMedicas = this.pacienteActivo.getVisitasMedicas();

            modeloTabla = this.configurarListaVisitasMedicas();

            for (VIndice = 0; VIndice < this.visitasMedicas.size(); VIndice++) {
                visitaAux = this.visitasMedicas.get(VIndice);

                Linea[0] = visitaAux.getFecha().toString();
                Linea[1] = visitaAux.getMedico().getNombreFormalCompleto();

                modeloTabla.addRow(Linea);
            }

            this.tablaVisitasMedicas.setModel(modeloTabla);

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private DefaultTableModel configurarListaVisitasMedicas() {

        DefaultTableModel devolucion;

        try {
            devolucion = new DefaultTableModel();

            devolucion.addColumn("Fecha");
            devolucion.addColumn("Médico");

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;

    }

    // </editor-fold>
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        botonAgregar = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        panelFichas = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        textDni = new javax.swing.JTextField();
        textCi = new javax.swing.JTextField();
        textNumSeguridadSocial = new javax.swing.JTextField();
        textNombre = new javax.swing.JTextField();
        textApellido1 = new javax.swing.JTextField();
        textApellido2 = new javax.swing.JTextField();
        textFechaNacimiento = new javax.swing.JFormattedTextField();
        textFechaIngreso = new javax.swing.JFormattedTextField();
        textFechaAlta = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textObservaciones = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        comboCama = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDiagnosticos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaVisitasMedicas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jToolBar1.setRollover(true);

        botonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hospital/images/Anhadir.png"))); // NOI18N
        botonAgregar.setBorder(null);
        botonAgregar.setBorderPainted(false);
        botonAgregar.setFocusable(false);
        botonAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAgregar.setMargin(new java.awt.Insets(2, 40, 2, 40));
        botonAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAgregarMouseClicked(evt);
            }
        });
        jToolBar1.add(botonAgregar);

        botonModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hospital/images/Aceptar.png"))); // NOI18N
        botonModificar.setBorder(null);
        botonModificar.setBorderPainted(false);
        botonModificar.setFocusable(false);
        botonModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonModificarMouseClicked(evt);
            }
        });
        jToolBar1.add(botonModificar);

        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hospital/images/Cancelar.png"))); // NOI18N
        botonEliminar.setBorder(null);
        botonEliminar.setBorderPainted(false);
        botonEliminar.setFocusable(false);
        botonEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEliminarMouseClicked(evt);
            }
        });
        jToolBar1.add(botonEliminar);

        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hospital/images/Salir.png"))); // NOI18N
        botonSalir.setBorder(null);
        botonSalir.setBorderPainted(false);
        botonSalir.setFocusable(false);
        botonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonSalirMouseClicked(evt);
            }
        });
        jToolBar1.add(botonSalir);

        try {
            textFechaNacimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            textFechaIngreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            textFechaAlta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        textObservaciones.setColumns(20);
        textObservaciones.setRows(5);
        jScrollPane1.setViewportView(textObservaciones);

        jLabel1.setText("Dni:");

        jLabel2.setText("Fecha Nacimiento:");

        jLabel3.setText("CI:");

        jLabel4.setText("Nº Seg. Social:");

        jLabel5.setText("Nombre");

        jLabel6.setText("1º Apellido:");

        jLabel7.setText("2º Apellido:");

        jLabel8.setText("Fecha Ingreso:");

        jLabel9.setText("Fecha Alta:");

        jLabel10.setText("Observaciones:");

        jLabel11.setText("Cama:");

        comboCama.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCamaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textDni, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(2, 2, 2)
                                .addComponent(textCi, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textNumSeguridadSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboCama, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textCi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNumSeguridadSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboCama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        panelFichas.addTab("Datos Generales", jPanel2);

        tablaDiagnosticos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Fecha", "Medico"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaDiagnosticos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFichas.addTab("Diagnósticos", jPanel3);

        tablaVisitasMedicas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Fecha", "Médico"
            }
        ));
        tablaVisitasMedicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaVisitasMedicasMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablaVisitasMedicas);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFichas.addTab("Visitas Médicas", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFichas))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFichas, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Eventos del Formulario">

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked
        // TODO add your handling code here:
        if (this.recogerDatosInterfaz() == true) {
            if (this.pacienteActivo.agregar()) {
                FormAvisoUsuario formularioAviso;
                formularioAviso = new FormAvisoUsuario(
                        FormAvisoUsuario.OPERACION_EXITOSA,
                        this,
                        true);
                formularioAviso.setVisible(true);

                if (formularioAviso.esOperacionAceptada()) {
                    this.dispose();
                }
            }
        } else {
            FormAvisoUsuario formularioAviso;
            formularioAviso = new FormAvisoUsuario(
                    FormAvisoUsuario.OPERACION_CON_DATOS_INCORRECTOS,
                    this,
                    true);
            formularioAviso.setVisible(true);
        }
    }//GEN-LAST:event_botonAgregarMouseClicked

    private void botonModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonModificarMouseClicked
        // TODO add your handling code here:
        if (this.recogerDatosInterfaz() == true) {
            if (this.pacienteActivo.modificar()) {
                FormAvisoUsuario formularioAviso;
                formularioAviso = new FormAvisoUsuario(
                        FormAvisoUsuario.OPERACION_EXITOSA,
                        this,
                        true);
                formularioAviso.setVisible(true);

                if (formularioAviso.esOperacionAceptada()) {
                    this.dispose();
                }
            }
        } else {
            FormAvisoUsuario formularioAviso;
            formularioAviso = new FormAvisoUsuario(
                    FormAvisoUsuario.OPERACION_CON_DATOS_INCORRECTOS,
                    this,
                    true);
            formularioAviso.setVisible(true);
        }
    }//GEN-LAST:event_botonModificarMouseClicked

    private void botonEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarMouseClicked
        // TODO add your handling code here:
        FormAvisoUsuario formularioAviso;
        formularioAviso = new FormAvisoUsuario(
                FormAvisoUsuario.OPERACION_NO_REVERSIBLE,
                this,
                true);
        formularioAviso.setVisible(true);

        if (formularioAviso.esOperacionAceptada()) {
            this.pacienteActivo.eliminar();
            this.dispose();
            formularioAviso = new FormAvisoUsuario(
                    FormAvisoUsuario.OPERACION_EXITOSA,
                    this,
                    true);
            formularioAviso.setVisible(true);
        }
    }//GEN-LAST:event_botonEliminarMouseClicked

    private void comboCamaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCamaItemStateChanged
        // TODO add your handling code here:

        this.camaSeleccionada = this.listaCamas.get(this.comboCama.getSelectedIndex());
    }//GEN-LAST:event_comboCamaItemStateChanged

    private void tablaVisitasMedicasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVisitasMedicasMousePressed
        // TODO add your handling code here:
        FormMantMedicos formularioMedico;

        if (this.visitasMedicas != null) {
            this.visitaSeleccionada = this.visitasMedicas.get(this.tablaVisitasMedicas.getSelectedRow());

            if (this.visitaSeleccionada.getMedico() != null) {

                formularioMedico = new FormMantMedicos(this.visitaSeleccionada.getMedico(),
                        FormMantPacientes.MODIFICAR,
                        this.conexionBD, this, true);

                formularioMedico.setVisible(true);

            } else {
                FormAvisoUsuario formularioAviso;

                formularioAviso = new FormAvisoUsuario(
                        FormAvisoUsuario.INFO_INEXISTENTE,
                        this,
                        true);
                formularioAviso.setVisible(true);

            }
        }
    }//GEN-LAST:event_tablaVisitasMedicasMousePressed

    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JComboBox<String> comboCama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTabbedPane panelFichas;
    private javax.swing.JTable tablaDiagnosticos;
    private javax.swing.JTable tablaVisitasMedicas;
    private javax.swing.JTextField textApellido1;
    private javax.swing.JTextField textApellido2;
    private javax.swing.JTextField textCi;
    private javax.swing.JTextField textDni;
    private javax.swing.JFormattedTextField textFechaAlta;
    private javax.swing.JFormattedTextField textFechaIngreso;
    private javax.swing.JFormattedTextField textFechaNacimiento;
    private javax.swing.JTextField textNombre;
    private javax.swing.JTextField textNumSeguridadSocial;
    private javax.swing.JTextArea textObservaciones;
    // End of variables declaration//GEN-END:variables
}
