/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoDeVenta;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mike
 */
public class ControladorVistaLogin implements Initializable {

    String contenidoTxtUsuario, contenidoTxtpassword, usuarioActual, rolLogin;
    boolean isProgramador, isAdministrador, isUsuario, isEncargadoAlmacen;

    @FXML
    private JFXTextField txtUser;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private void ingresar(ActionEvent event) {
        acceso();
    }

    public void cambiarVista(ActionEvent e) throws Exception {
        validarLogin();
        
        Parent panelTabla = FXMLLoader.load(getClass().getResource("VistaPrincipal.fxml"));
        Scene panelTablaScene = new Scene(panelTabla);

        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(panelTablaScene);
        window.show();
    }
    
    public void acceso() {
        validarLogin();
        
        if (isProgramador == true && isAdministrador == false && isUsuario == false && isEncargadoAlmacen == false) {
            System.out.println("Es administrador");
            //   loginMeta.tipoUsuario ="administrador" ;}
            System.out.println("entro admin");
            rolLogin = "Programador";

        } else if (isProgramador == true && isAdministrador == false && isUsuario == false && isEncargadoAlmacen == false) {
            System.out.println("Es Profesor");
            System.out.println("entro profe");
            //   loginMeta.tipoUsuario ="profesor" ;
            rolLogin = "Administrador";
        } else if (isProgramador == true && isAdministrador == false && isUsuario == false && isEncargadoAlmacen == false) {
            System.out.println("Es Alumno");
            // loginMeta.tipoUsuario ="alumno" ;
            rolLogin = "Usuario";
        } else if (isProgramador == true && isAdministrador == false && isUsuario == false && isEncargadoAlmacen == false) {
            System.out.println("Es Alumno");
            // loginMeta.tipoUsuario ="alumno" ;
            rolLogin = "EncargadoAlmacen";
        }else {
            System.out.println("El usuario no existe");
        }
        System.out.println("El login del usuario actual es " + usuarioActual);
    }    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
