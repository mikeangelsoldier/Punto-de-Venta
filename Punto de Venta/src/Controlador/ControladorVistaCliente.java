/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.ClienteBD;
import AccesoBD.ConectaBD_Punto_de_venta;
import Modelo.Cliente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 *
 * @author Mike
 */


public class ControladorVistaCliente implements Initializable {
    
    ClienteBD ClienteDB;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    private static String AYUDA_AL_AGREGAR = "Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_MODIFICAR = "Seleccionar un registro, Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_FILTRAR = "Escribe en los campos la informacion con la que deben coincidir los registros del filtro. Da clic en Filtro nuevamente para salir";
    
    private boolean filtrarActivado, agregarActivado, modificarActivado;
    
    String contenidoTxtIDCliente, contenidoTxtNombreCliente,contenidoTxtRFC, contenidoTxtTelefono, contenidoTxtCorreo, 
            contenidoTxtDireccion,contenidoTxtColonia, contenidotxtMunicipio, contenidoTxtCP, contenidotxtEstado;
    ManejadorFiltroKey manejador;
    
    @FXML
    JFXTextField txtIdcliente,txtRFC,txtNombre_cliente,txtTelefono,txtCorreo,txtDireccion,txtColonia,
            txtMunicipio,txtCp_cliente,txtEstado;
    
    @FXML
    private TableView<Cliente> tblDatosCliente;
    
    @FXML
    private Label lblAyuda;
    
    @FXML
    private TableColumn<Cliente, Integer> tbcID;

    @FXML
    private TableColumn<Cliente, String> tbcNombre,
            tbcRFC, tbcTelefono, tbcCorreo,tbcDireccion,tbcColonia,tbcMunicipio,tbcCP,tbcEstado;
    
    @FXML
    private JFXButton btnAgregarCliente, btnModificarCliente, btnEliminarCliente, btnGuardarCambiosAgregarCliente,
            btnGuardarCambiosModificarCliente,btnFiltrarCliente,btnCancelarCliente,btnRegresarCliente;
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void handleButtonAgregar(ActionEvent event){
            agregarClienteActivado();
        
    }
    
    @FXML
    private void handleButtonActualizar(ActionEvent event) {
            modificarClienteActivado();
    }
    
    @FXML
    private void handleButtonAgregarCambios(ActionEvent event){
        if (agregarActivado) {
            agregarCliente();
            llenarTabla(ClienteDB.getClientes());
            //limpiarCampos();
        }
    }
    
    @FXML
    private void handleButtonActualizarCambios(ActionEvent event) {
        if (modificarActivado) {
            actualizarCliente();
            llenarTabla(ClienteDB.getClientes());
            //limpiarCampos();

        }

    }
    
    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        eliminarCliente();
        llenarTabla(ClienteDB.getClientes());
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
        // TODO
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        ClienteDB = new ClienteBD(conectaBD_PuntoVenta.getConnection());
        filtrarActivado = false;
        agregarActivado = false;
        modificarActivado = false;
        llenarTabla(ClienteDB.getClientes());

        txtIdcliente.setEditable(false);
        txtNombre_cliente.setEditable(false);
        txtRFC.setEditable(false);
        txtTelefono.setEditable(false);
        txtCorreo.setEditable(false);
        txtDireccion.setEditable(false);
        txtColonia.setEditable(false);
        txtMunicipio.setEditable(false);
        txtCp_cliente.setEditable(false);
        txtEstado.setEditable(false);
        
        lblAyuda.setText("");
        
        regresarBotonesAFormaOriginal();
        manejador = new ManejadorFiltroKey();
        
    }    
    
    private void llenarTabla(ArrayList<Cliente> listaClientes) {
        tblDatosCliente.getItems().clear();
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcRFC.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tbcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tbcColonia.setCellValueFactory(new PropertyValueFactory<>("colonia"));
        tbcMunicipio.setCellValueFactory(new PropertyValueFactory<>("minicipio"));
        tbcCP.setCellValueFactory(new PropertyValueFactory<>("cp"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        for (Cliente cliente : listaClientes) {
            tblDatosCliente.getItems().add(cliente);
        }
    }
    
    @FXML
    private void handleTableChange(Event event) {
        Cliente cliente = tblDatosCliente.getSelectionModel().getSelectedItem();

        if (cliente != null) {
            txtIdcliente.setText(String.valueOf(cliente.getId()));
            txtNombre_cliente.setText(cliente.getNombre());
            txtRFC.setText(cliente.getRfc());
            txtTelefono.setText(cliente.getTelefono());
            txtCorreo.setText(cliente.getCorreo());
            txtDireccion.setText(cliente.getDireccion());
            txtColonia.setText(cliente.getColonia());
            txtMunicipio.setText(cliente.getMunicipio());
            txtCp_cliente.setText(cliente.getCp());
            txtEstado.setText(cliente.getEstado());
        }
    }
    
    private void filtrarCliente() {
        
        filtrarActivado = !filtrarActivado;
        limpiarCampos();
        if (filtrarActivado) {
            lblAyuda.setText(AYUDA_AL_FILTRAR);
            
            btnAgregarCliente.setDisable(true);
            btnModificarCliente.setDisable(true);
            btnEliminarCliente.setDisable(true);

            btnRegresarCliente.setVisible(false);

            txtIdcliente.setEditable(true);
            txtNombre_cliente.setEditable(true);
            txtRFC.setEditable(true);
            txtTelefono.setEditable(true);
            txtCorreo.setEditable(true);
            txtDireccion.setEditable(true);
            txtColonia.setEditable(true);
            txtMunicipio.setEditable(true);
            txtCp_cliente.setEditable(true);
            txtEstado.setEditable(true);

            txtIdcliente.textProperty().addListener(manejador);
            txtNombre_cliente.textProperty().addListener(manejador);
            txtRFC.textProperty().addListener(manejador);
            txtTelefono.textProperty().addListener(manejador);
            txtCorreo.textProperty().addListener(manejador);
            txtDireccion.textProperty().addListener(manejador);
            txtColonia.textProperty().addListener(manejador);
            txtMunicipio.textProperty().addListener(manejador);
            txtCp_cliente.textProperty().addListener(manejador);
            txtEstado.textProperty().addListener(manejador);
        } else {
            btnAgregarCliente.setDisable(false);
            btnModificarCliente.setDisable(false);
            btnEliminarCliente.setDisable(false);

            btnRegresarCliente.setVisible(true);
            
            txtIdcliente.setEditable(false);
            txtNombre_cliente.setEditable(false);
            txtRFC.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);
            txtDireccion.setEditable(false);
            txtColonia.setEditable(false);
            txtMunicipio.setEditable(false);
            txtCp_cliente.setEditable(false);
            txtEstado.setEditable(false);
            
            txtIdcliente.textProperty().addListener(manejador);
            txtNombre_cliente.textProperty().addListener(manejador);
            txtRFC.textProperty().addListener(manejador);
            txtTelefono.textProperty().addListener(manejador);
            txtCorreo.textProperty().addListener(manejador);
            txtDireccion.textProperty().addListener(manejador);
            txtColonia.textProperty().addListener(manejador);
            txtMunicipio.textProperty().addListener(manejador);
            txtCp_cliente.textProperty().addListener(manejador);
            txtEstado.textProperty().addListener(manejador);

            llenarTabla(ClienteDB.getClientes());
            //limpiarCampos();//----------------------------------------------------------
            lblAyuda.setText("");
        }
    }

    
    private void agregarCliente() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas agregar a este Cliente?");

            if (!camposPorCompletar()) {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    contenidoTxtNombreCliente = txtNombre_cliente.getText();
                    contenidoTxtRFC = txtRFC.getText();
                    contenidoTxtTelefono= txtTelefono.getText();
                    contenidoTxtCorreo = txtCorreo.getText();
                    contenidoTxtDireccion = txtDireccion.getText();
                    contenidoTxtColonia = txtColonia.getText();
                    contenidotxtMunicipio = txtMunicipio.getText();
                    contenidoTxtCP = txtCp_cliente.getText();
                    contenidotxtEstado = txtEstado.getText();
                    

                    ClienteDB.addCliente(contenidoTxtNombreCliente,contenidoTxtRFC,
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
            alert.setContentText("El ID " + contenidoTxtIDCliente + " ya existe en la base de datos");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El Cliente no se ha podido agregar. Error al acceder a la base de datos");
            alert.show();
        }

    }
    
    private void actualizarCliente() {
        int idDeClienteSeleccionado = 0;

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas modificar el registro de este Cliente?");

            if (!camposPorCompletar()) {
                if (tblDatosCliente.getSelectionModel().getSelectedItems().isEmpty()) {
                    Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                    alertSeleccion.setTitle("Advertencia");
                    alertSeleccion.setHeaderText(null);
                    alertSeleccion.setContentText("Por favor elige un registro para modificar");
                    alertSeleccion.show();
                    return;
                }
                
                if (alert.showAndWait().get() == ButtonType.OK) {//solo si se acepto continuar
                    idDeClienteSeleccionado = tblDatosCliente.getSelectionModel().getSelectedItem().getId();
                    contenidoTxtNombreCliente = txtNombre_cliente.getText();
                    contenidoTxtRFC = txtRFC.getText();
                    contenidoTxtTelefono= txtTelefono.getText();
                    contenidoTxtCorreo = txtCorreo.getText();
                    contenidoTxtDireccion = txtDireccion.getText();
                    contenidoTxtColonia = txtColonia.getText();
                    contenidotxtMunicipio = txtMunicipio.getText();
                    contenidoTxtCP = txtCp_cliente.getText();
                    contenidotxtEstado = txtEstado.getText();

                    ClienteDB.updateCliente(idDeClienteSeleccionado,contenidoTxtNombreCliente,contenidoTxtRFC,
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
            alert.setContentText("El Cliente no se ha podido actualizar. Error al acceder a la base de datos");
            alert.show();
            ex.printStackTrace();
        }

    }
    
    private void eliminarCliente() {
        try {
            if (tblDatosCliente.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de este Cliente?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosCliente.getSelectionModel().getSelectedItem().getId();
                ClienteDB.deleteCliente(ID);
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
            alert.setContentText("El Cliente no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }
    
    private boolean camposPorCompletar() {
        
            String nombre = txtNombre_cliente.getText();
            String  rfc = txtRFC.getText();
            String telefono = txtTelefono.getText();
            String  correo = txtCorreo.getText();
            String direccion = txtDireccion.getText();
            String colonia = txtColonia.getText();
            String municipio = txtMunicipio.getText();
            String  cp = txtCp_cliente.getText();
            String estado = txtEstado.getText();

//        String rol =ROL_DEFAULT;
//        try{
//             rol = cboRolUsuario.getSelectionModel().getSelectedItem().toString();
//        }catch(Exception e){
//             rol =ROL_DEFAULT;
//             
//        }

        if (nombre.equals("") || rfc.equals("")|| telefono.equals("") || 
                correo.equals("") || direccion.equals("") || colonia.equals("") || 
                municipio.equals("") || cp.equals("") || estado.equals("")) {
            return true;
        } else {
            return false;
        }

    }
    
    void agregarClienteActivado() {
        agregarActivado = true;
        modificarActivado = false;

        limpiarCampos();
        if (agregarActivado) {
            lblAyuda.setText(AYUDA_AL_AGREGAR);
            
            btnAgregarCliente.setDisable(true);
            btnModificarCliente.setDisable(true);
            btnEliminarCliente.setDisable(true);
            btnFiltrarCliente.setDisable(true);

            btnCancelarCliente.setVisible(true);
            btnRegresarCliente.setVisible(false);
            btnGuardarCambiosAgregarCliente.setVisible(true);
            btnGuardarCambiosModificarCliente.setVisible(false);

            txtIdcliente.setEditable(false);
            txtNombre_cliente.setEditable(true);
            txtRFC.setEditable(true);
            txtTelefono.setEditable(true);
            txtCorreo.setEditable(true);
            txtDireccion.setEditable(true);
            txtColonia.setEditable(true);
            txtMunicipio.setEditable(true);
            txtCp_cliente.setEditable(true);
            txtEstado.setEditable(true);

        } else {

        }
    }
    
    void modificarClienteActivado() {
        modificarActivado = true;
        agregarActivado = false;

        limpiarCampos();
        if (modificarActivado) {
            lblAyuda.setText(AYUDA_AL_MODIFICAR);
            
            btnAgregarCliente.setDisable(true);
            btnModificarCliente.setDisable(true);
            btnEliminarCliente.setDisable(true);
            btnFiltrarCliente.setDisable(true);

            btnCancelarCliente.setVisible(true);
            btnRegresarCliente.setVisible(false);
            btnGuardarCambiosAgregarCliente.setVisible(false);
            btnGuardarCambiosModificarCliente.setVisible(true);

            txtIdcliente.setEditable(false);
            txtNombre_cliente.setEditable(true);
            txtRFC.setEditable(true);
            txtTelefono.setEditable(true);
            txtCorreo.setEditable(true);
            txtDireccion.setEditable(true);
            txtColonia.setEditable(true);
            txtMunicipio.setEditable(true);
            txtCp_cliente.setEditable(true);
            txtEstado.setEditable(true);

        } else {

        }
    }
    
    private void limpiarCampos() {
        txtIdcliente.clear();
        txtNombre_cliente.clear();
        txtRFC.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
        txtColonia.clear();
        txtMunicipio.clear();
        txtCp_cliente.clear();
        txtEstado.clear();
    }
    
    void regresarBotonesAFormaOriginal() {
        modificarActivado = false;
        agregarActivado = false;
        filtrarActivado = false;

            btnAgregarCliente.setDisable(false);
            //btnAgregarUsuario.setStyle("fx-background-color: #0FFF09");
            btnModificarCliente.setDisable(false);
            btnEliminarCliente.setDisable(false);
            btnFiltrarCliente.setDisable(false);

            btnCancelarCliente.setVisible(false);
            btnRegresarCliente.setVisible(true);
            btnGuardarCambiosAgregarCliente.setVisible(false);
            btnGuardarCambiosModificarCliente.setVisible(false);

            txtIdcliente.setEditable(false);
            txtNombre_cliente.setEditable(false);
            txtRFC.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);
            txtDireccion.setEditable(false);
            txtColonia.setEditable(false);
            txtMunicipio.setEditable(false);
            txtCp_cliente.setEditable(false);
            txtEstado.setEditable(false);

        //lblAyuda.setText("");
    }
    
    void ManejadorFiltro() {
        System.out.println("si entra al metodo");
        if (filtrarActivado) {
            if (txtIdcliente.getText().equals("") || isNumeric(txtIdcliente.getText())) {
                int id;
                System.out.println("Si entra if");

                if (txtIdcliente.getText().equals("")) {
                    id = 0;
                } else {
                    id = Integer.valueOf(txtIdcliente.getText());
                }
                String nombre = txtNombre_cliente.getText();
                String  rfc = txtRFC.getText();
                String telefono = txtTelefono.getText();
                String  correo = txtCorreo.getText();
                String direccion = txtDireccion.getText();
                String colonia = txtColonia.getText();
                String municipio = txtMunicipio.getText();
                String  cp = txtCp_cliente.getText();
                String estado = txtEstado.getText();

                leerFiltrarTabla(id, nombre, rfc,telefono,correo,direccion,colonia,municipio,
                cp,estado);
                System.out.println(nombre);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El ID debe ser numerico para realizar el filtro");
                alert.show();
                txtIdcliente.clear();
            }
        }
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
    
    private void leerFiltrarTabla(int id,String nombre,String rfc,String telefono,
            String correo,String direccion,String colonia,String municipio,String cp,String estado) {
        if (id == 0) {
            llenarTabla(ClienteDB.getClientesFiltro1(nombre, rfc,
                    telefono,correo,direccion,colonia,municipio,cp,estado));
        } else {
            llenarTabla(ClienteDB.getClientesFiltro2(id, nombre, rfc,
                    telefono,correo,direccion,colonia,municipio,cp,estado));
        }
    }
    
        class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }
    }

    
}
