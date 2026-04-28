package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Map.MapObject;
import Map.MapObjects;

public class MapObjectsSelector extends JPanel {
    private Grid grid; //Referenz
    private int objectSize = 64; //größe eines tiles in der objectselection
    private int columns = 8; //anzahl der spalten pro reihe
    public int objectCount = 100;

    public MapObjectsSelector(Grid grid) {
        this.grid = grid; //speichert das grid
        grid.ObjectTextures = new Image[objectCount];  //gesamtanzahl der objects indem fall 100
        loadObjectsTextures();
        this.setPreferredSize(new Dimension(530, 900));

        addMouseListener(new MouseAdapter() { //Maus Listener / schaut an welches object ich auf der objectselection gedrückt habe
            @Override
            public void mousePressed(MouseEvent e) {

                int col = e.getX() / objectSize; //spalte in der objectselection
                int row = e.getY() / objectSize; //reihe in der objectselection
                int index = row * columns + col; //berechnet tile id

                if(index < objectCount) {
                    grid.setSelectedPhysicsObject2D(index); //setzt das ausgewählte object auf dem grid
                    repaint(); //repaint um neu zu zeichnen
                }
            }
        });
    }

    private void loadObjectsTextures() {
        MapObjects.RegisterMapObjects();

        objectCount = 0;
        java.util.List<MapObject> objects = new java.util.ArrayList<>(MapObjects.MAP_OBJECTS.Registered.values());
        for (int i = 0; i < objects.size(); i++) {
            String name = objects.get(i).getRegistryName();
            String path = "src/main/resources/assets/textures/mapObjects/" + name + ".png";
            grid.ObjectTextures[objectCount] = new ImageIcon(path).getImage();
            grid.objectNames[objectCount] = name;
            objectCount++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < objectCount; i++) {
            int col = (i) % columns; //berechnet spalte des objects in der objectselection
            int row = (i) / columns; //berechnet reihe des objects in der objectselection
            
            int x = col * objectSize; //x position auf der objectselection
            int y = row * objectSize; //y position auf der objectselection

            if (Grid.ObjectTextures[i] != null) { //wenn ein bild vorhanden ist zeichne es
                g.drawImage(Grid.ObjectTextures[i], x, y, objectSize, objectSize, null);
            } else { //sonst ist das feld grau
                g.setColor(Color.GRAY);
                g.fillRect(x, y, objectSize, objectSize);
            }
            
            g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
            g.drawRect(x, y, objectSize, objectSize); //rahmen um jedes object
        }
    }
}