
package AccesoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Login;


public class LoginBD {
    Connection connect;
    public LoginBD(Connection connect) {
        this.connect = connect;
    }
    
    public ArrayList<Login> getLogins(){
        ArrayList<Login> listaLogins = new ArrayList<>();
        
        try {
            //ResultSet rs = connect.prepareCall("EXEC getLogin").executeQuery(); //Para SQL Server
            ResultSet rs = connect.prepareCall("CALL getLogin").executeQuery(); //Para MySql
            while (rs.next()){
                listaLogins.add(new Login(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaLogins;
    }

}
