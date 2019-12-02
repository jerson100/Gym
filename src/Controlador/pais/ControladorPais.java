package Controlador.pais;

import enumerados.EDaoManager;
import interfaces.IController;
import interfaces.ICrud;
import modelo.dao.daoManager.DaoManager;
import vista.paneles.PanelPais;

/**
 *
 * @author Jerson
 */
public class ControladorPais implements IController{
    private TablaPais tblPais;
    private PanelPais pnlPais;
    private ICrud dao;

    public ControladorPais() {
        
    }
    
    private void init(){
        dao = DaoManager.getDaoManager(EDaoManager.PAIS);
        tblPais = new TablaPais(dao);
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
    public void open() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiarJtextFiel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
