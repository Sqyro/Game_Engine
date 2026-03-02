import javax.sound.sampled.*;
import java.io.File;

public class SoundHandler {
   public static synchronized void playSound(final String SoundName) {
    new Thread(new Runnable() {
    // The wrapper thread is unnecessary, unless it blocks on the
    // Clip finishing; see comments.
    public void run() {
      try {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/assets/sounds" + SoundName).getAbsoluteFile());
        clip.open(inputStream);
        clip.start(); 
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }).start();
} 
}
