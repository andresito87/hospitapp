package hospital.UI;

import hospital.kernel.Diagnostico;
import hospital.kernel.Medico;
import hospital.kernel.Paciente;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andres
 */
public class FormListDiagnosticos extends javax.swing.JDialog {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private final Connection conexionBD;
    private ArrayList<Diagnostico> listaDiagnosticos;

    private Diagnostico diagnosticoSeleccionado = null;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public FormListDiagnosticos(Connection conexionBD,
            java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Listado de Diagnósticos");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Realización de las consultas">
    private boolean realizarConsulta() {
        boolean devolucion;
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        long idMedico = 0;
        long idPaciente = 0;
        int tipo = 0;

        try {

            try {
                fechaInicio = LocalDate.parse(this.textFechaInicio.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            try {
                fechaFin = LocalDate.parse(this.textFechaFin.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {

            }

            if (!this.textIdMedico.getText().equals("")) {
                idMedico = Long.parseLong(this.textIdMedico.getText());
            }

            if (!this.textIdPaciente.getText().equals("")) {
                idPaciente = Long.parseLong(this.textIdPaciente.getText());
            }

            if (!this.textTipo.getText().equals("")) {
                tipo = Integer.parseInt(this.textTipo.getText());
            }

            this.listaDiagnosticos = Diagnostico.getDiagnosticos(
                    this.checkFecha.isSelected(), fechaInicio, fechaFin,
                    this.checkIdMedico.isSelected(), idMedico,
                    this.checkIdPaciente.isSelected(), idPaciente,
                    this.checkTipo.isSelected(), tipo,
                    this.checkCodigo.isSelected(), this.textCodigo.getText(),
                    this.checkDescripcion.isSelected(), this.textDescripcion.getText(),
                    this.checkObservaciones.isSelected(), this.textObservaciones.getText(),
                    conexionBD);

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private boolean visualizarListaMedicos() {
        boolean devolucion;
        int indice;
        Diagnostico diagnosticoAux;
        String[] linea = new String[7];

        DefaultTableModel modeloTabla;

        try {

            modeloTabla = this.configurarListaDiagnosticos();

            for (indice = 0; indice < this.listaDiagnosticos.size(); indice++) {
                diagnosticoAux = this.listaDiagnosticos.get(indice);

                linea[0] = diagnosticoAux.getFecha().toString();
                linea[1] = Medico.getMedico(diagnosticoAux.getIdMedico(), conexionBD).getNombreFormalCompleto();
                linea[2] = Paciente.getPaciente(diagnosticoAux.getIdMedico(), conexionBD).getNombreFormalCompleto();
                linea[3] = Integer.toString(diagnosticoAux.getTipo());
                linea[4] = diagnosticoAux.getCodigo();
                linea[5] = diagnosticoAux.getDescripcion();
                linea[6] = diagnosticoAux.getObservaciones();

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

            devolucion.addColumn("Fecha");
            devolucion.addColumn("Médico");
            devolucion.addColumn("Paciente");
            devolucion.addColumn("Tipo");
            devolucion.addColumn("Código");
            devolucion.addColumn("Descripción");
            devolucion.addColumn("Observaciones");

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

        menuTablaMedicos = new javax.swing.JPopupMenu();
        opcionModificar = new javax.swing.JMenuItem();
        opcionEliminar = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        botonBuscar = new javax.swing.JButton();
        botonAgregar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDiagnosticos = new javax.swing.JTable();
        checkIdMedico = new javax.swing.JCheckBox();
        textIdMedico = new javax.swing.JTextField();
        checkIdPaciente = new javax.swing.JCheckBox();
        textIdPaciente = new javax.swing.JTextField();
        checkCodigo = new javax.swing.JCheckBox();
        textCodigo = new javax.swing.JTextField();
        checkObservaciones = new javax.swing.JCheckBox();
        textObservaciones = new javax.swing.JTextField();
        checkTipo = new javax.swing.JCheckBox();
        textTipo = new javax.swing.JTextField();
        checkDescripcion = new javax.swing.JCheckBox();
        textDescripcion = new javax.swing.JTextField();
        textFechaInicio = new javax.swing.JFormattedTextField();
        checkFecha = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        textFechaFin = new javax.swing.JFormattedTextField();

        opcionModificar.setText("Modificar");
        opcionModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                opcionModificarMousePressed(evt);
            }
        });
        menuTablaMedicos.add(opcionModificar);

        opcionEliminar.setText("Eliminar");
        opcionEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                opcionEliminarMousePressed(evt);
            }
        });
        menuTablaMedicos.add(opcionEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Médicos");

        jToolBar1.setRollover(true);

        botonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hospital/images/Lupa.png"))); // NOI18N
        botonBuscar.setBorder(null);
        botonBuscar.setBorderPainted(false);
        botonBuscar.setFocusable(false);
        botonBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonBuscarMouseClicked(evt);
            }
        });
        jToolBar1.add(botonBuscar);

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

        tablaDiagnosticos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Médico", "Paciente", "Tipo", "Código", "Decripción", "Observaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDiagnosticos.setComponentPopupMenu(menuTablaMedicos);
        tablaDiagnosticos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDiagnosticosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDiagnosticos);
        if (tablaDiagnosticos.getColumnModel().getColumnCount() > 0) {
            tablaDiagnosticos.getColumnModel().getColumn(3).setPreferredWidth(50);
            tablaDiagnosticos.getColumnModel().getColumn(3).setMaxWidth(50);
            tablaDiagnosticos.getColumnModel().getColumn(4).setPreferredWidth(50);
            tablaDiagnosticos.getColumnModel().getColumn(4).setMaxWidth(50);
        }

        checkIdMedico.setText("Id Médico:");
        checkIdMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkIdMedicoActionPerformed(evt);
            }
        });

        checkIdPaciente.setText("Id Paciente:");

        checkCodigo.setText("Código:");
        checkCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCodigoActionPerformed(evt);
            }
        });

        checkObservaciones.setText("Observaciones:");

        checkTipo.setText("Tipo:");
        checkTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTipoActionPerformed(evt);
            }
        });

        checkDescripcion.setText("Descripción:");
        checkDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkDescripcionActionPerformed(evt);
            }
        });

        try {
            textFechaInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        checkFecha.setText("Fecha:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("-");

        try {
            textFechaFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkObservaciones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textObservaciones))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textDescripcion))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkIdMedico)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textIdMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(checkTipo)
                                .addGap(18, 18, 18)
                                .addComponent(textTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkIdPaciente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(checkCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 37, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkFecha)
                    .addComponent(jLabel9)
                    .addComponent(textFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkIdMedico)
                        .addComponent(textIdMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkTipo)
                        .addComponent(textTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkIdPaciente)
                    .addComponent(textIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkCodigo)
                    .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkDescripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkObservaciones))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

// <editor-fold defaultstate="collapsed" desc="Eventos del Formulario">

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked

        this.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

    private void botonBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonBuscarMouseClicked

        this.realizarConsulta();
        this.visualizarListaMedicos();

    }//GEN-LAST:event_botonBuscarMouseClicked

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked

        FormMantDiagnosticos formulario;

        formulario = new FormMantDiagnosticos(this.conexionBD, this, true);
        formulario.setVisible(true);

    }//GEN-LAST:event_botonAgregarMouseClicked

    private void tablaDiagnosticosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDiagnosticosMouseClicked

        this.diagnosticoSeleccionado = listaDiagnosticos.get(tablaDiagnosticos.getSelectedRow());

    }//GEN-LAST:event_tablaDiagnosticosMouseClicked

    private void opcionModificarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionModificarMousePressed

        FormMantDiagnosticos formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un diagnostico seleccionado, evito errores en consola
        if (this.diagnosticoSeleccionado != null) {
            formulario = new FormMantDiagnosticos(this.diagnosticoSeleccionado, FormMantDiagnosticos.MODIFICAR,
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

        FormMantDiagnosticos formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un diagnostico seleccionado, evito errores en consola
        if (this.diagnosticoSeleccionado != null) {
            formulario = new FormMantDiagnosticos(this.diagnosticoSeleccionado, FormMantDiagnosticos.ELIMINAR,
                    this.conexionBD, this, true);
            formulario.setVisible(true);
        } else {
            // Sino hay diagnostico seleccionado, muestro aviso al usuario
            formularioAviso = new FormAvisoUsuario(
                    FormAvisoUsuario.NO_SELECCIONADO,
                    this,
                    true);
            formularioAviso.setVisible(true);
        }

    }//GEN-LAST:event_opcionEliminarMousePressed

    private void checkTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkTipoActionPerformed

    private void checkCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkCodigoActionPerformed

    private void checkIdMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkIdMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkIdMedicoActionPerformed

    private void checkDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkDescripcionActionPerformed

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkCodigo;
    private javax.swing.JCheckBox checkDescripcion;
    private javax.swing.JCheckBox checkFecha;
    private javax.swing.JCheckBox checkIdMedico;
    private javax.swing.JCheckBox checkIdPaciente;
    private javax.swing.JCheckBox checkObservaciones;
    private javax.swing.JCheckBox checkTipo;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPopupMenu menuTablaMedicos;
    private javax.swing.JMenuItem opcionEliminar;
    private javax.swing.JMenuItem opcionModificar;
    private javax.swing.JTable tablaDiagnosticos;
    private javax.swing.JTextField textCodigo;
    private javax.swing.JTextField textDescripcion;
    private javax.swing.JFormattedTextField textFechaFin;
    private javax.swing.JFormattedTextField textFechaInicio;
    private javax.swing.JTextField textIdMedico;
    private javax.swing.JTextField textIdPaciente;
    private javax.swing.JTextField textObservaciones;
    private javax.swing.JTextField textTipo;
    // End of variables declaration//GEN-END:variables
}
