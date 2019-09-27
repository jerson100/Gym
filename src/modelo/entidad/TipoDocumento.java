package modelo.entidad;

/**
 *
 * @author Jerson
 */
public class TipoDocumento {
    private int idTipoDocumento;
    private String tipo;
    private byte estado;
    private String abreviatura;

    public TipoDocumento(int idTipoDocumento, String tipo, byte estado) {
        this.idTipoDocumento = idTipoDocumento;
        this.tipo = tipo;
        this.estado = estado;
    }

    public TipoDocumento(int idTipoDocumento, String tipo) {
        this.idTipoDocumento = idTipoDocumento;
        this.tipo = tipo;
    }

    public TipoDocumento(String tipo, byte estado) {
        this.tipo = tipo;
        this.estado = estado;
    }

    public TipoDocumento(int idTipoDocumento, String tipo, String abreviatura) {
        this.idTipoDocumento = idTipoDocumento;
        this.tipo = tipo;
        this.abreviatura = abreviatura;
    }
    
    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
    
    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "TipoDocumento{" + "idTipoDocumento=" + idTipoDocumento + ", tipo=" + tipo + ", estado=" + estado + '}';
    }
    
    
}
