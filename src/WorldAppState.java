import java.util.ArrayList;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import de.lessvoid.nifty.Nifty;

public class WorldAppState extends BaseAppState{
	Node rootNode=new Node();
	private Nifty nifty;
	private boolean left = false, right = false, up = false, down = false;
	private boolean rise = false, descend = false;
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();
	private Vector3f walkDirection = new Vector3f();
	boolean guiLoaded=false;
	private Vector3f oldPosition;
	private Vector3f initalUp;
	private CameraNode cameranode;

	private BulletAppState bulletAppState;
	private Spatial sceneModel;
	private RigidBodyControl landscape;
	private CharacterControl playerController;

	private Player player;
	private ArrayList<Spatial> cubeLocations;
	@Override
	protected void cleanup(Application arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialize(Application app) {
		app.getStateManager().detach(app.getStateManager().getState(FlyCamAppState.class));
		app.getInputManager().setCursorVisible(false);
		cubeLocations = new ArrayList<>();
		/*
		JmeCursor jCursor = new JmeCursor();
		int[] i = new int[16 * 16];
		Arrays.fill(i, 0);
		jCursor.setImagesData(IntBuffer.wrap(i));
		jCursor.setHeight(16);
		jCursor.setWidth(16);
		jCursor.setxHotSpot(0);
		jCursor.setyHotSpot(15);
		jCursor.setNumImages(1);
		inputManager.setMouseCursor(jCursor);
		*/
		
		// https://hub.jmonkeyengine.org/t/solved-hiding-the-mouse-cursor/32327
		
		bulletAppState = new BulletAppState();
		app.getStateManager().attach(bulletAppState);

		app.getViewPort().setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
		/*
		Level3 levelOne = new Level3(app, app.getAssetManager());
		rootNode.attachChild(levelOne.getWorld());
		bulletAppState.getPhysicsSpace().add(levelOne.getRB());
		//adding the blocks into the world
		for (WorldObject block : levelOne.parkourBlocks) {
			rootNode.attachChild(block.getSpatial());
			bulletAppState.getPhysicsSpace().add(block.getRB());
		}
		
		
		// sets up collision and physics for player
		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
		playerController = new CharacterControl(capsuleShape, 0.05f);
		playerController.setJumpSpeed(0);
		playerController.setFallSpeed(0);
		playerController.setGravity(0);
		playerController.setPhysicsLocation(new Vector3f(0, 10, 0));
		bulletAppState.getPhysicsSpace().add(playerController);

		initKeys();
		initalUp = cam.getUp();

		// add a light to the HUD
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(0, 0, -1.0f));
		guiNode.addLight(sun);
		setUpLight();

		
*/
	}

	@Override
	protected void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onEnable() {
		// TODO Auto-generated method stub
		
	}

}
