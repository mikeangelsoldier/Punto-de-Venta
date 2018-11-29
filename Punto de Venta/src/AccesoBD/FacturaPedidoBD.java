/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoBD;

import Modelo.FacturaPedido;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PaulAdrian
 */
public class FacturaPedidoBD {
    
    Connection connect;
    
    public FacturaPedidoBD(Connection connect){
        this.connect = connect;
    }
    
    public ArrayList<FacturaPedido> getPedidos(){
        ArrayList<FacturaPedido> listaPedidos = new ArrayList<>();
        try {
            //ResultSet rs = connect.prepareCall("EXEC getUsuarios").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getPedidos").executeQuery(); //Para MySql
            while (rs.next()) {
                listaPedidos.add(new FacturaPedido(rs.getInt(1), rs.getInt(2), rs.getInt(3)
                       , rs.getFloat(4), rs.getDate(5)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return listaPedidos;
    }
    
    public void addPedido(int id_proveedor, int id_usuario,
            float monto, Date fecha) throws SQLException{
        PreparedStatement statement = connect.prepareCall("CALL addPedido(?, ?, ?, ?)");
        statement.setInt(1, id_proveedor);
        statement.setInt(2, id_usuario);
        statement.setFloat(3, monto);
        statement.setDate(4, fecha);
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
        
    }
    
    public void updatePedido(int folio_factura, int id_proveedor, int id_usuario,
            float monto, Date fecha) throws SQLException{
        PreparedStatement statement = connect.prepareCall("CALL updatePedido(?, ?, ?, ?, ?)");
        statement.setInt(1, folio_factura);
        statement.setInt(2, id_proveedor);
        statement.setInt(3, id_usuario);
        statement.setFloat(4, monto);
        statement.setDate(5, fecha);
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
        
    } 
    
    public void deletePedido(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deletePedido(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
    }
    
    public ArrayList<FacturaPedido> getPedidosFiltro1(String id_proveedor, String id_usuario, String monto, String fecha) {
        ArrayList<FacturaPedido> listaPedidos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario2").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaPedido1(?, ?, ?, ?)"); //Para MySql

            ps.setString(1, id_proveedor);
            ps.setString(2, id_usuario);
            ps.setString(3, monto);
            ps.setString(4, fecha);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaPedidos.add(new FacturaPedido(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4), rs.getDate(5)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaPedidos;
    }
    
    public ArrayList<FacturaPedido> getPedidosFiltro2(String folio_factura, String id_proveedor, 
            String id_usuario, String monto, String fecha) {
        ArrayList<FacturaPedido> listaPedidos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario2").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaPedido2(?, ?, ?, ?, ?)"); //Para MySql
            
            ps.setString(1, folio_factura);
            ps.setString(2, id_proveedor);
            ps.setString(3, id_usuario);
            ps.setString(4, monto);
            ps.setString(5, fecha);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaPedidos.add(new FacturaPedido(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4), rs.getDate(5)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaPedidos;
    }
    
    
}
