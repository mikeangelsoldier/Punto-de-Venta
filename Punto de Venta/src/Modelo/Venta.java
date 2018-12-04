
package Modelo;


public class Venta {
    private int idVenta;
    private String fechaVenta;
    private Double subtotalVenta;
    private Double ivaVenta;
    private Double totalVenta;
    private String formaPagoVenta;
    private int idUsuario;
    private int idCliente;
    
    
    private String nombreUsuario;
    private String nombreCliente;

    public Venta() {
        
    }
    
    public Venta(int idVenta, String fechaVenta, Double subtotalVenta, Double ivaVenta, Double totalVenta, String formaPagoVenta, int idUsuario, int idCliente) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.subtotalVenta = subtotalVenta;
        this.ivaVenta = ivaVenta;
        this.totalVenta = totalVenta;
        this.formaPagoVenta = formaPagoVenta;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
    }

    public Venta(int idVenta, String fechaVenta, Double subtotalVenta, Double ivaVenta, Double totalVenta, String formaPagoVenta, String nombreUsuario, String nombreCliente) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.subtotalVenta = subtotalVenta;
        this.ivaVenta = ivaVenta;
        this.totalVenta = totalVenta;
        this.formaPagoVenta = formaPagoVenta;
        this.nombreUsuario = nombreUsuario;
        this.nombreCliente = nombreCliente;
    }

    public Venta(int idVenta, String fechaVenta, Double subtotalVenta, Double ivaVenta, Double totalVenta, String formaPagoVenta, int idUsuario, int idCliente, String nombreCliente) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.subtotalVenta = subtotalVenta;
        this.ivaVenta = ivaVenta;
        this.totalVenta = totalVenta;
        this.formaPagoVenta = formaPagoVenta;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    
    
    
            
}
