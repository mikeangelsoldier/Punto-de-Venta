/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.ClienteBD;
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
import AccesoBD.ReporteVentaBD;
import AccesoBD.UsuarioBD;
import AccesoBD.VentaBD;
import Modelo.Cliente;
import Modelo.Proveedor;
import Modelo.Usuario;
import Modelo.Venta;
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
public class ControladorVistaReporteVentas implements Initializable {
    
    VentaBD ventaBD;
    //ReporteVentaBD ventaBD;
    ClienteBD clienteBD;

    ConectaBD_Punto_de_venta conectaBD_PuntoVenta;
    
    private boolean existenProveedores = false;
    private boolean existenUsuarios = false;
    
    private static String AYUDA_AL_GENERAR = "Selecciona las fechas entre las que se debe generar el reporte. Para borrar el reporte presiona Limpiar Tabla";
    
    private ArrayList<Cliente> listaObjetosClientes;
   
    
    Date contenidoFechaDesde,contenidoFechaHasta;
    
    
    @FXML
    private JFXDatePicker pickerFechaDesde,pickerFechaHasta;
    
    @FXML
    private JFXButton btnGenerarPDF, btnGenerarReporte, btnRegresarReporte, btnLimpiarTabla;
    
    @FXML
    private Label lblAyuda;
    
    @FXML
    private TableView<Venta> tblDatosReporteVenta;
    @FXML
    private TableColumn<FacturaPedido, Integer> tbcIdVenta,tbcIdCliente;
    
    @FXML
    private TableColumn<FacturaPedido, String> tbcTipoPago;
    
    @FXML
    private TableColumn<FacturaPedido, Double> tbcSubtotal,tbcIVA, tbcTotal;
    
    @FXML
    private TableColumn<FacturaPedido, String> tbcFecha;
    
    
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
        ventaBD = new VentaBD(conectaBD_PuntoVenta.getConnection());
        clienteBD = new ClienteBD(conectaBD_PuntoVenta.getConnection());
        
        
         listaObjetosClientes = new ArrayList<Cliente>();
        
        llenarTabla(ventaBD.getVentas());

        lblAyuda.setText(AYUDA_AL_GENERAR);

        
    }

    
    
   private void llenarTabla(ArrayList<Venta> listaVentas) {

        for (int i = 0; i < listaVentas.size(); i++) {
            int id = listaVentas.get(i).getIdVenta();
            String fecha = listaVentas.get(i).getFechaVenta();
            double subtotal = listaVentas.get(i).getSubtotalVenta();
            double iva = listaVentas.get(i).getIvaVenta();
            double total = listaVentas.get(i).getTotalVenta();
            String tipoPago = listaVentas.get(i).getFormaPagoVenta();
            int idCliente = listaVentas.get(i).getIdCliente();

            //System.out.println("FK categoria leida= " + categoria);
            //System.out.println("FK proveedor leid= " + proveedor);
            this.listaObjetosClientes = new ArrayList<Cliente>(clienteBD.getClientes());
            String nombreCliente = "";

            for (int j = 0; j < listaObjetosClientes.size(); j++) {
                if (listaObjetosClientes.get(j).getId() == idCliente) {
                    nombreCliente = listaObjetosClientes.get(j).getNombre();
                    //System.out.println(nombreCategoria);
                    break;
                }
            }


            Venta venta = new Venta();
            venta.setIdVenta(id);
            venta.setFechaVenta(fecha);
            venta.setSubtotalVenta(subtotal);
            venta.setIvaVenta(iva);
            venta.setTotalVenta(total);
            venta.setFormaPagoVenta(tipoPago);
            venta.setNombreCliente(nombreCliente);
            //System.out.println("Categoria cambiada a "+productoConNombreCategoriaYProveedor.getNombreCategoria());
            //System.out.println("proveedor cambiada a "+productoConNombreCategoriaYProveedor.getNombreProveedor());
            listaVentas.set(i, venta);

        }

        tblDatosReporteVenta.getItems().clear();

        tbcIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        tbcSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotalVenta"));
        tbcIVA.setCellValueFactory(new PropertyValueFactory<>("ivaVenta"));
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));
        tbcTipoPago.setCellValueFactory(new PropertyValueFactory<>("formaPagoVenta"));
        tbcIdCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        

        for (Venta ventas : listaVentas) {
            tblDatosReporteVenta.getItems().add(ventas);
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
       llenarTabla(ventaBD.getVentas());
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
        for (int i = 0; i < listaObjetosClientes.size(); i++) {
            if (listaObjetosClientes.get(i).getNombre().equals(proveedor)) {
                idProveedor = listaObjetosClientes.get(i).getId();
            }

        }
        return idProveedor;
    }
    
   
    
     
     private void leerFiltrarTabla(String fechaDesde, String fechaHasta) {
        
            llenarTabla(ventaBD.getReporteVentas(fechaDesde,fechaHasta));
        
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
                "src\\reportes\\ReporteVentas.jasper";
        JasperReport jr=null;
        try {
            jr=(JasperReport) JRLoader.loadObjectFromFile(path);
            
             Map parametro = new HashMap();
            
            parametro.put("fecha_inicio",fechaDesde);
            parametro.put("fecha_fin", fechaHasta);
            parametro.put("id_usuario", "");
            parametro.put("id_cliente", "");
          
            
            
            JasperPrint jp=JasperFillManager.fillReport(jr,parametro,conectaBD_PuntoVenta.getConnection());
            
            JasperViewer jv=new JasperViewer(jp,false);
            jv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jv.setTitle("Reporte de Ventas");
            jv.setVisible(true);
            
            
           // conectaBD_punto_de_venta.cerrarConexion();
        } catch (JRException ex) {
           
        }
        
        
    }
    
    
    
    
}

