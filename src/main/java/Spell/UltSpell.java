package Spell;

public abstract class UltSpell extends Spell{
    public UltSpell(int IconTextureID, float SpellIconWidth, float SpellIconHeight, int CastTextureID, float SpellCastWidth, float SpellCastHeight, String RegistryName) {
        super(IconTextureID, SpellIconWidth, SpellIconHeight, CastTextureID, SpellCastWidth, SpellCastHeight, RegistryName, 0);
    }
}