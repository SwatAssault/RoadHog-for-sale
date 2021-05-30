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
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;

import Scenes.HUD;

import static utils.Constants.PPM;

public class Busters {
    public class Buster {
        public Body buster;
        public Sprite buster_sprite;
        public String type;
        Texture texture;
        public int width;
        public boolean isAlive;
        public int height;
        public boolean isUsed;

        public Buster(int x, int y, String texture) {
            this.texture = new Texture("busters/" + texture + ".png");
            this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            isUsed = false;
            type = texture;
            buster_sprite = new Sprite(this.texture);
            width = this.texture.getWidth();
            height = this.texture.getHeight();

            //Параметры тела
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            def.position.set(x / PPM, y / PPM);
            def.fixedRotation = true;

            isAlive = true;

            //Тело оправляется в Мир
            buster = MainGame.world.createBody(def);

            //Геометрическая форма тела
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

            buster.createFixture(shape, 100);

            //Для обработки коллизий
            buster.setUserData(this);

            shape.dispose();
        }

        public void setIsRenderParicle(boolean value) {
            isRenderParicle = value;
            x_booster = buster.getPosition().x * PPM;
            y_booster = buster.getPosition().y * PPM;
            particle_booster.setPosition(x_booster, y_booster);
        }

        public boolean getIsRenderParticle() {
            return isRenderParicle;
        }

        public void update() {
            buster.setLinearVelocity(0, -1.9f * MainGame.bg.getSpeed());
        }
    }

    public static ArrayList<Busters.Buster> busters;
    final static ArrayList<String> nameOfBusters = new ArrayList<String>(Arrays.asList("magnit", "health", "shield", "x2Coins"));
    public static boolean isActiveBuster;
    public static long time;
    public static int forCoins;
    public static boolean forHP;
    public int timeIsLeft;
    private static boolean isRenderParicle;
    private float x_booster;
    private float y_booster;
    private ParticleEffect particle_booster;
    private float forPause;

    public void obnulit(){
        forPause = 0;
        isRenderParicle = false;
        time = 0;
        timeIsLeft = 0;
        forCoins = 1;
        isActiveBuster = false;
        forHP = false;
        while(busters.size() != 0){
            delBuster(0);
        }
        particle_booster.reset();
    }

    public Busters() {
        isRenderParicle = false;
        particle_booster = new ParticleEffect();
        particle_booster.load(Gdx.files.internal("busters/particles_boosters"), Gdx.files.internal("busters"));

        particle_booster.start();
        time = 0;
        timeIsLeft = 0;
        forCoins = 1;
        isActiveBuster = false;
        forHP = false;
        busters = new ArrayList<Busters.Buster>();
        busters.add(new Busters.Buster(200, -100, nameOfBusters.get(MathUtils.random(0, nameOfBusters.size() - 1))));
    }


    public static void delBuster(int i) {
        if (i != -1) {
            MainGame.world.destroyBody(busters.get(i).buster);
            busters.get(i).buster.setUserData(null);
            busters.get(i).buster = null;
            busters.remove(busters.get(i));
        }
    }

    public static void searchIndex(Body buster) {
        for (int i = 0; i < busters.size(); i++) {
            if (busters.get(i).buster.equals(buster))
                busters.get(i).isAlive = (false);
        }
    }

    public void update() {
        if (!isActiveBuster || (Driver.pogonya)) {
            forHP = false;
            forCoins = 1;
            isActiveBuster = false;
        }
        for (int i = 0; i < busters.size(); i++) {

            if (isActiveBuster) {
                if ("magnit".equals(busters.get(i).type)) {
                    timeIsLeft = 20;
                    magnit();
                }
                if ("health".equals(busters.get(i).type)) {
                    health();
                }
                if ("shield".equals(busters.get(i).type)) {
                    timeIsLeft = 10;
                    shield();
                }
                if ("x2Coins".equals(busters.get(i).type)) {
                    timeIsLeft = 15;
                    x2Coins();
                }
            } else {
                busters.get(i).update();
            }
            if (busters.get(i).buster.getPosition().y * PPM + busters.get(i).height / 2 < 0 && !isActiveBuster || Driver.pogonya || !busters.get(i).isAlive && !isActiveBuster) {
                delBuster(i);
            }
        }
        if (MathUtils.random(0, 180) == 137 && busters.size() < 1)
            busters.add(new Busters.Buster(MainGame.driver.randomRoad(), MainGame.HEIGHT + 100, nameOfBusters.get(MathUtils.random(0, nameOfBusters.size() - 1))));
    }

    public void magnit() {
        if ((TimeUtils.timeSinceMillis(time) / 1000) < timeIsLeft) {
            for (Coins.Coin coin : Coins.coins) {
                if (coin.coin.getPosition().x * PPM != Racer.racer.getPosition().x * PPM &&
                        coin.coin.getPosition().y * PPM != Racer.racer.getPosition().y * PPM)
                    if (Math.abs(coin.coin.getPosition().x * PPM) < Math.abs(Racer.racer.getPosition().x * PPM + 250) &&
                            Math.abs(coin.coin.getPosition().y * PPM) < Math.abs(Racer.racer.getPosition().y * PPM + 250))
                        coin.coin.setLinearVelocity(2 * (Racer.racer.getPosition().x - coin.coin.getPosition().x), 2 * (Racer.racer.getPosition().y - coin.coin.getPosition().y));
            }
        } else {
            isActiveBuster = false;
        }
    }

    public void health() {
        MainGame.HP = 3;
        isActiveBuster = false;
    }

    public void shield() {
        if ((TimeUtils.timeSinceMillis(time) / 1000) < timeIsLeft) {
            forHP = true;
        } else {
            forHP = false;
            isActiveBuster = false;
        }
    }

    public void x2Coins() {
        if ((TimeUtils.timeSinceMillis(time) / 1000) < timeIsLeft) {
            forCoins = 2;
        } else {
            forCoins = 1;
            isActiveBuster = false;
        }
    }

    public void render(SpriteBatch batch) {
        for (Buster a : busters) {
            if (!isActiveBuster) {
                a.buster_sprite.setPosition(a.buster.getPosition().x * PPM - a.width / 2f, a.buster.getPosition().y * PPM - a.height / 2f);
                a.buster_sprite.draw(batch);
            }
        }
    }

    public void renderHUD(SpriteBatch batch) {
        for (Buster a : busters) {
            if (isActiveBuster && !Driver.pogonya && !MainGame.is_paused) {
                a.buster_sprite.setPosition(HUD.HP_image.getX() + HUD.HP_image.getWidth() + 20, HUD.HP_image.getY() - 10);
                MainGame.font_score.draw(batch, String.valueOf(timeIsLeft - TimeUtils.timeSinceMillis(time) / 1000), HUD.HP_image.getX() + HUD.HP_image.getWidth() + 70, HUD.HP_image.getY() + 30);
                a.buster_sprite.draw(batch);
                forPause = timeIsLeft - TimeUtils.timeSinceMillis(time) / 1000;
            }
            if(MainGame.is_paused)
            {
                a.buster_sprite.setPosition(HUD.HP_image.getX() + HUD.HP_image.getWidth() + 20, HUD.HP_image.getY() - 10);
                a.buster_sprite.draw(batch);
                MainGame.font_score.draw(batch, String.valueOf((int)forPause), HUD.HP_image.getX() + HUD.HP_image.getWidth() + 70, HUD.HP_image.getY() + 30);
            }
        }
    }

    public void renderParticles(SpriteBatch batch) {
        if (isRenderParicle) {
            batch.begin();
            particle_booster.draw(batch, Gdx.graphics.getDeltaTime());
            batch.end();
            if (particle_booster.isComplete()) {
                particle_booster.reset();
                isRenderParicle = false;
            }
        }
    }

    public void dispose() {
        for (Buster b : busters) {
            b.texture.dispose();
        }
    }
}
