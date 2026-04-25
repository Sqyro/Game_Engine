package Inputs.Actions;

import Inputs.InputHandler;
import Inputs.KeyAction;
import Player.Player;
import Scenes.GameScene;

public class WalkAction extends KeyAction {
    private static volatile boolean upPressed = false;
    private static volatile boolean downPressed = false;
    private static volatile boolean leftPressed = false;
    private static volatile boolean rightPressed = false;
    
    public WalkDirection WalkDirection;

    public WalkAction(WalkDirection WalkDirection) {
        this.WalkDirection = WalkDirection;
    }

    @Override
    public void onPress() {
        if(GameScene.GameRunning && !Player.Player.isDodging && Player.Player.isAlive) {
            switch (WalkDirection) {
                case UP:
                    upPressed = true; //Wurde gerückt, also ja es wurde was gerückt hier rein schreiben für später//Schau nicht weiter (Wenn das nicht hier ist, dann wartet er bis ein Key gedrückt wurde und führt dann alles aus)
                    break;
                case DOWN:
                    downPressed = true;
                    break;
                case LEFT:
                    leftPressed = true;
                    break;
                case RIGHT:
                    rightPressed = true;
                    break;
            }
        }
        
        if(GameScene.GameRunning) {
            // Wir callen Move jedes mal wenn irgendeiner von den Movement Keys jetzt gerade gedrückt wird und wenn nicht, dann stoppen wir
            if (upPressed || downPressed || leftPressed || rightPressed) {
                InputHandler.Move(Player.Player);
            } else {
                InputHandler.Stop(Player.Player);
            }
        }
    }

    @Override
    public void onRelease() {
        switch (WalkDirection) {
            case UP:
                upPressed = false;
                break;
            case DOWN:
                downPressed = false;
                break;
            case LEFT:
                leftPressed = false;
                break;
            case RIGHT:
                rightPressed = false;
                break;
        }
        
        if(GameScene.GameRunning) {
            // Wir callen Move jedes mal wenn irgendeiner von den Movement Keys jetzt gerade gedrückt wird und wenn nicht, dann stoppen wir
            if (upPressed || downPressed || leftPressed || rightPressed) {
                InputHandler.Move(Player.Player);
            } else {
                InputHandler.Stop(Player.Player);
            }
        }
    }
    
    public static void updatePlayerDirection() { // Hab den Direction Skript von oben hier runter gemoved und ihn flüssig gemacht, vorher hat der so gestottert, weil Direction für eine Frame 0 war (nach W-S oder A-D)
        //Immer vorher auf 0 setzen
        float DirX = 0;
        float DirY = 0;

        Player player = Player.Player;

        //Wenn die Keys gedrückt wurden dann addieren/Subtrahieren (nicht setzen, sonst buggt das wenn man zwei Keys gleichzeitig drückt)
        if (leftPressed) DirX -= 1;
        if (rightPressed) DirX += 1;
        if (upPressed) DirY -= 1;
        if (downPressed) DirY += 1;

        //Richtung setzen
        player.setDirectionX(DirX);
        player.setDirectionY(DirY);

        if (DirX != 0) {
            player.setLastDirectionX(DirX);
        }

        if(DirY != 0){ //Letzte Y Richtung speichern
            player.setLastDirectionY(DirY);
        }

        if (DirY == 0 && DirX != 0) { //Falls wir uns nochmal auf der X Achse bewegt haben zurücksetzen
            player.setLastDirectionY(0);
        } else if (DirX == 0 && DirY != 0) {
            player.setLastDirectionX(0);
        }
    }
}