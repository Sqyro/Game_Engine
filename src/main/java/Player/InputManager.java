package Player;

import GUI.BarElement;
import GUI.ImageManager;
import java.awt.Color;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;
    
    private static final int[] Direction = {0, 0};
    
    public static void init(long window) {

        glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {

            Player player = Player.Player;

            boolean pressed = action != GLFW_RELEASE;

            switch (key) {
                case GLFW_KEY_W:
                    System.out.println("W Pressed");
                    wPressed = pressed;
                    break;
                case GLFW_KEY_S:
                    System.out.println("S Pressed");
                    sPressed = pressed;
                    break;
                case GLFW_KEY_A:
                    System.out.println("A Pressed");
                    aPressed = pressed;
                    break;
                case GLFW_KEY_D:
                    System.out.println("D Pressed");
                    dPressed = pressed;
                    break;
                case GLFW_KEY_E:
                    System.out.println("E Pressed");
                    GUI.GUIHandler.PlaceNewBar(100, 100, 300, 100, GUI.ImageManager.PLAYER, 0, Color.RED);
                    break;
                case GLFW_KEY_Q:
                    System.out.println("Q Pressed");
                    GUI.HudElement Hud = GUI.GUIHandler.HudElements.get(0);
                    BarElement bar = (BarElement) Hud;
                    bar.setBarDamage(bar.getBarDamage() + 10);
                    break;
                case GLFW_KEY_C:
                    System.out.println("C Pressed");
                    Enemy.Enemy.Spawn(40, 40, 50, 50, ImageManager.ENEMY, 0, new int[]{0,0});
                    break;
                case GLFW_KEY_V:
                    System.out.println("V Pressed");
                            Physics2D.PhysicsObject2D loaded = Save.Save.LoadData();
                            if (loaded != null) {
                                Player.Player.setPosX(loaded.getPosX());
                                Player.Player.setPosY(loaded.getPosY());
                            }
                    break;
                case GLFW_KEY_X:
                    System.out.println("X Pressed");
                            Save.Save.SaveData(Player.Player);
                    break;
                    
            }

            // Klasse unten callen, damit der Spieler Richtig ausgerichtet ist nach eingabe einer Taste
            updatePlayerDirection();

            // Wir callen Move jedes mal wenn irgendeiner von den Movement Keys jetzt gerade gedrückt wird und wenn nicht, dann stoppen wir
            if (wPressed || aPressed || sPressed || dPressed) {
                InputHandler.Move(player);
            } else {
                InputHandler.Stop(player);
            }
        });
    }
    
    
    public static void updatePlayerDirection() { // Hab den Direction Skript von oben hier runter gemoved und ihn flüssig gemacht, vorher hat der so gestottert, weil Direction für eine Frame 0 war (nach W-S oder A-D)
        int x = 0;
        int y = 0;
        
        Player player = Player.Player;
        
        if (wPressed) y += 1; 
        if (sPressed) y -= 1;
        if (aPressed) x += 1;
        if (dPressed) x -= 1;
        
        player.setDirectionX(x);
        player.setDirectionY(y);
    }
}
