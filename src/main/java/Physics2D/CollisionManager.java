package Physics2D;

import Enemy.Enemy;
import Player.Player;
import Map.Wall;

public class CollisionManager {
    public static void Player_Enemy () {
        LivingObject currentEnemy;
        for (int i = 0; i <= Enemy.Enemies.size() - 1; i++) {
            currentEnemy = Enemy.Enemies.get(i);
            if (checkCollision(Player.Player, currentEnemy) == true) {
                CollisionHandler.Collide(Player.Player, currentEnemy);
            }
        }
    }

    /*
    public static void Player_Wall () {
        PhysicsObject2D currentWall;
        for (int i = 0; i <= Wall.Walls.size() - 1; i++) {
            currentWall = Wall.Walls.get(i);
            if (checkCollision(Player.Player, currentWall) == true) {
                CollisionHandler.Collide(Player.Player, currentWall);
            }
        }
    }
    */
    /*
    public static void Enemy_Wall () {
        LivingObject currentEnemy;
        PhysicsObject2D currentWall;
        for (int i = 0; i <= Enemy.Enemies.size() - 1; i++) {
            currentEnemy = Enemy.Enemies.get(i);
            for (int j = 0; j <= Wall.Walls.size() - 1; j++) {
                currentWall = Wall.Walls.get(j);
                if (checkCollision(currentEnemy, currentWall) == true) {
                    CollisionHandler.Collide(currentEnemy, currentWall);
                }
            }
        }        
    }
    */
    
    public static boolean checkCollision(LivingObject Object1, LivingObject Object2){
        return ((Object1.PosX + Object1.ObjLength / 2 + Object1.Hitbox.OffsetX) - (Object2.PosX + Object2.ObjLength / 2 + Object2.Hitbox.OffsetX)) * ((Object1.PosX + Object1.ObjLength / 2 + Object1.Hitbox.OffsetX) - (Object2.PosX + Object2.ObjLength / 2 + Object2.Hitbox.OffsetX)) +
               ((Object1.PosY + Object1.ObjHeight / 2 + Object1.Hitbox.OffsetY) - (Object2.PosY + Object2.ObjHeight / 2 + Object2.Hitbox.OffsetY)) * ((Object1.PosY + Object1.ObjHeight / 2 + Object1.Hitbox.OffsetY) - (Object2.PosY + Object2.ObjHeight / 2 + Object2.Hitbox.OffsetY)) <
               (Object2.Hitbox.Radius + Object1.Hitbox.Radius) * (Object2.Hitbox.Radius + Object1.Hitbox.Radius);
    }
}
