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

        Grid grid = new Grid(128, 128, 16, 100, 100); //(Reihen, Zeilen, Tile Größe, verschoben X Richtung, verschoben Y Richtung
        frame.add(grid, BorderLayout.CENTER);

        JTextField sizeInput = new JTextField(4);

        //buttons fürs zoomen
        JPanel buttonPanel = new JPanel();
        JButton zoomIn = new JButton("+");
        JButton zoomOut = new JButton("-");
        JButton print = new JButton("Print Map");
        JButton importm = new JButton("Import");
        JButton exportm = new JButton("Export");
        JButton resizeMap = new JButton("Resize Map");

        buttonPanel.add(zoomIn);
        buttonPanel.add(zoomOut);
        //buttonPanel.add(print);
        buttonPanel.add(importm);
        buttonPanel.add(exportm);

        buttonPanel.add(new JLabel("Map Size:"));
        buttonPanel.add(sizeInput);
        buttonPanel.add(resizeMap);

        frame.add(buttonPanel, BorderLayout.SOUTH);


        TileSelector palette = new TileSelector(grid);
        JScrollPane scrollPane = new JScrollPane(palette);
        scrollPane.setPreferredSize(new Dimension(530, 1250));

        frame.add(scrollPane, BorderLayout.EAST);

        // Zoom in
        zoomIn.addActionListener((ActionEvent e) -> {
            grid.visibleTiles(grid.visibleRows * grid.zoom, grid.visibleCols * grid.zoom);
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
            grid.requestFocusInWindow();
        });

        //imported map
        importm.addActionListener((ActionEvent e) -> {
            grid.importMap();
            grid.requestFocusInWindow();
        });

        //exported map
        exportm.addActionListener((ActionEvent e) -> {
            grid.exportMap();
            grid.requestFocusInWindow();
        });

        //resize map
        resizeMap.addActionListener((ActionEvent e) -> {
            try {
                int newSize = Integer.parseInt(sizeInput.getText());
                if (newSize >= 32 && newSize <= 512) {
                    grid.resizeMap(newSize);
                } else {
                    JOptionPane.showMessageDialog(frame, "Zahl zwischen 32 bis 512");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Wie kann man so reinkacken");
            }
            grid.requestFocusInWindow();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        grid.requestFocusInWindow();

       //grid.printMap();
    }
}