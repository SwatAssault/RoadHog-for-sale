package Screens;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.Racer;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen implements Screen {

    private Texture gameover_texture;

    Image gameover_bg;

    private Texture restart_texture;
    private Sprite gameover_texture_sprite;

    OrthographicCamera camera;
    Viewport viewport;

    public static Stage over_stage;

    Table table;

    SpriteBatch batch;

    public static boolean is_double_once_pressed;

    private Texture double_texture;
    private ImageButton double_btn;

    public static RoadHog game;

    private ImageButton restart_btn;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private BitmapFont font_score;

    public static boolean isX2;

    public GameOverScreen(final RoadHog game) {

        this.game = game;
        isX2 = false;
        is_double_once_pressed = false;

        //    fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PermanentMarker-Regular.ttf"));
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Rubik-BlackItalic.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 70;
        fontParameter.color = Color.WHITE;

        font = fontGenerator.generateFont(fontParameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fontParameter.size = 35;
        font_score = fontGenerator.generateFont(fontParameter);
        font_score.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        gameover_texture = new Texture("Screens/gameover_bg.png");
        gameover_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gameover_texture_sprite = new Sprite(gameover_texture);
        //  gameover_texture_sprite.setAlpha(0.5f);
        gameover_bg = new Image(gameover_texture_sprite);

        double_texture = new Texture("buttons/double_btn.png");
        double_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        double_btn = new ImageButton(new TextureRegionDrawable(double_texture),
                new TextureRegionDrawable(new Texture("buttons/double_btn_pressed.png")));

        double_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (!is_double_once_pressed) {
                    game.setDoubleScreen(game);
                    RoadHog.soundManager.run_click_sound();
                    is_double_once_pressed = true;
                }

            }

        });

        restart_texture = new Texture("buttons/restart.png");
        restart_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        table = new Table();

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);

        over_stage = new Stage(viewport);

        restart_btn = new ImageButton(
                new TextureRegionDrawable(restart_texture),
                new TextureRegionDrawable(new Texture("buttons/restart_pressed.png"))
        );

        restart_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //                dispose();
                //  game.setScreen(new MainGame(game));

                isX2 = false;
                RoadHog.soundManager.run_click_sound();
                game.setMainGame(game, true);
            }


        });

        over_stage.addActor(gameover_bg);

        // over_stage.setDebugAll(true);

        double_btn.setPosition(270, 170);
        over_stage.addActor(double_btn);

        over_stage.addActor(table);
        table.setFillParent(true);
        table.row();
        table.add(restart_btn).expandY().padTop(200);

        Gdx.input.setInputProcessor(over_stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        RoadHog.mainGame.render(1);

        update();
        over_stage.draw();

        batch.begin();
        MainGame.layout.setText(font, String.valueOf((int) Racer.score));
        font.draw(batch, String.valueOf((int) Racer.score), 130 - MainGame.layout.width / 2, 660);

        MainGame.layout.setText(font, String.valueOf((int) MenuScreen.high_score));
        font.draw(batch, String.valueOf((int) MenuScreen.high_score), 400 - MainGame.layout.width / 2, 615);

        if (!isX2) {
            MainGame.layout.setText(font_score, String.valueOf(MenuScreen.earned_coins));
            font_score.draw(batch, String.valueOf(MenuScreen.earned_coins), 140 - MainGame.layout.width / 2f, MainGame.HEIGHT / 5f + 50);
        }
        else {
            MainGame.layout.setText(font_score, String.valueOf(MenuScreen.earned_coins + "   x2"));
            font_score.draw(batch, String.valueOf(MenuScreen.earned_coins) + "   x2", 140 - (MainGame.layout.width) / 2f, MainGame.HEIGHT / 5f + 50);
        }

        batch.end();
        batch.setProjectionMatrix(camera.combined);

    }

    public void update() {

        if (Racer.score > MenuScreen.high_score) {
            MenuScreen.high_score = Racer.score;
            MenuScreen.preffs.save_highScore();
        }

        camera.update();
    }

    @Override
    public void resize(int width, int height) {

        over_stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
//        restart_texture.dispose();
//        gameover_texture.dispose();
//        fontGenerator.dispose();
//        font.dispose();
//        over_stage.dispose();

    }

}
