package Player;

import Rendering.AnimationObject;
import Rendering.ImageManager;

public class AnimationManager {
    public AnimationObject idleAnimation;
    public AnimationObject walkAnimation;
    public AnimationObject walkUpAnimation;
    public AnimationObject walkDownAnimation;
    
    public void createAllAnimations() {
        idleAnimation = new AnimationObject(ImageManager.PLAYER, 4, 7, 0, 7, 0.15f);
        walkAnimation = new AnimationObject(ImageManager.PLAYER, 4, 7, 1, 4, 0.15f); //Texturen Horizontal (auf dem Sheet), TexturenVertical (auf dem Sheet), Horizontale Position auf dem Sheet, Anzahl der Frames dieser Animation, Zeit pro Frame in Sekunden
        walkUpAnimation = new AnimationObject(ImageManager.PLAYER, 4, 7, 2, 4, 0.15f);
        walkDownAnimation = new AnimationObject(ImageManager.PLAYER, 4, 7, 3, 4, 0.15f);
    }
}
