package GUI;

import GUI.Canva;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JFrame implements ActionListener{

    JPanel canva;
    Timer t;
    public static int FramesPerSecond = 60; // 60 FPS sind gerade Standart, soll dann aber einstellbar sein
    public static int ScreenHeight = 1080; // Wird je nach Setting überschrieben, Momentan auf HD Fullscreen gehardcoded
    public static int ScreenWidth = 1920;
            
    public Frame(String title) throws HeadlessException {
        super(title);
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        
        canva  = new Canva(this.getWidth(), this.getHeight());
        this.add(canva);
        
        this.setVisible(true);
        this.pack();
        
        t = new Timer(1/FramesPerSecond * 1000, this); //1/60 sind die Sekunden pro Frame. Mal eintausend für Millisekunden pro Frame
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canva.repaint();
    }
    
    
}
