package Spell;

import Registry.DeferredRegister;
import Rendering.ImageManager;
import Spell.SpellClasses.BasicSpell;
import Spell.SpellClasses.FireballSpell;

public class Spells {
    public static final DeferredRegister<Spell> SPELLS = new DeferredRegister<>();

    public static void RegisterSpells() {
        SPELLS.register(new BasicSpell(ImageManager.SWORD,32, 32, ImageManager.BASIC_SPELL_ANIM, 16, 16, "basic", 1));
        SPELLS.register(new FireballSpell(ImageManager.SWORD, 32, 32, ImageManager.FIREBALL_SPELL_ANIM, 64, 64, "fireball", 2));
        System.out.println("Spells registered");
    }
}