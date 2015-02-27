package test5.demo.example.nfc.nfc_test5;

import android.content.Context;
import android.media.MediaPlayer;

public class Sounds {

    boolean silent=false;

    //TODO: move mediaplayers to initialization



    public static void PlayFailed(Context context, boolean silent) {
        if(!silent) {
            MediaPlayer failedPlayer = MediaPlayer.create(context, R.raw.failed);

            failedPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                }
            });
            if(failedPlayer != null) failedPlayer.start();
        }

    }

    public static void PlaySuccess(Context context,boolean silent) {
        if(!silent) {
            MediaPlayer player = MediaPlayer.create(context, R.raw.beep);

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                }
            });
            if(player != null) player.start();
        }
    }
}