package Enemy;

import Physics2D.LivingObject;
import java.util.ArrayList;
import GUI.Camera;
import java.awt.Image;

public class Enemy extends LivingObject {
    public static LivingObject Enemy;
    
    //Lebende Enemies werden hier gespeichert
    public static ArrayList<Enemy> Enemies = new ArrayList<>();
    
    //Tote Enemies werden hier gespeichert, damit ich sie später wieder "recyclen" kann
    public static ArrayList<Enemy> EnemyPool = new ArrayList<>();
    
    public int HP;
    
    public Enemy(int PosX, int PosY, Image img, float Velocity, int[] Direction) {
        super(PosX, PosY, img, Velocity, Direction);
    }
    
    public static void Spawn(int PosX, int PosY, Image img, float Velocity, int[] Direction) {
        Enemy newEnemy;

        if (!EnemyPool.isEmpty()) { //neuen Gegner erstellen, wenn keiner recycled werden kann, alten verwenden wenn noch einer da ist
            newEnemy = EnemyPool.remove(EnemyPool.size() - 1);

            newEnemy.setPosX(PosX);
            newEnemy.setPosY(PosY);
            newEnemy.setImage(img);
            newEnemy.setVelocity(Velocity);
            newEnemy.setDirection(Direction);
            newEnemy.setHP(100);

        } else {
            newEnemy = new Enemy(PosX, PosY, img, Velocity, Direction);
        }
        
        newEnemy.setPosX(newEnemy.getPosX() - Camera.PosX);
        newEnemy.setPosY(newEnemy.getPosY() - Camera.PosY);
        
        Enemies.add(newEnemy);
        
        System.out.println("Spawned Enemy at: " + (newEnemy.getPosX() - Camera.PosX) + ", " + (newEnemy.getPosY() - Camera.PosY));
        System.out.println("Displaying at: " + newEnemy.getPosX() + "," + newEnemy.getPosY());
    }
    
    public void die() {
        Enemies.remove(this); //aus den berechneten entfernen
        EnemyPool.add(this); //in die nicht berechneten hinzufügen
    }
    
    public int getHP() {
        return HP;
    }
    
    public void setHP(int newHP) {
        HP = newHP;
    }
}
