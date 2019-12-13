package picrom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import picrom.gameboard.TooManyCastlesException;
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

			// loading textures:
			new Drawables();
			scene.setFill(Color.BLACK);

			// Create world:
			World gameboard = new World(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT, scene);

			gameboard.generateOwners(Settings.NUMBER_OF_AIS, Settings.NUMBER_OF_BARONS);

			try {
				gameboard.generateWorldCastles();
			} catch (TooManyCastlesException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Trop de châteaux !");
				alert.setHeaderText(
						"La génération des châteaux est trop longue, certains châteaux n'ont peut-être pas été placés sur le plateau.");
				alert.setContentText(
						"Essayez de réduire le nombre de chateaux ou l'espacement entre eux, ou augmentez la taille du plateau de jeu.");
				alert.showAndWait();
			}

			// setup GUI:
			root.getChildren().add(gameboard);

			Timeline gameLoop = new Timeline();
			gameLoop.setCycleCount(Timeline.INDEFINITE);
			gameLoop.getKeyFrames().add(new KeyFrame(Settings.TURN_DURATION, new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					// TODO test if pause disabled

					// update world:
					gameboard.processCastles();
					gameboard.processUnits();

					// TODO Test end of game
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
