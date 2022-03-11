package igra;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scena extends Canvas implements Runnable {

	private Igra igra;
	private Thread nit;
	private KruznaFigura igrac;
	private List<KruznaFigura> figure = new ArrayList<>();

	public Scena(Igra igra) {
		this.igra = igra;
		nit = null;
		igrac = null;
		figure = new ArrayList<>();

	}

	public synchronized void dodaj(KruznaFigura f) {
		figure.add(f);
	}

	public synchronized Igrac getIgrac() {
		return (Igrac) igrac;
	}

	public synchronized void izbaci(KruznaFigura kruznaFigura) {
		figure.remove(kruznaFigura);
	}

	public void prekini() {
		if (nit != null)
			nit.interrupt();
	}

	public void pokreni() {
		Vektor centar = new Vektor(getWidth() / 2, getHeight() * 9 / 10);
		Vektor brzina = new Vektor(0, 0);
		dodaj(igrac = new Igrac(centar, 30, brzina, this));
		nit = new Thread(this);
		nit.start();
	}

	private synchronized void proveriSudare() {
		for (int i = 0; i < figure.size() - 1; i++)
			for (int j = i + 1; j < figure.size(); j++) {
				if (Krug.preklapajuSe(figure.get(i), figure.get(j))) {
					figure.get(i).sudaranje(figure.get(j));
					figure.get(j).sudaranje(figure.get(i));
				}
			}
	}

	private void azuriraj() {
		synchronized (this) {
			for (int i = 0; i < figure.size(); i++) {
				figure.get(i).obavesti(60.0 / 1000);
			}
		}
		proveriSudare();
	}

	@Override
	public void paint(Graphics g) {
		synchronized (this) {
			for (int i = 0; i < figure.size(); i++)
				figure.get(i).crtaj(this);
		}
	}

	@Override
	public void run() {
		Vektor brzina = new Vektor(0., 60.);
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(60);
				Random random = new Random(System.currentTimeMillis());
				if (Math.random() <= 0.1) {
					Vektor cen = new Vektor(random.nextInt(this.getWidth()), 20);
					KruznaFigura k = new Balon(cen, 20.0, brzina, this);
					brzina.pomnozi(1.01);
					dodaj(k);
				}
				azuriraj();
				repaint();
			}
		} catch (InterruptedException e) {
		}
	}

}
