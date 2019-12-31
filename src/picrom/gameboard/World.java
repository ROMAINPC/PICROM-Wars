package picrom.gameboard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import picrom.entity.Owner;
import picrom.entity.castle.Castle;
import picrom.entity.unit.Unit;
import picrom.utils.Drawables;
import picrom.utils.Settings;
import picrom.utils.Utils;
import picrom.utils.Utils.Direction;
import picrom.utils.Utils.OwnerType;

public class World extends Context {

	private static Random random = Settings.SEED;

	// 2D array to store castles positions
	private Castle[][] castlesArray;

	// lists of units engaged in the world
	private List<Unit> units;

	// list of owners engaged in the world
	private List<Owner> owners;

	// size in number of cells
	private int worldWidth;
	private int worldHeight;

	private int nbPlayers;
	private int nbAIs;
	private int nbBarons;

	public World(int worldWidth, int worldHeight, Scene context) {
		this(worldWidth, worldHeight, new SimpleDoubleProperty(0), new SimpleDoubleProperty(0), context.widthProperty(),
				context.heightProperty());

	}

	public World(int worldWidth, int worldHeight, ReadOnlyDoubleProperty x, ReadOnlyDoubleProperty y,
			ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
		super(x, y, width, height, (double) worldWidth / (double) worldHeight); // linking layout
		// generate background and manage layout with parent
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;

		this.castlesArray = new Castle[worldWidth][worldHeight];
		this.units = new LinkedList<Unit>();
		this.owners = new ArrayList<Owner>();

		// load and display background:
		ImageView background = new ImageView(Drawables.worldBackground);
		background.fitWidthProperty().bind(this.widthProperty());
		background.fitHeightProperty().bind(this.heightProperty());
		background.xProperty().bind(this.xProperty());
		background.yProperty().bind(this.yProperty());
		this.getChildren().add(background);

	}

	public void generateOwners(int nbAIs, int nbBarons) {
		this.nbAIs = nbAIs;
		this.nbBarons = nbBarons;
		this.nbPlayers = 1;
		
		// generate player:
		for (int i = 0; i < this.nbPlayers; i++)
			owners.add(new Owner(OwnerType.Player));
		// generate AIs:
		for (int i = 0; i < this.nbAIs; i++)
			owners.add(new Owner(OwnerType.AI));
		// generate barons:
		for (int i = 0; i < this.nbBarons; i++)
			owners.add(new Owner(OwnerType.Baron));
	}

	public void generateWorldCastles() throws TooManyCastlesException {
		
		// Each owner (player or AI or baron) start the game with one castle.
		long time = System.currentTimeMillis();
		for (Owner owner : owners) {
			boolean validCastle = false;
			boolean validDoor = false;
			int x = 0, y = 0;
			Direction doorDir = null;
			while (!validCastle) { // avoid too near castles.
				// infinite loop check:
				if (System.currentTimeMillis() - time > 1000)
					throw new TooManyCastlesException();
				x = random.nextInt(worldWidth);
				y = random.nextInt(worldHeight);
				validCastle = true;
				for (int i = -Settings.MINIMAL_CASTLE_DISTANCE; i <= Settings.MINIMAL_CASTLE_DISTANCE; i++) {
					for (int j = -Settings.MINIMAL_CASTLE_DISTANCE; j <= Settings.MINIMAL_CASTLE_DISTANCE; j++) {
						if (x + i >= 0 && x + i < worldWidth && y + j >= 0 && y + j < worldHeight) {
							if (castlesArray[x + i][y + j] != null
									&& Utils.manDistance(x, y, x + i, y + j) <= Settings.MINIMAL_CASTLE_DISTANCE) {
								validCastle = false;
								j = Settings.MINIMAL_CASTLE_DISTANCE + 1;
								i = j;
							}
						}
					}
				}
			}

			while (!validDoor) {
				doorDir = Direction.randomDirection();
				validDoor = true;
				if (x + doorDir.getX() >= worldWidth || x + doorDir.getX() < 0 || y + doorDir.getY() >= worldHeight
						|| y + doorDir.getY() < 0) {
					validDoor = false;
				}
			}
			
			Castle castle = new Castle(owner, x, y, doorDir, this);
			owner.addCastle(castle);
			castlesArray[x][y] = castle;
			this.getChildren().add(castle);

		}
	}

	public void processCastles() {
		
		for (Owner owner : owners) {
			for (Castle castle : owner.getCastles()) {
				// Manage money
				castle.setTreasure(castle.getTreasure() + castle.getIncome());

				// Manage production 
				castle.updateProduction();
				
				// Manage doors and exit of units
				List<Unit> l = castle.getCourtyard().takeOutUnits();
				if (l != null) {
					for(Unit u : l) {
						u.setWorldX(u.getWorldX() + castle.getDoor().getDir().getX());
						u.setWorldY(u.getWorldY() + castle.getDoor().getDir().getY());
					}
					units.addAll(l);
					this.getChildren().addAll(l);
				}

			}
		}
	}

	// Process units engaged on the field
	public void processUnits() {

		for (Unit u : units) {
			double varx = u.getObjective().getWorldX() - u.getWorldX();
			double vary = u.getObjective().getWorldY() - u.getWorldY();
			int indx, indy;

			// For each unit step
			for (int i = 0; i < u.getSpeed(); i++) {
				if (Math.abs(varx) > Math.abs(vary))
					u.setWorldX(u.getWorldX() + (varx / Math.abs(varx)) / Settings.UNITS_GRID_SIZE);
				else
					u.setWorldY(u.getWorldY() + (vary / Math.abs(vary)) / Settings.UNITS_GRID_SIZE);

				indx = (int) Math.round(u.getWorldX());
				indy = (int) Math.round(u.getWorldY());

				// If the unit finds a castle
				if (castlesArray[indx][indy] != null) {
					System.out.println("ici");
					// If objective
					if (castlesArray[indx][indy] == u.getObjective()) {
						// If enemy
						if (u.getObjective().getOwner() != u.getOwner()) {
							if (!castlesArray[indx][indy].getCourtyard().getUnits().isEmpty()) {
								// If unit dead
								if (u.getDamage() == u.getDamageDone()) {
									units.remove(u);
								} else {
									// Assault
									castlesArray[indx][indy].getCourtyard().assault();
									u.setDamageDone(u.getDamageDone() + 1);
								}
							} else {
								// Victory
								castlesArray[indx][indy].setOwner(u.getOwner());
								System.out.println(castlesArray[indx][indy].getOwner());
								castlesArray[indx][indy].getProductionUnit().setProduction(null);
							}
						}
						// If same owner
						else {
							// Add unit to courtyard
							u.getObjective().getCourtyard().addUnit(u);
							units.remove(u);
						}
					}

				}
			}

		}
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

	public List<Owner> getOwners() {
		return owners;
	}
}
