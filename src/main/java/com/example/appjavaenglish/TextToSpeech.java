package com.example.appjavaenglish;

import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextToSpeech {
    private static final String API_KEY = "ee1a861047db41e3aed6cca75554a826";
    private static final String AUDIO_PATH = "src/main/resources/com/example/appjavaenglish/voice/audio.wav";

    public static String language = "en-gb";
    public static String Name = "Linda";
    public static double speed = 0.8;

    public static void speakWord(String word, String language) throws Exception {
        VoiceProvider tts = new VoiceProvider(API_KEY);
        VoiceParameters params = new VoiceParameters(word, AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(language);
        params.setVoice(Name);
        params.setRate((int) Math.round(-2.9936 * speed * speed + 15.2942 * speed - 12.7612));

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream(AUDIO_PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();

        playAudio(AUDIO_PATH);
    }

    private static void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Đợi âm thanh phát xong
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);

            clip.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        speakWord("Hello everyone","en-gb" );
    }
}
