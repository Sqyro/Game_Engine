package Player;

import java.awt.Image;
import java.awt.Toolkit;

public class PlayerAnimationHandler {
    private static Image PlayerWalkingOrig = Toolkit.getDefaultToolkit().getImage("src/main/resources/assets/animations/player/walking.gif");
    private static Image PlayerWalking = PlayerWalkingOrig.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    
    public static void PlayWalkingAnimation () {
        if(InputManager.Player.getVelocity() > 0) {
            InputManager.Player.setImage(PlayerWalking);
        }
    }
    public static void StopAnimation () {
        if(InputManager.Player.getVelocity() == 0) {
            InputManager.Player.setImage(InputManager.PlayerImg);
        }
    }
}
