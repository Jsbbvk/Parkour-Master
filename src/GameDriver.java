import com.jme3.system.AppSettings;

import javafx.application.Application;
/**
 * To run the game, simply run this file.
 *
 */
public class GameDriver {

	public static void main(String[] rags) {
		System.out.println("Starting your game!");
		//startGame("Level 1");
		Application.launch(MainMenu2.class);
	}
	/**
	 * Utility method for running a specific level (in development mode).
	 *
	 * @param a the String representing the level
	 */
	public static void startGame(String a) {
		//MainMenu.menuStage.hide();
		World w = new World();
		w.startGame(a);
		
	}
}
