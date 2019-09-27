package Controlador.tipoDocumento;

import enumerados.EDaoManager;
import interfaces.ICrud;
import java.awt.event.KeyAdapter;
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
    
    public void init(){
       daoTipoDocumento = DaoManager.getDaoManager(EDaoManager.TIPO_DOCUMENTO_DAO);
       vista = new VistaTipoDocumento();
    };
    
    public void open(){
        vista.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        vista.setVisible(true);
        agregarOyente();
        VistaMenuPrincipal.ESCRITORIO.add(vista);
    }
    
    //agregamos los listener
    private void agregarOyente(){
        
    }
    
    public void cerrar(){
        vista.dispose();
    }
    
    private class OyenteBoton extends MouseAdapter{
        
        @Override
        public void mouseClicked(MouseEvent evt){
            
        }
        
    }
    
}
