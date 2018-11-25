/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.CategoriaBD;
import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.MarcaBD;
import AccesoBD.ProductoBD;
import AccesoBD.ProveedorBD;
import Modelo.Categoria;
import Modelo.Marca;
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
import Modelo.Producto;
import Modelo.Proveedor;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.swing.ImageIcon;

/**
 *
 * @author Mike
 */
public class ControladorVistaProducto implements Initializable {

    ProductoBD productoDB;
    CategoriaBD categoriaDB;
    MarcaBD marcaDB;
    ProveedorBD proveedorDB;

    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    //ManejadorFiltroKey manejador;
    private boolean filtrarActivado, agregarActivado, modificarActivado, agregarMarcaActivado;

    private static String MARCA_DEFAULT = "Eliger marca";
    private static String PROVEEDOR_DEFAULT = "Elegir Proveedor";
    private static String CATEGORIA_DEFAULT = "Elegir Categoria";

    private static String AYUDA_AL_AGREGAR = "Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_MODIFICAR = "Seleccionar un registro, Escribe en los campos los datos deseados, da clic en Guardar Cambios, o da clic en cancelar";
    private static String AYUDA_AL_FILTRAR = "Escribe en los campos la informacion con la que deben coincidir los registros del filtro. Da clic en Filtro nuevamente para salir";

    private ArrayList<Proveedor> listaObjetosProveedores;
    private ArrayList<Marca> listaObjetosMarcas;
    private ArrayList<Categoria> listaObjetosCategorias;
    private boolean existenMarcas = false;
    private boolean existenProveedores = false;
    private boolean existenCategorias = false;

    private String contenidoTxtCodigoProducto,
            contenidoTxtPrecioProducto,
            contenidoTxtCostoProducto,
            contenidoTxtUnidadProducto,
            contenidoTxtStockProducto,
            contenidoTxtStockMinimoProducto,
            contenidoTxaDescripcionProducto,
            seleccionCboMarcaProducto,
            seleccionCboCategoriaProducto,
            seleccionCboProveedorProducto;

    ManejadorFiltroKey manejador, manejadorMarca;

    @FXML
    private JFXTextArea txaDescripcionProducto,
            txaNombreMarca;

    @FXML
    private JFXTextField txtCodigoProducto,
            txtPrecioProducto,
            txtCostoProducto,
            txtUnidadProducto,
            txtStockProducto,
            txtStockMinimoProducto,
            txtIdMarca;

    @FXML
    private JFXComboBox cboMarcaProducto,
            cboCategoriaProducto,
            cboProveedorProducto;

    @FXML
    private TableView<Producto> tblDatosProducto;

    @FXML
    private TableView<Marca> tblDatosMarca;

    @FXML
    private TableColumn<Producto, Integer> tbcID,
            tbcStock, tbcStockMinimo;

    @FXML
    private TableColumn<Marca, Integer> tbcIDMarca;

    /*
     @FXML
    private TableColumn<Producto, Integer> tbcID,
            tbcStock, tbcStockMinimo, tbcCategoria, tbcProveedor;
     */

 /*
    @FXML
    private TableColumn<Producto, String> tbcCodigo,
            tbcDescripcion,
            tbcMarca,
            tbcUnidad;
     */
    @FXML
    private TableColumn<Producto, String> tbcCodigo,
            tbcDescripcion,
            tbcMarca,
            tbcUnidad, tbcCategoria, tbcProveedor;

    @FXML
    private TableColumn<Marca, String> tbcNombreMarcaMarca;

    @FXML
    private TableColumn<Producto, Double> tbcCosto,
            tbcPrecio;

    @FXML
    private JFXButton btnAgregarProducto,
            btnModificarProducto,
            btnEliminarProducto,
            btnCancelarProducto,
            btnFiltrarProducto,
            btnRegresarProducto,
            btnGuardarInsercionProducto,
            btnGuardarModificacionProducto,
            btnGenerarCodigoAleatorioProducto,
            btnLeerCodigoProducto,
            btnMostrarCodigoBarrasProducto,
            btnImprimirCodigoBarrasProducto,
            btnAccesoDirectoEditarProveedoresProducto,
            btnAccesoDirectoEditarCategoriasProducto,
            btnAccesoDirectoEditarMarcasProducto,
            btnImprimirCodigoBarrasVariosProductos,
            btnRegresarAVistaProductos, btnAgregarMarca, btnEliminarMarca, btnGuardarInsercionMarca, btnCancelarMarca;

    @FXML
    private Label lblAyuda, lblCodigoBarras;

    @FXML
    private Pane panelCodigoBarras, panelMarcas, panelProductos;

    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        agregarProductoActivado();
    }

    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        modificarProductoActivado();
    }

    @FXML
    private void handleButtonGuardarInsercion(ActionEvent event) {
        if (agregarActivado) {
            agregarProducto();
            llenarTabla(productoDB.getProductos());
            //limpiarCampos();
        }

    }

    @FXML
    private void handleButtonGuardarModificacion(ActionEvent event) {
        if (modificarActivado) {
            actualizarProducto();
            llenarTabla(productoDB.getProductos());
            //limpiarCampos();

        }

    }

    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        eliminarProducto();
        llenarTabla(productoDB.getProductos());
        /*limpiarCampos();*/
    }

    @FXML
    private void handleButtonCancelar(ActionEvent event) {
        /*limpiarCampos();*/
        regresarBotonesAFormaOriginal();
        lblAyuda.setText("");
    }

    @FXML
    private void handleButtonMostrarCodigoDeBarras(ActionEvent event) {
        verificarMostrarCodigoBarras();

    }

    @FXML
    private void handleButtonGuardarCodigoDeBarras(ActionEvent event) {
        verificarGuardarCodigoBarras();

    }

    @FXML
    private void handleButtonGuardarCodigosDeBarrasVariosProductos(ActionEvent event) {

        guardarCodigoBarrasProductosFiltrados();

    }

    @FXML
    private void handleButtonAbrirVistaMarcas(ActionEvent event) {
        panelMarcas.setVisible(true);
        panelProductos.setVisible(false);
        abrirVistaMarcas();
    }

    @FXML
    private void handleButtonRegresarAVistaProductos(ActionEvent event) {
        panelMarcas.setVisible(false);
        panelProductos.setVisible(true);
        iniciarVistaProductos();

    }

    @FXML
    private void filtroBusqueda(ActionEvent event) {
        //filtrarActivado = !filtrarActivado;

        ManejadorFiltro();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarVistaProductos();
        panelMarcas.setVisible(false);
        panelProductos.setVisible(true);
    }

    private void iniciarVistaProductos() {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        productoDB = new ProductoBD(conectaBD_PuntoVenta.getConnection());
        categoriaDB = new CategoriaBD(conectaBD_PuntoVenta.getConnection());
        marcaDB = new MarcaBD(conectaBD_PuntoVenta.getConnection());
        proveedorDB = new ProveedorBD(conectaBD_PuntoVenta.getConnection());

        filtrarActivado = false;
        agregarActivado = false;
        modificarActivado = false;

        listaObjetosProveedores = new ArrayList<Proveedor>();
        listaObjetosMarcas = new ArrayList<Marca>();
        listaObjetosCategorias = new ArrayList<Categoria>();

        llenarCbosOpciones();
        //mostraCodigoBarras();

        llenarTabla(productoDB.getProductos());

        lblAyuda.setText("");
        lblCodigoBarras.setText("");

        regresarBotonesAFormaOriginal();

        /*
        Image image = new Image("/images/iconUser.png");
        logo.setGraphic(new ImageView(image));
         */
        manejador = new ManejadorFiltroKey();
    }

    @FXML
    private void generarCodigoBarrasAleatorio(ActionEvent event) {
        String cadena;
        do {
            cadena = "";

            //corregir para que solo genere numeros
            for (int i = 0; i < 13; i++) {
                //cadena = cadena + (char) (Math.random() * 9);
                cadena = cadena + (int) (Math.random() * 9);
            }

        } while (yaExisteCodigo(cadena));//antes verificar que otro producto no lo tenga ya

        txtCodigoProducto.setText(cadena);
    }

    private boolean yaExisteCodigo(String cadena) {
        boolean existe = false;

        if (cadena.equals("")) {
            existe = true;
        } else {
            int cantidadProductos = tblDatosProducto.getItems().size();

            for (int i = 0; i < cantidadProductos; i++) {
                Producto producto = tblDatosProducto.getItems().get(i);
                String codigo = producto.getCodigo();
                if (codigo.equals(cadena)) {
                    existe = true;
                    break;
                }
            }
        }

        return existe;
    }

    @FXML
    private void handleTableChange(Event event) {
        Producto producto = tblDatosProducto.getSelectionModel().getSelectedItem();

        if (producto != null) {
            System.out.println("si entro al evento de la tabla");
            txtCodigoProducto.setText(producto.getCodigo());
            txaDescripcionProducto.setText(producto.getDescripcion());
            cboMarcaProducto.getSelectionModel().select(producto.getMarca());
            //txtPrecioProducto.setText(producto.getPrecio());
            txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
            txtCostoProducto.setText(String.valueOf(producto.getCosto()));
            txtUnidadProducto.setText(producto.getPresentacion());
            txtStockProducto.setText(String.valueOf(producto.getStock()));
            txtStockMinimoProducto.setText(String.valueOf(producto.getStock_minimo()));

            /*
            int idProveedor;
            int idCategoria;
            idProveedor = producto.getProveedor();
            idCategoria = producto.getCategoria();

            for (int i = 0; i < listaObjetosProveedores.size(); i++) {
                if (listaObjetosProveedores.get(i).getId_proveedor() == idProveedor) {
                    cboProveedorProducto.getSelectionModel().select(listaObjetosProveedores.get(i).getNombre_proveedor());

                    break;
                }
            }

            for (int i = 0; i < listaObjetosCategorias.size(); i++) {
                if (listaObjetosCategorias.get(i).getId_categoria() == idCategoria) {
                    cboCategoriaProducto.getSelectionModel().select(listaObjetosCategorias.get(i).getNombre());
                    break;
                }
            }
             */
            String nombreProveedor = producto.getNombreProveedor();
            String nombreCategoria = producto.getNombreCategoria();

            cboProveedorProducto.getSelectionModel().select(nombreProveedor);
            cboCategoriaProducto.getSelectionModel().select(nombreCategoria);

        }
    }

    private void llenarTabla(ArrayList<Producto> listaProductos) {

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

        tblDatosProducto.getItems().clear();

        tbcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tbcStockMinimo.setCellValueFactory(new PropertyValueFactory<>("stock_minimo"));
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        tbcProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));

        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tbcUnidad.setCellValueFactory(new PropertyValueFactory<>("presentacion"));

        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        for (Producto producto : listaProductos) {
            tblDatosProducto.getItems().add(producto);
        }
    }

    void verificarMostrarCodigoBarras() {
        contenidoTxtCodigoProducto = txtCodigoProducto.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (!contenidoTxtCodigoProducto.equals("")) {
            mostrarImagenCodigoBarras(contenidoTxtCodigoProducto);
        } else {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("El campo de codigo de barras esta vacio");
            alert.show();
        }
    }

    void verificarGuardarCodigoBarras() {
        contenidoTxtCodigoProducto = txtCodigoProducto.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (!contenidoTxtCodigoProducto.equals("")) {
            guardarCodigoBarras(contenidoTxtCodigoProducto);
        } else {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("El campo de codigo de barras esta vacio");
            alert.show();
        }
    }

    void guardarCodigoBarrasProductosFiltrados() {
        contenidoTxtCodigoProducto = txtCodigoProducto.getText();
        Alert alert;

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Realmente deseas guardar en PDF los codigos de de barras de los productos filtrados?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            guardarVariosCodigoBarras();

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

    }

    /*
    private void llenarTabla(ArrayList<Producto> listaProductos) {
        tblDatosProducto.getItems().clear();

        tbcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tbcStockMinimo.setCellValueFactory(new PropertyValueFactory<>("stock_minimo"));
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tbcProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tbcUnidad.setCellValueFactory(new PropertyValueFactory<>("presentacion"));

        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        for (Producto producto : listaProductos) {
            tblDatosProducto.getItems().add(producto);
        }
    }
     */
    private void llenarCbosOpciones() {
        actualizarComboCategorias();
        actualizarComboMarcas();
        actualizarComboProveedores();
    }

    private void actualizarComboCategorias() {
        cboCategoriaProducto.getItems().clear();
        this.listaObjetosCategorias = new ArrayList<Categoria>(categoriaDB.getCategorias());

        /*
        System.out.println("\n*****************Las CATEGORIAS almacenadas son :");
        for (Categoria categoria : listaObjetosCategorias) {
            System.out.println(categoria.getId_categoria()
                    + "         " + categoria.getNombre()
                    + "         " + categoria.getDescripcion()
            );
        }
         */
        cboCategoriaProducto.getItems().add(CATEGORIA_DEFAULT);
        for (int i = 0; i < listaObjetosCategorias.size(); i++) {
            cboCategoriaProducto.getItems().add(listaObjetosCategorias.get(i).getNombre());
        }
        if (listaObjetosCategorias.size() > 0) {
            cboCategoriaProducto.setValue(listaObjetosCategorias.get(0).getNombre());
            existenCategorias = true;
        }
    }

    private void actualizarComboMarcas() {
        cboMarcaProducto.getItems().clear();
        this.listaObjetosMarcas = new ArrayList<Marca>(marcaDB.getMarcas());
        /*
        System.out.println("*****************Las Marcas almacenadas son :");
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            System.out.println(listaObjetosMarcas.get(i).getId_marca() + "         " + listaObjetosMarcas.get(i).getMarca());
        }
         */

        cboMarcaProducto.getItems().add(MARCA_DEFAULT);
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            cboMarcaProducto.getItems().add(listaObjetosMarcas.get(i).getMarca());
        }
        if (listaObjetosMarcas.size() > 0) {
            cboMarcaProducto.setValue(listaObjetosMarcas.get(0).getMarca());
            existenMarcas = true;
        }
    }

    private void actualizarComboProveedores() {
        cboProveedorProducto.getItems().clear();

        this.listaObjetosProveedores = new ArrayList<Proveedor>(proveedorDB.getProveedores());

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
        cboProveedorProducto.getItems().add(PROVEEDOR_DEFAULT);
        for (int i = 0; i < listaObjetosProveedores.size(); i++) {
            cboProveedorProducto.getItems().add(listaObjetosProveedores.get(i).getNombre_proveedor());
        }
        if (listaObjetosProveedores.size() > 0) {
            cboProveedorProducto.setValue(listaObjetosProveedores.get(0).getNombre_proveedor());
            existenProveedores = true;
        }
    }

    private void mostrarImagenCodigoBarras(String Codigo) {

        Barcode128 codigoBarra = new Barcode128();
        codigoBarra.setCode(Codigo);
        java.awt.Image imagenAWT = codigoBarra.createAwtImage(java.awt.Color.black, java.awt.Color.white);

        BufferedImage bufferedImage = convertirABufferedImage(imagenAWT);
        BufferedImage buferredImageAumentada = aumentarTamañoDeBufferedImage(bufferedImage, 270, 70);
        javafx.scene.image.Image imagenFX = SwingFXUtils.toFXImage(buferredImageAumentada, null);

        ImageView imageView = new ImageView();
        imageView.setImage(imagenFX);
        lblCodigoBarras.setGraphic(imageView);
        lblCodigoBarras.setText(".");

    }

    private void guardarCodigoBarras(String Codigo) {
        if (!(lblCodigoBarras.getText().equals(""))) {
            Document documento = new Document();
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Elije donde guardar el codigo de barras");
                String ruta;
                try {
                    ruta = fileChooser.showSaveDialog(null).getPath();
                } catch (Exception e) {
                    ruta = "";
                }

                if (!ruta.equals("")) {
                    PdfWriter pdf = PdfWriter.getInstance(documento, new FileOutputStream(ruta + ".pdf"));
                    documento.open();
                    Barcode128 code2 = new Barcode128();
                    code2.setCode(Codigo);

                    Image img2 = code2.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                    img2.scalePercent(200);
                    documento.add(img2);
                    documento.close();
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControladorVistaProducto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ControladorVistaProducto.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void guardarVariosCodigoBarras() {
        Document documento = new Document();
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Elije donde guardar el codigo de barras");
            String ruta;
            try {
                ruta = fileChooser.showSaveDialog(null).getPath();
            } catch (Exception e) {
                ruta = "";
            }

            if (!ruta.equals("")) {
                PdfWriter pdf = PdfWriter.getInstance(documento, new FileOutputStream(ruta + ".pdf"));
                documento.open();

                int cantidadProductos = tblDatosProducto.getItems().size();

                for (int i = 0; i < cantidadProductos; i++) {
                    Producto producto = tblDatosProducto.getItems().get(i);
                    String codigo = producto.getCodigo();
                    Barcode128 codigoBarras = new Barcode128();

                    codigoBarras.setCode(codigo);

                    Image imagenCodigoBarrasParaPDF = codigoBarras.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                    imagenCodigoBarrasParaPDF.scalePercent(200);
                    documento.add(imagenCodigoBarrasParaPDF);
                    documento.add(new Paragraph(" "));
                }

                documento.close();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorVistaProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ControladorVistaProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static BufferedImage convertirABufferedImage(java.awt.Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;

    }

    public static BufferedImage aumentarTamañoDeBufferedImage(BufferedImage src, int w, int h) {
        BufferedImage img
                = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++) {
            ys[y] = y * hh / h;
        }
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    private void agregarProducto() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (!camposImportantesPorCompletar()) {

            String codigoProducto = txtCodigoProducto.getText();
            String descripcionProducto = txaDescripcionProducto.getText();
            String costoProducto = txtCostoProducto.getText();
            String PrecioProducto = txtPrecioProducto.getText();
            String UnidadProducto = txtUnidadProducto.getText();
            String stockProducto = txtStockProducto.getText();
            String stockMinimoProducto = txtStockMinimoProducto.getText();
            String nombreMarcaProducto = "";
            String nombreCategoriaProducto = "";
            String nombreProveedorProducto = "";
            nombreMarcaProducto = cboMarcaProducto.getSelectionModel().getSelectedItem().toString();
            nombreCategoriaProducto = cboCategoriaProducto.getSelectionModel().getSelectedItem().toString();
            nombreProveedorProducto = cboProveedorProducto.getSelectionModel().getSelectedItem().toString();

            if (isNumeric(stockProducto)) {
                if (isNumeric(stockMinimoProducto)) {
                    if (isDouble(PrecioProducto)) {
                        if (isDouble(costoProducto)) {
                            try {
                                alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmación");
                                alert.setHeaderText(null);
                                alert.setContentText("¿Realmente deseas agregar este Producto?");
                                if (alert.showAndWait().get() == ButtonType.OK) {

                                    int categoria = obtenerIDdeCategoria(nombreCategoriaProducto);
                                    int proveedor = obtenerIDdeProveedor(nombreProveedorProducto);
                                    double prec = Double.parseDouble(PrecioProducto);
                                    double cost = Double.parseDouble(costoProducto);
                                    int stock = Integer.parseInt(stockProducto);
                                    int stockMinimo = Integer.parseInt(stockMinimoProducto);

                                    productoDB.addProducto(codigoProducto, descripcionProducto,
                                            nombreMarcaProducto, cost, prec, UnidadProducto, stock, stockMinimo, categoria, proveedor);

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

                            } catch (SQLIntegrityConstraintViolationException ex2) {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                alert2.setTitle("Error");
                                alert2.setHeaderText("Un error ha ocurrido");
                                alert2.setContentText("El ID ya existe en la base de datos");
                                alert2.show();
                            } catch (SQLException ex) {
                                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                                alert3.setTitle("Error");
                                alert3.setHeaderText("Un error ha ocurrido");
                                alert3.setContentText("El producto no se ha podido agregar. Error al acceder a la base de datos");
                                alert3.show();
                            }
                        } else {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Costo en formato erroneo");
                            alert.setHeaderText(null);
                            alert.setContentText("El costo del producto debe ser númerico");
                            alert.show();
                        }
                    } else {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Precio en formato erroneo");
                        alert.setHeaderText(null);
                        alert.setContentText("El precio del producto debe ser númerico");
                        alert.show();
                    }
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Stock minimo con informacion incorrecta");
                    alert.setHeaderText(null);
                    alert.setContentText("El Stock minimo debe ser númerico");
                    alert.show();
                }

            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Stock con informacion incorrecta");
                alert.setHeaderText(null);
                alert.setContentText("El Stock debe ser númerico");
                alert.show();
            }

        } else {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Aun existen campos por completar");
            alert.show();
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

    private int obtenerIDdeCategoria(String categoria) {
        int idCategoria = 0;
        for (int i = 0; i < listaObjetosCategorias.size(); i++) {
            if (listaObjetosCategorias.get(i).getNombre().equals(categoria)) {
                idCategoria = listaObjetosCategorias.get(i).getId_categoria();
            }

        }
        return idCategoria;
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

    public static boolean isDouble(String cadena) {

        boolean resultado;

        try {
            Double.parseDouble(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    private void actualizarProducto() {
        int idDeProductoSeleccionado = 0;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (!camposImportantesPorCompletar()) {

            String codigoProducto = txtCodigoProducto.getText();
            String descripcionProducto = txaDescripcionProducto.getText();
            String costoProducto = txtCostoProducto.getText();
            String PrecioProducto = txtPrecioProducto.getText();
            String UnidadProducto = txtUnidadProducto.getText();
            String stockProducto = txtStockProducto.getText();
            String stockMinimoProducto = txtStockMinimoProducto.getText();
            String nombreMarcaProducto = "";
            String nombreCategoriaProducto = "";
            String nombreProveedorProducto = "";
            nombreMarcaProducto = cboMarcaProducto.getSelectionModel().getSelectedItem().toString();
            nombreCategoriaProducto = cboCategoriaProducto.getSelectionModel().getSelectedItem().toString();
            nombreProveedorProducto = cboProveedorProducto.getSelectionModel().getSelectedItem().toString();

            if (isNumeric(stockProducto)) {
                if (isNumeric(stockMinimoProducto)) {
                    if (isDouble(PrecioProducto)) {
                        if (isDouble(costoProducto)) {
                            try {

                                if (tblDatosProducto.getSelectionModel().getSelectedItems().isEmpty()) {
                                    Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                                    alertSeleccion.setTitle("Advertencia");
                                    alertSeleccion.setHeaderText(null);
                                    alertSeleccion.setContentText("Por favor elige un registro para modificar");
                                    alertSeleccion.show();
                                    return;
                                }

                                alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmación");
                                alert.setHeaderText(null);
                                alert.setContentText("¿Realmente deseas modificar este Producto?");

                                if (alert.showAndWait().get() == ButtonType.OK) {
                                    idDeProductoSeleccionado = tblDatosProducto.getSelectionModel().getSelectedItem().getId();
                                    int categoria = obtenerIDdeCategoria(nombreCategoriaProducto);
                                    int proveedor = obtenerIDdeProveedor(nombreProveedorProducto);
                                    double prec = Double.parseDouble(PrecioProducto);
                                    double cost = Double.parseDouble(costoProducto);
                                    int stock = Integer.parseInt(stockProducto);
                                    int stockMinimo = Integer.parseInt(stockMinimoProducto);

                                    productoDB.updateProducto(idDeProductoSeleccionado, codigoProducto, descripcionProducto,
                                            nombreMarcaProducto, cost, prec, UnidadProducto, stock, stockMinimo, categoria, proveedor);

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

                            } catch (SQLException ex) {
                                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                                alert3.setTitle("Error");
                                alert3.setHeaderText("Un error ha ocurrido");
                                alert3.setContentText("El producto no se ha podido modificar. Error al acceder a la base de datos");
                                alert3.show();
                                ex.printStackTrace();
                            }
                        } else {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Costo en formato erroneo");
                            alert.setHeaderText(null);
                            alert.setContentText("El costo del producto debe ser númerico");
                            alert.show();
                        }
                    } else {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Precio en formato erroneo");
                        alert.setHeaderText(null);
                        alert.setContentText("El precio del producto debe ser númerico");
                        alert.show();
                    }
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Stock minimo con informacion incorrecta");
                    alert.setHeaderText(null);
                    alert.setContentText("El Stock minimo debe ser númerico");
                    alert.show();
                }

            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Stock con informacion incorrecta");
                alert.setHeaderText(null);
                alert.setContentText("El Stock debe ser númerico");
                alert.show();
            }

        } else {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Aun existen campos por completar");
            alert.show();
        }

    }

    private void eliminarProducto() {
        try {
            if (tblDatosProducto.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de este Producto?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosProducto.getSelectionModel().getSelectedItem().getId();
                productoDB.deleteProducto(ID);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("La operación se ha realizado con éxito");
                alert.show();

                limpiarCampos();
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
            alert.setContentText("El Producto no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }

    }

    private boolean camposImportantesPorCompletar() {
        String codigo = txtCodigoProducto.getText();
        String descripcion = txaDescripcionProducto.getText();
        String costo = txtCostoProducto.getText();
        String Precio = txtPrecioProducto.getText();
        String Unidad = txtUnidadProducto.getText();
        String stock = txtStockProducto.getText();
        String stockMinimo = txtStockMinimoProducto.getText();
        String marca = "";
        String categoria = "";
        String proveedor = "";

        marca = cboMarcaProducto.getSelectionModel().getSelectedItem().toString();
        categoria = cboCategoriaProducto.getSelectionModel().getSelectedItem().toString();
        proveedor = cboProveedorProducto.getSelectionModel().getSelectedItem().toString();

        if (codigo.equals("") || descripcion.equals("") || costo.equals("")
                || Precio.equals("") || Unidad.equals("") || stock.equals("") || stockMinimo.equals("")
                || marca.equals(MARCA_DEFAULT) || proveedor.equals(PROVEEDOR_DEFAULT) || categoria.equals(CATEGORIA_DEFAULT)) {
            return true;
        } else {
            return false;
        }

    }

    void ManejadorFiltro() {

        // System.out.println("si entra al metodo");
        if (filtrarActivado) {

            //System.out.println("Si entra if");
            String codigoProducto = txtCodigoProducto.getText();
            String descripcionProducto = txaDescripcionProducto.getText();
            String costoProducto = txtCostoProducto.getText();
            String PrecioProducto = txtPrecioProducto.getText();
            String UnidadProducto = txtUnidadProducto.getText();
            String stockProducto = txtStockProducto.getText();
            String stockMinimoProducto = txtStockMinimoProducto.getText();
            String nombreMarcaProducto = "";
            String nombreCategoriaProducto = "";
            String nombreProveedorProducto = "";
            nombreMarcaProducto = cboMarcaProducto.getSelectionModel().getSelectedItem().toString();
            nombreCategoriaProducto = cboCategoriaProducto.getSelectionModel().getSelectedItem().toString();
            nombreProveedorProducto = cboProveedorProducto.getSelectionModel().getSelectedItem().toString();

            String categoria = "";
            String proveedor = "";

            if (nombreMarcaProducto.equals(MARCA_DEFAULT)) {
                nombreMarcaProducto = "";
            }

            if (nombreCategoriaProducto.equals(CATEGORIA_DEFAULT)) {
                categoria = "";
            } else {
                categoria = String.valueOf(obtenerIDdeCategoria(nombreCategoriaProducto));
            }

            if (nombreProveedorProducto.equals(PROVEEDOR_DEFAULT)) {
                proveedor = "";
            } else {
                proveedor = String.valueOf(obtenerIDdeProveedor(nombreProveedorProducto));
            }

            leerFiltrarTabla("", codigoProducto, descripcionProducto, nombreMarcaProducto, costoProducto,
                    PrecioProducto, UnidadProducto, stockProducto, stockMinimoProducto, categoria, proveedor);

        }

    }

    private void leerFiltrarTabla(String id, String codigo, String descripcion, String marca, String costo, String precio,
            String unidad, String stock, String stock_minimo,
            String categoria, String proveedor) {
        llenarTabla(productoDB.getProductosFiltro2(id, codigo, descripcion, marca, costo, precio,
                unidad, stock, stock_minimo, categoria, proveedor));
    }

    void regresarBotonesAFormaOriginal() {
        modificarActivado = false;
        agregarActivado = false;
        filtrarActivado = false;

        //ESTADO ORIGINAL DE LOS BOTONES
        btnAgregarProducto.setDisable(false);
        //btnAgregarUsuario.setStyle("fx-background-color: #222288");
        btnModificarProducto.setDisable(false);
        //btnModificarUsuario.setStyle("fx-background-color: #222288");
        btnEliminarProducto.setDisable(false);
        btnFiltrarProducto.setDisable(false);
        //btnRegresarUsuario.setDisable(false);

        btnRegresarProducto.setVisible(true);

        btnCancelarProducto.setVisible(false);
        btnGuardarInsercionProducto.setVisible(false);
        btnGuardarModificacionProducto.setVisible(false);

        btnGenerarCodigoAleatorioProducto.setVisible(false);
        btnLeerCodigoProducto.setVisible(false);
        btnAccesoDirectoEditarProveedoresProducto.setVisible(false);
        btnAccesoDirectoEditarCategoriasProducto.setVisible(false);
        btnAccesoDirectoEditarMarcasProducto.setVisible(false);

        panelCodigoBarras.setVisible(true);

        btnImprimirCodigoBarrasVariosProductos.setVisible(false);

        //POR DEFECTO NO SE PUEDE EDITAR EN LOS TXT
        txtCodigoProducto.setEditable(false);
        txaDescripcionProducto.setEditable(false);
        txtPrecioProducto.setEditable(false);
        txtCostoProducto.setEditable(false);
        txtUnidadProducto.setEditable(false);
        txtStockProducto.setEditable(false);
        txtStockMinimoProducto.setEditable(false);

        //cboMarcaProducto.setEditable(false);
        //cboCategoriaProducto.setEditable(false);
        //cboProveedorProducto.setEditable(false);
        cboMarcaProducto.getSelectionModel().select(0);
        cboCategoriaProducto.getSelectionModel().select(0);
        cboProveedorProducto.getSelectionModel().select(0);

        lblAyuda.setText("");
        lblCodigoBarras.setText("");

        limpiarCampos();

    }

    void agregarProductoActivado() {
        agregarActivado = true;
        modificarActivado = false;

        limpiarCampos();
        if (agregarActivado) {
            lblAyuda.setText(AYUDA_AL_AGREGAR);

            btnAgregarProducto.setDisable(true);
            //btnAgregarUsuario.setStyle("fx-background-color: #0FFF09");
            btnModificarProducto.setDisable(true);
            btnEliminarProducto.setDisable(true);
            btnFiltrarProducto.setDisable(true);

            btnCancelarProducto.setVisible(true);
            btnRegresarProducto.setVisible(false);
            btnGuardarInsercionProducto.setVisible(true);
            btnGuardarModificacionProducto.setVisible(false);

            btnGenerarCodigoAleatorioProducto.setVisible(true);
            btnLeerCodigoProducto.setVisible(false);
            btnAccesoDirectoEditarProveedoresProducto.setVisible(true);
            btnAccesoDirectoEditarCategoriasProducto.setVisible(true);
            btnAccesoDirectoEditarMarcasProducto.setVisible(true);

            panelCodigoBarras.setVisible(true);

            btnImprimirCodigoBarrasVariosProductos.setVisible(false);

            txaDescripcionProducto.setEditable(true);
            txtCodigoProducto.setEditable(true);
            txtPrecioProducto.setEditable(true);
            txtCostoProducto.setEditable(true);
            txtUnidadProducto.setEditable(true);
            txtStockProducto.setEditable(true);
            txtStockMinimoProducto.setEditable(true);

            //cboMarcaProducto.setEditable(true);
            //cboCategoriaProducto.setEditable(true);
            //cboProveedorProducto.setEditable(true);
            cboMarcaProducto.getSelectionModel().select(0);
            cboCategoriaProducto.getSelectionModel().select(0);
            cboProveedorProducto.getSelectionModel().select(0);

        } else {

        }
    }

    void modificarProductoActivado() {
        modificarActivado = true;
        agregarActivado = false;

        limpiarCampos();
        if (modificarActivado) {
            lblAyuda.setText(AYUDA_AL_MODIFICAR);

            btnAgregarProducto.setDisable(true);
            btnModificarProducto.setDisable(true);
            //btnModificarUsuario.setStyle("fx-background-color: #0FFF09");
            btnEliminarProducto.setDisable(true);
            btnFiltrarProducto.setDisable(true);

            btnCancelarProducto.setVisible(true);
            btnRegresarProducto.setVisible(false);
            btnGuardarInsercionProducto.setVisible(false);
            btnGuardarModificacionProducto.setVisible(true);

            btnGenerarCodigoAleatorioProducto.setVisible(true);
            btnLeerCodigoProducto.setVisible(false);
            btnAccesoDirectoEditarProveedoresProducto.setVisible(true);
            btnAccesoDirectoEditarCategoriasProducto.setVisible(true);
            btnAccesoDirectoEditarMarcasProducto.setVisible(true);

            panelCodigoBarras.setVisible(true);

            btnImprimirCodigoBarrasVariosProductos.setVisible(false);

            txaDescripcionProducto.setEditable(true);
            txtCodigoProducto.setEditable(true);
            txtPrecioProducto.setEditable(true);
            txtCostoProducto.setEditable(true);
            txtUnidadProducto.setEditable(true);
            txtStockProducto.setEditable(true);
            txtStockMinimoProducto.setEditable(true);

            //cboMarcaProducto.setEditable(true);
            //cboCategoriaProducto.setEditable(true);
            //cboProveedorProducto.setEditable(true);
            cboMarcaProducto.getSelectionModel().select(0);
            cboCategoriaProducto.getSelectionModel().select(0);
            cboProveedorProducto.getSelectionModel().select(0);

        } else {

        }
    }

    @FXML
    private void filtrarProducto() {

        filtrarActivado = !filtrarActivado;
        limpiarCampos();
        if (filtrarActivado) {
            lblAyuda.setText(AYUDA_AL_FILTRAR);

            btnAgregarProducto.setDisable(true);
            btnModificarProducto.setDisable(true);
            btnEliminarProducto.setDisable(true);

            btnRegresarProducto.setVisible(false);

            btnGenerarCodigoAleatorioProducto.setVisible(false);
            btnLeerCodigoProducto.setVisible(false);
            btnAccesoDirectoEditarProveedoresProducto.setVisible(false);
            btnAccesoDirectoEditarCategoriasProducto.setVisible(false);
            btnAccesoDirectoEditarMarcasProducto.setVisible(false);

            panelCodigoBarras.setVisible(false);

            btnImprimirCodigoBarrasVariosProductos.setVisible(true);

            txaDescripcionProducto.setEditable(true);
            txtCodigoProducto.setEditable(true);
            txtPrecioProducto.setEditable(true);
            txtCostoProducto.setEditable(true);
            txtUnidadProducto.setEditable(true);
            txtStockProducto.setEditable(true);
            txtStockMinimoProducto.setEditable(true);

            //cboMarcaProducto.setEditable(true);
            //cboCategoriaProducto.setEditable(true);
            //cboProveedorProducto.setEditable(true);
            cboMarcaProducto.getSelectionModel().select(0);
            cboCategoriaProducto.getSelectionModel().select(0);
            cboProveedorProducto.getSelectionModel().select(0);

            txaDescripcionProducto.textProperty().addListener(manejador);
            txtCodigoProducto.textProperty().addListener(manejador);
            txtPrecioProducto.textProperty().addListener(manejador);
            txtCostoProducto.textProperty().addListener(manejador);
            txtUnidadProducto.textProperty().addListener(manejador);
            txtStockProducto.textProperty().addListener(manejador);
            txtStockMinimoProducto.textProperty().addListener(manejador);

            cboMarcaProducto.valueProperty().addListener(manejador);
            cboCategoriaProducto.valueProperty().addListener(manejador);
            cboProveedorProducto.valueProperty().addListener(manejador);

        } else {
            btnAgregarProducto.setDisable(false);
            btnModificarProducto.setDisable(false);
            btnEliminarProducto.setDisable(false);

            btnRegresarProducto.setVisible(true);

            btnGenerarCodigoAleatorioProducto.setVisible(false);
            btnLeerCodigoProducto.setVisible(false);
            btnAccesoDirectoEditarProveedoresProducto.setVisible(false);
            btnAccesoDirectoEditarCategoriasProducto.setVisible(false);
            btnAccesoDirectoEditarMarcasProducto.setVisible(false);

            panelCodigoBarras.setVisible(true);

            btnImprimirCodigoBarrasVariosProductos.setVisible(false);

            txaDescripcionProducto.setEditable(false);
            txtCodigoProducto.setEditable(false);
            txtPrecioProducto.setEditable(false);
            txtCostoProducto.setEditable(false);
            txtUnidadProducto.setEditable(false);
            txtStockProducto.setEditable(false);
            txtStockMinimoProducto.setEditable(false);

            cboMarcaProducto.setEditable(false);
            cboCategoriaProducto.setEditable(false);
            cboProveedorProducto.setEditable(false);
            cboMarcaProducto.getSelectionModel().select(0);
            cboCategoriaProducto.getSelectionModel().select(0);
            cboProveedorProducto.getSelectionModel().select(0);

            txaDescripcionProducto.textProperty().removeListener(manejador);
            txtCodigoProducto.textProperty().removeListener(manejador);
            txtPrecioProducto.textProperty().removeListener(manejador);
            txtCostoProducto.textProperty().removeListener(manejador);
            txtUnidadProducto.textProperty().removeListener(manejador);
            txtStockProducto.textProperty().removeListener(manejador);
            txtStockMinimoProducto.textProperty().removeListener(manejador);

            cboMarcaProducto.valueProperty().removeListener(manejador);
            cboCategoriaProducto.valueProperty().removeListener(manejador);
            cboProveedorProducto.valueProperty().removeListener(manejador);

            llenarTabla(productoDB.getProductos());
            //limpiarCampos();//----------------------------------------------------------
            lblAyuda.setText("");

        }
    }

    private void limpiarCampos() {
        txaDescripcionProducto.clear();
        txtCodigoProducto.clear();
        txtPrecioProducto.clear();
        txtCostoProducto.clear();
        txtUnidadProducto.clear();
        txtStockProducto.clear();
        txtStockMinimoProducto.clear();

        cboMarcaProducto.getSelectionModel().select(0);
        cboCategoriaProducto.getSelectionModel().select(0);
        cboProveedorProducto.getSelectionModel().select(0);

    }

    class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }
    }

    /*------------------------------------------        Métodos de categorias              */
    public void abrirVistaMarcas() {

        agregarMarcaActivado = false;

        //mostraCodigoBarras();
        llenarTablaMarcas(marcaDB.getMarcas());

        regresarBotonesMarcasAFormaOriginal();

        /*
        Image image = new Image("/images/iconUser.png");
        logo.setGraphic(new ImageView(image));
         */
    }

    @FXML
    private void handleButtonAgregarMarca(ActionEvent event) {
        agregarMarcaActivado();
    }

    @FXML
    private void handleButtonGuardarInsercionMarca(ActionEvent event) {
        if (agregarMarcaActivado) {
            agregarMarca();
            llenarTablaMarcas(marcaDB.getMarcas());
            //limpiarCamposMarca();
        }
    }

    @FXML
    private void handleButtonEliminarMarca(ActionEvent event) {
        eliminarMarca();
        llenarTablaMarcas(marcaDB.getMarcas());
        //limpiarCamposMarca();
    }

    @FXML
    private void handleButtonCancelarMarca(ActionEvent event) {
        limpiarCamposMarca();
        regresarBotonesMarcasAFormaOriginal();
    }

    @FXML
    private void handleTableChangeMarcas(Event event) {
        Marca objetoMarca = tblDatosMarca.getSelectionModel().getSelectedItem();

        if (objetoMarca != null) {
            System.out.println("si entro al evento de la tabla Marca");
            txtIdMarca.setText(String.valueOf(objetoMarca.getId_marca()));
            txaNombreMarca.setText(objetoMarca.getMarca());
        }
    }

    private void llenarTablaMarcas(ArrayList<Marca> listaMarcas) {

        tblDatosMarca.getItems().clear();

        tbcIDMarca.setCellValueFactory(new PropertyValueFactory<>("id_marca"));
        tbcNombreMarcaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        for (Marca marca : listaMarcas) {
            tblDatosMarca.getItems().add(marca);
        }
    }

    private void agregarMarca() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (!camposImportantesMarcaPorCompletar()) {
            String marca = txaNombreMarca.getText();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¿Realmente deseas agregar esta Marca?");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    marcaDB.addMarca(marca);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("La operación se ha realizado con éxito");
                    alert.show();
                    regresarBotonesMarcasAFormaOriginal();
                    limpiarCamposMarca();

                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha cancelado la operación");
                    alert.show();
                }

            } catch (SQLIntegrityConstraintViolationException ex2) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("Un error ha ocurrido");
                alert2.setContentText("El ID ya existe en la base de datos");
                alert2.show();
            } catch (SQLException ex) {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Error");
                alert3.setHeaderText("Un error ha ocurrido");
                alert3.setContentText("La categoria no se ha podido agregar. Error al acceder a la base de datos");
                alert3.show();
            }

        } else {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Se debe escribir una marca para agregar");
            alert.show();
        }

    }

    private void eliminarMarca() {
        try {
            if (tblDatosMarca.getSelectionModel().getSelectedItems().isEmpty()) {
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
            alert.setContentText("¿Realmente deseas eliminar el registro de esta Marca?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                int ID = tblDatosMarca.getSelectionModel().getSelectedItem().getId_marca();
                marcaDB.deleteMarca(ID);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("La operación se ha realizado con éxito");
                alert.show();

                limpiarCamposMarca();
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
            alert.setContentText("La Marca no se ha podido eliminar. Error al acceder a la base de datos");
            alert.show();
        }
    }

    private boolean camposImportantesMarcaPorCompletar() {
        String marca = txaNombreMarca.getText();

        if (marca.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    void regresarBotonesMarcasAFormaOriginal() {
        agregarMarcaActivado = false;

        //ESTADO ORIGINAL DE LOS BOTONES
        btnAgregarMarca.setDisable(false);
        btnEliminarMarca.setDisable(false);

        btnCancelarMarca.setVisible(false);
        btnGuardarInsercionMarca.setVisible(false);

        //POR DEFECTO NO SE PUEDE EDITAR EN LOS TXT
        txtIdMarca.setEditable(false);
        txaNombreMarca.setEditable(false);

        limpiarCamposMarca();
    }

    void agregarMarcaActivado() {
        agregarMarcaActivado = true;

        limpiarCamposMarca();
        if (agregarMarcaActivado) {

            btnAgregarMarca.setDisable(true);
            btnEliminarMarca.setDisable(true);

            btnCancelarMarca.setVisible(true);
            btnGuardarInsercionMarca.setVisible(true);

            txaNombreMarca.setEditable(true);
        } else {
        }
    }

    private void limpiarCamposMarca() {
        txtIdMarca.clear();
        txaNombreMarca.clear();
    }

}
