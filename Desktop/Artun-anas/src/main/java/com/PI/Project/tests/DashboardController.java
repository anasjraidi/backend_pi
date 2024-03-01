package com.PI.Project.tests;

//import animatefx.animation.FadeInUp;
//import animatefx.animation.ZoomIn;
import com.PI.Project.utils.MyDabase;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class DashboardController implements Initializable {
    @FXML
    private VBox FieldHolder;
    @FXML
    private ImageView UserImage;
    @FXML
    private Label username;
    @FXML
    private VBox profileslide;
    @FXML
    private void ShowUsers() {
        try {
           // new ZoomIn(FieldHolder).play();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/User_Back_CRUD.fxml"));
            Node userFXML = loader.load();
            FieldHolder.getChildren().clear();
            FieldHolder.getChildren().add(userFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
    @FXML
    private void ShowDashboard() {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/dashboardField.fxml"));
            Node dashFXML = loader.load();
            FieldHolder.getChildren().clear();
            FieldHolder.getChildren().add(dashFXML);
           // new ZoomIn(dashFXML).play();
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
    @FXML
    private void ShowLivraision() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/Livraision_PlaceHolder.fxml"));
            Node LivraisionFXML = loader.load();
            FieldHolder.getChildren().clear();
            FieldHolder.getChildren().add(LivraisionFXML);
            //new ZoomIn(LivraisionFXML).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void ShowReclamation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/reclamation.fxml"));
            Node ReclamationFXML = loader.load();
            FieldHolder.getChildren().clear();
            FieldHolder.getChildren().add(ReclamationFXML);
          //  new ZoomIn(ReclamationFXML).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void ShowEvenement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/evenement.fxml"));
            Node EvenementFXML = loader.load();
            FieldHolder.getChildren().clear();
            FieldHolder.getChildren().add(EvenementFXML);
            //new ZoomIn(EvenementFXML).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void ShowCommandes() {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/Commandes_Placeholder.fxml"));
            Node commandesFXML = loader.load();
            FieldHolder.getChildren().clear();
            FieldHolder.getChildren().add(commandesFXML);
           // new ZoomIn(commandesFXML).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void ShowProduit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/Produit_Placeholder.fxml"));
            Node ProduitFXML = loader.load();
            FieldHolder.getChildren().clear();

            FieldHolder.getChildren().add(ProduitFXML);
          //  new ZoomIn(ProduitFXML).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowDashboard();

    }}


