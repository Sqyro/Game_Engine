package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainEditor {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Map Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        
        frame.setLayout(new BorderLayout());
    
        Grid grid = new Grid(256, 256, 16, 100, 100); //(Reihen, Zeilen, Tile Größe, verschoben X Richtung, verschoben Y Richtung
        frame.add(grid, BorderLayout.CENTER);
        
        
        //buttons fürs zoomen
        JPanel buttonPanel = new JPanel();
        JButton zoomIn = new JButton("+");
        JButton zoomOut = new JButton("-");
        JButton print = new JButton("Print Map");
        
        buttonPanel.add(zoomIn);
        buttonPanel.add(zoomOut);
        buttonPanel.add(print);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        
        TileSelector palette = new TileSelector(grid);
        JScrollPane scrollPane = new JScrollPane(palette);
        scrollPane.setPreferredSize(new Dimension(530, 1250));

        frame.add(scrollPane, BorderLayout.EAST);

        // Zoom in
        zoomIn.addActionListener((ActionEvent e) -> {
            grid.visibleTiles(grid.visibleRows * grid.zoom, grid.visibleCols* grid.zoom);
            grid.requestFocusInWindow();
        });

        // Zoom out
        zoomOut.addActionListener((ActionEvent e) -> {
            grid.visibleTiles(grid.visibleRows / grid.zoom, grid.visibleCols / grid.zoom);
            grid.requestFocusInWindow();
        });
        
        //printed die map
        print.addActionListener((ActionEvent e) -> {
            grid.printMap();
        });

        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        grid.requestFocusInWindow();
        
       //grid.printMap();
}
}
