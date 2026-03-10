package Rendering;

import Player.Player;

public class Camera {
    public static float PosX; //Position der Kamera, kann Statisch sein, weil wir nur eine Kamera haben
    public static float PosY;
    
    public Camera() { //leerer Constructor, haben nur eine Kamera, der ist nur hier For the Love of the Game
        
    }
    
    public static void UpdateCamera(Player ThisPlayer) {
        PosX = ThisPlayer.getPosX(); // Kamera verfolgt den Player
        PosY = ThisPlayer.getPosY();
    }
}
