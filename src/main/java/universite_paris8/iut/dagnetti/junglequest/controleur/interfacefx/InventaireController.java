package universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Inventaire;

import java.net.URL;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventaireController implements Initializable {

    @FXML
    private HBox slotBar;
    private Inventaire inventaire;
    private String itemSelectionne;
    private List<String> ordreItems = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            slotBar.getStylesheets().add(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/styles/inventaire.css"
            ).toExternalForm());
            System.out.println("Feuille de style de l’inventaire chargée.");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du CSS de l’inventaire.");
        }
    }

    /**
     * Définit l’inventaire et l’affiche graphiquement dans l’interface.
     */
    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;

        if (inventaire == null) {
            System.err.println("Inventaire non initialisé (null).");
        } else {
            ordreItems = new ArrayList<>(inventaire.getItems().keySet());
            afficherSlots();
            itemSelectionne = null;
            System.out.println("Inventaire appliqué au contrôleur. Contenu : " + inventaire.getItems().size() + " item(s).");
        }
    }

    /**
     * Met à jour les cases d'inventaire à partir des données internes.
     */
    private void afficherSlots() {
        slotBar.getChildren().clear();

        // Slot fixe pour l'épée
        StackPane slotEpee = creerSlotEpee();
        slotBar.getChildren().add(slotEpee);

        //  Affichage des objets possédés dans l'ordre mémorisé
        for (String nom : ordreItems) {
            int quantite = inventaire.getItems().getOrDefault(nom, 0);
            StackPane slot = creerSlot(nom, quantite);
            slotBar.getChildren().add(slot);
        }

        // Complétion visuelle avec des slots vides
        int slotsUtilises = ordreItems.size();
        int slotsTotaux = 9;

        for (int i = slotsUtilises; i < slotsTotaux; i++) {
            StackPane vide = creerSlotVide();
            slotBar.getChildren().add(vide);
        }
    }

    /**
     * Crée un slot rempli visuellement avec une icône et une étiquette de quantité.
     */
    private StackPane creerSlot(String nom, int quantite) {
        StackPane slot = new StackPane();
        slot.getStyleClass().add("slot-rempli");

        // Chargement de l’image de l’objet
        String cheminImage = "/universite_paris8/iut/dagnetti/junglequest/images/items/" + nom.toLowerCase() + ".png";
        ImageView icone = null;

        try {
            Image image = new Image(getClass().getResourceAsStream(cheminImage));
            icone = new ImageView(image);
            icone.setFitWidth(24);
            icone.setFitHeight(24);
        } catch (Exception e) {
            System.err.println("Icône introuvable : \"" + nom + "\" → Attendu à : " + cheminImage);
        }

        // Quantité
        Label label = new Label("x" + quantite);
        label.getStyleClass().add("label-item");
        StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);

        if (icone != null) {
            slot.getChildren().add(icone);
        } else {
            Label erreur = new Label("X");
            erreur.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            slot.getChildren().add(erreur);
        }

        slot.getChildren().add(label);

        //sélection d'un item pour le placement
        slot.setOnMouseClicked(e -> itemSelectionne = nom);
        return slot;
    }

    /**
     * Crée une case vide esthétique pour compléter l’inventaire.
     */
    private StackPane creerSlotVide() {
        StackPane empty = new StackPane();
        empty.getStyleClass().add("slot-vide");
        return empty;
    }
    /**Rafraichit l'affichage a partir des donnée de l'inventaire */
    public void rafraichir(){
        if (inventaire != null){
            afficherSlots();
        }
    }
    /**Retourne l'item actuellement séléctionné pour le placement */
    public String getItemSelectionne(){
        return itemSelectionne;
    }
    /**réinitialise la séléction d'item */
    public void deselectionner(){
        itemSelectionne = null;
    }

    /**
     * Sélectionne l'objet à l'indice donné dans l'inventaire.
     */
    public void selectionnerIndex(int index) {
        if (index >= 0 && index < ordreItems.size()) {
            itemSelectionne = ordreItems.get(index);
        }
    }

    private StackPane creerSlotEpee() {
        StackPane slot = new StackPane();
        slot.getStyleClass().add("slot-epee");
        Label icon = new Label("\u2694"); // simple sword unicode
        icon.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        slot.getChildren().add(icon);
        slot.setOnMouseClicked(e -> itemSelectionne = null);
        return slot;
    }

    /**
     * Affiche ou masque l'inventaire en conservant son emplacement dans la
     * mise en page.
     */
    public void basculerAffichage() {
        boolean visible = !slotBar.isVisible();
        slotBar.setVisible(visible);
        slotBar.setManaged(visible);
    }
}
