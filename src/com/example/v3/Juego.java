package com.example.v3;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;

import android.widget.Toast;


public class Juego extends Scene {
     
	public Personaje perso1;
	Camera mCamera;

	public Paisaje level1;
	
	public Juego() {
		
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    //mCamera = BaseActivity.getSharedInstance().mCamera;
	    
	    //perso1 = Personaje.getSharedInstance();
	    perso1 = new Personaje();
	    
	    level1 = Paisaje.getSharedInstance();
	        
	    attachChild(level1);
	    attachChild(perso1.sprite);
	    registerTouchArea(perso1.sprite);
	 
	    
	    level1.setTouchAreaBindingOnActionDownEnabled(true);
	    level1.setTouchAreaBindingOnActionMoveEnabled(true);

	}
	 
}
