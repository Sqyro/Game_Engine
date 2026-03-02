package GUI;

import Player.Player;

public class Camera {
    public static float PosX;
    public static float PosY;
    
    public Camera() {
        
    }
    
    public static void UpdateCamera(Player ThisPlayer) {
        PosX = ThisPlayer.getPosX(); // Kamera verfolgt jetzt den Player nicht andersrum
        PosY = ThisPlayer.getPosY();
    }
}
