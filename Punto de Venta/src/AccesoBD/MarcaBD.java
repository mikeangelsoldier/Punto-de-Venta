package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Marca;

public class MarcaBD {

    Connection connect;

    public MarcaBD(Connection connect) {
        this.connect = connect;
    }

    public ArrayList<Marca> getMarcas() {
        ArrayList<Marca> listaMarcas = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getMarcas();").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getMarcas();").executeQuery(); //Para MySql
            while (rs.next()) {
                listaMarcas.add(new Marca(rs.getInt(1), rs.getString(2)));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarcaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaMarcas;
    }

    public void addMarca(String marca) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL addMarca(?)");
        statement.setString(1, marca);
        System.out.println(statement.toString());
        statement.execute();

        statement.close();
    }

    public void updateMarca(int id, String marca) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL updateMarca(?, ?)");
        statement.setInt(1, id);
        statement.setString(2, marca);
        
        System.out.println(statement);
        statement.execute();

        statement.close();
    }

    public void deleteMarca(int ID) throws SQLException {
        PreparedStatement statement = connect.prepareCall("CALL deleteMarca(?)");
        statement.setInt(1, ID);
        
        System.out.println(statement);
        statement.execute();
        statement.close();
    }

    public ArrayList<Marca> getMarcasFiltro1( String marca) {
        ArrayList<Marca> listaMarcas = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaMarca1").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaMarca1(?)"); //Para MySql

            ps.setString(1, marca);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaMarcas.add(new Marca(rs.getInt(1), rs.getString(2)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarcaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaMarcas;
    }
    
    public ArrayList<Marca> getMarcasFiltro2( int id,String marca) {
        ArrayList<Marca> listaMarcas = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaMarca2").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaMarca2(?, ?)"); //Para MySql

            ps.setInt(1, id);
            ps.setString(2, marca);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaMarcas.add(new Marca(rs.getInt(1), rs.getString(2)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarcaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaMarcas;
    }
    
    
    public ArrayList<Marca> getMarcasFiltro3(String id, String marca) {
        ArrayList<Marca> listaMarcas = new ArrayList<>();

        try {
            //ResultSet rs = connect.prepareCall("EXEC getBusquedaMarca3").executeQuery(); //Para SQL Server
            PreparedStatement ps = connect.prepareStatement("CALL  getBusquedaMarca3(?)"); //Para MySql

            ps.setString(1, id);
            ps.setString(2, marca);

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaMarcas.add(new Marca(rs.getInt(1), rs.getString(2)));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarcaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaMarcas;
    }
    
    
}
