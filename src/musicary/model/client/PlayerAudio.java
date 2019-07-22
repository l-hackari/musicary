package musicary.model.client;

import java.io.*;

import java.util.Timer;
import java.util.TimerTask;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import musicary.controllers.MainController;
import musicary.model.Song;


public class PlayerAudio {

    private AdvancedPlayer player = null;

    private boolean inEsecuzione = false;
    private boolean timerIsRunning = false;
    private Integer second = 0;

    private int pausedOnFrame = 0;
    private int end;
    private MainController controller;

    private Song song;


    public PlayerAudio(Song song_) throws IOException {

        song = song_;
        end = 39 * song.getDuration();

    }

    private String secToString(int totalSecs){
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public Integer getSecond(){return second;}

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public boolean isRunning() {

        player.setPlayBackListener(new PlaybackListener() {

            @Override
            public void playbackFinished(PlaybackEvent event) {
                inEsecuzione = false;
                System.out.println("terminata");
            }

            @Override
            public void playbackStarted(PlaybackEvent event) {
                controller.setEndSongTime(secToString(song.getDuration()));
                inEsecuzione = true;
                System.out.println("iniziata");
            }
        });
        return inEsecuzione;
    }


    public void UpdateFrame() {

        Timer myTimer = new Timer();
        TimerTask frame = new TimerTask() {
            @Override
            public void run() {
                if (isRunning()) {
                    pausedOnFrame += 39;
                    second++;
                    controller.incrementPBarSecond((double)second / (double)song.getDuration());
                    controller.setCurrentSongTime(secToString(second));
                }
            }
        };

        myTimer.schedule(frame, 1000, 1000);

    }


    public void play() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    player = new AdvancedPlayer(song.getAudioInput());

                    if (!isRunning()) {

                        try {

                            if (!timerIsRunning) {

                                UpdateFrame();
                                timerIsRunning = true;

                            }

                            player.play(pausedOnFrame, end);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
    }

    public void pause() {

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isRunning())
                    player.stop();
            }
        });

        t2.start();
    }

    public int getEnd() {
        return end;
    }
}
