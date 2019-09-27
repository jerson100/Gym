package interfaces;

import java.sql.Connection;

/**
 *
 * @author Jerson
 */

public interface IConexionAbstract {
    
    public Connection conectar();
    public void cerrar();
    
}
