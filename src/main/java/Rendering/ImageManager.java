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

    //Texturen für mapObjects
    public static int GRAVESTONE_CROSS;
    public static int GRAVESTONE_BIG;
    public static int GRAVESTONE_SMALL;
    public static int GRAVESTONE_1;
    public static int GRAVESTONE_2;
    public static int GRAVESTONE_3;
    public static int GRAVESTONE_4;
    public static int CHRISTMAS_TREE;
    public static int DECIDUOUS_TREE;
    public static int CHINESE_ARBORVITAE ;
    public static int DEAD_TREE;
    public static int SMALL_ROCK;
    public static int BIG_ROCK;
    public static int HUGE_ROCK;
    public static int DEAD_LYING_TREE;
    public static int LANTERN;

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

            //Texturen für mapObjects
            GRAVESTONE_CROSS = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/gravestone_cross.png");
            GRAVESTONE_SMALL = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/gravestone_small.png");
            GRAVESTONE_BIG = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/gravestone_big.png");
            GRAVESTONE_1 = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/gravestone_1.png");
            GRAVESTONE_2 = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/gravestone_2.png");
            GRAVESTONE_3 = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/gravestone_3.png");
            GRAVESTONE_4 = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/gravestone_4.png");
            CHRISTMAS_TREE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/christmas_tree.png");
            DECIDUOUS_TREE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/deciduous_tree.png");
            CHINESE_ARBORVITAE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/chinese_arborvitae.png");
            DEAD_TREE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/dead_tree.png");
            SMALL_ROCK = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/small_rock.png");
            BIG_ROCK = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/big_rock.png");
            HUGE_ROCK = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/huge_rock.png");
            DEAD_LYING_TREE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/dead_lying_tree.png");
            LANTERN = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/lantern.png");

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