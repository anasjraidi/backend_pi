module com.PI.Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.base;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javax.mail;
    //requires AnimateFX;
    requires com.gluonhq.maps;
    requires com.fasterxml.jackson.databind;
    requires twilio;
    opens com.PI.Project.tests to javafx.fxml;

    // other module dependencies, if any

    exports com.PI.Project.models;
    exports com.PI.Project.tests;
    exports com.PI.Project.utils;

}