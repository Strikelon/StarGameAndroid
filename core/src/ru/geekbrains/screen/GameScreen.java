package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.ActionListener;
import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.Explosion;
import ru.geekbrains.sprite.GameOverSprite;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.NewGameSprite;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemiesEmmiter;


public class GameScreen extends Base2DScreen implements ActionListener {

    private static final int STAR_COUNT = 64;

    private Game game;

    private Texture bgTexture;
    private Background background;

    private TextureAtlas textureAtlas;
    private Star[] stars;

    private MainShip mainShip;

    private BulletPool bulletPool;

    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;
    private Music music;

    private EnemyPool enemyPool;
    private EnemiesEmmiter enemiesEmmiter;
    private ExplosionPool explosionPool;

    private boolean isGameOver = false;
    private GameOverSprite gameOverSprite;
    private NewGameSprite newGameSprite;

    public GameScreen(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("blasterShoot.mp3"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));

        bgTexture = new Texture("bg.png");
        background = new Background(new TextureRegion(bgTexture));
        textureAtlas = new TextureAtlas("mainAtlas.tpack");
        stars =new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(textureAtlas);
        }
        explosionPool = new ExplosionPool(textureAtlas, explosionSound);
        bulletPool = new BulletPool();
        mainShip = new MainShip(textureAtlas, bulletPool, explosionPool, laserSound);

        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, bulletSound);
        enemiesEmmiter = new EnemiesEmmiter(enemyPool, worldBounds, textureAtlas);

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
        gameOverSprite = new GameOverSprite(textureAtlas);
        newGameSprite = new NewGameSprite(textureAtlas,this);
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
        enemyPool.updateActiveObjects(delta);
        explosionPool.updateActiveObjects(delta);
        if (!isGameOver) {
            enemiesEmmiter.generate(delta);
        }else {
            gameOverSprite.update(delta);
            newGameSprite.update(delta);
        }
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                mainShip.destroy();
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                bullet.destroy();
                mainShip.damage(bullet.getDamage());
                return;
            }
        }

        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.isDestroyed() || bullet.getOwner() != mainShip) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    bullet.destroy();
                    enemy.damage(bullet.getDamage());
                    return;
                }
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    public void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
        }else{
            music.stop();
            List<Enemy> enemyList = enemyPool.getActiveObjects();
            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }else {
                    enemy.destroy();
                }
            }
            List<Bullet> bulletList = bulletPool.getActiveObjects();
            for (Bullet bullet : bulletList) {
                if (bullet.isDestroyed()) {
                    continue;
                }else {
                    bullet.destroy();
                }
            }
            isGameOver = true;
        }
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        if(isGameOver) {
            gameOverSprite.draw(batch);
            if(newGameSprite.isFlicker()) {
                newGameSprite.draw(batch);
            }
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOverSprite.resize(worldBounds);
        newGameSprite.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        textureAtlas.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
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
        mainShip.touchDown(touch, pointer);
        newGameSprite.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        newGameSprite.touchUp(touch,pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
         if (src == newGameSprite && isGameOver) {
            game.setScreen(new GameScreen(game));
        }
    }
}