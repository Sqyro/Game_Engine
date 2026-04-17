package Spell;

import Rendering.ImageHandler;
import Registry.IRegistrable;

public abstract class Spell implements IRegistrable {
    public int IconTextureID;
    public float SpellIconWidth;
    public float SpellIconHeight;

    public int CastTextureID;

    public String RegistryName;

    public Spell(int IconTextureID, float SpellIconWidth, float SpellIconHeight, int CastTextureID, String RegistryName) {
        this.IconTextureID = IconTextureID;
        this.SpellIconWidth = SpellIconWidth;
        this.SpellIconHeight = SpellIconHeight;

        this.CastTextureID = CastTextureID;
        this.RegistryName = RegistryName;
    }

    public abstract void onCast();

    public abstract void onSpellTick();

    public void renderIcon(float PosX, float PosY, ImageHandler renderer) {
        renderer.drawFull(IconTextureID, PosX, PosY, SpellIconWidth, SpellIconHeight, 1f, 1f, 1f, 1f);
    }

    @Override
    public String getRegistryName() {
        return RegistryName;
    }
}
