package Physics2D;

import java.io.Serializable;

public abstract class PhysicsObject2D implements Serializable { // Klasse für alle Objekte die Physik haben
    private static final long serialVersionUID = 1L;

    //Variablen deklarieren
    
    //Größe
    //Transient, weil größe nicht gespeichert werden muss
    public transient float ObjLength;
    public transient float ObjHeight;
    
    //TexturID für OpenGL
    public transient int TextureID; //Transient, weil Textur nicht gespeichert werden muss
    
    //Hitbox speichern
    public transient CircleCollider Hitbox;
    
    public PhysicsObject2D(float ObjLength, float ObjHeight, int TextureID, CircleCollider Hitbox) { //Constructor
        //Setzt die ganzen Variablen pro Objekt
        this.ObjLength = ObjLength;
        this.ObjHeight = ObjHeight;
        this.TextureID = TextureID;
        this.Hitbox = Hitbox;
    }
    
    //Methoden um an die Variablen von jedem Objekt zu kommen
   
    public void setObjLength(float newObjLength) {
        ObjLength = newObjLength;
    }
   
    public void setObjHeight(float newObjHeight) {
        ObjHeight = newObjHeight;
    }
   
    public void setTextureID(int newTextureID) {
        this.TextureID = newTextureID;
    }
    
    public abstract float getPosX ();
    public abstract float getPosY ();
}
