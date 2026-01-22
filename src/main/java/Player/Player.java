package Player;

import Physics2D.PhysicsObject2D;
import assets.objects.Item;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Player extends PhysicsObject2D {
    public static int LocPosX = GUI.Frame.ScreenWidth / 2;
    public static int LocPosY = GUI.Frame.ScreenHeight / 2;
    
    public static int PlayerSizeX = 50;
    public static int PlayerSizeY = 50;
    
    List<Item> inventory = new ArrayList<Item>();
    int hp;
    
    public Player(Image img, String name, int hp) {
        this.hp = hp;
    }
    
    public void addItem (Item item) {}
    
    public Item getIteam (int pos) {
        return inventory.get(pos);
    }

    public int getHp() {
        return hp;
    }
    
    public void setHp(int hp) {
        this.hp = hp;
    }
}
