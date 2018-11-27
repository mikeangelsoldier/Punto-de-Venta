package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Sucursal;

public class SucursalBD {

    Connection connect;

    public SucursalBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Sucursal> getSucursales() {
        ArrayList<Sucursal> listaSucursales = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getUsuarios").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getSucursales").executeQuery(); //Para MySql
            while (rs.next()) {
                listaSucursales.add(new Sucursal(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),rs.getString(10)));

            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SucursalBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSucursales;
    }

    public void addSucursal(String nombre,String sucursal, String telefono, String correo, String direccion, String colonia,
            String municipio, String cp, String estado) throws SQLException {

        try (PreparedStatement statement = connect.prepareCall("CALL addSucursal(?,?,?,?,?,?,?,?,?)")) {
            statement.setString(1, nombre);
            statement.setString(2, sucursal);
            statement.setString(3, telefono);
            statement.setString(4, correo);
            statement.setString(5, direccion);
            statement.setString(6, colonia);
            statement.setString(7, municipio);
            statement.setString(8, cp);
            statement.setString(9, estado);
            
            System.out.println(statement.toString());
            statement.execute();
        }
    }

    public void updateSucursal(int id,String nombre,String sucursal, String telefono, String correo, String direccion, String colonia,
            String municipio, String cp, String estado) throws SQLException {
        try (PreparedStatement statement = connect.prepareCall("CALL updateSucursal(?,?,?,?,?,?,?,?,?,?)")) {
            statement.setInt(1, id);
            statement.setString(2, nombre);
            statement.setString(3, sucursal);
            statement.setString(4, telefono);
            statement.setString(5, correo);
            statement.setString(6, direccion);
            statement.setString(7, colonia);
            statement.setString(8, municipio);
            statement.setString(9, cp);
            statement.setString(10, estado);
            
            System.out.println(statement);
            statement.execute();
        }
    }

    public void deleteSucursal(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteSucursal(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
    }
    
    public ArrayList<Sucursal> getSucursalesFiltro1(String nombre,String sucursal, String telefono,
            String correo, String direccion, String colonia, String municipio, String cp, String estado) {
        ArrayList<Sucursal> listaSucursales = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement statement = connect.prepareStatement("CALL  getBusquedaSucursal1( ?,?, ?, ?, ?, ?, ? ,? ,?)"); //Para MySql

            statement.setString(1, nombre);
            statement.setString(2, sucursal);
            statement.setString(3, telefono);
            statement.setString(4, correo);
            statement.setString(5, direccion);
            statement.setString(6, colonia);
            statement.setString(7, municipio);
            statement.setString(8, cp);
            statement.setString(9, estado);

            System.out.println(statement);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                listaSucursales.add(new Sucursal(rs.getInt(1),rs.getString(2), 
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10)));
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaSucursales;
    }

    public ArrayList<Sucursal> getSucursalesFiltro2(int id,String nombre,String sucursal, String telefono, String correo, String direccion, String colonia,
            String municipio, String cp, String estado) {
        ArrayList<Sucursal> listaSucursales = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaProveedor").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaSucursal2(?,?,?,?,?,?,?,?,?,?)"); //Para MySql

            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, sucursal);
            ps.setString(4, telefono);
            ps.setString(5, correo);
            ps.setString(6, direccion);
            ps.setString(7, colonia);
            ps.setString(8, municipio);
            ps.setString(9, cp);
            ps.setString(10, estado);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaSucursales.add(new Sucursal(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                rs.getString(10)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SucursalBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaSucursales;
    }

}
