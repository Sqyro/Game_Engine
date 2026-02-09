package GUI;

import Player.Player;

public class Camera {
    public static int PosX = Player.getPosX();
    public static int PosY = Player.getPosY();
    
    public static void UpdateCamera() {
        PosX = Player.getPosX(); // Kamera verfolgt jetzt den Player nicht andersrum
        PosY = Player.getPosY();
    }
}
