package Screens;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WelcomeScreen implements Screen {

    RoadHog game;

    private SpriteBatch batch;

    private OrthographicCamera camera;
    private Viewport viewport;

    public static Stage stage;

    private ImageButton double_btn;
    private ImageButton get_25_btn;

    private Texture welcome_texture;
    private Texture get25_texture;
    private Texture double_texture;
    private Image welcome_bg;

    public WelcomeScreen(final RoadHog game) {

        this.game = game;


        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);
        stage = new Stage(viewport);

        double_texture = new Texture("buttons/double1_btn.png");
        get25_texture = new Texture("buttons/get_25_btn.png");
        welcome_texture = new Texture("Screens/welcome_bg.png");

        double_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        get25_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        welcome_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        welcome_bg = new Image(welcome_texture);

        double_btn = new ImageButton(new TextureRegionDrawable(double_texture),
                new TextureRegionDrawable(new Texture("buttons/double1_btn_pressed.png")));
        get_25_btn = new ImageButton(new TextureRegionDrawable(get25_texture),
                new TextureRegionDrawable(new Texture("buttons/get_25_btn_pressed.png")));

        double_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.class_name = 'B';
            }
        });

        get_25_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScreen.coins += 25;
                MenuScreen.preffs.save_coins();
                RoadHog.soundManager.run_click_sound();
                game.setMenuScreen(game);
            }
        });

        stage.addActor(welcome_bg);

        //stage.setDebugAll(true);

        double_btn.setPosition(220, 340);
        get_25_btn.setPosition(45, 340);

        stage.addActor(double_btn);
        stage.addActor(get_25_btn);


        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        RoadHog.menuScreen.render(1);
        camera.update();


        batch.begin();

        batch.end();


        stage.act();
        stage.draw();
        batch.setProjectionMatrix(camera.combined);

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

    }
}
