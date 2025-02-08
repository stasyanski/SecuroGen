module sg.securogen {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.sql;

    opens sg.securogen to javafx.fxml;
    exports sg.securogen;
    exports sg.controllers;
    opens sg.controllers to javafx.fxml;
    exports sg.core;
    opens sg.core to javafx.fxml;
}