module universite_paris8.iut.dagnetti.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens universite_paris8.iut.dagnetti.demo to javafx.fxml;
    exports universite_paris8.iut.dagnetti.demo;
}