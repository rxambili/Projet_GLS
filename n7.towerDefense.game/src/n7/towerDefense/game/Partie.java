package n7.towerDefense.game;

import java.util.*;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class Partie extends BasicGame{
	
	private static final String DEFAULT_CheminSon = "ressources/other/bell.ogg";


	// listeElements
	private Collection<Element> listeElements;
	
	private ArrayList<ObstacleGui> listeObstacleGui;
	
	// listeVagues
	private ArrayList<Vague> listeVagues;
	
	// terrainDeJeu
	private TerrainJeu terrainDeJeu;
	
	// listeNiveaux
	private ArrayList<Niveau> listeNiveaux;

	private GameContainer container;	
	
	private int noVague;

	private int niveauDifficulte;

	private EtatPartie etat;
	
	private int pauseTime;

	public int energieDispo;

	private int nbSortis;

	private Sound bell;
	
	// le constructeur
	public Partie(Collection<Element> listElts, ArrayList<Vague> listWaves, TerrainJeu PlayField, ArrayList<Niveau> listLvl) {
		super("TowerDef");
		this.listeElements = listElts;
		this.listeVagues = listWaves;
		this.terrainDeJeu = PlayField;
		this.listeNiveaux = listLvl;
		this.listeObstacleGui = new ArrayList<ObstacleGui>();
	}
	
	// the getters
	public Collection<Element> getListeElements() {
		return this.listeElements;
	}
	
	public ArrayList<Vague> getListeVagues() {
		return this.listeVagues;
	}
	
	public TerrainJeu getTerrainDeJeu() {
		return this.terrainDeJeu;
	}
	
	public ArrayList<Niveau> getListeNiveaux() {
		return this.listeNiveaux;
	}
	
	
	// the setters
	public void setListeElements(Collection<Element> listElts) {
		this.listeElements = listElts;
	}
	
	public void setListeVagues(ArrayList<Vague> listWaves) {
		this.listeVagues = listWaves;
	}
	
	public void setTerrainDeJeu(TerrainJeu PlayField) {
		this.terrainDeJeu = PlayField;
	}
	
	public void setListeNiveaux(ArrayList<Niveau> listLvl) {
		this.listeNiveaux = listLvl;
	}
	
	
	@Override
	public void render(GameContainer c, Graphics g) throws SlickException {
		switch (etat) {
		case EnCours :
			terrainDeJeu.render(c,g);
			listeVagues.get(noVague).render(c, g);
			g.drawString("Energie : " + this.energieDispo, 10, 30);
			int vieMax = this.listeNiveaux.get(niveauDifficulte).getNbMobilesPourPerdre();
			g.drawString("Vies : " + (vieMax - nbSortis), 10, 50);
			break;
		case EnPause :
			terrainDeJeu.render(c,g);
			g.drawString("Energie : " + this.energieDispo, 10, 30);
			int tempsRestant = this.listeNiveaux.get(niveauDifficulte).getDureePause() - this.pauseTime / 1000;
			g.drawString("Temps restant : " + tempsRestant, c.getWidth()/2 - 75, 30);
			vieMax = this.listeNiveaux.get(niveauDifficulte).getNbMobilesPourPerdre();
			g.drawString("Vies : " + (vieMax - nbSortis), 10, 50);
			for (ObstacleGui e : listeObstacleGui) {
				e.render(c,g);
			}
			break;
		case Perdu :
			g.setColor(new Color(255,255,255));
			g.drawString("Perdu!", c.getWidth()/2, c.getHeight()/2);
			break;
		case Gagne :
			g.setColor(new Color(255,255,255));
			g.drawString("Gagné!", c.getWidth()/2, c.getHeight()/2);
			break;
		}
		
	}
	
	private void initGui() {
		listeObstacleGui = new ArrayList<ObstacleGui>();
		for (Obstacle o : listeVagues.get(noVague).getObstaclesVagues()) {
			listeObstacleGui.add(new ObstacleGui(o, this));
		}
	}

	@Override
	public void init(GameContainer c) throws SlickException {
		this.container = c;
		this.noVague = 0;
		this.niveauDifficulte = 0;
		this.nbSortis = 0;
		this.etat = EtatPartie.EnPause;
		this.pauseTime = 0;
		this.energieDispo = listeNiveaux.get(niveauDifficulte).getEnergieInitiale();
		for (Element e : listeElements) {
			e.initImg(c.getWidth()/terrainDeJeu.getNbColonnes(), c.getHeight()/terrainDeJeu.getNbLignes());
		}
		this.initGui();
		this.bell = new Sound(Partie.DEFAULT_CheminSon);
	}

	@Override
	public void update(GameContainer c, int delta) throws SlickException {
		switch (etat) {
		case EnCours : 
			Vague vagueCour = listeVagues.get(noVague);
			if (!vagueCour.isStarted()) {
				vagueCour.init(listeNiveaux.get(this.niveauDifficulte), nbSortis);
			} else {
				vagueCour.update(c, delta);
				this.nbSortis = vagueCour.getNbSortis();
			}
			if (vagueCour.getEtatVague() == EtatVague.Perdu) {
				etat = EtatPartie.Perdu;
			} else if (vagueCour.getEtatVague() == EtatVague.Gagne) {
				noVague++;
				this.energieDispo = this.energieDispo + vagueCour.getEnergieAttribueeSiVictoire();
				System.out.println("Nombre de vagues terminees: " + noVague);
				if (noVague == listeVagues.size()) {
					etat = EtatPartie.Gagne;
				} else {
					this.propagerObstacles();
					this.initGui();
					etat = EtatPartie.EnPause;
				}
			}
			break;
		case EnPause :
			this.pauseTime = this.pauseTime + delta;
			System.out.println("En pause depuis " + this.pauseTime + " ms");
			int dureePause = this.listeNiveaux.get(this.niveauDifficulte).getDureePause() * 1000;
			
			if (this.pauseTime >= dureePause) {
				etat = EtatPartie.EnCours;
				bell.play();
				this.pauseTime = 0;
			}
			break;
			
		case Perdu :
			
			break;
			
		case Gagne :
			break;
		}
	}

	private void propagerObstacles() {
		Vague vaguePrec = listeVagues.get(noVague-1);
		for (Obstacle obsPropa : vaguePrec.getObstaclesVagues()) {
			if (obsPropa.isAlive()) {
				Vague vagueSuiv = listeVagues.get(noVague);
				boolean placePrise = false;
				for (Obstacle obs : vagueSuiv.getObstaclesVagues()) {
					if (obsPropa.getCaseActu().equals(obs.getCaseActu())) {
						placePrise = true;
					}
				}
				if (!placePrise) {
					vagueSuiv.addObstacle(obsPropa);
				}
			}
		}
		
	}

	public GameContainer getContainer() {
		return container;
	}

	public void setContainer(GameContainer container) {
		this.container = container;
	}

	public int getNoVague() {
		return noVague;
	}

	public void setNoVague(int noVague) {
		this.noVague = noVague;
	}

	public int getNiveauDifficulte() {
		return niveauDifficulte;
	}

	public void setNiveauDifficulte(int niveauDifficulte) {
		this.niveauDifficulte = niveauDifficulte;
	}

	public EtatPartie getEtat() {
		return etat;
	}

	public void setEtat(EtatPartie etat) {
		this.etat = etat;
	}

	public int getEnergieDispo() {
		return energieDispo;
	}

	public void setEnergieDispo(int energieDispo) {
		this.energieDispo = energieDispo;
	}

	public void retirerObstacleVague(Obstacle obs) {
		this.listeVagues.get(noVague).retirerObstacle(obs);		
	}

	public void addObstacleVague(Obstacle obs) {
		this.listeVagues.get(noVague).addObstacle(obs);
		this.listeElements.add(obs);
		ObstacleGui obsG = new ObstacleGui(obs,this);
		this.listeObstacleGui.add(obsG);
		
	}
	
}
