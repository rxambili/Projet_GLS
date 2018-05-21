/**
 * 
 */
package n7.towerDefense.game;


/**
 * @author rxambili
 *
 */
public enum NatureTerrainType {
	
	Entree("Case d'entree", "ressources/cases/entree.png"),
	Sortie("Case de sortie", "ressources/cases/sortie.png"),
	Chemin("Case de chemin", "ressources/cases/chemin.png"),
	Campement("Case de campement", "ressources/cases/campement.png"),
	Decoration("Case de decoration", "ressources/cases/deco.png");
	
	private String nom;
	private String cheminImg;
	
	NatureTerrainType(String nom, String cheminImg) {
		this.nom = nom;
		this.cheminImg = cheminImg;
	}
	
	public String toSring() {
		return this.nom;
	}
	
	public String getCheminImg() {
		return this.cheminImg;
	}
	
}
