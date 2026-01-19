/* package frame;

import java.awt.Color;
import java.awt.Graphics;

public class Room {

    int[][] room = {
        {0, 0, 0, 0, 0},
        {0, 1, 1, 1, 0},
        {0, 1, 2, 1, 0},
        {0, 0, 0, 0, 0},};

    public Room() {
    }

    public void draw(Graphics g) {
        for (int i = 0; i < room[0].length; i++) {
            for (int j = 0; j < room.length; j++) {
                switch (room[j][i]) {
                    case 0:
                        g.setColor(Color.red);
                        break;
                    case 1:
                        g.setColor(Color.blue);
                        break;
                    case 2:
                        g.setColor(Color.green);
                        break;
                    default:
                        g.setColor(Color.yellow);
                }
                g.fillRect(i*64+16, j*64, 64, 64);
            }
        }
    }
} */