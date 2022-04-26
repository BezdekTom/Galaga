module galaga {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.logging.log4j;
    opens galaga to javafx.fxml;
    opens galaga_game to javafx.fxml;
    exports galaga;
}