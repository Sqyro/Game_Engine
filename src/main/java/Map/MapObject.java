package Map;

import Physics2D.CircleCollider;
import Physics2D.LivingObject;
import Physics2D.PhysicsObject2D;
import java.util.ArrayList;
import Physics2D.ISolidCollider;
import Registry.IRegistrable;

public class MapObject extends PhysicsObject2D implements IRegistrable, ISolidCollider {
    public String RegistryName;

    public MapObject(float ObjLength, float ObjHeight, int TextureID, CircleCollider Hitbox, String RegistryName) {
        super(ObjLength, ObjHeight, TextureID, Hitbox);
        this.RegistryName = RegistryName;
    }

    @Override
    public String getRegistryName() {
        return RegistryName;
    }
    
    @Override 
    public float getPosX () {
        return Hitbox.OffsetY;
    }
    
    @Override 
    public float getPosY () {
        return Hitbox.OffsetY;
    }
    
    @Override
    public void Collide (LivingObject Object, ArrayList List) {
        PhysicsObject2D currentObject;
        for (int i = 0; i < List.size(); i++) {
            currentObject = (PhysicsObject2D) List.get(i);
            if (((Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX) - (currentObject.getPosX() + currentObject.ObjLength / 2 + currentObject.Hitbox.OffsetX)) * ((Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX) - (currentObject.getPosX() + currentObject.ObjLength / 2 + currentObject.Hitbox.OffsetX)) +
                ((Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY) - (currentObject.getPosY() + currentObject.ObjHeight / 2 + currentObject.Hitbox.OffsetY)) * ((Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY) - (currentObject.getPosY() + currentObject.ObjHeight / 2 + currentObject.Hitbox.OffsetY)) <
                (currentObject.Hitbox.Radius + Object.Hitbox.Radius) * (currentObject.Hitbox.Radius + Object.Hitbox.Radius) == true) {
                
                float DefaultVelocity = Object.Velocity;//Standard Velocity vom Object bekommen
                float Pos1X = Object.PosX + Object.ObjLength / 2 + Object.Hitbox.OffsetX;//Position der Hitboxen einlesen
                float Pos2X = currentObject.getPosX() + currentObject.ObjLength / 2 + currentObject.Hitbox.OffsetX;
                float Pos1Y = Object.PosY + Object.ObjHeight / 2 + Object.Hitbox.OffsetY;
                float Pos2Y = currentObject.getPosY() + currentObject.ObjHeight / 2 + currentObject.Hitbox.OffsetY;

                float distanceX = Pos1X - Pos2X;//Distance zwischen beiden Objects rausbekommen erst DistanzX und DistanzY, dann mit Satz des Pythagoras komplette Distanz
                float distanceY = Pos1Y - Pos2Y;
                float distance = (float)Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                float overlap = Object.Hitbox.Radius + currentObject.Hitbox.Radius - distance;//wie weit das Object overlapped
                Object.setPosX(Object.PosX + distanceX / distance * overlap);//um den Overlap zurückschieben
                Object.setPosY(Object.PosY + distanceY / distance * overlap);

                float VelocityX = Object.getDirectionX() * Object.Velocity;//Velocity von Object in VelocityX und VelocityY aufteilen sodass man nicht immer mit Direction und Velocity rechnen muss sondern eins hat
                float VelocityY = Object.getDirectionY() * Object.Velocity;

                double force = VelocityX * (distanceX / distance) + VelocityY * (distanceY / distance);//herausfinden wie stark Object in currentObject drückt
                if (force < 0) {
                    VelocityX -= force * (distanceX / distance);//darauf VelocityX und VelocityY anpassen
                    VelocityY -= force * (distanceY / distance);
                }
                float newSpeed = (float) Math.sqrt(VelocityX * VelocityX + VelocityY * VelocityY);//neue geschwindigkeit berechnen

                if (newSpeed > 0.001f) {//wenn die geschwindigkeit nicht unnötig klein ist Velocity und Direction vom Object updaten
                    Object.setDirectionX(VelocityX / newSpeed);
                    Object.setDirectionY(VelocityY / newSpeed);
                    Object.setVelocity(Math.min(newSpeed, DefaultVelocity));
                } else {
                    Object.setVelocity(0);//sonst auf 0 setzen
                }
            }
        }
    }
}
