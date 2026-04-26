package Rendering;

import java.util.List;
import java.util.ArrayList;

public class ImageManager {
    //Variablen Deklarieren
    public static int PLAYER;
    public static int PLAYER_SPECIAL;

    public static int ENEMY;
    
    public static List<Integer> TileTextures = new ArrayList<>();
    public static int TileAmount = 3;
    
    public static int BAR;
    public static int TESTBAR;
    
    public static int INVENTORY;
    
    public static int SWORD;

    public static int BASIC_SPELL_ANIM;
    public static int FIREBALL_SPELL_ANIM;

    public static int MAIN_MENU;

    public static int GUI_ELEMENTS;
    public static int GAMEFONT;
    public static int CURSOR;
    
    //Methode um alle texturen zu laden beim Game start
    public static void loadGameTextures() {
        try {
            //Hier einfach alle Texturen mit .loadTextures reinschreiben die man will. Gibt den Integer für die Texture ID in OpenGL zurück
            PLAYER = ImageHandler.loadTexture("src/main/resources/assets/textures/player/player.png");
            PLAYER_SPECIAL = ImageHandler.loadTexture("src/main/resources/assets/textures/player/player_special.png");

            ENEMY = ImageHandler.loadTexture("src/main/resources/assets/textures/enemy/enemy.png");
        
            for(int i = 0; i < TileAmount; i++) {
                int TempTileTextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/sheet" + i + ".png");
                TileTextures.add(TempTileTextureID);
            }
        
            BAR = ImageHandler.loadTexture("src/main/resources/assets/textures/hud/bar/bar.png");
        
            TESTBAR = ImageHandler.loadTexture("src/main/resources/assets/textures/hud/bar/TestBar/TestBar.png");
        
            INVENTORY = ImageHandler.loadTexture("src/main/resources/assets/textures/gui/screens/inventory.png");
        
            SWORD = ImageHandler.loadTexture("src/main/resources/assets/textures/item/sword.png");

            BASIC_SPELL_ANIM = ImageHandler.loadTexture("src/main/resources/assets/textures/spell/basic.png");
            FIREBALL_SPELL_ANIM = ImageHandler.loadTexture("src/main/resources/assets/textures/spell/fire_ball.png");

            System.out.println("Game Textures loaded"); //Nachricht für den Debug
        } catch (Exception e) { //Falls es nicht geht

            System.err.println("Failed to load Game Textures"); //Nachricht für den Debug
            e.printStackTrace(); //Stacktrace ausgeben auch für debugging

            System.exit(-1); //Programm schließen
        }
    }
    
    public static void loadStartTextures() {
        try {
            //Hier einfach alle Texturen mit .loadTextures reinschreiben die man will. Gibt den Integer für die Texture ID in OpenGL zurück

            MAIN_MENU = ImageHandler.loadTexture("src/main/resources/assets/textures/backgrounds/main_menu.png");

            GUI_ELEMENTS = ImageHandler.loadTexture("src/main/resources/assets/textures/gui/gui_elements.png");
        
            GAMEFONT = ImageHandler.loadTexture("src/main/resources/assets/fonts/font.png");
        
            CURSOR = ImageHandler.loadTexture("src/main/resources/assets/textures/gui/cursor/cursor3.png");
        
            System.out.println("Starting Textures loaded"); //Nachricht für den Dabug

        } catch (Exception e) { //Falls es nicht geht
            System.err.println("Failed to load Starting Textures"); //Nachricht für den Debug
            e.printStackTrace(); //Stacktrace ausgeben auch für debugging

            System.exit(-1); //Programm schließen
        }
    }
}