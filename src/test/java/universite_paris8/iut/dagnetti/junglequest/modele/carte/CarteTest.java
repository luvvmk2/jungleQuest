package universite_paris8.iut.dagnetti.junglequest.modele.carte;

import org.junit.jupiter.api.Test;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.TileType;

import static org.junit.jupiter.api.Assertions.*;

public class CarteTest {

    @Test
    void testGetSetValeur() {
        int[][] grille = {{0,1},{2,3}};
        Carte carte = new Carte(grille);
        assertEquals(1, carte.getValeurTuile(0,1));
        carte.setValeurTuile(0,1,5);
        assertEquals(5, carte.getValeurTuile(0,1));
    }

    @Test
    void testIndicesInvalidesIgnorés() {
        int[][] grille = {{0}};
        Carte carte = new Carte(grille);
        carte.setValeurTuile(-1,-1,4);
        assertEquals(0, carte.getValeurTuile(0,0));
        assertEquals(Carte.TUILE_VIDE, carte.getValeurTuile(2,2));
    }

    @Test
    void testChercherLigneSol() {
        int[][] grille = {
                {TileType.VIDE.getId()},
                {TileType.TERRE.getId()}
        };
        Carte carte = new Carte(grille);
        assertEquals(1, carte.chercherLigneSol(0));
    }

    @Test
    void testEstSolide() {
        int[][] grille = {{TileType.VIDE.getId()}};
        Carte carte = new Carte(grille);
        assertFalse(carte.estSolide(0,0));
    }
}
