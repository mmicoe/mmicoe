package com.example.v3;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

public class Juego extends Scene {
     
	//public Figura ship;
	public Personaje perso1;
	Camera mCamera;

	public Juego() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = BaseActivity.getSharedInstance().mCamera;
	    perso1 = Personaje.getSharedInstance();
	    attachChild(perso1.sprite);
	    
	   //ship = Figura.getSharedInstance();
	   // attachChild(ship.sprite);
	}

	
}
