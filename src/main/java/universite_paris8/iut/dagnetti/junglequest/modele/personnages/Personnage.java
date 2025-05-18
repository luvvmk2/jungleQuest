package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

import javafx.scene.image.ImageView;

/**
 * Classe abstraite représentant un personnage générique avec position, vitesse et sprite.
 * Elle sert de base commune pour le joueur et d'autres entités futures.
 */
public abstract class Personnage {

    protected double x;
    protected double y;
    protected double vitesseX;
    protected double vitesseY;
    protected boolean versGauche;
    protected boolean estAuSol;

    protected final ImageView sprite;

    public Personnage(ImageView sprite, double x, double y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.vitesseX = 0;
        this.vitesseY = 0;
        this.versGauche = false;
        this.estAuSol = false;

        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    /**
     * Met à jour la position en fonction des vitesses actuelles.
     */
    public void mettreAJourPosition() {
        x += vitesseX;
        y += vitesseY;
        sprite.setX(x);
        sprite.setY(y);
    }

    /**
     * Applique la gravité si le personnage n'est pas au sol.
     */
    public void appliquerGravite(double gravite, double vitesseChuteMax) {
        if (!estAuSol) {
            vitesseY = Math.min(vitesseY + gravite, vitesseChuteMax);
        }
    }

    /**
     * Pose le personnage au sol à une hauteur précise.
     */
    public void poserAuSol(double ySol) {
        y = ySol;
        vitesseY = 0;
        estAuSol = true;
        sprite.setY(y);
    }

    /**
     * Lance un saut si le personnage est au sol.
     */
    public void sauter(double impulsion) {
        if (estAuSol) {
            vitesseY = -impulsion;
            estAuSol = false;
        }
    }

    /**
     * Déplace vers la gauche.
     */
    public void deplacerGauche(double vitesse) {
        vitesseX = -vitesse;
        versGauche = true;
    }

    /**
     * Déplace vers la droite.
     */
    public void deplacerDroite(double vitesse) {
        vitesseX = vitesse;
        versGauche = false;
    }

    /**
     * Arrête le mouvement horizontal.
     */
    public void arreter() {
        vitesseX = 0;
    }

    // --- Getters utiles ---

    public ImageView getSprite() { return sprite; }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getVitesseY() { return vitesseY; }
    public double getVitesseX() { return vitesseX; }
    public boolean estAuSol() { return estAuSol; }

    public boolean estVersGauche() { return versGauche; }

    public void setEstAuSol(boolean auSol) {
        this.estAuSol = auSol;
    }

}
