package com.mukhi.bobaorbust;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 *  This class represents a sound player object, which plays the sound according to the activities
 *  happening in the game.
 */
public class SoundPlayer {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 3;

    private static SoundPool soundPool;
    private static int hitBobaSound;
    private static int hitGermSound;
    private static int gameOverSound;

    public SoundPlayer(GameActivity activity) {

        // SoundPool is deprecated in API level 21. (Lollipop)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        hitBobaSound = soundPool.load(activity, R.raw.hitboba, 1);
        hitGermSound = soundPool.load(activity, R.raw.hitgerm, 1);
        gameOverSound = soundPool.load(activity, R.raw.die, 1);
    }

    public void playHitBobaSound() {
        soundPool.play(hitBobaSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playHitGermSound() {
        soundPool.play(hitGermSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playGameOverSound() {
        soundPool.play(gameOverSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}

