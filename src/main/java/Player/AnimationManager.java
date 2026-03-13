package Player;

import Rendering.AnimationObject;
import Rendering.ImageManager;

public class AnimationManager {
    public AnimationObject walkAnimation;
    
    public void createAllAnimations() {
        walkAnimation = new AnimationObject(ImageManager.PLAYER, 4, 7, 1, 4, 0.15f); //Texturen Horizontal (auf dem Sheet), TexturenVertical (auf dem Sheet), Horizontale Position auf dem Sheet, Anzahl der Frames dieser Animation, Zeit pro Frame in Sekunden
    }
}
