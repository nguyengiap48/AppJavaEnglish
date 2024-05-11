module com.example.appjavaenglish {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.web;
    requires org.jsoup;
    requires mysql.connector.j;
    requires freetts;
    requires java.net.http;
    requires org.json;

    opens com.example.appjavaenglish to javafx.fxml;
    exports com.example.appjavaenglish;
}