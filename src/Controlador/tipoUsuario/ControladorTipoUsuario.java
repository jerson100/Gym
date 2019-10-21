package Controlador.tipoUsuario;

import configuracion.App;
import enumerados.EDaoManager;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotUpdate;
import interfaces.IController;
import interfaces.ICrud;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import modelo.dao.daoManager.DaoManager;
import modelo.entidad.TipoUsuario;
import util.validator.JeValidatorLetter;
import vista.VistaMenuPrincipal;
import vista.VistaTipoUsuario;

/**
 *
 * @author Jerson
 */
public class ControladorTipoUsuario implements IController{
    public ICrud daoTipoUsuario;
    private VistaTipoUsuario vista;
    private TablaTipoUsuario modeloTabla;
    private boolean edit;

    public ControladorTipoUsuario() {
        init();
    }

    private void init() {
        daoTipoUsuario = DaoManager.getDaoManager(EDaoManager.TIPO_USUARIO_DAO);
        vista = new VistaTipoUsuario();
        vista.txtBuscarTipo.repintarPlaceHolder();
    }

    @Override
    public void open() {
        vista.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        modeloTabla = new TablaTipoUsuario(daoTipoUsuario);
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
        vista.btnSalir.addActionListener(oyenteBtn);
        vista.btnBorrar.addActionListener(oyenteBtn);
        vista.btnCancelar.addActionListener(oyenteBtn);
        vista.btnEditar.addActionListener(oyenteBtn);
        vista.btnGrabar.addActionListener(oyenteBtn);
        vista.btnNuevo.addActionListener(oyenteBtn);
        vista.btnLeft.addActionListener(oyenteBtn);
        vista.btnRight.addActionListener(oyenteBtn);
        vista.tblTipoDocumento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /*if(e.getModifiers() == 4){//si se hace click derecho
                    vista.popup.show(e.getComponent(), e.getX(), e.getY());
                }else{*/
                    if(e.getModifiers()==16){//click derecho
                        editar();
                        habilitaControles(false);
                    }
                /*}*/
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
    
    private class OyenteBoton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (vista.btnSalir == evt.getSource()) {
                cerrar();
            } else if (vista.btnNuevo == evt.getSource()) {
                habilitaControles(true);
                limpiarJtextFiel();
                repintarPlaceHolder();
            } else if (vista.btnGrabar == evt.getSource()) {
                TipoUsuario tipo = new TipoUsuario();
                tipo.setTipo(vista.txtTipo.getText().trim().toLowerCase());
                if (!validar()) {
                    return;
                }
                String aux = "";
                try {
                    if (edit) {
                        tipo.setIdTipoUsuario(Integer.parseInt(vista.txtId.getText()));
                        daoTipoUsuario.actualizar(tipo);
                        aux = "Registro Actualizado";
                    } else {
                        daoTipoUsuario.crear(tipo);
                        aux = "Registro Agregado";
                    }
                    actualizarTabla();
                    limpiarJtextFiel();
                    repintarPlaceHolder();
                } catch (NotUpdate | NotCreate ex) {
                    aux = ex.getMessage();
                } finally {
                    JOptionPane.showMessageDialog(vista, aux,
                            "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                }
                habilitaControles(false);
            } else if (vista.btnCancelar == evt.getSource()) {
                habilitaControles(false);
                limpiarJtextFiel();
                repintarPlaceHolder();
            } else if (vista.btnEditar == evt.getSource()) {
                if (vista.tblTipoDocumento.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(vista,
                            "Seleccione una fila de la tabla para poder editarlo");
                } else {
                    habilitaControles(true);
                    edit = true;
                    editar();
                    repintarTextoNormal();
                }
            } else if (vista.btnBorrar == evt.getSource()) {

                if (!vista.txtId.getText().isEmpty()) {

                    String aux = "";

                    int opc = JOptionPane.showConfirmDialog(vista,
                            "Seguro que desea elimnar el registro?",
                            "Precaución",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (opc != JOptionPane.YES_OPTION) {
                        return;
                    }

                    try {
                        daoTipoUsuario.eliminar(Integer.parseInt(vista.txtId.getText()));
                        aux = "Registro eliminado";
                        actualizarTabla();
                        limpiarJtextFiel();
                        repintarPlaceHolder();
                    } catch (NotDelete ex) {
                        aux = ex.getMessage();
                    } finally {
                        JOptionPane.showMessageDialog(vista, aux,
                                "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

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

    }

    private boolean validar() {
        String msg = "";
        if (!JeValidatorLetter.isLetter(vista.txtTipo.getText())) {
            msg = "El tipo de documento no es válido, solo se admiten letras";
        } else if (vista.txtTipo.getText().length() > App.MAX_NOMBRE_TIPODOCUMENTO) {
            msg = "El tipo de documento solo debe contener menos de: " + App.MAX_NOMBRE_TIPODOCUMENTO;
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
        vista.dispose();
    }
    
    private void actualizarTabla() {
        modeloTabla.setActiveSearch(false);
        modeloTabla.restablecerData();
        //modeloTabla.updateAll();
        vista.tblTipoDocumento.updateUI();
        pintarLabelsBD();
    }

    private void editar() {
        vista.txtId.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 0)));
        vista.txtTipo.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 1)));
    }

    private void repintarPlaceHolder() {
        vista.txtId.repintarPlaceHolder();
        vista.txtTipo.repintarPlaceHolder();
    }

    private void repintarTextoNormal() {
        vista.txtId.repintarTextoNormal();
        vista.txtTipo.repintarTextoNormal();
    }

    @Override
    public void limpiarTable() {
    }

    @Override
    public void habilitaControles(boolean opc) {
        vista.txtTipo.setEnabled(opc);
        vista.btnBorrar.setEnabled(!opc);
        vista.btnCancelar.setEnabled(opc);
        vista.btnEditar.setEnabled(!opc);
        vista.btnGrabar.setEnabled(opc);
        vista.btnNuevo.setEnabled(!opc);
        vista.btnSalir.setEnabled(opc);
        edit = false;
    }

    @Override
    public void limpiarJtextFiel() {
        vista.txtTipo.setText("");
        vista.txtId.setText("");
    }
}
