/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoBD;

import Modelo.Cliente;
import java.sql.Connection;
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
public class ClienteBD {
    
    Connection connect;
    
    public ClienteBD(Connection connect){
       this.connect = connect; 
    }
    
    public ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            //ResultSet rs = connect.prepareCall("EXEC getUsuarios").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getClientes").executeQuery(); //Para MySql
            while (rs.next()) {
                listaClientes.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3)
                       , rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                rs.getString(8), rs.getString(9),rs.getString(10), rs.getString(11)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return listaClientes;
    }
    
    public void addCliente(String nombre, String rfc, String telefono,
            String correo, String direccion, String colonia, String municipio, String cp, String estado) throws SQLException{
        PreparedStatement statement = connect.prepareCall("CALL addCliente(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, nombre);
        statement.setString(2, rfc);
        statement.setString(3, telefono);
        statement.setString(4, correo);
        statement.setString(5, direccion);
        statement.setString(6, colonia);
        statement.setString(7, municipio);
        statement.setString(8, cp);
        statement.setString(9, estado);
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
        
    }
    
    public void updateCliente(int id, String nombre, String rfc, String telefono,
            String correo, String direccion, String colonia, String municipio, String cp, String estado) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL updateCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, nombre);
        statement.setString(3, rfc);
        statement.setString(4, telefono);
        statement.setString(5, correo);
        statement.setString(6, direccion);
        statement.setString(7, colonia);
        statement.setString(8, municipio);
        statement.setString(9, cp);
        statement.setString(10, estado);
        System.out.println(statement);
        statement.execute();

        statement.close();
    }
    
    public void deleteCliente(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteCliente(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
    }
    
    public ArrayList<Cliente> getClientesFiltro1(String nombre, String rfc, String telefono,
            String correo, String direccion, String colonia, String municipio, String cp, String estado) {
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement statement = connect.prepareStatement("CALL  getBusquedaCliente1(?, ?, ?, ?, ?, ?, ? ,? ,?)"); //Para MySql

            statement.setString(1, nombre);
            statement.setString(2, rfc);
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
                listaClientes.add(new Cliente(rs.getInt(1),rs.getString(2), rs.getString(3), 
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                rs.getString(9), rs.getString(10),rs.getString(11)));
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaClientes;
    }
    
    
   public ArrayList<Cliente> getClientesFiltro2(int id, String nombre, String rfc, String telefono,
            String correo, String direccion, String colonia, String municipio, String cp, String estado) {
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement statement = connect.prepareStatement("CALL  getBusquedaCliente2(? ,?, ?, ?, ?, ?, ?, ? ,? ,?)"); //Para MySql
            
            statement.setInt(1,id);
            statement.setString(2, nombre);
            statement.setString(3, rfc);
            statement.setString(4, telefono);
            statement.setString(5, correo);
            statement.setString(6, direccion);
            statement.setString(7, colonia);
            statement.setString(8, municipio);
            statement.setString(9, cp);
            statement.setString(10, estado);

            System.out.println(statement);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                listaClientes.add(new Cliente(rs.getInt(1),rs.getString(2), rs.getString(3), 
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                rs.getString(9), rs.getString(10),rs.getString(11)));
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaClientes;
    } 
    
    
    
}
