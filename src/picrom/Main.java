package picrom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import picrom.entity.Owner;
import picrom.gameboard.Context;
import picrom.gameboard.TooManyCastlesException;
import picrom.gameboard.World;
import picrom.utils.Drawables;
import picrom.utils.Settings;

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
			SimpleDoubleProperty vSeparator = new SimpleDoubleProperty();
			vSeparator.bind(scene.widthProperty().multiply(Settings.WORLD_WIDTH_RATIO));
			World gameboard = new World(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT, new SimpleDoubleProperty(0),
					new SimpleDoubleProperty(0), vSeparator, scene.heightProperty());

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
			Context infos = new Context();
			infos.xProperty().bind(gameboard.xProperty().add(gameboard.widthProperty()));
			infos.yProperty().set(0);
			infos.heightProperty().bind(scene.heightProperty());
			infos.widthProperty().bind(scene.widthProperty().multiply(1 - Settings.WORLD_WIDTH_RATIO));
			ImageView infosBackground = new ImageView(Drawables.infosBackground);
			infos.bindIn(infosBackground, 0, 0, 1, 1);
			infos.getChildren().add(infosBackground);

			ScrollPane owners = new ScrollPane();
			owners.setId("owners_list");
			infos.bindIn(owners, 0.15, 0.05, 0.8, 0.4);
			owners.prefHeightProperty().bind(infos.heightProperty().divide(2));
			VBox ownersBox = new VBox();
			ownersBox.setAlignment(Pos.CENTER);
			for (Owner o : gameboard.getOwnersCastles().keySet())
				ownersBox.getChildren().add(o);
			owners.setContent(ownersBox);
			infos.getChildren().add(owners);

			// add elements:
			root.getChildren().addAll(gameboard, infos);

			// Main game loop:
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
