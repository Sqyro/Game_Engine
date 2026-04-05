package Physics2D;

import GameLang.Float.Vector2F;

public abstract class LivingObject extends PhysicsObject2D { // Klasse für Alle Objekte die Physik haben und sich bewegen können
    
    //Variablen (transient wegen Speichern)
    //Geschwindigkeit von dem Objekt
    public transient float Velocity;
    
    //Ausrichtung von dem Objekt
    public transient float[] Direction;
    
    boolean ObjectFlippedX = false;
    private float lastDirectionY = 0;
    
    public LivingObject(float PosX, float PosY, float ObjLength, float ObjHeight, int TextureID, float Velocity, float[] Direction, Hitbox Hitbox) { //Constructor
        super(PosX, PosY, ObjLength, ObjHeight, TextureID, Hitbox); //Passed alles außer Velocity und Direction an PhysicsObject2D weiter
        //Variablen pro Objekt setzen
        this.Velocity = Velocity;
        this.Direction = Direction;
    }
    
    //Methoden um an die Variablen von jedem Objekt ranzukommen
   
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

    public void setLastDirectionY(float dir) {
        lastDirectionY = dir;
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
