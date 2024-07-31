package hospital.UI;

import hospital.kernel.FormularioListener;
import hospital.kernel.Medico;
import hospital.kernel.Paciente;
import hospital.kernel.VisitaMedica;
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
public class FormListVisitasMedicas extends javax.swing.JDialog implements FormularioListener {

    // <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
        private final Connection conexionBD;
        private ArrayList<VisitaMedica> listaVisitasMedicas;

        private VisitaMedica visitaMedicaSelecionada = null;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
        public FormListVisitasMedicas(Connection conexionBD,
                java.awt.Frame parent, boolean modal) {
            super(parent, modal);
            initComponents();

            this.setTitle("Listado de Visitas Médicas");
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

                this.listaVisitasMedicas = VisitaMedica.getVisitasMedicas(
                        this.checkFecha.isSelected(), fechaInicio, fechaFin,
                        this.checkIdMedico.isSelected(), idMedico,
                        this.checkIdPaciente.isSelected(), idPaciente,
                        this.checkObservaciones.isSelected(), this.textObservaciones.getText(),
                        conexionBD);

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
            String[] linea = new String[4];

            DefaultTableModel modeloTabla;

            try {

                modeloTabla = this.configurarListaVisitasMedicas();

                for (indice = 0; indice < this.listaVisitasMedicas.size(); indice++) {
                    visitaMedicaAux = this.listaVisitasMedicas.get(indice);

                    linea[0] = visitaMedicaAux.getFecha().toString();
                    linea[1] = Medico.getMedico(visitaMedicaAux.getIdMedico(), conexionBD).getNombreFormalCompleto();
                    linea[2] = Paciente.getPaciente(visitaMedicaAux.getIdPaciente(), conexionBD).getNombreFormalCompleto();
                    linea[3] = visitaMedicaAux.getObservaciones();

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
                devolucion.addColumn("Médico");
                devolucion.addColumn("Paciente");
                devolucion.addColumn("Observaciones");

            } catch (Exception ex) {
                devolucion = null;
            }

            return devolucion;

        }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos de Interfaz">
        @Override
        public void cuandoCierraFormulario() {
            this.realizarConsulta();
            this.visualizarListaVisitasMedicas();
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
        tablaVisitasMedicas = new javax.swing.JTable();
        checkIdMedico = new javax.swing.JCheckBox();
        textIdMedico = new javax.swing.JTextField();
        checkIdPaciente = new javax.swing.JCheckBox();
        textIdPaciente = new javax.swing.JTextField();
        checkObservaciones = new javax.swing.JCheckBox();
        textObservaciones = new javax.swing.JTextField();
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

        tablaVisitasMedicas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Medico", "Paciente", "Fecha", "Observaciones"
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
        tablaVisitasMedicas.setComponentPopupMenu(menuTablaMedicos);
        tablaVisitasMedicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaVisitasMedicasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaVisitasMedicas);

        checkIdMedico.setText("Id Médico:");
        checkIdMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkIdMedicoActionPerformed(evt);
            }
        });

        checkIdPaciente.setText("Id Paciente:");

        checkObservaciones.setText("Observaciones:");

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkObservaciones)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addComponent(textIdMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkIdPaciente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkIdMedico)
                    .addComponent(textIdMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkIdPaciente)
                    .addComponent(textIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkFecha)
                    .addComponent(jLabel9)
                    .addComponent(textFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkObservaciones)
                    .addComponent(textObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Eventos del Formulario">

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked

        this.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

    private void botonBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonBuscarMouseClicked

        this.realizarConsulta();
        this.visualizarListaVisitasMedicas();

    }//GEN-LAST:event_botonBuscarMouseClicked

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked

        FormMantVisitasMedicas formulario;

        formulario = new FormMantVisitasMedicas(this, this.conexionBD, this, true);
        formulario.setVisible(true);

    }//GEN-LAST:event_botonAgregarMouseClicked

    private void opcionModificarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionModificarMousePressed

        FormMantVisitasMedicas formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un visita medica seleccionada, evito errores en consola
        if (this.visitaMedicaSelecionada != null
                || tablaVisitasMedicas.getSelectedRow() != -1
                && !Utils.isRowEmpty(tablaVisitasMedicas.getSelectedRow(), tablaVisitasMedicas)) {
            formulario = new FormMantVisitasMedicas(this, this.visitaMedicaSelecionada, FormMantVisitasMedicas.MODIFICAR,
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

        FormMantVisitasMedicas formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un visita medica seleccionada, evito errores en consola
        if (this.visitaMedicaSelecionada != null) {
            formulario = new FormMantVisitasMedicas(this, this.visitaMedicaSelecionada, FormMantVisitasMedicas.ELIMINAR,
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

    private void checkIdMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkIdMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkIdMedicoActionPerformed

    private void tablaVisitasMedicasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVisitasMedicasMousePressed
        // TODO add your handling code here:
        int lineaSeleccionada;

        lineaSeleccionada = this.tablaVisitasMedicas.getSelectedRow();

        // Evito errores de consola, comprobando que la línea está seleccionada
        if (lineaSeleccionada >= 0 && !Utils.isRowEmpty(lineaSeleccionada, tablaVisitasMedicas)) {
            this.visitaMedicaSelecionada = this.listaVisitasMedicas.get(lineaSeleccionada);
        }
    }//GEN-LAST:event_tablaVisitasMedicasMousePressed

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkFecha;
    private javax.swing.JCheckBox checkIdMedico;
    private javax.swing.JCheckBox checkIdPaciente;
    private javax.swing.JCheckBox checkObservaciones;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPopupMenu menuTablaMedicos;
    private javax.swing.JMenuItem opcionEliminar;
    private javax.swing.JMenuItem opcionModificar;
    private javax.swing.JTable tablaVisitasMedicas;
    private javax.swing.JFormattedTextField textFechaFin;
    private javax.swing.JFormattedTextField textFechaInicio;
    private javax.swing.JTextField textIdMedico;
    private javax.swing.JTextField textIdPaciente;
    private javax.swing.JTextField textObservaciones;
    // End of variables declaration//GEN-END:variables
}
