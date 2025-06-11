package universite_paris8.iut.dagnetti.junglequest.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class VueBackground extends Pane {

    private final List<ImageView> calqueArriere = new ArrayList<>();
    private final List<ImageView> calqueMilieu = new ArrayList<>();
    private final List<ImageView> calqueAvant = new ArrayList<>();

    public VueBackground(int largeurCartePx, double largeurEcran, double hauteurEcran) {
        this.setPrefSize(largeurEcran, hauteurEcran);

        genererCalque(calqueArriere, "/universite_paris8/iut/dagnetti/junglequest/images/background_arriere.png", largeurCartePx, largeurEcran, hauteurEcran);
        genererCalque(calqueMilieu,  "/universite_paris8/iut/dagnetti/junglequest/images/background_milieu.png", largeurCartePx, largeurEcran, hauteurEcran);
        genererCalque(calqueAvant,   "/universite_paris8/iut/dagnetti/junglequest/images/background_avant.png", largeurCartePx, largeurEcran, hauteurEcran);

        this.getChildren().addAll(calqueArriere);
        this.getChildren().addAll(calqueMilieu);
        this.getChildren().addAll(calqueAvant);

        this.setViewOrder(100); // tout derrière
    }

    private void genererCalque(List<ImageView> calque, String cheminImage, int largeurCartePx, double largeurEcran, double hauteurEcran) {
        Image image = new Image(getClass().getResourceAsStream(cheminImage));
        int nombreCopies = (int) Math.ceil((double) largeurCartePx / largeurEcran) + 2;

        for (int i = 0; i < nombreCopies; i++) {
            ImageView iv = new ImageView(image);
            iv.setFitWidth(largeurEcran);
            iv.setFitHeight(hauteurEcran);
            iv.setX(i * largeurEcran);
            iv.setY(0);
            calque.add(iv);
        }
    }

    public void mettreAJourScroll(double offsetX) {
        ajusterCalque(calqueArriere, offsetX, 0.2);
        ajusterCalque(calqueMilieu, offsetX, 0.5);
        ajusterCalque(calqueAvant, offsetX, 0.8);
    }

    private void ajusterCalque(List<ImageView> calque, double offsetX, double facteur) {
        double decalage = -offsetX * facteur;
        double largeurImage = calque.get(0).getFitWidth();

        for (int i = 0; i < calque.size(); i++) {
            ImageView iv = calque.get(i);
            iv.setX(i * largeurImage + decalage % largeurImage - largeurImage);
        }
    }
}
