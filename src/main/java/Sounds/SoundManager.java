package Sounds;

public class SoundManager {
    private long lastSoundTime = 0;
    
    public void PlayASound(String SoundName, int TimeInSeconds, float Volume) {
        SoundHandler.playSound(SoundName, TimeInSeconds, Volume);
    }
    
    public void PlaySoundsWithDelay(String SoundName, int TimeInSeconds, float Volume, float DelayInSeconds) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSoundTime >= DelayInSeconds * 1000) {
            lastSoundTime = currentTime;
            PlayASound(SoundName, TimeInSeconds, Volume);
        }
    }
    
    public void PlayASoundAtPos(String SoundName, int TimeInSeconds, float Volume, float PosX, float PosY, float Falloff) {
        SoundHandler.playSoundAtPos(SoundName, TimeInSeconds, Volume, PosX, PosY, Falloff);
    }
    
    public void PlaySoundsWithDelayAtPos(String SoundName, int TimeInSeconds, float Volume, float DelayInSeconds, float PosX, float PosY, float Falloff) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSoundTime >= DelayInSeconds * 1000) {
            lastSoundTime = currentTime;
            PlayASoundAtPos(SoundName, TimeInSeconds, Volume, PosX, PosY, Falloff);
        }
    }
}
