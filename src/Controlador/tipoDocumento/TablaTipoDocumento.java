package Controlador.tipoDocumento;

import util.JeAbstractTable;
import interfaces.ICrud;
import modelo.entidad.TipoDocumento;

/**
 *
 * @author Jerson
 */
public class TablaTipoDocumento extends JeAbstractTable<TipoDocumento> {

    public TablaTipoDocumento(ICrud daoTipoDocumento) {
        super(daoTipoDocumento);
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
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
        TipoDocumento tipo = super.getList().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipo.getIdTipoDocumento();
            case 1:
                return tipo.getTipo();
            case 2:
                return tipo.getAbreviatura();
        }
        return null;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

}
