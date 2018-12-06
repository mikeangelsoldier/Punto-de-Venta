/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import AccesoBD.UsuarioBD;
import AccesoBD.ClienteBD;
import AccesoBD.ConectaBD_Punto_de_venta;
import AccesoBD.DetalleVentaBD;
import AccesoBD.VentaBD;
import Modelo.DetalleVenta;
import Modelo.Usuario;
import Modelo.Venta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.WindowConstants;
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
 * @author Personal
 */
public class ControladorVistaHistorialVentas implements Initializable {

    private static String AÑO_DEFAULT = "Todos";
    private static String MES_DEFAULT = "Todos";
    private static String DIA_DEFAULT = "Todos";
    private static String USUARIO_DEFAULT = "Todos";

    private boolean existenAños = false;
    private boolean existenMeses = false;
    private boolean existenDias = false;
    private boolean existenUsuarios = false;

    ConectaBD_Punto_de_venta conectaBD_punto_de_venta;
    VentaBD ventaBD;
    DetalleVentaBD detalleVentaBD;
    UsuarioBD usuarioBD;

    ManejadorFiltroKey manejadorFiltro;

    ArrayList<String> listaAños;
    ArrayList<String> listaMeses;
    ArrayList<String> listaDias;
    ArrayList<Usuario> listaUsuarios;

    //------------------------------------------------------vista Ventas
    @FXML
    private JFXTextField txtIdVenta,
            txtSubTotal,
            txtIva,
            txtTotal,
            txtFormaPago,
            txtCliente;

    @FXML
    private JFXComboBox cboAños,
            cboMeses, cboDias, cboUsuarios;

    @FXML
    private JFXDatePicker dpkFechaVenta;

    @FXML
    private TableView<Venta> tblDatosVentas;

    @FXML
    private TableColumn<Venta, Integer> tbcIdVenta, tbcIdClienteVenta;

    @FXML
    private TableColumn<Venta, Double> tbcSubTotalVenta,
            tbcIvaVenta, tbcTotalVenta;

    @FXML
    private TableColumn<Venta, String> tbcFechaVenta,
            tbcFormaPago, tbcNombreClienteVenta;

    @FXML
    private JFXButton btnVerDetallesVenta;

    @FXML
    private Pane panelPrincipal;

    //------------------------------------------------------vista detalles de Ventas
    @FXML
    private TableView<DetalleVenta> tblDatosDetallesVentas;

    @FXML
    private TableColumn<DetalleVenta, Integer> tbcIdDetalleVenta, tbcCantidadDetalleVenta;

    @FXML
    private TableColumn<DetalleVenta, Double> tbcPrecioDetalleVenta,
            tbcImporteDetalleVenta;

    @FXML
    private TableColumn<DetalleVenta, String> tbcCodigoProductoDetalleVenta,
            tbcDescripcionDetalleVenta;

    @FXML
    private JFXButton btnRegresarAVentas;

    @FXML
    private Pane panelDetallesDeVenta;

    @FXML
    private void btnVerDetallesVentaEvento(ActionEvent event) {
        if (txtIdVenta.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Un error ha ocurrido");
            alert.setContentText("Por favor selecciona una venta");
            alert.show();
            return;
        }

        
        iniciarVistaDetallesVentas();
        panelPrincipal.setDisable(true);
        panelDetallesDeVenta.setVisible(true);
    }
    
    @FXML
    private void btnGenerarReporteEvento(ActionEvent event) {
        generarReporte();
    }
    
    

    @FXML
    private void btnRegresarAVentasEvento(ActionEvent event) {
        panelPrincipal.setDisable(false);
        panelDetallesDeVenta.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelPrincipal.setVisible(true);
        panelPrincipal.setDisable(false);
        panelDetallesDeVenta.setVisible(false);
        iniciarVistaPrincipal();

    }

    private void iniciarVistaPrincipal() {
        conectaBD_punto_de_venta = new ConectaBD_Punto_de_venta();

        ventaBD = new VentaBD(conectaBD_punto_de_venta.getConnection());
        usuarioBD = new UsuarioBD(conectaBD_punto_de_venta.getConnection());

        //txtApellidoPaternoB.textProperty().addListener(manejadorFiltro);
        //txtSemestreB.textProperty().addListener(manejadorFiltro);
        //txtNumControlB.textProperty().addListener(manejadorFiltro);
        llenarCombos();

        manejadorFiltro = new ManejadorFiltroKey();

        cboAños.valueProperty().addListener(manejadorFiltro);
        cboMeses.valueProperty().addListener(manejadorFiltro);
        cboDias.valueProperty().addListener(manejadorFiltro);
        cboUsuarios.valueProperty().addListener(manejadorFiltro);

        //---------------------------
        btnVerDetallesVenta.setDisable(true);

        cboAños.getSelectionModel().select(0);
        cboMeses.getSelectionModel().select(0);
        cboDias.getSelectionModel().select(0);
        cboUsuarios.getSelectionModel().select(0);
        llenadoInicialTablaVentas();
        //-------
    }

    private void llenadoInicialTablaVentas() {

        String año = cboAños.getSelectionModel().getSelectedItem().toString();
        String mes = cboMeses.getSelectionModel().getSelectedItem().toString();
        String dia = cboDias.getSelectionModel().getSelectedItem().toString();
        String usuario = cboUsuarios.getSelectionModel().getSelectedItem().toString();

        if (año.equals(AÑO_DEFAULT)) {
            año = "";
        }

        if (mes.equals(MES_DEFAULT)) {
            mes = "";
        }

        if (dia.equals(DIA_DEFAULT)) {
            dia = "";
        }

        if (usuario.equals(USUARIO_DEFAULT)) {
            usuario = "";
        } else {
            usuario = String.valueOf(obtenerIDdeUsuario(usuario));
        }

        llenarTablaVentas(ventaBD.getVentasAñoMesDiaFiltro(año, mes, dia, usuario));

    }

    private void llenarTablaVentas(ArrayList<Venta> listaVentas) {
        tblDatosVentas.getItems().clear();

        asignarCamposATableColumnsVenta();

        for (Venta venta : listaVentas) {
            tblDatosVentas.getItems().add(venta);
        }
    }

    private void asignarCamposATableColumnsVenta() {
        tbcIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
        tbcFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        tbcSubTotalVenta.setCellValueFactory(new PropertyValueFactory<>("subtotalVenta"));
        tbcIvaVenta.setCellValueFactory(new PropertyValueFactory<>("ivaVenta"));
        tbcTotalVenta.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));
        tbcFormaPago.setCellValueFactory(new PropertyValueFactory<>("formaPagoVenta"));
        tbcIdClienteVenta.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tbcNombreClienteVenta.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
    }

    private void iniciarVistaDetallesVentas() {

        detalleVentaBD = new DetalleVentaBD(conectaBD_punto_de_venta.getConnection());

        String venta = txtIdVenta.getText();

        llenarTablaDetallesVenta(detalleVentaBD.getDetallesConProductosDeUnaSolaVenta(Integer.parseInt(venta)));
    }

    @FXML
    private void handleTableChangeVentas(Event event) {
        Venta venta = tblDatosVentas.getSelectionModel().getSelectedItem();

        if (venta != null) {
            txtIdVenta.setText(String.valueOf(venta.getIdVenta()));
            dpkFechaVenta.setValue(LocalDate.parse(venta.getFechaVenta()));
            txtSubTotal.setText(String.valueOf(venta.getSubtotalVenta()));
            txtIva.setText(String.valueOf(venta.getIvaVenta()));
            txtTotal.setText(String.valueOf(venta.getTotalVenta()));
            txtFormaPago.setText(venta.getFormaPagoVenta());
            txtCliente.setText(venta.getNombreCliente());
        }
        btnVerDetallesVenta.setDisable(false);
    }

    private void llenarCombos() {
        cboAños.getItems().clear();
        this.listaAños = new ArrayList<String>(ventaBD.getAñosDeVentas());

        cboMeses.getItems().clear();
        this.listaMeses = new ArrayList<String>(ventaBD.getMesesDeVentas());

        cboDias.getItems().clear();
        this.listaDias = new ArrayList<String>(ventaBD.getDiasDeVentas());

        cboUsuarios.getItems().clear();
        this.listaUsuarios = new ArrayList<Usuario>(usuarioBD.getUsuarios());

        cboAños.getItems().add(AÑO_DEFAULT);
        for (int i = 0; i < listaAños.size(); i++) {
            cboAños.getItems().add(listaAños.get(i));
        }
        if (listaAños.size() > 0) {
            cboAños.setValue(listaAños.get(0));
            existenAños = true;
        }

        cboMeses.getItems().add(MES_DEFAULT);
        for (int i = 0; i < listaMeses.size(); i++) {
            cboMeses.getItems().add(listaMeses.get(i));
        }
        if (listaMeses.size() > 0) {
            cboMeses.setValue(listaMeses.get(0));
            existenMeses = true;
        }

        cboDias.getItems().add(DIA_DEFAULT);
        for (int i = 0; i < listaDias.size(); i++) {
            cboDias.getItems().add(listaDias.get(i));
        }
        if (listaDias.size() > 0) {
            cboDias.setValue(listaDias.get(0));
            existenDias = true;
        }

        cboUsuarios.getItems().add(USUARIO_DEFAULT);
        for (int i = 0; i < listaUsuarios.size(); i++) {
            cboUsuarios.getItems().add(listaUsuarios.get(i).getNombre());
        }
        if (listaUsuarios.size() > 0) {
            cboUsuarios.setValue(listaUsuarios.get(0).getNombre());
            existenUsuarios = true;
        }
    }

    private void llenarTablaDetallesVenta(ArrayList<DetalleVenta> listaDetallesVentas) {
        tblDatosDetallesVentas.getItems().clear();

        asignarCamposATableColumnsDetallesVenta();

        for (DetalleVenta detalleVenta : listaDetallesVentas) {
            tblDatosDetallesVentas.getItems().add(detalleVenta);
        }

        if (tblDatosDetallesVentas.getItems().isEmpty()) {
            btnVerDetallesVenta.setDisable(true);
        } else if (tblDatosDetallesVentas.getSelectionModel().getSelectedItems().size() > 0) {
            btnVerDetallesVenta.setDisable(false);
        }
    }

    private void asignarCamposATableColumnsDetallesVenta() {
        tbcIdDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("idDetalleVenta"));
        tbcCodigoProductoDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        tbcDescripcionDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("descripcionProducto"));
        tbcPrecioDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        tbcCantidadDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        tbcImporteDetalleVenta.setCellValueFactory(new PropertyValueFactory<>("importeProducto"));
    }

    private void limpiarCampos() {
        txtIdVenta.clear();
        txtSubTotal.clear();
        txtIva.clear();
        txtTotal.clear();
        txtFormaPago.clear();
        txtCliente.clear();

        cboAños.getSelectionModel().select(0);
        cboMeses.getSelectionModel().select(0);
        cboDias.getSelectionModel().select(0);
        cboUsuarios.getSelectionModel().select(0);
    }

    private void ManejadorFiltro() {
        String año, mes, dia, usuario;
        año = cboAños.getSelectionModel().getSelectedItem().toString();
        mes = cboMeses.getSelectionModel().getSelectedItem().toString();
        dia = cboDias.getSelectionModel().getSelectedItem().toString();
        usuario = cboUsuarios.getSelectionModel().getSelectedItem().toString();

        if (año.equals(AÑO_DEFAULT)) {
            año = "";
        }

        if (mes.equals(MES_DEFAULT)) {
            mes = "";
        }

        if (dia.equals(DIA_DEFAULT)) {
            dia = "";
        }

        if (usuario.equals(USUARIO_DEFAULT)) {
            usuario = "";
        } else {
            usuario = String.valueOf(obtenerIDdeUsuario(usuario));
        }

        llenarTablaVentas(ventaBD.getVentasAñoMesDiaFiltro(año, mes, dia, usuario));
    }

    private int obtenerIDdeUsuario(String usuario) {
        int idUsuario = 0;
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getNombre().equals(usuario)) {
                idUsuario = listaUsuarios.get(i).getId();
            }

        }
        return idUsuario;
    }

    class ManejadorFiltroKey implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            ManejadorFiltro();
        }

    }

    private void generarReporte() {
        String path
                = "src\\reportes\\ReporteVentaAñoMes.jasper";
        JasperReport jr = null;
        try {
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);

            String año, mes, dia, usuario;
            año = cboAños.getSelectionModel().getSelectedItem().toString();
            mes = cboMeses.getSelectionModel().getSelectedItem().toString();
            dia = cboDias.getSelectionModel().getSelectedItem().toString();
            usuario = cboUsuarios.getSelectionModel().getSelectedItem().toString();

            if (año.equals(AÑO_DEFAULT)) {
                año = "";
            }

            if (mes.equals(MES_DEFAULT)) {
                mes = "";
            }

            if (dia.equals(DIA_DEFAULT)) {
                dia = "";
            }

            if (usuario.equals(USUARIO_DEFAULT)) {
                usuario = "";
            } else {
                usuario = String.valueOf(obtenerIDdeUsuario(usuario));
            }
            
            Map parametro = new HashMap();

            parametro.put("año", año);
            parametro.put("mes", mes);
            parametro.put("dia", dia);
            parametro.put("id_usuario", usuario);

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conectaBD_punto_de_venta.getConnection());

            JasperViewer jv = new JasperViewer(jp, false);
            jv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jv.setTitle("Reporte de ventas");
            jv.setVisible(true);

            // conectaBD_punto_de_venta.cerrarConexion();
        } catch (JRException ex) {

        }

    }
}
