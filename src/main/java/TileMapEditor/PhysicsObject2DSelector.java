package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PhysicsObject2DSelector extends JPanel {

    private int objectSize = 64; //größe eines tiles in der objectselection
    private int columns = 8; //anzahl der spalten pro reihe
    private int numObjects = 67; //wie viele tiles angezeigt werden
    
    private Grid grid; //Referenz

    public PhysicsObject2DSelector(Grid grid) {
        this.grid = grid; //speichert das grid

        addMouseListener(new MouseAdapter() { //Maus Listener / schaut an welches object ich auf der objectselection gedrückt habe
            @Override
            public void mousePressed(MouseEvent e) {

                int col = e.getX() / objectSize; //spalte in der objectselection
                int row = e.getY() / objectSize; //reihe in der objectselection
                int index = row * columns + col; //berechnet tile id

                if(index < numObjects) {
                    grid.setSelectedPhysicsObject2D(index); //setzt das ausgewählte object auf dem grid
                    repaint(); //repaint um neu zu zeichnen
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < numObjects; i++) {
            int col = (i) % columns; //berechnet spalte des objects in der objectselection
            int row = (i) / columns; //berechnet reihe des objects in der objectselection
            
            int x = col * objectSize; //x position auf der objectselection
            int y = row * objectSize; //y position auf der objectselection

            if (Grid.objectTextures[i] != null) { //wenn ein bild vorhanden ist zeichne es
                g.drawImage(Grid.objectTextures[i], x, y, objectSize, objectSize, null);
            } else { //sonst ist das feld grau
                g.setColor(Color.GRAY);
                g.fillRect(x, y, objectSize, objectSize);
            }
            
            g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
            g.drawRect(x, y, objectSize, objectSize); //rahmen um jedes object

            g.setColor(Color.BLACK); //farbe des rahmens ist schwarz
            g.drawString("Obj " + i, x + 10, y + 20);
        }
    }
}