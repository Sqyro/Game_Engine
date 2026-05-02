package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import Shader.LightManager;
import Shader.LightEmitters.PointLight;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import Physics2D.*;

public class Grid extends JPanel {

    public int visibleRows = 8; //Anzahl der sichtbaren Reihen auf dem großen Grid
    public int visibleColumns = 8; //Anzahl der sichtbaren Spalten auf dem großen Grid
    
    private int mapRows = 128; //Gesamtanzahl der Reihen
    private int mapColumns = 128; //Gesamtanzahl der Spalten
    
    private int tileSize = 16; //Grid Tile Size, wie groß ein Tile ist
    
    private int bigMapSize = 800; //feste Größe für den sichtbaren großen GRid
    
    public int zoom = 2; //Zoom Faktor

    private int bigMapOffsetPosX = 100; //X Position des rechten Grids
    private int bigMapOffsetPosY = 100; //Y Position des rechten Grids
    
    private int cameraColumns = 0; //sagt welche Spalte gerade links angezeigt wird
    private int cameraRows = 0; //sagt welche Reihe gerade links angezeigt wird
    
    private int[][] mapTiles; //2D Array, es speichert auf welcher Position der Tile ist
    public static Image[] tileTextures; //Array, es speichert alle Bilder von den Tiles als bestimmten Wert

    public String[][] mapObjects; //2D Array, es speichert auf welcher Position der Object ist
    public static Image[] ObjectTextures; //Array, es speichert alle Bilder von den Objects als bestimmten Wert
    public String[] objectNames = new String[100];

    private BoxCollider[][] boxHitboxen; //speichert die hitboxen
    public static Image[] hitboxTextures;
    
    private PointLight[][] light; //speichert die lights 
    public static Image[] lightTextures;

    public static Image[] randomizationTexture;
    
    private int currentTile = 0; //welches tile ausgewählt ist
    private int currentTileRotation = 0; //aktuelle rotation (0-3)
    
    public int currentObject = 0; //aktuell ausgewähltes object

    public int currentHitbox = 4;
    
    private int currentLight = 100; //welches licht ausgewählt ist     ///note an mich currentLight und currentLightRange zu seperieren

    private long timerBigMap = 0; //timer für die minimap
    private long timerMiniMap = 0; //timer für die minimap
    
    private int mousePosX = -100; //mausposition x
    private int mousePosY = -100; //mausposition y

    private int oldMousePosX = -100; //alte mausposition x
    private int oldMousePosY = -100; //alte mausposition x

    private int lastRow = -1;
    private int lastCol = -1;

    private int previewSize = 64; //größe des previews unten rechts von der maus
    private int previewOffset = 20; //offset für das preview
    
    private Image minimapImage; //bild der minimap
    
    public int currentTab = 0; //der ausgewählte modus von den tabs (mode = 0 ist der tileselector usw.)
    
    private Color currentColor = Color.WHITE; //standardfarbe für das Licht
    
    private int currentTool = 0; //das gerade ausgewählte tool

    private boolean randomization = false;
    Random r = new Random();
    
    private Stack<int[][]> undoTiles = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie umzukehren
    private Stack<int[][]> redoTiles = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie wiederherzustellen wenn man etwas rückgangig gemacht hat
    private Stack<String[][]> undoObjects = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie umzukehren
    private Stack<String[][]> redoObjects = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie wiederherzustellen wenn man etwas rückgangig gemacht hat
    private Stack<List<PointLight>> undoLights = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie umzukehren
    private Stack<List<PointLight>> redoLights = new Stack<>(); //das ist sozusagen ein Stapel, wo er die letzten indem fall 67 aktionen speichert, um sie wiederherzustellen wenn man etwas rückgangig gemacht hat
    private Stack<BoxCollider[][]> undoHitboxen = new Stack<>();
    private Stack<BoxCollider[][]> redoHitboxen = new Stack<>();
    private final int maximumStacks = 67; //maximaler speicher der aktionen
    
    public void setSelectedTile(int id) { //methode um das aktuelle tile zu ändern
        currentTile = id;
        currentTileRotation = 0; //neu ausgewähltes tile wird von der rotation auf 0 gesetzt
        repaint();
    }
    
    public void setSelectedPhysicsObject2D(int id) { //methode um das aktuelle object zu ändern
        currentObject = id;
        repaint();
    }

    public void setSelectedHitbox(int id) { //methode um das aktuelle object zu ändern
        currentHitbox = id;
        repaint();
    }

    public Grid() {
        
        mapTiles = new int[mapRows][mapColumns]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r = 0; r<mapRows; r++)
            for(int c = 0; c < mapColumns; c++)
                mapTiles[r][c] = 3; //default tile id (was die ganze map painted)
        
        mapObjects = new String[mapRows][mapColumns]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r = 0; r < mapRows; r++)
            for(int c = 0; c < mapColumns; c++)
                mapObjects[r][c] = null; //default object id (was die ganze map painted)

        light = new PointLight[mapRows][mapColumns]; 
        for(int r = 0; r < mapRows; r++) {
            for(int c = 0; c < mapColumns; c++) {
                light[r][c] = null; //default light(was die ganze map painted)
            }
        }

        boxHitboxen = new BoxCollider[mapRows][mapColumns]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r = 0; r < mapRows; r++)
            for(int c = 0; c < mapColumns; c++)
                boxHitboxen[r][c] = null; //default object id (was die ganze map painted)

        setPreferredSize(new Dimension(bigMapSize, bigMapSize)); //panelgröße
        setFocusable(true); //damit keylistener funktioniert
        
        addKeyListener(new KeyAdapter() { //Tastatur Listener / Steuerung der Kamera mit WASD / Rotation der Tiles
            @Override
            public void keyPressed(KeyEvent e) {

                int c = e.getKeyCode();

                if (c == KeyEvent.VK_W) {
                    Camera(0, -5);
                }
                if (c == KeyEvent.VK_S) {
                    Camera(0, 5);
                }
                if (c == KeyEvent.VK_A) {
                    Camera(-5, 0);
                }
                if (c == KeyEvent.VK_D) {
                    Camera(5, 0);
                }

                if (c == KeyEvent.VK_E && currentTab == 0) {
                    currentTileRotation = (currentTileRotation + 1) % 4;
                }
                if (c == KeyEvent.VK_Q) {
                    currentTileRotation = (currentTileRotation + 3) % 4;
                }
                if (c == KeyEvent.VK_F) { //Taste für den COlorChooser um Farbe des lichtes zu ändern
                    chooseColor();
                }
                if (c == KeyEvent.VK_P) { //Taste für Pencil
                    currentTool = 0;
                    CursorManager.updateCursor(currentTool);
                }
                if (c == KeyEvent.VK_B) { //Taste für Bucket
                    currentTool = 1;
                    CursorManager.updateCursor(currentTool);
                } 
                if (c == KeyEvent.VK_C) { //Taste für Pipette
                    currentTool = 2;
                    CursorManager.updateCursor(currentTool);
                }
                if (e.isControlDown() && c == KeyEvent.VK_Z) { //STRG+Z für undo
                    undo();
                }
                if (e.isControlDown() && c == KeyEvent.VK_Y) { //STRG+Y für redo
                    redo();
                }
                if (c == KeyEvent.VK_R) {
                    randomization = !randomization;
                }
                repaint();
            }
        });
        
        addMouseListener(new MouseAdapter() { //Maus Listener / für das löschen und platzieren von Tiles mit der Maus
            @Override
            public void mousePressed(MouseEvent e) {
                mousePosX = e.getX(); //aktuelle maus x speichern
                mousePosY = e.getY(); //aktuelle maus x speichern
                int tileSizeGrid = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles
                int mcols = (mousePosX - bigMapOffsetPosX) / tileSizeGrid; //hier wird berechnet auf welcher Reihe die maus geklickt hat
                int mrows = (mousePosY - bigMapOffsetPosY) / tileSizeGrid; //hier wird berechnet auf welcher Spalte die maus geklickt hat

                int worldCol = cameraColumns + mcols; //gesamtspalten
                int worldRow = cameraRows + mrows; //gesamtreihen
                switch (currentTab) {

                    case 0: //Tilemodus

                        if (worldCol < 0 || worldCol >= mapColumns || worldRow < 0 || worldRow >= mapRows){ //schaut nur ob der Klick außerhalb war wenn ja bricht er ab
                            break;
                        }
                        
                        if (currentTool == 0) { //Pencil
                            saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert

                            if (SwingUtilities.isRightMouseButton(e)) {
                                placeTile(mousePosX, mousePosY, -1); //wenn rechtsklick löscht er das tile
                            } else {
                                placeTile(mousePosX, mousePosY, currentTile * 4 + currentTileRotation); //wenn linksklick platziert er das tile
                            }
                        }

                        if (currentTool == 1) { //Bucket
                            saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert

                            int tileid = mapTiles[worldRow][worldCol]; //nimmt das angeklickte tile
                            
                            int replace;
                            
                            if (SwingUtilities.isRightMouseButton(e)) {
                                replace = -1; //wenn rechtsklick löscht er das tile
                            } else {
                                replace = currentTile * 4 + currentTileRotation; //wenn linksklick platziert er das tile
                            }
                            bucketFill(worldRow, worldCol, tileid, replace); //methode damit er jetzt alles fillen muss

                            repaint(); //repaint um map neu zu zeichnen
                            return;
                        }
                        
                        if (currentTool == 2) { //Pipette
                            pickTile(mousePosX, mousePosY); //methode für die pipette setzt das tile was angeklickt wurde zum aktuellen tile
                            repaint();
                            return;
                        }
                        
                        break;

                    case 1: //PhysicsObjects2d modus
                        
                        if (currentTool == 0) { //Pencil
                            saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert

                            if (SwingUtilities.isRightMouseButton(e)) {
                                placePhysicsObject2D(mousePosX, mousePosY, null); //wenn rechtsklick löscht er das object
                            } else {
                                placePhysicsObject2D(mousePosX, mousePosY, objectNames[currentObject]); //wenn linksklick platziert er das object
                            }
                        }
                        
                        if (currentTool == 2) { //Pipette
                            pickObject(mousePosX, mousePosY); //methode für die pipette setzt das object was angeklickt wurde zum aktuellen object
                            repaint();
                            return;
                        }

                        break;

                    case 2: //Lichtmodus
                        saveState(); //methode für redo und undo, am anfang speichert er den jetzigen zustand bevor sich etwas ändert
                        
                        if (SwingUtilities.isRightMouseButton(e)) {
                            placeLight(mousePosX, mousePosY, -1); //wenn rechtsklick löscht er das licht
                        } else {
                            placeLight(mousePosX, mousePosY, currentLight); //wenn linksklick platziert er das licht
                        }   
                        break;

                    case 3:

                        if (currentTool == 0) {
                            saveState();

                            if (SwingUtilities.isRightMouseButton(e)) {
                                placeHitbox(mousePosX, mousePosY, -1);
                            } else {
                                placeHitbox(mousePosX, mousePosY, currentHitbox);
                            }
                        }
                        if (currentTool == 2) { //Pipette
                            pickHitbox(mousePosX, mousePosY);
                            repaint();
                            break;
                        }
                        break;
                }
                repaint(bigMapOffsetPosX + mcols * tileSizeGrid, bigMapOffsetPosY + mrows * tileSizeGrid, tileSizeGrid, tileSizeGrid);
            } 
            
            @Override
            public void mouseEntered(MouseEvent e) {
                //sobald die maus ins fenster kommt sofort koordinaten setzen
                mousePosX = e.getX();
                mousePosY = e.getY();
                CursorManager.mouseX = e.getX();
                CursorManager.mouseY = e.getY();
                repaint(); 
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() { //Mausbewegung Listener / das ist einfach dazu da das man nicht jedes einzelne Tile alleine platzieren muss, sondern auch gedrückt halten kann um mehrere zu platzieren
            
            @Override
            public void mouseMoved(MouseEvent e) { //fürs preview

                oldMousePosX = mousePosX; //speichert vorherige x position
                oldMousePosY = mousePosY; //speichert vorherige y position
                mousePosX = e.getX(); //aktuelle maus x speichern
                mousePosY = e.getY(); //aktuelle maus x speichern
                if (currentTab == 2) {
                    int tileSizelightpreview = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles
                    double lightScale = (double) tileSizelightpreview / 16.0; //skalierung um den zoom zu berücksichtigen
                    int radiuslight = (int) (currentLight * (lightScale / 4.0)) + 5; //radius
                    repaint(oldMousePosX - radiuslight, oldMousePosY - radiuslight, radiuslight * 2, radiuslight * 2); //erster repaint löscht den alten Kreis
                    repaint(mousePosX - radiuslight, mousePosY - radiuslight, radiuslight * 2, radiuslight * 2); //zweiter repaint fügt den neuen Kreis hinzu
                } else {
                    repaint(oldMousePosX + previewOffset - 5, oldMousePosY + previewOffset - 5, previewSize + 10, previewSize + 10); //erster repaint löscht das alte preview
                    repaint(mousePosX + previewOffset - 5, mousePosY + previewOffset - 5, previewSize + 10, previewSize + 10); //zweiter repaint fügt das neue preview hinzu
                }
                
                CursorManager.updateMouse(e.getX(), e.getY()); //hier wird einfach updateMouse aufgerufen was einfach die mausposition speichert und ändert
                repaint(CursorManager.oldMouseX + CursorManager.offsetX - 5, CursorManager.oldMouseY + CursorManager.offsetY - 5, CursorManager.cursorSize + 10, CursorManager.cursorSize + 10); //erster repaint löscht die alte custommaus
                repaint(CursorManager.mouseX + CursorManager.offsetX - 5, CursorManager.mouseY + CursorManager.offsetY - 5, CursorManager.cursorSize + 10, CursorManager.cursorSize + 10); //zweiter repaint fügt die neue custommaus hinzu
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                mousePosX = e.getX(); //aktuelle maus x speichern
                mousePosY = e.getY(); //aktuelle maus x speichern
                CursorManager.updateMouse(mousePosX, mousePosY); //hier wird einfach updateMouse aufgerufen was einfach die mausposition speichert und ändert

                int tileSizeGridLight = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles
                int mcol = (mousePosX - bigMapOffsetPosX) / tileSizeGridLight; //hier wird berechnet auf welcher Spalte die maus geklickt hat
                int mrow = (mousePosY - bigMapOffsetPosY) / tileSizeGridLight; //hier wird berechnet auf welcher Reihe die maus geklickt hat
                
                mousePosX = e.getX(); //aktuelle maus x speichern
                mousePosY = e.getY(); //aktuelle maus x speichern
                CursorManager.updateMouse(mousePosX, mousePosY); //hier wird einfach updateMouse aufgerufen was einfach die mausposition speichert und ändert
                
                if (currentTab == 0) { //Tilemodus
                    if (currentTool == 0) {
                        if (mcol >= 0 && mcol < visibleColumns && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
                            int worldCol = cameraColumns + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
                            int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

                            if (worldRow != lastRow || worldCol != lastCol) {
                                if (SwingUtilities.isRightMouseButton(e)) {
                                    placeTile(mousePosX, mousePosY, -1); //wenn rechtsklick löscht er das tile
                                } else {
                                    placeTile(mousePosX, mousePosY, currentTile * 4 + currentTileRotation); //wenn linksklick platziert er das tile
                                }
                                lastRow = worldRow;
                                lastCol = worldCol;
                            }
                        }
                    }
                } 
                else if (currentTab == 1) { //PhysicsObjects2d modus
                    if (mcol >= 0 && mcol < visibleColumns && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
                            int worldCol = cameraColumns + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
                            int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

                            if (worldRow != lastRow || worldCol != lastCol) {
                                if (SwingUtilities.isRightMouseButton(e)) {
                                    placePhysicsObject2D(mousePosX, mousePosY, null); //wenn rechtsklick löscht er das object
                                } else {
                                    placePhysicsObject2D(mousePosX, mousePosY, objectNames[currentObject]); //wenn linksklick platziert er das object
                                }
                                lastRow = worldRow;
                                lastCol = worldCol;
                            }
                        }
                }
                else if (currentTab == 3) { //Hitbox modus
                    if (SwingUtilities.isRightMouseButton(e)) {
                        placeHitbox(mousePosX, mousePosY, -1); //wenn rechtsklick löscht er das hitbox
                    } else {
                        placeHitbox(mousePosX, mousePosY, currentHitbox); //wenn linksklick platziert er das hitbox
                    }
                }
                repaint();
            }
        });
        
        addMouseWheelListener((MouseWheelEvent e) -> {
            if (currentTab == 2) { //lichtmodus
                currentLight -= e.getWheelRotation() * 10; //vergrößert den kreis pro wheel spin um 10 oder verringert ihn um 10
                
                if (currentLight < 10) {
                    currentLight = 10;
                } //kreis darf nicht kleiner als indem fall 10 sein
                if (currentLight > 200) {
                    currentLight = 200;
                } //kreis darf nicht größer als indem fall 200 sein
                
                repaint();
            } else { //restliche modi haben den normalen Zoom
                int rotation = e.getWheelRotation(); //gibt aus in welche richtung sich die mausgedreht hat 1 bei nach unten und -1 beim hochscrollen
                if (rotation < 0) {
                    visibleTiles(visibleRows / zoom, visibleColumns / zoom); //bei -1 wird das sichtfeld geringer
                } else {
                    visibleTiles(visibleRows * zoom, visibleColumns * zoom); //bei -1 wird das sichtfeld größer
                }
            }
        }); //Mausrad Listener / man kann das Mausrad benutzen um auf dem großen Grid zu zoomen
        CursorManager.deleteSystemMouse(this);
    }

    
    //paintComponent
    @Override
    protected void paintComponent(Graphics g) { //eine methode die alles auf ein panel zeichnet
        super.paintComponent(g);

        int tileSizeGrid = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles für das zoomen

        //Main Grid (große Grid)

        //tiles auf dem grid
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleColumns; c++) {

                int worldCol = cameraColumns + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                int x = bigMapOffsetPosX + c * tileSizeGrid;
                int y = bigMapOffsetPosY + r * tileSizeGrid;

                int tile = mapTiles[worldRow][worldCol]; //tile id an einer bestimmten position  

                if (tile >= 0 && tile < tileTextures.length && tileTextures[tile] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                    g.drawImage(tileTextures[tile], x, y, tileSizeGrid, tileSizeGrid, null); //tile bild zeichnen
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
            for (int c = 0; c < visibleColumns; c++) {

                int worldCol = cameraColumns + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                int x = bigMapOffsetPosX + c * tileSizeGrid;
                int y = bigMapOffsetPosY + r * tileSizeGrid;

                String objectName = mapObjects[worldRow][worldCol];

                if (objectName != null) {
                    int objectID = java.util.Arrays.asList(objectNames).indexOf(objectName);
                    if (objectID < 0 || objectID >= ObjectTextures.length) continue;
                    Image img = ObjectTextures[objectID];

                    double scale = (double) tileSizeGrid / (double) tileSize; //berechnung wie groß ein "pixeltile" ist

                    int scaledWidth = (int) (img.getWidth(null) * scale); //berechnung der "echten" breite von dem object mit scale
                    int scaledHeight = (int) (img.getHeight(null) * scale); //berechnung der "echten" höhe von dem object mit scale

                    int drawX = x - (scaledWidth - tileSizeGrid) / 2; //berechnung von x und y wo er es platzieren soll indem Fall mitte unten

                    int drawY = y - (scaledHeight - tileSizeGrid);

                    g.drawImage(img, drawX, drawY, scaledWidth, scaledHeight, null);//hier zeichnet er das bild auf das jeweilige Tile
                }

            }
        }

        //licht auf dem Grid
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleColumns; c++) {

                int worldCol = cameraColumns + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                if (worldRow < mapRows && worldCol < mapColumns && light[worldRow][worldCol] != null) { //prüft ob an der stelle ein licht existiert
                    PointLight pointlight = light[worldRow][worldCol];

                    int x = bigMapOffsetPosX + c * tileSizeGrid + (tileSizeGrid / 2); //berechnet die x position für die mitte des tiles
                    int y = bigMapOffsetPosY + r * tileSizeGrid + (tileSizeGrid / 2); //berechnet die y position für die mitte des tiles

                    double scale = (double) tileSizeGrid / 16.0;  //skalierungsfaktor damit es den zoom breücksichtigt
                    int radius = (int) (pointlight.Range * (scale / 4.0)); //berechnet den radius

                    if (radius > 0) { //pointlight benutzt werte zwischen 0.0 und 1.0 deswegen muss man die umrechnen
                        int red = Math.min(255, (int) (pointlight.getRed() * 255));
                        int green = Math.min(255, (int) (pointlight.getGreen() * 255));
                        int blue = Math.min(255, (int) (pointlight.getBlue() * 255));

                        g.setColor(new Color(red, green, blue, 80)); //transparent ausgefüllt damit man noch was unter dem licht sieht
                        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);

                        g.setColor(new Color(red, green, blue, 255)); //zeichnet den Rand aber ohne transparenz damit man sieht wo das ende ist
                        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);

                        g.fillRect(x - 2, y - 2, 4, 4); //zeigt die mitte des kreises
                    }
                }
            }
        }

        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleColumns; c++) {

                int worldCol = cameraColumns + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                if (worldRow < mapRows && worldCol < mapColumns && boxHitboxen[worldRow][worldCol] != null) { //prüft ob an der stelle ein licht existiert
                    BoxCollider boxCollider = boxHitboxen[worldRow][worldCol];

                    float offsetX = boxCollider.PosX - (int) boxCollider.PosX;
                    float offsetY = boxCollider.PosY - (int) boxCollider.PosY;
                    int x = bigMapOffsetPosX + c * tileSizeGrid + (int) (offsetX * tileSizeGrid);
                    int y = bigMapOffsetPosY + r * tileSizeGrid + (int) (offsetY * tileSizeGrid);
                    int w = (int) (boxCollider.Length * tileSizeGrid);
                    int h = (int) (boxCollider.Height * tileSizeGrid);

                    g.setColor(Color.RED);
                    g.drawRect(x, y, w, h);
                }
            }
        }

        //außenrahmen
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(bigMapOffsetPosX, bigMapOffsetPosY, bigMapSize, bigMapSize); //zeichent den äußeren Rahmen des gesamten großen Grids


        //Mini Grid
        int miniBreite = 400; //breite der minimap
        int miniHoehe = 400; //höhe der minimap
        int miniX = getWidth() - miniBreite - 67; //x position der minimap
        int miniY = 67; //y position der minimap

        double miniTileBreite = (double) miniBreite / mapColumns; //berechnet die breite eines Tiles auf der minimap
        double miniTileHoehe = (double) miniHoehe / mapRows; //berechnet die breite eines Tiles auf der minimap

        if (minimapImage != null) {
            g.drawImage(minimapImage, miniX, miniY, null); //minimap zeichnen falls es sie gibt
        }

        g.setColor(Color.BLACK); //farbe des rahmens ist scharz
        g.drawRect(miniX, miniY, miniBreite, miniHoehe); //rahmen um die minimap


        //Kamera Rahmen auf Mini Grid
        g.setColor(Color.BLUE); //farbe des rahmens ist blau
        g.drawRect(miniX + (int) (cameraColumns * miniTileBreite), miniY + (int) (cameraRows * miniTileHoehe), (int) (visibleColumns * miniTileBreite), (int) (visibleRows * miniTileHoehe) //berechnet die x und y position auf der minimap sowie die höhe und die breite
        );


        // Preview zeichnen
        int previewX = mousePosX + previewOffset; //x position der preview
        int previewY = mousePosY + previewOffset; //y position der preview
        switch (currentTab) {
            case 0:
                randomizationTexture = new Image[1];
                String pfad = "src/main/resources/assets/textures/gui/randomIcon.png"; //pfad von dem bild gerade nur sword weil ich noch keins habe
                randomizationTexture[0] = new ImageIcon(pfad).getImage(); //laden des bildes
                //Tile
                int tileIndex = currentTile * 4 + currentTileRotation; //ausgewähltes tile eventuell mit rotierung
                if (tileIndex >= 0 && tileIndex < tileTextures.length && tileTextures[tileIndex] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                    g.drawImage(tileTextures[tileIndex], previewX, previewY, previewSize, previewSize, null); //tile preview zeichnen
                    g.setColor(Color.BLACK); //scharzer rahmen um das preview
                    g.drawRect(previewX, previewY, previewSize, previewSize);
                }
                if (randomization){
                    g.drawImage(randomizationTexture[0], previewX, previewY, previewSize, previewSize, null);
                }
                break;
            case 1:
                //PhysicsObjects2D
                int objectIndex = currentObject; //ausgewähltes object
                if (objectIndex >= 0 && objectIndex < ObjectTextures.length && ObjectTextures[objectIndex] != null) { //prüft die object id (muss ein tile sein, das object muss im array liegen und es muss ein bild haben)
                    g.drawImage(ObjectTextures[objectIndex], previewX, previewY, previewSize, previewSize, null); //object preview zeichnen
                }
                break;
            case 2:
                Color previewColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), 100); //zeichnet eine preview mit der transparenz 100 und der currentcolor
                g.setColor(previewColor);
                double scale = (double) tileSizeGrid / 16.0; //skalierungsfaktor damit es den zoom breücksichtigt
                int radius = (int) (currentLight * (scale / 4.0));  //berechnugn von dem radius
                g.fillOval(mousePosX - radius, mousePosY - radius, radius * 2, radius * 2); //zeichnet den previewkreis auf der maus
                break;
            case 3:
                g.drawImage(hitboxTextures[currentHitbox], previewX, previewY, previewSize, previewSize, null);
                g.setColor(Color.BLACK);
                g.drawRect(previewX, previewY, previewSize, previewSize);
                break;
            default:
                break;
        }
        CursorManager.draw(g); //methode für cursormanager damit sich die maus repainted beim bewegen sowie tool switchen
    }
    
    public void Camera(int cameraX, int cameraY) { //Kamera Movemnet für den Rahmen
        
    cameraColumns += cameraX; //verschiebt die Kamera horizontal
    cameraRows += cameraY; //verschiebt die Kamera vertikal

    //hier wird einfach nur geschaut ob die Kamera außerhalb der Map geht wenn ja resettet sie sich zurück auf die psotion auf die sie vorher war
    if (cameraColumns < 0) {
        cameraColumns = 0;
    }
    
    if (cameraColumns > mapColumns - visibleColumns) {
        cameraColumns = mapColumns - visibleColumns;
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
        visibleColumns = Math.max(8, Math.min(cols, 32));//von den sichtbaren Spalten kann man maximal 8 und maximal 32 und die sichtibaren Zeilen ändern
    
        //wenn beim zoomen die kamera theoretisch außerhalb der map wäre wird sie hier nochmal korrigert, sodass sie nicht außerhalb der map ist
    if (cameraColumns > mapColumns - visibleColumns) {
        
        cameraColumns = mapColumns - visibleColumns;
    }
    
    if (cameraRows > mapRows - visibleRows) {
        
        cameraRows = mapRows - visibleRows;
        
    }
    
    repaint(); //und repaint damit das Grid neu gezeichnet wird nachdem gezoomed wurde

    }
   
    private void placeTile(int mouseX, int mouseY, int tilenum) {

        int tileSizeGridTile = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles

        int mcols = (mouseX - bigMapOffsetPosX) / tileSizeGridTile; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrows = (mouseY - bigMapOffsetPosY) / tileSizeGridTile; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcols >= 0 && mcols < visibleColumns && mrows >= 0 && mrows < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat

            int worldCol = cameraColumns + mcols; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrows; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapColumns && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist

                    if (randomization){
                        int randomrotation = r.nextInt(4);
                        int tile = tilenum - tilenum % 4;
                        int randomtilenum = tile + randomrotation;
                        mapTiles[worldRow][worldCol] = randomtilenum;
                    } else {
                        mapTiles[worldRow][worldCol] = tilenum;
                    }

                    int paintX = bigMapOffsetPosX + mcols * tileSizeGridTile;
                    int paintY = bigMapOffsetPosY + mrows * tileSizeGridTile;

                    if(System.currentTimeMillis() - timerBigMap > 50) {
                        repaint(paintX, paintY, tileSizeGridTile, tileSizeGridTile); //repaint um map neu zu zeichnen
                        timerBigMap = System.currentTimeMillis();
                    }
                    if(System.currentTimeMillis() - timerMiniMap > 50) { //repaint mit timer sonst kommt es einfach zu laggs
                        updateMinimap();
                        repaint(getWidth() - 467, 67, 400, 400);
                        timerMiniMap = System.currentTimeMillis();
                    }
            }
        }
    }
    
    private void placePhysicsObject2D(int mouseX, int mouseY, String objectName) {

        int tileSizeGridObject = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles

        int mcol = (mouseX - bigMapOffsetPosX) / tileSizeGridObject; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrow = (mouseY - bigMapOffsetPosY) / tileSizeGridObject; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcol >= 0 && mcol < visibleColumns && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int worldCol = cameraColumns + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapColumns && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist
                    mapObjects[worldRow][worldCol] = objectName;

                    int paintX = mcol * tileSizeGridObject;
                    int paintY = mrow * tileSizeGridObject;
                    
                    if(System.currentTimeMillis() - timerBigMap > 50) {
                    repaint(paintX, paintY, tileSizeGridObject, tileSizeGridObject); //repaint um map neu zu zeichnen
                    timerBigMap = System.currentTimeMillis();
                    }
                    if(System.currentTimeMillis() - timerMiniMap > 50) { //repaint mit timer sonst kommt es einfach zu laggs
                        updateMinimap();
                        repaint(getWidth() - 467, 67, 400, 400);
                        timerMiniMap = System.currentTimeMillis();

                }
            }
        }
    }
    
        private void placeLight(int mouseX, int mouseY, int radius) {

            int tileSizeGridLight = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles

            int mcol = (mouseX - bigMapOffsetPosX) / tileSizeGridLight; //hier wird berechnet auf welcher Spalte die maus geklickt hat
            int mrow = (mouseY - bigMapOffsetPosY) / tileSizeGridLight; //hier wird berechnet auf welcher Reihe die maus geklickt hat

            if (mcol >= 0 && mcol < visibleColumns && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
                int worldCol = cameraColumns + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
                int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

                if (worldCol >= 0 && worldCol < mapColumns && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist

                    //umrechnung der Farben
                    float r = currentColor.getRed() / 255.0f;
                    float g = currentColor.getGreen() / 255.0f;
                    float b = currentColor.getBlue() / 255.0f;

                    PointLight newLight = new PointLight(worldCol + 0.5f, worldRow + 0.5f, r, g, b, (float)radius); //zeichnet ein neues licht in der mitte eines tiles

                    PointLight oldlight = light[worldRow][worldCol];
                    if (oldlight != null) {
                        LightManager.AllPointLights.remove(oldlight);
                    }
                    light[worldRow][worldCol] = newLight;
                    LightManager.AllPointLights.add(newLight);

                    repaint();
                }
            }
        }

    private void placeHitbox(int mouseX, int mouseY, int currentHitboxId) {

        int tileSizeGridLight = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles

        int mcol = (mouseX - bigMapOffsetPosX) / tileSizeGridLight; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrow = (mouseY - bigMapOffsetPosY) / tileSizeGridLight; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcol >= 0 && mcol < visibleColumns && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int worldCol = cameraColumns + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapColumns && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist

                if (currentHitboxId == -1) {
                    BoxCollider oldhitbox = boxHitboxen[worldRow][worldCol];
                    CollisionManager.AllBoxColliders.remove(oldhitbox); //aus liste entfernen
                    boxHitboxen[worldRow][worldCol] = null;
                } else {
                    float[] size = getHitboxSize(currentHitboxId);
                    BoxCollider newBox = new BoxCollider(worldCol + size[2], worldRow + size[3], size[0], size[1]);
                    BoxCollider oldhitbox = boxHitboxen[worldRow][worldCol];
                    if (oldhitbox != null) {
                        CollisionManager.AllBoxColliders.remove(oldhitbox);
                    }
                    boxHitboxen[worldRow][worldCol] = newBox;
                    CollisionManager.AllBoxColliders.add(newBox);
                }
                repaint();
            }
        }
    }

    private float[] getHitboxSize(int index) {
        //height, length, offsetX, offsetY
        switch (index) {
            case 0: return new float[]{0.5f, 0.5f, 0.5f, 0.5f};
            case 1: return new float[]{0.5f, 1f, 0f, 0.5f};
            case 2: return new float[]{0.5f, 0.5f, 0f, 0.5f};
            case 3: return new float[]{1f, 0.5f, 0.5f, 0f};
            case 4: return new float[]{1f, 1f, 0f, 0f};
            case 5: return new float[]{1f, 0.5f, 0f, 0f};
            case 6: return new float[]{0.5f, 0.5f, 0.5f, 0f};
            case 7: return new float[]{0.5f, 1f, 0f, 0f};
            case 8: return new float[]{0.5f, 0.5f, 0f, 0f};
            default: return new float[]{0f, 0f, 0f, 0f};
        }
    }
    
    public void exportMap() {
        JFileChooser chooser = new JFileChooser(); //öffnet ein Fenster für die auswahl
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //man kann nur ordner auswählen

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { //wenn man auf speichern drückt
            File folder = chooser.getSelectedFile();

            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //Gson objekt wird erstellt / PrettyPrinting sorgt dafür das die Json datei lesbar ist

            try {
                try (FileWriter writer = new FileWriter(new File(folder, "tiles.json"))) { //Speichern der tiles
                    gson.toJson(mapTiles, writer); //array wird in die json file geschrieben
                }
                try (FileWriter writer = new FileWriter(new File(folder, "objects.json"))) { //Speichern der objects
                    gson.toJson(mapObjects, writer); //array wird in die json file geschrieben
                }
                try (FileWriter writer = new FileWriter(new File(folder, "lights.json"))) { //Speichern der lichter
                    gson.toJson(LightManager.AllPointLights, writer); //gesamte liste von allpointlights wird in die json file geschrieben
                }
                try (FileWriter writer = new FileWriter(new File(folder, "hitboxen.json"))) {
                    gson.toJson(CollisionManager.AllBoxColliders, writer); //gesamte liste von allboxcolliders wird in die json file geschrieben
                }
                System.out.println("exportiert :)"); //hier schaue ich nur ob es funktioniert hat
            } catch (JsonIOException | IOException ignored) {}
        }
    }

    public void importMap() {
        JFileChooser chooser = new JFileChooser(); //öffnet ein Fenster für die auswahl
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //man kann nur ordner auswählen

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //wenn man auf öffnen drückt
            File folder = chooser.getSelectedFile();
            Gson gson = new Gson(); //Gson objekt zum lesen

            try {
                mapTiles = gson.fromJson(new FileReader(new File(folder, "tiles.json")), int[][].class); //liest die datei und wandelt sie in einen array um wieder
                mapObjects = gson.fromJson(new FileReader(new File(folder, "objects.json")), String[][].class); //liest die datei und wandelt sie in einen array um wieder
                PointLight[] lights = gson.fromJson(new FileReader(new File(folder, "lights.json")), PointLight[].class); //gson erkennt keine liste deswegen speichern wir sie erstmal in ein normales array
                BoxCollider[] hitboxen = gson.fromJson(new FileReader(new File(folder, "hitboxen.json")), BoxCollider[].class);

                for (int r=0; r<mapRows; r++) { //hier wird erstmal die ganze alte Liste geleert
                    Arrays.fill(light[r], null);
                }
                LightManager.AllPointLights.clear(); //hier wird AllPointLights geleert

                for (int i = 0; i < lights.length; i++) {

                    PointLight pointlight = lights[i]; //hier wird das aktuelle licht an der bestimmten stelle geholt

                    LightManager.AllPointLights.add(pointlight); //hier wird aktuelle licht in allPointLights hinzugefügt

                    int col = (int) pointlight.PosX; //berechnugn an welcher spalte es liegt
                    int row = (int) pointlight.PosY; //berechnugn an welcher reihe es liegt

                    if (row >= 0 && row < mapRows && col >= 0 && col < mapColumns) { //prüfen ob es valide ist
                        light[row][col] = pointlight; //und hier setzen wir das licht an die richtige stelle im array
                    }
                }

                for (int i = 0; i < hitboxen.length; i++) {

                    BoxCollider boxcollider = hitboxen[i]; //hier wird die aktuelle boxcollider an der bestimmten stelle geholt

                    CollisionManager.AllBoxColliders.add(boxcollider); //hier wird die boxcollider in AllBoxColliders hinzugefügt

                    int col = (int) boxcollider.PosX; //berechnung an welcher spalte es liegt
                    int row = (int) boxcollider.PosY; //berechnung an welcher reihe es liegt

                    if (row >= 0 && row < mapRows && col >= 0 && col < mapColumns) { //prüfen ob es valide ist
                        boxHitboxen[row][col] = boxcollider; //und hier setzen wir die boxcollider an die richtige stelle im array
                    }
                }

                updateMinimap();
                repaint();
            } catch (JsonIOException | IOException ignored) {}
            repaint();
        }
    }
    
    public void resizeMap(int newSize) {
        
        int[][] newMap = new int[newSize][newSize]; //neues array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map tile ids kopieren
            for (int c = 0; c < newSize; c++) {
                if (r < mapRows && c < mapColumns) {
                    newMap[r][c] = mapTiles[r][c];
                } else {
                    newMap[r][c] = 3;
                }
            }
        }
        
        String[][] newobjectMap = new String[newSize][newSize]; //neues array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map object ids kopieren
            for (int c = 0; c < newSize; c++) { 
                if (r < mapRows && c < mapColumns) {
                    newobjectMap[r][c] = mapObjects[r][c];
                } else {
                    newobjectMap[r][c] = null;
                }
            }
        }

        BoxCollider[][] newBoxHitboxenMap = new BoxCollider[newSize][newSize]; //neues array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map hitboxen ids kopieren
            for (int c = 0; c < newSize; c++) {
                if (r < mapRows && c < mapColumns) {
                    newBoxHitboxenMap[r][c] = boxHitboxen[r][c];
                } else {
                    newBoxHitboxenMap[r][c] = null;
                }
            }
        }
        
        PointLight[][] newlightMap = new PointLight[newSize][newSize]; //neues PointLight array für die map erstellen

        for (int r = 0; r < newSize; r++) { //alte map light ids kopieren
            for (int c = 0; c < newSize; c++) { 
                if (r < mapRows && c < mapColumns) {
                    newlightMap[r][c] = light[r][c];
                } else {
                    newlightMap[r][c] = null;
                }
            }
        }

        mapTiles = newMap; //ersetzt alte map durch die neue
        mapObjects = newobjectMap; //ersetzt alte object map durch die neue
        boxHitboxen = newBoxHitboxenMap; //ersetzt alte map durch die neue
        light = newlightMap; //ersetzt alte map durch die neue
        mapRows = newSize; //die reihen aktualiesieren
        mapColumns = newSize; //die spalten aktualisieren

        if (cameraColumns > mapColumns - visibleColumns) cameraColumns = mapColumns - visibleColumns; //hier wird dann nochmal die kamera angepasst indem fall die Spalte
        if (cameraRows > mapRows - visibleRows) cameraRows = mapRows - visibleRows; //hier wird dann nochmal die kamera angepasst indem fall die Reihe

        repaint(); //repaint nachdem vergrößern oder verkleinern der map
    }
    
    public void updateMinimap() {

        int miniBreite = 400; //feste breite der minimap
        int miniHoehe = 400; //feste höhe der minimap
        
        BufferedImage img = new BufferedImage(miniBreite, miniHoehe, BufferedImage.TYPE_INT_ARGB); //erstellt ein neues bild worauf ich die minimap zeichne
        Graphics g = img.getGraphics(); //fürs zeichnen auf das neue bild (canva)

        double miniTileBreite = (double) miniBreite / mapColumns; //breite eines tiles sowie objects auf der minmap
        double miniTileHoehe = (double) miniHoehe / mapRows; //breite eines tiles sowie objects auf der minmap

        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapColumns; c++) {

                int tileId = mapTiles[r][c]; //tile id auf einer bestimmten position
                if (tileId >= 0 && tileId < tileTextures.length && tileTextures[tileId] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                    g.drawImage(tileTextures[tileId], (int)(c * miniTileBreite), (int)(r * miniTileHoehe), (int)miniTileBreite + 1, (int)miniTileHoehe + 1, null); //zeichnet das tile auf der map
                } else { //sonst zeichnet er etwas graues
                    g.setColor(Color.GRAY);
                    g.fillRect((int)(c * miniTileBreite), (int)(r * miniTileHoehe), (int)miniTileBreite + 1, (int)miniTileHoehe + 1); 
                }

                String objectName = mapObjects[r][c];
                if (objectName != null) {
                    int objectID = java.util.Arrays.asList(objectNames).indexOf(objectName);
                    if (objectID >= 0 && objectID < ObjectTextures.length && ObjectTextures[objectID] != null) {
                        g.drawImage(ObjectTextures[objectID], (int)(c * miniTileBreite), (int)(r * miniTileHoehe), (int)miniTileBreite + 1, (int)miniTileHoehe + 1, null);
                    }
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

        if (startRow < 0 || startRow >= mapRows || startCol < 0 || startCol >= mapColumns) { //prüfen ob es innerhalb des Grids liegt
            return;
        }

        Stack<Point> stack = new Stack<>(); //erstellen des Stacks
        stack.push(new Point(startRow, startCol)); //das tile was wir angeklickt haben wird zum startpunkt unseres Stacks(Stapels)

        while (!stack.isEmpty()) { //es wird solange ausgeführt bis es keine Punkte mehr auf dem stapel gibt
            Point p = stack.pop(); //hier wird der oberste punkt vom stapel runtergenommen
            int row = p.x; //spalte
            int col = p.y; //reihe

            if (row < 0 || row >= mapRows || col < 0 || col >= mapColumns) { //prüfung ob der punkt innerhalb der map liegt
                continue;
            } 

            if (mapTiles[row][col] != tileid) { //wenn ein tile schon das neue tile ist oder es ein anderes ist wir das ignoriert
                continue;
            } 
                if (randomization){
                    int randomrotation = r.nextInt(4);
                    int tile = replace - replace % 4;
                    int randomtilenum = tile + randomrotation;
                    mapTiles[row][col] = randomtilenum;
                } else {
                    mapTiles[row][col] = replace; //hier wird das tile ersetzt mit dem tile was wir wollten
                }
            

            //hier werden die nachbarn zum stack hinzugefügt damit die nochmal angeschaut werden
            stack.push(new Point(row + 1, col));
            stack.push(new Point(row - 1, col));
            stack.push(new Point(row, col + 1));
            stack.push(new Point(row, col - 1));
        }
        updateMinimap();
    }
    
    private void pickTile(int mouseX, int mouseY) {
        int tileSizepick = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles

        int mcols = (mouseX - bigMapOffsetPosX) / tileSizepick; //hier wird berechnet auf welcher reihe die maus geklickt hat
        int mrows = (mouseY - bigMapOffsetPosY) / tileSizepick; //hier wird berechnet auf welcher spalte die maus geklickt hat

        int worldCol = cameraColumns + mcols; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat
        int worldRow = cameraRows + mrows; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat

        if (worldCol >= 0 && worldCol < mapColumns && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int tile = mapTiles[worldRow][worldCol]; //holt sich die tileid an dieser stelle

            if (tile >= 0) { //wenn ein gültiges tile existiert
                currentTile = tile / 4; //schaut sich an welches Tile das ist ohne rotation
                currentTileRotation = tile % 4; //hiermit wird gesagt was für eine rotation es besitzt
            }
        }
    }
    private void pickObject(int mouseX, int mouseY) {
        int tileSizepick = bigMapSize / visibleColumns; //hier berechne ich die größe eines Tiles

        int mcols = (mouseX - bigMapOffsetPosX) / tileSizepick; //hier wird berechnet auf welcher reihe die maus geklickt hat
        int mrows = (mouseY - bigMapOffsetPosY) / tileSizepick; //hier wird berechnet auf welcher spalte die maus geklickt hat

        int worldCol = cameraColumns + mcols; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat
        int worldRow = cameraRows + mrows; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat

        if (worldCol >= 0 && worldCol < mapColumns && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            String object = mapObjects[worldRow][worldCol]; //holt sich die objectid an dieser stelle

            objectNames[currentObject] = object; //ersetzt das aktuelle object mit dem angeklickten
        }
    }
    private void pickHitbox(int mouseX, int mouseY) {
        int tileSizepick = bigMapSize / visibleColumns;

        int mcols = (mouseX - bigMapOffsetPosX) / tileSizepick;
        int mrows = (mouseY - bigMapOffsetPosY) / tileSizepick;

        int worldCol = cameraColumns + mcols;
        int worldRow = cameraRows + mrows;

        if (worldCol >= 0 && worldCol < mapColumns && worldRow >= 0 && worldRow < mapRows) {
            if (boxHitboxen[worldRow][worldCol] != null) {
                BoxCollider picked = boxHitboxen[worldRow][worldCol];
                for (int i = 1; i <= 9; i++) {
                    float[] size = getHitboxSize(i);
                    if (size[0] == picked.Height && size[1] == picked.Length) {
                        currentHitbox = i;
                        break;
                    }
                }
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

    private String[][] copyObjectMap(String[][] original) {
        String[][] copy = new String[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    private BoxCollider[][] copyHitboxMap(BoxCollider[][] original) {
        BoxCollider[][] copy = new BoxCollider[original.length][original[0].length];
        for (int r = 0; r < original.length; r++) {
            for (int c = 0; c < original[0].length; c++) {
                if (original[r][c] != null) {
                    BoxCollider boxCollider = original[r][c];
                    copy[r][c] = new BoxCollider(boxCollider.PosX, boxCollider.PosY, boxCollider.Height, boxCollider.Length);
                }
            }
        }
        return copy;
    }
    private void saveState() {
        //alles kopieren auf die undo Stapeln
        undoTiles.push(copyMap(mapTiles));
        undoObjects.push(copyObjectMap(mapObjects));
        undoLights.push(new ArrayList<>(LightManager.AllPointLights));
        undoHitboxen.push(copyHitboxMap(boxHitboxen));

        //redo wird gelöscht wenn man etwas neu zeichnet
        redoTiles.clear();
        redoObjects.clear();
        redoLights.clear();
        redoHitboxen.clear();

        if (undoTiles.size() > maximumStacks) { //wenn maximum erreicht wird wird das unterste im stapel gelöscht
            undoTiles.remove(0);
            undoObjects.remove(0);
            undoLights.remove(0);
            undoHitboxen.remove(0);
        }
    }

    public void undo() {
        if (!undoTiles.isEmpty()) {

            //der jetzige zustand wird in redo gesichert
            redoTiles.push(copyMap(mapTiles));
            redoObjects.push(copyObjectMap(mapObjects));
            redoLights.push(new ArrayList<>(LightManager.AllPointLights));
            redoHitboxen.push(copyHitboxMap(boxHitboxen));

            //der letzte zustand von dem stapel nehmen
            mapTiles = undoTiles.pop();
            mapObjects = undoObjects.pop();
            boxHitboxen = undoHitboxen.pop();

            LightManager.AllPointLights.clear(); //liste wird komplett entfernt
            LightManager.AllPointLights.addAll(undoLights.pop()); //nimmt alle lichter von dem letzten zustand und fügt sie ein

            for (int r = 0; r < mapRows; r++) { //setzt jedes feld vom array auf null
                Arrays.fill(light[r], null);
            }

            for (int i = 0; i < LightManager.AllPointLights.size(); i++) { //jetzt gehen wir durch AllPointlights hindurch
                PointLight pointlight = LightManager.AllPointLights.get(i); //und holen das licht objekt an dieser bestimmten stelle
                int row = (int) pointlight.PosY; //spalte
                int col = (int) pointlight.PosX; //reihe

                if (row >= 0 && row < mapRows && col >= 0 && col < mapColumns) { //prüfung ob es innerhalb des grids liegt
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
            undoTiles.push(copyMap(mapTiles));
            undoObjects.push(copyObjectMap(mapObjects));
            undoLights.push(new ArrayList<>(LightManager.AllPointLights));
            undoHitboxen.push(copyHitboxMap(boxHitboxen));

            //der zukunfts zustand von dem stapel nehmen
            mapTiles = redoTiles.pop();
            mapObjects = redoObjects.pop();
            boxHitboxen = redoHitboxen.pop();

            LightManager.AllPointLights.clear(); //liste wird komplett entfernt
            LightManager.AllPointLights.addAll(redoLights.pop()); //nimmt alle lichter von dem zukunfts zustand und fügt sie ein


            for (int r = 0; r < mapRows; r++) { //setzt jedes feld vom array auf null
                Arrays.fill(light[r], null);
            }
            for (int i = 0; i < LightManager.AllPointLights.size(); i++) { //jetzt gehen wir durch AllPointlights hindurch
                PointLight pointlight = LightManager.AllPointLights.get(i); //und holen das licht objekt an dieser bestimmten stelle
                int row = (int) pointlight.PosY; //spalte
                int col = (int) pointlight.PosX; //reihe
                if (row >= 0 && row < mapRows && col >= 0 && col < mapColumns) { //prüfung ob es innerhalb des grids liegt
                    light[row][col] = pointlight; //hier wird das licht an der stelle platziert
                }
            }
            repaint();
            updateMinimap();
        }
    }
}