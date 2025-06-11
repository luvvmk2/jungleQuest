package universite_paris8.iut.dagnetti.junglequest.modele.bloc;

import org.junit.jupiter.api.Test;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlocManagerTest {

    @Test
    void testCasserBlocTerreEnleveHerbeAuDessus() {
        int[][] grille = {
                {TileType.HERBE.getId()},
                {TileType.TERRE.getId()}
        };
        Carte carte = new Carte(grille);
        boolean modifie = BlocManager.casserBloc(carte, 1, 0);
        assertTrue(modifie);
        assertEquals(TileType.VIDE.getId(), carte.getValeurTuile(1,0));
        assertEquals(TileType.VIDE.getId(), carte.getValeurTuile(0,0));
    }

    @Test
    void testBlocsConnectes() {
        int H = TileType.ARBRE.getId();
        int[][] grille = {
                {H, H, -1},
                {H, -1, -1},
                {-1, -1, -1}
        };
        Carte carte = new Carte(grille);
        List<int[]> res = BlocManager.blocsConnectes(carte, 0, 0);
        assertEquals(3, res.size());
    }

    @Test
    void testCasserArbreSupprimeToutLeGroupe() {
        int A = TileType.ARBRE.getId();
        int[][] grille = {
                {A, A},
                {A, A}
        };
        Carte carte = new Carte(grille);
        boolean modifie = BlocManager.casserBloc(carte, 0, 0);
        assertTrue(modifie);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(TileType.VIDE.getId(), carte.getValeurTuile(i, j));
            }
        }
    }
}
