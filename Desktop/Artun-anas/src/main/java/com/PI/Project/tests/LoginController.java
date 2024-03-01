package com.PI.Project.tests;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    Button loginButton;
    @FXML
    AnchorPane SideForm;
    private void EnterDashboard() {
        try {
            // Load Dashboard.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/Dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();

            // Set the scene to Dashboard.fxml
            Scene scene = new Scene(dashboardRoot);
            stage.setScene(scene);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
    @FXML
    private void LoadRegister() {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/PI/Project/GUI/RegisterView.fxml"));
            Node userFXML = loader.load();
            SideForm.getChildren().clear();
            SideForm.getChildren().add(userFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
}
