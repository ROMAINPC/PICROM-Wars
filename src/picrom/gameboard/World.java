package picrom.gameboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import picrom.entity.Owner;
import picrom.entity.castle.Castle;
import picrom.entity.unit.Unit;
import picrom.settings.Drawables;
import picrom.settings.Settings;
import picrom.settings.Settings.OwnerType;
import picrom.settings.Utils;

public class World extends Context {

	private static Random random = Settings.SEED;

	// 2D array to store castles positions (used for click)
	private Castle[][] castlesArray;

	// lists of units engaged in the world
	private List<Unit> entities;

	// hashmap that binds owners to their castles
	private Map<Owner, List<Castle>> castles;

	// size in number of cells
	private int worldWidth;
	private int worldHeight;

	private int nbPlayers;
	private int nbAIs;
	private int nbBarons;

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

	public void generateOwners(int nbAIs, int nbBarons) {
		this.nbAIs = nbAIs;
		this.nbBarons = nbBarons;
		this.nbPlayers = 1;

		// generate Map structure:
		// generate player:
		for (int i = 0; i < this.nbPlayers; i++)
			castles.put(new Owner(OwnerType.Player), new LinkedList<Castle>());
		// generate AIs:
		for (int i = 0; i < this.nbAIs; i++)
			castles.put(new Owner(OwnerType.AI), new LinkedList<Castle>());
		// generate barons:
		for (int i = 0; i < this.nbBarons; i++)
			castles.put(new Owner(OwnerType.Baron), new LinkedList<Castle>());
	}

	public void generateWorldCastles() {
		// TODO generates castles, randomize position

		// Each owner (player or AI or baron) start the game with one castle.
		for (Owner owner : castles.keySet()) {
			boolean valid = false;
			int x = 0, y = 0;
			//TODO : security to avoid while loop if too many castles
			while (!valid) { // avoid too near castles.
				x = random.nextInt(worldWidth);
				y = random.nextInt(worldHeight);
				valid = true;
				for (int i = -Settings.MINIMAL_CASTLE_DISTANCE; i <= Settings.MINIMAL_CASTLE_DISTANCE; i++) {
					for (int j = -Settings.MINIMAL_CASTLE_DISTANCE; j <= Settings.MINIMAL_CASTLE_DISTANCE; j++) {
						if (x + i >= 0 && x + i < worldWidth && y + j >= 0 && y + j < worldHeight) {
							if (castlesArray[x + i][y + j] != null
									&& Utils.manDistance(x, y, x + i, y + j) <= Settings.MINIMAL_CASTLE_DISTANCE) {
								valid = false;
								j = Settings.MINIMAL_CASTLE_DISTANCE + 1;
								i = j;
							}
						}
					}
				}
			}
			Castle castle = new Castle(owner, x, y, this);
			castles.get(owner).add(castle);
			castlesArray[x][y] = castle;
			this.getChildren().add(castle);

		}

	}

	public void processCastles() {
		// TODO update money

		// TODO update production

		// TODO manage unit exit castle
	}

	// Process units engaged on the field
	public void processUnits() {
		// TODO move units

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
