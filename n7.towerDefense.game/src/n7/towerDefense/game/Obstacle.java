/**
 * 
 */
package n7.towerDefense.game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author rxambili
 *
 */
public class Obstacle extends ElementLanceur {
	
	private static String DEFAULT_cheminImg = "ressources/obstacles/obstacle1.png";
	
	/**
	 * @param _name
	 */
	public Obstacle(String _name, int energie, Tactique tactique, ArrayList<Projectile> projectiles, Case campement) {
		super(_name, energie, tactique, projectiles);
		this.setCaseActu(campement);
		this.alive = true;
	}
	
	public Obstacle(Obstacle obs, Case campement) {
		this(obs.getName(), obs.getEnergieMax(), obs.getTactique(), null, campement);
		ArrayList<Projectile> projectiles = new ArrayList<Projectile> ();
		for (Projectile p : obs.getProjectilesLancables()) {
			projectiles.add(new Projectile(p));
		}
		this.setProjectilesLancables(projectiles);
		this.img = obs.getImg();
		this.width = obs.getWidth();
		this.height = obs.getHeight();
		this.x = this.caseActu.getNoColonne()*width + width/2;
		this.y = this.caseActu.getNoLigne()*height + height/2;
		
	}
	
	public void initImg(float width, float height) throws SlickException {
		super.initImg(width, height);
		this.x = this.caseActu.getNoColonne()*width + width/2;
		this.y = this.caseActu.getNoLigne()*height + height/2;
		this.img = new Image(Obstacle.DEFAULT_cheminImg);
		this.img = this.img.getScaledCopy((int) width,(int) height);
		this.img.setCenterOfRotation(this.img.getWidth()/ 2, this.img.getHeight()/ 2);
	}
	
	public void update(GameContainer c, int delta) {
		boolean tir = this.tirer();
		boolean finProj = this.updateProjectiles(c, delta);
		if (!tir && finProj) {
			this.etatTour = EtatTour.TourFini;
			System.out.println(this.name + " a fini son tour");
		}		
	}
	
}
