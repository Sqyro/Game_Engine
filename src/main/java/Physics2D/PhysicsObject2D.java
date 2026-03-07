package Physics2D;

import java.io.Serializable;

public class PhysicsObject2D implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private float PosX = 0;
    private float PosY = 0;
    
    private int ObjLength;
    private int ObjHeight;
    
    public transient int TextureID;
    
    public PhysicsObject2D(float PosX, float PosY, int ObjLength, int ObjHeight, int TextureID) {
        this.PosX = PosX;
        this.PosY = PosY;
        this.ObjLength = ObjLength;
        this.ObjHeight = ObjHeight;
        this.TextureID = TextureID;
    }
    
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
   
   public int getObjLength() {
       return ObjLength;
   }
   
   public void setObjLength(int newObjLength) {
       ObjLength = newObjLength;
   }
   
   public int getObjHeight() {
       return ObjHeight;
   }
   
   public void setObjHeight(int newObjHeight) {
       ObjHeight = newObjHeight;
   }
   
   public int getTextureID() {
       return TextureID;
   }
   
   public void setTextureID(int newTextureID) {
       this.TextureID = newTextureID;
   }
}
