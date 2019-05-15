import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
/**
 * The WorldObject class is used to represent any special world object for the world (we didn't end up using it that much.)
 */
public class WorldObject {
	private Spatial spatial;
	private RigidBodyControl spatialRB;
	private World world;
	
	/**
	 * Constructs a WorldObject
	 * 
	 * @param w the World
	 * @param spatial the spatial for the object
	 * @param spatialRB the rigidBody for the spatial
	 */
	public WorldObject(World w, Spatial spatial, RigidBodyControl spatialRB) {
		this.spatial = spatial; this.spatialRB = spatialRB;
		this.world = w;
	}
	
	public WorldObject(World w) {
		this.world = w;
	}
	
	public void removeFromWorld() {
			world.getRootNode().detachChild(this.spatial);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public Spatial getSpatial() {return this.spatial;}
	public RigidBodyControl getRB() {return this.spatialRB;}
	
	/**
	 * Moves the object
	 * 
	 * @param dx x amount
	 * @param dy y amount
	 * @param dz z amount
	 */
	public void move(float dx, float dy, float dz) {
		//gets current position
		Vector3f currentPos = spatial.getLocalTranslation();
		currentPos.addLocal(dx, dy, dz);
		spatial.setLocalTranslation(currentPos);
	}
	/**
	 * Sets position
	 * 
	 * @param dx x amount to shift
	 * @param dy y amount to shift
	 * @param dz z amount to shift
	 */
	public void setPosition(float dx, float dy, float dz) {
		spatial.setLocalTranslation(dx, dy, dz);
	}
	/**
	 * Gets position
	 * 
	 * @return Vector representing the position from the center
	 */
	public Vector3f getPosition() {
		return this.spatial.getLocalTranslation();
	}
}
