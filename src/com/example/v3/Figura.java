package com.example.v3;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;


public class Figura {
	
	    public Rectangle sprite;
	    public static Figura instance;
	    Camera mCamera;

	    public static Figura getSharedInstance() {
	        if (instance == null)
	            instance = new Figura();
	        return instance;
	    }

	    private Figura() {
	        sprite = new Rectangle(0, 0, 70, 30, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager());

	        
	        mCamera = BaseActivity.getSharedInstance().mCamera;
	        sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
	            mCamera.getHeight() - sprite.getHeight() - 10);
	    }
	

}
