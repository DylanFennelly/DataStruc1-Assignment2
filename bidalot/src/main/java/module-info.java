module com.bid.bidalot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bid.bidalot to javafx.fxml;
    exports com.bid.bidalot;
    exports com.bid.bidalot.controllers;
    opens com.bid.bidalot.controllers to javafx.fxml;
}