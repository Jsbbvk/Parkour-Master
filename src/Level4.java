import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;
/**
 * The Level4 class extends Level, and represents the fourth level
 * @see Level
 */
public class Level4 extends Level{
	private Spatial world;
	private RigidBodyControl worldRB;
	private AssetManager assetManager;
	/**
	 * Constructs a Level4 object. Initializes the world and assetManager. Then, it loads in the specific scene for level 4.
	 *
	 * @param assetManager reference to the World's assetManager
	 * @param w reference to the World
	 * @see World
	 */
	public Level4(World w, AssetManager assetManager) {
		super(w, assetManager);
		this.assetManager = assetManager;
		
		//hardcode values of the block positions
		
		assetManager.registerLocator("assets/TempLevel", FileLocator.class);
	    world= assetManager.loadModel("LowPoly_Tree_01.mesh.xml");
	    world.scale(200);
	    world.setLocalTranslation(30f, -50f, 0f);
	    CollisionShape shape =
	            CollisionShapeFactory.createMeshShape(world);
	    worldRB = new RigidBodyControl(shape, 0);
	    world.addControl(worldRB);
	}
	
	public Spatial getWorld() {return this.world;}
	public RigidBodyControl getRB() {return this.worldRB;}
	
}
