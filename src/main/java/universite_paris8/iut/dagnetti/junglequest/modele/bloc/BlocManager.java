package universite_paris8.iut.dagnetti.junglequest.modele.bloc;

import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;

import java.util.*;

/**
 * Méthodes utilitaires pour la manipulation des blocs de la carte
 * (cassage, détection de groupes...).
 */
public final class BlocManager {

    private BlocManager() {}

    /** Vie restante par groupe d'arbres identifiés par une clé unique */
    private static final Map<String, Integer> vieArbres = new HashMap<>();

    /**
     * Casse un bloc et applique des règles spécifiques selon son type.
     * <p>
     * Exemple : si on casse un bloc de terre, l'herbe posée au-dessus est
     * également retirée.
     *
     * @return {@code true} si quelque chose a été modifié
     */
    public static boolean casserBloc(Carte carte, int ligne, int colonne) {
        int id = carte.getValeurTuile(ligne, colonne);
        TileType type = TileType.fromId(id);
        if (type == null || type == TileType.VIDE) {
            return false;
        }

        if (type == TileType.TERRE) {
            supprimerHerbeAuDessus(carte, ligne, colonne);
            carte.setValeurTuile(ligne, colonne, TileType.VIDE.getId());
            return true;
        }

        if (type == TileType.ARBRE) {
            return casserArbre(carte, ligne, colonne);
        }

        carte.setValeurTuile(ligne, colonne, TileType.VIDE.getId());
        return true;
    }

    /**
     * Gère la destruction progressive d'un arbre.
     */
    private static boolean casserArbre(Carte carte, int ligne, int colonne) {
        List<int[]> groupe = blocsConnectes(carte, ligne, colonne);
        String cle = creerCle(groupe);
        int vieRestante = vieArbres.getOrDefault(cle, 3);
        vieRestante--;
        if (vieRestante <= 0) {
            for (int[] pos : groupe) {
                carte.setValeurTuile(pos[0], pos[1], TileType.VIDE.getId());
            }
            vieArbres.remove(cle);
        } else {
            vieArbres.put(cle, vieRestante);
        }
        return true;
    }

    /**
     * Génère une clé unique pour identifier un groupe de tuiles.
     */
    private static String creerCle(List<int[]> groupe) {
        List<String> coords = new ArrayList<>();
        for (int[] p : groupe) {
            coords.add(p[0] + "," + p[1]);
        }
        Collections.sort(coords);
        return String.join(";", coords);
    }

    private static void supprimerHerbeAuDessus(Carte carte, int ligne, int colonne) {
        int dessus = carte.getValeurTuile(ligne - 1, colonne);
        if (dessus == TileType.HERBE.getId()) {
            carte.setValeurTuile(ligne - 1, colonne, TileType.VIDE.getId());
        }
    }

    /**
     * Renvoie la liste des positions connectées (4 directions) ayant la même
     * valeur que la tuile d'origine.
     */
    public static List<int[]> blocsConnectes(Carte carte, int ligne, int colonne) {
        int cible = carte.getValeurTuile(ligne, colonne);
        TileType typeCible = TileType.fromId(cible);
        if (typeCible == null || typeCible == TileType.VIDE) {
            return List.of();
        }
        List<int[]> resultat = new ArrayList<>();
        Deque<int[]> pile = new ArrayDeque<>();
        Set<String> visite = new HashSet<>();
        pile.push(new int[]{ligne, colonne});
        visite.add(ligne + "," + colonne);

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        while (!pile.isEmpty()) {
            int[] pos = pile.pop();
            int l = pos[0];
            int c = pos[1];
            if (TileType.fromId(carte.getValeurTuile(l, c)) != typeCible) {
                continue;
            }
            resultat.add(pos);
            for (int[] d : dirs) {
                int nl = l + d[0];
                int nc = c + d[1];
                if (nl < 0 || nl >= carte.getHauteur() || nc < 0 || nc >= carte.getLargeur()) {
                    continue;
                }
                if (TileType.fromId(carte.getValeurTuile(nl, nc)) == typeCible) {
                    String key = nl + "," + nc;
                    if (visite.add(key)) {
                        pile.push(new int[]{nl, nc});
                    }
                }
            }
        }
        return resultat;
    }
}
