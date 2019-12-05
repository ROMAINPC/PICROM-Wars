package picrom.gameboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import picrom.entity.Owner;
import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Onager;
import picrom.entity.unit.Unit;
import picrom.settings.Drawables;
import picrom.settings.Settings;

public class World extends Context {

	// 2D array to store castles positions (used for click)
	private Castle[][] castlesArray;

	// lists of units engaged in the world
	private List<Unit> entities;

	// hashmap that binds owners to their castles
	private Map<Owner, List<Castle>> castles;

	// size in number of cells
	private int worldWidth;
	private int worldHeight;

	private int nbPlayers = 1;
	private int nbAIs;
	private int nbBarons;

	private Castle test;
	private Knight testU;
	private Castle test2;
	private Onager testU2;
	private Castle test3;
	private Knight testU3;

	public World(int worldWidth, int worldHeight, Scene context) {
		this(worldWidth, worldHeight, context.xProperty(), context.yProperty(), context.widthProperty(),
				context.heightProperty());

	}

	public World(int worldWidth, int worldHeight, ReadOnlyDoubleProperty x, ReadOnlyDoubleProperty y,
			ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
		super(x, y, width, height); // linking layout
		// generate background and manage layout with parent
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;

		this.castlesArray = new Castle[worldWidth][worldHeight];
		this.entities = new LinkedList<Unit>();
		this.castles = new HashMap<Owner, List<Castle>>();

		// load and display background:
		ImageView background = new ImageView(Drawables.worldBackground);
		background.fitWidthProperty().bind(this.widthProperty());
		background.fitHeightProperty().bind(this.heightProperty());
		this.getChildren().add(background);
	}

	public void generateWorldCastles(int nbAIs, int nbBarons) {
		this.nbAIs = nbAIs;
		this.nbBarons = nbBarons;
		castlesArray = new Castle[worldWidth][worldHeight];

		// TODO generates castles, randomize position
		this.getChildren().addAll(new Castle(Settings.OwnerType.AI, 2, 4, this),
				new Castle(Settings.OwnerType.AI, 6, 4, this), new Castle(Settings.OwnerType.AI, 6, 5, this));

		test = new Castle(Settings.OwnerType.AI, 0, 8, this);
		testU = new Knight(test);
		this.getChildren().addAll(test, testU);
		test2 = new Castle(Settings.OwnerType.AI, 0, 9, this);
		testU2 = new Onager(test2);
		this.getChildren().addAll(test2, testU2);
		test3 = new Castle(Settings.OwnerType.AI, 0, 10, this);
		testU3 = new Knight(test3);
		this.getChildren().addAll(test3, testU3);

	}

	public void processCastles() {
		// TODO update money

		// TODO update production

		// TODO manage unit exit castle
	}

	// Process units engaged on the field
	public void processUnits() {
		// TODO move units
		testU.setWorldX(testU.getWorldX() + 1);
		testU2.setWorldX(testU2.getWorldX() + 2);
		testU3.setWorldX(testU3.getWorldX() + 3);

		// TODO assault or enter castle, if unit reached the target
	}

	public int getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

	public int getNbAIs() {
		return nbAIs;
	}

	public void setNbAIs(int nbAIs) {
		this.nbAIs = nbAIs;
	}

	public int getNbBarons() {
		return nbBarons;
	}

	public void setNbBarons(int nbBarons) {
		this.nbBarons = nbBarons;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

}
