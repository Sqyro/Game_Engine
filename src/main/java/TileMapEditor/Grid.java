package TileMapEditor;
    
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Grid extends JPanel {

    public int visibleRows = 8;
    public int visibleCols = 8;
    private int mapRows;
    private int mapCols;
    private int tileSizeG;
    private int festeGridSize = 800;
    public int zoom = 2;

    private int Xg;
    private int Yg;
    
    private int cameraCols = 0;
    private int cameraRows = 0;
    
    private int[][] mapData;
    public static Image[] tiles; 
    
    private int thisTile = 0;
    
    public void setSelectedTile(int id) {
        this.thisTile = id;
    }

    public Grid(int rows, int cols, int tileSize, int X, int Y) {
        mapRows = rows;
        mapCols = cols;
        tileSizeG = tileSize;
        Xg = X;
        Yg = Y;
        
        mapData = new int[mapRows][mapCols];
        TestMap();
        
        loadTiles();
        
        setPreferredSize(new Dimension(festeGridSize, festeGridSize));
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_W) Camera(0, -1);
                if (e.getKeyCode() == KeyEvent.VK_S) Camera(0, 1);
                if (e.getKeyCode() == KeyEvent.VK_A) Camera(-1, 0);
                if (e.getKeyCode() == KeyEvent.VK_D) Camera(1, 0);

            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {

                int tileSizeG = festeGridSize / visibleCols;

                int mcols = (e.getX() - Xg) / tileSizeG;
                int mrows = (e.getY() - Yg) / tileSizeG;

                if (mcols >= 0 && mcols < visibleCols && mrows >= 0 && mrows < visibleRows) {

                    int worldCol = cameraCols + mcols;
                    int worldRow = cameraRows + mrows;

                    if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) {

                        mapData[worldRow][worldCol] = thisTile;
                        repaint();
                    }
                }
            }
        });
    }

    private void TestMap() {
        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapCols; c++) {

                if (c < mapCols / 2) {
                    mapData[r][c] = 0;
                } else {
                    mapData[r][c] = 1;
                }

            }
        }
    }
    
    private void loadTiles() {
        int numTiles = 200;
        tiles = new Image[numTiles];

        for (int i = 0; i < numTiles; i++) {
            tiles[i] = Toolkit.getDefaultToolkit().getImage("src/main/resources/Tile_" + i + ".png").getScaledInstance(TileSelector.tileSize, TileSelector.tileSize, Image.SCALE_DEFAULT);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int tileSizeG = festeGridSize / visibleCols;
        
        //Main Grid
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleCols; c++) {

                int worldCols = cameraCols + c;
                int worldRows = cameraRows + r;
                
                int x = Xg + c * tileSizeG;
                int y = Yg + r * tileSizeG;
                
                int tile = mapData[worldRows][worldCols];
                
                if (tile >= 0 && tile < tiles.length && tiles[tile] != null) {
                    g.drawImage(tiles[tile], x, y, tileSizeG, tileSizeG, null);
                } else {
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, tileSizeG, tileSizeG);
                }
            }
        }
        
        //Mini Grid
        int miniBreite = 400;
        int miniHoehe = 400;
        int miniX = getWidth() - miniBreite - 67;
        int miniY = 67;

        double miniTileBreite = (double) miniBreite / mapCols;
        double miniTileHoehe = (double) miniHoehe / mapRows;

        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapCols; c++) {

                int tileId = mapData[r][c];

                if (tileId >= 0 && tileId < tiles.length && tiles[tileId] != null) {
                    g.drawImage(tiles[tileId],miniX + (int)(c * miniTileBreite),miniY + (int)(r * miniTileHoehe),(int)miniTileBreite + 1,(int)miniTileHoehe + 1,null);
                } else {
                    g.setColor(Color.GRAY);
                    g.fillRect(miniX + (int)(c * miniTileBreite),miniY + (int)(r * miniTileHoehe),(int)miniTileBreite + 1,(int)miniTileHoehe + 1);
                }
            }
        }

        //Rahmen auf Mini Grid
        g.setColor(Color.BLUE);
        g.drawRect(
                miniX + (int)(cameraCols * miniTileBreite),
                miniY + (int)(cameraRows * miniTileHoehe),
                (int)(visibleCols * miniTileBreite),
                (int)(visibleRows * miniTileHoehe)
        );
    }
    
    
    //Kamera Movemnet für den Rahmen
    public void Camera(int cx, int cy) {
        
    cameraCols += cx;
    cameraRows += cy;

    if (cameraCols < 0) {
        
        cameraCols = 0;
    }
    if (cameraCols > mapCols - visibleCols) {
        
        cameraCols = mapCols - visibleCols;
        
    }


    if (cameraRows < 0) {
        
        cameraRows = 0;   
    }
    if (cameraRows > mapRows - visibleRows) {
        
        cameraRows = mapRows - visibleRows;
        
    }
    
    repaint();
    
    }
    
    
    public void visibleTiles(int rows, int cols) {
        visibleRows = Math.max(8, Math.min(rows, 32));// Min/Max
        visibleCols = Math.max(8, Math.min(cols, 32));
    
    if (cameraCols > mapCols - visibleCols) {
        
        cameraCols = mapCols - visibleCols;
    }
    
    if (cameraRows > mapRows - visibleRows) {
        
        cameraRows = mapRows - visibleRows;
        
    }
    
    repaint();

    }
    
   public void printMap() {
        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapCols; c++) {
                System.out.print(mapData[r][c] + " ");
            }
            System.out.println();
        }
    }
    
}


