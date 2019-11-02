package main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;
import java.util.Map;

public class AudioController {

    /**
     * Map that stores all {@link AudioClip} in a key-value system
     */
    private Map<String, AudioClip> audioStorage;

    /**
     * Store all audio clip instances
     */
    public AudioController() {
        audioStorage = new HashMap<>();

        audioStorage.put("empty", Applet.newAudioClip(this.getClass().getResource("./res/empty.wav")));
        audioStorage.put("bomb", Applet.newAudioClip(this.getClass().getResource("./res/bomb.wav")));

    }

    /**
     * Reproduce the audioClip
     * @param audio name of the clip
     */
    public void play(String audio) {
        if(audioStorage.containsKey(audio)) {
            audioStorage.get(audio).stop();
            audioStorage.get(audio).play();
        }
    }
}
