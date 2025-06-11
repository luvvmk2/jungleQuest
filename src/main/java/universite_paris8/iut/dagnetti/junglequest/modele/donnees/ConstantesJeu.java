package universite_paris8.iut.dagnetti.junglequest.modele.donnees;

/**
 * Cette classe contient toutes les constantes générales du jeu :
 * physiques, visuelles ou temporelles.
 */
public final class ConstantesJeu {

    // --- Dimensions ---
    /** Taille (en pixels) d’une tuile de la carte */
    public static final int TAILLE_TUILE = 32;

    /** Taille d’une frame dans les spritesheets (associée aux animations du joueur) */
    public static final int TAILLE_SPRITE = 56;

    /** Largeur du joueur (en pixels, utilisée pour le centrage) */
    public static final int LARGEUR_JOUEUR = 56;

    // --- Mouvements et physique ---
    /** Vitesse horizontale du joueur (modifiable via le menu de paramètres) */
    public static int VITESSE_JOUEUR = 1;

    /** Accélération gravitationnelle appliquée chaque frame */
    public static final double GRAVITE = 0.5;

    /** Vitesse verticale maximale en chute libre */
    public static final double VITESSE_CHUTE_MAX = 2;

    /** Force de l’impulsion de saut du joueur (modifiable) */
    public static double IMPULSION_SAUT = 10;

    // --- Animation ---
    /** Délai entre deux frames d’animation (en nombre de frames) */
    public static final int DELAI_FRAME = 8;

    /** Durée totale d’une animation d’attaque (en frames) */
    public static final int DUREE_ATTAQUE = 8 * DELAI_FRAME;

    // Constructeur privé pour empêcher l’instanciation
    private ConstantesJeu() {}
}
