module universite_paris8.iut.dagnetti.junglequest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    exports universite_paris8.iut.dagnetti.junglequest.vue.fenetre;
    exports universite_paris8.iut.dagnetti.junglequest.controleur.demarrage;
    exports universite_paris8.iut.dagnetti.junglequest.controleur;
    exports universite_paris8.iut.dagnetti.junglequest.vue;
    exports universite_paris8.iut.dagnetti.junglequest.controleur.moteur;
    exports universite_paris8.iut.dagnetti.junglequest.modele.personnages;
    exports universite_paris8.iut.dagnetti.junglequest.modele.carte;
    exports universite_paris8.iut.dagnetti.junglequest.modele.donnees;
    exports universite_paris8.iut.dagnetti.junglequest.vue.animation;
    exports universite_paris8.iut.dagnetti.junglequest.vue.utilitaire;
    opens universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx to javafx.fxml;


}
