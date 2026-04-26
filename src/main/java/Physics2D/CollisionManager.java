package Physics2D;

import Enemy.Enemy;
import Player.Player;
import Map.Wall;

import java.util.ArrayList;
import java.util.List;


public class CollisionManager {

    public static List<BoxCollider> AllBoxColliders = new ArrayList<>();

    public static void Player_Enemy () {
        LivingObject currentEnemy;
        for (int i = 0; i <= Enemy.Enemies.size() - 1; i++) {
            currentEnemy = Enemy.Enemies.get(i);
            if (checkCollisionCircle(Player.Player, currentEnemy) == true) {
                CollisionHandler.Collide(Player.Player, currentEnemy);
            }
        }
    }
    
    /*public static void Player_Tile () {
        BoxCollider currentTile;
        for (int i = 0; i < Tile.Tiles.size(); i++) {
            currentTile = Tile.Tiles.get(i);
            if (checkCollisionBox(Player.Player, currentTile) == true) {
                CollisionHandler.Collide_Tile(Player.Player, currentTile);
            }
        }
    }*/
    
    /*public static void Enemy_Tile () {
        BoxCollider currentTile;
        LivingObject currentEnemy;
        for (int i = 0; i < Tile.Tiles.size(); i++) {
            currentTile = Tile.Tiles.get(i);
            for (int j = 0; j < Enemy.Enemies.size(); j++) {
                currentEnemy = Enemy.Enemies.get(j);
                if (checkCollisionBox(currentEnemy, currentTile) == true) {
                    CollisionHandler.Collide_Tile(Player.Player, currentTile);
                }
            }
            
        }
    }*/
    
    /*
    public static void Player_Wall () {
        PhysicsObject2D currentWall;
        for (int i = 0; i <= Wall.Walls.size() - 1; i++) {
            currentWall = Wall.Walls.get(i);
            if (checkCollisionCircle(Player.Player, currentWall) == true) {
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
                if (checkCollisionCircle(currentEnemy, currentWall) == true) {
                    CollisionHandler.Collide(currentEnemy, currentWall);
                }
            }
        }        
    }
    */
    
    public static boolean checkCollisionCircle(LivingObject Object1, LivingObject Object2){//Methode um Kollision zwischen zwie Kreisen zu checken
        return ((Object1.PosX + Object1.ObjLength / 2 + Object1.Hitbox.OffsetX) - (Object2.PosX + Object2.ObjLength / 2 + Object2.Hitbox.OffsetX)) * ((Object1.PosX + Object1.ObjLength / 2 + Object1.Hitbox.OffsetX) - (Object2.PosX + Object2.ObjLength / 2 + Object2.Hitbox.OffsetX)) +
               ((Object1.PosY + Object1.ObjHeight / 2 + Object1.Hitbox.OffsetY) - (Object2.PosY + Object2.ObjHeight / 2 + Object2.Hitbox.OffsetY)) * ((Object1.PosY + Object1.ObjHeight / 2 + Object1.Hitbox.OffsetY) - (Object2.PosY + Object2.ObjHeight / 2 + Object2.Hitbox.OffsetY)) <
               (Object2.Hitbox.Radius + Object1.Hitbox.Radius) * (Object2.Hitbox.Radius + Object1.Hitbox.Radius);
    }
    public static boolean checkCollisionBox (LivingObject Object, BoxCollider Hitbox) { //Methode um Kollision zwischen einem Kreis und einem Rechteck herauszufinden
        float closestX = Math.max(Hitbox.PosX, Math.min(Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX, Hitbox.PosX + Hitbox.Length));//nächsten Punkt auf dem Rechteck herausfinden zum Kreismittelpunkt
        float closestY = Math.max(Hitbox.PosY, Math.min(Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY, Hitbox.PosY + Hitbox.Height));

        float distanceX = Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX - closestX;//die Distanz zwischen dem nächsten Punkt und dem kreismittelpunkt herausbekommen
        float distanceY = Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY - closestY;

        return (distanceX * distanceX) + (distanceY * distanceY) < (Object.Hitbox.Radius * Object.Hitbox.Radius);//mit Satz des Phythagoras vergleichen 
    }
}
