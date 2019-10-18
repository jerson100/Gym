package modelo.dao.daoManager;

import conexionMysql.ConexionMysql;
import conexionOracle.ConexionOracle;
import enumerados.EDaoManager;
import interfaces.IConexionAbstract;
import interfaces.ICrud;

/**
 *
 * @author Jerson
 */

/*
  Patrón Factory:
  Nos permite crear instancias
*/
public class DaoManager {
    
    private static final String CONN = "MYSQL";
    
    private DaoManager(){};
    
    public static ICrud getDaoManager(EDaoManager dao){
        
        //IConexionAbstract conn = ConexionMysql.GetInstance();
        
        ICrud crud = null;
        
        if(CONN.equals("MYSQL")){
        
            switch(dao){
                case TIPO_DOCUMENTO_DAO:
                    crud = new modelo.dao.mysql.TipoDocumentoDao();
                    break;
                case TIPO_USUARIO_DAO:
                    crud = new modelo.dao.mysql.TipoUsuarioDao();
                    break;
            }
            
       }else{
        
            switch(dao){
                case TIPO_DOCUMENTO_DAO:
                    crud = new modelo.dao.oracle.TipoDocumentoDao();
                    break;
            }
        
        }
        
        return crud;
        
    }
    
}
