/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.SucursalBD;
import Modelo.Sucursal;
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
public class ControladorVistaSucursal implements Initializable {

    SucursalBD SucursalDB;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    //ManejadorFiltroKey manejador;
    private boolean filtrarActivado, agregarActivado, modificarActivado;


    private static String AYUDA_AL_AGREGAR = "Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_MODIFICAR = "Seleccionar un registro, Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_FILTRAR = "Escribe en los campos la informacion con la que deben coincidir los registros del filtro. Da clic en Filtro nuevamente para salir";

    String contenidoTxtID, contenidoTxtNombre,contenidoTxtSucursal, contenidoTxtTelefono, contenidoTxtCorreo, 
           contenidoTxtDireccion,contenidoTxtColonia, contenidotxtMunicipio, contenidoTxtCP, 
           contenidotxtEstado;
    ManejadorFiltroKey manejador;

    @FXML
    private JFXTextField txtID,txtNombre,txtSucursal,txtTelefono,
            txtCorreo,txtDireccion,txtColonia,txtMunicipio,txtCP,
            txtEstado;
    @FXML
    private TableView<Sucursal> tblDatosSucursal;

    @FXML
    private TableColumn<Sucursal,Integer> tbcID;

    @FXML
    private TableColumn<Sucursal, String> tbcNombre,tbcSucursal,
            tbcTelefono, tbcCorreo, tbcDireccion,tbcColonia,tbcMunicipio,tbcCP,
            tbcEstado;

    @FXML
    private JFXButton btnAgregarSucursal, btnModificarSucursal, btnEliminarSucursal, btnCancelarSucursal, btnFiltrarSucursal, btnRegresarSucursal,
            btnGuardarInsercionSucursal, btnGuardarModificacionSucursal;

    @FXML
    private Label lblAyuda;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        agregarSucursalActivado();
    }

    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        modificarSucursalActivado();
    }

    @FXML
    private void handleButtonGuardarInsercion(ActionEvent event) {
        if (agregarActivado) {
            agregarSucursal();
            llenarTabla(SucursalDB.getSucursales());
            //limpiarCampos();
        }

    }

    @FXML
    private void handleButtonGuardarModificacion(ActionEvent event) {
        if (modificarActivado) {
            actualizarSucursal();
            llenarTabla(SucursalDB.getSucursales());
            //limpiarCampos();

        }

    }

    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        eliminarProveedor();
        llenarTabla(SucursalDB.getSucursales());
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
        SucursalDB = new SucursalBD(conectaBD_PuntoVenta.getConnection());
        filtrarActivado = false;
        agregarActivado = false;
        modificarActivado = false;

        llenarTabla(SucursalDB.getSucursales());

        txtID.setEditable(false);
        txtNombre.setEditable(false);
        txtSucursal.setEditable(false);
        txtTelefono.setEditable(false);
        txtCorreo.setEditable(false);
        txtDireccion.setEditable(false);
        txtColonia.setEditable(false);
        txtMunicipio.setEditable(false);
        //txtCP.setEditable(false);
        txtEstado.setEditable(false);

        

        lblAyuda.setText("");

        regresarBotonesAFormaOriginal();

        manejador = new ManejadorFiltroKey();
       
    }

 

    @FXML
    private void handleTableChange(Event event) {
        Sucursal sucursal = tblDatosSucursal.getSelectionModel().getSelectedItem();

        if (sucursal != null) {
            txtID.setText(String.valueOf(sucursal.getId_sucursal()));
            txtNombre.setText(sucursal.getNombre_sucursal());
            txtSucursal.setText(sucursal.getSucursal());
            txtTelefono.setText(sucursal.getTelefono());
            txtCorreo.setText(sucursal.getCorreo());
            txtDireccion.setText(sucursal.getDireccion());
            txtColonia.setText(sucursal.getColonia());
            txtMunicipio.setText(sucursal.getMunicipio());
            txtCP.setText(sucursal.getCp());
            txtEstado.setText(sucursal.getEstado());
        }
    }

    private void llenarTabla(ArrayList<Sucursal> listaSucursal) {
        tblDatosSucursal.getItems().clear();
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id_sucursal"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_sucursal"));
        tbcSucursal.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tbcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tbcColonia.setCellValueFactory(new PropertyValueFactory<>("colonia"));
        tbcMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        tbcCP.setCellValueFactory(new PropertyValueFactory<>("cp"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        for (Sucursal sucursal : listaSucursal) {
            tblDatosSucursal.getItems().add(sucursal);
        }
    }

    private void agregarSucursal() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas agregar a esta Sucursal?");

            if (!camposPorCompletar()) {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    contenidoTxtNombre = txtNombre.getText();
                    contenidoTxtSucursal = txtSucursal.getText();
                    contenidoTxtTelefono= txtTelefono.getText();
                    contenidoTxtCorreo = txtCorreo.getText();
                    contenidoTxtDireccion = txtDireccion.getText();
                    contenidoTxtColonia = txtColonia.getText();
                    contenidotxtMunicipio = txtMunicipio.getText();
                    contenidoTxtCP = txtCP.getText();
                    contenidotxtEstado = txtEstado.getText();

                    SucursalDB.addSucursal(contenidoTxtNombre,contenidoTxtSucursal,
                            contenidoTxtTelefono, contenidoTxtCorreo,contenidoTxtDireccion,
                            contenidoTxtColonia, contenidotxtMunicipio, contenidoTxtCP, contenidotxtEstado);
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
            alert.setContentText("El ID " + contenidoTxtID + " ya existe en la base de datos");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("La sucursal no se ha podido agregar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private void actualizarSucursal() {
        int idSucursalSeleccionada = 0;

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas modificar el registro de esta Sucursal?");

            if (!camposPorCompletar()) {
                if (tblDatosSucursal.getSelectionModel().getSelectedItems().isEmpty()) {
                    Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                    alertSeleccion.setTitle("Advertencia");
                    alertSeleccion.setHeaderText(null);
                    alertSeleccion.setContentText("Por favor elige un registro para modificar");
                    alertSeleccion.show();
                    return;
                }

                if (alert.showAndWait().get() == ButtonType.OK) {//solo si se acepto continuar
                    idSucursalSeleccionada = tblDatosSucursal.getSelectionModel().getSelectedItem().getId_sucursal();
                    contenidoTxtNombre = txtNombre.getText();
                    contenidoTxtSucursal = txtSucursal.getText();
                    contenidoTxtTelefono= txtTelefono.getText();
                    contenidoTxtCorreo = txtCorreo.getText();
                    contenidoTxtDireccion = txtDireccion.getText();
                    contenidoTxtColonia = txtColonia.getText();
                    contenidotxtMunicipio = txtMunicipio.getText();
                    contenidoTxtCP = txtCP.getText();
                    contenidotxtEstado = txtEstado.getText();

                   SucursalDB.updateSucursal(idSucursalSeleccionada,contenidoTxtNombre,contenidoTxtSucursal,
                            contenidoTxtTelefono, contenidoTxtCorreo,contenidoTxtDireccion,
                            contenidoTxtColonia, contenidotxtMunicipio, contenidoTxtCP, contenidotxtEstado);
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
            alert.setContentText("La sucursal no se ha podido actualizar. Error al acceder a la base de datos");
            alert.show();
            ex.printStackTrace();
        }

    }

    private void eliminarProveedor() {
        try {
            if (tblDatosSucursal.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de esta Sucursal?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosSucursal.getSelectionModel().getSelectedItem().getId_sucursal();
                SucursalDB.deleteSucursal(ID);
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
            alert.setContentText("La sucursal no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private boolean camposPorCompletar() {
            String nombre = txtNombre.getText();
            String sucursal = txtSucursal.getText();
            String telefono = txtTelefono.getText();
            String  correo = txtCorreo.getText();
            String direccion = txtDireccion.getText();
            String colonia = txtColonia.getText();
            String municipio = txtMunicipio.getText();
            String  cp = txtCP.getText();
            String estado = txtEstado.getText();

//        String rol =ROL_DEFAULT;
//        try{
//             rol = cboRolUsuario.getSelectionModel().getSelectedItem().toString();
//        }catch(Exception e){
//             rol =ROL_DEFAULT;
//             
//        }

        if (nombre.equals("") ||sucursal.equals("")|| telefono.equals("") || 
                correo.equals("") || direccion.equals("") || colonia.equals("") || 
                municipio.equals("") || cp.equals("") || estado.equals("")) {
            return true;
        } else {
            return false;
        }

    }

    private void limpiarCampos() {
        txtID.clear();
        txtNombre.clear();
        txtSucursal.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
        txtColonia.clear();
        txtMunicipio.clear();
        txtCP.clear();
        txtEstado.clear();
    }

    @FXML
    private void filtrarProveedor() {
            filtrarActivado = !filtrarActivado;
            System.out.println(filtrarActivado);
        limpiarCampos();
        if (filtrarActivado) {
            lblAyuda.setText(AYUDA_AL_FILTRAR);
            
            btnAgregarSucursal.setDisable(true);
            btnModificarSucursal.setDisable(true);
            btnEliminarSucursal.setDisable(true);

            btnRegresarSucursal.setVisible(false);

            txtID.setEditable(true);
            txtNombre.setEditable(true);
            txtSucursal.setEditable(true);
            txtTelefono.setEditable(true);
            txtCorreo.setEditable(true);
            txtDireccion.setEditable(true);
            txtColonia.setEditable(true);
            txtMunicipio.setEditable(true);
            txtCP.setEditable(true);
            txtEstado.setEditable(true);

            txtID.textProperty().addListener(manejador);
            txtNombre.textProperty().addListener(manejador);
            txtSucursal.textProperty().addListener(manejador);
            txtTelefono.textProperty().addListener(manejador);
            txtCorreo.textProperty().addListener(manejador);
            txtDireccion.textProperty().addListener(manejador);
            txtColonia.textProperty().addListener(manejador);
            txtMunicipio.textProperty().addListener(manejador);
            txtCP.textProperty().addListener(manejador);
            txtEstado.textProperty().addListener(manejador);
        } else {
            btnAgregarSucursal.setDisable(false);
            btnModificarSucursal.setDisable(false);
            btnEliminarSucursal.setDisable(false);

            btnRegresarSucursal.setVisible(true);
            
            txtID.setEditable(false);
            txtNombre.setEditable(false);
            txtSucursal.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);
            txtDireccion.setEditable(false);
            txtColonia.setEditable(false);
            txtMunicipio.setEditable(false);
            txtCP.setEditable(false);
            txtEstado.setEditable(false);
            
            txtID.textProperty().addListener(manejador);
            txtNombre.textProperty().addListener(manejador);
            txtSucursal.textProperty().addListener(manejador);
            txtTelefono.textProperty().addListener(manejador);
            txtCorreo.textProperty().addListener(manejador);
            txtDireccion.textProperty().addListener(manejador);
            txtColonia.textProperty().addListener(manejador);
            txtMunicipio.textProperty().addListener(manejador);
            txtCP.textProperty().addListener(manejador);
            txtEstado.textProperty().addListener(manejador);

            llenarTabla(SucursalDB.getSucursales());
            //limpiarCampos();//----------------------------------------------------------
            lblAyuda.setText("");
        }
    }

    void agregarSucursalActivado() {
        agregarActivado = true;
        modificarActivado = false;

        limpiarCampos();
        if (agregarActivado) {
            lblAyuda.setText(AYUDA_AL_AGREGAR);

            btnAgregarSucursal.setDisable(true);
            //btnAgregarUsuario.setStyle("fx-background-color: #0FFF09");
            btnModificarSucursal.setDisable(true);
            btnEliminarSucursal.setDisable(true);
            btnFiltrarSucursal.setDisable(true);

            btnCancelarSucursal.setVisible(true);
            btnRegresarSucursal.setVisible(false);
            btnGuardarInsercionSucursal.setVisible(true);
            btnGuardarModificacionSucursal.setVisible(false);

            txtID.setEditable(false);
            txtNombre.setEditable(true);
            txtSucursal.setEditable(true);
            txtTelefono.setEditable(true);
            txtCorreo.setEditable(true);
            txtDireccion.setEditable(true);
            txtColonia.setEditable(true);
            txtMunicipio.setEditable(true);
            txtCP.setEditable(true);
            txtEstado.setEditable(true);

        } else {

        }
    }

    void modificarSucursalActivado() {
        modificarActivado = true;
        agregarActivado = false;

        limpiarCampos();
        if (modificarActivado) {
            lblAyuda.setText(AYUDA_AL_MODIFICAR);

            btnAgregarSucursal.setDisable(true);
            btnModificarSucursal.setDisable(true);
            //btnModificarUsuario.setStyle("fx-background-color: #0FFF09");
            btnEliminarSucursal.setDisable(true);
            btnFiltrarSucursal.setDisable(true);

            btnCancelarSucursal.setVisible(true);
            btnRegresarSucursal.setVisible(false);
            btnGuardarInsercionSucursal.setVisible(false);
            btnGuardarModificacionSucursal.setVisible(true);

            txtID.setEditable(false);
            txtNombre.setEditable(true);
            txtSucursal.setEditable(true);
            txtTelefono.setEditable(true);
            txtCorreo.setEditable(true);
            txtDireccion.setEditable(true);
            txtColonia.setEditable(true);
            txtMunicipio.setEditable(true);
            txtCP.setEditable(true);
            txtEstado.setEditable(true);

        } else {

        }
    }

    void regresarBotonesAFormaOriginal() {
        modificarActivado = false;
        agregarActivado = false;
        filtrarActivado = false;

        btnAgregarSucursal.setDisable(false);
        //btnAgregarUsuario.setStyle("fx-background-color: #222288");
        btnModificarSucursal.setDisable(false);
        //btnModificarUsuario.setStyle("fx-background-color: #222288");
        btnEliminarSucursal.setDisable(false);
        btnFiltrarSucursal.setDisable(false);
        //btnRegresarUsuario.setDisable(false);

        btnRegresarSucursal.setVisible(true);

        btnCancelarSucursal.setVisible(false);
        btnGuardarInsercionSucursal.setVisible(false);
        btnGuardarModificacionSucursal.setVisible(false);

            txtID.setEditable(false);
            txtNombre.setEditable(false);
            txtSucursal.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);
            txtDireccion.setEditable(false);
            txtColonia.setEditable(false);
            txtMunicipio.setEditable(false);
            txtCP.setEditable(false);
            txtEstado.setEditable(false);

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
    
    private void leerFiltrarTabla(int id,String nombre,String sucursal,String telefono,
            String correo,String direccion,String colonia,String municipio,String cp,String estado) {
        if (id == 0) {
            llenarTabla(SucursalDB.getSucursalesFiltro1(nombre,sucursal,
                    telefono,correo,direccion,colonia,municipio,cp,estado));
        } else {
            llenarTabla(SucursalDB.getSucursalesFiltro2(id, nombre,sucursal,
                    telefono,correo,direccion,colonia,municipio,cp,estado));
        }
    }

    

    void ManejadorFiltro() {
        System.out.println("si entra al metodo");
        if (filtrarActivado) {
            if (txtID.getText().equals("") || isNumeric(txtID.getText())) {
                int id;
                System.out.println("Si entra if");

                if (txtID.getText().equals("")) {
                    id = 0;
                } else {
                    id = Integer.valueOf(txtID.getText());
                }
                String nombre = txtNombre.getText();
                String sucursal = txtSucursal.getText();
                String telefono = txtTelefono.getText();
                String  correo = txtCorreo.getText();
                String direccion = txtDireccion.getText();
                String colonia = txtColonia.getText();
                String municipio = txtMunicipio.getText();
                String  cp = txtCP.getText();
                String estado = txtEstado.getText();

                leerFiltrarTabla(id, nombre,sucursal,telefono,correo,direccion,colonia,municipio,
                cp,estado);
                System.out.println(nombre);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El ID debe ser numerico para realizar el filtro");
                alert.show();
                txtID.clear();
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
