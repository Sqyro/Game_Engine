package Player;

import assets.GameAsset;
import assets.objects.Item;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameAsset {
    
    List<Item> inventory = new ArrayList<Item>();
    int hp;
    
    public Player(int x, int y, Image img, String name, int hp) {
        super(x, y, img, name);
        this.hp = hp;
    }

    public void walk(int direction) {}
    
    public void jump(int direction) {}
    
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
