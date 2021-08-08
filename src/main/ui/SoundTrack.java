package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;

// Source: https://www.youtube.com/watch?v=TErboGLHZGA

// Represent a soundtrack that will be played
public class SoundTrack {

    private Clip clip;

    // EFFECTS: Take a sound path in the device and plays the soundtrack
    public SoundTrack(String soundPath) {
        playSound(soundPath);
    }

    // EFFECTS: play the soundtrack
    public void playSound(String soundPath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
    }
}
