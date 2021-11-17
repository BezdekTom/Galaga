module galaga {
    requires transitive javafx.controls;
    requires javafx.fxml;
    opens galaga to javafx.fxml;
    exports galaga;
}