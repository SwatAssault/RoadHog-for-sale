package com.diplom.tunnelRacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.TimeUtils;

import Screens.MenuScreen;
import items.Busters;
import items.Coins;
import items.Pits;

public class Collisions implements ContactListener {

    AnimationManager animation = new AnimationManager();

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void beginContact(Contact contact) {

        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        Body b1 = f1.getBody();
        Body b2 = f2.getBody();

        Object o1 = b1.getUserData();
        Object o2 = b2.getUserData();


        if (o1.getClass() == Racer.class && o2.getClass() == Car.class || o2.getClass() == Racer.class && o1.getClass() == Car.class) {

            if (!MainGame.GameOver) {
                animation.hit_position = contact.getWorldManifold().getPoints()[0];
                MainGame.is_hit = true;
                MainGame.time = TimeUtils.millis();
                if (!Busters.forHP)
                    MainGame.HP--;
                if (!Driver.pogonya && !Busters.forHP) {
                    if (o1.getClass() == Racer.class) {
                        ((Racer) o1).setRenderParticle_minus(true, true);
                    } else {
                        ((Racer) o2).setRenderParticle_minus(true, true);
                    }
                    Racer.score -= 5;
                }
                if (MenuScreen.on_off_switches[2]) {
                    Gdx.input.vibrate(250);
                }
            }
        }

        if (o1.getClass() == Racer.class && o2.getClass() == Pits.Pit.class || o2.getClass() == Racer.class && o1.getClass() == Pits.Pit.class) {
            if (!MainGame.GameOver) {
                if (!Driver.pogonya && !Busters.forHP) {
                    if (o1.getClass() == Racer.class) {
                        ((Racer) o1).setRenderParticle_minus(true, false);
                    } else {
                        ((Racer) o2).setRenderParticle_minus(true, false);
                    }
                    Racer.score -= 3;
                }
            }
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        Body b1 = f1.getBody();
        Body b2 = f2.getBody();

        Object o1 = b1.getUserData();
        Object o2 = b2.getUserData();


        if (o1.getClass() == Racer.class && o2.getClass() == Pits.Pit.class || o2.getClass() == Racer.class && o1.getClass() == Pits.Pit.class) {
            contact.setEnabled(false);
        }


        if (o1.getClass() == Car.class && o2.getClass() == Pits.Pit.class || o2.getClass() == Car.class && o1.getClass() == Pits.Pit.class) {
            contact.setEnabled(false);
        }

        if (o1.getClass() == Coins.Coin.class && o2.getClass() == Pits.Pit.class || o2.getClass() == Coins.Coin.class && o1.getClass() == Pits.Pit.class ||
                o1.getClass() == Coins.Coin.class && o2.getClass() == Car.class || o2.getClass() == Coins.Coin.class && o1.getClass() == Car.class ||
                o1.getClass() == Busters.Buster.class && o2.getClass() == Car.class || o2.getClass() == Busters.Buster.class && o1.getClass() == Car.class ||
                o1.getClass() == Busters.Buster.class && o2.getClass() == Pits.Pit.class || o2.getClass() == Busters.Buster.class && o1.getClass() == Pits.Pit.class) {
            contact.setEnabled(false);
        }

        if (o1.getClass() == Coins.Coin.class && o2.getClass() == Racer.class || o1.getClass() == Racer.class && o2.getClass() == Coins.Coin.class) {
            if (!MainGame.GameOver) {
                contact.setEnabled(false);
                MenuScreen.coins += Busters.forCoins;
                MenuScreen.earned_coins += Busters.forCoins;
                MenuScreen.preffs.save_coins();
                if (o1.getClass() == Coins.Coin.class) {
                    if (!((Coins.Coin) o1).getIsRenderParticle())
                        ((Coins.Coin) o1).setIsRenderParicle(true);
                    Coins.searchIndex(b1);
                } else {
                    if (!((Coins.Coin) o2).getIsRenderParticle())
                        ((Coins.Coin) o2).setIsRenderParicle(true);
                    Coins.searchIndex(b2);
                }
                TunnelRacer.soundManager.run_coin_sound();
            }
        }

        if (o1.getClass() == Busters.Buster.class && o2.getClass() == Racer.class || o1.getClass() == Racer.class && o2.getClass() == Busters.Buster.class) {
            if (!MainGame.GameOver) {
                if (o1.getClass() == Busters.Buster.class) {
                    if (((Busters.Buster) o1).isAlive) {
                        TunnelRacer.soundManager.run_booster_pickup_sound();
                        ((Busters.Buster) o1).setIsRenderParicle(true);
                    }

                    Busters.searchIndex(b1);
                } else {
                    if (((Busters.Buster) o2).isAlive) {
                        TunnelRacer.soundManager.run_booster_pickup_sound();
                        ((Busters.Buster) o2).setIsRenderParicle(true);
                    }

                    Busters.searchIndex(b2);
                }

                contact.setEnabled(false);
                Busters.isActiveBuster = true;
                Busters.time = TimeUtils.millis();
            }
        }

        if (o1.getClass() == Police.class && o2.getClass() == Pits.Pit.class || o2.getClass() == Police.class && o1.getClass() == Pits.Pit.class) {
            contact.setEnabled(false);
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
