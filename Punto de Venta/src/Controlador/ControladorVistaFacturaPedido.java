/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.FacturaPedidoBD;
import AccesoBD.UsuarioBD;
import Modelo.FacturaPedido;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import AccesoBD.ProveedorBD;
import AccesoBD.UsuarioBD;
import Modelo.Proveedor;
import Modelo.Usuario;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author PaulAdrian
 */
public class ControladorVistaFacturaPedido implements Initializable {
    
    FacturaPedidoBD facturaBD;
    ProveedorBD proveedorBD;
    UsuarioBD usuarioBD;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    ManejadorFiltroKey manejador;
    
    private static String PROVEEDOR_DEFAULT = "Elegir Proveedor";
    private static String USUARIO_DEFAULT = "Elegir Usuario";
    
    private boolean existenProveedores = false;
    private boolean existenUsuarios = false;
    
    private static String AYUDA_AL_AGREGAR = "Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_MODIFICAR = "Seleccionar un registro, Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_FILTRAR = "Escribe en los campos la informacion con la que deben coincidir los registros del filtro. Da clic en Filtro nuevamente para salir";
    
    private boolean filtrarActivado, agregarActivado, modificarActivado;
    
    private ArrayList<Proveedor> listaObjetosProveedores;
    private ArrayList<Usuario> listaObjetosUsuarios;
    
    String contenidoTxtFolioFactura;
    
    float contenidoTxtMonto;
    
    int contenidoCboId_Proveedor, contenidoCboId_Usuario;
    
    Date contenidoFecha;
    
    @FXML
    private JFXTextField txtFolioFactura,txtMonto;
    
    @FXML
    private JFXComboBox cboProveedor,cboUsuario;
    
    @FXML
    private JFXDatePicker pickerFecha;
    
    @FXML
    private JFXButton btnAgregarPedido, btnModificarPedido, btnEliminarPedido, btnCancelarPedido, btnFiltrarPedido, btnRegresarPedido,
            btnGuardarInsercionPedido, btnGuardarModificacionPedido;
    
    @FXML
    private Label lblAyuda;
    
    @FXML
    private TableView<FacturaPedido> tblDatosPedidos;
    
    @FXML
    private TableColumn<FacturaPedido, Integer> tbcFolio,tbcProveedor,tbcUsuario;
    
    @FXML
    private TableColumn<FacturaPedido, Float> tbcMonto;
    
    @FXML
    private TableColumn<FacturaPedido, Date> tbcFecha;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        agregarPedidoActivado();
    }
    
    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        modificarPedidoActivado();
    }
    
     @FXML
    private void handleButtonGuardarInsercion(ActionEvent event) {
        if (agregarActivado) {
            agregarPedido();
            llenarTabla(facturaBD.getPedidos());
            //limpiarCampos();
        }

    }
    
    @FXML
    private void handleButtonGuardarModificacion(ActionEvent event) {
        if (modificarActivado) {
            actualizarPedido();
            llenarTabla(facturaBD.getPedidos());
            //limpiarCampos();

        }

    }
    
    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        eliminarPedido();
        llenarTabla(facturaBD.getPedidos());
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

    
    @FXML
    private void handleTableChange(Event event) {
        FacturaPedido pedido = tblDatosPedidos.getSelectionModel().getSelectedItem();

        if (pedido != null) {
            txtFolioFactura.setText(String.valueOf(pedido.getFolio_factura()));
            cboProveedor.getSelectionModel().select(pedido.getProveedor());
            cboUsuario.getSelectionModel().select(pedido.getUsuario());
            txtMonto.setText(String.valueOf(pedido.getMonto()));
            LocalDate fecha = Instant.ofEpochMilli(pedido.getFecha().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            pickerFecha.setValue(fecha);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        facturaBD = new FacturaPedidoBD(conectaBD_PuntoVenta.getConnection());
        proveedorBD = new ProveedorBD(conectaBD_PuntoVenta.getConnection());
        usuarioBD = new UsuarioBD(conectaBD_PuntoVenta.getConnection());
        
        filtrarActivado = false;
        agregarActivado = false;
        modificarActivado = false;
        
        listaObjetosProveedores = new ArrayList<Proveedor>();
        listaObjetosUsuarios = new ArrayList<Usuario>();
        
        llenarCbosOpciones();

        llenarTabla(facturaBD.getPedidos());

        txtFolioFactura.setEditable(false);
        cboProveedor.setEditable(false);
        cboUsuario.setEditable(false);
        txtMonto.setEditable(false);
        pickerFecha.setEditable(false);

        lblAyuda.setText("");

        regresarBotonesAFormaOriginal();

        manejador = new ControladorVistaFacturaPedido.ManejadorFiltroKey();
        
    }

    private void llenarCbosOpciones() {
        actualizarComboProveedores();
        actualizarComboUsuarios();
    }
    
    private void actualizarComboProveedores() {
        cboProveedor.getItems().clear();

        this.listaObjetosProveedores = new ArrayList<Proveedor>(proveedorBD.getProveedores());

        /*
        System.out.println("\n*****************Los PROVEEDORES almacenados son :");
        for (int i = 0; i < listaObjetosProveedores.size(); i++) {
            System.out.println(listaObjetosProveedores.get(i).getId_proveedor()
                    + "         " + listaObjetosProveedores.get(i).getNombre_proveedor()
                    + "         " + listaObjetosProveedores.get(i).getTelefono()
                    + "         " + listaObjetosProveedores.get(i).getCorreo()
                    + "         " + listaObjetosProveedores.get(i).getDireccion()
                    + "         " + listaObjetosProveedores.get(i).getColonia()
                    + "         " + listaObjetosProveedores.get(i).getMunicipio()
                    + "         " + listaObjetosProveedores.get(i).getCp()
                    + "         " + listaObjetosProveedores.get(i).getEstado()
            );
        }
         */
        cboProveedor.getItems().add(PROVEEDOR_DEFAULT);
        for (int i = 0; i < listaObjetosProveedores.size(); i++) {
            cboProveedor.getItems().add(listaObjetosProveedores.get(i).getNombre_proveedor());
        }
        if (listaObjetosProveedores.size() > 0) {
            cboProveedor.setValue(listaObjetosProveedores.get(0).getNombre_proveedor());
            existenProveedores = true;
        }
    }
    
    private void actualizarComboUsuarios() {
        cboUsuario.getItems().clear();

        this.listaObjetosUsuarios = new ArrayList<Usuario>(usuarioBD.getUsuarios());

        /*
        System.out.println("\n*****************Los PROVEEDORES almacenados son :");
        for (int i = 0; i < listaObjetosProveedores.size(); i++) {
            System.out.println(listaObjetosProveedores.get(i).getId_proveedor()
                    + "         " + listaObjetosProveedores.get(i).getNombre_proveedor()
                    + "         " + listaObjetosProveedores.get(i).getTelefono()
                    + "         " + listaObjetosProveedores.get(i).getCorreo()
                    + "         " + listaObjetosProveedores.get(i).getDireccion()
                    + "         " + listaObjetosProveedores.get(i).getColonia()
                    + "         " + listaObjetosProveedores.get(i).getMunicipio()
                    + "         " + listaObjetosProveedores.get(i).getCp()
                    + "         " + listaObjetosProveedores.get(i).getEstado()
            );
        }
         */
        cboUsuario.getItems().add(USUARIO_DEFAULT);
        for (int i = 0; i < listaObjetosUsuarios.size(); i++) {
            cboUsuario.getItems().add(listaObjetosUsuarios.get(i).getNombre());
        }
        if (listaObjetosUsuarios.size() > 0) {
            cboUsuario.setValue(listaObjetosUsuarios.get(0).getNombre());
            existenUsuarios = true;
        }
    }
    
   private void llenarTabla(ArrayList<FacturaPedido> listaPedidos) {

        for (int i = 0; i < listaPedidos.size(); i++) {
            int folio = listaPedidos.get(i).getFolio_factura();
            int proveedor = listaPedidos.get(i).getId_proveedor();
            int usuario = listaPedidos.get(i).getId_usuario();
            float monto = listaPedidos.get(i).getMonto();
            Date fecha = listaPedidos.get(i).getFecha();

            //System.out.println("FK categoria leida= " + categoria);
            //System.out.println("FK proveedor leid= " + proveedor);
            String nombreProvedor = "";
            String nombreUsuario = "";

            for (int j = 0; j < listaObjetosProveedores.size(); j++) {
                if (listaObjetosProveedores.get(j).getId_proveedor() == proveedor) {
                    nombreProvedor = listaObjetosProveedores.get(j).getNombre_proveedor();
                    //System.out.println(nombreCategoria);
                    break;
                }
            }

            for (int j = 0; j < listaObjetosUsuarios.size(); j++) {
                if (listaObjetosUsuarios.get(j).getId() == usuario) {
                    nombreUsuario = listaObjetosUsuarios.get(j).getNombre();
                    //System.out.println(nombreProvedor);
                    break;
                }
            }

            FacturaPedido factura = new FacturaPedido();
            factura.setFolio_factura(folio);
            factura.setProveedor(nombreProvedor);
            factura.setUsuario(nombreUsuario);
            factura.setMonto(monto);
            factura.setFecha(fecha);

            //System.out.println("Categoria cambiada a "+productoConNombreCategoriaYProveedor.getNombreCategoria());
            //System.out.println("proveedor cambiada a "+productoConNombreCategoriaYProveedor.getNombreProveedor());
            listaPedidos.set(i, factura);

        }

        tblDatosPedidos.getItems().clear();

        tbcFolio.setCellValueFactory(new PropertyValueFactory<>("folio_factura"));
        tbcProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        tbcUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        tbcMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        for (FacturaPedido facturaCompleta : listaPedidos) {
            tblDatosPedidos.getItems().add(facturaCompleta);
        }
    }
   
   @FXML
    private void filtrarPedido() {

        filtrarActivado = !filtrarActivado;
        limpiarCampos();
        if (filtrarActivado) {
            lblAyuda.setText(AYUDA_AL_FILTRAR);

            btnAgregarPedido.setDisable(true);
            btnModificarPedido.setDisable(true);
            btnEliminarPedido.setDisable(true);
          
            btnRegresarPedido.setVisible(true);

            txtFolioFactura.setEditable(true);
            cboProveedor.setEditable(true);
            cboProveedor.getSelectionModel().select(0);
            cboUsuario.setEditable(true);
            cboUsuario.getSelectionModel().select(0);
            txtMonto.setEditable(true);
            pickerFecha.setValue(null);
            pickerFecha.setEditable(true);

            txtFolioFactura.textProperty().addListener(manejador);
            cboProveedor.valueProperty().addListener(manejador);
            cboUsuario.valueProperty().addListener(manejador);
            txtMonto.textProperty().addListener(manejador);
            pickerFecha.valueProperty().addListener(manejador);
            //cboRolUsuario.promptTextProperty().addListener(manejador);
            //limpiarCampos();
        } else {
            btnAgregarPedido.setDisable(false);
            btnModificarPedido.setDisable(false);
            btnEliminarPedido.setDisable(false);
          
            btnRegresarPedido.setVisible(false);

             txtFolioFactura.setEditable(false);
            cboProveedor.setEditable(false);
            cboProveedor.getSelectionModel().select(0);
            cboUsuario.setEditable(false);
            cboUsuario.getSelectionModel().select(0);
            txtMonto.setEditable(false);
            pickerFecha.setValue(null);
            pickerFecha.setEditable(false);

            txtFolioFactura.textProperty().removeListener(manejador);
            cboProveedor.valueProperty().removeListener(manejador);
            cboUsuario.valueProperty().removeListener(manejador);
            txtMonto.textProperty().removeListener(manejador);
            pickerFecha.valueProperty().removeListener(manejador);

            llenarTabla(facturaBD.getPedidos());
            //limpiarCampos();//----------------------------------------------------------
            lblAyuda.setText("");

        }
    }
    
    private void agregarPedido() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas agregar a este Pedido?");

            if (!camposPorCompletar()) {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    contenidoCboId_Proveedor = cboProveedor.getSelectionModel().getSelectedIndex();
                    contenidoCboId_Usuario = cboUsuario.getSelectionModel().getSelectedIndex();
                    contenidoTxtMonto = Float.parseFloat(txtMonto.getText());
                    contenidoFecha = java.sql.Date.valueOf(pickerFecha.getValue());

                    facturaBD.addPedido(contenidoCboId_Proveedor, contenidoCboId_Usuario,
                            contenidoTxtMonto, contenidoFecha);
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
            alert.setContentText("El Folio " + contenidoTxtFolioFactura + " ya existe en la base de datos");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El Pedido no se ha podido agregar. Error al acceder a la base de datos");
            alert.show();
        }

    }
    
    private void actualizarPedido() {
        int idDePedidoSeleccionado = 0;

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas modificar el registro de este Pedido?");

            if (!camposPorCompletar()) {
                if (tblDatosPedidos.getSelectionModel().getSelectedItems().isEmpty()) {
                    Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                    alertSeleccion.setTitle("Advertencia");
                    alertSeleccion.setHeaderText(null);
                    alertSeleccion.setContentText("Por favor elige un registro para modificar");
                    alertSeleccion.show();
                    return;
                }

                if (alert.showAndWait().get() == ButtonType.OK) {//solo si se acepto continuar
                    idDePedidoSeleccionado = tblDatosPedidos.getSelectionModel().getSelectedItem().getFolio_factura();
                    contenidoCboId_Proveedor = cboProveedor.getSelectionModel().getSelectedIndex();
                    contenidoCboId_Usuario = cboUsuario.getSelectionModel().getSelectedIndex();
                    contenidoTxtMonto = Float.parseFloat(txtMonto.getText());
                    contenidoFecha = java.sql.Date.valueOf(pickerFecha.getValue());
                    
                    facturaBD.updatePedido(idDePedidoSeleccionado, contenidoCboId_Proveedor,
                            contenidoCboId_Usuario, contenidoTxtMonto, contenidoFecha);
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
            alert.setContentText("El Pedido no se ha podido actualizar. Error al acceder a la base de datos");
            alert.show();
            ex.printStackTrace();
        }

    }
    
    private void eliminarPedido() {
        try {
            if (tblDatosPedidos.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de este Pedido?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosPedidos.getSelectionModel().getSelectedItem().getFolio_factura();
                facturaBD.deletePedido(ID);
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
            alert.setContentText("El Pedido no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    
    
    
    private boolean camposPorCompletar() {
        String proveedor = "";
        String usuario = "";
        String monto = txtMonto.getText();
        String fecha = pickerFecha.getValue().toString();

//        String rol =ROL_DEFAULT;
//        try{
//             rol = cboRolUsuario.getSelectionModel().getSelectedItem().toString();
//        }catch(Exception e){
//             rol =ROL_DEFAULT;
//             
//        }
        proveedor = cboProveedor.getSelectionModel().getSelectedItem().toString();
        usuario = cboUsuario.getSelectionModel().getSelectedItem().toString();

        if (proveedor.equals(PROVEEDOR_DEFAULT) || usuario.equals(USUARIO_DEFAULT) || monto.equals("") || fecha.equals("")) {
            return true;
        } else {
            return false;
        }

    }
    
    private void limpiarCampos() {
        txtFolioFactura.clear();
        cboProveedor.getSelectionModel().select(0);
        cboUsuario.getSelectionModel().select(0);
        txtMonto.clear();
        pickerFecha.setValue(null);
    }
    
    void agregarPedidoActivado() {
        agregarActivado = true;
        modificarActivado = false;

        limpiarCampos();
        if (agregarActivado) {
            lblAyuda.setText(AYUDA_AL_AGREGAR);

            btnAgregarPedido.setDisable(true);
            btnModificarPedido.setDisable(true);
            btnEliminarPedido.setDisable(true);
            btnFiltrarPedido.setDisable(true);
            
            
            btnCancelarPedido.setVisible(true);
            btnRegresarPedido.setVisible(false);
            btnGuardarInsercionPedido.setVisible(true);
            btnGuardarModificacionPedido.setVisible(false);

            txtFolioFactura.setEditable(false);
            cboProveedor.setEditable(true);
            cboProveedor.getSelectionModel().select(0);
            cboUsuario.setEditable(true);
            cboUsuario.getSelectionModel().select(0);
            txtMonto.setEditable(true);
            pickerFecha.setValue(null);
            pickerFecha.setEditable(true);

        } else {

        }
    }
    
    void modificarPedidoActivado() {
        modificarActivado = true;
        agregarActivado = false;

        limpiarCampos();
        if (modificarActivado) {
            lblAyuda.setText(AYUDA_AL_MODIFICAR);

            btnAgregarPedido.setDisable(true);
            btnModificarPedido.setDisable(true);
            btnEliminarPedido.setDisable(true);
            btnFiltrarPedido.setDisable(true);
            
            
            btnCancelarPedido.setVisible(true);
            btnRegresarPedido.setVisible(false);
            btnGuardarInsercionPedido.setVisible(false);
            btnGuardarModificacionPedido.setVisible(true);

            txtFolioFactura.setEditable(false);
            cboProveedor.setEditable(true);
            cboProveedor.getSelectionModel().select(0);
            cboUsuario.setEditable(true);
            cboUsuario.getSelectionModel().select(0);
            txtMonto.setEditable(true);
            pickerFecha.setValue(null);
            pickerFecha.setEditable(true);

        } else {

        }
    }
    
    void regresarBotonesAFormaOriginal() {
        modificarActivado = false;
        agregarActivado = false;
        filtrarActivado = false;

        btnAgregarPedido.setDisable(false);
        //btnAgregarUsuario.setStyle("fx-background-color: #222288");
        btnModificarPedido.setDisable(false);
        //btnModificarUsuario.setStyle("fx-background-color: #222288");
        btnEliminarPedido.setDisable(false);
        btnFiltrarPedido.setDisable(false);
        //btnRegresarUsuario.setDisable(false);

        btnRegresarPedido.setVisible(true);

        btnCancelarPedido.setVisible(false);
        btnGuardarInsercionPedido.setVisible(false);
        btnGuardarModificacionPedido.setVisible(false);

        txtFolioFactura.setEditable(false);
        cboProveedor.setEditable(false);
        cboProveedor.getSelectionModel().select(0);
        cboUsuario.setEditable(false);
        cboUsuario.getSelectionModel().select(0);
        txtMonto.setEditable(false);
        pickerFecha.setValue(null);
        pickerFecha.setEditable(false);

        lblAyuda.setText("");
    }
    
    void ManejadorFiltro() {

        // System.out.println("si entra al metodo");
        if (filtrarActivado) {

            //System.out.println("Si entra if");
            String folio = txtFolioFactura.getText();
            String proveedor = cboProveedor.getSelectionModel().getSelectedItem().toString();
            String usuario = cboUsuario.getSelectionModel().getSelectedItem().toString();
            String monto = txtMonto.getText();
            String fecha = "";
            System.out.println(fecha);


            if (proveedor.equals(PROVEEDOR_DEFAULT)) {
                proveedor = "";
            }else{
               proveedor = String.valueOf(obtenerIDdeProveedor(proveedor)); 
            }

            if (usuario.equals(USUARIO_DEFAULT)) {
                usuario = "";
            }else{
                usuario = String.valueOf(obtenerIDdeUsuario(usuario));
            }
            
            if(pickerFecha.getValue().toString().equals("")){
                fecha="";
            }else{
                fecha=pickerFecha.getValue().toString();
                System.out.println(fecha);
            }

            leerFiltrarTabla(folio, proveedor, usuario, monto, fecha);

        }

    }
    
    private int obtenerIDdeProveedor(String proveedor) {
        int idProveedor = 0;
        for (int i = 0; i < listaObjetosProveedores.size(); i++) {
            if (listaObjetosProveedores.get(i).getNombre_proveedor().equals(proveedor)) {
                idProveedor = listaObjetosProveedores.get(i).getId_proveedor();
            }

        }
        return idProveedor;
    }
    
    private int obtenerIDdeUsuario(String usuario) {
        int idUsuario = 0;
        for (int i = 0; i < listaObjetosUsuarios.size(); i++) {
            if (listaObjetosUsuarios.get(i).getNombre().equals(usuario)) {
                idUsuario = listaObjetosUsuarios.get(i).getId();
            }

        }
        return idUsuario;
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
     
     private void leerFiltrarTabla(String folio, String id_proveedor, String id_usuario, String Monto, String fecha) {
        
            llenarTabla(facturaBD.getPedidosFiltro2(folio, id_proveedor, id_usuario, Monto, fecha));
        
    }
     
    class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }
    }
    
    
    
}
