package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Categoria;

public class CategoriaBD {

    Connection connect;

    public CategoriaBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Categoria> getCategorias() {
        ArrayList<Categoria> listaCategorias = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getCategorias").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getCategorias").executeQuery(); //Para MySql
            while (rs.next()) {
                listaCategorias.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaCategorias;
    }

    public void addCategoria(String nombre, String descripcion) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL addCategoria(?, ?)");
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
    }

    public void updateCategoria(int id, String nombre, String descripcion) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL updateCategoria(?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, nombre);
        statement.setString(3, descripcion);
        
        System.out.println(statement);
        statement.execute();

        statement.close();
    }

    public void deleteCategoria(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteCategoria(?)");
        statement.setInt(1, ID);
        
        System.out.println(statement);
        statement.execute();
        statement.close();
    }

    public ArrayList<Categoria> getCategoriasFiltro1(String nombre, String descripcion) {
        ArrayList<Categoria> listaCategorias = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaCategoria1").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaCategoria1(?, ?)"); //Para MySql

            ps.setString(1, nombre);
            ps.setString(2, descripcion);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaCategorias.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaCategorias;
    }
    
    
    public ArrayList<Categoria> getCategoriasFiltro2(int id,String nombre, String descripcion) {
        ArrayList<Categoria> listaCategorias = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaCategoria2").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaCategoria2(?, ?, ?)"); //Para MySql

            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(2, descripcion);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaCategorias.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaCategorias;
    }
    
    public ArrayList<Categoria> getCategoriasFiltro3(String id,String nombre, String descripcion) {
        ArrayList<Categoria> listaCategorias = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaCategoria3").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaCategoria3(?, ?, ?)"); //Para MySql
            
            ps.setString(1, id);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaCategorias.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaCategorias;
    }
}
