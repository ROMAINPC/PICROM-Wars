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
package picrom.gameboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Onager;
import picrom.entity.unit.Pikeman;
import picrom.entity.unit.Unit;
import picrom.owner.AI;
import picrom.owner.Neutral;
import picrom.owner.Owner;
import picrom.owner.Pensive;
import picrom.owner.Player;
import picrom.utils.Drawables;
import picrom.utils.Settings;
import picrom.utils.Utils;
import picrom.utils.Utils.Direction;

/**
 * World objects are graphical areas and also gameboards.
 * 
 * In a World there are different Owner : all players of the game.
 * 
 * The World contain several Castles, but also Units which are moving on the
 * terran (they have exited a Castle and not yet enter in another).
 * 
 * Graphically World is a JavaFX component wich shows ImageView of Castles and
 * Units. It adapts its size when window size change.
 * 
 * World doesn't run the game itself but offers methods to update Castles and
 * Units and do actions with them.
 * 
 * @see picrom.owner.Owner
 * @see picrom.entity.castle.Castle
 * @see picrom.entity.unit.Unit
 */
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

	/**
	 * Create a world and fix it in the entire window.
	 * 
	 * @param worldWidth  Number of cases horizontally
	 * @param worldHeight Number of cases vertically
	 * @param context     JavaFX Scene to fix the World.
	 */
	public World(int worldWidth, int worldHeight, Scene context) {
		this(worldWidth, worldHeight, new SimpleDoubleProperty(0), new SimpleDoubleProperty(0), context.widthProperty(),
				context.heightProperty());

	}

	/**
	 * Create a world and bind it in the specified area.
	 * 
	 * @param worldWidth  Number of cases horizontally
	 * @param worldHeight Number of cases vertically
	 * @param x           Property to bind World top left corner X coordinate.
	 * @param y           Property to bind World top left corner Y coordinate.
	 * @param width       Property to bin World graphic width.
	 * @param height      Property to bin World graphic height.
	 */
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

	/**
	 * Generate players, 1 human player and some AIs
	 * 
	 * @param nbAIs    Number of AIs to generate.
	 * @param nbBarons Number of Neutrals to generate.
	 * @see picrom.owner.AI
	 * @see picrom.owner.Neutral
	 * @see picrom.owner.Player
	 */
	public void generateOwners(int nbAIs, int nbBarons) {
		int nbPlayers = 1;

		// generate player:
		for (int i = 0; i < nbPlayers; i++)
			owners.add(new Player());
		// generate AIs:
		for (int i = 0; i < nbAIs; i++)
			owners.add(new AI());
		// generate neutrals:
		for (int i = 0; i < nbBarons; i++)
			owners.add(new Neutral());
	}

	/**
	 * Generate 1 Castle for each Owner in game. Found a random place to install the
	 * Castle. Also randomize door orientation and first garrison.
	 * 
	 * @throws TooManyCastlesException Throw Exception if too many castle cause
	 *                                 infinite research for an empty place to set a
	 *                                 Castle.
	 * @see picrom.utils.Settings#MINIMAL_CASTLE_DISTANCE
	 */
	public void generateWorldCastles() throws TooManyCastlesException {

		// Each owner (player or AI or baron) start the game with one castle.
		long time = System.currentTimeMillis();
		for (Owner owner : owners) {

			// choose position:
			boolean validCastle = false;
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

			// door orientation:
			boolean validDoor = false;
			while (!validDoor) {
				doorDir = Direction.randomDirection();
				validDoor = true;
				if (x + doorDir.getX() >= worldWidth || x + doorDir.getX() < 0 || y + doorDir.getY() >= worldHeight
						|| y + doorDir.getY() < 0) {
					validDoor = false;
				}
			}

			// create Castle:
			Castle castle = new Castle(owner, x, y, doorDir, this);
			owner.addCastle(castle);
			castlesArray[x][y] = castle;
			this.getChildren().add(castle);

			// Start garrison:
			int quantity = owner instanceof Neutral ? Settings.NEUTRAL_START_GARRISON : Settings.START_GARRISON;
			int generated = 0;
			while (generated < quantity) {
				int r = random.nextInt(3);
				Unit unit = null;
				if (r == 0)
					unit = new Pikeman(castle);
				else if (r == 1)
					unit = new Knight(castle);
				else
					unit = new Onager(castle);
				castle.enterUnit(unit);
				generated += unit.getHp();
			}
		}
	}

	/**
	 * Call this method to update all Castles in the World, for each of them that
	 * will update money, production and will manage exit if the Units.
	 */
	public void processCastles() {

		for (Owner owner : owners) {
			for (Castle castle : owner.getCastles()) {
				// Manage money
				castle.setTreasure(castle.getTreasure() + castle.getIncome());

				// Manage production
				castle.updateProduction();

				// Manage doors and exit of units
				List<Unit> toOut = castle.getLaunchList();
				for (Unit u : toOut) {
					u.setWorldX(u.getWorldX() + castle.getDoor().getDirection().getX());
					u.setWorldY(u.getWorldY() + castle.getDoor().getDirection().getY());
					castle.launchUnit(u);
				}

			}
		}
	}

	/**
	 * Call this method to move all Units currently engaged in the world. Also
	 * manage Castle enter and Castle assault.
	 */
	public void processUnits() {

		HashSet<Unit> toRemove = new HashSet<Unit>();

		for (Unit unit : units) {
			double varx = unit.getObjective().getWorldX() - unit.getWorldX();
			double vary = unit.getObjective().getWorldY() - unit.getWorldY();
			int indx, indy;

			// For each unit step (each unit has a number of step per tour equal to its
			// speed)
			for (int i = 0; i < unit.getSpeed(); i++) {
				// Moving (following a smaller division of the world grid):
				if (Math.abs(varx) > Math.abs(vary))
					unit.setWorldX(unit.getWorldX() + Math.signum(varx) / Settings.UNITS_GRID_SIZE);
				else
					unit.setWorldY(unit.getWorldY() + Math.signum(vary) / Settings.UNITS_GRID_SIZE);

				indx = (int) Math.round(unit.getWorldX()) >= this.getWorldWidth() ? this.getWorldWidth() - 1
						: (int) Math.round(unit.getWorldX());
				indy = (int) Math.round(unit.getWorldY()) >= this.getWorldHeight() ? this.getWorldHeight() - 1
						: (int) Math.round(unit.getWorldY());

				// If the unit finds a castle
				if (castlesArray[indx][indy] != null) {
					// If objective
					if (castlesArray[indx][indy] == unit.getObjective()) {
						// If enemy
						if (unit.getObjective().getOwner() != unit.getOwner()) {
							if (castlesArray[indx][indy].isGarrison()) {
								castlesArray[indx][indy].attackWith(unit, 1);
								if (unit.getDamage() <= 0)
									toRemove.add(unit);
							} else {
								// Victory
								castlesArray[indx][indy].getOwner().removeCastle(castlesArray[indx][indy]);
								unit.getOwner().addCastle(castlesArray[indx][indy]);
								castlesArray[indx][indy].setProduction(null);
								castlesArray[indx][indy].getDoor().setOpen(false);
							}
						}
						// If same owner (or castle just captured)
						if (unit.getObjective().getOwner() == unit.getOwner()) {
							// Add unit to courtyard
							toRemove.add(unit);
							castlesArray[indx][indy].enterUnit(unit);
						}
					}

				}
			}

		}
		// unengage units dead or entered in a castle:
		for (Unit u : toRemove)
			unengageUnit(u);

	}

	/**
	 * Call this method to make AI think oneitération.
	 */
	public void processAIs() {
		for (Owner owner : owners) {
			if (owner instanceof Pensive) {
				((Pensive) owner).reflect();
			}
		}
	}

	/**
	 * Unengage an Unit from the World,because this Unit is dead or was entered in a
	 * Castle.
	 * 
	 * @param unit the Unit
	 */
	public void unengageUnit(Unit unit) {
		units.remove(unit);
		this.getChildren().remove(unit);
	}

	/**
	 * Engage an Unit in the World, for instance because it exits a Castle.
	 * 
	 * @param unit the Unit
	 */
	public void engageUnit(Unit unit) {
		units.add(unit);
		this.getChildren().add(unit);
	}

	/**
	 * @return Number of case of the world horizontally.
	 */
	public int getWorldWidth() {
		return worldWidth;
	}

	/**
	 * @return Number of case of the world vertically.
	 */
	public int getWorldHeight() {
		return worldHeight;
	}

	/**
	 * @return List of Owners which play in the World (human or not)
	 */
	public List<Owner> getOwners() {
		return owners;
	}

	/**
	 * @return List of Owners which still in the World (human or not)
	 */
	public List<Owner> getInGameOwners() {
		List<Owner> inGame = new LinkedList<Owner>();
		for (Owner owner : owners) {
			if (owner.isInGame())
				inGame.add(owner);
		}
		return inGame;
	}

	@Override
	public String toString() {
		return "World: " + worldWidth + "x" + worldHeight + ", Owners: " + owners;
	}

}
