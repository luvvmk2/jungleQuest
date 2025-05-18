package universite_paris8.iut.dagnetti.junglequest.controleur;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Gère l’état du clavier (touches appuyées / relâchées).
 * Fournit une interface simple pour interroger si une touche est actuellement pressée.
 */
public class GestionClavier {

    private final Set<KeyCode> touchesActives = new HashSet<>();

    public GestionClavier(Scene scene) {
        scene.setOnKeyPressed(e -> touchesActives.add(e.getCode()));
        scene.setOnKeyReleased(e -> touchesActives.remove(e.getCode()));
    }

    public boolean estAppuyee(KeyCode touche) {
        return touchesActives.contains(touche);
    }

    public boolean estVide() {
        return touchesActives.isEmpty();
    }

    public Set<KeyCode> getTouchesActives() {
        return new HashSet<>(touchesActives);
    }
}
