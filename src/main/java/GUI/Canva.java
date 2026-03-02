package GUI;

//import frame.Room;
import Player.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;
import Enemy.Enemy;
import Player.InputManager;

public class Canva extends JPanel {
    
    //frame.Room r;
    
    Map.MapHandler Map;
    
    public Canva(int w, int h) {
        super();
        this.setPreferredSize(new Dimension(w,h));
        this.setSize(new Dimension(w,h));
        this.setOpaque(false);
        this.setBounds(0, 0, w, h);
        
        this.setVisible(true);
        
        //r = new frame.Room();
        
        Map = new Map.MapHandler();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        //r.draw(g);
        
        Map.draw(g, 0 + + Camera.PosX, 0 + Camera.PosY);
        
        // Render Player
        
        GUI.ImageHandler.draw(g, InputManager.Player.getImage(), Player.LocPosX - Player.PlayerSizeX / 2, Player.LocPosY - Player.PlayerSizeY / 2);

        //Render Enemies;
        for(int i = 0; i < Enemy.Enemies.size(); i++) {
            Enemy currentEnemy = Enemy.Enemies.get(i);
            int EnemyX = currentEnemy.getPosX();
            int EnemyY = currentEnemy.getPosY();
            
            GUI.ImageHandler.draw(g, currentEnemy.getImage(), EnemyX + Camera.PosX, EnemyY + Camera.PosY);
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
}
