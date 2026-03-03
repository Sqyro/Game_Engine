package Physics2D;

import java.awt.Image;
import java.io.Serializable;

public class PhysicsObject2D implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public float PosX = 0;
    public float PosY = 0;
    
    public transient Image img;
    
    public PhysicsObject2D(float PosX, float PosY, Image img) {
        this.PosX = PosX;
        this.PosY = PosY;
        this.img = img;
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
   
   public Image getImage() {
       return img;
   }
   
   public void setImage(Image newimg) {
       img = newimg;
   }
}
