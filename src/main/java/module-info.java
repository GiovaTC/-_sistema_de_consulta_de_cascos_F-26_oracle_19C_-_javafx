module com.f26.cascosf26_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.f26.cascosf26_2 to javafx.fxml;
    exports com.f26.cascosf26_2;
    exports com.f26.cascosf26_2.dao;
    opens com.f26.cascosf26_2.dao to javafx.fxml;
    exports com.f26.cascosf26_2.db;
    opens com.f26.cascosf26_2.db to javafx.fxml;
}