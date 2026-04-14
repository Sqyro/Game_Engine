package Physics2D;

import java.io.Serializable;

public abstract class LivingObject extends PhysicsObject2D implements Serializable { // Klasse für Alle Objekte die Physik haben und sich bewegen können
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
    
    public LivingObject(float PosX, float PosY, float ObjLength, float ObjHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox) { //Constructor
        super(ObjLength, ObjHeight, TextureID, Hitbox); //Passed alles außer Velocity und Direction an PhysicsObject2D weiter
        //Variablen pro Objekt setzen
        this.PosX = PosX;
        this.PosY = PosY;
        this.Velocity = Velocity;
        this.Direction = Direction;
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
}
