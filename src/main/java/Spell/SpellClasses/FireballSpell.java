package Spell.SpellClasses;

import Enemy.Enemy;
import Physics2D.CircleCollider;
import Physics2D.VelocityHandler;
import Player.Player;
import Rendering.ImageHandler;
import Shader.LightEmitters.PointLight;
import Shader.LightManager;
import Spell.Spell;
import Spell.Projectile;
import Spell.SpellAnimationManager;

import java.util.ArrayList;
import java.util.List;

public class FireballSpell extends Spell {
    public static float FIREBALL_SPELL_DAMAGE = 100;
    public static float FIREBALL_SPELL_SPLASH_DAMAGE = 50;
    public static float FIREBALL_SPELL_SPEED = 1300;
    public static float FIREBALL_SPELL_MAX_TRAVEL_DISTANCE = 600;
    public static float FIREBALL_SPELL_SPLASHDAMAGE_RADIUS = 30;

    public transient List<Projectile> LivingProjectiles;

    private transient SpellAnimationManager spellAnimationManager;

    private PointLight FIREBALL_SPELL_POINTLIGHT;
    private static final float FIREBALL_LIGHT_RANGE = 100;

    public FireballSpell(int IconTextureID, float SpellIconWidth, float SpellIconHeight, int CastTextureID, float SpellCastWidth, float SpellCastHeight, String RegistryName, float SpellCooldownInSeconds) {
        super(IconTextureID, SpellIconWidth, SpellIconHeight, CastTextureID, SpellCastWidth, SpellCastHeight, RegistryName, SpellCooldownInSeconds);
        init();
    }

    private void init() {
        spellAnimationManager = new SpellAnimationManager();
        LivingProjectiles = new ArrayList<>();
        FIREBALL_SPELL_POINTLIGHT = new PointLight(0, 0, 0, 0, 0, 0);
    }

    @Override
    public void onCast(float TargetPosX, float TargetPosY) {
        spellAnimationManager.createSpellAnimations();
        spellAnimationManager.currentAnimation = spellAnimationManager.fireballSpellAnimation;
        float[] ShootDirection = {TargetPosX - Player.Player.PosX, TargetPosY - Player.Player.PosY};
        LivingProjectiles.add(new Projectile(Player.Player.PosX, Player.Player.PosY, SpellCastWidth, SpellCastHeight, CastTextureID, FIREBALL_SPELL_SPEED, ShootDirection, new CircleCollider(SpellCastWidth/2, 0, 0)));
        System.out.println("Fireball Spell casted");
    }

    @Override
    public void onSpellTick(float deltaTime, ImageHandler renderer) {
        passedTime += deltaTime;
        spellAnimationManager.updateSpellAnimation(deltaTime);
        for (int i = LivingProjectiles.size() - 1; i >= 0; i--) {
            Projectile ThisProjectile = LivingProjectiles.get(i);
            VelocityHandler.calculatePosition(ThisProjectile, deltaTime);

            FIREBALL_SPELL_POINTLIGHT.PosX = ThisProjectile.PosX;
            FIREBALL_SPELL_POINTLIGHT.PosY = ThisProjectile.PosY;
            FIREBALL_SPELL_POINTLIGHT.Red = 1f;
            FIREBALL_SPELL_POINTLIGHT.Green = 0.5f;
            FIREBALL_SPELL_POINTLIGHT.Blue = 0.5f;
            FIREBALL_SPELL_POINTLIGHT.Range = FIREBALL_LIGHT_RANGE;


            boolean removed = false;

            for (Enemy CurrentEnemy1 : Enemy.Enemies) {
                if (CurrentEnemy1.PosX <= ThisProjectile.PosX && CurrentEnemy1.PosX + CurrentEnemy1.ObjLength >= ThisProjectile.PosX && CurrentEnemy1.PosY <= ThisProjectile.PosY && CurrentEnemy1.PosY + CurrentEnemy1.ObjHeight >= ThisProjectile.PosY) {
                    CurrentEnemy1.damageObject(FIREBALL_SPELL_DAMAGE);
                    System.out.println("Enemy Hit");
                    for (Enemy CurrentEnemy2 : Enemy.Enemies) {
                        if (CurrentEnemy2.PosX <= ThisProjectile.PosX + FIREBALL_SPELL_SPLASHDAMAGE_RADIUS && CurrentEnemy2.PosX + CurrentEnemy2.ObjLength >= ThisProjectile.PosX - FIREBALL_SPELL_SPLASHDAMAGE_RADIUS && CurrentEnemy2.PosY <= ThisProjectile.PosY + FIREBALL_SPELL_SPLASHDAMAGE_RADIUS && CurrentEnemy2.PosY + CurrentEnemy2.ObjHeight >= ThisProjectile.PosY - FIREBALL_SPELL_SPLASHDAMAGE_RADIUS) {
                            CurrentEnemy2.damageObject(FIREBALL_SPELL_SPLASH_DAMAGE);
                            System.out.println("Enemy Splash Damage Hit");
                        }
                    }
                    LivingProjectiles.remove(i);
                    LightManager.removeLight(FIREBALL_SPELL_POINTLIGHT);
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                if (Math.sqrt((ThisProjectile.PosX - ThisProjectile.StartPosX) * (ThisProjectile.PosX - ThisProjectile.StartPosX) + (ThisProjectile.PosY - ThisProjectile.StartPosY) * (ThisProjectile.PosY - ThisProjectile.StartPosY)) >= FIREBALL_SPELL_MAX_TRAVEL_DISTANCE) {
                    LivingProjectiles.remove(i);
                    LightManager.removeLight(FIREBALL_SPELL_POINTLIGHT);
                } else {
                    spellAnimationManager.currentAnimation.renderAnimation(ThisProjectile.PosX - ThisProjectile.ObjLength / 2, ThisProjectile.PosY - ThisProjectile.ObjHeight / 2, ThisProjectile.ObjLength, ThisProjectile.ObjHeight, false, renderer);
                    LightManager.removeLight(FIREBALL_SPELL_POINTLIGHT);
                    LightManager.addLight(FIREBALL_SPELL_POINTLIGHT);
                }
            }
        }
    }
}