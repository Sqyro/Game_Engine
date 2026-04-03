package Physics2D;


public class CollisionHandler {
    public static void Collide (LivingObject Object1, PhysicsObject2D Object2) {
        float DefaultVelocity = Object1.getVelocity();
        float m1x = Object1.getPosX() + Object1.getObjLength() / 2 + Object1.Hitbox.getOffsetX();
        float m2x = Object2.getPosX() + Object2.getObjLength() / 2 + Object2.Hitbox.getOffsetX();
        float m1y = Object1.getPosY() + Object1.getObjHeight() / 2 + Object1.Hitbox.getOffsetY();
        float m2y = Object2.getPosY() + Object2.getObjHeight() / 2 + Object2.Hitbox.getOffsetY();
        
        float distanceX = m1x - m2x;
        float distanceY = m1y - m2y;
        float distance = (float)Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        
        float overlap = Object1.Hitbox.getRadius() + Object2.Hitbox.getRadius() - distance;
        Object1.setPosX(Object1.getPosX() + distanceX / distance * overlap);
        Object1.setPosY(Object1.getPosY() + distanceY / distance * overlap);
        
        float VelocityX = Object1.getDirectionX() * Object1.getVelocity();
        float VelocityY = Object1.getDirectionY() * Object1.getVelocity();
        
        double dot = VelocityX * (distanceX / distance) + VelocityY * (distanceY / distance);
        if (dot < 0) {
            VelocityX -= dot * (distanceX / distance);
            VelocityY -= dot * (distanceY / distance);
        }
        float newSpeed = (float) Math.sqrt(VelocityX * VelocityX + VelocityY * VelocityY);
            
        if (newSpeed > 0.001f) {
            Object1.setDirectionX(VelocityX / newSpeed);
            Object1.setDirectionY(VelocityY / newSpeed);
            Object1.setVelocity(Math.min(newSpeed, DefaultVelocity));
        } else {
            Object1.setVelocity(0);
        }
    }
}
