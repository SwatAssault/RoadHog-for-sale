package Screens;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.Pedals;
import com.awprecords.roadhog.Police;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Scenes.HUD;
import shit.Busters;

public class PauseScreen implements Screen {

    private Texture return_home;
    private Texture resume_pressed_texture;

    private Texture resume_texture;
    private Texture returnHome_pressed_btn_texture;

    Table table;

    Image pause_image;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Image pause_bg;

    public static RoadHog game;
    public static Stage pause_stage;
    SpriteBatch pause_batch;
    long timeOfBuster;

    ImageButton resume_btn;
    ImageButton returnHome_btn;

    public PauseScreen(final RoadHog game) {

        timeOfBuster = TimeUtils.timeSinceMillis(Busters.time);
        this.game = game;
        pause_batch = new SpriteBatch();

        table = new Table();

        resume_texture = new Texture("buttons/resume_btn.png");
        return_home = new Texture("buttons/home_btn.png");

        returnHome_pressed_btn_texture = new Texture("buttons/home_btn_pressed.png");
        resume_pressed_texture = new Texture("buttons/resume_btn_pressed.png");

        pause_bg = new Image(new Texture("Screens/pause_bg.png"));

        resume_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return_home.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        returnHome_pressed_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        resume_pressed_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);

        pause_stage = new Stage(viewport);

        returnHome_btn = new ImageButton(
                new TextureRegionDrawable(return_home),
                new TextureRegionDrawable(returnHome_pressed_btn_texture)
        );

        resume_btn = new ImageButton(
                new TextureRegionDrawable(resume_texture),
                new TextureRegionDrawable(resume_pressed_texture)
        );

        returnHome_btn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                RoadHog.soundManager.run_click_sound();
                game.setMenuScreen(game);
                RoadHog.soundManager.run_menu_music();
                RoadHog.soundManager.stop_game_music();
                if (MenuScreen.on_off_switches[0] == false) {
                    RoadHog.soundManager.menu_music.setVolume(0);
                }
            }
        });

        resume_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(HUD.stage);

                RoadHog.soundManager.run_click_sound();
                Pedals.isNitro_on = false;
                Pedals.isBrake = false;
                game.setMainGame(game, false);
                MainGame.is_paused = false;
                Police.time = TimeUtils.millis();
                Busters.time = TimeUtils.millis() - timeOfBuster;
//                dispose();
            }

        });
        pause_stage.addActor(pause_bg);
        table.setFillParent(true);

        // pause_stage.setDebugAll(true);

        table.add(pause_image);
        table.row();
        table.add(resume_btn).expandY().bottom();
        table.row();
        table.add(returnHome_btn).expandY();


        pause_stage.addActor(table);


        Gdx.input.setInputProcessor(pause_stage);
    }

    @Override
    public void show() {
        timeOfBuster = TimeUtils.timeSinceMillis(Busters.time);
    }

    public void update() {
        camera.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setMainGame(game, false);
            MainGame.is_paused = false;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        RoadHog.mainGame.render(1);
        update();

        pause_batch.begin();

        pause_batch.end();

        pause_stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        pause_stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
//        Police.time = TimeUtils.millis();
////        Busters.time = TimeUtils.millis() - timeOfBuster;
//        pause_stage.dispose();
//        resume_texture.dispose();
//        return_home.dispose();
//        resume_pressed_texture.dispose();
//        returnHome_pressed_btn_texture.dispose();
//        pause_batch.dispose();
    }
}
