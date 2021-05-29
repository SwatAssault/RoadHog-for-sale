package Screens;

import com.awprecords.roadhog.MainGame;
import com.awprecords.roadhog.RoadHog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Awprecords implements Screen {

    SpriteBatch batch;

    public static RoadHog game;

    OrthographicCamera camera;

    public ParticleEffect particle_intro;

    public static boolean load_menu;

    public Awprecords(final RoadHog game) {

        particle_intro = new ParticleEffect();
        particle_intro.load(Gdx.files.internal("intro/particles_intro"), Gdx.files.internal("intro"));
        particle_intro.setPosition(MainGame.WIDTH / 2f, MainGame.HEIGHT / 2f);

        for (ParticleEmitter e : particle_intro.getEmitters()) {
            for (Sprite s : e.getSprites()) {
                 s.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
        }
        load_menu = false;
        particle_intro.start();


        this.game = game;

        batch = new SpriteBatch();

        camera = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);
        camera.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (!load_menu) {
            camera.update();

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            particle_intro.draw(batch, Gdx.graphics.getDeltaTime());
            if (particle_intro.isComplete()) {
                particle_intro.reset();
                load_menu = true;
            }

            batch.end();

            batch.setProjectionMatrix(camera.combined);
        }

        if (load_menu) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //dispose();
            game.setMenuScreen(game);
            RoadHog.soundManager.run_menu_music();
            if(!MenuScreen.on_off_switches[0]){
                RoadHog.soundManager.menu_music.setVolume(0);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        //  stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
//
//        //stage.dispose();
//        particle_intro.dispose();
//        batch.dispose();

    }
}
