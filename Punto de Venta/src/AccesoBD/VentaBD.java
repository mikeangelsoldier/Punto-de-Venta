package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Venta;

public class VentaBD {

    Connection connect;

    public VentaBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Venta> getVentas() {
        ArrayList<Venta> listaVentas = new ArrayList<>();

        try {
            ResultSet rs = connect.prepareCall("CALL getVentas").executeQuery(); //Para MySql
            while (rs.next()) {
                listaVentas.add(new Venta(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVentas;
    }
    
    public int getIdMayorDeUltimaVenta() {
        int idDeUltimaVenta = 0;

        try {
            ResultSet rs = connect.prepareCall("CALL getIdMayorDeUltimaVenta").executeQuery(); //Para MySql
            while (rs.next()) {
              idDeUltimaVenta=rs.getInt(1);
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idDeUltimaVenta;
    }

    public void addVenta(String fecha_venta,double subtotal_venta, double iva_venta,double total_venta,
            String forma_pago,int id_usuario, int id_cliente) throws SQLException {

        try (PreparedStatement statement = connect.prepareCall("CALL addVenta(?,?,?,?,?,?,?)")) {
            statement.setString(1, fecha_venta);
            statement.setDouble(2, subtotal_venta);
            statement.setDouble(3, iva_venta);
            statement.setDouble(4, total_venta);
            statement.setString(5, forma_pago);
            statement.setInt(6, id_usuario);
            statement.setInt(7, id_cliente);
            
            System.out.println(statement.toString());
            statement.execute();
        }
    }

    
    public void deleteVentasYDetalles(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteVentaYSusDetalles(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
        
        statement.close();
    }
    
    

    public ArrayList<Venta> getVentasFiltro(String fechaInicio,String fechaFin,int id_usuario, int id_cliente) {
        ArrayList<Venta> listaVentas = new ArrayList<>();

        try {
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaVenta(?,?,?,?)"); //Para MySql

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ps.setInt(3, id_usuario);
            ps.setInt(4, id_cliente);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaVentas.add(new Venta(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaVentas;
    }
    
    
    
}
