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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mike
 */
public class ControladorVistaPrincipal implements Initializable {

    @FXML
    Pane panel_principal;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void cambiarAVistaProducto (ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaProducto.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    public void cambiarAVistaCliente (ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaCliente.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cambiarAVistaReportePedidos (ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaReportePedidos.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void cambiarAVistaReporteVentas (ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaReporteVentas.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cambiarAVistaUsuario (ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaUsuario.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cambiarAVistaVentas (ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaVenta.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cambiarAVistaProveedor (ActionEvent e) throws Exception {
        try {
            panel_principal.getChildren().clear();
            panel_principal.getChildren().add(FXMLLoader.load(getClass().getResource("/PuntoDeVenta/VistaProveedor.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
