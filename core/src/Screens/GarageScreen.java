package Screens;

import com.diplom.tunnelRacer.AnimationManager;
import com.diplom.tunnelRacer.MainGame;
import com.diplom.tunnelRacer.Racer;
import com.diplom.tunnelRacer.TunnelRacer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

import Skins.skin;


public class GarageScreen implements Screen {

    final TunnelRacer game;

    SpriteBatch batch;

    OrthographicCamera camera;
    Viewport viewport;

    public static Stage stage;

    PagedScrollPane scrollPane;

    public static Table container;
    Table table;
    Table table_onTop;
    Table locked;
    Table info_table;

    ImageButton go_home;
    ImageButton select_btn;

    public static Image info;
    Image garage_background;

    Image btn1;
    Image btn2;
    Image btn3;
    Image btn4;
    Image btn5;
    Image btn6;
    Image btn7;
    Image btn8;
    Image btn9;
    Image btn10;
    Image btn11;

    private Texture home_btn_pressed_texture;
    private Texture home_btn_texture;
    private Texture select_btn_pressed_texture;
    private Texture select_btn_texture;
    private Texture buy_btn_texture;
    private Texture btn1_texture;
    private Texture btn2_texture;
    private Texture btn3_texture;
    private Texture btn4_texture;
    private Texture btn5_texture;
    private Texture btn6_texture;
    private Texture btn7_texture;
    private Texture btn8_texture;
    private Texture btn9_texture;
    private Texture btn10_texture;
    private Texture btn11_texture;
    private Texture garage_background_texture;

    public static ArrayList<Integer> cost_mass;

    public static String buffer_img;
    public static float buffer_turbo;
    public static float buffer_braking;
    public static float buffer_flex;
    public static float buffer_flex_angle;

    public static int current_car;

    public static TextureRegionDrawable bought_texture;
    public static TextureRegionDrawable buy_texture;
    public static TextureRegionDrawable locked11;

    public static ArrayList<Image> four;

    public static ArrayList<TextureRegionDrawable> texture_region_mass;

    public static ArrayList<String> string40;

    public static ArrayList<Texture> texture_skin40;

    public static Image buy_btn;

    public static ImageButton plus_btn;

    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public static BitmapFont font;

    AnimationManager animation;

    private TextureRegionDrawable not_locked_11;

    private boolean four_is_checked;
    public static boolean draw_bought_stamp;

    public static float triangleX;
    public static float triangleY;

    public static ArrayList<TextureRegionDrawable> car4_1;
    public static ArrayList<TextureRegionDrawable> car4_2;
    public static ArrayList<TextureRegionDrawable> car4_3;
    public static ArrayList<TextureRegionDrawable> car4_4;
    public static ArrayList<TextureRegionDrawable> car4_5;
    public static ArrayList<TextureRegionDrawable> car4_6;
    public static ArrayList<TextureRegionDrawable> car4_7;
    public static ArrayList<TextureRegionDrawable> car4_8;
    public static ArrayList<TextureRegionDrawable> car4_9;
    public static ArrayList<TextureRegionDrawable> car4_10;
    public static ArrayList<TextureRegionDrawable> car4_11;

    public static ArrayList<skin> cars_10;

    public static boolean draw_locked;

    private ParticleEffect particle_buy;
    private ParticleEffect particle_selected;
    private static boolean isRenderParicle_buy;
    private static boolean isRenderParicle_selected;

    private Texture plus_tex;
    private Texture plus_press_tex;

    public GarageScreen(final TunnelRacer game) {

        particle_buy = new ParticleEffect();
        particle_buy.load(Gdx.files.internal("garage/particles_buy"), Gdx.files.internal("garage"));
        particle_buy.start();
        isRenderParicle_buy = false;

        particle_selected = new ParticleEffect();
        particle_selected.load(Gdx.files.internal("garage/particles_selected"), Gdx.files.internal("garage"));
        particle_selected.start();
        isRenderParicle_selected = false;

        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);

        batch = new SpriteBatch();

        four_is_checked = false;
        draw_bought_stamp = false;

        plus_tex = new Texture("garage/plus.png");
        plus_press_tex = new Texture("garage/plus_pressed.png");
        plus_tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        plus_press_tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        plus_btn = new ImageButton(new TextureRegionDrawable(plus_tex),
                new TextureRegionDrawable(plus_press_tex));

        plus_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setExtraScreen(game);
                TunnelRacer.soundManager.run_click_sound();
            }

        });


        locked11 = new TextureRegionDrawable(new Texture(Gdx.files.internal("albumGarage/album_car_11_locked.png")));
        locked11.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //   fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PermanentMarker-Regular.ttf"));
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Rubik-BlackItalic.ttf"));

        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 35;
        fontParameter.color = Color.WHITE;

        font = fontGenerator.generateFont(fontParameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        draw_locked = false;

        animation = new AnimationManager();

        cost_mass = new ArrayList<Integer>(Arrays.asList(0, 300, 400, 550, 700, 880, 1000, 1240, 1400, 1550, -1));

        home_btn_pressed_texture = new Texture("buttons/home_btn_pressed.png");
        home_btn_pressed_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        home_btn_texture = new Texture("buttons/home_btn.png");
        home_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        go_home = new ImageButton(
                new TextureRegionDrawable(home_btn_texture),
                new TextureRegionDrawable(home_btn_pressed_texture)
        );

        select_btn_pressed_texture = new Texture("buttons/select_btn_pressed.png");
        select_btn_pressed_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        select_btn_texture = new Texture("buttons/select_btn.png");
        select_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        select_btn = new ImageButton(
                new TextureRegionDrawable(select_btn_texture),
                new TextureRegionDrawable(select_btn_pressed_texture)
        );

        select_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                TunnelRacer.soundManager.run_click_sound();
                if (MenuScreen.is_bought[current_car]) {

                    if (!four_is_checked) {
                        buffer_img = string40.get(current_car * 4);
                    }

                    // if(MenuScreen.selected_img != buffer_img){
                    //   buffer_img = string40.get(current_car * 4);
                    //   }
                    //  buffer_img = string40.get(current_car * 4);
                    MenuScreen.selected_img = buffer_img;
                    MenuScreen.selected_turbo = buffer_turbo;
                    MenuScreen.selected_braking = buffer_braking;
                    MenuScreen.selected_flex = buffer_flex;
                    MenuScreen.selected_flex_angle = buffer_flex_angle;
                    four_is_checked = false;
                    isRenderParicle_selected = true;


                }


            }
        });

        bought_texture = new TextureRegionDrawable(new Texture("bought.png"));
        buy_texture = new TextureRegionDrawable(new Texture("buy_btn.png"));

        bought_texture.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        buy_texture.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buy_btn_texture = new Texture("buy_btn.png");
        buy_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        buy_btn = new Image(buy_btn_texture);

        buy_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!MenuScreen.is_locked[current_car] && !MenuScreen.is_bought[current_car] && current_car != 10 || !MenuScreen.is_locked[10]/* && !MenuScreen.is_bought[10]*/) {
                    buffer_img = string40.get(current_car * 4);
                    MenuScreen.selected_img = buffer_img;
                    MenuScreen.coins -= cost_mass.get(current_car);
                    MenuScreen.is_bought[current_car] = true;
                    MenuScreen.preffs.save_coins();
                    MenuScreen.preffs.save_bought();
                    draw_bought_stamp = true;
                    isRenderParicle_buy = true;
                    TunnelRacer.soundManager.run_buy_sound();
                    TunnelRacer.soundManager.run_click_sound();
                }


            }
        });

        texture_region_mass = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(new Texture("infos/info1.png")),
                new TextureRegionDrawable(new Texture("infos/info2.png")), new TextureRegionDrawable(new Texture("infos/info3.png")),
                new TextureRegionDrawable(new Texture("infos/info4.png")), new TextureRegionDrawable(new Texture("infos/info5.png")),
                new TextureRegionDrawable(new Texture("infos/info6.png")), new TextureRegionDrawable(new Texture("infos/info7.png")),
                new TextureRegionDrawable(new Texture("infos/info8.png")), new TextureRegionDrawable(new Texture("infos/info9.png")),
                new TextureRegionDrawable(new Texture("infos/info10.png")), new TextureRegionDrawable(new Texture("infos/info11.png"))));

        for (TextureRegionDrawable d : texture_region_mass) {
            d.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        string40 = new ArrayList<String>(Arrays.asList("skins/skin1_1.png", "skins/skin1_2.png", "skins/skin1_3.png", "skins/skin1_4.png",
                "skins/skin2_1.png", "skins/skin2_2.png", "skins/skin2_3.png", "skins/skin2_4.png",
                "skins/skin3_1.png", "skins/skin3_2.png", "skins/skin3_3.png", "skins/skin3_4.png",
                "skins/skin4_1.png", "skins/skin4_2.png", "skins/skin4_3.png", "skins/skin4_4.png",
                "skins/skin5_1.png", "skins/skin5_2.png", "skins/skin5_3.png", "skins/skin5_4.png",
                "skins/skin6_1.png", "skins/skin6_2.png", "skins/skin6_3.png", "skins/skin6_4.png",
                "skins/skin7_1.png", "skins/skin7_2.png", "skins/skin7_3.png", "skins/skin7_4.png",
                "skins/skin8_1.png", "skins/skin8_2.png", "skins/skin8_3.png", "skins/skin8_4.png",
                "skins/skin9_1.png", "skins/skin9_2.png", "skins/skin9_3.png", "skins/skin9_4.png",
                "skins/skin10_1.png", "skins/skin10_2.png", "skins/skin10_3.png", "skins/skin10_4.png",
                "skins/skin11_1.png", "skins/skin11_2.png", "skins/skin11_3.png", "skins/skin11_4.png"));

        texture_skin40 = new ArrayList<Texture>(Arrays.asList(new Texture(string40.get(0)), new Texture(string40.get(1)), new Texture(string40.get(2)), new Texture(string40.get(3)),
                new Texture(string40.get(4)), new Texture(string40.get(5)), new Texture(string40.get(6)), new Texture(string40.get(7)), new Texture(string40.get(8)),
                new Texture(string40.get(9)), new Texture(string40.get(10)), new Texture(string40.get(11)), new Texture(string40.get(12)), new Texture(string40.get(13)),
                new Texture(string40.get(14)), new Texture(string40.get(15)), new Texture(string40.get(16)), new Texture(string40.get(17)), new Texture(string40.get(18)),
                new Texture(string40.get(19)), new Texture(string40.get(20)), new Texture(string40.get(21)), new Texture(string40.get(22)), new Texture(string40.get(23)),
                new Texture(string40.get(24)), new Texture(string40.get(25)), new Texture(string40.get(26)), new Texture(string40.get(27)), new Texture(string40.get(28)),
                new Texture(string40.get(29)), new Texture(string40.get(30)), new Texture(string40.get(31)), new Texture(string40.get(32)), new Texture(string40.get(33)),
                new Texture(string40.get(34)), new Texture(string40.get(35)), new Texture(string40.get(36)), new Texture(string40.get(37)), new Texture(string40.get(38)), new Texture(string40.get(39)),
                new Texture(string40.get(40)), new Texture(string40.get(41)), new Texture(string40.get(42)), new Texture(string40.get(43))));


        for (Texture x : texture_skin40) {
            x.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }


        car4_1 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(0)),
                new TextureRegionDrawable(texture_skin40.get(1)),
                new TextureRegionDrawable(texture_skin40.get(2)),
                new TextureRegionDrawable(texture_skin40.get(3))));

        car4_2 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(4)),
                new TextureRegionDrawable(texture_skin40.get(5)),
                new TextureRegionDrawable(texture_skin40.get(6)),
                new TextureRegionDrawable(texture_skin40.get(7))));

        car4_3 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(8)),
                new TextureRegionDrawable(texture_skin40.get(9)),
                new TextureRegionDrawable(texture_skin40.get(10)),
                new TextureRegionDrawable(texture_skin40.get(11))));

        car4_4 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(12)),
                new TextureRegionDrawable(texture_skin40.get(13)),
                new TextureRegionDrawable(texture_skin40.get(14)),
                new TextureRegionDrawable(texture_skin40.get(15))));

        car4_5 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(16)),
                new TextureRegionDrawable(texture_skin40.get(17)),
                new TextureRegionDrawable(texture_skin40.get(18)),
                new TextureRegionDrawable(texture_skin40.get(19))));

        car4_6 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(20)),
                new TextureRegionDrawable(texture_skin40.get(21)),
                new TextureRegionDrawable(texture_skin40.get(22)),
                new TextureRegionDrawable(texture_skin40.get(23))));

        car4_7 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(24)),
                new TextureRegionDrawable(texture_skin40.get(25)),
                new TextureRegionDrawable(texture_skin40.get(26)),
                new TextureRegionDrawable(texture_skin40.get(27))));

        car4_8 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(28)),
                new TextureRegionDrawable(texture_skin40.get(29)),
                new TextureRegionDrawable(texture_skin40.get(30)),
                new TextureRegionDrawable(texture_skin40.get(31))));

        car4_9 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(32)),
                new TextureRegionDrawable(texture_skin40.get(33)),
                new TextureRegionDrawable(texture_skin40.get(34)),
                new TextureRegionDrawable(texture_skin40.get(35))));

        car4_10 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(36)),
                new TextureRegionDrawable(texture_skin40.get(37)),
                new TextureRegionDrawable(texture_skin40.get(38)),
                new TextureRegionDrawable(texture_skin40.get(39))));


        car4_11 = new ArrayList<TextureRegionDrawable>(Arrays.asList(new TextureRegionDrawable(texture_skin40.get(40)),
                new TextureRegionDrawable(texture_skin40.get(41)),
                new TextureRegionDrawable(texture_skin40.get(42)),
                new TextureRegionDrawable(texture_skin40.get(43))));

        cars_10 = new ArrayList<skin>(Arrays.asList(new skin(car4_1), new skin(car4_2), new skin(car4_3), new skin(car4_4), new skin(car4_5),
                new skin(car4_6), new skin(car4_7), new skin(car4_8), new skin(car4_9), new skin(car4_10), new skin(car4_11)));


        four = new ArrayList<Image>(Arrays.asList(new Image(new Sprite(new Texture("skins/skin1_1.png"))),
                new Image(new Sprite(new Texture("skins/skin1_2.png"))),
                new Image(new Sprite(new Texture("skins/skin1_3.png"))),
                new Image(new Sprite(new Texture("skins/skin1_4.png")))));


        four.get(0).addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MenuScreen.is_bought[current_car]) {
                    buffer_img = string40.get(current_car * 4);
                    four_is_checked = true;
                    triangleX = four.get(0).getX();
                    triangleY = 147;
                    TunnelRacer.soundManager.run_click_sound();
                }

            }

        });

        four.get(1).addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MenuScreen.is_bought[current_car]) {
                    buffer_img = string40.get(current_car * 4 + 1);
                    four_is_checked = true;
                    triangleX = four.get(1).getX();
                    triangleY = 147;
                    TunnelRacer.soundManager.run_click_sound();
                }

            }
        });

        four.get(2).addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MenuScreen.is_bought[current_car]) {
                    buffer_img = string40.get(current_car * 4 + 2);
                    four_is_checked = true;
                    triangleX = four.get(2).getX();
                    triangleY = 147;
                    TunnelRacer.soundManager.run_click_sound();
                }

            }
        });

        four.get(3).addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MenuScreen.is_bought[current_car]) {
                    buffer_img = string40.get(current_car * 4 + 3);
                    four_is_checked = true;
                    triangleX = four.get(3).getX();
                    triangleY = 147;
                    TunnelRacer.soundManager.run_click_sound();
                }

            }
        });

        garage_background_texture = new Texture("Screens/garage_background.png");
        garage_background_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        garage_background = new Image(new Sprite(garage_background_texture));
        info = new Image(new Sprite());
        info.setDrawable(texture_region_mass.get(0));

        stage = new Stage(viewport);

        table = new Table();
        container = new Table();
        info_table = new Table();
        table_onTop = new Table();
        locked = new Table();

        btn1_texture = new Texture(Gdx.files.internal("albumGarage/album_car_1.png"));
        btn1_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn2_texture = new Texture(Gdx.files.internal("albumGarage/album_car_2.png"));
        btn2_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn3_texture = new Texture(Gdx.files.internal("albumGarage/album_car_3.png"));
        btn3_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn4_texture = new Texture(Gdx.files.internal("albumGarage/album_car_4.png"));
        btn4_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn5_texture = new Texture(Gdx.files.internal("albumGarage/album_car_5.png"));
        btn5_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn6_texture = new Texture(Gdx.files.internal("albumGarage/album_car_6.png"));
        btn6_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn7_texture = new Texture(Gdx.files.internal("albumGarage/album_car_7.png"));
        btn7_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn8_texture = new Texture(Gdx.files.internal("albumGarage/album_car_8.png"));
        btn8_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn9_texture = new Texture(Gdx.files.internal("albumGarage/album_car_9.png"));
        btn9_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn10_texture = new Texture(Gdx.files.internal("albumGarage/album_car_10.png"));
        btn10_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btn11_texture = new Texture(Gdx.files.internal("albumGarage/album_car_11.png"));
        btn11_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        not_locked_11 = new TextureRegionDrawable(btn11_texture);

        btn1 = new Image(new Sprite(btn1_texture));
        btn2 = new Image(new Sprite(btn2_texture));
        btn3 = new Image(new Sprite(btn3_texture));
        btn4 = new Image(new Sprite(btn4_texture));
        btn5 = new Image(new Sprite(btn5_texture));
        btn6 = new Image(new Sprite(btn6_texture));
        btn7 = new Image(new Sprite(btn7_texture));
        btn8 = new Image(new Sprite(btn8_texture));
        btn9 = new Image(new Sprite(btn9_texture));
        btn10 = new Image(new Sprite(btn10_texture));
        btn11 = new Image(new Sprite(btn11_texture));


        scrollPane = new PagedScrollPane();

        scrollPane.setFlingTime(0.4f);
        scrollPane.setPageSpacing(20);

        scrollPane.addPage(btn1);
        scrollPane.addPage(btn2);
        scrollPane.addPage(btn3);
        scrollPane.addPage(btn4);
        scrollPane.addPage(btn5);
        scrollPane.addPage(btn6);
        scrollPane.addPage(btn7);
        scrollPane.addPage(btn8);
        scrollPane.addPage(btn9);
        scrollPane.addPage(btn10);
        scrollPane.addPage(btn11);

        scrollPane.setWidth(414);
        scrollPane.setHeight(267);

        container.setWidth(414);
        container.setHeight(267);

        container.add(scrollPane);

        container.setX(540 / 2f - 414 / 2f);
        container.setY(550);

//          stage.setDebugAll(true);

        table.setFillParent(true);
        table_onTop.setFillParent(true);

        table.add(go_home).expand().right().top().pad(15).colspan(2);
        table.row();

        table.add(buy_btn).expand().bottom().padBottom(60);
        table.add(select_btn).expand().bottom().padBottom(60);
        table.row();

        for (Image x : four) {
            table_onTop.add(x).expand(0, MainGame.HEIGHT).bottom().padBottom(25).padLeft(10);
        }

        table.add(info).bottom().colspan(2);

        stage.addActor(garage_background);
        stage.addActor(table);
        stage.addActor(table_onTop);

        go_home.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                MenuScreen.preffs.save_racer();
                MenuScreen.preffs.save_coins();
                TunnelRacer.soundManager.run_click_sound();
                game.setMenuScreen(game);

            }
        });

        plus_btn.setPosition(390, 835);
        stage.addActor(plus_btn);

        stage.addActor(container);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

        Gdx.input.setInputProcessor(stage);

        particle_buy.setPosition(MainGame.WIDTH / 2f + 5, MainGame.HEIGHT * 2 / 3f + 47);
        particle_selected.setPosition(0, 0);
        for (ParticleEmitter a : particle_selected.getEmitters()) {
            for (Sprite b : a.getSprites()) {
                b.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
        }
    }

    public void update() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setMenuScreen(game);
        }

        camera.update();

        if (MenuScreen.coins >= cost_mass.get(current_car) && !MenuScreen.is_bought[current_car] && current_car != 10) {
            MenuScreen.is_locked[current_car] = false;
            draw_locked = false;
        } else if (current_car != 0) {
            MenuScreen.is_locked[current_car] = true;
            draw_locked = true;
        }

        if (MenuScreen.is_bought[9] && MenuScreen.is_bought[1] && MenuScreen.is_bought[8] && MenuScreen.is_bought[7] &&
                MenuScreen.is_bought[6] && MenuScreen.is_bought[5] && MenuScreen.is_bought[4] && MenuScreen.is_bought[3] && MenuScreen.is_bought[2]) {
            MenuScreen.is_bought[10] = true;
        }

        if (!MenuScreen.is_bought[10]) {
            if (btn11.getDrawable() != locked11) {
                btn11.setDrawable(locked11);
            }
        } else
            btn11.setDrawable(not_locked_11);


    }

    @Override
    public void show() {
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();

        stage.act();
        stage.draw();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        animation.render_locked(batch);

        if (MenuScreen.is_bought[current_car])
            animation.render_triangle(batch, triangleX, triangleY);

        font.draw(batch, String.valueOf(MenuScreen.coins), 335 - 40 - 25 * (String.valueOf(MenuScreen.coins).length() - 1), container.getY() + container.getHeight() + 48);

        if (isRenderParicle_buy) {
            particle_buy.draw(batch, Gdx.graphics.getDeltaTime());
            if (particle_buy.isComplete()) {
                particle_buy.reset();
                isRenderParicle_buy = false;
            }
        }

        if (isRenderParicle_selected) {
            particle_selected.draw(batch, Gdx.graphics.getDeltaTime());
            if (particle_selected.isComplete()) {
                particle_selected.reset();
                isRenderParicle_selected = false;
            }
        }

        if(cost_mass.get(current_car) == -1){
            font.draw(batch, String.valueOf("???"), 410, 390);
        } else
            font.draw(batch, String.valueOf(cost_mass.get(current_car)), 410, 390);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
//        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
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

//        for (Texture x : texture_skin40)
//            x.dispose();
//
//        home_btn_pressed_texture.dispose();
//        home_btn_texture.dispose();
//        select_btn_pressed_texture.dispose();
//        select_btn_texture.dispose();
//        buy_btn_texture.dispose();
//        btn1_texture.dispose();
//        btn2_texture.dispose();
//        btn3_texture.dispose();
//        btn4_texture.dispose();
//        btn5_texture.dispose();
//        btn6_texture.dispose();
//        btn7_texture.dispose();
//        btn8_texture.dispose();
//        btn9_texture.dispose();
//        btn10_texture.dispose();
//        btn11_texture.dispose();
//        garage_background_texture.dispose();
//
//        stage.dispose();
    }
}