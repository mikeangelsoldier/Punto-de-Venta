
package Modelo;

public class Sucursal {
    
    private int id_sucursal;
    private String nombre_sucursal;
    private String sucursal;
    private String telefono;
    private String correo;
    private String direccion;
    private String colonia;
    private String municipio;
    private String cp;
    private String estado;

    public Sucursal() {
        
    }

    public Sucursal(int id_sucursal, String nombre_sucursal,String sucursal, String telefono, String correo, String direccion, String colonia, String municipio, String cp, String estado) {
        this.id_sucursal = id_sucursal;
        this.nombre_sucursal = nombre_sucursal;
        this.sucursal = sucursal;        
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.colonia = colonia;
        this.municipio = municipio;
        this.cp = cp;
        this.estado = estado;
    }

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getNombre_sucursal() {
        return nombre_sucursal;
    }

    public void setNombre_sucursal(String nombre_sucursal) {
        this.nombre_sucursal = nombre_sucursal;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
