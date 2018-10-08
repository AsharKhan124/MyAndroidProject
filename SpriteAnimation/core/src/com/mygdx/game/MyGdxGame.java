package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//import javafx.animation.Animation;

public class MyGdxGame extends ApplicationAdapter {
	/*SpriteBatch batch;
	Texture img;*/
	private SpriteBatch batch;
	//private BitmapFont font;
	private Texture img;
	//private Sprite sprite;
	private TextureAtlas runAtlas;
	private Animation animation;
	private float timer=0;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		batch = new SpriteBatch();
		//font = new BitmapFont();
		//font.setColor(Color.BLUE);
		//img = new Texture("MarioStanding.bmp");
		//sprite = new Sprite(img);
		runAtlas = new TextureAtlas(Gdx.files.internal("mario.atlas"));
		animation = new Animation(1/3f,runAtlas.getRegions());
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//font.draw(batch,"Ashar Khan",10,10);
		//sprite.draw(batch);
		timer+=Gdx.graphics.getDeltaTime();
		batch.draw((TextureRegion) animation.getKeyFrame(timer,true),100,100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();

		batch.dispose();
		//font.dispose();
		//img.dispose();
		runAtlas.dispose();
	}
}
