package universite_paris8.iut.dagnetti.junglequest.controleur.moteur;

import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Personnage;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.TileType;

/**
 * Gère les règles physiques élémentaires du jeu :
 * gravité, détection de sol, mise à jour des positions.
 */
public class MoteurPhysique {

    /**
     * Applique la physique à un personnage : chute, collisions, position.
     *
     * @param personnage Le personnage à mettre à jour (ex. Joueur, Ennemi, etc.)
     * @param carte      La carte contenant les tuiles solides
     */
    public void mettreAJourPhysique(Personnage personnage, Carte carte) {
        // Appliquer la gravité
        personnage.appliquerGravite(ConstantesJeu.GRAVITE, ConstantesJeu.VITESSE_CHUTE_MAX);

        double largeur = personnage.getSprite().getFitWidth();
        double hauteur = personnage.getSprite().getFitHeight();

        double newX = personnage.getX() + personnage.getVitesseX();
        double newY = personnage.getY() + personnage.getVitesseY();

        // --- Gestion des collisions horizontales ---
        if (personnage.getVitesseX() > 0) { // vers la droite
            int col = (int) ((newX + largeur - 1) / ConstantesJeu.TAILLE_TUILE);
            int top = (int) (personnage.getY() / ConstantesJeu.TAILLE_TUILE);
            int bottom = (int) ((personnage.getY() + hauteur - 1) / ConstantesJeu.TAILLE_TUILE);
            for (int l = top; l <= bottom; l++) {
                if (carte.estSolide(l, col)) {
                    newX = col * ConstantesJeu.TAILLE_TUILE - largeur;
                    personnage.setVitesseX(0);
                    break;
                }
            }
        } else if (personnage.getVitesseX() < 0) { // vers la gauche
            int col = (int) (newX / ConstantesJeu.TAILLE_TUILE);
            int top = (int) (personnage.getY() / ConstantesJeu.TAILLE_TUILE);
            int bottom = (int) ((personnage.getY() + hauteur - 1) / ConstantesJeu.TAILLE_TUILE);
            for (int l = top; l <= bottom; l++) {
                if (carte.estSolide(l, col)) {
                    newX = (col + 1) * ConstantesJeu.TAILLE_TUILE;
                    personnage.setVitesseX(0);
                    break;
                }
            }
        }

        personnage.setX(newX);

        // --- Gestion des collisions verticales ---
        personnage.setEstAuSol(false);
        if (personnage.getVitesseY() > 0) { // chute
            int ligneBas = (int) ((newY + hauteur - 1) / ConstantesJeu.TAILLE_TUILE);
            int colGauche = (int) (newX / ConstantesJeu.TAILLE_TUILE);
            int colDroite = (int) ((newX + largeur - 1) / ConstantesJeu.TAILLE_TUILE);
            for (int c = colGauche; c <= colDroite; c++) {
                int id = carte.getValeurTuile(ligneBas, c);
                if (carte.estSolide(ligneBas, c) && TileType.fromId(id) != TileType.ARBRE) {
                    newY = ligneBas * ConstantesJeu.TAILLE_TUILE - hauteur;
                    personnage.setVitesseY(0);
                    personnage.setEstAuSol(true);
                    break;
                }
            }
        } else if (personnage.getVitesseY() < 0) { // saut
            int ligneHaut = (int) (newY / ConstantesJeu.TAILLE_TUILE);
            int colGauche = (int) (newX / ConstantesJeu.TAILLE_TUILE);
            int colDroite = (int) ((newX + largeur - 1) / ConstantesJeu.TAILLE_TUILE);
            for (int c = colGauche; c <= colDroite; c++) {
                int id = carte.getValeurTuile(ligneHaut, c);
                if (carte.estSolide(ligneHaut, c) && TileType.fromId(id) != TileType.ARBRE) {
                    newY = (ligneHaut + 1) * ConstantesJeu.TAILLE_TUILE;
                    personnage.setVitesseY(0);
                    break;
                }
            }
        }

        personnage.setY(newY);
    }
}
