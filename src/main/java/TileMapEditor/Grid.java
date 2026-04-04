package TileMapEditor;
    
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import Shader.LightManager;
import Shader.PointLight;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import java.util.Arrays;

public class Grid extends JPanel {

    public int visibleRows = 8; //Anzahl der sichtbaren Reihen auf dem großen Grid
    public int visibleCols = 8; //Anzahl der sichtbaren Spalten auf dem großen Grid
    private int mapRows; //Gesamtanzahl der Reihen
    private int mapCols; //Gesamtanzahl der Spalten
    private int tileSize = 16; //Grid Tile Size, wie groß ein Tile ist
    private int festeGridSize = 800; //feste Größe für den sichtbaren großen GRid
    public int zoom = 2; //Zoom Faktor

    private int Xg = 0; //X Position des rechten Grids
    private int Yg = 0; //Y Position des rechten GRids
    
    private int cameraCols = 0; //sagt welche Spalte gerade links angezeigt wird
    private int cameraRows = 0; //sagt welche Reihe gerade links angezeigt wird
    
    private int[][] mapData; //2D Array, es speichert auf welcher Position der Tile ist
    public static Image[] tiles; //Array, es speichert alle Bilder von den Tiles als bestimmten Wert
    
    private int[][] physicsObject2D; //2D Array, es speichert auf welcher Position der Object ist
    public static Image[] physicsObject2DTextures; //Array, es speichert alle Bilder von den Objects als bestimmten Wert
    
    private int[][] hitboxen; //2D Array, es speichert auf welcher Position die Hitbox ist
    
    private PointLight[][] light; // Speichert das Objekt 
    
    private int currentTileType = 0; //welches tile ausgewählt ist
    private int currentRotation = 0; //aktuelle rotation (0-3)
    
    public int currentPhysicsObject2DTexture = 0; //aktuell ausgewähltes object
    
    private int currentHitbox = 0; //welche hitbox ausgewählt ist
    
    private int currentLight = 100; //welches licht ausgewählt ist
    
    private long timer = 0; //timer für die minimap
    
    private int mouseX = -100; //mausposition x
    private int mouseY = -100; //mausposition y

    private int oldMouseX = -100; //alte mausposition x
    private int oldMouseY = -100; //alte mausposition x 

    private int previewSize = 64; //größe des previews unten rechts von der maus
    private int offset = 20; //offset für das preview
    
    private Image minimapImage; //bild der minimap
    
    public int currentMode = 0; //der ausgewählte modus von den tabs (mode = 0 ist der tileselector usw.)
    
    private Color currentColor = Color.WHITE; //standardfarbe für das Licht
    
    private int currentTool = 0; //das gerade ausgewählte tool
    
    private Stack<int[][]> undoTiles = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie umzukehren
    private Stack<int[][]> redoTiles = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie wiederherzustellen wenn man etwas rückgangig gemacht hat
    private Stack<int[][]> undoObjects = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie umzukehren
    private Stack<int[][]> redoObjects = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie wiederherzustellen wenn man etwas rückgangig gemacht hat
    private java.util.Stack<java.util.List<PointLight>> undoLights = new java.util.Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie umzukehren
    private java.util.Stack<java.util.List<PointLight>> redoLights = new java.util.Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie wiederherzustellen wenn man etwas rückgangig gemacht hat
    private final int MAX_HISTORY = 67; //maximaler speicher der aktionen
    
    public void setSelectedTile(int id) { //methode um das aktuelle tile zu ändern
        currentTileType = id;
        currentRotation = 0; //neu ausgewähltes tile wird von der rotation auf 0 gesetzt
        repaint();
    }
    
    public void setSelectedPhysicsObject2D(int id) { //methode um das aktuelle object zu ändern
        currentPhysicsObject2DTexture = id;
        repaint();
    }
    
    public void setSelectedHitbox(int id) { //methode um das aktuelle hitbox zu ändern
        currentHitbox = id;
        repaint();
    }
    
    public void setSelectedLight(int id) { //methode um das aktuelle licht zu ändern
        currentLight = id;
        repaint();
    }

    public Grid(int rows, int cols, int TSize, int X, int Y) {
        mapRows = rows; //gesamtanzahl der reihen
        mapCols = cols; //gesamtanzahl der spalten
        tileSize = TSize; //tilegröße
        Xg = X; //offset größe vom grid x
        Yg = Y; //offset größe vom grid y
        
        mapData = new int[mapRows][mapCols]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r = 0; r<mapRows; r++)
            for(int c = 0; c<mapCols; c++)
                mapData[r][c] = 3; //default tile id (was die ganze map painted)
        
        physicsObject2D = new int[mapRows][mapCols]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r = 0; r < mapRows; r++)
            for(int c = 0; c < mapCols; c++)
                physicsObject2D[r][c] = -1; //default object id (was die ganze map painted)
        
        hitboxen = new int[mapRows][mapCols]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r = 0; r < mapRows; r++)
            for(int c = 0; c < mapCols; c++)
                hitboxen[r][c] = -1; //default object id (was die ganze map painted)
        
        light = new PointLight[mapRows][mapCols]; 
        for(int r = 0; r < mapRows; r++) {
            for(int c = 0; c < mapCols; c++) {
                light[r][c] = null; //default light(was die ganze map painted)
            }
        }
        
        loadTiles(); //tile bilder laden
        loadPhysicsObject2DTextures(); //physicsobject bilder laden
        
        //TestMap(); //Test Map, aber gerade auskommentiert, weil ich sie jetzt nicht brauche

        setPreferredSize(new Dimension(festeGridSize, festeGridSize)); //panelgröße
        setFocusable(true); //damit keylistener funktioniert
        
        addKeyListener(new KeyAdapter() { //Tastatur Listener / Steuerung der Kamera mit WASD / Rotation der Tiles
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    Camera(0, -5);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    Camera(0, 5);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    Camera(-5, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    Camera(5, 0);
                }
                
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    currentRotation = (currentRotation + 1) % 4;
                    }

                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    currentRotation = (currentRotation + 3) % 4;
                }
                if (e.getKeyCode() == KeyEvent.VK_F) { //Taste für den COlorChooser um Farbe des lichtes zu ändern
                    chooseColor();
                }
                if (e.getKeyCode() == KeyEvent.VK_P) { //Taste für Pencil
                    currentTool = 0;
                    CursorManager.updateCursor(currentTool);
                }
                if (e.getKeyCode() == KeyEvent.VK_B) { //Taste für Bucket
                    currentTool = 1;
                    CursorManager.updateCursor(currentTool);
                } 
                if (e.getKeyCode() == KeyEvent.VK_C) { //Taste für Pipette
                    currentTool = 2;
                    CursorManager.updateCursor(currentTool);
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) { //STRG+Z für undo
                    undo();
                }

                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) { //STRG+Y für redo
                    redo();
                }
                repaint();
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() { //Maus Listener / für das löschen und platzieren von Tiles mit der Maus
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX(); //aktuelle maus x speichern
                mouseY = e.getY(); //aktuelle maus x speichern

                switch (currentMode) {

                    case 0: //Tilemodus

                        int tileSizeGrid = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles
                        int mcols = (mouseX - Xg) / tileSizeGrid; //hier wird berechnet auf welcher Reihe die maus geklickt hat
                        int mrows = (mouseY - Yg) / tileSizeGrid; //hier wird berechnet auf welcher Spalte die maus geklickt hat

                        int worldCol = cameraCols + mcols; //gesamtspalten
                        int worldRow = cameraRows + mrows; //gesamtreihen

                        if (worldCol < 0 || worldCol >= mapCols || worldRow < 0 || worldRow >= mapRows){ //schaut nur ob der Klick außerhalb war wenn ja bricht er ab
                            break;
                        }
                        
                        if (currentTool == 0) { //Pencil
                            saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert

                            if (SwingUtilities.isRightMouseButton(e)) {
                                placeTile(mouseX, mouseY, -1); //wenn rechtsklick löscht er das tile
                            } else {
                                placeTile(mouseX, mouseY, currentTileType * 4 + currentRotation); //wenn linksklick platziert er das tile
                            }
                        }

                        if (currentTool == 1) { //Bucket
                            saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert

                            int tileid = mapData[worldRow][worldCol]; //nimmt das angeklickte tile
                            
                            int replace;
                            
                            if (SwingUtilities.isRightMouseButton(e)) {
                                replace = -1; //wenn rechtsklick löscht er das tile
                            } else {
                                replace = currentTileType * 4 + currentRotation; //wenn linksklick platziert er das tile
                            }
                            bucketFill(worldRow, worldCol, tileid, replace); //methode damit er jetzt alles fillen muss
                            repaint();
                            return;
                        }
                        
                        if (currentTool == 2) { //Pipette
                            pickTile(mouseX, mouseY); //methode für die pipette setzt das tile was angeklickt wurde zum aktuellen tile
                            repaint();
                            return;
                        }
                        
                        break;

                    case 1: //PhysicsObjects2d modus
                        
                        if (currentTool == 0) { //Pencil
                            saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert

                            if (SwingUtilities.isRightMouseButton(e)) {
                                placePhysicsObject2D(mouseX, mouseY, -1); //wenn rechtsklick löscht er das object
                            } else {
                                placePhysicsObject2D(mouseX, mouseY, currentPhysicsObject2DTexture); //wenn linksklick platziert er das object
                            }
                        }
                        
                        if (currentTool == 2) { //Pipette
                            pickObject(mouseX, mouseY); //methode für die pipette setzt das object was angeklickt wurde zum aktuellen object
                            repaint();
                            return;
                        }
                        
                        break;

                    case 2: //Lichtmodus
                        saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert
                        
                        if (SwingUtilities.isRightMouseButton(e)) {
                            placeLight(mouseX, mouseY, -1); //wenn rechtsklick löscht er das licht
                        } else {
                            placeLight(mouseX, mouseY, currentLight); //wenn linksklick platziert er das licht
                        }   
                        break;

                    default:
                        break;

                }
                repaint();
            } 
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                //sobald die maus ins fenster kommt sofort koordinaten setzen
                mouseX = e.getX();
                mouseY = e.getY();
                CursorManager.mouseX = e.getX();
                CursorManager.mouseY = e.getY();
                repaint(); 
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() { //Mausbewegung Listener / das ist einfach dazu da das man nicht jedes einzelne Tile alleine platzieren muss, sondern auch gedrückt halten kann um mehrere zu platzieren
            
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) { //fürs preview

                oldMouseX = mouseX; //speichert vorherige x position
                oldMouseY = mouseY; //speichert vorherige y position
                mouseX = e.getX(); //aktuelle maus x speichern
                mouseY = e.getY(); //aktuelle maus x speichern
                
                if (currentMode == 2) {
                    int tileSizelightpreview = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles
                    double lightScale = (double) tileSizelightpreview / 16.0; //skalierung um den zoom zu berücksichtigen

                    int r = (int) (currentLight * (lightScale / 4.0)) + 5; //radius

                    repaint(oldMouseX - r, oldMouseY - r, r * 2, r * 2); //erster repaint löscht den alten Kreis
                    repaint(mouseX - r, mouseY - r, r * 2, r * 2); //zweiter repaint fügt den neuen Kreis hinzu
                } else {
                    repaint(oldMouseX + offset - 5, oldMouseY + offset - 5, previewSize + 10, previewSize + 10); //erster repaint löscht das alte preview
                    repaint(mouseX + offset - 5, mouseY + offset - 5, previewSize + 10, previewSize + 10); //zweiter repaint fügt das neue preview hinzu
                }
                
                CursorManager.updateMouse(e.getX(), e.getY()); //hier wird einfach updateMouse aufgerufen was einfach die mausposition speichert und ändert
                repaint(CursorManager.oldMouseX + CursorManager.offsetX - 5, CursorManager.oldMouseY + CursorManager.offsetY - 5, CursorManager.cursorSize + 10, CursorManager.cursorSize + 10); //erster repaint löscht die alte custommaus
                repaint(CursorManager.mouseX + CursorManager.offsetX - 5, CursorManager.mouseY + CursorManager.offsetY - 5, CursorManager.cursorSize + 10, CursorManager.cursorSize + 10); //zweiter repaint fügt die neue custommaus hinzu
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                
                mouseX = e.getX(); //aktuelle maus x speichern
                mouseY = e.getY(); //aktuelle maus x speichern
                CursorManager.updateMouse(mouseX, mouseY); //hier wird einfach updateMouse aufgerufen was einfach die mausposition speichert und ändert
                repaint();
                
                if (currentTool != 0) { //nur der Stift darf draggen
                    return;
                }              
                
                mouseX = e.getX(); //aktuelle maus x speichern
                mouseY = e.getY(); //aktuelle maus x speichern
                CursorManager.updateMouse(mouseX, mouseY); //hier wird einfach updateMouse aufgerufen was einfach die mausposition speichert und ändert
                
                if (currentMode == 0) { //Tilemodus
                    if (SwingUtilities.isRightMouseButton(e)) {
                        placeTile(mouseX, mouseY, -1); //wenn rechtsklick löscht er das tile
                    } else {
                        placeTile(mouseX, mouseY, currentTileType * 4 + currentRotation); //wenn linksklick platziert er das tile
                    }
                } 
                else if (currentMode == 1) { //PhysicsObjects2d modus
                    if (SwingUtilities.isRightMouseButton(e)) {
                        placePhysicsObject2D(mouseX, mouseY, -1); //wenn rechtsklick löscht er das object
                    } else {
                        placePhysicsObject2D(mouseX, mouseY, currentPhysicsObject2DTexture); //wenn linksklick platziert er das object
                    }
                }
                repaint();
            }
        });
        
        addMouseWheelListener((MouseWheelEvent e) -> {
            if (currentMode == 2) { //lichtmodus
                currentLight -= e.getWheelRotation() * 10; //vergrößert den kreis pro wheel spin um 10 oder verringert ihn um 10
                
                if (currentLight < 10) currentLight = 10; //kreis darf nicht kleiner als indem fall 10 sein
                if (currentLight > 200) currentLight = 200; //kreis darf nicht größer als indem fall 200 sein
                
                repaint();
            } else { //restliche modi haben den normalen Zoom
                int rotation = e.getWheelRotation(); //gibt aus in welche richtung sich die mausgedreht hat 1 bei nach unten und -1 beim hochscrollen
                if (rotation < 0) {
                    visibleTiles(visibleRows / zoom, visibleCols / zoom); //bei -1 wird das sichtfeld geringer
                } else {
                    visibleTiles(visibleRows * zoom, visibleCols * zoom); //bei -1 wird das sichtfeld größer
                }
            }
        }); //Mausrad Listener / man kann das Mausrad benutzen um auf dem großen Grid zu zoomen
        CursorManager.deleteSystemMouse(this);
    }

    
    //paintComponent
    @Override
    protected void paintComponent(Graphics g) { //eine methode die alles auf ein panel zeichnet
        super.paintComponent(g);
        
        int tileSizeGrid = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles für das zoomen
        
        
        //Main Grid (große Grid)
        
        //tiles auf dem grid
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleCols; c++) {

                int worldCol = cameraCols + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                int x = Xg + c * tileSizeGrid;
                int y = Yg + r * tileSizeGrid;

                int tile = mapData[worldRow][worldCol]; //tile id an einer bestimmten position  

                if (tile >= 0 && tile < tiles.length && tiles[tile] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                    g.drawImage(tiles[tile], x, y, tileSizeGrid, tileSizeGrid, null); //tile bild zeichnen
                } else {
                    g.setColor(Color.GRAY); // wenn kein Tile da ist, graues Feld
                    g.fillRect(x, y, tileSizeGrid, tileSizeGrid);
                }

                g.setColor(Color.BLACK); //schwazre frabe für den tile rahmen
                g.drawRect(x, y, tileSizeGrid, tileSizeGrid); //rahmen um jedes tile
            }
        }
        
        
        //physicsObjects auf dem Grid
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleCols; c++) {

                int worldCol = cameraCols + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                int x = Xg + c * tileSizeGrid;
                int y = Yg + r * tileSizeGrid;

                int objectID = physicsObject2D[worldRow][worldCol];

                if (objectID >= 0 && objectID < physicsObject2DTextures.length && physicsObject2DTextures[objectID] != null) { //prüft die object id (muss ein object sein, das object muss im array liegen und es muss ein bild haben)
                    Image img = physicsObject2DTextures[objectID];
                    
                    double scale = (double) tileSizeGrid / (double) tileSize; //berechnung wie groß ein "pixeltile" ist
                    
                    int scaledWidth = (int) (img.getWidth(null) * scale); //berechnung der "echten" breite von dem object mit scale
                    int scaledHeight = (int) (img.getHeight(null) * scale); //berechnung der "echten" höhe von dem object mit scale
                    
                    int drawX = x - (scaledWidth - tileSizeGrid) / 2; //berechnung von x und y wo er es platzieren soll indem Fall mitte unten

                    int drawY = y - (scaledHeight - tileSizeGrid);
                    
                    g.drawImage(img, drawX, drawY, scaledWidth, scaledHeight, null);//hier zeichnet er das bild auf das jeweilige Tile
                }
                
            }
        }

        Graphics2D g2 = (Graphics2D) g;

        //licht auf dem Grid
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleCols; c++) {

                int worldCol = cameraCols + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                if (worldRow < mapRows && worldCol < mapCols && light[worldRow][worldCol] != null) { //prüft ob an der stelle ein licht existiert
                    PointLight pointlight = light[worldRow][worldCol];

                    int x = Xg + c * tileSizeGrid + (tileSizeGrid / 2); //berechnet die x position für die mitte des tiles
                    int y = Yg + r * tileSizeGrid + (tileSizeGrid / 2); //berechnet die y position für die mitte des tiles

                    double scale = (double) tileSizeGrid / 16.0;  //skalierungsfaktor damit es den zoom breücksichtigt
                    int radius = (int) (pointlight.getRange() * (scale / 4.0)); //berechnet den radius

                    if (radius > 0) { //pointlight benutzt werte zwischen 0.0 und 1.0 deswegen muss man die umrechnen
                        int red = Math.min(255, (int)(pointlight.getRed() * 255));
                        int green = Math.min(255, (int)(pointlight.getGreen() * 255));
                        int blue = Math.min(255, (int)(pointlight.getBlue() * 255));

                        g2.setColor(new Color(red, green, blue, 80)); //transparent ausgefüllt damit man noch was unter dem licht sieht
                        g2.fillOval(x - radius, y - radius, radius * 2, radius * 2);

                        g2.setColor(new Color(red, green, blue, 255)); //zeichnet den Rand aber ohne transparenz damit man sieht wo das ende ist
                        g2.drawOval(x - radius, y - radius, radius * 2, radius * 2);

                        g2.fillRect(x - 2, y - 2, 4, 4); //zeigt die mitte des kreises
                    }
                }
            }
        }

        //außenrahmen
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(Xg, Yg, festeGridSize, festeGridSize); //zeichent den äußeren Rahmen des gesamten großen Grids
        
        
        //Mini Grid
        int miniBreite = 400; //breite der minimap
        int miniHoehe = 400; //höhe der minimap
        int miniX = getWidth() - miniBreite - 67; //x position der minimap
        int miniY = 67; //y position der minimap

        double miniTileBreite = (double) miniBreite / mapCols; //berechnet die breite eines Tiles auf der minimap
        double miniTileHoehe = (double) miniHoehe / mapRows; //berechnet die breite eines Tiles auf der minimap

        if (minimapImage != null) {
            g.drawImage(minimapImage, miniX, miniY, null); //minimap zeichnen falls es sie gibt
        }

        g.setColor(Color.BLACK); //farbe des rahmens ist scharz
        g.drawRect(miniX, miniY, miniBreite, miniHoehe); //rahmen um die minimap
        
        
        //Kamera Rahmen auf Mini Grid
        g.setColor(Color.BLUE); //farbe des rahmens ist blau
        g.drawRect(miniX + (int)(cameraCols * miniTileBreite), miniY + (int)(cameraRows * miniTileHoehe), (int)(visibleCols * miniTileBreite), (int)(visibleRows * miniTileHoehe) //berechnet die x und y position auf der minimap sowie die höhe und die breite
);
        
        
        // Preview zeichnen
        int previewX = mouseX + offset; //x position der preview
        int previewY = mouseY + offset; //y position der preview
        switch (currentMode) {
            case 0:
                //Tile
                int tileIndex = currentTileType * 4 + currentRotation; //ausgewähltes tile eventuell mit rotierung
                if(tileIndex >= 0 && tileIndex < tiles.length && tiles[tileIndex] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                    g.drawImage(tiles[tileIndex], previewX, previewY, previewSize, previewSize, null); //tile preview zeichnen
                    g.setColor(Color.BLACK); //scharzer rahmen um das preview
                    g.drawRect(previewX, previewY, previewSize, previewSize);
                }   break;
            case 1:
                //PhysicsObjects2D
                int objectIndex = currentPhysicsObject2DTexture; //ausgewähltes object
                if(objectIndex >= 0 && objectIndex < physicsObject2DTextures.length && physicsObject2DTextures[objectIndex] != null) { //prüft die object id (muss ein tile sein, das object muss im array liegen und es muss ein bild haben)
                    g.drawImage(physicsObject2DTextures[objectIndex], previewX, previewY, previewSize, previewSize, null); //object preview zeichnen
                }   break;
            case 2:
                Graphics2D g2d = (Graphics2D) g;
                Color previewColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), 100); //zeichnet eine preview mit der transparenz 100 und der currentcolor
                g2d.setColor(previewColor);
                double scale = (double) tileSizeGrid / 16.0; //skalierungsfaktor damit es den zoom breücksichtigt
                int radius = (int) (currentLight * (scale / 4.0));  //berechnugn von dem radius
                g2d.fillOval(mouseX - radius, mouseY - radius, radius * 2, radius * 2); //zeichnet den previewkreis auf der maus
                break;
                
            default:
                break;
        }      
        CursorManager.draw(g); //methode für cursormanager damit sich die maus repainted beim bewegen sowie tool switchen
    }
    
    public void Camera(int cx, int cy) { //Kamera Movemnet für den Rahmen
        
    cameraCols += cx; //verschiebt die Kamera horizontal
    cameraRows += cy; //verschiebt die Kamera vertikal

    //hier wird einfach nur geschaut ob die Kamera außerhalb der Map geht wenn ja resettet sie sich zurück auf die psotion auf die sie vorher war
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
    
    repaint(); //und repaint damit das Grid neu gezeichnet wird nachdem sich die Kamera bewegt hat   
    
    }
    
    
    public void visibleTiles(int rows, int cols) {
        visibleRows = Math.max(8, Math.min(rows, 32));//von den sichtbaren Reihen kann man maximal 8 und maximal 32 und die sichtbaren Reihen ändern
        visibleCols = Math.max(8, Math.min(cols, 32));//von den sichtbaren Spalten kann man maximal 8 und maximal 32 und die sichtibaren Zeilen ändern
    
        //wenn beim zoomen die kamera theoretisch außerhalb der map wäre wird sie hier nochmal korrigert, sodass sie nicht außerhalb der map ist
    if (cameraCols > mapCols - visibleCols) {
        
        cameraCols = mapCols - visibleCols;
    }
    
    if (cameraRows > mapRows - visibleRows) {
        
        cameraRows = mapRows - visibleRows;
        
    }
    
    repaint(); //und repaint damit das Grid neu gezeichnet wird nachdem gezoomed wurde

    }
    
   public void printMap() { //eine methode um das ganze array auszugeben
        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapCols; c++) {
                System.out.print(mapData[r][c] + " "); //printet das ganze array 
            }
            System.out.println();
        }
    }
   
   private void TestMap() { //das war einfach eine methode für eine testmap, wo die eine hälfte unerschiedlich zu anderen hälfte war, um zu schauen ob die kamera sowie das zoomen funktioniert
        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapCols; c++) {

                if (c < mapCols / 2) { //die map wurde in zwei tiles einfach aufgeteilt
                    mapData[r][c] = 0;
                } else {
                    mapData[r][c] = 1;
                }

            }
        }
    } 
   
   private void loadTiles() {
        int tileSizeloadTiles = 16;
        int[][] mapsheet = { //Tabelle für die sheets
            {5, 9, 13, 6, 1},   // Reihe 1
            {16, 17, 18, 10, 2}, // Reihe 2
            {12, 20, 19, 14, 3}, // Reihe 3
            {8, 15, 11, 7, 4}    // Reihe 4
        };

        tiles = new Image[400];
        int counter = 0; //zählt wie viele tiles bereits genommen wurden
        int i = 0;

        while (true) {
            String path = "src/main/resources/assets/textures/tiles/Sheet" + i + ".png"; //bildpfad
            File file = new File(path);
            if (!file.exists()) { //wir schauen ob eine file existiert wenn nicht dann macht er garnichts mehr
                break;
            }

            try {
                BufferedImage sheet = ImageIO.read(file);

                for (int t = 1; t <= 20; t++) { //wir schauen ob t an der stelle in mapsheet liegt wenn ja ist das das nächste tile und so weiter
                    for (int r = 0; r < 4; r++) {
                        for (int c = 0; c < 5; c++) {
                            if (mapsheet[r][c] == t) {
                                int x = c * tileSizeloadTiles;
                                int y = r * tileSizeloadTiles;

                                tiles[counter] = sheet.getSubimage(x, y, tileSizeloadTiles, tileSizeloadTiles);
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
        TileSelector.tileCount = counter; //updatet einfach den tileCounter von dem tileselector
        updateMinimap();
        repaint();
    }

   private void loadPhysicsObject2DTextures() {
            int numObjects = 67; //anzahl der insgesamten objects
            
            physicsObject2DTextures = new Image[numObjects];  //gesamtanzahl der objects indem fall 67

            for (int i = 0; i < numObjects; i++) {
                String path = "src/main/resources/assets/textures/physicsObject2D/Object" + i + ".png"; //bildpfad
                
                physicsObject2DTextures[i] = new ImageIcon(path).getImage();
            }
        }
   
    private void placeTile(int mouseX, int mouseY, int tilenum) {

        int tileSizeGridTile = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles

        int mcols = (mouseX - Xg) / tileSizeGridTile; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrows = (mouseY - Yg) / tileSizeGridTile; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcols >= 0 && mcols < visibleCols && mrows >= 0 && mrows < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat

            int worldCol = cameraCols + mcols; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrows; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist

                if(mapData[worldRow][worldCol] != tilenum) { //setzt das Tile an diese stelle
                    mapData[worldRow][worldCol] = tilenum;
                    
                    int paintX = Xg + mcols * tileSizeGridTile;
                    int paintY = Yg + mrows * tileSizeGridTile;
                    
                    repaint(paintX, paintY, tileSizeGridTile, tileSizeGridTile); //repaint um map neu zu zeichnen
                    
                    if(System.currentTimeMillis() - timer > 50) { //repaint mit timer sonst kommt es einfach zu laggs
                        updateMinimap();
                        repaint(getWidth() - 467, 67, 400, 400);
                        timer = System.currentTimeMillis();
                    }
                }
            }
        }
    }
    
    private void placePhysicsObject2D(int mouseX, int mouseY, int objectNum) {

        int tileSizeGridObject = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles

        int mcol = (mouseX - Xg) / tileSizeGridObject; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrow = (mouseY - Yg) / tileSizeGridObject; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcol >= 0 && mcol < visibleCols && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int worldCol = cameraCols + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist
                if (physicsObject2D[worldRow][worldCol] != objectNum) { //setzt das object an diese stelle
                    physicsObject2D[worldRow][worldCol] = objectNum;

                    int paintX = Xg + mcol * tileSizeGridObject;
                    int paintY = Yg + mrow * tileSizeGridObject;

                    repaint(paintX, paintY, tileSizeGridObject, tileSizeGridObject); //repaint um map neu zu zeichnen
                    
                    if(System.currentTimeMillis() - timer > 50) { //repaint mit timer sonst kommt es einfach zu laggs
                        updateMinimap();
                        repaint(getWidth() - 467, 67, 400, 400);
                        timer = System.currentTimeMillis();
                    }
                }
            }
        }
    }
    
    private void placeLight(int mouseX, int mouseY, int radius) {
        
        int tileSizeGridLight = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles

        int mcol = (mouseX - Xg) / tileSizeGridLight; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrow = (mouseY - Yg) / tileSizeGridLight; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcol >= 0 && mcol < visibleCols && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int worldCol = cameraCols + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist

                    //umrechnung der Farben
                    float r = currentColor.getRed() / 255.0f;
                    float g = currentColor.getGreen() / 255.0f;
                    float b = currentColor.getBlue() / 255.0f;

                    PointLight newLight = new PointLight(worldCol + 0.5f, worldRow + 0.5f, r, g, b, (float)radius); //zeichnet ein neues licht in der mitte eines tiles

                    light[worldRow][worldCol] = newLight; //speichern im array

                    LightManager.AllPointLights.add(newLight);
                
                repaint();
            }
        }
    }
    
    public void exportMap() {
        JFileChooser chooser = new JFileChooser(); //öffnet ein Fenster für die auswahl
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //man kann nur ordner auswählen

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { //wenn man auf speichern drückt
            File folder = chooser.getSelectedFile(); 
            if (!folder.exists()) { //falls der ordner noch nicht existiert wird ein neuer erstellt
                folder.mkdirs();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //Gson objekt wird erstellt / PrettyPrinting sorgt dafür das die Json datei lesbar ist

            try {
                try (FileWriter writer = new FileWriter(new File(folder, "tiles.json"))) { //Speichern der tiles
                    gson.toJson(mapData, writer); //array wird in die json file geschrieben
                }
                try (FileWriter writer = new FileWriter(new File(folder, "objects.json"))) { //Speichern der objects
                    gson.toJson(physicsObject2D, writer); //array wird in die json file geschrieben
                }
                try (FileWriter writer = new FileWriter(new File(folder, "lights.json"))) { //Speichern der lichter
                    gson.toJson(LightManager.AllPointLights, writer); //gesamte liste von allpointlights wird in die json file geschrieben
                }
                System.out.println("exportiert :)"); //hier schaue ich nur ob es funktioniert hat
            } catch (JsonIOException | IOException e) {}
        }
    }

    public void importMap() {
        JFileChooser chooser = new JFileChooser(); //öffnet ein Fenster für die auswahl
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //man kann nur ordner auswählen

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //wenn man auf öffnen drückt
            File folder = chooser.getSelectedFile();
            Gson gson = new Gson(); //Gson objekt zum lesen

            try {
                mapData = gson.fromJson(new FileReader(new File(folder, "tiles.json")), int[][].class); //liest die datei und wandelt sie in einen array um wieder
                physicsObject2D = gson.fromJson(new FileReader(new File(folder, "objects.json")), int[][].class); //liest die datei und wandelt sie in einen array um wieder
                PointLight[] lights = gson.fromJson(new FileReader(new File(folder, "lights.json")), PointLight[].class); //gson erkennt keine liste deswegen speichern wir sie erstmal in ein normales array

                for (int r=0; r<mapRows; r++) { //hier wird erstmal die ganze alte Liste geleert
                    Arrays.fill(light[r], null);
                }
                LightManager.AllPointLights.clear(); //hier wird AllPointLights geleert

                for (int i = 0; i < lights.length; i++) { 

                    PointLight pointlight = lights[i]; //hier wird das aktuelle licht an der bestimmten stelle geholt

                    LightManager.AllPointLights.add(pointlight); //hier wird aktuelle licht in allPointLights hinzugefügt

                    int col = (int) pointlight.PosX; //berechnugn an welcher spalte es liegt
                    int row = (int) pointlight.PosY; //berechnugn an welcher reihe es liegt

                    if (row >= 0 && row < mapRows && col >= 0 && col < mapCols) { //prüfen ob es valide ist
                        light[row][col] = pointlight; //und hier setzen wir das licht an die richtige stelle im array
                    }
                }
                updateMinimap();
                repaint();
            } catch (JsonIOException | IOException e) {}
        }
    }
    
    public void resizeMap(int newSize) {
        
        int[][] newMap = new int[newSize][newSize]; //neues array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map tile ids kopieren
            for (int c = 0; c < newSize; c++) {
                if (r < mapRows && c < mapCols) {
                    newMap[r][c] = mapData[r][c];
                } else {
                    newMap[r][c] = 3;
                }
            }
        }
        
        int[][] newobjectMap = new int[newSize][newSize]; //neues array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map object ids kopieren
            for (int c = 0; c < newSize; c++) { 
                if (r < mapRows && c < mapCols) {
                    newobjectMap[r][c] = physicsObject2D[r][c];
                } else {
                    newobjectMap[r][c] = -1;
                }
            }
        }
        
        int[][] newhitboxenMap = new int[newSize][newSize]; //neues array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map hitboxen ids kopieren
            for (int c = 0; c < newSize; c++) { 
                if (r < mapRows && c < mapCols) {
                    newhitboxenMap[r][c] = hitboxen[r][c];
                } else {
                    newhitboxenMap[r][c] = -1;
                }
            }
        }
        
        PointLight[][] newlightMap = new PointLight[newSize][newSize]; //neues PointLight array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map light ids kopieren
            for (int c = 0; c < newSize; c++) { 
                if (r < mapRows && c < mapCols) {
                    newlightMap[r][c] = light[r][c];
                } else {
                    newlightMap[r][c] = null;
                }
            }
        }

        mapData = newMap; //ersetzt alte map durch die neue
        physicsObject2D = newobjectMap; //ersetzt alte object map durch die neue
        hitboxen = newhitboxenMap; //ersetzt alte map durch die neue
        light = newlightMap; //ersetzt alte map durch die neue
        mapRows = newSize; //die reihen aktualiesieren
        mapCols = newSize; //die spalten aktualisieren

        if (cameraCols > mapCols - visibleCols) cameraCols = mapCols - visibleCols; //hier wird dann nochmal die kamera angepasst indem fall die Spalte
        if (cameraRows > mapRows - visibleRows) cameraRows = mapRows - visibleRows; //hier wird dann nochmal die kamera angepasst indem fall die Reihe

        repaint(); //repaint nachdem vergrößern oder verkleinern der map
    }
    
    public void updateMinimap() {

        int miniBreite = 400; //feste breite der minimap
        int miniHoehe = 400; //feste höhe der minimap
        
        BufferedImage img = new BufferedImage(miniBreite, miniHoehe, BufferedImage.TYPE_INT_ARGB); //erstellt ein neues bild worauf ich die minimap zeichne
        Graphics g = img.getGraphics(); //fürs zeichnen auf das neue bild (canva)

        double miniTileBreite = (double) miniBreite / mapCols; //breite eines tiles sowie objects auf der minmap
        double miniTileHoehe = (double) miniHoehe / mapRows; //breite eines tiles sowie objects auf der minmap

        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapCols; c++) {

                int tileId = mapData[r][c]; //tile id auf einer bestimmten position
                if (tileId >= 0 && tileId < tiles.length && tiles[tileId] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                    g.drawImage(tiles[tileId], (int)(c * miniTileBreite), (int)(r * miniTileHoehe), (int)miniTileBreite + 1, (int)miniTileHoehe + 1, null); //zeichnet das tile auf der map
                } else { //sonst zeichnet er etwas graues
                    g.setColor(Color.GRAY);
                    g.fillRect((int)(c * miniTileBreite), (int)(r * miniTileHoehe), (int)miniTileBreite + 1, (int)miniTileHoehe + 1); 
                }

                int objId = physicsObject2D[r][c]; //object id auf einer bestimmten position
                if(objId >= 0 && objId < physicsObject2DTextures.length && physicsObject2DTextures[objId] != null) { //prüft die object id (muss ein tile sein, das object muss im array liegen und es muss ein bild haben)
                    g.drawImage(physicsObject2DTextures[objId], (int)(c * miniTileBreite), (int)(r * miniTileHoehe), (int)miniTileBreite + 1, (int)miniTileHoehe + 1, null); //zeichnet das object auf der map 
                }
            }
        }
        g.dispose(); //das zeichnen freigeben sonst kommt es zu speicherlecks
        minimapImage = img; //speichert die minimap damit paintcomponent es zeichnen kann
    }
    
    public void chooseColor() {
        Color selectedColor = JColorChooser.showDialog(this, "Wähle eine Farbe aus", currentColor); //öffnet den Farbwähler für die Lichter

        if (selectedColor != null) { //prüft nochmal ob es wirklich eine Farbe ist
            currentColor = selectedColor; //ersetzt die aktuelle farbe mit der neuen
            repaint();
        }
    }
    private void bucketFill(int startRow, int startCol, int tileid, int replace) {
        if (tileid == replace) return; //wenn die neue farbe genau der alten entspricht returned es einfach

        if (startRow < 0 || startRow >= mapRows || startCol < 0 || startCol >= mapCols) { //prüfen ob es innerhalb des Grids liegt
            return;
        }

        java.util.Stack<Point> stack = new java.util.Stack<>(); //erstellen des Stacks 
        stack.push(new Point(startRow, startCol)); //das tile was wir angeklickt haben wird zum startpunkt unseres Stacks(Stapels)

        while (!stack.isEmpty()) { //es wird solange ausgeführt bis es keine Punkte mehr auf dem stapel gibt
            Point p = stack.pop(); //hier wird der oberste punkt vom stapel runtergenommen
            int row = p.x; //spalte
            int col = p.y; //reihe

            if (row < 0 || row >= mapRows || col < 0 || col >= mapCols) { //prüfung ob der punkt innerhalb der map liegt
                continue;
            } 

            if (mapData[row][col] != tileid) { //wenn ein tile schon das neue tile ist oder es ein anderes ist wir das ignoriert
                continue;
            } 

            mapData[row][col] = replace; //hier wird das tile ersetzt mit dem tile was wir wollten

            //hier werden die nachbarn zum stack hinzugefügt damit die nochmal angeschaut werden
            stack.push(new Point(row + 1, col));
            stack.push(new Point(row - 1, col));
            stack.push(new Point(row, col + 1));
            stack.push(new Point(row, col - 1));
        }
        updateMinimap();
    }
    
    private void pickTile(int mouseX, int mouseY) {
        int tileSizepick = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles

        int mcols = (mouseX - Xg) / tileSizepick; //hier wird berechnet auf welcher reihe die maus geklickt hat
        int mrows = (mouseY - Yg) / tileSizepick; //hier wird berechnet auf welcher spalte die maus geklickt hat

        int worldCol = cameraCols + mcols; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat
        int worldRow = cameraRows + mrows; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat

        if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int tile = mapData[worldRow][worldCol]; //holt sich die tileid an dieser stelle

            if (tile >= 0) { //wenn ein gültiges tile existiert
                currentTileType = tile / 4; //schaut sich an welches Tile das ist ohne rotation
                currentRotation = tile % 4; //hiermit wird gesagt was für eine rotation es besitzt
            }
        }
    }
    private void pickObject(int mouseX, int mouseY) {
        int tileSizepick = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles

        int mcols = (mouseX - Xg) / tileSizepick; //hier wird berechnet auf welcher reihe die maus geklickt hat
        int mrows = (mouseY - Yg) / tileSizepick; //hier wird berechnet auf welcher spalte die maus geklickt hat

        int worldCol = cameraCols + mcols; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat
        int worldRow = cameraRows + mrows; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat

        if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int object = physicsObject2D[worldRow][worldCol]; //holt sich die objectid an dieser stelle
            
            if (object >= 0) { //wenn ein gültiges object existiert
                currentPhysicsObject2DTexture = object; //ersetzt das aktuelle object mit dem angeklickten
            }
        }
    }
    private int[][] copyMap(int[][] original) {
        int[][] copy = new int[original.length][original[0].length]; //erstellt ein leeres array mit der größe des originalem
        for (int i = 0; i < original.length; i++) { //alles wird kopiert vom originalem zum kopiertem array
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
    private void saveState() {
        //alles kopieren auf die undo Stapeln
        undoTiles.push(copyMap(mapData)); 
        undoObjects.push(copyMap(physicsObject2D));
        undoLights.push(new java.util.ArrayList<>(LightManager.AllPointLights));

        //redo wird gelöscht wenn man etwas neu zeichnet
        redoTiles.clear();
        redoObjects.clear();
        redoLights.clear();

        if (undoTiles.size() > MAX_HISTORY) { //wenn maximum erreicht wird wird das unterste im stapel gelöscht
            undoTiles.remove(0);
            undoObjects.remove(0);
            undoLights.remove(0);
        }
    }

    public void undo() {
        if (!undoTiles.isEmpty()) {
            
            //der jetzige zustand wird in redo gesichert
            redoTiles.push(copyMap(mapData));
            redoObjects.push(copyMap(physicsObject2D));
            redoLights.push(new java.util.ArrayList<>(LightManager.AllPointLights));

            //der letzte zustand von dem stapel nehmen  
            mapData = undoTiles.pop();
            physicsObject2D = undoObjects.pop();

            LightManager.AllPointLights.clear(); //liste wird komplett entfernt
            LightManager.AllPointLights.addAll(undoLights.pop()); //nimmt alle lichter von dem letzten zustand und fügt sie ein

            
            for (int r = 0; r < mapRows; r++) { //setzt jedes feld vom array auf null
                java.util.Arrays.fill(light[r], null);
            }
            
            for (int i = 0; i < LightManager.AllPointLights.size(); i++) { //jetzt gehen wir durch AllPointlights hindurch
                PointLight pointlight = LightManager.AllPointLights.get(i); //und holen das licht objekt an dieser bestimmten stelle
                int row = (int) pointlight.PosY; //spalte
                int col = (int) pointlight.PosX; //reihe
                
                if (row >= 0 && row < mapRows && col >= 0 && col < mapCols) { //prüfung ob es innerhalb des grids liegt
                    light[row][col] = pointlight; //hier wird das licht an der stelle platziert
                }
            }
            repaint();
            updateMinimap();
        }
    }

    public void redo() {
        if (!redoTiles.isEmpty()) {
            //der jetzige zustand wird in undo gesichert
            undoTiles.push(copyMap(mapData));
            undoObjects.push(copyMap(physicsObject2D));
            undoLights.push(new java.util.ArrayList<>(LightManager.AllPointLights));

            //der zukunfts zustand von dem stapel nehmen 
            mapData = redoTiles.pop();
            physicsObject2D = redoObjects.pop();

            LightManager.AllPointLights.clear(); //liste wird komplett entfernt
            LightManager.AllPointLights.addAll(redoLights.pop()); //nimmt alle lichter von dem zukunfts zustand und fügt sie ein

            
            for (int r = 0; r < mapRows; r++) { //setzt jedes feld vom array auf null
                java.util.Arrays.fill(light[r], null);
            }
            for (int i = 0; i < LightManager.AllPointLights.size(); i++) { //jetzt gehen wir durch AllPointlights hindurch
                PointLight pointlight = LightManager.AllPointLights.get(i); //und holen das licht objekt an dieser bestimmten stelle
                int row = (int) pointlight.PosY; //spalte
                int col = (int) pointlight.PosX; //reihe
                if (row >= 0 && row < mapRows && col >= 0 && col < mapCols) { //prüfung ob es innerhalb des grids liegt
                    light[row][col] = pointlight; //hier wird das licht an der stelle platziert
                }
            }
            repaint();
            updateMinimap();
        }
    }
}