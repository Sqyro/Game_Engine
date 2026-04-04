package Rendering;

import GUI.Mouse;
import Physics2D.CollisionManager;
import Scenes.*;
import Player.Player;
import Physics2D.Hitbox;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;

public class Frame {

    //Variablen erstellen
    private long Window;
    long lastTime;
   
    public static int ScreenWidth = 1920;
    public static int ScreenHeight = 1080; // Wird je nach Setting überschrieben, HD FullScreen ist der Standart, wird aber je nach Monitor entsprechend geändert
    
    public static int FramesPerSecond = 60; // 60 FPS sind gerade Standart, soll dann aber einstellbar sein
    
    public static float NormalizedPixelWidth;
    public static float NormalizedPixelHeight;

    public static MainMenuScene MainMenuScene;
    public static LoadGameScene LoadGameScene;
    public static SettingsScene SettingsScene;
    public static GameScene GameScene;
    
    public Frame(String Title) { //Constructor, wird in Main gecalled. Von hier aus wird alles andere gestatet
        Start(Title); // Ruft die Start Methode auf, leitet alles zu starten dahin weiter

        //Startet die Update Methode, die für den Loop zuständig ist
        Update();
        
        //Falls Update irgendwie unterbrochen wird, dann wird diese Methode ausgeführtm die einfach das Window mit allen Funktionen killt und Platz freimacht, hiernach kann nichts OpenGL mäßiges mehr aufgerufen werden
        glfwTerminate();
    }
    
    private void Start(String Title) { // Start Funktion, um halt alles zu starten
        glfwInit(); //GLFW Initialisieren

        //Monitor holen und den in einen Video Mode schreiben von dem ich Variablen lesen kann
        long Monitor = glfwGetPrimaryMonitor();
        GLFWVidMode videoMode = glfwGetVideoMode(Monitor);
        
        //Bildschirmgröße auf den Monitor anpassen
        ScreenWidth = videoMode.width();
        ScreenHeight = videoMode.height();
        
        NormalizedPixelWidth = ScreenWidth/1920;
        NormalizedPixelHeight = ScreenWidth/1080;
        
        //glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Fenster kann vom User scaliert werden
        Window = glfwCreateWindow(ScreenWidth, ScreenHeight, Title, Monitor, 0); //Fenster mit richtiger Größe und Titel erstellen. Monitor, weil es Fullscreen ist, 0 würde heißen windowed
        
        if (Window == 0) { // Falls Fenster nicht erstellt wurde Exception werfen
            throw new RuntimeException("Das GLFW Fenster zu erstellen ist fehlgeschlagen");
        }
        
        // Fenster zentrieren
        GLFWVidMode VideoMode = glfwGetVideoMode(glfwGetPrimaryMonitor()); // Monitor holen
        int WindowX = (VideoMode.width() - ScreenWidth) / 2; // X Position Zentrieren, weil durch 2
        int WindowY = (VideoMode.height() - ScreenHeight) / 2; // Y Position Zentrieren, weil durch 2
        glfwSetWindowPos(Window, WindowX, WindowY); // Fenster positionieren, bei Zentrierten Koordinaten

        glfwMakeContextCurrent(Window); //Setzt das Fenster auf dem gezeichnet wird
        glfwShowWindow(Window); //Macht das Fenster sichtbar
        GL.createCapabilities(); //Für Funktionen später

        //System (OS) Cursor verstecken
        glfwSetInputMode(Window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        
        //Texturen die Transparent sind tatsächlich transparent rendern
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        MainMenuScene = new MainMenuScene();
        SceneManager.CreateNewScene(MainMenuScene, Window);
        LoadGameScene = new LoadGameScene();
        SceneManager.CreateNewScene(LoadGameScene, Window);
        SettingsScene = new SettingsScene();
        SceneManager.CreateNewScene(SettingsScene, Window);
        GameScene = new GameScene();
        SceneManager.CreateNewScene(GameScene, Window);
        
        SceneManager.LoadScene(MainMenuScene, Window);
    }
    
    private void Update() {
        float targetDeltaTime = 1.0f / FramesPerSecond; // Gewünschte FPS (FPS Cap)
        lastTime = System.nanoTime(); //Bei startup die erste Vergangenheit setzen
            
        while (!glfwWindowShouldClose(Window)) { //Solange das Window offen ist
                long currentTime = System.nanoTime(); //liest die Frame unabhängige "System Zeit"
                float deltaTime = (currentTime - lastTime) / 1_000_000_000f; //Differenz aus jetzige Zeit und vorherige Zeit ist die Zeit Pro Frame. Diese Zahl benutze ich, damit Geschwindigkeiten auf 30 FPS gleichstark wie auf 60 sind
                lastTime = currentTime; //Das jetzt ist jetzt vorbeit und ist vergangenheit, weil delta Time gesetzt wurde
                
                //Hört allen Events zu. KeyboardInput Event für den Input Handler
                glfwPollEvents();
                
                //Cursor Position updaten
                Mouse.UpdateMousePos(Window);
                
                //Szenen Updaten
                SceneManager.ActiveScene.onUpdate(deltaTime);
                
                if (deltaTime < targetDeltaTime) { //capped FPS bei den oben gesetzten
                    try {
                        Thread.sleep((long)((targetDeltaTime - deltaTime) * 1000)); // Wir schlafen für die differenz aus der gewünschten und der wirklichen deltaTime (*1000, weil wir deltatime ja durch 1000000000 teilen und wieder auf millisekunden kommen wollen)
                    } catch (InterruptedException e) { //schmeißt ne Exception und gibt Stacktrace für Fehlerbehebung aus, wenn das nicht klappt
                        e.printStackTrace();
                    }
                }
                
                //OpenGL Buffering, switched einfach das was der User gerade sieht mit dem was er sehen soll, also alles was gerendert wurde
                glfwSwapBuffers(Window);
        }
    }
}