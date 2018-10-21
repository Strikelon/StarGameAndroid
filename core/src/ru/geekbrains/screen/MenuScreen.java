package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;

public class MenuScreen extends Base2DScreen{

    private SpriteBatch batch;
    private Texture img;

    private Vector2 pos;


    private boolean isTouchDown = false;
    private boolean isGoToTargerMousePos = false;
    private boolean isKeyDownPressed = false;
    private boolean isKeyDownHolding = false;
    private Vector2 targetMouse;
    private Vector2 vTargetMouse;
    private Vector2 vKeyDown;


    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("sun.png");
        pos = new Vector2(0,0);
        targetMouse = new Vector2(pos.x,pos.y);
        vTargetMouse = new Vector2(0,0);
        vKeyDown = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        if(isTouchDown){
            isTouchDown= false;
            targetMouse.set(getCoordX(),getCoordY());
            vTargetMouse = targetMouse.cpy().sub(pos);
            vTargetMouse.nor();
            vTargetMouse.scl(2);
            System.out.println("Новая цель: x = "+getCoordX()+" y = "+getCoordY());
            isGoToTargerMousePos = true;
        }
        if(isGoToTargerMousePos) {
            if (Math.round(pos.x) != targetMouse.x && Math.round(pos.y) != targetMouse.y) {
                pos.add(vTargetMouse);
            }else {
                isGoToTargerMousePos = false;
                vTargetMouse.set(0,0);
            }
        }
        if(isKeyDownPressed){
            if(getKeyDownCode() == 19){
                vKeyDown.set(0,2);
            }
            if(getKeyDownCode() == 20){
                vKeyDown.set(0,-2);
            }
            if(getKeyDownCode() == 21){
                vKeyDown.set(-2,0);
            }
            if(getKeyDownCode() == 22){
                vKeyDown.set(2,0);
            }
            isKeyDownHolding = true;
            isKeyDownPressed = false;
        }
        if(isKeyDownHolding){
            pos.add(vKeyDown);
        }



    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isTouchDown = true;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(isGoToTargerMousePos){
            isGoToTargerMousePos = false;
        }
        isKeyDownPressed = true;
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        isKeyDownHolding = false;
        return super.keyUp(keycode);
    }
}
