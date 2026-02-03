package Enemy;

import Physics2D.PhysicsObject2D;

public class Enemy extends PhysicsObject2D {
    public static PhysicsObject2D Enemy;
    
    public static int[] PositionData = {};
    public static int EnemyID = 0;
    
    public static void Spawn(int posX, int posY, int ID) {
        EnemyID = ID;
        PositionData[EnemyID*2-1] = posX;
        PositionData[EnemyID*2] = posY;
    }
}
