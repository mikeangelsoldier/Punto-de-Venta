/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoDeVenta;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mike
 */
public class ControladorVistaLogin implements Initializable {

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    
    public void cambiarVista(ActionEvent e) throws Exception {

        Parent panelTabla = FXMLLoader.load(getClass().getResource("VistaPrincipal.fxml"));
        Scene panelTablaScene = new Scene(panelTabla);

        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(panelTablaScene);
        window.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
