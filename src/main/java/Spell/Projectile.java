package Spell;

import Physics2D.CircleCollider;
import Physics2D.LivingObject;

public class Projectile extends LivingObject {
    public float StartPosX;
    public float StartPosY;

    public Projectile(float PosX, float PosY, float ObjLength, float ObjHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox) {
        super(PosX, PosY, ObjLength, ObjHeight, TextureID, Velocity, Direction, Hitbox, 0);
        this.StartPosX = PosX;
        this.StartPosY = PosY;
    }
}
