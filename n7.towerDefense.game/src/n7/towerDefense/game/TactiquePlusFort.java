/**
 * 
 */
package n7.towerDefense.game;

import java.util.ArrayList;

/**
 * @author rxambili
 *
 */
public class TactiquePlusFort implements Tactique {

	/**
	 * 
	 */
	public TactiquePlusFort() {
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
		int energieMax = 0;
		for(int i=0; i < terrain.getNbLignes(); i++) {
			for(int j=0; j < terrain.getNbColonnes(); j++) {
				
				NatureTerrainType typeTerrain = cases[i][j].getNatureDeTerrain().getType();
				if ((lanceur instanceof Mobile && typeTerrain == NatureTerrainType.Campement) ||
						(lanceur instanceof Obstacle && (typeTerrain == NatureTerrainType.Chemin || typeTerrain == NatureTerrainType.Entree
							|| typeTerrain == NatureTerrainType.Sortie)))	{
					
					ArrayList<ElementLanceur> elements = cases[i][j].getElementLanceurs();
					if (depart.distance(cases[i][j]) <= p.getPortee() && !elements.isEmpty()) {
						for (ElementLanceur e : elements) {
							if (e.isAlive()) {
								if (cible == null) {
									cible = e;
									energieMax = cible.getEnergieActuelle();
								} else if (e.getEnergieActuelle() > energieMax) {
									energieMax = e.getEnergieActuelle();
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
