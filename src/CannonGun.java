import java.util.ArrayList;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
/**
 * The CannonGun is used for shooting at Targets at the end of each level. To shoot, the user clicks their
 * Left-Mouse button. The CannonGun carries out the shootBall() method to shoot out a CannonBall object.
 *
 */
public class CannonGun {

	/**
	 * Shooting speed
	 */
	public static int shootSpeed = 60;
	/**
	 * World's AssetManager
	 */
	private AssetManager assetManager;
	/**
	 * A Node of all cannonBalls fired from the gun.
	 */
	private Node cannonBalls;
	/**
	 * The BulletAppState (for integrating physics)
	 */
	private BulletAppState bulletApp;

	/**
	 * Constructs a CannonGun object. Initializes the assetManager that it will
	 * use when creating new CannonBalls, the BulletAppState (which allows for Physics 
	 * to be implemented in the game), and the CannonBalls node where all future
	 * cannon ball projectiles are added.
	 *
	 * @param  a reference to the World's assetManager
	 * @param b reference to the World's BulletAppState
	 */
	public CannonGun(AssetManager a, BulletAppState b) {
		this.assetManager = a;
		this.bulletApp = b;
		cannonBalls = new Node();
	}
	/**
	 * The shootBall() method creates a new CannonBall object and sets all necessary properties for its
	 * Spatial and RigidBodyControl. Then, it attaches it to the World's rootNode so the user can see it in the game.
	 *
	 * @param  location the current location of the character
	 * @param  direction the current direction of the character
	 * @param  w the World
	 * @see CannonBall
	 * 
	 */
	public void shootBall(Vector3f location, Vector3f direction, Node w) {
		CannonBall c = new CannonBall(this.assetManager);

		Spatial cBall = c.getSpatial();
		RigidBodyControl cBallRB = c.getRB();
		cBall.setLocalTranslation(location);
		cBallRB.setPhysicsLocation(location);


		cBallRB.setLinearVelocity(direction.mult(shootSpeed));
		cBallRB.setKinematic(false);
		cBallRB.setFriction(1f);
		bulletApp.getPhysicsSpace().add(cBallRB);
		cannonBalls.attachChild(cBall);
		//w.attachChild(cBall);
		//		cannonBalls[].attachChild(cBall);
		//System.out.println("CBALL" + cBall.getWorldBound());
	}

	public Node getCannonBallNode() {return this.cannonBalls;}
}
