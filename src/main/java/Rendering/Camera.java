package Rendering;

import Player.Player;

public class Camera {
    public static float PosX; //Position der Kamera, kann Statisch sein, weil wir nur eine Kamera haben
    public static float PosY;
    
    public Camera() { //leerer Constructor, haben nur eine Kamera, der ist nur hier For the Love of the Game
        
    }
    
    public static void UpdateCamera(Player ThisPlayer) {
        PosX = -(ThisPlayer.PosX - Frame.ScreenWidth / 2); // Kamera verfolgt den Player und wird auf dem Bildschirm Zentriert
        PosY = -(ThisPlayer.PosY - Frame.ScreenHeight / 2);
    }

    public static void resetCamera() {
        PosX = 0;
        PosY = 0;
    }
}
