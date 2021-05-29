package com.awprecords.roadhog;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static utils.Constants.PPM;

public class Background {

    public void obnulit() {
        backs[0].setPos(0, 0);
        backs[1].setPos(0, 1920);
        backs[2].setPos(0, 3840);
        backs[3].setPos(0, 5760);
    }


    class BGPicture {
        Texture tx;
        Vector2 pos;

        public BGPicture(Vector2 pos, String texture) {
            tx = new Texture("road/" + texture);
            tx.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            this.pos = pos;
        }

        public void setPos(float x, float y) {
            pos.set(x, y);
        }
    }

    private int speed;
    private BGPicture[] backs;
    private Body leftWall;
    private Body rightWall;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Background() {
        createWalls();
        speed = 9;
        backs = new BGPicture[4];
        backs[0] = new BGPicture(new Vector2(0, 0), "newroad1.png");
        backs[1] = new BGPicture(new Vector2(0, 1920), "newroad2.png");
        backs[2] = new BGPicture(new Vector2(0, 3840), "newroad3.png");
        backs[3] = new BGPicture(new Vector2(0, 5760), "newroad4.png");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < backs.length; i++) {
            batch.draw(backs[i].tx, backs[i].pos.x, backs[i].pos.y);
        }
    }

    public void update() {
        for (int i = 0; i < backs.length; i++) {
            backs[i].pos.y -= speed;
            if (backs[i].pos.y + backs[i].tx.getHeight() <= 0) {
                backs[i].pos.y = hightPosOfBG() - speed;
            }
        }
    }

    public float hightPosOfBG() {
        float res = Float.MIN_VALUE;
        for (int i = 0; i < backs.length; i++) {
            if (backs[i].pos.y + backs[i].tx.getHeight() > res)
                res = backs[i].pos.y + backs[i].tx.getHeight();
        }
        return res;
    }

    public void createWalls() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(0 / PPM, 0 / PPM);
        def.fixedRotation = true;

        leftWall = MainGame.world.createBody(def);
        def.position.set(MainGame.WIDTH / PPM, 0);
        rightWall = MainGame.world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((40) / 2f / PPM, MainGame.HEIGHT / PPM);

        leftWall.createFixture(shape, Float.MAX_VALUE);
        rightWall.createFixture(shape, Float.MAX_VALUE);

        leftWall.setUserData(this);
        rightWall.setUserData(this);
        shape.dispose();
    }
}
