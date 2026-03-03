package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileSelector extends JPanel {

    public static int tileSize = 64;
    private int columns = 8;
    public static int tileCount = 200;

    private Grid grid;

    public TileSelector(Grid grid) {
        this.grid = grid;

        int rows = (int)Math.ceil((double)tileCount / columns);
        setPreferredSize(new Dimension(columns * tileSize, rows * tileSize));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int col = e.getX() / tileSize;
                int row = e.getY() / tileSize;
                int index = row * columns + col;

                if (index < tileCount) {
                    grid.setSelectedTile(index);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < tileCount; i++) {

            int col = i % columns;
            int row = i / columns;

            int x = col * tileSize;
            int y = row * tileSize;

            if (Grid.tiles[i] != null) {
                g.drawImage(Grid.tiles[i], x, y, tileSize, tileSize, null);
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, tileSize, tileSize);
            }
            
            g.setColor(Color.BLACK);
            g.drawRect(x, y, tileSize, tileSize);

            g.drawString("" + i, x + 8, y + 20);
        }
    }
}
