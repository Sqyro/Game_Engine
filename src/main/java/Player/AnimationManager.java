package Player;

import Rendering.AnimationObject;
import Rendering.ImageManager;

public class AnimationManager {
    //Variablen für die ganzen Animatione
    public AnimationObject idleAnimation;
    public AnimationObject idleDownAnimation;
    public AnimationObject idleUpAnimation;
    public AnimationObject walkAnimation;
    public AnimationObject walkDownAnimation;
    public AnimationObject walkUpAnimation;
    
    //Methode um alle Animationen auf einmal am Anfang zu erstellen
    public void createAllAnimations() {
        //Idle Animationen
        //Texturen Horizontal (auf dem Sheet), TexturenVertical (auf dem Sheet), Horizontale Position auf dem Sheet, Anzahl der Frames dieser Animation, Zeit pro Frame in Sekunden
        idleAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 0, 6, 0.15f);
        idleDownAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 1, 6, 0.15f);
        idleUpAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 2, 6, 0.15f);
        //Lauf Animationen
        walkAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 3, 4, 0.15f);
        walkDownAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 4, 4, 0.15f);
        walkUpAnimation = new AnimationObject(ImageManager.PLAYER, 6, 6, 5, 4, 0.15f);
    }
    
    //Methode um alle Animationen auf einmal up-zu daten, wenn Animationen später für Lag sorgen, dann vermutlich wegen dieser Methode. Du musst einfach machen, dass nur die Momentan angezeigte Animation geupdated wird. Habs noch nicht gemacht, weil ich noch nicht weiß wie später animationen aussehen, wenn noch Enemies welche haben
    public void updateAllAnimations(float deltaTime) {
        idleAnimation.UpdateAnimation(deltaTime);
        idleDownAnimation.UpdateAnimation(deltaTime);
        idleUpAnimation.UpdateAnimation(deltaTime);
        walkAnimation.UpdateAnimation(deltaTime);
        walkDownAnimation.UpdateAnimation(deltaTime);
        walkUpAnimation.UpdateAnimation(deltaTime);
    }
}
