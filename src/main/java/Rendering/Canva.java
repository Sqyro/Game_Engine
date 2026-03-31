package Rendering;

import Player.Player;
import Player.AnimationManager;
import Enemy.Enemy;
import Shader.Shader;
import Shader.LightManager;
import GUI.GUIManager;
import GUI.TextHandler;
import GUI.GUIText;

import static org.lwjgl.opengl.GL11.*;


public class Canva {
    
    //Variablen Definieren
    Map.MapHandler Map;
    
    private int ScreenWidth;
    private int ScreenHeight;
    
    private Shader shader;
    private Shader hudshader;
    private ImageHandler renderer;
    private AnimationManager animationManager;
    
    public Canva() {
        this.ScreenWidth = Frame.ScreenWidth; //Canva Größe gleich Screengröße
        this.ScreenHeight = Frame.ScreenHeight;
        
        Map = new Map.MapHandler(); //Neues Map Objekt erstellen, gibt gerade nur eine Map, später aber vielleicht mehrere (Räume)
        
        //Erstellt das Shader Objekt für die Globalen Shader, also die, die alle Game Objekte rendern/beeinflussen
        shader = new Shader("src/main/resources/shaders/shader.vsh",
                            "src/main/resources/shaders/shader.fsh");

        //Erstellt das Shader Objekt für Hud, also alles was nicht von Licht beeinflusst werden soll, benutzt sehr einfache Vertex und Fragment Shader, die nur die Textur durchgeben
        hudshader = new Shader("src/main/resources/shaders/hudshader.vsh",
                              "src/main/resources/shaders/hudshader.fsh");
        
        renderer = new ImageHandler(); //Ja, ich benutze gerade den Image Handler als Renderer. Ich sollte dafür ne eingene Renderer Klasse machen, hab aber das eben von meinem alten Code geported und war zu faul
    
        animationManager = new AnimationManager();
        animationManager.createAllAnimations();
    }
    
    protected void drawNewFrame(float deltaTime) { // Zeichnet ein neues Frame
        
        //Der Background ist beeinflusst von dem Global light (Aber keinen Point Lights...)
        float GlobalLight = LightManager.getGlobalLight();
        glClearColor(1f * GlobalLight, 1f * GlobalLight, 1f * GlobalLight, 1f * GlobalLight);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Weiß setzen
        
        //Animationen Updaten
        if(Frame.GameRunning) {
            animationManager.updateAllAnimations(deltaTime);
        }
        
        //Benutzt die drawMap Methode aus dem Map Handler, die die einzelnen Tiles zeichnet
        Map.drawMap(shader, renderer, ScreenWidth, ScreenHeight);
        
        //Findet die Randpunkte des Bildschirms
        int ScreenLeft   = (int) -Camera.PosX;
        int ScreenRight  = ScreenLeft + ScreenWidth;
        int ScreenTop    = (int) -Camera.PosY;
        int ScreenBottom = ScreenTop + ScreenHeight;
        
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
        renderer.flush(shader, ScreenWidth, ScreenHeight);
        
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
        renderer.flush(shader, ScreenWidth, ScreenHeight);
        
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
                renderer.flush(hudshader, ScreenWidth, ScreenHeight);
            }
            
            //Fügt die Hud Elemente in den neuen draw que hinzu
            renderer.drawFull(currentHud.getTextureID(), HudX - Camera.PosX, HudY - Camera.PosY, currentHud.getHudLength(), currentHud.getHudHeight(), 1f, 1f, 1f);
        }
        
        //Flushed alle neuen Elemente im draw que (Nur HUD). Objekte werden mit dem HudShader geshaded und gezeichnet
        renderer.flush(hudshader, ScreenWidth, ScreenHeight);
        
        //Render GUI
        
        if(GUIManager.isScreenOpen()) {
            //Enqued den momentan offenen Screen in draw
            GUIManager.currentScreen.renderScreen(renderer, ScreenWidth, ScreenHeight);
        }
        
        for(GUIText guiText : TextHandler.ToBeDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }
        
        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f);
        
        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(hudshader, ScreenWidth, ScreenHeight);
    }
}