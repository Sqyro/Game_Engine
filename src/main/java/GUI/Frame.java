package GUI;

import Physics2D.VelocityHandler;
import Player.InputManager;
import Player.Player;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;

public class Frame {

    //Variablen erstellen
    private long Window;
    public Canva Canva;
    long lastTime;
    
    public static int FramesPerSecond = 60; // 60 FPS sind gerade Standart, soll dann aber einstellbar sein
            
    public Frame(int ScreenWidth, int ScreenHeight, String title) { //Constructor, wird in Main gecalled. Von hier aus wird alles andere gestatet
        Start(ScreenWidth, ScreenHeight, title); // Ruft die Start Methode auf, leitet alles zu starten dahin weiter
        Canva = new Canva(ScreenWidth, ScreenHeight); //Erstellt nen neuen Canva, mit der größe vom Screen

        //Startet die Update Methode, die für den Loop zuständig ist
        Update();
        
        //Falls Update irgendwie unterbrochen wird, dann wird diese Methode ausgeführtm die einfach das Window mit allen Funktionen killt und Platz freimacht, hiernach kann nichts OpenGL mäßiges mehr aufgerufen werden
        glfwTerminate();
    }
    
    private void Start(int ScreenWidth, int ScreenHeight, String Title) { // Start Funktion, um halt alles zu starten
        glfwInit(); //Fenster Initialisieren

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Fenster kann vom User scaliert werden
        Window = glfwCreateWindow(ScreenWidth, ScreenHeight, Title, 0, 0); //Fenster mit richtiger Größe und Titel erstellen
        
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

        //Texturen die Transparent sind tatsächlich transparent rendern
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        //Alle Texturen einmal laden
        ImageManager.loadAllTextures();
        
        //Spieler erstellen
        Player.createPlayer();
        
        //Hört allen Tastatur Inputs zu, startet im Prinzip den Input Manager
        InputManager.ListenforKeys(Window);
    }
    
    private void Update() {
        float targetDeltaTime = 1.0f / FramesPerSecond; // Gewünschte FPS (FPS Cap)
        lastTime = System.nanoTime(); //Bei startup die erste Vergangenheit setzen
        
        while (!glfwWindowShouldClose(Window)) { //Solange das Window offen ist
            long currentTime = System.nanoTime(); //liest die Frame unabhängige "System Zeit"
            float deltaTime = (currentTime - lastTime) / 1_000_000_000f; //Differenz aus jetzige Zeit und vorherige Zeit ist die Zeit Pro Frame. Diese Zahl benutze ich, damit Geschwindigkeiten auf 30 FPS gleichstark wie auf 60 sind
            lastTime = currentTime; //Das jetzt ist jetzt vorbeit und ist vergangenheit, weil delta Time gesetzt wurde

            //Rechen Updates
            InputManager.updatePlayerDirection();
            VelocityHandler.calculatePosition(Player.Player, deltaTime);
            Camera.UpdateCamera(Player.Player);
            
            //Grafik Updates
            Canva.drawNewFrame();
            
            if (deltaTime < targetDeltaTime) { //capped FPS bei den oben gesetzten
                try {
                    Thread.sleep((long)((targetDeltaTime - deltaTime) * 1000)); // Wir schlafen für die differenz aus der gewünschten und der wirklichen deltaTime (*1000, weil wir deltatime ja durch 1000000000 teilen und wieder auf millisekunden kommen wollen)
                } catch (InterruptedException e) { //schmeißt ne Exception und gibt Stacktrace für Fehlerbehebung aus, wenn das nicht klappt
                    e.printStackTrace();
                }
            }

            //OpenGL Buffering, switched einfach das was der User gerade sieht mit dem was er sehen soll, also alles was gerendert wurde
            glfwSwapBuffers(Window);
            //Hört allen Events zu. KeyboardInput Event für den Input Handler
            glfwPollEvents();
        }
    }

    //Hilfsmethode, wenn man das Window haben will
    public long getWindow() {
        return Window;
    }
}
