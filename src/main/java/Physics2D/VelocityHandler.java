package Physics2D;

public class VelocityHandler {
    public static void applyVelocity(PhysicsObject2D Object) {
        float Velocity = Object.getVelocity();
        
        Object.setPosX(Object.getPosX() + (int)Velocity * Object.getDirection()[0]);
        Object.setPosY(Object.getPosY() + (int)Velocity * Object.getDirection()[1]);
    }
}
