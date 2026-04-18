package Player;

import Rendering.AnimationObject;
import Rendering.ImageManager;

public class PlayerAnimationManager {
    //Variablen für die ganzen Animationen

    public AnimationObject currentAnimation;

    public AnimationObject idleAnimation;
    public AnimationObject idleDownAnimation;
    public AnimationObject idleUpAnimation;

    public AnimationObject walkAnimation;
    public AnimationObject walkDownAnimation;
    public AnimationObject walkUpAnimation;

    public AnimationObject dodgeRollAnimation;
    public AnimationObject dodgeRollDownAnimation;
    public AnimationObject dodgeRollUpAnimation;

    public AnimationObject ultSpellAnimation;
    public AnimationObject ultSpellDownAnimation;
    public AnimationObject ultSpellUpAnimation;

    public AnimationObject itemPickupAnimation;
    
    //Methode um alle Animationen auf einmal am Anfang zu erstellen
    public void createPlayerAnimations() {
        //Idle Animationen
        //Texturen Horizontal (auf dem Sheet), TexturenVertical (auf dem Sheet), Horizontale Position auf dem Sheet, Anzahl der Frames dieser Animation, Zeit pro Frame in Sekunden
        idleAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 0, 6, 0.2f);
        idleDownAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 1, 6, 0.2f);
        idleUpAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 2, 6, 0.2f);
        //Lauf Animationen
        walkAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 3, 4, 0.2f);
        walkDownAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 4, 4, 0.2f);
        walkUpAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 5, 4, 0.2f);

        dodgeRollAnimation = new AnimationObject(ImageManager.PLAYER_SPECIAL, 7, 15, 0, 5, 0.2f);
        dodgeRollDownAnimation = new AnimationObject(ImageManager.PLAYER_SPECIAL, 7, 15, 1, 5, 0.2f);
        dodgeRollUpAnimation = new AnimationObject(ImageManager.PLAYER_SPECIAL, 7, 15, 2, 5, 0.2f);

        ultSpellAnimation = new AnimationObject(ImageManager.PLAYER_SPECIAL, 7, 15, 3, 6, 0.2f);
        ultSpellDownAnimation = new AnimationObject(ImageManager.PLAYER_SPECIAL, 7, 15, 4, 6, 0.2f);
        ultSpellUpAnimation = new AnimationObject(ImageManager.PLAYER_SPECIAL, 7, 15, 5, 6, 0.2f);

        itemPickupAnimation = new AnimationObject(ImageManager.PLAYER_SPECIAL, 7, 15, 6, 15, 0.2f);

        currentAnimation = idleAnimation;
    }
    
    //Methode um alle Animationen auf einmal up-zu daten, wenn Animationen später für Lag sorgen, dann vermutlich wegen dieser Methode. Du musst einfach machen, dass nur die Momentan angezeigte Animation geupdated wird. Habs noch nicht gemacht, weil ich noch nicht weiß wie später animationen aussehen, wenn noch Enemies welche haben
    public void updatePlayerAnimation(float deltaTime) {
        currentAnimation.UpdateAnimation(deltaTime);
    }
}
