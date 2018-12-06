
package Modelo;

public class ReportePedido {
    
   
    private String folio_factura;
    private int id_proveedor;
    private int id_usuario;
    private float montoFactura;
    private int fecha;
    
    public ReportePedido() {
        
    }

    public ReportePedido(String folio_factura, int id_proveedor, int id_usuario, float montoFactura, int fecha) {
        this.folio_factura = folio_factura;
        this.id_proveedor = id_proveedor;
        this.id_usuario = id_usuario;
        this.montoFactura = montoFactura;
        this.fecha = fecha;
    }

    public String getFolio_factura() {
        return folio_factura;
    }

    public void setFolio_factura(String folio_factura) {
        this.folio_factura = folio_factura;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public float getMontoFactura() {
        return montoFactura;
    }

    public void setMontoFactura(float montoFactura) {
        this.montoFactura = montoFactura;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }
    
    

}
