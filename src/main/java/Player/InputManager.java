package Player;

import GUI.*;
import GUI.Screens.InventoryScreen;
import GUI.Screens.PauseScreen;
import Rendering.BarElement;
import Rendering.Camera;
import Rendering.ImageManager;
import Shader.LightEmitters.PointLight;
import Shader.LightManager;
import Rendering.Frame;
import Scenes.SceneManager;
import Scenes.GameScene;
import Physics2D.CircleCollider;

import java.awt.Color;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
    //Variablen Deklarieren
    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;

    private static final float[] Direction = {0, 0};
    
    //Eine Methode die ein Event Benutzt um zu hören ob vom Keyboard Inputs gemacht wurden
    public static void ListenforGameKeys(long Window) {
        glfwSetKeyCallback(Window, (win, key, scancode, action, mods) -> { //Event
                //Spieler Objekt aus der Spieler Klasse. Ich weiß die Namen von den Methoden und klassen könnten besser sein (Nicht alle Player)
                Player player = Player.Player;
            
                boolean Pressed = action != GLFW_RELEASE; //ist was gedrückt worden?
            
                switch (key) {
                    case GLFW_KEY_W: // W wurde gedrückt
                        if(GameScene.GameRunning && !player.isDodging && player.isAlive) {
                            System.out.println("W Pressed"); //Nachricht für den Debug
                            wPressed = Pressed; //Wurde gerückt, also ja es wurde was gerückt hier rein schreiben für später//Schau nicht weiter (Wenn das nicht hier ist, dann wartet er bis ein Key gedrückt wurde und führt dann alles aus)
                        }
                        break;
                    case GLFW_KEY_S:
                        if(GameScene.GameRunning && !player.isDodging && player.isAlive) {
                            System.out.println("S Pressed");
                            sPressed = Pressed;
                        }
                        break;
                    case GLFW_KEY_A:
                        if(GameScene.GameRunning && !player.isDodging && player.isAlive) {
                            System.out.println("A Pressed");
                            aPressed = Pressed;
                        }
                        break;
                    case GLFW_KEY_D:
                        if(GameScene.GameRunning && !player.isDodging && player.isAlive) {
                            System.out.println("D Pressed");
                            dPressed = Pressed;
                        }
                        break;
                    case GLFW_KEY_E:
                        if(action == GLFW_PRESS) { // Nur ausführen wenn der Key gedrückt wird, sonst wird das hier beim loslassen nochmal ausgeführt und der Screen schließt sich
                            System.out.println("E Pressed");
                            if(GUIManager.isScreenOpen()) { //Wenn der Bildschirm schon offen ist
                                if(GUIManager.currentScreen instanceof InventoryScreen) { //Wenn der Momentane Screen ein Inventar ist
                                    InventoryScreen Inventory = (InventoryScreen) GUIManager.currentScreen; //Screen holen
                                    Inventory.returnHeldItem(); //Die Methode callen, um das festgeahltene Item in seinen vorherigen Slot zu legen
                                    GUIManager.closeScreen(); //Bildschirm schließen
                                }
                            } else { //Wenn keiner offen ist, dann machen wir einen neuen aus
                                if(GUIManager.currentScreen == null) {
                                    GUIManager.openScreen(new InventoryScreen(48, 2, 2, 13, 5, 625 * Frame.NormalizedPixelWidth, 502 * Frame.NormalizedPixelHeight));
                                }
                            }
                        }
                        break;
                    case GLFW_KEY_ESCAPE:
                        if(action == GLFW_PRESS) {
                            System.out.println("Escape Pressed");
                            if(GUIManager.isScreenOpen()) {
                                GUIManager.closeScreen();
                            } else {
                                if(GUIManager.currentScreen == null) {
                                    GUIManager.openScreen(new PauseScreen());
                                }
                            }
                        }
                        break;
                    case GLFW_KEY_SPACE:
                        if(action == GLFW_PRESS && !player.isDodging && player.isAlive) {
                            System.out.println("Space Pressed");
                            wPressed = false;
                            aPressed = false;
                            sPressed = false;
                            dPressed = false;
                            player.isDodging = true;
                        }
                        break;
                    case GLFW_KEY_C:
                        if(GameScene.GameRunning) {
                            System.out.println("C Pressed");
                            Enemy.Enemy.Spawn((float)Mouse.PosX - Camera.PosX, (float)Mouse.PosY - Camera.PosY, 50, 50, ImageManager.ENEMY, 0, Direction, new CircleCollider(22, 0, 0), 5); //Spawnt einen Gegner bei 40, 40 Global mit der Größe 50, 50
                        }
                        break;
                    case GLFW_KEY_P:
                        if(GameScene.GameRunning) {
                            System.out.println("P Pressed");
                            Map.Wall.Spawn(200, 200, 50, 50, ImageManager.ENEMY, new CircleCollider(22, 0, 0)); //Spawnt einen Gegner bei 40, 40 Global mit der Größe 50, 50
                        }
                        break;
                    case GLFW_KEY_I:
                        if(GameScene.GameRunning) {
                            System.out.println("I Pressed");
                            PointLight pointLight = new PointLight((float)Mouse.PosX - Camera.PosX, (float)Mouse.PosY - Camera.PosY, 1f, 1f, 1f, 300f); //Erstellt ein neues Point Light bei der MausPos auf der Kamera mit den RGB Werten von 1, 1, 1 und der Reichweite von 300
                            LightManager.addLight(pointLight); //Frügt das Light in die Liste für Lights hinzu
                        }
                        break;
                    case GLFW_KEY_Z:
                        if(GameScene.GameRunning) {
                            System.out.println("Y Pressed");
                            GUIText randomText = new GUIText("Skibidi Tripple T Sigma :3", 80, 80, 80, 50, ImageManager.GAMEFONT, Color.RED); //Erstellt ein neues Text Element
                            Frame.GameScene.showTextForSeconds(randomText, 2); //Packt das Text Element in den To Be Displayed Text
                        }
                        break;
                }
            
                if(GameScene.GameRunning) {
                    // Wir callen Move jedes mal wenn irgendeiner von den Movement Keys jetzt gerade gedrückt wird und wenn nicht, dann stoppen wir
                    if (wPressed || aPressed || sPressed || dPressed) {
                        InputHandler.Move(player);
                    } else {
                        InputHandler.Stop(player);
                    }
                }
        });
        
        //Mouse Event, für Mouse related Dinge
        glfwSetMouseButtonCallback(Window, (win, button, action, mods) -> {
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) { //Wenn der Linke Mouse Button gedrückt wird
                    if(GUIManager.currentScreen instanceof InventoryScreen) { //Wenn der Momentane Bildschirm nen Inventar ist, dann die Handle Click Methode ausführen, um zu schauen ob wir mit nem Slot interagieren können
                        InventoryScreen Inventory = (InventoryScreen) GUIManager.currentScreen; //Inventar holen
                        Inventory.handleClick(Mouse.PosX, Mouse.PosY); //handle Click ausführen mit der Maus Position
                    }

                    SceneManager.ActiveScene.handleClick(Window, Mouse.PosX, Mouse.PosY);

                    //Position von der Maus ausgeben
                    System.out.println("Mouse clicked at: " + Mouse.PosX + ", " + Mouse.PosY);
                }
        });
    }
    
    public static void ListenforSettingsKeys(long Window) {
        glfwSetKeyCallback(Window, (win, key, scancode, action, mods) -> { //Event
                switch (key) {
                    case GLFW_KEY_ESCAPE:
                        if(action == GLFW_PRESS) {
                            System.out.println("Escape Pressed");
                            SceneManager.LoadScene(SceneManager.PreviousScene, Window);
                            if (SceneManager.ActiveScene instanceof GameScene) {
                                GUIManager.openScreen(new PauseScreen());
                            }
                        }
                        break;
                }
        });

        //Mouse Event, für Mouse related Dinge
        glfwSetMouseButtonCallback(Window, (win, button, action, mods) -> {
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) { //Wenn der Linke Mouse Button gedrückt wird
                    //Variablen für die Position von der Maus erstellen
                    double[] MousePosX = new double[1];
                    double[] MousePosY = new double[1];

                    //Position von der Maus holen und in die Variablen schreiben
                    glfwGetCursorPos(Window, MousePosX, MousePosY);

                    //Position von der Maus ausgeben
                    System.out.println("Mouse clicked at: " + MousePosX[0] + ", " + MousePosY[0]);
                }
        });
    }

    public static void ListenforLoadGameKeys(long Window) {
        glfwSetKeyCallback(Window, (win, key, scancode, action, mods) -> { //Event
            switch (key) {
                case GLFW_KEY_ESCAPE:
                    if(action == GLFW_PRESS) {
                        System.out.println("Escape Pressed");
                        SceneManager.LoadScene(SceneManager.PreviousScene, Window);
                    }
                    break;
            }
        });

        //Mouse Event, für Mouse related Dinge
        glfwSetMouseButtonCallback(Window, (win, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) { //Wenn der Linke Mouse Button gedrückt wird
                //Position von der Maus ausgeben
                System.out.println("Mouse clicked at: " + Mouse.PosX + ", " + Mouse.PosY);

                SceneManager.ActiveScene.handleClick(Window, Mouse.PosX, Mouse.PosY);
            }
        });
    }

    public static void ListenforMainMenuKeys(long Window) {
        glfwSetKeyCallback(Window, (win, key, scancode, action, mods) -> { //Event
            switch (key) {
                case GLFW_KEY_ESCAPE:
                    if(action == GLFW_PRESS) {
                        System.out.println("Escape Pressed");
                    }
                    break;
            }
        });

        //Mouse Event, für Mouse related Dinge
        glfwSetMouseButtonCallback(Window, (win, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) { //Wenn der Linke Mouse Button gedrückt wird
                //Position von der Maus ausgeben
                System.out.println("Mouse clicked at: " + Mouse.PosX + ", " + Mouse.PosY);

                SceneManager.ActiveScene.handleClick(Window, Mouse.PosX, Mouse.PosY);
            }
        });
    }
    
    public static void updatePlayerDirection() { // Hab den Direction Skript von oben hier runter gemoved und ihn flüssig gemacht, vorher hat der so gestottert, weil Direction für eine Frame 0 war (nach W-S oder A-D)
        //Immer vorher auf 0 setzen
        float DirX = 0;
        float DirY = 0;
        
        Player player = Player.Player;
        
        //Wenn die Keys gedrückt wurden dann addieren/Subtrahieren (nicht setzen, sonst buggt das wenn man zwei Keys gleichzeitig drückt)
        if (aPressed) DirX -= 1;
        if (dPressed) DirX += 1;
        if (wPressed) DirY -= 1;
        if (sPressed) DirY += 1;
        
        //Richtung setzen
        player.setDirectionX(DirX);
        player.setDirectionY(DirY);

        if (DirX != 0) {
            player.setLastDirectionX(DirX);
        }

        if(DirY != 0){ //Letzte Y Richtung speichern
            player.setLastDirectionY(DirY);
        }
        
        if (DirY == 0 && DirX != 0) { //Falls wir uns nochmal auf der X Achse bewegt haben zurücksetzen
            player.setLastDirectionY(0);
        } else if (DirX == 0 && DirY != 0) {
            player.setLastDirectionX(0);
        }
    }
}
