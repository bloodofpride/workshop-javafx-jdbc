module com.br.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.br.workshopjavafxjdbc to javafx.fxml;
    exports com.br.workshopjavafxjdbc;
}