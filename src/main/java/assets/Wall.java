package assets;

import Physics2D.PhysicsObject2D;

public class Wall extends PhysicsObject2D {
    public Wall(int PosX, int PosY, float Velocity, int[] Direction) {
        super(PosX, PosY, Velocity, Direction);
    }
}
