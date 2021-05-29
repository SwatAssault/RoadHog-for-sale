package Scenes;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.Pedals;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;

public class HUD {

    final static ArrayList<TextureRegionDrawable> nameOfHP = new ArrayList<TextureRegionDrawable>(Arrays.asList(
            new TextureRegionDrawable(new Texture("hp/green_hp.png")),
            new TextureRegionDrawable(new Texture("hp/orange_hp.png")),
            new TextureRegionDrawable(new Texture("hp/red_hp.png"))));


    public static Stage stage;

    private OrthographicCamera camera;
    private Viewport viewport;

    Table table;

    Sprite coin_sprite;

    public static Image coin_image;
    private Texture coin_texture;

    public static Pedals pedals;
    ImageButton pause_btn;

    public static ImageButton goLeft;
    public static ImageButton goRight;

    public static Image HP_image;

    private Texture pause_img;

    public static boolean goLeft_flag;
    public static boolean goRight_flag;



    public HUD() {
        goLeft_flag = false;
        goRight_flag = false;
        coin_texture = new Texture("hud_coins.png");
        coin_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        coin_sprite = new Sprite(coin_texture);
        coin_image = new Image(new SpriteDrawable(coin_sprite));

        for (TextureRegionDrawable d : nameOfHP) {
            d.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        HP_image = new Image(nameOfHP.get(0));

        pause_img = new Texture("buttons/pause_btn.png");
        pause_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pause_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        pedals = new Pedals();

        camera = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);

        stage = new Stage(viewport);

        goLeft = new ImageButton(new TextureRegionDrawable(new Texture("go.png")));
        goRight = new ImageButton(new TextureRegionDrawable(new Texture("go.png")));

        goLeft.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                goLeft_flag = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                goLeft_flag = false;
            }
        });

        goRight.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                goRight_flag = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                goRight_flag = false;
            }
        });


        pause_btn = new ImageButton(
                new TextureRegionDrawable(pause_img),
                new TextureRegionDrawable(new Texture("buttons/pause_btn_pressed.png"))
        );

        pause_btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RoadHog.soundManager.run_click_sound();
                MainGame.is_paused = true;
            }

        });

//          stage.setDebugAll(true);

        table = new Table();
        stage.addActor(table);
        table.setFillParent(true);

        table.add(HP_image).left().padLeft(65).padTop(0);
        table.add(pause_btn).right().pad(10).padTop(20);
        table.row();
        table.add(coin_image).colspan(2).right().padRight(10);
        table.row();
        table.add(goLeft).left().width(250).height(600).expand();
        table.add(goRight).right().width(250).height(600).expand();
        table.row();
        table.add(Pedals.brakes_button).left().bottom().expand().padBottom(10).padLeft(10);
        table.add(Pedals.accelerator_button).right().bottom().expand().padBottom(10).padRight(10);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

        Gdx.input.setInputProcessor(stage);
    }


    public void render() {
        stage.act();
        stage.draw();
    }

    public void update() {

        if (MainGame.HP == 3 && !HP_image.getDrawable().equals(nameOfHP.get(0))) {
            HP_image.setDrawable(nameOfHP.get(0));
        }

        if (MainGame.HP == 2 && !HP_image.getDrawable().equals(nameOfHP.get(1))) {
            HP_image.setDrawable(nameOfHP.get(1));
        }

        if (MainGame.HP == 1 && !HP_image.getDrawable().equals(nameOfHP.get(2))) {
            HP_image.setDrawable(nameOfHP.get(2));
        }

        pedals.update();
        camera.update();
    }

    public void show() {

    }


    public void resize() {
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void dispose() {
        stage.dispose();
        pause_img.dispose();
    }

}
