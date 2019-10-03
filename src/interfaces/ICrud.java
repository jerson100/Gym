package interfaces;

import excepcion.NotAll;
import excepcion.NotCreate;
import excepcion.NotDelete;
import excepcion.NotRead;
import excepcion.NotUpdate;
import java.util.List;

/**
 *
 * @author Jerson
 */
public interface ICrud<T> {

    void crear(T obj) throws NotCreate;

    T buscar(int id) throws NotRead;

    void actualizar(T obj) throws NotUpdate;

    void eliminar(int id) throws NotDelete;

    List<T> Listar() throws NotAll;
    
    List<T> ListarCondicion(String txt) throws NotAll;
    int allTiposDocumentos();
    
    List<T> Listar(int i,int f) throws NotAll;
    
    int allTiposDocumentosSearch(String txt);
    
    List<T> ListarCondicion(String like,int i,int c) throws NotAll;
    
}
