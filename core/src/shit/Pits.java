package shit;

import com.awprecords.roadhog.Driver;
import com.awprecords.roadhog.MainGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;
import java.util.Arrays;

import static utils.Constants.PPM;

public class Pits {
    public class Pit {
        Body pit;
        public Sprite pit_sprite;
        Texture texture;
        int width;
        int height;

        public Pit(int x, int y, String texture) {

            this.texture = new Texture("pit/" + texture);
            this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            pit_sprite = new Sprite(this.texture);
            width = this.texture.getWidth();
            height = this.texture.getHeight();

            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            def.position.set(x / PPM, y / PPM);
            def.fixedRotation = true;

            pit = MainGame.world.createBody(def);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

            pit.createFixture(shape, 100);
            pit.setUserData(this);

            shape.dispose();
        }

        public void dispose() {
            texture.dispose();
        }

        public void update() {
            pit.setLinearVelocity(0, -1.9f * MainGame.bg.getSpeed());
        }
    }

    ArrayList<Pit> pits;
    final static ArrayList<String> nameOfPit = new ArrayList<String>(Arrays.asList("pit1_53x60.png", "pit2_49x53.png", "pit3_54x53.png", "pit4_51x57.png"));

    public void obnulit() {
        while (pits.size() != 0){
            delPit(0);
        }
    }

    public void delPit(int i) {
        if (i != -1) {
            MainGame.world.destroyBody(pits.get(i).pit);
            pits.get(i).pit.setUserData(null);
            pits.get(i).pit = null;
            pits.remove(pits.get(i));
        }
    }
    public Pits() {
        pits = new ArrayList<Pit>();
        pits.add(new Pit(200, -100, nameOfPit.get(MathUtils.random(0, nameOfPit.size() - 1))));
    }

    public void render(SpriteBatch batch) {
        for (Pit a : pits) {
            a.pit_sprite.setPosition(a.pit.getPosition().x * PPM - a.width / 2f, a.pit.getPosition().y * PPM - a.height / 2f);
            a.pit_sprite.draw(batch);
        }
    }

    public void update() {
        for (int i = 0; i < pits.size(); i++) {
            pits.get(i).update();
            if (pits.get(i).pit.getPosition().y * PPM + pits.get(i).height / 2 < 0 || Driver.pogonya) {
                MainGame.world.destroyBody(pits.get(i).pit);
                pits.get(i).pit.setUserData(null);
                pits.get(i).pit = null;
                pits.get(i).pit_sprite = null;
                pits.get(i).dispose();
                pits.remove(pits.get(i));
            }
        }
        if (MathUtils.random(0, 200) == 137 && pits.size() < 1)
            pits.add(new Pit(MainGame.driver.randomRoad(), MainGame.HEIGHT + 100, nameOfPit.get(MathUtils.random(0, nameOfPit.size() - 1))));
    }

    public void dispose() {
    }
}
