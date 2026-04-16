package TileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainEditor {

    public static void main(String[] args) { //main damit alles hier gestartet wird
        JFrame frame = new JFrame("Map Editor"); //erstellt das hauptfenster
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //schließt das programm wenn du auf x drückst
        frame.setSize(1920, 1080); //fenstergröße idnem fall 1920x1080
        CursorManager.load(frame); //lädt die bilder von der custom maus und setzt sofort pencil als standard

        frame.setLayout(new BorderLayout()); //teilt das fenster ein in "parts"

        Grid grid = new Grid(); //erstellt neues grid mit (Reihen, Zeilen, Tile Größe, verschoben X Richtung, verschoben Y Richtung)
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
        //buttonPanel.add(zoomIn);
        //buttonPanel.add(zoomOut);
        //buttonPanel.add(print);
        buttonPanel.add(importm);
        buttonPanel.add(exportm);
        buttonPanel.add(new JLabel("Map Size:"));
        buttonPanel.add(sizeInput);
        buttonPanel.add(resizeMap);

        frame.add(buttonPanel, BorderLayout.SOUTH); //position der gesamten buttons und labels

        //tabs für die verschiedenen sachen
        JTabbedPane tabs = new JTabbedPane();
            
        //tile selctor tab
        TileSelector tileSelector = new TileSelector(grid); //erstellt die Tileauswahlliste rechts vom fenster
        JScrollPane tileScroll = new JScrollPane(tileSelector); //diese ist auch scrollbar
        tileScroll.setPreferredSize(new Dimension(530, 1250)); //und das ist die größe
        tabs.addTab("tiles", tileScroll); //addet es auch zum tab
        
        //physiscsObject2D selctor tab
        PhysicsObject2DSelector objectSelector = new PhysicsObject2DSelector(grid); //erstellt die Tileauswahlliste rechts vom fenster
        JScrollPane objectScroll = new JScrollPane(objectSelector); //diese ist auch scrollbar
        objectScroll.setPreferredSize(new Dimension(530, 1250)); //und das ist die größe
        tabs.addTab("objects", objectScroll); //addet es auch zum tab
        
        //licht selector tab
        LightSelector lightSelector = new LightSelector(grid); 
        JScrollPane lightScroll = new JScrollPane(lightSelector);
        lightScroll.setPreferredSize(new Dimension(530, 1250));
        tabs.addTab("light", lightScroll); //addet es auch zum tab
        
        //licht selector tab
        HitboxenSelector hitboxenSelector = new HitboxenSelector(grid); 
        JScrollPane hitboxenScroll = new JScrollPane(hitboxenSelector);
        hitboxenScroll.setPreferredSize(new Dimension(530, 1250));
        tabs.addTab("hitboxen", hitboxenScroll); //addet es auch zum tab
        
        //tab wechsel
        tabs.addChangeListener(e -> {

        int index = tabs.getSelectedIndex();

        if(index == 0) grid.currentTab = 0; //tiles
        if(index == 1) grid.currentTab = 1; //physicsobjects2D
        if(index == 2) grid.currentTab = 2; //licht
        if(index == 3) grid.currentTab = 3; //hitboxen

    });
        
        frame.add(tabs, BorderLayout.EAST); //tab rechts machen
        
        //ActionListener:
        //alles besitzen ein grid.requestFocusInWindow(); ,weil nachdem ich buttons drücke konnte man nicht mehr tiles platzieren
        //sonst an sich sind hier alle buttons was sie machen wenn man sie drückt
        
        // Zoom in
        zoomIn.addActionListener((ActionEvent e) -> {
            grid.visibleTiles(grid.visibleRows * grid.zoom, grid.visibleColumns * grid.zoom);
            grid.requestFocusInWindow(); //fenster fokussieren
        });

        // Zoom out
        zoomOut.addActionListener((ActionEvent e) -> {
            grid.visibleTiles(grid.visibleRows / grid.zoom, grid.visibleColumns / grid.zoom);
            grid.requestFocusInWindow(); //fenster fokussieren
        });

        //printed die map
        print.addActionListener((ActionEvent e) -> {
            grid.printMap();
            grid.requestFocusInWindow(); //fenster fokussieren
        });

        //imported map
        importm.addActionListener((ActionEvent e) -> {
            grid.importMap();
            grid.requestFocusInWindow(); //fenster fokussieren
        });

        //exported map
        exportm.addActionListener((ActionEvent e) -> {
            grid.exportMap();
            grid.requestFocusInWindow(); //fenster fokussieren
        });

        //resize map
        resizeMap.addActionListener((ActionEvent e) -> {
            try {
                int newSize = Integer.parseInt(sizeInput.getText());
                if (newSize >= 32 && newSize <= 670) {
                    grid.resizeMap(newSize);
                    grid.updateMinimap();
                } else { //fehlermeldung wenn man was falsches eingetippt hat
                    JOptionPane.showMessageDialog(frame, "Zahl zwischen 32 bis 512");
                }
            } catch (NumberFormatException ex) { //fehlermeldung wenn man was falsches eingetippt hat
                JOptionPane.showMessageDialog(frame, "Wie kann man so reinkacken");
            }
            grid.requestFocusInWindow(); //fenster fokussieren
        });

        frame.setLocationRelativeTo(null); //fenster zentrieren
        frame.setVisible(true); //fenster anzeigen

        grid.requestFocusInWindow(); //fenster fokussieren
    }
}