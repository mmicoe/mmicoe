package com.example.v3;

import org.andengine.engine.camera.Camera;
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
import org.andengine.util.debug.Debug;

import android.widget.Toast;


public class Juego extends Scene {
     
	//public Figura ship;
	public Personaje perso1;
	Camera mCamera;
	
	//Mapa Tiled
	private TMXTiledMap mTMXTiledMap;
	BaseActivity activity;

	public Juego() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = BaseActivity.getSharedInstance().mCamera;
	    perso1 = Personaje.getSharedInstance();
	    
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
	    
	    activity.mEngine.registerUpdateHandler(new FPSLogger());
	    //Cargamos el tileMap
	    try {
			final TMXLoader tmxLoader = new TMXLoader(BaseActivity.getSharedInstance().getAssets(),activity.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, BaseActivity.getSharedInstance().getVertexBufferObjectManager(), new ITMXTilePropertiesListener() {
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					/* We are going to count the tiles that have the property "cactus=true" set. */
					
				}
			});
			mTMXTiledMap = tmxLoader.loadFromAsset("txm/bosque.tmx");
	
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}

	    final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
		attachChild(tmxLayer);
	    
	    attachChild(perso1.sprite);
	    registerTouchArea(perso1.sprite);
		setTouchAreaBindingOnActionDownEnabled(true);
		
	   //ship = Figura.getSharedInstance();
	   // attachChild(ship.sprite);
	}

	
}
