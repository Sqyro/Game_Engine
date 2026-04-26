package Scenes;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    public static Scene ActiveScene;
    public static Scene PreviousScene;

    public static List<GameScene> AllGameScenes = new ArrayList<>();
    public static final int MAX_GAME_SCENES = 4;

    public static void init() {
        for (int i = 0; i < MAX_GAME_SCENES -1; i++) {
            AllGameScenes.add(null);
        }
    }

    public static void CreateNewScene(Scene newScene, long Window) {
        newScene.onCreation(Window);
        if (newScene instanceof GameScene) {
            AllGameScenes.set(((GameScene) newScene).SceneSaveID, (GameScene) newScene);
        }
    }
    
    public static void LoadScene(Scene newScene, long Window) {
        if (ActiveScene != null) {
            PreviousScene = ActiveScene;
            ActiveScene.onUnload();
        }
        ActiveScene = newScene;
        newScene.onLoadup(Window);
    }
}