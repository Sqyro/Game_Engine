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
    
    public Canva(int Width, int Height) {
        this.ScreenWidth = Width; //Canva Größe gleich Screengröße
        this.ScreenHeight = Height;
        
        Map = new Map.MapHandler(); //Neues Map Objekt erstellen, gibt gerade nur eine Map, später aber vielleicht mehrere (Räume)
        
        //Erstellt das Shader Objekt für die Globalen Shader, also die, die alle Game Objekte rendern/beeinflussen
        shader = new Shader("src/main/resources/shaders/shader.vert",
                            "src/main/resources/shaders/shader.frag");

        //Erstellt das Shader Objekt für Hud, also alles was nicht von Licht beeinflusst werden soll, benutzt sehr einfache Vertex und Fragment Shader, die nur die Textur durchgeben
        hudshader = new Shader("src/main/resources/shaders/hudshader.vert",
                              "src/main/resources/shaders/hudshader.frag");
        
        renderer = new ImageHandler(); //Ja, ich benutze gerade den Image Handler als Renderer. Ich sollte dafür ne eingene Renderer Klasse machen, hab aber das eben von meinem alten Code geported und war zu faul
    
        animationManager = new AnimationManager();
        animationManager.createAllAnimations();
    }
    
    protected void drawNewFrame(float deltaTime) { // Zeichnet ein neues Frame
        
        //Der Background ist beeinflusst von dem Global light (Aber keinen Point Lights...)
        float GlobalLight = LightManager.getGlobalLight();
        glClearColor(1f * GlobalLight, 1f * GlobalLight, 1f * GlobalLight, 1f * GlobalLight);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Weiß setzen
        
        //Animation Updaten
        animationManager.walkAnimation.UpdateAnimation(deltaTime);
        
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
            renderer.drawFull(currentEnemy.getTextureID(), EnemyX, EnemyY, currentEnemy.getObjLength(), currentEnemy.getObjHeight());
        }
        
        //Render Player
        
        //Macht eine Lokale Variable für den Spieler
        Player player = Player.Player;
        
        float[] PosOnTexture = animationManager.walkAnimation.getPosOnTextureAsArray(player.isFLipped());
        //Fügt den Spieler in den draw que hinzu. Liest die Werte aus der Variable
        renderer.draw(player.getTextureID(), player.LocPosX - player.PlayerSizeX / 2 - Camera.PosX, player.LocPosY - player.PlayerSizeY / 2 - Camera.PosY, player.getObjLength(), player.getObjHeight(), PosOnTexture[0], PosOnTexture[1], PosOnTexture[2], PosOnTexture[3]);

        //Flushed alle Objekte im draw que hierdrüber. Diese Objekte werden gashaded mit dem Global Shader und gezeichnet
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
                
                //hat mal die Farbe und Alpha gelesen und damit nen Quadrat gemalt
                /*
                glColor4f(bar.getColor().getRed() / 255f,
                          bar.getColor().getGreen() / 255f,
                          bar.getColor().getBlue() / 255f,
                          BarElement.BarAlpha);
                drawQuad(HudX, HudY, bar.getHudLength() - bar.getBarDamage(), bar.getHudHeight());
                
                //Farbe wieder auf Weiß setzen für folgende Objekte
                glColor4f(1f, 1f, 1f, 1f);

                */
            }
            
            //Fügt die Hud Elemente in den neuen draw que hinzu
            renderer.drawFull(currentHud.getTextureID(), HudX - Camera.PosX, HudY - Camera.PosY, currentHud.getHudLength(), currentHud.getHudHeight());
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
        
        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(hudshader, ScreenWidth, ScreenHeight);
    }
}