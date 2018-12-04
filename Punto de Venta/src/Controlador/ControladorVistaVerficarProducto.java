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
public class ControladorVistaVerficarProducto implements Initializable {

    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    ProductoBD productoBD;
    CategoriaBD categoriaBD;
    MarcaBD marcaBD;
    ProveedorBD proveedorDB;

    private static String MARCA_DEFAULT = "Elegir marca";

    private ArrayList<Proveedor> listaObjetosProveedores;
    private ArrayList<Categoria> listaObjetosCategorias;
    private ArrayList<Producto> listaObjetosProductosBucadosPorCodigo;

    private Producto objetoProductoAAgregar = new Producto();

    private boolean existenMarcas = false;
    private boolean existenProveedores = false;
    private boolean existenCategorias = false;

    //---------------------------------------------------vista venta
    @FXML
    private JFXTextField txtCodigoProducto,
            txtStockProducto,
            txtStockMinimoProducto,
            txtPrecioProducto,
            txtCostoProducto,
            txtUtilidadProducto,
            txtPresentacionProducto,
            txtMarcaProducto,
            txtCategoriaProducto,
            txtProveedorProducto;

    @FXML
    private JFXTextArea txaDescripcionProducto;

    @FXML
    private Pane panelPrincipalVerificarProducto;

    @FXML
    private void btnBuscarProductoPorCodigoEvento(ActionEvent event) {
        colocarDescripcionDeProductoPorCodigo();
    }

    @FXML
    private void btnLimpiarEvento(ActionEvent event) {
        limpiarCamposVistaVerificarProducto();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelPrincipalVerificarProducto.setVisible(true);
        iniciarVistaVerificarProducto();
    }

    private void iniciarVistaVerificarProducto() {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        productoBD = new ProductoBD(conectaBD_PuntoVenta.getConnection());

        categoriaBD = new CategoriaBD(conectaBD_PuntoVenta.getConnection());
        marcaBD = new MarcaBD(conectaBD_PuntoVenta.getConnection());
        proveedorDB = new ProveedorBD(conectaBD_PuntoVenta.getConnection());

        listaObjetosProveedores = new ArrayList<Proveedor>();
        listaObjetosCategorias = new ArrayList<Categoria>();

        llenarListaCategorias();
        llenarListaProveedores();

        EventHandler<KeyEvent> handlerCodigoProducto = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                colocarDescripcionDeProductoPorCodigo();
            }
        };
        txtCodigoProducto.setOnKeyPressed(handlerCodigoProducto);
    }

    private void llenarListaCategorias() {
        this.listaObjetosCategorias = new ArrayList<Categoria>(categoriaBD.getCategorias());
    }

    private void llenarListaProveedores() {
        this.listaObjetosProveedores = new ArrayList<Proveedor>(proveedorDB.getProveedores());
    }

    private void limpiarCamposVistaVerificarProducto() {
        txtCodigoProducto.clear();
        txaDescripcionProducto.clear();
        txtStockProducto.clear();
        txtStockMinimoProducto.clear();
        txtCostoProducto.clear();
        txtPrecioProducto.clear();
        txtUtilidadProducto.clear();
        txtPresentacionProducto.clear();
        txtMarcaProducto.clear();
        txtCategoriaProducto.clear();
        txtProveedorProducto.clear();
    }

    private void colocarDescripcionDeProductoPorCodigo() {
        String codigoProductoABuscar = txtCodigoProducto.getText();

        String codigoEncontrado = "";
        String descripcionEncontrada = "";
        String stockEncontrado = "";
        String stockMinimoEncontrado = "";
        String costoEncontrado = "";
        String precioEncontrado = "";
        String utilidad = "";
        String presentacionEncontrado = "";
        int categoria = 0;
        int proveedor = 0;
        String marcaEncontrado = "";
        String categoriaEncontrado = "";
        String proveedorEncontrado = "";

        if (existeProducto(codigoProductoABuscar)) {
            for (int i = 0; i < listaObjetosProductosBucadosPorCodigo.size(); i++) {
                codigoEncontrado = listaObjetosProductosBucadosPorCodigo.get(i).getCodigo();
                descripcionEncontrada = listaObjetosProductosBucadosPorCodigo.get(i).getDescripcion();
                stockEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getStock());
                stockMinimoEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getStock_minimo());
                costoEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getCosto());
                precioEncontrado = String.valueOf(listaObjetosProductosBucadosPorCodigo.get(i).getPrecio());
                
                utilidad = String.valueOf((Double.parseDouble(precioEncontrado))-(Double.parseDouble(costoEncontrado)));
                
                presentacionEncontrado = listaObjetosProductosBucadosPorCodigo.get(i).getPresentacion();
                marcaEncontrado = listaObjetosProductosBucadosPorCodigo.get(i).getMarca();
                categoria = listaObjetosProductosBucadosPorCodigo.get(i).getCategoria();
                proveedor = listaObjetosProductosBucadosPorCodigo.get(i).getProveedor();

                for (int j = 0; j < listaObjetosCategorias.size(); j++) {
                    if (listaObjetosCategorias.get(j).getId_categoria() == categoria) {
                        categoriaEncontrado = listaObjetosCategorias.get(j).getNombre();
                        break;
                    }
                }

                for (int j = 0; j < listaObjetosProveedores.size(); j++) {
                    if (listaObjetosProveedores.get(j).getId_proveedor() == proveedor) {
                        proveedorEncontrado = listaObjetosProveedores.get(j).getNombre_proveedor();
                        break;
                    }
                }

                objetoProductoAAgregar = new Producto();
                objetoProductoAAgregar.setCodigo(codigoEncontrado);
                objetoProductoAAgregar.setDescripcion(descripcionEncontrada);
                objetoProductoAAgregar.setStock(Integer.parseInt(stockEncontrado));
                objetoProductoAAgregar.setStock_minimo(Integer.parseInt(stockMinimoEncontrado));
                objetoProductoAAgregar.setPrecio(Double.parseDouble(precioEncontrado));
                objetoProductoAAgregar.setCosto(Double.parseDouble(costoEncontrado));
                objetoProductoAAgregar.setPresentacion(presentacionEncontrado);
                objetoProductoAAgregar.setMarca(marcaEncontrado);
                objetoProductoAAgregar.setCategoria(categoria);
                objetoProductoAAgregar.setProveedor(proveedor);

                txtCodigoProducto.setText(objetoProductoAAgregar.getCodigo());
                txaDescripcionProducto.setText(objetoProductoAAgregar.getDescripcion());
                txtStockProducto.setText(String.valueOf(objetoProductoAAgregar.getStock()));
                txtStockMinimoProducto.setText(String.valueOf(objetoProductoAAgregar.getStock_minimo()));
                txtCostoProducto.setText(String.valueOf(objetoProductoAAgregar.getCosto()));
                txtPrecioProducto.setText(String.valueOf(objetoProductoAAgregar.getPrecio()));
                txtUtilidadProducto.setText(utilidad);
                txtPresentacionProducto.setText(String.valueOf(objetoProductoAAgregar.getPresentacion()));
                txtMarcaProducto.setText(String.valueOf(objetoProductoAAgregar.getMarca()));
                txtCategoriaProducto.setText(categoriaEncontrado);
                txtProveedorProducto.setText(proveedorEncontrado);
            }
        }
    }

    private boolean existeProducto(String codigoProductoABuscar) {

        boolean codigoProductoExiste = false;

        if (txtCodigoProducto.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Primero escribe un codigo, despues preciona tecla ENTER \nO presiona el boton Buscar");
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
            alert.setHeaderText("Código Inexistente");
            alert.setContentText("No existe ningun producto registrado con el código " + codigoProductoABuscar);
            alert.show();
            codigoProductoExiste = false;
            limpiarCamposVistaVerificarProducto();
            txtCodigoProducto.setText(codigoProductoABuscar);
        }
        return codigoProductoExiste;
    }
}
