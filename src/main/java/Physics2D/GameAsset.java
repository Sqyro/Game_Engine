package Physics2D;

import java.awt.Graphics;
import java.awt.Image;


public abstract class GameAsset {
    int x, y;
    Image img;
    String name;

    public GameAsset(int x, int y, Image img, String name) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }
}
