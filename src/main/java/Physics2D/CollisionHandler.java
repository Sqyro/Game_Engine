package Physics2D;


public class CollisionHandler {
    public static void Collide (LivingObject Object1, LivingObject Object2) {
        float DefaultVelocity = Object1.Velocity;//Standard Velocity vom Object1 bekommen
        float m1x = Object1.PosX + Object1.ObjLength / 2 + Object1.Hitbox.OffsetX;//Position der Hitboxen einlesen
        float m2x = Object2.PosX + Object2.ObjLength / 2 + Object2.Hitbox.OffsetX;
        float m1y = Object1.PosY + Object1.ObjHeight / 2 + Object1.Hitbox.OffsetY;
        float m2y = Object2.PosY + Object2.ObjHeight / 2 + Object2.Hitbox.OffsetY;
        
        float distanceX = m1x - m2x;//Distance zwischen beiden Objects rausbekommen erst DistanzX und DistanzY, dann mit Satz des Pythagoras komplette Distanz
        float distanceY = m1y - m2y;
        float distance = (float)Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        
        float overlap = Object1.Hitbox.Radius + Object2.Hitbox.Radius - distance;//wie weit das Object1 overlapped
        Object1.setPosX(Object1.PosX + distanceX / distance * overlap);//um den Overlap zurückschieben
        Object1.setPosY(Object1.PosY + distanceY / distance * overlap);
        
        float VelocityX = Object1.getDirectionX() * Object1.Velocity;//Velocity von Object1 in VelocityX und VelocityY aufteilen
        float VelocityY = Object1.getDirectionY() * Object1.Velocity;
        
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
    
    public static void Collide_Tile (LivingObject Object, BoxCollider Hitbox) {
        float closestX = Math.max(Hitbox.PosX, Math.min(Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX, Hitbox.PosX + Hitbox.Length));//nächsten Punkt herausfinden
        float closestY = Math.max(Hitbox.PosY, Math.min(Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY, Hitbox.PosY + Hitbox.Height));

        float distanceX = Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX - closestX;//Differenz berechnen
        float distanceY = Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY - closestY;
        float distanceSq = distanceX * distanceX + distanceY * distanceY;

        float distance = (float) Math.sqrt(distanceSq);//Distanz mit Satz des Phythagoras berechnen
        
        if (distance == 0) return;

        float overlap = Object.Hitbox.Radius - distance;//overlap berechnen

        Object.setPosX(Object.PosX + (distanceX / distance) * overlap);//um Overlap zurückschieben
        Object.setPosY(Object.PosY + (distanceY / distance) * overlap);
        
        Object.setVelocity(0);//damit das Object nicht mehr in das Rechteck drückt setzen der Velocity auf 0
    }
}
