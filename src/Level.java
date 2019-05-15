import java.util.ArrayList;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;
/**
 * The Level class contains information about the block locations, the world, and the assetmanager. It is abstract.
 *
 */
public abstract class Level {
	public ArrayList<WorldObject> parkourBlocks;
	private AssetManager assetManager;
	private World w;
	/**
	 * Constructs a Level object. Initalizes the world and assetManager.
	 *
	 * @param assetManager reference to the World's assetManager
	 * @param w reference to the World
	 * @see World
	 */
	public Level(World w, AssetManager assetManager) {
		parkourBlocks = new ArrayList<>();
		this.assetManager = assetManager;
		this.w = w;
	}
	/**
	 * Adds a block to the world at the specified position.
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public void addBlock(float x, float y, float z) {
		assetManager.registerLocator("assets/Cube1", FileLocator.class);
		Spatial cube = assetManager.loadModel("cube.scene");
	    cube.setLocalTranslation(x,  y, z);
	    
	    CollisionShape shape =
	            CollisionShapeFactory.createMeshShape(cube);
	    RigidBodyControl cubeRB = new RigidBodyControl(shape, 0);
	    cube.addControl(cubeRB);
	    parkourBlocks.add(new WorldObject(w, cube, cubeRB));
	}
	/**
	 * Returns the spatial for this world
	 *
	 * @see World
	 */
	public abstract Spatial getWorld();
	
	/**
	 * Returns the RigidBodyControl for this world
	 *
	 * @see World
	 */
	public abstract RigidBodyControl getRB();
	
}
