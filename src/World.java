import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.system.NanoTimer;
import com.jme3.system.Timer;

/**
 * The World class is the main part of the game. This is where all the control occurs.
 * The appstates are attached and detached here to start and end levels. The World class 
 * extends the SimpleApplication class which is the main class of JME for building an application.
 * The implemented classes are the ones for building listeners in the application. 
 * 
 */
public class World extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

	private Spatial win;
	private ArrayList<Level> levelList=new ArrayList<Level>();
	private ArrayList<ArrayList<String>> cubeLocos=new ArrayList<ArrayList<String>>(); 
	private int slot;
	int modified;
	private Custom_Level_AppState le;
	private boolean winner=false;
	private boolean custom=false;
	private boolean finalReached=false;
	private CollisionShape winshape;
	private Vector3f start=new Vector3f(0,10,0);
	private Vector3f CP;
	private boolean left = false, right = false, up = false, down = false;
	private boolean rise = false, descend = false;
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();
	private Vector3f walkDirection = new Vector3f();
	boolean guiLoaded=false;
	private Vector3f oldPosition;
	private Vector3f initalUp;
	private CameraNode cameranode;
	private Node character=new Node();
	AmbientLight al;
	DirectionalLight dl;

	private Timer deathTimer=new NanoTimer();
	private BulletAppState bulletAppState;
	private Spatial sceneModel;
	private RigidBodyControl landscape;
	private CharacterControl playerController;
	Level level;
	private Player player;
	private ArrayList<Spatial> cubeLocations;
	private String levelName = "";

	private int state = 1;
	private final int DEV_MODE = 0;
	private final int PLAYER_MODE = 1;

	private AudioNode audioClip;

	private Node platformNodes;
	//private Node balls;
	private CannonGun cannonG;

	private BitmapText targetHitText;


	Target target;
	public SpeedPotion speedPot;
	/*
	 * Displays the environment
	 */
	
	/**
	 * The default method to start the game.
	 */
	public void startGame() {
		World a = new World();
		a.start();
	}

	/**
	 * Starts the game with the level defined by the levelVal String.
	 * @param levelVal The string representation of the level to be run.
	 */
	public void startGame(String levelVal) {
		levelName = levelVal;
		setShowSettings(false);
		player=new Player();
		AppSettings settings = new AppSettings(true);
		settings.setResolution(1280, 720);
		//		settings.setBitsPerPixel(32);
		//		settings.setFullscreen(true);
		setSettings(settings);
		start();
	}
	
	/**
	 * Returns the player for the game.
	 * 
	 * @return The Player of this application. 
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Sets the player for this game.
	 * 
	 * @param player The player object to set the player of this application to.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * The main method for the application. It builds the base for everything else
	 * and is called by the .start() method. It builds up all the underlaying logic
	 * that is not visible to the user.
	 */
	@Override
	public void simpleInitApp() {
		levelList.add(new Level1(this, assetManager));
		levelList.add(new Level2(this, assetManager));
		levelList.add(new Level3(this, assetManager));
		levelList.add(null);
		levelList.add(null);
		levelList.add(null);
		levelList.add(null);
		levelList.add(null);
		cubeLocos.add(null);
		cubeLocos.add(null);
		cubeLocos.add(null);
		cubeLocos.add(null);
		cubeLocos.add(null);
		for(int i=levelList.size()-1;i>=0;i--) {
			if(levelList.get(i)==null)
				slot=i;
		}
		player.setCam(cam);
		//flyCam.setEnabled(false);
		stateManager.detach(stateManager.getState(FlyCamAppState.class));
		inputManager.setCursorVisible(false);
		cubeLocations = new ArrayList<>();
		platformNodes = new Node("Platform Nodes");


		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);

		cannonG = new CannonGun(assetManager, bulletAppState);
		rootNode.attachChild(cannonG.getCannonBallNode());
		//		for(int i = 0; i<cannonG.getCannonBallNodes().size(); i++) {
		//			Spatial spat = balls.get(i).getSpatial();
		//			rootNode.attachChild(spat);
		//
		//		}
		viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));

		bulletAppState.getPhysicsSpace().addCollisionListener(this);


		// sets up collision and physics for player
		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
		playerController = new CharacterControl(capsuleShape, 0.05f);
		playerController.setJumpSpeed(40);
		playerController.setFallSpeed(100);
		playerController.setGravity(70);
		bulletAppState.getPhysicsSpace().add(playerController);
		character.addControl(playerController);
		character.setName("winner");
		le=new Custom_Level_AppState();
		le.setMode(true);
		switch (levelName) {
		case "Level 1":
			stateManager.attach(new Level_1_AppState());
			break;
		case "Level 2":
			stateManager.attach(new Level_2_AppState());
			break;
		case "Level 3":
			stateManager.attach(new Level_3_AppState());
			break;
		case "Level 4":
			custom=false;
			le.setSlot(4);
			le.setMode(false);
			stateManager.attach(le);
			break;
		case "Level 5":
			custom=false;
			le.setSlot(5);
			le.setMode(false);
			stateManager.attach(le);
			break;
		case "Level 6":
			custom=false;
			le.setSlot(6);
			le.setMode(false);
			stateManager.attach(le);
			break;
		case "Level 7":
			custom=false;
			le.setSlot(7);
			le.setMode(false);
			stateManager.attach(le);
			break;
		case "Level 8":
			custom=false;
			le.setSlot(8);
			le.setMode(false);
			stateManager.attach(le);
			break;
		case "Build 1":
			custom=true;
			modified=levelList.size();
			le.setType(1);
			le.setMode(true);
			stateManager.attach(le);
			break;
		case "Build 2":
			custom=true;
			modified=levelList.size();
			le.setType(2);
			le.setMode(true);
			stateManager.attach(le);
			break;
		case "Build 3":
			custom=true;
			modified=levelList.size();
			le.setType(3);
			le.setMode(true);
			stateManager.attach(le);
			break;
		case "Custom":
			break;
		default:
			stateManager.attach(new Level_1_AppState());
			break;

		}
		//		//adding the blocks into the world
		//		for (WorldObject block : level.parkourBlocks) {
		//			platformNodes.attachChild(block.getSpatial());
		//			bulletAppState.getPhysicsSpace().add(block.getRB());
		//		}

		initKeys();
		initAudio();
		initCrossHairs();
		initalUp = cam.getUp();
		System.out.println(platformNodes.getChildren().size());
		// add a light to the HUD
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(0, 0, -1.0f));
		guiNode.addLight(sun);


		rootNode.attachChild(platformNodes);
		rootNode.attachChild(character);

		//rootNode.attachChild(speedPot.getSpatial());

		//setting the players starting position to the first block
	}
	
	/**
	 * Returns the speedpotion for this application.
	 * @return The speedPotion for this application.
	 */
	public SpeedPotion getSpeedPot() {
		return speedPot;
	}

	/**
	 * Sets the speedPotion for this application.
	 * @param speedPot The speedPotion to set the speedPot to.
	 */
	public void setSpeedPot(SpeedPotion speedPot) {
		this.speedPot = speedPot;
	}

	/**
	 * Builds the music for the game and adds it to the rootNode. 
	 */
	private void initAudio() {

		assetManager.registerLocator("audio", FileLocator.class);

		//any clip (has to be .ogg) 
		audioClip = new AudioNode(assetManager, "Megalovania.ogg", DataType.Stream);
		audioClip.setLooping(true); 
		audioClip.setPositional(false);
		audioClip.setVolume((float) 0.1);
		rootNode.attachChild(audioClip);
		audioClip.play(); 
	}
	private long firstTime = 0;
	private long hitTarget = 0;

 	/**
	 * This method is continuously called by the appstate and is used to
	 * continuously check conditions in the game whenever they might change.
	 * Some of the things that this method checks for are is the player has 
	 * finished a level and is ready to move onto the next one.
	 * 
	 * @param tpf The the time per frame between the calls of the method.
	 */
	@Override
	public void simpleUpdate(float tpf) {
		camDir.set(cam.getDirection()).multLocal(0.6f);
		camLeft.set(cam.getLeft()).multLocal(0.4f);
		walkDirection.set(0, 0, 0);
		if (left) {
			walkDirection.addLocal(camLeft);
		}
		if (right) {
			walkDirection.addLocal(camLeft.negate());
		}
		if (up) {
			walkDirection.addLocal(camDir);
		}
		if (down) {
			walkDirection.addLocal(camDir.negate());
		}
		walkDirection.setY(0);
		playerController.setWalkDirection(walkDirection);
		cam.setLocation(playerController.getPhysicsLocation());

		if (rise) {
			Vector3f a = playerController.getPhysicsLocation();
			a.setY((float) (a.getY() + 0.1));
			playerController.setPhysicsLocation(a);

			cam.setLocation(playerController.getPhysicsLocation());
		}
		if (descend) {
			Vector3f a = playerController.getPhysicsLocation();
			a.setY((float) (a.getY() - 0.1));
			playerController.setPhysicsLocation(a);

			cam.setLocation(playerController.getPhysicsLocation());
		}
		character.setLocalTranslation(playerController.getPhysicsLocation());


		boolean targetHit=false;


		if (finalReached) {
			Node balls = cannonG.getCannonBallNode();
			targetHit = false;
			for (Spatial ball : balls.getChildren()) {
				//System.out.println("Loop?");

				if (target.checkHit(ball)) {
					targetHit = true;
					break;
				}
			} 
		}
		if(targetHit) {
			System.out.println("Ball hit");
			AudioNode audioClip2 = new AudioNode(assetManager, "targetHit.ogg", DataType.Stream);
			audioClip2.setLooping(false); 
			audioClip2.setPositional(false);
			audioClip2.setVolume((float) 0.2);
			rootNode.attachChild(audioClip2);
			audioClip2.play(); 
			platformNodes.attachChild(win);
			rootNode.attachChild(win);
			hitTarget = System.currentTimeMillis();
			targetHitText = new BitmapText(guiFont, false);
			targetHitText.setSize(guiFont.getCharSet().getRenderedSize() * 5);
			targetHitText.setText("You unlocked the exit!");

			targetHitText.setLocalTranslation(
					settings.getWidth() / 2 - targetHitText.getLineWidth() / 2, settings.getHeight() / 2 + targetHitText.getLineHeight() / 2, 0);
			guiNode.attachChild(targetHitText);	
			winner=true;
			//this.destroy();
		}
		if(hitTarget!=0) {
			if(System.currentTimeMillis()-hitTarget>3000) {
				targetHitText.removeFromParent();

			}
		}
		if(winner) {
			if(System.currentTimeMillis()-firstTime>3000) {
				targetHitText.removeFromParent();
			} 
			targetHitText.setText("Level Complete!");
			targetHitText.setLocalTranslation(
					settings.getWidth() / 2 - targetHitText.getLineWidth() / 2, settings.getHeight() / 2 + targetHitText.getLineHeight() / 2, 0);
			guiNode.attachChild(targetHitText);

		}
		if(playerController.onGround()) {
			deathTimer.reset();
		}
		if(deathTimer.getTimeInSeconds()>=4) {
			playerController.setPhysicsLocation(CP);
			playerController.setViewDirection(win.getLocalTranslation());
			deathTimer.reset();
		}
		if(winner==true) {
			if(stateManager.hasState(stateManager.getState(Level_1_AppState.class))) {
				stateManager.detach(stateManager.getState(Level_1_AppState.class));
				stateManager.attach(new Level_2_AppState());
				winner=false;
			}
			else if(stateManager.hasState(stateManager.getState(Level_2_AppState.class))) {
				stateManager.detach(stateManager.getState(Level_2_AppState.class));
				stateManager.attach(new Level_3_AppState());
				winner=false;
			}
			else if(stateManager.hasState(stateManager.getState(Level_3_AppState.class))) {
				stateManager.detach(stateManager.getState(Level_3_AppState.class));
				System.out.println("DOES THIS");
				winner=false;
				this.stop();
				//what to do once third level is complete//stateManager.attach(new Level_1_AppState());
			}
		}
	}

	/**
	 * Sets the light up for the game so that objects can be seen.
	 */
	public void setUpLight() {
		// We add light so we see the scene
		al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(al);

		dl = new DirectionalLight();
		dl.setColor(ColorRGBA.White);
		dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
		rootNode.addLight(dl);
	}

	/*
	 * Sets up all the key strokes for the game and and adds action listeners to
	 * them.
	 */
	private void initKeys() {


		inputManager.addMapping("Look_Left", new MouseAxisTrigger(MouseInput.AXIS_X, true),
				new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("Look_Right", new MouseAxisTrigger(MouseInput.AXIS_X, false),
				new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping("Look_Up", new MouseAxisTrigger(MouseInput.AXIS_Y, false),
				new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping("Look_Down", new MouseAxisTrigger(MouseInput.AXIS_Y, true),
				new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addListener(analogListener, "Look_Left", "Look_Right", "Look_Up", "Look_Down"
				// "Left", "Right", "Forward", "Backward", "Jump"
				);

		inputManager.addMapping("Rise", new KeyTrigger(KeyInput.KEY_E));
		inputManager.addMapping("Descend", new KeyTrigger(KeyInput.KEY_Q));

		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping("Restart", new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping("BackUp", new KeyTrigger(KeyInput.KEY_B));


		inputManager.addMapping("Save", new KeyTrigger(KeyInput.KEY_TAB));
		inputManager.addMapping("ToggleState", new KeyTrigger(KeyInput.KEY_LSHIFT));
		inputManager.addMapping("ShootCannonBall", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("Drink", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));

		inputManager.addListener(this, "ShootCannonBall");
		inputManager.addListener(this, "ToggleState");
		inputManager.addListener(this, "Save");
		inputManager.addListener(this, "Left");
		inputManager.addListener(this, "Right");
		inputManager.addListener(this, "Up");
		inputManager.addListener(this, "Down");
		inputManager.addListener(this, "Jump");
		inputManager.addListener(this, "Restart");
		inputManager.addListener(this, "BackUp");
		inputManager.addListener(this, "Rise");
		inputManager.addListener(this, "Descend");
		inputManager.addListener(this, "Drink");

	}

	/**
	 * Rotates the camera by the given parameters.
	 * 
	 * @param value the amount to rotate by
	 * @param axis the axis to rotate the camera on
	 */
	protected void rotateCamera(float value, Vector3f axis) {
		Matrix3f mat = new Matrix3f();
		float rotateSpeed = 2f;
		mat.fromAngleNormalAxis(rotateSpeed * value, axis);

		Vector3f up = cam.getUp();
		Vector3f left = cam.getLeft();
		Vector3f dir = cam.getDirection();
		mat.mult(up, up);
		mat.mult(left, left);
		mat.mult(dir, dir);

		Quaternion q = new Quaternion();
		q.fromAxes(left, up, dir);
		q.normalizeLocal();

		if (up.angleBetween(Vector3f.UNIT_Y) <= FastMath.HALF_PI) {
			cam.setAxes(q);
		}

	}

	/**
	 * Moves the camera by the given parameters.
	 * @param value the amount to moves the camera
	 * @param sideways Weather or not to move the camera sideways(true) or forward and backward(false)
	 */
	protected void moveCamera(float value, boolean sideways) {
		Vector3f vel = new Vector3f();
		Vector3f pos = cam.getLocation().clone();
		if (sideways) {
			cam.getLeft(vel);
		} else {
			cam.getDirection(vel);
			vel.setY(0);
		}
		vel.multLocal(value * 7);

		pos.addLocal(vel);

		cam.setLocation(pos);
	}

	/**
	 * Defines what the controls of the game should do.
	 * @param binding The identifier of the buttons as a string.
	 * @param isPressed Weather the button is pressed or not
	 * @param tpf Time per frame that this method is called
	 */
	public void onAction(String binding, boolean isPressed, float tpf) {
		if (binding.equals("ToggleState")) {
			if (isPressed) {
				if (state == DEV_MODE) {
					System.out.println("Entered Player Mode");
					state = PLAYER_MODE;
					playerController.setJumpSpeed(40);
					playerController.setFallSpeed(100);
					playerController.setGravity(70);

					for (Spatial block : cubeLocations) {
						CollisionShape shape =
								CollisionShapeFactory.createMeshShape(block);
						RigidBodyControl cubeRB = new RigidBodyControl(shape, 0);
						block.addControl(cubeRB);
						bulletAppState.getPhysicsSpace().add(cubeRB);
					}
				} else {
					System.out.println("Entered Developer Mode");
					state = DEV_MODE;
					playerController.setJumpSpeed(0);
					playerController.setFallSpeed(0);
					playerController.setGravity(0);
				}
			}
		}

		if (binding.equals("ShootCannonBall") && state == PLAYER_MODE) {
			if (isPressed) {
				/*
				 * The player has to shoot a target, which reveals the exit
				 */
				//TODO remove the balls after 5 secs

				// https://stackoverflow.com/questions/16376961/how-to-detect-the-intersections-between-2-nodes-in-jmonkey
				// use this to detect if the ball hits the target
				cannonG.shootBall(cam.getLocation().add(cam.getDirection().mult(5f)), cam.getDirection(), this.rootNode);
			}
		}

		if (binding.equals("RemoveBlock") && state == DEV_MODE) {
			CollisionResults results = new CollisionResults();
			Ray ray = new Ray(cam.getLocation(), cam.getDirection());
			platformNodes.collideWith(ray, results);

			if (results.size() > 0)
			{
				CollisionResult closest = results.getClosestCollision();
				Spatial s = closest.getGeometry();

				platformNodes.detachChild(s.getParent().getParent().getParent().getParent());
				cubeLocations.remove(s.getParent().getParent().getParent().getParent());

				//rootNode.updateGeometricState();

			}
		}
		if(binding.equals("Drink")) {
			CollisionResults results = new CollisionResults();
			Ray ray = new Ray(cam.getLocation(), cam.getDirection());
			if(speedPot != null && speedPot.getNode() != null) {
				speedPot.getNode().collideWith(ray, results);

			}

			if (results.size() > 0)
			{
				CollisionResult closest = results.getClosestCollision();
				Spatial s = closest.getGeometry();
				//if(speedPot.getNode().getLocalTranslation().distance(character.getLocalTranslation())) {
				speedPot.getNode().detachAllChildren();
				//rootNode.detachChild(s);
				//rootNode.detachChild(speedPot.getNode());
				this.getBulletAppState().getPhysicsSpace().removeAll(speedPot.getNode());
				rootNode.detachChild(speedPot.getNode());
				AudioNode audioClip3 = new AudioNode(assetManager, "slurp.ogg", DataType.Stream);
				audioClip3.setLooping(false); 
				audioClip3.setPositional(false);
				audioClip3.setVolume((float) 0.2);
				rootNode.attachChild(audioClip3);
				audioClip3.play(); 
				s.setLocalTranslation(0, -100, 0);
				speedPot.getRB().setEnabled(false);
				speedPot.getRB().destroy();
				speedPot.getNode().setLocalTranslation(0, -100, 0);

				speedPot.getNode().updateGeometricState();
				s.updateGeometricState();
				rootNode.updateGeometricState();

				this.getBulletAppState().setSpeed(this.getBulletAppState().getSpeed()*1.3f);
				//}
			}



		}

		if (binding.equals("Left")) {
			left = isPressed;
		} else if (binding.equals("Right")) {
			right = isPressed;
		} else if (binding.equals("Up")) {
			up = isPressed;
		} else if (binding.equals("Down")) {
			down = isPressed;
		} else if (binding.equals("Jump")) {

			if (state == DEV_MODE && isPressed) {
				CollisionResults results = new CollisionResults();
				Ray ray = new Ray(cam.getLocation(), cam.getDirection());
				platformNodes.collideWith(ray, results);
				if(results.size()==0) {
					assetManager.registerLocator("assets/Platform", FileLocator.class);
					Spatial cube = assetManager.loadModel("platform.scene");
					Vector3f pos = playerController.getPhysicsLocation().add(cam.getDirection().mult(30f));
					cube.setLocalTranslation(pos);
					cube.scale(5);

					CollisionShape shape =
							CollisionShapeFactory.createMeshShape(cube);
					RigidBodyControl cubeRB = new RigidBodyControl(shape, 0);
					cube.addControl(cubeRB);
					cubeLocations.add(cube);
					//bulletAppState.getPhysicsSpace().add(cubeRB);
					platformNodes.attachChild(cube);
					System.out.println(cube);
				}
				else if(results.size()>0) {
					CollisionResult closest = results.getClosestCollision();
					Spatial s = closest.getGeometry();

					platformNodes.detachChild(s.getParent().getParent().getParent().getParent());
					cubeLocations.remove(s.getParent().getParent().getParent().getParent());
				}
			}
			if (state == PLAYER_MODE) {
				playerController.jump();
			} 

		} else if (binding.equals("Rise")) {
			rise = isPressed;
		} else if (binding.equals("Descend")) {
			descend = isPressed;
		} else if (binding.equals("Save")) {
			if (custom) {
				if(levelName.equals("Build 1"))
					levelList.set(slot,new Level1(this, assetManager));
				else if(levelName.equals("Build 2"))
					levelList.set(slot,new Level2(this, assetManager));
				else if(levelName.equals("Build 3"))
					levelList.set(slot,new Level3(this, assetManager));
				cubeLocos.set(slot-3, new ArrayList<String>());
				for(Spatial s:cubeLocations) {
					String line=s.getLocalTranslation().toString();
					for(int i=0;i<line.length();i++) {
						if(line.charAt(i)==','||line.charAt(i)=='('||line.charAt(i)==')')
							line=line.substring(0, i)+line.substring(i+1);
					}
					cubeLocos.get(slot-3).add(line);
				}
				slot++;
				this.stop();
			}
		} else if(binding.equals("Restart")) {
			playerController.setPhysicsLocation(start);
			CP=new Vector3f(start.getX(),start.getY(),start.getZ());
		} else if(binding.equals("BackUp")) {
			playerController.setPhysicsLocation(CP);
		}
	}
	
	/**
	 * Builds the crosshairs that display where the player's center is.
	 */
	protected void initCrossHairs()
	{
		//setDisplayStatView(false);
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");
		ch.setLocalTranslation(
				settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
	}
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {

			int speed = 25;
			if (name.equals("Look_Left")) {
				rotateCamera(value, initalUp);
			} else if (name.equals("Look_Right")) {
				rotateCamera(-value, initalUp);
			} else if (name.equals("Look_Up")) {
				rotateCamera(-value, cam.getLeft());
			} else if (name.equals("Look_Down")) {
				rotateCamera(value, cam.getLeft());
			}

			if (name.equals("Forward")) {
				moveCamera(value, false);
			} else if (name.equals("Backward")) {
				moveCamera(-value, false);
			} else if (name.equals("Left")) {
				moveCamera(value, true);
			} else if (name.equals("Right")) {
				moveCamera(-value, true);
			}
		}
	};


	/**
	 * Sets up the collision listener that checks when two objects in the
	 * world collide.
	 * @param e The collision event where the collision occured.
	 */
	@Override
	public void collision(PhysicsCollisionEvent e) {
		// TODO Auto-generated method stub
		if(!winner) {
			if((e.getNodeA().getName().equals("final")&&e.getNodeB().getName().equals("winner"))||e.getNodeA().getName().equals("winner")&&e.getNodeB().getName().equals("final")) {
				//winner=true;
				firstTime = System.currentTimeMillis();
				finalReached=true;
				//System.out.println("IT WORKS");//test final block collision
				//optional code to move on once player wins
			}
		}
		if((e.getNodeA().getName().equals("normal")&&e.getNodeB().getName().equals("winner"))||e.getNodeA().getName().equals("winner")&&e.getNodeB().getName().equals("normal")) {
			if(e.getNodeA().getName().equals("normal")) {
				CP=e.getNodeA().getLocalTranslation().add(0, 10, 0);
			}
			else {
				CP=e.getNodeB().getLocalTranslation().add(0, 10, 0);
			}
		}
	}

	/**
	 * Returns the ArrayList of cubeLocations for the current level.
	 * @return The list of locations for the current level.
	 */
	public ArrayList<Spatial> getCubeLocations() {
		return cubeLocations;
	}

	/**
	 * Returns this worlds bulletappstate.
	 * @return The bulletappstate of this world.
	 */
	public BulletAppState getBulletAppState() {
		return bulletAppState;
	}

	/**
	 * Returns the Node that holds all the blocks for the game.
	 * @return The node that holds all the blocks.
	 */
	public Node getPlatformNodes() {
		return platformNodes;
	}

	/**
	 * Set the final block or the win block for the game.
	 * @param loadModel The Spatial to set the win block to.
	 */
	public void setWinBlock(Spatial loadModel) {
		win=loadModel;
	}

	/**
	 * Returns the winning platform of the game.
	 * @return The winning platform as a spatial.
	 */
	public Spatial getWinBlock() {
		return win;
	}

	/**
	 * Returns the Character Control of the player which is the 
	 * main representation of the player.
	 * @return The Character Control of the player.
	 */
	public CharacterControl getPlayerController() {
		return playerController;
	}

	/**
	 * Returns the start location of the player as a vector.
	 * @return The starting location of the paleyer.
	 */
	public Vector3f getStartLocation() {
		return start;
	}

	/**
	 * Sets or updates the checkpoint for the game.
	 * @param cp The vector for the position of the checkpoint,
	 */
	public void setCheckPoint(Vector3f cp) {
		CP=cp;
	}
	
	/**
	 * Returns the level for the game.
	 * @return The level of the game.
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Sets the level for the game.
	 * @param le The level to set the game to.
	 */
	public void setLevel(Level le) {
		level=le;
	}
	
	/**
	 * Returns the target that the player must hit to move past a level.
	 * @return The target for the level.
	 */
	public Target getTarget() {
		return target;
	}

	/**
	 * Sets the target of the level.
	 * @param target The target object to set for the level.
	 */
	public void setTarget(Target target) {
		this.target = target;
	}

	/**
	 * Returns value of the finalReaced variable. True if the final
	 * platform is reached and false of it isn't.
	 * @return The value of the finalReached variable.
	 */
	public boolean getFinalReached() {
		// TODO Auto-generated method stub
		return finalReached;
	}
	
	/**
	 * Sets the finalReached variable as true or false.
	 * @param r The state to set the finalReached variable to.
	 */
	public void setFinalReached(boolean r) {
		finalReached=r;
	}
	
	/**
	 * Returns the list containing the levels of this game.
	 * @return The list of levels for this game.
	 */
	public ArrayList<Level> getLevelList() {
		return levelList;
	}

	/**
	 * Returns the ArrayList of String ArraayLists that holds the block 
	 * locations for each of the blocks in each of the levels.
	 * @return The ArrayList of String ArrayLists that hold the locations of the the blocks for each level.
	 */
	public ArrayList<ArrayList<String>> getCubeLocos() {
		// TODO Auto-generated method stub
		return cubeLocos;
	}
	
	/**
	 * Returns the slot that is the representation of the the 8 slots from the 
	 * main menu.
	 * @return The slot number
	 */
	public int getSlot() {
		return slot;
	}
	
	/**
	 * Sets the numerical value of the slot variable.
	 * @param i The integer to set the slot variable to.
	 */
	public void setSlot(int i) {
		slot=i;
	}
	
	/**
	 * Returns the ambient light for this world.
	 * @return The ambient light for this world.
	 */
	public AmbientLight getLight() {
		return al;
	}
	
	/**
	 * Returns the directional light for this world.
	 * @return The directional light for this world.
	 */
	public DirectionalLight getDirectLight() {
		return dl;
	}
}
