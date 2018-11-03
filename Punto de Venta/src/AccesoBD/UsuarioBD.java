package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Usuario;

public class UsuarioBD {

    Connection connect;

    public UsuarioBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getUsuarios").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getUsuarios").executeQuery(); //Para MySql
            while (rs.next()) {
                listaUsuarios.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaUsuarios;
    }

    public void addUsuario(String nombre, String login, String password, String rol) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL addUsuario(?, ?, ?, ?)");
        statement.setString(1, nombre);
        statement.setString(2, login);
        statement.setString(3, password);
        statement.setString(4, rol);
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
    }

    public void updateUsuario(int id, String nombre, String login, String password, String rol) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL updateUsuario(?, ?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, nombre);
        statement.setString(3, login);
        statement.setString(4, password);
        statement.setString(5, rol);
        System.out.println(statement);
        statement.execute();

        statement.close();
    }

    public void deleteUsuario(int ID) throws SQLException {

        PreparedStatement statement = connect.prepareCall("CALL deleteUsuario(?)");
        statement.setInt(1, ID);
        System.out.println(statement);
        statement.execute();
    }

    public ArrayList<Usuario> getUsuariosFiltro1(String nombre, String login, String rol) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaUsuario1(?, ?, ?)"); //Para MySql

            ps.setString(1, nombre);
            ps.setString(2, login);
            ps.setString(3, rol);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaUsuarios.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaUsuarios;
    }
    
    public ArrayList<Usuario> getUsuariosFiltro2(int id, String nombre, String login, String rol) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaUsuario").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaUsuario2(?, ?, ?, ?)"); //Para MySql

            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, login);
            ps.setString(4, rol);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaUsuarios.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaUsuarios;
    }
}
