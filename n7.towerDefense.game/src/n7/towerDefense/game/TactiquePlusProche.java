/**
 * 
 */
package n7.towerDefense.game;

import java.util.ArrayList;

/**
 * @author rxambili
 *
 */
public class TactiquePlusProche implements Tactique {

	/**
	 * 
	 */
	public TactiquePlusProche() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see n7.towerDefense.game.Tactique#viser()
	 */
	@Override
	public ElementLanceur viser(ElementLanceur lanceur, Projectile p) {
		Case depart = lanceur.getCaseActu();
		TerrainJeu terrain = depart.getTerrain();
		Case[][] cases = terrain.getTabCases();
		Case caseCible = null;
		ElementLanceur cible = null;
		for(int i=0; i < terrain.getNbLignes(); i++) {
			for(int j=0; j < terrain.getNbColonnes(); j++) {
				
				NatureTerrainType typeTerrain = cases[i][j].getNatureDeTerrain().getType();
				if ((lanceur instanceof Mobile && typeTerrain == NatureTerrainType.Campement) ||
						(lanceur instanceof Obstacle && (typeTerrain == NatureTerrainType.Chemin || typeTerrain == NatureTerrainType.Entree
							|| typeTerrain == NatureTerrainType.Sortie)))	{
					
					ArrayList<ElementLanceur> adversaires = cases[i][j].getElementLanceurs();
					System.out.println(depart.distance(cases[i][j]) + "depart : " + depart + "; cible : "+ cases[i][j]);
					
					if (depart.distance(cases[i][j]) <= p.getPortee() && !adversaires.isEmpty()) {
						if (caseCible == null) {
							caseCible = cases[i][j];
							cible = adversaires.get(0);
						} else if (depart.distance(caseCible) > depart.distance(cases[i][j])) {
							caseCible = cases[i][j];
							cible = adversaires.get(0);
						}
					}
				} 
			}
		}
		return cible;
	}

}
