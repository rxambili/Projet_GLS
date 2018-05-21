/**
 * 
 */
package n7.towerDefense.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author rxambili
 *
 */
public class NatureTerrain extends Element {

	private NatureTerrainType type;
	
	/**
	 * @param _name
	 */
	public NatureTerrain(String _name, NatureTerrainType type) {
		super(_name);
		this.type = type;
		this.alive = true;
	}

	public NatureTerrainType getType() {
		return type;
	}

	public void setType(NatureTerrainType type) {
		this.type = type;
	}

	public void render(GameContainer c, Graphics g, int noLigne, int noColonne) {
		if (alive) {
			float x = noColonne*width;
			float y = noLigne*height;
			g.drawImage(this.img, x, y, x+width, y+height, 0, 0, this.img.getWidth(), this.img.getHeight());
		}
		
	}
	
	public void initImg(float width, float height) throws SlickException {
		super.initImg(width, height);			
		this.img = new Image(this.type.getCheminImg());
		this.img.setCenterOfRotation(this.img.getWidth()/ 2, this.img.getHeight()/ 2);
	}
	
	public void update(GameContainer c, int delta) {}

}
