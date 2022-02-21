module com.br.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.br.workshopjavafxjdbc to javafx.fxml;
    opens com.br.workshopjavafxjdbc.model.entities to javafx.base;
    exports com.br.workshopjavafxjdbc;
}