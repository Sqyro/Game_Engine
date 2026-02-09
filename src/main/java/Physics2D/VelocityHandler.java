package Physics2D;

public class VelocityHandler {
    public static void calculatePosition(PhysicsObject2D Object) {
        float Velocity = Object.getVelocity();
        
        //System.out.println("Alte Position X: " + Object.getPosX() + " Alte Position Y: " + Object.getPosY());
        
        Object.setPosX(Object.getPosX() + (int)Velocity * Object.getDirection()[0]);
        Object.setPosY(Object.getPosY() + (int)Velocity * Object.getDirection()[1]);
        
        //System.out.println("Neue Position X: " + Object.getPosX() + " Neue Position Y: " + Object.getPosY());
    }
}
