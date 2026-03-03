package Sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class SoundHandler {
   public static synchronized void playSound(final String SoundName, long TimeInSeconds) {
    new Thread(new Runnable() {
    @Override
    public void run() {
      try {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/assets/sounds" + SoundName).getAbsoluteFile());
        clip.open(inputStream);
        clip.start();
        TimeUnit.SECONDS.sleep(TimeInSeconds);
        clip.stop();
        clip.close();
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }).start();
} 
}
