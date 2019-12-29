package picrom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import picrom.entity.Owner;
import picrom.entity.castle.Castle;
import picrom.gameboard.Context;
import picrom.gameboard.TooManyCastlesException;
import picrom.gameboard.World;
import picrom.utils.Drawables;
import picrom.utils.Settings;
import picrom.utils.Utils;

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
						"Essayez de réduire le nombre de châteaux ou l'espacement entre eux, ou augmentez la taille du plateau de jeu.");
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
			infos.bindIn(owners, 0.15, 0.07, 0.8, 0.4);
			owners.prefHeightProperty().bind(infos.heightProperty().divide(2));
			VBox ownersBox = new VBox();
			ownersBox.setAlignment(Pos.CENTER);
			for (Owner o : gameboard.getOwners())
				ownersBox.getChildren().add(o);
			owners.setContent(ownersBox);
			infos.getChildren().add(owners);

			VBox castleInfos = new VBox();
			ScrollPane castleInfosSP = new ScrollPane(castleInfos);
			infos.bindIn(castleInfosSP, 0.15, 0.57, 0.8, 0.4);
			castleInfos.setAlignment(Pos.CENTER);
			StackPane castlePreview = new StackPane();
			ImageView castleImage = new ImageView(Drawables.castle.getImage());
			ImageView castleMask = new ImageView(Drawables.castle.getMask());
			castleImage.fitHeightProperty().bind(infos.heightProperty().multiply(0.15));
			castleImage.setPreserveRatio(true);
			castleMask.fitHeightProperty().bind(infos.heightProperty().multiply(0.15));
			castleMask.setPreserveRatio(true);
			castlePreview.getChildren().addAll(castleImage, castleMask);
			Label ownerL = new Label();
			ownerL.setTextAlignment(TextAlignment.CENTER);
			Label levelL = new Label();
			Label treasorL = new Label();
			Label incomeL = new Label();
			Label productionL = new Label();
			Label doorL = new Label();
			doorL.setTextAlignment(TextAlignment.CENTER);
			Label garrisonL = new Label();
			castleInfosSP.setVisible(false);
			castleInfos.getChildren().addAll(castlePreview, ownerL, levelL, treasorL, incomeL, productionL, doorL,
					garrisonL);
			infos.getChildren().add(castleInfosSP);

			// add elements:
			root.getChildren().addAll(gameboard, infos);

			// Listeners:
			gameboard.setOnMouseClicked(e -> {
				Parent clicked = e.getPickResult().getIntersectedNode().getParent();
				if (clicked instanceof Castle) {
					Castle castle = (Castle) clicked;
					Utils.colorize(castleMask, castle.getOwner().getColor());
					ownerL.setText("Propriétaire:\n" + castle.getOwner().getName());
					levelL.setText("Niveau: " + castle.getLevel());
					treasorL.setText("Trésor: " + castle.getTreasure());
					incomeL.setText("Revenu: ");
					productionL.setText("Production: ");
					doorL.setText("Porte:\n" + castle.getDoor());
					garrisonL.setText("Garnison:" + castle.getCourtyard());
					castleInfosSP.setVisible(true);
				} else {
					castleInfosSP.setVisible(false);
				}
			});

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
			primaryStage.setTitle("PICROM Wars ! par ROMAINPC & Picachoc, projet POO L3 info Université de Bordeaux");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
