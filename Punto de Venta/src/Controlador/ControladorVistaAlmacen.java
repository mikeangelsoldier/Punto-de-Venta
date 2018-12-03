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
import Modelo.Categoria;
import Modelo.Cliente;
import Modelo.DetalleVenta;
import Modelo.Marca;
import Modelo.Producto;
import Modelo.ProductoDetalleVenta;
import Modelo.Proveedor;
import PuntoDeVenta.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.swing.JTextArea;

/**
 *
 * @author Mike
 */
public class ControladorVistaAlmacen implements Initializable {

    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    ProductoBD productoBD;
    CategoriaBD categoriaBD;
    MarcaBD marcaBD;

    private boolean busquedaProductoActivado;

    private static String MARCA_DEFAULT = "Elegir marca";

    private ArrayList<Proveedor> listaObjetosProveedores;
    private ArrayList<Marca> listaObjetosMarcas;
    private ArrayList<Categoria> listaObjetosCategorias;
    private ArrayList<Producto> listaObjetosProductosBucadosPorCodigo;

    private Producto objetoProductoAModificarExistencia = new Producto();

    private boolean existenMarcas = false;
    private boolean existenProveedores = false;
    private boolean existenCategorias = false;

    //---------------------------------------------------vista almacen
    @FXML
    private JFXTextField txtCodigoProducto,
            txtStockProducto,
            txtCostoProducto,
            txtCantidad;

    @FXML
    private JFXTextArea txaDescripcionProducto;

    @FXML
    private JFXButton btnBuscarYElegirProducto, btnAumentarStock, btnReducirStock, btnCancelar;

    @FXML
    private Pane panelPrincipalAlmacen;

    //----------------------------------------------busqueda producto
    @FXML
    private JFXTextField txtCodigoProductoFiltro,
            txtDescripcionProductoFiltro;

    @FXML
    private JFXComboBox cboMarcaProductoFiltro;

    @FXML
    private JFXButton btnSeleccionarProducto,
            btnRegresarAVistaPrincipal;

    @FXML
    private TableView<Producto> tblDatosProductoFiltro;

    @FXML
    private TableColumn<Producto, Integer> tbcIDProductoFiltro,
            tbcStockProductoFiltro,
            tbcStockMinimoProductoFiltro;

    @FXML
    private TableColumn<Producto, String> tbcCodigoProductoFiltro,
            tbcDescripcionProductoFiltro,
            tbcMarcaProductoFiltro,
            tbcUnidadProductoFiltro,
            tbcCategoriaProductoFiltro,
            tbcProveedorProductoFiltro;

    @FXML
    private TableColumn<Producto, Double> tbcCostoProductoFiltro,
            tbcPrecioProductoFiltro;

    @FXML
    private Pane panelBuscarProductos;

    ManejadorFiltroKeyProducto manejadorProducto;

    @FXML
    private void btnSeleccionarProductoEvento(ActionEvent event) {
        selecionarProducto();
    }

    @FXML
    private void btnBuscarYElegirProductosEvento(ActionEvent event) {
        panelPrincipalAlmacen.setDisable(true);
        panelBuscarProductos.setVisible(true);
        iniciarVistaBusquedaProductos();
    }

    @FXML
    private void btnBuscarProductoPorCodigoEvento(ActionEvent event) {
        colocarDescripcionDeProductoPorCodigo();

    }

    @FXML
    private void btnRegresarAVistaPrincipalEvento(ActionEvent event) {
        panelPrincipalAlmacen.setDisable(false);
        panelBuscarProductos.setVisible(false);

        txtCodigoProductoFiltro.textProperty().removeListener(manejadorProducto);
        txtDescripcionProductoFiltro.textProperty().removeListener(manejadorProducto);
        cboMarcaProductoFiltro.valueProperty().removeListener(manejadorProducto);
    }

    @FXML
    private void btnCancelarEvento(ActionEvent event) {
        botonesAlmacenPosicionInicial();
        limpiarCamposProductoVistaAlmacen();
    }

    @FXML
    private void btnAumentarStockEvento(ActionEvent event) {
        aumentarStockDeProducto();
    }

    @FXML
    private void btnReducirStockEvento(ActionEvent event) {
        reducirStockDeProducto();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelPrincipalAlmacen.setVisible(true);

        panelBuscarProductos.setVisible(false);

        iniciarVistaPrincipalAlmacen();
    }

    private void iniciarVistaPrincipalAlmacen() {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        productoBD = new ProductoBD(conectaBD_PuntoVenta.getConnection());

        categoriaBD = new CategoriaBD(conectaBD_PuntoVenta.getConnection());
        marcaBD = new MarcaBD(conectaBD_PuntoVenta.getConnection());

        listaObjetosProveedores = new ArrayList<Proveedor>();
        listaObjetosMarcas = new ArrayList<Marca>();
        listaObjetosCategorias = new ArrayList<Categoria>();

        EventHandler<KeyEvent> handler1 = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                cantidadProductoIngresadaCorrecta();
            }
        };

        txtCantidad.setOnKeyPressed(handler1);

        busquedaProductoActivado = false;

        EventHandler<KeyEvent> handlerCodigoProducto = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                colocarDescripcionDeProductoPorCodigo();
            }
        };

        txtCodigoProducto.setOnKeyPressed(handlerCodigoProducto);
    }

    private void botonesAlmacenPosicionInicial() {

    }

    private void iniciarVistaBusquedaProductos() {

        panelPrincipalAlmacen.setDisable(true);
        panelBuscarProductos.setVisible(true);

        listaObjetosProveedores = new ArrayList<Proveedor>();
        //listaObjetosMarcas = new ArrayList<Marca>();
        listaObjetosCategorias = new ArrayList<Categoria>();

        llenarCboMarcas();

        llenarTablaBusquedaProducto(productoBD.getProductos());
        regresarBotonesVistaBusquedaProductoAFormaOriginal();
        manejadorProducto = new ManejadorFiltroKeyProducto();

        txtCodigoProductoFiltro.textProperty().addListener(manejadorProducto);
        txtDescripcionProductoFiltro.textProperty().addListener(manejadorProducto);
        cboMarcaProductoFiltro.valueProperty().addListener(manejadorProducto);
    }

    void regresarBotonesVistaBusquedaProductoAFormaOriginal() {
        limpiarCamposVistaBusquedaProducto();

    }

    private void limpiarCamposVistaBusquedaProducto() {
        txtCodigoProductoFiltro.clear();
        txtDescripcionProductoFiltro.clear();
        cboMarcaProductoFiltro.getSelectionModel().select(0);
    }

    private void limpiarCamposProductoVistaAlmacen() {
        txtCodigoProducto.clear();
        txtStockProducto.clear();
        txaDescripcionProducto.clear();
        txtCostoProducto.clear();
        txtCantidad.clear();
    }

    private void llenarCboMarcas() {
        cboMarcaProductoFiltro.getItems().clear();
        this.listaObjetosMarcas = new ArrayList<Marca>(marcaBD.getMarcas());

        cboMarcaProductoFiltro.getItems().add(MARCA_DEFAULT);
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            cboMarcaProductoFiltro.getItems().add(listaObjetosMarcas.get(i).getMarca());
        }
        if (listaObjetosMarcas.size() > 0) {
            cboMarcaProductoFiltro.setValue(listaObjetosMarcas.get(0).getMarca());
            existenMarcas = true;
        }
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

            String nombreProvedor = "";
            String nombreCategoria = "";

            for (int j = 0; j < listaObjetosCategorias.size(); j++) {
                if (listaObjetosCategorias.get(j).getId_categoria() == categoria) {
                    nombreCategoria = listaObjetosCategorias.get(j).getNombre();
                    break;
                }
            }

            for (int j = 0; j < listaObjetosProveedores.size(); j++) {
                if (listaObjetosProveedores.get(j).getId_proveedor() == proveedor) {
                    nombreProvedor = listaObjetosProveedores.get(j).getNombre_proveedor();
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

            listaProductos.set(i, productoConNombreCategoriaYProveedor);
        }

        tblDatosProductoFiltro.getItems().clear();

        tbcIDProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcStockProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tbcStockMinimoProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("stock_minimo"));
        tbcCategoriaProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        tbcProveedorProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));

        tbcCodigoProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcDescripcionProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcMarcaProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tbcUnidadProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("presentacion"));

        tbcCostoProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("costo"));
        tbcPrecioProductoFiltro.setCellValueFactory(new PropertyValueFactory<>("precio"));

        for (Producto producto : listaProductos) {
            tblDatosProductoFiltro.getItems().add(producto);
        }
    }

    void selecionarProducto() {
        try {
            if (tblDatosProductoFiltro.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas elegir este producto?");
            if (alert.showAndWait().get() == ButtonType.OK) {

                String codigoProducto = tblDatosProductoFiltro.getSelectionModel().getSelectedItem().getCodigo();
                String descripion = tblDatosProductoFiltro.getSelectionModel().getSelectedItem().getDescripcion();
                String stock = String.valueOf(tblDatosProductoFiltro.getSelectionModel().getSelectedItem().getStock());
                String costo = String.valueOf(tblDatosProductoFiltro.getSelectionModel().getSelectedItem().getCosto());

                objetoProductoAModificarExistencia = new Producto();
                objetoProductoAModificarExistencia.setCodigo(codigoProducto);
                objetoProductoAModificarExistencia.setDescripcion(descripion);
                objetoProductoAModificarExistencia.setStock(Integer.parseInt(stock));
                objetoProductoAModificarExistencia.setCosto(Double.parseDouble(costo));

                txtCodigoProducto.setText(objetoProductoAModificarExistencia.getCodigo());
                txaDescripcionProducto.setText(objetoProductoAModificarExistencia.getDescripcion());
                txtStockProducto.setText(String.valueOf(objetoProductoAModificarExistencia.getStock()));
                txtCostoProducto.setText(String.valueOf(objetoProductoAModificarExistencia.getCosto()));

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
            alert.setContentText("El Producto no se ha seleccionado");
            alert.show();
        }
    }

    private void colocarDescripcionDeProductoPorCodigo() {
        String codigoProductoABuscar = txtCodigoProducto.getText();

        String codigoEncontrado = "";
        String DescripcionEncontrada = "";
        String costoEncontrado = "";
        String stockEncontrado = "";

        if (existeProducto(codigoProductoABuscar)) {
            for (int i = 0; i < listaObjetosProductosBucadosPorCodigo.size(); i++) {
                codigoEncontrado = listaObjetosProductosBucadosPorCodigo.get(i).getCodigo();
                DescripcionEncontrada = listaObjetosProductosBucadosPorCodigo.get(i).getDescripcion();
                costoEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getCosto());
                stockEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getStock());

                objetoProductoAModificarExistencia = new Producto();
                objetoProductoAModificarExistencia.setCodigo(codigoEncontrado);
                objetoProductoAModificarExistencia.setDescripcion(DescripcionEncontrada);
                objetoProductoAModificarExistencia.setStock(Integer.parseInt(stockEncontrado));
                objetoProductoAModificarExistencia.setCosto(Double.parseDouble(costoEncontrado));

                txtCodigoProducto.setText(objetoProductoAModificarExistencia.getCodigo());
                txaDescripcionProducto.setText(objetoProductoAModificarExistencia.getDescripcion());
                txtStockProducto.setText(String.valueOf(objetoProductoAModificarExistencia.getStock()));
                txtCostoProducto.setText(String.valueOf(objetoProductoAModificarExistencia.getCosto()));
            }
        }
    }

    private boolean existeProducto(String codigoProductoABuscar) {

        boolean codigoProductoExiste = false;

        if (txtCodigoProducto.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Primero escribe un codigo, despues preciona tecla ENTER para buscarlo \n o presiona ELEGIR y busca uno del catalogo de productos");
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
            limpiarCamposProductoVistaAlmacen();
            txtCodigoProducto.setText(codigoProductoABuscar);
        }
        return codigoProductoExiste;
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

    void ManejadorFiltroProducto() {
        String codigoProducto = txtCodigoProductoFiltro.getText();
        String descripcionProducto = txtDescripcionProductoFiltro.getText();
        String nombreMarcaProducto = "";
        nombreMarcaProducto = cboMarcaProductoFiltro.getSelectionModel().getSelectedItem().toString();

        if (nombreMarcaProducto.equals(MARCA_DEFAULT)) {
            nombreMarcaProducto = "";
        }

        llenarTablaBusquedaProducto(productoBD.getProductosFiltro4(codigoProducto, descripcionProducto, nombreMarcaProducto));

    }

    private void aumentarStockDeProducto() {
        if (cantidadProductoIngresadaCorrecta()) {
            try {
                String codigoProducto = objetoProductoAModificarExistencia.getCodigo();
                int cantidad = Integer.valueOf(txtCantidad.getText());
                int stockActual = Integer.valueOf(txtStockProducto.getText());
                String descripcionProducto = txaDescripcionProducto.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¿Realmente deseas agregar " + cantidad + " existencias del producto con el codigo " + codigoProducto + " ?");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    productoBD.aumentarExistencia(codigoProducto, cantidad);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Se agregaron " + cantidad + " existencias al producto con el codigo " + codigoProducto);
                    alert.setTitle("Información");
                    alert.setContentText("\nAhora se cuentan con " + (stockActual + cantidad) + " existencias del producto \n" + descripcionProducto);
                    alert.showAndWait();

                    limpiarCamposProductoVistaAlmacen();

                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha cancelado la operación");
                    alert.show();
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

    private void reducirStockDeProducto() {
        if (cantidadProductoIngresadaCorrecta()) {
            try {
                String codigoProducto = objetoProductoAModificarExistencia.getCodigo();
                int cantidad = Integer.valueOf(txtCantidad.getText());
                int stockActual = Integer.valueOf(txtStockProducto.getText());
                String descripcionProducto = txaDescripcionProducto.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¿Realmente deseas quitar " + cantidad + " existencias del producto con el código " + codigoProducto + " ?");
                if (alert.showAndWait().get() == ButtonType.OK) {

                    productoBD.reducirExistencia(codigoProducto, cantidad);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Se eliminaron " + cantidad + " existencias del producto con el codigo " + codigoProducto);
                    alert.setTitle("Información");
                    alert.setContentText("\nAhora se cuentan con " + (stockActual - cantidad) + " existencias del producto \n" + descripcionProducto);
                    alert.showAndWait();

                    limpiarCamposProductoVistaAlmacen();

                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha cancelado la operación");
                    alert.show();
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

    private boolean cantidadProductoIngresadaCorrecta() {

        if (txtStockProducto.getText().equals("")) {

            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Primero debe de seleccionar un producto para ingresar una cantidad");
            alertStockProducto.show();
            return false;
        }

        if (txtCantidad.getText().equals("")) {

            Alert alertStockProducto = new Alert(Alert.AlertType.INFORMATION);
            alertStockProducto.setTitle("Informacion");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("¡¡¡¡Escribe una cantidad");
            alertStockProducto.show();
            return false;
        }

        if (isNumeric(txtCantidad.getText()) == false) {
            Alert alertStockProducto = new Alert(Alert.AlertType.WARNING);
            alertStockProducto.setTitle("Advertencia");
            alertStockProducto.setHeaderText(null);
            alertStockProducto.setContentText("Se debe de escribir una cantidad numerica");
            alertStockProducto.show();
            return false;
        }

        return true;
    }

}
