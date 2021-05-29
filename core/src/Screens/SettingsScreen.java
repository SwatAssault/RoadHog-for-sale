package Screens;

import com.awprecords.roadhog.MainGame;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsScreen implements Screen {

    public static Stage stage;

    Table table;
    Table table_top;
    Table table_top_1;
    Table table2;

    public static ArrayList<TextureRegionDrawable> on_off_textures;

    ImageButton goback;

    ImageButton about_btn;

    Image settings_bg;

    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;

    TextureRegionDrawable ON;
    private Texture bg;
    private Texture home_btn_pressed_texture;
    private Texture home_btn_texture;
    private Texture about_btn_pressed_texture;
    private Texture about_btn_texture;

    public static ArrayList<Image> on_off_mass;

    public static RoadHog game;

    public SettingsScreen(final RoadHog game) {

        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);

        batch = new SpriteBatch();

        ON = new TextureRegionDrawable(new Texture("switch_ON.png"));

        on_off_textures = new ArrayList<TextureRegionDrawable>(Arrays.asList(
                new TextureRegionDrawable(new Texture("switch_OFF.png")),
                new TextureRegionDrawable(new Texture("switch_ON.png"))
        ));

        on_off_textures.get(0).getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        on_off_textures.get(1).getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        on_off_mass = new ArrayList<Image>(Arrays.asList(
                new Image(on_off_textures.get(MenuScreen.on_off_switches[0] ? 1 : 0)),
                new Image(on_off_textures.get(MenuScreen.on_off_switches[1] ? 1 : 0)),
                new Image(on_off_textures.get(MenuScreen.on_off_switches[2] ? 1 : 0))
        ));

        on_off_mass.get(0).addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (MenuScreen.on_off_switches[0] == true) {
                    RoadHog.soundManager.menu_music.setVolume(0);
                    RoadHog.soundManager.game_music.setVolume(0);
                    MenuScreen.on_off_switches[0] = false;
                    RoadHog.soundManager.run_click_sound();
                    on_off_mass.get(0).setDrawable(on_off_textures.get(MenuScreen.on_off_switches[0] ? 1 : 0));
                } else {
                    RoadHog.soundManager.menu_music.setVolume(0.1f);
                    RoadHog.soundManager.game_music.setVolume(0.05f);
                    MenuScreen.on_off_switches[0] = true;
                    RoadHog.soundManager.run_click_sound();
                    on_off_mass.get(0).setDrawable(on_off_textures.get(MenuScreen.on_off_switches[0] ? 1 : 0));
                }

                MenuScreen.preffs.save_settings();
            }
        });

        on_off_mass.get(1).addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (MenuScreen.on_off_switches[1] == true) {
                    RoadHog.soundManager.run_click_sound();
                    MenuScreen.on_off_switches[1] = false;
                    on_off_mass.get(1).setDrawable(on_off_textures.get(MenuScreen.on_off_switches[1] ? 1 : 0));
                } else {
                    RoadHog.soundManager.run_click_sound();
                    MenuScreen.on_off_switches[1] = true;
                    on_off_mass.get(1).setDrawable(on_off_textures.get(MenuScreen.on_off_switches[1] ? 1 : 0));
                }

                MenuScreen.preffs.save_settings();
            }
        });

        on_off_mass.get(2).addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (MenuScreen.on_off_switches[2] == true) {
                    RoadHog.soundManager.run_click_sound();
                    MenuScreen.on_off_switches[2] = false;
                    on_off_mass.get(2).setDrawable(on_off_textures.get(MenuScreen.on_off_switches[2] ? 1 : 0));
                } else {
                    RoadHog.soundManager.run_click_sound();
                    MenuScreen.on_off_switches[2] = true;
                    on_off_mass.get(2).setDrawable(on_off_textures.get(MenuScreen.on_off_switches[2] ? 1 : 0));

                }

                MenuScreen.preffs.save_settings();
            }
        });

        bg = new Texture("Screens/settings_bg.png");
        bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settings_bg = new Image(new Sprite(bg));

        stage = new Stage(viewport);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

        home_btn_pressed_texture = new Texture("buttons/home_btn_pressed.png");
        home_btn_pressed_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        home_btn_texture = new Texture("buttons/home_btn.png");
        home_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        goback = new ImageButton(
                new TextureRegionDrawable(home_btn_texture),
                new TextureRegionDrawable(home_btn_pressed_texture)
        );

        goback.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setMenuScreen(game);
                RoadHog.soundManager.run_click_sound();
            }
        });

        about_btn_pressed_texture = new Texture("buttons/about_btn_pressed.png");
        about_btn_pressed_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        about_btn_texture = new Texture("buttons/about_btn.png");
        about_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        about_btn = new ImageButton(
                new TextureRegionDrawable(about_btn_texture),
                new TextureRegionDrawable(about_btn_pressed_texture)
        );

        about_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
//                dispose();
//                game.setScreen(new AboutScreen(game));
                game.setAboutScreen(game);
                RoadHog.soundManager.run_click_sound();
            }
        });

        table = new Table();
        table_top = new Table();
        table_top_1 = new Table();

        table2 = new Table();

        table_top_1.setFillParent(true);
        table.setZIndex(0);
        table_top.setZIndex(1);
        table2.setZIndex(2);
        table_top_1.setZIndex(1);
        table.setFillParent(true);

        stage.addActor(settings_bg);

        //stage.setDebugAll(true);

        stage.addActor(table);

        table2.setFillParent(true);
        stage.addActor(table2);
        table2.add(about_btn).bottom().right().expand().padBottom(290).padRight(240);
        table.add(goback).expand().right().top().padRight(15).padTop(15);

        for (Image a : on_off_mass) {
            table_top.add(a).padBottom(38);
            table_top.row();
        }


        table_top_1.add(table_top).right().expand().padRight(85).padBottom(63);


        stage.addActor(table_top_1);


    }

    @Override
    public void show() {
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
    }

    public void update() {
        camera.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setMenuScreen(game);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();

        batch.setProjectionMatrix(camera.combined);

        stage.act();
        stage.draw();
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
//        bg.dispose();
//        home_btn_pressed_texture.dispose();
//        home_btn_texture.dispose();
//        about_btn_pressed_texture.dispose();
//        about_btn_texture.dispose();
//        stage.dispose();
//        batch.dispose();
    }
}
