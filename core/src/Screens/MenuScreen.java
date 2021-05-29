package Screens;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.PrefferencesManager;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MenuScreen implements Screen {

    public static String selected_img;

    public static float selected_turbo;
    public static float selected_braking;
    public static float selected_flex;
    public static float selected_flex_angle;

    public static float turbo_mass[];
    public static float braking_mass[];
    public static float flex_mass[];
    public static float flex_angle_mass[];

    public static int current_day;
    public static int current_month;
    public static int last_month;
    public static int last_day;

    public static Date date;
    public static Calendar calendarG;

    public static PrefferencesManager preffs;

    public static Stage stage;

    SpriteBatch batch;
    OrthographicCamera camera;

    Viewport viewport;

    Image menu_img;

    Texture exitButtonTexture, exitButtonTexture_press;
    Texture garage_btn_texture;
    Texture garage_btn_texture_press;
    Texture settingsButtonTexture, settingsButtonTexture_press;
    private Texture menuBG;
    private ImageButton tapToPlay;

    ImageButton exit_btn;
    ImageButton settings_btn;
    ImageButton garage_btn;

    private boolean welcome_flag = false;

    public static int coins;
    public static int earned_coins;

    public static boolean[] is_locked;
    public static boolean[] is_bought;

    public static float high_score;

    public static boolean[] on_off_switches;

    public MenuScreen(final RoadHog game) {
        this.game = game;

        tapToPlay = new ImageButton(new TextureRegionDrawable(new Texture("go.png")));
        tapToPlay.setWidth(540);
        tapToPlay.setHeight(250);

        date = new Date(TimeUtils.millis());
        calendarG = new GregorianCalendar();
        calendarG.setTime(date);

        preffs = new PrefferencesManager();

        preffs.load_date();
        preffs.load_racer();
        preffs.load_coins();
        preffs.load_highScore();

        current_day = calendarG.get(Calendar.DAY_OF_MONTH);
        current_month = calendarG.get(Calendar.MONTH) + 1;

        if (last_day < current_day || last_month != current_month)
            welcome_flag = true;


        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);
        stage = new Stage(viewport);

        batch = new SpriteBatch();

        is_locked = new boolean[]{false, true, true, true, true, true, true, true, true, true, true};

        is_bought = new boolean[11];

        on_off_switches = new boolean[]{true, true, true};

        preffs.load_settings();

        preffs.load_bought();

        turbo_mass = new float[]{5, 7, 7, 7, 8, 9, 9, 10.5f, 11, 12, 13};
        braking_mass = new float[]{5.5f, 5, 5.5f, 7, 8, 8, 9, 9, 10, 12, 13};
        flex_mass = new float[]{2, 2, 2.3f, 2.2f, 2.3f, 2.5f, 2.3f, 2.7f, 2.9f, 2.9f, 3.0f};
        flex_angle_mass = new float[]{0.4f, 0.4f, 0.37f, 0.36f, 0.37f, 0.35f, 0.37f, 0.34f, 0.33f, 0.33f, 0.32f};

        garage_btn_texture = new Texture("buttons/garage_b.png");
        garage_btn_texture_press = new Texture("buttons/garage_b_press.png");
        exitButtonTexture = new Texture("buttons/exit_b.png");
        exitButtonTexture_press = new Texture("buttons/exit_b_press.png");
        settingsButtonTexture = new Texture("buttons/settings_b.png");
        settingsButtonTexture_press = new Texture("buttons/settings_b_press.png");
        menuBG = new Texture("menu.png");

        garage_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        garage_btn_texture_press.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        exitButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        exitButtonTexture_press.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settingsButtonTexture_press.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        menuBG.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        menu_img = new Image(new Sprite(menuBG));


        exit_btn = new ImageButton(
                new TextureRegionDrawable(exitButtonTexture),
                new TextureRegionDrawable(exitButtonTexture_press)
        );

        exit_btn.setPosition(430,880);
        settings_btn = new ImageButton(
                new TextureRegionDrawable(settingsButtonTexture),
                new TextureRegionDrawable(settingsButtonTexture_press)
        );
        settings_btn.setPosition(320,880);

        settings_btn.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                RoadHog.soundManager.run_click_sound();
                game.setSettingsScreen(game);
            }
        });


        exit_btn.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                RoadHog.soundManager.run_click_sound();
                System.exit(0);
            }
        });

        garage_btn = new ImageButton(
                new TextureRegionDrawable(garage_btn_texture),
                new TextureRegionDrawable(garage_btn_texture_press)
        );
        garage_btn.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                RoadHog.soundManager.run_click_sound();
                game.setGarageScreen(game);
            }
        });
        garage_btn.setPosition(66, 220);

        tapToPlay.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainGame(game, true);
                RoadHog.soundManager.stop_menu_music();
                RoadHog.soundManager.run_game_music();
                if (MenuScreen.on_off_switches[0] == false) {
                    RoadHog.soundManager.game_music.setVolume(0);
                }
            }
        });
//          stage.setDebugAll(true);

        stage.addActor(menu_img);
        stage.addActor(tapToPlay);

        stage.addActor(exit_btn);
        stage.addActor(garage_btn);
        stage.addActor(settings_btn);

//        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

        Gdx.input.setInputProcessor(stage);


        tapToPlay.setPosition(0,320);
    }

    @Override
    public void show() {
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (game.getScreen() != RoadHog.welcomeScreen)
            update();


        stage.act();
        stage.draw();

//        batch.begin();
//            particle_play.draw(batch, Gdx.graphics.getDeltaTime());
//        batch.end();

        if (welcome_flag) {
            game.setWelcomeScreen(game);
            preffs.save_date();
            welcome_flag = false;
        }
    }

    final RoadHog game;


    public void update() {

        camera.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            System.exit(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

    @Override
    public void dispose() {
////        stage.addAction(Actions.fadeOut(1f));
//        stage.dispose();
//        //   exitButtonTexture.dispose();
//        // soundManager.dispose();
//        settingsButtonTexture.dispose();
//        garage_btn_texture.dispose();
//        menuBG.dispose();
//        // batch.dispose();
    }
}