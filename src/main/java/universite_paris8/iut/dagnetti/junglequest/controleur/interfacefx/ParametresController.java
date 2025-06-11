package universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur de la fenêtre des paramètres du jeu.
 * Permet de modifier la vitesse du joueur et la force du saut.
 */
public class ParametresController implements Initializable {

    @FXML
    private Slider sliderVitesse;
    @FXML
    private Slider sliderSaut;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sliderVitesse.setValue(ConstantesJeu.VITESSE_JOUEUR);
        sliderSaut.setValue(ConstantesJeu.IMPULSION_SAUT);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void fermer() {
        ConstantesJeu.VITESSE_JOUEUR = (int) sliderVitesse.getValue();
        ConstantesJeu.IMPULSION_SAUT = sliderSaut.getValue();
        if (stage != null) {
            stage.close();
        }
    }
}
