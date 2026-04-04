package TileMapEditor;

import javax.swing.*;
import java.awt.*;

public class HitboxenSelector extends JPanel {
    private Grid grid; //Referenz
    private Image ImageHitbox; //zwei bild was angezeigt wird weil ich nur zwei brauche gerade vielleicht kommen mehr
    private Image ImageHitbox2;
    private int buttonSize = 100; //wie groß soll der button werden

    public HitboxenSelector(Grid grid) {
        this.grid = grid; //speichert das grid
        
        String pfad = "src/main/resources/assets/textures/gui/cursor/cursor1.png"; //pfad von dem bild gerade nur sword weil ich noch keins habe
        this.ImageHitbox = new ImageIcon(pfad).getImage(); //laden des bildes
        setPreferredSize(new Dimension(buttonSize + 20, buttonSize + 20)); //legt die größe des Panels fest von einem bild
        
        String pfad2 = "src/main/resources/assets/textures/gui/cursor/cursor2.png"; //pfad von dem bild gerade nur sword weil ich noch keins habe
        this.ImageHitbox2 = new ImageIcon(pfad2).getImage(); //laden des bildes
        setPreferredSize(new Dimension(buttonSize + 20, buttonSize + 20)); //legt die größe des Panels fest von einem bild
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ImageHitbox != null) { //eine prüfung ob das bild geladen hat
            g.drawImage(ImageHitbox, 10, 10, buttonSize, buttonSize, null); //wenn ja zeichent er es an diese stelle
        }
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(10, 10, buttonSize, buttonSize); //rahmen um das Image
        
        if (ImageHitbox2 != null) { //eine prüfung ob das bild geladen hat
            g.drawImage(ImageHitbox2, 120, 10, buttonSize, buttonSize, null); //wenn ja zeichent er es an diese stelle
        }
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(120, 10, buttonSize, buttonSize); //rahmen um das Image
    }
}