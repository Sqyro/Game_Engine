package Physics2D;

import java.awt.Image;
import java.io.Serializable;

public class PhysicsObject2D implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public float PosX = 0;
    public float PosY = 0;
    
    public transient float Velocity = 0;
    
    public transient int[] Direction = {0, 0};
    
    public transient Image img;
    
    public PhysicsObject2D(float PosX, float PosY, float Velocity, int[] Direction, Image img) {
        this.PosX = PosX;
        this.PosY = PosY;
        this.Velocity = Velocity;
        this.Direction = Direction;
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
       return Direction[1];
   }
   
   public int getDirectionY() {
       return Direction[1];
   }
   
   public void setDirection(int newX, int newY) {
       Direction[0] = newX;
       Direction[1] = newY;
   }
   
   public void setDirectionX(int newX) {
       Direction[0] = newX;
   }
   
   public void setDirectionY(int newY) {
       Direction[1] = newY;
   }
   
   public Image getImage() {
       return img;
   }
   
   public void setImage(Image newimg) {
       img = newimg;
   }
}
