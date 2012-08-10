package com.example.v3;


import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.widget.Toast;


public class Personaje extends Scene {
		
	public static Personaje instance;
	//public Sprite sprite;
	Camera mCamera;
	public AnimatedSprite sprite;
	
	private ITexture mTexture;
	private ITextureRegion mFaceTextureRegion;
	
	private RepeatingSpriteBackground mGrassBackground;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mPlayerTextureRegion;
	Path path;
	
	public static Personaje getSharedInstance() {
        if (instance == null)
            instance = new Personaje();
        return instance;
    }

public int onWaypointPassed(float[] x,float[] y , int waypoint) {
	
        int DIRECTION_UP = 1;
        int DIRECTION_LEFT = 2;
        int DIRECTION_DOWN = 3;
        int DIRECTION_RIGHT = 4;
        
 	    final float[] xs = x;
	    final float[] ys = y; 
	    
	    int direction=0;
	    
	    if(waypoint <= xs.length-2) {
	      final float xCur = xs[waypoint];
	      final float yCur = ys[waypoint];
	      final float xNext = xs[waypoint+1];
	      final float yNext = ys[waypoint+1];
	      double angle = Math.atan2(yCur-yNext, xNext-xCur);
	      angle = (angle* 180 / Math.PI);
	      if(angle >= 45 && angle <= 135) {
	        direction = DIRECTION_UP;
	      } else if(angle >= 135 && angle <= 225) {
	        direction = DIRECTION_LEFT;
	      } else if(angle >= 225 && angle <= 315) {
	        direction = DIRECTION_DOWN;
	      } else {
	        direction = DIRECTION_RIGHT;
	      }
	      
	    }
	    return direction;
}

	    
	private Personaje() {
	   
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
		mBitmapTextureAtlas = new BitmapTextureAtlas(BaseActivity.getSharedInstance().getTextureManager(), 128, 128);
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas,BaseActivity.getSharedInstance().getBaseContext(), "enemy.png", 0, 0, 3, 4);
		//mGrassBackground = new RepeatingSpriteBackground(BaseActivity.getSharedInstance().CAMERA_WIDTH,BaseActivity.getSharedInstance().CAMERA_HEIGHT,BaseActivity.getSharedInstance().getTextureManager(), AssetBitmapTextureAtlasSource.create(BaseActivity.getSharedInstance().getAssets(), "gfx/background_grass.png"),BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		mBitmapTextureAtlas.load();
		
		sprite = new AnimatedSprite(100, 100, 48, 64, mPlayerTextureRegion, BaseActivity.getSharedInstance().getVertexBufferObjectManager()){
			
			//Movimiento Touch
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				 
				 int DIRECTION_UP = 0;
			     int DIRECTION_LEFT = 1;
			     int DIRECTION_DOWN = 2;
			     int DIRECTION_RIGHT = 3;
			     int direction;
			     float lastX=0;
			     float lastY=0;
			
			 
				float currentX = pSceneTouchEvent.getX()- this.getWidth() / 2;
                float currentY = pSceneTouchEvent.getY()- this.getHeight() / 2;
                
                setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                
                // calculate angle between the two points, and convert it to degrees
                int angle = (int) (Math.atan2(currentY - lastY, currentX - lastX) * 180 / Math.PI);
                
                lastX = currentX;
                lastY = currentY;
			 
			 
                if(angle >= 45 && angle <= 135) {
        	        direction = DIRECTION_UP;
        	      } else if(angle >= 135 && angle <= 225) {
        	        direction = DIRECTION_LEFT;
        	      } else if(angle >= 225 && angle <= 315) {
        	        direction = DIRECTION_DOWN;
        	      } else {
        	        direction = DIRECTION_RIGHT;
        	      }
				
						switch(direction) {
							case 0:
								sprite.animate(new long[]{100, 100, 100}, 0, 2, true);
								break;
							case 1:
								sprite.animate(new long[]{100, 100, 100}, 9, 11, true);
								break;
							case 2:
								sprite.animate(new long[]{100, 100, 100}, 6, 8, true);
								break;
							case 3:
								sprite.animate(new long[]{100, 100, 100}, 3, 5, true);
								break;
						}
			 	
				return true;
		
		}
		};
			
		
	/** try {
			mTexture = new BitmapTexture(BaseActivity.getSharedInstance().getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return BaseActivity.getSharedInstance().getAssets().open("img/Motobug.png");
				}
			});

			this.mTexture.load();
			this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture(this.mTexture);
			
		} catch (IOException e) {
			Debug.e(e);
		}
		
		//BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(null, 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(null, 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BaseActivity.getSharedInstance().getTextureManager().loadTexture(Texture1);

		ITextureRegion player = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, BaseActivity.getSharedInstance().getBaseContext(), "img/Motobug.png", 0, 0);
		
		mCamera = BaseActivity.getSharedInstance().mCamera;
		//sprite= new Sprite(0, 0, this.mFaceTextureRegion, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		sprite= new Sprite(0, 0, player, BaseActivity.getSharedInstance().getVertexBufferObjectManager()){
		//Movimiento Touch
			@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
			return true;
		}
	};
		sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,mCamera.getHeight() - sprite.getHeight() - 10);

*/
    }

	
		
}