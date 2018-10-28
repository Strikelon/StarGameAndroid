package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Exit extends Sprite {

    public Exit(TextureAtlas atlas){
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.16f);
    }

    @Override
    public void resize(Rect worldBounds){
        pos.set(worldBounds.getRight()/2.5f,0.025f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(this.isMe(touch)){
            this.setScale(0.8f);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(this.getScale()<1f){
            this.setScale(1f);
        }
        if(this.isMe(touch)){
            System.exit(0);
        }
        return false;
    }


}
