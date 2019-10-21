package util;

import conexionMysql.ConexionMysql;
import enumerados.EDaoManager;
import interfaces.ICrud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import modelo.dao.daoManager.DaoManager;
import modelo.entidad.TipoDocumento;

/**
 *
 * @author Jerson
 */
public class Prueba extends javax.swing.JFrame {

    private final ICrud dao;
    private final JePagination<TipoDocumento> pg;
    //private final TablaTipoDocumento tbl;
    private DefaultTableModel modelo;
    public Prueba() {
        initComponents();
        setTitle("Creado por Jerson Ramírez Ortiz - juamkoo@gmail.com");
        setLocationRelativeTo(null);
        modelo = (DefaultTableModel)tblDatos.getModel();
        dao = DaoManager.getDaoManager(EDaoManager.TIPO_DOCUMENTO_DAO);
        pg = new JePagination(dao.cantidadRegistros(), 5) {
            @Override
            public void updateData() {try {
                //en cada actualización que deseas que se realize
                /*try {
                tbl.update();
                } catch (NotAll ex) {
                }*/
                List<TipoDocumento> li = getDataBD(pg.getPaginaActual()*pg.getRegistrosXPagina(),
                                                   pg.getRegistrosXPagina());
                TipoDocumento t;
                removerDataTable();
                for (int i = 0; i < li.size(); i++) {
                    t = li.get(i);
                    Object[]obj = {t.getIdTipoDocumento(),t.getTipo(),t.getAbreviatura()};
                    modelo.addRow(obj);
                }
                lblPages.setText(pg.toString());
                tblDatos.updateUI();
                } catch (Exception ex) {}
            }

            //toda la lógica de la bd
            @Override
            public List<TipoDocumento> getDataBD(int paginaActual, int registrosXPagina) throws Exception {
                List<TipoDocumento> tdList = new ArrayList<>();
                ConexionMysql conexion = ConexionMysql.GetInstance();
                Connection con = conexion.conectar();
                PreparedStatement pr = null;
                ResultSet rs = null;
                try {
                    pr = con.prepareStatement("select * from vTipoDocumento limit ?,?");
                    pr.setInt(1, paginaActual);
                    pr.setInt(2, registrosXPagina);
                    rs = pr.executeQuery();
                    if (rs.next()) {
                        tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2), rs.getString(3)));
                        while (rs.next()) {
                            tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2), rs.getString(3)));
                        }
                    }
                } catch (SQLException e) {} 
                finally {}//cierra las conexiones
                return tdList;
            }
        };
        //tbl = new TablaTipoDocumento(dao);
        //tblDatos.setModel(tbl);
        lblCantidadRegistros.setText("" + pg.getCantidadRegistros());
        pg.updateData();
    }

    private void removerDataTable(){
        while(modelo.getRowCount()>0){
            modelo.removeRow(0);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblCantidadRegistros = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblPages = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblDatos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Tipo", "Abreviatura"
            }
        ));
        jScrollPane1.setViewportView(tblDatos);

        btnLeft.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLeft.setText("left");
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRight.setText("right");
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("cantidad de registros:");

        lblCantidadRegistros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCantidadRegistros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidadRegistros.setText("0");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("pages:");

        lblPages.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPages.setText("n/m");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Simple Paginación con BD");
        jLabel3.setToolTipText("");

        txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Ingrese la cantidad de registros por página");

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("establecer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnLeft)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRight)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(lblPages, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addGap(153, 153, 153)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCantidadRegistros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(67, 67, 67)))
                .addGap(79, 79, 79)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addGap(93, 93, 93))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPages, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblCantidadRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        pg.prev();
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        pg.next();
    }//GEN-LAST:event_btnRightActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int cant = Integer.parseInt(this.txt.getText());
        pg.setRegistrosXPagina(cant);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
           /* for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }*/
           JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Prueba().setVisible(true);
            }
        });
    }
/*
    public class TablaTipoDocumento extends AbstractTableModel {

        private ICrud daoTipoDocumento;
        private List<TipoDocumento> listaTipoDocumento;

        public TablaTipoDocumento(ICrud daoTipoDocumento) {
            try {
                this.daoTipoDocumento = daoTipoDocumento;
                update();
            } catch (NotAll ex) {
                listaTipoDocumento = new ArrayList<>();
            }
        }

        public void remove() {
            listaTipoDocumento.clear();
            listaTipoDocumento = null;
        }

        public void update() throws NotAll {
            listaTipoDocumento = daoTipoDocumento.listar(pg.getPaginaActual()
                    * pg.getRegistrosXPagina(), pg.getRegistrosXPagina());
        }

        public void updateAll() {
            try {
                listaTipoDocumento = daoTipoDocumento.listar();
            } catch (NotAll ex) {
                listaTipoDocumento = new ArrayList<>();
            }
        }

        public void searchSensitve(String dat) {
            try {
                remove();
                listaTipoDocumento = daoTipoDocumento.ListarCondicion(dat);
            } catch (NotAll ex) {
                listaTipoDocumento = new ArrayList<>();
            }
        }

        @Override
        public int getRowCount() {
            return listaTipoDocumento.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "IdTipoDocumento";
                case 1:
                    return "Tipo";
                case 2:
                    return "Abreviatura";
            }
            return "";
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            TipoDocumento tipo = listaTipoDocumento.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return tipo.getIdTipoDocumento();
                case 1:
                    return tipo.getTipo();
                case 2:
                    return tipo.getAbreviatura();
            }
            return null;
        }

    }

*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidadRegistros;
    private javax.swing.JLabel lblPages;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txt;
    // End of variables declaration//GEN-END:variables
}
