package ru.geekbrains.screen;

import ru.geekbrains.base.Base2DScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.Enemy0Pool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip0;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 64;

    private Texture bgTexture;
    private Background background;

    private TextureAtlas textureAtlas;
    private Star[] stars;

    private MainShip mainShip;

    private Sound blasterShoot;
    private Music music;

    Rect worldBounds;

    private BulletPool bulletPool;
    //---------------------------------------
    private Enemy0Pool enemy0Pool;
    //---------------------------------------


    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("mainAtlas.tpack");
        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setVolume(0.4f);
        music.setLooping(true);
        music.play();
        bulletPool = new BulletPool();
        blasterShoot = Gdx.audio.newSound(Gdx.files.internal("blasterShoot.mp3"));
        mainShip = new MainShip(textureAtlas, bulletPool, blasterShoot);
        //---------------------------------------
        enemy0Pool = new Enemy0Pool();
        //---------------------------------------
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveObjects(delta);
        //---------------------------------------
        enemy0Pool.updateActiveObjects(delta);
        if(enemy0Pool.isEmpty()){
            EnemyShip0 enemyShip0 = enemy0Pool.obtain();
            enemyShip0.set(textureAtlas,worldBounds);
        }
        //---------------------------------------
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        //---------------------------------------
        enemy0Pool.freeAllDestroyedActiveObjects();
        //---------------------------------------
    }

    public void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveObjects(batch);
        //---------------------------------------
        enemy0Pool.drawActiveObjects(batch);
        //---------------------------------------
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        blasterShoot.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch,pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch,pointer);
        return super.touchUp(touch, pointer);
    }

}
