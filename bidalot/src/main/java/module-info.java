module com.bid.bidalot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bid.bidalot to javafx.fxml;
    opens com.bid.bidalot.controllers to javafx.fxml;
    opens com.bid.bidalot.lists to javafx.fxml;
    opens com.bid.bidalot.objects to javafx.fxml;

    exports com.bid.bidalot;
    exports com.bid.bidalot.controllers;
    exports com.bid.bidalot.lists;
    exports com.bid.bidalot.objects;

}