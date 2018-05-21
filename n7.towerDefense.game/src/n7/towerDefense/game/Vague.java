package n7.towerDefense.game;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Vague {

	private int energieAttribueeSiVictoire;
	private ArrayList<Mobile> mobilesVague;
	private ArrayList<Obstacle> obstaclesVagues;
	private int nbEntres;
	private int nbSortis;
	private int nbElimine;
	private int nbTours;
	private EtatVague etatVague;
	private Niveau niveau;
	
	// le constructeur
	public Vague(int eMC2_GainIf_V, ArrayList<Mobile> MobWave, ArrayList<Obstacle> ObsWave) {
		this.energieAttribueeSiVictoire = eMC2_GainIf_V;
		this.mobilesVague = MobWave;
		this.obstaclesVagues = ObsWave;
		this.etatVague = EtatVague.Attente;
	}

	// the geters
	public int getEnergieAttribueeSiVictoire() {
		return this.energieAttribueeSiVictoire;
	}
	
	public ArrayList<Mobile> getMobilesVague() {
		return this.mobilesVague;
	}
	
	public ArrayList<Obstacle> getObstaclesVagues() {
		return this.obstaclesVagues;
	}

	// the seters
	public void setEnergieAttribueeSiVictoire(int eMC2_GainIf_V) {
		this.energieAttribueeSiVictoire = eMC2_GainIf_V;
	}
	
	public void setMobilesVague(ArrayList<Mobile> MobWave) {
		this.mobilesVague = MobWave;
	}
	
	public void setObstaclesVagues(ArrayList<Obstacle> ObsWave) {
		this.obstaclesVagues = ObsWave;
	}

	public int getNbEntres() {
		return nbEntres;
	}

	public void setNbEntres(int nbEntres) {
		this.nbEntres = nbEntres;
	}

	public int getNbSortis() {
		return nbSortis;
	}

	public void setNbSortis(int nbSortis) {
		this.nbSortis = nbSortis;
	}

	private void afficher() {
		for (Mobile m : mobilesVague) {
			System.out.println(m);
		}
		for (Obstacle o : obstaclesVagues) {
			System.out.println(o);
		}
	}

	public boolean isStarted() {
		return etatVague==EtatVague.EnCours;
	}
	
	public void render(GameContainer c, Graphics g) {
		if (etatVague == EtatVague.EnCours) {
			for (Mobile m : mobilesVague) {
				m.render(c,g);
			}
			for (Obstacle o : obstaclesVagues) {
				o.render(c, g);
			}
		}
	}

	public void init(Niveau niveau, int nbSortis) {
		this.niveau = niveau;
		this.nbEntres = 0;
		this.nbSortis = nbSortis;
		this.nbElimine = 0;
		this.nbTours = 0;
		this.etatVague = EtatVague.EnCours;		
	}

	public void update(GameContainer c, int delta) {
		boolean finTour = this.updateTour(c, delta);
		this.afficher();
		System.out.println("Mobiles entres : " + nbEntres);
		System.out.println("Mobiles sortis : " + nbSortis);
		System.out.println("Mobiles elimines : " + nbElimine);
		if (finTour) {
			if (nbSortis >= niveau.getNbMobilesPourPerdre()) {
				this.etatVague = EtatVague.Perdu;
			} else if (nbElimine == mobilesVague.size()) {
				this.etatVague = EtatVague.Gagne;
			}
		}
	}

	private boolean updateTour(GameContainer c, int delta) {
		
		boolean tourSuivant = this.isTourFinished();
		
		if (tourSuivant) {
			this.initAllAlive();
			this.enterNext();
			this.nbTours++;
			System.out.println("Initialisation du tour n°" + nbTours);
		} else {
			this.enterNext();
			this.updateAllAlive(c, delta);
			this.updateStats();
			System.out.println("Tour n°" + nbTours + " en cours");
		}
		return tourSuivant;		
	}

	private void updateStats() {
		//on compte les mobile elimines et sortis
		int nbE = 0;
		int nbS = 0;
		for(int i = 0; i < nbEntres; i++) {
			Mobile m = mobilesVague.get(i);
			if (!m.isAlive()) {
				nbE++;
				if (m.isOut()) {
					nbS++;
				}
			}
		}
		this.nbElimine = nbE;
		this.nbSortis = nbS;
		
		
	}

	private void updateAllAlive(GameContainer c, int delta) {
		boolean mobilesFinished = true;
		for(int i = 0; i < nbEntres; i++) {
			Mobile m = mobilesVague.get(i);
			if (m.isAlive() && !m.isFinished()) {
				m.update(c, delta);
				mobilesFinished = false;
			}			
		}
		if (mobilesFinished) {
			for (Obstacle o : obstaclesVagues) {
				if (o.isAlive() && !o.isFinished()) {
					o.update(c, delta);
				}
			}
		}		
	}

	private void enterNext() {
		if (this.nbEntres < mobilesVague.size()) {
			Mobile m = mobilesVague.get(nbEntres);
			if (m.getVolume() + m.getEntree().getVolume() <= m.getEntree().getVolumeMax()) {
				this.nbEntres++;
				m.setAlive(true);
				m.setCaseActu(m.getEntree());
				m.initTour();
				System.out.println(m.getName() + " est entree");
			}
		}
		
	}

	private void initAllAlive() {
		for(int i = 0; i < nbEntres; i++) {
			if (mobilesVague.get(i).isAlive()) {
				mobilesVague.get(i).initTour();
			}
		}
		for (Obstacle o : obstaclesVagues) {
			if (o.isAlive()) {
				o.initTour();
			}
		}
		
	}

	private boolean isTourFinished() {
		boolean tourFini = true;
		for(int i = 0; i < nbEntres; i++) {
			if (mobilesVague.get(i).isAlive()) {
				if (!mobilesVague.get(i).isFinished()) {
					tourFini = false;
				}
			}
		}
		for (Obstacle o : obstaclesVagues) {
			if (o.isAlive()) {
				if (!o.isFinished()) {
					tourFini = false;
				}
			}
		}
		return tourFini;
	}

	public EtatVague getEtatVague() {
		return etatVague;
	}

	public void setEtatVague(EtatVague etatVague) {
		this.etatVague = etatVague;
	}

	public void addObstacle(Obstacle obs) {
		this.obstaclesVagues.add(obs);
		
	}

	public void retirerObstacle(Obstacle obs) {
		this.obstaclesVagues.remove(obs);
	}
}
