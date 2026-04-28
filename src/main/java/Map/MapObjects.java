package Map;

import Physics2D.CircleCollider;
import Physics2D.ISolidCollider;
import Registry.DeferredRegister;
import Rendering.ImageManager;

public class MapObjects {
    public static final DeferredRegister<MapObject> MAP_OBJECTS = new DeferredRegister<>();

    public static void RegisterMapObjects() {
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_CROSS, new CircleCollider(20, 0, 0), "gravestone_cross"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_SMALL, new CircleCollider(20, 0, 0), "gravestone_small"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_BIG, new CircleCollider(20, 0, 0), "gravestone_big"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_1, new CircleCollider(20, 0, 0), "gravestone_1"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_2, new CircleCollider(20, 0, 0), "gravestone_2"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_3, new CircleCollider(20, 0, 0), "gravestone_3"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.GRAVESTONE_4, new CircleCollider(20, 0, 0), "gravestone_4"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.CHRISTMAS_TREE, new CircleCollider(20, 0, 0), "christmas_tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.DECIDUOUS_TREE, new CircleCollider(20, 0, 0), "deciduous_tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.CHINESE_ARBORVITAE, new CircleCollider(20, 0, 0), "chinese_arborvitae"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.DEAD_TREE, new CircleCollider(20, 0, 0), "dead_tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.SMALL_ROCK, new CircleCollider(20, 0, 0), "small_rock"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.BIG_ROCK, new CircleCollider(20, 0, 0), "big_rock"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.HUGE_ROCK, new CircleCollider(20, 0, 0), "huge_rock"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.DEAD_LYING_TREE, new CircleCollider(20, 0, 0), "dead_lying_tree"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.LANTERN, new CircleCollider(20, 0, 0), "lantern"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.OPEN_DOOR_FENCE, new CircleCollider(20, 0, 0), "open_door_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.CLOSED_DOOR_FENCE, new CircleCollider(20, 0, 0), "closed_door_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.TOP_LEFT_FENCE, new CircleCollider(20, 0, 0), "top_left_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.TOP_RIGHT_FENCE, new CircleCollider(20, 0, 0), "top_right_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.BOTTOM_RIGHT_FENCE, new CircleCollider(20, 0, 0), "bottom_right_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.BOTTOM_LEFT_FENCE, new CircleCollider(20, 0, 0), "bottom_left_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.LEFT_SINGLE_FENCE, new CircleCollider(20, 0, 0), "left_single_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.RIGHT_SINGLE_FENCE, new CircleCollider(20, 0, 0), "right_single_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.LEFT_SIDE_FENCE, new CircleCollider(20, 0, 0), "left_side_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.RIGHT_SIDE_FENCE, new CircleCollider(20, 0, 0), "right_side_fence"));
        MAP_OBJECTS.register(new Wall(32, 32, ImageManager.DOUBLE_FENCE, new CircleCollider(20, 0, 0), "double_fence"));

        System.out.println("Map Objects registered");
    }
}
