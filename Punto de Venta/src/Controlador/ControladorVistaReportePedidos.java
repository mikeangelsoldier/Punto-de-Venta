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
import AccesoBD.ReportePedidoBD;
import AccesoBD.UsuarioBD;
import Modelo.Proveedor;
import Modelo.Usuario;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
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
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author PaulAdrian
 */
public class ControladorVistaReportePedidos implements Initializable {
    
    FacturaPedidoBD facturaBD;
    ProveedorBD proveedorBD;
    UsuarioBD usuarioBD;
    ReportePedidoBD reporteBD;

    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    
    private boolean existenProveedores = false;
    private boolean existenUsuarios = false;
    
    private static String AYUDA_AL_GENERAR = "Selecciona las fechas entre las que se debe generar el reporte. Para borrar el reporte presiona Limpiar Tabla";
    
    private ArrayList<Proveedor> listaObjetosProveedores;
    private ArrayList<Usuario> listaObjetosUsuarios;
   
    
    Date contenidoFechaDesde,contenidoFechaHasta;
    
    
    @FXML
    private JFXDatePicker pickerFechaDesde,pickerFechaHasta;
    
    @FXML
    private JFXButton btnGenerarPDF, btnGenerarReporte, btnRegresarReporte, btnLimpiarTabla;
    
    @FXML
    private Label lblAyuda;
    
    @FXML
    private TableView<FacturaPedido> tblDatosPedidos;
    
    @FXML
    private TableColumn<FacturaPedido, String> tbcFolio,tbcProveedor,tbcUsuario;
    
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
    private void handleButtonPDF(ActionEvent event) {
        generarReporte();
    }
    
    
    
        
      
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conectaBD_PuntoVenta = new ConectaBD_Punto_de_venta();
        facturaBD = new FacturaPedidoBD(conectaBD_PuntoVenta.getConnection());
        proveedorBD = new ProveedorBD(conectaBD_PuntoVenta.getConnection());
        usuarioBD = new UsuarioBD(conectaBD_PuntoVenta.getConnection());
        reporteBD = new ReportePedidoBD(conectaBD_PuntoVenta.getConnection());
        
         listaObjetosProveedores = new ArrayList<Proveedor>();
         listaObjetosUsuarios = new ArrayList<Usuario>();
        
        llenarTabla(facturaBD.getPedidos());

        lblAyuda.setText(AYUDA_AL_GENERAR);

        
    }

    
    
   private void llenarTabla(ArrayList<FacturaPedido> listaPedidos) {

        for (int i = 0; i < listaPedidos.size(); i++) {
            String folio = listaPedidos.get(i).getFolio_factura();
            int proveedor = listaPedidos.get(i).getId_proveedor();
            int usuario = listaPedidos.get(i).getId_usuario();
            float monto = listaPedidos.get(i).getMonto();
            Date fecha = listaPedidos.get(i).getFecha();

            //System.out.println("FK categoria leida= " + categoria);
            //System.out.println("FK proveedor leid= " + proveedor);
            this.listaObjetosProveedores = new ArrayList<Proveedor>(proveedorBD.getProveedores());
            this.listaObjetosUsuarios = new ArrayList<Usuario>(usuarioBD.getUsuarios());
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
   
//   @FXML
//    private void filtrarPedido() {
//
//        filtrarActivado = !filtrarActivado;
//        limpiarCampos();
//        if (filtrarActivado) {
//            lblAyuda.setText(AYUDA_AL_FILTRAR);
//
//            btnAgregarPedido.setDisable(true);
//            btnModificarPedido.setDisable(true);
//            btnEliminarPedido.setDisable(true);
//          
//            btnRegresarPedido.setVisible(true);
//
//            txtFolioFactura.setEditable(true);
//            cboProveedor.setEditable(true);
//            cboProveedor.getSelectionModel().select(0);
//            cboUsuario.setEditable(true);
//            cboUsuario.getSelectionModel().select(0);
//            txtMonto.setEditable(true);
//            pickerFecha.setValue(null);
//            pickerFecha.setEditable(true);
//
//            txtFolioFactura.textProperty().addListener(manejador);
//            cboProveedor.valueProperty().addListener(manejador);
//            cboUsuario.valueProperty().addListener(manejador);
//            txtMonto.textProperty().addListener(manejador);
//            pickerFecha.valueProperty().addListener(manejador);
//            //cboRolUsuario.promptTextProperty().addListener(manejador);
//            //limpiarCampos();
//        } else {
//            btnAgregarPedido.setDisable(false);
//            btnModificarPedido.setDisable(false);
//            btnEliminarPedido.setDisable(false);
//          
//            btnRegresarPedido.setVisible(false);
//
//             txtFolioFactura.setEditable(false);
//            cboProveedor.setEditable(false);
//            cboProveedor.getSelectionModel().select(0);
//            cboUsuario.setEditable(false);
//            cboUsuario.getSelectionModel().select(0);
//            txtMonto.setEditable(false);
//            pickerFecha.setValue(null);
//            pickerFecha.setEditable(false);
//
//            txtFolioFactura.textProperty().removeListener(manejador);
//            cboProveedor.valueProperty().removeListener(manejador);
//            cboUsuario.valueProperty().removeListener(manejador);
//            txtMonto.textProperty().removeListener(manejador);
//            pickerFecha.valueProperty().removeListener(manejador);
//
//            llenarTabla(facturaBD.getPedidos());
//            //limpiarCampos();//----------------------------------------------------------
//            lblAyuda.setText("");
//
//        }
//    }
   
   @FXML
   void limpiarTabla(){
       llenarTabla(facturaBD.getPedidos());
   }
    
    @FXML
    void ManejadorFiltro() {

            if(!camposPorCompletar()){
               String fechaDesde = String.valueOf(pickerFechaDesde.getValue());
               String fechaHasta = String.valueOf(pickerFechaHasta.getValue());
               leerFiltrarTabla(fechaDesde,fechaHasta);
            }else{
                Alert alertSeleccion = new Alert(Alert.AlertType.WARNING);
                    alertSeleccion.setTitle("Advertencia");
                    alertSeleccion.setHeaderText(null);
                    alertSeleccion.setContentText("Falta elegir una fecha para generar el reporte");
                    alertSeleccion.show();
                    return;  
            }

        

    }
    
    private boolean camposPorCompletar() {
        String fechaDesde = "";
        String fechaHasta = "";
        
        if(pickerFechaDesde.getValue() == null){
            fechaDesde="";
        }else{
           fechaDesde =pickerFechaDesde.getValue().toString();
        }
        
        if(pickerFechaHasta.getValue() == null){
            fechaHasta="";
        }else{
           fechaHasta =pickerFechaHasta.getValue().toString();
        }
        
        if (fechaDesde.equals("") || fechaHasta.equals("")) {
            return true;
        } else {
            return false;
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
    
     
     private void leerFiltrarTabla(String fechaDesde, String fechaHasta) {
        
            llenarTabla(reporteBD.getReportePedidos(fechaDesde,fechaHasta));
        
    }
     
     private void generarReporte() {
        String fechaDesde = "";
        String fechaHasta = "";
        
        if(pickerFechaDesde.getValue() == null){
            fechaDesde="1980-01-01";
        }else{
           fechaDesde =pickerFechaDesde.getValue().toString();
        }
        
        if(pickerFechaHasta.getValue() == null){
            fechaHasta="2050-01-01";
        }else{
           fechaHasta =pickerFechaHasta.getValue().toString();
        }
               
        String path=
                "src\\reportes\\ReportePedidos.jasper";
        JasperReport jr=null;
        try {
            jr=(JasperReport) JRLoader.loadObjectFromFile(path);
            
             Map parametro = new HashMap();
            
            parametro.put("fecha_inicio",fechaDesde);
            parametro.put("fecha_fin", fechaHasta);
          
            
            
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
