package Player;

import GUI.AnimationObject;
import GUI.ImageManager;

public class AnimationManager {
    public AnimationObject walkAnimation;
    
    public void createAllAnimations() {
        walkAnimation = new AnimationObject(ImageManager.PLAYER, 1, 4, 4, 0.15f); //Texturen Horizontal, TexturenVertical, Anzahl der Frames, Zeit pro Frame in Sekunden
    }
}
