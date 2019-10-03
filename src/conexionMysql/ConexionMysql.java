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
            String html = "<html>"
                    + "<head>"
                    + "<style>"
                    + ".c{color:red;text-align:center;}"
                    + ".gmail{color:blue;text-decoration:underline;}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<h3 class='c'>"
                    + "No se pudo establecer la conexión con"
                    + "<br>"
                    + "la base de datos, contactarse con "
                    + "<span class='gmail'>juamkoo@gmail.com</span>"
                    + "<br>"
                    + "Disculpe las molestias, gracias."
                    + "</h3>"
                    + "</body>"
                    + "</html>";
            JOptionPane.showMessageDialog(null, html, "Error de conexión", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
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
                System.out.println("ee");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            
        }
    }

}
