package universite_paris8.iut.dagnetti.junglequest.controleur.moteur;

import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;
import universite_paris8.iut.dagnetti.junglequest.modele.utilitaire.Carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Personnage;

public class MoteurPhysique {

    private static final double EPSILON = 1.0;  // Tolérance d'erreur verticale (en pixels)

    public void mettreAJourPhysique(Personnage personnage, Carte carte, double decalageX) {
        // Appliquer la gravité uniquement si en l'air
        personnage.appliquerGravite(ConstantesJeu.GRAVITE, ConstantesJeu.VITESSE_CHUTE_MAX);

        // Coordonnées des pieds
        double piedX = personnage.getX() + personnage.getSprite().getFitWidth() / 2 + decalageX;
        double piedY = personnage.getY() + personnage.getSprite().getFitHeight();

        int colonne = (int) (piedX / ConstantesJeu.TAILLE_TUILE);
        int ligne = (int) (piedY / ConstantesJeu.TAILLE_TUILE);

        if (carte.estSolide(ligne, colonne)) {
            double ySol = ligne * ConstantesJeu.TAILLE_TUILE;

            if (personnage.getVitesseY() >= 0 && piedY >= ySol - EPSILON) {
                personnage.poserAuSol(ySol - personnage.getSprite().getFitHeight());
            }
        } else {
            personnage.setEstAuSol(false);
        }

        personnage.mettreAJourPosition();
    }
}
