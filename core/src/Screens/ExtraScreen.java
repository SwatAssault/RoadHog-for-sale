package Screens;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class ExtraScreen implements Screen {

    public static RoadHog game;

    private SpriteBatch batch;

    private OrthographicCamera camera;
    private Viewport viewport;

    public static Stage stage;

    private ImageButton yes_btn;
    private ImageButton no_btn;

    private Texture yes_texture;
    private Texture no_texture;
    private Texture extra_texture;
    private Image extra_bg;

    public ExtraScreen(final RoadHog game) {

        this.game = game;

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);
        stage = new Stage(viewport);

        yes_texture = new Texture("buttons/yes_btn.png");
        no_texture = new Texture("buttons/no_btn.png");

        extra_texture = new Texture("Screens/coins_25_bg.png");

        yes_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        no_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        extra_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        extra_bg = new Image(extra_texture);

        yes_btn = new ImageButton(new TextureRegionDrawable(yes_texture),
                new TextureRegionDrawable(new Texture("buttons/yes_btn_pressed.png")));
        no_btn = new ImageButton(new TextureRegionDrawable(no_texture),
                new TextureRegionDrawable(new Texture("buttons/no_btn_pressed.png")));

        yes_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.class_name = 'E';
            }
        });

        no_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                RoadHog.soundManager.run_click_sound();
                game.setGarageScreen(game);
            }
        });


        stage.addActor(extra_bg);

        //stage.setDebugAll(true);

        yes_btn.setPosition(210, 330);
        no_btn.setPosition(60, 330);

        stage.addActor(yes_btn);
        stage.addActor(no_btn);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        RoadHog.garageScreen.render(1);
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setGarageScreen(game);
        }

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
