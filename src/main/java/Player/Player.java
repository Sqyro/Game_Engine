package Player;

import Physics2D.LivingObject;
//import assets.objects.Item;
import java.awt.Image;
//import java.util.ArrayList;
//import java.util.List;
import java.io.Serializable;


public class Player extends LivingObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static int LocPosX = GUI.Frame.ScreenWidth / 2;
    public static int LocPosY = GUI.Frame.ScreenHeight / 2;
    
    public static int PlayerSizeX = 100;
    public static int PlayerSizeY = 100;
    
    public static LivingObject Player;
    
    //List<Item> inventory = new ArrayList<Item>();
    public static int HP;
    
    public Player(int PosX, int PosY, Image img, float Velocity, int[] Direction /*, String name, int hp*/) {
        super(PosX, PosY, img ,Velocity, Direction);
        //this.hp = hp;
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
