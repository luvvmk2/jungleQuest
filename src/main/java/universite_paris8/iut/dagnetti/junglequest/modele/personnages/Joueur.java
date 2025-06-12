package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

import javafx.scene.image.ImageView;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Inventaire;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;

/**
 * Représente le joueur du jeu, héritant du comportement de base d’un personnage.
 */
public class Joueur extends Personnage {

    private boolean estEnAttaque;
    private final Inventaire inventaire;
    private int pointsDeVie;

    public Joueur(ImageView sprite, double x, double y) {
        super(sprite, x, y);
        this.estEnAttaque = false;
        this.inventaire = new Inventaire();
        this.pointsDeVie = ConstantesJeu.VIE_MAX_JOUEUR;
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

    public Inventaire getInventaire() {
        return inventaire;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public void subirDegats(int quantite) {
        pointsDeVie -= quantite;
        if (pointsDeVie < 0) {
            pointsDeVie = 0;
        }
    }

    public void soigner(int quantite) {
        pointsDeVie = Math.min(pointsDeVie + quantite, ConstantesJeu.VIE_MAX_JOUEUR);
    }
}
