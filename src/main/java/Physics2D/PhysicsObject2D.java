package Physics2D;

public class PhysicsObject2D {
    public int PosX = 0;
    public int PosY = 0;
    
    public float Velocity = 0;
    
    public int[] Direction = {0, 0};
    
    public PhysicsObject2D(int PosX, int PosY, float Velocity, int[] Direction) {
        this.PosX = PosX;
        this.PosY = PosY;
        this.Velocity = Velocity;
        this.Direction = Direction;
    }
    
   public int getPosX() {
       return PosX;
   }
   
   public void setPosX(int newPosX) {
       PosX = newPosX;
   }
   
   public int getPosY() {
       return PosY;
   }
   
   public void setPosY(int newPosY) {
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
