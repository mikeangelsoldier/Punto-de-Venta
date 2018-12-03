
package Modelo;


public class DetalleVenta {
    private int idDetalleVenta;
    private int idDVenta;
    private int idProducto;
    private String codigoProducto;
    private String descripcionProducto;
    
    private int cantidadProducto;
    private double importeProducto;

    public DetalleVenta() {
    }

    
    
    public DetalleVenta(int idDetalleVenta, int idDVenta, int idProducto, int cantidadProducto, double importeProducto) {
        this.idDetalleVenta = idDetalleVenta;
        this.idDVenta = idDVenta;
        this.idProducto = idProducto;
        this.cantidadProducto = cantidadProducto;
        this.importeProducto = importeProducto;
    }

    public DetalleVenta(int idDetalleVenta, int idDVenta, String codigoProducto, int cantidadProducto, double importeProducto) {
        this.idDetalleVenta = idDetalleVenta;
        this.idDVenta = idDVenta;
        this.codigoProducto = codigoProducto;
        this.cantidadProducto = cantidadProducto;
        this.importeProducto = importeProducto;
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getIdDVenta() {
        return idDVenta;
    }

    public void setIdDVenta(int idDVenta) {
        this.idDVenta = idDVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getImporteProducto() {
        return importeProducto;
    }

    public void setImporteProducto(double importeProducto) {
        this.importeProducto = importeProducto;
    }

    
    
    
    
    
    
    
    
}
