package universite_paris8.iut.dagnetti.junglequest.modele.utilitaire.Carte;

public class Carte {

    private final int[][] grille;
    private final int largeur;
    private final int hauteur;

    public static final int TUILE_VIDE = -1;

    public Carte(int[][] grille) {
        this.grille = grille;
        this.hauteur = grille.length;
        this.largeur = grille[0].length;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getValeurTuile(int ligne, int colonne) {
        if (ligne < 0 || ligne >= hauteur || colonne < 0 || colonne >= largeur) {
            return TUILE_VIDE;
        }
        return grille[ligne][colonne];
    }

    public boolean estSolide(int ligne, int colonne) {
        int id = getValeurTuile(ligne,colonne);
        return id != TUILE_VIDE && id != 1;
    }

    /**
     * Renvoie la ligne (indice) de la première tuile solide en partant du bas pour une colonne donnée.
     * @param colonne colonne actuelle du joueur
     * @return la ligne de la première tuile solide ou -1 si aucun sol trouvé
     */
    public int chercherLigneSol(int colonne) {
        for (int ligne = hauteur - 1; ligne >= 0; ligne--) {
            if (estSolide(ligne, colonne)) {
                return ligne;
            }
        }
        return -1;
    }

    public int[][] getGrille() {
        return grille;
    }

    /**
     * Retourne l'identifiant de la tuile à l'endroit donné.
     */
    public int getIdTuile(int ligne, int colonne) {
        return getValeurTuile(ligne, colonne);
    }
}
