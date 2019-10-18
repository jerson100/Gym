package Controlador.tipoUsuario;

import interfaces.ICrud;
import modelo.entidad.TipoUsuario;
import util.JeAbstractTable;

/**
 *
 * @author Jerson
 */
public class TablaTipoUsuario extends JeAbstractTable<TipoUsuario>{

    public TablaTipoUsuario(ICrud dao) {
        super(dao);
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "IdTipoUsuario";
            case 1:
                return "Tipo";
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoUsuario tipo = super.getList().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipo.getIdTipoUsuario();
            case 1:
                return tipo.getTipo();
        }
        return null;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
}
