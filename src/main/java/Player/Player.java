package Player;

import Physics2D.LivingObject;
import Rendering.ImageManager;
import Rendering.Frame;
import Item.Item;
import Item.Items;
import Item.Weapons.SwordItem;
import Physics2D.CircleCollider;

import java.io.Serializable;

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
    private static float[] DefaultDirection = {0, 0};

    public int MAX_HP;
    public int HP;
    
    //Variable fürs Spieler Inventar, erstellt nen neues Inventar mit der Größe 65
    public InventoryManager inventory = new InventoryManager(65);
    
    //Constructor vom Spieler, gibt alle Werte an LivingObject hoch
    public Player(float PosX, float PosY, float PlayerLength, float PlayerHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox /*, String name, int hp*/) { //Constructor
        super(PosX, PosY, PlayerLength, PlayerHeight, TextureID, Velocity, Direction, Hitbox); //Passed alle Werte an LivingObject weiter
        //this.HP = HP;
    }
    
    public static void createPlayer() { // Methode um nen Spieler zu erstellen
        Player = new Player(0, 0, PlayerSizeX, PlayerSizeY, ImageManager.PLAYER, 0, DefaultDirection, new CircleCollider(32, 0, 15)); //Setzt einfach die Spieler Variable oben auf nen neuen Spieler, damit der Spieler benutzt werden kann
        //Erstellt zwei test Items wärend der Spieler erstellung, damit man das Inventar schonmal ausprobieren kann
        Player.Player.inventory.setItem(0, Items.ITEMS.getRegistry("sword"));
        Player.Player.inventory.setItem(1, Items.ITEMS.getRegistry("sword"));
    }
    
    //Methode, um ein Item ins Spieler Inventar hinzu zu fügen, z.B von nem Drop
    public void addItem (Item addedItem) {
        for(int i = 0; i < inventory.getInventorySize(); i++) { //Das ganze Inventar durchgehen
            if(inventory.getItem(i) == null) { //Schauen oder der Slot, wo man gerade ist leer ist
                inventory.setItem(i, addedItem); //Wenn er leer ist, dann fügt man das Item in diesen Slot hinzu
                return; //Methode beenden, also nicht weiter die Slots durchschauen
            }
        }
        //Falls alle Slots belegt sind, dann sagen, dass das Inventar voll ist
        System.out.println("Inventory Full!");
    }
    
    //Helfer Methoden um die HP zu setzen

    public void setMaxHp(int newMaxHP) {
        MAX_HP = newMaxHP;
    }

    public void setHp(int newHP) {
        HP = newHP;
    }

    public void damagePlayer(int Damage) {
        HP -= Damage;
    }
}