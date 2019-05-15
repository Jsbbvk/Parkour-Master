import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
/**
 * The Target class is used to represent the target that spawns at the end for each world.
 */
public class Target {

	private Spatial target;
	private RigidBodyControl targetRB;
	private AssetManager assetManager;
	private Node targetNode;
	
	
	/**
	 * Constructs a Target object to spawn at (0,0,0).
	 *
	 * @param assetManager reference to the World's assetManager
	 */
	public Target(AssetManager assetManager) {
		assetManager.registerLocator("assets/Target", FileLocator.class);
	    target= assetManager.loadModel("target.scene");
	    
	    Material stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

		stone_mat.setColor("Color", ColorRGBA.Red);
		target.setMaterial(stone_mat);
		target.scale(5);
		
	    CollisionShape shape =
	            CollisionShapeFactory.createMeshShape(target);
	    targetRB = new RigidBodyControl(shape, 0);
	    target.addControl(targetRB);
	    targetNode = new Node("TargetNode");
	    targetNode.attachChild(target);
	}
	/**
	 * Constructs a Target object to spawn at (x,y,z).
	 *
	 * @param assetManager reference to the World's assetManager
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public Target(AssetManager assetManager, float x, float y, float z) {
		System.out.println("TargetInit");
		assetManager.registerLocator("assets/Target", FileLocator.class);
	    target= assetManager.loadModel("target.scene");
	    target.setLocalTranslation(x, y, z);
	    
		Material stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

		stone_mat.setColor("Color", ColorRGBA.Red);
		target.setMaterial(stone_mat);
	    target.scale(5);
	    
	    CollisionShape shape =
	            CollisionShapeFactory.createMeshShape(target);
	    targetRB = new RigidBodyControl(shape, 0);
	    target.addControl(targetRB);
	    targetNode = new Node("TargetNode");
	    targetNode.attachChild(target);
	}
	
	public Spatial getSpatial() {return this.target;}
	public RigidBodyControl getRB() {return this.targetRB;}

	//add the targetNode as a child of the rootNode
	public Node getNode() {return targetNode;}
	
	/*
	public Target(World w, AssetManager assetManager) {
		assetManager.registerLocator("assets/Level1", FileLocator.class);
		world= assetManager.loadModel("Low Poly Mill.scene");
		world.scale(200);
		world.setLocalTranslation(30f, -50f, 0f);
		CollisionShape shape =
				CollisionShapeFactory.createMeshShape(world);
		worldRB = new RigidBodyControl(shape, 0);
		world.addControl(worldRB);

		assetManager.registerLocator("assets/Platform", FileLocator.class);
		cube = (Node)assetManager.loadModel("Cube.mesh.xml");
		cube.setLocalTranslation(5,10,5);
		cube.scale(5);

		CollisionShape shape1 =
				CollisionShapeFactory.createMeshShape(cube);
		RigidBodyControl cubeRB = new RigidBodyControl(shape1, 0);
		cube.addControl(cubeRB);

	}*/

	/**
	 * Checks if a ball has hit the target.
	 *
	 * @param ball the spatial of the ball to check for collision
	 * @return whether there has been a collision
	 */
	public boolean checkHit(Spatial ball) {
		
		//System.out.println("Target: " + target.getLocalTranslation());

		if(ball.getWorldBound()==null) {
			return false;
		}
//		System.out.println("CBall: " + ball1.getSpatial().getLocalTranslation());
		
		CollisionResults list = new CollisionResults();
		ball.getWorldBound().collideWith(target,list);
		//System.out.println("CheckPass2: " + list);
		
		if(list.size()>0) {
			
//			System.out.println("LIST.size" + list.size());

//			System.out.println("Bounce");
		
			//ball.getRB().setLinearVelocity(ball.getRB().getLinearVelocity().mult(0));
			ball.getParent().detachChild(ball);
			
			//collision.g
			return true;
		}
		return false;
	}

}
