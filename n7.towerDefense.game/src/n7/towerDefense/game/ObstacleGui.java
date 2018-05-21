package n7.towerDefense.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.MouseOverArea;

public class ObstacleGui extends MouseOverArea {
	

	private Obstacle obs;
	private Partie partie;
	private boolean dragged;
	private boolean selected;
	
	
	public ObstacleGui(Obstacle obs, Partie p) {
		super(p.getContainer(), obs.getImg(),
				(int) (obs.getX() - obs.getWidth()/2),
				(int) (obs.getY() - obs.getHeight()/2));
		this.obs = obs;
		this.partie = p;
		this.dragged = false;
		this.selected = false;
		this.setMouseOverColor(new Color(0.6f,0.6f,0.6f,1f));
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if (partie.getEtat() == EtatPartie.EnPause && this.isMouseOver()) {
			this.setX(newx - obs.getWidth()/2);
			this.setY(newy - obs.getHeight()/2);
			this.dragged = true;
		}
	}
	
	public void mouseClicked(int button, int mx, int my, int clickCount) {
		if (partie.getEtat() == EtatPartie.EnPause  && this.isMouseOver()) {
			if (button == Input.MOUSE_LEFT_BUTTON && clickCount == 2) {
				System.out.println("COUCOU");
				if (selected) {
					this.selected = false;
				} else {
					this.selected = true;
				}
			}
		}
	}
		
	public void mouseReleased(int button, int mx, int my) {
		if (partie.getEtat() == EtatPartie.EnPause  && this.isMouseOver()) {
			switch (button) {
			case Input.MOUSE_LEFT_BUTTON :
				if (this.dragged) {
					Case newCase = partie.getTerrainDeJeu().getCase(partie.getContainer(), mx, my);
					if (!newCase.equals(obs.getCaseActu())) {
						if (newCase.getNatureDeTerrain().getType() == NatureTerrainType.Campement && newCase.isEmpty()) {
							if (partie.getEnergieDispo() >= 2) {
								partie.setEnergieDispo(partie.getEnergieDispo() - 2);
								obs.getCaseActu().enleverElementLanceur(obs);
								obs.setCaseActu(newCase);
							}
						}
					}
					this.dragged = false;
					this.setX(obs.getX() - obs.getWidth()/2);
					this.setY(obs.getY() - obs.getHeight()/2);
				}
				break;
			case Input.MOUSE_RIGHT_BUTTON :
				if (this.dragged) {
					Case newCase = partie.getTerrainDeJeu().getCase(partie.getContainer(), mx, my);
					if (!newCase.equals(obs.getCaseActu())) {
						if (newCase.getNatureDeTerrain().getType() == NatureTerrainType.Campement && newCase.isEmpty()) {
							if (partie.getEnergieDispo() >= obs.getEnergieMax()) {
								partie.setEnergieDispo(partie.getEnergieDispo() - obs.getEnergieMax());
								Obstacle newObs = new Obstacle(obs, newCase);
								partie.addObstacleVague(newObs);
							}
						}
					}
					this.dragged = false;
					this.setX(obs.getX() - obs.getWidth()/2);
					this.setY(obs.getY() - obs.getHeight()/2);
				}
			}
		}
	}
	
	public void keyPressed(int key, char c) {
		if (selected) {
			switch(key) {
			case Input.KEY_SPACE :
				if (partie.getEnergieDispo() > 0 && obs.getEnergieActuelle() < obs.getEnergieMax()) {
					obs.setEnergieActuelle(obs.energieActuelle + 1);
					partie.setEnergieDispo(partie.getEnergieDispo() - 1);
				}
				break;
			case Input.KEY_DELETE : 
				if (obs.isAlive()) {
					obs.kill();
					partie.setEnergieDispo(partie.getEnergieDispo() + obs.getEnergieActuelle());
					partie.retirerObstacleVague(obs);
				}
				break;
			default :
				break;
			}
		}
	}
	
	public void render(GameContainer c, Graphics g) {
		if (obs.isAlive()) {
			super.render(c, g);
			g.setColor(new Color(15,15,15,100));
			g.fillRect(this.getX() + obs.getWidth()/4, this.getY(), obs.getWidth()/2, 20);
			g.setColor(new Color(255,50,50));
			g.fillRect(this.getX() + obs.getWidth()/4, this.getY(), obs.getWidth()/2 * obs.getEnergieActuelle()/obs.getEnergieMax(), 20);
			g.setColor(new Color(255,255,255));
			g.drawString(obs.getEnergieActuelle() + "/" + obs.getEnergieMax(), this.getX() + obs.getWidth()/4 + 10, this.getY());
			if (selected) {
				g.setColor(new Color(255,255,255));
				g.drawRect(this.getX(), this.getY(), obs.getWidth(), obs.getHeight());
			}
		}
	}

	public boolean isDragged() {
		return dragged;
	}

	public void setDragged(boolean dragged) {
		this.dragged = dragged;
	}
}
