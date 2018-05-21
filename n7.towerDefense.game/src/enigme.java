import n7.towerDefense.game.*;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class enigme {
	public static void main(String[] args) throws SlickException {
		
		NatureTerrain montagne = new NatureTerrain("montagne", NatureTerrainType.Decoration);
		NatureTerrain entry = new NatureTerrain("entry", NatureTerrainType.Entree);
		NatureTerrain route = new NatureTerrain("route", NatureTerrainType.Chemin);
		NatureTerrain exit = new NatureTerrain("exit", NatureTerrainType.Sortie);
		NatureTerrain garage = new NatureTerrain("garage", NatureTerrainType.Campement);
		
		// Terrain de jeu
		Case[][] cases = new Case[3][5]; 
		cases[0][0] = new Case("montagne1", 0, 0, 1, 0, montagne);
		cases[0][1] = new Case("montagne2", 0, 1, 1, 0, montagne);
		cases[0][2] = new Case("montagne3", 0, 2, 1, 0, montagne);
		cases[0][3] = new Case("montagne4", 0, 3, 1, 0, montagne);
		cases[0][4] = new Case("montagne5", 0, 4, 1, 0, montagne);
		cases[1][0] = new Case("debut", 1, 0, 1, 1, entry);
		cases[1][1] = new Case("dd1", 1, 1, 1, 1, route);
		cases[1][2] = new Case("dd2", 1, 2, 1, 1, route);
		cases[1][3] = new Case("dd3", 1, 3, 1, 1, route);
		cases[1][4] = new Case("fin", 1, 4, 1, 1, exit);
		cases[2][0] = new Case("tu1", 2, 0, 0, 0, garage);
		cases[2][1] = new Case("tu2", 2, 1, 0, 0, garage);
		cases[2][2] = new Case("gar", 2, 2, 0, 0, garage);
		cases[2][3] = new Case("tu3", 2, 3, 0, 0, garage);
		cases[2][4] = new Case("td", 2, 4, 0, 0, garage);
		TerrainJeu terrain = new TerrainJeu(cases, 3, 5);
		
		// Liste des elements
		Projectile P1 = new Projectile("P", 1, 1, 1, 1);
		ArrayList<Projectile> projectilesLancables1 = new ArrayList<Projectile>();
		projectilesLancables1.add(P1);
		Projectile P2 = new Projectile("P", 1, 1, 1, 1);
		ArrayList<Projectile> projectilesLancables2 = new ArrayList<Projectile>();
		projectilesLancables2.add(P2);
		Projectile P3 = new Projectile("P", 1, 1, 1, 1);
		ArrayList<Projectile> projectilesLancables3 = new ArrayList<Projectile>();
		projectilesLancables3.add(P3);
		Projectile P4 = new Projectile("P", 1, 1, 1, 1);
		ArrayList<Projectile> projectilesLancables4 = new ArrayList<Projectile>();
		projectilesLancables4.add(P4);
		Mobile M1 = new Mobile("M1", 1, new TactiquePlusProche(), projectilesLancables1, 1, 1, cases[1][0], cases[1][4]);
		Mobile M2 = new Mobile("M2", 1, new TactiquePlusProche(), projectilesLancables2, 1, 2, cases[1][0], cases[1][4]);
		Mobile M3 = new Mobile("M3", 1, new TactiquePlusProche(), projectilesLancables3, 1, 3, cases[1][0], cases[1][4]);
		Obstacle O = new Obstacle("O", 1, new TactiquePlusFaible(), projectilesLancables4, cases[2][2]);
		
		ArrayList<Element> listeElements = new ArrayList<Element>();
		listeElements.add(P1);
		listeElements.add(P2);
		listeElements.add(P3);
		listeElements.add(P4);
		listeElements.add(M1);
		listeElements.add(M2);
		listeElements.add(M3);
		listeElements.add(O);
		listeElements.add(montagne);
		listeElements.add(entry);
		listeElements.add(route);
		listeElements.add(exit);
		listeElements.add(garage);
		
		
		// Liste des niveaux
		ArrayList<Mobile> mobilesVague = new ArrayList<Mobile>();
		mobilesVague.add(M1);
		mobilesVague.add(M2);
		mobilesVague.add(M3);
		ArrayList<Obstacle> obstaclesVague = new ArrayList<Obstacle>();
		obstaclesVague.add(O);
		Vague vague = new Vague(3, mobilesVague, obstaclesVague);
		ArrayList<Vague> listeVagues = new ArrayList<Vague>();
		listeVagues.add(vague);
		Niveau niveau1 = new Niveau(1, 0, 2);
		ArrayList<Niveau> listeNiveaux = new ArrayList<Niveau>();
		listeNiveaux.add(niveau1);
		
		// Partie finale
		Partie enigme = new Partie(listeElements, listeVagues, terrain, listeNiveaux);
		AppGameContainer app = new AppGameContainer(enigme);
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
