package util;

import java.util.List;

/**
 *
 * @author Jerson Ramírez Ortiz
 * @param <T>
 */
public abstract class JePagination<T>{
    private int paginaActual;
    private int cantidadRegistros;
    private int registrosXPagina;
    private int cantidadPaginas;
    
    public JePagination(int cantidadRegistros,int registrosXPagina){
        this.cantidadRegistros = cantidadRegistros;
        this.registrosXPagina = registrosXPagina;
        setCantidadPaginas();
    }
    
    private void setCantidadPaginas() {
        this.cantidadPaginas = cantidadRegistros/registrosXPagina + 
                ((cantidadRegistros % registrosXPagina>0)?1:0);
    }
    
    public int getCantidadPaginas(){
        return cantidadPaginas;
    }

    public int getRegistrosXPagina() {
        return registrosXPagina;
    }

    public void setRegistrosXPagina(int registrosXPagina) {
        this.registrosXPagina = registrosXPagina;
        setCantidadRegistros(cantidadRegistros);
        updateData();
    }
    
    public void next(){
       if(cantidadPaginas-1>paginaActual)paginaActual++;
       updateData();
    }
    
    public void prev(){
       if(paginaActual>0)paginaActual--;
       updateData();
    }

    public int getPaginaActual() {
        return paginaActual;
    }
    
    public void setPaginaActual(int pageActual){
        this.paginaActual = pageActual;
    }
    
    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
        //this.paginaActual = 0;
        setCantidadPaginas();
        //updateData();
    }

    /**
       Método que se debe de sobreescribir, en la cuál tiene que implementar la lógica 
       necesaria para su negocio, cuando se llama al método next(),prev(),setCantidadRegistros(),setRegistrosXPagina
       tambien se ejecutará este método  
    **/
    public abstract void updateData();
    public abstract List<T> getDataBD(int paginaActual,int registrosXPagina)throws Exception;
    
    @Override
    public String toString() {
        return (paginaActual+1)+"/"+cantidadPaginas;
    }
    
}
