package picrom;

import java.util.LinkedList;
import java.util.Random;

import javafx.scene.Group;

public class World extends Group {

	private static final Random random = new Random();

	// 2D array to store castle, used essentially for click events.
	private Castle[][] castlesArray;

	// lists of entities engaged in the world.
	private LinkedList<Entity> castles;
	private LinkedList<Entity> entities;

	// size in number of cells
	private int width;
	private int height;

	private int nbPlayers = 1;
	private int nbAIs;
	private int nbBarons;

	public World(int width, int height) {
		// generate background and manage layout with parent
		this.width = width;
		this.height = height;
	}

	public void generateWorldCastles(int nbAIs, int nbBarons) {
		this.nbAIs = nbAIs;
		this.nbBarons = nbBarons;
		castlesArray = new Castle[width][height];

		// TODO generates castles, randomize position

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
		return width;
	}

	public int getWorldHeight() {
		return height;
	}

}