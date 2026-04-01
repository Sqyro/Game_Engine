package Sounds;

import Scenes.GameScene;

public class SoundManager {
    private float lastSoundTime = 0;
    
    public void PlayASound(String SoundName, int TimeInMilliseconds, float Volume) {
        SoundHandler.playSound(SoundName, TimeInMilliseconds, Volume);
    }
    
    public void PlaySoundsWithDelay(String SoundName, int TimeInSeconds, float Volume, float DelayInSeconds) {
        float currentTime = GameScene.Gametime;
        
        if (currentTime - lastSoundTime >= DelayInSeconds || lastSoundTime == 0) {
            lastSoundTime = currentTime;
            PlayASound(SoundName, TimeInSeconds, Volume);
        }
    }
    
    public void PlayASoundAtPos(String SoundName, int TimeInMilliseconds, float Volume, float PosX, float PosY, float Falloff) {
        SoundHandler.playSoundAtPos(SoundName, TimeInMilliseconds, Volume, PosX, PosY, Falloff);
    }
    
    public void PlaySoundsWithDelayAtPos(String SoundName, int TimeInMilliseconds, float Volume, float DelayInMilliseconds, float PosX, float PosY, float Falloff) {
        float currentTime = GameScene.Gametime;

        if (currentTime - lastSoundTime >= DelayInMilliseconds || lastSoundTime == 0) {
            lastSoundTime = currentTime;
            PlayASoundAtPos(SoundName, TimeInMilliseconds, Volume, PosX, PosY, Falloff);
        }
    }
}
