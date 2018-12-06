package AccesoBD;

import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.ReportePedido;

public class ReporteFaltantesBD {

    Connection connect;

    public ReporteFaltantesBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> listaProductosFaltantes = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getProductosFaltantes(?,?,?)"); //Para MySql
            
            ps.setString(1, "");
            ps.setString(2, "");
            ps.setString(3, "");

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProductosFaltantes.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
                        rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),rs.getInt(11)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProductosFaltantes;
    
    }        
    
    public ArrayList<Producto> getFaltantes(String marca, String categoria, String proveedor) {
        ArrayList<Producto> listaProductosFaltantes = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getProductosFaltantes(?,?,?)"); //Para MySql
            
            ps.setString(1, marca);
            ps.setString(2, categoria);
            ps.setString(3, proveedor);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaProductosFaltantes.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
                        rs.getDouble(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),rs.getInt(11)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaProductosFaltantes;
    }
}
