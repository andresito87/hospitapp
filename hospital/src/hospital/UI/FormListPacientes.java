package hospital.UI;

import hospital.kernel.Paciente;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import utils.Utils;

/**
 *
 * @author andres
 */
public class FormListPacientes extends javax.swing.JDialog {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private Connection conexionBD;

    ArrayList<Paciente> listaPacientes;

    Paciente pacienteSeleccionado = null;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public FormListPacientes(Connection conexionBD,
            java.awt.Frame parent,
            boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Listado de Pacientes");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Realización de las consultas">
    private boolean realizarConsulta() {
        boolean devolucion;

        try {
            LocalDate fechaNacimientoInicio = null;
            LocalDate fechaNacimientoFin = null;
            LocalDate fechaIngresoInicio = null;
            LocalDate fechaIngresoFin = null;
            LocalDate fechaAltaInicio = null;
            LocalDate fechaAltaFin = null;

            try {
                fechaNacimientoInicio = LocalDate.parse(this.textFechaNacimientoInicio.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            try {
                fechaNacimientoFin = LocalDate.parse(this.textFechaNacimientoFin.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            try {
                fechaIngresoInicio = LocalDate.parse(this.textFechaIngresoInicio.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            try {
                fechaIngresoFin = LocalDate.parse(this.textFechaIngresoFin.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            try {
                fechaAltaInicio = LocalDate.parse(this.textFechaAltaInicio.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            try {
                fechaAltaFin = LocalDate.parse(this.textFechaAltaFin.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            this.listaPacientes = Paciente.getPacientes(this.checkDni.isSelected(), this.textDni.getText(),
                    this.checkCi.isSelected(), this.textCi.getText(),
                    this.checkNombre.isSelected(), this.textNombre.getText(),
                    this.checkApellido1.isSelected(), this.textApellido1.getText(),
                    this.checkApellido2.isSelected(), this.textApellido2.getText(),
                    this.checkFechaNacimiento.isSelected(),
                    fechaNacimientoInicio, fechaNacimientoFin,
                    this.checkNumeroSeguridadSocial.isSelected(), this.textNumSeguridadSocial.getText(),
                    this.checkFechaIngreso.isSelected(),
                    fechaIngresoInicio, fechaIngresoFin,
                    this.checkFechaAlta.isSelected(),
                    fechaAltaInicio, fechaAltaFin,
                    false, 0,
                    this.checkObservaciones.isSelected(), this.textObservaciones.getText(),
                    conexionBD);
            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean visualizarListaPacientes() {
        boolean devolucion;
        int VIndice;
        Paciente pacienteAux;
        String[] Linea = new String[4];

        DefaultTableModel modeloTabla;

        try {

            modeloTabla = this.configurarListaPacientes();

            for (VIndice = 0; VIndice < this.listaPacientes.size(); VIndice++) {
                pacienteAux = this.listaPacientes.get(VIndice);

                Linea[0] = pacienteAux.getCi();
                Linea[1] = pacienteAux.getApellido1();
                Linea[2] = pacienteAux.getApellido2();
                Linea[3] = pacienteAux.getNombre();

                modeloTabla.addRow(Linea);
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

            devolucion.addColumn("CI");
            devolucion.addColumn("1º apellido");
            devolucion.addColumn("2º Apellido");
            devolucion.addColumn("Nombre");

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

        popMenuMantenimiento = new javax.swing.JPopupMenu();
        opcionModificar = new javax.swing.JMenuItem();
        opcionEliminar = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPacientes = new javax.swing.JTable();
        textDni = new javax.swing.JTextField();
        textNombre = new javax.swing.JTextField();
        textApellido1 = new javax.swing.JTextField();
        textApellido2 = new javax.swing.JTextField();
        textCi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        textObservaciones = new javax.swing.JTextField();
        textFechaIngresoFin = new javax.swing.JFormattedTextField();
        textFechaNacimientoInicio = new javax.swing.JFormattedTextField();
        textFechaIngresoInicio = new javax.swing.JFormattedTextField();
        checkObservaciones = new javax.swing.JCheckBox();
        checkDni = new javax.swing.JCheckBox();
        checkCi = new javax.swing.JCheckBox();
        checkFechaNacimiento = new javax.swing.JCheckBox();
        checkNombre = new javax.swing.JCheckBox();
        checkApellido2 = new javax.swing.JCheckBox();
        checkApellido1 = new javax.swing.JCheckBox();
        checkFechaIngreso = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        textFechaNacimientoFin = new javax.swing.JFormattedTextField();
        checkFechaAlta = new javax.swing.JCheckBox();
        textFechaAltaInicio = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        textFechaAltaFin = new javax.swing.JFormattedTextField();
        checkNumeroSeguridadSocial = new javax.swing.JCheckBox();
        textNumSeguridadSocial = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        botonConsultar = new javax.swing.JButton();
        botonAgregar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();

        opcionModificar.setText("Modificar");
        opcionModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                opcionModificarMousePressed(evt);
            }
        });
        popMenuMantenimiento.add(opcionModificar);

        opcionEliminar.setText("Eliminar");
        opcionEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                opcionEliminarMousePressed(evt);
            }
        });
        popMenuMantenimiento.add(opcionEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "CI", "Prem. Apellido", "Seg. Apellido", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPacientes.setComponentPopupMenu(popMenuMantenimiento);
        tablaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaPacientesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPacientes);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("-");

        try {
            textFechaIngresoFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            textFechaNacimientoInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            textFechaIngresoInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        checkObservaciones.setText("Observaciones:");

        checkDni.setText("Dni:");

        checkCi.setText("CI:");

        checkFechaNacimiento.setText("Fecha Nacimiento:");

        checkNombre.setText("Nombre:");

        checkApellido2.setText("2º Apellido:");

        checkApellido1.setText("1º Apellido:");

        checkFechaIngreso.setText("Fecha Ingreso:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("-");

        try {
            textFechaNacimientoFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        checkFechaAlta.setText("Fecha Alta:");

        try {
            textFechaAltaInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("-");

        try {
            textFechaAltaFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        checkNumeroSeguridadSocial.setText("Nº S.S:");

        jToolBar1.setRollover(true);

        botonConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hospital/images/Lupa.png"))); // NOI18N
        botonConsultar.setBorder(null);
        botonConsultar.setBorderPainted(false);
        botonConsultar.setFocusable(false);
        botonConsultar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonConsultar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonConsultar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonConsultarMouseClicked(evt);
            }
        });
        jToolBar1.add(botonConsultar);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkFechaAlta)
                        .addGap(18, 18, 18)
                        .addComponent(textFechaAltaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFechaAltaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkApellido1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkApellido2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkDni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textDni, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkCi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textCi, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkNumeroSeguridadSocial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textNumSeguridadSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkFechaNacimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFechaNacimientoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFechaNacimientoFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkFechaIngreso)
                        .addGap(18, 18, 18)
                        .addComponent(textFechaIngresoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFechaIngresoFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkObservaciones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textNumSeguridadSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkNumeroSeguridadSocial))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textCi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkDni)
                        .addComponent(checkCi)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkNombre)
                    .addComponent(checkApellido2)
                    .addComponent(checkApellido1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFechaNacimientoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkFechaNacimiento)
                    .addComponent(jLabel9)
                    .addComponent(textFechaNacimientoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(textFechaIngresoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFechaIngresoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkFechaIngreso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(textFechaAltaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFechaAltaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkFechaAlta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkObservaciones))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

// <editor-fold defaultstate="collapsed" desc="Eventos del Formulario">

    private void botonConsultarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonConsultarMouseClicked
        // TODO add your handling code here:

        this.realizarConsulta();
        this.visualizarListaPacientes();

    }//GEN-LAST:event_botonConsultarMouseClicked

    private void tablaPacientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPacientesMousePressed
        // TODO add your handling code here:
        int lineaSeleccionada;

        lineaSeleccionada = this.tablaPacientes.getSelectedRow();

        // Evito errores de consola, comprobando que la línea está seleccionada
        if (lineaSeleccionada >= 0 && !Utils.isRowEmpty(lineaSeleccionada, tablaPacientes)) {
            this.pacienteSeleccionado = this.listaPacientes.get(lineaSeleccionada);
        }

    }//GEN-LAST:event_tablaPacientesMousePressed

    private void opcionModificarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionModificarMousePressed
        // TODO add your handling code here:
        FormMantPacientes formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un paciente seleccionado, evito errores en consola
        if (this.pacienteSeleccionado != null
                || tablaPacientes.getSelectedRow() != -1
                && !Utils.isRowEmpty(tablaPacientes.getSelectedRow(), tablaPacientes)) {
            formulario = new FormMantPacientes(this.pacienteSeleccionado, FormMantPacientes.MODIFICAR,
                    this.conexionBD, this, true);
            formulario.setVisible(true);
        } else {
            // Sino hay paciente seleccionado, muestro aviso al usuario
            formularioAviso = new FormAvisoUsuario(
                    FormAvisoUsuario.NO_SELECCIONADO,
                    this,
                    true);
            formularioAviso.setVisible(true);
        }

    }//GEN-LAST:event_opcionModificarMousePressed

    private void opcionEliminarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionEliminarMousePressed
        // TODO add your handling code here:
        FormMantPacientes formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un paciente seleccionado, evito errores en consola
        if (this.pacienteSeleccionado != null) {
            formulario = new FormMantPacientes(this.pacienteSeleccionado, FormMantPacientes.ELIMINAR,
                    this.conexionBD, this, true);
            formulario.setVisible(true);
        } else {
            // Sino hay paciente seleccionado, muestro aviso al usuario
            formularioAviso = new FormAvisoUsuario(
                    FormAvisoUsuario.NO_SELECCIONADO,
                    this,
                    true);
            formularioAviso.setVisible(true);
        }

    }//GEN-LAST:event_opcionEliminarMousePressed

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked
        // TODO add your handling code here:
        FormMantPacientes formulario;

        formulario = new FormMantPacientes(this.conexionBD, this, true);
        formulario.setVisible(true);


    }//GEN-LAST:event_botonAgregarMouseClicked

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked

        this.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkApellido1;
    private javax.swing.JCheckBox checkApellido2;
    private javax.swing.JCheckBox checkCi;
    private javax.swing.JCheckBox checkDni;
    private javax.swing.JCheckBox checkFechaAlta;
    private javax.swing.JCheckBox checkFechaIngreso;
    private javax.swing.JCheckBox checkFechaNacimiento;
    private javax.swing.JCheckBox checkNombre;
    private javax.swing.JCheckBox checkNumeroSeguridadSocial;
    private javax.swing.JCheckBox checkObservaciones;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem opcionEliminar;
    private javax.swing.JMenuItem opcionModificar;
    private javax.swing.JPopupMenu popMenuMantenimiento;
    private javax.swing.JTable tablaPacientes;
    private javax.swing.JTextField textApellido1;
    private javax.swing.JTextField textApellido2;
    private javax.swing.JTextField textCi;
    private javax.swing.JTextField textDni;
    private javax.swing.JFormattedTextField textFechaAltaFin;
    private javax.swing.JFormattedTextField textFechaAltaInicio;
    private javax.swing.JFormattedTextField textFechaIngresoFin;
    private javax.swing.JFormattedTextField textFechaIngresoInicio;
    private javax.swing.JFormattedTextField textFechaNacimientoFin;
    private javax.swing.JFormattedTextField textFechaNacimientoInicio;
    private javax.swing.JTextField textNombre;
    private javax.swing.JTextField textNumSeguridadSocial;
    private javax.swing.JTextField textObservaciones;
    // End of variables declaration//GEN-END:variables
}
