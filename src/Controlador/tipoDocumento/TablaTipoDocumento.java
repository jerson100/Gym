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
    private boolean opc=false;
    private String txtS="";

    public boolean isOpc() {
        return opc;
    }

    public void setOpc(boolean opc) {
        this.opc = opc;
    }

    public String getTxtS() {
        return txtS;
    }

    public void setTxtS(String txtS) {
        this.txtS = txtS;
    }
    
    public TablaTipoDocumento(ICrud daoTipoDocumento) {
        this.daoTipoDocumento = daoTipoDocumento;
        
        int cantidadR = daoTipoDocumento.allTiposDocumentos();
        this.paginador = new JePagination<TipoDocumento>(cantidadR, 10) {
            @Override
            public void updateData() {
                if(!opc){
                  updateAll();    
                }else{
                    searchSensitve(txtS,false);
                }
                System.out.println("actualizando la informacion");
            }

            @Override
            public List<TipoDocumento> getDataBD(int paginaActual, int registrosXPagina) throws Exception {
                return listaTipoDocumento;
            }
        };

        //updateAll();
        
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
            ex.printStackTrace();
            listaTipoDocumento = new ArrayList<>();
        }

    }

    public JePagination<TipoDocumento> getPaginador() {
        return paginador;
    }

    public void setPaginador(JePagination<TipoDocumento> paginador) {
        this.paginador = paginador;
    }

    public void searchSensitve(String dat,boolean opc) {
        //paginador.setPaginaActual(0);
        if(opc){
         paginador.setPaginaActual(0);   
        }
        try {
            remove();
            int cantidaR = daoTipoDocumento.allTiposDocumentosSearch(dat);
            System.out.println("paginador: "+paginador);
            paginador.setCantidadRegistros(cantidaR);
            System.out.println("paginador: "+paginador);
            listaTipoDocumento = daoTipoDocumento.ListarCondicion(dat, paginador.getPaginaActual() * paginador.getRegistrosXPagina(),
                    paginador.getRegistrosXPagina());
            
        } catch (NotAll ex) {
            listaTipoDocumento = new ArrayList<>();
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
