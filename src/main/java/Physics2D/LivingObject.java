package Physics2D;

public class LivingObject extends PhysicsObject2D {
    
    public transient float Velocity = 0;
    
    public transient int[] Direction = {0, 0};
    
    public LivingObject(float PosX, float PosY, int ObjLength, int ObjHeight, int TextureID, float Velocity, int[] Direction) {
        super(PosX, PosY, ObjLength, ObjHeight, TextureID);
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
}
