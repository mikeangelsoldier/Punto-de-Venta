/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.CategoriaBD;
import AccesoBD.ClienteBD;
import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.MarcaBD;
import AccesoBD.ProductoBD;
import AccesoBD.VentaBD;
import AccesoBD.DetalleVentaBD;
import AccesoBD.ProveedorBD;
import AccesoBD.SucursalBD;
import GeneradorReportes.GeneradorPDF;
import Modelo.Categoria;
import Modelo.Cliente;
import Modelo.DetalleVenta;
import Modelo.Marca;
import Modelo.Producto;
import Modelo.ProductoDetalleVenta;
import Modelo.Proveedor;
import Modelo.Sucursal;
import PuntoDeVenta.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.swing.JTextArea;

/**
 *
 * @author Mike
 */
public class ControladorVistaVentas implements Initializable {

    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    ClienteBD clienteBD;
    ProductoBD productoBD;
    CategoriaBD categoriaBD;
    MarcaBD marcaBD;
    VentaBD ventaBD;
    DetalleVentaBD detalleVentaBD;
    ProveedorBD proveedorDB;
    SucursalBD sucursalBD;

    private boolean busquedaClienteActivado, busquedaProductoActivado;

    private static String MARCA_DEFAULT = "Elegir marca";

    private ArrayList<Proveedor> listaObjetosProveedores;
    private ArrayList<Marca> listaObjetosMarcas;
    private ArrayList<Categoria> listaObjetosCategorias;
    private ArrayList<Producto> listaObjetosProductosBucadosPorCodigo;
    private ArrayList<Sucursal> listaSucursales;

    private ArrayList<String> formasPago = new ArrayList<String>();

    ObservableList<String> observableListFormasPago;

    private Producto objetoProductoAAgregar = new Producto();

    private boolean existenMarcas = false;
    private boolean existenProveedores = false;
    private boolean existenCategorias = false;

    private String importeParaParaPagarActual = "";

    //---------------------------------------------------vista venta
    @FXML
    private JFXTextField txtCodigoProducto,
            txtStockProducto,
            txtPrecioProducto,
            txtCantidadProductosAAgregar,
            txtTotalProductoAntesDeAgregar,
            txtImporte,
            txtCambio,
            txtSubtotalVenta,
            txtIvaVenta,
            txtTotalAPagarVenta,
            txtNumVenta,
            txtIdClienteVenta,
            txtNombreClienteVenta,
            txtRfcClienteVenta;

    @FXML
    private JFXTextArea txaDescripcionProductoVenta;

    @FXML
    private JFXButton btnNuevaVenta,
            btnGenerarVenta,
            btnImporteRecibido,
            btnCancelarVenta,
            btnAgregarProductoAVenta,
            btnQuitarProductoDeVenta,
            btnBuscarYElegirProductoParaVender,
            btnBuscarClienteParaVenta;

    @FXML
    private Label lblAyuda;

    @FXML
    private JFXComboBox cboFormaPago;

    @FXML
    private JFXDatePicker dpkFechaVenta;

    @FXML
    private TableView<ProductoDetalleVenta> tblDatosDetalleVenta;

    @FXML
    private TableColumn<ProductoDetalleVenta, Integer> tbcCantidadProductoDetalleVenta;

    @FXML
    private TableColumn<ProductoDetalleVenta, Double> tbcPrecioUnitarioProductoDetalleVenta,
            tbcTotalProductoDetalleVenta;

    @FXML
    private TableColumn<ProductoDetalleVenta, String> tbcCodigoProductoDetalleVenta,
            tbcDescripcionProductoDetalleVenta;

    @FXML
    private Pane panelPrincipalVentas, panelBotonesVentas, panelDatosProductoVenta,
            panelCantidadProductoVenta, panelNumVenta, panelTotalVenta, panelDatosCliente;

    //-----------------------------------------------busqueda cliente
    @FXML
    private JFXTextField txtIdClienteFiltroVenta,
            txtNombreClienteFiltroVenta,
            txtRfcClienteFiltroVenta;

    @FXML
    private JFXButton btnAgregarClienteAVentaFiltroVenta,
            btnClienteRegresarAVentaFiltroVenta;

    @FXML
    private TableView<Cliente> tblDatosClienteFiltroVenta;

    @FXML
    private TableColumn<Cliente, Integer> tbcIDBusquedaCliente;

    @FXML
    private TableColumn<Cliente, String> tbcNombreBusquedaCliente,
            tbcRFCBusquedaCliente,
            tbcTelefonoBusquedaCliente,
            tbcCorreoBusquedaCliente,
            tbcDireccionBusquedaCliente,
            tbcColoniaBusquedaCliente,
            tbcMunicipioBusquedaCliente,
            tbcCPBusquedaCliente,
            tbcEstadoBusquedaCliente;

    @FXML
    private Pane panelBuscarClientes;

    //----------------------------------------------busqueda producto
    @FXML
    private JFXTextField txtCodigoProductoFiltroVenta,
            txtDescripcionProductoFiltroVenta;

    @FXML
    private JFXComboBox cboMarcaProductoFiltroVenta;

    @FXML
    private JFXButton btnAgregarProductoAVentaFiltroVenta,
            btnProductoRegresarAVentaFiltroVenta;

    @FXML
    private TableView<Producto> tblDatosProductoFiltroVenta;

    @FXML
    private TableColumn<Producto, Integer> tbcIDProductoFiltroVenta,
            tbcStockProductoFiltroVenta,
            tbcStockMinimoProductoFiltroVenta;

    @FXML
    private TableColumn<Producto, String> tbcCodigoProductoFiltroVenta,
            tbcDescripcionProductoFiltroVenta,
            tbcMarcaProductoFiltroVenta,
            tbcUnidadProductoFiltroVenta,
            tbcCategoriaProductoFiltroVenta,
            tbcProveedorProductoFiltroVenta;

    @FXML
    private TableColumn<Producto, Double> tbcCostoProductoFiltroVenta,
            tbcPrecioProductoFiltroVenta;

    @FXML
    private Pane panelBuscarProductos;

    ManejadorFiltroKey manejador;

    ManejadorFiltroKeyCliente manejadorCliente;

    ManejadorFiltroKeyProducto manejadorProducto;

    ManejadorFormaPago manejadorFormaPago;

    @FXML
    private void btnAgregarClienteAVentaEvento(ActionEvent event) {
        //panel2Grupos.setVisible(false);
        agregarClienteAVenta();

    }

    @FXML
    private void btnAgregarProductoAVentaEvento(ActionEvent event) {
        //panel2Grupos.setVisible(false);
        agregarProductoAVistaVenta();

    }

    @FXML
    private void btnBuscarClientesEvento(ActionEvent event) {
        //panel2Grupos.setVisible(false);
        panelPrincipalVentas.setDisable(true);
        panelBuscarClientes.setVisible(true);
        iniciarVistaBusquedaClientes();

    }

    @FXML
    private void btnBuscarYElegirProductosEvento(ActionEvent event) {
        panelPrincipalVentas.setDisable(true);
        panelBuscarProductos.setVisible(true);
        iniciarVistaBusquedaProductos();

    }

    @FXML
    private void btnBuscarCodigoPorCodigoEvento(ActionEvent event) {
        colocarDescripcionDeProductoPorCodigo();

    }

    @FXML
    private void btnClienteRegresarAVentaEvento(ActionEvent event) {
        panelPrincipalVentas.setDisable(false);
        panelBuscarClientes.setVisible(false);

        txtIdClienteFiltroVenta.textProperty().removeListener(manejadorCliente);
        txtNombreClienteFiltroVenta.textProperty().removeListener(manejadorCliente);
        txtRfcClienteFiltroVenta.textProperty().removeListener(manejadorCliente);
    }

    @FXML
    private void btnProductoRegresarAVentaEvento(ActionEvent event) {
        panelPrincipalVentas.setDisable(false);
        panelBuscarProductos.setVisible(false);

        txtCodigoProductoFiltroVenta.textProperty().removeListener(manejadorProducto);
        txtDescripcionProductoFiltroVenta.textProperty().removeListener(manejadorProducto);
        cboMarcaProductoFiltroVenta.valueProperty().removeListener(manejadorProducto);
    }

    @FXML
    private void btnAgregarProductoADetalleVentaEvento(ActionEvent event) {
        agregarProductoADetalleDeVenta();
    }

    @FXML
    private void btnEliminarProductoDeDetalleVentaEvento(ActionEvent event) {
        eliminarProductoDeDetalleVenta();
    }

    @FXML
    private void btnCalcularCambioEvento(ActionEvent event) {
        calcularCambio();
    }

    @FXML
    private void btnNuevaVentaEvento(ActionEvent event) {
        btnNuevaVenta.setDisable(true);

        btnGenerarVenta.setDisable(false);
        btnImporteRecibido.setDisable(false);
        btnCancelarVenta.setDisable(false);

        deshabilitarComponentesVistaVentas(false);
        iniciarNuevaVenta();
    }

    @FXML
    private void btnGenerarVentaEvento(ActionEvent event) {
        generarVenta();
    }

    @FXML
    private void btnCancelarVentaEvento(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Realmente deseas Cancelar la actual venta?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            botonesVentaPosicionInicial();
            limpiarVentaCompleta();
            deshabilitarComponentesVistaVentas(true);

        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Se ha cancelado la operación");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelPrincipalVentas.setVisible(true);
        panelBuscarClientes.setVisible(false);
        panelBuscarProductos.setVisible(false);

        iniciarVistaVentas();
    }

    private void iniciarVistaVentas() {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        clienteBD = new ClienteBD(conectaBD_PuntoVenta.getConnection());
        productoBD = new ProductoBD(conectaBD_PuntoVenta.getConnection());

        categoriaBD = new CategoriaBD(conectaBD_PuntoVenta.getConnection());
        marcaBD = new MarcaBD(conectaBD_PuntoVenta.getConnection());
        productoBD = new ProductoBD(conectaBD_PuntoVenta.getConnection());
        proveedorDB = new ProveedorBD(conectaBD_PuntoVenta.getConnection());
        sucursalBD = new SucursalBD(conectaBD_PuntoVenta.getConnection());

        ventaBD = new VentaBD(conectaBD_PuntoVenta.getConnection());
        detalleVentaBD = new DetalleVentaBD(conectaBD_PuntoVenta.getConnection());

        listaObjetosProveedores = new ArrayList<Proveedor>();
        listaObjetosMarcas = new ArrayList<Marca>();
        listaObjetosCategorias = new ArrayList<Categoria>();

        busquedaProductoActivado = false;
        busquedaClienteActivado = false;

        //regresarBotonesAFormaOriginal();
        manejador = new ManejadorFiltroKey();

        //txtCantidadProductosAAgregar.textProperty().addListener( manejadorCantidadProducto);
        //txtCantidadProductosAAgregar.
        EventHandler<KeyEvent> handler1 = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                colocarPrecioPorProducto();
            }
        };

        txtCantidadProductosAAgregar.setOnKeyPressed(handler1);

        EventHandler<KeyEvent> handlerCodigoProducto = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                colocarDescripcionDeProductoPorCodigo();

            }
        };

        txtCodigoProducto.setOnKeyPressed(handlerCodigoProducto);

        //-------
        asignarCamposATableColumnsDeDetalleVenta();

        formasPago.add("EFECTIVO");
        formasPago.add("TARJETA");

        observableListFormasPago = FXCollections.observableList(formasPago);

        cboFormaPago.setItems(observableListFormasPago);
        cboFormaPago.getSelectionModel().select(0);

        manejadorFormaPago = new ManejadorFormaPago();
        cboFormaPago.valueProperty().addListener(manejadorFormaPago);

        dpkFechaVenta.setValue(LocalDate.now());

        deshabilitarComponentesVistaVentas(true);

        botonesVentaPosicionInicial();

    }

    private void deshabilitarComponentesVistaVentas(boolean deshabilitar) {
        if (deshabilitar) {
            panelDatosProductoVenta.setDisable(true);
            panelCantidadProductoVenta.setDisable(true);
            tblDatosDetalleVenta.setDisable(true);
            panelNumVenta.setDisable(true);
            panelTotalVenta.setDisable(true);
            panelDatosCliente.setDisable(true);

        } else {
            panelDatosProductoVenta.setDisable(false);
            panelCantidadProductoVenta.setDisable(false);
            tblDatosDetalleVenta.setDisable(false);
            panelNumVenta.setDisable(false);
            panelTotalVenta.setDisable(false);
            panelDatosCliente.setDisable(false);
        }
    }

    private void botonesVentaPosicionInicial() {
        btnNuevaVenta.setDisable(false);

        btnGenerarVenta.setDisable(true);
        btnImporteRecibido.setDisable(true);
        btnCancelarVenta.setDisable(true);

    }

    private void iniciarNuevaVenta() {
        //primero asignar el nuevo id a la venta
        int idVentaAnterior = ventaBD.getIdMayorDeUltimaVenta();
        int idCalculadoVentaActual = idVentaAnterior + 1;

        txtNumVenta.setText(String.valueOf(idCalculadoVentaActual));

    }

    private void asignarCamposATableColumnsDeDetalleVenta() {
        tbcCodigoProductoDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcDescripcionProductoDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcCantidadProductoDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("cantidadProductoDetalleVenta"));
        tbcPrecioUnitarioProductoDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("precioUnitarioProductoDetalleVenta"));
        tbcTotalProductoDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("totalPorProductoDetalleVenta"));
    }

    private void iniciarVistaBusquedaClientes() {

        panelPrincipalVentas.setDisable(true);
        panelBuscarClientes.setVisible(true);

        llenarTablaBusquedaCliente(clienteBD.getClientes());
        regresarBotonesVistaBusquedaClienteAFormaOriginal();

        manejadorCliente = new ManejadorFiltroKeyCliente();

        txtIdClienteFiltroVenta.textProperty().addListener(manejadorCliente);
        txtNombreClienteFiltroVenta.textProperty().addListener(manejadorCliente);
        txtRfcClienteFiltroVenta.textProperty().addListener(manejadorCliente);

    }

    private void iniciarVistaBusquedaProductos() {

        panelPrincipalVentas.setDisable(true);
        panelBuscarProductos.setVisible(true);

        //listaObjetosProveedores = new ArrayList<Proveedor>();
        //listaObjetosMarcas = new ArrayList<Marca>();
        //listaObjetosCategorias = new ArrayList<Categoria>();
        llenarCboMarcas();
        llenarListaCategorias();
        llenarListaProveedores();
        llenarListaSucursales();

        llenarTablaBusquedaProducto(productoBD.getProductos());
        regresarBotonesVistaBusquedaProductoAFormaOriginal();
        manejadorProducto = new ManejadorFiltroKeyProducto();

        txtCodigoProductoFiltroVenta.textProperty().addListener(manejadorProducto);
        txtDescripcionProductoFiltroVenta.textProperty().addListener(manejadorProducto);
        cboMarcaProductoFiltroVenta.valueProperty().addListener(manejadorProducto);
    }

    void regresarBotonesVistaBusquedaProductoAFormaOriginal() {
        //lblAyuda.setText("");
        //lblCodigoBarras.setText("");

        limpiarCamposVistaBusquedaProducto();

    }

    void regresarBotonesVistaBusquedaClienteAFormaOriginal() {
        limpiarCamposVistaBusquedaCliente();
    }

    private void limpiarCamposVistaBusquedaProducto() {
        txtCodigoProductoFiltroVenta.clear();
        txtDescripcionProductoFiltroVenta.clear();

        cboMarcaProductoFiltroVenta.getSelectionModel().select(0);
    }

    private void limpiarCamposTotalVenta() {
        txtSubtotalVenta.clear();
        txtIvaVenta.clear();

        txtTotalAPagarVenta.clear();
    }

    private void limpiarCamposProductoVistaVenta() {
        txtCodigoProducto.clear();
        txtStockProducto.clear();
        txaDescripcionProductoVenta.clear();
        txtPrecioProducto.clear();
        txtCantidadProductosAAgregar.clear();
        txtTotalProductoAntesDeAgregar.clear();

    }

    private void limpiarCamposClienteVistaVenta() {
        txtIdClienteVenta.clear();
        txtNombreClienteVenta.clear();
        txtRfcClienteVenta.clear();

    }

    private void limpiarCamposVistaBusquedaCliente() {
        txtIdClienteFiltroVenta.clear();
        txtNombreClienteFiltroVenta.clear();
        txtRfcClienteFiltroVenta.clear();

    }

    private void llenarCboMarcas() {
        cboMarcaProductoFiltroVenta.getItems().clear();
        this.listaObjetosMarcas = new ArrayList<Marca>(marcaBD.getMarcas());

        cboMarcaProductoFiltroVenta.getItems().add(MARCA_DEFAULT);
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            cboMarcaProductoFiltroVenta.getItems().add(listaObjetosMarcas.get(i).getMarca());
        }
        if (listaObjetosMarcas.size() > 0) {
            cboMarcaProductoFiltroVenta.setValue(listaObjetosMarcas.get(0).getMarca());
            existenMarcas = true;
        }
    }

    private void llenarListaCategorias() {
        this.listaObjetosCategorias = new ArrayList<Categoria>(categoriaBD.getCategorias());
    }

    private void llenarListaProveedores() {
        this.listaObjetosProveedores = new ArrayList<Proveedor>(proveedorDB.getProveedores());
    }

    private void llenarListaSucursales() {
        this.listaSucursales = new ArrayList<Sucursal>(sucursalBD.getSucursales());
    }

    private void llenarTablaBusquedaProducto(ArrayList<Producto> listaProductos) {

        for (int i = 0; i < listaProductos.size(); i++) {
            int id = listaProductos.get(i).getId();
            String codigo = listaProductos.get(i).getCodigo();
            String descripcion = listaProductos.get(i).getDescripcion();
            String marca = listaProductos.get(i).getMarca();
            double costo = listaProductos.get(i).getCosto();
            double precio = listaProductos.get(i).getPrecio();
            String presentacion = listaProductos.get(i).getPresentacion();
            int stock = listaProductos.get(i).getStock();
            int stock_minimo = listaProductos.get(i).getStock_minimo();
            int categoria = listaProductos.get(i).getCategoria();
            int proveedor = listaProductos.get(i).getProveedor();

            //System.out.println("FK categoria leida= " + categoria);
            //System.out.println("FK proveedor leid= " + proveedor);
            String nombreProvedor = "";
            String nombreCategoria = "";

            for (int j = 0; j < listaObjetosCategorias.size(); j++) {
                if (listaObjetosCategorias.get(j).getId_categoria() == categoria) {
                    nombreCategoria = listaObjetosCategorias.get(j).getNombre();
                    //System.out.println(nombreCategoria);
                    break;
                }
            }

            for (int j = 0; j < listaObjetosProveedores.size(); j++) {
                if (listaObjetosProveedores.get(j).getId_proveedor() == proveedor) {
                    nombreProvedor = listaObjetosProveedores.get(j).getNombre_proveedor();
                    //System.out.println(nombreProvedor);
                    break;
                }
            }

            Producto productoConNombreCategoriaYProveedor = new Producto();
            productoConNombreCategoriaYProveedor.setId(id);
            productoConNombreCategoriaYProveedor.setCodigo(codigo);
            productoConNombreCategoriaYProveedor.setDescripcion(descripcion);
            productoConNombreCategoriaYProveedor.setMarca(marca);
            productoConNombreCategoriaYProveedor.setCosto(costo);
            productoConNombreCategoriaYProveedor.setPrecio(precio);
            productoConNombreCategoriaYProveedor.setPresentacion(presentacion);
            productoConNombreCategoriaYProveedor.setStock(stock);
            productoConNombreCategoriaYProveedor.setStock_minimo(stock_minimo);
            productoConNombreCategoriaYProveedor.setNombreCategoria(nombreCategoria);
            productoConNombreCategoriaYProveedor.setNombreProveedor(nombreProvedor);

            //System.out.println("Categoria cambiada a "+productoConNombreCategoriaYProveedor.getNombreCategoria());
            //System.out.println("proveedor cambiada a "+productoConNombreCategoriaYProveedor.getNombreProveedor());
            listaProductos.set(i, productoConNombreCategoriaYProveedor);

        }

        tblDatosProductoFiltroVenta.getItems().clear();

        tbcIDProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcStockProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tbcStockMinimoProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("stock_minimo"));
        tbcCategoriaProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        tbcProveedorProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));

        tbcCodigoProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcDescripcionProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcMarcaProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tbcUnidadProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("presentacion"));

        tbcCostoProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("costo"));
        tbcPrecioProductoFiltroVenta.setCellValueFactory(new PropertyValueFactory<>("precio"));

        for (Producto producto : listaProductos) {
            tblDatosProductoFiltroVenta.getItems().add(producto);
        }
    }

    private void llenarTablaBusquedaCliente(ArrayList<Cliente> listaClientes) {
        tblDatosClienteFiltroVenta.getItems().clear();

        tbcIDBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNombreBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcRFCBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        tbcTelefonoBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tbcCorreoBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tbcDireccionBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tbcColoniaBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("colonia"));
        tbcMunicipioBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        tbcCPBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("cp"));
        tbcEstadoBusquedaCliente.setCellValueFactory(new PropertyValueFactory<>("estado"));

        for (Cliente cliente : listaClientes) {
            tblDatosClienteFiltroVenta.getItems().add(cliente);
        }
    }

    private void agregarClienteAVenta() {
        try {
            if (tblDatosClienteFiltroVenta.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas asignar este cliente a la venta actual?");

            if (alert.showAndWait().get() == ButtonType.OK) {

                String idCliente = String.valueOf(tblDatosClienteFiltroVenta.getSelectionModel().getSelectedItem().getId());
                String nombreCliente = tblDatosClienteFiltroVenta.getSelectionModel().getSelectedItem().getNombre();
                String rfcCliente = tblDatosClienteFiltroVenta.getSelectionModel().getSelectedItem().getRfc();

                txtIdClienteVenta.setText(idCliente);
                txtNombreClienteVenta.setText(nombreCliente);
                txtRfcClienteVenta.setText(rfcCliente);

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

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El Cliente no se ha podido agregar");
            alert.show();
        }
    }

    void agregarProductoAVistaVenta() {
        try {
            if (tblDatosProductoFiltroVenta.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas agregar este producto a la venta?");
            if (alert.showAndWait().get() == ButtonType.OK) {

                String codigoProducto = tblDatosProductoFiltroVenta.getSelectionModel().getSelectedItem().getCodigo();
                String descripion = tblDatosProductoFiltroVenta.getSelectionModel().getSelectedItem().getDescripcion();
                String stock = String.valueOf(tblDatosProductoFiltroVenta.getSelectionModel().getSelectedItem().getStock());
                String precio = String.valueOf(tblDatosProductoFiltroVenta.getSelectionModel().getSelectedItem().getPrecio());

                objetoProductoAAgregar = new Producto();
                objetoProductoAAgregar.setCodigo(codigoProducto);
                objetoProductoAAgregar.setDescripcion(descripion);
                objetoProductoAAgregar.setStock(Integer.parseInt(stock));
                objetoProductoAAgregar.setPrecio(Double.parseDouble(precio));

                txtCodigoProducto.setText(objetoProductoAAgregar.getCodigo());
                txaDescripcionProductoVenta.setText(objetoProductoAAgregar.getDescripcion());
                txtStockProducto.setText(String.valueOf(objetoProductoAAgregar.getStock()));
                txtPrecioProducto.setText(String.valueOf(objetoProductoAAgregar.getPrecio()));

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

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El Producto no se ha agregar");
            alert.show();
        }
    }

    private void colocarDescripcionDeProductoPorCodigo() {
        String codigoProductoABuscar = txtCodigoProducto.getText();

        String codigoEncontrado = "";
        String DescripcionEncontrada = "";
        String precioEncontrado = "";
        String stockEncontrado = "";

        if (existeProducto(codigoProductoABuscar)) {
            for (int i = 0; i < listaObjetosProductosBucadosPorCodigo.size(); i++) {
                codigoEncontrado = listaObjetosProductosBucadosPorCodigo.get(i).getCodigo();
                DescripcionEncontrada = listaObjetosProductosBucadosPorCodigo.get(i).getDescripcion();
                precioEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getPrecio());
                stockEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getStock());

                objetoProductoAAgregar = new Producto();
                objetoProductoAAgregar.setCodigo(codigoEncontrado);
                objetoProductoAAgregar.setDescripcion(DescripcionEncontrada);
                objetoProductoAAgregar.setStock(Integer.parseInt(stockEncontrado));
                objetoProductoAAgregar.setPrecio(Double.parseDouble(precioEncontrado));

                txtCodigoProducto.setText(objetoProductoAAgregar.getCodigo());
                txaDescripcionProductoVenta.setText(objetoProductoAAgregar.getDescripcion());
                txtStockProducto.setText(String.valueOf(objetoProductoAAgregar.getStock()));
                txtPrecioProducto.setText(String.valueOf(objetoProductoAAgregar.getPrecio()));

            }
        }
    }

    private boolean existeProducto(String codigoProductoABuscar) {

        boolean codigoProductoExiste = false;

        if (txtCodigoProducto.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Primero escribe un codigo, despues preciona tecla ENTER para buscarlo \n o elige uno del catalogo de productos");
            alert.show();
            return false;
        }

        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        productoBD = new ProductoBD(conectaBD_PuntoVenta.getConnection());
        listaObjetosProductosBucadosPorCodigo = new ArrayList<Producto>(productoBD.getProductosPorCodigo(codigoProductoABuscar));

        if (!listaObjetosProductosBucadosPorCodigo.isEmpty()) {//si existe un producto al menos

            codigoProductoExiste = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Codigo Inexistente");
            alert.setContentText("No existe ningun producto registrado con el codigo " + codigoProductoABuscar);
            alert.show();
            codigoProductoExiste = false;
            limpiarCamposProductoVistaVenta();
            txtCodigoProducto.setText(codigoProductoABuscar);
        }

        return codigoProductoExiste;
    }

    class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            //ManejadorFiltro();
        }
    }

    class ManejadorFiltroKeyCliente implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltroCliente();
        }
    }

    class ManejadorFormaPago implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            quitarEfectivoRecibidoYCambio();
        }
    }

    class ManejadorFiltroKeyProducto implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltroProducto();
        }
    }

    private static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    private static boolean isDouble(String cadena) {

        boolean resultado;

        try {
            Double.parseDouble(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    private void colocarPrecioPorProducto() {

        if (txtStockProducto.getText().equals("")) {

            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Primero debe de seleccionar un producto para ingresar una cantidad");
            alertStockProducto.show();
            return;
        }

        if (txtCantidadProductosAAgregar.getText().equals("")) {

            Alert alertStockProducto = new Alert(Alert.AlertType.INFORMATION);
            alertStockProducto.setTitle("Informacion");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Escribe una cantidad");
            alertStockProducto.show();
            return;
        }

        if (isNumeric(txtCantidadProductosAAgregar.getText()) == false) {
            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("Se debe de escribir una cantidad numerica");
            alertStockProducto.show();
            return;
        }

        if (txtStockProducto.getText().equals("0")) {
            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Ya no se cuenta con existencias de este producto, revisa el catalogo de producto o el almacen");
            alertStockProducto.show();
            return;
        }

        try {

            int stockDelProducto = Integer.valueOf(txtStockProducto.getText());
            int cantidadAAgregar = Integer.valueOf(txtCantidadProductosAAgregar.getText());

            if (cantidadAAgregar > stockDelProducto) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("¡¡¡¡La cantidad a vender debe ser menor al stock!!!! \n Solo se cuenta con " + stockDelProducto + " existencias de este producto");
                alert.show();
                return;
            }

            double precioProductoAAgregar = Double.valueOf(txtPrecioProducto.getText());
            double totalProductoAAgregarAVenta = (precioProductoAAgregar * cantidadAAgregar);

            txtTotalProductoAntesDeAgregar.setText(String.valueOf(totalProductoAAgregarAVenta));

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("");
            alert.show();
        }
    }

    private void agregarProductoADetalleDeVenta() {
        if (fueCorrectoElCalculoDelProducto()) {
            try {

                if (productoExisteEnLaTablaDetalleVenta(txtCodigoProducto.getText()) == false) {

                    ProductoDetalleVenta productoDetalleVenta = new ProductoDetalleVenta();
                    productoDetalleVenta.setCodigo(objetoProductoAAgregar.getCodigo());
                    productoDetalleVenta.setDescripcion(txaDescripcionProductoVenta.getText());
                    productoDetalleVenta.setCantidadProductoDetalleVenta(Integer.valueOf(txtCantidadProductosAAgregar.getText()));
                    productoDetalleVenta.setPrecioUnitarioProductoDetalleVenta(Double.parseDouble(txtPrecioProducto.getText()));
                    productoDetalleVenta.setTotalPorProductoDetalleVenta(Double.parseDouble(txtTotalProductoAntesDeAgregar.getText()));

                    tblDatosDetalleVenta.getItems().add(productoDetalleVenta);
                    calcularTotalDeVenta();
                    limpiarCamposProductoVistaVenta();
                    quitarEfectivoRecibidoYCambio();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Información");
                    alerta.setHeaderText("Ese producto ya a sido agredado a la venta");
                    alerta.setContentText("");
                    alerta.show();
                    limpiarCamposProductoVistaVenta();
                }

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Información");
                alert.setHeaderText("Un Error a Ocurrido ");
                alert.setContentText("");
                alert.show();
            }
        }
    }

    private boolean fueCorrectoElCalculoDelProducto() {

        if (txtStockProducto.getText().equals("")) {

            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Primero debe de seleccionar un producto para ingresar una cantidad");
            alertStockProducto.show();

            return false;
        }

        if (txtCantidadProductosAAgregar.getText().equals("")) {

            Alert alertStockProducto = new Alert(Alert.AlertType.INFORMATION);
            alertStockProducto.setTitle("Informacion");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Escribe una cantidad");
            alertStockProducto.show();
            return false;
        }

        if (isNumeric(txtCantidadProductosAAgregar.getText()) == false) {
            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("Se debe de escribir una cantidad numerica");
            alertStockProducto.show();
            return false;
        }

        if (txtStockProducto.getText().equals("0")) {
            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Ya no se cuenta con existencias de este producto, revisa el catalogo de producto o el almacen");
            alertStockProducto.show();
            return false;
        }

        try {

            int stockDelProducto = Integer.valueOf(txtStockProducto.getText());
            int cantidadAAgregar = Integer.valueOf(txtCantidadProductosAAgregar.getText());

            if (cantidadAAgregar > stockDelProducto) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("¡¡¡¡La cantidad a vender debe ser menor al stock!!!! \n Solo se cuenta con " + stockDelProducto + " existencias de este producto");
                alert.show();
                return false;
            }

            double precioProductoAAgregar = Double.valueOf(txtPrecioProducto.getText());
            double totalProductoAAgregarAVenta = (precioProductoAAgregar * cantidadAAgregar);

            txtTotalProductoAntesDeAgregar.setText(String.valueOf(totalProductoAAgregarAVenta));
            return true;
        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("");
            alert.show();
            return false;
        }
    }

    private boolean productoExisteEnLaTablaDetalleVenta(String codigo) {
        boolean existe = false;

        for (int i = 0; i < tblDatosDetalleVenta.getItems().size(); i++) {
            ProductoDetalleVenta productoDetalleVenta = tblDatosDetalleVenta.getItems().get(i);
            String codigoDeProducto = tblDatosDetalleVenta.getItems().get(i).getCodigo();

            if (codigoDeProducto.equals(codigo)) {
                existe = true;
            }
        }
        return existe;
    }

    private void calcularTotalDeVenta() {
        String totalVenta = "";
        Double subTotal = 0.0;

        if (!(tblDatosDetalleVenta.getItems().isEmpty())) {
            for (int i = 0; i < tblDatosDetalleVenta.getItems().size(); i++) {
                Double precioTotalPorProducto = tblDatosDetalleVenta.getItems().get(i).getTotalPorProductoDetalleVenta();
                subTotal += precioTotalPorProducto;
            }

            Double iva = (subTotal) * (.16);
            Double total = subTotal + iva;

            DecimalFormat df = new DecimalFormat("#.00");

            iva = Double.valueOf(df.format(iva));
            total = Double.valueOf(df.format(total));

            txtSubtotalVenta.setText(String.valueOf(subTotal));
            txtIvaVenta.setText(String.valueOf(iva));
            txtTotalAPagarVenta.setText(String.valueOf(total));

        }

    }

    private void eliminarProductoDeDetalleVenta() {
        try {
            if (tblDatosDetalleVenta.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el producto selecionado de la venta actual?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int seleccion = tblDatosDetalleVenta.getSelectionModel().getSelectedIndex();

                tblDatosDetalleVenta.getItems().remove(seleccion);

                Alert alertaEliminacion = new Alert(Alert.AlertType.INFORMATION);
                alertaEliminacion.setHeaderText(null);
                alertaEliminacion.setTitle("Información");
                alertaEliminacion.setContentText("La operación se ha realizado con éxito");
                alertaEliminacion.show();

                calcularTotalDeVenta();
                quitarEfectivoRecibidoYCambio();

                if (tblDatosDetalleVenta.getItems().isEmpty()) {
                    quitarEfectivoRecibidoYCambio();
                    limpiarCamposTotalVenta();
                }

            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Se ha cancelado la operación");
                alert.show();
            }

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("El Producto no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private void calcularCambio() {

        if (!tblDatosDetalleVenta.getItems().isEmpty()) {

            if (!(cboFormaPago.getSelectionModel().getSelectedItem().toString().equals("EFECTIVO"))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Selecciona la forma de pago EFECTIVO para poder calcular el cambio");
                alert.showAndWait();

                return;
            }

            if (elEfectivoIngresadoEsCorrecto()) {

                double efectivoRecibido = 0.0;
                double totalAPagar = 0.0;
                double cambio = 0.0;
                efectivoRecibido = Double.parseDouble(importeParaParaPagarActual);
                totalAPagar = Double.parseDouble(txtTotalAPagarVenta.getText());

                if (efectivoRecibido < totalAPagar) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("EL dinero ingresado no es suficiente para pagar la venta actual "
                            + "\n"
                            + "El total a pagar hasta el momento es de $" + totalAPagar + ""
                            + "\n Se requieren mas de $" + efectivoRecibido + " para pagar");
                    alert.showAndWait();
                    return;
                }

                DecimalFormat df = new DecimalFormat("#.00");

                cambio = efectivoRecibido - totalAPagar;

                cambio = Double.valueOf(df.format(cambio));
                txtImporte.setText(importeParaParaPagarActual);
                txtCambio.setText(String.valueOf(cambio));

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Por favor escriba una cantidad correcta");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Aun no se han agregado productos a la venta");
            alert.showAndWait();

        }

    }

    private boolean elEfectivoIngresadoEsCorrecto() {
        boolean ingresoCorrecto = false;

        TextInputDialog dialog = new TextInputDialog("100");
        dialog.setTitle("Importe reibido");
        dialog.setHeaderText("Ingresar pago");
        dialog.setContentText("Ingresa el efectivo recibido $");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {

            if (isNumeric(result.get())) {
                ingresoCorrecto = true;
                importeParaParaPagarActual = result.get();
            } else if (isDouble(result.get())) {
                ingresoCorrecto = true;
                importeParaParaPagarActual = result.get();
            } else {
                ingresoCorrecto = false;
                importeParaParaPagarActual = "";
            }
        }

        return ingresoCorrecto;
    }

    private void quitarEfectivoRecibidoYCambio() {
        txtImporte.clear();
        txtCambio.clear();
    }

    private void generarVenta() {
        if (camposNecesariosParaVentaEstanCompletos()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Realmente deseas Finalzar y registrar la actual venta?");
            if (alert.showAndWait().get() == ButtonType.OK) {

                if (cboFormaPago.getSelectionModel().getSelectedItem().toString().equals("EFECTIVO")) {
                    Alert alertPago = new Alert(Alert.AlertType.INFORMATION);
                    alertPago.setTitle("Información");
                    alertPago.setHeaderText("Cambio de la venta");
                    alertPago.setContentText("Se recibio $" + txtImporte.getText() + "\nEl cambio es de $" + txtCambio.getText());
                    alertPago.showAndWait();
                }

                guardarVenta();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("La venta se ha realizado con éxito");
                alert.showAndWait();

            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Se ha cancelado la operación");
                alert.show();
            }

        } else {
            System.out.println("Existen campos por completar");
        }
    }

    private boolean camposNecesariosParaVentaEstanCompletos() {
        boolean camposNecesariosCompletos = false;
        String fecha = dpkFechaVenta.getValue().format(DateTimeFormatter.ISO_DATE);
        String subtotal = txtSubtotalVenta.getText();
        String iva = txtIvaVenta.getText();
        String total = txtTotalAPagarVenta.getText();
        String formaPago = cboFormaPago.getSelectionModel().getSelectedItem().toString();
        String clienteDeVenta = txtIdClienteVenta.getText();

        int idcliente = 0;
        int idUsuario = 0;//BUSCAR EL ID DEL USUARIO ACTUAL EL QUE INICIO SESION

        if (fecha.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Faltan Datos");
            alert.setHeaderText(null);
            alert.setContentText("Escriba una fecha correcta para la venta actual");
            alert.showAndWait();

            camposNecesariosCompletos = false;
            return camposNecesariosCompletos;
        }

        if (subtotal.equals("") || iva.equals("") || total.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Faltan Datos");
            alert.setHeaderText(null);
            alert.setContentText("No hay prodcuctos agregados a la venta actual");
            alert.showAndWait();

            camposNecesariosCompletos = false;
            return camposNecesariosCompletos;
        }

        if (clienteDeVenta.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cliente faltante");
            alert.setHeaderText(null);
            alert.setContentText("Es necesario indicar un cliente al cual vender, \n por defecto hay un cliente general");
            alert.showAndWait();

            camposNecesariosCompletos = false;
            return camposNecesariosCompletos;
        }

        if (cboFormaPago.getSelectionModel().getSelectedItem().toString().equals("EFECTIVO")) {
            //calcularCambio();

            if (txtImporte.getText().equals("") || txtCambio.getText().equals("")) {
                calcularCambio();

                if (txtImporte.getText().equals("") || txtCambio.getText().equals("")) {

                    camposNecesariosCompletos = false;
                    return camposNecesariosCompletos;
                }
            }
        }

        //si llego hasta aqui entonces todos los campos necesarios estan completos
        camposNecesariosCompletos = true;

        return camposNecesariosCompletos;
    }

    private void guardarVenta() {
        //primero almaceno la venta
        String fecha_venta = dpkFechaVenta.getValue().format(DateTimeFormatter.ISO_DATE);
        double subtotal = Double.parseDouble(txtSubtotalVenta.getText());
        double iva = Double.parseDouble(txtIvaVenta.getText());
        double total = Double.parseDouble(txtTotalAPagarVenta.getText());
        String formaPago = cboFormaPago.getSelectionModel().getSelectedItem().toString();
        int id_usuario = Integer.parseInt(loginMeta.idUsuario);
        int id_cliente = Integer.parseInt(txtIdClienteVenta.getText());

        try {

            ventaBD.addVenta(fecha_venta, subtotal, iva, total, formaPago, id_usuario, id_cliente);

            //despues obtengo el id de esta venta para almacenar los detalles
            int ventaActual = ventaBD.getIdMayorDeUltimaVenta();
            if (!(tblDatosDetalleVenta.getItems().isEmpty())) {
                for (int i = 0; i < tblDatosDetalleVenta.getItems().size(); i++) {
                    //Double precioTotalPorProducto = tblDatosDetalleVenta.getItems().get(i).getTotalPorProductoDetalleVenta();
                    String codigoBarrasDelProductoActual = tblDatosDetalleVenta.getItems().get(i).getCodigo();
                    int idProductoActual = productoBD.getIDDeProductoDelCodigoTal(codigoBarrasDelProductoActual);
                    System.out.println("El producto con el codigo " + codigoBarrasDelProductoActual + " tiene el id de producto " + idProductoActual);

                    int cantidad = tblDatosDetalleVenta.getItems().get(i).getCantidadProductoDetalleVenta();
                    double importe = tblDatosDetalleVenta.getItems().get(i).getTotalPorProductoDetalleVenta();

                    detalleVentaBD.addDetalleDeVenta(ventaActual, idProductoActual, cantidad, importe);

                    //depues descuento del stock lo vendido
                    productoBD.reducirExistencia(codigoBarrasDelProductoActual, cantidad);

                }

                crearTicket();
                botonesVentaPosicionInicial();
                limpiarVentaCompleta();
                deshabilitarComponentesVistaVentas(true);
            }

            //despues limpio todo
            limpiarVentaCompleta();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVistaVentas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void crearTicket() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Desea generar el ticket de la venta actual?");
        if (alert.showAndWait().get() == ButtonType.OK) {

            Alert alertEleccion = new Alert(Alert.AlertType.CONFIRMATION);
            alertEleccion.setTitle("Eleccion de forma de guardado");
            alertEleccion.setHeaderText("Elige una ruta para almacenar el ticket");
            alertEleccion.setContentText("Deseas guardar el ticket en la ruta por defecto\no deseas elegirla tu mismo");

            ButtonType buttonTypeOne = new ButtonType("Guardar en ruta por defecto");
            ButtonType buttonTypeTwo = new ButtonType("Elegir ruta de guardado");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alertEleccion.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

            Optional<ButtonType> result = alertEleccion.showAndWait();
            if (result.get() == buttonTypeOne) {
                guardarTicket("src/PDF/Folio #" + txtNumVenta.getText() + ".pdf");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("Se ha generado el ticket de la venta " + txtNumVenta.getText());
                alert.showAndWait();

            } else if (result.get() == buttonTypeTwo) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Elije donde guardar el archivo");
                    String ruta;
                    try {
                        ruta = fileChooser.showSaveDialog(null).getPath();
                    } catch (Exception e) {
                        ruta = "";
                    }

                    if (!ruta.equals("")) {
                        guardarTicket(ruta + ".pdf");

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Información");
                        alert.setContentText("Se ha generado el ticket de la venta " + txtNumVenta.getText());
                        alert.showAndWait();
                    }

                } catch (Exception ex) {
                }
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
            alert.setContentText("Se ha cancelado la operación");
            alert.show();
        }

    }

    private void guardarTicket(String ruta) {

        String fecha_venta = dpkFechaVenta.getValue().format(DateTimeFormatter.ISO_DATE);
        double subtotal = Double.parseDouble(txtSubtotalVenta.getText());
        double iva = Double.parseDouble(txtIvaVenta.getText());
        double total = Double.parseDouble(txtTotalAPagarVenta.getText());
        String formaPago = cboFormaPago.getSelectionModel().getSelectedItem().toString();
        int id_usuario = Integer.parseInt(loginMeta.idUsuario);
        int id_cliente = Integer.parseInt(txtIdClienteVenta.getText());

        Calendar calendario = Calendar.getInstance();
        String hora = calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE) + " hrs.";

        String nombreSucursal = "";
        String sucursal = "";
        String telefono = "";
        String correo = "";
        String direccion = "";
        String colonia = "";
        String municipio = "";
        String cp = "";
        String estado = "";

        if (listaSucursales.size() > 0) {
            nombreSucursal = listaSucursales.get(0).getNombre_sucursal();
            sucursal = listaSucursales.get(0).getSucursal();
            telefono = listaSucursales.get(0).getTelefono();
            correo = listaSucursales.get(0).getCorreo();
            direccion = listaSucursales.get(0).getDireccion();
            colonia = listaSucursales.get(0).getColonia();
            municipio = listaSucursales.get(0).getMunicipio();
            cp = listaSucursales.get(0).getCp();
            estado = listaSucursales.get(0).getEstado();
        }

        String encabezado = "\t   * " + nombreSucursal + " *\n\n"
                + "Plomeria y ferreteria\n"
                + "Arturo Gonzáles Araujo\n"
                + direccion + " "
                + "Col. "+colonia + "\n"
                + "Código Postal " + cp + ", " + municipio + ", " + estado + "\n"
                + " Quejas y sugerencias " + telefono + "\n";

        String cliente = "----------------------" + "\nID Cliente: " + id_cliente + "\nNombre: "
                + txtNombreClienteVenta.getText() + " "
                + "\nAtendio \nID Usuario: " + id_usuario
                + "\nNombre: " + loginMeta.nombreUsuario
                + "\n----------------------\n";
        String productos = "";

        if (!(tblDatosDetalleVenta.getItems().isEmpty())) {
            for (int i = 0; i < tblDatosDetalleVenta.getItems().size(); i++) {
                Double precioTotalPorProducto = tblDatosDetalleVenta.getItems().get(i).getTotalPorProductoDetalleVenta();
                productos += tblDatosDetalleVenta.getItems().get(i).getCodigo() + "     ";
                productos += tblDatosDetalleVenta.getItems().get(i).getDescripcion() + "     ";
                productos += tblDatosDetalleVenta.getItems().get(i).getPrecioUnitarioProductoDetalleVenta()+ "     ";
                productos += tblDatosDetalleVenta.getItems().get(i).getCantidadProductoDetalleVenta() + "     ";
                productos += tblDatosDetalleVenta.getItems().get(i).getTotalPorProductoDetalleVenta() + "     ";
                productos += "\n";
            }
        }

        String pBaja1 = "\n\t  Subtotal ** $" + subtotal + "\n"
                + "\n\t  IVA ** $" + iva + "\n"
                + "\n\t  Total ** $" + total + "\n\n"
                + "     " + tblDatosDetalleVenta.getItems().size() + " artículo(s) vendido(s) ";
        String pBaja2 = "\n\n ||| ||||||| ||| ||| |||||| |||| ||"
                + "\n\nForma de pago: " + formaPago + "\n"
                + "  " + fecha_venta + " " + hora + "\n"
                + "\n      *** Copia del cliente ***"
                + "\n ************ Más por menos *********";
        //System.out.println(encabezado);
        //System.out.println(cliente);
        //System.out.println(productos);
        //System.out.println(pBaja1);
        //System.out.println(pBaja2);
        GeneradorPDF b = new GeneradorPDF();

        b.crearTicket(encabezado, cliente, productos, pBaja1, pBaja2,
                ruta);
        String fileLocal = ruta;

        try {
            File path = new File(fileLocal);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("error");
            alert.setContentText("No se pudo encontrar el archivo");
            alert.showAndWait();

            e.printStackTrace();
        }
    }

    private void limpiarVentaCompleta() {
        quitarEfectivoRecibidoYCambio();
        limpiarCamposProductoVistaVenta();
        limpiarCamposTotalVenta();
        limpiarCamposClienteVistaVenta();
        tblDatosDetalleVenta.getItems().clear();
        txtNumVenta.clear();
    }

    void ManejadorFiltroProducto() {
        String codigoProducto = txtCodigoProductoFiltroVenta.getText();
        String descripcionProducto = txtDescripcionProductoFiltroVenta.getText();
        String nombreMarcaProducto = "";
        nombreMarcaProducto = cboMarcaProductoFiltroVenta.getSelectionModel().getSelectedItem().toString();

        if (nombreMarcaProducto.equals(MARCA_DEFAULT)) {
            nombreMarcaProducto = "";
        }

        llenarTablaBusquedaProducto(productoBD.getProductosFiltro4(codigoProducto, descripcionProducto, nombreMarcaProducto));

    }

    void ManejadorFiltroCliente() {
        String idCliente = txtIdClienteFiltroVenta.getText();
        String nombre = txtNombreClienteFiltroVenta.getText();
        String rfc = txtRfcClienteFiltroVenta.getText();

        llenarTablaBusquedaCliente(clienteBD.getClientesFiltro4(idCliente, nombre, rfc));
    }

}
