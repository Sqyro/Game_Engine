package GUI;

import Physics2D.VelocityHandler;
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
    
    long lastTime = System.nanoTime();
    
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
        
        t = new Timer(1000 / FramesPerSecond, this); //1000/60 sind die Millisekunden pro Frame.
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long now = System.nanoTime(); //liest die Frame unabhängige "System Zeit"
        float deltaTime = (now - lastTime) / 1_000_000_000f; //Differenz aus jetzige Zeit und vorherige Zeit ist die Zeit Pro Frame. Diese Zahl benutze ich, damit Geschwindigkeiten auf 30 FPS gleichstark wie auf 60 sind
        lastTime = now; //Das jetzt ist jetzt vorbeit und ist vergangenheit, weil delta Time gesetzt wurde
        
        Player.InputManager.updatePlayerDirection();
        VelocityHandler.calculatePosition(Player.InputManager.Player, deltaTime);
        Camera.UpdateCamera(Player.InputManager.Player);
        canva.repaint();
    }
    
    
}
