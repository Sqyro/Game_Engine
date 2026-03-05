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
        
        int screenLeft   = (int) -Camera.PosX;
        int screenRight  = screenLeft + getWidth();
        int screenTop    = (int) -Camera.PosY;
        int screenBottom = screenTop + getHeight();

        
        Map.draw(g, 0 + (int)Camera.PosX, 0 + (int)Camera.PosY);
        
        //Render Player
        
        GUI.ImageHandler.draw(g, InputManager.Player.getImage(), Player.LocPosX - Player.PlayerSizeX / 2, Player.LocPosY - Player.PlayerSizeY / 2);

        //Render Enemies;
        for(int i = 0; i < Enemy.Enemies.size(); i++) {
            Enemy currentEnemy = Enemy.Enemies.get(i);
            float EnemyX = currentEnemy.getPosX();
            float EnemyY = currentEnemy.getPosY();
            
            if (EnemyX < screenLeft - 100 || EnemyX > screenRight + 100 ||EnemyY < screenTop - 100 || EnemyY > screenBottom + 100) { //Wenn dieser Enemie nicht auf dem Screen ist (Plus Minus 100, damit man es nicht merkt)
                continue; // Skippt diesen Enemy und macht mit dem nächsten weiter
            }
            
            GUI.ImageHandler.draw(g, currentEnemy.getImage(), (int)(EnemyX + Camera.PosX), (int)(EnemyY + Camera.PosY));
        }
        
        //Render HUD
        
        for(int i = 0; i < GUIHandler.HudElements.size(); i++) {
            HudElement currentHud = GUIHandler.HudElements.get(i);
            int HudX = currentHud.getPosX();
            int HudY = currentHud.getPosY();
            
            GUI.ImageHandler.draw(g, currentHud.getImage(), HudX, HudY);
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
}
