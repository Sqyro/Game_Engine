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
import java.awt.image.BufferedImage;
import Physics2D.PhysicsObject2D;

public class Grid extends JPanel {

    public int visibleRows = 8; //Anzahl der sichtbaren Reihen auf dem großen Grid
    public int visibleCols = 8; //Anzahl der sichtbaren Spalten auf dem großen Grid
    private int mapRows; //Gesamtanzahl der Reihen
    private int mapCols; //Gesamtanzahl der Spalten
    private int tileSizeG; //Grid Tile Size, wie groß ein Tile ist
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
    
    private int currentTileType = 0; //welches tile ausgewählt ist
    private int currentRotation = 0; //aktuelle rotation (0-3)
    
    public int currentPhysicsObject2DTexture = 0; //aktuell ausgewähltes object
    
    private long timer = 0; //timer für die minimap
    
    private int mouseX = -100; //mausposition x
    private int mouseY = -100; //mausposition y

    private int oldMouseX = -100; //alte mausposition x
    private int oldMouseY = -100; //alte mausposition x 

    private int previewSize = 64; //größe des previews unten rechts von der maus
    private int offset = 20; //offset für das preview
    
    private Image minimapImage; //bild der minimap
    
    public int currentMode = 0; //der ausgewählte modus von den tabs (mode = 0 ist der tileselector usw.)
    
    public void setSelectedTile(int id) { //methode um das aktuelle tile zu ändern
        currentTileType = id;
        currentRotation = 0; //neu ausgewähltes tile wird von der rotation auf 0 gesetzt
        repaint();
    }
    
    public void setSelectedPhysicsObject2D(int id) {
        currentPhysicsObject2DTexture = id;
        repaint();
    }

    public Grid(int rows, int cols, int tileSize, int X, int Y) {
        mapRows = rows; //gesamtanzahl der reihen
        mapCols = cols; //gesamtanzahl der spalten
        tileSizeG = tileSize; //tilegröße
        Xg = X; //offset größe vom grid x
        Yg = Y; //offset größe vom grid y
        
        mapData = new int[mapRows][mapCols]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r=0; r<mapRows; r++)
            for(int c=0; c<mapCols; c++)
                mapData[r][c] = 3; //default tile id (was die ganze map painted)
        
        physicsObject2D = new int[mapRows][mapCols]; //erstellt ein Array mit der größe der Reihen x Spalten
        for(int r = 0; r < mapRows; r++)
            for(int c = 0; c < mapCols; c++)
                physicsObject2D[r][c] = -1; //default object id (was die ganze map painted)
        
        loadTiles(); //tile bilder laden
        loadPhysicsObject2DTextures(); //physicsobject bilder laden
        
        //TestMap(); //Debug Map, aber gerade auskommentiert, weil ich sie jetzt nicht brauche

        setPreferredSize(new Dimension(festeGridSize, festeGridSize)); //panelgröße
        setFocusable(true); //damit keylistener funktioniert
        
        addKeyListener(new KeyAdapter() { //Tastatur Listener / Steuerung der Kamera mit WASD / Rotation der Tiles
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_W) Camera(0, -1);
                if (e.getKeyCode() == KeyEvent.VK_S) Camera(0, 1);
                if (e.getKeyCode() == KeyEvent.VK_A) Camera(-1, 0);
                if (e.getKeyCode() == KeyEvent.VK_D) Camera(1, 0);
                
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    currentRotation = (currentRotation + 1) % 4;
                    }

                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    currentRotation = (currentRotation + 3) % 4;
                }
                repaint();
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() { //Maus Listener / für das löschen und platzieren von Tiles mit der Maus
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
        
                int mouseX = e.getX();
                int mouseY = e.getY();

                if(currentMode == 0) { //tile mode

                    if (SwingUtilities.isRightMouseButton(e)) {
                        placeTile(mouseX, mouseY, -1); //tile löschen
                    } else {
                        placeTile(mouseX, mouseY, currentTileType * 4 + currentRotation); //tile platzieren
                    }

                }

                if(currentMode == 1) { //physicsobject2D mode

                    if (SwingUtilities.isRightMouseButton(e)) {
                        placePhysicsObject2D(mouseX, mouseY, -1); //object löschen
                    } else {
                        placePhysicsObject2D(mouseX, mouseY, currentPhysicsObject2DTexture); //object löschen
                    }
                    repaint();
                }

            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // sobald die maus ins fenster kommt sofort koordinaten setzen
                mouseX = e.getX();
                mouseY = e.getY();
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

                repaint(oldMouseX + offset - 5, oldMouseY + offset - 5, previewSize + 15, previewSize + 15); //erster repaint löscht das alte preview
                repaint(mouseX + offset - 5, mouseY + offset - 5, previewSize + 15, previewSize + 15); //zweiter repaint fügt das neue preview hinzu
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {

                mouseX = e.getX();
                mouseY = e.getY();

                if (currentMode == 0) { // Tile
                    if (SwingUtilities.isRightMouseButton(e)) {
                        placeTile(mouseX, mouseY, -1); // Rechtsklick löscht Tile
                    } else {
                        placeTile(mouseX, mouseY, currentTileType * 4 + currentRotation); // Linksklick platziert Tile
                    }
                } 
                else if (currentMode == 1) { // PhysicsObject
                    if (SwingUtilities.isRightMouseButton(e)) {
                        placePhysicsObject2D(mouseX, mouseY, -1); // Rechtsklick löscht Object
                    } else {
                        placePhysicsObject2D(mouseX, mouseY, currentPhysicsObject2DTexture); // Linksklick platziert Object
                    }
                }
                repaint();
            }
        });
        
        addMouseWheelListener(new MouseWheelListener() { //Mausrad Listener / man kann das Mausrad benutzen um auf dem großen Grid zu zoomen
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                int rotation = e.getWheelRotation(); //erkennt ob es nach oben oder nach unten scrollt

                if(rotation < 0) { //wenn ich nach oben gescrollt habe gibt es -1 aus also zoome ich rein
                    visibleTiles(visibleRows / zoom, visibleCols / zoom);
                }
                else { //sonst zoome ich raus
                    visibleTiles(visibleRows * zoom, visibleCols * zoom);
                }
            }
        });
    }

    
    //paintComponent
    @Override
    protected void paintComponent(Graphics g) { //eine methode die alles auf ein panel zeichnet
        super.paintComponent(g);
        
        int tileSizeG = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles für das zoomen
        
        
        //Main Grid (große Grid)
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleCols; c++) {

                int worldCol = cameraCols + c; //gesamtspalten
                int worldRow = cameraRows + r; //gesamtreihen

                int x = Xg + c * tileSizeG;
                int y = Yg + r * tileSizeG;

                int tile = mapData[worldRow][worldCol]; //tile id an einer bestimmten position  

                if (tile >= 0 && tile < tiles.length && tiles[tile] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                    g.drawImage(tiles[tile], x, y, tileSizeG, tileSizeG, null); //tile bild zeichnen
                } else {
                    g.setColor(Color.GRAY); // wenn kein Tile da ist, graues Feld
                    g.fillRect(x, y, tileSizeG, tileSizeG);
                }

                g.setColor(Color.BLACK); //schwazre frabe für den tile rahmen
                g.drawRect(x, y, tileSizeG, tileSizeG); //rahmen um jedes tile
            }
        }
        
        
        //physicsObjects auf dem Grid
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleCols; c++) {

                int worldCol = cameraCols + c;
                int worldRow = cameraRows + r;
                
                int x = Xg + c * tileSizeG;
                int y = Yg + r * tileSizeG;
                
                int objectID = physicsObject2D[worldRow][worldCol]; //object id an einer bestimmten position

                if (objectID >= 0 && objectID < physicsObject2DTextures.length && physicsObject2DTextures[objectID] != null) { //prüft die object id (muss ein tile sein, das object muss im array liegen und es muss ein bild haben)
                    g.drawImage(physicsObject2DTextures[objectID], x, y, tileSizeG, tileSizeG, null); //object bild zeichnen
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

        if(currentMode == 0) { // Tile
            int tileIndex = currentTileType * 4 + currentRotation; //ausgewähltes tile eventuell mit rotierung
            if(tileIndex >= 0 && tileIndex < tiles.length && tiles[tileIndex] != null) { //prüft die tile id (muss ein tile sein, das tile muss im array liegen und es muss ein bild haben)
                g.drawImage(tiles[tileIndex], previewX, previewY, previewSize, previewSize, null); //tile preview zeichnen
                g.setColor(Color.BLACK); //scharzer rahmen um das preview
                g.drawRect(previewX, previewY, previewSize, previewSize);
            }
        } 
        else if(currentMode == 1) { // PhysicsObject
            int objectIndex = currentPhysicsObject2DTexture; //ausgewähltes object
            if(objectIndex >= 0 && objectIndex < physicsObject2DTextures.length && physicsObject2DTextures[objectIndex] != null) { //prüft die object id (muss ein tile sein, das object muss im array liegen und es muss ein bild haben)
                g.drawImage(physicsObject2DTextures[objectIndex], previewX, previewY, previewSize, previewSize, null); //object preview zeichnen
            }
        }
        
    }
    
    //Kamera Movemnet für den Rahmen
    public void Camera(int cx, int cy) {
        
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

            int numTiles = 100; //anzahl der insgesamten tiles
            int numRotations = 4; //wie viele rotation ein tile hat
 
            tiles = new Image[numTiles * numRotations]; //gesamtanzahl der Tiles indem fall 100x4

            int counter = 0; //zähler für den array

            for (int i = 0; i < numTiles; i++) {

                for (int r = 0; r < numRotations; r++) {

                    String path = "src/main/resources/assets/textures/tiles/Tile" + i + "_Rotated/Tile" + r + ".png"; //Bildpfad

                    tiles[counter] = new ImageIcon(path).getImage();
                    
                    counter++; //zähler damit die tiles unterschiedlich gespeichert werden
                }
            }
            repaint();
            updateMinimap();
        }
   
   private void loadPhysicsObject2DTextures() {
            int numObjects = 67; //anzahl der insgesamten objects
            
            physicsObject2DTextures = new Image[numObjects];  //gesamtanzahl der objects indem fall 67

            for (int i = 0; i < numObjects; i++) {
                String path = "src/main/resources/assets/textures/physicsObject2D/Object" + i + ".png"; //Bildpfad
                
                physicsObject2DTextures[i] = new ImageIcon(path).getImage();
            }
        }
   
    private void placeTile(int mouseX, int mouseY, int tilenum) {

        int tileSizeG = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles

        int mcols = (mouseX - Xg) / tileSizeG; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrows = (mouseY - Yg) / tileSizeG; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcols >= 0 && mcols < visibleCols && mrows >= 0 && mrows < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat

            int worldCol = cameraCols + mcols; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrows; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist

                if(mapData[worldRow][worldCol] != tilenum) { //setzt das Tile an diese stelle
                    mapData[worldRow][worldCol] = tilenum;
                    
                    int paintX = Xg + mcols * tileSizeG;
                    int paintY = Yg + mrows * tileSizeG;
                    
                    repaint(paintX, paintY, tileSizeG, tileSizeG); //repaint um map neu zu zeichnen
                    
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

        int tileSizeG = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles

        int mcol = (mouseX - Xg) / tileSizeG; //hier wird berechnet auf welcher Spalte die maus geklickt hat
        int mrow = (mouseY - Yg) / tileSizeG; //hier wird berechnet auf welcher Reihe die maus geklickt hat

        if (mcol >= 0 && mcol < visibleCols && mrow >= 0 && mrow < visibleRows) { //prüfung ob die maus überhaupt auf dem großen grid geklickt hat
            int worldCol = cameraCols + mcol; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die spalte auf der man geklickt hat
            int worldRow = cameraRows + mrow; //hier berücksichtige ich die Kamera, weil sie nicht immer 0,0 ist und berechne die reihe auf der man geklickt hat

            if (worldCol >= 0 && worldCol < mapCols && worldRow >= 0 && worldRow < mapRows) { //prüfung ob die position valide ist
                if (physicsObject2D[worldRow][worldCol] != objectNum) { //setzt das object an diese stelle
                    physicsObject2D[worldRow][worldCol] = objectNum;

                    int paintX = Xg + mcol * tileSizeG;
                    int paintY = Yg + mrow * tileSizeG;

                    repaint(paintX, paintY, tileSizeG, tileSizeG); //repaint um map neu zu zeichnen
                    
                    if(System.currentTimeMillis() - timer > 50) { //repaint mit timer sonst kommt es einfach zu laggs
                        updateMinimap();
                        repaint(getWidth() - 467, 67, 400, 400);
                        timer = System.currentTimeMillis();
                    }
                }
            }
        }
    }
    
    public void exportMap() {
        JFileChooser fileChooser = new JFileChooser(); //öffnet das Dateiauswahl Fenster 
        fileChooser.setDialogTitle("Speichern unter"); //titel des Fensters
        int userSelection = fileChooser.showSaveDialog(this); //sagt ob der benutzer abgebrochen hat oder ok gedrückt hat

        if (userSelection == JFileChooser.APPROVE_OPTION) { //wenn ein benutzer eine datei auswählt
            File fileToSave = fileChooser.getSelectedFile(); //hier holt er sich die datei
            try (PrintWriter pw = new PrintWriter(fileToSave)) { //datei öffnen um zu schreiben
                for (int r = 0; r < mapRows; r++) {
                    for (int c = 0; c < mapCols; c++) {
                        pw.print(mapData[r][c]); //schreibt jede tile id auf
                        if (c < mapCols - 1) pw.print(" "); //mit leerzeichen zwischen den tiles
                    }
                    pw.println();
                }
                System.out.println("Map gespeichert: " + fileToSave.getAbsolutePath()); //nur für mmich um zu schauen welche datei gespeichert wurde
            } catch (Exception ex) { //falls ein fehler auftritt
                ex.printStackTrace();
            }
        }
    }
    
    public void importMap() {
        JFileChooser fileChooser = new JFileChooser(); //öffnet das Dateiauswahl Fenster 
        fileChooser.setDialogTitle("Datei öffnen"); //titel des Fensters
        int userSelection = fileChooser.showOpenDialog(this); //sagt ob der benutzer abgebrochen hat oder ok gedrückt hat

        if (userSelection == JFileChooser.APPROVE_OPTION) { //wenn ein benutzer eine datei auswählt
            File fileToOpen = fileChooser.getSelectedFile(); //hier holt er sich die datei
            try (Scanner sc = new Scanner(fileToOpen)) { //öffnet die datei zum lesen, try sorgt dafür das die datei nach benutzung geschlossen wird
                int r = 0;
                while (sc.hasNextLine() && r < mapRows) { //wird solange gelesen bis wir die mapgröße überschritten haben
                    String line = sc.nextLine(); //liest aktuelle zeile als string
                    String[] tokens = line.trim().split("\\s+"); //entfernt die leerzeichen und wandelt alle in ein string um
                    for (int c = 0; c < Math.min(tokens.length, mapCols); c++) { //geht durch alle Spalten der Zeile, math.min sorgt dafür das wir nicht mehr Spalten schreiben als die Map hat
                        mapData[r][c] = Integer.parseInt(tokens[c]); //wandelt string in eine zahl um und speichert die tile id in dem array
                    }
                    r++;
                }
                repaint(); //repaint um die map neu zu zeichnen nachdem import  
                System.out.println("Map geladen: " + fileToOpen.getAbsolutePath()); //nur für mmich um zu schauen welche datei geladen wurde
            } catch (Exception ex) { //falls ein fehler auftritt
                ex.printStackTrace();
            }
        }
    }
    
    public void resizeMap(int newSize) {
        int[][] newMap = new int[newSize][newSize]; //neues array für die map erstellen

        for (int r = 0; r < Math.min(mapRows, newSize); r++) { // alte map tile ids kopieren
            for (int c = 0; c < Math.min(mapCols, newSize); c++) {
                newMap[r][c] = mapData[r][c];
            }
        }
        
        mapData = newMap; //ersetzt alte map durch die neue
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
}