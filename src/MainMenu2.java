import java.awt.event.InputMethodEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.event.HyperlinkEvent.EventType;

import com.jme3.system.AppSettings;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * The MainMenu2 class extends the JavaFX Application Class, and is used for the Main Menu launcher for the game. It serves
 * as the basic stepping stone for the user to start any level they want (essentially like a control panel).
 */
public class MainMenu2 extends Application {

	private World world=new World();
	
	private BorderPane root;
	private Scene scene;
	private VBox account;
	private TabPane tabPane;
	public static Stage menuStage;
	private ImageView play;
	private ChoiceBox cb;

	private String levelName = "";

	private HBox menuOpen;
	private VBox menu;
	private VBox menuItems;

	private GridPane grid;
	private ImageView[] builds = new ImageView[8];

	private Polygon about;
	private Polygon howtoplay;
	private Polygon playButton;
	private Polygon quit;

	private BorderPane aboutBPane;
	private BorderPane buildBPane;
	private BorderPane howBPane;
	private BorderPane playBPane;

	private FadeTransition levelsTransition;
	private ImageView image1;

	private VBox buttons;

	private StackPane playPane;
	private StackPane aboutPane;
	private StackPane howPane;
	private StackPane quitPane;
	boolean firstPlay = true;

	final double multiplier = 1.4;
	private 	KeyboardListener keys = new KeyboardListener();

	private int currIndex = 1;
	public MainMenu2() {
	}
	//	public static void main(String[] args) {
	//		launch();
	//
	//	}
	@Override
	public void start(Stage stage) throws Exception {
		openNewStage(stage);
		//		AudioClip clip = new AudioClip("file:assets/Menu/titletheme.mp3");
		//		//clip.play();
		//		stage.setTitle("Parkour Master Launcher");
		//		stage.setResizable(false);
		//		stage.sizeToScene();
		//		BorderPane root = new BorderPane();
		//		root.setBackground(new Background(new BackgroundFill[] {new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)}));	
		//		Scene scene = new Scene(root, 600, 600);	
		//		stage.setScene(scene);
		//
		//		//ParallelTransition load1 = new ParallelTransition();
		//		FadeTransition fadeInTitle = new FadeTransition();
		//		ImageView title = new ImageView(new Image("file:assets/Menu/title.png"));
		//		root.setCenter(title);
		//		fadeInTitle.setDuration(Duration.seconds(5));
		//		fadeInTitle.setNode(title);
		//		fadeInTitle.setFromValue(0);
		//		fadeInTitle.setToValue(1);
		//
		//
		//		Rectangle rect = new Rectangle();
		//		rect.setWidth(200);
		//		rect.setHeight(50);
		//		rect.setX(50);
		//		rect.setY(500);
		//		rect.setFill(Color.LIGHTGREEN);
		//		TranslateTransition ft = new TranslateTransition(Duration.seconds(3));
		//		ft.setNode(rect);
		//		ft.setByX(150);
		//		ft.setCycleCount(1);
		//		root.getChildren().add(rect);
		//
		//		Rectangle rectWhite = new Rectangle();
		//		rectWhite.setFill(Color.LIGHTBLUE);
		//		rectWhite.setWidth(200);
		//		rectWhite.setHeight(50);
		//		rectWhite.setX(50);
		//		rectWhite.setY(500);
		//		root.getChildren().add(rectWhite);
		//		ft.setOnFinished(new EventHandler<ActionEvent>() {
		//
		//			@Override
		//			public void handle(ActionEvent event) {
		//				// TODO Auto-generated method stub
		//				Text text = new Text("Loaded!");
		//
		//			}
		//
		//		});
		//		//		FadeTransition loading = new FadeTransition();
		//		//		ImageView load = new ImageView(new Image("file:assets/loading.gif"));
		//		//		root.setBottom(load);
		//		//		loading.setDuration(Duration.seconds(5));
		//		//		loading.setNode(load);
		//		//		loading.setFromValue(0);
		//		//		loading.setToValue(1);
		//
		//		PauseTransition pause = new PauseTransition();
		//		pause.setOnFinished(new EventHandler<ActionEvent>() {
		//
		//			@Override
		//			public void handle(ActionEvent event) {
		//				openNewStage(stage);				
		//			}
		//
		//		});
		//		pause.setDuration(Duration.seconds(5));
		//		stage.show();
		//		fadeInTitle.play();
		//		ft.play();
		//
		//		pause.play();
	}
	/**
	 * Opens the Stage (in the case that the developer wishes to include the beginning animation). If not, it simply acts as the start() method.
	 * In this method, all components of the stage are loaded and displayed for the user to see in the MainMenu. Listeners and Handlers are defined
	 * outside of this method for the most part. All components of the stage are inside of a BorderPane, with inner BorderPanes, GridPanes, VBoxes, HBoxes, and
	 * more being used for displaying inner components.
	 *
	 * @param old reference to the old Stage (if there was an opening animation).
	 */
	private void openNewStage(Stage old) {

		old.close();
		Stage stage = new Stage();
		menuStage = stage;
		root = new BorderPane();
		scene = new Scene(root, 800, 600);
		ImageView logo = new ImageView(new Image("file:assets/Menu/gameLogo.png"));
		logo.setX(root.getWidth()/2 - logo.getImage().getWidth()/2);
		logo.setY(root.getHeight()/2);
		root.getChildren().add(logo);
		TranslateTransition tt = new TranslateTransition();
		tt.setNode(logo);
		tt.setByY(-(root.getHeight()/2) + 20*multiplier);
		tt.setDuration(Duration.seconds(5));

		BorderPane.setAlignment(logo, Pos.CENTER);

		Text copyRight = new Text("Copyright 2018");
		copyRight.setFill(Color.WHITE);
		root.setBottom(copyRight);
		BorderPane.setAlignment(copyRight, Pos.CENTER);
		//root.setAlignment(logo, Pos.CENTER);

		URL t = this.getClass().getResource("file:src/style.css");
		//(t);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		root.setBackground(new Background(new BackgroundFill[] {new BackgroundFill(Color.rgb(147,165,139), CornerRadii.EMPTY, Insets.EMPTY)}));	
		stage.setTitle("Parkour Master");
		stage.setResizable(false);
		stage.sizeToScene();

		aboutBPane = new BorderPane();
		//initAbout(aboutBPane);
		howBPane = new BorderPane();

		playBPane = new BorderPane();
		double vd = 40*multiplier;
		buttons = new VBox();

		about = new Polygon();
		about.setFill(Color.WHITE);
		about.getPoints().addAll(0.0,100.0,200.0,100.0,175.0,120.0,0.0,120.0);
		buttons.getChildren().add(about);
		Text aboutText = new Text("About");
		aboutPane = new StackPane();
		aboutPane.getChildren().add(about);
		aboutPane.getChildren().add(aboutText);
		aboutPane.setLayoutX(100);
		aboutPane.setLayoutY(100);

		howtoplay = new Polygon();
		howtoplay.setFill(Color.WHITE);
		howtoplay.getPoints().addAll(0.0,100.0+vd,200.0,100.0+vd,175.0,120.0+vd,0.0,120.0+vd);
		Text howtoplayText = new Text("How To Play");
		howPane = new StackPane();
		howPane.getChildren().add(howtoplay);
		howPane.getChildren().add(howtoplayText);
		howPane.setLayoutX(100);
		howPane.setLayoutY(100+vd);

		quit = new Polygon();
		quit.setFill(Color.WHITE);
		quit.getPoints().addAll(0.0,100.0+2*vd,200.0,100.0+2*vd,175.0,120.0+2*vd,0.0,120.0+2*vd);
		Text quitText = new Text("Quit");
		quitText.setX(quit.getLayoutX() + 50);
		quitText.setY(quit.getLayoutY() + 50);
		quitPane = new StackPane();
		quitPane.getChildren().add(quit);
		quitPane.getChildren().add(quitText);
		quitPane.setLayoutX(100);
		quitPane.setLayoutY(100+2*vd);

		playButton = new Polygon();
		playButton.setFill(Color.WHITE);
		playButton.getPoints().addAll(0.0,100.0+3*vd,200.0,100.0+3*vd,175.0,120.0+3*vd,0.0,120.0+3*vd);
		Text playText = new Text("Back");
		playText.setX(playButton.getLayoutX() + 50);
		playText.setY(playButton.getLayoutY() + 50);
		playPane = new StackPane();
		playPane.getChildren().add(playButton);
		playPane.getChildren().add(playText);
		playPane.setLayoutX(100);
		playPane.setLayoutY(100+3*vd);


		buttons.getChildren().add(aboutPane);
		aboutPane.setPadding(new Insets(10*multiplier,0,10*multiplier,0));
		buttons.getChildren().add(howPane);
		howPane.setPadding(new Insets(10*multiplier,0,10*multiplier,0));
		buttons.getChildren().add(quitPane);
		quitPane.setPadding(new Insets(10*multiplier,0,10*multiplier,0));

		//root.setLeft(buttons);
		ButtonHandler bh = new ButtonHandler();

		aboutPane.setOnMouseMoved(bh);
		aboutPane.setOnMouseClicked(bh);
		aboutPane.setOnMouseExited(bh);
		howPane.setOnMouseMoved(bh);
		howPane.setOnMouseClicked(bh);
		howPane.setOnMouseExited(bh);
		playPane.setOnMouseMoved(bh);
		playPane.setOnMouseClicked(bh);
		playPane.setOnMouseExited(bh);
		quitPane.setOnMouseMoved(bh);
		quitPane.setOnMouseClicked(bh);
		quitPane.setOnMouseExited(bh);
		root.setLeft(buttons);
		buttons.setOpacity(0);
		FadeTransition buttonsTransition = new FadeTransition();
		buttonsTransition.setNode(buttons);
		buttonsTransition.setFromValue(0);
		buttonsTransition.setToValue(1);
		buttonsTransition.setDuration(Duration.seconds(5));
		PauseTransition pause = new PauseTransition();
		pause.setDuration(Duration.seconds(1));


		stage.setScene(scene);
		stage.show();
		tt.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				initPlay(playBPane);
				root.setCenter(playBPane);
				//("Hello");
				//ParallelTransition parallel = new ParallelTransition(levelsTransition, buttonsTransition);
				//parallel.play();
				//buttons.setVisible(true);
				buttonsTransition.play();
				levelsTransition.play();
			}

		});
		tt.play();
		//pause.play();
	}
	/**
	 * KeyboardListener for detecting user's key movements and displaying them on the main menu. Allows the user to select a level with left arrow, right arrow, and enter.
	 */
	class KeyboardListener implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
			//("Came in here");
			//(event.getCode().toString() + "<" + KeyCode.ENTER);
			if(event.getCode().toString().equals(KeyCode.ENTER.toString())){
				FadeTransition ft = new FadeTransition();
				ft.setNode(root);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setDuration(Duration.seconds(3));
				ft.play();
				//("Enter");
				ft.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						World world = new World();
						world.startGame("Level " + currIndex);
						//world.startGame("Target");
						root.setOpacity(1);
					}

				});


			} else if(event.getCode().toString().equals(KeyCode.RIGHT.toString())) {
				//("Right");
				builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));
				//("The current index is: " + currIndex);
				if(currIndex==8) currIndex = 1;
				else currIndex++;
				//("The new index is: " + currIndex);
				builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

			} else if(event.getCode().toString().equals(KeyCode.LEFT.toString())) {
				//("Left");

				builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

				//(currIndex);

				if(currIndex==1) currIndex = 8;
				else currIndex--;
				//(currIndex);

				builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

			}
		}

	}
	
	/**
	 * Constructs the About BorderPane and initializes the display inside.
	 *
	 * @param about the BorderPane for the about page
	 */
	private void initAbout(BorderPane about) {
		playBPane.removeEventHandler(KeyEvent.KEY_PRESSED, keys);
		//about.setId("about");
		VBox rightBox = new VBox();
		about.setRight(rightBox);
		rightBox.setMinWidth(225/multiplier);

		Text text = new Text();
		text.setFill(Color.WHITE);
		//text.setStyle("-fx-text-inner-color: w;");
		text.setWrappingWidth(350*multiplier);
		text.setText("PK Master is a 3D Parkour Adventure Game developed by Jacob Zhang,"
				+ " Roshan Regula, and Anjan Bharadwaj (Period 4 Ferrante). The game"
				+ " utilizes the JMonkeyEngine3 framework/library to render 3D objects"
				+ " in the game, although the core game engine is built using the same"
				+ " template that we learned in class through Minesweeper (Model-View-Controller)"
				+ " and the GameEngine Demo (World-Actor). The game is a parkour game in first person perspective. You"
				+ "must parkour your way to the top blue block, and can drink powerups on the way to improve your chances."
				+ "Once you reach the top, shoot at the red target to unlock the next level! You can create your"
				+ "own levels if you wish with the custom level builder!");
		text.setStyle("-fx-font: 18 arial;");		
		about.setCenter(text);
		about.setAlignment(text, Pos.CENTER);
	}
	/**
	 * Constructs the Play BorderPane and initializes the display inside.
	 *
	 * @param play the BorderPane for the play (level select) page
	 */
	private void initPlay(BorderPane play) {
		play.setFocusTraversable(true);
		play.requestFocus();
		//("HEH" + play.isFocused());
		play.setOnKeyPressed(keys);
		////("initbuild");
		//about.setId("about");
		VBox rightBox = new VBox();
		play.setRight(rightBox);
		rightBox.setMinWidth(225/multiplier);

		VBox box = new VBox(); 
		if(firstPlay) {
			box.setOpacity(0);
			firstPlay = false;
		}
		levelsTransition = new FadeTransition();
		levelsTransition.setNode(box);
		levelsTransition.setFromValue(0);
		levelsTransition.setToValue(1);
		levelsTransition.setDuration(Duration.seconds(3));
		Label label = new Label("Select a Level");
		label.setStyle("-fx-font: 18 arial;");	
		label.setTextFill(Color.WHITE);
		label.setLayoutX(200*multiplier);
		label.setLayoutY(0);
		box.getChildren().add(label);
		box.setAlignment(Pos.CENTER);
		play.setAlignment(box, Pos.CENTER);
		ImageView stickMan = new ImageView(new Image("file:assets/Menu/stickParkour.png"));
		stickMan.setPreserveRatio(true);
		stickMan.setFitWidth(50*multiplier);
		box.getChildren().add(stickMan);
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(0,0,0,0));
		for(int i = 1; i<9; i++) {
			//("I: " + i);
			String x;
			if(i==1) {
				x = "file:assets/Menu/Levels/" + i + "s.png";
			}
			else {
				x = "file:assets/Menu/Levels/" + i + ".png";
			}
			ImageView add1 = new ImageView(new Image(x));
			add1.setFitWidth(50*multiplier);
			add1.setFitHeight(50*multiplier);
			add1.setPreserveRatio(true);
			grid.getChildren().add(add1);
			grid.setRowIndex(add1, (i-1)/4);
			grid.setColumnIndex(add1, (i-1)%4);
			builds[i-1] = add1;
			GridPane.setMargin(add1, new Insets(10*multiplier, 10*multiplier, 10*multiplier, 10*multiplier));

			if(i==1) {
				image1 = add1;
			}
		}
		box.getChildren().add(grid);
		play.setCenter(box);
		GridHandler gh = new GridHandler();
		grid.setOnMouseClicked(gh);
		grid.setOnMouseMoved(gh);
		grid.alignmentProperty().set(Pos.CENTER);

	}
	/**
	 * Constructs the How BorderPane and initializes the display inside.
	 *
	 * @param how the BorderPane for the how page
	 */
	private void initHow(BorderPane how) {
		VBox rightBox = new VBox();
		how.setRight(rightBox);
		rightBox.setMinWidth(225/multiplier);

		ListView controls = new ListView();
		controls.getItems().addAll("W: Forward", "D: Right", "S: Backward", "A: Left", "Spacebar: Jump", 
				"Shift: Toggle Cheat Mode",
				"Right-Click: Use item",
				"Left-Click: Shoot Ball",
				"Mouse / arrow keys : look",
				"CHEAT: E: Rise",
				"CHEAT: Q: Descend",
				"CHEAT: Space: Add block",
				"CHEAT: Tab: Save Build", 
				"R: Restart", "B: Back Up");
		//text.setStyle("-fx-text-inner-color: w;");
		how.setCenter(controls);
		controls.setMaxWidth(250*multiplier);
		controls.setMaxHeight(200*multiplier);
		how.setAlignment(controls, Pos.CENTER);

	}
	/**
	 * GridHandler for detecting user's mouse movements and displaying them on the main menu. Allows the user to select a level with by clicking around and hovering.
	 */
	class GridHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			double x = event.getX();
			double y = event.getY();
			double gridx = image1.getLayoutX();
			double gridy = image1.getLayoutY();
			if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
				//("HI");
				playBPane.removeEventHandler(KeyEvent.KEY_PRESSED, keys);
				playBPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						// TODO Auto-generated method stub
						
					}
					
				});
				FadeTransition ft = new FadeTransition();
				ft.setNode(root);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setDuration(Duration.seconds(3));
				//(x + "--" + gridx + "," + y + "--" + gridy);
				if(x>gridx && x<gridx+50*multiplier && y>gridy && y<gridy+50*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));
					currIndex = 1;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));
					//("level1");
					ft.play();
					
					ft.setOnFinished(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							world.startGame("Level 1");
							root.setOpacity(1);
							playBPane.setOnKeyPressed(keys);

						}

					});
				}
				else if(x>gridx+70*multiplier && x<gridx+120*multiplier && y>gridy && y<gridy+50*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

					currIndex = 2;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

					//("level2");
					ft.play();
					ft.setOnFinished(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							world.startGame("Level 2");
							root.setOpacity(1);
							playBPane.setOnKeyPressed(keys);

						}

					});			}
				else if(x>gridx+140*multiplier && x<gridx+190*multiplier && y>gridy && y<gridy+50*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

					currIndex = 3;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

					//("level3");
					ft.play();
					ft.setOnFinished(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							world.startGame("Level 3");
							root.setOpacity(1);
							playBPane.setOnKeyPressed(keys);

						}

					});			}
				else if(x>gridx+210*multiplier && x<gridx+260*multiplier && y>gridy && y<gridy+50*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

					currIndex = 4;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

					//("Level 4");
					if(world.getSlot()>=currIndex) {
						ft.play();
						ft.setOnFinished(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub

								world.startGame("Level 4");
								root.setOpacity(1);
								playBPane.setOnKeyPressed(keys);
							}
						});
					}
					else {
						createCustomLevelBuilder();
					}
				}
				else if(x>gridx && x<gridx+50*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

					currIndex = 5;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

					//("level5");
					if(world.getSlot()>=currIndex) {
						ft.play();
						ft.setOnFinished(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub

								world.startGame("Level 5");
								root.setOpacity(1);
								playBPane.setOnKeyPressed(keys);
							}
						});
					}
					else {
						createCustomLevelBuilder();
					}
				}
				else if(x>gridx+70*multiplier && x<gridx+120*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

					currIndex = 6;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

					//("level6");
					if(world.getSlot()>=currIndex) {
						ft.play();
						ft.setOnFinished(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub

								world.startGame("Level 6");
								root.setOpacity(1);
								playBPane.setOnKeyPressed(keys);
							}
						});
					}
					else {
						createCustomLevelBuilder();
					}
				}
				else if(x>gridx+140*multiplier && x<gridx+190*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

					currIndex = 7;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

					//("level7");
					if(world.getSlot()>=currIndex) {
						ft.play();
						ft.setOnFinished(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub

								world.startGame("Level 7");
								root.setOpacity(1);
								playBPane.setOnKeyPressed(keys);
							}
						});
					}
					else {
						createCustomLevelBuilder();
					}
				}
				else if(x>gridx+210*multiplier && x<gridx+260*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + ".png"));

					currIndex = 8;
					builds[currIndex-1].setImage(new Image("file:assets/Menu/Levels/" + currIndex + "s.png"));

					//("level8");
					if(world.getSlot()>=currIndex) {
						ft.play();
						ft.setOnFinished(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub

								world.startGame("Level 8");
								root.setOpacity(1);
								playBPane.setOnKeyPressed(keys);
							}
						});
					}
					else {
						createCustomLevelBuilder();
					}
				}
			} 
			
			else if(event.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
				if(x>gridx && x<gridx+50*multiplier && y>gridy && y<gridy+50*multiplier) {
					builds[0].setImage(new Image("file:assets/Menu/Levels/" + 1 + "s.png"));

				}
				else if(x>gridx+70*multiplier && x<gridx+120*multiplier && y>gridy && y<gridy+50*multiplier) {
					builds[1].setImage(new Image("file:assets/Menu/Levels/" + 2 + "s.png"));

					//("level2");
				}
				else if(x>gridx+140*multiplier && x<gridx+190*multiplier && y>gridy && y<gridy+50*multiplier) {
					
					builds[2].setImage(new Image("file:assets/Menu/Levels/" + 3 + "s.png"));

					//("level3");
				}
				else if(x>gridx+210*multiplier && x<gridx+260*multiplier && y>gridy && y<gridy+50*multiplier) {
					
					builds[3].setImage(new Image("file:assets/Menu/Levels/" + 4 + "s.png"));

					//("Level 4");
				}
				else if(x>gridx && x<gridx+50*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
					
					builds[4].setImage(new Image("file:assets/Menu/Levels/" + 5 + "s.png"));

					//("level5");
					//ft.play();
					//world.startGame("Level1");
				}
				else if(x>gridx+70*multiplier && x<gridx+120*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
			
					builds[5].setImage(new Image("file:assets/Menu/Levels/" + 6 + "s.png"));

					//("level6");
					//ft.play();
					//world.startGame("Level2");
				}
				else if(x>gridx+140*multiplier && x<gridx+190*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
			
					builds[6].setImage(new Image("file:assets/Menu/Levels/" + 7 + "s.png"));

					//("level7");
					//ft.play();
					//world.startGame("Level3");
				}
				else if(x>gridx+210*multiplier && x<gridx+260*multiplier && y>gridy+70*multiplier && y<gridy+120*multiplier) {
	
					builds[7].setImage(new Image("file:assets/Menu/Levels/" + 8 + "s.png"));

					//("level8");
					//ft.play();
				} else {
					for(int i = 0; i <builds.length; i++) {
						builds[i].setImage(new Image("file:assets/Menu/Levels/" + (i+1) + ".png"));
					}
				}
			}
			
		}
		/**
		 * Creates the Custom Level Builder, where players can build their own levels on the Windmill scene.
		 *
		 * @see Level
		 */
		public void createCustomLevelBuilder() {
			Stage popup=new Stage();
			popup.setHeight(menuStage.getHeight()/2);
			popup.setWidth(menuStage.getWidth()/2);
			HBox base=new HBox();
			Scene root=new Scene(base);
			ImageView img=new ImageView(new Image("file:assets/Menu/windmill.png"));
			img.setFitHeight(popup.getWidth()/2);
			img.setFitWidth(popup.getWidth()/2);
			
			VBox options=new VBox();
			ChoiceBox settings= new ChoiceBox(FXCollections.observableArrayList(
					"Windmill", "Forest Clearing", "City")
					);
			settings.setValue("Windmill");
			settings.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(settings.getValue().toString().equals("Windmill")) {
						img.setImage(new Image("file:assets/Menu/windmill.png"));
						img.setFitHeight(popup.getWidth()/2);
						img.setFitWidth(popup.getWidth()/2);
					}
					else if(settings.getValue().toString().equals("Forest Clearing")) {
						img.setImage(new Image("file:assets/Menu/forest.png"));
						img.setFitHeight(popup.getWidth()/2);
						img.setFitWidth(popup.getWidth()/2);
					}
					else if(settings.getValue().toString().equals("City")) {
						img.setImage(new Image("file:assets/Menu/city.png"));
						img.setFitHeight(popup.getWidth()/2);
						img.setFitWidth(popup.getWidth()/2);
					}
				}
				
			});;
			HBox buttons=new HBox();
			Button cancel=new Button("Cancel");
			Button build=new Button("Build");
			buttons.getChildren().addAll(cancel, build);
			cancel.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					popup.close();
				}
				
			});
			build.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					for(int i=world.getLevelList().size()-1;i>=0;i--) {
						if(world.getLevelList().get(i)==null)
							world.setSlot(i);
					}
					world.setSlot(world.getLevelList().size());
					if(settings.getValue().toString().equals("Windmill"))
						world.startGame("Build 1");
					if(settings.getValue().toString().equals("Forest Clearing"))
						world.startGame("Build 2");
					if(settings.getValue().toString().equals("City"))
						world.startGame("Build 3");
					popup.close();
				}
				
			});	
			buttons.setAlignment(Pos.CENTER);
			buttons.setSpacing(5.0);
			options.setAlignment(Pos.CENTER);
			options.getChildren().addAll(settings, buttons);
			options.setSpacing(5.0);
			
			
			VBox image =new VBox();
			image.getChildren().add(img);
			image.setAlignment(Pos.CENTER);
			
			base.getChildren().addAll(image, options);
			base.setAlignment(Pos.CENTER);
			base.setSpacing(5.0);
			base.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, new CornerRadii(0), new Insets(0))));
			popup.setScene(root);
			popup.show();
			
		}
	}
	/**
	 * ButtonHandler for detecting user's mouse movements and displaying them on the main menu. Allows the user to click the parallelogram buttons.
	 */
	class ButtonHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			//make noise
			//(event.getSource());
			StackPane pane = (StackPane) event.getSource();
			Polygon polygon = (Polygon) (pane.getChildren().get(0));
			if(event.getEventType().equals(MouseEvent.MOUSE_MOVED)) {

				polygon.setFill(Color.LIGHTGREY);
//				Color c = (Color) polygon.getFill();
//				if(c.equals(Color.WHITE)) {
//					polygon.setFill(Color.LIGHTGREY);
//				} else {
//					polygon.setFill(Color.WHITE);
//
//				}
			} else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {

				polygon.setFill(Color.WHITE);
//				Color c = (Color) polygon.getFill();
//				if(c.equals(Color.WHITE)) {
//					polygon.setFill(Color.LIGHTGREY);
//				} else {
//					polygon.setFill(Color.WHITE);
//
//				}
			} else if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
				if(!buttons.getChildren().contains(playPane)) {
					buttons.getChildren().add(playPane);
					playPane.setPadding(new Insets(10*multiplier,0,10*multiplier,0));
				}
				if(pane.equals(aboutPane)) {
					initAbout(aboutBPane);
					root.setCenter(aboutBPane);
				}
				if(pane.equals(howPane)) {
					initHow(howBPane);
					root.setCenter(howBPane);

				}
				if(pane.equals(quitPane)) {
					menuStage.close();
				}

				if(pane.equals(playPane)) {
					//("Play?");
					initPlay(playBPane);
					root.setCenter(playBPane);
					//levelsTransition.play();
				}

			}

		}
		
		

	}

}