package Player;

import Physics2D.LivingObject;
import GUI.ImageManager;

import java.io.Serializable;


public class Player extends LivingObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int LocPosX = main.Main.ScreenWidth / 2;
    public int LocPosY = main.Main.ScreenHeight / 2;
    
    public static int PlayerSizeX = 100;
    public static int PlayerSizeY = 100;
    
    public static Player Player;
    
    private static int[] DefaultDirection = {0, 0};
    
    //List<Item> inventory = new ArrayList<Item>();
    public static int HP;
    
    public Player(int PosX, int PosY, int PlayerLength, int PlayerHeight, int TextureID, float Velocity, int[] Direction /*, String name, int hp*/) {
        super(PosX, PosY, PlayerLength, PlayerHeight, TextureID, Velocity, Direction);
        //this.hp = hp;
    }
    
    public static void createPlayer() {
        Player = new Player(0, 0, PlayerSizeX, PlayerSizeY, ImageManager.PLAYER, 0, DefaultDirection);
    }
    
    /*
    public void addItem (Item item) {}
    
    public Item getIteam (int pos) {
        return inventory.get(pos);
    }
    */
    
    public int getHp() {
        return HP;
    }
    
    public void setHp(int newHP) {
        HP = newHP;
    }
}
