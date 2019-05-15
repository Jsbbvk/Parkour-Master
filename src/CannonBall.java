import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
/**
 * The CannonBall is used for shooting at Targets at the end of each level. To shoot, the user clicks their
 * Left-Mouse button.
 *
 */
public class CannonBall {
	
	/** Spatial representing the ball */
	private Spatial cBall;
	/**
	 * The RigidBodyControl for the ball (for collisions)
	 */
	private RigidBodyControl cBallRB;
	
	/**
	 * Constructs a CannonBall object. Initializes the Spatial for the 
	 * CannonBall, as well as the Spatial's RigidBodyControl.
	 *
	 * @param a reference to the World's assetManager
	 * @see CannonGun
	 */
	public CannonBall(AssetManager a) {
		float ballRad = 0.5f;
		cBall = new Geometry("cannon ball", new Sphere(32, 32, ballRad, true, false));
		
		Material stone_mat = new Material(a, "Common/MatDefs/Misc/Unshaded.j3md");
	    
	    stone_mat.setColor("Color", ColorRGBA.Gray);
	    cBall.setMaterial(stone_mat);
	    
		
	    cBallRB = new RigidBodyControl(1000f);
	    cBall.addControl(cBallRB);
	}
	/**
	 * Returns the Spatial that serves as the physical representation of the CannonBall in the world.
	 *
	 * @return the CannonBall's spatial
	 */
	public Spatial getSpatial() {return this.cBall;}
	/**
	 * Returns the RigidBodyControl that is used for detecting bounding boxes/collisions in the world.
	 *
	 * @return the CannonBall's RigidBodyControl
	 */
	public RigidBodyControl getRB() {return this.cBallRB;}
	
}
