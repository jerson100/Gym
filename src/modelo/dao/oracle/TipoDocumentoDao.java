package modelo.dao.oracle;

import conexionOracle.ConexionOracle;
import excepcion.NotAll;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotRead;
import excepcion.NotUpdate;
import interfaces.ITipoDocumento;
import java.util.List;
import modelo.entidad.TipoDocumento;

/**
 *
 * @author Jerson
 */
public class TipoDocumentoDao implements ITipoDocumento{
    
    private ConexionOracle conexion;
    
    public TipoDocumentoDao(){
    }

    @Override
    public void crear(TipoDocumento obj) throws NotCreate {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoDocumento buscar(int id) throws NotRead {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar(TipoDocumento obj) throws NotUpdate {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TipoDocumento> Listar() throws NotAll {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(int id) throws NotDelete {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
