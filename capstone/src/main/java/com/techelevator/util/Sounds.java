package com.techelevator.util;

import com.sun.tools.javac.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sounds {

    // use on menu selection items
    public static synchronized void playSoundBeep() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    File audio = new File("src/main/resources/Low Beep.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    // use when user inserts money
    public static synchronized void playSoundCurrencyAccepted() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    File audio = new File("src/main/resources/Currency Accepted.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    // use when change is returned
    public static synchronized void playSoundChangeReturn() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    File audio = new File("src/main/resources/Change Return.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    // use on program start
    public static synchronized void playSoundOpen() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    File audio = new File("src/main/resources/Program Open.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    // use on program close
    public static synchronized void playSoundClose() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    File audio = new File("src/main/resources/Program Close.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    // use when dispensing item to customer
    public static synchronized void playSoundDispense() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    File audio = new File("src/main/resources/Dispenser3.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    // use when user inputs incorrect input
    public static synchronized void playSoundError() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    File audio = new File("src/main/resources/Error.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
