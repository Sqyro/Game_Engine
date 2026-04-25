package Spell.SpellClasses;

import Enemy.Enemy;
import Physics2D.CircleCollider;
import Physics2D.VelocityHandler;
import Player.Player;
import Rendering.ImageHandler;
import Spell.Spell;
import Spell.Projectile;
import Spell.SpellAnimationManager;

import java.util.ArrayList;
import java.util.List;

public class BasicSpell extends Spell {
    public static float BASIC_SPELL_DAMAGE = 10;
    public static float BASIC_SPELL_SPEED = 1200;
    public static float BASIC_SPELL_MAX_TRAVEL_DISTANCE = 600;

    public transient List<Projectile> LivingProjectiles;

    private transient SpellAnimationManager spellAnimationManager;

    public BasicSpell(int IconTextureID, float SpellIconWidth, float SpellIconHeight, int CastTextureID, float SpellCastWidth, float SpellCastHeight, String RegistryName, float SpellCooldownInSeconds) {
        super(IconTextureID, SpellIconWidth, SpellIconHeight, CastTextureID, SpellCastWidth, SpellCastHeight, RegistryName, SpellCooldownInSeconds);
        init();
    }

    private void init() {
        spellAnimationManager = new SpellAnimationManager();
        LivingProjectiles = new ArrayList<>();
    }

    @Override
    public void onCast(float TargetPosX, float TargetPosY) {
        spellAnimationManager.createSpellAnimations();
        float[] ShootDirection = {TargetPosX - Player.Player.PosX, TargetPosY - Player.Player.PosY};
        LivingProjectiles.add(new Projectile(Player.Player.PosX, Player.Player.PosY, SpellCastWidth, SpellCastHeight, CastTextureID, BASIC_SPELL_SPEED, ShootDirection, new CircleCollider(SpellCastWidth/2, 0, 0)));
        System.out.println("Basic Spell casted");
    }

    @Override
    public void onSpellTick(float deltaTime, ImageHandler renderer) {
        passedTime += deltaTime;
        spellAnimationManager.updateSpellAnimation(deltaTime);
        for (int i = LivingProjectiles.size() - 1; i >= 0; i--) {
            Projectile ThisProjectile = LivingProjectiles.get(i);
            VelocityHandler.calculatePosition(ThisProjectile, deltaTime);

            boolean removed = false;

            for (Enemy CurrentEnemy : Enemy.Enemies) {
                if (CurrentEnemy.PosX <= ThisProjectile.PosX && CurrentEnemy.PosX + CurrentEnemy.ObjLength >= ThisProjectile.PosX && CurrentEnemy.PosY <= ThisProjectile.PosY && CurrentEnemy.PosY + CurrentEnemy.ObjHeight >= ThisProjectile.PosY) {
                    CurrentEnemy.damageObject(BASIC_SPELL_DAMAGE);
                    System.out.println("Enemy Hit");
                    LivingProjectiles.remove(i);
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                if (Math.sqrt((ThisProjectile.PosX - ThisProjectile.StartPosX) * (ThisProjectile.PosX - ThisProjectile.StartPosX) + (ThisProjectile.PosY - ThisProjectile.StartPosY) * (ThisProjectile.PosY - ThisProjectile.StartPosY)) >= BASIC_SPELL_MAX_TRAVEL_DISTANCE) {
                    LivingProjectiles.remove(i);
                } else {
                    spellAnimationManager.currentAnimation.renderAnimation(ThisProjectile.PosX, ThisProjectile.PosY, ThisProjectile.ObjLength, ThisProjectile.ObjHeight, false, renderer);
                }
            }
        }
    }
}