package com.awprecords.roadhog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import Screens.MenuScreen;

public class SoundManager {

    public static Music menu_music;
    public static Music game_music;

    public static Sound coin_sound;
    public static Sound buy_sound;
    public static Sound click_sound;

    public static Sound booster_pickup_sound;
    public static Music police_sound;

    public boolean flag;

    public SoundManager() {

        flag = true;

        police_sound = Gdx.audio.newMusic(Gdx.files.internal("Sounds/police_siren.wav"));
        police_sound.setLooping(true);
        police_sound.setVolume(0.5f);
        click_sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/click_sound.wav"));
        coin_sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/coin_sound.wav"));
        buy_sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/buy_sound.wav"));
        booster_pickup_sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/booster_pickup_sound.ogg"));

        menu_music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/menu_music.mp3"));
        menu_music.setLooping(true);

        game_music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/game_music.mp3"));
        game_music.setLooping(true);
    }

    public void run_click_sound(){

        if (MenuScreen.on_off_switches[1])
              click_sound.play();
    }

    public void run_police_sound() {
        if (MenuScreen.on_off_switches[1]){

            if(Driver.pogonya && flag){
                police_sound.play();
                flag = false;
            }

            if(!Driver.pogonya){
                police_sound.stop();
                flag = true;
            }



        }


    }

    public void run_buy_sound() {
        if (MenuScreen.on_off_switches[1])
            buy_sound.play(1.0f);
    }

    public void run_booster_pickup_sound() {
        if (MenuScreen.on_off_switches[1])
            booster_pickup_sound.play(1.0f);
    }

    public void run_coin_sound() {
        if (MenuScreen.on_off_switches[1])
            coin_sound.play(1.0f);
    }

    public void run_game_music() {

        game_music.setVolume(0.05f);
        game_music.play();

    }

    public void run_menu_music() {

        menu_music.setVolume(0.1f);
        menu_music.play();

    }

    public void stop_menu_music() {
        menu_music.stop();
    }

    public void stop_game_music() {
        game_music.stop();
    }
}
