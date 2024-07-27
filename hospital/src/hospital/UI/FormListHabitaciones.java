package hospital.UI;

import hospital.kernel.Habitacion;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andres
 */
public class FormListHabitaciones extends javax.swing.JDialog {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private final Connection conexionBD;

    ArrayList<Habitacion> listaHabitaciones;

    Habitacion habitacionSeleccionada = null;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public FormListHabitaciones(Connection conexionBD,
            java.awt.Frame parent,
            boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Listado de Habitaciones");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Realización de las consultas">
    private boolean realizarConsulta() {
        boolean devolucion;
        int plazas = 0;
        int numHabitacion = 0;
        int planta = 0;

        try {

            if (!this.textPlazas.getText().equals("")) {
                plazas = Integer.parseInt(this.textPlazas.getText());
            }

            if (!this.textNumHabitacion.getText().equals("")) {
                numHabitacion = Integer.parseInt(this.textNumHabitacion.getText());
            }

            if (!this.textPlanta.getText().equals("")) {
                plazas = Integer.parseInt(this.textPlanta.getText());
            }

            this.listaHabitaciones = Habitacion.getTodasHabitaciones(
                    this.checkNumHabitacion.isSelected(),
                    numHabitacion,
                    this.checkPlanta.isSelected(),
                    planta,
                    this.checkPlazas.isSelected(),
                    plazas,
                    this.checkObservaciones.isSelected(),
                    this.textObservaciones.getText(),
                    conexionBD);

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }
        return devolucion;
    }

    private boolean visualizarListaHabitaciones() {
        boolean devolucion;
        int VIndice;
        Habitacion habitacionAux;
        String[] Linea = new String[4];

        DefaultTableModel modeloTabla;

        try {

            modeloTabla = this.configurarListaHabitaciones();

            for (VIndice = 0; VIndice < this.listaHabitaciones.size(); VIndice++) {
                habitacionAux = this.listaHabitaciones.get(VIndice);

                Linea[0] = Integer.toString(habitacionAux.getNumHabitacion());
                Linea[1] = Integer.toString(habitacionAux.getNumPlanta());
                Linea[2] = Integer.toString(habitacionAux.getNumPlazas());
                Linea[3] = habitacionAux.getObservaciones();

                modeloTabla.addRow(Linea);
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
            devolucion.addColumn("Plazas");
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

        popMenuMantenimiento = new javax.swing.JPopupMenu();
        opcionModificar = new javax.swing.JMenuItem();
        opcionEliminar = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaHabitaciones = new javax.swing.JTable();
        textNumHabitacion = new javax.swing.JTextField();
        textPlanta = new javax.swing.JTextField();
        textObservaciones = new javax.swing.JTextField();
        checkObservaciones = new javax.swing.JCheckBox();
        checkNumHabitacion = new javax.swing.JCheckBox();
        checkPlanta = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        checkPlazas = new javax.swing.JCheckBox();
        textPlazas = new javax.swing.JTextField();
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

        tablaHabitaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Número", "Planta", "Plazas", "Observaciones"
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
        tablaHabitaciones.setComponentPopupMenu(popMenuMantenimiento);
        tablaHabitaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaHabitacionesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaHabitaciones);

        checkObservaciones.setText("Observaciones:");

        checkNumHabitacion.setText("Número:");
        checkNumHabitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkNumHabitacionActionPerformed(evt);
            }
        });

        checkPlanta.setText("Planta:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("-");

        checkPlazas.setText("Plazas:");

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
                        .addGap(238, 238, 238)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkNumHabitacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textNumHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkPlanta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkPlazas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkObservaciones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(textPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkPlazas))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textNumHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkNumHabitacion)
                        .addComponent(checkPlanta)))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkObservaciones)
                    .addComponent(textObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

// <editor-fold defaultstate="collapsed" desc="Eventos del Formulario">

    private void botonConsultarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonConsultarMouseClicked
        // TODO add your handling code here:

        this.realizarConsulta();
        this.visualizarListaHabitaciones();

    }//GEN-LAST:event_botonConsultarMouseClicked

    private void tablaHabitacionesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaHabitacionesMousePressed
        // TODO add your handling code here:
        int lineaSeleccionada;

        lineaSeleccionada = this.tablaHabitaciones.getSelectedRow();

        // Evito errores de consola, comprobando que la línea está selecc
        if (lineaSeleccionada >= 0) {
            this.habitacionSeleccionada = this.listaHabitaciones.get(lineaSeleccionada);
        }

    }//GEN-LAST:event_tablaHabitacionesMousePressed

    private void opcionModificarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionModificarMousePressed
        // TODO add your handling code here:
        FormMantHabitaciones formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un paciente seleccionado, evito errores en consola
        if (this.habitacionSeleccionada != null) {
            formulario = new FormMantHabitaciones(this.habitacionSeleccionada, FormMantHabitaciones.MODIFICAR,
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
        FormMantHabitaciones formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un paciente seleccionado, evito errores en consola
        if (this.habitacionSeleccionada != null) {
            formulario = new FormMantHabitaciones(this.habitacionSeleccionada, FormMantHabitaciones.ELIMINAR,
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
        FormMantHabitaciones formulario;

        formulario = new FormMantHabitaciones(this.conexionBD, this, true);
        formulario.setVisible(true);

    }//GEN-LAST:event_botonAgregarMouseClicked

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked

        this.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

    private void checkNumHabitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkNumHabitacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkNumHabitacionActionPerformed

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkNumHabitacion;
    private javax.swing.JCheckBox checkObservaciones;
    private javax.swing.JCheckBox checkPlanta;
    private javax.swing.JCheckBox checkPlazas;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem opcionEliminar;
    private javax.swing.JMenuItem opcionModificar;
    private javax.swing.JPopupMenu popMenuMantenimiento;
    private javax.swing.JTable tablaHabitaciones;
    private javax.swing.JTextField textNumHabitacion;
    private javax.swing.JTextField textObservaciones;
    private javax.swing.JTextField textPlanta;
    private javax.swing.JTextField textPlazas;
    // End of variables declaration//GEN-END:variables
}