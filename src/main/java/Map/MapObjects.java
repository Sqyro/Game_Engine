package Map;

import Physics2D.Hitbox;
import Registry.DeferredRegister;
import Rendering.ImageManager;

public class MapObjects {
    public static final DeferredRegister<MapObject> MAP_OBJECTS = new DeferredRegister<>();

    public static void RegisterMapObjects() {
        MAP_OBJECTS.register(new Wall(50, 50, ImageManager.ENEMY, new Hitbox(22, 0, 0), "Wall"));
    }
}
