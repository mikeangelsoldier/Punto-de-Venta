/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.ProveedorBD;
import Modelo.Proveedor;
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
public class ControladorVistaProveedor implements Initializable {

    ProveedorBD ProveedorDB;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    //ManejadorFiltroKey manejador;
    private boolean filtrarActivado, agregarActivado, modificarActivado;


    private static String AYUDA_AL_AGREGAR = "Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_MODIFICAR = "Seleccionar un registro, Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_FILTRAR = "Escribe en los campos la informacion con la que deben coincidir los registros del filtro. Da clic en Filtro nuevamente para salir";

    String contenidoTxtID, contenidoTxtProveedor, contenidoTxtTelefono, contenidoTxtCorreo, 
           contenidoTxtDireccion,contenidoTxtColonia, contenidotxtMunicipio, contenidoTxtCP, 
           contenidotxtEstado;
    ManejadorFiltroKey manejador;

    @FXML
    private JFXTextField txtID,txtProveedor,txtTelefono,
            txtCorreo,txtDireccion,txtColonia,txtMunicipio,txtCP,
            txtEstado;
    @FXML
    private TableView<Proveedor> tblDatosProveedor;

    @FXML
    private TableColumn<Proveedor, Integer> tbcID;

    @FXML
    private TableColumn<Proveedor, String> tbcEmpresa,
            tbcTelefono, tbcCorreo, tbcDireccion,tbcColonia,tbcMunicipio,tbcCP,
            tbcEstado;

    @FXML
    private JFXButton btnAgregarProveedor, btnModificarProveedor, btnEliminarProveedor, btnCancelarProveedor, btnFiltrarProveedor, btnRegresarProveedor,
            btnGuardarInsercionProveedor, btnGuardarModificacionProveedor;

    @FXML
    private Label lblAyuda;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        agregarProveedorActivado();
    }

    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        modificarProveedorActivado();
    }

    @FXML
    private void handleButtonGuardarInsercion(ActionEvent event) {
        if (agregarActivado) {
            agregarProveedor();
            llenarTabla(ProveedorDB.getProveedores());
            //limpiarCampos();
        }

    }

    @FXML
    private void handleButtonGuardarModificacion(ActionEvent event) {
        if (modificarActivado) {
            actualizarProveedor();
            llenarTabla(ProveedorDB.getProveedores());
            //limpiarCampos();

        }

    }

    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        eliminarProveedor();
        llenarTabla(ProveedorDB.getProveedores());
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
        ProveedorDB = new ProveedorBD(conectaBD_PuntoVenta.getConnection());
        filtrarActivado = false;
        agregarActivado = false;
        modificarActivado = false;

        llenarTabla(ProveedorDB.getProveedores());

        txtID.setEditable(false);
        txtProveedor.setEditable(false);
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
        Proveedor proveedor = tblDatosProveedor.getSelectionModel().getSelectedItem();

        if (proveedor != null) {
            txtID.setText(String.valueOf(proveedor.getId_proveedor()));
            txtProveedor.setText(proveedor.getNombre_proveedor());
            txtTelefono.setText(proveedor.getTelefono());
            txtCorreo.setText(proveedor.getCorreo());
            txtDireccion.setText(proveedor.getDireccion());
            txtColonia.setText(proveedor.getColonia());
            txtMunicipio.setText(proveedor.getMunicipio());
            txtCP.setText(proveedor.getCp());
            txtEstado.setText(proveedor.getEstado());
        }
    }

    private void llenarTabla(ArrayList<Proveedor> listaProveedores) {
        tblDatosProveedor.getItems().clear();
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id_proveedor"));
        tbcEmpresa.setCellValueFactory(new PropertyValueFactory<>("nombre_proveedor"));
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tbcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tbcColonia.setCellValueFactory(new PropertyValueFactory<>("colonia"));
        tbcMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        tbcCP.setCellValueFactory(new PropertyValueFactory<>("cp"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        for (Proveedor proveedor : listaProveedores) {
            tblDatosProveedor.getItems().add(proveedor);
        }
    }

    private void agregarProveedor() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas agregar a este Usuario?");

            if (!camposPorCompletar()) {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    contenidoTxtProveedor = txtProveedor.getText();
                    contenidoTxtTelefono= txtTelefono.getText();
                    contenidoTxtCorreo = txtCorreo.getText();
                    contenidoTxtDireccion = txtDireccion.getText();
                    contenidoTxtColonia = txtColonia.getText();
                    contenidotxtMunicipio = txtMunicipio.getText();
                    contenidoTxtCP = txtCP.getText();
                    contenidotxtEstado = txtEstado.getText();

                    ProveedorDB.addProveedor(contenidoTxtProveedor,
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
            alert.setContentText("El Proveedor no se ha podido agregar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private void actualizarProveedor() {
        int idProveedorSeleccionado = 0;

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas modificar el registro de este Proveedor?");

            if (!camposPorCompletar()) {
                if (tblDatosProveedor.getSelectionModel().getSelectedItems().isEmpty()) {
                    Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                    alertSeleccion.setTitle("Advertencia");
                    alertSeleccion.setHeaderText(null);
                    alertSeleccion.setContentText("Por favor elige un registro para modificar");
                    alertSeleccion.show();
                    return;
                }

                if (alert.showAndWait().get() == ButtonType.OK) {//solo si se acepto continuar
                    idProveedorSeleccionado = tblDatosProveedor.getSelectionModel().getSelectedItem().getId_proveedor();
                    contenidoTxtProveedor = txtProveedor.getText();
                    contenidoTxtTelefono= txtTelefono.getText();
                    contenidoTxtCorreo = txtCorreo.getText();
                    contenidoTxtDireccion = txtDireccion.getText();
                    contenidoTxtColonia = txtColonia.getText();
                    contenidotxtMunicipio = txtMunicipio.getText();
                    contenidoTxtCP = txtCP.getText();
                    contenidotxtEstado = txtEstado.getText();

                   ProveedorDB.updateProveedor(idProveedorSeleccionado,contenidoTxtProveedor,
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
            alert.setContentText("El Proveedor no se ha podido actualizar. Error al acceder a la base de datos");
            alert.show();
            ex.printStackTrace();
        }

    }

    private void eliminarProveedor() {
        try {
            if (tblDatosProveedor.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de este Proveedor?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosProveedor.getSelectionModel().getSelectedItem().getId_proveedor();
                ProveedorDB.deleteProveedor(ID);
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
            alert.setContentText("El Proveedor no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private boolean camposPorCompletar() {
            String nombre = txtProveedor.getText();
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

        if (nombre.equals("") || telefono.equals("") || 
                correo.equals("") || direccion.equals("") || colonia.equals("") || 
                municipio.equals("") || cp.equals("") || estado.equals("")) {
            return true;
        } else {
            return false;
        }

    }

    private void limpiarCampos() {
        txtID.clear();
        txtProveedor.clear();
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
            
            btnAgregarProveedor.setDisable(true);
            btnModificarProveedor.setDisable(true);
            btnEliminarProveedor.setDisable(true);

            btnRegresarProveedor.setVisible(false);

            txtID.setEditable(true);
            txtProveedor.setEditable(true);
            txtTelefono.setEditable(true);
            txtCorreo.setEditable(true);
            txtDireccion.setEditable(true);
            txtColonia.setEditable(true);
            txtMunicipio.setEditable(true);
            txtCP.setEditable(true);
            txtEstado.setEditable(true);

            txtID.textProperty().addListener(manejador);
            txtProveedor.textProperty().addListener(manejador);
            txtTelefono.textProperty().addListener(manejador);
            txtCorreo.textProperty().addListener(manejador);
            txtDireccion.textProperty().addListener(manejador);
            txtColonia.textProperty().addListener(manejador);
            txtMunicipio.textProperty().addListener(manejador);
            txtCP.textProperty().addListener(manejador);
            txtEstado.textProperty().addListener(manejador);
        } else {
            btnAgregarProveedor.setDisable(false);
            btnModificarProveedor.setDisable(false);
            btnEliminarProveedor.setDisable(false);

            btnRegresarProveedor.setVisible(true);
            
            txtID.setEditable(false);
            txtProveedor.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);
            txtDireccion.setEditable(false);
            txtColonia.setEditable(false);
            txtMunicipio.setEditable(false);
            txtCP.setEditable(false);
            txtEstado.setEditable(false);
            
            txtID.textProperty().addListener(manejador);
            txtProveedor.textProperty().addListener(manejador);
            txtTelefono.textProperty().addListener(manejador);
            txtCorreo.textProperty().addListener(manejador);
            txtDireccion.textProperty().addListener(manejador);
            txtColonia.textProperty().addListener(manejador);
            txtMunicipio.textProperty().addListener(manejador);
            txtCP.textProperty().addListener(manejador);
            txtEstado.textProperty().addListener(manejador);

            llenarTabla(ProveedorDB.getProveedores());
            //limpiarCampos();//----------------------------------------------------------
            lblAyuda.setText("");
        }
    }

    void agregarProveedorActivado() {
        agregarActivado = true;
        modificarActivado = false;

        limpiarCampos();
        if (agregarActivado) {
            lblAyuda.setText(AYUDA_AL_AGREGAR);

            btnAgregarProveedor.setDisable(true);
            //btnAgregarUsuario.setStyle("fx-background-color: #0FFF09");
            btnModificarProveedor.setDisable(true);
            btnEliminarProveedor.setDisable(true);
            btnFiltrarProveedor.setDisable(true);

            btnCancelarProveedor.setVisible(true);
            btnRegresarProveedor.setVisible(false);
            btnGuardarInsercionProveedor.setVisible(true);
            btnGuardarModificacionProveedor.setVisible(false);

            txtID.setEditable(false);
            txtProveedor.setEditable(true);
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

    void modificarProveedorActivado() {
        modificarActivado = true;
        agregarActivado = false;

        limpiarCampos();
        if (modificarActivado) {
            lblAyuda.setText(AYUDA_AL_MODIFICAR);

            btnAgregarProveedor.setDisable(true);
            btnModificarProveedor.setDisable(true);
            //btnModificarUsuario.setStyle("fx-background-color: #0FFF09");
            btnEliminarProveedor.setDisable(true);
            btnFiltrarProveedor.setDisable(true);

            btnCancelarProveedor.setVisible(true);
            btnRegresarProveedor.setVisible(false);
            btnGuardarInsercionProveedor.setVisible(false);
            btnGuardarModificacionProveedor.setVisible(true);

            txtID.setEditable(false);
            txtProveedor.setEditable(true);
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

        btnAgregarProveedor.setDisable(false);
        //btnAgregarUsuario.setStyle("fx-background-color: #222288");
        btnModificarProveedor.setDisable(false);
        //btnModificarUsuario.setStyle("fx-background-color: #222288");
        btnEliminarProveedor.setDisable(false);
        btnFiltrarProveedor.setDisable(false);
        //btnRegresarUsuario.setDisable(false);

        btnRegresarProveedor.setVisible(true);

        btnCancelarProveedor.setVisible(false);
        btnGuardarInsercionProveedor.setVisible(false);
        btnGuardarModificacionProveedor.setVisible(false);

            txtID.setEditable(false);
            txtProveedor.setEditable(false);
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
    
    private void leerFiltrarTabla(int id,String nombre,String telefono,
            String correo,String direccion,String colonia,String municipio,String cp,String estado) {
        if (id == 0) {
            llenarTabla(ProveedorDB.getProveedoresFiltro1(nombre,
                    telefono,correo,direccion,colonia,municipio,cp,estado));
        } else {
            llenarTabla(ProveedorDB.getProveedoresFiltro2(id, nombre,
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
                String nombre = txtProveedor.getText();
                String telefono = txtTelefono.getText();
                String  correo = txtCorreo.getText();
                String direccion = txtDireccion.getText();
                String colonia = txtColonia.getText();
                String municipio = txtMunicipio.getText();
                String  cp = txtCP.getText();
                String estado = txtEstado.getText();

                leerFiltrarTabla(id, nombre,telefono,correo,direccion,colonia,municipio,
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
