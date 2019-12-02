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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        int count = 0;
        try {
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            pr = con.prepareStatement("select count(idPais) from vpais");
            rs = pr.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            cerrarConexion();
        }
        return count;
    }

    @Override
    public List<Pais> listar(int i, int f) throws NotAll {
        List<Pais> list = null;
        try {
            
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            pr = con.prepareStatement("select * from vpais limit ?,?");
            pr.setInt(1, i);
            pr.setInt(2, f);
            
            rs = pr.executeQuery();
            if(rs.next()){
                list = new ArrayList<>();
                Pais p = new Pais();
                p.setIdPais(rs.getInt(1));
                p.setNombre(rs.getString(2));
                list.add(p);
                while(rs.next()){
                    p = new Pais();
                    p.setIdPais(rs.getInt(1));
                    p.setNombre(rs.getString(2));
                    list.add(p);
                }
            }else{
                throw new NotAll("No se encontraron países disponibles");
            }
            
        } catch (SQLException e) {
            throw new NotAll("No se encontraron países disponibles");
        } finally{
            cerrarConexion();
        }
        
        return list;
        
    }

    @Override
    public int cantidadRegistrosCondicion(String txt) {
        
        int count = 0;
        try {
            
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            pr = con.prepareStatement("select count(idPais) from vpais where nombre like ?");
            pr.setString(1, "%"+txt+"%");
            
            rs = pr.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
        } finally{
            cerrarConexion();
        }
        
        return count;
        
    }

    @Override
    public List<Pais> ListarCondicion(String like, int i, int f) throws NotAll {
        List<Pais> list = null;
        try {
            
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            pr = con.prepareStatement("select * from vpais where nombre like ? limit ?,?");
            pr.setString(1, "%"+like+"%");
            pr.setInt(2, i);
            pr.setInt(3, f);
            
            rs = pr.executeQuery();
            if(rs.next()){
                list = new ArrayList<>();
                Pais p = new Pais();
                p.setIdPais(rs.getInt(1));
                p.setNombre(rs.getString(2));
                list.add(p);
                while(rs.next()){
                    p = new Pais();
                    p.setIdPais(rs.getInt(1));
                    p.setNombre(rs.getString(2));
                    list.add(p);
                }
            }else{
                throw new NotAll("No se encontraron países disponibles");
            }
            
        } catch (SQLException e) {
            throw new NotAll("No se encontraron países disponibles");
        } finally{
            cerrarConexion();
        }
        
        return list;
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
