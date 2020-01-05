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
package picrom.utils;

import javafx.scene.image.Image;
import picrom.entity.Entity;
import picrom.entity.castle.Castle;
import picrom.entity.unit.Knight;
import picrom.entity.unit.Onager;
import picrom.entity.unit.Pikeman;

/**
 * Class for load all game pictures.
 */
public class Drawables {

	public static Image worldBackground;
	public static Image infosBackground;
	public static Image circled;
	public static Image hammer;
	public static Image crown;
	public static Image treasure;
	public static Image door_close;
	public static Image door_open;
	public static EntityAssets castle;
	public static Image castle_s;
	public static Image castle_e;
	public static Image castle_w;
	public static EntityAssets onager;
	public static EntityAssets knight;
	public static EntityAssets pikeman;

	/**
	 * Constructor, instantiate it to load pictures in Image object.
	 * 
	 * @see javafx.scene.image.Image
	 */
	public Drawables() {
		worldBackground = new Image("Drawables/world_background.png");
		infosBackground = new Image("Drawables/infos_background.png");
		circled = new Image("Drawables/circled.png");
		hammer = new Image("Drawables/hammer.png");
		crown = new Image("Drawables/crown.png");
		treasure = new Image("Drawables/treasure.png");
		door_close = new Image("Drawables/door_close.png");
		door_open = new Image("Drawables/door_open.png");

		castle = new EntityAssets("castle");
		castle_s = new Image("Drawables/castle_s.png");
		castle_e = new Image("Drawables/castle_e.png");
		castle_w = new Image("Drawables/castle_w.png");
		onager = new EntityAssets("onager");
		knight = new EntityAssets("knight");
		pikeman = new EntityAssets("pikeman");
	}

	/**
	 * Usefull class for Entity. Combine two Image for showing Entity.
	 * 
	 * @see picrom.entity.Entity
	 * @see Drawables
	 */
	public class EntityAssets {
		private Image image;
		private Image mask;

		/**
		 * Constructor, load Images from file.
		 * 
		 * @param name Name of the file to laod. (exemple AAA if files are AAA.png and
		 *             AAA_mask.png)
		 */
		private EntityAssets(String name) {
			image = new Image("Drawables/" + name + ".png");
			mask = new Image("Drawables/" + name + "_mask.png");
		}

		/**
		 * @return The main Image to show an entity.
		 */
		public Image getImage() {
			return image;
		}

		/**
		 * @return A second Image, only white and transparent, which will be colored and
		 *         superposed on the main Image.
		 */
		public Image getMask() {
			return mask;
		}

	}

	/**
	 * Method used to get the correct assets for an Entity depending the type.
	 * 
	 * @param type Entity class type
	 * @return Image and mask for the Entity, null if unknow Entity.
	 */
	public static EntityAssets getAssets(Class<? extends Entity> type) {
		if (type == Castle.class)
			return castle;
		if (type == Pikeman.class)
			return pikeman;
		if (type == Onager.class)
			return onager;
		if (type == Knight.class)
			return knight;
		return null;
	}

}
