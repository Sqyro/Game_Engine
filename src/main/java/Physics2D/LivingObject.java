package Physics2D;

import java.awt.Image;

public class LivingObject extends PhysicsObject2D {
    
    public transient float Velocity = 0;
    
    public transient int[] Direction = {0, 0};
    
    public LivingObject(float PosX, float PosY, Image img, float Velocity, int[] Direction) {
        super(PosX, PosY, img);
        this.Velocity = Velocity;
        this.Direction = Direction;
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
}
