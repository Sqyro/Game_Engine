package Map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class MapHandler {
    private final int TileSize = 48;
    
    public Image Tile1Img = Toolkit.getDefaultToolkit().getImage("src/main/resources/assets/textures/tiles/Tile1.png").getScaledInstance(TileSize, TileSize, Image.SCALE_DEFAULT);
    public Image Tile2Img = Toolkit.getDefaultToolkit().getImage("src/main/resources/assets/textures/tiles/Tile2.png").getScaledInstance(TileSize, TileSize, Image.SCALE_DEFAULT);
    public Image Tile3Img = Toolkit.getDefaultToolkit().getImage("src/main/resources/assets/textures/tiles/Tile3.png").getScaledInstance(TileSize, TileSize, Image.SCALE_DEFAULT);
    
    
    int[][] MAP = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
    
    public MapHandler() {}
    
    public void draw(Graphics g, int PosX, int PosY) {
        int screenWidth = GUI.Frame.ScreenWidth;
        int screenHeight = GUI.Frame.ScreenHeight;

        int startTileX = Math.max(0, -PosX / TileSize); //die ersten Tiles die gerendert werden
        int startTileY = Math.max(0, -PosY / TileSize);

        //Die letzten Tiles die gerendert werden, eine Seite
        int endTileX = Math.min(MAP[0].length, startTileX + (screenWidth / TileSize) + 2);  //Bildschirm wird in tiles unterteilt durch Width/TileSize. Plus StartTile, sonst würde man ja nur die ersten paar tiles in der Liste dsehen können. +2 damit man es nicht merkt
        int endTileY = Math.min(MAP.length, startTileY + (screenHeight / TileSize) + 2);
        
        for (int j = startTileY; j < endTileY; j++) {
            for (int i = startTileX; i < endTileX; i++) {
                switch (MAP[j][i]) {
                    case 0:
                        g.drawImage(Tile1Img, i*TileSize + PosX, j*TileSize + PosY, null);
                        break;
                    case 1:
                        g.drawImage(Tile2Img, i*TileSize + PosX, j*TileSize + PosY, null);
                        break;
                    case 2:
                        g.drawImage(Tile3Img, i*TileSize + PosX, j*TileSize + PosY, null);
                        break;
                    default:
                        g.drawImage(Player.InputManager.PlayerImg, i*TileSize + PosX, j*TileSize + PosY, null);
                }
            }
        }
    }
}
