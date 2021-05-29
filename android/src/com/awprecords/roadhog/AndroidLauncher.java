package com.awprecords.roadhog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication {

    private static final String TAG = "AndroidLauncher";

    Handler handler_up;
    Handler handler_down;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        config.hideStatusBar = true;

        RelativeLayout layout = new RelativeLayout(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler_up = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };
        handler_down = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };

        View gameView = initializeForView(new RoadHog(), config);
        layout.addView(gameView);

        setContentView(layout);

        try { Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        } catch (Exception e) {}
    }


    @Override
    protected void onPause() {
//        if()
//        SoundManager.police_sound.stop();
//        SoundManager.booster_pickup_sound.stop();
//        SoundManager.buy_sound.stop();
//        SoundManager.click_sound.stop();
//        SoundManager.game_music.stop();
//        SoundManager.menu_music.stop();
//        System.exit(0);
        super.onPause();
    }
}
