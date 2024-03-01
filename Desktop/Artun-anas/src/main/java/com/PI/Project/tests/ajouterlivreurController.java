package com.PI.Project.tests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.PI.Project.models.livreur;
import com.PI.Project.services.livreurService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.System;
public class ajouterlivreurController {
    private final livreurService lv = new livreurService();
    private boolean update;
    private int id;
    public void getId(int id)
    {
        this.id=id ;
    }
    @FXML
    private Label label;

    @FXML
    private TextField Maill;

    @FXML
    private TextField Mdpl;

    @FXML
    private TextField Noml;



    @FXML
    private TextField Prenoml;
    @FXML
    private ImageView imagee;

    @FXML
    private Button importbtn;
    private String imagePath = "";
    public Image image;

    public void InsererImage(){
        FileChooser open = new FileChooser();
        File file =open.showOpenDialog(importbtn.getScene().getWindow());

        if (file != null){
            imagePath = "file:///" + file.getAbsolutePath().replace("\\", "\\\\");
            image = new Image(file.toURI().toString(),101, 127, false, true);
            imagee.setImage(image);


        }
    }

    @FXML
    void AjouterLivreur(ActionEvent event) {
        String nom = Noml.getText().trim();
        String prenom = Prenoml.getText().trim();
        String mail = Maill.getText().trim();
        String mdp = Mdpl.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || mdp.isEmpty() || imagePath == null) {
            afficherMessageErreur("Veuillez remplir tous les champs obligatoires.");
            return;
        }



        String emailValidationResult = isValidEmailAddress(mail);
        if (emailValidationResult != null) {
            afficherMessageErreur(emailValidationResult);
            return;
        }

        String passwordValidationResult = isValidPassword(mdp);
        if (passwordValidationResult != null) {
            afficherMessageErreur(passwordValidationResult);
            return;
        }

        if (!update) {
            try {
                if (imagePath.isEmpty()) {
                    afficherMessageErreur("Veuillez sélectionner une image.");
                    return;
                }
                lv.ajouter(new livreur(nom, prenom, mail, imagePath, mdp));

                afficherMessageInformation("Livreur ajouté avec succès.");
            } catch (SQLException e) {
                afficherMessageErreur("Erreur lors de l'ajout du livreur : " + e.getMessage());
            }
        } else {
            try {
                livreur livreur = lv.findById(id);
                livreur.setNom(nom);
                livreur.setPrenom(prenom);
                livreur.setMail(mail);
                livreur.setMdp(mdp);
                livreur.setPhoto(imagee.getImage().getUrl());
                lv.modifier(livreur);
                afficherMessageInformation("Livreur modifié avec succès.");

            } catch (SQLException e) {
                afficherMessageErreur("Erreur lors de la modification du livreur : " + e.getMessage());
                Logger.getLogger(afficherlivreurController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setPrefWidth(600);

        alert.showAndWait();
    }


    private void afficherMessageInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }


    private String isValidEmailAddress(String email) {
        if (email.isEmpty()) {
            return "L'adresse email ne peut pas être vide.";
        }
        String emailRegex = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-z]{2,4}$";
        if (!email.matches(emailRegex)) {
            return "L'adresse email n'est pas valide. Assurez-vous de suivre le format exemple@domaine.com";
        }
        return null;
    }

    private String isValidPassword(String password) {
        if (password.isEmpty()) {
            return "Le mot de passe ne peut pas être vide.";
        }
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (!password.matches(passwordRegex)) {
            return "Le mot de passe doit contenir au moins 8 caractères, inclure au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.";
        }
        return null;
    }





    void setUpdate(boolean b) {
        this.update = b;

    }
    void DisplayImage(String photoUrl) {
        if (photoUrl != null && !photoUrl.isEmpty()) {
            Image image = new Image(photoUrl, true);
            imagee.setImage(image);

            image.errorProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    System.out.println("Error loading image: " + photoUrl);
                }
            });
        } else {
            imagee.setImage(null);
        }
    }

    void setTextField(String nom, String prenom , String photo, String mail, String mdp ) {
        label.setText("Modifier livreur");
        Noml.setText(nom);
        Prenoml.setText(prenom);
        DisplayImage(photo);
        Maill.setText(mail);
        Mdpl.setText(mdp);

    }
    @FXML
    private void annuler (ActionEvent event) {
        Noml.setText(null);
        Prenoml.setText(null);
        DisplayImage(null);
        Maill.setText(null);
        Mdpl.setText(null);

    }


    }











