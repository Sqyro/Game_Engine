package Physics2D;

public abstract class LivingObject extends PhysicsObject2D { // Klasse für Alle Objekte die Physik haben und sich bewegen können
    
    //Variablen (transient wegen Speichern)
    //Geschwindigkeit von dem Objekt
    public transient float Velocity = 0;
    
    //Ausrichtung von dem Objekt
    public transient int[] Direction = {0, 0};
    
    boolean PlayerFlippedX = false;
    
    public LivingObject(float PosX, float PosY, float ObjLength, float ObjHeight, int TextureID, float Velocity, int[] Direction) { //Constructor
        super(PosX, PosY, ObjLength, ObjHeight, TextureID); //Passed alles außer Velocity und Direction an PhysicsObject2D weiter
        //Variablen pro Objekt setzen
        this.Velocity = Velocity;
        this.Direction = Direction;
    }
    
    
    //Methoden um an die Variablen von jedem Objekt ranzukommen
    public float getVelocity() {
        return Velocity;
    }
   
    public void setVelocity(float newVelocity) {
        Velocity = newVelocity;
    }
   
    public int[] getDirection() {
        return Direction;
    }
   
    public int getDirectionX() {
        return Direction[0];
    }
   
    public int getDirectionY() {
        return Direction[1];
    }
   
    public void setDirection(int[] newDirection) {
        Direction = newDirection;
    }
   
    public void setDirectionX(int newX) {
        Direction[0] = newX;
    }
   
    public void setDirectionY(int newY) {
        Direction[1] = newY;
    }
    
    public boolean isFLipped() {
        switch (this.getDirectionX()) {
            case -1:
                PlayerFlippedX = false;
                break;
            case 1:
                PlayerFlippedX = true;
                break;
        }
        
        return PlayerFlippedX;
    }
}
