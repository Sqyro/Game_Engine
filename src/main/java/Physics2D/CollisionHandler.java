package Physics2D;


public class CollisionHandler {
    public static void Collide (LivingObject Object1, PhysicsObject2D Object2) {
        float DefaultVelocity = Object1.getVelocity();//Standard Velocity vom Object1 bekommen
        float m1x = Object1.getPosX() + Object1.getObjLength() / 2 + Object1.Hitbox.getOffsetX();//Position der Hitboxen einlesen
        float m2x = Object2.getPosX() + Object2.getObjLength() / 2 + Object2.Hitbox.getOffsetX();
        float m1y = Object1.getPosY() + Object1.getObjHeight() / 2 + Object1.Hitbox.getOffsetY();
        float m2y = Object2.getPosY() + Object2.getObjHeight() / 2 + Object2.Hitbox.getOffsetY();
        
        float distanceX = m1x - m2x;//Distance zwischen beiden Objects rausbekommen erst DistanzX und DistanzY, dann mit Satz des Pythagoras komplette Distanz
        float distanceY = m1y - m2y;
        float distance = (float)Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        
        float overlap = Object1.Hitbox.getRadius() + Object2.Hitbox.getRadius() - distance;//wie weit das Object1 overlapped
        Object1.setPosX(Object1.getPosX() + distanceX / distance * overlap);//um den Overlap zurückschieben
        Object1.setPosY(Object1.getPosY() + distanceY / distance * overlap);
        
        float VelocityX = Object1.getDirectionX() * Object1.getVelocity();//Velocity von Object1 in VelocityX und VelocityY aufteilen
        float VelocityY = Object1.getDirectionY() * Object1.getVelocity();
        
        double dot = VelocityX * (distanceX / distance) + VelocityY * (distanceY / distance);//herausfinden wie stark Object1 in Object2 drückt
        if (dot < 0) {
            VelocityX -= dot * (distanceX / distance);//darauf VelocityX und VelocityY anpassen
            VelocityY -= dot * (distanceY / distance);
        }
        float newSpeed = (float) Math.sqrt(VelocityX * VelocityX + VelocityY * VelocityY);//neue geschwindigkeit berechnen
            
        if (newSpeed > 0.001f) {//wenn die geschwindigkeit nicht unnötig klein ist Velocity und Direction vom Object1 updaten
            Object1.setDirectionX(VelocityX / newSpeed);
            Object1.setDirectionY(VelocityY / newSpeed);
            Object1.setVelocity(Math.min(newSpeed, DefaultVelocity));
        } else {
            Object1.setVelocity(0);//sonst auf 0 setzen
        }
    }
}
