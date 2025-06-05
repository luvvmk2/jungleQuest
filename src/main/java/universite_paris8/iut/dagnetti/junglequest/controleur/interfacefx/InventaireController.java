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
import java.util.ResourceBundle;

public class InventaireController implements Initializable {

    @FXML
    private HBox slotBar;

    private Inventaire inventaire;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Appliquer le fichier CSS externe
        slotBar.getStylesheets().add(getClass().getResource(
                "/universite_paris8/iut/dagnetti/junglequest/styles/inventaire.css"
        ).toExternalForm());
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        afficherSlots();
        System.out.println("Affichage inventaire FXML démarré.");
    }

    private void afficherSlots() {
        slotBar.getChildren().clear();

        if (inventaire == null) return;

        // 🔹 Ajouter chaque item réel
        for (Map.Entry<String, Integer> entry : inventaire.getItems().entrySet()) {
            String nom = entry.getKey();
            int quantite = entry.getValue();

            StackPane slot = creerSlot(nom, quantite);
            slotBar.getChildren().add(slot);
        }

        // 🔹 Compléter avec des cases vides
        int slotsManquants = 9 - inventaire.getItems().size();
        for (int i = 0; i < slotsManquants; i++) {
            StackPane vide = creerSlotVide();
            slotBar.getChildren().add(vide);
        }

        System.out.println("Inventaire chargé : " + inventaire.getItems().size() + " items.");
    }

    private StackPane creerSlot(String nom, int quantite) {
        StackPane slot = new StackPane();
        slot.getStyleClass().add("slot-rempli");

        // 📦 Icône de l’objet
        String cheminImage = "/universite_paris8/iut/dagnetti/junglequest/images/items/" + nom.toLowerCase() + ".png";
        ImageView icone = null;
        try {
            icone = new ImageView(new Image(getClass().getResourceAsStream(cheminImage)));
            icone.setFitWidth(24);
            icone.setFitHeight(24);
        } catch (Exception e) {
            System.err.println("⚠️ Icône non trouvée pour : " + nom);
        }

        // 🏷 Quantité
        Label label = new Label("x" + quantite);
        label.getStyleClass().add("label-item");
        StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);

        if (icone != null) slot.getChildren().add(icone);
        slot.getChildren().add(label);

        return slot;
    }

    private StackPane creerSlotVide() {
        StackPane empty = new StackPane();
        empty.getStyleClass().add("slot-vide");
        return empty;
    }
}
