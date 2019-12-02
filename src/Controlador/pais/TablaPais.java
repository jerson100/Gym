package Controlador.pais;

import interfaces.ICrud;
import modelo.entidad.Pais;
import util.JeAbstractTable;

/**
 *
 * @author Jerson
 */
public class TablaPais extends JeAbstractTable<Pais> {

    public TablaPais(ICrud dao) {
        super(dao);
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "Id País";
            case 1:
                return "País";
        }
        return "";
    }

    
    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pais p = this.getList().get(rowIndex);
        switch(columnIndex){
            case 0:
                return  p.getIdPais();
            case 1:
                return p.getNombre();
            
        }
        return null;
    }
    
}
