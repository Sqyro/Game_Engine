package Map;

import GUI.ImageHandler;
import GUI.Camera;
import Player.Player;

public class MapHandler {
    private final int TileSize = 48;
    
    public int Tile1TextureID;
    public int Tile2TextureID;
    public int Tile3TextureID;
    
    public MapHandler() {
        try {
            Tile1TextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/Tile1.png", TileSize, TileSize);
            Tile2TextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/Tile2.png", TileSize, TileSize);
            Tile3TextureID = ImageHandler.loadTexture("src/main/resources/assets/textures/tiles/Tile3.png", TileSize, TileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    int[][] MAP = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };
    
    public void draw() {
        int screenWidth = main.Main.ScreenWidth;
        int screenHeight = main.Main.ScreenHeight;

        int startTileX = Math.max(0, (int)-Camera.PosX / TileSize); //die ersten Tiles die gerendert werden
        int startTileY = Math.max(0, (int)-Camera.PosY / TileSize);

        //Die letzten Tiles die gerendert werden, eine Seite
        int endTileX = Math.min(MAP[0].length, startTileX + (screenWidth / TileSize) + 2);  //Bildschirm wird in tiles unterteilt durch Width/TileSize. Plus StartTile, sonst würde man ja nur die ersten paar tiles in der Liste dsehen können. +2 damit man es nicht merkt
        int endTileY = Math.min(MAP.length, startTileY + (screenHeight / TileSize) + 2);
        
        for (int j = startTileY; j < endTileY; j++) {
            for (int i = startTileX; i < endTileX; i++) {
                int PosX = i * TileSize;
                int PosY = j * TileSize;
                
                switch (MAP[j][i]) {
                    case 0:
                        ImageHandler.draw(Tile1TextureID, PosX, PosY, TileSize, TileSize);
                        break;
                    case 1:
                        ImageHandler.draw(Tile2TextureID, PosX, PosY, TileSize, TileSize);
                        break;
                    case 2:
                        ImageHandler.draw(Tile3TextureID, PosX, PosY, TileSize, TileSize);
                        break;
                    default:
                        ImageHandler.draw(Player.Player.getTextureID(), PosX, PosY, TileSize, TileSize);
                }
            }
        }
    }
}
