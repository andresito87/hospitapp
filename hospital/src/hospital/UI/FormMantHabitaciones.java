package hospital.UI;

import hospital.kernel.FormularioListener;
import hospital.kernel.Habitacion;
import hospital.kernel.Paciente;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andres
 */
public class FormMantHabitaciones extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
        public static final int AGREGAR = 0;
        public static final int MODIFICAR = 1;
        public static final int ELIMINAR = 2;

        private final Connection conexionBD;

        private Habitacion habitacionActiva = null;

        private int operacionActiva = AGREGAR;

        private ArrayList<Paciente> pacientes;
        private ArrayList<Habitacion> habitaciones;
        private Paciente pacienteSeleccionado = null;

        private final FormularioListener listener;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
        public FormMantHabitaciones(FormularioListener listener, Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Habitación");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.listener = listener;
            this.conexionBD = conexionBD;

            this.desabilitarPestanhasEnModoAgregar();
            this.prepararFormulario();

        }

        public FormMantHabitaciones(FormularioListener listener, Habitacion habitacion, int operacion, Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Habitación");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.listener = listener;
            this.conexionBD = conexionBD;
            this.habitacionActiva = habitacion;
            this.operacionActiva = operacion;

            this.desabilitarPestanhasEnModoAgregar();
            this.prepararFormulario();

        }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestión del Formulario">
        private void desabilitarPestanhasEnModoAgregar() {

            if (operacionActiva == FormMantHabitaciones.AGREGAR) {

                panelFichas.setEnabledAt(1, false);
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
                if (this.habitacionActiva != null) {
                    devolucion = this.cargarDatosGeneralesInterfaz();
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

            try {
                this.textNumHabitacion.setText(Integer.toString(this.habitacionActiva.getNumHabitacion()));
                this.textPlanta.setText(Integer.toString(this.habitacionActiva.getNumPlanta()));
                this.textPlazas.setText(Integer.toString(this.habitacionActiva.getNumPlazas()));
                this.textObservaciones.setText(this.habitacionActiva.getObservaciones());

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private boolean recogerDatosInterfaz() {
            boolean devolucion;

            try {
                if (this.habitacionActiva == null) {
                    this.habitacionActiva = new Habitacion(this.conexionBD);
                }

                this.habitacionActiva.setData(
                        Integer.parseInt(this.textNumHabitacion.getText()),
                        Integer.parseInt(this.textPlanta.getText()),
                        Integer.parseInt(this.textPlazas.getText()),
                        this.textObservaciones.getText()
                );

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

    // <editor-fold defaultstate="collapsed" desc="Carga de datos de los Pacientes">
        public boolean cargarListaPacientesInterfaz() {
            boolean devolucion;

            try {
                this.pacientes = Habitacion.getTodosPacientes(this.habitacionActiva.getId(), conexionBD);

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

                    linea[0] = pacienteAux.getNombre();
                    linea[1] = pacienteAux.getApellido1();
                    linea[2] = pacienteAux.getApellido2();

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

                devolucion.addColumn("Nombre");
                devolucion.addColumn("Primer Apellido");
                devolucion.addColumn("Segundo Apellido");

            } catch (Exception ex) {
                devolucion = null;
            }

            return devolucion;

        }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Carga de datos de las habitaciones">
        public boolean cargarListaHabitacionesInterfaz() {
            boolean devolucion;

            try {
                this.habitaciones = Paciente.getHabitaciones(pacientes);

                this.visualizarListaHabitaciones();

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private boolean visualizarListaHabitaciones() {
            boolean devolucion;
            int indice;
            Habitacion habitacionAux;
            String[] linea = new String[2];

            DefaultTableModel modeloTabla;

            try {
                modeloTabla = this.configurarListaHabitaciones();

                for (indice = 0; indice < this.habitaciones.size(); indice++) {
                    habitacionAux = this.habitaciones.get(indice);

                    linea[0] = String.valueOf(habitacionAux.getNumHabitacion());
                    linea[1] = String.valueOf(habitacionAux.getNumPlanta());
                    modeloTabla.addRow(linea);
                }
                this.tablaPacientes.setModel(modeloTabla);

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private DefaultTableModel configurarListaHabitaciones() {

            DefaultTableModel devolucion;

            try {
                devolucion = new DefaultTableModel();

                devolucion.addColumn("Número");
                devolucion.addColumn("Planta");

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
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        textNumHabitacion = new javax.swing.JTextField();
        textPlanta = new javax.swing.JTextField();
        textPlazas = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textObservaciones = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPacientes = new javax.swing.JTable();

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

        textObservaciones.setColumns(20);
        textObservaciones.setRows(5);
        jScrollPane1.setViewportView(textObservaciones);

        jLabel1.setText("Número:");

        jLabel3.setText("Planta:");

        jLabel5.setText("Plazas:");

        jLabel10.setText("Observaciones:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textNumHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 86, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textNumHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
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

        tablaPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Primer Apellido", "Segundo Apellido"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFichas.addTab("Pacientes", jPanel4);

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

    private void tablaHabitacionesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaHabitacionesMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_tablaHabitacionesMousePressed

    private void botonEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarMouseClicked
        // TODO add your handling code here:
        FormAvisoUsuario formularioAviso;
        formularioAviso = new FormAvisoUsuario(
                FormAvisoUsuario.OPERACION_NO_REVERSIBLE,
                this,
                true);
        formularioAviso.setVisible(true);

        if (formularioAviso.esOperacionAceptada()) {
            this.habitacionActiva.eliminar();
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
            if (this.habitacionActiva.agregar()) {
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
            if (this.habitacionActiva.modificar()) {
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

    private void tablaPacientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPacientesMousePressed
        // TODO add your handling code here:
        FormMantPacientes formularioPaciente;

        if (this.pacientes != null) {
            this.pacienteSeleccionado = this.pacientes.get(this.tablaPacientes.getSelectedRow());

            if (Paciente.getPaciente(pacienteSeleccionado.getId(), conexionBD) != null) {
                formularioPaciente = new FormMantPacientes(Paciente.getPaciente(pacienteSeleccionado.getId(), conexionBD),
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

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTabbedPane panelFichas;
    private javax.swing.JTable tablaPacientes;
    private javax.swing.JTextField textNumHabitacion;
    private javax.swing.JTextArea textObservaciones;
    private javax.swing.JTextField textPlanta;
    private javax.swing.JTextField textPlazas;
    // End of variables declaration//GEN-END:variables
}
