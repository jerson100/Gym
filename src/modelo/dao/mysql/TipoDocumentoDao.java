package modelo.dao.mysql;

import conexionMysql.ConexionMysql;
import excepcion.NotAll;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotRead;
import excepcion.NotUpdate;
import interfaces.ITipoDocumento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entidad.TipoDocumento;

/**
 *
 * @author Jerson
 */
//Para mysql
public class TipoDocumentoDao implements ITipoDocumento {

    private ConexionMysql conexion;
    private ResultSet rs;
    private PreparedStatement pr;
    private CallableStatement ca;
    private Connection con;
    
    public TipoDocumentoDao() {}

    @Override
    public void crear(TipoDocumento obj) throws NotCreate {
        conexion = ConexionMysql.GetInstance();
        con = conexion.conectar();
        try {
            ca = con.prepareCall("call ps_insert_tipoDocumento(?,?)");
            ca.setString(1, obj.getTipo());
            ca.setString(2, obj.getAbreviatura());
            if (ca.executeUpdate() == 0) {
                throw new NotCreate("No se pudo crear el tipo de documento");
            }
        } catch (SQLException e) {
            throw new NotCreate("No se pudo crear el tipo de documento: "+e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public TipoDocumento buscar(int id) throws NotRead {
        TipoDocumento td = null;
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            pr = con.prepareStatement("select * from vTipoDocumento where idTipoDocumento = ?");
            pr.setInt(1, id);
            rs = pr.executeQuery();
            if (rs.next()) {
                td = new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3));
            } else {
                throw new NotRead("No se encontró el tipo de documento: ");
            }
        } catch (SQLException e) {
             throw new NotRead("No se encontró el tipo de documento: "+e.getMessage());
        } finally{
            cerrarConexion();
        }
        return td;
    }

    @Override
    public void actualizar(TipoDocumento obj) throws NotUpdate {
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            ca = con.prepareCall("call ps_update_tipoDocumento(?,?,?)");
            ca.setInt(1, obj.getIdTipoDocumento());
            ca.setString(2, obj.getTipo());
            ca.setString(3, obj.getAbreviatura());
            if (ca.executeUpdate() == 0) {
                throw new NotUpdate("No se pudo actualizar el tipo de documento");
            }
        } catch (SQLException e) {
            throw new NotUpdate("No se pudo actualizar el tipo de documento: "+e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public void eliminar(int id) throws NotDelete {
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            ca = con.prepareCall("call ps_delete_tipoDocumento(?)");
            ca.setInt(1, id);
            if (ca.executeUpdate() == 0) {
                throw new NotDelete("No se pudo eliminar el tipo de documento");
            }
        } catch (SQLException e) {
            throw new NotDelete("No se pudo eliminar el tipo de documento: "+e.getMessage());
        } finally {
            cerrarConexion();
        }
    }
    
    @Override
    public List<TipoDocumento> listar() throws NotAll {
        List<TipoDocumento> tdList = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            pr = con.prepareStatement("select * from vTipoDocumento");
            rs = pr.executeQuery();
            if(rs.next()){
                tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                while(rs.next()){
                    tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                }
            }else{
                throw new NotAll("No se encontraron registros de tipos de documentos");
            }
        } catch (SQLException e) {
            throw new NotAll("No se encontraron registros de tipos de documentos");
        } finally{
            cerrarConexion();
        }
        return tdList;
    }
    
    @Override
    public int cantidadRegistros(){
        int all = 0;
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            pr = con.prepareStatement("select count(*) from vTipoDocumento");
            rs = pr.executeQuery();
            if(rs.next()){
                all = rs.getInt(1);
            }
        } catch (SQLException e) {
        } finally{
            cerrarConexion();
        }
        return all;
    }
    
    @Override
    public List<TipoDocumento> ListarCondicion(String txt) throws NotAll {
        List<TipoDocumento> tdList = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            String d = "%"+txt+"%";
            pr = con.prepareStatement("select * from vTipoDocumento where tipo like ?");
            pr.setString(1, d);
            rs = pr.executeQuery();
            if(rs.next()){
                tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                while(rs.next()){
                    tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                }
            }else{
                throw new NotAll("No se encontraron registros de tipos de documentos");
            }
        } catch (SQLException e) {
            throw new NotAll("No se encontraron registros de tipos de documentos: "+e.getMessage());
        } finally{
            cerrarConexion();
        }
        return tdList;
    }
    
    @Override
    public List<TipoDocumento> listar(int i, int f) throws NotAll{
        List<TipoDocumento> tdList = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            pr = con.prepareStatement("select * from vTipoDocumento limit ?,?");
            pr.setInt(1, i);
            pr.setInt(2, f);
            rs = pr.executeQuery();
            if(rs.next()){
                tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                while(rs.next()){
                    tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                }
            }else{
                throw new NotAll("No se encontraron registros");
            }
        } catch (SQLException e) {
            throw new NotAll("No se encontraron registros");
        } finally{
            cerrarConexion();
        }
        return tdList;
}

    @Override
    public int cantidadRegistrosCondicion(String txt) {
        int all = 0;
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        String like = "%"+txt+"%";
        try {
            pr = con.prepareStatement("select count(*) from vTipoDocumento where tipo like ?");
            pr.setString(1, like);
            rs = pr.executeQuery();
            if(rs.next()){
                all = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            cerrarConexion();
        }
        return all;
    }

    @Override
    public List<TipoDocumento> ListarCondicion(String txt, int i, int c) throws NotAll {
        List<TipoDocumento> tdList = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        Connection con = conexion.conectar();
        try {
            String like = "%"+txt+"%";
            pr = con.prepareStatement("select * from vTipoDocumento where tipo like ? limit ?,?");
            pr.setString(1,like);
            pr.setInt(2, i);
            pr.setInt(3, c);
            rs = pr.executeQuery();
            if(rs.next()){
                tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                while(rs.next()){
                    tdList.add(new TipoDocumento(rs.getInt(1), rs.getString(2),rs.getString(3)));
                }
            }else{
                throw new NotAll("No se encontraron registros");
            }
        } catch (SQLException e) {
            throw new NotAll("No se encontraron registros");
        } finally{
            cerrarConexion();
        }
        return tdList;
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
