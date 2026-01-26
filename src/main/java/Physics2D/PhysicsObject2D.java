package Physics2D;

public class PhysicsObject2D {
    public static int PosX = 0;
    public static int PosY = 0;
    
    public static float Velocity = 0;
    
    public static int[] Direction = {0, 0};
    
    
   public static int getPosX() {
       return PosX;
   }
   
   public static void setPosX(int newPosX) {
       PosX = newPosX;
   }
   
   public static int getPosY() {
       return PosY;
   }
   
   public static void setPosY(int newPosY) {
       PosY = newPosY;
   }
   
   public static float getVelocity() {
       return Velocity;
   }
   
   public static void setVelocity(float newVelocity) {
       Velocity = newVelocity;
   }
   
   public static int[] getDirection() {
       return Direction;
   }
   
   public static void setDirection(int newX, int newY) {
       Direction[0] = newX;
       Direction[1] = newY;
   }
}
