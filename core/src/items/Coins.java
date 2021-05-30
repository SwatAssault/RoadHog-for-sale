package items;

import com.diplom.tunnelRacer.Driver;
import com.diplom.tunnelRacer.MainGame;
import com.diplom.tunnelRacer.Racer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

import static utils.Constants.PPM;

public class Coins {
    public void obnulit() {
        isRenderParicle = false;
        isRenderParicle_minus = false;
        isRenderParicle_minus_3 = false;
        while (coins.size() != 0){
            delCoin(0);
        }
        particle_minus_coin.reset();
        particle_minus_coin_3.reset();
        particle_coin.reset();
    }

    public class Coin {
        public Body coin;
        public Sprite pit_sprite;
        Texture texture;
        public int width;
        boolean isAlive;
        public int height;

        public Coin(int x, int y, String texture) {
            this.texture = new Texture("Coins/" + texture);
            this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            pit_sprite = new Sprite(this.texture);
            width = this.texture.getWidth();
            height = this.texture.getHeight();

            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            def.position.set(x / PPM, y / PPM);
            def.fixedRotation = true;

            isAlive = true;

            coin = MainGame.world.createBody(def);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

            coin.createFixture(shape, 100);

            coin.setUserData(this);
            shape.dispose();
        }

        public void setIsRenderParicle(boolean value) {
            isRenderParicle = value;
            x_coin = coin.getPosition().x * PPM;
            y_coin = coin.getPosition().y * PPM;
            particle_coin.setPosition(x_coin, y_coin);

        }

        public boolean getIsRenderParticle() {
            return isRenderParicle;
        }

        public void update() {
            coin.setLinearVelocity(0, -1.9f * MainGame.bg.getSpeed());
        }
    }

    public static ArrayList<Coin> coins;
    public ParticleEffect particle_coin;
    private static boolean isRenderParicle;
    private float x_coin;
    private float y_coin;

    public static ParticleEffect particle_minus_coin;
    public static ParticleEffect particle_minus_coin_3;

    private static boolean isRenderParicle_minus;
    private static boolean isRenderParicle_minus_3;

    public Coins() {
        isRenderParicle = false;
        particle_coin = new ParticleEffect();
        particle_coin.load(Gdx.files.internal("Coins/particles_coins"), Gdx.files.internal("Coins"));
        particle_coin.start();

        isRenderParicle_minus = false;
        particle_minus_coin = new ParticleEffect();
        particle_minus_coin.load(Gdx.files.internal("Coins/particles_minus_coins"), Gdx.files.internal("Coins"));
        particle_minus_coin.start();

        isRenderParicle_minus_3 = false;
        particle_minus_coin_3 = new ParticleEffect();
        particle_minus_coin_3.load(Gdx.files.internal("Coins/particles_minus_coins_3"), Gdx.files.internal("Coins"));
        particle_minus_coin_3.start();

        coins = new ArrayList<Coin>();
        coins.add(new Coin(200, -100, "coin.png"));
    }


    public static void delCoin(int i) {
        if (i != -1) {
            MainGame.world.destroyBody(coins.get(i).coin);
            coins.get(i).coin.setUserData(null);
            coins.get(i).coin = null;
            coins.remove(coins.get(i));
        }
    }

    public static void searchIndex(Body coin) {
        for (int i = 0; i < coins.size(); i++) {
            if (coins.get(i).coin.equals(coin))
                coins.get(i).isAlive = (false);
        }
    }

    public void update() {
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).update();
            if (coins.get(i).coin.getPosition().y * PPM + coins.get(i).height / 2 < 0 || !coins.get(i).isAlive || Driver.pogonya) {
                delCoin(i);
            }
        }
        if (MathUtils.random(0, 200) == 137 && coins.size() < 1)
            coins.add(new Coin(MainGame.driver.randomRoad(), MainGame.HEIGHT + 100, "coin.png"));
    }

    public void renderParticles(SpriteBatch batch) {
        if (isRenderParicle) {
            batch.begin();
            particle_coin.draw(batch, Gdx.graphics.getDeltaTime());
            batch.end();
            if (particle_coin.isComplete()) {
                particle_coin.reset();
                isRenderParicle = false;
            }
        }

        if (isRenderParicle_minus) {
            batch.begin();
//            particle_minus_coin.setPosition(Racer.racer.getPosition().x * PPM, Racer.racer.getPosition().y * PPM);
            particle_minus_coin.draw(batch, Gdx.graphics.getDeltaTime());
            batch.end();
            if (particle_minus_coin.isComplete()) {
                particle_minus_coin.reset();
                isRenderParicle_minus = false;
            }
        }

        if (isRenderParicle_minus_3) {
            batch.begin();
//            particle_minus_coin_3.setPosition(Racer.racer.getPosition().x * PPM, Racer.racer.getPosition().y * PPM);
            particle_minus_coin_3.draw(batch, Gdx.graphics.getDeltaTime());
            batch.end();
            if (particle_minus_coin_3.isComplete()) {
                particle_minus_coin_3.reset();
                isRenderParicle_minus_3 = false;
            }
        }
    }


    public static void setIsRenderParicle_minus(boolean value, boolean hit) {
        if (hit) {
            isRenderParicle_minus = value;
            particle_minus_coin.setPosition(Racer.racer.getPosition().x * PPM + 50, Racer.racer.getPosition().y * PPM);
        } else {
            isRenderParicle_minus_3 = value;
            particle_minus_coin_3.setPosition(Racer.racer.getPosition().x * PPM - 50, Racer.racer.getPosition().y * PPM);
        }
    }

    public void dispose() {
    }
}
