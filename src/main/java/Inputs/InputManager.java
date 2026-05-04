package Inputs;

import Enemy.Enemy;
import GUI.*;
import GUI.Screens.InventoryScreen;
import GUI.Screens.PauseScreen;
import Inputs.Actions.*;
import Physics2D.CircleCollider;
import Rendering.Camera;
import Rendering.Frame;
import Rendering.ImageManager;
import Scenes.SceneManager;
import Scenes.GameScene;
import Shader.LightEmitters.PointLight;
import Shader.LightManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
    //Variablen Deklarieren

    private static final float[] Direction = {0, 0};

    private static WalkAction UpAction = new WalkAction(WalkDirection.UP);
    private static WalkAction DownAction = new WalkAction(WalkDirection.DOWN);
    private static WalkAction LeftAction = new WalkAction(WalkDirection.LEFT);
    private static WalkAction RightAction = new WalkAction(WalkDirection.RIGHT);

    private static DashAction DashAction = new DashAction();

    private static InventoryAction InventoryAction = new InventoryAction();

    private static EscapeAction EscapeAction = new EscapeAction();

    private static List<KeyAction> GameKeys = new ArrayList<>();

    private static final int SPACE_ASCII = 32;
    private static final int A_ASCII = 65;
    private static final int D_ASCII = 68;
    private static final int E_ASCII = 69;
    private static final int S_ASCII = 83;
    private static final int W_ASCII = 87;
    private static final int ESC_ASCII = 256;

    public static void init() {
        for (int i = 0; i < 512; i++) { //Out of Bounds verhindern, Listengröße erstellen
            GameKeys.add(null);
        }
        //Standard Einstellung, wird später aus dem Speicher von Settings gelesen
        GameKeys.set(SPACE_ASCII, DashAction);
        GameKeys.set(A_ASCII, LeftAction);
        GameKeys.set(D_ASCII, RightAction);
        GameKeys.set(E_ASCII, InventoryAction);
        GameKeys.set(S_ASCII, DownAction);
        GameKeys.set(W_ASCII, UpAction);
        GameKeys.set(ESC_ASCII, EscapeAction);
    }

    //Eine Methode die ein Event Benutzt um zu hören ob vom Keyboard Inputs gemacht wurden
    public static void ListenforGameKeys(long Window) {
        glfwSetKeyCallback(Window, (win, key, scancode, action, mods) -> { //Event
            boolean Pressed = action != GLFW_RELEASE; //ist was gedrückt worden?

            if (GameKeys.get(key) != null) {
                if (Pressed) {
                    GameKeys.get(key).onPress();
                    System.out.println(key + " Key Pressed");
                } else {
                    GameKeys.get(key).onRelease();
                }
            }

                switch (key) {
                    case GLFW_KEY_C:
                        if(GameScene.GameRunning) {
                            System.out.println("C Pressed");
                            Enemy.Spawn((float)Mouse.PosX - Camera.PosX, (float)Mouse.PosY - Camera.PosY, 50, 50, ImageManager.ENEMY, 0, Direction, new CircleCollider(Enemy.ENEMY_HITBOX_RADIUS, 0, 0), 5); //Spawnt einen Gegner bei 40, 40 Global mit der Größe 50, 50
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
                            if (SceneManager.ActiveScene instanceof GameScene) {
                                ((GameScene) SceneManager.ActiveScene).showTextForSeconds(randomText, 2); //Packt das Text Element in den To Be Displayed Text
                            }
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
}
