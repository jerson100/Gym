package conexionMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import interfaces.IConexionAbstract;

/**
 *
 * @author Jerson
 */
public class ConexionMysql implements IConexionAbstract {

    private Connection conn;
    private static ConexionMysql instance;
    private static final String URL = "jdbc:mysql://localhost:3306/gym";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASS = "";

    private ConexionMysql() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static ConexionMysql GetInstance() {
        if (instance == null) {
            instance = new ConexionMysql();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    @Override
    public Connection conectar() {
        return conn;
    }

    @Override
    public void cerrar() {
        try {
            if(!conn.isClosed()){
                conn.close();
                conn = null;
                instance = null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

}
