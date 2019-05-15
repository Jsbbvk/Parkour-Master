import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
/**
 * The SpeedPotion class is used to represent the speed potion that spawns on the 8th block in each world.
 */
public class SpeedPotion {

	private Spatial potion;
	private RigidBodyControl potionRB;
	private AssetManager assetManager;
	private Node potionNode;


	/**
	 * Constructs a SpeedPotion object to spawn at (0,0,0).
	 *
	 * @param assetManager reference to the World's assetManager
	 */
	public SpeedPotion(AssetManager assetManager) {
		assetManager.registerLocator("assets/SpeedPot", FileLocator.class);
		potion= assetManager.loadModel("Cone1.mesh.xml");

		Material speed_color = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		ColorRGBA a = new ColorRGBA();
		a.set((float)(0/255.0), (float)(231/255.0), (float)(255/255.0), (float)(1));
		speed_color.setColor("Color", a);
		potion.setMaterial(speed_color);
		potion.scale(1);

		CollisionShape shape =
				CollisionShapeFactory.createMeshShape(potion);
		potionRB = new RigidBodyControl(shape, 0);
		potion.addControl(potionRB);
		potionNode = new Node("PotionNode");
		potionNode.attachChild(potion);
		potion.rotate(0.0f, (float)(Math.PI), 0.0f);
		potion.setLocalRotation(potion.getLocalRotation());
		System.out.println(potion.getLocalRotation());
	}
	/**
	 * Constructs a SpeedPotion object to spawn at (x,y,z).
	 *
	 * @param assetManager reference to the World's assetManager
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public SpeedPotion(AssetManager assetManager, float x, float y, float z) {
		assetManager.registerLocator("assets/SpeedPot", FileLocator.class);
		potion= assetManager.loadModel("Cone1.mesh.xml");
		potion.setLocalTranslation(x, y, z);
		Material speed_color = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		ColorRGBA a = new ColorRGBA();
		a.set((float)(0/255.0), (float)(231/255.0), (float)(255/255.0), (float)(1));
		speed_color.setColor("Color", a);
		potion.setMaterial(speed_color);
		potion.scale(1);

		CollisionShape shape =
				CollisionShapeFactory.createMeshShape(potion);
		potionRB = new RigidBodyControl(shape, 0);
		potion.addControl(potionRB);
		potionNode = new Node("PotionNode");
		potionNode.attachChild(potion);
		potion.rotate(0.0f, (float)(Math.PI), 0.0f);
		potion.setLocalRotation(potion.getLocalRotation());
		System.out.println(potion.getLocalRotation());
		
	}

	public Spatial getSpatial() {return this.potion;}
	public RigidBodyControl getRB() {return this.potionRB;}

	public Node getNode() {return potionNode;}


}
