package Controlador.tipoDocumento;

import configuracion.App;
import enumerados.EDaoManager;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotUpdate;
import interfaces.IController;
import interfaces.ICrud;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.dao.daoManager.DaoManager;
import modelo.entidad.TipoDocumento;
import util.validator.JeValidatorLetter;
import vista.paneles.PanelTipoDocumento;

/**
 *
 * @author Jerson
 */
public class ControladorTipoDocumento implements IController {

    public ICrud daoTipoDocumento;
    private PanelTipoDocumento vista;
    private TablaTipoDocumento modeloTabla;

    public ControladorTipoDocumento() {
        init();
    }

    private void init() {
        daoTipoDocumento = DaoManager.getDaoManager(EDaoManager.TIPO_DOCUMENTO_DAO);
        vista = new PanelTipoDocumento();
        vista.txtBuscarTipo.repintarPlaceHolder();
    }

    public void open(JPanel pnlPrincipal) {
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

        pnlPrincipal.updateUI();
    }

    @Override
    public void open() {
        //vista.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        modeloTabla = new TablaTipoDocumento(daoTipoDocumento);
        vista.tblTipoDocumento.setModel(modeloTabla);
        modeloTabla.updateAll();
        pintarLabelsBD();//si queremos imprimir algo por si no hay registros
        vista.setVisible(true);
        agregarOyente();
        //VistaMenuPrincipal.ESCRITORIO.add(vista);
    }

    //agregamos los listener
    private void agregarOyente() {
        OyenteBoton oyenteBtn = new OyenteBoton();
        vista.btnLeft.addMouseListener(oyenteBtn);
        vista.btnRight.addMouseListener(oyenteBtn);
        vista.btnAgregar.addMouseListener(oyenteBtn);
        vista.btnEliminar.addMouseListener(oyenteBtn);
        vista.btnActualizar.addMouseListener(oyenteBtn);
        vista.tblTipoDocumento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editar();
                habilitaControles(false);
            }

        });
        vista.txtBuscarTipo.addKeyListener(new OyenteTeclado());
    }

    private class OyenteTeclado extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (vista.txtBuscarTipo.getText().isEmpty()) {
                actualizarTabla();
            } else {
                modeloTabla.searchSensitve(vista.txtBuscarTipo.getText(), true);
                modeloTabla.setActiveSearch(true);
                modeloTabla.setTextPreviousSearch(vista.txtBuscarTipo.getText());
            }
            vista.tblTipoDocumento.updateUI();
            pintarLabelsBD();
        }

    }

    private class OyenteBoton extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {

            if (vista.btnAgregar == evt.getSource()) {

                agregarTipoDocumento();

            } else if (vista.btnActualizar == evt.getSource()) {
                
                actualizarTipoDocumento();

            } else if (vista.btnEliminar == evt.getSource()) {

                eliminarTipoDocumento();

            } else if (vista.btnLeft == evt.getSource()) {
                modeloTabla.getPaginator().prev();
                pintarLabelsBD();
                vista.tblTipoDocumento.updateUI();
            } else if (vista.btnRight == evt.getSource()) {
                modeloTabla.getPaginator().next();
                pintarLabelsBD();
                vista.tblTipoDocumento.updateUI();
            }
        }

        private void agregarTipoDocumento() {
            //obtenemos los datos de las cajas de texto
            String tipo = vista.txtTipo_agregar.getText();
            String abreviatura = vista.txtAbreviatura_agregar.getText();

            if (validar(tipo, abreviatura)) {

                String msg = "";
                TipoDocumento tipoD = new TipoDocumento(tipo, abreviatura);

                try {
                    daoTipoDocumento.crear(tipoD);
                    msg = "Registro agregado satisfactoriamente";
                    limpiarJtextFiel();
                    repintarPlaceHolder();
                    actualizarTabla();
                } catch (NotCreate ex) {
                    msg = ex.getMessage();
                } finally {
                    JOptionPane.showMessageDialog(null, msg, "Informe",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        }

        private void actualizarTipoDocumento() {
            
            String msg = "";
            
            if(!vista.txtId_actualizar.getText().isEmpty()){
                
                int id = Integer.parseInt(vista.txtId_actualizar.getText());
                String tipo = vista.txtTipo_actualizar.getText();
                String abreviatura = vista.txtAbreviatura_actualizar.getText();
                
                if(validar(tipo, abreviatura)){
                    
                    TipoDocumento tipoDocumento = new TipoDocumento(id,tipo,abreviatura);
                    
                    try {
                        daoTipoDocumento.actualizar(tipoDocumento);
                        msg = "Registro actualizado satisfactoriamente";
                        limpiarJtextFiel();
                        repintarPlaceHolder();
                        actualizarTabla();
                    } catch (NotUpdate ex) {
                        msg = ex.getMessage();
                    } 
                }else{
                    return;
                }
                
            }else{
            
                msg = "Seleccione en la pestaña principal el regiStro a modificar ♥";
                
            }
            
            JOptionPane.showMessageDialog(null, msg, "Informe",
                            JOptionPane.INFORMATION_MESSAGE);
            
        }
        
        private void eliminarTipoDocumento() {
            
            String msg = "";
            
            if (!vista.txtId_eliminar.getText().isEmpty()) {

                int opc = JOptionPane.showConfirmDialog(vista,
                        "Seguro que desea elimnar el registro?",
                        "Precaución",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (opc != JOptionPane.YES_OPTION) return;

                try {
                    daoTipoDocumento.eliminar(Integer.parseInt(vista.txtId_eliminar.getText()));
                    msg = "Registro eliminado satisfactoriamente";
                    limpiarJtextFiel();
                    repintarPlaceHolder();
                    actualizarTabla();
                } catch (NotDelete ex) {
                    msg = ex.getMessage();
                } 
                
            }else{
                
                msg = "Seleccione en la pestaña principal el regiStro a modificar ♥";
                
            }
            
            JOptionPane.showMessageDialog(vista, msg,
                            "Informe", JOptionPane.INFORMATION_MESSAGE);
            
        }

    }

    private boolean validar(String jtxtTipo, String jtxtAbreviatura) {
        String msg = "";
        if (!JeValidatorLetter.isLetter(jtxtTipo)) {
            msg = "El tipo de documento no es válido, solo se admiten letras";
        } else if (jtxtTipo.length() > App.MAX_NOMBRE_TIPODOCUMENTO) {
            msg = "El tipo de documento solo debe contener menos de: " + App.MAX_NOMBRE_TIPODOCUMENTO;
        } else if (!JeValidatorLetter.isLetter(jtxtAbreviatura)) {
            msg = "La abreviatura no es válido, solo se admiten letras";
        } else if (jtxtAbreviatura.length() > App.MAX_ABREVIATURA_TIPODOCUMENTO) {
            msg = "El tipo de documento solo debe contener menos de: " + App.MAX_ABREVIATURA_TIPODOCUMENTO;
        }

        if (!msg.isEmpty()) {
            JOptionPane.showMessageDialog(vista, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void pintarLabelsBD() {
        vista.lblCntRegistros.setText(String.valueOf(modeloTabla
                .getPaginator()
                .getCantidadRegistros()));
        vista.lblNumPaginas.setText((modeloTabla.getPaginator().getPaginaActual() + 1)
                + "/" + modeloTabla.getPaginator().getCantidadPaginas());
    }

    @Override
    public void cerrar() {
    }

    private void actualizarTabla() {
        modeloTabla.setActiveSearch(false);
        modeloTabla.restablecerData();
        vista.tblTipoDocumento.updateUI();
        pintarLabelsBD();
    }

    //debemos cargar la info a los jtextField para poder actualizar el registro y tmb el de eliminar
    private void editar() {
        vista.txtId_eliminar.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 0)));
        vista.txtId_actualizar.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 0)));
        vista.txtTipo_actualizar.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 1)));
        vista.txtAbreviatura_actualizar.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 2)));    
        vista.txtAbreviatura_actualizar.repintarTextoNormal();
        
    }

    private void repintarPlaceHolder() {
        vista.txtAbreviatura_agregar.repintarPlaceHolder();
        vista.txtAbreviatura_actualizar.repintarPlaceHolder();
        vista.txtBuscarTipo.repintarPlaceHolder();
        vista.txtTipo_agregar.repintarPlaceHolder();
        vista.txtTipo_actualizar.repintarPlaceHolder();
        vista.txtId_eliminar.repintarPlaceHolder();
        vista.txtId_actualizar.repintarPlaceHolder();
    }

    private void repintarTextoNormal() {
        vista.txtAbreviatura_agregar.repintarTextoNormal();
        vista.txtId_actualizar.repintarTextoNormal();
        vista.txtTipo_agregar.repintarTextoNormal();
    }

    @Override
    public void limpiarTable() {
    }

    @Override
    public void habilitaControles(boolean opc) {
    }

    @Override
    public void limpiarJtextFiel() {
        vista.txtAbreviatura_agregar.setText("");
        vista.txtAbreviatura_actualizar.setText("");
        vista.txtBuscarTipo.setText("");
        vista.txtTipo_agregar.setText("");
        vista.txtTipo_actualizar.setText("");
        vista.txtId_eliminar.setText("");
        vista.txtId_actualizar.setText("");
    }

}
