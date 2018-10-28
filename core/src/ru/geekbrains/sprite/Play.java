package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class Play extends Sprite {

    public Play(TextureAtlas atlas){
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.2f);
    }

    @Override
    public void resize(Rect worldBounds){
        pos.set(worldBounds.getLeft()/2.5f,0.05f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(this.isMe(touch)){
            this.setScale(0.8f);
        }
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, Game game) {
        if(this.getScale()<1f){
            this.setScale(1f);
        }
        if(this.isMe(touch)){
            game.setScreen(new GameScreen());
        }
        return false;
    }

}
