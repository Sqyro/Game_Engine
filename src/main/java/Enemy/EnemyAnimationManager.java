package Enemy;

import Rendering.AnimationObject;
import Rendering.ImageManager;

public class EnemyAnimationManager {
    public AnimationObject currentAnimation;

    public AnimationObject swampWalkAnimation;
    public AnimationObject swampWalkDownAnimation;
    public AnimationObject swampWalkUpAnimation;

    public void createEnemyAnimations() {
        swampWalkAnimation = new AnimationObject(ImageManager.ENEMIES, 8, 4, 3, 4, 0.2f);
        swampWalkDownAnimation = new AnimationObject(ImageManager.ENEMIES, 8, 4, 2, 4, 0.2f);
        swampWalkUpAnimation = new AnimationObject(ImageManager.ENEMIES, 8, 4, 4, 4, 0.2f);

        currentAnimation = swampWalkAnimation;
    }

    public void updateEnemyAnimation(float deltaTime) {
        currentAnimation.UpdateAnimation(deltaTime);
    }
}
