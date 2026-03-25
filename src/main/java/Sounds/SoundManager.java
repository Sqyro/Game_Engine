package Sounds;

import Rendering.Frame;

public class SoundManager {
    private long lastSoundTime = 0;
    
    public void PlayASound(String SoundName, int TimeInMilliseconds, float Volume) {
        SoundHandler.playSound(SoundName, TimeInMilliseconds, Volume);
    }
    
    public void PlaySoundsWithDelay(String SoundName, int TimeInMilliseconds, float Volume, float DelayInMilliseconds) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSoundTime >= DelayInMilliseconds) {
            lastSoundTime = currentTime;
            PlayASound(SoundName, TimeInMilliseconds, Volume);
        }
    }
    
    public void PlayASoundAtPos(String SoundName, int TimeInMilliseconds, float Volume, float PosX, float PosY, float Falloff) {
        SoundHandler.playSoundAtPos(SoundName, TimeInMilliseconds, Volume, PosX, PosY, Falloff);
    }
    
    public void PlaySoundsWithDelayAtPos(String SoundName, int TimeInMilliseconds, float Volume, float DelayInMilliseconds, float PosX, float PosY, float Falloff) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSoundTime >= DelayInMilliseconds) {
            lastSoundTime = currentTime;
            PlayASoundAtPos(SoundName, TimeInMilliseconds, Volume, PosX, PosY, Falloff);
        }
    }
}
