package universite_paris8.iut.dagnetti.junglequest.vue.utilitaire;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.List;

public class ExtracteurSprites {
    private static final int TAILLE_FRAME = 56;

    public static WritableImage[] extraire(Image spriteSheet, List<PositionFrame> frames) {
        WritableImage[] resultats = new WritableImage[frames.size()];
        PixelReader lecteur = spriteSheet.getPixelReader();

        for (int i = 0; i < frames.size(); i++) {
            PositionFrame f = frames.get(i);
            int x = f.colonne() * TAILLE_FRAME;
            int y = f.ligne() * TAILLE_FRAME;
            resultats[i] = new WritableImage(lecteur, x, y, TAILLE_FRAME, TAILLE_FRAME);
        }

        return resultats;
    }

    public static WritableImage[] idle(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 0, 5));
    }

    public static WritableImage[] attaque(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 1, 7));
    }

    public static WritableImage[] marche(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 2, 7));
    }

    public static WritableImage[] preparationSaut(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 3, 1));
    }

    public static WritableImage[] volSaut(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(2, 3, 5));
    }

    public static WritableImage[] sautReload(Image spriteSheet) {
        List<PositionFrame> frames = new ArrayList<>();
        frames.add(new PositionFrame(6, 3));
        frames.add(new PositionFrame(7, 3));
        frames.add(new PositionFrame(0, 4));
        return extraire(spriteSheet, frames);
    }

    public static WritableImage[] chute(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(1, 4, 4));
    }

    public static WritableImage[] atterrissage(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(5, 4, 7));
    }

    public static WritableImage[] degats(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 6, 3));
    }

    public static WritableImage[] mort(Image spriteSheet) {
        List<PositionFrame> frames = new ArrayList<>();
        for (int i = 0; i <= 7; i++) frames.add(new PositionFrame(i, 6));
        for (int i = 0; i <= 3; i++) frames.add(new PositionFrame(i, 7));
        return extraire(spriteSheet, frames);
    }

    public static WritableImage[] sort(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 8, 7));
    }

    public static WritableImage[] accroupi(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 9, 2));
    }

    public static WritableImage[] bouclier(Image spriteSheet) {
        return extraire(spriteSheet, frameRange(0, 10, 2));
    }

    private static List<PositionFrame> frameRange(int debutCol, int ligne, int finCol) {
        List<PositionFrame> frames = new ArrayList<>();
        for (int i = debutCol; i <= finCol; i++) {
            frames.add(new PositionFrame(i, ligne));
        }
        return frames;
    }
}
