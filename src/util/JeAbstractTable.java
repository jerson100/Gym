package util;

import excepcion.NotAll;
import interfaces.ICrud;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerson
 * @param <T>
 */
public abstract class JeAbstractTable<T> extends AbstractTableModel {
    private ICrud dao;
    private List<T> list;
    private JePagination<T> paginator;
    private boolean activeSearch = false;
    private String textPreviousSearch = "";

    public JeAbstractTable(ICrud dao) {
        this.dao = dao;
        int cantidadR = dao.cantidadRegistros();
        this.paginator = new JePagination<T>(cantidadR, 10) {
            @Override
            public void updateData() {
                if (!activeSearch) {
                    updateAll();
                } else {
                    searchSensitve(textPreviousSearch, false);
                }
            }
            @Override
            public List<T> getDataBD(int paginaActual, int registrosXPagina) throws Exception {
                return null;
            }
        };
    }

    private void remove() {
        list.clear();
        list = null;
    }

    public void updateAll() {
        try {
            int cantidadR = dao.cantidadRegistros();//obtenemos la cantidad de registros en la bd
            paginator.setCantidadRegistros(cantidadR);
            list = dao.listar(paginator.getPaginaActual() * paginator.getRegistrosXPagina(),
                    paginator.getRegistrosXPagina());
        } catch (NotAll ex) {
            list = new ArrayList<>();
        }
    }
    
    public void restablecerData(){
        remove();
        paginator.setPaginaActual(0);
        updateAll();
    }

    /**
     * Método que permite buscar coincidencias de una secuencias de carácteres en la base de datos
     * @param dat La cadena de carácteres a buscar.
     * @param activekeyReleased <br>
     * <strong style="color: red;">true:</strong> Esto permitirá identificar si el usuario está soltando la tecla
     * del txt para poder restablecer la página actual a 0.<br>
     * <strong style="color: red;">false:</strong> Si se establece como falso entonces la página actual no se reestablecerá a 0
     * esto es debido a que el usuario puede estar pulsando el botón next o prev y por ende se debe modificar la pagina
     * mas no restablecerla.
    **/
    public void searchSensitve(String dat, boolean activekeyReleased) {
        if (activekeyReleased) {
            paginator.setPaginaActual(0);
        }
        try {
            remove();
            int cantidaR = dao.cantidadRegistrosCondicion(dat);
            //System.out.println("paginator: " + paginator);
            paginator.setCantidadRegistros(cantidaR);
            //System.out.println("paginator: " + paginator);
            list = dao.ListarCondicion(dat, paginator.getPaginaActual() * paginator.getRegistrosXPagina(),
                    paginator.getRegistrosXPagina());
        } catch (NotAll ex) {
            list = new ArrayList<>();
        }
    }

    /**
     *  Este método nos permite obtener la cantidad de columnas de la tabla
     *  @return Int 
     **/
    
    @Override
    public abstract int getColumnCount();
    
    

    public boolean isActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(boolean activeSearch) {
        this.activeSearch = activeSearch;
    }

    public String getTextPreviousSearch() {
        return textPreviousSearch;
    }

    public void setTextPreviousSearch(String textPreviousSearch) {
        this.textPreviousSearch = textPreviousSearch;
    }

    public JePagination<T> getPaginator() {
        return paginator;
    }

    public void setPaginator(JePagination<T> paginator) {
        this.paginator = paginator;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    

}
