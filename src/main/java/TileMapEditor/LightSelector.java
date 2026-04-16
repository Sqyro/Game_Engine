package TileMapEditor;

import javax.swing.*;
import java.awt.*;

public class LightSelector extends JPanel {
    private Grid grid; //Referenz
    private int IconSize = 100; //wie groß soll der button werden

    public LightSelector(Grid grid) {
        this.grid = grid; //speichert das Grid
        grid.lightTextures = new Image[1];
        
        String pfad = "src/main/resources/assets/textures/gui/light_gui.png"; //pfad von dem bild gerade nur sword weil ich noch keins habe
        grid.lightTextures[0] = new ImageIcon(pfad).getImage(); //laden des bildes
        setPreferredSize(new Dimension(IconSize + 20, IconSize + 20)); //legt die größe des Panels fest weil ich nur ein bild brauche
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grid.lightTextures[0] != null) { //eine prüfung ob das bild geladen hat
            g.drawImage(grid.lightTextures[0], 10, 10, IconSize, IconSize, null); //wenn ja zeichent er es an diese stelle
        }
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(10, 10, IconSize, IconSize); //rahmen um das Image
    }
}