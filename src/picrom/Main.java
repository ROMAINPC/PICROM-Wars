package picrom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import picrom.gameboard.World;
import picrom.settings.Drawables;
import picrom.settings.Settings;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			Group root = new Group();
			Scene scene = new Scene(root, Settings.DEFAULT_SCENE_WIDTH, Settings.DEFAULT_SCENE_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//loading textures:
			new Drawables();
			
			// Create world:
			World gameboard = new World(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT, scene);
			gameboard.generateWorldCastles(Settings.NUMBER_OF_AIS, Settings.NUMBER_OF_BARONS);

			// setup GUI:
			root.getChildren().add(gameboard);

			Timeline gameLoop = new Timeline();
			gameLoop.setCycleCount(Timeline.INDEFINITE);
			gameLoop.getKeyFrames().add(new KeyFrame(Settings.TURN_DURATION, new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					//TODO test if pause disabled

					// update world:
					gameboard.processCastles();
					gameboard.processUnits();

					//TODO Test end of game
				}
			}));

			// start loop
			gameLoop.play();

			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
