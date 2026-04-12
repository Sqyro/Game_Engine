package Enemy;

import Physics2D.LivingObject;
import java.util.ArrayList;

import Registry.Registrable;
import Rendering.Camera;
import Physics2D.CircleCollider;

public class Enemy extends LivingObject { //Enemy ist ein Living Object, also ein sich bewegendes
    //Lebende Enemies werden hier gespeichert
    public static ArrayList<Enemy> Enemies = new ArrayList<>();
    
    //Tote Enemies werden hier gespeichert, damit ich sie später wieder "recyclen" kann
    public static ArrayList<Enemy> EnemyPool = new ArrayList<>();

    public int MAX_HP;
    public int HP;

    public Enemy(float PosX, float PosY, float EnemyLength, float EnemyHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox, int MAX_HP) { //Constructor für einen neuen Enemy
        super(PosX, PosY, EnemyLength, EnemyHeight, TextureID, Velocity, Direction, Hitbox); //Passed einfach nur alle Values weiter an Living Object
        this.MAX_HP = MAX_HP;
        this.HP = MAX_HP;
    }
    
    //Soll einen neuen Enemy Spawnen
    public static void Spawn(float PosX, float PosY, float EnemyLength, float EnemyHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox, int MAX_HP) {
        Enemy newEnemy; //Erstellt neue Enemy Variable
        
        //neuen Gegner erstellen, wenn keiner recycled werden kann, alten verwenden wenn noch einer da ist. 
        //Macht bei Enemies sinn würde ich sagen, weil wir später vielleicht sehr viele spawnen und die sonst nen haufen Memory essen und den garbage Collector beschäftigen, wenn sie immer neu erstellt werden.
        if (!EnemyPool.isEmpty()) {
            
            newEnemy = EnemyPool.remove(EnemyPool.size() - 1); //Entfernt den ersten enemy

            //setzt alle Values auf die, die reinkommen, damit nicht einfach der alte Enemy zurück kommt
            newEnemy.setPosX(PosX);
            newEnemy.setPosY(PosY);
            newEnemy.setObjLength(EnemyLength);
            newEnemy.setObjHeight(EnemyHeight);
            newEnemy.setTextureID(TextureID);
            newEnemy.setVelocity(Velocity);
            newEnemy.setDirection(Direction);
            newEnemy.Hitbox.setRadius(Hitbox.Radius);
            newEnemy.Hitbox.setOffsetX(Hitbox.OffsetX);
            newEnemy.Hitbox.setOffsetY(Hitbox.OffsetY);
            newEnemy.setMaxHp(MAX_HP);
            newEnemy.setHp(MAX_HP);

        } else {
            //Erstellt neues Enemy Object mit den reinkommenden Values, wenn kein alter recycled werden kann
            newEnemy = new Enemy((int)PosX, (int)PosY, (int)EnemyLength, (int)EnemyHeight, TextureID, Velocity, Direction, Hitbox, MAX_HP);
        }
        
        //fügt den eben erstellten Enemy in die Liste hinzu
        Enemies.add(newEnemy);
        
        //printed Infos für den Debug, Selbsterklärend
        System.out.println("Spawned Enemy at: " + (newEnemy.PosX - Camera.PosX) + ", " + (newEnemy.PosY - Camera.PosY));
        System.out.println("Displaying at: " + newEnemy.PosX + "," + newEnemy.PosY);
    }
    
    public void die() {
        Enemies.remove(this); //aus den angezeigten und brechneten Enemies entfernen
        EnemyPool.add(this); //in die nicht angezeigten und brechneten Enemies hinzufügen. Zwischenspeicher. Kann daraus später wieder belebt/recycled werden
    }

    public void setMaxHp(int newMaxHP) {
        MAX_HP = newMaxHP;
    }

    public void setHp(int newHP) {
        HP = newHP;
    }

    public void damageEnemy(int Damage) {
        HP -= Damage;
    }

}
