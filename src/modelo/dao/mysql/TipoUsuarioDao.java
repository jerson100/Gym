package modelo.dao.mysql;

import conexionMysql.ConexionMysql;
import excepcion.NotAll;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotRead;
import excepcion.NotUpdate;
import interfaces.ITipoUsuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.entidad.TipoUsuario;

/**
 *
 * @author Jerson
 */
public class TipoUsuarioDao implements ITipoUsuario {

    private ConexionMysql conexion;
    private ResultSet rs;
    private PreparedStatement pr;
    private CallableStatement ca;
    private Connection con;

    @Override
    public void crear(TipoUsuario obj) throws NotCreate {
        try {
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            ca = con.prepareCall("call sp_insert_tipoUsuario(?)");
            ca.setString(1, obj.getTipo());
            
            if(ca.executeUpdate()==0){
                throw new NotCreate("No se pudo crear el tipo de usuario");
            }
        } catch (SQLException ex) {
            throw new NotCreate("No se pudo crear el tipo de usuario");
        } finally{
            cerrarConexion();
        }
    }

    @Override
    public TipoUsuario buscar(int id) throws NotRead {
        TipoUsuario t;
        try {
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            pr = con.prepareStatement("select * from vtipousuario where idTipoUsuario = ?");
            pr.setInt(1, id);
            rs = pr.executeQuery();
            if(rs.next()){
                t = new TipoUsuario();
                t.setIdTipoUsuario(rs.getInt(1));
                t.setTipo(rs.getString(2));
            }else{
                throw new NotRead("No se encontró el tipo de usuario");
            }
        } catch (SQLException ex) {
            throw new NotRead("No se encontró el tipo de usuario");
        } finally{
            cerrarConexion();
        }
        return t;
    }

    @Override
    public void actualizar(TipoUsuario obj) throws NotUpdate {
        try {
            conexion = ConexionMysql.GetInstance();
            con = conexion.conectar();
            ca = con.prepareCall("call sp_update_tipoUsuario(?,?)");
            ca.setInt(1, obj.getIdTipoUsuario());
            ca.setString(2, obj.getTipo());
            if(ca.executeUpdate()==0){
                throw new NotUpdate("No se pudo actualizar el tipo de usuario especificado");
            }
        } catch (SQLException ex) {
            throw new NotUpdate("No se pudo actualizar el tipo de usuario especificado");
        } finally{
            cerrarConexion();
        }
    }

    @Override
    public void eliminar(int id) throws NotDelete {
        conexion = ConexionMysql.GetInstance();
        con = conexion.conectar();
        try {
            ca = con.prepareCall("call sp_delete_tipoUsuario(?)");
            ca.setInt(1, id);
            if (ca.executeUpdate() == 0) {
                throw new NotDelete("No se pudo eliminar el tipo de usuario");
            }
        } catch (SQLException e) {
            throw new NotDelete("No se pudo eliminar el tipo de usuario: "+e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public List<TipoUsuario> listar() throws NotAll {
        List<TipoUsuario> tdList = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        con = conexion.conectar();
        try {
            pr = con.prepareStatement("select * from vtipoUsuario");
            rs = pr.executeQuery();
            if(rs.next()){
                TipoUsuario t = new TipoUsuario();
                t.setIdTipoUsuario(rs.getInt(1));
                t.setTipo(rs.getString(2));
                tdList.add(t);
                while(rs.next()){
                    t = new TipoUsuario();
                    t.setIdTipoUsuario(rs.getInt(1));
                    t.setTipo(rs.getString(2));
                    tdList.add(t);
                    tdList.add(t);
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
    public List<TipoUsuario> ListarCondicion(String txt) throws NotAll {
        List<TipoUsuario> tdList = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        con = conexion.conectar();
        try {
            String like = "%"+txt+"%";
            pr = con.prepareStatement("select * from vtipoUsuario where tipo like ?");
            pr.setString(1, like);
            rs = pr.executeQuery();
            if(rs.next()){
                TipoUsuario t = new TipoUsuario();
                t.setIdTipoUsuario(rs.getInt(1));
                t.setTipo(rs.getString(2));
                tdList.add(t);
                while(rs.next()){
                    t = new TipoUsuario();
                    t.setIdTipoUsuario(rs.getInt(1));
                    t.setTipo(rs.getString(2));
                    tdList.add(t);
                    tdList.add(t);
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
    public int cantidadRegistros() {
        int all = 0;
        conexion = ConexionMysql.GetInstance();
        con = conexion.conectar();
        try {
            pr = con.prepareStatement("select count(*) from vtipousuario");
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
    public List<TipoUsuario> listar(int i, int f) throws NotAll {
        List<TipoUsuario> tdList = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        con = conexion.conectar();
        try {
            pr = con.prepareStatement("select * from vtipousuario limit ?,?");
            pr.setInt(1, i);
            pr.setInt(2, f);
            rs = pr.executeQuery();
            if(rs.next()){
                TipoUsuario t = new TipoUsuario();
                t.setIdTipoUsuario(rs.getInt(1));
                t.setTipo(rs.getString(2));
                tdList.add(t);
                while(rs.next()){
                    t = new TipoUsuario();
                    t.setIdTipoUsuario(rs.getInt(1));
                    t.setTipo(rs.getString(2));
                    tdList.add(t);
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
        con = conexion.conectar();
        String like = "%"+txt+"%";
        try {
            pr = con.prepareStatement("select count(*) from vtipoUsuario where tipo like ?");
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
    public List<TipoUsuario> ListarCondicion(String like, int i, int c) throws NotAll {
        List<TipoUsuario> lista = new ArrayList<>();
        conexion = ConexionMysql.GetInstance();
        con = conexion.conectar();
        try {
            String l = "%" + like + "%";
            pr = con.prepareStatement("select * from vtipoUsuario where tipo like ? limit ?,?");
            pr.setString(1, l);
            pr.setInt(2, i);
            pr.setInt(3, c);
            rs = pr.executeQuery();
            if (rs.next()) {
                TipoUsuario t = new TipoUsuario();
                t.setIdTipoUsuario(rs.getInt(1));
                t.setTipo(rs.getString(2));
                lista.add(t);
                while (rs.next()) {
                    t = new TipoUsuario();
                    t.setIdTipoUsuario(rs.getInt(1));
                    t.setTipo(rs.getString(2));
                    lista.add(t);
                }
            } else {
                throw new NotAll("No se encontraron registros");
            }
        } catch (SQLException e) {
            throw new NotAll("No se encontraron registros");
        } finally {
            cerrarConexion();
        }
        return lista;
    }

    private void cerrarConexion() {
        conexion.cerrar();
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
            }
        }
        if (pr != null) {
            try {
                pr.close();
                pr = null;
            } catch (SQLException ex) {
            }
        }
        if (ca != null) {
            try {
                ca.close();
                ca = null;
            } catch (SQLException ex) {
            }
        }
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {
            }
        }
    }

}
