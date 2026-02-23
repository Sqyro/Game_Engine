package Enemy;

import Physics2D.PhysicsObject2D;
import java.util.ArrayList;
import GUI.Camera;
import java.awt.Image;

public class Enemy extends PhysicsObject2D {
    public static PhysicsObject2D Enemy;
    
    public static ArrayList<Enemy> Enemies = new ArrayList<>();
    
    public static int HP;
    
    public Enemy(int PosX, int PosY, float Velocity, int[] Direction, Image img) {
        super(PosX, PosY, Velocity, Direction, img);
    }
    
    public static void Spawn(Enemy newEnemy) {
        newEnemy.setPosX(newEnemy.getPosX() - Camera.PosX);
        newEnemy.setPosY(newEnemy.getPosY() - Camera.PosY);
        
        Enemies.add(newEnemy);
        
        System.out.println("Spawned Enemy at: " + (newEnemy.getPosX() - Camera.PosX) + ", " + (newEnemy.getPosY() - Camera.PosY));
        System.out.println("Displaying at: " + newEnemy.getPosX() + "," + newEnemy.getPosY());
    }
    
    public static int getHP() {
        return HP;
    }
    
    public static void setHP(int newHP) {
        HP = newHP;
    }
}
