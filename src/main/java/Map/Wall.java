package Map;

import Physics2D.PhysicsObject2D;

public class Wall extends PhysicsObject2D {
    public Wall(int PosX, int PosY, float WallLength, float WallHeight, int TextureID) {
        super(PosX, PosY, WallLength, WallHeight, TextureID);
    }
}
