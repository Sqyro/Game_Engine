package TileMapEditor;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HitboxenSelector extends JPanel {
    private Grid grid;
    private int hitboxSize = 64;
    private static final int cols = 3;
    private static final int rows = 3;
    private int hitboxCount = 9;

    public HitboxenSelector(Grid grid) {
        this.grid = grid;
        grid.hitboxTextures = new Image[9]; //9 hitboxen
        loadHitboxen();
        setPreferredSize(new Dimension(530,900));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / hitboxSize;
                int row = e.getY() / hitboxSize;
                if (col >= 0 && col < cols && row >= 0 && row < rows) {
                    int index = row * cols + col; //welche hitbox angeklickt wurde
                    grid.setSelectedHitbox(index); //setzt die aktuelle hitbox
                    grid.repaint();
                }
            }
        });
    }

    private void loadHitboxen() {
        String pfad = "src/main/resources/assets/textures/gui/hitboxen_sheet.png";
        try {
            BufferedImage sheet = ImageIO.read(new File(pfad));
            int tileW = sheet.getWidth() / cols;  //breite eines feldes im sheet
            int tileH = sheet.getHeight() / rows; //höhe eines feldes im sheet
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int index = r * cols + c;
                    grid.hitboxTextures[index] = sheet.getSubimage(c * tileW, r * tileH, tileW, tileH);
                }
            }
        } catch (IOException e) {
            System.err.println("hitbox sheet nicht gefunden: " + pfad);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < hitboxCount; i++) {
            int col = (i) % cols; //berechnet spalte des objects in der objectselection
            int row = (i) / cols; //berechnet reihe des objects in der objectselection

            int x = col * hitboxSize; //x position auf der objectselection
            int y = row * hitboxSize; //y position auf der objectselection

            if (Grid.hitboxTextures[i] != null) { //wenn ein bild vorhanden ist zeichne es
                g.drawImage(Grid.hitboxTextures[i], x, y, hitboxSize, hitboxSize, null);
            } else { //sonst ist das feld grau
                g.setColor(Color.GRAY);
                g.fillRect(x, y, hitboxSize, hitboxSize);
            }

            g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
            g.drawRect(x, y, hitboxSize, hitboxSize); //rahmen um jedes object
        }
    }
}