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
        for (int i = 0; i < MAP[0].length; i++) {
            for (int j = 0; j < MAP.length; j++) {
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
                //g.fillRect(i*64+16, j*64, 64, 64);
            }
        }
    }
}
