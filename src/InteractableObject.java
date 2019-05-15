
public abstract class InteractableObject extends WorldObject{
	

	public InteractableObject(World w) {
		super(w);
	}

	public abstract void onPlayerInteract(Player p);
}