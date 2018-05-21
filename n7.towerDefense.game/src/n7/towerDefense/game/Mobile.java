/**
 * 
 */
package n7.towerDefense.game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


/**
 * @author rxambili
 *
 */
public class Mobile extends ElementLanceur {
	
	private static final String DEFAULT_cheminImg = "ressources/mobiles/mobile1.png";
	private static final String DEFAULT_cheminSonElim = "ressources/mobiles/pain1.wav";	
	private static final float DEFAULT_vitesse = 1;

	private float vitesse;
	private float angle;
	private int volume;
	private int posDansVague;
	private Case entree;
	private Case sortie;
	private Case nextCase;
	
	/**
	 * @param _name
	 */
	public Mobile(String _name, int energie, Tactique tactique, ArrayList<Projectile> projectiles, int vol, int pos, Case entree, Case sortie) {
		super(_name, energie, tactique, projectiles);
		this.volume = vol;
		this.posDansVague = pos;
		this.entree = entree;
		this.sortie = sortie;
		this.vitesse = Mobile.DEFAULT_vitesse;
		this.angle = 0;
	}
	
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public int getPosDansVague() {
		return posDansVague;
	}
	
	public void setPosDansVague(int posDansVague) {
		this.posDansVague = posDansVague;
	}
	public Case getEntree() {
		return entree;
	}
	public void setEntree(Case entree) {
		this.entree = entree;
	}
	public Case getSortie() {
		return sortie;
	}
	public void setSortie(Case sortie) {
		this.sortie = sortie;
	}
	
	public void setCaseActu(Case caseActu) {
		if (this.caseActu != null) {
			this.caseActu.setVolume(this.caseActu.getVolume() - this.volume);
			this.caseActu.enleverElementLanceur(this);
		}
		this.caseActu = caseActu;
		this.caseActu.ajoutElementLanceur(this);
		this.caseActu.setVolume(this.caseActu.getVolume() + this.volume);
	}

		
	public String toString() {
		if (this.caseActu != null) {
			return super.toString() + "; " + this.caseActu.toString();
		} else {
			return super.toString();
		}
	}
	
	public void kill() {
		super.kill();
		this.caseActu.setVolume(this.caseActu.getVolume() - this.volume);
		this.jouerSon();
	}

		
	public boolean isOut() {
		return this.caseActu.equals(this.sortie);
	}
	
	public void initTour() {
		super.initTour();
		this.nextCase = null;
	}
	
	public void update(GameContainer c, int delta) {
		boolean finDeplacement = false;
		boolean tir = false;
		if (this.nextCase == null) {
			this.nextCase = this.getNextCase();
			if (this.nextCase == null) {
				finDeplacement = true;
			}			
		} else {
			this.deplacer(delta);
		}
		if (finDeplacement) {
			tir = this.tirer();
			boolean finProj = this.updateProjectiles(c, delta);
			if (!tir && finProj) {
				System.out.println(this.name + " a fini son tour");
				this.etatTour = EtatTour.TourFini;
			}
		}
		if (this.isOut()) {
			this.kill();
		}
		
		
		
	}
	

	private Case getNextCase() {
		ArrayList<Case> chemin = this.caseActu.getTerrain().getChemin(this.entree,this.sortie);
		Case next = chemin.get( (chemin.indexOf(this.caseActu)) + 1);
		
		// verifie si on peut entrer dans la case Next
		if (next.getVolume() + this.volume <= next.getVolumeMax()) {			
			if (this.energieTour >= next.getEnergie()) {
				this.energieTour = this.energieTour - next.getEnergie();
			} else {
				next = null;
			}
		} else {
			next = null;
		}
		return next;
	}


	private void deplacer(int delta) {
		this.pivoterCase();
		this.x = (float) (this.x + this.vitesse*Math.cos(angle)*delta);
		this.y = (float) (this.y + this.vitesse*Math.sin(angle)*delta);
		float xCase = this.nextCase.getNoColonne()*width + width/2;
		float yCase = this.nextCase.getNoLigne()*height + height/2;
		if ((this.x <= xCase + 10 && this.x >= xCase - 10)
					&& (this.y <= yCase + 10 && this.y >= yCase - 10)) {
			this.setCaseActu(this.nextCase);
			this.nextCase = null;
		}
	}
	
	
	private void pivoterCase() {
		if (nextCase != null) {
			float x2 = nextCase.getNoColonne()*width + width/2;
			float y2 = nextCase.getNoLigne()*height + height/2;
			this.angle = (float) Math.atan2(y2 - y, x2 - x);
			this.img.setRotation((float) (angle*180/Math.PI) + 90);
		}		
	}


	public void initImg(float width, float height) throws SlickException {
		super.initImg(width, height);
		this.img = new Image(Mobile.DEFAULT_cheminImg);
		this.img = this.img.getScaledCopy((int) width,(int) height);
		this.img.setCenterOfRotation(this.img.getWidth()/ 2, this.img.getHeight()/ 2);
		
		this.sonElimination = new Sound(Mobile.DEFAULT_cheminSonElim);
	}

}
