package Scenes;

import Rendering.AnimationObject;
import Rendering.ImageManager;

public class BackgroundAnimationManager {
    public AnimationObject currentAnimation;

    public AnimationObject MainMenuAnimation;

    public void createBackgroundAnimations() {
        //Idle Animationen
        //Texturen Horizontal (auf dem Sheet), TexturenVertical (auf dem Sheet), Horizontale Position auf dem Sheet, Anzahl der Frames dieser Animation, Zeit pro Frame in Sekunden
        MainMenuAnimation = new AnimationObject(ImageManager.MAIN_MENU, 1, 21, 0, 21, 0.1f);

        currentAnimation = MainMenuAnimation;
    }

    public void updateBackgroundAnimation(float deltaTime) {
        currentAnimation.UpdateAnimation(deltaTime);
    }
}
