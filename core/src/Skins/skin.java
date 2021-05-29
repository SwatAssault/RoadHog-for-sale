package Skins;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import java.util.ArrayList;

public class skin {

    ArrayList<TextureRegionDrawable> skin_mass;

   public  skin(ArrayList<TextureRegionDrawable> x){

       skin_mass = new ArrayList<TextureRegionDrawable>();

        for(TextureRegionDrawable a : x){

            skin_mass.add(a);

        }
    }
}
