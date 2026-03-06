package TileMapEditor;
    
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


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
        //TestMap();
        
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
        
                if (SwingUtilities.isRightMouseButton(e)) {
                    placeTile(e.getX(), e.getY(), 0);
                } else {
                    placeTile(e.getX(), e.getY(), thisTile);
                }
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    placeTile(e.getX(), e.getY(), 0);
                } else {
                    placeTile(e.getX(), e.getY(), thisTile);
                }
            }
        });
        
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                int rotation = e.getWheelRotation();

                if(rotation < 0) {
                    visibleTiles(visibleRows / zoom, visibleCols / zoom);
                }
                else {
                    visibleTiles(visibleRows * zoom, visibleCols * zoom);
                }
            }
        });
        
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
                
                g.setColor(Color.BLACK);
                g.drawRect(x, y, tileSizeG, tileSizeG);
                
            }
        }
        
        g.setColor(Color.BLACK);
        g.drawRect(Xg, Yg, festeGridSize, festeGridSize);
        
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
        
        g.setColor(Color.BLACK);
        g.drawRect(miniX, miniY, miniBreite, miniHoehe);
        
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

            int numTiles = 100;     
            int numRotations = 4;   

            tiles = new Image[numTiles * numRotations];

            int counter = 0;

            for (int i = 0; i < numTiles; i++) {

                for (int r = 0; r < numRotations; r++) {

                    String path = "src/main/resources/assets/textures/tiles/Tile" + i + "_Rotated/Tile" + r + ".png";

                    tiles[counter] = Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(TileSelector.tileSize, TileSelector.tileSize, Image.SCALE_DEFAULT);

                    counter++;
                }
            }
        }
   
    private void placeTile(int mouseX, int mouseY, int tilenum) {

        int tileSizeG = festeGridSize / visibleCols;

        int mcols = (mouseX - Xg) / tileSizeG;
        int mrows = (mouseY - Yg) / tileSizeG;

        if (mcols >= 0 && mcols < visibleCols && mrows >= 0 && mrows < visibleRows) {

            int worldCol = cameraCols + mcols;
            int worldRow = cameraRows + mrows;

            if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) {

                if(mapData[worldRow][worldCol] != tilenum) {
                    mapData[worldRow][worldCol] = tilenum;
                    repaint();
                }
            }
        }
    }
    
    public void exportMap() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Speichern unter");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(fileToSave)) {
                for (int r = 0; r < mapRows; r++) {
                    for (int c = 0; c < mapCols; c++) {
                        pw.print(mapData[r][c]);
                        if (c < mapCols - 1) pw.print(" ");
                    }
                    pw.println();
                }
                System.out.println("Map gespeichert: " + fileToSave.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void importMap() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Datei öffnen");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (Scanner sc = new Scanner(fileToOpen)) {
                int r = 0;
                while (sc.hasNextLine() && r < mapRows) {
                    String line = sc.nextLine();
                    String[] tokens = line.trim().split("\\s+");
                    for (int c = 0; c < Math.min(tokens.length, mapCols); c++) {
                        mapData[r][c] = Integer.parseInt(tokens[c]);
                    }
                    r++;
                }
                repaint();
                System.out.println("Map geladen: " + fileToOpen.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void resizeMap(int newSize) {
        int[][] newMap = new int[newSize][newSize];

        for (int r = 0; r < Math.min(mapRows, newSize); r++) {
            for (int c = 0; c < Math.min(mapCols, newSize); c++) {
                newMap[r][c] = mapData[r][c];
            }
        }

        for (int r = 0; r < newSize; r++) {
            for (int c = 0; c < newSize; c++) {
                if (newMap[r][c] == 0) newMap[r][c] = 0;
            }
        }
        
        mapData = newMap;
        mapRows = newSize;
        mapCols = newSize;

        if (cameraCols > mapCols - visibleCols) cameraCols = mapCols - visibleCols;
        if (cameraRows > mapRows - visibleRows) cameraRows = mapRows - visibleRows;

        repaint();
    }
}