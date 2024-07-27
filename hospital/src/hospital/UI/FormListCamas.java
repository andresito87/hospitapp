package hospital.UI;

import hospital.kernel.Cama;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andres
 */
public class FormListCamas extends javax.swing.JDialog {

// <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    private final Connection conexionBD;

    ArrayList<Cama> listaCamas;

    Cama camaSeleccionada = null;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public FormListCamas(Connection conexionBD,
            java.awt.Frame parent,
            boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Listado de Camas");
        this.setLocation(300, 50);
        this.setSize(1000, 750);

        this.conexionBD = conexionBD;
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Realización de las consultas">
    private boolean realizarConsulta() {
        boolean devolucion;
        int tipo = -1;

        try {

            if (!this.textTipo.getText().equals("")) {
                tipo = Integer.parseInt(this.textTipo.getText());
            }

            this.listaCamas = Cama.getCamas(this.checkMarca.isSelected(), this.textMarca.getText(),
                    this.checkModelo.isSelected(), this.textModelo.getText(),
                    this.checkTipo.isSelected(), tipo,
                    this.checkObservaciones.isSelected(), this.textObservaciones.getText(),
                    conexionBD);

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }
        return devolucion;
    }

    private boolean visualizarListaCamas() {
        boolean devolucion;
        int VIndice;
        Cama camaAux;
        String[] Linea = new String[4];

        DefaultTableModel modeloTabla;

        try {

            modeloTabla = this.configurarListaCamas();

            for (VIndice = 0; VIndice < this.listaCamas.size(); VIndice++) {
                camaAux = this.listaCamas.get(VIndice);

                Linea[0] = camaAux.getMarca();
                Linea[1] = camaAux.getModelo();
                Linea[2] = Long.toString(camaAux.getTipo());
                Linea[3] = camaAux.getObservaciones();

                modeloTabla.addRow(Linea);
            }

            this.tablaCamas.setModel(modeloTabla);

            devolucion = true;
        } catch (Exception ex) {
            devolucion = false;
        }

        return devolucion;
    }

    private DefaultTableModel configurarListaCamas() {

        DefaultTableModel devolucion;

        try {
            devolucion = new DefaultTableModel();

            devolucion.addColumn("Marca");
            devolucion.addColumn("Modelo");
            devolucion.addColumn("Tipo");
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
        tablaCamas = new javax.swing.JTable();
        textMarca = new javax.swing.JTextField();
        textModelo = new javax.swing.JTextField();
        textObservaciones = new javax.swing.JTextField();
        checkObservaciones = new javax.swing.JCheckBox();
        checkMarca = new javax.swing.JCheckBox();
        checkModelo = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        checkTipo = new javax.swing.JCheckBox();
        textTipo = new javax.swing.JTextField();
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

        tablaCamas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Marca", "Modelo", "Tipo", "Observaciones"
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
        tablaCamas.setComponentPopupMenu(popMenuMantenimiento);
        tablaCamas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaCamasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCamas);

        checkObservaciones.setText("Observaciones:");

        checkMarca.setText("Marca:");
        checkMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMarcaActionPerformed(evt);
            }
        });

        checkModelo.setText("Modelo:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("-");

        checkTipo.setText("Tipo:");

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
                        .addComponent(checkMarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkModelo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(checkTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(textTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkTipo))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkMarca)
                        .addComponent(checkModelo)))
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
        this.visualizarListaCamas();

    }//GEN-LAST:event_botonConsultarMouseClicked

    private void tablaCamasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCamasMousePressed
        // TODO add your handling code here:
        int lineaSeleccionada;

        lineaSeleccionada = this.tablaCamas.getSelectedRow();

        // Evito errores de consola, comprobando que la línea está selecc
        if (lineaSeleccionada >= 0) {
            this.camaSeleccionada = this.listaCamas.get(lineaSeleccionada);
        }

    }//GEN-LAST:event_tablaCamasMousePressed

    private void opcionModificarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionModificarMousePressed
        // TODO add your handling code here:
        FormMantCamas formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un paciente seleccionado, evito errores en consola
        if (this.camaSeleccionada != null) {
            formulario = new FormMantCamas(this.camaSeleccionada, FormMantCamas.MODIFICAR,
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
        FormMantCamas formulario;
        FormAvisoUsuario formularioAviso;

        // Compruebo si hay un paciente seleccionado, evito errores en consola
        if (this.camaSeleccionada != null) {
            formulario = new FormMantCamas(this.camaSeleccionada, FormMantCamas.ELIMINAR,
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

    private void checkMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkMarcaActionPerformed

// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox checkMarca;
    private javax.swing.JCheckBox checkModelo;
    private javax.swing.JCheckBox checkObservaciones;
    private javax.swing.JCheckBox checkTipo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem opcionEliminar;
    private javax.swing.JMenuItem opcionModificar;
    private javax.swing.JPopupMenu popMenuMantenimiento;
    private javax.swing.JTable tablaCamas;
    private javax.swing.JTextField textMarca;
    private javax.swing.JTextField textModelo;
    private javax.swing.JTextField textObservaciones;
    private javax.swing.JTextField textTipo;
    // End of variables declaration//GEN-END:variables
}