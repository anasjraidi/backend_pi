package com.PI.Project.tests;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.PI.Project.models.livreur;
import com.PI.Project.services.livreurService;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class afficherlivreurController {
    private final livreurService lv = new livreurService();
    livreur livreur = null;
    ObservableList<livreur> livreurList = FXCollections.observableArrayList();


    @FXML
    private TextField search;
    @FXML
    private TableColumn<livreur, String> Act;
    @FXML
    private TableColumn<livreur, String> colln;

    @FXML
    private TableColumn<livreur, String> collp;

    @FXML
    private TableColumn<livreur, String> collm;

    @FXML
    private TableColumn<livreur, String> colli;

    @FXML
    private TableView<livreur> table;

    @FXML
    void initialize() {
        loaddata();
    }


    @FXML
    void refresh(ActionEvent event) {

        loaddata();

    }
@FXML
    private void loaddata(){
        try {
             livreurList = (ObservableList<livreur>) lv.recuperer();

            colln.setCellValueFactory(new PropertyValueFactory<>("nom"));
            collp.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            collm.setCellValueFactory(new PropertyValueFactory<>("mail"));
            colli.setCellValueFactory(new PropertyValueFactory<>("photo"));
            colli.setCellFactory(new Callback<TableColumn<livreur, String>, TableCell<livreur, String>>() {
                @Override
                public TableCell<livreur, String> call(TableColumn<livreur, String> param) {
                    final TableCell<livreur, String> cell = new TableCell<livreur, String>() {
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                String imageUrl = getTableColumn().getTableView().getItems().get(getIndex()).getPhoto();
                                Image image = null;
                                try {
                                    if (imageUrl.startsWith("file://")) {
                                        File imageFile = new File(imageUrl.substring(7));
                                        BufferedImage bufferedImage = ImageIO.read(imageFile);
                                        image = SwingFXUtils.toFXImage(bufferedImage, null);
                                    } else {
                                        URL url = new URL(imageUrl);
                                        image = new Image(url.toExternalForm());
                                    }
                                    ImageView imageView = new ImageView(image);
                                    imageView.setFitWidth(150);
                                    imageView.setFitHeight(100);
                                    setGraphic(imageView);
                                } catch (MalformedURLException ex) {
                                    System.err.println("Error loading image: " + ex.getMessage());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };
                    return cell;
                }
            });







            table.setItems(livreurList);

            FilteredList<livreur> filteredList = new FilteredList<>(livreurList, b -> true);
            search.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                filteredList.setPredicate(livreur -> {
                    if (newvalue == null || newvalue.isEmpty()) {
                        return true;
                    }
                    String key = newvalue.toLowerCase();
                    if (livreur.getNom().toLowerCase().contains(key) || livreur.getPrenom().toLowerCase().contains(key)||livreur.getMail().toLowerCase().contains(key))
                    {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            table.refresh();
            SortedList<livreur> sorteddata = new SortedList<>(filteredList);
            sorteddata.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sorteddata);



        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        Callback<TableColumn<livreur, String>, TableCell<livreur, String>> cellFoctory = (TableColumn<livreur, String> param) -> {
            final TableCell<livreur, String> cell = new TableCell<livreur, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                        table.refresh();
                    } else {

                        ImageView deleteIcon = new ImageView(String.valueOf(getClass().getResource("/com/PI/Project/GUI/image/trash.png")));
                        ImageView editIcon = new ImageView(String.valueOf(getClass().getResource("/com/PI/Project/GUI/image/pen.png")));
                        deleteIcon.setFitWidth(25);
                        deleteIcon.setFitHeight(25);
                        editIcon.setFitWidth(25);
                        editIcon.setFitHeight(25);


                        deleteIcon.setOnMouseClicked( event -> {
                            try {
                                livreur = table.getSelectionModel().getSelectedItem();
                                lv.supprimer(livreur.getId());
                                livreurList.remove(livreur);

                            }
                            catch (SQLException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setContentText(e.getMessage());
                                alert.showAndWait();
                            }
                        });



                        editIcon.setOnMouseClicked(event -> {
                            livreur = table.getSelectionModel().getSelectedItem();
                            int id=livreur.getId();

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/com/PI/Project/GUI/ajouterlivreur.fxml"));
                            try {
                                loader.load();
                                table.refresh();
                            } catch (IOException ex) {
                                Logger.getLogger(afficherlivreurController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ajouterlivreurController ajouterlivreurController = loader.getController();
                            ajouterlivreurController.setUpdate(true);
                            ajouterlivreurController.getId(id);

                            ajouterlivreurController.setTextField(livreur.getNom(),livreur.getPrenom(),livreur.getPhoto(),livreur.getMail(),livreur.getMdp());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();


                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                    }
                    setText(null);
                }

            };

            return cell;
        };
    colln.setStyle("-fx-alignment: CENTER;");
    collp.setStyle("-fx-alignment: CENTER;");
    collm.setStyle("-fx-alignment: CENTER;");
    colli.setStyle("-fx-alignment: CENTER;");


    Act.setCellFactory(cellFoctory);
    }












    @FXML
    void naviguezVersAjouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/PI/Project/GUI/ajouterlivreur.fxml"));
            // Use the TableView to get the Scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            // Set the new dimensions

            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
