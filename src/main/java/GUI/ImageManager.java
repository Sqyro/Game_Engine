package GUI;

import Player.Player;
import Map.MapHandler;

public class ImageManager {
    //Variablen Deklarieren
    public static int PLAYER;
    public static int ENEMY;

    public static int Tile1TextureID;
    public static int Tile2TextureID;
    public static int Tile3TextureID;
    
    //Methode um alle texturen zu laden beim Game start
    public static void loadAllTextures() {
        try {
        //Hier einfach alle Texturen mit .loadTextures reinschreiben die man will. Gibt den Integer für die Texture ID in OpenGL zurück
        PLAYER = ImageHandler.loadTexture("src/main/resources/assets/textures/player/Player.png", (int) Player.PlayerSizeX, (int) Player.PlayerSizeY);

        ENEMY = ImageHandler.loadTexture("src/main/resources/assets/textures/enemy/Enemy.png", 50, 50);

        Tile1TextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/Tile1.png", (int)MapHandler.TileSize, (int)MapHandler.TileSize);
        Tile2TextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/Tile2.png", (int)MapHandler.TileSize, (int)MapHandler.TileSize);
        Tile3TextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/Tile3.png", (int)MapHandler.TileSize, (int)MapHandler.TileSize);
        
        System.out.println("Textures loaded"); //Nachricht für den Dabug
        } catch (Exception e) { //Falls es nicht geht

            System.err.println("Failed to load Textures!"); //Nachricht für den Debug
            e.printStackTrace(); //Stacktrace ausgeben auch für debugging

            System.exit(-1); //Programm schließen
        }
    }
}
