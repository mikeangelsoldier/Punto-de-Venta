/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import PuntoDeVenta.*;
import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.ConexionJavaMySQL;
import AccesoBD.LoginBD;
import Modelo.Login;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Mike
 */
public class ControladorVistaLogin implements Initializable {

    LoginBD loginDB;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    private ArrayList<Login> logins;
    String tipoUsuario, contenidoTxtUsuario, contenidoTxtpassword, usuarioActual, rolLogin;
    private String mensajeLogin;
    private static String MENSAJE_USUARIO_INCORRECTO = "El usuario Ingresado no existe";
    private static String MENSAJE_CONTRASEÑA_INCORRECTA = "Usuario existente pero contraseña incorrecta";
    private static String ROL_PROGRAMADOR = "Programador";
    private static String ROL_ADMINISTRADOR = "Administrador";
    private static String ROL_EMPLEADO = "Empleado";
    private static String ROL_ENCARGADO_ALMACEN = "Encargado de Almacen";

    boolean isProgramador, isAdministrador, isUsuario, isEncargadoAlmacen;
    boolean loginExiste;

    @FXML
    private JFXTextField txtUser;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private Label lblAvisoLogin;

    /*
    @FXML
    private void ingresar(ActionEvent event) {

        validarLogin();
        imprimirResultadoBusquedaLogin();

    }
     */
    public void cambiarVista(ActionEvent e) throws Exception {
        validarLogin();
        imprimirResultadoBusquedaLogin();
        if (loginExiste) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Bienvenido");
            alert.setTitle("Acceso correcto");
            alert.setContentText("Bienvenido "+loginMeta.nombreUsuario);
            alert.showAndWait();
            
            Parent panelTabla = FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaPrincipal.fxml"));
            Scene panelTablaScene = new Scene(panelTabla);

            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(panelTablaScene);
            window.show();
        }

    }

    public void imprimirResultadoBusquedaLogin() {

        if (isProgramador == true && isAdministrador == false && isUsuario == false && isEncargadoAlmacen == false) {

            rolLogin = ROL_PROGRAMADOR;
            //loginMeta.tipoUsuario =rolLogin;
            System.out.println("Es " + rolLogin);
        } else if (isProgramador == false && isAdministrador == true && isUsuario == false && isEncargadoAlmacen == false) {

            rolLogin = ROL_ADMINISTRADOR;
            //loginMeta.tipoUsuario =rolLogin;
            System.out.println("Es " + rolLogin);
        } else if (isProgramador == false && isAdministrador == false && isUsuario == true && isEncargadoAlmacen == false) {

            rolLogin = ROL_EMPLEADO;
            //loginMeta.tipoUsuario =rolLogin;
            System.out.println("Es " + rolLogin);
        } else if (isProgramador == false && isAdministrador == false && isUsuario == false && isEncargadoAlmacen == true) {

            rolLogin = ROL_ENCARGADO_ALMACEN;
            //loginMeta.tipoUsuario =rolLogin;
            System.out.println("Es " + rolLogin);

        } else {
            System.out.println("El usuario no existe");
        }
        System.out.println("El login del usuario actual es " + usuarioActual);
    }

    public void validarLogin() {
        loginExiste = false;
        lblAvisoLogin.setText("");

        contenidoTxtUsuario = txtUser.getText();

        contenidoTxtpassword = txtPass.getText();
        System.out.println("loginIngresado=     " + contenidoTxtUsuario + "         contraseñaIngresada= " + contenidoTxtpassword);

        int idEncontrado;
        String nombreEncontrado = "";
        String loginEncontrado = "";
        String passEncontrado = "";
        String rolEncontrado = "";

        isProgramador = false;
        isAdministrador = false;
        isUsuario = false;
        isEncargadoAlmacen = false;

        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        loginDB = new LoginBD(conectaBD_PuntoVenta.getConnection());
        logins = new ArrayList<Login>(loginDB.getLogins());

        if (!logins.isEmpty()) {//si hay un login existente
            System.out.println("existe almenos un login en la BD");
            System.out.println("tamaño del arreglo de logins = " + logins.size());

            for (int i = 0; i < logins.size(); i++) {
                idEncontrado = logins.get(i).getId();
                nombreEncontrado = logins.get(i).getNombre();
                loginEncontrado = logins.get(i).getLogin();
                passEncontrado = logins.get(i).getPassword();
                rolEncontrado = logins.get(i).getRol();

                System.out.println("id usuario: " + idEncontrado + "             "
                        + "nombre: " + nombreEncontrado + "             "
                        + "login: " + loginEncontrado + "             "
                        + "password: " + passEncontrado + "             "
                        + "rol: " + rolEncontrado);

                if (loginEncontrado.equals(contenidoTxtUsuario)) {
                    if (passEncontrado.equals(contenidoTxtpassword)) {
                        String rol = logins.get(i).getRol();

                        usuarioActual = loginEncontrado;
                        loginExiste = true;
                        System.out.println("login existe= " + loginExiste);

                        switch (rol) {
                            case "Programador":
                                isProgramador = true;
                                isAdministrador = false;
                                isUsuario = false;
                                isEncargadoAlmacen = false;
                                break;
                            case "Administrador":
                                isProgramador = false;
                                isAdministrador = true;
                                isUsuario = false;
                                isEncargadoAlmacen = false;
                                break;
                            case "Usuario":
                                isProgramador = false;
                                isAdministrador = false;
                                isUsuario = true;
                                isEncargadoAlmacen = false;
                                break;
                            case "Encargado de almacen":
                                isProgramador = false;
                                isAdministrador = false;
                                isUsuario = false;
                                isEncargadoAlmacen = true;
                                break;
                            default:
                                break;
                        }

                        loginMeta.idUsuario = String.valueOf(logins.get(i).getId());
                        loginMeta.nombreUsuario=logins.get(i).getNombre();
                        loginMeta.rolUsuario = logins.get(i).getRol();
                        loginMeta.Password = logins.get(i).getPassword();
                        
                    } else {
                        lblAvisoLogin.setText(MENSAJE_CONTRASEÑA_INCORRECTA);
                    }
                    break;
                } else {
                    lblAvisoLogin.setText(MENSAJE_USUARIO_INCORRECTO);
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
