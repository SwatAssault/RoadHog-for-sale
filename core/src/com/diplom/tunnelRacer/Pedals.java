package com.diplom.tunnelRacer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import Screens.MenuScreen;

import static utils.Constants.PPM;


public class Pedals {

    public class Accelerator {

        private Texture accelerator_img;

        private Texture accelerator_pressed_img;

        public Accelerator() {

            accelerator_img = new Texture("pedals/accelerator.png");
            accelerator_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            accelerator_pressed_img = new Texture("pedals/accelerator_pressed.png");
            accelerator_pressed_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            accelerator_button = new ImageButton(
                    new TextureRegionDrawable(accelerator_img),
                    new TextureRegionDrawable(accelerator_pressed_img)
            );

            accelerator_button.addListener(new ClickListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    isNitro_on = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    isNitro_on = false;
                }
            });
        }
    }

    public class Brakes {

        private Texture brakes_img;

        private Texture brakes_pressed_img;

        public Brakes() {

            brakes_img = new Texture("pedals/brakes.png");
            brakes_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            brakes_pressed_img = new Texture("pedals/brakes_pressed.png");
            brakes_pressed_img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            brakes_button = new ImageButton(
                    new TextureRegionDrawable(brakes_img),
                    new TextureRegionDrawable(brakes_pressed_img)
            );

            brakes_button.addListener(new ClickListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    isBrake = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    isBrake = false;
                }
            });
        }
    }

    public static Accelerator accelerator;
    public static Brakes brakes;
    public static boolean isNitro_on;
    public static boolean isBrake;
    public static ImageButton accelerator_button;
    public static ImageButton brakes_button;

    public Pedals() {
        isBrake = false;
        isNitro_on = false;

        accelerator = new Accelerator();
        brakes = new Brakes();
    }

    public void render(SpriteBatch batch) {

    }

    public void update() {

        if (isNitro_on) {
            if (Racer.racer.getPosition().y * PPM + Racer.height / 2 < MainGame.HEIGHT - 100) {
                Racer.racer.setLinearVelocity(0, MenuScreen.selected_turbo);
            } else {
                Racer.racer.setLinearVelocity(0, 0);
            }
            Racer.angle = 0;
            Racer.horizontalForce = 0;
        }

        if (isBrake) {
            if (Racer.racer.getPosition().y * PPM - Racer.height / 2 > 70)
                Racer.racer.setLinearVelocity(0, -MenuScreen.selected_braking);
            else Racer.racer.setLinearVelocity(0, 0);
            Racer.angle = 0;
            Racer.horizontalForce = 0;
        }

    }

}


