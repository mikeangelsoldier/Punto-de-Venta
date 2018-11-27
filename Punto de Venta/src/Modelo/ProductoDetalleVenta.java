/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

public class ProductoDetalleVenta {
    private int id;
    private String codigo;
    private String descripcion;
    private String marca;
    private double costo;
    private double precio;
    private String presentacion;
    private int stock;
    private int stock_minimo;
    private int cantidadProductoDetalleVenta;
    private double precioUnitarioProductoDetalleVenta;
    private double totalPorProductoDetalleVenta;
    
    public ProductoDetalleVenta() {
        
    }

    public ProductoDetalleVenta(int id, String codigo, String descripcion, String marca, double costo, double precio, String presentacion, int stock, int stock_minimo) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.costo = costo;
        this.precio = precio;
        this.presentacion = presentacion;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
    }

    public ProductoDetalleVenta(int id, String codigo, String descripcion, String marca, double costo, double precio, String presentacion, int stock, int stock_minimo, int cantidadProductoDetalleVenta, double precioUnitarioProductoDetalleVenta, double totalPorProductoDetalleVenta) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.costo = costo;
        this.precio = precio;
        this.presentacion = presentacion;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
        this.cantidadProductoDetalleVenta = cantidadProductoDetalleVenta;
        this.precioUnitarioProductoDetalleVenta = precioUnitarioProductoDetalleVenta;
        this.totalPorProductoDetalleVenta = totalPorProductoDetalleVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public int getCantidadProductoDetalleVenta() {
        return cantidadProductoDetalleVenta;
    }

    public void setCantidadProductoDetalleVenta(int cantidadProductoDetalleVenta) {
        this.cantidadProductoDetalleVenta = cantidadProductoDetalleVenta;
    }

    public double getPrecioUnitarioProductoDetalleVenta() {
        return precioUnitarioProductoDetalleVenta;
    }

    public void setPrecioUnitarioProductoDetalleVenta(double precioUnitarioProductoDetalleVenta) {
        this.precioUnitarioProductoDetalleVenta = precioUnitarioProductoDetalleVenta;
    }

    public double getTotalPorProductoDetalleVenta() {
        return totalPorProductoDetalleVenta;
    }

    public void setTotalPorProductoDetalleVenta(double totalPorProductoDetalleVenta) {
        this.totalPorProductoDetalleVenta = totalPorProductoDetalleVenta;
    }

    
    
    
}
