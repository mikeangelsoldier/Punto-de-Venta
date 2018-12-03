
package Modelo;


public class VentasConDetallesVenta {
    private int idVenta;
    private String fechaVenta;
    private Double subtotalVenta;
    private Double ivaVenta;
    private Double totalVenta;
    private String formaPagoVenta;
    private int idUsuario;
    private int idCliente;
    private int idDetalleVenta;
    private int idProducto;
    private String codigoProducto;
    private String descripcionProducto;
    
    private int cantidadProducto;
    private double importeProducto;

    public VentasConDetallesVenta() {
    }

    public VentasConDetallesVenta(int idVenta, String fechaVenta, Double subtotalVenta, Double ivaVenta, Double totalVenta, String formaPagoVenta, int idUsuario, int idCliente, int idDetalleVenta, int idProducto, int cantidadProducto, double importeProducto) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.subtotalVenta = subtotalVenta;
        this.ivaVenta = ivaVenta;
        this.totalVenta = totalVenta;
        this.formaPagoVenta = formaPagoVenta;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.idDetalleVenta = idDetalleVenta;
        this.idProducto = idProducto;
        this.cantidadProducto = cantidadProducto;
        this.importeProducto = importeProducto;
    }

    public VentasConDetallesVenta(int idVenta, String fechaVenta, Double subtotalVenta, Double ivaVenta, Double totalVenta, String formaPagoVenta, int idUsuario, int idCliente, int idDetalleVenta, String codigoProducto, int cantidadProducto, double importeProducto) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.subtotalVenta = subtotalVenta;
        this.ivaVenta = ivaVenta;
        this.totalVenta = totalVenta;
        this.formaPagoVenta = formaPagoVenta;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.idDetalleVenta = idDetalleVenta;
        this.codigoProducto = codigoProducto;
        this.cantidadProducto = cantidadProducto;
        this.importeProducto = importeProducto;
    }

    public VentasConDetallesVenta(int idVenta, String fechaVenta, Double subtotalVenta, Double ivaVenta, Double totalVenta, String formaPagoVenta, int idUsuario, int idCliente, int idDetalleVenta, String codigoProducto, String descripcionProducto, int cantidadProducto, double importeProducto) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.subtotalVenta = subtotalVenta;
        this.ivaVenta = ivaVenta;
        this.totalVenta = totalVenta;
        this.formaPagoVenta = formaPagoVenta;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.idDetalleVenta = idDetalleVenta;
        this.codigoProducto = codigoProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadProducto = cantidadProducto;
        this.importeProducto = importeProducto;
    }
    
    

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getSubtotalVenta() {
        return subtotalVenta;
    }

    public void setSubtotalVenta(Double subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }

    public Double getIvaVenta() {
        return ivaVenta;
    }

    public void setIvaVenta(Double ivaVenta) {
        this.ivaVenta = ivaVenta;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getFormaPagoVenta() {
        return formaPagoVenta;
    }

    public void setFormaPagoVenta(String formaPagoVenta) {
        this.formaPagoVenta = formaPagoVenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
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
