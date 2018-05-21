package n7.towerDefense.game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public abstract class Element {

	protected String name;
	protected boolean alive;
	protected Image img;
	protected Animation elimination;
	protected Sound sonElimination;
	protected EtatTour etatTour;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	private float caseWidth;
	private float caseHeight;
	
	public Element(String _name) {
		this.name = _name;
		this.alive = false;
		this.etatTour = EtatTour.TourFini;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public void activer() {}
	
	public void kill() {
		this.alive = false;
		this.etatTour = EtatTour.TourFini;
		System.out.println(this.name + " a été détruit");
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public String toString() {
		return this.name;
	}
	
	public Image getImg() {
		return this.img;
	}
	
	public void initImg(float width, float height) throws SlickException {
		this.x = 0;
		this.y = 0;
		this.caseWidth = width;
		this.caseHeight = height;
		this.width = width;
		this.height = height;
	}

	public EtatTour getEtatTour() {
		return etatTour;
	}

	public void setEtatTour(EtatTour etatTour) {
		this.etatTour = etatTour;
	}
	
	public boolean isFinished() {
		return etatTour == EtatTour.TourFini;
	}
	
	public boolean estTouche(float X, float Y) {
		return X >= x- width/2 && X <= x + width/2 && Y >= y - height/2 && Y <= y + height/2;
	}

	
	public abstract void update(GameContainer c, int delta);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	public void render(GameContainer c, Graphics g) {}
	
	public boolean isInside(Case c) {
		return x>= c.getNoColonne()*caseWidth && x<= (c.getNoColonne()+1)*caseWidth
					&& y >= c.getNoLigne()*caseHeight && y<= (c.getNoLigne()+1)*caseHeight;
		
	}
	
	public void jouerSon() {
		if (this.sonElimination != null) {
			this.sonElimination.play();
		}		
	}

	public Animation getElimination() {
		return elimination;
	}

	public void setElimination(Animation elimination) {
		this.elimination = elimination;
	}

	public Sound getSonElimination() {
		return sonElimination;
	}

	public void setSonElimination(Sound sonElimination) {
		this.sonElimination = sonElimination;
	}
	
}
