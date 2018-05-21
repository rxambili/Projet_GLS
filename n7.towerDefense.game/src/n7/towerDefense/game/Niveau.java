package n7.towerDefense.game;


public class Niveau {

	private int dureePause;
	private int energieInitiale;
	private int nbMobilesPourPerdre;
	
	// le constructeur
	public Niveau(int pauseDuration, int init_eMC2, int nbMobTillLose) {
		this.dureePause = pauseDuration;
		this.energieInitiale = init_eMC2;
		this.nbMobilesPourPerdre = nbMobTillLose;
	}

	// the geters
	public int getDureePause() {
		return this.dureePause;
	}
	
	public int getEnergieInitiale() {
		return this.energieInitiale;
	}
	
	public int getNbMobilesPourPerdre() {
		return this.nbMobilesPourPerdre;
	}

	// the seters
	public void setDureePause(int pauseDuration) {
		this.dureePause = pauseDuration;
	}
	
	public void setEnergieInitiale(int init_eMC2) {
		this.energieInitiale = init_eMC2;
	}
	
	public void setNbMobilesPourPerdre(int nbMobTillLose) {
		this.nbMobilesPourPerdre = nbMobTillLose;
	}
	
}
