package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class GameOverSprite extends Sprite {

    private final float MAX_HEIGHT = 0.05f;

    public GameOverSprite(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.01f);
        pos.set(worldBounds.pos);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(getHeight()<MAX_HEIGHT){
            setHeightProportion(getHeight()+delta/4);
        }
    }
}
