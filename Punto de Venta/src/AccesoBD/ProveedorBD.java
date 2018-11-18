package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Proveedor;

public class ProveedorBD {

    Connection connect;

    public ProveedorBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Proveedor> getProveedores() {
        ArrayList<Proveedor> listaProveedores = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getUsuarios").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getProveedores").executeQuery(); //Para MySql
            while (rs.next()) {
                listaProveedores.add(new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));

            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProveedores;
    }

    public void addProveedor(String nombre, String telefono, String correo, String direccion, String colonia,
            String municipio, String cp, String estado) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL addProveedor(?,?,?,?,?,?,?,?)");
        statement.setString(1, nombre);
        statement.setString(2, telefono);
        statement.setString(3, correo);
        statement.setString(4, direccion);
        statement.setString(5, colonia);
        statement.setString(6, municipio);
        statement.setString(7, cp);
        statement.setString(8, estado);

        System.out.println(statement.toString());
        statement.execute();

        statement.close();
    }

    public void updateProveedor(int id,String nombre, String telefono, String correo, String direccion, String colonia,
            String municipio, String cp, String estado) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL updateProveedor(?,?,?,?,?,?,?,?,?)");
        statement.setInt(1, id);
        statement.setString(2, nombre);
        statement.setString(3, telefono);
        statement.setString(4, correo);
        statement.setString(5, direccion);
        statement.setString(6, colonia);
        statement.setString(7, municipio);
        statement.setString(8, cp);
        statement.setString(9, estado);
        
        System.out.println(statement);
        statement.execute();

        statement.close();
    }

    public void deleteProveedor(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteProveedor(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
    }

    public ArrayList<Proveedor> getProveedoresFiltro(String id,String nombre, String telefono, String correo, String direccion, String colonia,
            String municipio, String cp, String estado) {
        ArrayList<Proveedor> listaProveedores = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaProveedor").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaProveedor(?,?,?,?,?,?,?,?,?)"); //Para MySql

            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, telefono);
            ps.setString(4, correo);
            ps.setString(5, direccion);
            ps.setString(6, colonia);
            ps.setString(7, municipio);
            ps.setString(8, cp);
            ps.setString(9, estado);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProveedores.add(new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProveedores;
    }

}
