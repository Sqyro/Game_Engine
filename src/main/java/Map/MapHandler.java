package Map;

import GUI.ImageHandler;
import GUI.Camera;
import Player.Player;
import Shader.Shader;
import GUI.ImageManager;

public class MapHandler {
    
    //Variabel Deklaration
    public static final float TileSize = 48;
    
    //Leerer Contructor for the love of the Game
    public MapHandler() {
        
    }
    
    //Array der die gesamte Map speicher (ich weiß, dass ist ne scheiß Lösung und wir sollten die Map unterteilen in viele kleine Arrays) 0, 2, 1 ist die TileID (NICHT TEXTURE ID!!)
    int[][] MAP = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };
    
    public void drawMap(Shader shader, ImageHandler renderer, int ScreenWidth, int ScreenHeight) { //Methode um die Map zu zeichnen

        int startTileX = Math.max(0, (int)-Camera.PosX / (int)TileSize); //die ersten Tiles die gerendert werden. Start für den Loop
        int startTileY = Math.max(0, (int)-Camera.PosY / (int)TileSize);

        //Die letzten Tiles die gerendert werden, eine Seite. Ende für den Loop
        int endTileX = Math.min(MAP[0].length, startTileX + (ScreenWidth / (int)TileSize) + 2); //Bildschirm wird in tiles unterteilt durch Width/TileSize. Plus StartTile, sonst würde man ja nur die ersten paar tiles in der Liste dsehen können. +2 damit man es nicht merkt
        int endTileY = Math.min(MAP.length, startTileY + (ScreenHeight / (int)TileSize) + 2);
        
        for (int j = startTileY; j < endTileY; j++) { //Geht von Links Nach Rechts die Tiles durch
            for (int i = startTileX; i < endTileX; i++) { //geht auf dieser Achse von oben nach unten die Tiles durch
                //Variablen, damit das besser lesbar ist und ich nicht das gleiche tausendmal schreiben muss
                float PosX = i * TileSize;
                float PosY = j * TileSize;
                
                //Switch um für die entsprechende Tile ID an diesem Punkt das Richtige Tile zu zeichnen (Oder erstmal das zeichnen in den que hinzu zu fügen)
                switch (MAP[j][i]) {
                    case 0:
                        renderer.drawFull(ImageManager.Tile1TextureID, PosX, PosY, TileSize, TileSize);
                        break;
                    case 1:
                        renderer.drawFull(ImageManager.Tile2TextureID, PosX, PosY, TileSize, TileSize);
                        break;
                    case 2:
                        renderer.drawFull(ImageManager.Tile3TextureID, PosX, PosY, TileSize, TileSize);
                        break;
                    default:
                        renderer.drawFull(Player.Player.getTextureID(), PosX, PosY, TileSize, TileSize);
                }
            }
        }
        
        // Flushed die Texturen für die Map.
        renderer.flush(shader, ScreenWidth, ScreenHeight);
    }
}
