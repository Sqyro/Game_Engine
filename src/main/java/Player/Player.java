package Player;

import Physics2D.LivingObject;
import Physics2D.VelocityHandler;
import Rendering.BarElement;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Item.Item;
import Item.Items;
import Physics2D.CircleCollider;
import Spell.Spell;
import Spell.Spells;

import java.io.Serializable;

public class Player extends LivingObject implements Serializable { // Serialization, weil wir später HP und Inventar Inhalt Speichern müssen
    private static final long serialVersionUID = 1L;
    
    //Variablen deklarieren
    //Größe
    public static float PlayerSizeX = 100;
    public static float PlayerSizeY = 100;

    public static int PLAYER_MAX_HP = 20;

    public volatile boolean isDodging = false;
    public static int PLAYER_DODGE_VELOCITY = 900;
    private float dodgeElapsedTime;
    private float DODGE_TIME_IN_SECONDS = 0.8f;

    //Objekt, welche alle benutzen können um Sachen vom Spieler zu lesen
    public static Player Player;
    
    //Ausrichtung, wenn der Spieler gespawned wird
    private static float[] DefaultDirection = {0, 0};

    //Variable fürs Spieler Inventar, erstellt nen neues Inventar mit der Größe 65
    public InventoryManager inventory = new InventoryManager(65, 10, 1);

    //Constructor vom Spieler, gibt alle Werte an LivingObject hoch
    public Player(float PosX, float PosY, float PlayerLength, float PlayerHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox, int Max_HP) { //Constructor
        super(PosX, PosY, PlayerLength, PlayerHeight, TextureID, Velocity, Direction, Hitbox, Max_HP); //Passed alle Werte an LivingObject weiter
    }

    public void PlayerTick(float deltaTime, ImageHandler renderer) {
        if (isAlive) {
            InputManager.updatePlayerDirection();
            if (isDodging) {
                doADodgeRoll(deltaTime);
            }
            VelocityHandler.calculatePosition(Player, deltaTime);
            //System.out.println(HP);
            Rendering.HudElement Hud = Rendering.HudHandler.HudElements.get(0);
            BarElement bar = (BarElement) Hud;
            if(bar.BarFilledPercentage * bar.HudLength > 0 + bar.BarOffsetX) {
                bar.setBarFilledPercentage(HP/Max_HP);
            }
            if (HP <= 0) {
                die();
            }
            for (int i = 0; i < inventory.getSpellSize(); i++) {
                Spell CurrentSpell = inventory.getSpell(i);
                if (CurrentSpell != null) {
                    if (CurrentSpell.passedTime >= CurrentSpell.SpellCooldownInSeconds) {
                        CurrentSpell.passedTime = 0;
                        CurrentSpell.onCast(0, 0);
                    }
                    CurrentSpell.onSpellTick(deltaTime, renderer);
                }
            }
        }
    }

    public static void createPlayer() { // Methode um nen Spieler zu erstellen
        Player = new Player(0, 0, PlayerSizeX, PlayerSizeY, ImageManager.PLAYER, 0, DefaultDirection, new CircleCollider(32, 0, 15), PLAYER_MAX_HP); //Setzt einfach die Spieler Variable oben auf nen neuen Spieler, damit der Spieler benutzt werden kann
        //Erstellt zwei test Items während der Spieler erstellung, damit man das Inventar schon mal ausprobieren kann
        Player.inventory.setItem(0, Items.ITEMS.getRegistry("sword"));
        Player.inventory.setItem(1, Items.ITEMS.getRegistry("sword"));
        Player.inventory.setSpell(0, Spells.SPELLS.getRegistry("basic"));
        System.out.println("New Player created");
    }

    public void doADodgeRoll(float deltaTime) {
        if (DODGE_TIME_IN_SECONDS > dodgeElapsedTime) {
            Player.setDirectionX(Player.getLastDirectionX());
            Player.setDirectionY(Player.getLastDirectionY());
            dodgeElapsedTime += deltaTime;
            Player.Velocity = PLAYER_DODGE_VELOCITY;
        }
        else {
            isDodging = false;
            dodgeElapsedTime = 0;
            Player.setDirectionX(0);
            Player.setDirectionY(0);
        }
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

    public void die() {
        isAlive = false;
        System.out.println("Player died");
    }

    public void respawn(float PosX, float PosY) {
        HP = Max_HP;
        this.PosX = PosX;
        this.PosY = PosY;
        isAlive = true;
        System.out.println("Player respawned");
    }
}