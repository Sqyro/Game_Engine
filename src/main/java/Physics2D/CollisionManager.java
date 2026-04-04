package Physics2D;

import Enemy.Enemy;
import Player.Player;
import Map.Wall;

public class CollisionManager {
    public static void Player_Enemy () {
        PhysicsObject2D currentEnemy;
        for (int i = 0; i <= Enemy.Enemies.size() - 1; i++) {
            currentEnemy = Enemy.Enemies.get(i);
            if (checkCollision(Player.Player, currentEnemy) == true) {
                CollisionHandler.Collide(Player.Player, currentEnemy);
            }
        }
    }
  
    public static void Player_Wall () {
        PhysicsObject2D currentWall;
        for (int i = 0; i <= Wall.Walls.size() - 1; i++) {
            currentWall = Wall.Walls.get(i);
            if (checkCollision(Player.Player, currentWall) == true) {
                CollisionHandler.Collide(Player.Player, currentWall);
            }
        }
    }
    
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
    
    public static boolean checkCollision(PhysicsObject2D Object1, PhysicsObject2D Object2){
        return ((Object1.getPosX() + Object1.getObjLength() / 2 + Object1.Hitbox.getOffsetX()) - (Object2.getPosX() + Object2.getObjLength() / 2 + Object2.Hitbox.getOffsetX())) * ((Object1.getPosX() + Object1.getObjLength() / 2 + Object1.Hitbox.getOffsetX()) - (Object2.getPosX() + Object2.getObjLength() / 2 + Object2.Hitbox.getOffsetX())) +
               ((Object1.getPosY() + Object1.getObjHeight() / 2 + Object1.Hitbox.getOffsetY()) - (Object2.getPosY() + Object2.getObjHeight() / 2 + Object2.Hitbox.getOffsetY())) * ((Object1.getPosY() + Object1.getObjHeight() / 2 + Object1.Hitbox.getOffsetY()) - (Object2.getPosY() + Object2.getObjHeight() / 2 + Object2.Hitbox.getOffsetY())) < 
               (Object2.Hitbox.getRadius() + Object1.Hitbox.getRadius()) * (Object2.Hitbox.getRadius() + Object1.Hitbox.getRadius());
    }
}
