package musicary.model.client;

import java.io.*;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import musicary.controllers.MainController;
import musicary.model.Song;


public class PlayerAudio {

    private AdvancedPlayer player = null;

    private boolean inPlay = false;
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

    public Song getPlayerSong(){return song;}

    public int getPausedOnFrame(){ return pausedOnFrame;}
    public void setPausedOnFrame(int pausedOnFrame){ this.pausedOnFrame = pausedOnFrame;}

    private String secToString(int totalSecs){
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public Integer getSecond(){return second;}

    public void setSecond(int second) {this.second = second;}

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public boolean isRunning() {

        player.setPlayBackListener(new PlaybackListener() {

            @Override
            public void playbackFinished(PlaybackEvent event) {
                inPlay = false;
                if(pausedOnFrame >= end) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.playNextAudio(null);
                        }
                    });
                }
            }

            @Override
            public void playbackStarted(PlaybackEvent event) {
                controller.setEndSongTime(secToString(song.getDuration()));
                inPlay = true;
            }
        });
        return inPlay;
    }


    public void UpdateFrame() {

        Timer myTimer = new Timer();
        TimerTask frame = new TimerTask() {
            @Override
            public void run() {
                if (isPlaying()) {
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
                    while(inPlay){
                        //sleep
                    }
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

    public boolean isPlaying(){
        return inPlay;
    }

    public int getEnd() {
        return end;
    }
}
