/**
 * 
 */
package n7.towerDefense.game;


import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 * @author rxambili
 *
 */
public class Projectile extends Element {
	
	private static String DEFAULT_cheminImg = "ressources/projectiles/projectile1.png";
	private static String DEFAULT_cheminExp = "ressources/projectiles/boom3.png";
	private static String DEFAULT_cheminSonExp = "ressources/projectiles/explodemini.wav";

	private int portee;
	private int masse;
	private int vitesse;
	private int energie;
	private ElementLanceur cible;
	private ElementLanceur tireur;
	private float angle;
	private boolean explose;
	
	/**
	 * @param _name
	 */
	public Projectile(String _name, int portee, int masse, int vitesse, int energie) {
		super(_name);
		this.portee = portee;
		this.masse = masse;
		this.vitesse = vitesse;
		this.energie = energie;
		this.cible = null;
		this.tireur = null;
		this.explose = false;
	}

	public Projectile(Projectile p) {
		this(p.getName(), p.getPortee(), p.getMasse(), p.getVitesse(), p.getEnergie());
		this.angle = 0;
		this.elimination = p.getElimination();
		this.sonElimination = p.getSonElimination();
		this.img = p.getImg();
	}

	public int getPortee() {
		return portee;
	}

	public void setPortee(int portee) {
		this.portee = portee;
	}

	public int getMasse() {
		return masse;
	}

	public void setMasse(int masse) {
		this.masse = masse;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getEnergie() {
		return energie;
	}

	public void setEnergie(int energie) {
		this.energie = energie;
	}
	
	public void lancer(ElementLanceur cible, ElementLanceur tireur) {
		this.cible = cible;
		this.tireur = tireur;
		this.setAlive(true);
		this.x = tireur.getX();
		this.y = tireur.getY();
		this.angle = 0;
		this.explose = false;
		this.etatTour = EtatTour.TourEnCours;		
	}
	
	public void boom() {
		if (elimination != null) {
			this.explose = true;
		} else {
			this.kill();
		}
		this.sonElimination.play();
	}
		
	public void kill() {
		this.cible = null;
		this.tireur = null;
		this.alive = false;
		this.angle = 0;
		this.etatTour = EtatTour.TourFini;
	}

	public void render(GameContainer c, Graphics g) {
		if (alive) {
			if (explose) {			
				g.drawAnimation(this.elimination,
								(float) (this.x + this.width*Math.cos(angle)/4) - this.elimination.getWidth()/2,
								(float) (this.y + this.height*Math.sin(angle)/4) - this.elimination.getHeight()/2);
			} else {
				this.img.drawCentered(x, y);
			}
		} 
	}
	
	public void update(GameContainer c, int delta) {
		if (alive) {
			if (explose) {
				if (elimination.isStopped()) {
					this.kill();
				}
			} else {
				this.deplacer(delta);
				if (this.x >= c.getWidth() || this.x < 0 || this.y >= c.getHeight() || this.y < 0) {
					this.kill();
				} else {
					this.toucher(c);
				}
			}			
		}
	}
	
	private void deplacer(int delta) {
		System.out.println("Cible de " + this.name + " : " + cible);
		this.pivoterCible();
		this.x = (float) (this.x + this.vitesse*Math.cos(angle)*delta);
		this.y = (float) (this.y + this.vitesse*Math.sin(angle)*delta);
	}

	private void toucher(GameContainer c) {
		TerrainJeu terrain = cible.getCaseActu().getTerrain();
		ElementLanceur touche = terrain.getTouche(c, x, y);
		if (touche != null && !touche.equals(tireur)) {
			System.out.println("Touche!");
			if (touche.getEnergieActuelle() > energie) {
				this.boom();
				touche.setEnergieActuelle(touche.getEnergieActuelle() - energie);
				touche.jouerSon();
			} else if (touche.getEnergieActuelle() == energie)  {
				touche.kill();
				this.boom();
				if (touche.equals(cible)) {
					cible = null;
				}
			} else {
				touche.kill();
				energie = energie - touche.getEnergieActuelle();
				if (touche.equals(cible)) {
					cible = null;
				}
			}
		}		
	}

	private void pivoterCible() {
		if (cible != null && cible.isAlive()) {
			System.out.println("Pivote vers : " + cible);
			this.angle = (float) Math.atan2(cible.getY() - y, cible.getX() - x);
			this.img.setRotation((float) (angle*180/Math.PI) + 90);
		}
		
	}

	public void initImg(float width, float height) throws SlickException {
		super.initImg(width, height);
		this.img = new Image(Projectile.DEFAULT_cheminImg);
		this.img = this.img.getScaledCopy((int) width,(int) height);
		this.img.setCenterOfRotation(this.img.getWidth()/ 2, this.img.getHeight()/ 2);
		
		SpriteSheet ss = new SpriteSheet(Projectile.DEFAULT_cheminExp, 256, 256);
		this.elimination = new Animation();
		for (int i=0; i<ss.getHorizontalCount(); i++) {
			//for (int j=0; j<ss.getVerticalCount(); j++) {
				this.elimination.addFrame(ss.getSubImage(0, i).getScaledCopy((int) (width*0.8),(int) (height*0.8)), 50);
			//}
		}
		this.elimination.setLooping(false);
		this.elimination.setAutoUpdate(true);
		
		this.sonElimination = new Sound(Projectile.DEFAULT_cheminSonExp);
		
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void init() {
		this.cible = null;
		this.tireur = null;
		this.explose = false;
		this.alive = false;
		this.angle = 0;
		this.elimination.restart();
		this.etatTour = EtatTour.TourFini; 
	}

}
