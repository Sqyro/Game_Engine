package Rendering;

import java.util.List;
import java.util.ArrayList;

public class ImageManager {
    //Variablen Deklarieren
    public static int PLAYER;
    public static int ENEMY;
    
    public static int TESTITEM;
    
    public static List<Integer> TileTextures = new ArrayList<>();
    public static int TileAmount = 3;
    
    public static int GAMEFONT;
    
    //Methode um alle texturen zu laden beim Game start
    public static void loadAllTextures() {
        try {
        //Hier einfach alle Texturen mit .loadTextures reinschreiben die man will. Gibt den Integer für die Texture ID in OpenGL zurück
        PLAYER = ImageHandler.loadTexture("src/main/resources/assets/textures/player/Player.png");

        ENEMY = ImageHandler.loadTexture("src/main/resources/assets/textures/enemy/Enemy.png");

        TESTITEM = ImageHandler.loadTexture("src/main/resources/assets/textures/enemy/Enemy.png");
        
        for(int i = 0; i <= TileAmount-1; i++) {
            int TempTileTextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/Tile" + i + ".png");
            TileTextures.add(TempTileTextureID);
        }
        
        GAMEFONT = ImageHandler.loadTexture("src/main/resources/assets/fonts/font.png");
        
        System.out.println("Textures loaded"); //Nachricht für den Dabug
        } catch (Exception e) { //Falls es nicht geht

            System.err.println("Failed to load Textures!"); //Nachricht für den Debug
            e.printStackTrace(); //Stacktrace ausgeben auch für debugging

            System.exit(-1); //Programm schließen
        }
    }
}