package Inputs.Actions;

import Inputs.KeyAction;
import Player.Player;

public class DashAction extends KeyAction {

    @Override
    public void onPress() {
        if(!Player.Player.isDodging && Player.Player.isAlive) {
            System.out.println("Space Pressed");
            Player.Player.isDodging = true;
        }
    }

    @Override
    public void onRelease() {

    }
}
