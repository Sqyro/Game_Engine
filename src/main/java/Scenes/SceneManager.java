package Scenes;

public class SceneManager {
    public static Scene ActiveScene;
    public static Scene PreviousScene;
    
    public static void CreateNewScene(Scene newScene, long Window) {
        newScene.onCreation(Window);
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