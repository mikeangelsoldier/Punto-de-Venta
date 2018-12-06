/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.CategoriaBD;
import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.FacturaPedidoBD;
import AccesoBD.MarcaBD;
import AccesoBD.ProductoBD;
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
import AccesoBD.ReporteFaltantesBD;
import AccesoBD.UsuarioBD;
import Modelo.Categoria;
import Modelo.Marca;
import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.Usuario;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author PaulAdrian
 */
public class ControladorFaltantes implements Initializable {
    
    ReporteFaltantesBD faltantesBD;
    ProveedorBD proveedorBD;
    ProductoBD productoBD;
    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    ManejadorFiltroKey manejador;
    MarcaBD marcaBD;
    CategoriaBD categoriaBD;
    
    private static String PROVEEDOR_DEFAULT = "Elegir Proveedor";
    private static String MARCA_DEFAULT = "Elegir Marca";
    private static String CATEGORIA_DEFAULT = "Elegir Categoria";
    
    private boolean existenProveedores = false;
    private boolean existenProductos = false;
    private boolean existenMarcas = false;
    private boolean existenCategoria = false;
    
    private ArrayList<Proveedor> listaObjetosProveedores;
    private ArrayList<Producto> listaObjetosProductos;
    private ArrayList<Marca> listaObjetosMarcas;
    private ArrayList<Categoria> listaObjetosCategorias;
    
    
    
    int contenidoCboId_Proveedor, contenidoCboMarca, contenidoCboCategoria;
        
        
    @FXML
    private JFXComboBox cboProveedor,cboMarca, cboCategoria;
    
       
    @FXML
    private JFXButton btnGenerarPDF, btnRegresar;
    
    
    
    @FXML
    private TableView<Producto> tblDatosFaltantes;
    
    @FXML
    private TableColumn<Producto, Integer> tbcID,tbcProveedor,tbcMarca,tbcCategoria,tbcStock;
    
       
    @FXML
    private TableColumn<Producto, String> tbcDescripcion;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
   
    
    @FXML
    private void handleButtonGenerarPDF(ActionEvent event) {
        
          generarReporte();
        
    }
    
    
    
   
    
    @FXML
    private void filtroBusqueda(ActionEvent event) {
        ManejadorFiltro();
    }

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        faltantesBD = new ReporteFaltantesBD(conectaBD_PuntoVenta.getConnection());
        proveedorBD = new ProveedorBD(conectaBD_PuntoVenta.getConnection());
        productoBD = new ProductoBD(conectaBD_PuntoVenta.getConnection());
        categoriaBD = new CategoriaBD(conectaBD_PuntoVenta.getConnection());
        marcaBD = new MarcaBD(conectaBD_PuntoVenta.getConnection());
        
       
        
        listaObjetosProveedores = new ArrayList<Proveedor>();
        listaObjetosProductos = new ArrayList<Producto>();
        listaObjetosMarcas = new ArrayList<Marca>();
        listaObjetosCategorias = new ArrayList<Categoria>();
        
        llenarCbosOpciones();
        cboProveedor.getSelectionModel().select(0);
        cboCategoria.getSelectionModel().select(0);
        cboMarca.getSelectionModel().select(0);
        llenarTabla(faltantesBD.getProductos());
     
        
        manejador = new ControladorFaltantes.ManejadorFiltroKey();
        filtrarFaltantes();
    }

    private void llenarCbosOpciones() {
        actualizarComboProveedores();
        actualizarComboCategoria();
        actualizarComboMarca();
    }
    
    private void actualizarComboProveedores() {
        cboProveedor.getItems().clear();

        this.listaObjetosProveedores = new ArrayList<Proveedor>(proveedorBD.getProveedores());

       
        cboProveedor.getItems().add(PROVEEDOR_DEFAULT);
        for (int i = 0; i < listaObjetosProveedores.size(); i++) {
            cboProveedor.getItems().add(listaObjetosProveedores.get(i).getNombre_proveedor());
        }
        if (listaObjetosProveedores.size() > 0) {
            cboProveedor.setValue(listaObjetosProveedores.get(0).getNombre_proveedor());
            existenProveedores = true;
        }
        
        
        
        
        
        
    }
    
    private void actualizarComboCategoria() {
        cboCategoria.getItems().clear();

        this.listaObjetosCategorias = new ArrayList<Categoria>(categoriaBD.getCategorias());

        cboCategoria.getItems().add(CATEGORIA_DEFAULT);
        for (int i = 0; i < listaObjetosCategorias.size(); i++) {
            cboCategoria.getItems().add(listaObjetosCategorias.get(i).getNombre());
        }
        if (listaObjetosCategorias.size() > 0) {
            cboCategoria.setValue(listaObjetosCategorias.get(0).getNombre());
            existenCategoria = true;
        }
    }
    
    private void actualizarComboMarca() {
        cboMarca.getItems().clear();
        this.listaObjetosMarcas = new ArrayList<Marca>(marcaBD.getMarcas());
        /*
        System.out.println("*****************Las Marcas almacenadas son :");
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            System.out.println(listaObjetosMarcas.get(i).getId_marca() + "         " + listaObjetosMarcas.get(i).getMarca());
        }
         */

        cboMarca.getItems().add(MARCA_DEFAULT);
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            cboMarca.getItems().add(listaObjetosMarcas.get(i).getMarca());
        }
        if (listaObjetosMarcas.size() > 0) {
            cboMarca.setValue(listaObjetosMarcas.get(0).getMarca());
            existenMarcas = true;
        }
    }
    
    
    
    
   private void llenarTabla(ArrayList<Producto> listaProductos) {

        for (int i = 0; i < listaProductos.size(); i++) {
            int id_producto = listaProductos.get(i).getId();
            String descripcion = listaProductos.get(i).getDescripcion();
            int proveedor = listaProductos.get(i).getProveedor();
            int categoria = listaProductos.get(i).getCategoria();
            String marca = listaProductos.get(i).getMarca();
            int stock = listaProductos.get(i).getStock();
            
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
            productoConNombreCategoriaYProveedor.setId(id_producto);
            productoConNombreCategoriaYProveedor.setDescripcion(descripcion);
            productoConNombreCategoriaYProveedor.setMarca(marca);
            productoConNombreCategoriaYProveedor.setStock(stock);
            productoConNombreCategoriaYProveedor.setNombreCategoria(nombreCategoria);
            productoConNombreCategoriaYProveedor.setNombreProveedor(nombreProvedor);

            //System.out.println("Categoria cambiada a "+productoConNombreCategoriaYProveedor.getNombreCategoria());
            //System.out.println("proveedor cambiada a "+productoConNombreCategoriaYProveedor.getNombreProveedor());
            listaProductos.set(i, productoConNombreCategoriaYProveedor);

        }

        tblDatosFaltantes.getItems().clear();

        tbcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        tbcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tbcStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        

        for (Producto producto : listaProductos) {
            tblDatosFaltantes.getItems().add(producto);
            
        }
    }
   
  
    private void filtrarFaltantes() {

        

            cboProveedor.valueProperty().addListener(manejador);
            cboCategoria.valueProperty().addListener(manejador);
            cboMarca.valueProperty().addListener(manejador);
          

            

           // llenarTabla(faltantesBD.getProductos());
            
        
    }
    
    
    
    void ManejadorFiltro() {

        // System.out.println("si entra al metodo");
      

            //System.out.println("Si entra if");
           
            String proveedor = cboProveedor.getSelectionModel().getSelectedItem().toString();
            String categoria = cboCategoria.getSelectionModel().getSelectedItem().toString();
            String marca = cboMarca.getSelectionModel().getSelectedItem().toString();
            


            if (proveedor.equals(PROVEEDOR_DEFAULT)) {
                proveedor = "";
            }else{
               proveedor = String.valueOf(obtenerIDdeProveedor(proveedor)); 
            }

            if (marca.equals(MARCA_DEFAULT)) {
                marca = "";
            }else{
                marca = cboMarca.getSelectionModel().getSelectedItem().toString();
                System.out.println(marca);
            }
            
            if (categoria.equals(CATEGORIA_DEFAULT)) {
                categoria = "";
            }else{
                categoria = String.valueOf(obtenerIDdeCategoria(categoria));
            }
            
            
            leerFiltrarTabla(marca , categoria, proveedor);

        

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
    
    private int obtenerIDdeMarca(String marca) {
        int idMarca = 0;
        for (int i = 0; i < listaObjetosMarcas.size(); i++) {
            if (listaObjetosMarcas.get(i).getMarca().equals(marca)) {
                idMarca = listaObjetosMarcas.get(i).getId_marca();
            }

        }
        return idMarca;
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
    
     
     
     private void leerFiltrarTabla(String marca,String categoria,String proveedor) {
        
            llenarTabla(faltantesBD.getFaltantes(marca, categoria, proveedor));
        
    }
     
    class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }
    }
//    public void btnImprimir(ArrayList<Producto> listaProductos) {   
//        ProductoReport producto;
//        List <ProductoReport>lista = new ArrayList<>();
//        for (int i = 0; i < listaProductos.size(); i++) {
//            String id_producto =String.valueOf(listaProductos.get(i).getId());
//            String descripcion = listaProductos.get(i).getDescripcion();
//            int proveedor = listaProductos.get(i).getProveedor();
//            int categoria = listaProductos.get(i).getCategoria();
//            String marca = listaProductos.get(i).getMarca();
//            String stock = String.valueOf(listaProductos.get(i).getStock());
//            
//           String nombreProvedor = "";
//            String nombreCategoria = "";
//
//            for (int j = 0; j < listaObjetosCategorias.size(); j++) {
//                if (listaObjetosCategorias.get(j).getId_categoria() == categoria) {
//                    nombreCategoria = listaObjetosCategorias.get(j).getNombre();
//                    //System.out.println(nombreCategoria);
//                    break;
//                }
//            }
//
//            for (int j = 0; j < listaObjetosProveedores.size(); j++) {
//                if (listaObjetosProveedores.get(j).getId_proveedor() == proveedor) {
//                    nombreProvedor = listaObjetosProveedores.get(j).getNombre_proveedor();
//                    //System.out.println(nombreProvedor);
//                    break;
//                }
//            }
//
//            producto = new ProductoReport(id_producto,descripcion,
//                    nombreProvedor,nombreCategoria,marca,stock);
//          
//            lista.add(producto);
//            //System.out.println("Categoria cambiada a "+productoConNombreCategoriaYProveedor.getNombreCategoria());
//            //System.out.println("proveedor cambiada a "+productoConNombreCategoriaYProveedor.getNombreProveedor());
//            //listaProductos.set(i, productoConNombreCategoriaYProveedor);
//            //System.out.println(listaProductos.get(i).getCategoria());
//
//        }
//        System.out.println(lista.size());
//          
//        //Creamos una lista de empleados con ArrayList para obtener cada empleado
////        for (int i = 0; i < tblDatosFaltantes.getItems().size(); i++)                        
////            for (TableColumn column : tblDatosFaltantes.getVisibleLeafColumns())            
////                System.out.println(column.getCellData(i));
////        for(int i=0; i<tblDatosFaltantes.getItems().size(); i++){ // Iterena cada fila de la tabla
////            em = new Producto(tblDatosFaltantes.ge.toString(),tbEmpleados.getValueAt(i,1).toString(), //Tomamos de la tabla el valor de cada columna y creamos un objeto empleado
////            tbEmpleados.getValueAt(i, 2).toString(),tbEmpleados.getValueAt(i, 3).toString(),tbEmpleados.getValueAt(i,4).toString());
////            lista.add(em); //Agregamos el objeto empleado a la lista
////        }
//        JasperReport reporte; // Instaciamos el objeto reporte
//        String path = "src\\Controlador\\Reports.jasper"; //Ponemos la localizacion del reporte creado
//        try {
//            reporte = (JasperReport) JRLoader.loadObjectFromFile(path); //Se carga el reporte de su localizacion
//            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(lista)); //Agregamos los parametros para llenar el reporte
//            JasperViewer viewer = new JasperViewer(jprint, false); //Se crea la vista del reportes
//            viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Se declara con dispose_on_close para que no se cierre el programa cuando se cierre el reporte
//            viewer.setVisible(true); //Se vizualiza el reporte
//        } catch (JRException ex) {
//           
//        }
//    }    
    
    
    private void generarReporte() {
        String proveedor = cboProveedor.getSelectionModel().getSelectedItem().toString();
        String categoria = cboCategoria.getSelectionModel().getSelectedItem().toString();
        String marca = cboMarca.getSelectionModel().getSelectedItem().toString();
            


            if (proveedor.equals(PROVEEDOR_DEFAULT)) {
                proveedor = "";
            }else{
               proveedor = String.valueOf(obtenerIDdeProveedor(proveedor)); 
            }

            if (marca.equals(MARCA_DEFAULT)) {
                marca = "";
            }else{
                marca = cboMarca.getSelectionModel().getSelectedItem().toString();
                System.out.println(marca);
            }
            
            if (categoria.equals(CATEGORIA_DEFAULT)) {
                categoria = "";
            }else{
                categoria = String.valueOf(obtenerIDdeCategoria(categoria));
            }
               
        String path=
                "src\\reportes\\ReporteFaltantes.jasper";
        JasperReport jr=null;
        try {
            jr=(JasperReport) JRLoader.loadObjectFromFile(path);
            
             Map parametro = new HashMap();
            
            parametro.put("marca",marca);
            parametro.put("id_categoria", categoria);
            parametro.put("id_proveedor", proveedor);
            
            
            JasperPrint jp=JasperFillManager.fillReport(jr,parametro,conectaBD_PuntoVenta.getConnection());
            
            JasperViewer jv=new JasperViewer(jp,false);
            jv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jv.setTitle("Reporte Productos Faltantes");
            jv.setVisible(true);
            
            
           // conectaBD_punto_de_venta.cerrarConexion();
        } catch (JRException ex) {
           
        }
        
        
    }
    
    
}
