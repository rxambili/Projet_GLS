package n7.towerDefense.game;
import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class Case {

	private String name;
	
	private int noLigne;
	
	private int noColonne;
	
	private int energie;
	
	private int volumeMax;
	
	private int volume;
	
	private NatureTerrain natureDeTerrain;
	
	private TerrainJeu terrain;
	
	private ArrayList<ElementLanceur> elementLanceurs;

	// le constructeur (on initialise toutles attributs)
	public Case(String nom, int nL, int nC, int eMC2, int vMax, NatureTerrain fieldNature) {
		this.name = nom;
		this.noLigne = nL;
		this.noColonne = nC;
		this.energie = eMC2;
		this.volumeMax = vMax;
		this.natureDeTerrain = fieldNature;
		this.volume = 0;
		this.elementLanceurs = new ArrayList<ElementLanceur>();
	}

	// the geters
	public String getName() {
		return this.name;
	}
	
	public int getNoLigne() {
		return this.noLigne;
	}
	
	public int getNoColonne() {
		return this.noColonne;
	}
	
	public int getEnergie() {
		return this.energie;
	}
	public int getVolumeMax() {
		return this.volumeMax;
	}
	
	public NatureTerrain getNatureDeTerrain() {
		return this.natureDeTerrain;
	}
	
	// the seters
	public void setName(String nom) {
		this.name = nom;
	}
	
	public void setNoLigne(int nL) {
		this.noLigne = nL;
	}
	
	public void setNoColonne(int nC) {
		this.noColonne = nC;
	}
	
	public void setEnergie(int eMC2) {
		this.energie = eMC2;
	}
	public void setVolumeMax(int vMax) {
		this.volumeMax = vMax;
	}
	
	public void setNatureDeTerrain(NatureTerrain fieldNature) {
		this.natureDeTerrain = fieldNature;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public TerrainJeu getTerrain() {
		return this.terrain;
	}

	public void setTerrain(TerrainJeu field) {
		this.terrain = field;
	}
	
	public ArrayList<Case> getCasesAutour() {
		ArrayList<Case> caseAutour = new ArrayList<Case>();
		Case[][] cases = this.getTerrain().getTabCases();
		if (this.noLigne == 0 && this.noColonne == 0) {
			caseAutour.add(cases[1][0]);
			caseAutour.add(cases[0][1]);				
		} else if (this.noLigne == this.getTerrain().getNbLignes() && this.noColonne == this.getTerrain().getNbColonnes()){
			caseAutour.add(cases[this.noLigne][this.noColonne - 1]);
			caseAutour.add(cases[this.noLigne - 1][this.noColonne]);
		} else if (this.noLigne == 0 ) {
			caseAutour.add(cases[0][this.noColonne + 1]);
			caseAutour.add(cases[0][this.noColonne - 1]);
			caseAutour.add(cases[1][this.noColonne]);
		} else if (this.noColonne == 0) {
			caseAutour.add(cases[this.noLigne + 1][0]);
			caseAutour.add(cases[this.noLigne - 1][0]);
			caseAutour.add(cases[this.noLigne][1]);
		} else if (this.noLigne == this.getTerrain().getNbLignes()) {
			caseAutour.add(cases[this.noLigne][this.noColonne + 1]);
			caseAutour.add(cases[this.noLigne][this.noColonne - 1]);
			caseAutour.add(cases[this.noLigne-1][this.noColonne]);
		} else if (this.noColonne == this.getTerrain().getNbColonnes()) {
			caseAutour.add(cases[this.noLigne + 1][this.noColonne]);
			caseAutour.add(cases[this.noLigne - 1][this.noColonne]);
			caseAutour.add(cases[this.noLigne][this.noColonne - 1]);
		} else {
			caseAutour.add(cases[this.noLigne][this.noColonne - 1]);
			caseAutour.add(cases[this.noLigne - 1][this.noColonne]);
			caseAutour.add(cases[this.noLigne][this.noColonne + 1]);
			caseAutour.add(cases[this.noLigne + 1][this.noColonne]);
		}
		return caseAutour;
	}

	public ArrayList<ElementLanceur> getElementLanceurs() {
		return this.elementLanceurs;
	}

	public void setElementLanceurs(ArrayList<ElementLanceur> elts) {
		this.elementLanceurs = elts;
	}
	
	public void ajoutElementLanceur(ElementLanceur elementLanceur) {
		this.elementLanceurs.add(elementLanceur);
	}
	
	public void enleverElementLanceur(ElementLanceur elementLanceur) {
		this.elementLanceurs.remove(elementLanceur);		
	}
	
	public double distance(Case _case) {
		double result_x = Math.pow(_case.getNoColonne() - this.noColonne,2);
		double result_y = Math.pow(_case.getNoLigne() - this.noLigne,2);
		return Math.sqrt(result_x + result_y);
	}
	
	public String toString() {
		return "Case[" + noLigne + "][" + noColonne + "]";
	}

	public void render(GameContainer c, Graphics g) {
		natureDeTerrain.render(c, g, noLigne, noColonne);	
	}

	public boolean isEmpty() {
		return this.elementLanceurs.isEmpty();
	}
	
}
