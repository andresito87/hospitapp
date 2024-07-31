package hospital.UI;

import hospital.kernel.FormularioListener;
import hospital.kernel.Cama;
import hospital.kernel.Habitacion;
import hospital.kernel.Paciente;
import hospital.kernel.TipoCama;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andres
 */
public class FormMantCamas extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
        public static final int AGREGAR = 0;
        public static final int MODIFICAR = 1;
        public static final int ELIMINAR = 2;

        private final Connection conexionBD;

        private Cama camaActiva = null;

        private int operacionActiva = AGREGAR;

        private ArrayList<Paciente> pacientes;
        private ArrayList<Habitacion> habitaciones;
        private Paciente pacienteSeleccionado;

        private final FormularioListener listener;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
        public FormMantCamas(FormularioListener listener, Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Cama");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.listener = listener;
            this.conexionBD = conexionBD;

            this.desabilitarPestanhasEnModoAgregar();
            this.prepararFormulario();

        }

        public FormMantCamas(FormularioListener listener, Cama cama, int operacion, Connection conexionBD,
                javax.swing.JDialog parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Información de Cama");
            this.setLocation(300, 50);
            this.setSize(1000, 750);

            this.listener = listener;
            this.conexionBD = conexionBD;
            this.camaActiva = cama;
            this.operacionActiva = operacion;

            this.desabilitarPestanhasEnModoAgregar();
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
            boolean devolucion;

            try {
                this.botonAgregar.setVisible(this.operacionActiva == AGREGAR);
                this.botonModificar.setVisible(this.operacionActiva == MODIFICAR);
                this.botonEliminar.setVisible(this.operacionActiva == ELIMINAR);

                this.cargarDatosComboCamaInterfaz();
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
                if (this.camaActiva != null) {
                    devolucion = this.cargarDatosGeneralesInterfaz();
                    devolucion = this.cargarListaPacientesInterfaz() && devolucion;
                    devolucion = this.cargarListaHabitacionesInterfaz() && devolucion;
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
                this.textMarca.setText(this.camaActiva.getMarca());
                this.textModelo.setText(this.camaActiva.getModelo());
                // Resto 1 al tipo para acoplar los datos de la BD al enum TipoCama
                this.comboBoxTipoCama.setSelectedIndex(this.camaActiva.getTipo() - 1);
                this.textObservaciones.setText(this.camaActiva.getObservaciones());

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private boolean recogerDatosInterfaz() {
            boolean devolucion;

            try {
                if (this.camaActiva == null) {
                    this.camaActiva = new Cama(this.conexionBD);
                }

                this.camaActiva.setData(this.textMarca.getText(),
                        this.textModelo.getText(),
                        this.comboBoxTipoCama.getSelectedIndex() + 1,
                        this.textObservaciones.getText());

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        private boolean cargarDatosComboCamaInterfaz() {
            boolean devolucion;
            try {
                comboBoxTipoCama.removeAllItems(); // Limpiar el combo box antes de rellenar

                for (TipoCama tipo : TipoCama.values()) {
                    comboBoxTipoCama.addItem(tipo.getCodigo() + " - " + tipo.getDescripcion());
                }

                // Si necesitas seleccionar un valor específico en el combo box
                long camaSeleccionada = 0; // Esto debería ser el valor que quieres seleccionar
                TipoCama tipoSeleccionado = TipoCama.fromCodigo((int) camaSeleccionada);

                for (int i = 0; i < comboBoxTipoCama.getItemCount(); i++) {
                    if (comboBoxTipoCama.getItemAt(i).startsWith(tipoSeleccionado.getCodigo() + " -")) {
                        comboBoxTipoCama.setSelectedIndex(i);
                        break;
                    }
                }

                devolucion = true;
            } catch (Exception ex) {
                devolucion = false;
            }

            return devolucion;
        }

        @Override
        public void dispose(){
            super.dispose();
            if(listener != null){
                listener.cuandoCierraFormulario();
            }
        }

    // <editor-fold defaultstate="collapsed" desc="Carga de datos de los Pacientes">
        public boolean cargarListaPacientesInterfaz() {
            boolean devolucion;

            try {
                this.pacientes = this.camaActiva.getTodosPacientes();

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
                this.tablaHabitaciones.setModel(modeloTabla);

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
        textMarca = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textObservaciones = new javax.swing.JTextArea();
        labelMarca = new javax.swing.JLabel();
        labelObservaciones = new javax.swing.JLabel();
        textModelo = new javax.swing.JTextField();
        labelModelo = new javax.swing.JLabel();
        labelTipo = new javax.swing.JLabel();
        comboBoxTipoCama = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPacientes = new javax.swing.JTable();
        panelHabitaciones = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaHabitaciones = new javax.swing.JTable();

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

        labelMarca.setText("Marca:");

        labelObservaciones.setText("Observaciones:");

        labelModelo.setText("Modelo:");

        labelTipo.setText("Tipo:");

        comboBoxTipoCama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelMarca)
                                .addGap(12, 12, 12)
                                .addComponent(textMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelModelo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelTipo)
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxTipoCama, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(423, 423, 423))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMarca))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelModelo))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTipo)
                    .addComponent(comboBoxTipoCama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(labelObservaciones)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
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
        jScrollPane2.setViewportView(tablaPacientes);

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

        panelFichas.addTab("Pacientes", jPanel3);

        tablaHabitaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Número", "Planta"
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
        tablaHabitaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaHabitacionesMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablaHabitaciones);

        javax.swing.GroupLayout panelHabitacionesLayout = new javax.swing.GroupLayout(panelHabitaciones);
        panelHabitaciones.setLayout(panelHabitacionesLayout);
        panelHabitacionesLayout.setHorizontalGroup(
            panelHabitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
        );
        panelHabitacionesLayout.setVerticalGroup(
            panelHabitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFichas.addTab("Habitaciones", panelHabitaciones);

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
            this.camaActiva.eliminar();
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
            if (this.camaActiva.agregar()) {
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
            if (this.camaActiva.modificar()) {
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

        if (this.tablaPacientes != null) {
            this.pacienteSeleccionado = this.pacientes.get(this.tablaPacientes.getSelectedRow());

            if (this.pacienteSeleccionado != null) {

                formularioPaciente = new FormMantPacientes(this.pacienteSeleccionado,
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
    private javax.swing.JComboBox<String> comboBoxTipoCama;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labelMarca;
    private javax.swing.JLabel labelModelo;
    private javax.swing.JLabel labelObservaciones;
    private javax.swing.JLabel labelTipo;
    private javax.swing.JTabbedPane panelFichas;
    private javax.swing.JPanel panelHabitaciones;
    private javax.swing.JTable tablaHabitaciones;
    private javax.swing.JTable tablaPacientes;
    private javax.swing.JTextField textMarca;
    private javax.swing.JTextField textModelo;
    private javax.swing.JTextArea textObservaciones;
    // End of variables declaration//GEN-END:variables
}
