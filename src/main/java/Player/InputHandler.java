package Player;

import Sounds.SoundHandler;

public class InputHandler {
    public static int MovementSpeed = 2;
    
    
    public static void Move(Player Player) {
        //Camera.PosY = Camera.PosY + MovementSpeed * Direction; //Ich ersetze das Später mit dem Velocity Handler, mache das nur weil es erstmal einfacher ist
        Player.setVelocity(MovementSpeed);
        SoundHandler.playSound("/normalerSound.wav", 2);
        Save.Save.SaveData();
    }
    
    public static void Stop(Player Player) {
        Player.setVelocity(0);
    }
}
