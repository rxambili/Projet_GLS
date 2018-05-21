/**
 * 
 */
package n7.towerDefense.game;

import java.util.ArrayList;

/**
 * @author rxambili
 *
 */
public class TactiquePlusFaible implements Tactique {

	/**
	 * 
	 */
	public TactiquePlusFaible() {
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
		ElementLanceur cible = null;
		int energieMin = 0;
		for(int i=0; i < terrain.getNbLignes(); i++) {
			for(int j=0; j < terrain.getNbColonnes(); j++) {
				
				NatureTerrainType typeTerrain = cases[i][j].getNatureDeTerrain().getType();
				if ((lanceur instanceof Mobile && typeTerrain == NatureTerrainType.Campement) ||
						(lanceur instanceof Obstacle && (typeTerrain == NatureTerrainType.Chemin || typeTerrain == NatureTerrainType.Entree
							|| typeTerrain == NatureTerrainType.Sortie)))	{
					
					ArrayList<ElementLanceur> elements = cases[i][j].getElementLanceurs();
					
					System.out.println(depart.distance(cases[i][j]) + "depart : " + depart + "; cible : "+ cases[i][j]);
					
					if (depart.distance(cases[i][j]) <= p.getPortee() && !elements.isEmpty()) {
						for (ElementLanceur e : elements) {
							if (e.isAlive()) {
								if (cible == null) {
									cible = e;
									energieMin = cible.getEnergieActuelle();
								} else if (e.getEnergieActuelle() < energieMin) {
									energieMin = e.getEnergieActuelle();
									cible = e;
								}
							}
						}
					}
				}
			}
		}
		return cible;
	}

}
