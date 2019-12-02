package Controlador.escritorio;

import Controlador.pais.ControladorPais;
import Controlador.tipoDocumento.ControladorTipoDocumento;
import Controlador.tipoUsuario.ControladorTipoUsuario;
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
        vista.btnTipoDocumento.addMouseListener(oyenteBtn);
        vista.btnTipoUsurio.addMouseListener(oyenteBtn);
        vista.btnSalir.addMouseListener(oyenteBtn);
        vista.btnPais.addMouseListener(oyenteBtn);
    }
    
    private class OyenteBotton extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getSource()==vista.btnTipoDocumento){
                ControladorTipoDocumento controladorTipoD = new ControladorTipoDocumento();
                controladorTipoD.open(vista.pnlPrincipal);
            }else if(e.getSource()==vista.btnTipoUsurio){
                ControladorTipoUsuario controladorTU = new ControladorTipoUsuario();
                controladorTU.open(vista.pnlPrincipal);
            }else if(e.getSource()==vista.btnPais){
                ControladorPais controlador = new ControladorPais();
                controlador.open(vista.pnlPrincipal);
            }else if(e.getSource()==vista.btnSalir){
                System.exit(0);
            }
        }

    }
}
