package Player;

import Physics2D.PhysicsObject2D;
//import assets.objects.Item;
import java.awt.Image;
//import java.util.ArrayList;
//import java.util.List;

public class Player extends PhysicsObject2D {
    public static int LocPosX = GUI.Frame.ScreenWidth / 2;
    public static int LocPosY = GUI.Frame.ScreenHeight / 2;
    
    public static int PlayerSizeX = 100;
    public static int PlayerSizeY = 100;
    
    public static PhysicsObject2D Player;
    
    //List<Item> inventory = new ArrayList<Item>();
    public static int HP;
    
    public Player(int PosX, int PosY, float Velocity, int[] Direction, Image img /*, String name, int hp*/) {
        super(PosX, PosY, Velocity, Direction, img);
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
