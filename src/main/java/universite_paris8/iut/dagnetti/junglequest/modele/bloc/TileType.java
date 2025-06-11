package universite_paris8.iut.dagnetti.junglequest.modele.bloc;

/**
 * Représente les différents types de tuiles manipulées par le jeu.
 * Chaque type possède un identifiant entier présent dans la carte CSV.
 */
public enum TileType {
    /** Aucune tuile */
    VIDE(-1),
    /** Différentes herbes */
    HERBE(1),
    /** Sol en terre */
    TERRE(29),
    /** Regroupe toutes les tuiles composant les arbres */
    ARBRE(129,
            328,329,330,331,
            300,301,302,303,
            271,272,273,274,275,
            243,244,245,246,247,
            214,215,216,217,218,219,220,221,
            186,187,188,189,190,191,192,193,
            160,161,162,163);

    /** Identifiants numériques associés */
    private final int[] ids;

    TileType(int... ids) {
        this.ids = ids;
    }

    /** Identifiant principal (le premier de la liste) */
    public int getId() {
        return ids[0];
    }

    /** Retourne true si cette tuile contient l'identifiant donné */
    public boolean contientId(int id) {
        for (int val : ids) {
            if (val == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recherche le type correspondant à l'identifiant fourni.
     */
    public static TileType fromId(int id) {
        for (TileType t : values()) {
            if (t.contientId(id)) {
                return t;
            }
        }
        return null;
    }
}
