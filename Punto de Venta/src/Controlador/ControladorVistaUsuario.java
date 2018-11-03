/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.UsuarioBD;
import PuntoDeVenta.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Modelo.Usuario;
import com.jfoenix.controls.JFXPasswordField;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Mike
 */
public class ControladorVistaUsuario implements Initializable {

    UsuarioBD usuarioDB;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    //ManejadorFiltroKey manejador;
    private boolean filtrarActivado;

    private static String ROL_PROGRAMADOR = "Programador";
    private static String ROL_ADMINISTRADOR = "Administrador";
    private static String ROL_EMPLEADO = "Empleado";
    private static String ROL_ENCARGADO_ALMACEN = "Encargado de Almacen";

    String contenidoTxtIDUsuario, contenidoTxtNombreUsuario, contenidoTxtLoginUsuario, contenidoTxtPasswordUsuario, contenidoCboRolUsuario;

    @FXML
    private JFXTextField txtIDUsuario, txtNombreUsuario, txtLoginUsuario;

    @FXML
    private JFXPasswordField txtPasswordUsuario;

    @FXML
    private JFXComboBox cboRolUsuario;

    @FXML
    private TableView<Usuario> tblDatosUsuario;

    @FXML
    private TableColumn<Usuario, Integer> tbcID;

    @FXML
    private TableColumn<Usuario, String> tbcNombre,
            tbcLogin, tbcContraseña, tbcRol;

    @FXML
    private JFXButton btnAgregarUsuario, btnModificarUsuario, btnEliminarUsuario, btnCancelarUsuario, btnFiltrarUsuario, btnRegresarUsuario;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        agregarUsuario();
        llenarTabla(usuarioDB.getUsuarios());
    }

    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        //actualizarAlumno();
        //llenarTabla(alumnoBD.getAlumnos());
    }

    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        //eliminarAlumno();
        //llenarTabla(alumnoBD.getAlumnos());
    }

    @FXML
    private void filtroBusqueda(ActionEvent event) {
        // ManejadorFiltro();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        usuarioDB = new UsuarioBD(conectaBD_PuntoVenta.getConnection());
        filtrarActivado = false;
        llenarTabla(usuarioDB.getUsuarios());

        //manejador = new ManejadorFiltroKey();
        cboRolUsuario.setItems(FXCollections.observableArrayList(ROL_PROGRAMADOR, ROL_ADMINISTRADOR, ROL_EMPLEADO, ROL_ENCARGADO_ALMACEN));

    }

    private void llenarTabla(ArrayList<Usuario> listaUsuarios) {

        tblDatosUsuario.getItems().clear();

        tbcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tbcContraseña.setCellValueFactory(new PropertyValueFactory<>("password"));
        tbcRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        for (Usuario usuario : listaUsuarios) {
            tblDatosUsuario.getItems().add(usuario);
        }
    }

    @FXML
    private void handleTableChange(Event event) {
        Usuario usuario = tblDatosUsuario.getSelectionModel().getSelectedItem();

        if (usuario != null) {
            txtIDUsuario.setText(String.valueOf(usuario.getId()));
            txtNombreUsuario.setText(usuario.getNombre());
            txtLoginUsuario.setText(usuario.getLogin());
            txtPasswordUsuario.setText(usuario.getPassword());
            cboRolUsuario.getSelectionModel().select(usuario.getRol());

        }

    }

    private void agregarUsuario() {
        contenidoTxtNombreUsuario = txtNombreUsuario.getText();
        contenidoTxtLoginUsuario = txtLoginUsuario.getText();
        contenidoTxtPasswordUsuario = txtPasswordUsuario.getText();
        contenidoCboRolUsuario = cboRolUsuario.getSelectionModel().getSelectedItem().toString();

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas agregar a este Usuario?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                usuarioDB.addUsuario(contenidoTxtNombreUsuario, contenidoTxtLoginUsuario,
                        contenidoTxtPasswordUsuario, contenidoCboRolUsuario);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("La operación se ha realizado con éxito");
                alert.show();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Se ha cancelado la operación");
                alert.show();
            }
        } catch (SQLIntegrityConstraintViolationException ex2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El ID " + contenidoTxtIDUsuario + " ya existe en la base de datos");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El Usuario no se ha podido agregar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    /*
    private void actualizarAlumno(){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas modificar el registro de este Usuario?");
            
            if(alert.showAndWait().get() == ButtonType.OK){
            
                alumnoBD.updateAlumno(tblDatosAlumno.getSelectionModel().getSelectedItem().getID(), txtCurp.getText(), txtNombre.getText(),txtApellidoP.getText(), 
                    txtApellidoM.getText(), txtSexo.getText(), dpkFechaNac.getValue().format(DateTimeFormatter.ISO_DATE), 
                    txtDomicilio.getText(), 
                    txtTelefono.getText(), 
                    txtCorreo.getText(), 
                    cboSemestreAlumno.getSelectionModel().getSelectedItem().toString(),
                    txtUser.getText(), 
                    txtPassword.getText(), "1");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("La operación se ha realizado con éxito");
                alert.show();
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Se ha cancelado la operación");
                alert.show();
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El alumno no se ha podido actualizar. Error al acceder a la base de datos");
            alert.show();
            ex.printStackTrace();
        }
 
    }
    
     */
 /*
    private void eliminarAlumno(){
        try {
            if(tblDatosAlumno.getSelectionModel().getSelectedItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor elige un registro");
                alert.show();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas eliminar el registro de este alumno?");
            if(alert.showAndWait().get() == ButtonType.OK){
                String ID = tblDatosAlumno.getSelectionModel().getSelectedItem().getID();
                alumnoBD.deleteAlumno(ID);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("La operación se ha realizado con éxito");
                alert.show();
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Se ha cancelado la operación");
                alert.show();
            }
                
            
        } catch (SQLException ex) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El alumno no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }
 
    }
     */
 /*
    private void limpiarCampos(){
        for (int i = 0; i < txtCAMPOS.length; i++) {
                txtCAMPOS[i].clear();
        }
        txtPassword.clear();
        cboSemestreAlumno.getSelectionModel().select(0);
    }
     */
 /*
    @FXML
    private void filtrarAlumno(){
        filtrarActivado=!filtrarActivado;
        if(filtrarActivado){
            btnAgregar.setDisable(true);
            btnActualizar.setDisable(true);
            btnEliminar.setDisable(true);
            for (int i = 0; i < txtCAMPOS.length; i++) {
                txtCAMPOS[i].textProperty().addListener(manejador);
            }
            cboSemestreAlumno.promptTextProperty().addListener(manejador);
            limpiarCampos();
            
        }else{
            btnAgregar.setDisable(false);
            btnActualizar.setDisable(false);
            btnEliminar.setDisable(false);
            for (int i = 0; i < txtCAMPOS.length; i++) {
                txtCAMPOS[i].textProperty().removeListener(manejador);
            }
            cboSemestreAlumno.promptTextProperty().removeListener(manejador);
        }
    }
     */
 /*
    void ManejadorFiltro(){
        System.out.println("si entra al metodo");
        if(filtrarActivado){
            String id, curp, nombre, apellidoP, apellidoM, sexo, domicilio, telefono, correo, semestreAlumno;
                    System.out.println("Si entra if");
            id = txtID.getText(); 
            curp = txtCurp.getText(); 
            nombre = txtNombre.getText(); 
            apellidoP = txtApellidoP.getText(); 
            apellidoM = txtApellidoM.getText(); 
            sexo = txtSexo.getText(); 
            domicilio = txtDomicilio.getText(); 
            telefono = txtTelefono.getText(); 
            correo = txtCorreo.getText();
            semestreAlumno = cboSemestreAlumno.getSelectionModel().getSelectedItem().toString();
            leerFiltrarTabla(id, curp, nombre, apellidoP, apellidoM, sexo, domicilio, telefono, correo, semestreAlumno);
            System.out.println(nombre);
        }
    }
     */
 /*
    private void leerFiltrarTabla(String id, String curp, String nombre, String apellidoP, 
                                  String apellidoM, String sexo, String domicilio, String telefono, String correo, String semestreAlumno){
        
        llenarTabla(alumnoBD.getAlumnosFiltro(id, curp, nombre, apellidoP, apellidoM, sexo, domicilio, telefono, correo, semestreAlumno));
    }
     */
 /*
    class ManejadorFiltroKey implements ChangeListener{

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }
        
    }

     */
}
