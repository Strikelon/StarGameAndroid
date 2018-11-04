package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.geekbrains.base.Sprite;
import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip0;

public class Enemy0Pool extends SpritesPool<EnemyShip0> {


    @Override
    protected EnemyShip0 newObject() {
        return new EnemyShip0();
    }

    public boolean isEmpty(){
        return activeObjects.size()==0;
    }

}
