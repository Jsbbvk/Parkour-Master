import java.io.File;
import java.util.ArrayList;
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
 * The Custom_Level_AppState is called when the player makes their own levels or wants to play them.
 *
 */
public class Custom_Level_AppState extends BaseAppState{
	private World world;
	private boolean mode;
	private int type=1;
	private int slot;
	
	@Override
	/**
	 * Method that is call when this appstate is detached from an application's state manager.
	 * Acts to remove all elements built by the appstate.
	 * 
	 * @param arg0 The application that is detaching this appstate
	 * 
	 * 
	 */
	protected void cleanup(Application arg0) {
		// TODO Auto-generated method stub
		world.getRootNode().removeLight(world.getLight());
		world.getRootNode().removeLight(world.getDirectLight());
		if(mode) {
			world.getRootNode().getChildren().remove(world.getLevel().getWorld());
			world.getBulletAppState().getPhysicsSpace().remove(world.getLevel().getRB());
			world.getCubeLocations().clear();
			world.getPlatformNodes().getChildren().clear();
			world.stop();
		}
		else {
			world.getRootNode().getChildren().remove(world.getLevel().getWorld());
			world.getRootNode().getChildren().remove(world.getWinBlock());
			world.getTarget().getNode().detachAllChildren();
			world.getRootNode().detachChild(world.getTarget().getNode());
			world.getPlatformNodes().getChildren().remove(world.getTarget().getSpatial());
			world.getBulletAppState().getPhysicsSpace().remove(world.getTarget().getRB());
			world.getPlatformNodes().getChildren().clear();
			world.getPlatformNodes().getChildren().remove(world.getWinBlock());
			world.getBulletAppState().getPhysicsSpace().getRigidBodyList().clear();
			world.setFinalReached(false);
			if(world.getLevel() instanceof Level1)
				world.getLevelList().set(slot-1, new Level1(world,world.getAssetManager()));
			else if(world.getLevel() instanceof Level2)
				world.getLevelList().set(slot-1, new Level2(world,world.getAssetManager()));
			else if(world.getLevel() instanceof Level3)
				world.getLevelList().set(slot-1, new Level3(world,world.getAssetManager()));
		}
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
		world=(World) app;
		Level level;
		if(mode) {
			if(type==1)
				level=new Level1(world,world.getAssetManager());
			else if(type==2)
				level=new Level2(world,world.getAssetManager());
			else
				level=new Level3(world,world.getAssetManager());
			world.setLevel(level);
			world.getRootNode().attachChild(level.getWorld());
			world.getBulletAppState().getPhysicsSpace().add(level.getRB());
		}
		else {
			level=world.getLevelList().get(slot-1);
			world.setLevel(level);
			world.getRootNode().attachChild(level.getWorld());
			world.getBulletAppState().getPhysicsSpace().add(level.getRB());
			buildBlocks();
			world.getPlayerController().setPhysicsLocation(world.getStartLocation());
			Vector3f sTLVec = world.getWinBlock().getLocalTranslation();

			world.setTarget(new Target(world.getAssetManager(), sTLVec.getX()-20,sTLVec.getY()+10, sTLVec.getZ()+30));

			world.getBulletAppState().getPhysicsSpace().setGravity(new Vector3f(0, -40f, 0));
		}
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
	 * Builds the bolcks for the specific level given by the variable slot.
	 * The block locations are given by the cubeLocos arraylist from the world.
	 * The forst block is the starting position for the player and the final block
	 * is the winning location.
	 */
	private void buildBlocks(){
			int d=0;
			ArrayList<String> s=world.getCubeLocos().get(slot-4);
			for(int j=0;j<s.size();j++) {
				String line=s.get(j);
				int i=line.indexOf(" ");
				float x=(float) Double.parseDouble(line.substring(0,i));
				float y=(float) Double.parseDouble(line.substring(i+1,line.indexOf(" ", i+1)));
				i=line.indexOf(" ", i+1);
				float z=(float) Double.parseDouble(line.substring(i+1));
				if(j!=s.size()-1) {
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
				if(d==0) {
					d++;
					world.getStartLocation().set(x, y+10, z);
					world.setCheckPoint(new Vector3f(x,y+10,z));
				}


			}
		} 

	/**
	 * Set the state of the variable mode. True means the appstate
	 * is being used to build the new level. False means the appstate
	 * is an actual level that is being loaded and played.
	 * @param b The boolean value to set mode to.
	 */
	public void setMode(boolean b) {
		mode=b;
	}

	/**
	 * Sets the value of the variable type. 1 means the map of the level is the
	 * Windmill. 2 is the Forest Clearing. 3 is the City.
	 * @param i The value to set type to.
	 */
	public void setType(int i) {
		type=i;
	}
	
	/**
	 * Sets the value of the slot variable. The slot is the position in the cubeLocos 
	 * and levelList that is being used in this instance.
	 * @param i The value to set slot to.
	 */
	public void setSlot(int i) {
		slot=i;
	}
}
