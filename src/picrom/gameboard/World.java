package picrom.gameboard;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Random;

import com.sun.prism.paint.Color;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import picrom.entity.Entity;
import picrom.entity.Owner;
import picrom.entity.castle.Castle;
import picrom.settings.Drawables;
import picrom.settings.Settings;

public class World extends Context {

	private static final Random random = new Random();

	// 2D array to store castle, used essentially for click events.
	private Castle[][] castlesArray;

	// lists of entities engaged in the world.
	private LinkedList<Entity> castles;
	private LinkedList<Entity> entities;
	
	// size in number of cells
	private int worldWidth;
	private int worldHeight;

	private int nbPlayers = 1;
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
		
		Owner loic = new Owner(Color.RED, "Loic", Settings.OwnerType.Player);
		Owner romain = new Owner(Color.BLUE, "Romain", Settings.OwnerType.AI);
		Owner switzerland = new Owner(Color.WHITE, "Switzerland", Settings.OwnerType.Baron);
		
		// TODO generates castles, randomize position
		this.getChildren().addAll(new Castle(loic, 2, 4, this), 
								  new Castle(romain, 6, 4, this), 
								  new Castle(switzerland, 6, 5, this));

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
