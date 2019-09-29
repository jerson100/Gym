package Controlador.tipoDocumento;

import configuracion.App;
import enumerados.EDaoManager;
import excepcion.NotAll;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotUpdate;
import interfaces.IController;
import interfaces.ICrud;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import modelo.dao.daoManager.DaoManager;
import modelo.entidad.TipoDocumento;
import util.validator.JeValidatorLetter;
import vista.VistaMenuPrincipal;
import vista.VistaTipoDocumento;

/**
 *
 * @author Jerson
 */
public class ControladorTipoDocumento implements IController {

    public ICrud daoTipoDocumento;
    private VistaTipoDocumento vista;
    private TablaTipoDocumento modeloTabla;
    private boolean edit;

    public ControladorTipoDocumento() {
        init();
    }

    private void init() {
        daoTipoDocumento = DaoManager.getDaoManager(EDaoManager.TIPO_DOCUMENTO_DAO);
        vista = new VistaTipoDocumento();
        vista.txtBuscarTipo.repintarPlaceHolder();
    }

    
    
    @Override
    public void open() {
        vista.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        modeloTabla = new TablaTipoDocumento(daoTipoDocumento);
        vista.tblTipoDocumento.setModel(modeloTabla);
        try {
            modeloTabla.update();
        } catch (NotAll ex) {
        }//si queremos imprimir algo por si no hay registros
        vista.setVisible(true);
        agregarOyente();
        VistaMenuPrincipal.ESCRITORIO.add(vista);
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
        vista.tblTipoDocumento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editar();
                habilitaControles(false);
            }

        });
    }

    @Override
    public void cerrar() {
        vista.dispose();
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
                TipoDocumento tipo = new TipoDocumento(vista.txtTipo.getText().trim(),
                         vista.txtAbreviatura.getText().trim());
                if (!validar())return;
                String aux = "";
                try {
                    if (edit) {
                        tipo.setIdTipoDocumento(Integer.parseInt(vista.txtId.getText()));
                        daoTipoDocumento.actualizar(tipo);
                        aux = "Registro Actualizado";
                    } else {
                        daoTipoDocumento.crear(tipo);
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
                        daoTipoDocumento.eliminar(Integer.parseInt(vista.txtId.getText()));
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

            }
        }

    }

    private void actualizarTabla() {
        try {
            modeloTabla.remove();
            modeloTabla.update();
        } catch (NotAll ex) {
        } finally {
            vista.tblTipoDocumento.updateUI();
        }

    }

    private boolean validar() {
        String msg = "";
        if (!JeValidatorLetter.isLetter(vista.txtTipo.getText())) {
            msg = "El tipo de documento no es válido, solo se admiten letras";
        } else if (vista.txtTipo.getText().length() > App.MAX_NOMBRE_TIPODOCUMENTO) {
            msg = "El tipo de documento solo debe contener menos de: " + App.MAX_NOMBRE_TIPODOCUMENTO;
        } else if (!JeValidatorLetter.isLetter(vista.txtAbreviatura.getText())) {
            msg = "La abreviatura no es válido, solo se admiten letras";
        } else if (vista.txtAbreviatura.getText().length() > App.MAX_ABREVIATURA_TIPODOCUMENTO) {
            msg = "El tipo de documento solo debe contener menos de: " + App.MAX_ABREVIATURA_TIPODOCUMENTO;
        }

        if (!msg.isEmpty()) {
            JOptionPane.showMessageDialog(vista, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void editar() {

        vista.txtId.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 0)));
        vista.txtTipo.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 1)));
        vista.txtAbreviatura.setText(String.valueOf(vista.tblTipoDocumento.getValueAt(vista.tblTipoDocumento.getSelectedRow(), 2)));

    }

    private void repintarPlaceHolder() {
        vista.txtAbreviatura.repintarPlaceHolder();
        vista.txtId.repintarPlaceHolder();
        vista.txtTipo.repintarPlaceHolder();
    }

    private void repintarTextoNormal() {
        vista.txtAbreviatura.repintarTextoNormal();
        vista.txtId.repintarTextoNormal();
        vista.txtTipo.repintarTextoNormal();
    }

    @Override
    public void limpiarTable() {
    }

    @Override
    public void habilitaControles(boolean opc) {
        vista.txtAbreviatura.setEnabled(opc);
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
        vista.txtAbreviatura.setText("");
        vista.txtTipo.setText("");
        vista.txtId.setText("");
    }

}
