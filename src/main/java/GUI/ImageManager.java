package GUI;

import Player.Player;

public class ImageManager {
    public static int PLAYER;
    public static int ENEMY;

    public static void loadAllTextures() {
        try {
        PLAYER = ImageHandler.loadTexture("src/main/resources/assets/textures/player/Player.png", Player.PlayerSizeX, Player.PlayerSizeY);

        ENEMY = ImageHandler.loadTexture("src/main/resources/assets/textures/enemy/Enemy.png", 50, 50);

        System.out.println("Textures loaded");
        } catch (Exception e) {

            System.err.println("Failed to load textures!");
            e.printStackTrace();

            System.exit(-1);
        }
    }
}
