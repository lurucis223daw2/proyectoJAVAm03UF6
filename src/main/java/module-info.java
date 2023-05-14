module edu.fje.provajavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.kordamp.bootstrapfx.core;

    opens edu.fje.provajavafx to javafx.fxml;
    exports edu.fje.provajavafx;
}