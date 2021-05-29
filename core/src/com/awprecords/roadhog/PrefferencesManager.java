package com.awprecords.roadhog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import Screens.MenuScreen;

public class PrefferencesManager {

    public static Preferences settings;

    public PrefferencesManager(){

        settings = Gdx.app.getPreferences("settings");

    }

    public void save_settings(){
        settings.putBoolean("music", MenuScreen.on_off_switches[0]);
        settings.putBoolean("vibrator", MenuScreen.on_off_switches[2]);
        settings.putBoolean("sounds", MenuScreen.on_off_switches[1]);
        settings.flush();
    }

    public void load_settings(){
        MenuScreen.on_off_switches[0] = settings.getBoolean("music", true);
        MenuScreen.on_off_switches[2] = settings.getBoolean("vibrator", true);
        MenuScreen.on_off_switches[1] = settings.getBoolean("sounds", true);
    }


    public void save_racer(){

        settings.putFloat("selected_flex", MenuScreen.selected_flex);
        settings.putString("selected_img", MenuScreen.selected_img);
        settings.putFloat("selected_turbo", MenuScreen.selected_turbo);
        settings.putFloat("selected_braking", MenuScreen.selected_braking);
        settings.putFloat("selected_flex_angle", MenuScreen.selected_flex_angle);
        settings.flush();

    }

    public void load_racer(){

        MenuScreen.selected_flex_angle = settings.getFloat("selected_flex_angle", 0.4f);
        MenuScreen.selected_flex = settings.getFloat("selected_flex", 2);
        MenuScreen.selected_turbo = settings.getFloat("selected_turbo",5);
        MenuScreen.selected_braking = settings.getFloat("selected_braking",5.5f);
        MenuScreen.selected_img = settings.getString("selected_img", "skins/skin1_1.png");

    }

    public void load_highScore(){

        MenuScreen.high_score = settings.getFloat("high_score", 0);

    }

    public void save_highScore(){

        settings.putFloat("high_score", MenuScreen.high_score);
        settings.flush();

    }

    public void load_coins(){

        MenuScreen.coins = settings.getInteger("coins", 0);
   //     MenuScreen.coins = settings.getInteger("coins", 111110);

    }


    public void save_coins(){

       //settings.putInteger("coins", 11110);
       settings.putInteger("coins", MenuScreen.coins);
        settings.flush();

    }

    public void load_date(){

        MenuScreen.last_day = settings.getInteger("day", 1);
        MenuScreen.last_month = settings.getInteger("month", 1);

    }

    public void save_date(){

        settings.putInteger("day", MenuScreen.current_day);
        settings.putInteger("month", MenuScreen.current_month);
        settings.flush();

    }

    public void load_bought(){

        MenuScreen.is_bought[0] = settings.getBoolean("bought0", true);
        MenuScreen.is_bought[1] = settings.getBoolean("bought1", false);
        MenuScreen.is_bought[2] = settings.getBoolean("bought2", false);
        MenuScreen.is_bought[3] = settings.getBoolean("bought3", false);
        MenuScreen.is_bought[4] = settings.getBoolean("bought4", false);
        MenuScreen.is_bought[5] = settings.getBoolean("bought5", false);
        MenuScreen.is_bought[6] = settings.getBoolean("bought6", false);
        MenuScreen.is_bought[7] = settings.getBoolean("bought7", false);
        MenuScreen.is_bought[8] = settings.getBoolean("bought8", false);
        MenuScreen.is_bought[9] = settings.getBoolean("bought9", false);
        MenuScreen.is_bought[10] = settings.getBoolean("bought10", false);

    }

    public void save_bought(){

        settings.putBoolean("bought0", MenuScreen.is_bought[0]);
        settings.putBoolean("bought1", MenuScreen.is_bought[1]);
        settings.putBoolean("bought2", MenuScreen.is_bought[2]);
        settings.putBoolean("bought3", MenuScreen.is_bought[3]);
        settings.putBoolean("bought4", MenuScreen.is_bought[4]);
        settings.putBoolean("bought5", MenuScreen.is_bought[5]);
        settings.putBoolean("bought6", MenuScreen.is_bought[6]);
        settings.putBoolean("bought7", MenuScreen.is_bought[7]);
        settings.putBoolean("bought8", MenuScreen.is_bought[8]);
        settings.putBoolean("bought9", MenuScreen.is_bought[9]);
        settings.putBoolean("bought10", MenuScreen.is_bought[10]);

    }
}
