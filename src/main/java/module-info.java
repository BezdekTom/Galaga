module galaga {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens galaga to javafx.fxml;
    exports galaga;
}