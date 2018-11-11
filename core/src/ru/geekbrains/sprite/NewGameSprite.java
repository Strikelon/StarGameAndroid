package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ActionListener;
import ru.geekbrains.base.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;

public class NewGameSprite extends ScaledTouchUpButton {

    private boolean Flicker = true;
    private float flickerInterval = 0.4f;
    private float flickerTimer = 0;

    public NewGameSprite(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), actionListener);
        setHeightProportion(0.03f);
    }

    public boolean isFlicker() {
        return Flicker;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        flickerTimer += delta;
        if (flickerTimer  >= flickerInterval) {
            if(isFlicker()){
                Flicker = false;
            }else {
                Flicker = true;
            }
            flickerTimer = 0f;
        }

    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom()/2);
        pos.x = 0;
    }
}
