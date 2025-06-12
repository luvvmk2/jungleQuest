package universite_paris8.iut.dagnetti.junglequest.controleur.moteur;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.TileType;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;

import static org.junit.jupiter.api.Assertions.*;

public class MoteurPhysiqueTest {

    @Test
    void testChuteEtCollisionSol() {
        int[][] grille = {
                {TileType.VIDE.getId()},
                {TileType.TERRE.getId()}
        };
        Carte carte = new Carte(grille);
        ImageView sprite = new ImageView();
        sprite.setFitWidth(32);
        sprite.setFitHeight(32);
        Joueur joueur = new Joueur(sprite, 0, 0);
        MoteurPhysique moteur = new MoteurPhysique();
        moteur.mettreAJourPhysique(joueur, carte);
        assertTrue(joueur.estAuSol());
        assertEquals(0, joueur.getY());
    }

    @Test
    void testChuteLibre() {
        int[][] grille = {{TileType.VIDE.getId()}};
        Carte carte = new Carte(grille);
        ImageView sprite = new ImageView();
        sprite.setFitWidth(32);
        sprite.setFitHeight(32);
        Joueur joueur = new Joueur(sprite, 0, 0);
        MoteurPhysique moteur = new MoteurPhysique();
        moteur.mettreAJourPhysique(joueur, carte);
        assertFalse(joueur.estAuSol());
        assertTrue(joueur.getY() > 0);
    }
}
