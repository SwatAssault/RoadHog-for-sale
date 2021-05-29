package com.awprecords.roadhog;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import Scenes.HUD;
import Screens.Awprecords;
import Screens.DoubleScreen;
import Screens.ExtraScreen;
import Screens.GameOverScreen;
import Screens.GarageScreen;
import Screens.MenuScreen;
import Screens.PauseScreen;
import Screens.SettingsScreen;
import Screens.WelcomeScreen;

public class RoadHog extends Game {

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
    public static Awprecords awprecords;
    /////////Screens////////////////////

    public char class_name;

    public RoadHog() {

    }

    @Override
    public void create() {
        setMenuScreen(this);
        Gdx.input.setCatchBackKey(true);

        if (soundManager == null)
            soundManager = new SoundManager();

        class_name = 'x';
    }

    public void setGameOverScreen(RoadHog game) {
        if (gameOverScreen == null)
            gameOverScreen = new GameOverScreen(this);
        game.setScreen(gameOverScreen);
        Gdx.input.setInputProcessor(GameOverScreen.over_stage);
    }

    public void setPauseScreen(RoadHog game) {
        if (pauseScreen == null)
            pauseScreen = new PauseScreen(this);
        game.setScreen(pauseScreen);
        Gdx.input.setInputProcessor(PauseScreen.pause_stage);
    }

    public void setMainGame(RoadHog game, boolean flag) {
        if (mainGame == null)
            mainGame = new MainGame(this);
        if (flag)
            mainGame.obnulit();
        game.setScreen(mainGame);
        Gdx.input.setInputProcessor(HUD.stage);
    }

    public void setSettingsScreen(RoadHog game) {
        if (settingsScreen == null)
            settingsScreen = new SettingsScreen(this);
        game.setScreen(settingsScreen);
        Gdx.input.setInputProcessor(SettingsScreen.stage);
    }

    public void setGarageScreen(RoadHog game) {
        if (garageScreen == null)
            garageScreen = new GarageScreen(this);
        game.setScreen(garageScreen);
        Gdx.input.setInputProcessor(GarageScreen.stage);
    }

    public void setMenuScreen(RoadHog game) {
        if (menuScreen == null)
            menuScreen = new MenuScreen(this);
        game.setScreen(menuScreen);
        Gdx.input.setInputProcessor(MenuScreen.stage);
    }

    public void setDoubleScreen(RoadHog game) {
        if (doubleScreen == null) {
            doubleScreen = new DoubleScreen(this);
        }
        game.setScreen(doubleScreen);
        Gdx.input.setInputProcessor(DoubleScreen.stage);
    }

    public void setExtraScreen(RoadHog game) {
        if (extraScreen == null) {
            extraScreen = new ExtraScreen(this);
        }
        game.setScreen(extraScreen);
        Gdx.input.setInputProcessor(ExtraScreen.stage);
    }

    public void setWelcomeScreen(RoadHog game) {
        if (welcomeScreen == null) {
            welcomeScreen = new WelcomeScreen(this);
        }
        game.setScreen(welcomeScreen);
        Gdx.input.setInputProcessor(WelcomeScreen.stage);
    }
}
