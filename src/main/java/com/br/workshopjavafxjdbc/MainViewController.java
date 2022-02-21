package com.br.workshopjavafxjdbc;

import com.br.workshopjavafxjdbc.HelloApplication;
import com.br.workshopjavafxjdbc.gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction(){
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartmentAction(){
        System.out.println("onMenuItemDepartmentAction");
    }

    @FXML
    public void onMenuItemAboutAction(){
        loadView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private synchronized void loadView(){
        try {
            URL fxmlLocation = getClass().getResource ("About.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            VBox newVbox = loader.load();

            Scene mainScene = HelloApplication.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());
        }
        catch (IOException e){
            Alerts.showAlert("IO exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
