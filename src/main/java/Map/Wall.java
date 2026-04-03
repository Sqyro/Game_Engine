package Map;

import Physics2D.PhysicsObject2D;
import Physics2D.Hitbox;

public class Wall extends PhysicsObject2D {
    public Wall(int PosX, int PosY, float WallLength, float WallHeight, int TextureID, Hitbox Hitbox) {
        super(PosX, PosY, WallLength, WallHeight, TextureID, Hitbox);
    }
}
