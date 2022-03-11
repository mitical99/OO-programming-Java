package igra;

import java.awt.Color;

public class KruznaFigura extends Krug {

	protected Vektor brzina;
	protected Scena scena;

	public KruznaFigura(Vektor centar, Color boja, double precnik, Vektor brzina, Scena scena) {
		super(centar, boja, precnik);
		this.brzina = brzina;
		this.scena = scena;
	}

	public void obavesti(double period) {
		Vektor pomeraj = brzina.clone();
		pomeraj.pomnozi(period);
		centar.saberi(pomeraj);
		if (centar.getX() < 0 || centar.getX() > scena.getWidth() || centar.getY() < 0
				|| centar.getY() > scena.getHeight())
			scena.izbaci(this);
		// else crtaj(scena);
	}

	public void sudaranje(KruznaFigura k) {

	}
}
