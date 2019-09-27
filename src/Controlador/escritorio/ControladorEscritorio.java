package Controlador.escritorio;

import Controlador.tipoDocumento.ControladorTipoDocumento;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import vista.VistaMenuPrincipal;

/**
 *
 * @author Jerson
 */
public final class ControladorEscritorio {
    
    private VistaMenuPrincipal vista;
    
    public ControladorEscritorio(){init();}
    
    public void init(){
       vista = new VistaMenuPrincipal();
    }
   
    public void open(){
       addOyente();
       vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       vista.setLocationRelativeTo(null);
       vista.setVisible(true); 
    }
    
    private void addOyente(){
        OyenteBotton oyenteBtn = new OyenteBotton();
        vista.mnuItemPais.addMouseListener(oyenteBtn);
    }
    
    private class OyenteBotton extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getSource()==vista.mnuItemPais){
                ControladorTipoDocumento controladorTipoD = new ControladorTipoDocumento();
                controladorTipoD.open();
            }
        }

    }
}