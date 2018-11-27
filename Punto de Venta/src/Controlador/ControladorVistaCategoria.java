/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.CategoriaBD;
import AccesoBD.ConectaBD_Punto_de_venta;
import Modelo.Categoria;
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
public class ControladorVistaCategoria implements Initializable {

    CategoriaBD CategoriaBD;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    //ManejadorFiltroKey manejador;
    private boolean filtrarActivado, agregarActivado, modificarActivado;


    private static String AYUDA_AL_AGREGAR = "Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_MODIFICAR = "Seleccionar un registro, Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_FILTRAR = "Escribe en los campos la informacion con la que deben coincidir los registros del filtro. Da clic en Filtro nuevamente para salir";

    String contenidoTxtID, contenidoTxtCategoria, contenidoTxtDescripcion;
    ManejadorFiltroKey manejador;

    @FXML
    private JFXTextField txtID,txtCategoria,txtDescripcion;
    @FXML
    private TableView<Categoria> tblDatosCategoria;

    @FXML
    private TableColumn<Categoria, Integer> tbcID;

    @FXML
    private TableColumn<Categoria, String> tbcCategoria,
            tbcDescripcion;

    @FXML
    private JFXButton btnAgregarCategoria, btnModificarCategoria, btnEliminarCategoria, btnCancelarCategoria,
            btnFiltrarCategoria, btnRegresarCategoria,
            btnGuardarInsercionCategoria, btnGuardarModificacionCategoria;

    @FXML
    private Label lblAyuda;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        agregarCategoriaActivado();
    }

    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        modificarCategoriaActivado();
    }

    @FXML
    private void handleButtonGuardarInsercion(ActionEvent event) {
        if (agregarActivado) {
            agregarCategoria();
            llenarTabla(CategoriaBD.getCategorias());
            //limpiarCampos();
        }

    }

    @FXML
    private void handleButtonGuardarModificacion(ActionEvent event) {
        if (modificarActivado) {
            actualizarCategoria();
            llenarTabla(CategoriaBD.getCategorias());
            //limpiarCampos();

        }

    }

    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        eliminarCategoria();
        llenarTabla(CategoriaBD.getCategorias());
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
        CategoriaBD = new CategoriaBD(conectaBD_PuntoVenta.getConnection());
        filtrarActivado = false;
        agregarActivado = false;
        modificarActivado = false;

        llenarTabla(CategoriaBD.getCategorias());

        txtID.setEditable(false);
        txtCategoria.setEditable(false);
        txtDescripcion.setEditable(false);

        

        lblAyuda.setText("");

        regresarBotonesAFormaOriginal();

        manejador = new ManejadorFiltroKey();
       
    }

 

    @FXML
    private void handleTableChange(Event event) {
        Categoria categoria = tblDatosCategoria.getSelectionModel().getSelectedItem();

        if (categoria != null) {
            txtID.setText(String.valueOf(categoria.getId_categoria()));
            txtCategoria.setText(categoria.getNombre());
            txtDescripcion.setText(categoria.getDescripcion());
        }
    }

    private void llenarTabla(ArrayList<Categoria> listaCategorias) {
        tblDatosCategoria.getItems().clear();
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        for (Categoria categoria : listaCategorias) {
            tblDatosCategoria.getItems().add(categoria);
        }
    }

    private void agregarCategoria() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas agregar esta Categoria?");

            if (!camposPorCompletar()) {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    contenidoTxtCategoria = txtCategoria.getText();
                    contenidoTxtDescripcion= txtDescripcion.getText();

                    CategoriaBD.addCategoria(contenidoTxtCategoria,
                            contenidoTxtDescripcion);
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
            alert.setContentText("La Categoria no se ha podido agregar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private void actualizarCategoria() {
        int idCategoriaSeleccionada = 0;

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas modificar el registro esta Categoria?");

            if (!camposPorCompletar()) {
                if (tblDatosCategoria.getSelectionModel().getSelectedItems().isEmpty()) {
                    Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                    alertSeleccion.setTitle("Advertencia");
                    alertSeleccion.setHeaderText(null);
                    alertSeleccion.setContentText("Por favor elige un registro para modificar");
                    alertSeleccion.show();
                    return;
                }

                if (alert.showAndWait().get() == ButtonType.OK) {//solo si se acepto continuar
                    idCategoriaSeleccionada = tblDatosCategoria.getSelectionModel().getSelectedItem().getId_categoria();
                    contenidoTxtCategoria = txtCategoria.getText();
                    contenidoTxtDescripcion= txtDescripcion.getText();
                  

                   CategoriaBD.updateCategoria(idCategoriaSeleccionada,contenidoTxtCategoria,
                            contenidoTxtDescripcion);
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
            alert.setContentText("La Categoria no se ha podido actualizar. Error al acceder a la base de datos");
            alert.show();
            ex.printStackTrace();
        }

    }

    private void eliminarCategoria() {
        try {
            if (tblDatosCategoria.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de esta Categoria?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosCategoria.getSelectionModel().getSelectedItem().getId_categoria();
                CategoriaBD.deleteCategoria(ID);
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
            alert.setContentText("La Categoria no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private boolean camposPorCompletar() {
            String nombre = txtCategoria.getText();
            String descripcion = txtDescripcion.getText();
            

        if (nombre.equals("") || descripcion.equals("")) {
            return true;
        } else {
            return false;
        }

    }

    private void limpiarCampos() {
        txtID.clear();
        txtCategoria.clear();
        txtDescripcion.clear();
    }

    @FXML
    private void filtrarCategoria() {
            filtrarActivado = !filtrarActivado;
            System.out.println(filtrarActivado);
        limpiarCampos();
        if (filtrarActivado) {
            lblAyuda.setText(AYUDA_AL_FILTRAR);
            
            btnAgregarCategoria.setDisable(true);
            btnModificarCategoria.setDisable(true);
            btnEliminarCategoria.setDisable(true);

            btnRegresarCategoria.setVisible(false);

            txtID.setEditable(true);
            txtCategoria.setEditable(true);
            txtDescripcion.setEditable(true);
           

            txtID.textProperty().addListener(manejador);
            txtCategoria.textProperty().addListener(manejador);
            txtDescripcion.textProperty().addListener(manejador);
        
        } else {
            btnAgregarCategoria.setDisable(false);
            btnModificarCategoria.setDisable(false);
            btnEliminarCategoria.setDisable(false);

            btnRegresarCategoria.setVisible(true);
            
            txtID.setEditable(false);
            txtCategoria.setEditable(false);
            txtDescripcion.setEditable(false);
            
            
            txtID.textProperty().addListener(manejador);
            txtCategoria.textProperty().addListener(manejador);
            txtDescripcion.textProperty().addListener(manejador);
           

            llenarTabla(CategoriaBD.getCategorias());
            //limpiarCampos();//----------------------------------------------------------
            lblAyuda.setText("");
        }
    }

    void agregarCategoriaActivado() {
        agregarActivado = true;
        modificarActivado = false;

        limpiarCampos();
        if (agregarActivado) {
            lblAyuda.setText(AYUDA_AL_AGREGAR);

            btnAgregarCategoria.setDisable(true);
            //btnAgregarUsuario.setStyle("fx-background-color: #0FFF09");
            btnModificarCategoria.setDisable(true);
            btnEliminarCategoria.setDisable(true);
            btnFiltrarCategoria.setDisable(true);

            btnCancelarCategoria.setVisible(true);
            btnRegresarCategoria.setVisible(false);
            btnGuardarInsercionCategoria.setVisible(true);
            btnGuardarModificacionCategoria.setVisible(false);

            txtID.setEditable(false);
            txtCategoria.setEditable(true);
            txtDescripcion.setEditable(true);
            

        } else {

        }
    }

    void modificarCategoriaActivado() {
        modificarActivado = true;
        agregarActivado = false;

        limpiarCampos();
        if (modificarActivado) {
            lblAyuda.setText(AYUDA_AL_MODIFICAR);

            btnAgregarCategoria.setDisable(true);
            btnModificarCategoria.setDisable(true);
            //btnModificarUsuario.setStyle("fx-background-color: #0FFF09");
            btnEliminarCategoria.setDisable(true);
            btnFiltrarCategoria.setDisable(true);

            btnCancelarCategoria.setVisible(true);
            btnRegresarCategoria.setVisible(false);
            btnGuardarInsercionCategoria.setVisible(false);
            btnGuardarModificacionCategoria.setVisible(true);

            txtID.setEditable(false);
            txtCategoria.setEditable(true);
            txtDescripcion.setEditable(true);

        } else {

        }
    }

    void regresarBotonesAFormaOriginal() {
        modificarActivado = false;
        agregarActivado = false;
        filtrarActivado = false;

        btnAgregarCategoria.setDisable(false);
        //btnAgregarUsuario.setStyle("fx-background-color: #222288");
        btnModificarCategoria.setDisable(false);
        //btnModificarUsuario.setStyle("fx-background-color: #222288");
        btnEliminarCategoria.setDisable(false);
        btnFiltrarCategoria.setDisable(false);
        //btnRegresarUsuario.setDisable(false);

        btnRegresarCategoria.setVisible(true);

        btnCancelarCategoria.setVisible(false);
        btnGuardarInsercionCategoria.setVisible(false);
        btnGuardarModificacionCategoria.setVisible(false);

            txtID.setEditable(false);
            txtCategoria.setEditable(false);
            txtDescripcion.setEditable(false);

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
    
    private void leerFiltrarTabla(int id,String categoria,String descripcion) {
        if (id == 0) {
            llenarTabla(CategoriaBD.getCategoriasFiltro1( categoria, descripcion));
        } else {
            llenarTabla(CategoriaBD.getCategoriasFiltro2(id, categoria, descripcion));
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
                String categoria = txtCategoria.getText();
                String descripcion = txtDescripcion.getText();
                

                leerFiltrarTabla(id, categoria,descripcion);
                System.out.println(id);
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
