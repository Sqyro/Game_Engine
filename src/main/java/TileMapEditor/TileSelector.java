package TileMapEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileSelector extends JPanel {
    private Grid grid; //Referenz
    public static int tileSize = 64; //größe eines tiles in der Tileselection
    private int columns = 8; //anzahl der spalten pro reihe
    public static int tileCount; //wie viele tiles angezeigt werden

    public TileSelector(Grid grid) {
        this.grid = grid; //speichert das grid
        grid.tileTextures = new Image[1000];
        loadTiles();
        this.setPreferredSize(new Dimension(530, 4000));

        addMouseListener(new MouseAdapter() { //Maus Listener / schaut an welches tile ich auf der tileselection gedrückt habe
            @Override
            public void mousePressed(MouseEvent e) {

                int col = e.getX() / tileSize; //spalte in der tileselection
                int row = e.getY() / tileSize; //reihe in der tileselection
                int index = row * columns + col; //berechnet tile id

                if (index < tileCount) {
                    grid.setSelectedTile(index); //setzt das ausgewählte tile auf dem grid
                    grid.repaint(); //repaint um neu zu zeichnen
                }
            }
        });
    }

    private void loadTiles() {
        int tileSizeloadTiles = 16;
        int[][] tilesheet = { //Tabelle für die sheets
                {9, 13, 17, 10, 1},   //Reihe 1
                {20, 21, 22, 14, 2}, //Reihe 2
                {16, 24, 23, 18, 3}, //Reihe 3
                {12, 19, 15, 11, 4}, //Reihe 4
                {5, 6, 7, 8, 0} //Reihe 5
        };

        int counter = 0; //zählt wie viele tiles bereits genommen wurden
        int i = 0;

        while (true) {
            String path = "src/main/resources/assets/textures/tiles/sheet" + i + ".png"; //bildpfad
            File file = new File(path);
            if (!file.exists()) { //wir schauen ob eine file existiert wenn nicht dann macht er garnichts mehr
                break;
            }

            try {
                BufferedImage sheet = ImageIO.read(file);

                for (int t = 1; t <= 24; t++) { //wir schauen ob t an der stelle in tilesheet liegt wenn ja ist das das nächste tile und so weiter
                    for (int r = 0; r < 5; r++) {
                        for (int c = 0; c < 5; c++) {
                            if (tilesheet[r][c] == t) {
                                int x = c * tileSizeloadTiles;
                                int y = r * tileSizeloadTiles;

                                grid.tileTextures[counter] = sheet.getSubimage(x, y, tileSizeloadTiles, tileSizeloadTiles);
                                counter++;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("tatütata fehler: " + path);
            }
            i++; //erhöht i also das nächste sheet
        }
        tileCount = counter; //updatet einfach den tileCounter von dem tileselector
        grid.updateMinimap();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < tileCount; i++) { 
            int col = (i) % columns; //berechnet spalte des tiles in der tileselection
            int row = (i) / columns; //berechnet reihe des tiles in der tileselection

            int x = col * tileSize; //x position auf der tileselection
            int y = row * tileSize; //y position auf der tileselection

            int arrayindex = i * 4; 

            // Prüfen, ob der Index im Array existiert und nicht null ist
            if (arrayindex < Grid.tileTextures.length && Grid.tileTextures[arrayindex] != null) { 
                g.drawImage(Grid.tileTextures[arrayindex], x, y, tileSize, tileSize, null);
            } else { 
                g.setColor(Color.GRAY);
                g.fillRect(x, y, tileSize, tileSize);
            }

            g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
            g.drawRect(x, y, tileSize, tileSize); //rahmen um jedes tile

            g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
            g.drawString("" + i, x + 8, y + 20); //Tile id links oben anzeigen
        }
    }
}