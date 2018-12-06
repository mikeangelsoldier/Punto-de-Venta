/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import PuntoDeVenta.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Mike
 */
public class ControladorVistaPrincipal implements Initializable {

    @FXML
    Pane panel_principal;

    @FXML
    MenuItem menuItemVentas,
            menuItemVerificarProductos,
            menuItemHistorialVentas,
            menuItemReporteAlmacen,
            menuItemReporteVentas,
            menuItemCatalogoUsuarios,
            menuItemCatalogoProveedores,
            menuItemCatalogoProductos,
            menuItemCatalogoClientes,
            menuItemCatalogoCategorias,
            menuItemCatalogoSucursales,
            menuItemPedidos,
            menuItemAlmacen
            ;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        habilitarOpciones();
    }

    private void habilitarOpciones() {
        if (loginMeta.rolUsuario.equals("Empleado")) {
            menuItemVentas.setVisible(true);
            menuItemVerificarProductos.setVisible(true);
            menuItemHistorialVentas.setVisible(true);
            menuItemReporteAlmacen.setVisible(false);
            menuItemReporteVentas.setVisible(false);
            menuItemCatalogoUsuarios.setVisible(false);
            menuItemCatalogoProveedores.setVisible(false);
            menuItemCatalogoProductos.setVisible(true);
            menuItemCatalogoClientes.setVisible(false);
            menuItemCatalogoCategorias.setVisible(true);
            menuItemCatalogoSucursales.setVisible(false);
            menuItemPedidos.setVisible(false);
            menuItemAlmacen.setVisible(false);
        } else if (loginMeta.rolUsuario.equals("Programador")) {
            menuItemVentas.setVisible(true);
            menuItemVerificarProductos.setVisible(true);
            menuItemHistorialVentas.setVisible(true);
            menuItemReporteAlmacen.setVisible(true);
            menuItemReporteVentas.setVisible(true);
            menuItemCatalogoUsuarios.setVisible(true);
            menuItemCatalogoProveedores.setVisible(true);
            menuItemCatalogoProductos.setVisible(true);
            menuItemCatalogoClientes.setVisible(true);
            menuItemCatalogoCategorias.setVisible(true);
            menuItemCatalogoSucursales.setVisible(true);
            menuItemPedidos.setVisible(true);
            menuItemAlmacen.setVisible(true);
        } else if (loginMeta.rolUsuario.equals("Administrador")) {
            menuItemVentas.setVisible(true);
            menuItemVerificarProductos.setVisible(true);
            menuItemHistorialVentas.setVisible(true);
            menuItemReporteAlmacen.setVisible(true);
            menuItemReporteVentas.setVisible(true);
            menuItemCatalogoUsuarios.setVisible(true);
            menuItemCatalogoProveedores.setVisible(true);
            menuItemCatalogoProductos.setVisible(true);
            menuItemCatalogoClientes.setVisible(true);
            menuItemCatalogoCategorias.setVisible(true);
            menuItemCatalogoSucursales.setVisible(true);
            menuItemPedidos.setVisible(true);
            menuItemAlmacen.setVisible(true);
        } else if (loginMeta.rolUsuario.equals("Encargado de Almacen")) {
            menuItemVentas.setVisible(true);
            menuItemVerificarProductos.setVisible(true);
            menuItemHistorialVentas.setVisible(true);
            menuItemReporteAlmacen.setVisible(true);
            menuItemReporteVentas.setVisible(true);
            menuItemCatalogoUsuarios.setVisible(false);
            menuItemCatalogoProveedores.setVisible(false);
            menuItemCatalogoProductos.setVisible(false);
            menuItemCatalogoClientes.setVisible(false);
            menuItemCatalogoCategorias.setVisible(false);
            menuItemCatalogoSucursales.setVisible(false);
            menuItemPedidos.setVisible(true);
            menuItemAlmacen.setVisible(true);
        }
    }

    @FXML
    public void cambiarAVistaProducto(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaProducto.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaCliente(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaCliente.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaReportePedidos(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaReportePedido.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaReporteVentas(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaReporteVentas.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaPedidos(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaFacturaPedido.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaUsuario(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaUsuario.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaVentas(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaVenta.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaVerificarProducto(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaVerificarProducto.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaHistorialVentas(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaHistorialVentas.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaAlmacen(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaAlmacen.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaProveedor(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaProveedor.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaCategoria(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaCategoria_1.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaSucursales(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaSucursal.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cambiarAVistaReporteAlmacen(ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaProductosFaltantes.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cerrarSesion(ActionEvent e) throws Exception {
        try {
            Parent panelTabla = FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaLogin.fxml"));
            Scene panelTablaScene = new Scene(panelTabla);

            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(panelTablaScene);
            window.centerOnScreen();
            window.show();

        } catch (Exception ex) {

        }
    }

}
