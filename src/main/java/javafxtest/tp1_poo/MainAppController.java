package javafxtest.tp1_poo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import appareil.*;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;

public class MainAppController implements Initializable {
    @FXML
    private TextField textfield1;

    @FXML
    private VBox vbox1;

    @FXML
    private VBox vbox_affichage;

    // Afficher la liste des appareils volés lors du démarrage de l'app
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vbox_affichage.getChildren().clear();

        // Creation d'un point de connexion à la bd pour recuperer les appareils
        List<Appareil> liste_app = new ArrayList<>();
        try {
            MainApp.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp1_poo", "Jean_Roland", "Papasenegal0");
            Statement stmt = MainApp.con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from ordinateur;");
            while (resultSet.next()) {
                Appareil appareil = new Ordinateur(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                liste_app.add(appareil);
            }
            resultSet.close();

            resultSet = stmt.executeQuery("select * from telephone;");
            while (resultSet.next()) {
                Appareil appareil = new Telephone(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                liste_app.add(appareil);
            }

            stmt.close();
            MainApp.con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des appareils dans l'app
        for (Appareil appareil : liste_app) {
            HBox hBox = new HBox();
            hBox.setStyle("-fx-border-color : #2c1b4a; -fx-border-width : 0 0 1 0;");
            hBox.setPrefHeight(100);
            hBox.setPrefWidth(200);
            ImageView imageView = new ImageView(new Image(getClass().getResource(appareil.getImage()).toString()));
            imageView.setFitWidth(150);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
            Label label1 = new Label();
            label1.setText("Numéro de série : \n" + appareil.getNumero_de_serie().toString());
            label1.setStyle("-fx-text-fill : black; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            Label label2 = new Label();
            label2.setText("Nom : \n" + appareil.getNom_machine());
            label2.setStyle("-fx-text-fill : black; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            Label label3 = new Label();
            label3.setText("Déclarée comme volée");
            label3.setStyle("-fx-text-fill : red; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            hBox.getChildren().addAll(imageView, label1, label2, label3);
            HBox.setMargin(imageView, new Insets(0, 20, 0, 0));
            HBox.setMargin(label1, new Insets(25, 30, 0, 0));
            HBox.setMargin(label2, new Insets(25, 30, 0, 0));
            HBox.setMargin(label3, new Insets(25, 10, 0, 0));
            vbox_affichage.getChildren().add(hBox);
        }
    }

    // Afficher un/plusieurs appareil/s particulier/s
    @FXML
    void rechercherElt(ActionEvent event) {
        if (textfield1.getText().isEmpty()) {
            return;
        }

        vbox_affichage.getChildren().clear();

        // Creation d'un point de connexion à la bd pour recuperer les appareils
        List<Appareil> liste_app = new ArrayList<>();
        try {
            MainApp.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp1_poo", "Jean_Roland", "Papasenegal0");
            Statement stmt = MainApp.con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from ordinateur where numero_de_serie like \"%"+ textfield1.getText() +"%\";");
            while (resultSet.next()) {
                Appareil appareil = new Ordinateur(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                liste_app.add(appareil);
            }
            resultSet.close();

            resultSet = stmt.executeQuery("select * from telephone where numero_de_serie = "+ textfield1.getText() +";");
            while (resultSet.next()) {
                Appareil appareil = new Telephone(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                liste_app.add(appareil);
            }

            stmt.close();
            MainApp.con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des appareils dans l'app
        for (Appareil appareil : liste_app) {
            HBox hBox = new HBox();
            hBox.setStyle("-fx-border-color : #2c1b4a; -fx-border-width : 0 0 1 0;");
            hBox.setPrefHeight(100);
            hBox.setPrefWidth(200);
            ImageView imageView = new ImageView(new Image(getClass().getResource(appareil.getImage()).toString()));
            imageView.setFitWidth(150);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
            Label label1 = new Label();
            label1.setText("Numero de serie : \n" + appareil.getNumero_de_serie().toString());
            label1.setStyle("-fx-text-fill : black; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            Label label2 = new Label();
            label2.setText("Nom :\n" + appareil.getNom_machine());
            label2.setStyle("-fx-text-fill : black; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            Label label3 = new Label();
            label3.setText("Déclarée comme volée");
            label3.setStyle("-fx-text-fill : red; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            hBox.getChildren().addAll(imageView, label1, label2, label3);
            HBox.setMargin(imageView, new Insets(0, 20, 0, 0));
            HBox.setMargin(label1, new Insets(35, 30, 0, 0));
            HBox.setMargin(label2, new Insets(35, 30, 0, 0));
            HBox.setMargin(label3, new Insets(35, 10, 0, 0));
            vbox_affichage.getChildren().add(hBox);
        }
    }

    // Methode pour rafraichir la liste des objets volées
    @FXML
    void refresh1(ActionEvent event) {
        vbox_affichage.getChildren().clear();

        // Creation d'un point de connexion à la bd pour recuperer les appareils
        List<Appareil> liste_app = new ArrayList<>();
        try {
            MainApp.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp1_poo", "Jean_Roland", "Papasenegal0");
            Statement stmt = MainApp.con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from ordinateur;");
            while (resultSet.next()) {
                Appareil appareil = new Ordinateur(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                liste_app.add(appareil);
            }
            resultSet.close();

            resultSet = stmt.executeQuery("select * from telephone;");
            while (resultSet.next()) {
                Appareil appareil = new Telephone(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                liste_app.add(appareil);
            }

            stmt.close();
            MainApp.con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage dans l'app
        for (Appareil appareil : liste_app) {
            HBox hBox = new HBox();
            hBox.setStyle("-fx-border-color : #2c1b4a; -fx-border-width : 0 0 1 0;");
            hBox.setPrefHeight(100);
            hBox.setPrefWidth(200);
            ImageView imageView = new ImageView(new Image(getClass().getResource(appareil.getImage()).toString()));
            imageView.setFitWidth(150);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
            Label label1 = new Label();
            label1.setText("Numéro de série : \n" + appareil.getNumero_de_serie().toString());
            label1.setStyle("-fx-text-fill : black; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            Label label2 = new Label();
            label2.setText("Nom : \n" + appareil.getNom_machine());
            label2.setStyle("-fx-text-fill : black; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            Label label3 = new Label();
            label3.setText("Déclarée comme volée");
            label3.setStyle("-fx-text-fill : red; -fx-font-size : 15; -fx-font-family : cambria; -fx-font-weigth : bold;");
            hBox.getChildren().addAll(imageView, label1, label2, label3);
            HBox.setMargin(imageView, new Insets(0, 20, 0, 0));
            HBox.setMargin(label1, new Insets(35, 30, 0, 0));
            HBox.setMargin(label2, new Insets(35, 30, 0, 0));
            HBox.setMargin(label3, new Insets(35, 10, 0, 0));
            vbox_affichage.getChildren().add(hBox);
        }
    }

    @FXML
    void refresh2(ActionEvent event) {

    }

    // Methode pour signaler un objet volé, on chargera une nouvelle UI qui permettra de signaler
    @FXML
    void signaler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Page1UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("Page1UI.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("Brocande de Kennedy");
        stage.setScene(scene);
        stage.show();
        Stage stage1 = (Stage) vbox1.getScene().getWindow();
        stage1.close();
    }
}