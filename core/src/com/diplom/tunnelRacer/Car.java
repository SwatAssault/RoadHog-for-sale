package com.diplom.tunnelRacer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

import static utils.Constants.PPM;

public class Car {
    public Body car_driver;
    public Sprite driver_sprite;
    public float speed;
    int numRoad;
    int width;
    int height;
    float angle;
    int dist;
    int betweenLines;

    Texture texture;

    boolean flag;
    boolean bonus;
    int kuda;

    public Car(int x, int y, String texture) {
        speed = MathUtils.random(10) + 3;
        if (speed == MainGame.bg.getSpeed())
            speed += 2;

        this.texture = new Texture("car/" + texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        driver_sprite = new Sprite(this.texture);
        numRoad = -1;
        width = this.texture.getWidth();
        height = this.texture.getHeight();

        bonus = true;
        kuda = 0;
        angle = 0;

        dist = MathUtils.random(55, 59);
        flag = false;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;

        car_driver = MainGame.world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width - 5) / 2f / PPM, height / 2f / PPM);

        car_driver.createFixture(shape, 500);

        car_driver.setUserData(this);
        changeNumRoad();
        shape.dispose();
    }

    public boolean changeNumRoad() {
        if (car_driver.getPosition().x * PPM >= 80 && car_driver.getPosition().x * PPM <= 130) {
            if (numRoad != 1) {
                numRoad = 1;
                return true;
            }
        }
        if (car_driver.getPosition().x * PPM >= 200 && car_driver.getPosition().x * PPM <= 240) {
            if (numRoad != 2) {
                numRoad = 2;
                return true;
            }
        }
        if (car_driver.getPosition().x * PPM >= 310 && car_driver.getPosition().x * PPM <= 340) {
            if (numRoad != 3) {
                numRoad = 3;
                return true;
            }
        }
        if (car_driver.getPosition().x * PPM >= 410 && car_driver.getPosition().x * PPM <= 450) {
            if (numRoad != 4) {
                numRoad = 4;
                return true;
            }
        }
        return false;
    }

    public void update(ArrayList<Car> list) {
        distance(list);
        car_driver.setLinearVelocity(0, -speed);
        logic(list);
        changeNumRoad();
    }

    public void distance(ArrayList<Car> list) {
        for (Car otherCar : list) {
            if (otherCar.car_driver.getPosition().y * PPM - otherCar.height / 2f - dist < car_driver.getPosition().y * PPM + height / 2 &&
                    otherCar.car_driver.getPosition().y * PPM > car_driver.getPosition().y * PPM &&
                    numRoad == otherCar.numRoad) {
                if (speed < otherCar.speed) {
                    if (otherCar.car_driver.getPosition().y * PPM - otherCar.height / 2f - dist / 5f < car_driver.getPosition().y * PPM + height / 2)
                        speed = otherCar.speed;
                    speed += (float) Math.pow(Math.atan(speed), -1) / 3;
                } else
                    speed = otherCar.speed;
            }
        }
    }

    public void logic(ArrayList<Car> list) {
        for (Car otherCar : list) {

            if (Math.abs(otherCar.car_driver.getPosition().x * PPM - Racer.racer.getPosition().x * PPM) < (Racer.width / 2 + otherCar.width / 2 + 25) &&
                    Racer.racer.getPosition().y * PPM + Racer.height / 2 > otherCar.car_driver.getPosition().y * PPM - otherCar.height / 2 &&
                    Racer.racer.getPosition().y * PPM - Racer.height / 2 < otherCar.car_driver.getPosition().y * PPM - otherCar.height / 2) {
                if (otherCar.bonus) {
                    if (!Driver.pogonya) {
                        if(MainGame.HP == 3)
                        Racer.score += 5;
                        otherCar.bonus = false;
                    }
                }
            } else otherCar.bonus = true;

            if (!flag)
                move(0);
            if (otherCar.car_driver.getPosition().y * PPM - otherCar.height < car_driver.getPosition().y * PPM + height / 2 &&
                    otherCar.car_driver.getPosition().y * PPM > car_driver.getPosition().y * PPM &&
                    otherCar.numRoad == numRoad) {
                if (!flag) {
                    flag = true;
                    kuda = kudaEdem(list);
                    if (numRoad + kuda < 1 || numRoad + kuda > 4) kuda = 0;
                    distance(list);
                    if (kuda != 0)
                        switch (numRoad + kuda) {
                            case 1:
                                betweenLines = MathUtils.random(80, 130);
                                break;
                            case 2:
                                betweenLines = MathUtils.random(200, 240);
                                break;
                            case 3:
                                betweenLines = MathUtils.random(310, 340);
                                break;
                            case 4:
                                betweenLines = MathUtils.random(410, 450);
                                break;
                            default:
                                kuda = 0;
                        }
                }
            }
            if (!ch_numRoad() && kuda != 0) {
                move(kuda);
            } else {
                if (kuda != 0)
                    speed /= 2.5;
                flag = false;
                kuda = 0;
            }
        }
    }

    public boolean ch_numRoad() {
        if (kuda == 1)
            if (car_driver.getPosition().x * PPM < betweenLines)
                return false;
        if (kuda == -1)
            if (car_driver.getPosition().x * PPM > betweenLines)
                return false;
        return true;
    }

    public int kudaEdem(ArrayList<Car> list) {
        float currentY = car_driver.getPosition().y * PPM;
        float topLine = currentY + height * 1.5f;
        float bottomLine = currentY - height * 1.5f;

        int left = 0, right = 0;
        for (Car otherCar : list) {
            if (otherCar.numRoad == numRoad - 1) {
                if (!(otherCar.car_driver.getPosition().y * PPM + otherCar.height / 2 < bottomLine ||
                        otherCar.car_driver.getPosition().y * PPM - otherCar.height / 2 > topLine)) {
                    left++;
                }
            }

            if (otherCar.numRoad == numRoad + 1) {
                if (!(otherCar.car_driver.getPosition().y * PPM + otherCar.height / 2 < bottomLine ||
                        otherCar.car_driver.getPosition().y * PPM - otherCar.height / 2 > topLine)) {
                    right++;
                }
            }
        }

        if (right == 0 && numRoad != 4) return 1;
        if (left == 0 && numRoad != 1) return -1;
        return 0;
    }

    public void move(int rotate) {
        if (rotate != 0) {
            if (rotate == 1)
                if (angle > -0.3f)
                    angle -= 0.002f;
            if (rotate == -1)
                if (angle < 0.3f)
                    angle += 0.002f;
        } else {
            if ((int) (angle * 10000) != 0) angle /= 1.02f;
        }
        car_driver.setLinearVelocity(5 * rotate, -speed);
        car_driver.setTransform(car_driver.getPosition().x, car_driver.getPosition().y, angle);
        driver_sprite.setRotation((float) (angle / Math.PI * 180));
    }

    public void dispose() {
        texture.dispose();
    }
}
