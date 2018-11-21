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
    private boolean filtrarActivado, agregarActivado, modificarActivado;

    private static String MARCA_DEFAULT = "-";
    private static String PROVEEDOR_DEFAULT = "-";
    private static String CATEGORIA_DEFAULT = "-";

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

    ManejadorFiltroKey manejador;

    @FXML
    private JFXTextArea txaDescripcionProducto;

    @FXML
    private JFXTextField txtCodigoProducto,
            txtPrecioProducto,
            txtCostoProducto,
            txtUnidadProducto,
            txtStockProducto,
            txtStockMinimoProducto;

    @FXML
    private JFXComboBox cboMarcaProducto,
            cboCategoriaProducto,
            cboProveedorProducto;

    @FXML
    private TableView<Producto> tblDatosProducto;

    @FXML
    private TableColumn<Producto, Integer> tbcID,
            tbcStock, tbcStockMinimo, tbcCategoria, tbcProveedor;

    @FXML
    private TableColumn<Producto, String> tbcCodigo,
            tbcDescripcion,
            tbcMarca,
            tbcUnidad;

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
            btnAccesoDirectoEditarMarcasProducto;

    @FXML
    private Label lblAyuda, lblCodigoBarras;

    @FXML
    private Pane panelCodigoBarras;

    @FXML
    private void handleButtonAgregar(ActionEvent event) {
        /*agregarUsuarioActivado();*/
    }

    @FXML
    private void handleButtonActualizar(ActionEvent event) {
        /*modificarUsuarioActivado();*/
    }

    @FXML
    private void handleButtonAgregarCambios(ActionEvent event) {
        if (agregarActivado) {
            /* agregarUsuario();*/
            llenarTabla(productoDB.getProductos());
            //limpiarCampos();
        }

    }

    @FXML
    private void handleButtonActualizarCambios(ActionEvent event) {
        if (modificarActivado) {
            /* actualizarUsuario();*/
            llenarTabla(productoDB.getProductos());
            //limpiarCampos();

        }

    }

    @FXML
    private void handleButtonEliminar(ActionEvent event) {
        /*eliminarUsuario();*/
        llenarTabla(productoDB.getProductos());
        /*limpiarCampos();*/
    }

    @FXML
    private void handleButtonCancelar(ActionEvent event) {
        /*limpiarCampos();*/
 /*regresarBotonesAFormaOriginal();*/
        lblAyuda.setText("");
    }

    @FXML
    private void filtroBusqueda(ActionEvent event) {
        filtrarActivado=!filtrarActivado;
        
        ManejadorFiltro();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        mostraCodigoBarras();

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
        String cadena = "";

        //corregir para que solo genere numeros
        for (int i = 0; i < 13; i++) {
            cadena = cadena + (char) (Math.random() * 9);
        }

        //antes verificar que otro producto no lo tenga ya
        txtCodigoProducto.setText(cadena);
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

        //POR DEFECTO NO SE PUEDE EDITAR EN LOS TXT
        txtCodigoProducto.setEditable(false);
        txaDescripcionProducto.setEditable(false);
        txtPrecioProducto.setEditable(false);
        txtCostoProducto.setEditable(false);
        txtUnidadProducto.setEditable(false);
        txtStockProducto.setEditable(false);
        txtStockMinimoProducto.setEditable(false);

        cboMarcaProducto.getSelectionModel().select(0);
        cboCategoriaProducto.getSelectionModel().select(0);
        cboProveedorProducto.getSelectionModel().select(0);

        lblAyuda.setText("");
        lblCodigoBarras.setText("");

    }

    @FXML
    private void handleTableChange(Event event) {
        Producto producto = tblDatosProducto.getSelectionModel().getSelectedItem();

        if (producto != null) {
            System.out.println("si estro al evento de la tabla");
            txtCodigoProducto.setText(producto.getCodigo());
            txaDescripcionProducto.setText(producto.getDescripcion());
            cboMarcaProducto.getSelectionModel().select(producto.getMarca());
            //txtPrecioProducto.setText(producto.getPrecio());
            txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
            txtCostoProducto.setText(String.valueOf(producto.getCosto()));
            txtUnidadProducto.setText(producto.getPresentacion());
            txtStockProducto.setText(String.valueOf(producto.getStock()));
            txtStockMinimoProducto.setText(String.valueOf(producto.getStock_minimo()));

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
        }
    }

    private void llenarTabla(ArrayList<Producto> listaProductos) {
        tblDatosProducto.getItems().clear();

        /*
        private int id;
    private String ;
    private String ;
    private String ;
    private double ;
    private double ;
    private String presentacion;
        
         */
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

    private void llenarCbosOpciones() {
        actualizarComboCategorias();
        actualizarComboMarcas();
        actualizarComboProveedores();
    }

    private void actualizarComboCategorias() {
        cboCategoriaProducto.getItems().clear();
        this.listaObjetosCategorias = new ArrayList<Categoria>(categoriaDB.getCategorias());

        System.out.println("\n*****************Las CATEGORIAS almacenadas son :");
        for (Categoria categoria : listaObjetosCategorias) {
            System.out.println(categoria.getId_categoria()
                    + "         " + categoria.getNombre()
                    + "         " + categoria.getDescripcion()
            );
        }

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

        System.out.println("*****************Las Marcas almacenadas son :");
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            System.out.println(listaObjetosMarcas.get(i).getId_marca() + "         " + listaObjetosMarcas.get(i).getMarca());
        }

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

        for (int i = 0; i < listaObjetosProveedores.size(); i++) {
            cboProveedorProducto.getItems().add(listaObjetosProveedores.get(i).getNombre_proveedor());
        }
        if (listaObjetosProveedores.size() > 0) {
            cboProveedorProducto.setValue(listaObjetosProveedores.get(0).getNombre_proveedor());
            existenProveedores = true;
        }
    }

    private void mostraCodigoBarras() {
        Document documento = new Document();
        try {
            PdfWriter pdf = PdfWriter.getInstance(documento, new FileOutputStream("codigos.pdf"));
            documento.open();

            Barcode128 code2 = new Barcode128();
            code2.setCode("145784kkh512");

            Image img2 = code2.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
            img2.scalePercent(200);
            documento.add(img2);
            documento.close();

            Barcode128 code3 = new Barcode128();
            code3.setCode("145784kkh512");
            java.awt.Image imgawt = code3.createAwtImage(java.awt.Color.black, java.awt.Color.white);
            //java.awt.Image imgawt = code3.createAwtImage(java.awt.Color.darkGray, java.awt.Color.darkGray);

            BufferedImage bImage = toBufferedImage(imgawt);
            BufferedImage nuevaBImage = scale(bImage, 270, 70);
            javafx.scene.image.Image imgfx = SwingFXUtils.toFXImage(nuevaBImage, null);

            ImageView imageView = new ImageView();
            imageView.setImage(imgfx);
            lblCodigoBarras.setGraphic(imageView);
            //lblCodigoBarras.setGraphic(imageView);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorVistaProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ControladorVistaProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static BufferedImage toBufferedImage(java.awt.Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;

    }

    public static BufferedImage scale(BufferedImage src, int w, int h) {
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

    /*
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

                    productoDB.addUsuario(contenidoTxtNombreUsuario, contenidoTxtLoginUsuario,
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

    }*/
 /*
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

                    productoDB.updateUsuario(idDeUsuarioSeleccionado, contenidoTxtNombreUsuario,
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

    }*/

 /*
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
                productoDB.deleteUsuario(ID);
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

    }*/
 /*
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

        if (nombre.equals("") || login.equals("") || password.equals("") || rol.equals(MARCA_DEFAULT)) {
            return true;
        } else {
            return false;
        }

    }
     */
 /*
    private void limpiarCampos() {
        txtIDUsuario.clear();
        txtNombreUsuario.clear();
        txtLoginUsuario.clear();
        txtPasswordUsuario.clear();
        cboRolUsuario.getSelectionModel().select(0);
    }
     */
 /*
    @FXML
    private void filtrarUsuario() {
        
        filtrarActivado = !filtrarActivado;
        limpiarCampos();
        if (filtrarActivado) {
            lblAyuda.setText(AYUDA_AL_FILTRAR);
            
            btnAgregarProducto.setDisable(true);
            btnModificarProducto.setDisable(true);
            btnEliminarProducto.setDisable(true);

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
            btnAgregarProducto.setDisable(false);
            btnModificarProducto.setDisable(false);
            btnEliminarProducto.setDisable(false);

            btnAgregarProducto.setDisable(false);
            btnModificarProducto.setDisable(false);
            btnEliminarProducto.setDisable(false);

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

            llenarTabla(productoDB.getUsuarios());
            //limpiarCampos();//----------------------------------------------------------
        }
    }*/
 /*
    void agregarUsuarioActivado() {
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

            btnCancelarUsuario.setVisible(true);
            btnRegresarUsuario.setVisible(false);
            btnGuardarInsercionProducto.setVisible(true);
            btnGuardarCambiosModificarUsuario.setVisible(false);

            txtIDUsuario.setEditable(false);
            txtNombreUsuario.setEditable(true);
            txtLoginUsuario.setEditable(true);
            txtPasswordUsuario.setEditable(true);
            cboRolUsuario.setEditable(true);
            cboRolUsuario.getSelectionModel().select(0);

        } else {

        }
    }*/
 /*
    void modificarUsuarioActivado() {
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

            btnCancelarUsuario.setVisible(true);
            btnRegresarUsuario.setVisible(false);
            btnGuardarInsercionProducto.setVisible(false);
            btnGuardarCambiosModificarUsuario.setVisible(true);

            txtIDUsuario.setEditable(false);
            txtNombreUsuario.setEditable(true);
            txtLoginUsuario.setEditable(true);
            txtPasswordUsuario.setEditable(true);
            cboRolUsuario.setEditable(true);
            cboRolUsuario.getSelectionModel().select(0);

        } else {

        }
    }
     */
 /*
    void regresarBotonesAFormaOriginal() {
        modificarActivado = false;
        agregarActivado = false;

        btnAgregarProducto.setDisable(false);
        //btnAgregarUsuario.setStyle("fx-background-color: #222288");
        btnModificarProducto.setDisable(false);
        //btnModificarUsuario.setStyle("fx-background-color: #222288");
        btnEliminarProducto.setDisable(false);
        btnFiltrarProducto.setDisable(false);

        btnCancelarUsuario.setVisible(false);
        btnRegresarUsuario.setVisible(true);
        btnGuardarInsercionProducto.setVisible(false);
        btnGuardarCambiosModificarUsuario.setVisible(false);

        txtIDUsuario.setEditable(false);
        txtNombreUsuario.setEditable(false);
        txtLoginUsuario.setEditable(false);
        txtPasswordUsuario.setEditable(false);
        cboRolUsuario.setEditable(false);
        cboRolUsuario.getSelectionModel().select(0);

        lblAyuda.setText("");
    }
     */
 /*
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
     */
 /*
    private void leerFiltrarTabla(int id, String nombre, String login, String rol) {
        if (id == 0) {
            llenarTabla(productoDB.getUsuariosFiltro1(nombre, login, rol));
        } else {
            llenarTabla(productoDB.getUsuariosFiltro2(id, nombre, login, rol));
        }
    }*/
    void ManejadorFiltro() {

        /*
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
                if (rol.equals(MARCA_DEFAULT)) {
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
        
        
         */
    }

    class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }
    }

}
