package Inputs;

import Player.Player;
import Sounds.SoundManager;

public class InputHandler {
    public static int MovementSpeed = 670; // Bewegungsgeschwindigkeit vom Spieler
    
    private static SoundManager WalkSoundManager = new SoundManager(); //Sound Manager Variable für den Lauf Sound
    
    //Methode wenn der Spieler bewegt werden soll
    public static void Move(Player Player) {

        Player.setVelocity(MovementSpeed); //Setzt die Geschwindigkeit vom Spieler auf die Bewegungsgeschwingigkeit

        WalkSoundManager.PlaySoundsWithDelay("/normalerSound.wav", 7, -20, 7); //Spielt einen sehr normalenSound für 7 Sekunden ab, wenn sich der Spieler bewegt
    }
    
    //Methode um den Spieler anzuhalten
    public static void Stop(Player Player) {
        Player.setVelocity(0); //Setzt seine Geschwindigkeit auf 0
    }
}
