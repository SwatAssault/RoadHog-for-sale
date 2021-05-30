package Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class PagedScrollPane extends ScrollPane {

    private boolean wasPanDragFling = false;

    private SpriteBatch batch;

    private boolean flag;
    private boolean flag1;

    private TextureRegionDrawable four_11_locked;

    private Table content;

    public PagedScrollPane() {
        super(null);
        content = new Table();
        content.defaults().space(50);
        setWidget(content);

        batch = new SpriteBatch();

        four_11_locked = new TextureRegionDrawable(new Texture("skins/skin11_locked.png"));
        four_11_locked.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        flag = true;
        flag1 = true;
    }

    public PagedScrollPane(Skin skin) {
        super(null, skin);
        content = new Table();
        content.defaults().space(50);
        setWidget(content);
    }

    public PagedScrollPane(Skin skin, String styleName) {
        super(null, skin, styleName);
        content = new Table();
        content.defaults().space(50);
        setWidget(content);
    }

    public PagedScrollPane(Actor widget, ScrollPaneStyle style) {
        super(null, style);
        content = new Table();
        content.defaults().space(50);
        setWidget(content);
    }

    public void addPages(Actor... pages) {
        for (Actor page : pages) {
            content.add(page).expandY().fillY();
        }
    }

    public void addPage(Actor page) {
        content.add(page).expandY().fillY();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false;
            scrollToPage();

        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true;
            }
        }

        GarageScreen.current_car = (int) (getScrollX() / 414);

        if (GarageScreen.current_car == 0 && flag) {
            GarageScreen.triangleX = 140;
            GarageScreen.triangleY = 147;
            flag = false;
        } else if (flag) {
            GarageScreen.triangleX = GarageScreen.four.get(0).getX();
            GarageScreen.triangleY = 147;
            flag = false;
        }


        if (MenuScreen.is_bought[GarageScreen.current_car]) {
            if (GarageScreen.buy_btn.getDrawable() != GarageScreen.bought_texture)
                GarageScreen.buy_btn.setDrawable(GarageScreen.bought_texture);
        } else if (GarageScreen.buy_btn.getDrawable() != GarageScreen.buy_texture)
            GarageScreen.buy_btn.setDrawable(GarageScreen.buy_texture);

        GarageScreen.buffer_turbo = MenuScreen.turbo_mass[GarageScreen.current_car];
        GarageScreen.buffer_braking = MenuScreen.braking_mass[GarageScreen.current_car];
        GarageScreen.buffer_flex = MenuScreen.flex_mass[GarageScreen.current_car];
        GarageScreen.buffer_flex_angle = MenuScreen.flex_angle_mass[GarageScreen.current_car];

        if (GarageScreen.current_car == 0/* && flag1*/) {
            int i = 0;
            //flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_1.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 1 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_2.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 2 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_3.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 3 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_4.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 4 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_5.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 5 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_6.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 6 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_7.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 7 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_8.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 8 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_9.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 9 && flag1) {
            int i = 0;
            flag1 = false;
            for (Image x : GarageScreen.four) {
                x.setDrawable(GarageScreen.car4_10.get(i));
                i++;
            }
        }

        if (GarageScreen.current_car == 10/* && flag1*/) {
            int i = 0;
            //    flag1 = false;
            for (Image x : GarageScreen.four) {
//                if (!MenuScreen.is_bought[9] && !MenuScreen.is_bought[1] && !MenuScreen.is_bought[8] && !MenuScreen.is_bought[7] &&
//                        !MenuScreen.is_bought[6] && !MenuScreen.is_bought[5] && !MenuScreen.is_bought[4] && !MenuScreen.is_bought[3] && !MenuScreen.is_bought[2]) { // если не все машины куплены
//                if (!(!MenuScreen.is_bought[9] || !MenuScreen.is_bought[1] || !MenuScreen.is_bought[8] || !MenuScreen.is_bought[7] ||
//                        !MenuScreen.is_bought[6] || !MenuScreen.is_bought[5] || !MenuScreen.is_bought[4] || !MenuScreen.is_bought[3] || !MenuScreen.is_bought[2])) { // если не все машины куплены
                if(!MenuScreen.is_bought[10]){
                    x.setDrawable(four_11_locked);
                } else
                    x.setDrawable(GarageScreen.car4_11.get(i));
                i++;
            }

        }

        if (GarageScreen.info.getDrawable() != GarageScreen.texture_region_mass.get(GarageScreen.current_car)) {
            GarageScreen.info.setDrawable(GarageScreen.texture_region_mass.get(GarageScreen.current_car));
        }
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        if (content != null) {
            for (Cell cell : content.getCells()) {
                cell.width(width);
            }
            content.invalidate();
        }
    }

    public void setPageSpacing(float pageSpacing) {
        if (content != null) {
            content.defaults().space(pageSpacing);
            for (Cell cell : content.getCells()) {
                cell.space(pageSpacing);
            }
            content.invalidate();
        }
    }

    private void scrollToPage() {
        final float width = getWidth();
        final float scrollX = getScrollX();
        final float maxX = getMaxX();

        if (scrollX >= maxX || scrollX <= 0) return;

        Array<Actor> pages = content.getChildren();
        float pageX = 0;
        float pageWidth = 0;
        if (pages.size > 0) {
            for (Actor a : pages) {
                pageX = a.getX();

                pageWidth = a.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    break;
                }
            }

            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));


            flag = true;
            flag1 = true;
        }

        if ((int) getScrollX() / 414 != GarageScreen.current_car)
            flag1 = true;

    }

}