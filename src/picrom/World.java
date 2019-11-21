package picrom;

import java.util.LinkedList;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class World extends Group{
	
	//Castle size
	private Castle[][] world;
	
	private LinkedList<Entity> castles;
	private LinkedList<Entity> entities;
	
	//Background, call of the generate method
	public World(int nbCastles, int width, int height) {
		world = new Castle[width][height];
	}
	
	private Castle[][] generateWorld(int nbCastles, int width, int height) {
		return null;
	}
	
	
}
