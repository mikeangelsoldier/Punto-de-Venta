
package Modelo;

public class Producto {
    private int id;
    private String codigo;
    private String descripcion;
    private String marca;
    private double costo;
    private double precio;
    private String presentacion;
    private int stock;
    private int stock_minimo;
    private int categoria;
    private int proveedor;
    private String nombreCategoria;
    private String nombreProveedor;
    
    public Producto() {
        
    }

    public Producto(int id, String codigo, String descripcion, String marca, double costo, double precio, String presentacion, int stock, int stock_minimo, int categoria, int proveedor) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.costo = costo;
        this.precio = precio;
        this.presentacion = presentacion;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    //constructor sobrecargado
    public Producto(int id, String codigo, String descripcion, String marca, double costo, double precio, String presentacion, int stock, int stock_minimo, String nombreCategoria, String nombreProveedor) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.costo = costo;
        this.precio = precio;
        this.presentacion = presentacion;
        this.stock = stock;
        this.stock_minimo = stock_minimo;
        this.nombreCategoria = nombreCategoria;
        this.nombreProveedor = nombreProveedor;
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

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    
    
    
    
}
