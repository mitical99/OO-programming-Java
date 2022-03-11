package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Krug {
	protected Vektor centar;
	protected Color boja;
	protected double precnik;

	public Krug(Vektor centar, Color boja, double precnik) {
		super();
		this.centar = centar;
		this.boja = boja;
		this.precnik = precnik;
	}

	public static boolean preklapajuSe(Krug k1, Krug k2) {
		double distance = Math.sqrt(
				Math.pow(k1.centar.getX() - k2.centar.getX(), 2) + Math.pow(k1.centar.getY() - k2.centar.getY(), 2));
		return distance <= (k1.precnik + k2.precnik) / 2;
	}

	public void crtaj(Scena scena) {
		Graphics g = scena.getGraphics();
		g.setColor(boja);
		g.fillOval((int) (centar.getX() - (int) precnik / 2), (int) (centar.getY() - (int) precnik / 2), (int) precnik,
				(int) precnik);
	}
}
