package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Producto;

public class ProductoBD {

    Connection connect;

    public ProductoBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getProductos").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getProductos").executeQuery(); //Para MySql
            while (rs.next()) {
                listaProductos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
                        rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),rs.getInt(11)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProductos;
    }

    
    public int getIDDeProductoDelCodigoTal(String codigo) {
        int idDeProducto=0;

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getIdProductoCodigo(?)"); //Para MySql
            
            ps.setString(1, codigo);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               idDeProducto=rs.getInt(1);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idDeProducto;
    }
    
            
            
    public void addProducto(String codigo, String descripcion, String marca, double costo, double precio,
            String presentacion, int stock, int stock_minimo,
            int categoria, int proveedor) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL addProducto(?,?,?,?,?,?,?,?,?,?)");
   
        statement.setString(1, codigo);
        statement.setString(2, descripcion);
        statement.setString(3, marca);
        statement.setDouble(4, costo);
        statement.setDouble(5, precio);
        statement.setString(6, presentacion);
        statement.setInt(7, stock);
        statement.setInt(8, stock_minimo);
        statement.setInt(9, categoria);
        statement.setInt(10, proveedor);
        
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
    }

    public void updateProducto(int id,String codigo, String descripcion, String marca, double costo, double precio,
            String presentacion, int stock, int stock_minimo,
            int categoria, int proveedor) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL updateProducto(?,?,?,?,?,?,?,?,?,?,?)");
        statement.setInt(1, id);
        statement.setString(2, codigo);
        statement.setString(3, descripcion);
        statement.setString(4, marca);
        statement.setDouble(5, costo);
        statement.setDouble(6, precio);
        statement.setString(7, presentacion);
        statement.setInt(8, stock);
        statement.setInt(9, stock_minimo);
        statement.setInt(10, categoria);
        statement.setInt(11, proveedor);
        
        System.out.println(statement);
        statement.execute();

        statement.close();
    }
    
    public void reducirExistencia(String codigo, int cantidad) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL reducirStockProducto(?,?)");
        
        statement.setString(1, codigo);
        statement.setInt(2, cantidad);
        
        System.out.println(statement);
        statement.execute();

        statement.close();
    }
    
    public void aumentarExistencia(String codigo, int cantidad) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL aumentarStockProducto(?,?)");
        
        statement.setString(1, codigo);
        statement.setInt(2, cantidad);
        
        System.out.println(statement);
        statement.execute();

        statement.close();
    }

    public void deleteProducto(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteProducto(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
        
        statement.close();
    }

    public ArrayList<Producto> getProductosFiltro1(String codigo, String descripcion, String marca, String costo, String precio,
            String presentacion, String stock, String stock_minimo,
            String categoria, String proveedor) {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaProducto1(?,?,?,?,?,?,?,?,?,?)"); //Para MySql

            ps.setString(1, codigo);
            ps.setString(2, descripcion);
            ps.setString(3, marca);
            ps.setString(4, costo);
            ps.setString(5, precio);
            ps.setString(6, presentacion);
            ps.setString(7, stock);
            ps.setString(8, stock_minimo);
            ps.setString(9, categoria);
            ps.setString(10, proveedor);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProductos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
                        rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),rs.getInt(11)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProductos;
    }
    
    public ArrayList<Producto> getProductosFiltro2(String id,String codigo, String descripcion, String marca, String costo, String precio,
            String presentacion, String stock, String stock_minimo,
            String categoria, String proveedor) {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaProducto2(?,?,?,?,?,?,?,?,?,?,?)"); //Para MySql
            
            ps.setString(1, id);
            ps.setString(2, codigo);
            ps.setString(3, descripcion);
            ps.setString(4, marca);
            ps.setString(5, costo);
            ps.setString(6, precio);
            ps.setString(7, presentacion);
            ps.setString(8, stock);
            ps.setString(9, stock_minimo);
            ps.setString(10, categoria);
            ps.setString(11, proveedor);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProductos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
                        rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),rs.getInt(11)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProductos;
    }
    
    
    public ArrayList<Producto> getProductosFiltro4(String codigo, String descripcion, String marca) {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaProducto4(?,?,?)"); //Para MySql
            
            ps.setString(1, codigo);
            ps.setString(2, descripcion);
            ps.setString(3, marca);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProductos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
                        rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),rs.getInt(11)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProductos;
    }
    
    
    public ArrayList<Producto> getProductosPorCodigo(String codigo) {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getProductosPorCodigo(?)"); //Para MySql
            
            ps.setString(1, codigo);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProductos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
                        rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),rs.getInt(11)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProductos;
    }
    
    
    
}
