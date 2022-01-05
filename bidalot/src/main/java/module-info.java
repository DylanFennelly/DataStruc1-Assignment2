module com.bid.bidalot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires xstream;


    opens com.bid.bidalot to javafx.fxml, xstream;
    opens com.bid.bidalot.controllers to javafx.fxml, xstream;
    opens com.bid.bidalot.lists to javafx.fxml, xstream;
    opens com.bid.bidalot.objects to javafx.fxml, xstream;
    opens com.bid.bidalot.hashing to javafx.fxml, xstream;

    exports com.bid.bidalot;
    exports com.bid.bidalot.controllers;
    exports com.bid.bidalot.lists;
    exports com.bid.bidalot.objects;
    exports com.bid.bidalot.hashing;

}