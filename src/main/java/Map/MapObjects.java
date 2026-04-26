package Map;

import Physics2D.CircleCollider;
import Physics2D.ISolidCollider;
import Registry.DeferredRegister;
import Rendering.ImageManager;

public class MapObjects {
    public static final DeferredRegister<MapObject> MAP_OBJECTS = new DeferredRegister<>();

    public static void RegisterMapObjects() {
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_CROSS, new CircleCollider(20, 0, 0), "Gravestone_Cross"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_SMALL, new CircleCollider(20, 0, 0), "Gravestone_Small"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_BIG, new CircleCollider(20, 0, 0), "Gravestone_Big"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_1, new CircleCollider(20, 0, 0), "Gravestone_1"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_2, new CircleCollider(20, 0, 0), "Gravestone_2"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_3, new CircleCollider(20, 0, 0), "Gravestone_3"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_4, new CircleCollider(20, 0, 0), "Gravestone_4"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.CHRISTMAS_TREE, new CircleCollider(20, 0, 0), "Christmas_Tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.DECIDUOUS_TREE, new CircleCollider(20, 0, 0), "Deciduous_Tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.CHINESE_ARBORVITAE, new CircleCollider(20, 0, 0), "Chinese_Arborvitae"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.DEAD_TREE, new CircleCollider(20, 0, 0), "Dead_Tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.SMALL_ROCK, new CircleCollider(20, 0, 0), "Small_Rock"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.BIG_ROCK, new CircleCollider(20, 0, 0), "Big_Rock"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.HUGE_ROCK, new CircleCollider(20, 0, 0), "Huge_Rock"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.DEAD_LYING_TREE, new CircleCollider(20, 0, 0), "Dead_Lying_Tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.LANTERN, new CircleCollider(20, 0, 0), "Lantern"));
        System.out.println("Map Objects registered");
    }
}
