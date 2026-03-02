package Map;

import Physics2D.PhysicsObject2D;
import java.awt.Image;

public class Wall extends PhysicsObject2D {
    public Wall(int PosX, int PosY, float Velocity, int[] Direction, Image img) {
        super(PosX, PosY, Velocity, Direction, img);
    }
}
