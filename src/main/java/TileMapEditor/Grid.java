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

    public int visibleRows = 8; //Anzahl der sichtbaren Reihen auf dem großen Grid
    public int visibleCols = 8; //Anzahl der sichtbaren Spalten auf dem großen Grid
    private int mapRows; //Gesamtanzahl der Reihen
    private int mapCols; //Gesamtanzahl der Spalten
    private int tileSizeG; //Grid Tile Size, wie groß ein Tile ist
    private int festeGridSize = 800; //feste Größe für den sichtbaren großen GRid
    public int zoom = 2; //Zoom Faktor

    private int Xg; //X Position des rechten Grids
    private int Yg; //Y Position des rechten GRids
    
    private int cameraCols = 0; //sagt welche Spalte gerade links angezeigt wird
    private int cameraRows = 0; //sagt welche Reihe gerade links angezeigt wird
    
    private int[][] mapData; //2D Array, es speichert auf welcher Position der Tile ist
    public static Image[] tiles; //Array, es speichert alle Bilder von den Tiles als bestimmten Wert
    
    private int thisTile = 0; //aktuell ausgewählte Tile
    
    public void setSelectedTile(int id) { //methode um das aktuelle tile zu ändern
        this.thisTile = id;
    }

    public Grid(int rows, int cols, int tileSize, int X, int Y) {
        mapRows = rows;
        mapCols = cols;
        tileSizeG = tileSize;
        Xg = X;
        Yg = Y;
        
        mapData = new int[mapRows][mapCols]; //erstellt ein Array mit der größe der Reihen x Spalten
        
        //TestMap(); //Debug Map, aber gerade auskommentiert, weil ich sie jetzt nicht brauche
        
        loadTiles(); //lädt einfach alle Bilder, wie der name eigentlich schon sagt
        
        setPreferredSize(new Dimension(festeGridSize, festeGridSize));
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() { //Tastatur Listener / Steuerung der Kamera mit WASD
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_W) Camera(0, -1);
                if (e.getKeyCode() == KeyEvent.VK_S) Camera(0, 1);
                if (e.getKeyCode() == KeyEvent.VK_A) Camera(-1, 0);
                if (e.getKeyCode() == KeyEvent.VK_D) Camera(1, 0);

            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() { //Maus Listener / für das löschen und platzieren von Tiles mit der Maus
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
        
                if (SwingUtilities.isRightMouseButton(e)) { //wenn rechte Masutaste gedrückt wird dann "löscht" er das Tile, also ersetzt er das Tile mit 0
                    placeTile(e.getX(), e.getY(), 0);
                } else { //sonst wird ein Tile hinzugefügt mit dem aktuellen Tile
                    placeTile(e.getX(), e.getY(), thisTile);
                }
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() { //Mausbewegung Listener / das ist einfach dazu da das man nicht jedes einzelne Tile alleine platzieren muss, sondern auch gedrückt halten kann um mehrere zu platzieren
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) { //wenn rechte Masutaste gedrückt wird dann "löscht" er das Tile, also ersetzt er das Tile mit 0
                    placeTile(e.getX(), e.getY(), 0);
                } else { //sonst wird ein Tile hinzugefügt mit dem aktuellen Tile
                    placeTile(e.getX(), e.getY(), thisTile);
                }
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

    @Override
    protected void paintComponent(Graphics g) { //eine methode die alles auf ein panel zeichnet
        super.paintComponent(g);
        
        int tileSizeG = festeGridSize / visibleCols; //hier berechne ich die größe eines Tiles für das zoomen
        
        //Main Grid (große Grid)
        for (int r = 0; r < visibleRows; r++) {
            for (int c = 0; c < visibleCols; c++) {

                int worldCols = cameraCols + c; //hier wird berechnet welche Spalte gerade auf der Map angezeigt wird
                int worldRows = cameraRows + r; //hier wird berechnet welche Reihe gerade auf der Map angezeigt wird
                
                int x = Xg + c * tileSizeG; //x position eines Tiles
                int y = Yg + r * tileSizeG; //y psoition eines Tiles
                
                int tile = mapData[worldRows][worldCols]; //holt die Tile ID an einem bestimmten Punkt
                
                if (tile >= 0 && tile < tiles.length && tiles[tile] != null) { //hier wird geschaut ob das Tile gültig ist. Es darf 1. nicht negativ sein 2.das Tile darf nicht größer als Tile Array sein und bei 3. wird geschaut ob das Bild geladen wurde
                    g.drawImage(tiles[tile], x, y, tileSizeG, tileSizeG, null); //hier zeichnet er das Bild an der Position und wie groß das ist
                } else { //wnn die kriterien oben nicht erfüllt werden zeichnet er einfach ein Feld rein, das heißt meistens es gab einen Fehler
                    g.setColor(Color.GRAY); 
                    g.fillRect(x, y, tileSizeG, tileSizeG);
                }
                
                g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
                g.drawRect(x, y, tileSizeG, tileSizeG); //zeichnet ein rahmen um jedes Tile
                
            }
        }
        
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(Xg, Yg, festeGridSize, festeGridSize); //zeichent den äußeren Rahmen des gesamten großen Grids
        
        //Mini Grid
        int miniBreite = 400; //breite der minimap
        int miniHoehe = 400; //höhe der minimap
        int miniX = getWidth() - miniBreite - 67; //x position der minimap
        int miniY = 67; //y position der minimap

        double miniTileBreite = (double) miniBreite / mapCols; //berechnet die breite eines Tiles auf der minimap
        double miniTileHoehe = (double) miniHoehe / mapRows; //berechnet die breite eines Tiles auf der minimap

        for (int r = 0; r < mapRows; r++) {
            for (int c = 0; c < mapCols; c++) {

                int tileId = mapData[r][c]; //eigentlich das gleiche wie oben, nur hab ich den namen etwas geändert damit es übersichtlicher ist, aber hier holt die Tile ID an einem bestimmten Punkt

                if (tileId >= 0 && tileId < tiles.length && tiles[tileId] != null) { //hier wird geschaut ob das Tile gültig ist. Es darf 1. nicht negativ sein 2.das Tile darf nicht größer als Tile Array sein und bei 3. wird geschaut ob das Bild geladen wurde
                    g.drawImage(tiles[tileId],miniX + (int)(c * miniTileBreite),miniY + (int)(r * miniTileHoehe),(int)miniTileBreite + 1,(int)miniTileHoehe + 1,null); //hier zeichnet er das Bild an der Position und wie groß das ist
                } else { //wenn die kriterien oben nicht erfüllt werden zeichnet er einfach ein Feld rein, das heißt meistens es gab einen Fehler
                    g.setColor(Color.GRAY);
                    g.fillRect(miniX + (int)(c * miniTileBreite),miniY + (int)(r * miniTileHoehe),(int)miniTileBreite + 1,(int)miniTileHoehe + 1);
                }
            }
        }
        
        g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
        g.drawRect(miniX, miniY, miniBreite, miniHoehe); //zeichent den äußeren Rahmen des gesamten großen Grids
        
        //Kamera Rahmen auf Mini Grid
        g.setColor(Color.BLUE); //farbe des rahmens ist blau
        g.drawRect(miniX + (int)(cameraCols * miniTileBreite), miniY + (int)(cameraRows * miniTileHoehe), (int)(visibleCols * miniTileBreite), (int)(visibleRows * miniTileHoehe) //berechnet die x und y position auf der minimap sowie die höhe und die breite
        );
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

                    tiles[counter] = Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(TileSelector.tileSize, TileSelector.tileSize, Image.SCALE_DEFAULT); //lädt das bid von eben und skaliert es noch

                    counter++; //zähler damit die tiles unterschiedlich gespeichert werden
                }
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
                    repaint(); //repaint um map neu zu zeichnen
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
}