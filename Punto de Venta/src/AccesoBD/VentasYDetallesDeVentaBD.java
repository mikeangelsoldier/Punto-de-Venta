package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.VentasConDetallesVenta;

public class VentasYDetallesDeVentaBD {

    Connection connect;

    public VentasYDetallesDeVentaBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<VentasConDetallesVenta> getVentasYSusDetalles() {
        ArrayList<VentasConDetallesVenta> listaVentasConSusDetallesDeVenta = new ArrayList<>();

        try {
            ResultSet rs = connect.prepareCall("CALL getVentasYSusDetalles").executeQuery(); //Para MySql
            while (rs.next()) {
                listaVentasConSusDetallesDeVenta.add(new VentasConDetallesVenta(rs.getInt(1), rs.getString(2), 
                        rs.getDouble(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),
                        rs.getInt(10),rs.getInt(11),rs.getDouble(12)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentasYDetallesDeVentaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVentasConSusDetallesDeVenta;
    }
    
    

    public ArrayList<VentasConDetallesVenta> getVentasYSusDetallesDeVentaFiltro(String fechaInicio,String fechaFin,int id_usuario, int id_cliente) {
        ArrayList<VentasConDetallesVenta> listaVentasConSusDetallesDeVenta = new ArrayList<>();

        try {
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaVentaYSusDetallesDeVenta(?,?,?,?)"); //Para MySql

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ps.setInt(3, id_usuario);
            ps.setInt(4, id_cliente);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaVentasConSusDetallesDeVenta.add(new VentasConDetallesVenta(rs.getInt(1), rs.getString(2), 
                        rs.getDouble(3), rs.getDouble(4), rs.getDouble(5),
                        rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),
                        rs.getInt(10),rs.getInt(11),rs.getDouble(12)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentasYDetallesDeVentaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaVentasConSusDetallesDeVenta;
    }
    
    
    
}
