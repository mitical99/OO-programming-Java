package basta;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Basta extends Panel implements Runnable {
	private int vrsta, kolona;
	private Rupa[][] rupe;
	private Igra igra;
	private Thread nit;
	private int povrce = 100;
	private int interval;
	private int brojKoraka;
	private List<Rupa> slobodne;

	public Basta(int vrs, int kol, Igra igra, int interval, int brKoraka) {
		vrsta = vrs;
		kolona = kol;
		this.igra = igra;
		this.interval = interval;
		povrce = 100;
		brojKoraka = brKoraka;
		rupe = new Rupa[vrsta][kolona];
		slobodne = new ArrayList<>();
		konfigurisi();

	}

	private void konfigurisi() {
		setBackground(Color.GREEN);
		setLayout(new GridLayout(vrsta, kolona, 20, 20));
		for (int i = 0; i < vrsta; i++) {
			for (int j = 0; j < kolona; j++) {
				rupe[i][j] = new Rupa(this);
				rupe[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						((Rupa) e.getComponent()).zgazi();
					}
				});
				add(rupe[i][j]);
				slobodne.add(rupe[i][j]);
			}
		}
	}

	public int getBrojKoraka() {
		return brojKoraka;
	}

	public void setBrojKoraka(int brojKoraka) {
		this.brojKoraka = brojKoraka;
		for (int i = 0; i < vrsta; i++) {
			for (int j = 0; j < kolona; j++) {
				rupe[i][j].setBrojKoraka(brojKoraka);
			}
		}
	}

	public synchronized void smanji() {
		povrce--;
		if (povrce <= 0)
			povrce = 0;
		igra.azurirajPovrce(povrce);
	}

	public int getPovrce() {
		return povrce;
	}

	public void setPovrce(int p) {
		povrce = p;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void kreni() {
		nit = new Thread(this);
		nit.start();
	}

	public void prekini() {
		for (int i = 0; i < vrsta; i++)
			for (int j = 0; j < kolona; j++)
				if (rupe[i][j].pokrenutaNit())
					rupe[i][j].prekini();
		if (nit != null)
			nit.interrupt();

	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {

				Rupa slobodna = dohvatiSlobodnu();
				if (slobodna == null)
					continue;
				slobodna.setZivotinja(new Krtica(slobodna));
				slobodna.stvori();
				slobodna.kreni();
				slobodne.remove(slobodna);
				interval = (int) (interval * 0.99);
				Thread.sleep(interval);
				if (povrce <= 0)
					prekini();
			}
		} catch (InterruptedException e) {
		}
	}

	private Rupa dohvatiSlobodnu() {
		Random random = new Random(System.currentTimeMillis());
		if (slobodne.size() == 0)
			return null;
		return slobodne.get(random.nextInt(slobodne.size()));
	}

	public List<Rupa> getList() {
		return slobodne;
	}
}