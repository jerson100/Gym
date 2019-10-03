package Controlador.tipoDocumento;

import excepcion.NotAll;
import interfaces.ICrud;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.entidad.TipoDocumento;
import util.JePagination;

/**
 *
 * @author Jerson
 */
public class TablaTipoDocumento extends AbstractTableModel {

    private ICrud daoTipoDocumento;
    private List<TipoDocumento> listaTipoDocumento;
    private JePagination<TipoDocumento> paginador;
    private boolean activoBuscador = false;
    private String textoPrevioABuscar = "";

    public TablaTipoDocumento(ICrud daoTipoDocumento) {
        this.daoTipoDocumento = daoTipoDocumento;

        int cantidadR = daoTipoDocumento.allTiposDocumentos();
        this.paginador = new JePagination<TipoDocumento>(cantidadR, 10) {
            @Override
            public void updateData() {
                if (!activoBuscador) {
                    updateAll();
                } else {
                    searchSensitve(textoPrevioABuscar, false);
                }
            }

            @Override
            public List<TipoDocumento> getDataBD(int paginaActual, int registrosXPagina) throws Exception {
                return null;
            }
        };
    }

    public void remove() {
        listaTipoDocumento.clear();
        listaTipoDocumento = null;
    }

    public void updateAll() {
        try {
            int cantidadR = daoTipoDocumento.allTiposDocumentos();//obtenemos la cantidad de registros en la bd
            paginador.setCantidadRegistros(cantidadR);
            listaTipoDocumento = daoTipoDocumento.Listar(paginador.getPaginaActual() * paginador.getRegistrosXPagina(),
                    paginador.getRegistrosXPagina());
        } catch (NotAll ex) {
            listaTipoDocumento = new ArrayList<>();
        }
    }

    public void searchSensitve(String dat, boolean activekeyReleased) {
        if (activekeyReleased) {
            paginador.setPaginaActual(0);
        }
        try {
            remove();
            int cantidaR = daoTipoDocumento.allTiposDocumentosSearch(dat);
            System.out.println("paginador: " + paginador);
            paginador.setCantidadRegistros(cantidaR);
            System.out.println("paginador: " + paginador);
            listaTipoDocumento = daoTipoDocumento.ListarCondicion(dat, paginador.getPaginaActual() * paginador.getRegistrosXPagina(),
                    paginador.getRegistrosXPagina());
        } catch (NotAll ex) {
            listaTipoDocumento = new ArrayList<>();
        }
    }

    public boolean isActivoBuscador() {
        return activoBuscador;
    }

    public void setActivoBuscador(boolean activoBuscador) {
        this.activoBuscador = activoBuscador;
    }

    public String getTextoPrevioABuscar() {
        return textoPrevioABuscar;
    }

    public void setTextoPrevioABuscar(String textoPrevioABuscar) {
        this.textoPrevioABuscar = textoPrevioABuscar;
    }

    public JePagination<TipoDocumento> getPaginador() {
        return paginador;
    }

    public void setPaginador(JePagination<TipoDocumento> paginador) {
        this.paginador = paginador;
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
        TipoDocumento tipo = listaTipoDocumento.get(rowIndex);
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

}
