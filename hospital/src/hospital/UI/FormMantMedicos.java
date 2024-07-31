package hospital.UI;

import hospital.kernel.Diagnostico;
import hospital.kernel.Medico;
import hospital.kernel.Paciente;
import hospital.kernel.VisitaMedica;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andres
 */
public class FormMantMedicos extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
        public static final int AGREGAR = 0;
        public static final int MODIFICAR = 1;
        public static final int ELIMINAR = 2;

        private final Connection conexionBD;

        private Medico medicoActivo = null;

        private int operacionActiva = AGREGAR;

        private ArrayList<Diagnostico> diagnosticos;

        private ArrayList<VisitaMedica> visitasMedicas;
        private VisitaMedica visitaSeleccionada = null;

        private FormularioListener listener;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
        public FormMantMedicos(Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Médico");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.conexionBD = conexionBD;

            this.desabilitarPestanhasEnModoAgregar();
            this.prepararFormulario();

        }

        public FormMantMedicos(Medico medico, int operacion, Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Médico");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.conexionBD = conexionBD;
            this.medicoActivo = medico;
            this.operacionActiva = operacion;

            this.desabilitarPestanhasEnModoAgregar();
            this.prepararFormulario();

        }

        public FormMantMedicos(FormularioListener listener, Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Médico");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.listener = listener;
            this.conexionBD = conexionBD;

            this.desabilitarPestanhasEnModoAgregar();
            this.prepararFormulario();

        }

        public FormMantMedicos(FormularioListener listener, Medico medico, int operacion, Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Médico");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.listener = listener;
            this.conexionBD = conexionBD;
            this.medicoActivo = medico;
            this.operacionActiva = operacion;

            this.desabilitarPestanhasEnModoAgregar();
            this.prepararFormulario();

        }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestión del Formulario">
        private void desabilitarPestanhasEnModoAgregar() {

            if (operacionActiva == FormMantMedicos.AGREGAR) {

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
                if (this.medicoActivo != null) {
                    devolucion = this.cargarDatosGeneralesInterfaz();
                    devolucion = this.cargarListaDiagnosticosInterfaz() && devolucion;
                    devolucion = this.cargarListaVisitasMedicasInterfaz() && devolucion;
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

            try {

                this.textNumColegiado.setText(Long.toString(this.medicoActivo.getNumColegiado()));
                this.textNombre.setText(this.medicoActivo.getNombre());
                this.textApellido1.setText(this.medicoActivo.getApellido1());
                this.textApellido2.setText(this.medicoActivo.getApellido2());
                this.textObservaciones.setText(this.medicoActivo.getObservaciones());

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private boolean recogerDatosInterfaz() {
            boolean devolucion;

            try {
                if (this.medicoActivo == null) {
                    this.medicoActivo = new Medico(this.conexionBD);
                }

                String nombre = this.textNombre.getText();
                String apellido1 = this.textApellido1.getText();
                String apellido2 = this.textApellido2.getText();

                // Verifica si los campos están vacíos
                if (nombre == null || nombre.trim().isEmpty()) {
                    throw new Exception("El campo nombre es obligatorio.");
                }
                if (apellido1 == null || apellido1.trim().isEmpty()) {
                    throw new Exception("El campo apellido1 es obligatorio.");
                }
                if (apellido2 == null || apellido2.trim().isEmpty()) {
                    throw new Exception("El campo apellido2 es obligatorio.");
                }

                this.medicoActivo.setData(Long.parseLong(this.textNumColegiado.getText()),
                        this.textNombre.getText(),
                        this.textApellido1.getText(),
                        this.textApellido2.getText(),
                        this.textObservaciones.getText());

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        @Override
        public void dispose() {
            super.dispose();
            if (listener != null) {
                listener.cuandoCierraFormulario();
            }
        }

    // <editor-fold defaultstate="collapsed" desc="Carga de datos de los Diagnósticos">
        public boolean cargarListaDiagnosticosInterfaz() {
            boolean devolucion;

            try {
                this.diagnosticos = this.medicoActivo.getDiagnosticos();

                this.visualizarListaDiagnosticos();

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private boolean visualizarListaDiagnosticos() {
            boolean devolucion;
            int indice;
            Diagnostico diagnosticoAux;
            Paciente pacienteAux;
            String[] linea = new String[3];

            DefaultTableModel modeloTabla;

            try {
                modeloTabla = this.configurarListaDiagnosticos();

                for (indice = 0; indice < this.diagnosticos.size(); indice++) {
                    diagnosticoAux = this.diagnosticos.get(indice);

                    linea[0] = diagnosticoAux.getCodigo();
                    linea[1] = diagnosticoAux.getFecha().toString();

                    pacienteAux = diagnosticoAux.getPaciente();
                    if (pacienteAux != null) {
                        linea[2] = pacienteAux.getNombreFormalCompleto();
                    } else {
                        linea[2] = "";
                    }

                    modeloTabla.addRow(linea);
                }

                this.tablaDiagnosticos.setModel(modeloTabla);

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private DefaultTableModel configurarListaDiagnosticos() {

            DefaultTableModel devolucion;

            try {
                devolucion = new DefaultTableModel();

                devolucion.addColumn("Código");
                devolucion.addColumn("Fecha");
                devolucion.addColumn("Paciente");

            } catch (Exception ex) {
                devolucion = null;
            }

            return devolucion;

        }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Carga de datos de los Visitas">
        public boolean cargarListaVisitasMedicasInterfaz() {
            boolean devolucion;

            try {
                this.visitasMedicas = this.medicoActivo.getVisitasMedicas();
                this.visualizarListaVisitasMedicas();

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private boolean visualizarListaVisitasMedicas() {
            boolean devolucion;
            int indice;
            VisitaMedica visitaMedicaAux;
            Paciente pacienteAux;
            String[] linea = new String[2];

            DefaultTableModel modeloTabla;

            try {
                modeloTabla = this.configurarListaVisitasMedicas();

                for (indice = 0; indice < this.visitasMedicas.size(); indice++) {
                    visitaMedicaAux = this.visitasMedicas.get(indice);

                    linea[0] = visitaMedicaAux.getFecha().toString();

                    pacienteAux = visitaMedicaAux.getPaciente();
                    if (pacienteAux != null) {
                        linea[1] = pacienteAux.getNombreFormalCompleto();
                    } else {
                        linea[1] = "";
                    }

                    modeloTabla.addRow(linea);
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
                devolucion.addColumn("Paciente");

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
        textNombre = new javax.swing.JTextField();
        textApellido1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textApellido2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textNumColegiado = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textObservaciones = new javax.swing.JTextArea();
        fichaDiagnosticos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDiagnosticos = new javax.swing.JTable();
        fichaVisitasMedicas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaVisitasMedicas = new javax.swing.JTable();

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

        jLabel1.setText("Nombre:");

        jLabel2.setText("1º Apellido:");

        jLabel3.setText("2º Apellido:");

        jLabel4.setText("Nº Colegiado:");

        textNumColegiado.setForeground(new java.awt.Color(255, 0, 0));

        jLabel5.setText("Observaciones:");

        textObservaciones.setColumns(20);
        textObservaciones.setRows(5);
        jScrollPane1.setViewportView(textObservaciones);

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
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textNumColegiado, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)))
                    .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        fichaDatosGeneralesLayout.setVerticalGroup(
            fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaDatosGeneralesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textNumColegiado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(fichaDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        panelFichas.addTab("Datos Generales", fichaDatosGenerales);

        tablaDiagnosticos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Código", "Fecha", "Paciente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaDiagnosticos);

        javax.swing.GroupLayout fichaDiagnosticosLayout = new javax.swing.GroupLayout(fichaDiagnosticos);
        fichaDiagnosticos.setLayout(fichaDiagnosticosLayout);
        fichaDiagnosticosLayout.setHorizontalGroup(
            fichaDiagnosticosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaDiagnosticosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
                .addContainerGap())
        );
        fichaDiagnosticosLayout.setVerticalGroup(
            fichaDiagnosticosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaDiagnosticosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        panelFichas.addTab("Diagnósticos", fichaDiagnosticos);

        tablaVisitasMedicas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Fecha", "Paciente"
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
        tablaVisitasMedicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaVisitasMedicasMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablaVisitasMedicas);

        javax.swing.GroupLayout fichaVisitasMedicasLayout = new javax.swing.GroupLayout(fichaVisitasMedicas);
        fichaVisitasMedicas.setLayout(fichaVisitasMedicasLayout);
        fichaVisitasMedicasLayout.setHorizontalGroup(
            fichaVisitasMedicasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaVisitasMedicasLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                .addContainerGap())
        );
        fichaVisitasMedicasLayout.setVerticalGroup(
            fichaVisitasMedicasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fichaVisitasMedicasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFichas.addTab("Visitas Médicas", fichaVisitasMedicas);

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

    private void tablaVisitasMedicasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVisitasMedicasMousePressed
        // TODO add your handling code here:
        FormMantPacientes formularioPaciente;

        if (this.visitasMedicas != null) {
            this.visitaSeleccionada = this.visitasMedicas.get(this.tablaVisitasMedicas.getSelectedRow());

            if (this.visitaSeleccionada.getPaciente() != null) {
                formularioPaciente = new FormMantPacientes(this.visitaSeleccionada.getPaciente(),
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

    }//GEN-LAST:event_tablaVisitasMedicasMousePressed

    private void botonEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarMouseClicked
        // TODO add your handling code here:
        FormAvisoUsuario formularioAviso;
        formularioAviso = new FormAvisoUsuario(
                FormAvisoUsuario.OPERACION_NO_REVERSIBLE,
                this,
                true);
        formularioAviso.setVisible(true);

        if (formularioAviso.esOperacionAceptada()) {
            this.medicoActivo.eliminar();
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
            if (this.medicoActivo.agregar()) {
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
            if (this.medicoActivo.modificar()) {
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

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JPanel fichaDatosGenerales;
    private javax.swing.JPanel fichaDiagnosticos;
    private javax.swing.JPanel fichaVisitasMedicas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTabbedPane panelFichas;
    private javax.swing.JTable tablaDiagnosticos;
    private javax.swing.JTable tablaVisitasMedicas;
    private javax.swing.JTextField textApellido1;
    private javax.swing.JTextField textApellido2;
    private javax.swing.JTextField textNombre;
    private javax.swing.JTextField textNumColegiado;
    private javax.swing.JTextArea textObservaciones;
    // End of variables declaration//GEN-END:variables
}
