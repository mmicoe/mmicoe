package com.example.v3;


import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;


public class Personaje extends Scene {
		
	public static Personaje instance;
	public Sprite sprite;
	Camera mCamera;
	
	private ITexture mTexture;
	private ITextureRegion mFaceTextureRegion;
	
	public static Personaje getSharedInstance() {
        if (instance == null)
            instance = new Personaje();
        return instance;
    }

	
	private Personaje() {
		
		try {
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

    }
}