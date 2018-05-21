import java.util.ArrayList;
import n7.towerDefense.game.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class enigme_sujet {
	public static void main(String[] args) throws SlickException{
		NatureTerrain montagne = new NatureTerrain("montagne", NatureTerrainType.Decoration);
		NatureTerrain entry = new NatureTerrain("entry", NatureTerrainType.Entree);
		NatureTerrain route = new NatureTerrain("route", NatureTerrainType.Chemin);
		NatureTerrain exit = new NatureTerrain("exit", NatureTerrainType.Sortie);
		NatureTerrain garage = new NatureTerrain("garage", NatureTerrainType.Campement);

		// Terrain de jeu
		Case[][] cases = new Case[3][5];
		cases[0][0] = new Case("uu", 0, 0, 0, 0, montagne);
		cases[0][1] = new Case("ud", 0, 1, 0, 0, montagne);
		cases[0][2] = new Case("ut", 0, 2, 0, 0, montagne);
		cases[0][3] = new Case("uq", 0, 3, 0, 0, montagne);
		cases[0][4] = new Case("uc", 0, 4, 0, 0, montagne);
		cases[1][0] = new Case("debut", 1, 0, 1, 1, entry);
		cases[1][1] = new Case("dd", 1, 1, 1, 1, route);
		cases[1][2] = new Case("dt", 1, 2, 1, 1, route);
		cases[1][3] = new Case("dq", 1, 3, 1, 1, route);
		cases[1][4] = new Case("fin", 1, 4, 1, 1, exit);
		cases[2][0] = new Case("tu", 2, 0, 0, 0, garage);
		cases[2][1] = new Case("td", 2, 1, 0, 0, garage);
		cases[2][2] = new Case("tt", 2, 2, 0, 0, garage);
		cases[2][3] = new Case("tq", 2, 3, 0, 0, garage);
		cases[2][4] = new Case("tc", 2, 4, 0, 0, garage);
		TerrainJeu terrain = new TerrainJeu(cases, 3, 5);

		// Liste des elements
		Projectile P = new Projectile("P", 1, 1, 1, 1);
		ArrayList<Projectile> projectilesLancables_M1 = new ArrayList<Projectile>();
		projectilesLancables_M1.add(P);
		Mobile M1 = new Mobile("M1", 1, new TactiquePlusProche(), projectilesLancables_M1, 1, 1, cases[1][0], cases[1][4]);
		ArrayList<Projectile> projectilesLancables_M2 = new ArrayList<Projectile>();
		projectilesLancables_M2.add(P);
		Mobile M2 = new Mobile("M2", 1, new TactiquePlusProche(), projectilesLancables_M2, 1, 2, cases[1][0], cases[1][4]);
		ArrayList<Projectile> projectilesLancables_M3 = new ArrayList<Projectile>();
		projectilesLancables_M3.add(P);
		Mobile M3 = new Mobile("M3", 1, new TactiquePlusProche(), projectilesLancables_M3, 1, 3, cases[1][0], cases[1][4]);
		ArrayList<Projectile> projectilesLancables_O = new ArrayList<Projectile>();
		projectilesLancables_O.add(P);
		Obstacle O = new Obstacle("O", 1, new TactiquePlusFaible(), projectilesLancables_O, cases[2][2]);

		ArrayList<Element> listeElements = new ArrayList<Element>();
		listeElements.add(P);
		listeElements.add(M1);
		listeElements.add(M2);
		listeElements.add(M3);
		listeElements.add(O);
		listeElements.add(montagne);
		listeElements.add(entry);
		listeElements.add(route);
		listeElements.add(exit);
		listeElements.add(garage);

		// Liste des vagues
		ArrayList<Mobile> mobilesVague = new ArrayList<Mobile>();
		mobilesVague.add(M1);
		mobilesVague.add(M2);
		mobilesVague.add(M3);
		ArrayList<Obstacle> obstaclesVague = new ArrayList<Obstacle>();
		obstaclesVague.add(O);
		Vague vague1 = new Vague(3, mobilesVague, obstaclesVague);
		ArrayList<Vague> listeVagues = new ArrayList<Vague>();
		listeVagues.add(vague1);

		// Liste des niveaux
		Niveau niveau1 = new Niveau(1, 0, 2);
		ArrayList<Niveau> listeNiveaux = new ArrayList<Niveau>();
		listeNiveaux.add(niveau1);

		// Partie finale
		Partie enigme_sujet = new Partie(listeElements, listeVagues, terrain, listeNiveaux);
		AppGameContainer app = new AppGameContainer(enigme_sujet);
		int width;
		int height;
		float ratio = terrain.getNbLignes()/terrain.getNbColonnes();
		if (ratio > 1) {
			width = app.getScreenWidth();
			height = (int) (width*ratio);
			if (height > app.getScreenHeight()) {
				height = app.getScreenHeight();
			}
		} else {
			height = app.getScreenHeight();
			width = (int) (height/ratio);
			if (width > app.getScreenWidth()) {
				width = app.getScreenWidth();
			}
		}
		app.setDisplayMode(width, height, false);
		app.start();
	}
}
