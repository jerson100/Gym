package modelo.dao.mysql;

import conexionMysql.ConexionMysql;
import excepcion.NotAll;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotRead;
import excepcion.NotUpdate;
import interfaces.IPais;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import modelo.entidad.Pais;

/**
 *
 * @author Jerson
 */
public class PaisDao implements IPais {
    
    private ConexionMysql conexion;
    private ResultSet rs;
    private PreparedStatement pr;
    private CallableStatement ca;
    private Connection con;

    @Override
    public void crear(Pais obj) throws NotCreate {
        try {
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            
            ca = con.prepareCall("call sq_insertPais(?)");
            ca.setString(1, obj.getNombre());
            
            if(ca.executeUpdate() == 0){
                throw new NotCreate("No se pudo crear el país");
            }
        } catch (SQLException ex) {
            throw new NotCreate("No se pudo crear el país");
        } finally{
            cerrarConexion();
        }
    }

    @Override
    public Pais buscar(int id) throws NotRead {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar(Pais obj) throws NotUpdate {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(int id) throws NotDelete {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pais> listar() throws NotAll {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pais> ListarCondicion(String txt) throws NotAll {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadRegistros() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pais> listar(int i, int f) throws NotAll {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadRegistrosCondicion(String txt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pais> ListarCondicion(String like, int i, int c) throws NotAll {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void cerrarConexion() {
        conexion.cerrar();
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {}
        }
        if (pr != null) {
            try {
                pr.close();
                pr = null;
            } catch (SQLException ex) {}
        }
        if (ca != null) {
            try {
                ca.close();
                ca = null;
            } catch (SQLException ex) {}
        }
        if(con != null){
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {}
        }
    }
    
}
