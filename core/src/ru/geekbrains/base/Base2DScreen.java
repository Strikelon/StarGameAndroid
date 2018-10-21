package ru.geekbrains.base;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

public class Base2DScreen implements Screen, InputProcessor {

    private int screenHeigth;
    private int screenWidth;
    private int coordY;
    private int coordX;
    private int keyDownCode;
    private int keyUpCode;

    protected int getCoordY(){
        return coordY;
    }

    protected int getCoordX(){
        return coordX;
    }

    protected int getKeyDownCode(){
        return keyDownCode;
    }

    protected int getKeyUpCode(){
        return keyUpCode;
    }




    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        this.screenHeigth = height;
        this.screenWidth = width;
        System.out.println("resize w = " + width + " h = " + height);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }

    @Override
    public boolean keyDown(int keycode) {
        keyDownCode = keycode;
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyUpCode = keycode;
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        coordY = screenHeigth - screenY;
        coordX = screenX;
        System.out.println("Обновленный touchDown screenX = " + coordX + " screenY = " + coordY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled");
        return false;
    }
}
