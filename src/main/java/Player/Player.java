package Player;

import Physics2D.LivingObject;
import Rendering.ImageManager;
import Rendering.Frame;
import Item.Item;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Player extends LivingObject implements Serializable { // Serialization, weil wir später HP und Inventar Inhalt Speichern müssen
    private static final long serialVersionUID = 1L;
    
    //Variablen deklarieren
    //Position auf dem Bildschirm
    public transient float LocPosX = Frame.ScreenWidth / 2;
    public transient float LocPosY = Frame.ScreenHeight / 2;
    
    //Größe
    public transient static float PlayerSizeX = 100;
    public transient static float PlayerSizeY = 100;
    
    //Objekt, welche alle benutzen können um Sachen vom Spieler zu lesen
    public static Player Player;
    
    //Ausrichtung, wenn der Spieler gespawned wird
    private static int[] DefaultDirection = {0, 0};
    
    public static int HP;
    List<Item> PlayerInventory = new ArrayList<Item>();
    
    
    public Player(float PosX, float PosY, float PlayerLength, float PlayerHeight, int TextureID, float Velocity, int[] Direction /*, String name, int hp*/) { //Constructor
        super(PosX, PosY, PlayerLength, PlayerHeight, TextureID, Velocity, Direction); //Passed alle Werte an LivingObject weiter
        //this.hp = hp;
    }
    
    public static void createPlayer() { // Methode um nen Spieler zu erstellen
        Player = new Player(0, 0, PlayerSizeX, PlayerSizeY, ImageManager.PLAYER, 0, DefaultDirection); //Setzt einfach die Spieler Variable oben auf nen neuen Spieler, damit der Spieler benutzt werden kann
    }
    
    
    public void addItem (Item addedItem) {
        PlayerInventory.add(addedItem);
    }
    
    public Item getIteam (int pos) {
        return PlayerInventory.get(pos);
    }
       
    public int getHp() {
        return HP;
    }
    
    public void setHp(int newHP) {
        HP = newHP;
    }
}
