package universite_paris8.iut.dagnetti.junglequest.controleur;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

import static universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu.*;

import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.controleur.moteur.MoteurPhysique;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.BlocManager;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.TileType;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;
import universite_paris8.iut.dagnetti.junglequest.vue.VueBackground;
import universite_paris8.iut.dagnetti.junglequest.vue.animation.GestionAnimation;
import javafx.scene.image.WritableImage;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.InventaireController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.ParametresController;

public class ControleurJeu {

    private final MoteurPhysique moteur = new MoteurPhysique();
    private final Carte carte;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;
    private final GestionClavier clavier;
    private final GestionAnimation animation;
    private final InventaireController inventaireController;
    private final ProgressBar barreVie;
    private VueBackground vueBackground;
    private final double largeurEcran;

    private int compteurAttaque = 0;
    private int frameMort = 0;
    private int frameSort = 0;
    private double offsetX = 0;

    private boolean joueurMort = false;

    private boolean enPause = false;
    private Stage fenetreParametres;

    /**
     * Initialise le contrôleur principal du jeu : clavier, animation, logique du joueur et gestion des clics.
     */
    public ControleurJeu(Scene scene, Carte carte, CarteAffichable carteAffichable, Joueur joueur, InventaireController inventaireController, ProgressBar barreVie,
                         WritableImage[] idle, WritableImage[] marche,
                         WritableImage[] attaque,
                         WritableImage[] preparationSaut, WritableImage[] volSaut, WritableImage[] sautReload,
                         WritableImage[] chute, WritableImage[] atterrissage,
                         WritableImage[] degats, WritableImage[] mort,
                         WritableImage[] sort, WritableImage[] accroupi, WritableImage[] bouclier) {

        this.carte = carte;
        this.carteAffichable = carteAffichable;
        this.joueur = joueur;
        this.inventaireController = inventaireController;
        this.barreVie = barreVie;
        this.clavier = new GestionClavier(scene);
        this.largeurEcran = scene.getWidth();

        // Initialisation des animations
        this.animation = new GestionAnimation(
                idle, marche, attaque,
                preparationSaut, volSaut, sautReload,
                chute, atterrissage,
                degats, mort,
                sort, accroupi, bouclier
        );

        // Gestion du clic gauche pour attaquer
        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (!joueur.estEnAttaque()) {
                    joueur.attaquer();
                    animation.reset();
                    compteurAttaque = 0;
                } else {
                    animation.demandeCombo();
                }
            }
        });
        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                gererClicDroit(e.getX(), e.getY());
            } });

        // Appuyer sur 'P' permet d'afficher ou de masquer l'inventaire. L'événement
        // est écouté directement sur la scène afin de ne pas interférer avec la
        // gestion classique du clavier.
        scene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.P) {
                if (fenetreParametres == null) {
                    ouvrirParametres(scene);
                }
            } else if (e.getCode() == KeyCode.I) {
                if (inventaireController != null) {
                    inventaireController.basculerAffichage();
                }
            } else if (e.getCode() == KeyCode.ENTER) {
                enPause = !enPause;
            }
        });

        // Lancement de la boucle de jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJour();
            }
        }.start();
    }

    /**
     * Méthode principale appelée à chaque frame pour gérer les actions du joueur et l'affichage.
     */
    private void mettreAJour() {
        if (enPause) {
            return;
        }
        // Récupération des touches clavier pressées
        boolean gauche = clavier.estAppuyee(KeyCode.Q) || clavier.estAppuyee(KeyCode.LEFT);
        boolean droite = clavier.estAppuyee(KeyCode.D) || clavier.estAppuyee(KeyCode.RIGHT);
        boolean toucheSaut = clavier.estAppuyee(KeyCode.SPACE);
        boolean toucheAccroupi = clavier.estAppuyee(KeyCode.CONTROL);
        boolean toucheBouclier = clavier.estAppuyee(KeyCode.SHIFT);
        boolean toucheDegats = clavier.estAppuyee(KeyCode.M);
        boolean toucheMort = clavier.estAppuyee(KeyCode.DIGIT2);
        boolean toucheSort = clavier.estAppuyee(KeyCode.E);
        boolean touchePreparationSaut = clavier.estAppuyee(KeyCode.DIGIT3);
        boolean toucheAtterrissage = clavier.estAppuyee(KeyCode.DIGIT4);

        if (toucheDegats) {
            joueur.subirDegats(1);
        }

        if (joueur.getPointsDeVie() <= 0) {
            joueurMort = true;
        }

        if (toucheMort) {
            joueurMort = true;
        }

        // Déplacement horizontal
        if (!joueurMort) {
            if (gauche) {
                joueur.deplacerGauche(VITESSE_JOUEUR);
            } else if (droite) {
                joueur.deplacerDroite(VITESSE_JOUEUR);
            } else {
                joueur.arreter();
            }
        } else {
            joueur.arreter();
        }

        // Saut
        if (!joueurMort && toucheSaut && joueur.estAuSol()) {
            joueur.sauter(IMPULSION_SAUT);
        }

        moteur.mettreAJourPhysique(joueur, carte);
        offsetX = joueur.getX() - largeurEcran / 2;
        if (offsetX < 0) offsetX = 0;
        double maxOffset = carte.getLargeur() * TAILLE_TUILE -largeurEcran;
        if(offsetX > maxOffset)
            offsetX = maxOffset;

        // Redessiner la carte avec le nouveau décalage
        carteAffichable.redessiner(offsetX);

        // Redessiner le fond si présent
        if (vueBackground != null) {
            vueBackground.mettreAJourScroll(offsetX);
        }
        joueur.getSprite().setX(joueur.getX() - offsetX);
        barreVie.setLayoutX(joueur.getX() - offsetX);
        barreVie.setLayoutY(joueur.getY() - 10);
        double ratioVie = joueur.getPointsDeVie() / (double) VIE_MAX_JOUEUR;
        barreVie.setProgress(ratioVie);
        // La couleur passe du vert au rouge en fonction de la vie restante
        Color couleurVie = Color.GREEN.interpolate(Color.RED, 1 - ratioVie);
        String hex = String.format("#%02X%02X%02X",
                (int) (couleurVie.getRed() * 255),
                (int) (couleurVie.getGreen() * 255),
                (int) (couleurVie.getBlue() * 255));
        barreVie.setStyle("-fx-accent: " + hex + ";");

        // Gestion des animations
        ImageView sprite = joueur.getSprite();

        if (joueurMort) {
            animation.animerMort(sprite, frameMort++);
            if (frameMort >= 12) frameMort = 11;
            sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
            return;
        } else if (toucheDegats) {
            animation.animerDegats(sprite);
        } else if (toucheSort) {
            animation.animerSort(sprite, frameSort++);
            if (frameSort >= 8) frameSort = 0;
        } else if (touchePreparationSaut) {
            animation.animerIdle(sprite, DELAI_FRAME);
            animation.animerPreparationSaut(sprite);
        } else if (toucheAtterrissage) {
            animation.animerAtterrissage(sprite);
        } else if (joueur.estEnAttaque()) {
            animation.animerAttaque(sprite, DELAI_FRAME, () -> joueur.finAttaque());
            compteurAttaque++;
        } else if (!joueur.estAuSol()) {
            animation.animerSaut(sprite, joueur.getVitesseY());
        } else if (toucheAccroupi) {
            animation.animerAccroupi(sprite);
        } else if (toucheBouclier) {
            animation.animerBouclier(sprite);
        } else if (joueur.getVitesseX() != 0) {
            animation.animerMarche(sprite, DELAI_FRAME);
        } else {
            animation.animerIdle(sprite, DELAI_FRAME);
        }

        // Inversion du sprite si le joueur regarde à gauche
        sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
    }

    /**
     * Permet de lier dynamiquement la vue du fond à ce contrôleur.
     */
    public void setVueBackground(VueBackground vueBackground) {
        this.vueBackground = vueBackground;
    }

    private void gererClicDroit(double xScene, double yScene) {
        int colonne = (int) ((xScene + offsetX) / TAILLE_TUILE);
        int ligne = (int) (yScene / TAILLE_TUILE);
        boolean dansCarte = colonne >= 0 && colonne < carte.getLargeur()
                && ligne >= 0 && ligne < carte.getHauteur();

        if (dansCarte) {

            String selection = inventaireController != null ? inventaireController.getItemSelectionne() : null;

            if (selection != null) {
                TileType type = switch (selection.toLowerCase()) {
                    case "bois" -> TileType.ARBRE;
                    case "terre" -> TileType.TERRE;
                    case "herbe" -> TileType.HERBE;
                    default -> null;
                };
                if (type != null && joueur.getInventaire().retirerItem(selection, 1)) {
                    carte.setValeurTuile(ligne, colonne, type.getId());
                }
                if (inventaireController != null) {
                    inventaireController.deselectionner();
                    inventaireController.rafraichir();
                }
            } else {
                int idAvant = carte.getValeurTuile(ligne, colonne);
                if (BlocManager.casserBloc(carte, ligne, colonne) && idAvant != Carte.TUILE_VIDE) {
                    joueur.getInventaire().ajouterItem("Bois", 1);
                    if (inventaireController != null) {
                        inventaireController.rafraichir();
                    }
                }
            }

            carteAffichable.redessiner(offsetX);
        }
    }

    private void ouvrirParametres(Scene scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/interface/Parametres.fxml"));
            Parent root = loader.load();
            fenetreParametres = new Stage();
            fenetreParametres.initOwner(scene.getWindow());
            fenetreParametres.initModality(Modality.WINDOW_MODAL);
            fenetreParametres.setTitle("Paramètres");
            fenetreParametres.setScene(new Scene(root));
            ParametresController controller = loader.getController();
            controller.setStage(fenetreParametres);
            enPause = true;
            fenetreParametres.setOnHidden(e -> { enPause = false; fenetreParametres = null; });
            fenetreParametres.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
