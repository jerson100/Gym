package Controlador.tipoDocumento;

import enumerados.EDaoManager;
import excepcion.NotAll;
import interfaces.ICrud;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import modelo.dao.daoManager.DaoManager;
import vista.VistaMenuPrincipal;
import vista.VistaTipoDocumento;

/**
 *
 * @author Jerson
 */
public class ControladorTipoDocumento{
    
    private ICrud daoTipoDocumento;
    private VistaTipoDocumento vista;
    private TablaTipoDocumento modeloTabla;
    
    public ControladorTipoDocumento(){init();}
    
    private void init(){
       daoTipoDocumento = DaoManager.getDaoManager(EDaoManager.TIPO_DOCUMENTO_DAO);
       vista = new VistaTipoDocumento();
    };
    
    public void open(){
        vista.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        modeloTabla = new TablaTipoDocumento(daoTipoDocumento);
        vista.tblTipoDocumento.setModel(modeloTabla);
        try {
            modeloTabla.update();
        } catch (NotAll ex) {}//si queremos imprimir algo por si no hay registros
        vista.setVisible(true);
        agregarOyente();
        VistaMenuPrincipal.ESCRITORIO.add(vista);
    }
    
    //agregamos los listener
    private void agregarOyente(){
        OyenteBoton oyenteBtn = new OyenteBoton();
        vista.btnSalir.addMouseListener(oyenteBtn);
        vista.btnBorrar.addMouseListener(oyenteBtn);
        vista.btnCancelar.addMouseListener(oyenteBtn);
        vista.btnEditar.addMouseListener(oyenteBtn);
        vista.btnGrabar.addMouseListener(oyenteBtn);
        vista.btnNuevo.addMouseListener(oyenteBtn);
    }
    
    public void cerrar(){
        vista.dispose();
    }
    
    private class OyenteBoton extends MouseAdapter{
        
        @Override
        public void mouseClicked(MouseEvent evt){
            if(vista.btnSalir==evt.getSource()){
                cerrar();
            }
        }
        
    }
    
    private void limpiarTable(){
        
    }
    
}
