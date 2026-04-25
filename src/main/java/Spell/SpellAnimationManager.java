package Spell;

import Rendering.AnimationObject;
import Rendering.ImageManager;

public class SpellAnimationManager {
    public AnimationObject currentAnimation;

    public AnimationObject basicSpellAnimation;

    public void createSpellAnimations() {
        basicSpellAnimation = new AnimationObject(ImageManager.BASIC_SPELL_ANIM, 1, 1, 0, 1, 0.2f);

        currentAnimation = basicSpellAnimation;
    }


    public void updateSpellAnimation(float deltaTime) {
        if (currentAnimation != null) {
            currentAnimation.UpdateAnimation(deltaTime);
        }
    }
}
