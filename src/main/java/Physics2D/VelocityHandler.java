package Physics2D;

public class VelocityHandler {
    public static void calculatePosition(PhysicsObject2D Object, float deltaTime) {
        float Velocity = Object.getVelocity();
        
        //System.out.println("Alte Position X: " + Object.getPosX() + " Alte Position Y: " + Object.getPosY());
        
        float DirectionX = Object.getDirection()[0];
        float DirectionY = Object.getDirection()[1];

        float length = (float)Math.sqrt(DirectionX * DirectionX + DirectionY * DirectionY); //Satz des Pytagoras, ich normalisiere den Vektor wenn zwei Keys gleichzeitig gedrückt werden, weil sich sonst die Geschwindigkeit addiert

        if (length != 0) {
            DirectionX /= length;
            DirectionY /= length;
        }
        
        Object.setPosX(Object.getPosX() + Velocity * deltaTime * DirectionX);
        Object.setPosY(Object.getPosY() + Velocity * deltaTime * DirectionY);
        
        //System.out.println("Neue Position X: " + Object.getPosX() + " Neue Position Y: " + Object.getPosY());
    }
}
