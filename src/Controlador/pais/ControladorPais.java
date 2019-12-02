package Controlador.pais;

import enumerados.EDaoManager;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotUpdate;
import interfaces.IController;
import interfaces.ICrud;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.dao.daoManager.DaoManager;
import modelo.entidad.Pais;
import vista.paneles.PanelPais;

/**
 *
 * @author Jerson
 */
public class ControladorPais implements IController{
    private TablaPais tblPais;
    private PanelPais vista;
    private ICrud dao;

    public ControladorPais() {}

    public void open(JPanel pnlPrincipal){
        open();
        GroupLayout pnlPrincipalLayout = (GroupLayout) pnlPrincipal.getLayout();
        pnlPrincipal.removeAll();//removemos todos los componentes que contiene

        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(vista, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
        );
        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(vista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlPrincipal.updateUI();//actualizamos el panel principal
    }
    
    private class OyenteBoton extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent e) {
        
            JButton btn = (JButton)e.getSource();
            
            if(btn == vista.btnAgregar){
                
                try {
                    String txtPais = btn.getText();
                    Pais pais = new Pais();
                    pais.setNombre(txtPais);
                    dao.crear(pais);
                    JOptionPane.showMessageDialog(vista, "País creado");
                } catch (NotCreate ex) {
                    JOptionPane.showMessageDialog(vista, ex.getMessage());
                }
                
            }else if(btn == vista.btnActualizar){
                
                try {
                    int id = Integer.parseInt(vista.txtActualiza_IdPais.getText());
                    String pais = vista.txtActualiza_pais.getText();
                    
                    Pais obj = new Pais();
                    obj.setIdPais(id);
                    obj.setNombre(pais);
                    
                    dao.actualizar(obj);
                    JOptionPane.showMessageDialog(vista, "País actualizado");
                    
                } catch (NotUpdate ex) {
                    JOptionPane.showMessageDialog(vista, ex.getMessage());
                }
                
            }else if(btn == vista.btnEliminar){
                
                try {
                    int id = Integer.parseInt(vista.txtEliminar_IdPais.getText());
                    dao.eliminar(id);
                    JOptionPane.showMessageDialog(vista, "País eliminado");
                } catch (NotDelete ex) {
                    JOptionPane.showMessageDialog(vista, ex.getMessage());
                }
                
            }else if(btn == vista.btnLeft){
                
                tblPais.getPaginator().prev();
                vista.tblPais.updateUI();
                
            }else if(btn == vista.btnRight){
                
                tblPais.getPaginator().next();
                vista.tblPais.updateUI();
                
            }
            
        }
        
        
        
    }
    
    private class OyenteTeclado extends KeyAdapter{

        @Override
        public void keyReleased(KeyEvent e) {
            
            JTextField txt = (JTextField)e.getSource();
            
            if(txt == vista.txtBuscarPais){
                
                tblPais.searchSensitve(txt.getText(), true);
                tblPais.setActiveSearch(true);
                tblPais.setTextPreviousSearch(txt.getText());
                
                pintarLabels();
                
                //actualizamos la tabla
                
                vista.tblPais.updateUI();
                
            }
        
        }
        
    }
    
    @Override
    public void open(){
        
        dao = DaoManager.getDaoManager(EDaoManager.PAIS);
        vista = new PanelPais();
        tblPais = new TablaPais(dao);
        
        //asignamos el modelo de la tabla
        vista.tblPais.setModel(tblPais);
        
        //actualizamos la tabla
        tblPais.updateAll();
        
        //hacemos visible el panel vista
        vista.setVisible(true);
        
        agregarOyente();
        
    }
    
    private void agregarOyente(){
        OyenteBoton oyente = new OyenteBoton();
        vista.btnAgregar.addMouseListener(oyente);
        vista.btnActualizar.addMouseListener(oyente);
        vista.btnEliminar.addMouseListener(oyente);
        vista.btnLeft.addMouseListener(oyente);
        vista.btnRight.addMouseListener(oyente);
        
        vista.tblPais.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                Point posicion = e.getPoint();
                int row = vista.tblPais.rowAtPoint(posicion);
                llenarCampos(row);
                
            }

            private void llenarCampos(int row) {
                vista.txtActualiza_IdPais.setText(String.valueOf(tblPais.getValueAt(row, 0)));
                vista.txtActualiza_pais.setText(String.valueOf(tblPais.getValueAt(row, 1)));
                vista.txtEliminar_IdPais.setText(String.valueOf(tblPais.getValueAt(row, 0)));
            }
            
        });
        
        
        vista.txtBuscarPais.addKeyListener(new OyenteTeclado()); 
    }
    
    private void pintarLabels(){
        vista.lblNumPaginas.setText(String.valueOf(tblPais.getPaginator().getCantidadPaginas()));
        vista.lblCntRegistros.setText(String.valueOf(tblPais.getPaginator().getCantidadRegistros()));
    }
    
    @Override
    public void limpiarTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void habilitaControles(boolean opc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cerrar() {
        
    }

    @Override
    public void limpiarJtextFiel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
