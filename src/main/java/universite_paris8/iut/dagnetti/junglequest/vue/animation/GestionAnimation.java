package universite_paris8.iut.dagnetti.junglequest.vue.animation;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class GestionAnimation {

    private final WritableImage[] idle;
    private final WritableImage[] marche;
    private final WritableImage[] attaque;
    private final WritableImage[] preparationSaut;
    private final WritableImage[] volSaut;
    private final WritableImage[] sautReload;
    private final WritableImage[] chute;
    private final WritableImage[] atterrissage;
    private final WritableImage[] degats;
    private final WritableImage[] mort;
    private final WritableImage[] sort;
    private final WritableImage[] accroupi;
    private final WritableImage[] bouclier;

    private int frameIdle = 0, compteurIdle = 0;
    private int frameMarche = 0, compteurMarche = 0;
    private int frameAttaque = 0, compteurAttaque = 0;

    private boolean attaqueTerminee = true;
    private boolean comboDemande = false;

    public GestionAnimation(WritableImage[] idle, WritableImage[] marche, WritableImage[] attaque,
                            WritableImage[] preparationSaut, WritableImage[] volSaut, WritableImage[] sautReload,
                            WritableImage[] chute, WritableImage[] atterrissage,
                            WritableImage[] degats, WritableImage[] mort,
                            WritableImage[] sort, WritableImage[] accroupi, WritableImage[] bouclier) {
        this.idle = idle;
        this.marche = marche;
        this.attaque = attaque;
        this.preparationSaut = preparationSaut;
        this.volSaut = volSaut;
        this.sautReload = sautReload;
        this.chute = chute;
        this.atterrissage = atterrissage;
        this.degats = degats;
        this.mort = mort;
        this.sort = sort;
        this.accroupi = accroupi;
        this.bouclier = bouclier;
    }

    public void animerIdle(ImageView sprite, int delai) {
        if (compteurIdle++ >= delai) {
            sprite.setImage(idle[frameIdle]);
            compteurIdle = 0;
            frameIdle = (frameIdle + 1) % idle.length;
        }
    }

    public void animerMarche(ImageView sprite, int delai) {
        if (compteurMarche++ >= delai) {
            sprite.setImage(marche[frameMarche]);
            compteurMarche = 0;
            frameMarche = (frameMarche + 1) % marche.length;
        }
    }

    public void animerSaut(ImageView sprite, double vitesseY) {
        if (vitesseY < -1) {
            sprite.setImage(volSaut[0]);
        } else if (vitesseY > 1) {
            sprite.setImage(chute[0]);
        } else {
            sprite.setImage(sautReload[0]);
        }
    }

    public void animerAttaque(ImageView sprite, int delai, Runnable finAttaque) {
        attaqueTerminee = false;

        if (compteurAttaque++ >= delai) {
            sprite.setImage(attaque[frameAttaque]);
            compteurAttaque = 0;
            frameAttaque++;

            if (frameAttaque == 6 && !comboDemande) {
                frameAttaque = 0;
                attaqueTerminee = true;
                comboDemande = false;
                finAttaque.run();
                return;
            }

            if (frameAttaque >= attaque.length) {
                frameAttaque = 0;
                attaqueTerminee = true;
                comboDemande = false;
                finAttaque.run();
            }
        }
    }

    public void animerDegats(ImageView sprite) {
        sprite.setImage(degats[0]);
    }

    public void animerMort(ImageView sprite, int frame) {
        if (frame >= 0 && frame < mort.length) {
            sprite.setImage(mort[frame]);
        }
    }

    public void animerSort(ImageView sprite, int frame) {
        if (frame >= 0 && frame < sort.length) {
            sprite.setImage(sort[frame]);
        }
    }

    public void animerAccroupi(ImageView sprite) {
        sprite.setImage(accroupi[0]);
    }

    public void animerBouclier(ImageView sprite) {
        sprite.setImage(bouclier[0]);
    }

    public void animerPreparationSaut(ImageView sprite) {
        sprite.setImage(preparationSaut[0]);
    }

    public void animerAtterrissage(ImageView sprite) {
        sprite.setImage(atterrissage[0]);
    }

    public void demandeCombo() {
        this.comboDemande = true;
    }

    public void reset() {
        frameIdle = frameMarche = frameAttaque = compteurIdle = compteurMarche = compteurAttaque = 0;
        attaqueTerminee = false;
        comboDemande = false;
    }

    public boolean isAttaqueTerminee() {
        return attaqueTerminee;
    }
}
