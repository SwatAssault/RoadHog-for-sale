package Screens;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AboutScreen implements Screen {

    public static Stage stage;

    private OrthographicCamera camera;
    private Viewport viewport;

    Table table;
    Table table_top;
    private Texture bg;

    SpriteBatch batch;

    private Image about_bg;

    private ImageButton go_home_btn;

    public static RoadHog game;

    private Label linkFreeSound;
    private Label linkOpenGameArt;
    private Label linkGoogleFont;
    private Label linkFontSpace;
    private Label linkPP;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    private Texture home_btn_pressed_texture;
    private Texture home_btn_texture;

    public AboutScreen(final RoadHog game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);

        stage = new Stage(viewport);

        batch = new SpriteBatch();

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PermanentMarker-Regular.ttf"));

        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 24;
        fontParameter.color = Color.WHITE;

        font = fontGenerator.generateFont(fontParameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        linkFreeSound = new Label("FreeSound.org", new Label.LabelStyle(font, Color.WHITE));
        linkOpenGameArt = new Label("OpenGameArt.org", new Label.LabelStyle(font, Color.WHITE));
        linkGoogleFont = new Label("fonts.google.com", new Label.LabelStyle(font, Color.WHITE));
        linkFontSpace = new Label("fontspace.com", new Label.LabelStyle(font, Color.WHITE));
        linkPP = new Label("Privacy Policy", new Label.LabelStyle(font, Color.WHITE));

        linkOpenGameArt.setPosition(MainGame.WIDTH / 2f - linkOpenGameArt.getWidth() / 2, MainGame.HEIGHT / 2f - 80);
        linkFreeSound.setPosition(MainGame.WIDTH / 2f - linkFreeSound.getWidth() / 2, MainGame.HEIGHT / 2f + 5);
        linkGoogleFont.setPosition(MainGame.WIDTH / 2f - linkGoogleFont.getWidth() / 2, MainGame.HEIGHT / 2f - 210);
        linkFontSpace.setPosition(MainGame.WIDTH / 2f - linkFontSpace.getWidth() / 2, MainGame.HEIGHT / 2f - 290);
        linkPP.setPosition(MainGame.WIDTH / 2f - linkPP.getWidth() / 2, 30);

        linkFreeSound.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://freesound.org");
            }
        });

        linkOpenGameArt.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://opengameart.org");
            }
        });

        linkGoogleFont.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://fonts.google.com/");
            }
        });

        linkFontSpace.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://www.fontspace.com/");
            }
        });

        linkPP.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://docviewer.yandex.ru/view/280786753/?page=1&*=QXUTph7Ca6ONFRGwlYfgwNcn0LB7InVybCI6InlhLWRpc2stcHVibGljOi8vWmE5ek1XSzVJdW5mTVRSb0wxbExHOE9uUWMzWEdzZ0ltTzlDTGlSM1ZyOHNZL2YvaDFDMGxzSlltQVdrWUw2WHEvSjZicG1SeU9Kb25UM1ZvWG5EYWc9PSIsInRpdGxlIjoiUHJpdmFjeSBwb2xpY3kuZG9jeCIsIm5vaWZyYW1lIjpmYWxzZSwidWlkIjoiMjgwNzg2NzUzIiwidHMiOjE1NjExOTU5OTEwODUsInl1IjoiMjI3MTM5NTgzMTU0OTI4MzU1MCJ9");
            }
        });



        bg = new Texture("Screens/about_bg.png");
        bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        about_bg = new Image(bg);

        table = new Table();
        table_top = new Table();

        home_btn_pressed_texture = new Texture("buttons/back_btn_pressed.png");
        home_btn_pressed_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        home_btn_texture = new Texture("buttons/back_btn.png");
        home_btn_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        go_home_btn = new ImageButton(
                new TextureRegionDrawable(home_btn_texture),
                new TextureRegionDrawable(home_btn_pressed_texture)
        );

        go_home_btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSettingsScreen(game);
                RoadHog.soundManager.run_click_sound();
            }
        });


//        stage.setDebugAll(true);
        table_top.setFillParent(true);
        table_top.setZIndex(1);


        table.setZIndex(0);
        table.setFillParent(true);
        table.add(go_home_btn).expand().right().top().padRight(15).padTop(15);

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
        stage.addActor(about_bg);
        stage.addActor(table);
        stage.addActor(table_top);
        stage.addActor(linkOpenGameArt);
        stage.addActor(linkFreeSound);
        stage.addActor(linkGoogleFont);
        stage.addActor(linkFontSpace);
        stage.addActor(linkPP);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        batch.begin();
        linkFreeSound.draw(batch, 1);
        linkOpenGameArt.draw(batch, 1);
        batch.end();
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setSettingsScreen(game);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
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
//        home_btn_pressed_texture.dispose();
//        home_btn_texture.dispose();
//        stage.dispose();
    }
}
