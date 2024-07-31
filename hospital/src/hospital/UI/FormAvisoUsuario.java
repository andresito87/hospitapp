package hospital.UI;

/**
 *
 * @author andres
 */
public class FormAvisoUsuario extends javax.swing.JDialog {

    // <editor-fold defaultstate="collapsed" desc="Atributos de la Clase">
    public static final int NO_SELECCIONADO = 0;
    public static final int ERROR_OPERACION = 1;
    public static final int OPERACION_NO_REVERSIBLE = 2;
    public static final int INFO_INEXISTENTE = 3;
    public static final int OPERACION_EXITOSA = 4;
    public static final int OPERACION_CON_DATOS_INCORRECTOS = 5;
    public static final int OPERACION_SALIR = 6;

    private final int tipoAviso;
    private String mensaje;
    private boolean operacionAceptada;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructores de la Clase">
    public FormAvisoUsuario(int tipoAviso,
            java.awt.Dialog parent,
            boolean modal) {
        super(parent, modal);
        initComponents();

        this.setTitle("Aviso");
        this.setLocation(500, 150);
        this.setSize(550, 250); // Tamaño del diálogo
        this.setLayout(null); // Establecer layout a null
        this.botonAceptar.setBounds(100, 150, 100, 40); // Colocar botón Aceptar
        this.botonCancelar.setBounds(300, 150, 100, 40); // Colocar botón Cancelar

        this.tipoAviso = tipoAviso;
        this.mensaje = this.generarMensaje();
        //coloco el texto del aviso
        this.textoAviso.setText(mensaje);

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos GET">
    public int getTipoAviso() {
        return tipoAviso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean esOperacionAceptada() {
        return operacionAceptada;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos SET">
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setOperacionAceptada(boolean operacionAceptada) {
        this.operacionAceptada = operacionAceptada;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos personalizados">
    private String generarMensaje() {
        String mensajeDevuelto;
        switch (this.tipoAviso) {
            case 0:
                mensajeDevuelto = "No podemos procesar la información, selecciona un elemento de la lista.";
                noVisualizarBotonCancelar();
                break;
            case 1:
                mensajeDevuelto = "Error en la operación, vuelva a intentarlo.";
                noVisualizarBotonCancelar();
                break;
            case 2:
                mensajeDevuelto = "Atención esta operación es irreversible. ¿Estás segur@ que deseas continuar?";
                break;
            case 3:
                mensajeDevuelto = "Lo sentimos pero no disponemos de esa información.";
                noVisualizarBotonCancelar();
                break;
            case 4:
                mensajeDevuelto = "Genial, Operación realizada con éxito.";
                noVisualizarBotonCancelar();
                break;
            case 5:
                mensajeDevuelto = "Uy, parece que hay datos incorrectos. Revisa y vuelve a intentarlo.";
                noVisualizarBotonCancelar();
                break;
            case 6:
                mensajeDevuelto = "¿Estás segur@ que deseas cerrar la aplicación?";
                break;
            default:
                mensajeDevuelto = "Hubo algún problema. Revisa la información suministrada.";
                noVisualizarBotonCancelar();
        }

        return mensajeDevuelto;
    }

    private void noVisualizarBotonCancelar() {
        this.botonCancelar.setVisible(false);
        this.botonAceptar.setBounds(225, 150, 100, 40); // Reposicionar el botón "Aceptar"
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

        jLabel1 = new javax.swing.JLabel();
        textoAviso = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("¡¡¡¡ Aviso Importante !!!!");

        textoAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoAviso.setText("Texto Mensaje de operacion");

        botonAceptar.setText("Aceptar");
        botonAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAceptarMouseClicked(evt);
            }
        });
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonCancelarMouseClicked(evt);
            }
        });
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(textoAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Eventos del Formulario">
    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAceptarMouseClicked
        // TODO add your handling code here:
        this.setOperacionAceptada(true);
        this.dispose();
    }//GEN-LAST:event_botonAceptarMouseClicked

    private void botonCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCancelarMouseClicked
        // TODO add your handling code here:
        this.setOperacionAceptada(false);
        this.dispose();
    }//GEN-LAST:event_botonCancelarMouseClicked

    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel textoAviso;
    // End of variables declaration//GEN-END:variables

}
