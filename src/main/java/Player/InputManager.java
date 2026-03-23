package Player;

import Rendering.BarElement;
import Rendering.ImageManager;
import Shader.PointLight;
import Shader.LightManager;
import GUI.GUIManager;
import GUI.GUIText;
import GUI.TextHandler;
import GUI.InventoryScreen;
import GUI.PauseScreen;
import Rendering.Frame;

import java.awt.Color;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
    //Variablen Deklarieren
    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;
    
    private static final int[] Direction = {0, 0};
    
    //Eine Methode die ein Event Benutzt um zu hören ob vom Keyboard Inputs gemacht wurden
    public static void ListenforKeys(long window) {

        glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> { //Event
            //Spieler Objekt aus der Spieler Klasse. Ich weiß die Namen von den Methoden und klassen könnten besser sein (Nicht alle Player)
            Player player = Player.Player;
            
            if(Frame.GameRunning) {
            
                boolean Pressed = action != GLFW_RELEASE; //ist was gedrückt worden?
            
                switch (key) {
                    case GLFW_KEY_W: // W wurde gedrückt
                        System.out.println("W Pressed"); //Nachricht für den Debug
                        wPressed = Pressed; //Wurde gerückt, also ja es wurde was gerückt hier rein schreiben für später
                        break; //Schau nicht weiter (Wenn das nicht hier ist, dann wartet er bis ein Key gedrückt wurde und führt dann alles aus)
                    case GLFW_KEY_S:
                        System.out.println("S Pressed");
                        sPressed = Pressed;
                        break;
                    case GLFW_KEY_A:
                        System.out.println("A Pressed");
                        aPressed = Pressed;
                        break;
                    case GLFW_KEY_D:
                        System.out.println("D Pressed");
                        dPressed = Pressed;
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
                                    GUIManager.openScreen(new InventoryScreen(48, 2, 2, 13, 5, 625, 502));
                                }
                            }
                        }
                        break;
                    case GLFW_KEY_ESCAPE:
                        if(action == GLFW_PRESS) {
                            System.out.println("Escape Pressed");
                            if(GUIManager.isScreenOpen()) {
                                TextHandler.clearDisplayedTextQue();
                                GUIManager.closeScreen();
                            } else {
                                if(GUIManager.currentScreen == null) {
                                    GUIManager.openScreen(new PauseScreen());
                                }
                            }
                        }
                        break;
                    case GLFW_KEY_R:
                        System.out.println("R Pressed");
                        if(Rendering.HudHandler.HudElements.isEmpty()) {
                            Rendering.HudHandler.PlaceNewBar(100, 100, 400, 50, Rendering.ImageManager.TESTBAR, 0, 60, 4, Color.RED); //Erstellt eine Bar auf der GUI
                        }
                        break;
                    case GLFW_KEY_Q:
                        System.out.println("Q Pressed");
                        Rendering.HudElement Hud = Rendering.HudHandler.HudElements.get(0); //Nimmt das Hud Element was auf Position 0 ist
                        BarElement bar = (BarElement) Hud; //Konvertiert das Hud Element in ne Bar, geht gerade weil ich nur ein Objekt in der GUI hab, daher ist die Bar immer auf 0
                        if(bar.getBarDamage() < bar.getHudLength() - bar.getBarOffsetX()) { //Wenn noch was von der Bar übrig ist, damit sie nicht ins Minus gerät
                            bar.setBarDamage(bar.getBarDamage() + 2); //Damaged die Bar etwas ums zu testen
                        }
                        break;
                    case GLFW_KEY_C:
                        System.out.println("C Pressed");
                        Enemy.Enemy.Spawn(40, 40, 50, 50, ImageManager.ENEMY, 0, Direction); //Spawnt einen Gegner bei 40, 40 Global mit der Größe 50, 50
                        break;
                    case GLFW_KEY_I:
                        System.out.println("I Pressed");
                        PointLight pointLight = new PointLight(670, 670, 1f, 1f, 1f, 300f); //Erstellt ein neues Point Light bei 670, 670 mit den RGB Werten von 1, 1, 1 und der Reichweite von 300
                        LightManager.addLight(pointLight); //Frügt das Light in die Liste für Lights hinzu
                        break;
                    case GLFW_KEY_Z:
                        System.out.println("Y Pressed");
                        GUIText randomText = new GUIText("Skibidi Tripple T Sigma :3", 80, 80, 80, 50, ImageManager.GAMEFONT); //Erstellt ein neues Text Element
                        TextHandler.addDisplayedText(randomText); //Packt das Text Element in den To Be Displayed Text
                        break;
                    case GLFW_KEY_V:
                        System.out.println("V Pressed");
                            Player LoadedData = (Player) Save.Save.LoadData(); //Läd Spieldateinen aus dem Speicher
                            if (LoadedData != null) { //Darf nicht leer sein
                                Player.Player.setPosX(LoadedData.getPosX()); //Holt sich die Positonen aus den Daten
                                Player.Player.setPosY(LoadedData.getPosY());
                                Player.Player.inventory = LoadedData.inventory; //Holt sich das Inventar aus dem Speicher
                            }
                        break;
                    case GLFW_KEY_X:
                        System.out.println("X Pressed");
                        Save.Save.SaveData(Player.Player); //Speichert Daten vom Spieler
                        break;
                }
            
                // Wir callen Move jedes mal wenn irgendeiner von den Movement Keys jetzt gerade gedrückt wird und wenn nicht, dann stoppen wir
                if (wPressed || aPressed || sPressed || dPressed) {
                    InputHandler.Move(player);
                } else {
                    InputHandler.Stop(player);
                }
            }
        });
        
        //Mouse Event, für Mouse related Dinge
        glfwSetMouseButtonCallback(window, (win, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) { //Wenn der Linke Mouse Button gedrückt wird
                Player player = Player.Player; // Spieler holen
                
                //Variablen für die Position von der Maus erstellen
                double[] MousePosX = new double[1];
                double[] MousePosY = new double[1];

                //Position von der Maus holen und in die Variablen schreiben
                glfwGetCursorPos(window, MousePosX, MousePosY);

                if(GUIManager.currentScreen instanceof InventoryScreen) { //Wenn der Momentane Bildschirm nen Inventar ist, dann die Handle Click Methode ausführen, um zu schauen ob wir mit nem Slot interagieren können
                    InventoryScreen Inventory = (InventoryScreen) GUIManager.currentScreen; //Inventar holen
                    Inventory.handleClick(MousePosX[0], MousePosY[0]); //handle Click ausführen mit der Maus Position
                } else if(GUIManager.currentScreen instanceof PauseScreen) {
                    PauseScreen PauseScreen = (PauseScreen) GUIManager.currentScreen;
                    PauseScreen.handleClick(MousePosX[0], MousePosY[0]);
                }
                
                //Position von der Maus ausgeben
                System.out.println("Mouse clicked at: " + MousePosX[0] + ", " + MousePosY[0]);
            }

        });
    }
    
    public static void updatePlayerDirection() { // Hab den Direction Skript von oben hier runter gemoved und ihn flüssig gemacht, vorher hat der so gestottert, weil Direction für eine Frame 0 war (nach W-S oder A-D)
        //Immer vorher auf 0 setzen
        int DirX = 0;
        int DirY = 0;
        
        Player player = Player.Player;
        
        //Wenn die Keys gedrückt wurden dann addieren/Subtrahieren (nicht setzen, sonst buggt das wenn man zwei Keys gleichzeitig drückt)
        if (aPressed) DirX -= 1;
        if (dPressed) DirX += 1;
        if (wPressed) DirY -= 1;
        if (sPressed) DirY += 1;
        
        //Richtung setzen
        player.setDirectionX(DirX);
        player.setDirectionY(DirY);
        
        if(DirY != 0){ //Letzte Y Richtung speichern
            player.setLastDirectionY(DirY);
        }
        
        if (DirY == 0 && DirX != 0) { //Falls wir uns nochmal auf der X Achse bewegt haben zurücksetzen
            player.setLastDirectionY(0);
        }
    }
}
