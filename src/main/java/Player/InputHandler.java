package Player;

import GUI.Camera;

public class InputHandler {
    public static int MovementSpeed = 50;
    
    public static void MoveY(int Direction) { //Direction kann 1 oder -1 sein und gibt die Richtung der Bewegung an
        Camera.PosY = Camera.PosY + MovementSpeed * Direction; //Ich ersetze das Später mit dem Velocity Handler, mache das nur weil es erstmal einfacher ist
    }
    
    public static void MoveX(int Direction) {
        Camera.PosX = Camera.PosX + MovementSpeed * Direction;
    }
}
