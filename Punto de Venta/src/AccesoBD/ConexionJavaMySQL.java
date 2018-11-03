
package AccesoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class ConexionJavaMySQL {
   
     public static Connection connect=null;
    
    public ConexionJavaMySQL(){
    }
    /*
    public static boolean conectarBD(){//cuando se con server de alfredo
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            conect = DriverManager.getConnection("jdbc:sqlserver://panterahell.hopto.org;database=IngenieriaSoftware", "sa", "Fredy97@");
        }catch(SQLException ex){
            System.out.println("Error al establecer coneccion: "+ex);
            JOptionPane.showMessageDialog(null,ex.getMessage()+"1");
            return false;
        }
        return true;
    }*/
    
    
    public static boolean conectarBD(){//cuando sea con my pc en mysql
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect= DriverManager.getConnection("jdbc:mysql://localhost:3306/punto_de_venta","root","");
        }catch(SQLException ex){
            System.out.println("Error al establecer coneccion: "+ex);
            JOptionPane.showMessageDialog(null,ex.getMessage()+"1");
            return false;
        }catch(ClassNotFoundException ex){
            System.out.println("Error al cargar driver: "+ex);
            JOptionPane.showMessageDialog(null,ex.getMessage()+"2"+ex.getCause());
            return false;
        }
        return true;
    }
    
    
    /*
     *Ejecutar instrucciones SQL INSERT,UPDATE, y DELETE
     * @param sql instruccion a ejecutar
     *@return estado de la accion
     */
    public static boolean ejecutarSQLUpdate(String sql){
        try{
            Statement sentencia=connect.createStatement();//4 Hace la sentencia para ejecutar en sql
            sentencia.executeUpdate(sql);
            //sentencia.executeQuery(sql);
            
            return true;
        }catch(SQLException ex){
            System.out.println("Error en instrucción: "+ex);
            return false;
        }
    }
    
    
    /*
     *Ejecutar instruccion SQL SELECT
     * @param sql instruccion a ejecutar
     *@return resultado de la consulta
     */
    public static ResultSet ejecutarSQLSelect(String sql){
        try{
            Statement sentencia=connect.createStatement();//4 Hace la sentencia para ejecutar en sql
            return sentencia.executeQuery(sql);
        }catch(SQLException ex){
            System.out.println("Error en instrucción: "+ex);
            return null;
        }
    }
    
    /*
    *cierra la conexion  con la base de datos 
    */
    public void cerrarConexion(){
        try{
            connect.close();//4 Hace la sentencia para ejecutar en sql
        }catch(SQLException ex){
            System.out.println("Error al cerrar la conexion"+ex);
        }
    }
}
