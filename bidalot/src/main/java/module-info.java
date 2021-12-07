module com.bid.bidalot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bid.bidalot to javafx.fxml;
    exports com.bid.bidalot;
    exports com.bid.bidalot.Controllers;
    opens com.bid.bidalot.Controllers to javafx.fxml;
}