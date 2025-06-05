package universite_paris8.iut.dagnetti.junglequest.modele.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente l’inventaire d’un personnage. Chaque objet est associé à une quantité.
 * Fournit des méthodes sûres pour ajouter, retirer et consulter les objets.
 */
public class Inventaire {

    private final Map<String, Integer> items;

    public Inventaire() {
        this.items = new HashMap<>();
    }

    /**
     * Ajoute un objet à l’inventaire.
     * @param nom Nom de l’objet (ex : "Bois", "Pierre")
     * @param quantite Quantité à ajouter (doit être ≥ 1)
     * @return true si l’opération est réussie
     */
    public boolean ajouterItem(String nom, int quantite) {
        if (nom == null || quantite <= 0) return false;

        items.put(nom, items.getOrDefault(nom, 0) + quantite);
        return true;
    }

    /**
     * Retire une certaine quantité d’un objet, si elle est disponible.
     * @param nom Nom de l’objet
     * @param quantite Quantité à retirer
     * @return true si l’objet a été retiré avec succès
     */
    public boolean retirerItem(String nom, int quantite) {
        if (!items.containsKey(nom) || quantite <= 0) return false;

        int actuelle = items.get(nom);
        if (actuelle < quantite) return false;

        if (actuelle == quantite) {
            items.remove(nom);
        } else {
            items.put(nom, actuelle - quantite);
        }
        return true;
    }

    /**
     * Retourne une vue non modifiable de l’inventaire actuel.
     */
    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * Vérifie si un objet est présent en quantité suffisante.
     */
    public boolean contient(String nom, int quantite) {
        return items.getOrDefault(nom, 0) >= quantite;
    }

    /**
     * Vide entièrement l’inventaire.
     */
    public void vider() {
        items.clear();
    }

    @Override
    public String toString() {
        return "Inventaire : " + items.toString();
    }
}
