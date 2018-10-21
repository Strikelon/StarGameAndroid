package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("starBack.jpg");
		img = new Texture("badlogic.jpg");
		Vector2 v1 = new Vector2(4,5);
		Vector2 v2 = new Vector2(6,5);
		v1.add(v2);
		System.out.println("("+v1.x+","+v1.y+")");
		v1.set(7,5);
		v2.set(2,2);
		Vector2 v3 = new Vector2();
		Vector2 v4 = new Vector2();
		v3 = v1.cpy().sub(v2);
		v4 = v1.sub(v2);
		v1.sub(v2);
		System.out.println("("+v1.x+","+v1.y+")");
		System.out.println("("+v3.x+","+v3.y+")");
		System.out.println("("+v4.x+","+v4.y+")");
		v1.set(5,5);
		v1.scl(5);
		System.out.println(v1);
		v1.set(5,5);
		System.out.println(v1.len());
		System.out.println(v1.len2());
		v1.nor();
		System.out.println(v1.len());
		System.out.println("("+v1.x+","+v1.y+")");
		v1.set(3,8);
		v1.nor();
		System.out.println(v1.len());
		System.out.println("("+v1.x+","+v1.y+")");
		v1.set(3,5);
		v2.set(24,9);
		v1.nor();
		v2.nor();
		System.out.println(Math.acos(v1.dot(v2)));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background,0,0);
		batch.draw(img,0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		background.dispose();
	}
}
