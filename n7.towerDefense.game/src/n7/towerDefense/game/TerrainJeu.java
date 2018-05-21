package n7.towerDefense.game;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class TerrainJeu {

	// �a utilise des cases
	private int nbLignes;
	private int nbColonnes;
	private Case[][] tabCases;
	
	// le constructeur
	public TerrainJeu(Case[][] cases, int nbL, int nbC) {
		this.nbColonnes = nbC;
		this.nbLignes = nbL;
		this.tabCases = cases;
		for(int i =0; i<nbL; i++) {
			for(int j =0; j<nbC; j++) {
				cases[i][j].setTerrain(this);
			}
		}
	}
	


	public ArrayList<Case> getChemin(Case depart, Case arrivee) {
		// commence par liste vide
		ArrayList<Case> chemin = new ArrayList<Case>();
		chemin.add(depart);
		//on cherche les autres cases
		while (!(chemin.get(chemin.size() - 1)).equals(arrivee)) {
			ArrayList<Case> voisins = (chemin.get(chemin.size() - 1)).getCasesAutour();
			for (Case c : voisins) {
				if (c.getNatureDeTerrain().getType() == NatureTerrainType.Chemin || c.getNatureDeTerrain().getType() == NatureTerrainType.Sortie) {
					if (chemin.size() == 1) {
						chemin.add(c);
					} else if (!c.equals(chemin.get(chemin.size() - 2))) {
						chemin.add(c);
					}
				}
			}
		}
		return chemin;
	}



	public int getNbLignes() {
		return nbLignes;
	}



	public void setNbLignes(int nbLignes) {
		this.nbLignes = nbLignes;
	}



	public int getNbColonnes() {
		return nbColonnes;
	}



	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}



	public Case[][] getTabCases() {
		return tabCases;
	}



	public void setTabCases(Case[][] tabCases) {
		this.tabCases = tabCases;
	}



	public void render(GameContainer c, Graphics g) {
		for (int i=0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				tabCases[i][j].render(c, g);
			}
		}
		
	}
	
	public Case getCase(GameContainer c, float x, float y) {
		return tabCases[(int) Math.floor(y*nbLignes/c.getHeight())][(int) Math.floor(x*nbColonnes/c.getWidth())];
	}
	
	public ElementLanceur getTouche(GameContainer c, float x, float y) {
		Case laCase = this.getCase(c, x, y);
		ArrayList<ElementLanceur> cibles = laCase.getElementLanceurs();
		ElementLanceur res = null;
		for (ElementLanceur e : cibles) {
			if (e.estTouche(x,y)) {
				res = e;
			}
		}
		return res;
		
	}

}
