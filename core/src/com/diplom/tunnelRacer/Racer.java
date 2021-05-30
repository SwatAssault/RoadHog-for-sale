package com.diplom.tunnelRacer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.TimeUtils;

import Scenes.HUD;
import Screens.MenuScreen;
import items.Coins;

import static utils.Constants.PPM;

public class Racer {
    public static Body racer;
    public static float angle;
    public static int width;
    public static int height;
    public static float stageStepScore;

    public static boolean isNewStage;

    public static float horizontalForce = 0;
    public static int verticalForce = 0;
    public static float score;

    public long timeStart;

    private Texture racer_img;
    private Sprite sprite;
    private String nameOfTexture;

    public void obnulit() {
        timeStart = TimeUtils.millis();
        isNewStage = false;
        nameOfTexture = MenuScreen.selected_img;
        racer_img = new Texture(nameOfTexture);
        racer_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(racer_img);
        angle = 0;
        width = racer_img.getWidth();
        height = racer_img.getHeight();

        horizontalForce = 0;
        verticalForce = 0;

        HUD.goRight_flag = false;
        HUD.goLeft_flag = false;
        Pedals.isNitro_on = false;
        Pedals.isBrake = false;

        Racer.racer.setLinearVelocity(0, 0);
        racer.setTransform(200 / PPM, 150 / PPM, 1);
    }

    public Racer(int x, int y) {
        timeStart = TimeUtils.millis();
        isNewStage = false;
        nameOfTexture = MenuScreen.selected_img;
        racer_img = new Texture(nameOfTexture);
        racer_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(racer_img);
        angle = 0;
        width = racer_img.getWidth();
        height = racer_img.getHeight();
        racer = createRacer(x, y);

        racer.setUserData(this);
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(racer.getPosition().x * PPM - width / 2f, racer.getPosition().y * PPM - height / 2f);
        sprite.draw(batch);
    }

    public void update() {
        inputUpdate();
        if (racer.getPosition().y * PPM + height / 2 < 0)
            MainGame.GameOver = true;
        if (!Driver.pogonya) {
            if (score < 0)
                score = 0;
            if (TimeUtils.timeSinceMillis(timeStart) / 10 > 50) {
                score += MathUtils.random(stageStepScore, stageStepScore + 0.7f);
                timeStart = TimeUtils.millis();
            }
        }
    }


    public void inputUpdate() {

        verticalForce = 0;


        if (!Police.isCreate) {
            if (HUD.goRight_flag) {
                if (racer.getPosition().x * PPM > 540 - 80) {
                    horizontalForce = 0;
                    if ((int) (angle * 100000) != 0) angle = angle / 1.2f;
                } else {
                    if (horizontalForce < MenuScreen.selected_flex) {
                        horizontalForce += 0.3f;
                    }
                    if (angle > -MenuScreen.selected_flex_angle)
                        angle -= 0.03f;
                }
            }

            if (HUD.goLeft_flag) {

                if (racer.getPosition().x * PPM < 80) {
                    horizontalForce = 0;
                    if ((int) (angle * 100000) != 0) angle = angle / 1.2f;
                } else {
                    if (horizontalForce > -MenuScreen.selected_flex) {
                        horizontalForce -= 0.3f;
                    }
                    if (angle < MenuScreen.selected_flex_angle) {
                        angle += 0.03f;
                    }
                }
            }

//            if (!HUD.goLeft.isPressed() && !HUD.goRight.isPressed()) {
            if (!HUD.goLeft_flag && !HUD.goRight_flag) {
                if ((int) (angle * 10000) != 0) angle = angle / 1.2f;
                horizontalForce = 0;
            }

            if(!Pedals.isBrake && !Pedals.isNitro_on)
                racer.setLinearVelocity(horizontalForce * 4, verticalForce * 4);

            racer.setTransform(racer.getPosition().x, racer.getPosition().y, angle);
            sprite.setRotation((float) (angle / Math.PI * 180));
        } else {
            if (Driver.pogonya) {
                horizontalForce = 0;
                if (!(Racer.racer.getPosition().x * PPM > MainGame.WIDTH / 2 - 5 &&
                        Racer.racer.getPosition().x * PPM < MainGame.WIDTH / 2 - 1)) {
                    if (racer.getPosition().x * PPM > MainGame.WIDTH / 2 - 1) {
                        horizontalForce -= 6;
                        if (angle < 0.2f)
                            angle += 0.03f;
                    } else {
                        if (racer.getPosition().x * PPM < MainGame.WIDTH / 2 + 1) {
                            horizontalForce += 6;
                            if (angle > -0.2f)
                                angle -= 0.03f;

                        }
                    }
                }
                if (racer.getPosition().y * PPM > MainGame.HEIGHT * 0.58 + 10) {
                    verticalForce -= 9;
                } else {
                    if (racer.getPosition().y * PPM < MainGame.HEIGHT * 0.58 - 10) {
                        verticalForce += 9;
                    }
                }
                if (Racer.racer.getPosition().x * PPM > MainGame.WIDTH / 2 - 5 &&
                        Racer.racer.getPosition().x * PPM < MainGame.WIDTH / 2 - 1) {
                    if ((int) (angle * 10000) != 0) angle = angle / 1.2f;
                }
                racer.setLinearVelocity(horizontalForce, verticalForce);
                racer.setTransform(racer.getPosition().x, racer.getPosition().y, angle);
                sprite.setRotation((float) (angle / Math.PI * 180));

            } else {
                if (racer.getPosition().y * PPM > MainGame.HEIGHT / 6) {
                    racer.setLinearVelocity(0, -6);
                    isNewStage = true;
                } else {
                    Police.isCreate = false;
                    MainGame.police.kill();
                    isNewStage = false;
                    MainGame.driver.start();
                }
            }
        }
    }

    public Body createRacer(int x, int y) {

        int width = racer_img.getWidth();
        int height = racer_img.getHeight();

        Body body_to_return;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;

        body_to_return = MainGame.world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width - 5) / 2f / PPM, height / 2f / PPM);
        body_to_return.createFixture(shape, 100f);

        shape.dispose();
        return body_to_return;
    }

    public void setRenderParticle_minus(boolean value, boolean hit) {
        Coins.setIsRenderParicle_minus(value, hit);
    }


    public void setStageStepScore(float stageStepScore) {
        Racer.stageStepScore = stageStepScore;
    }

    public void dispose() {
        racer_img.dispose();
    }
}