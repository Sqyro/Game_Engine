package Player;

import Sounds.SoundHandler;

public class InputHandler {
    public static int MovementSpeed = 670; // Bewegungsgeschwindigkeit vom Spieler
    
    //Methode wenn der Spieler bewegt werden soll
    public static void Move(Player Player) {
        //Camera.PosY = Camera.PosY + MovementSpeed * Direction; //Ich ersetze das Später mit dem Velocity Handler, mache das nur weil es erstmal einfacher ist
        Player.setVelocity(MovementSpeed); //Setzt die Geschwindigkeit vom Spieler auf die Bewegungsgeschwingigkeit
        SoundHandler.playSound("/normalerSound.wav", 7); //Spielt einen sehr normalenSound für 7 Sekunden ab, wenn sich der Spieler bewegt
    }
    
    //Methode um den Spieler anzuhalten
    public static void Stop(Player Player) {
        Player.setVelocity(0); //Setzt seine Geschwindigkeit auf 0
    }
}
