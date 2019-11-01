package main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioController {
    private Map<String, AudioClip> audioStorage;

    public AudioController() {
        audioStorage = new HashMap<>();

        audioStorage.put("empty", Applet.newAudioClip(this.getClass().getResource("./res/empty.wav")));
        audioStorage.put("bomb", Applet.newAudioClip(this.getClass().getResource("./res/bomb.wav")));

    }


    public void play(String audio) {
        audioStorage.get(audio).stop();
        audioStorage.get(audio).play();
    }
}
