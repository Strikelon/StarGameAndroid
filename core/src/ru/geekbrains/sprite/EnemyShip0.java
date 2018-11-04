package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.utils.Regions;

public class EnemyShip0 extends Sprite {

    private TextureAtlas atlas;
    private Rect worldBounds;
    private Vector2 v = new Vector2();

    public EnemyShip0(){
    }

    public void set(TextureAtlas textureAtlas, Rect worldBounds) {
        this.atlas = textureAtlas;
        this.regions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        setHeightProportion(0.15f);
        this.v.set(0f, Rnd.nextFloat(-0.2f, -0.1f));
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = worldBounds.getTop();
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }


}
