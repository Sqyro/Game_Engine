package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileSelector extends JPanel {

    public static int tileSize = 64; //größe eines tiles in der Tileselection
    private int columns = 8; //anzahl der spalten pro reihe
    public static int tileCount; //wie viele tiles angezeigt werden

    private Grid grid; //Referenz

    public TileSelector(Grid grid) {
        this.grid = grid; //speichert das grid

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
            if (arrayindex < Grid.tiles.length && Grid.tiles[arrayindex] != null) { 
                g.drawImage(Grid.tiles[arrayindex], x, y, tileSize, tileSize, null);
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