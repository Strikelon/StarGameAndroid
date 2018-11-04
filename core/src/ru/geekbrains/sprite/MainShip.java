package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class MainShip extends Sprite {

    private Vector2 v0 = new Vector2(0.5f, 0);
    private Vector2 v = new Vector2();

    private boolean pressedLeft;
    private boolean pressedRight;

    private boolean touchedLeft;
    private boolean touchedRight;
    private int pointerLeft;
    private int pointerRight;

    private BulletPool bulletPool;

    private TextureAtlas atlas;

    private Rect worldBounds;

    private Sound blasterShoot;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound blasterShoot) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.atlas = atlas;
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        this.blasterShoot = blasterShoot;
    }

    @Override
    public void update(float delta) {
        if(this.getLeft()<worldBounds.getLeft()) {
            this.setLeft(worldBounds.getLeft());
        }
        if(this.getRight()>worldBounds.getRight()) {
            this.setRight(worldBounds.getRight());
        }
        pos.mulAdd(v, delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < 0){
            System.out.println("Left ship moving");
            this.pointerLeft = pointer;
            this.touchedLeft = true;
            moveLeft();
        } else if (touch.x > 0){
            System.out.println("Right ship moving");
            this.pointerRight = pointer;
            this.touchedRight = true;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if( pointer == this.pointerLeft ){
            touchedLeft = false;
            if (touchedRight) {
                moveRight();
            } else {
                stop();
            }
        }
        if(pointer == this.pointerRight){
            touchedRight = false;
            if( touchedLeft){
                moveLeft();
            }else {
                stop();
            }
        }
        return false;
    }


    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }


    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                    pressedLeft = false;
                    if (pressedRight) {
                        moveRight();
                    } else {
                        stop();
                    }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
            v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, atlas.findRegion("bulletMainShip"), pos, new Vector2(0, 0.5f), 0.01f, worldBounds, 1);
        blasterShoot.play();
    }

}
