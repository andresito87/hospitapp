package hospital.UI;

import hospital.kernel.Diagnostico;
import hospital.kernel.Medico;
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
public class FormMantVisitasMedicas extends javax.swing.JDialog {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    public static final int AGREGAR = 0;
    public static final int MODIFICAR = 1;
    public static final int ELIMINAR = 2;

    private final Connection conexionBD;

    private VisitaMedica visitaMedicaActiva = null;

    private int operacionActiva = AGREGAR;

    private ArrayList<Medico> medicos;
    private ArrayList<Paciente> pacientes;

    private Medico medicoSeleccionado = null;
    private Paciente pacienteSeleccionado = null;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public FormMantVisitasMedicas(Connection conexionBD,
            javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Información de Diagnóstico");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;

        this.desabilitarPestanhasEnModoAgregar();
        this.prepararFormulario();

    }

    public FormMantVisitasMedicas(VisitaMedica visitaMedica, int operacion, Connection conexionBD,
            javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Información de Diagnóstico");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;
        this.visitaMedicaActiva = visitaMedica;
        this.operacionActiva = operacion;

        this.desabilitarPestanhasEnModoAgregar();
        this.prepararFormulario();

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Gestión del Formulario">
    private void desabilitarPestanhasEnModoAgregar() {

        if (operacionActiva == FormMantVisitasMedicas.AGREGAR) {

            panelFichas.setEnabledAt(1, false);
            panelFichas.setEnabledAt(2, false);
        }
    }

    private boolean prepararFormulario() {
        boolean devolucion;

        try {
            this.botonAgregar.setVisible(this.operacionActiva == AGREGAR);
            this.botonModificar.setVisible(this.operacionActiva == MODIFICAR);
            this.botonEliminar.setVisible(this.operacionActiva == ELIMINAR);

            if (this.operacionActiva == MODIFICAR || this.operacionActiva == ELIMINAR) {
                devolucion = this.cargarDatosInterfaz();
            } else {
                devolucion = true;
            }

        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean cargarDatosInterfaz() {
        boolean devolucion;

        try {
            if (this.visitaMedicaActiva != null) {
                devolucion = this.cargarDatosGeneralesInterfaz();
                devolucion = this.cargarListaMedicosInterfaz() && devolucion;
                devolucion = this.cargarListaPacientesInterfaz() && devolucion;
            } else {
                devolucion = false;
            }
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    public boolean cargarDatosGeneralesInterfaz() {
        boolean devolucion;
        String fecha = null;
        try {
            fecha = LocalDate.parse(this.visitaMedicaActiva.getFecha().toString())
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            this.textFecha.setText(fecha);
            this.textIdMedico.setText(Long.toString(this.visitaMedicaActiva.getIdMedico()));
            this.textIdPaciente.setText(Long.toString(this.visitaMedicaActiva.getIdPaciente()));
            this.textObservaciones.setText(this.visitaMedicaActiva.getObservaciones());

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean recogerDatosInterfaz() {
        boolean devolucion;

        try {
            if (this.visitaMedicaActiva == null) {
                this.visitaMedicaActiva = new VisitaMedica(this.conexionBD);
            }

            this.visitaMedicaActiva.setData(
                    Long.parseLong(this.textIdMedico.getText()),
                    Long.parseLong(this.textIdPaciente.getText()),
                    LocalDate.parse(this.textFecha.getText()),
                    this.textObservaciones.getText()
            );

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

// <editor-fold defaultstate="collapsed" desc="Carga de datos de los Médicos">
    public boolean cargarListaMedicosInterfaz() {
        boolean devolucion;

        try {
            this.medicos = Medico.getMedicosConVisMed(this.visitaMedicaActiva.getId(), conexionBD);

            this.visualizarListaMedicos();

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean visualizarListaMedicos() {
        boolean devolucion;
        int indice;
        Medico medicoAux;
        String[] linea = new String[2];

        DefaultTableModel modeloTabla;

        try {
            modeloTabla = this.configurarListaMedicos();

            for (indice = 0; indice < this.medicos.size(); indice++) {
                medicoAux = this.medicos.get(indice);

                linea[0] = Long.toString(medicoAux.getNumColegiado());
                linea[1] = medicoAux.getNombreFormalCompleto();

                modeloTabla.addRow(linea);
            }

            this.tablaMedicos.setModel(modeloTabla);

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private DefaultTableModel configurarListaMedicos() {

        DefaultTableModel devolucion;

        try {
            devolucion = new DefaultTableModel();

            devolucion.addColumn("Número Colegiado");
            devolucion.addColumn("Nombre Completo");

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Carga de datos de los Pacientes">
    public boolean cargarListaPacientesInterfaz() {
        boolean devolucion;

        try {
            this.pacientes = Paciente.getPacientesConVisMed(this.visitaMedicaActiva.getId(), conexionBD);
            this.visualizarListaPacientes();

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean visualizarListaPacientes() {
        boolean devolucion;
        int indice;
        Paciente pacienteAux;
        String[] linea = new String[3];

        DefaultTableModel modeloTabla;

        try {
            modeloTabla = this.configurarListaPacientes();

            for (indice = 0; indice < this.pacientes.size(); indice++) {
                pacienteAux = this.pacientes.get(indice);

                linea[0] = pacienteAux.getDni();
                linea[1] = pacienteAux.getNombreFormalCompleto();

                modeloTabla.addRow(linea);
            }
            this.tablaPacientes.setModel(modeloTabla);

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private DefaultTableModel configurarListaPacientes() {

        DefaultTableModel devolucion;

        try {
            devolucion = new DefaultTableModel();

            devolucion.addColumn("DNI");
            devolucion.addColumn("Nombre Completo");

        } catch (Exception ex) {
            devolucion = null;
        }

        return devolucion;

    }

// </editor-fold>
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
        fichaDatosGenerales = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textIdMedico = new javax.swing.JTextField();
        textIdPaciente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textObservaciones = new javax.swing.JTextArea();
        textFecha = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        fichaMedicos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMedicos = new javax.swing.JTable();
        fichaPacientes = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPacientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jToolBar1.setRollover(true);

        botonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hospital/images/Anhadir.png"))); // NOI18N
        botonAgregar.setBorder(null);
        botonAgregar.setBorderPainted(false);
        botonAgregar.setFocusable(false);
        botonAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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

        jLabel1.setText("ID Médico:");

        jLabel2.setText("ID Paciente:");

        jLabel5.setText("Observaciones:");

        textObservaciones.setColumns(20);
        textObservaciones.setRows(5);
        jScrollPane1.setViewportView(textObservaciones);

        try {
            textFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel8.setText("Fecha:");

        javax.swing.GroupLayout fichaDatosGeneralesLayout = new javax.swing.GroupLayout(fichaDatosGenerales);
        fichaDatosGenerales.setLayout(fichaDatosGeneralesLayout);
        fichaDatosGeneralesLayout.setHorizontalGroup(
            fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textIdMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5)))
                    .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        fichaDatosGeneralesLayout.setVerticalGroup(
            fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textIdMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        panelFichas.addTab("Datos Generales", fichaDatosGenerales);

        tablaMedicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Número Colegiado", "Nombre Completo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMedicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaMedicosMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaMedicos);

        javax.swing.GroupLayout fichaMedicosLayout = new javax.swing.GroupLayout(fichaMedicos);
        fichaMedicos.setLayout(fichaMedicosLayout);
        fichaMedicosLayout.setHorizontalGroup(
            fichaMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaMedicosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
                .addContainerGap())
        );
        fichaMedicosLayout.setVerticalGroup(
            fichaMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaMedicosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        panelFichas.addTab("Médicos", fichaMedicos);

        tablaPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "DNI", "Nombre Completo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaPacientesMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablaPacientes);

        javax.swing.GroupLayout fichaPacientesLayout = new javax.swing.GroupLayout(fichaPacientes);
        fichaPacientes.setLayout(fichaPacientesLayout);
        fichaPacientesLayout.setHorizontalGroup(
            fichaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaPacientesLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                .addContainerGap())
        );
        fichaPacientesLayout.setVerticalGroup(
            fichaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFichas.addTab("Pacientes", fichaPacientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelFichas)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFichas))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

// <editor-fold defaultstate="collapsed" desc="Eventos del Formulario">

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked
        // TODO add your handling code here:

        this.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

    private void tablaPacientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPacientesMousePressed
        // TODO add your handling code here:
        FormMantPacientes formularioPaciente;

        if (this.pacientes != null) {
            this.pacienteSeleccionado = this.pacientes.get(this.tablaPacientes.getSelectedRow());

            if (Paciente.getPaciente(this.pacienteSeleccionado.getId(), conexionBD) != null) {
                formularioPaciente = new FormMantPacientes(Paciente.getPaciente(this.pacienteSeleccionado.getId(), conexionBD),
                        FormMantPacientes.MODIFICAR,
                        this.conexionBD, this, true);
                formularioPaciente.setVisible(true);
            } else {
                FormAvisoUsuario formularioAviso;
                formularioAviso = new FormAvisoUsuario(
                        FormAvisoUsuario.INFO_INEXISTENTE,
                        this,
                        true);
                formularioAviso.setVisible(true);

            }
        }

    }//GEN-LAST:event_tablaPacientesMousePressed

    private void botonEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarMouseClicked
        // TODO add your handling code here:
        FormAvisoUsuario formularioAviso;
        formularioAviso = new FormAvisoUsuario(
                FormAvisoUsuario.OPERACION_NO_REVERSIBLE,
                this,
                true);
        formularioAviso.setVisible(true);

        if (formularioAviso.esOperacionAceptada()) {
            this.visitaMedicaActiva.eliminar();
            this.dispose();
            formularioAviso = new FormAvisoUsuario(
                    FormAvisoUsuario.OPERACION_EXITOSA,
                    this,
                    true);
            formularioAviso.setVisible(true);
        }
    }//GEN-LAST:event_botonEliminarMouseClicked

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked
        // TODO add your handling code here:
        if (this.recogerDatosInterfaz() == true) {
            this.visitaMedicaActiva.agregar();
        }
    }//GEN-LAST:event_botonAgregarMouseClicked

    private void botonModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonModificarMouseClicked
        // TODO add your handling code here:
        if (this.recogerDatosInterfaz() == true) {
            this.visitaMedicaActiva.modificar();
        }
    }//GEN-LAST:event_botonModificarMouseClicked

    private void tablaMedicosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMedicosMousePressed
        // TODO add your handling code here:
        FormMantMedicos formularioMedico;

        if (this.medicos != null) {
            this.medicoSeleccionado = this.medicos.get(this.tablaMedicos.getSelectedRow());

            if (Medico.getMedico(this.medicoSeleccionado.getId(), conexionBD) != null) {
                formularioMedico = new FormMantMedicos(Medico.getMedico(this.medicoSeleccionado.getId(), conexionBD),
                        FormMantMedicos.MODIFICAR,
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
    }//GEN-LAST:event_tablaMedicosMousePressed

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JPanel fichaDatosGenerales;
    private javax.swing.JPanel fichaMedicos;
    private javax.swing.JPanel fichaPacientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTabbedPane panelFichas;
    private javax.swing.JTable tablaMedicos;
    private javax.swing.JTable tablaPacientes;
    private javax.swing.JFormattedTextField textFecha;
    private javax.swing.JTextField textIdMedico;
    private javax.swing.JTextField textIdPaciente;
    private javax.swing.JTextArea textObservaciones;
    // End of variables declaration//GEN-END:variables
}
