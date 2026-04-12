package Map;

import Physics2D.CircleCollider;
import Registry.DeferredRegister;
import Rendering.ImageManager;

public class MapObjects {
    public static final DeferredRegister<MapObject> MAP_OBJECTS = new DeferredRegister<>();

    public static void RegisterMapObjects() {
        MAP_OBJECTS.register(new Wall(50, 50, ImageManager.ENEMY, new CircleCollider(22, 0, 0), "Wall"));
        System.out.println("Map Objects registered");
    }
}
