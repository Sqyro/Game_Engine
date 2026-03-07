package GUI;

import Physics2D.VelocityHandler;
import Player.InputManager;
import Player.Player;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;

public class Frame {

    private long Window;
    public Canva Canva;
    long lastTime;
    
    public static int FramesPerSecond = 60; // 60 FPS sind gerade Standart, soll dann aber einstellbar sein
            
    public Frame(int ScreenWidth, int ScreenHeight, String title) {
        Start(ScreenWidth, ScreenHeight, title);
        Canva = new Canva(ScreenWidth, ScreenHeight);

        Update();
        glfwTerminate();
    }
    
    private void Start(int ScreenWidth, int ScreenHeight, String title) {
        if (!glfwInit()) {
            throw new IllegalStateException("GLFW zu Initialisieren ist fehltgeschlagen");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        Window = glfwCreateWindow(ScreenWidth, ScreenHeight, title, 0, 0);
        
        if (Window == 0) {
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
        
        glMatrixMode(GL_PROJECTION); //Matrix für 2D Projection
        glOrtho(0, ScreenWidth, ScreenHeight, 0, -1, 1); //Orthographische Projektion 0, 0 Links Oben, Bildschirm Breite und höhe Rechts Unten. 1, -1: Keine Z Koordinate 
        
        glMatrixMode(GL_MODELVIEW); // ModelMatric für transformation und größe und so
        
        ImageManager.loadAllTextures();
        
        Player.createPlayer();
        
        InputManager.init(Window);
    }
    
    private void Update() {
        float targetDeltaTime = 1.0f / FramesPerSecond; // Gewünschte FPS (FPS Cap)
        lastTime = System.nanoTime();
        
        while (!glfwWindowShouldClose(Window)) {
            long currentTime = System.nanoTime(); //liest die Frame unabhängige "System Zeit"
            float deltaTime = (currentTime - lastTime) / 1_000_000_000f; //Differenz aus jetzige Zeit und vorherige Zeit ist die Zeit Pro Frame. Diese Zahl benutze ich, damit Geschwindigkeiten auf 30 FPS gleichstark wie auf 60 sind
            lastTime = currentTime; //Das jetzt ist jetzt vorbeit und ist vergangenheit, weil delta Time gesetzt wurde

            //Rechen Updates
            InputManager.updatePlayerDirection();
            VelocityHandler.calculatePosition(Player.Player, deltaTime);
            Camera.UpdateCamera(Player.Player);
            
            //Grafik Updates
            Canva.drawNewFrame();

            glfwSwapBuffers(Window);
            glfwPollEvents();
            
            if (deltaTime < targetDeltaTime) {
                try {
                    Thread.sleep((long)((targetDeltaTime - deltaTime) * 1000)); // Wir schlafen für die differenz aus der gewünschten und der wirklichen deltaTime (*1000, weil wir deltatime ja durch 1000000000 teilen und wieder auf millisekunden kommen wollen)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public long getWindow() {
        return Window;
    }
}
