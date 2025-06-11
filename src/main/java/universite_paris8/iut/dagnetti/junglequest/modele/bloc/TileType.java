package universite_paris8.iut.dagnetti.junglequest.modele.bloc;

/**
 * Représente les différents types de tuiles manipulées par le jeu.
 * Chaque type possède un identifiant entier présent dans la carte CSV.
 */
public enum TileType {
    VIDE(-1),
    HERBE(1),
    TERRE(29),
    ARBRE(129); // valeur indicative pour les troncs d'arbres

    private final int id;

    TileType(int id) {
        this.id = id;
    }

    /**
     * Identifiant numérique tel qu'il apparaît dans la carte.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le type correspondant à l'identifiant fourni ou null si inconnu.
     */
    public static TileType fromId(int id) {
        for (TileType t : values()) {
            if (t.id == id) {
                return t;
            }
        }
        return null;
    }
}
