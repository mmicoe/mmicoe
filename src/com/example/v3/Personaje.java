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
import org.andengine.extension.physics.box2d.util.Vector2Pool;
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
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import android.widget.Toast;


public class Personaje extends Scene {
		
	public static Personaje instance;
	//public Sprite sprite;
	Camera mCamera;
	public AnimatedSprite sprite;
	
	private float b_touchX;
    private float b_touchY;
    
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
				 
				b_touchX = sprite.getX() + (sprite.getWidth() / 2);
			    b_touchY = sprite.getY() + (sprite.getHeight() / 2);
                
			    float touchX = pSceneTouchEvent.getX();
                float touchY = pSceneTouchEvent.getY();

                float x_length = touchX - b_touchX;
                float y_length = touchY - b_touchY;
                
                sprite.setRotation(MathUtils.radToDeg((float)Math.atan2(y_length, x_length)));
                
				sprite.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2); 
                sprite.animate(new long[]{200, 200, 200}, 3, 5, true);
                
               
                /* Rota sobre sí mismo
                if(pSceneTouchEvent.isActionMove()){
                sprite.setRotation(sprite.getRotation()+90);} */
                  
              
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