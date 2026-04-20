package Inputs.Actions;

import GUI.GUIManager;
import GUI.Screens.PauseScreen;
import Inputs.KeyAction;

public class EscapeAction extends KeyAction {
    @Override
    public void onPress() {
        if(GUIManager.isScreenOpen()) {
            GUIManager.closeScreen();
        } else {
            if(GUIManager.currentScreen == null) {
                GUIManager.openScreen(new PauseScreen());
            }
        }
    }

    @Override
    public void onRelease() {

    }
}
