package Physics2D;

import Enemy.Enemy;
import Player.Player;

public class CollisionManager {
    public static void Player_Enemy () {
        if (!Enemy.Enemies.isEmpty()) {
            for (int i = 0; i <= Enemy.Enemies.size() - 1; i++) {
                LivingObject currentEnemy = Enemy.Enemies.get(i);
                if (checkCollision(Player.Player, currentEnemy) == true) {
                    CollisionHandler.Collide(Player.Player, currentEnemy);
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
