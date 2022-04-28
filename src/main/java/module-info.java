module galaga {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.logging.log4j;
	requires java.ws.rs;
	requires org.apache.cxf.rs.client;
	//requires jackson.jaxrs.json.provider;
	
	
    opens galaga to javafx.fxml;
    opens galaga_game to javafx.fxml;
    
    exports galaga;
}