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
    public static float BASIC_SPELL_SPLASH_DAMAGE = 0;
    public static float BASIC_SPELL_SPEED = 1200;
    public static float BASIC_SPELL_MAX_TRAVEL_DISTANCE = 600;
    public static float BASIC_SPELL_SPLASHDAMAGE_RADIUS = 0;

    public transient List<Projectile> LivingProjectiles;

    private transient SpellAnimationManager spellAnimationManager;

    public BasicSpell(int IconTextureID, float SpellIconWidth, float SpellIconHeight, int CastTextureID, float SpellCastWidth, float SpellCastHeight, String RegistryName, float SpellCooldownInSeconds) {
        super(IconTextureID, SpellIconWidth, SpellIconHeight, CastTextureID, SpellCastWidth, SpellCastHeight, RegistryName, SpellCooldownInSeconds);
        this.MAX_TRAVEL_DIST = BASIC_SPELL_MAX_TRAVEL_DISTANCE;
        init();
    }

    private void init() {
        spellAnimationManager = new SpellAnimationManager();
        LivingProjectiles = new ArrayList<>();
    }

    @Override
    public void onCast(float TargetPosX, float TargetPosY) {
        spellAnimationManager.createSpellAnimations();
        spellAnimationManager.currentAnimation = spellAnimationManager.basicSpellAnimation;
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

            for (Enemy CurrentEnemy1 : Enemy.Enemies) {
                if (CurrentEnemy1.PosX <= ThisProjectile.PosX && CurrentEnemy1.PosX + CurrentEnemy1.ObjLength >= ThisProjectile.PosX && CurrentEnemy1.PosY <= ThisProjectile.PosY && CurrentEnemy1.PosY + CurrentEnemy1.ObjHeight >= ThisProjectile.PosY) {
                    CurrentEnemy1.damageObject(BASIC_SPELL_DAMAGE);
                    System.out.println("Enemy Hit");
                    for (Enemy CurrentEnemy2 : Enemy.Enemies) {
                        if (CurrentEnemy2.PosX <= ThisProjectile.PosX + BASIC_SPELL_SPLASHDAMAGE_RADIUS && CurrentEnemy1.PosX + CurrentEnemy1.ObjLength >= ThisProjectile.PosX + BASIC_SPELL_SPLASHDAMAGE_RADIUS && CurrentEnemy1.PosY <= ThisProjectile.PosY + BASIC_SPELL_SPLASHDAMAGE_RADIUS && CurrentEnemy1.PosY + CurrentEnemy1.ObjHeight >= ThisProjectile.PosY + BASIC_SPELL_SPLASHDAMAGE_RADIUS) {
                            CurrentEnemy2.damageObject(BASIC_SPELL_SPLASH_DAMAGE);
                            System.out.println("Enemy Splash Damage Hit");
                        }
                    }
                    LivingProjectiles.remove(i);
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                if (Math.sqrt((ThisProjectile.PosX - ThisProjectile.StartPosX) * (ThisProjectile.PosX - ThisProjectile.StartPosX) + (ThisProjectile.PosY - ThisProjectile.StartPosY) * (ThisProjectile.PosY - ThisProjectile.StartPosY)) >= BASIC_SPELL_MAX_TRAVEL_DISTANCE) {
                    LivingProjectiles.remove(i);
                } else {
                    spellAnimationManager.currentAnimation.renderAnimation(ThisProjectile.PosX - ThisProjectile.ObjLength / 2, ThisProjectile.PosY - ThisProjectile.ObjHeight / 2, ThisProjectile.ObjLength, ThisProjectile.ObjHeight, false, renderer);
                }
            }
        }
    }
}