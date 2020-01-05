/*******************************************************************************
 * Copyright (C) 2019-2020 ROMAINPC
 * Copyright (C) 2019-2020 Picachoc
 * 
 * This file is part of PICROM-Wars
 * 
 * PICROM-Wars is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * PICROM-Wars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package picrom;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Optional;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Onager;
import picrom.entity.unit.Pikeman;
import picrom.entity.unit.Unit;
import picrom.gameboard.Context;
import picrom.gameboard.TooManyCastlesException;
import picrom.gameboard.World;
import picrom.owner.Owner;
import picrom.owner.Player;
import picrom.utils.Drawables;
import picrom.utils.Settings;
import picrom.utils.Utils;

/**
 * Main class, setup UI, init gameboard and run it.
 */
public class Main extends Application {

	private Castle currentClicked;
	private Border border;
	private Border alphaBorder;
	private ImageView castleMask, knightM, pikemanM, onagerM, circled, door;
	private Label ownerL, levelL, pikemanNumber, knightNumber, onagerNumber, treasureL;
	private StackPane knightSP, onagerSP, pikemanSP, hammerSP;
	private World gameboard;

	private boolean pause;

	@Override
	public void start(Stage primaryStage) {
		try {

			Group root = new Group();
			Scene scene = new Scene(root, Settings.DEFAULT_SCENE_WIDTH, Settings.DEFAULT_SCENE_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// loading textures:
			new Drawables();
			scene.setFill(Color.BLACK);
			SimpleDoubleProperty vSeparator = new SimpleDoubleProperty();
			vSeparator.bind(scene.widthProperty().multiply(Settings.WORLD_WIDTH_RATIO));
			
			Alert loadSave = new Alert(AlertType.CONFIRMATION);
			loadSave.setTitle("PICROM Wars - Par ROMAINPC & Picachoc");
			loadSave.setHeaderText("Souhaitez-vous charger une sauvegarde ?");
			ButtonType buttonYes = new ButtonType("Oui");
			ButtonType buttonNo = new ButtonType("Non");
			ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			loadSave.getButtonTypes().setAll(buttonYes, buttonNo, buttonTypeCancel);
			Optional<ButtonType> choice = loadSave.showAndWait();
			
			if (choice.get() == buttonYes){
				String filename;
				final FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose a save");
				fileChooser.setInitialDirectory(new File("./saves"));
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PICROM WARS SAVE", "*.pw"));

				File file = fileChooser.showOpenDialog(primaryStage);
				filename = file.getName();
				load(filename);
				
			} else if (choice.get() == buttonNo) {
				gameboard = new World(Settings.WORLD_WIDTH, Settings.WORLD_HEIGHT, new SimpleDoubleProperty(0),
						new SimpleDoubleProperty(0), vSeparator, scene.heightProperty());
						
				// Add players and AIs in the game:
				gameboard.generateOwners(Settings.NUMBER_OF_AIS, Settings.NUMBER_OF_BARONS);

				// Generate first castle for each owner:
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
			} else {
			    System.exit(0);
			}
			

			// setup GUI:
			Context infos = new Context(); // informations and actions area at the right of the game.
			infos.xProperty().bind(gameboard.xProperty().add(gameboard.widthProperty()));
			infos.yProperty().set(0);
			infos.heightProperty().bind(scene.heightProperty());
			infos.widthProperty().bind(scene.widthProperty().multiply(1 - Settings.WORLD_WIDTH_RATIO));
			ImageView infosBackground = new ImageView(Drawables.infosBackground);
			infos.bindIn(infosBackground, 0, 0, 1, 1);
			infos.getChildren().add(infosBackground);

			// owners list:
			ScrollPane owners = new ScrollPane();
			infos.bindIn(owners, 0.15, 0.07, 0.8, 0.4);
			owners.prefHeightProperty().bind(infos.heightProperty().divide(2));
			VBox ownersBox = new VBox();
			ownersBox.setAlignment(Pos.CENTER);
			for (Owner o : gameboard.getOwners())
				ownersBox.getChildren().add(o);
			owners.setContent(ownersBox);
			infos.getChildren().add(owners);

			// castle preview
			VBox castleInfos = new VBox();
			ScrollPane castleInfosSP = new ScrollPane(castleInfos);
			infos.bindIn(castleInfosSP, 0.15, 0.57, 0.8, 0.4);
			castleInfos.setAlignment(Pos.CENTER);
			StackPane castlePreview = new StackPane();
			ImageView castleImage = new ImageView(Drawables.castle.getImage());
			castleMask = new ImageView(Drawables.castle.getMask());
			castleImage.fitHeightProperty().bind(infos.heightProperty().multiply(0.15));
			castleImage.setPreserveRatio(true);
			castleMask.fitHeightProperty().bind(infos.heightProperty().multiply(0.15));
			castleMask.setPreserveRatio(true);
			door = new ImageView(Drawables.door_close);
			door.fitHeightProperty().bind(castleImage.fitHeightProperty().multiply(0.4));
			door.translateYProperty()
					.bind(castleImage.fitHeightProperty().divide(2).subtract(door.fitHeightProperty().divide(2)));
			door.setPreserveRatio(true);
			castlePreview.getChildren().addAll(castleImage, castleMask, door);

			// Production buttons:
			HBox produceChoice = new HBox(); // also print quantity in garrison
			VBox pikeman = new VBox();
			ImageView pikemanIV = new ImageView(Drawables.pikeman.getImage());
			pikemanM = new ImageView(Drawables.pikeman.getMask());
			pikemanSP = new StackPane(pikemanIV, pikemanM);
			pikemanNumber = new Label();
			pikeman.setAlignment(Pos.CENTER);
			pikeman.getChildren().addAll(pikemanSP, pikemanNumber);
			VBox knight = new VBox();
			ImageView knightIV = new ImageView(Drawables.knight.getImage());
			knightM = new ImageView(Drawables.knight.getMask());
			knightSP = new StackPane(knightIV, knightM);
			knightNumber = new Label();
			knight.setAlignment(Pos.CENTER);
			knight.getChildren().addAll(knightSP, knightNumber);
			VBox onager = new VBox();
			ImageView onagerIV = new ImageView(Drawables.onager.getImage());
			onagerM = new ImageView(Drawables.onager.getMask());
			onagerSP = new StackPane(onagerIV, onagerM);
			onagerNumber = new Label();
			onager.setAlignment(Pos.CENTER);
			onager.getChildren().addAll(onagerSP, onagerNumber);
			VBox hammer = new VBox();
			ImageView hammerIV = new ImageView(Drawables.hammer);
			hammerSP = new StackPane(hammerIV);
			levelL = new Label();
			hammer.setAlignment(Pos.CENTER);
			hammer.getChildren().addAll(hammerSP, levelL);
			pikemanIV.setPreserveRatio(true);
			pikemanIV.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			pikemanM.setPreserveRatio(true);
			pikemanM.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			knightIV.setPreserveRatio(true);
			knightIV.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			knightM.setPreserveRatio(true);
			knightM.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			onagerIV.setPreserveRatio(true);
			onagerIV.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			onagerM.setPreserveRatio(true);
			onagerM.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			hammerIV.setPreserveRatio(true);
			hammerIV.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			produceChoice.getChildren().addAll(pikeman, knight, onager, hammer);
			border = new Border(new BorderStroke(Color.DARKRED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
					new BorderWidths(3, 3, 3, 3))); // Border to circle production choosed.
			alphaBorder = new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
					new BorderWidths(3, 3, 3, 3)));

			// Labels:
			ownerL = new Label();
			ownerL.setTextAlignment(TextAlignment.CENTER);

			// Treasure:
			HBox treasure = new HBox();
			ImageView treasureIV = new ImageView(Drawables.treasure);
			treasureIV.setPreserveRatio(true);
			treasureIV.fitHeightProperty().bind(castleImage.fitHeightProperty().divide(3));
			treasureL = new Label();
			treasure.setAlignment(Pos.CENTER);
			treasure.getChildren().addAll(treasureIV, treasureL);
			castleInfosSP.setVisible(false);
			castleInfos.getChildren().addAll(castlePreview, produceChoice, ownerL, treasure);
			infos.getChildren().add(castleInfosSP);

			// Objective:
			circled = new ImageView(Drawables.circled);
			circled.setVisible(false);
			circled.fitHeightProperty()
					.bind(gameboard.heightProperty().divide(gameboard.getWorldHeight()).multiply(1.4));
			circled.fitWidthProperty().bind(gameboard.widthProperty().divide(gameboard.getWorldWidth()).multiply(1.4));

			// add elements:
			root.getChildren().addAll(gameboard, infos, circled);

			// Listeners:
			currentClicked = null;
			gameboard.setOnMouseClicked(e -> {
				Parent clicked = e.getPickResult().getIntersectedNode().getParent();
				if (clicked instanceof Castle) {
					if (currentClicked != null && currentClicked.getOwner() instanceof Player
							&& e.getButton() == MouseButton.SECONDARY) { // Right click to
																			// define objectiv
						Castle target = (Castle) clicked;
						if (target != currentClicked) {
							currentClicked.setObjective(target);
						}
					} else if (e.getButton() == MouseButton.PRIMARY) { // Normal click
						currentClicked = (Castle) clicked;
						castleInfosSP.setVisible(true);
					}
				} else { // Click on other thing than a Castle :
					castleInfosSP.setVisible(false);
					currentClicked = null;
				}

			});

			// production choice, click twice to set no production:
			pikemanSP.setOnMouseClicked(e -> {
				if (currentClicked.getOwner() instanceof Player)
					currentClicked
							.setProduction(currentClicked.getProduction() != Pikeman.class ? Pikeman.class : null);
			});
			knightSP.setOnMouseClicked(e -> {
				if (currentClicked.getOwner() instanceof Player)
					currentClicked.setProduction(currentClicked.getProduction() != Knight.class ? Knight.class : null);
			});
			onagerSP.setOnMouseClicked(e -> {
				if (currentClicked.getOwner() instanceof Player)
					currentClicked.setProduction(currentClicked.getProduction() != Onager.class ? Onager.class : null);
			});
			hammerSP.setOnMouseClicked(e -> {
				if (currentClicked.getOwner() instanceof Player)
					currentClicked.setProduction(currentClicked.getProduction() != Castle.class ? Castle.class : null);
			});

			// Door close or open:
			castlePreview.setOnMouseClicked(e -> {
				if (currentClicked.getOwner() instanceof Player)
					currentClicked.getDoor().setOpen(!currentClicked.getDoor().isOpen());

			});

			// pause button:
			Button pauseB = new Button("Lancer");
			infos.bindIn(pauseB, 0.15, 0.47, 0.6, 0.1);
			infos.getChildren().add(pauseB);
			pause = true;
			pauseB.setOnAction(e -> {
				if (pause) {
					pauseB.setText("Pause");
					pause = false;
				} else {
					pauseB.setText("Reprendre");
					pause = true;
				}
			});
			
			Button saveB = new Button("Sauvegarder la partie");
			infos.bindIn(saveB, 0.15, 0.37, 0.6, 0.1);
			//infos.bindIn(iV, xRatio, yRatio, widthRatio, heightRatio);
			infos.getChildren().add(saveB);
			saveB.setOnAction(e -> {
				String saveName = "";
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Save name");
				// dialog.setHeaderText("Look, a Text Input Dialog");
				dialog.setContentText("Please enter a name for this save:");
				saveName = dialog.showAndWait().orElse(null);
				save(saveName, gameboard);
			});

			// Main game loop:
			Label victoryL = new Label("--");
			victoryL.setId("victory-label");
			gameboard.bindCenterIn(victoryL, 0.5, 0.5);
			ImageView crown = new ImageView(Drawables.crown);
			gameboard.bindCenterIn(crown, 0.5, 0.38, 0.2, 0.2);
			Group victory = new Group(victoryL, crown);
			victory.setVisible(false);
			root.getChildren().add(victory);

			Timeline gameLoop = new Timeline();
			gameLoop.setCycleCount(Timeline.INDEFINITE);
			gameLoop.getKeyFrames().add(new KeyFrame(Settings.TURN_DURATION, new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (!pause) {
						// Update AI choices:
						gameboard.processAIs();

						// update world:
						gameboard.processCastles();
						gameboard.processUnits();

						// Test end of game:
						List<Owner> inGame = gameboard.getInGameOwners();
						if (inGame.size() < 2) {

							if (inGame.size() == 1)
								victoryL.setText(inGame.get(0).getName());
							else
								victoryL.setText("Pas de vainqueur");

							victory.setVisible(true);
							pause = true;
							pauseB.setVisible(false);
						}

					}

					// refresh UI
					refreshCastleInfos();
				}
			}));

			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.setTitle("PICROM Wars ! par ROMAINPC & Picachoc, projet POO L3 info Université de Bordeaux");
			primaryStage.setMaximized(true);
			primaryStage.show();

			// START LOOP
			gameLoop.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void save(String filename, World gameboard) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("./saves/" + filename + "." + Settings.SAVES_EXTENSION);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(gameboard);
		} catch (IOException e1) {
			System.err.println("Error while saving the game.");
			e1.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		
	}
	
	private void load(String filename) {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			fileInputStream = new FileInputStream("./saves/" + filename);
			objectInputStream = new ObjectInputStream(fileInputStream);
			//Doesn't display it for the moment.. work in progress
			gameboard = (World) objectInputStream.readObject();
			//TODO add the gameboard to the group
		} catch (IOException e) {
			System.err.println("Error while loading the save.");
			e.printStackTrace();
		}	
		  catch (ClassNotFoundException e) {
			System.err.println("Error : the save is corrupted.");
			e.printStackTrace();
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Used to update values of castle preview.
	 */
	private void refreshCastleInfos() {
		if (currentClicked != null) {
			Utils.colorize(castleMask, currentClicked.getOwner().getColor());
			Utils.colorize(pikemanM, currentClicked.getOwner().getColor());
			Utils.colorize(knightM, currentClicked.getOwner().getColor());
			Utils.colorize(onagerM, currentClicked.getOwner().getColor());
			door.setImage(currentClicked.getDoor().isOpen() ? Drawables.door_open : Drawables.door_close);
			ownerL.setText(currentClicked.getOwner().getName());
			treasureL.setText(currentClicked.getTreasure() + " (+" + currentClicked.getIncome() + ")");
			levelL.setText("Nv " + currentClicked.getLevel());
			Map<Class<? extends Unit>, Integer> units = currentClicked.getGarrisonQuantity();
			Class<? extends Unit> key = Pikeman.class;
			pikemanNumber.setText(units.containsKey(key) ? String.valueOf(units.get(key)) : "0");
			key = Knight.class;
			knightNumber.setText(units.containsKey(key) ? String.valueOf(units.get(key)) : "0");
			key = Onager.class;
			onagerNumber.setText(units.containsKey(key) ? String.valueOf(units.get(key)) : "0");
			pikemanSP.setBorder(currentClicked.getProduction() == Pikeman.class ? border : alphaBorder);
			knightSP.setBorder(currentClicked.getProduction() == Knight.class ? border : alphaBorder);
			onagerSP.setBorder(currentClicked.getProduction() == Onager.class ? border : alphaBorder);
			hammerSP.setBorder(currentClicked.getProduction() == Castle.class ? border : alphaBorder);

			if (currentClicked.getObjective() != null) {
				circled.setVisible(true);
				circled.layoutXProperty()
						.bind(gameboard.xProperty()
								.add(circled.fitWidthProperty().divide(1.4)
										.multiply(currentClicked.getObjective().getWorldX())
										.subtract(circled.fitWidthProperty().divide(7))));
				circled.layoutYProperty()
						.bind(gameboard.yProperty()
								.add(circled.fitHeightProperty().divide(1.4)
										.multiply(currentClicked.getObjective().getWorldY())
										.subtract(circled.fitHeightProperty().divide(7))));

			} else {
				circled.setVisible(false);
			}
		} else {
			circled.setVisible(false);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
