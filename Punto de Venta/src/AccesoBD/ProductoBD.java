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
            //ResultSet rs = connect.prepareCall("EXEC getUsuarios").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getProductos").executeQuery(); //Para MySql
            while (rs.next()) {
                listaProductos.add(new Producto(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProductos;
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

    public void updateUsuario(String codigo, String descripcion, String marca, double costo, double precio,
            String presentacion, int stock, int stock_minimo,
            int categoria, int proveedor) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL updateProducto(?, ?, ?, ?, ?)");
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
        System.out.println(statement);
        statement.execute();

        statement.close();
    }

    public void deleteProducto(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteProducto(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
    }

    public ArrayList<Producto> getProductosFiltro(String codigo, String descripcion, String marca, double costo, double precio,
            String presentacion, int stock, int stock_minimo,
            int categoria, int proveedor) {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaProducto(?, ?, ? , ?, ? , ? , ? , ? , ? , ?)"); //Para MySql

            ps.setString(1, codigo);
            ps.setString(2, descripcion);
            ps.setString(3, marca);
            ps.setDouble(4, costo);
            ps.setDouble(5, precio);
            ps.setString(6, presentacion);
            ps.setInt(7, stock);
            ps.setInt(8, stock_minimo);
            ps.setInt(9, categoria);
            ps.setInt(10, proveedor);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProductos.add(new Producto(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProductos;
    }

}
