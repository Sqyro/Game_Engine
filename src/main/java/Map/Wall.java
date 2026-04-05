package Map;

import Physics2D.PhysicsObject2D;
import Physics2D.Hitbox;
import java.util.ArrayList;

public class Wall extends MapObject {
    //ArrayList in der die Walls gespeichert werden
    public static ArrayList<Wall> Walls = new ArrayList<>();

    public Wall(float WallLength, float WallHeight, int TextureID, Hitbox Hitbox, String RegistryName) {
        super(WallLength, WallHeight, TextureID, Hitbox, RegistryName);
    }
    
    public static void Spawn (int PosX, int PosY, float WallLength, float WallHeight, int TextureID, Hitbox Hitbox) {
        //Wall newWall;
        //neue  Wall spawnen
        //newWall = new Wall(PosX, PosY, (int)WallLength, (int)WallHeight, TextureID, Hitbox);
        //Walls.add(newWall);
    }
}
