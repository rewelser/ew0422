module com.toolrent.toolrentcheckout {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.toolrent.toolrentcheckout to javafx.fxml;
    exports com.toolrent.toolrentcheckout;
}