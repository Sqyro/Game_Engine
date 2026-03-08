package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainEditor {

    public static void main(String[] args) { //main damit alles hier gestartet wird
        JFrame frame = new JFrame("Map Editor"); //erstellt das hauptfenster
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //schließt das programm wenn du auf x drückst
        frame.setSize(1920, 1080); //fenstergröße idnem fall 1920x1080

        frame.setLayout(new BorderLayout()); //teilt das fenster ein in "parts"

        Grid grid = new Grid(128, 128, 16, 100, 100); //erstellt neues grid mit (Reihen, Zeilen, Tile Größe, verschoben X Richtung, verschoben Y Richtung)
        frame.add(grid, BorderLayout.CENTER); //das Grid liegt im center ungefähr

        
        //eingabefelder
        JTextField sizeInput = new JTextField(4);

        //buttons
        JPanel buttonPanel = new JPanel();
        JButton zoomIn = new JButton("+");
        JButton zoomOut = new JButton("-");
        JButton print = new JButton("Print Map");
        JButton importm = new JButton("Import");
        JButton exportm = new JButton("Export");
        JButton resizeMap = new JButton("Resize Map");

        //panels für die buttons und labels
        buttonPanel.add(zoomIn);
        buttonPanel.add(zoomOut);
        //buttonPanel.add(print);
        buttonPanel.add(importm);
        buttonPanel.add(exportm);
        buttonPanel.add(new JLabel("Map Size:"));
        buttonPanel.add(sizeInput);
        buttonPanel.add(resizeMap);

        frame.add(buttonPanel, BorderLayout.SOUTH); //position der gesamten buttons und labels


        TileSelector palette = new TileSelector(grid); //erstellt die Tileauswahlliste rechts vom fenster
        JScrollPane scrollPane = new JScrollPane(palette); //diese ist auch scrollbar
        scrollPane.setPreferredSize(new Dimension(530, 1250)); //und das ist die größe
        
        frame.add(scrollPane, BorderLayout.EAST); //postion im osten

        //ActionListener:
        //alles besitzen ein grid.requestFocusInWindow(); ,weil nachdem ich buttons drücke konnte man nicht mehr tiles platzieren
        //sonst an sich sind hier alle buttons was sie machen wenn man sie drückt
        
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
                } else { //fehlermeldung wenn man was falsches eingetippt hat
                    JOptionPane.showMessageDialog(frame, "Zahl zwischen 32 bis 512");
                }
            } catch (NumberFormatException ex) { //fehlermeldung wenn man was falsches eingetippt hat
                JOptionPane.showMessageDialog(frame, "Wie kann man so reinkacken");
            }
            grid.requestFocusInWindow();
        });

        frame.setLocationRelativeTo(null); //fenster zentrieren
        frame.setVisible(true); //fenster anzeigen

        grid.requestFocusInWindow();
    }
}