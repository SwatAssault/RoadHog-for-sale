package com.diplom.tunnelRacer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import Scenes.HUD;
import Screens.DoubleScreen;
import Screens.ExtraScreen;
import Screens.GameOverScreen;
import Screens.GarageScreen;
import Screens.MenuScreen;
import Screens.PauseScreen;
import Screens.SettingsScreen;
import Screens.WelcomeScreen;

public class TunnelRacer extends Game {

    public static SoundManager soundManager;

    /////////Screens////////////////////
    public static MenuScreen menuScreen;
    public static GarageScreen garageScreen;
    public static SettingsScreen settingsScreen;
    public static MainGame mainGame;
    public static PauseScreen pauseScreen;
    public static GameOverScreen gameOverScreen;
    public static DoubleScreen doubleScreen;
    public static ExtraScreen extraScreen;
    public static WelcomeScreen welcomeScreen;
    /////////Screens////////////////////

    public char class_name;

    public TunnelRacer() {

    }

    @Override
    public void create() {
        setMenuScreen(this);
        Gdx.input.setCatchBackKey(true);

        if (soundManager == null)
            soundManager = new SoundManager();

        class_name = 'x';
    }

    public void setGameOverScreen(TunnelRacer game) {
        if (gameOverScreen == null)
            gameOverScreen = new GameOverScreen(this);
        game.setScreen(gameOverScreen);
        Gdx.input.setInputProcessor(GameOverScreen.over_stage);
    }

    public void setPauseScreen(TunnelRacer game) {
        if (pauseScreen == null)
            pauseScreen = new PauseScreen(this);
        game.setScreen(pauseScreen);
        Gdx.input.setInputProcessor(PauseScreen.pause_stage);
    }

    public void setMainGame(TunnelRacer game, boolean flag) {
        if (mainGame == null)
            mainGame = new MainGame(this);
        if (flag)
            mainGame.obnulit();
        game.setScreen(mainGame);
        Gdx.input.setInputProcessor(HUD.stage);
    }

    public void setSettingsScreen(TunnelRacer game) {
        if (settingsScreen == null)
            settingsScreen = new SettingsScreen(this);
        game.setScreen(settingsScreen);
        Gdx.input.setInputProcessor(SettingsScreen.stage);
    }

    public void setGarageScreen(TunnelRacer game) {
        if (garageScreen == null)
            garageScreen = new GarageScreen(this);
        game.setScreen(garageScreen);
        Gdx.input.setInputProcessor(GarageScreen.stage);
    }

    public void setMenuScreen(TunnelRacer game) {
        if (menuScreen == null)
            menuScreen = new MenuScreen(this);
        game.setScreen(menuScreen);
        Gdx.input.setInputProcessor(MenuScreen.stage);
    }

    public void setDoubleScreen(TunnelRacer game) {
        if (doubleScreen == null) {
            doubleScreen = new DoubleScreen(this);
        }
        game.setScreen(doubleScreen);
        Gdx.input.setInputProcessor(DoubleScreen.stage);
    }

    public void setExtraScreen(TunnelRacer game) {
        if (extraScreen == null) {
            extraScreen = new ExtraScreen(this);
        }
        game.setScreen(extraScreen);
        Gdx.input.setInputProcessor(ExtraScreen.stage);
    }

    public void setWelcomeScreen(TunnelRacer game) {
        if (welcomeScreen == null) {
            welcomeScreen = new WelcomeScreen(this);
        }
        game.setScreen(welcomeScreen);
        Gdx.input.setInputProcessor(WelcomeScreen.stage);
    }
}
