package Spell;

import Rendering.ImageHandler;
import Registry.IRegistrable;

import java.io.Serializable;

public abstract class Spell implements IRegistrable, Serializable {
    private static final long serialVersionUID = 1L;

    public int IconTextureID;
    public float SpellIconWidth;
    public float SpellIconHeight;

    public int CastTextureID;
    public float SpellCastWidth;
    public float SpellCastHeight;

    public String RegistryName;

    public transient float passedTime;
    public float SpellCooldownInSeconds;

    public float MAX_TRAVEL_DIST;

    public Spell(int IconTextureID, float SpellIconWidth, float SpellIconHeight, int CastTextureID, float SpellCastWidth, float SpellCastHeight, String RegistryName, float SpellCooldownInSeconds) {
        this.IconTextureID = IconTextureID;
        this.SpellIconWidth = SpellIconWidth;
        this.SpellIconHeight = SpellIconHeight;

        this.CastTextureID = CastTextureID;
        this.SpellCastWidth = SpellCastWidth;
        this.SpellCastHeight = SpellCastHeight;

        this.RegistryName = RegistryName;
        this.SpellCooldownInSeconds = SpellCooldownInSeconds;
        this.passedTime = SpellCooldownInSeconds;
    }

    public abstract void onCast(float TargetPosX, float TargetPosY);

    public abstract void onSpellTick(float deltaTime, ImageHandler renderer);

    public void renderIcon(float PosX, float PosY, ImageHandler renderer) {
        renderer.drawFull(IconTextureID, PosX, PosY, SpellIconWidth, SpellIconHeight, 1f, 1f, 1f, 1f);
    }

    @Override
    public String getRegistryName() {
        return RegistryName;
    }
}