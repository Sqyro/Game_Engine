package Scenes;

import Enemy.Enemy;
import GUI.GUIManager;
import GUI.GUIText;
import GUI.TextHandler;
import Physics2D.CollisionManager;
import Physics2D.VelocityHandler;
import Player.AnimationManager;
import Player.InputManager;
import Player.Player;
import Rendering.BarElement;
import Rendering.Camera;
import Rendering.Frame;
import Rendering.HudElement;
import Rendering.HudHandler;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Shader.LightManager;
import Shader.Shader;
import Sounds.SoundHandler;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class GameScene extends Scene {
    private Shader shader;
    private Shader hudshader;
    private ImageHandler renderer;
    private AnimationManager animationManager;
    
    Map.MapHandler Map;
    
    public static boolean GameRunning;
    private boolean wasGameRunning = true;
    
    public static float Gametime = 0;
    
    public GameScene() {
        Map = new Map.MapHandler(); //Neues Map Objekt erstellen, gibt gerade nur eine Map, später aber vielleicht mehrere (Räume)
        
        //Erstellt das Shader Objekt für die Globalen Shader, also die, die alle Game Objekte rendern/beeinflussen
        shader = new Shader("src/main/resources/shaders/shader.vsh",
                            "src/main/resources/shaders/shader.fsh");

        //Erstellt das Shader Objekt für Hud, also alles was nicht von Licht beeinflusst werden soll, benutzt sehr einfache Vertex und Fragment Shader, die nur die Textur durchgeben
        hudshader = new Shader("src/main/resources/shaders/hudshader.vsh",
                              "src/main/resources/shaders/hudshader.fsh");
        
        renderer = new ImageHandler(); //Ja, ich benutze gerade den Image Handler als Renderer. Ich sollte dafür ne eingene Renderer Klasse machen, hab aber das eben von meinem alten Code geported und war zu faul
    
        animationManager = new AnimationManager();
        animationManager.createGameAnimations();
    }
    
    @Override
    public void onCreation(long Window) {
        ImageManager.loadGameTextures();
        
        //Spieler erstellen
        Player.createPlayer();
    }
    
    @Override
    public void onLoadup(long Window) {
        GameRunning = true;
        
        //Hört allen Tastatur Inputs zu, startet im Prinzip den Input Manager
        InputManager.ListenforGameKeys(Window);
        
        TextHandler.clearDisplayedTextQue();
    }
    
    @Override
    public void onUpdate(float deltaTime) {
        if (GameRunning != wasGameRunning) { //Schaut ob sich der GameRunning State geändert hat
            if (!GameRunning) { //Wenn das Spiel gestoppt wurde dann pausiere alle Sounds
                SoundHandler.pauseAll();
            } else { //Wenn das Spiel nicht gestoppt, also resumed wurde, dann unpausiere alle Sounds
                SoundHandler.resumeAll();
            }
            wasGameRunning = GameRunning; //Die was Game Running variable updaten auf das neuste
        }
                
        //Rechen Updates
        if(GameRunning) {
            Gametime += deltaTime;
            InputManager.updatePlayerDirection();
            VelocityHandler.calculatePosition(Player.Player, deltaTime);
            SoundHandler.updateSounds(deltaTime);
            Camera.UpdateCamera(Player.Player);
            //Hitbox update machen
            CollisionManager.Player_Enemy();
        }
        
        //Der Background ist beeinflusst von dem Global light (Aber keinen Point Lights...)
        float GlobalLight = LightManager.getGlobalLight();
        glClearColor(1f * GlobalLight, 1f * GlobalLight, 1f * GlobalLight, 1f * GlobalLight);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Weiß setzen
        
        //Animationen Updaten
        if(GameRunning) {
            animationManager.updateGameAnimations(deltaTime);
        }
        
        //Benutzt die drawMap Methode aus dem Map Handler, die die einzelnen Tiles zeichnet
        Map.drawMap(shader, renderer, Frame.ScreenWidth, Frame.ScreenHeight);
        
        //Findet die Randpunkte des Bildschirms
        int ScreenLeft   = (int) -Camera.PosX;
        int ScreenRight  = ScreenLeft + Frame.ScreenWidth;
        int ScreenTop    = (int) -Camera.PosY;
        int ScreenBottom = ScreenTop + Frame.ScreenHeight;
        
        //Render Enemies;
        for(int i = 0; i < Enemy.Enemies.size(); i++) { //Liest die Enemies Liste durch
            //Kopiert den momentan gelesenen Enemy in eine Lokale Variable
            Enemy currentEnemy = Enemy.Enemies.get(i);
            
            //Sneaked sich die ganzen Positionen in andere Variablen rein, damit man es besser lesen kann
            float EnemyX = currentEnemy.getPosX();
            float EnemyY = currentEnemy.getPosY();
            
            //Wenn dieser Enemie nicht auf dem Screen ist (Plus Minus 100, damit man es nicht merkt)
            if (EnemyX < ScreenLeft - 100 || EnemyX > ScreenRight + 100 ||EnemyY < ScreenTop - 100 || EnemyY > ScreenBottom + 100) {
                continue; // Skippt diesen Enemy und macht mit dem nächsten weiter
            }
            
            //Sonst fügt er den Enemy in den draw que hinzu
            renderer.drawFull(currentEnemy.getTextureID(), EnemyX, EnemyY, currentEnemy.getObjLength(), currentEnemy.getObjHeight(), 1f, 1f, 1f);
        }
        
        //Flushed Map und Enemies unter den Spieler. Werden geshaded und gezeichnet mit dem Global Shader
        renderer.flush(shader, Frame.ScreenWidth, Frame.ScreenHeight);
        
        //Render Player
        
        //Macht eine Lokale Variable für den Spieler
        Player player = Player.Player;
        //Fügt den Spieler in den draw que hinzu. Liest die Werte aus der Variable
        if(player.getVelocity() > 0) {
            if(player.getDirectionY() == 0) {
                float[] PosOnTexture = animationManager.walkAnimation.getPosOnTextureAsArray(player.isFLipped());
                renderer.draw(player.getTextureID(), player.getPosX(), player.getPosY(), player.getObjLength(), player.getObjHeight(), PosOnTexture[0], PosOnTexture[1], PosOnTexture[2], PosOnTexture[3], 1f, 1f, 1f);
            } else if(player.getDirectionY() == 1) {
                float[] PosOnTexture = animationManager.walkDownAnimation.getPosOnTextureAsArray(false);
                renderer.draw(player.getTextureID(), player.getPosX(), player.getPosY(), player.getObjLength(), player.getObjHeight(), PosOnTexture[0], PosOnTexture[1], PosOnTexture[2], PosOnTexture[3], 1f, 1f, 1f);
            } else {
                float[] PosOnTexture = animationManager.walkUpAnimation.getPosOnTextureAsArray(false);
                renderer.draw(player.getTextureID(), player.getPosX(), player.getPosY(), player.getObjLength(), player.getObjHeight(), PosOnTexture[0], PosOnTexture[1], PosOnTexture[2], PosOnTexture[3], 1f, 1f, 1f);
            }
        } else {
            if(player.getLastDirectionY() == 0) {
                float[] PosOnTexture = animationManager.idleAnimation.getPosOnTextureAsArray(player.isFLipped());
                renderer.draw(player.getTextureID(), player.getPosX(), player.getPosY(), player.getObjLength(), player.getObjHeight(), PosOnTexture[0], PosOnTexture[1], PosOnTexture[2], PosOnTexture[3], 1f, 1f, 1f);
            } else if(player.getLastDirectionY() == 1) {
                float[] PosOnTexture = animationManager.idleDownAnimation.getPosOnTextureAsArray(false);
                renderer.draw(player.getTextureID(), player.getPosX(), player.getPosY(), player.getObjLength(), player.getObjHeight(), PosOnTexture[0], PosOnTexture[1], PosOnTexture[2], PosOnTexture[3], 1f, 1f, 1f);
            } else {
                float[] PosOnTexture = animationManager.idleUpAnimation.getPosOnTextureAsArray(false);
                renderer.draw(player.getTextureID(), player.getPosX(), player.getPosY(), player.getObjLength(), player.getObjHeight(), PosOnTexture[0], PosOnTexture[1], PosOnTexture[2], PosOnTexture[3], 1f, 1f, 1f);
            }
        }
        //Flushed den Spieler später, damit er über allem drüber liegt. Er wird gashaded mit dem Global Shader und gezeichnet
        renderer.flush(shader, Frame.ScreenWidth, Frame.ScreenHeight);
        
        //Render HUD
        
        for(int i = 0; i < HudHandler.HudElements.size(); i++) { //Ähnlich wie bei den Enemies, geht die Hud Elemente durch
            //Moved das Momentane Hud Element in eine Lokale Variable
            HudElement currentHud = HudHandler.HudElements.get(i);
            //Sneaked sich die Werte für Position für bessere Lesbarkeit
            int HudX = currentHud.getPosX();
            int HudY = currentHud.getPosY();
            
            
            //Der Code hat mal mit ner Helfmethode für Quadrate zeichnen Funktioniert, jetzt aber nichtmehr, weil ich ja von dem Base OpenGL renderer weggegangen bin für Shader, muss das mach fixen
            if(currentHud instanceof BarElement) { // Wenn es ein BarElement ist, dann zeichnet er die Farbe ("Bar darunter") mit dazu
                BarElement bar = (BarElement) currentHud;
                
                //Farbe Holen
                float Red = bar.getColor().getRed() / 255f;
                float Green = bar.getColor().getGreen() / 255f;
                float Blue = bar.getColor().getBlue() / 255f;
                
                //Rechteck unter die Textur zeichnen
                renderer.drawRectangle(ImageManager.BAR, HudX - Camera.PosX + bar.getBarOffsetX(), HudY - Camera.PosY + bar.getBarOffsetY(), bar.getHudLength() * bar.getBarFilledPercentage() - bar.getBarOffsetX(), bar.getHudHeight() - bar.getBarOffsetY() * 2, Red, Green, Blue);
                //Bar Extra flushen, damit es unter der Textur liegt
                renderer.flush(hudshader, Frame.ScreenWidth, Frame.ScreenHeight);
            }
            
            //Fügt die Hud Elemente in den neuen draw que hinzu
            renderer.drawFull(currentHud.getTextureID(), HudX - Camera.PosX, HudY - Camera.PosY, currentHud.getHudLength(), currentHud.getHudHeight(), 1f, 1f, 1f);
        }
        
        //Flushed alle neuen Elemente im draw que (Nur HUD). Objekte werden mit dem HudShader geshaded und gezeichnet
        renderer.flush(hudshader, Frame.ScreenWidth, Frame.ScreenHeight);
        
        //Render GUI
        
        if(GUIManager.isScreenOpen()) {
            //Enqued den momentan offenen Screen in draw
            GUIManager.currentScreen.renderScreen(renderer, Frame.ScreenWidth, Frame.ScreenHeight);
        }
        
        for(GUIText guiText : TextHandler.ToBeDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }
        
        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f);
        
        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(hudshader, Frame.ScreenWidth, Frame.ScreenHeight);
    }
}
