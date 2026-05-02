package Rendering;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.io.File;


public class ImageManager {

    //Variablen Deklarieren
    public static int PLAYER;
    public static int PLAYER_SPECIAL;

    public static int ENEMY;
    public static int ENEMIES;
    
    public static List<Integer> TileTextures = new ArrayList<>();
    public static List<float[]> TileData = new ArrayList<>();
    public static int TileAmount;
    
    public static int BAR;
    public static int TESTBAR;
    
    public static int INVENTORY;
    
    public static int SWORD;

    public static int BASIC_SPELL_ANIM;
    public static int FIREBALL_SPELL_ANIM;
    public static int LIGHTNINGBALL_SPELL_ANIM;

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
    public static int LEFT_CLOSED_FENCE;
    public static int LEFT_OPEN_FENCE;
    public static int RIGHT_CLOSED_FENCE;
    public static int RIGHT_OPEN_FENCE;
    public static int TOP_LEFT_FENCE;
    public static int TOP_RIGHT_FENCE;
    public static int BOTTOM_RIGHT_FENCE;
    public static int BOTTOM_LEFT_FENCE;
    public static int LEFT_SINGLE_FENCE;
    public static int RIGHT_SINGLE_FENCE;
    public static int LEFT_SIDE_FENCE;
    public static int RIGHT_SIDE_FENCE;
    public static int DOUBLE_FENCE;

    public static int MAIN_MENU;

    public static int FOREST_SAFEPOINT;
    public static int GRAVEYARD_SAFEPOINT;
    public static int SNOW_SAFEPOINT;
    public static int SWAMP_SAFEPOINT;
    public static int VULCAN_SAFEPOINT;

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

            ENEMIES = ImageHandler.loadTexture("src/main/resources/assets/textures/enemy/enemies.png");

            BAR = ImageHandler.loadTexture("src/main/resources/assets/textures/hud/bar/bar.png");

            TESTBAR = ImageHandler.loadTexture("src/main/resources/assets/textures/hud/bar/TestBar/TestBar.png");
        
            INVENTORY = ImageHandler.loadTexture("src/main/resources/assets/textures/gui/screens/inventory.png");
        
            SWORD = ImageHandler.loadTexture("src/main/resources/assets/textures/item/sword.png");

            BASIC_SPELL_ANIM = ImageHandler.loadTexture("src/main/resources/assets/textures/spell/basic.png");
            FIREBALL_SPELL_ANIM = ImageHandler.loadTexture("src/main/resources/assets/textures/spell/fire_ball.png");
            LIGHTNINGBALL_SPELL_ANIM = ImageHandler.loadTexture("src/main/resources/assets/textures/spell/lightning_ball.png");

            //für alle tiles (nils kann meine eier lecken (ohne grund musste ich das machen obowhl ich es schon hatte))
            //Nein, ich kann nicht deine Eier lecken, wir packen die Tiles auf Sheets, damit weniger geladen werden muss und jetzt lädst du trotzdem alles einzeln
            int[][] tilesheet = {
                    {9, 13, 17, 10, 1},
                    {20, 21, 22, 14, 2},
                    {16, 24, 23, 18, 3},
                    {12, 19, 15, 11, 4},
                    {5, 6, 7, 8, 0}
            };

            int counter = 0;
            int i = 0;

            while (true) {
                String path = "src/main/resources/assets/textures/tiles/sheet" + i + ".png";
                File file = new File(path);
                if (!file.exists()) {
                    break;
                }
                try {
                    BufferedImage sheet = ImageIO.read(file);
                    int textureID = ImageHandler.loadTexture(path);

                    float tileSize = 16;
                    float sheetWidth = sheet.getWidth();
                    float sheetHeight = sheet.getHeight();

                    for (int t = 1; t <= 24; t++) {
                        for (int r = 0; r < 5; r++) {
                            for (int c = 0; c < 5; c++) {

                                if (tilesheet[r][c] == t) {

                                    int x = c * 16;
                                    int y = r * 16;

                                    float textureX = x / sheetWidth;
                                    float textureY = y / sheetHeight;
                                    float textureWidth = tileSize / sheetWidth;
                                    float textureHeight = tileSize / sheetHeight;

                                    ImageManager.TileTextures.add(textureID);
                                    ImageManager.TileData.add(new float[]{textureX, textureY, textureWidth, textureHeight});

                                    counter++;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("ich hasse mein leben: " + path);
                }
                i++;
            }
            ImageManager.TileAmount = counter;

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
            LEFT_CLOSED_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/left_closed_fence.png");
            LEFT_OPEN_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/left_open_fence.png");
            RIGHT_CLOSED_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/right_closed_fence.png");
            RIGHT_OPEN_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/right_open_fence.png");
            TOP_LEFT_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/top_left_fence.png");
            TOP_RIGHT_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/top_right_fence.png");
            BOTTOM_RIGHT_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/bottom_right_fence.png");
            BOTTOM_LEFT_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/bottom_left_fence.png");
            LEFT_SINGLE_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/left_single_fence.png");
            RIGHT_SINGLE_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/right_single_fence.png");
            LEFT_SIDE_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/left_side_fence.png");
            RIGHT_SIDE_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/right_side_fence.png");
            DOUBLE_FENCE = ImageHandler.loadTexture("src/main/resources/assets/textures/mapObjects/double_fence.png");



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

            FOREST_SAFEPOINT = ImageHandler.loadTexture("src/main/resources/assets/textures/backgrounds/safepoints/forest.png");
            GRAVEYARD_SAFEPOINT = ImageHandler.loadTexture("src/main/resources/assets/textures/backgrounds/safepoints/graveyard.png");
            SNOW_SAFEPOINT = ImageHandler.loadTexture("src/main/resources/assets/textures/backgrounds/safepoints/snow.png");
            SWAMP_SAFEPOINT = ImageHandler.loadTexture("src/main/resources/assets/textures/backgrounds/safepoints/swamp.png");
            VULCAN_SAFEPOINT = ImageHandler.loadTexture("src/main/resources/assets/textures/backgrounds/safepoints/vulcan.png");

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