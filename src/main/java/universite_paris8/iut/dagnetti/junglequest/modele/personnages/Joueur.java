package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

import javafx.scene.image.ImageView;

/**
 * Représente le joueur du jeu, héritant du comportement de base d’un personnage.
 */
public class Joueur extends Personnage {

    private boolean estEnAttaque;

    public Joueur(ImageView sprite, double x, double y) {
        super(sprite, x, y);
        this.estEnAttaque = false;
    }

    public boolean estEnAttaque() {
        return estEnAttaque;
    }

    public void attaquer() {
        estEnAttaque = true;
    }

    public void finAttaque() {
        estEnAttaque = false;
    }
}
