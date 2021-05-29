package com.awprecords.roadhog;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static utils.Constants.PPM;

public class Driver {

    public static ArrayList<Car> drivers_mass;
    static ArrayList<String> nameTexture;
    public static boolean pogonya;
    public int countCar;

    public void obnulit() {
        pogonya = false;
        while (drivers_mass.size() != 0){
            delCar(0);
        }
        start();
    }

    public Driver() {
        pogonya = false;
        drivers_mass = new ArrayList<Car>();

        nameTexture = new ArrayList<String>(Arrays.asList("1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png", "11.png",
                "12.png", "13.png", "14.png", "15.png", "16.png", "17.png", "18.png", "19.png", "20.png", "21.png", "22.png", "23.png", "24.png", "25.png", "26.png",
                "27.png", "28.png", "29.png", "30.png", "31.png", "32.png", "33.png"));

        countCar = 4;
        start();
    }

    public void render(SpriteBatch batch) {
        for (Car a : drivers_mass) {
            a.driver_sprite.setPosition(a.car_driver.getPosition().x * PPM - a.width / 2f, a.car_driver.getPosition().y * PPM - a.height / 2f);
            a.driver_sprite.draw(batch);
        }
    }

    public void start() {
        for (int j = 0; j < countCar; j++) {
            drivers_mass.add(new Car(j < 4 ? j : randomRoad(), -150, nameTexture.get(MathUtils.random(0, nameTexture.size() - 1))));
        }
    }

    public int randomRoad() {
        int x_to_create;
        int i;

        i = MathUtils.random(1, 4);

        if (i == 1) {
            x_to_create = MathUtils.random(80, 130);
        } else if (i == 2) {
            x_to_create = MathUtils.random(200, 210);
        } else if (i == 3) {
            x_to_create = MathUtils.random(310, 340);
        } else {
            x_to_create = MathUtils.random(410, 450);
        }

        return x_to_create;
    }

    public void delCar(int i){
        MainGame.world.destroyBody(drivers_mass.get(i).car_driver);
        drivers_mass.get(i).car_driver.setUserData(null);
        drivers_mass.get(i).car_driver = null;
        drivers_mass.get(i).driver_sprite = null;
        drivers_mass.get(i).dispose();
        drivers_mass.remove(drivers_mass.get(i));
    }

    public void update() {
        for (int i = 0; i < drivers_mass.size(); i++) {
            drivers_mass.get(i).update(drivers_mass);
            if (drivers_mass.get(i).car_driver.getPosition().y * PPM + drivers_mass.get(i).height / 2 < 0 && line_isClear() || (drivers_mass.size() == 0)) {
//                MainGame.world.destroyBody(drivers_mass.get(i).car_driver);
//                drivers_mass.get(i).car_driver.setUserData(null);
//                drivers_mass.get(i).car_driver = null;
//                drivers_mass.get(i).driver_sprite = null;
//                drivers_mass.get(i).dispose();
//                drivers_mass.remove(drivers_mass.get(i));
                delCar(i);
                System.runFinalization();
                if (!pogonya) {
                    drivers_mass.add(new Car(randomRoad(), MainGame.HEIGHT + 100, nameTexture.get(MathUtils.random(0, nameTexture.size() - 1))));
                } else if (drivers_mass.size() != 0) upSpeed();
            }
        }
    }

    public void upSpeed() {
        for (Car car : drivers_mass) {
            car.speed += 3;
        }
    }

    public boolean line_isClear() {
        for (Car car : drivers_mass) {
            if (car.car_driver.getPosition().y * PPM > MainGame.HEIGHT - 100) return false;
        }
        return true;
    }

    public void dispose() {
    }
}