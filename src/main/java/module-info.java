module javafxtest.tp1_poo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens javafxtest.tp1_poo to javafx.fxml;
    exports javafxtest.tp1_poo;
}