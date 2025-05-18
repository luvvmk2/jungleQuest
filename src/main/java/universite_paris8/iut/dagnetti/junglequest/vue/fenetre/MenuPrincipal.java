package universite_paris8.iut.dagnetti.junglequest.vue.fenetre;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import universite_paris8.iut.dagnetti.junglequest.controleur.demarrage.LanceurJeu;

public class MenuPrincipal extends Application {

    private AudioClip ambiance; // déclaré ici pour pouvoir le stopper

    @Override
    public void start(Stage primaryStage) {
        // --- Arrière-plan ---
        Image background = new Image(getClass().getResourceAsStream(
                "/universite_paris8/iut/dagnetti/junglequest/images/menu_principal_forest.png"));
        ImageView backgroundView = new ImageView(background);
        backgroundView.setPreserveRatio(false);
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);

        // --- Titre animé ---
        Text titre = new Text("JUNGLE QUEST");
        titre.getStyleClass().add("menu-title");

        VBox zoneTitre = new VBox(titre);
        zoneTitre.setAlignment(Pos.TOP_CENTER);
        zoneTitre.setTranslateY(100);

        FadeTransition fadeTitre = new FadeTransition(Duration.millis(1200), titre);
        fadeTitre.setFromValue(0);
        fadeTitre.setToValue(1);
        fadeTitre.setDelay(Duration.millis(300));

        TranslateTransition slideTitre = new TranslateTransition(Duration.millis(1000), titre);
        slideTitre.setFromY(-50);
        slideTitre.setToY(0);
        slideTitre.setDelay(Duration.millis(300));

        fadeTitre.play();
        slideTitre.play();

        // --- Son d'ambiance ---
        ambiance = new AudioClip(getClass().getResource(
                "/universite_paris8/iut/dagnetti/junglequest/sons/menu_jungle.mp3"
        ).toExternalForm());
        ambiance.setCycleCount(AudioClip.INDEFINITE);
        ambiance.setVolume(0.4);
        ambiance.play();

        // --- Bouton "JOUER" ---
        Button jouer = new Button("JOUER");
        jouer.getStyleClass().add("menu-button");
        appliquerEffetSurvol(jouer);
        jouer.setOnAction(e -> {
            ambiance.stop();
            try {
                new LanceurJeu().start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // --- Bouton "QUITTER" ---
        Button quitter = new Button("QUITTER");
        quitter.getStyleClass().add("menu-button");
        appliquerEffetSurvol(quitter);
        quitter.setOnAction(e -> {
            ambiance.stop();
            primaryStage.close();
        });

        VBox boutons = new VBox(60, jouer, quitter);
        boutons.getStyleClass().add("menu-boutons");

        // --- Composition ---
        StackPane racine = new StackPane(backgroundView, zoneTitre, boutons);
        Scene scene = new Scene(racine, 1920, 1080);
        scene.getStylesheets().add(getClass().getResource(
                "/universite_paris8/iut/dagnetti/junglequest/styles/menu.css").toExternalForm());

        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void appliquerEffetSurvol(Button bouton) {
        DropShadow effet = new DropShadow(20, Color.web("#fff58a", 0.6));
        effet.setSpread(0.3);

        bouton.setOnMouseEntered(e -> {
            bouton.setEffect(effet);
            FadeTransition fade = new FadeTransition(Duration.millis(200), bouton);
            fade.setFromValue(1.0);
            fade.setToValue(0.8);
            fade.play();
        });

        bouton.setOnMouseExited(e -> {
            bouton.setEffect(null);
            FadeTransition fade = new FadeTransition(Duration.millis(200), bouton);
            fade.setFromValue(0.8);
            fade.setToValue(1.0);
            fade.play();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
