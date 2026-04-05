package Physics2D;

import java.io.Serializable;

public abstract class PhysicsObject2D implements Serializable { // Klasse für alle Objekte die Physik haben, Serialization für Sava Data vom Spieler
    private static final long serialVersionUID = 1L; //Version von Serialisation
    
    //Variablen deklarieren
    
    //Position
    public float PosX;
    public float PosY;
    
    //Größe
    //Transient, weil größe nicht gespeichert werden muss
    public transient float ObjLength;
    public transient float ObjHeight;
    
    //TexturID für OpenGL
    public transient int TextureID; //Transient, weil Textur nicht gespeichert werden muss
    
    //Hitbox speichern
    public transient Hitbox Hitbox;
    
    public PhysicsObject2D(float PosX, float PosY, float ObjLength, float ObjHeight, int TextureID, Hitbox Hitbox) { //Constructor
        //Setzt die ganzen Variablen pro Objekt
        this.PosX = PosX;
        this.PosY = PosY;
        this.ObjLength = ObjLength;
        this.ObjHeight = ObjHeight;
        this.TextureID = TextureID;
        this.Hitbox = Hitbox;
    }
    
    //Methoden um an die Variablen von jedem Objekt zu kommen

    public void setPosX(float newPosX) {
        PosX = newPosX;
    }
   
    public void setPosY(float newPosY) {
        PosY = newPosY;
    }
   
    public void setObjLength(float newObjLength) {
        ObjLength = newObjLength;
    }
   
    public void setObjHeight(float newObjHeight) {
        ObjHeight = newObjHeight;
    }
   
    public void setTextureID(int newTextureID) {
        this.TextureID = newTextureID;
    }
}
