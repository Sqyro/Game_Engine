package Player;

public class InputHandler {
    public static int MovementSpeed = 2;
    
    
    public static void Move() {
        //Camera.PosY = Camera.PosY + MovementSpeed * Direction; //Ich ersetze das Später mit dem Velocity Handler, mache das nur weil es erstmal einfacher ist
        Player.setVelocity(MovementSpeed);
    }
}
