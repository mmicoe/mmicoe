package com.example.v3;


import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.BoundCamera;
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
import org.andengine.extension.tmx.TMXTile;
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
import org.andengine.util.Constants;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import android.widget.Toast;


public class Personaje {
		
	public static Personaje instance;
	//public Sprite sprite;
	Camera mCamera;
	final AnimatedSprite sprite;
	
	private float b_touchX;
    private float b_touchY;
    
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mPlayerTextureRegion;
	Path path;
	
	public static Personaje getSharedInstance() {
        if (instance == null)
            instance = new Personaje();
        return instance;
    }
	    
	public Personaje() {
	   
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
		mBitmapTextureAtlas = new BitmapTextureAtlas(BaseActivity.getSharedInstance().getTextureManager(), 72, 128);
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas,BaseActivity.getSharedInstance().getBaseContext(), "enemy.png", 0, 0, 3, 4);
		//mGrassBackground = new RepeatingSpriteBackground(BaseActivity.getSharedInstance().CAMERA_WIDTH,BaseActivity.getSharedInstance().CAMERA_HEIGHT,BaseActivity.getSharedInstance().getTextureManager(), AssetBitmapTextureAtlasSource.create(BaseActivity.getSharedInstance().getAssets(), "gfx/background_grass.png"),BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		mBitmapTextureAtlas.load();
		
		
		sprite = new AnimatedSprite(100, 100, mPlayerTextureRegion, BaseActivity.getSharedInstance().getVertexBufferObjectManager()){
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
			
    }

	
		
}