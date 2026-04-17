package Physics2D;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class LivingObject extends PhysicsObject2D implements Serializable, ISolidCollider { // Klasse für Alle Objekte die Physik haben und sich bewegen können
    private static final long serialVersionUID = 1L;

    //Position
    public float PosX;
    public float PosY;

    //Variablen (transient wegen Speichern)
    //Geschwindigkeit von dem Objekt
    public transient float Velocity;
    
    //Ausrichtung von dem Objekt
    public transient float[] Direction;
    
    boolean ObjectFlippedX = false;
    private float lastDirectionX = 0;
    private float lastDirectionY = 0;

    public float Max_HP;
    public float HP;

    public boolean isAlive;
    
    public LivingObject(float PosX, float PosY, float ObjLength, float ObjHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox, float Max_HP) { //Constructor
        super(ObjLength, ObjHeight, TextureID, Hitbox); //Passed alles außer Velocity und Direction an PhysicsObject2D weiter
        //Variablen pro Objekt setzen
        this.PosX = PosX;
        this.PosY = PosY;
        this.Velocity = Velocity;
        this.Direction = Direction;
        this.Max_HP = Max_HP;
        this.HP = Max_HP;
        this.isAlive = true;
    }
    
    //Methoden um an die Variablen von jedem Objekt ranzukommen

    public void setPosX(float newPosX) {
        PosX = newPosX;
    }

    public void setPosY(float newPosY) {
        PosY = newPosY;
    }

    public void setVelocity(float newVelocity) {
        Velocity = newVelocity;
    }

    public float getDirectionX() {
        return Direction[0];
    }
   
    public float getDirectionY() {
        return Direction[1];
    }
   
    public void setDirection(float[] newDirection) {
        Direction = newDirection;
    }
   
    public void setDirectionX(float newX) {
        Direction[0] = newX;
    }
   
    public void setDirectionY(float newY) {
        Direction[1] = newY;
    }
    
    public float getLastDirectionY() {
        return lastDirectionY;
    }

    public void setLastDirectionY(float newDirectionY) {
        lastDirectionY = newDirectionY;
    }

    public float getLastDirectionX() {
        return lastDirectionX;
    }

    public void setLastDirectionX(float newDirectionX) {
        lastDirectionX = newDirectionX;
    }
    
    public boolean isFLipped() {
        if (this.getDirectionX() >= -1 && this.getDirectionX() < 0) {
            ObjectFlippedX = true;
        }
        else if (this.getDirectionX() <= 1 && this.getDirectionX() > 0) {
            ObjectFlippedX = false;
        }
        return ObjectFlippedX;
    }

    public void setMaxHp(float newMaxHP) {
        Max_HP = newMaxHP;
    }

    public void setHp(float newHP) {
        HP = newHP;
    }

    public void damageObject(float Damage) {
        HP -= Damage;
    }
    
    @Override 
    public float getPosX () {
        return PosX;
    }
    
    @Override 
    public float getPosY () {
        return PosY;
    }
    
    @Override
    public void Collide (LivingObject Object, ArrayList List) {
        PhysicsObject2D currentObject;
        for (int i = 0; i < List.size(); i++) {
            currentObject = (PhysicsObject2D) List.get(i);
            if (((Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX) - (currentObject.getPosX() + currentObject.ObjLength / 2 + currentObject.Hitbox.OffsetX)) * ((Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX) - (currentObject.getPosX() + currentObject.ObjLength / 2 + currentObject.Hitbox.OffsetX)) +
                ((Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY) - (currentObject.getPosY() + currentObject.ObjHeight / 2 + currentObject.Hitbox.OffsetY)) * ((Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY) - (currentObject.getPosY() + currentObject.ObjHeight / 2 + currentObject.Hitbox.OffsetY)) <
                (currentObject.Hitbox.Radius + Object.Hitbox.Radius) * (currentObject.Hitbox.Radius + Object.Hitbox.Radius) == true) {
                
            }
        } 
    }
}
