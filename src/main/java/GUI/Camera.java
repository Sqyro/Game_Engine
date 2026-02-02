package GUI;

import Player.Player;

public class Camera {
    public static int PosX = 0;
    public static int PosY = 0;
    
    public static void UpdateCamera() {
        PosX = Player.getPosX(); // Kamera verfolgt jetzt den Player nicht andersrum
        PosY = Player.getPosY();
    }
}
