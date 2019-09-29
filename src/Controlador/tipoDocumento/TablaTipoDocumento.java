package Controlador.tipoDocumento;

import excepcion.NotAll;
import interfaces.ICrud;
import java.util.ArrayList;
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
        try {
            this.daoTipoDocumento = daoTipoDocumento;
            update();
        } catch (NotAll ex) {listaTipoDocumento = new ArrayList<>();}
    }
    
    public void remove(){
        listaTipoDocumento.clear();
    }
 
    public void update() throws NotAll {
        listaTipoDocumento = daoTipoDocumento.Listar();
    }
    
    public void removeData(TipoDocumento t){
        int index = listaTipoDocumento.indexOf(t);
        if(index!=-1){
            listaTipoDocumento.remove(t);
        }
    }
    
    @Override
    public int getRowCount() {
        return listaTipoDocumento.size();
    }

    @Override
    public int getColumnCount() {
       return 3;    
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "IdTipoDocumento";
            case 1:
                return "Tipo";
            case 2:
                return "Abreviatura";
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoDocumento tipo = listaTipoDocumento.get(rowIndex);
        switch(columnIndex){
            case 0:
                return tipo.getIdTipoDocumento();
            case 1:
                return tipo.getTipo();
            case 2:
                return tipo.getAbreviatura();
        }    
        return null;
    }

}
