package Physics2D;

import java.io.Serializable;

public abstract class PhysicsObject2D implements Serializable { // Klasse für alle Objekte die Physik haben, Serialization für Sava Data vom Spieler
    private static final long serialVersionUID = 1L; //Version von Serialisation
    
    //Variablen deklarieren
    
    //Position
    private float PosX = 0;
    private float PosY = 0;
    
    //Größe
    //Transient, weil größe nicht gespeichert werden muss
    private transient float ObjLength;
    private transient float ObjHeight;
    
    //TexturID für OpenGL
    public transient int TextureID; //Transient, weil Textur nicht gespeichert werden muss
    
    public PhysicsObject2D(float PosX, float PosY, float ObjLength, float ObjHeight, int TextureID) { //Constructor
        //Setzt die ganzen Variablen pro Objekt
        this.PosX = PosX;
        this.PosY = PosY;
        this.ObjLength = ObjLength;
        this.ObjHeight = ObjHeight;
        this.TextureID = TextureID;
    }
    
    //Methoden um an die Variablen von jedem Objekt zu kommen
    public float getPosX() {
        return PosX;
    }
   
    public void setPosX(float newPosX) {
        PosX = newPosX;
    }
   
    public float getPosY() {
        return PosY;
    }
   
    public void setPosY(float newPosY) {
        PosY = newPosY;
    }
   
    public float getObjLength() {
        return ObjLength;
    }
   
    public void setObjLength(float newObjLength) {
        ObjLength = newObjLength;
    }
   
    public float getObjHeight() {
        return ObjHeight;
    }
   
    public void setObjHeight(float newObjHeight) {
        ObjHeight = newObjHeight;
    }
   
    public int getTextureID() {
        return TextureID;
    }
   
    public void setTextureID(int newTextureID) {
        this.TextureID = newTextureID;
    }
}
