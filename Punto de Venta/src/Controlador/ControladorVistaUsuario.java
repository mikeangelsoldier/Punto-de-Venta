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
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

/**
 *
 * @author Mike
 */
public class ControladorVistaUsuario implements Initializable {

    UsuarioBD usuarioDB;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    //ManejadorFiltroKey manejador;
    private boolean filtrarActivado, agregarActivado, modificarActivado;

    private static String ROL_DEFAULT = "Elegir Rol";
    private static String ROL_PROGRAMADOR = "Programador";
    private static String ROL_ADMINISTRADOR = "Administrador";
    private static String ROL_EMPLEADO = "Empleado";
    private static String ROL_ENCARGADO_ALMACEN = "Encargado de Almacen";
    
    private static String AYUDA_AL_AGREGAR = "Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_MODIFICAR = "Seleccionar un registro, Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_FILTRAR = "Escribe en los campos la informacion con la que deben coincidir los registros del filtro. Da clic en Filtro nuevamente para salir";

    String contenidoTxtIDUsuario, contenidoTxtNombreUsuario, contenidoTxtLoginUsuario, contenidoTxtPasswordUsuario, contenidoCboRolUsuario;

    ManejadorFiltroKey manejador;

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
    private JFXButton btnAgregarUsuario, btnModificarUsuario, btnEliminarUsuario, btnCancelarUsuario, btnFiltrarUsuario, btnRegresarUsuario,
            btnGuardarInsercionUsuario, btnGuardarModificacionUsuario;

    @FXML
    private Label lblAyuda;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        agregarUsuarioActivado();
    }

    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        modificarUsuarioActivado();
    }

    @FXML
    private void handleButtonAgregarCambios(ActionEvent event) {
        if (agregarActivado) {
            agregarUsuario();
            llenarTabla(usuarioDB.getUsuarios());
            //limpiarCampos();
        }

    }

    @FXML
    private void handleButtonActualizarCambios(ActionEvent event) {
        if (modificarActivado) {
            actualizarUsuario();
            llenarTabla(usuarioDB.getUsuarios());
            //limpiarCampos();

        }

    }

    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        eliminarUsuario();
        llenarTabla(usuarioDB.getUsuarios());
        limpiarCampos();
    }

    @FXML
    private void handleButtonCancelar(ActionEvent event) {
        limpiarCampos();
        regresarBotonesAFormaOriginal();
        lblAyuda.setText("");
    }

    @FXML
    private void filtroBusqueda(ActionEvent event) {
        ManejadorFiltro();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        usuarioDB = new UsuarioBD(conectaBD_PuntoVenta.getConnection());
        filtrarActivado = false;
        agregarActivado = false;
        modificarActivado = false;
        llenarTabla(usuarioDB.getUsuarios());

        txtIDUsuario.setEditable(false);
        txtNombreUsuario.setEditable(false);
        txtLoginUsuario.setEditable(false);
        txtPasswordUsuario.setEditable(false);
        cboRolUsuario.setEditable(false);
        
        lblAyuda.setText("");

        manejador = new ManejadorFiltroKey();
        cboRolUsuario.setItems(FXCollections.observableArrayList(ROL_DEFAULT, ROL_PROGRAMADOR, ROL_ADMINISTRADOR, ROL_EMPLEADO, ROL_ENCARGADO_ALMACEN));
        cboRolUsuario.getSelectionModel().select(0);
    }

    @FXML
    private void generarPassw(ActionEvent event) {
        String cadena = "";
        for (int i = 0; i < 10; i++) {
            cadena = cadena + (char) (Math.random() * 57 + 65);
        }
        txtPasswordUsuario.setText(cadena);
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

    private void agregarUsuario() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas agregar a este Usuario?");

            if (!camposPorCompletar()) {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    contenidoTxtNombreUsuario = txtNombreUsuario.getText();
                    contenidoTxtLoginUsuario = txtLoginUsuario.getText();
                    contenidoTxtPasswordUsuario = txtPasswordUsuario.getText();
                    contenidoCboRolUsuario = cboRolUsuario.getSelectionModel().getSelectedItem().toString();

                    usuarioDB.addUsuario(contenidoTxtNombreUsuario, contenidoTxtLoginUsuario,
                            contenidoTxtPasswordUsuario, contenidoCboRolUsuario);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("La operación se ha realizado con éxito");
                    alert.show();
                    regresarBotonesAFormaOriginal();
                    limpiarCampos();
                    lblAyuda.setText("");

                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha cancelado la operación");
                    alert.show();
                }
            } else {

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Aun existen campos por completar");
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

    private void actualizarUsuario() {
        int idDeUsuarioSeleccionado = 0;

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas modificar el registro de este Usuario?");

            if (!camposPorCompletar()) {
                if (tblDatosUsuario.getSelectionModel().getSelectedItems().isEmpty()) {
                    Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                    alertSeleccion.setTitle("Advertencia");
                    alertSeleccion.setHeaderText(null);
                    alertSeleccion.setContentText("Por favor elige un registro para modificar");
                    alertSeleccion.show();
                    return;
                }
                
                if (alert.showAndWait().get() == ButtonType.OK) {//solo si se acepto continuar
                    idDeUsuarioSeleccionado = tblDatosUsuario.getSelectionModel().getSelectedItem().getId();
                    contenidoTxtNombreUsuario = txtNombreUsuario.getText();
                    contenidoTxtLoginUsuario = txtLoginUsuario.getText();
                    contenidoTxtPasswordUsuario = txtPasswordUsuario.getText();
                    contenidoCboRolUsuario = cboRolUsuario.getSelectionModel().getSelectedItem().toString();

                    usuarioDB.updateUsuario(idDeUsuarioSeleccionado, contenidoTxtNombreUsuario,
                            contenidoTxtLoginUsuario, contenidoTxtPasswordUsuario, contenidoCboRolUsuario);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("La operación se ha realizado con éxito");
                    alert.show();
                    regresarBotonesAFormaOriginal();
                    limpiarCampos();
                    lblAyuda.setText("");
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha cancelado la operación");
                    alert.show();
                }

            } else {

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Aun existen campos por completar");
                alert.show();
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El Usuario no se ha podido actualizar. Error al acceder a la base de datos");
            alert.show();
            ex.printStackTrace();
        }

    }

    private void eliminarUsuario() {
        try {
            if (tblDatosUsuario.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de este Usuario?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosUsuario.getSelectionModel().getSelectedItem().getId();
                usuarioDB.deleteUsuario(ID);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("La operación se ha realizado con éxito");
                alert.show();
            } else {
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
            alert.setContentText("El Usuario no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private boolean camposPorCompletar() {
        String nombre = txtNombreUsuario.getText();
        String login = txtLoginUsuario.getText();
        String password = txtPasswordUsuario.getText();
        String rol = "";

//        String rol =ROL_DEFAULT;
//        try{
//             rol = cboRolUsuario.getSelectionModel().getSelectedItem().toString();
//        }catch(Exception e){
//             rol =ROL_DEFAULT;
//             
//        }
        rol = cboRolUsuario.getSelectionModel().getSelectedItem().toString();

        if (nombre.equals("") || login.equals("") || password.equals("") || rol.equals(ROL_DEFAULT)) {
            return true;
        } else {
            return false;
        }

    }

    private void limpiarCampos() {
        txtIDUsuario.clear();
        txtNombreUsuario.clear();
        txtLoginUsuario.clear();
        txtPasswordUsuario.clear();
        cboRolUsuario.getSelectionModel().select(0);
    }

    @FXML
    private void filtrarUsuario() {
        
        filtrarActivado = !filtrarActivado;
        limpiarCampos();
        if (filtrarActivado) {
            lblAyuda.setText(AYUDA_AL_FILTRAR);
            
            btnAgregarUsuario.setDisable(true);
            btnModificarUsuario.setDisable(true);
            btnEliminarUsuario.setDisable(true);

            btnRegresarUsuario.setVisible(false);

            txtIDUsuario.setEditable(true);
            txtNombreUsuario.setEditable(true);
            txtLoginUsuario.setEditable(true);
            txtPasswordUsuario.setEditable(true);
            cboRolUsuario.setEditable(true);
            cboRolUsuario.getSelectionModel().select(0);

            txtIDUsuario.textProperty().addListener(manejador);
            txtNombreUsuario.textProperty().addListener(manejador);
            txtLoginUsuario.textProperty().addListener(manejador);

            //cboRolUsuario.promptTextProperty().addListener(manejador);
            cboRolUsuario.valueProperty().addListener(manejador);
            //limpiarCampos();
        } else {
            btnAgregarUsuario.setDisable(false);
            btnModificarUsuario.setDisable(false);
            btnEliminarUsuario.setDisable(false);

            btnAgregarUsuario.setDisable(false);
            btnModificarUsuario.setDisable(false);
            btnEliminarUsuario.setDisable(false);

            btnRegresarUsuario.setVisible(true);

            txtIDUsuario.setEditable(false);
            txtNombreUsuario.setEditable(false);
            txtLoginUsuario.setEditable(false);
            txtPasswordUsuario.setEditable(false);
            cboRolUsuario.setEditable(false);
            cboRolUsuario.getSelectionModel().select(0);

            txtIDUsuario.textProperty().removeListener(manejador);
            txtNombreUsuario.textProperty().removeListener(manejador);
            txtLoginUsuario.textProperty().removeListener(manejador);

            //cboRolUsuario.promptTextProperty().removeListener(manejador);
            cboRolUsuario.valueProperty().removeListener(manejador);

            llenarTabla(usuarioDB.getUsuarios());
            //limpiarCampos();//----------------------------------------------------------
        }
    }

    void agregarUsuarioActivado() {
        agregarActivado = true;
        modificarActivado = false;

        limpiarCampos();
        if (agregarActivado) {
            lblAyuda.setText(AYUDA_AL_AGREGAR);
            
            btnAgregarUsuario.setDisable(true);
            //btnAgregarUsuario.setStyle("fx-background-color: #0FFF09");
            btnModificarUsuario.setDisable(true);
            btnEliminarUsuario.setDisable(true);
            btnFiltrarUsuario.setDisable(true);

            btnCancelarUsuario.setVisible(true);
            btnRegresarUsuario.setVisible(false);
            btnGuardarInsercionUsuario.setVisible(true);
            btnGuardarModificacionUsuario.setVisible(false);

            txtIDUsuario.setEditable(false);
            txtNombreUsuario.setEditable(true);
            txtLoginUsuario.setEditable(true);
            txtPasswordUsuario.setEditable(true);
            cboRolUsuario.setEditable(true);
            cboRolUsuario.getSelectionModel().select(0);

        } else {

        }
    }

    void modificarUsuarioActivado() {
        modificarActivado = true;
        agregarActivado = false;

        limpiarCampos();
        if (modificarActivado) {
            lblAyuda.setText(AYUDA_AL_MODIFICAR);
            
            btnAgregarUsuario.setDisable(true);
            btnModificarUsuario.setDisable(true);
            //btnModificarUsuario.setStyle("fx-background-color: #0FFF09");
            btnEliminarUsuario.setDisable(true);
            btnFiltrarUsuario.setDisable(true);

            btnCancelarUsuario.setVisible(true);
            btnRegresarUsuario.setVisible(false);
            btnGuardarInsercionUsuario.setVisible(false);
            btnGuardarModificacionUsuario.setVisible(true);

            txtIDUsuario.setEditable(false);
            txtNombreUsuario.setEditable(true);
            txtLoginUsuario.setEditable(true);
            txtPasswordUsuario.setEditable(true);
            cboRolUsuario.setEditable(true);
            cboRolUsuario.getSelectionModel().select(0);

        } else {

        }
    }

    void regresarBotonesAFormaOriginal() {
        modificarActivado = false;
        agregarActivado = false;

        btnAgregarUsuario.setDisable(false);
        //btnAgregarUsuario.setStyle("fx-background-color: #222288");
        btnModificarUsuario.setDisable(false);
        //btnModificarUsuario.setStyle("fx-background-color: #222288");
        btnEliminarUsuario.setDisable(false);
        btnFiltrarUsuario.setDisable(false);

        btnCancelarUsuario.setVisible(false);
        btnRegresarUsuario.setVisible(true);
        btnGuardarInsercionUsuario.setVisible(false);
        btnGuardarModificacionUsuario.setVisible(false);

        txtIDUsuario.setEditable(false);
        txtNombreUsuario.setEditable(false);
        txtLoginUsuario.setEditable(false);
        txtPasswordUsuario.setEditable(false);
        cboRolUsuario.setEditable(false);
        cboRolUsuario.getSelectionModel().select(0);

        lblAyuda.setText("");
    }

    

    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    private void leerFiltrarTabla(int id, String nombre, String login, String rol) {
        if (id == 0) {
            llenarTabla(usuarioDB.getUsuariosFiltro1(nombre, login, rol));
        } else {
            llenarTabla(usuarioDB.getUsuariosFiltro2(id, nombre, login, rol));
        }
    }

    void ManejadorFiltro() {
        System.out.println("si entra al metodo");
        if (filtrarActivado) {
            if (txtIDUsuario.getText().equals("") || isNumeric(txtIDUsuario.getText())) {
                int id;
                String nombre, login, rol;
                System.out.println("Si entra if");

                if (txtIDUsuario.getText().equals("")) {
                    id = 0;
                } else {
                    id = Integer.valueOf(txtIDUsuario.getText());
                }
                nombre = txtNombreUsuario.getText();
                login = txtLoginUsuario.getText();
                rol = cboRolUsuario.getSelectionModel().getSelectedItem().toString();
                if (rol.equals(ROL_DEFAULT)) {
                    rol = "";
                }
                System.out.println("ID para filtrar: " + id);
                System.out.println("nombre para filtrar: " + nombre);
                System.out.println("login para filtrar: " + login);
                System.out.println("rol para filtrar: " + rol);

                leerFiltrarTabla(id, nombre, login, rol);
                System.out.println(nombre);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El ID debe ser numerico para realizar el filtro");
                alert.show();
                txtIDUsuario.clear();
            }
        }
    }
    
    class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }
    }
    
    
}
