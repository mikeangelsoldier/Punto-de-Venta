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
public class ReportePedidoBD {
    
    Connection connect;
    
    public ReportePedidoBD(Connection connect){
        this.connect = connect;
    }
    
    public ArrayList<FacturaPedido> getPedidos(){
        ArrayList<FacturaPedido> listaPedidos = new ArrayList<>();
        try {
            //ResultSet rs = connect.prepareCall("EXEC getUsuarios").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getPedidos").executeQuery(); //Para MySql
            while (rs.next()) {
                listaPedidos.add(new FacturaPedido(rs.getString(1), rs.getInt(2), rs.getInt(3)
                       , rs.getFloat(4), rs.getDate(5)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return listaPedidos;
    }
    public ArrayList<FacturaPedido> getReportePedidos(String fechaDesde, String fechaHasta) {
        ArrayList<FacturaPedido> listaPedidos = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario2").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  geReportePedidos(?, ?)"); //Para MySql
            
            ps.setString(1, fechaDesde);
            ps.setString(2, fechaHasta);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaPedidos.add(new FacturaPedido(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4), rs.getDate(5)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaPedidos;
    }
    
    
}
