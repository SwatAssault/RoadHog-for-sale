package com.diplom.tunnelRacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import Screens.GarageScreen;
import Screens.MenuScreen;
import items.Coins;

import static utils.Constants.PPM;

public class AnimationManager {

    private TextureAtlas triangle_atlas;
    private Animation triangle_animation;

    private TextureAtlas locked_atlas;
    private Animation locked_animation;

    private TextureAtlas coin_atlas;
    private Animation coin_animation;

    private TextureAtlas beacon_blue_atlas;
    private Animation beacon_blue_animation;

    private TextureAtlas pursuit_atlas;
    private Animation pursuit_animation;

    private TextureAtlas nitro_atlas;
    private Animation nitro_animation;

    private TextureAtlas beacon_red_atlas;
    private Animation beacon_red_animation;

    private TextureAtlas escape_atlas;
    private Animation escape_animation;

    private float timePassed = 0;


    public static Vector2 hit_position = new Vector2();


    public AnimationManager() {

        triangle_atlas = new TextureAtlas("Animations/triangle.atlas");
        triangle_animation = new Animation(1 / 10f, triangle_atlas.getRegions());
        for (Texture t : triangle_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        coin_atlas = new TextureAtlas("Animations/coins.atlas");
        coin_animation = new Animation(1 / 8f, coin_atlas.getRegions());
        for (Texture t : coin_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        locked_atlas = new TextureAtlas("Animations/locked.atlas");
        locked_animation = new Animation(1 / 10f, locked_atlas.getRegions());
        for (Texture t : locked_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        escape_atlas = new TextureAtlas("Animations/escape.atlas");
        escape_animation = new Animation(1 / 10f, escape_atlas.getRegions());
        for (Texture t : escape_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        pursuit_atlas = new TextureAtlas("Animations/pursuit.atlas");
        pursuit_animation = new Animation(1f, pursuit_atlas.getRegions());
        for (Texture t : pursuit_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        beacon_blue_atlas = new TextureAtlas("Animations/blue.atlas");
        beacon_blue_animation = new Animation(1 / 30f, beacon_blue_atlas.getRegions());
        for (Texture t : beacon_blue_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        beacon_red_atlas = new TextureAtlas("Animations/red.atlas");
        beacon_red_animation = new Animation(1 / 20f, beacon_red_atlas.getRegions());

        for (Texture t : beacon_red_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        nitro_atlas = new TextureAtlas("Animations/nitro.atlas");
        nitro_animation = new Animation(1 / 10f, nitro_atlas.getRegions());
        for (Texture t : nitro_atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

    }

    public void render_triangle(SpriteBatch batch, float x, float y) {

        timePassed += Gdx.graphics.getDeltaTime();

        batch.draw((TextureRegion) triangle_animation.getKeyFrame(timePassed, false), x, y - 10);
    }

    public void render_coin(SpriteBatch batch) {

        timePassed += Gdx.graphics.getDeltaTime();

        for (Coins.Coin a : Coins.coins) {
            batch.draw((TextureRegion) coin_animation.getKeyFrame(timePassed, true), a.coin.getPosition().x * PPM - a.width / 2f, a.coin.getPosition().y * PPM - a.height / 2f);
        }
    }

    public void render_locked(SpriteBatch batch) {

        timePassed += Gdx.graphics.getDeltaTime();

        if (GarageScreen.draw_locked && !MenuScreen.is_bought[GarageScreen.current_car] && GarageScreen.current_car != 10) {
            batch.draw((TextureRegion) locked_animation.getKeyFrame(timePassed, true), GarageScreen.container.getX(), GarageScreen.container.getY());
        }
    }

    public void render_escape(SpriteBatch batch) {

        timePassed += Gdx.graphics.getDeltaTime();

        if (MainGame.time_2sec < 3) {
            batch.draw((TextureRegion) escape_animation.getKeyFrame(timePassed / 3, true), MainGame.WIDTH / 2f - 190, MainGame.HEIGHT / 4f);
        } else {
            MainGame.time = TimeUtils.millis();
        }
    }

    public void render_pursuit(SpriteBatch batch) {

        timePassed += Gdx.graphics.getDeltaTime();

        batch.draw((TextureRegion) pursuit_animation.getKeyFrame(timePassed, true), Racer.racer.getPosition().x * PPM - 125, Racer.racer.getPosition().y * PPM + 100);

    }

    public void render_red_beacon(SpriteBatch batch) {

        timePassed += Gdx.graphics.getDeltaTime();

        batch.draw((TextureRegion) beacon_red_animation.getKeyFrame(timePassed, true), Police.ment_driver.getPosition().x * PPM - 150, Police.ment_driver.getPosition().y * PPM - 15);

    }

    public void render_blue_beacon(SpriteBatch batch) {

        timePassed += Gdx.graphics.getDeltaTime();

        batch.draw((TextureRegion) beacon_blue_animation.getKeyFrame(timePassed / 2, true), Police.ment_driver.getPosition().x * PPM - 115, Police.ment_driver.getPosition().y * PPM - 15);

    }


    public void render_nitro(SpriteBatch batch) {

        if(!MainGame.GameOver) {
            timePassed += Gdx.graphics.getDeltaTime();
            batch.draw((TextureRegion) nitro_animation.getKeyFrame(timePassed, true), Racer.racer.getPosition().x * PPM - 60 + Racer.width / 2f, Racer.racer.getPosition().y * PPM - 196 - Racer.height / 2f);
        }

    }

    public void dispose() {
        nitro_atlas.dispose();
        beacon_blue_atlas.dispose();
        beacon_red_atlas.dispose();
    }

}
