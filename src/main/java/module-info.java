module com.isep.management_application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires jdk.javadoc;
    requires kernel;

    opens com.isep.management_application to javafx.fxml;
    exports com.isep.management_application;
}