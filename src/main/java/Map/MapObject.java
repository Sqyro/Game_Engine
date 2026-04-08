package Map;

import Physics2D.Hitbox;
import Physics2D.PhysicsObject2D;
import Registry.Registrable;

public class MapObject extends PhysicsObject2D implements Registrable {
    public String RegistryName;

    public MapObject(float ObjLength, float ObjHeight, int TextureID, Hitbox Hitbox, String RegistryName) {
        super(ObjLength, ObjHeight, TextureID, Hitbox);
        this.RegistryName = RegistryName;
    }

    @Override
    public String getRegistryName() {
        return RegistryName;
    }
}
