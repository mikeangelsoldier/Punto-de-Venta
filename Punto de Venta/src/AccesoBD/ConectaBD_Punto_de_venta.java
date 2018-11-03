/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConectaBD_Punto_de_venta {
    private Connection conect;
    public ConectaBD_Punto_de_venta(){
        try {
            //conect = DriverManager.getConnection("jdbc:sqlserver://panterahell.hopto.org;database=IngenieriaSoftware", "sa", "Fredy97@"); //SQL Server
            //conect = DriverManager.getConnection("jdbc:mysql://localhost/punto_de_venta", "root", "");    //MySql
             conect = DriverManager.getConnection("jdbc:mysql://localhost:3306/punto_de_venta", "root", "");    //MySql
            
        } catch (SQLException ex) {
            System.out.println("Error.");
            ex.printStackTrace();
        }
    }
    
    public Connection getConnection(){
        return conect;
    }
}
