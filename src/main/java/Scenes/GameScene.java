package Scenes;

import Enemy.Enemy;
import GUI.GUIInteractableField;
import GUI.GUIManager;
import GUI.GUIText;
import Item.Items;
import Map.MapObjects;
import Physics2D.CollisionManager;
import Player.PlayerAnimationManager;
import Inputs.InputManager;
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
import Spell.Spells;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GameScene extends Scene implements Serializable {
    private final Shader shader;
    private final Shader hudshader;
    private final ImageHandler renderer;
    private final PlayerAnimationManager playerAnimationManager;
    
    Map.MapHandler Map;
    
    public static boolean GameRunning;
    private boolean wasGameRunning = true;
    
    public static float Gametime = 0;

    public List<GUIInteractableField> GameInteractableFields = new ArrayList<>();

    //Liste für alle Texte die gerendert werden sollen
    public static List<GUIText> GameDisplayedText = new ArrayList<>();

    public static List<TimedTextEnque> TimedText = new ArrayList<>();

    public int SceneSaveID;

    public GameScene(int ID) {
        Map = new Map.MapHandler(); //Neues Map Objekt erstellen, gibt gerade nur eine Map, später aber vielleicht mehrere (Räume)
        
        //Erstellt das Shader Objekt für die Globalen Shader, also die, die alle Game Objekte rendern/beeinflussen
        shader = new Shader("src/main/resources/shaders/shader.vsh",
                            "src/main/resources/shaders/shader.fsh");

        //Erstellt das Shader Objekt für Hud, also alles was nicht von Licht beeinflusst werden soll, benutzt sehr einfache Vertex und Fragment Shader, die nur die Textur durchgeben
        hudshader = new Shader("src/main/resources/shaders/hudshader.vsh",
                              "src/main/resources/shaders/hudshader.fsh");
        
        renderer = new ImageHandler(); //Ja, ich benutze gerade den Image Handler als Renderer. Ich sollte dafür ne eingene Renderer Klasse machen, hab aber das eben von meinem alten Code geported und war zu faul
    
        playerAnimationManager = new PlayerAnimationManager();

        this.SceneSaveID = ID;
    }
    
    @Override
    public void onCreation(long Window) {
        ImageManager.loadGameTextures();
        playerAnimationManager.createPlayerAnimations();

        MapObjects.RegisterMapObjects();
        Items.RegisterItems();
        Spells.RegisterSpells();

        //Spieler erstellen
        Player.createPlayer();
        InputManager.init();
        Rendering.HudHandler.PlaceNewBar(100, 100, 400, 50, Rendering.ImageManager.TESTBAR, 1, 60, 4, Color.RED);
    }
    
    @Override
    public void onLoadup(long Window) {
        //Hört allen Tastatur Inputs zu, startet im Prinzip den Input Manager
        InputManager.ListenforGameKeys(Window);

        Camera.UpdateCamera(Player.Player);

        GameRunning = true;
    }

    @Override
    public void onUnload() {
        GameRunning = false;

        GUIManager.closeScreen();

        clearOnScreenFields();
        clearDisplayedTextQue();

        Camera.resetCamera();
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

        //Macht eine Lokale Variable für den Spieler
        Player player = Player.Player;
                
        //Rechen Updates
        if(GameRunning) {
            Gametime += deltaTime;
            player.PlayerTick(deltaTime, renderer);
            Enemy.UpdateAllEnemyAI(deltaTime);
            SoundHandler.updateSounds(deltaTime);
            updateTimedText();
            Camera.UpdateCamera(Player.Player);
            //Liste an aktiven Lichtern updaten
            //LightManager.updateVisibleLights(Frame.ScreenWidth, Frame.ScreenHeight);
            //Hitbox update machen
            CollisionManager.Player_Enemy();
            //CollisionManager.Player_Wall();
            //CollisionManager.Enemy_Wall();
        }
        
        //Der Background ist beeinflusst von dem Global light (Aber keinen Point Lights...)
        float GlobalLight = LightManager.getGlobalLight();
        glClearColor(GlobalLight, GlobalLight, GlobalLight, GlobalLight);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Weiß setzen
        
        //Animationen Updaten
        if(GameRunning) {
            playerAnimationManager.updatePlayerAnimation(deltaTime);
            for (Enemy CurrentEnemy : Enemy.Enemies) {
                CurrentEnemy.enemyAnimationManager.updateEnemyAnimation(deltaTime);
            }
        }
        
        //Benutzt die drawMap Methode aus dem Map Handler, die die einzelnen Tiles zeichnet
        Map.drawMap(shader, renderer, Frame.ScreenWidth, Frame.ScreenHeight);
        
        //Findet die Randpunkte des Bildschirms
        int ScreenLeft   = (int) -Camera.PosX;
        int ScreenRight  = ScreenLeft + Frame.ScreenWidth;
        int ScreenTop    = (int) -Camera.PosY;
        int ScreenBottom = ScreenTop + Frame.ScreenHeight;
        
        //Render Enemies;
        for(Enemy currentEnemy : Enemy.Enemies) { //Liest die Enemies Liste durch
            //Sneaked sich die ganzen Positionen in andere Variablen rein, damit man es besser lesen kann
            float EnemyDrawX = currentEnemy.PosX - currentEnemy.ObjLength/2;
            float EnemyDrawY = currentEnemy.PosY - currentEnemy.ObjHeight/2;
            
            //Wenn dieser Enemie nicht auf dem Screen ist (Plus Minus 100, damit man es nicht merkt)
            if (EnemyDrawX < ScreenLeft - 100 || EnemyDrawX > ScreenRight + 100 || EnemyDrawY < ScreenTop - 100 || EnemyDrawY > ScreenBottom + 100) {
                continue; // Skippt diesen Enemy und macht mit dem nächsten weiter
            }

            if (currentEnemy.getDirectionY() == 0) {
                currentEnemy.enemyAnimationManager.currentAnimation = currentEnemy.enemyAnimationManager.swampWalkAnimation;
            } else if (currentEnemy.getDirectionY() == 1) {
                currentEnemy.enemyAnimationManager.currentAnimation = currentEnemy.enemyAnimationManager.swampWalkDownAnimation;
            } else {
                currentEnemy.enemyAnimationManager.currentAnimation = currentEnemy.enemyAnimationManager.swampWalkUpAnimation;
            }

            currentEnemy.enemyAnimationManager.currentAnimation.renderAnimation(EnemyDrawX, EnemyDrawY, currentEnemy.ObjLength, currentEnemy.ObjHeight, currentEnemy.isFLipped(), renderer);

            //Sonst fügt er den Enemy in den draw que hinzu
            //renderer.drawFull(currentEnemy.TextureID, EnemyDrawX, EnemyDrawY, currentEnemy.ObjLength, currentEnemy.ObjHeight, 1f, 1f, 1f, 1f);
        }
        /*
        //Render Walls
        for(int i = 0; i < Wall.Walls.size(); i++) { //Liest die Walls Liste durch
            //Kopiert die momentan gelesene Wall in eine Lokale Variable
            Wall currentWall = Wall.Walls.get(i);
            
            //Sneaked sich die ganzen Positionen in andere Variablen rein, damit man es besser lesen kann
            float WallX = currentWall.PosX;
            float WallY = currentWall.PosY;
            
            //Wenn diese Wall nicht auf dem Screen ist (Plus Minus 100, damit man es nicht merkt)
            if (WallX < ScreenLeft - 100 || WallX > ScreenRight + 100 ||WallY < ScreenTop - 100 || WallY > ScreenBottom + 100) {
                continue; // Skippt diese Wall und macht mit dem nächsten weiter
            }
            
            //Sonst fügt er den Enemy in den draw que hinzu
            renderer.drawFull(currentWall.TextureID, WallX, WallY, currentWall.ObjLength, currentWall.ObjHeight, 1f, 1f, 1f);
        }
        */
        
        //Flushed Map und Enemies unter den Spieler. Werden geshaded und gezeichnet mit dem Global Shader
        renderer.flush(shader, Frame.ScreenWidth, Frame.ScreenHeight);
        
        //Render Player

        //Fügt den Spieler in den draw que hinzu. Liest die Werte aus der Variable

        if (!player.isDodging) {
            if (player.Velocity > 0) {
                if (player.getDirectionY() == 0) {
                    playerAnimationManager.currentAnimation = playerAnimationManager.walkAnimation;
                } else if (player.getDirectionY() == 1) {
                    playerAnimationManager.currentAnimation = playerAnimationManager.walkDownAnimation;
                } else {
                    playerAnimationManager.currentAnimation = playerAnimationManager.walkUpAnimation;
                }
            } else {
                if (player.getLastDirectionY() == 0) {
                    playerAnimationManager.currentAnimation = playerAnimationManager.idleAnimation;
                } else if (player.getLastDirectionY() == 1) {
                    playerAnimationManager.currentAnimation = playerAnimationManager.idleDownAnimation;
                } else {
                    playerAnimationManager.currentAnimation = playerAnimationManager.idleUpAnimation;
                }
            }
        } else {
            if (player.getDirectionY() == 1) {
                playerAnimationManager.currentAnimation = playerAnimationManager.dodgeRollDownAnimation;
            } else if (player.getDirectionY() == -1) {
                playerAnimationManager.currentAnimation = playerAnimationManager.dodgeRollUpAnimation;
            } else {
                playerAnimationManager.currentAnimation = playerAnimationManager.dodgeRollAnimation;
            }
        }

        playerAnimationManager.currentAnimation.renderAnimation(player.PosX - player.ObjLength/2, player.PosY - player.ObjHeight/2, player.ObjLength, player.ObjHeight, player.isFLipped(), renderer);

        //Flushed den Spieler später, damit er über allem drüber liegt. Er wird gashaded mit dem Global Shader und gezeichnet
        renderer.flush(shader, Frame.ScreenWidth, Frame.ScreenHeight);

        //Render HUD
        
        for(HudElement currentHud : HudHandler.HudElements) { //Ähnlich wie bei den Enemies, geht die Hud Elemente durch
            //Sneaked sich die Werte für Position für bessere Lesbarkeit
            int HudX = currentHud.PosX;
            int HudY = currentHud.PosY;

            //Der Code hat mal mit ner Helfmethode für Quadrate zeichnen Funktioniert, jetzt aber nichtmehr, weil ich ja von dem Base OpenGL renderer weggegangen bin für Shader, muss das mach fixen
            if(currentHud instanceof BarElement) { // Wenn es ein BarElement ist, dann zeichnet er die Farbe ("Bar darunter") mit dazu
                BarElement bar = (BarElement) currentHud;
                
                //Farbe Holen
                float Red = bar.BarColor.getRed() / 255f;
                float Green = bar.BarColor.getGreen() / 255f;
                float Blue = bar.BarColor.getBlue() / 255f;
                
                //Rechteck unter die Textur zeichnen
                if (bar.BarFilledPercentage > 0) {
                    renderer.drawRectangle(ImageManager.BAR, HudX - Camera.PosX + bar.BarOffsetX, HudY - Camera.PosY + bar.BarOffsetY, bar.HudLength * bar.BarFilledPercentage - bar.BarOffsetX, bar.HudHeight - bar.BarOffsetY * 2, Red, Green, Blue, 1f);
                }
                //Bar Extra flushen, damit es unter der Textur liegt
                renderer.flush(hudshader, Frame.ScreenWidth, Frame.ScreenHeight);
            }
            
            //Fügt die Hud Elemente in den neuen draw que hinzu
            renderer.drawFull(currentHud.TextureID, HudX - Camera.PosX, HudY - Camera.PosY, currentHud.HudLength, currentHud.HudHeight, 1f, 1f, 1f, 1f);
        }
        
        //Flushed alle neuen Elemente im draw que (Nur HUD). Objekte werden mit dem HudShader geshaded und gezeichnet
        renderer.flush(hudshader, Frame.ScreenWidth, Frame.ScreenHeight);
        
        //Render GUI
        
        if(GUIManager.isScreenOpen()) {
            //Enqued den momentan offenen Screen in draw
            GUIManager.currentScreen.renderScreen(renderer, Frame.ScreenWidth, Frame.ScreenHeight);
        }

        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(hudshader, Frame.ScreenWidth, Frame.ScreenHeight);

        for(GUIInteractableField CurrentField : GameInteractableFields) {
            CurrentField.drawField(renderer);
        }

        for(GUIText guiText : GameDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }

        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f, 1f);

        //Flushed alles andere danach, weil der Cursor sonst unter dem Inventar ist, einfach wegen der Reihenfolge in der alles geladen wird
        renderer.flush(hudshader, Frame.ScreenWidth, Frame.ScreenHeight);
    }

    @Override
    public void clearOnScreenFields() {
        GameInteractableFields.clear();
    }

    @Override
    public void handleClick(long Window, double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : GameInteractableFields) {
            if(CurrentField.CursorHoveringOver(CursorX, CursorY)) {
                CurrentField.onFieldClick(Window);
                break;
            }
        }
    }

    @Override
    public void handleHovering(double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : GameInteractableFields) {
            CurrentField.CursorHoveringOver(CursorX, CursorY);
        }
    }

    public void updateTimedText() {
        float currentGameTime = Gametime;
        for (int i = 0; i < TimedText.size(); i++) {
            TimedTextEnque ThisTimedText = TimedText.get(i);
            if (currentGameTime - ThisTimedText.startGametime >= ThisTimedText.TextLivingTimeInMilliSeconds) {
                removeDisplayedText(ThisTimedText.Text);
                TimedText.remove(i);
                i--;
                System.out.println("Text removed");
            }
        }
    }

    @Override
    public void addDisplayedText(GUIText addedText) {
        GameDisplayedText.add(addedText);
    }

    public void removeDisplayedText(GUIText removedText) {
        GameDisplayedText.remove(removedText);
    }

    @Override
    public void clearDisplayedTextQue() {
        GameDisplayedText.clear();
    }

    public void showTextForSeconds(GUIText shownText, float TimeInSeconds) {
        float startGametime = Gametime;
        addDisplayedText(shownText);
        TimedText.add(new TimedTextEnque(shownText, TimeInSeconds, startGametime));
    }

    public class TimedTextEnque {
        public GUIText Text;
        public float TextLivingTimeInMilliSeconds;
        float startGametime;

        public TimedTextEnque(GUIText Text, float TextLivingTimeInMilliSeconds, float startGametime) {
            this.Text = Text;
            this.TextLivingTimeInMilliSeconds = TextLivingTimeInMilliSeconds;
            this.startGametime = startGametime;
        }
    }
}