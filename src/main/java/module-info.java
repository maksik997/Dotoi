module pl.magzik.dotoi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens pl.magzik.dotoi to javafx.fxml;
    exports pl.magzik.dotoi;
}