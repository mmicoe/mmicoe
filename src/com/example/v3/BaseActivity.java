package com.example.v3;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.Engine;


public class BaseActivity extends SimpleBaseGameActivity {
	
	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;

	public Font mFont;
	public Camera mCamera;

	//A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;
	 
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		
		    instance = this;
		    mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		    return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	   
	}
	
	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
	
		    mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		    mFont.load();
		    
	}
	
	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
	
		    mEngine.registerUpdateHandler(new FPSLogger());
		    mCurrentScene = new Presentacion();
		    //Comentado porque ya modifico el Background en la clase Presentacion
		    //mCurrentScene.setBackground(new Background(0.09804f, 0.7274f, 0.8f));
		    return mCurrentScene;
		
	}
	
	public static BaseActivity getSharedInstance() {
		
	    return instance;
	    
	}

	// Lo usamos para cambiar a la nueva escena.
	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	}

}