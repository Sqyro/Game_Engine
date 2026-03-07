package Map;

import Physics2D.PhysicsObject2D;

public class Wall extends PhysicsObject2D {
    public Wall(int PosX, int PosY, int WallLength, int WallHeight, int TextureID) {
        super(PosX, PosY, WallLength, WallHeight, TextureID);
    }
}
