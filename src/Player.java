import com.jme3.renderer.Camera;
/**
 * The Player class serves to represent the player in a first person view in the game. It is primarily used to keep track of the camera,
 * and can be used for tracking the inventory in the future (we never got to this point.
 */
public class Player {
	private WorldObject[] inventory;
	private Camera cam;
	public WorldObject[] getInventory() {
		return inventory;
	}
	public void setInventory(WorldObject[] inventory) {
		this.inventory = inventory;
	}
	public Camera getCam() {
		return cam;
	}
	public void setCam(Camera cam) {
		
		this.cam = cam;
	}
	
}
