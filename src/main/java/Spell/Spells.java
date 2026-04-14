package Spell;

import Registry.DeferredRegister;

public class Spells {
    public static final DeferredRegister<Spell> SPELLS = new DeferredRegister<>();

    public static void RegisterSpells() {

        System.out.println("Spells registered");
    }
}
