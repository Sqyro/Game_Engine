package TileMapEditor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CursorManager {

    public static BufferedImage pencil;
    public static BufferedImage bucket;
    public static BufferedImage pipette;
    public static BufferedImage currentCursor;

    public static int mouseX; //mausposition x
    public static int mouseY; //mausposition y
    public static int oldMouseX; //alte mausposition x
    public static int oldMouseY; //alte mausposition y
    
    public static int cursorSize = 32; //größe des cursors
    public static int offsetX = 0; //verschiebung an x
    public static int offsetY = 0; //verschiebung an y

    public static void deleteSystemMouse(JPanel panel) {
        panel.setCursor(java.awt.Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),new java.awt.Point(0, 0), null)); //ersetz aktuelle Maus mit einem 1x1 großen leeren Punkt und der Klickpunkt des leeren Punktes bleibt gleich
    }

    public static void load(JFrame frame) { //alle maustexturen werden hier geladen

        try {

            pencil = javax.imageio.ImageIO.read(new java.io.File("src/main/resources/assets/textures/gui/cursor/pencil.png")); //pencil textur
 
            bucket = javax.imageio.ImageIO.read(new java.io.File("src/main/resources/assets/textures/gui/cursor/bucket.png")); //bucket textur

            pipette = javax.imageio.ImageIO.read(new java.io.File("src/main/resources/assets/textures/gui/cursor/pipette.png")); //pipetten textur

            //default cursor
            currentCursor = pencil;
            offsetX = 0;
            offsetY = -31;

        } catch (IOException e) {
        }
    }

    public static void updateCursor(int currentTool) { //hier wird der Cursor geändert je nach tool und da meine texturen andere punkte haben als eine normale maus versetze ich die damit sie passen
        switch (currentTool) {
            case 0:
                currentCursor = pencil;
                offsetX = 0;   
                offsetY = -31;
                break;
            case 1:
                currentCursor = bucket;
                offsetX = 0;
                offsetY = -17;
                break;
            case 2:
                currentCursor = pipette;
                offsetX = 0;
                offsetY = -31;
                break;
        }
    }
    
    public static void draw(Graphics g) { //methode fürs zeichnen der cursors könnte man theoretisch in paintcomponent schreiben in Grid, aber einfach eine methode auszuführen ist simpler
        if (currentCursor != null) {
            g.drawImage(currentCursor, mouseX + offsetX, mouseY + offsetY, cursorSize, cursorSize, null);
        }
    }

    public static void updateMouse(int x, int y) {
        oldMouseX = mouseX; //speichert vorherige x position
        oldMouseY = mouseY; //speichert vorherige y position
        mouseX = x; //aktuelle maus x speichern
        mouseY = y; //aktuelle maus y speichern
    }
}