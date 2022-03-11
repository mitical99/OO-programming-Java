package igra;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mreza extends Panel implements Runnable {
	private Polje[][] polja;
	private Thread nit;
	private int size = 17;
	private Igra igra;
	private Figura igrac;
	private List<Figura> novcici;
	private List<Figura> tenkovi;

	public Mreza(int size, Igra igra) {
		super(new GridLayout(size, size, 0, 0));
		this.size = size;
		this.igra = igra;
		// setBackground(Color.GREEN);
		polja = new Polje[size][size];
		novcici = new ArrayList<>();
		tenkovi = new ArrayList<>();
		formiraj();
	}

	public Mreza(Igra igra) {
		this(17, igra);
	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < novcici.size(); i++)
			novcici.get(i).crtaj();
		for (int i = 0; i < tenkovi.size(); i++)
			tenkovi.get(i).crtaj();
		if (igrac != null)
			igrac.crtaj();
	}

	protected void zameniPolje(int i, int j) {
		this.remove(i * size + j);
		Polje f;
		if (igra.proveriTrava())
			f = new Trava(this);
		else
			f = new Zid(this);
		dodajOsluskivac(f);
		this.add(f, i * size + j);
		polja[i][j] = f;
		revalidate();
	}

	private void dodajOsluskivac(Polje p) {
		p.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				igra.requestFocus();
				if (!igra.getRezimIzmene())
					return;
				int[] poz = ((Polje) e.getComponent()).getPozicija();
				((Polje) e.getComponent()).getMreza().zameniPolje(poz[0], poz[1]);
			}
		});
	}

	private void formiraj() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				double rnd = Math.random();
				if (rnd < 0.8)
					polja[i][j] = new Trava(this);
				else
					polja[i][j] = new Zid(this);
			}
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				dodajOsluskivac(polja[i][j]);
				add(polja[i][j]);
				polja[i][j].repaint();
			}
	}

	public void inicijalizuj(int count) {
		if (igra.getRezimIzmene())
			return;
		int brojTenkova = count / 3;
		Random random = new Random(System.currentTimeMillis());
		novcici = new ArrayList<Figura>();
		while (count > 0) {
			int vr = random.nextInt(size), kol = random.nextInt(size);
			Figura novcic = new Novcic(polja[vr][kol]);
			if (!polja[vr][kol].moze(novcic))
				continue;
			boolean postoji = false;
			for (Figura f : novcici)
				if (novcic.equals(f)) {
					postoji = true;
					break;
				}
			if (postoji)
				continue;
			novcici.add(novcic);
			count--;
		}
		while (true) {
			int vr = random.nextInt(size), kol = random.nextInt(size);
			if (!polja[vr][kol].moze(igrac))
				continue;
			igrac = new Igrac(polja[vr][kol]);
			break;
		}
		tenkovi = new ArrayList<Figura>();
		while (brojTenkova > 0) {
			Tenk t = null;
			int vr = random.nextInt(size), kol = random.nextInt(size);
			if (!polja[vr][kol].moze(t))
				continue;
			t = new Tenk(polja[vr][kol]);
			t.pokreni();
			tenkovi.add(t);
			brojTenkova--;
		}
	}

	public Igrac getIgrac() {
		return (Igrac) igrac;
	}

	public Polje[][] getPolja() {
		return polja;
	}

	private void azuriraj() {
		for (int i = 0; i < tenkovi.size(); i++) {
			if (tenkovi.get(i).equals(igrac)) {
				this.prekini();
			}
		}
		for (int i = 0; i < novcici.size(); i++) {
			if (novcici.get(i).equals(igrac)) {
				novcici.get(i).getP().repaint();
				novcici.remove(i);
				igra.povecajSkor();
			}
		}

		if (novcici.size() == 0)
			prekini();
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(40);
				azuriraj();
				repaint();
			}
		} catch (InterruptedException e) {
		}
	}

	public void pokreni() {
		if (nit != null)
			nit.interrupt();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				polja[i][j].repaint();
		nit = new Thread(this);
		nit.start();
	}

	public void prekini() {
		for (int i = 0; i < tenkovi.size(); i++)
			((Tenk) tenkovi.get(i)).prekini();
		tenkovi.clear();
		novcici.clear();
		igrac = null;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				polja[i][j].repaint();
		if (nit != null)
			nit.interrupt();
	}
}
