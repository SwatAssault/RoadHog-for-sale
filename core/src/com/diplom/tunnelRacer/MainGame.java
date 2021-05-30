package com.diplom.tunnelRacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import Scenes.HUD;
import Screens.GameOverScreen;
import Screens.MenuScreen;
import items.*;

public class MainGame implements Screen {
    final TunnelRacer game;

    public static int score;
    public static boolean reIIIenieProblembl;

    public static SpriteBatch batch;
    public static OrthographicCamera camera;
    public static Background bg;

    private AnimationManager animations;
    public static Racer racer;
    public static Driver driver;
    public static Pits pits;
    public static Police police;
    public static Coins coins;
    public static Busters busters;

    private boolean GO_flag;

    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    public static BitmapFont font_score;
    public static BitmapFont font_stage;
    public static GlyphLayout layout;

    HUD hud;

    public static boolean is_hit;

    public static boolean GameOver;

    public static int HP;

    public static boolean is_paused;

    public static int WIDTH = 540;
    public static int HEIGHT = 960;


    public static long time;
    public static long time_2sec;

    private Box2DDebugRenderer b2dr;
    public static World world;
    public static int lvl;
    public long timeForStage1;


    public void obnulit() {
        reIIIenieProblembl = true;
        MenuScreen.earned_coins = 0;
        lvl = -1;
        GameOver = false;
        Racer.score = 0;
        HP = 3;
        is_hit = false;
        timeForStage1 = TimeUtils.millis();
        police.kill();
        racer.obnulit();
        driver.obnulit();
        bg.setDefaultValues();
//        if (is_paused) {
        is_paused = false;
        busters.obnulit();
//        }
        coins.obnulit();
        pits.obnulit();
        upLvl();
        GO_flag = false;
        GameOverScreen.is_double_once_pressed = false;
    }

    public MainGame(final TunnelRacer game) {
        layout = new GlyphLayout();
        reIIIenieProblembl = true;

        this.game = game;
        batch = new SpriteBatch();
        MenuScreen.earned_coins = 0;
        lvl = -1;
        animations = new AnimationManager();

        GameOver = false;
        score = 0;
        HP = 3;
        is_hit = false;
        is_paused = false;

        // fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PermanentMarker-Regular.ttf"));
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Rubik-BlackItalic.ttf"));
        GO_flag = false;
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 24;
        fontParameter.color = Color.WHITE;

        font = fontGenerator.generateFont(fontParameter);

        fontParameter.size = 30;
        font_score = fontGenerator.generateFont(fontParameter);

        fontParameter.size = 55;
        font_stage = fontGenerator.generateFont(fontParameter);

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font_score.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font_stage.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        hud = new HUD();

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.setToOrtho(false, WIDTH, HEIGHT);

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new Collisions());

        b2dr = new Box2DDebugRenderer();

        bg = new Background();
        racer = new Racer(200, 150);
        driver = new Driver();
        pits = new Pits();
        coins = new Coins();
        busters = new Busters();

        police = new Police();

        timeForStage1 = TimeUtils.millis();
        upLvl();
    }

    @Override
    public void render(float delta) {
        //    System.out.println("ef");

        if (!is_paused) {
            reIIIenieProblembl = true;
            update();
        } else {
            if (reIIIenieProblembl) {
                reIIIenieProblembl = false;
                if_paused();
            }
        }

        // System.out.println(MenuScreen.);

//        if(!GameOver){
//            update();
//        }

        batch.begin();

        bg.render(batch);
        if (!Driver.pogonya) {
            pits.render(batch);
            animations.render_coin(batch);
        }
        if (!Police.isCreate) {
            busters.render(batch);
        }
        driver.render(batch);
        busters.renderHUD(batch);
        if (!GameOver)
            racer.render(batch);

        if (police.isCreate && Driver.pogonya) {
            police.render(batch);
            animations.render_red_beacon(batch);
            animations.render_blue_beacon(batch);
            if (Police.tap_count < 10)
                animations.render_escape(batch);
            if (!Police.flag)
                animations.render_pursuit(batch);

        }

        if (Pedals.isNitro_on) {
            animations.render_nitro(batch);
        }

        layout.setText(font, String.valueOf(MenuScreen.coins));
        font.draw(batch, String.valueOf(MenuScreen.coins), HUD.coin_image.getX() - 10 - layout.width, HUD.coin_image.getY() + HUD.coin_image.getHeight() * 2 / 3 + 5);
        font_score.draw(batch, "SCORE: " + String.valueOf((int) Racer.score), hud.HP_image.getX(), hud.HP_image.getY() - 20);

        batch.end();

        batch.setProjectionMatrix(camera.combined);
       // b2dr.render(world, camera.combined.scl(PPM));

        hud.render();
        coins.renderParticles(batch);
        busters.renderParticles(batch);

        if (TimeUtils.timeSinceMillis(timeForStage1) / 1000 < 3)
            drawNewStage();

        if (Racer.isNewStage)
            drawNewStage();
    }

    public void update() {
        world.step(1 / 60f, 6, 2);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setMenuScreen(game);
        }

        hit_HP();
        if_GameOver();

        time_2sec = (System.currentTimeMillis() - time) / 1000;

        camera.update();
        bg.update();
        pits.update();
        coins.update();
        busters.update();
        hud.update();

        if (!GameOver)
            racer.update();

        driver.update();

        TunnelRacer.soundManager.run_police_sound();

        if (!police.isCreate && (int) (Racer.score) / 150 == lvl + 1)
            police.create();
        if (police.isCreate)
            police.update();
    }

    public void if_paused() {
        game.setPauseScreen(game);
    }

    public void hit_HP() {
        if (HP == 0) {
            GameOver = true;
        }
    }

    public void if_GameOver() {
        if (GameOver && !GO_flag) {
            GO_flag = true;
            game.setGameOverScreen(game);
        }
    }

    @Override
    public void show() {
        hud.show();
    }

    @Override
    public void resize(int width, int height) {
        hud.resize();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    public static void upLvl() {
        lvl++;
        driver.countCar = 4 + (int) (lvl * 0.5f) <= 8 ? 4 + (int) (lvl * 0.5f) : 8;
        bg.setSpeed(7 + lvl <= 11 ? 7 + lvl : 11);
        racer.setStageStepScore(1f - lvl * 0.05f > 0 ? 1f - lvl * 0.05f : 0.05f);
        police.delta = 0.5f - (lvl - 1) / 15f > 0 ? 0.5f - (lvl - 1) / 15f : 0.09f;
    }

    public static void drawNewStage() {
        batch.begin();
        font_stage.draw(batch, "STAGE " + (lvl + 1), WIDTH / 2f - 105, HEIGHT / 2f + 90);
        batch.end();
    }

    @Override
    public void dispose() {
        //world.dispose();
        hud.dispose();
        racer.dispose();
        driver.dispose();
        police.dispose();
        //    bg.dispose();
        //  batch.dispose();
        //b2dr.dispose();
        //pause_stage.dispose();

        fontGenerator.dispose();
        font.dispose();
        font_score.dispose();
        font_stage.dispose();


        lvl = -1;
        //    MenuScreen.settings.putInteger("coins", MenuScreen.coins);
        //   MenuScreen.settings.flush();

        MenuScreen.preffs.save_coins();

    }
}
