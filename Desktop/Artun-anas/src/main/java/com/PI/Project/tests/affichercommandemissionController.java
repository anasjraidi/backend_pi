package com.PI.Project.tests;

//import animatefx.animation.ZoomIn;
import com.PI.Project.models.mission;
import com.PI.Project.services.commandemissionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.PI.Project.models.commande;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class affichercommandemissionController {

    private final commandemissionService cm = new commandemissionService();
    private ObservableList<commande> commandemissionList = FXCollections.observableArrayList();

    private ObservableList<String> items = FXCollections.observableArrayList(); // Déplacer ici


    private String currentCommandeString;
    private int id;
    @FXML
    private TextField textFieldMissionId;


    @FXML
    private ListView<String> listView; // Assurez-vous que cela correspond à l'ID dans votre fichier FXML

    @FXML
    void initialize() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loaddata();
    }





    private void loaddata() {
        try {
            List<commande> commandList = cm.recuperercommande();

            commandemissionList.clear();
            commandemissionList.addAll(commandList);

            items = FXCollections.observableArrayList(
                    commandemissionList.stream().map(this::commandeToString).collect(Collectors.toList())
            );

            // Définir les éléments de la ListView
            listView.setItems(items);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    private String commandeToString(commande cmd) {
        return String.format("Ref: %s                 Date: %s                 Prix: %.2f       ", cmd.getReference(), cmd.getDate(), cmd.getPrixtotal());
    }

    @FXML
    void naviguezVersMission(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/PI/Project/GUI/mission.fxml"));
            // Use the TableView to get the Scene
            Scene scene = listView.getScene();
            if (scene != null) {
                scene.setRoot(root);
            }
            Stage stage = (Stage) scene.getWindow();
            // Set the new dimensions
            stage.setHeight(445);
            stage.setWidth(620);
           // new ZoomIn(root).play();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void creermission(ActionEvent event) {
        // Récupérer l'ID de la mission à partir du TextField
        int missionId = Integer.parseInt(textFieldMissionId.getText());

        // Insérer une nouvelle mission dans la base de données
        mission newMission = new mission(missionId, "non livré");
        try {
            cm.ajoutermission(newMission);
            missionId = newMission.getId();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to create mission: " + e.getMessage());
            alert.showAndWait();
        }

        // Pour chaque indice sélectionné dans la ListView
        for (Integer selectedIndex : listView.getSelectionModel().getSelectedIndices()) {
            commande selectedCommand = commandemissionList.get(selectedIndex);

            int commandeId = selectedCommand.getId();

            try {
                selectedCommand.setId_mission(missionId);
                cm.mettreAJourCommande(selectedCommand);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to update commande: " + e.getMessage());
                alert.showAndWait();
                return;
            }


        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Mission created successfully!");
        alert.showAndWait();
    }



    }



