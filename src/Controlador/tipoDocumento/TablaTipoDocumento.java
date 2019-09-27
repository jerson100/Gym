package Controlador.tipoDocumento;

import excepcion.NotAll;
import interfaces.ICrud;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.entidad.TipoDocumento;

/**
 *
 * @author Jerson
 */
public class TablaTipoDocumento extends AbstractTableModel {

    private ICrud daoTipoDocumento;
    private List<TipoDocumento> listaTipoDocumento;

    public TablaTipoDocumento(ICrud daoTipoDocumento) {
        this.daoTipoDocumento = daoTipoDocumento;
    }

    public void update() throws NotAll {
        listaTipoDocumento = daoTipoDocumento.Listar();
    }
    
    @Override
    public int getRowCount() {
        return listaTipoDocumento.size();
    }

    @Override
    public int getColumnCount() {
       return 2;    
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 1:
                return "IdTipoDocumento";
            case 2:
                return "Tipo";
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoDocumento tipo = listaTipoDocumento.get(rowIndex);
        switch(columnIndex){
            case 1:
                return tipo.getIdTipoDocumento();
            case 2:
                return tipo.getTipo();
        }    
        return null;
    }

}
