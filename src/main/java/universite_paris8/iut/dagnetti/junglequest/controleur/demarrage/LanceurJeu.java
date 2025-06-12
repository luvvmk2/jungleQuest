package universite_paris8.iut.dagnetti.junglequest.controleur.demarrage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import universite_paris8.iut.dagnetti.junglequest.controleur.ControleurJeu;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.InventaireController;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.ChargeurCarte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.vue.VueBackground;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.ExtracteurSprites;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.PositionFrame;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;

public class LanceurJeu extends Application {

    private static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        System.out.println("Initialisation du jeu Jungle Quest...");
        initialiserMusique();

        Rectangle2D ecran = Screen.getPrimary().getBounds();
        double largeur = ecran.getWidth();
        double hauteur = ecran.getHeight();

        Pane racine;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/VueJeu.fxml"));
            racine = loader.load();
        } catch (IOException e) {
            racine = new Pane();
        }
        Scene scene = new Scene(racine, largeur, hauteur);

        try {
            int[][] grille = ChargeurCarte.chargerCarteDepuisCSV("/universite_paris8/iut/dagnetti/junglequest/cartes/jungle_map_calque1.csv");
            Carte carte = new Carte(grille);
            System.out.println("Carte chargée avec succès.");

            Image tileset = new Image(getClass().getResourceAsStream("/universite_paris8/iut/dagnetti/junglequest/images/tileset_jungle.png"));
            if (tileset.isError()) System.err.println("Erreur de chargement du tileset.");
            else System.out.println("Tileset jungle chargé.");

            CarteAffichable carteAffichable = new CarteAffichable(carte, tileset, (int) largeur, (int) hauteur);
            int largeurCartePx = carte.getLargeur() * ConstantesJeu.TAILLE_TUILE;
            VueBackground vueBackground = new VueBackground((int) largeur, (int) hauteur, largeurCartePx);
            System.out.println("Background dynamique initialisé.");

            racine.getChildren().add(vueBackground);

            System.out.println("Extraction des animations du personnage...");
            Image personnage = new Image(getClass().getResourceAsStream("/universite_paris8/iut/dagnetti/junglequest/images/sprite1.png"));
            WritableImage[] idle = ExtracteurSprites.idle(personnage);
            WritableImage[] attaque = ExtracteurSprites.attaque(personnage);
            WritableImage[] marche = ExtracteurSprites.marche(personnage);
            WritableImage[] accroupi = ExtracteurSprites.accroupi(personnage);
            WritableImage[] preparationSaut = ExtracteurSprites.preparationSaut(personnage);
            WritableImage[] volSaut = ExtracteurSprites.volSaut(personnage);
            WritableImage[] sautReload = ExtracteurSprites.sautReload(personnage);
            WritableImage[] chute = ExtracteurSprites.chute(personnage);
            WritableImage[] atterrissage = ExtracteurSprites.atterrissage(personnage);
            WritableImage[] degats = ExtracteurSprites.degats(personnage);
            WritableImage[] mort = ExtracteurSprites.mort(personnage);
            WritableImage[] sort = ExtracteurSprites.sort(personnage);
            WritableImage[] bouclier = ExtracteurSprites.bouclier(personnage);
            System.out.println("Animations extraites.");

            double xInitial = 320;
            int colonne = (int) (xInitial / ConstantesJeu.TAILLE_TUILE);
            int ligneSol = carte.chercherLigneSol(colonne);
            double yInitial = ligneSol != -1 ? (carte.getHauteur() - 1 - ligneSol) * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.TAILLE_SPRITE : 56;
            System.out.printf("Position initiale du joueur : (%.0f, %.0f)\n", xInitial, yInitial);

            ImageView spriteJoueur = new ImageView(idle[0]);
            spriteJoueur.setFitWidth(ConstantesJeu.TAILLE_SPRITE);
            spriteJoueur.setFitHeight(ConstantesJeu.TAILLE_SPRITE);

            Joueur joueur = new Joueur(spriteJoueur, xInitial, yInitial);
            racine.getChildren().addAll(carteAffichable, spriteJoueur);
            InventaireController inventaireCtrl = afficherInventaire(racine, joueur, largeur, hauteur);

            ControleurJeu controleurJeu = new ControleurJeu(scene, carte, carteAffichable, joueur, inventaireCtrl,
                    idle, marche, attaque, preparationSaut, volSaut, sautReload,
                    chute, atterrissage, degats, mort, sort, accroupi, bouclier);

            controleurJeu.setVueBackground(vueBackground);

        } catch (IOException e) {
            System.err.println("Erreur critique : " + e.getMessage());
            e.printStackTrace();
        }

        stage.setTitle("Jungle Quest");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
        System.out.println("Jeu lancé avec succès.");
    }

    private void initialiserMusique() {
        try {
            URL ressourceAudio = getClass().getResource("/universite_paris8/iut/dagnetti/junglequest/sons/musique_jeu.mp3");
            if (ressourceAudio != null) {
                Media media = new Media(ressourceAudio.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.25);
                mediaPlayer.play();
                System.out.println("Musique de fond lancée.");
            }
        } catch (Exception e) {
            System.err.println("Erreur musique : " + e.getMessage());
        }
    }

    private InventaireController afficherInventaire (Pane racine, Joueur joueur, double largeur, double hauteur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/dagnetti/junglequest/vue/interface/Inventaire.fxml"));
            Node inventaireUI = loader.load();
            InventaireController inventaireController = loader.getController();
            inventaireController.setInventaire(joueur.getInventaire());
            inventaireUI.setLayoutX(10);
            inventaireUI.setLayoutY(10);
            inventaireUI.setViewOrder(-10);
            racine.getChildren().add(inventaireUI);
            System.out.println("Interface d'inventaire chargée.");
            return inventaireController;
        } catch (IOException e) {
            System.err.println("Inventaire UI non chargé : " + e.getMessage());
            return null;
        }
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
