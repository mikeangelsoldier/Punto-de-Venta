package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.DetalleVenta;

public class DetallleVentaBD {

    Connection connect;

    public DetallleVentaBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<DetalleVenta> getDetallesDeVenta() {
        ArrayList<DetalleVenta> listaDetallesDeVenta = new ArrayList<>();

        try {
            ResultSet rs = connect.prepareCall("CALL getDetallesVenta").executeQuery(); //Para MySql
            while (rs.next()) {
                listaDetallesDeVenta.add(new DetalleVenta(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DetallleVentaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDetallesDeVenta;
    }
    
    

    public void addDetalleDeVenta(int id_venta , int id_producto, int cantidad, int importe) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL addDetalleVenta(?,?,?,?)");
   
        statement.setInt(1, id_venta);
        statement.setInt(2, id_producto);
        statement.setInt(3, cantidad);
        statement.setDouble(4, importe);
        
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
    }
    
    public ArrayList<DetalleVenta> getDetallesDeUnaSolaVenta(int idVenta) {
        ArrayList<DetalleVenta> listaDetallesVentaDeUnaSolaVenta = new ArrayList<>();

        try {
            PreparedStatement ps = connect.prepareStatement("CALL  getDetallesDeUnaVenta(?)"); //Para MySql

            ps.setInt(1, idVenta);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaDetallesVentaDeUnaSolaVenta.add(new DetalleVenta(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaDetallesVentaDeUnaSolaVenta;
    }
}
