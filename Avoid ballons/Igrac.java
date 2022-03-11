package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends KruznaFigura {

	public Igrac(Vektor centar, double precnik, Vektor brzina, Scena s) {
		super(centar, Color.GREEN, precnik, brzina, s);
	}

	@Override
	public void obavesti(double period) {
		// crtaj(scena);
	}

	public void pomeri(int pomeraj) {
		double x = this.centar.getX();
		if (((x + pomeraj - precnik / 2) <= 0) || ((x + pomeraj + precnik / 2) >= scena.getWidth()))
			return;
		else {
			Vektor pomerajVektor = new Vektor(pomeraj, 0);
			centar.saberi(pomerajVektor);

		}
	}

	@Override
	public void crtaj(Scena s) {
		super.crtaj(s);
		Graphics g = s.getGraphics();
		g.setColor(Color.BLUE);
		double x = centar.getX() - (int) precnik / 4;
		double y = centar.getY() - (int) precnik / 4;
		g.fillOval((int) x, (int) y, (int) precnik / 2, (int) precnik / 2);
	}

	@Override
	public void sudaranje(KruznaFigura k) {
		scena.prekini();
	}
}
