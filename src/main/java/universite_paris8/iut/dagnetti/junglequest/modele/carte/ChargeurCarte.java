package universite_paris8.iut.dagnetti.junglequest.modele.carte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire permettant de charger une carte au format CSV depuis les ressources.
 */
public class ChargeurCarte {

    /**
     * Charge une carte à partir d’un fichier CSV et renvoie une matrice d’entiers.
     *
     * @param cheminDansRessources Le chemin du fichier CSV dans le dossier resources
     * @return Un tableau 2D représentant la carte
     * @throws IOException si le fichier est introuvable ou mal formaté
     */
    public static int[][] chargerCarteDepuisCSV(String cheminDansRessources) throws IOException {
        List<int[]> lignes = new ArrayList<>();

        try (BufferedReader lecteur = new BufferedReader(new InputStreamReader(
                ChargeurCarte.class.getResourceAsStream(cheminDansRessources)))) {

            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                String[] valeurs = ligne.split(",");
                int[] ligneEntiers = new int[valeurs.length];
                for (int i = 0; i < valeurs.length; i++) {
                    ligneEntiers[i] = Integer.parseInt(valeurs[i].trim());
                }
                lignes.add(ligneEntiers);
            }

        } catch (NumberFormatException e) {
            throw new IOException("Erreur de format dans le fichier CSV : " + cheminDansRessources, e);
        }

        return lignes.toArray(new int[0][]);
    }
}
