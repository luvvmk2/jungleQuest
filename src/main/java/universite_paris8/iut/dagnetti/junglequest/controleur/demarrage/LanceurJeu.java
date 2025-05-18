package universite_paris8.iut.dagnetti.junglequest.controleur.demarrage;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import universite_paris8.iut.dagnetti.junglequest.controleur.ControleurJeu;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;
import universite_paris8.iut.dagnetti.junglequest.modele.utilitaire.Carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.utilitaire.Carte.ChargeurCarte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.modele.utilitaire.ExtracteurSprites;
import universite_paris8.iut.dagnetti.junglequest.modele.utilitaire.PositionFrame;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LanceurJeu extends Application {

    private static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        try {
            URL ressourceAudio = getClass().getResource("/universite_paris8/iut/dagnetti/junglequest/sons/musique_jeu2.mp3");
            if (ressourceAudio != null) {
                Media media = new Media(ressourceAudio.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.25);
                mediaPlayer.play();
            } else {
                System.err.println("Audio non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Rectangle2D ecran = Screen.getPrimary().getBounds();
        double largeur = ecran.getWidth();
        double hauteur = ecran.getHeight();

        Pane racine = new Pane();
        Scene scene = new Scene(racine, largeur, hauteur);

        try {
            int[][] grille = ChargeurCarte.chargerCarteDepuisCSV("/universite_paris8/iut/dagnetti/junglequest/cartes/jungle_map_calque1.csv");
            Carte carte = new Carte(grille);

            Image tileset = new Image(getClass().getResourceAsStream("/universite_paris8/iut/dagnetti/junglequest/images/tileset_jungle.png"));
            CarteAffichable carteAffichable = new CarteAffichable(carte, tileset, (int) largeur, (int) hauteur);

            Image spriteSheet = new Image(getClass().getResourceAsStream("/universite_paris8/iut/dagnetti/junglequest/images/sprite1.png"));

            WritableImage[] idle = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 0, 5, 0));
            WritableImage[] marche = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 2, 7, 2));
            WritableImage[] attaque = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 1, 7, 1));
            WritableImage[] preparationSaut = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 3, 1, 3));
            WritableImage[] volSaut = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(2, 3, 5, 3));
            WritableImage[] sautReload = ExtracteurSprites.extraire(spriteSheet, List.of(new PositionFrame(6, 3), new PositionFrame(0, 4)));
            WritableImage[] chute = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(1, 4, 4, 4));
            WritableImage[] atterrissage = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(5, 4, 7, 4));
            WritableImage[] degats = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 6, 3, 6));
            WritableImage[] mort = ExtracteurSprites.extraire(spriteSheet, concatFrames(creerListeFrames(0, 6, 7, 6), creerListeFrames(0, 7, 3, 7)));
            WritableImage[] sort = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 8, 7, 8));
            WritableImage[] accroupi = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 9, 2, 9));
            WritableImage[] bouclier = ExtracteurSprites.extraire(spriteSheet, creerListeFrames(0, 10, 2, 10));

            double xInitial = 320;
            int colonne = (int) (xInitial / ConstantesJeu.TAILLE_TUILE);
            int ligneSol = carte.chercherLigneSol(colonne);
            double yInitial = ligneSol != -1
                    ? (carte.getHauteur() - 1 - ligneSol) * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.TAILLE_SPRITE
                    : 0;

            ImageView spriteJoueur = new ImageView(idle[0]);
            spriteJoueur.setFitWidth(ConstantesJeu.TAILLE_SPRITE);
            spriteJoueur.setFitHeight(ConstantesJeu.TAILLE_SPRITE);

            Joueur joueur = new Joueur(spriteJoueur, xInitial, yInitial);
            racine.getChildren().addAll(carteAffichable, spriteJoueur);

            new ControleurJeu(scene, carte, carteAffichable, joueur,
                    idle, marche, attaque, preparationSaut, volSaut, sautReload,
                    chute, atterrissage, degats, mort, sort, accroupi, bouclier);

        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Jungle Quest");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    private List<PositionFrame> creerListeFrames(int debutCol, int ligne, int finCol, int ligneFin) {
        List<PositionFrame> frames = new ArrayList<>();
        for (int l = ligne; l <= ligneFin; l++) {
            int start = (l == ligne) ? debutCol : 0;
            int end = (l == ligneFin) ? finCol : 7;
            for (int c = start; c <= end; c++) {
                frames.add(new PositionFrame(c, l));
            }
        }
        return frames;
    }

    private List<PositionFrame> concatFrames(List<PositionFrame> a, List<PositionFrame> b) {
        List<PositionFrame> result = new ArrayList<>(a);
        result.addAll(b);
        return result;
    }
}
