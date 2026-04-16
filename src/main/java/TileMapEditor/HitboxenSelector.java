package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HitboxenSelector extends JPanel {
    private Grid grid; //Referenz
    private int buttonSize = 100; //wie groß soll der button werden

    public HitboxenSelector(Grid grid) {
        this.grid = grid; //speichert das grid
        grid.hitboxTextures = new Image[2];
        
        String pfad = "src/main/resources/assets/textures/gui/roundhitbox_gui.png"; //pfad von dem bild gerade nur sword weil ich noch keins habe
        grid.hitboxTextures[0] = new ImageIcon(pfad).getImage(); //laden des bildes
        setPreferredSize(new Dimension(buttonSize + 20, buttonSize + 20)); //legt die größe des Panels fest von einem bild
        
        String pfad2 = "src/main/resources/assets/textures/gui/squarehitbox_gui.png"; //pfad von dem bild gerade nur sword weil ich noch keins habe
        grid.hitboxTextures[1] = new ImageIcon(pfad2).getImage(); //laden des bildes
        setPreferredSize(new Dimension(buttonSize + 20, buttonSize + 20)); //legt die größe des Panels fest von einem bild
    
        addMouseListener(new MouseAdapter() { //Maus Listener / schaut an welches tile ich auf der tileselection gedrückt habe
            @Override
            public void mousePressed(MouseEvent e) {

                int MouseX = e.getX(); //spalte in der tileselection
                int MouseY = e.getY(); //reihe in der tileselection

                if (MouseX > 10 && MouseX < 110 && MouseY > 10 && MouseY < 110) {
                    grid.setSelectedHitbox(0);
                } else if (MouseX > 120 && MouseX < 220 && MouseY > 10 && MouseY < 110) {
                    grid.setSelectedHitbox(1);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grid.hitboxTextures[0] != null) { //eine prüfung ob das bild geladen hat
            g.drawImage(grid.hitboxTextures[0], 10, 10, buttonSize, buttonSize, null); //wenn ja zeichent er es an diese stelle
        }
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(10, 10, buttonSize, buttonSize); //rahmen um das Image
        
        if (grid.hitboxTextures[1] != null) { //eine prüfung ob das bild geladen hat
            g.drawImage(grid.hitboxTextures[1], 120, 10, buttonSize, buttonSize, null); //wenn ja zeichent er es an diese stelle
        }
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(120, 10, buttonSize, buttonSize); //rahmen um das Image
    }
}