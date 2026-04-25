package Enemy;

import Physics2D.LivingObject;
import java.util.ArrayList;

import Physics2D.VelocityHandler;
import Player.Player;
import Rendering.Camera;
import Physics2D.CircleCollider;
import Scenes.GameScene;

public class Enemy extends LivingObject { //Enemy ist ein Living Object, also ein sich bewegendes
    public static float ENEMY_HITBOX_RADIUS = 22;

    public static int ENEMY_MOVEMENT_SPEED = 400;
    public static float ENEMY_MAX_DETECTION_RANGE = 600;
    public static int ENEMY_ATTACK_RANGE = 5;
    public static int ENEMY_ATTACK_DAMAGE = 5;
    public static float ENEMY_ATTACK_COOLDOWN = 1f;

    // Lebende Enemies werden hier gespeichert
    public static ArrayList<Enemy> Enemies = new ArrayList<>();
    
    //Tote Enemies werden hier gespeichert, damit ich sie später wieder "recyclen" kann
    public static ArrayList<Enemy> EnemyPool = new ArrayList<>();

    private float lastDamageTime;

    public Enemy(float PosX, float PosY, float EnemyLength, float EnemyHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox, float Max_HP) { //Constructor für einen neuen Enemy
        super(PosX, PosY, EnemyLength, EnemyHeight, TextureID, Velocity, Direction, Hitbox, Max_HP); //Passed einfach nur alle Values weiter an Living Object
        this.lastDamageTime = 0;
    }
    
    //Soll einen neuen Enemy Spawnen
    public static void Spawn(float PosX, float PosY, float EnemyLength, float EnemyHeight, int TextureID, float Velocity, float[] Direction, CircleCollider Hitbox, float Max_HP) {
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
            newEnemy.setMaxHp(Max_HP);
            newEnemy.setHp(Max_HP);

        } else {
            //Erstellt neues Enemy Object mit den reinkommenden Values, wenn kein alter recycled werden kann
            newEnemy = new Enemy((int)PosX, (int)PosY, (int)EnemyLength, (int)EnemyHeight, TextureID, Velocity, Direction, Hitbox, Max_HP);
        }
        
        //fügt den eben erstellten Enemy in die Liste hinzu
        Enemies.add(newEnemy);
        
        //printed Infos für den Debug, Selbsterklärend
        System.out.println("Spawned Enemy at: " + (newEnemy.PosX - Camera.PosX) + ", " + (newEnemy.PosY - Camera.PosY));
        System.out.println("Displaying at: " + newEnemy.PosX + "," + newEnemy.PosY);
    }

    public static void UpdateAllEnemyAI(float deltaTime) {
        float TargetPosX = Player.Player.PosX;
        float TargetPosY = Player.Player.PosY + Player.PLAYER_HITBOX_OFFSET_Y;

        for (int i = 0; i < Enemies.size(); i++) {
            Enemy currentEnemy = Enemies.get(i);
            if (currentEnemy.HP <= 0) {
                currentEnemy.die();
                i--;
                continue;
            }

            float DistanceX = TargetPosX - currentEnemy.PosX;
            float DistanceY = TargetPosY - currentEnemy.PosY;

            double totalDistance = Math.sqrt(DistanceX * DistanceX + DistanceY * DistanceY);

            if (totalDistance <= ENEMY_MAX_DETECTION_RANGE && totalDistance > Player.PLAYER_HITBOX_RADIUS + ENEMY_HITBOX_RADIUS) {
                currentEnemy.setVelocity(ENEMY_MOVEMENT_SPEED);
                currentEnemy.setDirectionX(DistanceX);
                currentEnemy.setDirectionY(DistanceY);
                VelocityHandler.calculatePosition(currentEnemy, deltaTime);
            } else {
                if (totalDistance <= Player.PLAYER_HITBOX_RADIUS + ENEMY_HITBOX_RADIUS + ENEMY_ATTACK_RANGE && Player.Player.isAlive && GameScene.Gametime - currentEnemy.lastDamageTime >= ENEMY_ATTACK_COOLDOWN) {
                    Player.Player.damageObject(ENEMY_ATTACK_DAMAGE);
                    currentEnemy.lastDamageTime = GameScene.Gametime;
                }
                currentEnemy.setDirectionX(0);
                currentEnemy.setDirectionY(0);
                currentEnemy.setVelocity(0);
            }
        }
    }

    public void die() {
        Enemies.remove(this); //aus den angezeigten und berechneten Enemies entfernen
        EnemyPool.add(this); //in die nicht angezeigten und berechneten Enemies hinzufügen. Zwischenspeicher. Kann daraus später wieder belebt/recycled werden
    }
}