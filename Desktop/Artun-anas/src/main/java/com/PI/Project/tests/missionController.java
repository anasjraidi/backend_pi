package com.PI.Project.tests;

//import animatefx.animation.ZoomIn;
import com.PI.Project.models.mission;
import com.PI.Project.services.commandemissionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class missionController {
    private final commandemissionService cm = new commandemissionService();

    private ObservableList<String> items = FXCollections.observableArrayList();
    private List<mission> missionList;

    @FXML
    private Button newmission;

    @FXML
    private ListView<String> listviewmission;
    @FXML
    private TextField search;
    @FXML
    void initialize() {
        loaddatamission();
    }

    @FXML
    void naviguezVersCommandemission(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/PI/Project/GUI/commandemission.fxml"));
            Scene scene = newmission.getScene();
            if (scene != null) {
                scene.setRoot(root);
            }
            Stage stage = (Stage) scene.getWindow();
            //new ZoomIn(root).play();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String missionToString(mission ms) {
        return String.format(" id: %s          etat: %s", ms.getId(), ms.getEtat());
    }

    private void loaddatamission() {
        try {
            missionList = cm.recuperermission();

            items = FXCollections.observableArrayList(
                    missionList.stream().map(this::missionToString).collect(Collectors.toList())
            );

            listviewmission.setItems(items);

        } catch (SQLException e) {
        }

        listviewmission.setCellFactory(param -> new ListCell<String>() {
            private final HBox content = new HBox();
            private final Label label = new Label();
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                HBox.setMargin(imageView, new Insets(0, 5, 0, 30));

                content.getChildren().addAll(label, imageView);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    String imagePath = getClass().getResource("/com/PI/Project/GUI/image/officer.png").toExternalForm();
                    imageView.setImage(new Image(imagePath));
                    imageView.setOnMouseClicked(event -> {
                        try {
                            int index = getIndex();
                            mission missionToUpdate = missionList.get(index);
                            mission missionToDelete = missionList.get(index);



                            cm.supprimercommandeParMission(missionToDelete.getId());
                            cm.modifiermission(missionToUpdate);
                            missionList = cm.recuperermission();

                            items.clear(); // Nettoyer les éléments actuels
                            items.addAll(missionList.stream().map(missionController.this::missionToString).collect(Collectors.toList())); // Ajouter les éléments mis à jour
                            listviewmission.setItems(items); // Rafraîchir la liste des missions dans la ListView

                            listviewmission.refresh();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });

                    label.setText(item);
                    setGraphic(content);
                }
            }
        });
    }
}
