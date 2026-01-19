package GUI;

//import frame.Room;
import Player.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Canva extends JPanel {

    int x = 0;
    int y = 0;
    //Room r;
    
    public Canva(int w, int h) {
        super();
        this.setPreferredSize(new Dimension(w,h));
        this.setSize(new Dimension(w,h));
        this.setOpaque(false);
        this.setBounds(0, 0, w, h);
        
        this.setVisible(true);
        
        //r = new Room();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        //r.draw(g);
        
        g.setColor(Color.yellow);
        g.fillOval(x + Camera.PosX, y + Camera.PosY, 20, 20); // Game Objects werden abhängig zur Camera gerendered
        
        // Render Player
        
        g.setColor(Color.blue);
        g.fillOval(Player.LocPosX - Player.PlayerSizeX / 2, Player.LocPosY - Player.PlayerSizeY / 2, Player.PlayerSizeX, Player.PlayerSizeY); // Spieler befindet sich immer in der Mitte vom Screen, nur die Camera bewegt sich
    }
}
