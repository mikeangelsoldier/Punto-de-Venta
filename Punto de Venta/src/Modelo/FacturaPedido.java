/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author PaulAdrian
 */
public class FacturaPedido {
    
    private int folio_factura;
    private int id_proveedor;
    private int id_usuario;
    private String proveedor;
    private String usuario;
    private float monto;
    private Date fecha;
    
    public FacturaPedido() {
        
    }

    public FacturaPedido(int folio_factura, int id_proveedor, int id_usuario, float monto, Date fecha) {
        this.folio_factura = folio_factura;
        this.id_proveedor = id_proveedor;
        this.id_usuario = id_usuario;
        this.monto = monto;
        this.fecha = fecha;
    }
    
    public FacturaPedido(int folio_factura, String proveedor, String usuario, float monto, Date fecha) {
        this.folio_factura = folio_factura;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String Usuario) {
        this.usuario = Usuario;
    }
    

    public int getFolio_factura() {
        return folio_factura;
    }

    public void setFolio_factura(int folio_factura) {
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

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
    
}
