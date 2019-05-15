import java.io.File;
import java.util.Scanner;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
/**
 * This is the appstate for level 2. It is attached when the player wants to play it
 * and detached when the player is done. This is also the reason the levels transition
 * between each other once one is done.
 *
 */
public class Level_2_AppState extends BaseAppState{
	private World world;

	/**
	 * Method that is call when this appstate is detached from an application's state manager.
	 * Acts to remove all elements built by the appstate.
	 * 
	 * @param arg0 The application that is detaching this appstate
	 * 
	 * 
	 */
	@Override
	protected void cleanup(Application arg0) {
		// TODO Auto-generated method stub
		world.getRootNode().removeLight(world.getLight());
		world.getRootNode().removeLight(world.getDirectLight());
		world.getRootNode().getChildren().remove(world.getLevel().getWorld());
		world.getRootNode().getChildren().remove(world.getWinBlock());
		world.getTarget().getNode().detachAllChildren();
		world.getRootNode().detachChild(world.getTarget().getNode());
		world.getPlatformNodes().getChildren().remove(world.getTarget().getSpatial());
		world.getBulletAppState().getPhysicsSpace().remove(world.getTarget().getRB());
		world.getPlatformNodes().getChildren().clear();
		world.getPlatformNodes().getChildren().remove(world.getWinBlock());
		world.getBulletAppState().getPhysicsSpace().getRigidBodyList().clear();
		world.getBulletAppState().getPhysicsSpace().remove(world.getLevel().getRB());
		world.getCubeLocations().clear();
		world.setFinalReached(false);
		world.getLevelList().set(1, new Level2(world,world.getAssetManager()));
	}

	/**
	 * Method that is called when this appstate is attached to the applications 
	 * state manager. This method builds all the elements.
	 * 
	 * @param app The application that is building this appstate.
	 * 
	 */
	@Override
	protected void initialize(Application app) {
		// TODO Auto-generated method stub
		world=(World) app;
		Level level=world.getLevelList().get(1);
		world.setLevel(level);
		world.getRootNode().attachChild(level.getWorld());
		world.getBulletAppState().getPhysicsSpace().add(level.getRB());
		buildBlocks("block_locos2.txt");
		world.getPlayerController().setPhysicsLocation(world.getStartLocation());
		Vector3f sTLVec = world.getWinBlock().getLocalTranslation();

		world.setTarget(new Target(world.getAssetManager(), sTLVec.getX()-20,sTLVec.getY()+10, sTLVec.getZ()+30));

		world.getBulletAppState().getPhysicsSpace().setGravity(new Vector3f(0, -40f, 0));
		world.setUpLight();
	}

	@Override
	protected void onDisable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onEnable() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * This method is continuously called by the appstate and is used to
	 * continuously check conditions in the game whenever they might change.
	 * Adds the target to the world when the player reaches the final block.
	 * 
	 * @param tpf The the time per frame between the calls of the method.
	 */
	@Override   
	public void update(float tpf) {       
		//TODO: implement behavior during runtime
		if(world.getFinalReached()&&!world.getPlatformNodes().getChildren().contains(world.getTarget().getSpatial())) {
			world.getPlatformNodes().getChildren().add(world.getTarget().getSpatial());
			world.getRootNode().getChildren().add(world.getTarget().getNode());
			world.getBulletAppState().getPhysicsSpace().add(world.getTarget().getRB());
		}
	}

	/**
	 * Builds the bolcks for the specific level given by the file path.
	 * The block locations are given by the file and read by this method.
	 * The first block is the starting position for the player and the final block
	 * is the winning location.
	 * 
	 * @param path The file path with locations to load blocks from.
	 */
	private void buildBlocks(String path) {
		try {
			int s=0;
			File in=new File(path);
			Scanner read=new Scanner(in);
			while(read.hasNext()) {
				String line=read.nextLine();
				int i=line.indexOf(" ");
				float x=(float) Double.parseDouble(line.substring(0,i));
				float y=(float) Double.parseDouble(line.substring(i+1,line.indexOf(" ", i+1)));
				i=line.indexOf(" ", i+1);
				float z=(float) Double.parseDouble(line.substring(i+1));
				if(read.hasNext()) {
					world.getAssetManager().registerLocator("assets/Platform", FileLocator.class);
					Spatial cube = world.getAssetManager().loadModel("platform.scene");
					cube.setLocalTranslation(x,y,z);
					cube.scale(5);

					CollisionShape shape =
							CollisionShapeFactory.createMeshShape(cube);
					RigidBodyControl cubeRB = new RigidBodyControl(shape, 0);
					cube.addControl(cubeRB);
					cube.setName("normal");
					world.getCubeLocations().add(cube);
					world.getBulletAppState().getPhysicsSpace().add(cubeRB);
					world.getPlatformNodes().attachChild(cube);
				}
				else {
					world.getAssetManager().registerLocator("assets/Platform", FileLocator.class);
					world.setWinBlock(world.getAssetManager().loadModel("platform.scene"));
					Material mat1 = new Material(world.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
					mat1.setColor("Color", ColorRGBA.Blue);
					world.getWinBlock().setMaterial(mat1);
					world.getWinBlock().setLocalTranslation(x,y,z);
					world.getWinBlock().scale(5);
					world.getWinBlock().setName("final");
					CollisionShape shape =
							CollisionShapeFactory.createMeshShape(world.getWinBlock());
					RigidBodyControl cubeRB = new RigidBodyControl(shape, 0);
					world.getWinBlock().addControl(cubeRB);
					world.getCubeLocations().add(world.getWinBlock());
					world.getBulletAppState().getPhysicsSpace().add(cubeRB);
					world.getPlatformNodes().attachChild(world.getWinBlock());
					world.getPlayerController().setViewDirection(world.getWinBlock().getLocalTranslation());
				}
				if(s==0) {
					s++;
					world.getStartLocation().set(x, y+10, z);
					world.setCheckPoint(new Vector3f(x,y+10,z));
				}


			}

			Spatial secondToLast  = world.getCubeLocations().get(8);
			Vector3f sTLVec = secondToLast.getLocalTranslation();

			world.speedPot = new SpeedPotion(world.getAssetManager(), sTLVec.getX()+5,sTLVec.getY()+5,sTLVec.getZ());
			world.getRootNode().attachChild(world.speedPot.getNode());
			world.getBulletAppState().getPhysicsSpace().add(world.speedPot.getRB());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
