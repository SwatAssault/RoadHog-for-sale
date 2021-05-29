package com.awprecords.roadhog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.TimeUtils;

import Screens.MenuScreen;

import static utils.Constants.PPM;

public class Police {

    public static Body ment_driver;
    public Sprite ment_sprite;
    public float speed;
    int width;
    int height;

    private Texture texture;

    public static boolean play_sirens = false;

    public static int tap_count;

    float delta;
    float a;
    public static boolean isCreate, flag;
    public static long time;
    public static long time_start;
    private int bonus;

    public Police() {
        this.texture = new Texture("car/Poli_80x169.png");
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Driver.pogonya = false;
        isCreate = false;
        bonus = 0;
    }

    public void create() {
        Driver.pogonya = true;
        if (Driver.drivers_mass.size() == 0 && ment_driver == null) {
            this.speed = -5;
            a = 0.05f;
            flag = false;

            ment_sprite = new Sprite(this.texture);
            width = texture.getWidth();
            height = texture.getHeight();

            isCreate = true;
            tap_count = 0;

            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            def.position.set(MainGame.WIDTH / 2f / PPM, -height / PPM);
            def.fixedRotation = true;

            ment_driver = MainGame.world.createBody(def);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

            ment_driver.createFixture(shape, Integer.MAX_VALUE);
            ment_driver.setUserData(this);

            shape.dispose();
        }
    }

    public void kill() {
        Driver.pogonya = false;
        MainGame.HP = 3;
        flag = false;

        if (ment_driver != null && isCreate) {
            if(!MainGame.GameOver) {
                bonus = 5 * (MainGame.lvl + 1) - TimeUtils.timeSinceMillis(time_start) / 2500 > 0 ?
                        (int) (5 * (MainGame.lvl + 1) - TimeUtils.timeSinceMillis(time_start) / 2500) : 0;
                MenuScreen.coins += bonus;
                MenuScreen.earned_coins += bonus;
            }
            MainGame.world.destroyBody(ment_driver);
            ment_driver.setUserData(null);
            ment_driver = null;

            MainGame.upLvl();
        }
        tap_count = 0;


//        play_sirens = false;
//        RoadHog.soundManager.police_sound.stop();
    }

    public void render(SpriteBatch batch) {
        if (isCreate) {
            ment_sprite.setPosition(ment_driver.getPosition().x * PPM - width / 2f, ment_driver.getPosition().y * PPM - height / 2f);
            ment_sprite.draw(batch);
        }
    }

    public void anim() {
        time_start = TimeUtils.millis();
        if (Racer.racer.getPosition().x * PPM > MainGame.WIDTH / 2 - 15 &&
                Racer.racer.getPosition().x * PPM < MainGame.WIDTH / 2 + 15 &&
                Racer.racer.getPosition().y * PPM > MainGame.HEIGHT * 0.58 - 15 &&
                Racer.racer.getPosition().y * PPM < MainGame.HEIGHT * 0.58 + 15) {

            if (ment_driver.getPosition().y * PPM < height + 20) {
                ment_driver.setLinearVelocity(0, -speed);
                flag = false;
            } else {
                speed = -1f;
                ment_driver.setLinearVelocity(0, -speed);
                flag = true;
                time = TimeUtils.millis();
            }
        }
    }

    public void update() {
        if (ment_driver != null) {
            if (!flag) anim();
            else {
                ment_driver.setLinearVelocity(0, -speed);
                tap();
                speed -= a * ((TimeUtils.timeSinceMillis(time)) / 150f);
            }
            if (ment_driver.getPosition().y * PPM + height / 2 < 0 && Driver.pogonya && isCreate && flag) {
                kill();
            }

            if (Racer.racer.getPosition().y * PPM - Racer.height / 2 > MainGame.HEIGHT && Driver.pogonya && isCreate && flag) {
                MainGame.GameOver = true;
                kill();
            }

            if (!Driver.pogonya && isCreate && ment_driver == null)
                kill();
        }

    }

    public void tap() {
        if (isCreate)
            if (Gdx.input.justTouched()) {
                tap_count++;
                speed += delta / 2;
                time = TimeUtils.millis();
            } else {
                if (speed > 2 + delta * 10)
                    speed /= 2;
            }
    }

    public void dispose() {
        texture.dispose();
        if (ment_driver != null && isCreate) {
            MainGame.world.destroyBody(ment_driver);
            ment_driver.setUserData(null);
            ment_driver = null;
        }

        Driver.pogonya = false;
        isCreate = false;
        flag = false;
    }
}
