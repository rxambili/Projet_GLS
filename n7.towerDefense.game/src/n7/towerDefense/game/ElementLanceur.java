/**
 * 
 */
package n7.towerDefense.game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


/**
 * @author rxambili
 *
 */
public abstract class ElementLanceur extends Element {

	protected int energieMax;
	protected int energieActuelle;
	protected int energieTour;
	protected Tactique tactique;
	protected ArrayList<Projectile> projectilesLancables;
	protected Case caseActu;
	
	
	public ElementLanceur(String _name, int energie, Tactique tactique,ArrayList<Projectile> projectiles) {
		super(_name);
		this.energieActuelle = energie;
		this.energieMax = energie;
		this.energieTour = energie;
		this.tactique = tactique;
		this.projectilesLancables = projectiles;
	}
	
	public int getEnergieMax() {
		return energieMax;
	}
	
	public void setEnergieMax(int energieMax) {
		this.energieMax = energieMax;
	}
	
	public int getEnergieActuelle() {
		return energieActuelle;
	}
	
	public void setEnergieActuelle(int energieActuelle) {
		this.energieActuelle = energieActuelle;
	}
	
	public int getEnergieTour() {
		return energieTour;
	}
	
	public void setEnergieTour(int energieTour) {
		this.energieTour = energieTour;
	}
	
	public Tactique getTactique() {
		return tactique;
	}
	
	public void setTactique(Tactique tactique) {
		this.tactique = tactique;
	}

	public ArrayList<Projectile> getProjectilesLancables() {
		return projectilesLancables;
	}

	public void setProjectilesLancables(ArrayList<Projectile> projectilesLancables) {
		this.projectilesLancables = projectilesLancables;
	}
	
	public Case getCaseActu() {
		return caseActu;
	}
	public void setCaseActu(Case caseActu) {
		this.caseActu = caseActu;
		this.x = this.caseActu.getNoColonne()*width + width/2;
		this.y = this.caseActu.getNoLigne()*height + height/2; 
		this.caseActu.ajoutElementLanceur(this);
	}
	
		
	protected boolean tirer() {
		//System.out.println(this.name + " tente de tirer");
		boolean tir = false;
		for (Projectile p : projectilesLancables) {
			if (!p.isAlive()) {
				if (this.energieTour >= p.getMasse()) {
					ElementLanceur cible = this.tactique.viser(this, p);
					if (cible != null) {
						p.lancer(cible, this);
						this.pivoterCible(cible);
						this.energieTour = this.energieTour - p.getMasse();
						tir = true;
						System.out.println(this.name + " a tire le projectile " + p.getName());
					} else {
						tir = tir || false;
					}
				} else {
					tir = tir || false;
				}
			}
		}
		return tir;
	}
	
	
	public String toString() {
		return super.toString() + " : EA = " + this.energieActuelle + ", ET = " + this.energieTour;
	}
	
	public void kill() {
		super.kill();
		this.caseActu.enleverElementLanceur(this);
	}
	
	public boolean isFinished() {
		boolean fini = super.isFinished();
		for (Projectile p : projectilesLancables) {
			fini = fini && p.isFinished();
		}
		return fini;
	}
	

	public void initTour() {
		this.etatTour = EtatTour.TourEnCours;
		this.x = this.caseActu.getNoColonne()*width + width/2;
		this.y = this.caseActu.getNoLigne()*height + height/2;
		for (Projectile p : projectilesLancables) {
			p.init();
		}
		this.energieTour = this.energieActuelle;
	}
	
	protected boolean updateProjectiles(GameContainer c, int delta) {
		boolean finTour = true;
		for (Projectile p : projectilesLancables) {
			if (p.isAlive()) {
				p.update(c, delta);
			}
			if (!p.isFinished()) {
				finTour = false;
			}
		}
		return finTour;
	}
	
	protected void pivoterCible(ElementLanceur cible) {
		if (cible != null && cible.isAlive()) {
			//System.out.println("Pivote vers : " + cible);
			float angle = (float) Math.atan2(cible.getY() - y, cible.getX() - x);
			this.img.setRotation((float) (angle*180/Math.PI) + 90);
		}
	}
	
	public void render(GameContainer c, Graphics g) {
		for (Projectile p : projectilesLancables) {
			p.render(c, g);
		}
		if (alive) {
			this.img.drawCentered(x, y);
			g.setColor(new Color(15,15,15,100));
			g.fillRect(x - width/4, y - height/2, width/2, 20);
			g.fillRect(x - width/4, y - height/2 + 20, width/2, 20);
			g.setColor(new Color(255,50,50));
			g.fillRect(x - width/4, y - height/2, width/2 * energieActuelle/energieMax, 20);
			g.setColor(new Color(50,50,255));
			g.fillRect(x - width/4, y - height/2 + 20, width/2 * energieTour/energieMax, 20);
			g.setColor(new Color(255,255,255));
			g.drawString(energieActuelle + "/" + energieMax, x - width/4 + 10, y - height/2);
			g.drawString(energieTour + "/" + energieMax, x - width/4 + 10, y - height/2 + 20);
		}
		
	}
	
}
