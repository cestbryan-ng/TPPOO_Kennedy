package javafxtest.tp1_poo;

import appareil.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Page1Controller {
    @FXML
    private TextField image;

    @FXML
    private TextField nom;

    @FXML
    private TextField numero_serie;

    @FXML
    private VBox vbox1;

    @FXML
    private CheckBox tel_choix;

    @FXML
    private CheckBox ordi_choix;

    // Charger l'image de l'appareil volée
    @FXML
    void fichier(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) vbox1.getScene().getWindow();
        File fichier = fileChooser.showOpenDialog(stage);

        if (fichier.equals(null)) {
            return;
        }

        if ((tel_choix.isSelected() == true) && (!(ordi_choix.isSelected()))) {
            image.setText("tel/" + fichier.getName());
            return;
        }

        if ((ordi_choix.isSelected() == true) && (!(tel_choix.isSelected()))) {
            image.setText("pc/" + fichier.getName());
        }
    }

    // Inserer l'appareil dans la BD
    @FXML
    void inserer(ActionEvent event) throws SQLException {
        if ((numero_serie.getText().isEmpty()) || (nom.getText().isEmpty()) || (image.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erreur");
            alert.setContentText("Assurer d'avoir tous les champs remplis");
            alert.showAndWait();
            return;
        }

        MainApp.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp1_poo", "Jean_Roland", "Papasenegal0");
        boolean insertion = false;

        if ((ordi_choix.isSelected()) && (!(tel_choix.isSelected()))) {
            try {
                Appareil ordinateur = new Ordinateur(Integer.parseInt(numero_serie.getText()), nom.getText(), image.getText());
                insertion = ordinateur.charger(MainApp.con);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Erreur");
                alert.setContentText("L'appareil n'a pas pu être ajouté");
                alert.showAndWait();
                return;
            }
        }

        if ((tel_choix.isSelected()) && (!(ordi_choix.isSelected()))) {
            try {
                Appareil telephone = new Telephone(Integer.parseInt(numero_serie.getText()), nom.getText(), image.getText());
                insertion = telephone.charger(MainApp.con);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Erreur");
                alert.setContentText("L'appareil n'a pas pu être ajouté");
                alert.showAndWait();
                return;
            }
        }

        if (insertion) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Appareil ajouté");
            alert.setContentText("Votre appareil a été ajouté avec succès");
            alert.showAndWait();
        }

        MainApp.con.close();
    }

    @FXML
    void refresh2(ActionEvent event) {

    }

    // Retour à la page d'accueil
    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MainAppUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("MainAppUI.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("Brocande de Kennedy");
        stage.setScene(scene);
        stage.show();
        Stage stage1 = (Stage) vbox1.getScene().getWindow();
        stage1.close();
    }

}

